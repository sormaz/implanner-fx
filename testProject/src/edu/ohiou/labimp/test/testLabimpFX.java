package edu.ohiou.labimp.test;

import edu.ohiou.mfgresearch.labimp.basis.Draw2DApplet;
import edu.ohiou.mfgresearch.labimp.basis.Draw2DPanel;
import edu.ohiou.mfgresearch.labimp.fx.DrawPanelFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class testLabimpFX extends Application {

	public testLabimpFX() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
            primaryStage.setTitle("Test labimp.fx");
            
    		CubicCurveIMPlanner tdo = new CubicCurveIMPlanner();
//    		ViewObject tdo = new ViewObject();
//    		Draw2DApplet da = new Draw2DApplet(tdo);
//    		tdo.display();
            
            Group myPane = new DrawPanelFX();
            
            
            Scene myScene = new Scene(myPane);
            primaryStage.setScene(myScene);
            primaryStage.show();
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    
    }

}
