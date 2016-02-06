/**
 * 
 */
package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This is the class which defines the GUI. The GUI is called in the main function.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
@SuppressWarnings("serial")
public class GraphViewer extends JFrame implements GraphListener {

	/**
	 * Graph model
	 */
	private Graph myGraph  = new Graph();
	
	/**
	 * Graph Layouter
	 */
	private Layouter layoutManager = new RandomLayouter(myGraph);
	
	/**
	 * Graph GUI
	 */
	private GraphViewPanel mainView;
	
	/**
	 * Listmodel for graph nodes
	 */
	private DefaultListModel<Node> nodeListModel = new DefaultListModel<Node>();
	
	/**
	 * Listmodel for graph arcs
	 */
	private DefaultListModel<Arc> arcListModel = new DefaultListModel<Arc>();

	/**
	 * Create new graphviewer. Initialize and set display properties.
	 */
	public GraphViewer() {
		init();
		display();
//		addWindowListener(new MaximizeHandler());
	}

	/**
	 * Application main function
	 */
	public static void main(String[] args) {

		GraphViewer mainFrame = new GraphViewer();

		JButton openFile, saveFile, addNode, addArc, deleteNode, deleteArc, clearGraph, redrawGraph;
		openFile = new JButton("Open File");
		saveFile = new JButton("Save File");
		addNode = new JButton("Add Node");
		addArc = new JButton("Add Arc");
		deleteNode = new JButton("Delete Node");
		deleteArc = new JButton("Delete Arc");
		redrawGraph = new JButton("Redraw Graph");
		clearGraph = new JButton("Clear Graph");

		
		openFile.setToolTipText("Open new graph file");
		saveFile.setToolTipText("Save current graph to file");
		addNode.setToolTipText("Add new graph node");
		addArc.setToolTipText("Add new arc");
		deleteNode.setToolTipText("Delete selected (\"From list\") node");
		deleteArc.setToolTipText("Delete selected arc");
		redrawGraph.setToolTipText("Create new graph layout");
		clearGraph.setToolTipText("Clear Graph");


		openFile.addActionListener(mainFrame.new OpenFileListener());
		saveFile.addActionListener(mainFrame.new SaveFileListener());
		addNode.addActionListener(mainFrame.new AddNodeListener());
		addArc.addActionListener(mainFrame.new AddArcListener());
		deleteNode.addActionListener(mainFrame.new DeleteNodeListener());
		deleteArc.addActionListener(mainFrame.new DeleteArcListener());
		redrawGraph.addActionListener(mainFrame.new ReDrawGraphListener());
		clearGraph.addActionListener(mainFrame.new ClearGraphListener());


		JToolBar buttons = new JToolBar();
		buttons.add(openFile);
		buttons.add(saveFile);
		buttons.add(addNode);
		buttons.add(addArc);
		buttons.add(deleteNode);
		buttons.add(deleteArc);
		buttons.add(redrawGraph);
		buttons.add(clearGraph);
		buttons.setFloatable(false);
		
		mainFrame.add(buttons, BorderLayout.SOUTH);
		mainFrame.pack();

	}

	/**
	 * Create new graph view panel. Make graph viewer listed to changes in graph model.s
	 */
	public void init() {
		mainView = new GraphViewPanel();
		setLayout(new BorderLayout());
		add(mainView, BorderLayout.CENTER);
//		setResizable(false);
		
		myGraph.addListener(this);
	}

	/**
	 * Set display properties
	 */
	public void display() {
		setTitle("Graph Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setVisible(true);
	}

	/**
	 * Class that defines the GUI without the buttons.
	 */
	class GraphViewPanel extends JPanel {
		
		private JList<Node> nodeList;
		private JList<Node> secNodeList;
		private JList<Arc> arcList;

		/**
		 * Create new graph view panel and initilize it
		 */
		public GraphViewPanel() {
			init();
		}
		
		/**
		 * Get selected node from jList (from list)
		 */
		public Node getSelNode() {
			return GraphViewer.this.nodeListModel.get(nodeList.getSelectedIndex());
		}
		
		/**
		 * Get selected node from jList (to list)
		 */
		public Node getSecSelNode() {
			return GraphViewer.this.nodeListModel.get(secNodeList.getSelectedIndex());
		}
		
		/**
		 * Get selected arc from jList
		 */
		public Arc getSelArc() {
			return GraphViewer.this.arcListModel.get(arcList.getSelectedIndex());
		}

		/**
		 * Create jLists, panels and panes and tabs and add to view. Set target for DrawPanel.
		 */
		public void init() {

			Border etched = BorderFactory.createEtchedBorder();
			Border graphPanelBorder
						= BorderFactory.createTitledBorder(etched, "Graph Panel");
			setBorder(graphPanelBorder);

			setLayout(new GridLayout(1,1));
			
			nodeList = new JList<Node>(nodeListModel);
			nodeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			nodeList.setVisibleRowCount(12);
			JScrollPane nodeListScrollPane = new JScrollPane(nodeList);
			Border fromListBorder = BorderFactory.createTitledBorder(etched, "From List");
			nodeListScrollPane.setBorder(fromListBorder);

			secNodeList = new JList<Node>(nodeListModel);
			secNodeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			secNodeList.setVisibleRowCount(12);
			JScrollPane secNodeListScrollPane = new JScrollPane(secNodeList);
			Border toListBorder = BorderFactory.createTitledBorder(etched, "To List");
			secNodeListScrollPane.setBorder(toListBorder);

			JSplitPane nodeLists 
						= new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
										nodeListScrollPane, secNodeListScrollPane);
			Border nodeListsBorder 
						= BorderFactory.createTitledBorder(etched, "Graph Nodes");
			nodeLists.setBorder(nodeListsBorder);

			arcList = new JList<Arc>(arcListModel);
			JScrollPane arcListScrollPane = new JScrollPane(arcList);
			Border arcListScrollPaneBorder 
						= BorderFactory.createTitledBorder(etched, "Graph Arcs");
			arcListScrollPane.setBorder(arcListScrollPaneBorder);

			JTabbedPane tabbedPane = new JTabbedPane();
			tabbedPane.addTab("Nodes", nodeLists);
			tabbedPane.addTab("Arcs", arcListScrollPane);
			
			Dimension paneSize = new Dimension
						((int) tabbedPane.getPreferredSize().getWidth(), getHeight());	
			tabbedPane.setMinimumSize(paneSize);
			tabbedPane.setPreferredSize(paneSize);
			DrawPanel graphDisplay = new DrawPanel((Drawable) layoutManager, true);
//			JScrollPane graphDisplayScrollPane = new JScrollPane(graphDisplay);
			
			JSplitPane mainView 
				= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, graphDisplay);
			mainView.setResizeWeight(0);
//			mainView.set
			add(mainView);

		}		

	}
	
	/**
	 * ActionListener for opening file
	 */
	private class OpenFileListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event)
		{		
			File f; 
			JFileChooser chooser = new  JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Graph File", "graph");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(GraphViewer.this);

			if(returnVal == JFileChooser.APPROVE_OPTION) {
				f = chooser.getSelectedFile();
				myGraph.clearGraph();
				myGraph.read(f);				
			}
		}
	}
	
	/**
	 * ActionListener for saving file
	 */
	private class SaveFileListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event)
		{		
			File f; 
			JFileChooser chooser = new  JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Graph File", "graph");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(GraphViewer.this);

			if(returnVal == JFileChooser.APPROVE_OPTION) {
				f = chooser.getSelectedFile();
				String file_name = f.getName();

				if (!file_name.endsWith(filter.getExtensions()[0])) {
				    file_name += ("." + filter.getExtensions()[0]);
				    f = new File(file_name);
				}

				try {
					if(!f.exists()){

						f.createNewFile();
						PrintWriter output = new PrintWriter(f);
						output.write(myGraph.getGraphCommands());
						output.close();
					}
					else{
						String file_path = f.getParent();
						String message = "File • " + file_name + " • already exist in \n" + file_path + ":\n" + "Do you want to overwrite?";
						String title = "Warning";
						int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
						if(reply == JOptionPane.YES_OPTION){
							f.delete();
							f.createNewFile();
							PrintWriter output = new PrintWriter(f);
							output.write(myGraph.getGraphCommands());
							output.close();
						}
					}
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
	}
	
	/**
	 * ActionListener for adding node
	 */
	private class AddNodeListener implements ActionListener {

		public void actionPerformed(ActionEvent event)
		{		
			String nodeName = JOptionPane.showInputDialog("Enter node name");

			if (nodeName != null){
				
				try{
					if (nodeName.equals("")) throw new Exception("Enter valid node name");
					
					nodeName = nodeName.toLowerCase().replace(" ", "_");
					String command = "node " + nodeName;
					ArrayList<String> commandList = new ArrayList<String>(Arrays.asList(command.split(" ")));
					myGraph.executeNode(commandList);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
	}

	/**
	 * ActionListener for adding arc
	 */
	private class AddArcListener implements ActionListener, DialogCloseListener {
		
		NewArcDialog newArcPopUp;
		
		@Override
		public void actionPerformed(ActionEvent event)
		{		
			try{
				
				Node node1 = GraphViewer.this.mainView.getSelNode();
				Node node2 = GraphViewer.this.mainView.getSecSelNode();
				
				if (node1.equals(node2)) throw new Exception("Cannot create arc within the same node");
				
				newArcPopUp = new NewArcDialog(GraphViewer.this, this, node1.getName(), node2.getName());
				newArcPopUp.pack();
				newArcPopUp.setVisible(true);
				
			}catch(ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(null, "Select two nodes to create an arc");
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		
		public void onDialogClose() {
			try {

				Node node1 = GraphViewer.this.mainView.getSelNode();
				Node node2 = GraphViewer.this.mainView.getSecSelNode();
				
				if (newArcPopUp.getArcType().equals(NewArcDialog.UNDIRECTED)) {					
					myGraph.addBiArc(node1, node2, newArcPopUp.getRelation());
				} else {
					myGraph.addDiArc(node1, node2, newArcPopUp.getRelation());
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	/**
	 * ActionListener for deleting node
	 */
	private class DeleteNodeListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event)
		{		
			try{
				
				Node aNode = GraphViewer.this.mainView.getSelNode();
				
				HashSet<Arc> arcs = new HashSet<Arc>(); 
				arcs.addAll(aNode.getArcs());
				arcs.addAll(aNode.getInArcs());
				arcs.addAll(aNode.getOutArcs());		
				
				myGraph.removeNode(aNode);
				
			}catch(ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(null, "Select node (use \"From list\") to be deleted ");
			}
		}
	}
	
	/**
	 * ActionListener for deleting arc
	 */
	private class DeleteArcListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event)
		{		
			try{
				
				Arc anArc = GraphViewer.this.mainView.getSelArc();
				
				if (anArc instanceof UndirectedArc) {
					myGraph.removeArc((UndirectedArc) anArc);
				} else {
					myGraph.removeArc((DirectedArc) anArc);
				}
				
			}catch(ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(null, "Select the arc to be deleted");
			}
		}
	}
	
	/**
	 * ActionListener for redrawing graph
	 */
	private class ReDrawGraphListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event)
		{		
			layoutManager.makeLayout();
		}
	}
	
	/**
	 * ActionListener for clearing graph
	 */
	private class ClearGraphListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			myGraph.clearGraph();
		}
	}

//	/**
//	 * WindowListener for maximizing application
//	 */
//	private class MaximizeHandler extends WindowAdapter {
//	    
//	    /**
//	     * Invoked when a window is activated.
//	     */
//	    public void windowActivated(WindowEvent e) {
//	    	((Drawable) layoutManager).notifyDisplayOfLayoutChanged();
//	    }
//		
//	}
	
	@Override
	public void nodeAdded(Node aNode) {
		// TODO Auto-generated method stub
		nodeListModel.addElement(aNode);		
	}

	@Override
	public void nodeDeleted(Node aNode) {
		// TODO Auto-generated method stub
		nodeListModel.removeElement(aNode);
	}

	@Override
	public void arcAdded(Arc anArc) {
		// TODO Auto-generated method stub
		arcListModel.addElement(anArc);
	}

	@Override
	public void arcDeleted(Arc anArc) {
		// TODO Auto-generated method stub
		arcListModel.removeElement(anArc);
	}

	@Override
	public void graphErased() {
		// TODO Auto-generated method stub
		nodeListModel.clear();
		arcListModel.clear();
	}
	
}
