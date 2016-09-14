package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Text;

public interface DrawableFX {
	
	public LinkedList<Shape> getFXShapes();
	
	public LinkedList<Shape3D> getFX3DShapes();
	
	public LinkedList<Shape> getFXFillShapes();
	
	public LinkedList<Text> getFXStringList();
	
	public Color getStrokeColor();
	
	public Color getFillColor();
	
	public LinkedList<Shape> getFXShapesWColor();
	
	public LinkedList<Shape3D> getFX3DShapesWColor();
	
	public LinkedList<Shape> getFXFillShapesWColor();
	
	@SuppressWarnings("rawtypes")
	public LinkedList getFXSelectables();

	public void addListener(DrawListener listener);
	
	public void removeListener(DrawListener listener);
	
	public Pane getPanel();
	
	void display();

	public StringProperty name();
	
	public BooleanProperty getVisible();
	
	public void changeVisibility();
	
}
