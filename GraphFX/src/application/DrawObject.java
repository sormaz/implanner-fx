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
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This is the superclass for all shape objects to be displayed in graph
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public abstract class DrawObject implements Drawable {

	/**
	 * Center point of the object
	 */
	private Point2D.Double center;

	
	/**
	 * Creates a new shape object with origin as center
	 */
	public DrawObject() {
		center = new Point2D.Double();
	}
	
	/**
	 * Creates a new shape object with center specified by user
	 * @param x the x-coordinate of the center
	 * @param y the y-coordinate of the center
	 */
	public DrawObject(double x, double y) {
		center = new Point2D.Double(x, y);
	}
	
	
	/**
	 * Sets the center point
	 * @param x the x-coordinate of the center
	 * @param y the y-coordinate of the center
	 */
	public void setCenterPoint(double x, double y) {
		center.setLocation(x, y);
	}
	
	/**
	 * Gets the center point
	 */
	public Point2D.Double getCenter() {
		return center;
	}	
	
	/**
	 * Gives a string representation of the object
	 * @return String representation of the object
	 */
	public String toString() {	
		return String.format("\nThe object is a %s\n" + "Center point of the %1$s is (%f, %f)",
											this.getClass().getSimpleName(),center.getX(),center.getY());
	}
	
	/**
	 * Return graph string to be displayed
	 */
	public String toGraphString() {
		return toString();
	}
	
	/**
	 * Return graph string with coordinates
	 */
    public HashMap<Point2D, String> getGraphString() {
    	HashMap<Point2D, String> graphString = new HashMap<Point2D, String>();
    	graphString.put(center, toGraphString());
    	return graphString;
    }
    
	/**
	 * Get shape item to be drawn on graph
	 */
	public abstract Shape getDrawShape();
    
	public HashMap<Shape, DrawObject> getSelectables() {
		HashMap<Shape, DrawObject> selectable = new HashMap<Shape, DrawObject>();
		selectable.put(getDrawShape(), this);
		return selectable;
	}
	
	@Override
	public ArrayList<Shape> makeDrawList() {
		// TODO Auto-generated method stub
		ArrayList<Shape> drawList = new ArrayList<Shape>();
		drawList.add(getDrawShape());
		return drawList;
	}
	
	public Rectangle2D getBoundingBox() {
		return getDrawShape().getBounds2D();
	}
	
	
	//Empty Methods, Overridden or unused
	
	
//    public void makeDrawSet(DrawPanel p) {
//		makeDrawList();
//    }
	
//	public void setSelectable() {}	
	
	// Only used in layouter
	
	public void addListener(LayoutChangeListener listener) {}

	public void removeListener(LayoutChangeListener listener) {}

	public void notifyDisplayOfLayoutChanged() {}
	
}
