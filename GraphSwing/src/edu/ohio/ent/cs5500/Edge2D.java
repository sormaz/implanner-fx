/**
 * 
 */
package edu.ohio.ent.cs5500;

import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class defines the draw object for arcs.
 * @author Arif
 * @Date 11 Sep 2013
 * 
 */
public class Edge2D extends DrawObject implements Edge {

	/**
	 * Layouter which contains this draw object
	 */
	private AbstractLayouter parentLayouter;
	
	/**
	 * Arc associated with the edge
	 */
	private Arc owner;
	
	
	/**
	 * Create new edge object
	 */
	public Edge2D(Arc owner, AbstractLayouter parentLayouter) {
		setArc(owner);
		setParentLayouter(parentLayouter);
	}
	
	/**
	 * Set layouter on which this edge is created
	 */
	public void setParentLayouter(AbstractLayouter parentLayouter) {
		this.parentLayouter = parentLayouter;
	}
	
	/**
	 * Get arc associated with this edge
	 */
	public Arc getArc() {
		return owner;
	}

	/**
	 * Set arc associated with this edge
	 */
	public void setArc(Arc owner) {
		this.owner = owner;
	}
	
	public Shape getDrawShape() {
		Shape drawShape;
		Node[] arcNodes = owner.getNodes();
		Point2D p1 = parentLayouter.getVertex(arcNodes[0]).getCenter();
		Point2D p2 = parentLayouter.getVertex(arcNodes[1]).getCenter();
		if (owner instanceof UndirectedArc) {
			drawShape = new Line2D.Double(p1, p2);	
		} else {
			double x1 = p1.getX();
			double x2 = p2.getX();
			double y1 = p1.getY();
			double y2 = p2.getY();
			double r = 0.5 * p1.distance(p2);
			
			double startAngle = getStartAngle(p1, p2);
			
			drawShape = new Arc2D.Double
							(0.5 * (x1 + x2) - r, 
							 0.5 * (y1 + y2) - r,
							 2 * r, 2 * r, 
							 Math.toDegrees(startAngle),
							 180, Arc2D.OPEN);
		}
		return drawShape;
	}
	
	/**
	 * Get starting angle of the arc
	 */
	public double getStartAngle(Point2D p1, Point2D p2) {
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();
		
//		double startAngle = Math.atan((x2-x1)/(y2-y1)) + Math.PI / 2;
		double startAngle = Math.atan2((x2-x1), (y2-y1)) + Math.PI / 2;
		return startAngle;
	}

	@Override
	public ArrayList<Shape> makeDrawList() {
		// TODO Auto-generated method stub
		ArrayList<Shape> drawList = new ArrayList<Shape>();
		drawList.add(getDrawShape());
		return drawList;
	}
	
	public String toGraphString() {
		return owner.getRelation();
	}

}
