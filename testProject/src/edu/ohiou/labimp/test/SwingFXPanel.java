/**
 * 
 */
package edu.ohiou.labimp.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Shape;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFApplet;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawableWF;
import edu.ohiou.mfgresearch.labimp.fx.Globe;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Arif
 *
 */
public class SwingFXPanel extends Application {

	Globe t1 = new Globe(1);
	Globe t2 = new Globe(2);
	DrawWFApplet da1 = new DrawWFApplet(t1);
	DrawWFApplet da2 = new DrawWFApplet(t2);

	/**
	 * 
	 */
	public SwingFXPanel() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		final SwingNode swingNode1 = new SwingNode();
		final SwingNode swingNode2 = new SwingNode();
		final SwingNode swingNode3 = new SwingNode();
		final SwingNode swingNode4 = new SwingNode();

		da1.init();
		da2.init();

//		da1.gettCanvas().setOpaque(false);
//		da1.gettCanvas().setBackground(new Color(0, 0, 0, 0));

//		da2.gettCanvas().setOpaque(false);
//		da2.gettCanvas().setBackground(new Color(0, 0, 0, 0));

		createSwingContent(swingNode1, swingNode2);
		createSwingContent2(swingNode3, swingNode4);

		BorderPane pane = new BorderPane();
		StackPane a1 = new StackPane();
		a1.getChildren().addAll(swingNode1, swingNode3);
		StackPane a2 = new StackPane(swingNode2);
		pane.setCenter(a1);
		pane.setBottom(a2);

		stage.setTitle("Swing in JavaFX");
		Scene scene = new Scene(pane, 800,600);
		stage.setScene(scene);
		stage.show();

//		swingNode1.setOnMouseDragged(event -> {
//					
//		});
//		
//		swingNode2.setOnMouseDragged(event -> {
//	
//		});
		
		pane.widthProperty().addListener(evt -> {
			//        	swingNode1.resize(a1.getWidth(), a1.getHeight());
			//        	swingNode2.resize(a2.getWidth(), a2.getHeight());
			createSwingContent(swingNode1, swingNode2);
			createSwingContent2(swingNode3, swingNode4);
		});
		pane.heightProperty().addListener(evt -> {
			//        	swingNode1.resize(a1.getWidth(), a1.getHeight());
			//        	swingNode2.resize(a2.getWidth(), a2.getHeight());
			createSwingContent(swingNode1, swingNode2);
			createSwingContent2(swingNode3, swingNode4);
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	private void createSwingContent(final SwingNode swingNode1, 
			final SwingNode swingNode2) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode1.setContent(da1.gettCanvas());
				swingNode2.setContent(t1.gettPanel());
			}
		});
	}

	private void createSwingContent2(final SwingNode swingNode1, 
			final SwingNode swingNode2) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode1.setContent(da2.gettCanvas());
				swingNode2.setContent(t2.gettPanel());
			}
		});
	}

}
