package edu.ohiou.mfgresearch.labimp.fx;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.sun.j3d.utils.scenegraph.io.SceneGraphStreamWriter;

import edu.ohiou.mfgresearch.implanner.geometry.PartModel;
import edu.ohiou.mfgresearch.implanner.parts.MfgPartModel;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;

public class DrawFXPanel extends BorderPane{

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
			loader.load();
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
			System.out.println(exception.getStackTrace());
		}	
	}
	
	@FXML
	private void initialize() {
		
		targetView.setSelectionModel(null);
		
		targetCol.setCellValueFactory(cellData -> cellData.getValue().name());
		
		visiblityCol.setCellValueFactory(
				param -> new ReadOnlyObjectWrapper<>(param.getValue())
				);
		
		setActiveCol.setCellValueFactory(
				param -> new ReadOnlyObjectWrapper<>(param.getValue())
				);
		
		visiblityCol.setCellFactory(param -> new TableCell<DrawableFX, DrawableFX>() {
			private final Button visibilityBtn = new Button();

			@Override
			protected void updateItem(DrawableFX target, boolean empty) {
				super.updateItem(target, empty);

				if(target == null) {
					setGraphic(null);
					return;
				}
				
				if(target.getVisible().get()) {
					visibilityBtn.setText("Hide");
				} else {
					visibilityBtn.setText("Show");
				}

				target.getVisible().addListener((e) -> {
					if(target.getVisible().get()) {
						visibilityBtn.setText("Hide");
					} else {
						visibilityBtn.setText("Show");
					}
				});

				setAlignment(Pos.CENTER);
				setGraphic(visibilityBtn);
				visibilityBtn.setOnAction(
						event -> {
							target.changeVisibility();
						}
				);
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
				setActiveBtn.setOnAction(
						event -> setActiveTarget(target)
				);
			}
		});
				
		targetView.setItems(getTargetList());
	
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
			System.out.println(e.getStackTrace());
		}
	}
	
    @FXML
    private void handleOpenFileAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Part File");
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Part File", "*.prt"),
				new ExtensionFilter("All Files", "*.*"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			PartModel partModel = new PartModel(selectedFile);
			Swing3DConverter newTarget = new Swing3DConverter(partModel);
			addTarget(newTarget);
		}

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
    	canvas.updateView();
    }
    
    @FXML
    private void handleHideAllAction(ActionEvent event) {
    	getTargetList().stream()
			.filter((t) -> t.getVisible().get())
			.forEach((t) -> t.getVisible().set(!t.getVisible().get()));
    	canvas.updateView();
    }
    
    @FXML
    private void handleClearAllAction(ActionEvent event) {
    	getTargetList().clear();
    	canvas.updateView();
    }
	
}
