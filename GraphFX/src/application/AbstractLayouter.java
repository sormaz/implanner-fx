/**
 * 
 */
package application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class is abstract class which implements layouter interface.
 * @author Arif
 * @Date 11 Sep 2013
 * 
 */
public abstract class AbstractLayouter implements Layouter {
	
	/**
	 * Graph model
	 */
	private Graph myGraph;
	
	/**
	 * Map of nodes and vertexes in layout
	 */
	private HashMap<Node, Vertex2D> nodeMap = new HashMap<Node, Vertex2D>();
	
	/**
	 * Map of arcs and edges in layout
	 */
	private HashMap<Arc, Edge2D> arcMap = new HashMap<Arc, Edge2D>();
	
	/**
	 * Listeners (DrawPanel) to layouter
	 */
	private ArrayList<LayoutChangeListener> observers = new ArrayList<LayoutChangeListener>();
	
	
	
	/**
	 * Create new layouter. Set graph model and set 
	 * layouter as listener to graph model.
	 */
	public AbstractLayouter(Graph myGraph) {
		setGraph(myGraph);
		populateMaps();
	}

	/**
	 * Get graph model
	 */
	public Graph getGraph() {
		return myGraph;
	}

	/**
	 * Set graph model and set layouter as listener to graph 
	 */
	public void setGraph(Graph myGraph) {
		this.myGraph = myGraph;
		myGraph.addListener(this);	
	}

	/**
	 * Return node map
	 */
	public HashMap<Node, Vertex2D> getNodeMap() {
		return nodeMap;
	}

	/**
	 * Return arc map
	 */
	public HashMap<Arc, Edge2D> getArcMap() {
		return arcMap;
	}
	
	/**
	 * Add a pair of node and vertex to map
	 */
	public void addNodeVertexPairToMap(Node aNode, Vertex2D aVertex) {
		nodeMap.put(aNode, aVertex);
	}
	
	/**
	 * Add a pair of arc and edge to map
	 */
	public void addArcEdgePairToMap(Arc anArc, Edge2D anEdge) {
		arcMap.put(anArc, anEdge);
	}
	
	/**
	 * Remove a pair of node and vertex to map
	 */
	public void removeNodeVertexPairFromMap(Node aNode) {
		nodeMap.remove(aNode);
	}
	
	/**
	 * Remove a pair of arc and edge to map
	 */
	public void removeArcEdgePairFromMap(Arc anArc) {
		arcMap.remove(anArc);
	}
	
	/**
	 * Get vertex associated with this node
	 */
	public Vertex2D getVertex(Node aNode) {
		return nodeMap.get(aNode);
	}
	
	/**
	 * Get edge associated with this arc
	 */
	public Edge2D getEdge(Arc anArc) {
		return arcMap.get(anArc);
	}
	
	/**
	 * Clear node and arc map
	 */
	public void clearMaps() {
		arcMap.clear();
		nodeMap.clear();
	}
	
	
	/**
	 * Populate node map and arc map from graph model
	 */
	public void populateMaps() {
		
		clearMaps();
		
		for (Node aNode: myGraph.getNodes()) {
			addNodeVertexPairToMap(aNode, new Vertex2D(aNode, this));
		}
		
		for (Arc anArc: myGraph.getUndirectedArcs()) {
			addArcEdgePairToMap(anArc, new Edge2D(anArc, this));
		}
		
		for (Arc anArc: myGraph.getDirectedArcs()) {
			addArcEdgePairToMap(anArc, new Edge2D(anArc, this));
		}
	}
	
	/**
	 * Add listeners (DrawPanel) to layouter
	 */
	public void addListener(LayoutChangeListener listener) {
		// TODO Auto-generated method stub
		observers.add(listener);
	}

	/**
	 * Remove listeners (DrawPanel) to layouter
	 */
	public void removeListener(LayoutChangeListener listener) {
		// TODO Auto-generated method stub
		observers.remove(listener);
	}

	/**
	 * Notify listeners (DrawPanel) that layout has changed
	 */
	public void notifyDisplayOfLayoutChanged() {
		// TODO Auto-generated method stub
		for (LayoutChangeListener observer : observers) {
			observer.layoutChanged();
		}
		
	}

}
