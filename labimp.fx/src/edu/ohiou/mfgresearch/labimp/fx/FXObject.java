/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.ohiou.labimp.test.Globe;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Arif
 *
 */
public class FXObject implements DrawableFX, ViewableFX {
	
	protected Pane viewPanel;
	protected DrawFXCanvas parentContainer;
	
	public void setParentContainer(DrawFXCanvas parentContainer) {
		this.parentContainer = parentContainer;
	}

	public FXObject(DrawFXCanvas parentContainer) {
		this.parentContainer = parentContainer;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		viewPanel = new Pane();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
//		ApplicationLauncher app = new ApplicationLauncher();
//		app.setTarget(this);
//		
//		app.launch(this);		
		
	}

	@Override
	public Pane getPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<DrawableFX> getTargetList() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

	@Override
	public LinkedList<Shape> getFXShapes() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

	@Override
	public LinkedList<Shape> getFXFillShapes() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

	@Override
	public LinkedList<Text> getFXStringList() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

	@Override
	public LinkedList getFXSelectables() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

}
