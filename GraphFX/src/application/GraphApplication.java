/**
 * 
 */
package application;

import application.Graph;


/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This is the main class of the project GraphApplication and is 
 * used to interact with the user using command line interface.
 *
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public class GraphApplication {

	/**
	 * The graph object containing all the nodes and arcs
	 */
	private static Graph myGraph;


	/**
	 * Main function of the graph application
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		showDisplayOptions();	

		myGraph  = new Graph();
		
		if(args.length > 0){
				myGraph.read(args[0]);
		}
		
		myGraph.read(System.in);
		
	}



	/**
	 * Displays the welcome statement, used at the start of the application
	 */
	public static void showDisplayOptions(){
		System.out.println("\nWelcome to Graph Application");
		System.out.println("\nEnter Command:\n");
	}
	
	
}
