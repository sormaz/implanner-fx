package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.fx.test.TestDrawableFX;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ApplicationLauncherExternal extends Application {

	static DrawListener listener;
	
	public void setListener(DrawListener l) {
		// TODO Auto-generated constructor stub
		ApplicationLauncherExternal.listener = l;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        Pane panel = listener.getView();       
        Scene myScene = new Scene(panel);
        primaryStage.setScene(myScene);
        primaryStage.show();				
	}

	public void launch(DrawListener l) {
		setListener(l);
		launch("");		
	}

}
