package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.fx.test.TestDrawableFX;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationLauncherExternal extends Application {

	static DrawableFX target;
	
	public void setTarget(DrawableFX dfx) {
		// TODO Auto-generated constructor stub
		ApplicationLauncherExternal.target = dfx;
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

	public void launch(DrawableFX dfx) {
		setTarget(dfx);
		launch("");		
	}

}
