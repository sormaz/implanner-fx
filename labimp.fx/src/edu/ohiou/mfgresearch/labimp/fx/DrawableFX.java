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
	 * 2D shapes that does not have fill color. 
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXShapes();
	
	/**
	 * 3D shapes. 
	 * @return list of 3D shapes
	 */
	public LinkedList<Shape3D> getFX3DShapes();
	
	/**
	 * 2D shapes that has fill color.
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXFillShapes();
	
	/**
	 * All strings with size, position and orientation.
	 * @return list of strings
	 */
	public LinkedList<Text> getFXStringList();
	
	/**
	 * Stroke color is added to 2D shapes (from getFXShapes()).
	 * Canvas calls this method to get list of shapes with their desired stroke color.
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXShapesWColor();
	
	/**
	 * Properties like draw mode, cull face and color are applied to 3D shapes (from getFX3DShapes()).
	 * Canvas calls this method to get list of 3D shapes with their desired properties.
	 * @return list of 3D shapes
	 */
	public LinkedList<Shape3D> getFX3DShapesWColor();

	/**
	 * Fill color is added to 2D shapes (from getFXFillShapes()).
	 * Canvas calls this method to get list of shapes with their desired fill color.
	 * @return list of 2D shapes
	 */
	public LinkedList<Shape> getFXFillShapesWColor();
	
	/**
	 * List of modifiable points or objects.
	 * @return list of selectables.
	 */
	@SuppressWarnings("rawtypes")
	public LinkedList getFXSelectables();

	/**
	 * Adds DrawListener to this object.
	 * @param listener listener to be added this object.
	 */
	public void addListener(DrawListener listener);
	
	/**
	 * Removes DrawListener from this object.
	 * @param listener listener to be remove from this object.
	 */
	public void removeListener(DrawListener listener);
	
	/**
	 * Panel to provide controls for this object. 
	 * @return panel that provides controls to modify object.
	 */
	public Pane getPanel();
	
	/**
	 * Displays this object inside through listeners.
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
	 * Changes current visibility status of this object.
	 */
	public void changeVisibility();
	
}
