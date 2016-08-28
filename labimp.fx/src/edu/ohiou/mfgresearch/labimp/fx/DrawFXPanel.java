package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
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
			protected void updateItem(DrawableFX t, boolean empty) {
				super.updateItem(t, empty);

				if(t == null) {
					setGraphic(null);
					return;
				}
				
				if(t.IsVisible()) {
					visibilityBtn.setText("Hide");
				} else {
					visibilityBtn.setText("Show");
				}

				setAlignment(Pos.CENTER);
				setGraphic(visibilityBtn);
				visibilityBtn.setOnAction(
						event -> {
							t.changeVisibility();
							if(t.IsVisible()) {
								visibilityBtn.setText("Hide");
							} else {
								visibilityBtn.setText("Show");
							}
						}
				);
			}
		});
		
		setActiveCol.setCellFactory(param -> new TableCell<DrawableFX, DrawableFX>() {
			private final RadioButton setActiveBtn = new RadioButton("");

			@Override
			protected void updateItem(DrawableFX t, boolean empty) {
				super.updateItem(t, empty);
				
				if (t == null) {
					setGraphic(null);
					return;
				}

				setActiveBtn.setToggleGroup(showActiveToggleGroup);		
				setActiveBtn.setSelected(getActiveTarget() == t);

				setAlignment(Pos.CENTER);
				setGraphic(setActiveBtn);
				setActiveBtn.setOnAction(
						event -> setActiveTarget(t)
				);
			}
		});
		
		
		targetView.setItems(canvas.getTargetList());
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
//			if(activeTarget instanceof SwingConverter) {
////				((SwingConverter)activeTarget).getSwingTarget().init();
////				JPanel jp = ((SwingConverter)activeTarget).getSwingTarget().gettPanel();
////				SwingNode n = new SwingNode();
////				n.setContent(jp);
////				
////				viewPanel.getChildren().clear();
////				viewPanel.getChildren().add(new StackPane(n));
////				
////				JFrame j = new JFrame();
////				j.add(jp);
////				j.pack();
////				j.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
////				j.setVisible(true);
//			} else {
				viewPanel.getChildren().clear();
				viewPanel.getChildren().add(activeTarget.getPanel());
//			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}
	
	
	
}
