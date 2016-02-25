package graph.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphViewerFXML extends Application {

	public GraphViewerFXML() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
            primaryStage.setTitle("GraphViewerFXML");
            
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GraphViewerFXML.fxml"));
			BorderPane myPane = (BorderPane) loader.load();
            
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
