package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationLauncher extends Application {

	FXObject target;
	
	public ApplicationLauncher(){
	}
	
	public void setTarget(FXObject target) {
		// TODO Auto-generated constructor stub
		this.target = target;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        primaryStage.setTitle("DrawCanvasFX");
        
        LinkedList<DrawableFX> targetList = new LinkedList<>();
        targetList.add(target);
        DrawFXCanvas canvas = new DrawFXCanvas(targetList);
        
        Scene myScene = new Scene(canvas);
        primaryStage.setScene(myScene);
        primaryStage.show();				
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	public void launch(FXObject target) {
		setTarget(target);
		launch("");
		
//		Stage primaryStage = new Stage();
//        LinkedList<DrawableFX> targetList = new LinkedList<>();
//        targetList.add(target);
//        DrawFXCanvas canvas = new DrawFXCanvas(targetList);
//        
//        Scene myScene = new Scene(canvas);
//        primaryStage.setScene(myScene);
//        primaryStage.show();	
		
	}

}
