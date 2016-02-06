/**
 * 
 */
package application;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This interface is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This interface gives the definition of the drawable interface.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public interface Drawable {

//    public void makeDrawSet(DrawPanel p);

	/**
	 * Creates and returns the list of shapes to be drawn in display
	 */
    ArrayList<Shape> makeDrawList();
    
	/**
	 * Returns the bounds of the shapes to be drawn. Basically gives data range
	 */
    Rectangle2D getBoundingBox();
    
	/**
	 * Creates and returns the graph string with coordinates (to be displayed in graph)
	 */
    HashMap<Point2D, String> getGraphString();

//    void display(); 
//
//    void display(String title, Dimension size, int closingOperation);

//    void setSelectable();
    
	/**
	 * Creates and returns map of shape and drawObject
	 */
    HashMap<Shape, DrawObject> getSelectables();
    
	/**
	 * Adds LayoutChangeListener (DrawPanel) to layouter (Random Layouter)
	 */
    void addListener(LayoutChangeListener listener);
    
	/**
	 * Removes LayoutChangeListener (DrawPanel) to layouter (Random Layouter)
	 */
    void removeListener(LayoutChangeListener listener);
    
	/**
	 * Notifies listener (DrawPanel) of layout change
	 */
    void notifyDisplayOfLayoutChanged();   
    
}
