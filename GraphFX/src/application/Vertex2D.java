/**
 * 
 */
package application;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class defines the draw object for nodes.
 * @author Arif
 * @Date 11 Sep 2013
 * 
 */
public class Vertex2D extends DrawObject implements Vertex {
	
	/**
	 * Diameter of circle used to display node
	 */
	public static final int DIAMETER = 20;
	
	/**
	 * Layouter which contains this draw object
	 * Not used for vertex. But kept for consistency with edge objects.
	 */
	@SuppressWarnings("unused")
	private AbstractLayouter parentLayouter;
	
	/**
	 * Node associated with the vertex
	 */
	private Node owner;

	/**
	 * Create new vertex object
	 */
	public Vertex2D(Node owner, AbstractLayouter parentLayouter) {
		setNode(owner);
		setParentLayouter(parentLayouter);
	}
	
	/**
	 * Set layouter on which this edge is created
	 */
	public void setParentLayouter(AbstractLayouter parentLayouter) {
		this.parentLayouter = parentLayouter;
	}
	
	/**
	 * Get node associated with this vertex
	 */
	public Node getNode() {
		return owner;
	}

	
	/**
	 * Set node associated with this vertex
	 */
	public void setNode(Node mNode) {
		this.owner = mNode;
	}

	public Shape getDrawShape() {
		return new Ellipse2D.Double(getCenter().getX() - DIAMETER / 2, 
									getCenter().getY() - DIAMETER / 2, 
									DIAMETER, 
									DIAMETER);
	}


	public ArrayList<Shape> makeDrawList() {
		// TODO Auto-generated method stub
		ArrayList<Shape> drawList = new ArrayList<Shape>();
		drawList.add(getDrawShape());
		return drawList;
	}
	
	public String toGraphString() {
		return owner.getName();
	}

}
