package edu.ohiou.mfgresearch.labimp.fx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.javafx.application.PlatformImpl;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;

public class DrawFXPanel extends BorderPane implements DrawListener{

	@FXML
	private DrawFXCanvas canvas;
	@FXML
	private StackPane viewPanel;
	@FXML
	private TableView<DrawableFX> targetView;
	@FXML
	private TableColumn<DrawableFX, String> targetCol; 
	@FXML
	private TableColumn<DrawableFX, DrawableFX> visiblityCol;
	@FXML
	private TableColumn<DrawableFX, DrawableFX> setActiveCol;
	@FXML
	Button slideBtn;
	@FXML
	MenuItem openFileMI;
	@FXML
	MenuItem exitMI;
	@FXML
	MenuItem clearAllMI;
	@FXML
	MenuItem showAllMI;
	@FXML
	MenuItem hideAllMI;

	private ToggleGroup showActiveToggleGroup = new ToggleGroup();

	public DrawFXPanel() {
		URL fxmlURL = this.getClass().getResource("FXPanelView.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(fxmlURL);
		loader.setRoot(this);
		loader.setController(this);
		try {
			PlatformImpl.startup(() -> {});
			loader.load();
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
    		System.out.println(Arrays.toString(exception.getStackTrace()));
		}	
	}

	@FXML
	private void initialize() {

		final CheckBox masterVisibilityControl = new CheckBox();
		masterVisibilityControl.setSelected(true);
		masterVisibilityControl.setOnAction((e) -> {
			if(masterVisibilityControl.isSelected()) {
				handleShowAllAction(e);
			} else {
				handleHideAllAction(e);
			}
		});

		visiblityCol.setGraphic(masterVisibilityControl);

		targetCol.setCellValueFactory(cellData -> cellData.getValue().name());

		visiblityCol.setCellValueFactory(
				param -> new ReadOnlyObjectWrapper<>(param.getValue())
				);

		setActiveCol.setCellValueFactory(
				param -> new ReadOnlyObjectWrapper<>(param.getValue())
				);

		visiblityCol.setCellFactory(param -> new TableCell<DrawableFX, DrawableFX>() {
			private final CheckBox visibilityBtn = new CheckBox();

			@Override
			protected void updateItem(DrawableFX target, boolean empty) {
				super.updateItem(target, empty);

				if(target == null) {
					setGraphic(null);
					return;
				}

				visibilityBtn.setSelected(target.getVisible().get());

				target.getVisible().addListener((e) -> {
					visibilityBtn.setSelected(target.getVisible().get());
				});

				setAlignment(Pos.CENTER);
				setGraphic(visibilityBtn);
				visibilityBtn.setOnAction(event -> {
					target.changeVisibility();
					checkMasterVisibilityState(masterVisibilityControl);
					updateView();
				}); 
			}
		});

		setActiveCol.setCellFactory(param -> new TableCell<DrawableFX, DrawableFX>() {
			private final RadioButton setActiveBtn = new RadioButton("");

			@Override
			protected void updateItem(DrawableFX target, boolean empty) {
				super.updateItem(target, empty);

				if (target == null) {
					setGraphic(null);
					return;
				}

				setActiveBtn.setToggleGroup(showActiveToggleGroup);		
				setActiveBtn.setSelected(getActiveTarget() == target);

				setAlignment(Pos.CENTER);
				setGraphic(setActiveBtn);
				setActiveBtn.setOnAction(event -> {
					setActiveTarget(target);
					checkMasterVisibilityState(masterVisibilityControl);
				});
			}
		});

		targetView.setItems(getTargetList());

		setupSliding();		
	}
	
	public void setupSliding() {
		
		final double expandedWidth = targetView.getPrefWidth();
		
		targetView.setPrefWidth(expandedWidth);
		
		slideBtn.setOnAction(new EventHandler<ActionEvent>() {
	          @Override public void handle(ActionEvent actionEvent) {
	            // create an animation to hide sidebar.
	            final Animation hideSidebar = new Transition() {
	              { setCycleDuration(Duration.millis(250)); }
	              protected void interpolate(double frac) {
	                final double curWidth = expandedWidth * (1.0 - frac);
	                targetView.setPrefWidth(curWidth);
	                targetView.setTranslateX(-expandedWidth + curWidth);
	              }
	            };
	            hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
	              @Override public void handle(ActionEvent actionEvent) {
	            	targetView.setVisible(false);	            	  
	              }
	            });
	    
	            // create an animation to show a sidebar.
	            final Animation showSidebar = new Transition() {
	              { setCycleDuration(Duration.millis(250)); }
	              protected void interpolate(double frac) {
	                final double curWidth = expandedWidth * frac;
	                targetView.setPrefWidth(curWidth);
	                targetView.setTranslateX(-expandedWidth + curWidth);
	              }
	            };
	    
	            if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
	              if (targetView.isVisible()) {
	                hideSidebar.play();
	              } else {
	            	targetView.setVisible(true);
	                showSidebar.play();
	              }
	            }
	          }
	        });
	}
	
	public void checkMasterVisibilityState(CheckBox masterVisibilityControl) {
		Set<DrawableFX> visibleList = getTargetList()
									.stream()
									.filter((t) -> t.getVisible().get())
									.collect(Collectors.toSet());
		if(visibleList.isEmpty()) {
			masterVisibilityControl.setIndeterminate(false);
			masterVisibilityControl.setSelected(false);
		} else if (visibleList.containsAll(getTargetList())) {
			masterVisibilityControl.setIndeterminate(false);
			masterVisibilityControl.setSelected(true);
		} else {
			masterVisibilityControl.setIndeterminate(true);
		}
	}

	public ObservableList<DrawableFX> getTargetList() {
		return canvas.getTargetList();
	}

	public void setTargetList(ObservableList<DrawableFX> tList) {
		canvas.setTargetList(tList);
		targetView.setItems(canvas.getTargetList());
	}

	public void addTarget(DrawableFX target) {
		canvas.addTarget(target);
	}

	public DrawableFX getActiveTarget() {
		return canvas.getActiveTarget();
	}

	public void setActiveTarget(DrawableFX activeTarget) {
		canvas.setActiveTarget(activeTarget);

		try {	

			viewPanel.getChildren().clear();
			viewPanel.getChildren().add(activeTarget.getPanel());

		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		System.out.println(Arrays.toString(e.getStackTrace()));
		}
	}

	@FXML
	private void handleOpenFileAction(ActionEvent event) {
		
		VBox root = new VBox();
		HBox prtHbox = new HBox();
		Label prtLbl = new Label("Part File Location:   ");
		TextField prtTxt = new TextField("");
		HBox.setHgrow(prtTxt, Priority.ALWAYS);
		Button prtBtn = new Button("...");
		prtHbox.getChildren().addAll(prtLbl, prtTxt, prtBtn);
		
		prtHbox.getChildren().forEach(n -> {
			HBox.setMargin(n, new Insets(10,10,10,10));
		});
		
		HBox stkHbox = new HBox();
		Label stkLbl = new Label("Stock File Location:");
		TextField stkTxt = new TextField("");
		HBox.setHgrow(stkTxt, Priority.ALWAYS);
		Button stkBtn = new Button("...");
		stkHbox.getChildren().addAll(stkLbl, stkTxt, stkBtn);
		
		stkHbox.getChildren().forEach(n -> {
			HBox.setMargin(n, new Insets(10,10,10,10));
		});
		
		HBox btnHbox = new HBox();
		btnHbox.setAlignment(Pos.CENTER_RIGHT);
		Button okBtn = new Button("Ok");
		btnHbox.getChildren().add(okBtn);
		btnHbox.getChildren().forEach(n -> {
			HBox.setMargin(n, new Insets(10,10,10,10));
		});
		
		root.getChildren().add(prtHbox);
		root.getChildren().add(stkHbox);
		root.getChildren().add(btnHbox);
		
		Scene scene = new Scene(root, 600, 150);
		Stage stage = new Stage();
		stage.resizableProperty().set(false);
		stage.setScene(scene);
		stage.show();
		
		prtBtn.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Part File");
			
			File existDirectory = 
					new File(FXObject.properties.getProperty("UG_FILE_FOLDER"));
			
			if(existDirectory.exists()) {
				fileChooser.setInitialDirectory(existDirectory);
			} else {
				fileChooser.setInitialDirectory(new File("."));
			}
	
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Part File", "*.prt"),
					new ExtensionFilter("All Files", "*.*"));
			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				prtTxt.setText(selectedFile.getPath());
			}
		});
		
		stkBtn.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Stock File");
			
			File existDirectory = 
					new File(FXObject.properties.getProperty("UG_FILE_FOLDER"));
			
			if(existDirectory.exists()) {
				fileChooser.setInitialDirectory(existDirectory);
			} else {
				fileChooser.setInitialDirectory(new File("."));
			}
	
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Part File", "*.prt"),
					new ExtensionFilter("All Files", "*.*"));
			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				stkTxt.setText(selectedFile.getPath());

			}
		});
		
		okBtn.setOnAction(e -> {
			
			File prtFile = new File(prtTxt.getText());
			File stkFile = new File(stkTxt.getText());
			
			if(prtFile.exists() && stkFile.exists()) {
				PartModelConverter newTarget = new PartModelConverter(prtFile);
				newTarget.setStock(stkFile);
				addTarget(newTarget);
				stage.close();
			}
		});
		


	}

	@FXML
	private void handleExitAction(ActionEvent event) {
		System.exit(0);
		Platform.exit();
	}

	@FXML
	private void handleShowAllAction(ActionEvent event) {
		getTargetList().stream()
		.filter((t) -> !t.getVisible().get())
		.forEach((t) -> t.getVisible().set(!t.getVisible().get()));
		updateView();
	}

	@FXML
	private void handleHideAllAction(ActionEvent event) {
		getTargetList().stream()
		.filter((t) -> t.getVisible().get())
		.forEach((t) -> t.getVisible().set(!t.getVisible().get()));
		updateView();
	}

	@FXML
	private void handleClearAllAction(ActionEvent event) {
		getTargetList().clear();
		updateView();
	}

	@Override
	public void display() {
		ApplicationLauncherExternal app = new ApplicationLauncherExternal();	
		app.setListener(this);
		app.launch(this);	
	}

	@Override
	public void updateView() {
		canvas.updateView();		
	}

	@Override
	public DrawWFPanel getVirtualPanel() {
		return canvas.getVirtualPanel();
	}

	@Override
	public Pane getView() {
		return this;
	}

}
