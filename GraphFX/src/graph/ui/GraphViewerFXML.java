package graph.ui;

import edu.ohio.ent.cs5500.Graph;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphViewerFXML extends Application {

	@FXML
	private Graph myGraph  = new Graph();
	
	public GraphViewerFXML() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
            primaryStage.setTitle("GraphViewerFXML");
            
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GraphViewerFXML.fxml"));
			BorderPane myPane = loader.<BorderPane>load();
            
            Scene myScene = new Scene(myPane);
            primaryStage.setScene(myScene);
            primaryStage.show();
    	} catch (Exception e) {
    		GraphDialog.error(e.getMessage());
    	}
    
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
