package edu.ohiou.mfgresearch.labimp.fx;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class DrawFXPanel extends BorderPane{

	@FXML
	private DrawFXCanvas canvas;
	@FXML
	TableView<DrawableFX> targetView;
	@FXML
	TableColumn<DrawableFX, String> targetCol; 
	@FXML
	private TableColumn<DrawableFX, DrawableFX> showCol;
	@FXML
	private TableColumn<DrawableFX, DrawableFX> hideCol;
	@FXML
	private TableColumn<DrawableFX, DrawableFX> setActiveCol;

//	
//	private ObservableList<DrawableFX> targetList = 
//			FXCollections.observableArrayList(canvas.getTargetList());
	
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
		
	}
	
	
}
