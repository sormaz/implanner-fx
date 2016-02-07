/**
 * 
 */
package edu.ohio.ent.cs5500;

import java.util.HashSet;
import java.util.Iterator;


/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class contains all the information of the node object, 
 * i.e. the relation with other nodes.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public class Node {

	/**
	 * Name of the node
	 */
	private String name;

	/**
	 * List of directed arcs in which this node is a child
	 */
	private HashSet<DirectedArc> childList;

	/**
	 * List of directed arcs in which this node is a parent
	 */
	private HashSet<DirectedArc> parentList;

	/**
	 * List of undirected arcs associated with this node
	 */
	private HashSet<UndirectedArc> undirArcList;

	/**
	 * User object
	 */
	private Object user;

	/**
	 * Create a new node object
	 * @param name the name of the node
	 */
	public Node(String name) {
		setName(name);
		childList = new HashSet<DirectedArc>();
		parentList = new HashSet<DirectedArc>();
		undirArcList = new HashSet<UndirectedArc>();
	}

	/**
	 * Sets the name of the node
	 * @param name the name of the node specified by the user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the node
	 * @return name string
	 */
	public String getName() {
		return name;
	}


	//// Not used, complimentary function exists
	/**
	 * Adds a node to this node connected by an undirected arc
	 * @param aNode the node to connect to this node 
	 */
	public void addNode(Node aNode, String relation) {
		new UndirectedArc(this, aNode, relation);
	}



	/**
	 * Removes the relation between the parameter and this node
	 * @param aNode the node whose relation is removed
	 */
	public void removeNode(Node aNode) {

		for(UndirectedArc arc: undirArcList)
		{
			if (arc.nodeExists(aNode)) {
				arc.clear();
				break;
			}
		}

		for(DirectedArc arc: childList)
		{
			if (arc.getParent().equals(aNode)) {
				arc.clear();
				break;
			}
		}

		for(DirectedArc arc: parentList)
		{
			if (arc.getChild().equals(aNode)) {
				arc.clear();
				break;
			}
		}

	}

	//// Not used, complimentary function exists
	/**
	 * Adds a child node connected by a directed arc with this object as parent
	 * @param child child node to this node
	 * @param relation relation between parent and child in directed arc
	 */
	public void addChild(Node child, String relation) {
		new DirectedArc(this, child, relation);
	}

	/**
	 * Removes the child node and the arc associated with it
	 * @param child child node to this node
	 * @throws UnknownNodeException if input node is not a child node
	 */
	public void removeChild(Node child) throws UnknownNodeException {

		for(DirectedArc arc: parentList)
		{
			if (arc.getChild().equals(child)) {
				arc.clear();
				return;
			}
		}

		throw new UnknownNodeException(String.format(
				"%s is not a child to %s",child,this));

	}

	//// Not used, complimentary function exists
	/**
	 * Adds a parent node connected by a directed arc with this object as child
	 * @param parent parent node to this node
	 * @param relation relation between parent and child in directed arc
	 */
	public void addParent(Node parent, String relation) {
		new DirectedArc(parent, this, relation);
	}

	/**
	 * Removes the parent node and the arc associated with it
	 * @param parent parent node to this node
	 * @throws UnknownNodeException if input node is not a parent node
	 */
	public void removeParent(Node parent) throws UnknownNodeException {

		for(DirectedArc arc: childList)
		{
			if (arc.getParent().equals(parent)) {
				arc.clear();
				return;
			}
		}

		throw new UnknownNodeException(String.format(
				"%s is not a parent to %s",parent,this));
	}

	/**
	 * Returns a list of graph nodes connected to this node by undirected arcs
	 * @return List of connected Nodes
	 */
	public HashSet<Node> getNodes() {
		HashSet<Node> newNodeList = new HashSet<Node>();
		Iterator<UndirectedArc> it = undirArcList.iterator();
		while(it.hasNext()) {
			try {
				newNodeList.add(it.next().getOtherNode(this));
			} catch (UnknownNodeException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();

				// No need to catch exception, never happens
				// If it happens, then something is seriously wrong
				System.out.println(String.format("Records inside %s is damaged", this));
				e.printStackTrace();
			}
		}
		return newNodeList;
	}

	/**
	 * Returns a list of parent nodes connected to this node by directed arcs
	 * @return List of parent Nodes
	 */
	public HashSet<Node> getParents() {
		HashSet<Node> newNodeList = new HashSet<Node>();
		Iterator<DirectedArc> it = childList.iterator();
		while(it.hasNext()) {
			newNodeList.add(it.next().getParent());
		}
		return newNodeList;
	}

	/**
	 * Returns a list of child nodes connected to this node by directed arcs
	 * @return List of child Nodes
	 */
	public HashSet<Node> getChildren() {
		HashSet<Node> newNodeList = new HashSet<Node>();
		Iterator<DirectedArc> it = parentList.iterator();
		while(it.hasNext()) {
			newNodeList.add(it.next().getChild());
		}
		return newNodeList;
	}

	/**
	 * Returns true if this node is root node having no parent nodes
	 * @return true if root node
	 */
	public boolean isRoot() {
		return getParents().isEmpty();
	}

	/**
	 * Returns true if this node is leaf node having no child nodes
	 * @return true if child node
	 */
	public boolean isLeaf() {
		return getChildren().isEmpty();
	}

	/**
	 * Returns true if this node has at least one parent node
	 * @return true if child
	 */
	public boolean isChild() {
		return !isRoot(); 
	}

	/**
	 * Returns true if this node is child to the input node
	 * @return true if child
	 */
	public boolean isChild(Node parent) {
		Iterator<DirectedArc> it = childList.iterator();
		while(it.hasNext()) {
			if(it.next().getParent().equals(parent)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if this node has at least one child node
	 * @return true if parent
	 */
	public boolean isParent() {
		return !isLeaf();
	}

	/**
	 * Returns true if this node is parent to the input node
	 * @param child graph node
	 * @return true if parent
	 */
	public boolean isParent(Node child) {
		Iterator<DirectedArc> it = parentList.iterator();
		while(it.hasNext()) {
			if(it.next().getChild().equals(child)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if this node is connected to at least one node by an undirected arc
	 * @return true if connected
	 */
	public boolean isConnected() {
		return !undirArcList.isEmpty();
	}

	/**
	 * Returns true if the input node is connected to this node
	 * <br>Note to self: Checks only connections with undirected arc 
	 * @param aNode
	 * @return true if input node is connected
	 */
	public boolean isConnected(Node aNode) {
		Iterator<UndirectedArc> it = undirArcList.iterator();
		while(it.hasNext()) {
			try{
				if(it.next().getOtherNode(this).equals(aNode)){
					return true;
				}
			}catch(UnknownNodeException e){
				continue;
			}
		}
		return false;
	}

	/**
	 * Adds the directed arc to this node with this node as child
	 * @param arc the DirectedArc to be added
	 */
	public void addInArc(DirectedArc arc) {
		childList.add(arc);
	}

	/**
	 * Removes the directed arc to this node
	 * @param arc the DirectedArc to be removed
	 */
	public void removeInArc(DirectedArc arc) {
		childList.remove(arc);
	}

	/**
	 * Adds the directed arc to this node with this node as parent
	 * @param arc the DirectedArc to be added
	 */
	public void addOutArc(DirectedArc arc) {
		parentList.add(arc);
	}

	/**
	 * Removes the directed arc to this node
	 * @param arc the DirectedArc to be removed
	 */
	public void removeOutArc(DirectedArc arc) {
		parentList.remove(arc);
	}

	/**
	 * Adds the undirected arc to this node
	 * @param arc the UndirectedArc to be added
	 */
	public void addArc(UndirectedArc arc) {
		undirArcList.add(arc);
	}

	/**
	 * Removes the undirected arc to this node
	 * @param arc the UndirectedArc to be removed
	 */
	public void removeArc(UndirectedArc arc) {
		undirArcList.remove(arc);
	}

	/**
	 * Returns the undirected arc between the input node and this node
	 * @param aNode Node
	 * @return the undirected arc between the nodes
	 * @throws UnknownNodeException if no arc exists
	 */	
	public UndirectedArc getArc(Node aNode) throws UnknownNodeException {
		//		try{
		for(UndirectedArc arc: undirArcList)
		{
			try {
				if (arc.getOtherNode(this).equals(aNode)){
					return arc;
				}
			} catch (UnknownNodeException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();

				continue;
			}
		}

		throw new UnknownNodeException(String.format("%s is not connected to %s",aNode,this));
	}

	/**
	 * Returns the directed arc between the input node and this node
	 * @param parent Node
	 * @return the directed arc between the nodes
	 * @throws Exception if no arc exists
	 */	
	public DirectedArc getInArc(Node parent) throws Exception {
		for(DirectedArc arc: childList)
		{
			if (arc.getParent().equals(parent)) {
				return arc;
			}
		}

		throw new Exception(String.format("Failed to retrive directed arc between %s & %s", parent, this));
	}

	/**
	 * Returns the directed arc between the input node and this node
	 * @param child Node
	 * @return the undirected arc between the nodes
	 * @throws Exception if no arc exists
	 */	
	public DirectedArc getOutArc(Node child) throws Exception {
		for(DirectedArc arc: parentList)
		{
			if (arc.getChild().equals(child)) {
				return arc;
			}
		}

		throw new Exception(String.format("Failed to retrive directed arc between %s & %s", this, child));
	}

	/**
	 * Returns a list of directed arcs to nodes having this node as child node
	 * @return list of directed arcs
	 */	
	public HashSet<DirectedArc> getInArcs() {
		return childList;
	}

	/**
	 * Returns a list of directed arcs to nodes having this node as parent node
	 * @return list of directed arcs
	 */	
	public HashSet<DirectedArc> getOutArcs() {
		return parentList;
	}

	/**
	 * Returns a list of undirected arcs to nodes connected to this node
	 * @return list of undirected arcs
	 */	
	public HashSet<UndirectedArc> getArcs() {
		return undirArcList;
	}

	/**
	 * Clears the relations (arcs) between this node and other connected nodes
	 * and removes references to this node in other nodes
	 */
	public void clearArcs() {
		for(UndirectedArc undirArc: undirArcList){
			try {
				undirArc.getOtherNode(this).removeArc(undirArc);
			} catch (UnknownNodeException e) {
				// TODO Auto-generated catch block
				// This exception should never happen

				System.out.println(String.format("Records inside %s is damaged", this));
				e.printStackTrace();
			}
		}
		for(DirectedArc dirArc: childList){
			dirArc.getParent().removeOutArc(dirArc);
		}
		for(DirectedArc dirArc: parentList){
			dirArc.getChild().removeInArc(dirArc);
		}

		undirArcList.clear();
		childList.clear();
		parentList.clear();

	}

	/**
	 * Sets the user object associated with the node
	 * @param o the user object 
	 * @throws IllegalArgumentException if o is null
	 */
	public void setUserObject(Object o) throws IllegalArgumentException {
		if(o == null){
			throw new IllegalArgumentException();
		}
		user = o;
	}

	/**
	 * Gets the user object associated with the arc
	 * @return user object 
	 */
	public Object getUserObject() {
		return user;
	}


	/**
	 * Gives the string representation of the node
	 * @return Returns the string representation of the object
	 */
	public String toString() {	
		return String.format("Node<%s>", name);
	}
}
