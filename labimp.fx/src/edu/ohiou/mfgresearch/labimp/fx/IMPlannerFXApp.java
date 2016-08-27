package edu.ohiou.mfgresearch.labimp.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IMPlannerFXApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
            primaryStage.setTitle("IMPlannerFX");
            
            BorderPane panel = new DrawFXPanel();       
            
            Scene myScene = new Scene(panel);
            primaryStage.setScene(myScene);
            primaryStage.show();
            
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("FXPanelView.fxml"));
//			BorderPane myPane = loader.<BorderPane>load();
//            
//            Scene myScene = new Scene(myPane);
//            primaryStage.setScene(myScene);
//            primaryStage.show();
    	} catch (Exception e) {

    	}
    
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	
}
