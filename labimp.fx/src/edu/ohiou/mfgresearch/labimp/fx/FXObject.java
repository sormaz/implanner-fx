/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

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
		ApplicationLauncherExternal app = new ApplicationLauncherExternal();	
		app.setTarget(this);
		app.launch(this);		
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
