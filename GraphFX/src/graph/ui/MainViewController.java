package graph.ui;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import graph.ui.DrawPanelFX;

import edu.ohio.ent.cs5500.Arc;
import edu.ohio.ent.cs5500.DirectedArc;
import edu.ohio.ent.cs5500.Graph;
import edu.ohio.ent.cs5500.GraphListener;
import edu.ohio.ent.cs5500.Layouter;
import edu.ohio.ent.cs5500.Node;
import edu.ohio.ent.cs5500.UndirectedArc;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainViewController implements GraphListener {

	@FXML
	private Button openFileBtn,saveFileBtn,addNodeBtn,addArcBtn,
				deleteNodeBtn,deleteArcBtn,clearGraphBtn,redrawGraphBtn;
	
	@FXML
	private Graph myGraph;
	
	@FXML
	private Layouter graphCanvas;
	
	@FXML
	private StackPane graphPane;
	
	/**
	 * Listmodel for nodes.
	 */
	private ObservableList<Node> nodes;
	
	/**
	 * Listmodel for arcs.
	 */
	private ObservableList<Arc> arcs;

	/**
	 * Listview of "nodes".
	 */
	@FXML
	private ListView<Node> nodeListView;
	
	/**
	 * Listview of arcs.
	 */
	@FXML
	private ListView<Arc> arcListView;
	
	@FXML
	private void initialize() {
		nodes = nodeListView.getItems();
		arcs = arcListView.getItems();
		
//		((DrawPanelFX)graphCanvas).heightProperty().set(400);
//		((DrawPanelFX)graphCanvas).widthProperty().set(400);
		
		((DrawPanelTx)graphCanvas).widthProperty().bind(
				graphPane.widthProperty());
		((DrawPanelTx)graphCanvas).heightProperty().bind(
				graphPane.heightProperty());
		
		initializeToolbar();
		myGraph.addListener(this);
	}
	
	/**
	 * Create toolbar and button for graphviewer. Provide button action handlers.
	 * @return
	 */
	private void initializeToolbar() {

		openFileBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Graph File");
				fileChooser.setInitialDirectory(new File("."));
				fileChooser.getExtensionFilters().addAll(
						new ExtensionFilter("Graph File", "*.graph"),
						new ExtensionFilter("All Files", "*.*"));
				File selectedFile = fileChooser.showOpenDialog(null);
				if (selectedFile != null) {
					myGraph.clearGraph();
					myGraph.read(selectedFile);		
				}

			}
		});

		saveFileBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Graph File");	
				fileChooser.setInitialDirectory(new File("."));
				fileChooser.getExtensionFilters().addAll(
						new ExtensionFilter("Graph File", "*.graph"));
				File selectedFile = fileChooser.showSaveDialog(null);

				try {

//					if(!selectedFile.exists()){

						selectedFile.createNewFile();
						PrintWriter output = new PrintWriter(selectedFile);
						output.write(myGraph.getGraphCommands());
						output.close();
//					}

				}catch(Exception e){

					if (!(e.getMessage()==null)){
						GraphDialog.error(e.getMessage());				
					}
				}
			}
		});

		addNodeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("New Node");
				dialog.setHeaderText("Enter Node Name:");
				//				dialog.setContentText("Enter Node Name:");

				Stage parentWindow = (Stage)((Button)event.getSource()).getScene().getWindow();

				dialog.setX 
				(parentWindow.getX()
						+ (parentWindow.getWidth() / 2)
						- 125);
				dialog.setY
				(parentWindow.getY()
						+ (parentWindow.getHeight() / 2)
						- 125);

				Optional<String> result = dialog.showAndWait();
				if (!result.isPresent()){
					return;
				}

				String nodeName = result.get(); // = JOptionPane.showInputDialog("Enter node name");

				if (nodeName != null){

					try{
						if (nodeName.equals("")) throw new Exception("Enter node name");

						nodeName = nodeName.toLowerCase().replace(" ", "_");
						String command = "node " + nodeName;
						ArrayList<String> commandList = new ArrayList<String>(Arrays.asList(command.split(" ")));
						myGraph.executeNode(commandList);
					}catch(Exception e){
						GraphDialog.error(e.getMessage());
					}
				}
			}
		});

		addArcBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				try {

//					Node node1 = fromNodeList.getSelectionModel().getSelectedItem();
//					Node node2 = toNodeList.getSelectionModel().getSelectedItem();
//
//					if (node1==null || node2==null) throw new Exception("Select nodes to create arc.");
//					if (node1.equals(node2)) throw new Exception("Cannot create arc within the same node");

					Stage window = new Stage();

					Stage parentWindow = (Stage)((Button)event.getSource()).getScene().getWindow();

					window.setTitle("New Arc");

					GridPane grid = new GridPane();
					grid.setAlignment(Pos.CENTER);
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(25, 25, 25, 25));
					
					ComboBox<Node> fromNodeChoice = new ComboBox<>(nodeListView.getItems());
					ComboBox<Node> toNodeChoice = new ComboBox<>(nodeListView.getItems());
					
					fromNodeChoice.setVisibleRowCount(6);
					toNodeChoice.setVisibleRowCount(6);
					
					Label fromNodeLbl = new Label("From Node:");
					grid.add(fromNodeLbl, 0, 0);
					
					grid.add(fromNodeChoice, 1, 0);
					
					Label toNodeLbl = new Label("To Node:");
					grid.add(toNodeLbl, 0,1);

					grid.add(toNodeChoice, 1, 1);
					
					Label arcNameLbl = new Label("Arc Name:");
					grid.add(arcNameLbl, 0, 2);

					TextField arcNameValue = new TextField();
					grid.add(arcNameValue, 1, 2);

					Label relationLbl = new Label("Arc Type:");
					grid.add(relationLbl, 0, 3);

					ChoiceBox<String> arcType = new ChoiceBox<>();
					arcType.getItems().add("Directed");
					arcType.getItems().add("Undirected");
					//Set a default value
					arcType.setValue("Directed");
					grid.add(arcType, 1, 3);

					Button button = new Button("Ok");
					grid.add(button, 0, 4);

					button.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							try {
								
								Node node1 = fromNodeChoice.getSelectionModel().getSelectedItem();
								Node node2 = toNodeChoice.getSelectionModel().getSelectedItem();

								if (arcNameValue.getText().equals(""))  throw new Exception("Choose arc name.");

								if (arcType.getValue()=="Undirected") {					
									myGraph.addBiArc(node1, node2, arcNameValue.getText());
								} else {
									myGraph.addDiArc(node1, node2, arcNameValue.getText());
								}
							} catch (Exception e) {
								GraphDialog.error(e.getMessage());
							} finally {
								window.close();
							}


						}
					});

					Scene scene = new Scene(grid, 300, 200);
					window.setScene(scene);

					window.setX 
					(parentWindow.getX() 
							+ parentWindow.getWidth() / 2
							- scene.getWidth()/2);
					window.setY
					(parentWindow.getY() 
							+ parentWindow.getHeight() / 2
							- scene.getHeight()/2);

					window.show();
				}catch(Exception e){
					GraphDialog.error(e.getMessage());
				}
			}
		});

		deleteNodeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try{

					Node aNode = nodeListView.getSelectionModel().getSelectedItem();

					if (aNode==null) throw new Exception
					("Select node (use \"From list\") to be deleted ");

					HashSet<Arc> arcs = new HashSet<Arc>(); 
					arcs.addAll(aNode.getArcs());
					arcs.addAll(aNode.getInArcs());
					arcs.addAll(aNode.getOutArcs());		

					myGraph.removeNode(aNode);

				}catch(Exception e){
					GraphDialog.error(e.getMessage());
				}
			}
		});

		deleteArcBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try{

					Arc anArc = arcListView.getSelectionModel().getSelectedItem();

					if (anArc == null) throw new Exception
					("Select the arc to be deleted");

					if (anArc instanceof UndirectedArc) {
						myGraph.removeArc((UndirectedArc) anArc);
					} else {
						myGraph.removeArc((DirectedArc) anArc);
					}

				}catch(Exception e){
					GraphDialog.error(e.getMessage());
				}
			}
		});

		redrawGraphBtn.setOnAction(e -> graphCanvas.makeLayout());

		clearGraphBtn.setOnAction(e -> myGraph.clearGraph());

		Tooltip.install
		(openFileBtn, new Tooltip("Open new graph file"));
		Tooltip.install
		(saveFileBtn, new Tooltip("Save current graph to file"));
		Tooltip.install
		(addNodeBtn, new Tooltip("Add new graph node"));
		Tooltip.install
		(addArcBtn, new Tooltip("Add new arc"));
		Tooltip.install
		(deleteNodeBtn, new Tooltip("Delete selected (\"From list\") node"));
		Tooltip.install
		(deleteArcBtn, new Tooltip("Delete selected arc"));
		Tooltip.install
		(redrawGraphBtn, new Tooltip("Create new graph layout"));
		Tooltip.install
		(clearGraphBtn, new Tooltip("Clear Graph"));

	}
	
	@Override
	public void nodeAdded(Node aNode) {
		// TODO Auto-generated method stub
		nodes.add(aNode);
	}

	@Override
	public void nodeDeleted(Node aNode) {
		// TODO Auto-generated method stub
		nodes.remove(aNode);
	}

	@Override
	public void arcAdded(Arc anArc) {
		// TODO Auto-generated method stub
		arcs.add(anArc);
	}

	@Override
	public void arcDeleted(Arc anArc) {
		// TODO Auto-generated method stub
		arcs.remove(anArc);
	}

	@Override
	public void graphErased() {
		// TODO Auto-generated method stub
		nodes.clear();
		arcs.clear();
	}
	

}
