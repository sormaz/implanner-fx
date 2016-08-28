/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Text;

/**
 * @author Arif
 *
 */
public abstract class FXObject implements DrawableFX, ViewableFX {

	private boolean isVisible = true;
	protected DrawFXCanvas parentContainer;
	
	public FXObject(DrawFXCanvas parentContainer) {
		setParentContainer(parentContainer);
	}
	
	public void setParentContainer(DrawFXCanvas parentContainer) {
		this.parentContainer = parentContainer;
	}
	
	public StringProperty name() {
		return new SimpleStringProperty(getClass().getSimpleName().toString());
	}
	
	public Boolean IsVisible() {
		return isVisible;
	}
	
	@Override
	public void changeVisibility() {
		isVisible = !isVisible;
		parentContainer.updateView();
	}

//	@Override
//	public void init() {
//		// TODO Auto-generated method stub
//		viewPanel = new Pane();
//	}

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
		return new Pane();
	}

	@Override
	public ObservableList<DrawableFX> getTargetList() {
		// TODO Auto-generated method stub
		return FXCollections.observableArrayList();
	}

	@Override
	public LinkedList<Shape> getFXShapes() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}
	
	@Override
	public LinkedList<Shape3D> getFX3DShapes() {
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
