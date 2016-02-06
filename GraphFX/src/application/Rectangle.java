/**
 * 
 */
package application;

import java.awt.Shape;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class contains the position and size 
 * information of the rectangle.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public class Rectangle extends DrawObject {
	
	/**
	 * The length of the rectangle 
	 */
	private double length; 
	
	/**
	 * The width of the rectangle 
	 */
	private double width;
	
	/**
	 * Creates a new rectangle with origin as center
	 * @param length the length of the rectangle
	 * @param width the width of the rectangle
	 */
	public Rectangle(double length, double width){
		super();
		setDimension(length, width);
	}

	/**
	 * Creates a new rectangle with user-specified origin
	 * @param x the x-coordinate of the center
	 * @param y the y-coordinate of the center
	 * @param length the length of the rectangle
	 * @param width the width of the rectangle
	 */
	public Rectangle(double x, double y, double length, double width){
		super(x,y);
		setDimension(length, width);
	}
	
	/**
	 * Sets the dimensions of the rectangle
	 * @param length the length of the rectangle
	 * @param width the width of the rectangle
	 */
	public void setDimension(double length, double width){
		this.length = length;
		this.width = width;
	}
   
	
	
	/**
	 * Gives a string representation of the rectangle
	 * @return Returns the string representation of the object
	 */
	public String toString(){	
		return String.format(super.toString() + "\nThe %s has length = %f units and width = %f\n units\n",
											this.getClass().getSimpleName(),length,width);
	}

	@Override
	public Shape getDrawShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
