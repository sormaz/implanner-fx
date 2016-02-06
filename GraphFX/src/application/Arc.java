/**
 * 
 */
package application;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This is the superclass for all types of arcs. 
 * Contains the properties and functionalities common to both
 * undirected and directed arcs.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public abstract class Arc {
	
	/**
	 * User object
	 */
	private Object user;
	
	/**
	 * Relation between the nodes in the arc
	 */
	private String relation;
	
	/**
	 * Creates a new arc
	 * @param relation the relationship between the nodes of the arc
	 */
	public Arc(String relation){
		setRelation(relation);
	}
	

	/**
	 * Determines whether a node belongs to the arc
	 * @param aNode graph node 
	 */
	public abstract boolean nodeExists(Node aNode);

	/**
	 * Clears the arc and removes the relations in the nodes 
	 */
	public abstract void clear();
	
	public abstract Node[] getNodes();
	
	/**
	 * Sets the relationship between the nodes associated with the arc
	 * @param relation the relationship between the nodes in the arc
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * Gets the user object associated with the arc
	 * @return user object 
	 */
	public String getRelation(){
		return relation;
	}
	
	/**
	 * Sets the user object associated with the arc
	 * @param o the user object 
	 * @throws IllegalArgumentException if o is null
	 */
	public void setUserObject(Object o) throws IllegalArgumentException{
//		try {
			if(o == null){
				throw new IllegalArgumentException();
			}
			user = o;
//		} catch (IllegalArgumentException e) {
//			System.out.println("Cannot set user object to null");
//		}
	}

	/**
	 * Gets the user object associated with the arc
	 * @return user object 
	 */
	public Object getUserObject(){
		return user;
	}
}

