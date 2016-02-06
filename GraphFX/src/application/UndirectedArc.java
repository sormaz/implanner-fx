/**
 * 
 */
package application;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class contains all the information about the 
 * undirected arcs in the graph.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public class UndirectedArc extends Arc {
	
	/**
	 * Node associated with this arc
	 */
	private Node node1, node2;
	
	/**
	 * Creates a new undirected arc
	 * @param node1 node of the arc
	 * @param node2 node of the arc
	 * @param relation the relation between the nodes in the arc
	 */
	public UndirectedArc(Node node1, Node node2, String relation){
		super(relation);
		this.node1 = node1;
		this.node2 = node2;
		node1.addArc(this);
		node2.addArc(this);
	}
	
	/**
	 * Determines whether a node belongs to the arc
	 * @param aNode graph node 
	 */
	public boolean nodeExists(Node aNode){
			return aNode.equals(node1) || aNode.equals(node2);
	}

	/**
	 * Clears the arc and removes the relations in the nodes 
	 */
	public void clear(){
		node1.removeArc(this);
		node2.removeArc(this);
//		node1 = null;
//		node2 = null;
//		setRelation("");
	}
	
	
	/**
	 * Returns the other node associated with the arc given one node
	 * @param aNode graph node in the arc
	 * @throws UnknownNodeException if node does not belong to arc	 
	 */
	public Node getOtherNode(Node aNode) throws UnknownNodeException{
//		try {
			
			if(aNode.equals(node1)){
				return node2;
			}else if(aNode.equals(node2)){
				return node1;
			}else{
				throw new UnknownNodeException(String.format(
						"%s is not connected to %s",aNode,this));
			}
			
//		} catch (IllegalArgumentException e) {
//			System.out.println("Node does not exist in arc. Failed to get other node");
//		}
//		return null;

	}

//	/**
//	 * @param
//	 * @return Returns the nodes associated with the arc
//	 */
//	public HashSet<Node> getNodes(){
//		HashSet<Node> nodes = new HashSet<Node>();
//		nodes.add(node1);
//		nodes.add(node2);
//		return nodes;
//	}
	
	/**
	 * @param
	 * @return Returns the nodes associated with the arc
	 */
	public Node[] getNodes(){
		return new Node[]{node1, node2};
	}
	
	/**
	 * @param
	 * @return Returns the string representation of the object
	 */
	public String toString(){	
		return String.format("(%s) ---- (%s) : (%s)",
				node1.getName(), node2.getName(), getRelation()); 
//		return String.format("Undir-Arc: Node<%s>-Node<%s>-Relation<%s>", 
//						node1.getName(), node2.getName(), getRelation()); 
	}

}

