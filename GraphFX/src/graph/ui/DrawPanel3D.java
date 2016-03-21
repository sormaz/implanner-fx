package graph.ui;

import java.util.Collection;
import java.util.Random;

import edu.ohio.ent.cs5500.Arc;
import edu.ohio.ent.cs5500.Graph;
import edu.ohio.ent.cs5500.Node;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class DrawPanel3D extends Group implements LayouterTx {
	
	private Group selectables = new Xform("selectables");
	private SubScene scene;
    private PerspectiveCamera camera;
    private DoubleProperty defaultWidth = new SimpleDoubleProperty(400);
    private DoubleProperty defaultHeight = new SimpleDoubleProperty(400);	
    private double oldX, oldY;
    private Node current;
	
	private Graph myGraph;

	public DrawPanel3D(Graph graph) {
		myGraph = graph;	
		myGraph.addListener(this);
        
        scene = new SubScene(selectables, defaultWidth.get(), defaultHeight.get());
        scene.setFill(Color.WHITE);    
        scene.setDepthTest(DepthTest.ENABLE);
        
        camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-1000);
        scene.setCamera(camera);	
        getChildren().add(scene);
        
        widthProperty().addListener(evt -> makeLayout());
        heightProperty().addListener(evt -> makeLayout());
		
//        scene.setOnMouseClicked(event-> {
//			oldX = event.getX();
//			oldY = event.getY();	
//            javafx.scene.Node picked = event.getPickResult().getIntersectedNode();
//            if(null != picked) {
//            	
//            }
//        });
//        
        scene.setOnMouseMoved((MouseEvent event) -> {
			if (find(event) == null) {
				setCursor(Cursor.DEFAULT);
			} else {
				setCursor(Cursor.CROSSHAIR);
			}
        }); 
        
	}
	
	public DoubleProperty widthProperty() {		
		if (scene == null) {
			return defaultWidth;
		} else {
			return scene.widthProperty();
		}
	};
	
	public DoubleProperty heightProperty() {	
		if (scene == null) {
			return defaultHeight;
		} else {
			return scene.heightProperty();
		}
	};
	
	public Node find(MouseEvent event) {
		javafx.scene.Node picked = event.getPickResult().getIntersectedNode();
		if (picked == null) {
			return null;
		} else {
			try {
				String nodeName = picked.getId();
				Node selectedNode = myGraph.getNode(nodeName);
				return selectedNode;
			} catch (Exception e) {
				return null;
			}			
		}
	}
	
	public Color getRandomColor() {
		Random randNumber = new Random();
		double rValue, gValue, bValue, opacity; 

		rValue = randNumber.nextDouble();
		gValue = randNumber.nextDouble();
		bValue = randNumber.nextDouble();
		opacity = 1;
		
		Color color = new Color(rValue, gValue, bValue, opacity);
		return color;
	}
	
	public Point3D getRandCoord(Node aNode) {
		// TODO Auto-generated method stub
		
//		GraphicsContext gc = getGraphicsContext2D();
		Random randNumber = new Random();
		int xCoordinate, yCoordinate, zCoordinate; 

		int minX = -(int) widthProperty().get() / 2;
		int minY = -(int) heightProperty().get() / 2;
		int minZ = -150;
		int maxX = (int) widthProperty().get() / 2; 
		int maxY = (int) heightProperty().get() / 2; 
		int maxZ = 150;

		xCoordinate = minX + randNumber.nextInt(maxX - minX);
		yCoordinate = minY + randNumber.nextInt(maxY - minY);
		zCoordinate = minZ + randNumber.nextInt(maxZ - minZ);
		
		System.out.println(aNode.getName() + ": " 
								+ "(" + xCoordinate + "," + yCoordinate
								+ "," + zCoordinate + ")"  );
		
		return new Point3D(xCoordinate,yCoordinate,zCoordinate);
	}


	@Override
	public void makeLayout() {
		// TODO Auto-generated method stub
		
		selectables.getChildren().clear();
		
		for (Node aNode: myGraph.getNodes()) {
			nodeAdded(aNode);
		}

	}

	@Override
	public void nodeAdded(Node aNode) {
		// TODO Auto-generated method stub
		
//        final PhongMaterial redMaterial = new PhongMaterial();
//        redMaterial.setDiffuseColor(Color.DARKRED);
//        redMaterial.setSpecularColor(Color.RED);
//
//        final PhongMaterial whiteMaterial = new PhongMaterial();
//        whiteMaterial.setDiffuseColor(Color.WHITE);
//        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);
		


        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(getRandomColor());
        greyMaterial.setSpecularColor(Color.GREY);
		
        Sphere nodeSphere = new Sphere(40.0);
        nodeSphere.setMaterial(greyMaterial);
        nodeSphere.setId(aNode.getName());
        
        Point3D location = getRandCoord(aNode);
//        nodeSphere.setTranslateX(location.getX());
//        nodeSphere.setTranslateY(location.getY());
//        nodeSphere.setTranslateZ(location.getZ());
        
        Text text = new Text(aNode.getName());
        text.setId(aNode.getName());
//        text.setBoundsType(TextBoundsType.VISUAL); 
        Group nodeObject = new Group();
        nodeObject.setId(aNode.getName());
        nodeObject.getChildren().addAll(nodeSphere, text);
        nodeObject.setTranslateX(location.getX());
        nodeObject.setTranslateY(location.getY());
        nodeObject.setTranslateZ(location.getZ());
        selectables.getChildren().add(nodeObject);
        
//        nodeSphere.setOnMouseClicked(event-> {
//			oldX = event.getX();
//			oldY = event.getY();	
//            javafx.scene.Node picked = event.getPickResult().getIntersectedNode();
//            if(null != picked) {
//            	
//            }
//        });
//        
//        nodeSphere.setOnMouseMoved((MouseEvent event) -> {
//			if (event.getPickResult().getIntersectedNode() == null) {
//				setCursor(Cursor.DEFAULT);
//			} else {
//				setCursor(Cursor.CROSSHAIR);
//			}
//        }); 
        
	}

	@Override
	public void nodeDeleted(Node aNode) {
		// TODO Auto-generated method stub
		selectables.getChildren().removeAll(selectables.lookupAll("#" + aNode.getName()));		
	}

	@Override
	public void arcAdded(Arc anArc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arcDeleted(Arc anArc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void graphErased() {
		// TODO Auto-generated method stub
		selectables.getChildren().clear();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scaleToFit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fitVertical() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fitHorizontal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void zoomIn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void zoomOut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void panLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void panRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void panUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void panDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateCW() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotateCCW() {
		// TODO Auto-generated method stub

	}

}
