/**
 * 
 */
package edu.ohio.ent.cs5500;

import java.awt.Shape;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class contains the position and size 
 * information of the circle.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public class Circle extends DrawObject {
	
	/**
	 * Radius of the circle
	 */
	private double radius;
	
	/**
	 * Creates a new circle with origin as center
	 * @param radius the radius of the circle
	 */
	public Circle(double radius){
		super();
		setDimension(radius);
	}

	/**
	 * Creates a new circle with center specified by user
	 * @param x the x-coordinate of the center
	 * @param y the y-coordinate of the center
	 * @param radius the radius of the circle
	 */
	public Circle(double x, double y, double radius){
		super(x,y);
		setDimension(radius);
	}
	
	/**
	 * Sets the radius of the circle
	 * @param radius the radius of the circle
	 */
	public void setDimension(double radius){
		this.radius = radius;
	}
	

	
	/**
	 * @return Returns the string representation of the object
	 */
	public String toString(){	
		return String.format(super.toString() + "\nThe %s has radius = %f units\n",
											this.getClass().getSimpleName(),radius);
	}

	@Override
	public Shape getDrawShape() {
		// TODO Auto-generated method stub
		return null;
	}


}
