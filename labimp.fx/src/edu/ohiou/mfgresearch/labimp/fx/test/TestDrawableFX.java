package edu.ohiou.mfgresearch.labimp.fx.test;

import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.fx.ApplicationLauncherExternal;
import edu.ohiou.mfgresearch.labimp.fx.DrawableFX;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Text;

public class TestDrawableFX implements DrawableFX {

	@Override
	public LinkedList<Shape> getFXShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Shape3D> getFX3DShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Shape> getFXFillShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Text> getFXStringList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList getFXSelectables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableList<DrawableFX> getTargetList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pane getPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub	
		ApplicationLauncherExternal app = new ApplicationLauncherExternal();	
		app.setTarget(this);
		app.launch(this);		
	}

	@Override
	public StringProperty name() {
		// TODO Auto-generated method stub
		return new SimpleStringProperty(this.getClass().getSimpleName());
	}

	@Override
	public BooleanProperty getVisible() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeVisibility() {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestDrawableFX tdfx = new TestDrawableFX();
		tdfx.display();
	}

}
