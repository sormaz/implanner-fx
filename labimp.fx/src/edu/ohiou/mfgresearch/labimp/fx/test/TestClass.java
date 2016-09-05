/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.fx.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Arif
 *
 */
public class TestClass extends Application{

	/**
	 * 
	 */
	public TestClass() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		GridPane canvasControls = new GridPane();
		//		canvasControls.setPrefWidth(1000);
		canvasControls.setAlignment(Pos.CENTER);


		Label viewLbl = new Label("View Point :");
		viewLbl.setPadding(new Insets(4, 4, 4, 4));
		TextField xTxt = new TextField();
		TextField yTxt = new TextField();
		TextField zTxt = new TextField();
		Label scaleLbl = new Label("Scale :");
		scaleLbl.setPadding(new Insets(4, 4, 4, 4));
		TextField scaleTxt = new TextField();	
		CheckBox wcsCheck = new CheckBox("Show WorldCS");
		wcsCheck.setPadding(new Insets(4, 4, 4, 4));
		Button redisplayBtn = new Button("ReDisplay");
		redisplayBtn.setPrefWidth(Double.MAX_VALUE);


		canvasControls.add(viewLbl, 0, 0, 2, 1);
		canvasControls.add(xTxt, 2, 0, 1, 1);
		canvasControls.add(yTxt, 3, 0, 1, 1);
		canvasControls.add(zTxt, 4, 0, 1, 1);
		canvasControls.add(scaleLbl, 5, 0, 1, 1);
		canvasControls.add(scaleTxt, 6, 0, 1, 1);
		canvasControls.add(wcsCheck, 7, 0, 3, 1);
		canvasControls.add(redisplayBtn, 10, 0, 2, 1);

		for (int i = 0; 
				i < canvasControls.getColumnIndex(redisplayBtn) + 
				canvasControls.getColumnSpan(redisplayBtn) ; 
				i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPrefWidth(100);
			canvasControls.getColumnConstraints().add(column);
		}

		RowConstraints row = new RowConstraints();
		row.setPrefHeight(30);
		canvasControls.getRowConstraints().add(row);

		Pane p = new Pane();
		p.setPrefSize(500, 400);
		VBox.setVgrow(p, Priority.ALWAYS);
		canvasControls.setPrefWidth(500);
		
		VBox v = new VBox(p, canvasControls);

		primaryStage.setTitle("Toolbar");
		primaryStage.setScene(new Scene(v));
		primaryStage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
