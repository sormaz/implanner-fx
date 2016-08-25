package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;
import edu.ohiou.labimp.test.Globe;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testApplication extends Application {

	public testApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        primaryStage.setTitle("DrawCanvasFX");
              
        Globe globe = new Globe(3, 0, 0, 0);
        Swing3DConverter globeFX = new Swing3DConverter(globe);
        
        LinkedList<DrawableFX> targetList = new LinkedList<>();
        targetList.add(globeFX);
        DrawFXCanvas canvas = new DrawFXCanvas(targetList);
        
        Scene myScene = new Scene(canvas);
        primaryStage.setScene(myScene);
        primaryStage.show();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
