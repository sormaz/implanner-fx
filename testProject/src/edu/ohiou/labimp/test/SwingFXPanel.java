/**
 * 
 */
package edu.ohiou.labimp.test;

import java.awt.BorderLayout;
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
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Arif
 *
 */
public class SwingFXPanel extends Application {

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
        final SwingNode swingNode = new SwingNode();

        createSwingContent(swingNode);

        StackPane pane = new StackPane();
        pane.getChildren().add(swingNode);

        stage.setTitle("Swing in JavaFX");
        stage.setScene(new Scene(pane, 800,600));
        stage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		Globe t = new Globe();
        		t.init();
        		DrawWFApplet da = new DrawWFApplet(t);
        		da.init();		
        		JPanel j = new JPanel(new BorderLayout());
        		j.add(da.gettCanvas(),BorderLayout.CENTER);
        		j.add(t.gettPanel(), BorderLayout.SOUTH);
            	swingNode.setContent(j);
            }
        });
    }

}
