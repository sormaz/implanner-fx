/**
 * 
 */
package application;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This interface is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This interface is used as listener to notify changes in graph model.
 * @author Arif
 * @Date 11 Sep 2013
 * 
 */
public interface GraphListener {

	/**
	 * Method called when node is added to graph model
	 */
	void nodeAdded(Node aNode);
	
	/**
	 * Method called when node is removed from graph model
	 */
	void nodeDeleted(Node aNode);
	
	/**
	 * Method called when arc is added to graph model
	 */
	void arcAdded(Arc anArc);
	
	/**
	 * Method called when arc is removed to graph model
	 */
	void arcDeleted(Arc anArc);
	
	/**
	 * Method called when graph model is completely erased
	 */
	void graphErased();
	
}
