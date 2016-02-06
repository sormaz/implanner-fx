/**
 * 
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class contains all the information of the graph, 
 * i.e. the nodes and the arcs.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public class Graph {

	/**
	 * List of nodes
	 */
	private HashSet<Node> nodeList;

	/**
	 * List of directed arcs
	 */
	private HashSet<DirectedArc> dirArcList;

	/**
	 * List of undirected arcs
	 */
	private HashSet<UndirectedArc> undirArcList;
	
	/**
	 * List of listeners (GraphViewer and Layouter) to changes in graph model
	 */
	private ArrayList<GraphListener> observers = new ArrayList<GraphListener>();
	
	/**
	 * Creates a new graph
	 */
	public Graph(){
		nodeList = new HashSet<Node>();
		dirArcList = new HashSet<DirectedArc>();
		undirArcList = new HashSet<UndirectedArc>();
	}

	/**
	 * Creates a new graph
	 * by reading the contents of the graph from a file with specified filename
	 * @param filename the name of the file from which to read
	 */
	public Graph(String filename){
		this();		
		read(filename);
	}

	/**
	 * Creates a new graph
	 * by reading the contents of the graph from a file
	 * @param file the file from which to read
	 */
	public Graph(File file){
		this();
		read(file);
	}

	/**
	 * Creates a new graph
	 * by reading the contents of the graph from supplied stream parameter
	 * @param inStream the stream parameter from which to read
	 * @param readingFromFile boolean parameter which specifies whether we are
	 * taking commands from user or reading commands from a file
	 */
	public Graph(InputStream inStream){
		this();
		read(inStream);
	}
	
	/**
	 * Add listener (GraphViewer and Layouter) to graph model
	 */
	public void addListener(GraphListener listener) {
		observers.add(listener);
	}
	
	/**
	 * Remove listener (GraphViewer and Layouter)  to graph model
	 */
	public void removeListener(GraphListener listener) {
		observers.remove(listener);
	}
	
	/**
	 * Notify listeners (GraphViewer and Layouter) of node addition to graph model
	 */
	public void notifyNodeAddtion(Node aNode) {
		
		for (GraphListener observer: observers) {
			observer.nodeAdded(aNode);
		}
		
	}

	/**
	 * Notify listeners (GraphViewer and Layouter) of node deletion from graph model
	 */
	public void notifyNodeDeletion(Node aNode) {
		
		for (GraphListener observer: observers) {
			observer.nodeDeleted(aNode);
		}
		
	}
	
	/**
	 * Notify listeners (GraphViewer and Layouter) of arc addition to graph model
	 */
	public void notifyArcAddtion(Arc anArc) {
		
		for (GraphListener observer: observers) {
			observer.arcAdded(anArc);
		}
		
	}
	
	/**
	 * Notify listeners (GraphViewer and Layouter) of arc deletion from graph model
	 */
	public void notifyArcDeletion(Arc anArc) {
		
		for (GraphListener observer: observers) {
			observer.arcDeleted(anArc);
		}
		
	}
	
	/**
	 * Notify listeners (GraphViewer and Layouter) that graph model is cleared
	 */
	public void notifyGraphErased() {
		
		for (GraphListener observer: observers) {
			observer.graphErased();
		}
		
	}
	
	/**
	 * Clear graph model
	 */
	public void clearGraph() {
		nodeList.clear();
		dirArcList.clear();
		undirArcList.clear();
		notifyGraphErased();
	}
	


	/**
	 * Reads the contents of the graph from supplied stream parameter
	 * @param inStream the stream parameter from which to read
	 * @param readingFromFile boolean parameter which specifies whether we are
	 * taking commands from user or reading commands from a file
	 * <br>the boolean parameter is created because we want to printout the 
	 * commands that we read from file, but when we are taking inputs 
	 * from keyboard, we do not reprint the commands
	 */
	public void read(InputStream inStream){
		Scanner input;
		String inputString;
		input = new Scanner(inStream);
		do{
//			System.out.print(">> ");
			inputString = input.nextLine().trim();
//			System.out.println(inputString);
			ArrayList<String> commandList = getCommandList(inputString);
			try {
				executeCommand(commandList);			
			}catch (NumberFormatException e) {
				System.out.println("\nCould not parse string to double\n");
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		}while(input.hasNext());
		input.close();
	}

	/**
	 * Reads the contents of the graph from a file with specified filename
	 * @param filename the name of the file from which to read
	 */
	public void read(String filename){
		File file = new File(filename);		
		read(file);
	}

	/**
	 * Reads the contents of the graph from a file
	 * @param file the file from which to read
	 */
	public void read(File file){
		InputStream inStream;

		try {
			inStream = new FileInputStream(file);
			read(inStream);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Modifies input string such to remove all unnecessary space
	 * and converts all to lower caps
	 * @param inputString the original input from the user
	 * @return Returns the user specified command in an arraylist, 
	 * with each word as a separate element inside the arraylist
	 */
	public ArrayList<String> getCommandList(String inputString){
		String commands[] = inputString.toLowerCase().split(" ");

		ArrayList<String> commandList = new ArrayList<String>();

		for (String command : commands){
			if(!command.isEmpty()){ 
				commandList.add(command);
			}
		}
		return commandList;
	}	

	/**
	 * Calls the appropriate function according to the command specified
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeCommand(ArrayList<String> commandList) throws Exception{
		if(commandList.isEmpty()){
			return;
		}else if(commandList.get(0).equalsIgnoreCase("quit")){
			System.exit(0); 
		}else{
			switch(commandList.get(0)){
			case "nodes":
				executeNodes(commandList);
				break;
			case "arcs":
				executeArcs(commandList);
				break;
			case "node":
				executeNode(commandList);
				break;
			case "dir-arc":
				executeDirArc(commandList);
				break;
			case "undir-arc":
				executeUnDirArc(commandList);
				break;
			case "printout":
				executePrintout(commandList);
				break;
			case "delete":
				executeDelete(commandList);
				break;
			case "rectangle":
				executeRectangle(commandList);
				break;
			case "circle":
				executeCircle(commandList);
				break;
			default:
				throw new Exception("\nUnknown Command\n");
			}
		}
	}


	/**
	 * Executes the "Delete" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeDelete(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 2 && commandList.size() != 3){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \ndeletenode_name" +
					"\ndelete node1_name node2_name\n");
		}

		if(commandList.size() == 2){
			Node node1 = getNode(commandList.get(1));
			removeNode(node1);
		}else{
			Node node1 = getNode(commandList.get(1));
			Node node2 = getNode(commandList.get(2));
			Boolean arcExists = false;
			
			
			try {
				UndirectedArc undirArc = getUndirArc(node1, node2);
				arcExists = true;
				removeArc(undirArc);
			} catch (Exception e) {

			}
			try {
				DirectedArc dirArc = getDirArc(node1, node2);
				arcExists = true;
				removeArc(dirArc);
			} catch (Exception e) {

			}
			try {
				DirectedArc dirArc = getDirArc(node2, node1);
				arcExists = true;
				removeArc(dirArc);
			} catch (Exception e) {

			}

			if(!arcExists){
				throw new Exception("No arcs exist between nodes");
			}

		}		
	}


	/**
	 * Executes the "Printout" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executePrintout(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 2 && commandList.size() != 3){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \nprintout node_name" +
					"\nprintout node1_name node2_name\n");
		}

		if(commandList.size() == 2){
			Node node = getNode(commandList.get(1));
			
			System.out.println(node.toString());
			
			// Also printout all the arcs the node is connected to
			// I kept it because it helped in debugging
			for(UndirectedArc undirArc: node.getArcs()){
				System.out.println(undirArc.toString());
			}
			for(DirectedArc dirArc: node.getInArcs()){
				System.out.println(dirArc.toString());
			}
			for(DirectedArc dirArc: node.getOutArcs()){
				System.out.println(dirArc.toString());
			}


		}else{
			Node node1 = getNode(commandList.get(1));
			Node node2 = getNode(commandList.get(2));
			Boolean arcExists = false;

			try {
				System.out.println(getDirArc(node1, node2).toString());
				arcExists = true;
			} catch (Exception e) {

			}
			try {
				System.out.println(getDirArc(node2, node1).toString());
				arcExists = true;
			} catch (Exception e) {

			}
			try {
				System.out.println(getUndirArc(node1, node2).toString());
				arcExists = true;
			} catch (Exception e) {

			}

			if(!arcExists){
				System.out.println("No arcs exist between node");
			}

		}		
	}


	/**
	 * Executes the "undir-arc" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeUnDirArc(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 4){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \nundir-arc node1_name node2_name relation\n");
		}
		
		Node node1, node2;
		node1 = getNode(commandList.get(1));
		node2 = getNode(commandList.get(2));

		addBiArc(node1, node2, commandList.get(3));
		
	}

	/**
	 * Executes the "dir-arc" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeDirArc(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 4){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \ndir-arc parent_name child_name relation\n");
		}

		Node parent, child;
		parent = getNode(commandList.get(1));
		child = getNode(commandList.get(2));

		addDiArc(parent, child, commandList.get(3));
	
	}

	/**
	 * Executes the "Node" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeNode(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 2){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \nnode node_name\n");
		}

		Node newNode;
		try {
			newNode = getNode(commandList.get(1));
		} catch (Exception e) {
			newNode = new Node(commandList.get(1));
		}
		addNode(newNode);
	}

	/**
	 * Executes the "Arcs" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeArcs(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 1){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \narcs\n");
		}

		HashSet<DirectedArc> dirArcList = getDirectedArcs();
		HashSet<UndirectedArc> undirArcList = getUndirectedArcs();

		for(DirectedArc dirArc: dirArcList){
			System.out.println(dirArc.toString());
		}

		for(UndirectedArc undirArc: undirArcList){
			System.out.println(undirArc.toString());
		}

	}

	/**
	 * Executes the "Nodes" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeNodes(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 1){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \nnodes\n");
		}

		HashSet<Node> nodeList = getNodes();

		for(Node aNode: nodeList){
			System.out.println(aNode.toString());
		}

	}


	/**
	 * Executes the "Rectangle" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeRectangle(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 3 && commandList.size() != 5){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \nrectangle length width" +
					"\nrectangle X Y length width\n");
		}

		double x,y,length,width;

		if(commandList.size() == 3){
			length = Double.parseDouble(commandList.get(1));
			width = Double.parseDouble(commandList.get(2));
			Rectangle rect = new Rectangle(length,width);
			System.out.println(rect.toString());
		}else{
			x = Double.parseDouble(commandList.get(1));
			y = Double.parseDouble(commandList.get(2));
			length = Double.parseDouble(commandList.get(3));
			width = Double.parseDouble(commandList.get(4));
			Rectangle rect = new Rectangle(x,y,length,width);
			System.out.println(rect.toString());
		}
	}


	/**
	 * Executes the "Circle" command
	 * @param commandList the command along with the necessary parameters specified by the user
	 */
	public void executeCircle(ArrayList<String> commandList) throws Exception{
		if(commandList.size() != 2 && commandList.size() != 4){
			throw new Exception
			("\nInvalid arguments, code not executed" +
					"\nCorrect Syntax: \ncircle radius" +
					"\ncircle X Y radius\n");
		}

		double x,y,radius;

		if(commandList.size() == 2){
			radius = Double.parseDouble(commandList.get(1));
			Circle circ = new Circle(radius);
			System.out.println(circ.toString());
		}else{
			x = Double.parseDouble(commandList.get(1));
			y = Double.parseDouble(commandList.get(2));
			radius = Double.parseDouble(commandList.get(3));
			Circle circ = new Circle(x,y,radius);
			System.out.println(circ.toString());
		}
	}

	
//	public void addCommand(String command) {
//		commandHistory += (command + "\n");
//	}

//	public String getCommandHistory() {
////		return commandHistory;
//	}

	public String getGraphCommands() {
		String commands = "";
				
		for(Node aNode: nodeList){
			commands += ("node " + aNode.getName() + "\n");
		}		
		
		for(DirectedArc dirArc: dirArcList){
			commands += ("dir-arc " + dirArc.getParent().getName() + " " 
											+ dirArc.getChild().getName() + " " + dirArc.getRelation() + "\n");
		}

		for(UndirectedArc undirArc: undirArcList){
			Node[] nodes = undirArc.getNodes();
			commands += ("undir-arc " + nodes[0].getName() + " " 
					+ nodes[1].getName() + " " + undirArc.getRelation() + "\n");
		}
		
		return commands;
	}

	/**
	 * Sets the list of nodes in the graph
	 * @param nodeList the list of nodes that we want to set the graph to
	 */
	public void setNodeList(HashSet<Node> nodeList) {
		this.nodeList = nodeList;
	}

//	/**
//	 * Gets the list of nodes in the graph
//	 * @return list of nodes in the graph 
//	 */
//	public HashSet<Node> getNodeList(){
//		return nodeList;
//	}

	/**
	 * Sets the list of directed arcs in the graph
	 * @param dirArcList the list of directed arcs that we want to set the graph to
	 */
	public void setDirectedArcs(HashSet<DirectedArc> dirArcList) {
		this.dirArcList = dirArcList;
	}

	/**
	 * Returns an iterator over the set of  DirectedArcs present in the graph model
	 * @return list of directed arcs
	 */
	public HashSet<DirectedArc> getDirectedArcs(){
		return dirArcList;
	}

	/**
	 * Sets the list of undirected arcs in the graph
	 * @param undirArcList the list of undirected arcs that we want to set the graph to
	 */
	public void setUndirectedArcs(HashSet<UndirectedArc> undirArcList) {
		this.undirArcList = undirArcList;
	}

	/**
	 * Returns an iterator over the set of UndirectedArcs present in the graph model
	 * @return list of undirected arcs
	 */
	public HashSet<UndirectedArc> getUndirectedArcs(){
		return undirArcList;
	}

	/**
	 * Adds a node to the graph
	 * @param node Graph node
	 * @throws throws exception if node already exists
	 */
	public void addNode(Node node) throws Exception{

		if(!nodeList.add(node)){
			throw new DuplicateNodeException("Node already exists");
		} else {
			notifyNodeAddtion(node);
		}

		// This part of the code is only needed when we are using subgraphs
		// which we are not doing for the purpose of this project
		// Since we are adding only empty nodes to our graphs for this project
		// These for loops will not run

		
//		for(Node otherNode: node.getNodes()){
//			try {
//				undirArcList.add(getUndirArc(node, otherNode));
//			} catch (Exception e) {
//				// Never happens, no need to catch, but still
//				continue;
//			}
//		}
//
//		for(Node parent: node.getParents()){
//			try {
//				dirArcList.add(getDirArc(parent, node));
//			} catch (Exception e) {
//				// Never happens, no need to catch, but still
//				continue;
//			}
//		}
//
//		for(Node child: node.getChildren()){
//			try {
//				dirArcList.add(getDirArc(node, child));
//			} catch (Exception e) {
//				// Never happens, no need to catch, but still
//				continue;
//			}
//		}
	}

	/**
	 * Removes a node from the graph
	 * @param node Graph node
	 */
	public void removeNode(Node node){

		if(nodeList.contains(node)){
			
			HashSet<UndirectedArc> unDirArcs = new HashSet<UndirectedArc>(node.getArcs());
			
			for(UndirectedArc undirArc: unDirArcs){
				
				removeArc(undirArc);

			}

			HashSet<DirectedArc> dirArcs =  new HashSet<DirectedArc>(node.getInArcs());
			
			for(DirectedArc dirArc: dirArcs){

				removeArc(dirArc);
			}

			dirArcs = new HashSet<DirectedArc>(node.getOutArcs());
			
			for(DirectedArc dirArc: node.getOutArcs()){

				removeArc(dirArc);
			}
			
			nodeList.remove(node);
			notifyNodeDeletion(node);
		}	 
	}

	/**
	 * Adds an undirected arc to the graph
	 * @param node1 Node
	 * @param node2 Node
	 */
	public void addBiArc(Node node1, Node node2, String relation) throws Exception {
		
		if(node1.equals(node2)){
			throw new Exception
			("Cannot create arc with the same node");
		}

		if(undirArcExists(node1, node2)){
			throw new Exception
			("Undirected arc already exists between these two nodes");
		}
		
		UndirectedArc newArc = new UndirectedArc(node1, node2, relation);
		undirArcList.add(newArc);
		notifyArcAddtion(newArc);
	}

	/**
	 * Adds an directed arc to the graph
	 * @param source graph node
	 * @param sink graph node
	 */
	public void addDiArc(Node source, Node sink, String relation) throws Exception {
		
		if(source.equals(sink)){
			throw new Exception
			("Cannot create arc with the same node");
		}

		if(dirArcExists(source, sink)){
			throw new Exception
			("Directed arc already exists between these two nodes");
		}	
		
		DirectedArc newArc = new DirectedArc(source, sink, relation);
		dirArcList.add(newArc);
		notifyArcAddtion(newArc);
	}

	/**
	 * Removes an undirected arc from the graph
	 * @param arc undirected arc
	 */
	public void removeArc(UndirectedArc arc){
		undirArcList.remove(arc);
		arc.clear();
		notifyArcDeletion(arc);
	}

	/**
	 * Removes a directed arc from the graph
	 * @param arc directed arc
	 */
	public void removeArc(DirectedArc arc){
		dirArcList.remove(arc);
		arc.clear();
		notifyArcDeletion(arc);
	}

	/**
	 * Tests whether a graph node exists in the graph
	 * @param node graph node
	 * @return true if node exists
	 */
	public boolean nodeExists(Node node){
		for(Node aNode: nodeList)
		{
			if(aNode.equals(node)){
				return true;
			}
		}
		return false;    
	}


	/**
	 * Gets undirected arc between two nodes in the graph
	 * @param node1 graph node
	 * @param node2 graph node
	 * @return Undirected arc between nodes if arc exists
	 * @throws Exception if arc does not exist
	 */
	public UndirectedArc getUndirArc(Node node1, Node node2) throws Exception{
		for(UndirectedArc undirArc: undirArcList)
		{
			if(undirArc.nodeExists(node1) && undirArc.nodeExists(node2)){
				return undirArc;
			}
		}   
		throw new Exception("Undirected arc does not exist between the nodes");
	}


	/**
	 * Tests whether an undirected arc exists between two nodes in the graph
	 * @param node1 graph node
	 * @param node2 graph node
	 * @return true if arc exists
	 */
	public boolean undirArcExists(Node node1, Node node2){
		for(UndirectedArc undirArc: undirArcList)
		{
			if(undirArc.nodeExists(node1) && undirArc.nodeExists(node2)){
				return true;
			}
		}   
		return false;
	}


	/**
	 * Gets directed arc between two nodes in the graph
	 * @param parent parent node
	 * @param child child node
	 * @return Directed arc between nodes if arc exists
	 * @throws Exception if arc does not exist
	 */
	public DirectedArc getDirArc(Node parent, Node child) throws Exception{
		for(DirectedArc dirArc: dirArcList)
		{
			if(dirArc.getParent().equals(parent) && dirArc.getChild().equals(child)){
				return dirArc;
			}
		}  
		throw new Exception("Directed arc does not exist between the two nodes");
	}


	/**
	 * Tests whether a directed arc exists between two nodes in the graph
	 * @param parent parent node
	 * @param child child node
	 * @return true if arc exists
	 */
	public boolean dirArcExists(Node parent, Node child){
		for(DirectedArc dirArc: dirArcList)
		{
			if(dirArc.getParent().equals(parent) && dirArc.getChild().equals(child)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests whether a graph node is connected to other nodes by undirected or directed arcs
	 * @param node graph node
	 * @return true if node has connections
	 */
	public boolean nodeHasArcs(Node node){
		return node.isChild() || node.isParent() || node.isConnected();
	}

	/**
	 * Gets a node with the specified name
	 * @param name name of graph node which is to be retrieved
	 * @throws Exception node is not found
	 * @return graph Node
	 */
	public Node getNode(String name) throws Exception{
		for(Node aNode: nodeList)
		{
			if(aNode.getName().equals(name)){
				return aNode;
			}
		}

		throw new Exception(String.format("Node %s not found", name));
	}

	/**
	 * Tests whether a graph node is connected to other nodes by directed arcs
	 * @param node graph node
	 * @return true if node exists
	 */
	public boolean nodeHasDirectedArcs(Node node){
		return node.isChild() || node.isParent();
	}

	/**
	 * Returns an iterator over the nodes connected to the input node
	 * @param node graph nodes
	 * @return an iterator
	 */
	public HashSet<Node> getConnectedNodes(Node node){
		HashSet<Node> connectList = new HashSet<Node>();

		for(Node aNode: nodeList)
		{
			if(aNode.isConnected(node) || aNode.isParent(node) || aNode.isChild(node)){
				connectList.add(aNode);
			}
		}

		return connectList;
	}

	/**
	 * Returns an iterator over the children nodes to the input node
	 * @param node graph nodes
	 * @return an iterator
	 */
	public HashSet<Node> getChildNodes(Node node){
		HashSet<Node> connectList = new HashSet<Node>();

		for(Node aNode: nodeList)
		{
			if(aNode.isChild(node)){
				connectList.add(aNode);
			}
		}

		return connectList;
	}

	/**
	 * Returns an iterator over the parent nodes to the input node
	 * @param node graph nodes
	 * @return an iterator
	 */
	public HashSet<Node> getParentNodes(Node node){
		HashSet<Node> connectList = new HashSet<Node>();

		for(Node aNode: nodeList)
		{
			if(aNode.isParent(node)){
				connectList.add(aNode);
			}
		}

		return connectList;
	}

	/**
	 * Returns an iterator over all the nodes in the graph model
	 * @param node graph nodes
	 * @return an iterator
	 */
	public HashSet<Node> getNodes(){
		return nodeList;
	}


	/**
	 * Returns a subGraph based on the specified list of nodes
	 * @param nodes graph nodes for the subgraph
	 */
//	public Graph subGraph(HashSet<Node> nodes){
//		Graph subGraph = new Graph();
//		for(Node aNode: nodes){
//			try {
//				subGraph.addNode(aNode);
//			} catch (Exception e) {
//				continue;
//			}
//		}
//		return subGraph;
//	}

	/**
	 * Adds a listener to the graph
	 * @param l an EventListener
	 */
	//Method not created yet, not needed for now

}

