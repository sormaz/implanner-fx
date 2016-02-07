/**
 * 
 */
package edu.ohio.ent.cs5500;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>Layouter class which creates random layout for the graph points.
 * @author Arif
 * @Date 11 Sep 2013
 * 
 */
public class RandomLayouter extends AbstractLayouter implements Drawable {
	
	/**
	 * Create new random layouter
	 */
	public RandomLayouter(Graph myGraph) {
		super(myGraph);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Make new random layout
	 */
	public void makeLayout() {
		// TODO Auto-generated method stub
		
		Random randNumber = new Random();
		int xCoordinate, yCoordinate; 
		
		int minX = 0;
		int minY = 0;
		int maxX = 400;
		int maxY = 400;	
		
		for (Vertex2D aVertex : getNodeMap().values()) {

			xCoordinate = minX + randNumber.nextInt(maxX - minX);
			yCoordinate = minY + randNumber.nextInt(maxY - minY);
			
			aVertex.setCenterPoint(xCoordinate, yCoordinate);
		}
		
//		for (Vertex2D aVertex : getNodeMap().values()) {
//			System.out.println(aVertex.getCenter());
//		}
		
		notifyDisplayOfLayoutChanged();
	}

	/**
	 * Add node to layout using random coordinates
	 */
	@Override
	public void nodeAdded(Node aNode) {
		// TODO Auto-generated method stub

		Vertex2D newVertex = new Vertex2D(aNode, this);
		
		int minX = 0;
		int minY = 0;
		int maxX = 400;
		int maxY = 400;
		
		Random randNumber = new Random();
		int xCoordinate = minX + randNumber.nextInt(maxX - minX);
		int yCoordinate = minY + randNumber.nextInt(maxY - minY);
		newVertex.setCenterPoint(xCoordinate, yCoordinate);
		
		addNodeVertexPairToMap(aNode, newVertex);
		notifyDisplayOfLayoutChanged();

	}

	/**
	 * Delete node from layout
	 */
	@Override
	public void nodeDeleted(Node aNode) {
//		 TODO Auto-generated method stub
		
		removeNodeVertexPairFromMap(aNode);
		notifyDisplayOfLayoutChanged();
		
	}

	/**
	 * Add arc to layout
	 */
	@Override
	public void arcAdded(Arc anArc) {
		// TODO Auto-generated method stub
		
		Edge2D newEdge =  new Edge2D(anArc, this);
		addArcEdgePairToMap(anArc, newEdge);
		notifyDisplayOfLayoutChanged();
		
	}

	/**
	 * Delete arc from layout
	 */
	@Override
	public void arcDeleted(Arc anArc) {
		// TODO Auto-generated method stub

		removeArcEdgePairFromMap(anArc);
		notifyDisplayOfLayoutChanged();

	}
	
	/**
	 * Clear layout
	 */
	@Override
	public void graphErased() {
		// TODO Auto-generated method stub
		
		clearMaps();
		notifyDisplayOfLayoutChanged();
	}
	
	/**
	 * Add listener (DrawPanel) to layout change
	 */
	@Override
	public void addListener(LayoutChangeListener listener) {
		// TODO Auto-generated method stub
		super.addListener(listener);
	}

	/**
	 * Remove listener (DrawPanel) to layout change
	 */
	@Override
	public void removeListener(LayoutChangeListener listener) {
		// TODO Auto-generated method stub
		super.removeListener(listener);
	}

	/**
	 * Notify listener (DrawPanel) of change in layout
	 */
	@Override
	public void notifyDisplayOfLayoutChanged() {
		// TODO Auto-generated method stub
		super.notifyDisplayOfLayoutChanged();
	}

//	@Override
//	public void makeDrawSet(DrawPanel p) {
//		// TODO Auto-generated method stub
//		makeDrawList();
//	}

	/**
	 * Creates and returns the list of shapes to be drawn in display
	 */
	public ArrayList<Shape> makeDrawList() {
		// TODO Auto-generated method stub
		ArrayList<Shape> drawList = new ArrayList<Shape>();
		
		for (Vertex2D aVertex : getNodeMap().values()) {
			drawList.add(aVertex.getDrawShape());
		}
		
		for (Edge2D anEdge : getArcMap().values()) {
			drawList.add(anEdge.getDrawShape());
		}
		
		return drawList;
	}
	
	public Rectangle2D getBoundingBox() {
		
		GeneralPath b = new GeneralPath();
		
		for (DrawObject d : getNodeMap().values()) {			
			b.append(d.getBoundingBox(), false);
		}
		
		for (DrawObject d : getArcMap().values()) {			
			b.append(d.getBoundingBox(), false);
		}

		if (b.getBounds2D().isEmpty()) {
			return new Rectangle2D.Double(0, 0, 1, 1);
		} else {
			return b.getBounds2D();
		}
	}
	
	/**
	 * Creates and returns the graph string with coordinates (to be displayed in graph)
	 */
	public HashMap<Point2D, String> getGraphString() {
		// TODO Auto-generated method stub
		HashMap<Point2D, String> graphString = new HashMap<Point2D, String>();
		for (Vertex2D aVertex : getNodeMap().values()) {
			for (Point2D key : aVertex.getGraphString().keySet()) {
				String string = graphString.get(key);
				if (string != null) {
					string += aVertex.getGraphString().get(key);
					graphString.put(key, string);
				} else {
					string = aVertex.getGraphString().get(key);
					graphString.put(key, string);
				}	
			}
		}
		
		return graphString;
	}
		
	/**
	 * Creates and returns map of shape and drawObject
	 */
	public HashMap<Shape, DrawObject> getSelectables() {
		// TODO Auto-generated method stub
		HashMap<Shape, DrawObject> selectables = new HashMap<Shape, DrawObject>();
		
		for (Vertex2D aVertex : getNodeMap().values()) {
			selectables.putAll(aVertex.getSelectables());
		}
		
		return selectables;
	}

//	@Override
//	public void setSelectable() {}

//	@Override
//	public void display() {}
//
//	@Override
//	public void display(String title, Dimension size, int closingOperation) {}

}
