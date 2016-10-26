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
	
	/**
	 * Defines all 2D shapes that does not have fill color. 
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXShapes();
	
	/**
	 * Defines all 3D shapes. 
	 * @return list of 3D shapes
	 */
	public LinkedList<Shape3D> getFX3DShapes();
	
	/**
	 * Defines all 2D shapes that has fill color.
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXFillShapes();
	
	/**
	 * Defines all strings with size, position and orientation.
	 * @return list of strings
	 */
	public LinkedList<Text> getFXStringList();
	
	/**
	 * This is where we attach stroke color to the 2D shapes.
	 * Canvas calls this method to get list of shapes with their desired stroke color.
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXShapesWColor();
	
	/**
	 * This is where we attach properties like 
	 * draw mode, cull face and color to 3D shapes.
	 * Canvas calls this method to get list of shapes with their desired color.
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape3D> getFX3DShapesWColor();

	/**
	 * This is where we attach fill color to the 2D shapes.
	 * Canvas calls this method to get list of shapes with their desired fill color.
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXFillShapesWColor();
	
	/**
	 * Defines list of modifiable points or objects.
	 * @return list of selectables.
	 */
	@SuppressWarnings("rawtypes")
	public LinkedList getFXSelectables();

	/**
	 * Method to add DrawListener to this object.
	 * @param listener listener to display this object.
	 */
	public void addListener(DrawListener listener);
	
	/**
	 * Method to remove DrawListener from this object.
	 * @param listener listener to display this object.
	 */
	public void removeListener(DrawListener listener);
	
	/**
	 * Defines panel to control this object. 
	 * @return panel that provides controls to modify object.
	 */
	public Pane getPanel();
	
	/**
	 * Method to display this object inside it's respective listeners.
	 * @param args string argument to pass to listener.
	 */
	void display(String ... args);

	/**
	 * Tooltip associated with this object.
	 * @return tooltip
	 */
	public String getToolTip();
	
	/**
	 * Name of object that is displayed on the target table.
	 * @return name of this object.
	 */
	public StringProperty name();
	
	/**
	 * Property that states the visibility of this object.
	 * @return visibility property.
	 */
	public BooleanProperty getVisible();
	
	/**
	 * Change current visibility status of this object.
	 */
	public void changeVisibility();
	
}
