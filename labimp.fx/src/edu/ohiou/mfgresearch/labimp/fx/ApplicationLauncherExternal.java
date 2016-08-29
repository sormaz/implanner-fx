package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationLauncherExternal extends Application {

	static FXObject target;
	
	public void setTarget(FXObject target) {
		// TODO Auto-generated constructor stub
		ApplicationLauncherExternal.target = target;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        primaryStage.setTitle(target.name().get());
                
        DrawFXPanel panel = new DrawFXPanel();
        panel.addTarget(target);
        
        Scene myScene = new Scene(panel);
        primaryStage.setScene(myScene);
        primaryStage.show();				
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void launch(FXObject target) {
		setTarget(target);
		launch("");		
	}

}
