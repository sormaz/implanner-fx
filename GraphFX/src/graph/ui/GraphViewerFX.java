package graph.ui;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import edu.ohio.ent.cs5500.Arc;
import edu.ohio.ent.cs5500.DirectedArc;
import edu.ohio.ent.cs5500.Graph;
import edu.ohio.ent.cs5500.GraphListener;
import edu.ohio.ent.cs5500.Node;
import edu.ohio.ent.cs5500.UndirectedArc;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class GraphViewerFX extends Application implements GraphListener  {

	private Graph myGraph  = new Graph();
	private ObservableList<Node> nodes = 
			FXCollections.observableArrayList();
	private ObservableList<Arc> arcs = 
			FXCollections.observableArrayList();

	ListView<Node> fromNodeList = new ListView<>(nodes);
	ListView<Node> toNodeList = new ListView<>(nodes);
	ListView<Arc> arcList = new ListView<>(arcs);

	public static void main(String[] args) {
		launch(args);
	}

	public GraphViewerFX () {
		super();
		myGraph.addListener(this);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("GraphViewer");
		BorderPane root = new BorderPane();
		primaryStage.setScene(new Scene(root, 800, 600));

		SplitPane mainSplitter = new SplitPane();
		mainSplitter.setDividerPositions(0.3f, 1.0f);
		root.setCenter(mainSplitter);
		TabPane tab = new TabPane();
		Tab nodeTab = new Tab("Nodes");
		Tab arcTab = new Tab("Arcs");
		nodeTab.closableProperty().set(false);
		arcTab.closableProperty().set(false);

		tab.getTabs().addAll(nodeTab, arcTab);
		mainSplitter.getItems().add(tab);
		
		Tooltip fromListTT = new Tooltip("From List.");
		fromNodeList.setTooltip(fromListTT);
		Tooltip toListTT = new Tooltip("To List.");
		toNodeList.setTooltip(toListTT);
		Tooltip arcListTT = new Tooltip("Arc List.");
		arcList.setTooltip(arcListTT);


		SplitPane toFromListSplitter = new SplitPane();
		toFromListSplitter.orientationProperty().set(Orientation.VERTICAL);
		toFromListSplitter.setDividerPositions(0.5f, 1f);


		toFromListSplitter.getItems().addAll(fromNodeList,toNodeList);
		nodeTab.setContent(toFromListSplitter);

		arcTab.setContent(arcList);

		Canvas graphPanel = createGraphPanel(500, 500);
		mainSplitter.getItems().add(graphPanel);



		ToolBar buttons = createToolbar();
		root.setBottom(buttons);

		primaryStage.show();

		//		AnchorPane.setBottomAnchor(child, value);
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

	Canvas createGraphPanel(double width, double height){
		Canvas canvas = new Canvas(width, height);

		final GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.setFill(Color.BLACK);
		gc.setFont(Font.getDefault());
		gc.fillText("hello   world!", 15, 50);

		gc.setLineWidth(5);
		gc.setStroke(Color.PURPLE);
		gc.setFill(Color.AQUA);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.strokeOval(10, 60, 30, 30);
		gc.strokeOval(60, 60, 30, 30);
		gc.strokeRect(30, 100, 40, 40);

		return canvas;
	}

	ToolBar createToolbar() {
		ToolBar buttons = new ToolBar();
		Button openFile, saveFile, addNode, addArc, deleteNode, deleteArc, clearGraph, redrawGraph;
		openFile = new Button("Open File");
		saveFile = new Button("Save File");
		addNode = new Button("Add Node");
		addArc = new Button("Add Arc");
		deleteNode = new Button("Delete Node");
		deleteArc = new Button("Delete Arc");
		redrawGraph = new Button("Redraw Graph");
		clearGraph = new Button("Clear Graph");

		openFile.setOnAction(new EventHandler<ActionEvent>() {

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

		saveFile.setOnAction(new EventHandler<ActionEvent>() {

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

					if(!selectedFile.exists()){

						selectedFile.createNewFile();
						PrintWriter output = new PrintWriter(selectedFile);
						output.write(myGraph.getGraphCommands());
						output.close();
					}

				}catch(Exception e){

					if (!(e.getMessage()==null)){
						GraphDialog.error(e.getMessage());				
					}
				}
			}
		});

		addNode.setOnAction(new EventHandler<ActionEvent>() {

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

		addArc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				try {

					Node node1 = fromNodeList.getSelectionModel().getSelectedItem();
					Node node2 = toNodeList.getSelectionModel().getSelectedItem();
					
					if (node1==null || node2==null) throw new Exception("Select nodes to create arc.");
					if (node1.equals(node2)) throw new Exception("Cannot create arc within the same node");
					
					Stage window = new Stage();

					Stage parentWindow = (Stage)((Button)event.getSource()).getScene().getWindow();

					window.setTitle("New Arc");

					GridPane grid = new GridPane();
					grid.setAlignment(Pos.CENTER);
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(25, 25, 25, 25));

					Label arcNameLbl = new Label("Arc Name:");
					grid.add(arcNameLbl, 0, 0);

					TextField arcNameValue = new TextField();
					grid.add(arcNameValue, 1, 0);

					Label relationLbl = new Label("Arc Type:");
					grid.add(relationLbl, 0, 1);

					ChoiceBox<String> arcType = new ChoiceBox<>();
					arcType.getItems().add("Directed");
					arcType.getItems().add("Undirected");
					//Set a default value
					arcType.setValue("Directed");
					grid.add(arcType, 1, 1);

					Button button = new Button("Ok");
					grid.add(button, 0, 2);

					button.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							try {
								
								if (arcNameValue.getText()=="") throw new Exception("Choose arc name.");
							
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

		deleteNode.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try{
					
					Node aNode = fromNodeList.getSelectionModel().getSelectedItem();
					
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

		deleteArc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try{

					Arc anArc = arcList.getSelectionModel().getSelectedItem();

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

		redrawGraph.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

			}
		});

		clearGraph.setOnAction(e -> myGraph.clearGraph());

		Tooltip.install
		(openFile, new Tooltip("Open new graph file"));
		Tooltip.install
		(saveFile, new Tooltip("Save current graph to file"));
		Tooltip.install
		(addNode, new Tooltip("Add new graph node"));
		Tooltip.install
		(addArc, new Tooltip("Add new arc"));
		Tooltip.install
		(deleteNode, new Tooltip("Delete selected (\"From list\") node"));
		Tooltip.install
		(deleteArc, new Tooltip("Delete selected arc"));
		Tooltip.install
		(redrawGraph, new Tooltip("Create new graph layout"));
		Tooltip.install
		(clearGraph, new Tooltip("Clear Graph"));

		buttons.getItems().addAll
		(openFile, saveFile, addNode, 
				addArc, deleteNode, deleteArc, 
				clearGraph, redrawGraph);

		return buttons;
	}

}
