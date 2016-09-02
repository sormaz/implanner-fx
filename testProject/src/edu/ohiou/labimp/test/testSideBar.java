package edu.ohiou.labimp.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class testSideBar extends Application {

	public testSideBar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		SideBar sideBar = new SideBar(300, new StackPane());
		
		HBox root = new HBox();
		HBox.setHgrow(sideBar, Priority.ALWAYS);
		
		root.getChildren().add(sideBar);
		
		AnchorPane buttonHolder = new AnchorPane();
		buttonHolder.setPrefWidth(20);
		buttonHolder.setPrefHeight(600);
		Button control = sideBar.getControlButton();
		AnchorPane.setBottomAnchor(control, 0.0);
		AnchorPane.setTopAnchor(control, 0.0);
		AnchorPane.setLeftAnchor(control, 0.0);
		AnchorPane.setRightAnchor(control, 0.0);
		buttonHolder.getChildren().add(control);
		
		root.getChildren().add(buttonHolder);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
