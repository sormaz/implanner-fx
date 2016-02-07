/**
 * 
 */
package edu.ohio.ent.cs5500;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class contains all the information about the 
 * directed arcs in the graph.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public class DirectedArc extends Arc {

	/**
	 * Parent node of the arc
	 */
	private Node parent;
	
	/**
	 * Child node of the arc
	 */
	private Node child;
	
	/**
	 * Creates a new directed arc
	 * @param parent parent node of the arc
	 * @param child child node of the arc
	 * @param relation the relation between the nodes in the arc
	 */
	public DirectedArc(Node parent, Node child, String relation){
		super(relation);
		setParent(parent);
		setChild(child);
		parent.addOutArc(this);
		child.addInArc(this);
	}
	
	
	/**
	 * Determines whether a node belongs to the arc
	 * @param aNode graph node 
	 */
	public boolean nodeExists(Node aNode){
		return aNode.equals(parent) || aNode.equals(child);
	}

	/**
	 * Clears the arc and removes the relations in the nodes 
	 */
	public void clear(){
		parent.removeOutArc(this);
		child.removeInArc(this);
//		parent = null;
//		child = null;
//		setRelation("");
	}
	
	public Node[] getNodes(){
		return new Node[]{parent, child};
	}

	/**
	 * Returns the parent graph node in the arc
	 * @return parent Graph node
	 */
	public Node getParent(){
		return parent;
	}
	
	/**
	 * Returns the child graph node in the arc
	 * @return parent Graph node
	 */
	public Node getChild(){
		return child;
	}
	
	/**
	 * Sets the parent graph node in the arc
	 * @param parent Graph node
	 */
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	/**
	 * Sets the parent graph node in the arc
	 * @param child Graph node
	 */
	public void setChild(Node child){
		this.child = child;
	}
	
	/**
	 * @param
	 * @return Returns the string representation of the object
	 */
	public String toString(){	
		return String.format("(%s) -->> (%s) : (%s)",
				parent.getName(), child.getName(), getRelation()); 
//		return String.format("Dir-Arc: Parent<%s>-Child<%s>-Relation<%s>",
//						parent.getName(), child.getName(), getRelation()); 
	}

}	

