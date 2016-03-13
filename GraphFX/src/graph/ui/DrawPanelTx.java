package graph.ui;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import edu.ohio.ent.cs5500.Arc;
import edu.ohio.ent.cs5500.Graph;
import edu.ohio.ent.cs5500.LayoutChangeListener;
import edu.ohio.ent.cs5500.Node;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Translate;

/**
 * This class defines the canvas where the nodes are drawn.
 * @author Arif
 */
public class DrawPanelTx extends Canvas implements LayoutChangeListener, LayouterTx {

	/**
	 * Graph model.
	 */
	private Graph myGraph;
	public Graph getMyGraph() {
		return myGraph;
	}

	public void setMyGraph(Graph myGraph) {
		this.myGraph = myGraph;
	}

	/**
	 * List of selectables.
	 */
	private HashMap<Node, Point2D> selectables = new HashMap<Node, Point2D>();
	/**
	 * Radius of each node  in view.
	 */
	private final static int NODE_RADIUS = 15;
	/**
	 * Currently selected node.
	 */
	private Node current;
	
	/**
	 * X and Y coordinates of previous mouse press event
	 */
	private double oldX, oldY;
	
	/**
	 * Keeps record of the number of rotations
	 */
	private double rotationCounter = 0;
	
	private double margin = 0.2;
	
	private Affine viewTransform = new Affine();
	
	/**
	 * Constructor for graph canvas. Listeners are added and mouse behaviour is set. 
	 * @param g
	 */
	public DrawPanelTx(Graph g) {

		myGraph = g;
		myGraph.addListener(this);

        // Redraw canvas when size changes.
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> makeLayout());
        heightProperty().addListener(evt -> makeLayout());	
     
        setOnMousePressed((MouseEvent me) -> {
			oldX = me.getX();
			oldY = me.getY();	
			current = find(new Point2D(oldX, oldY));			
        });
            
        
        setOnMouseDragged((MouseEvent me) -> {
			        	
        	if (current != null)
			{
				int x = (int)me.getX();
				int y = (int)me.getY();
				Point2D p = new Point2D(x, y);	
				
				if (!(x < 0 || x > getWidth() || y < 0 || y > getHeight())){
					try {
						selectables.replace(current, selectables.get(current), viewTransform.inverseTransform(p));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
			
			draw();
			
        });
        
        setOnMouseMoved((MouseEvent me) -> {
			if (find(new Point2D(me.getX(), me.getY())) == null) {
				setCursor(Cursor.DEFAULT);
			} else {
				setCursor(Cursor.CROSSHAIR);
			}
        }); 

	}
	
    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
    	return 10;
//        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
    	return 10;
//        return getHeight();
    }  
    
	@Override
	public void nodeAdded(Node aNode) {
		// TODO Auto-generated method stub
				
		selectables.put(aNode, getRandCoord(aNode));
		layoutChanged();
	}

	@Override
	public void nodeDeleted(Node aNode) {
		// TODO Auto-generated method stub
		
		try {
			selectables.remove(aNode);
		}catch (Exception e) {
			GraphDialog.error(e.getMessage());
		}
		layoutChanged();
	}

	@Override
	public void arcAdded(Arc anArc) {
		// TODO Auto-generated method stub
		layoutChanged();
	}

	@Override
	public void arcDeleted(Arc anArc) {
		// TODO Auto-generated method stub
		layoutChanged();
	}

	@Override
	public void graphErased() {
		// TODO Auto-generated method stub
		selectables.clear();
		layoutChanged();
	}

	@Override
	public void layoutChanged() {
		// TODO Auto-generated method stub

		resetGraph();
		draw();
	}
	
	@Override
	public void makeLayout() {
		// TODO Auto-generated method stub
		
		selectables.clear();
			
		for (Node aNode : myGraph.getNodes()) {		
			selectables.put
			(aNode, getRandCoord(aNode));
		}
		resetGraph();
		draw();

	}
	
	/**
	 * Draw graph nodes and arcs from list of selectables and graph model.
	 */
	private void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setTransform(new Affine());
		
		double width = getWidth();
		double height = getHeight();
		gc.clearRect(0, 0, width, height);
		gc.setTransform(viewTransform);
		
		gc.setFill(Color.BEIGE);
		gc.fillRect(0, 0, width, height);

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeRect(0, 0, width, height);
		
		gc.setFill(Color.RED);
		
		gc.setStroke(Color.BLUE);
		
		for (Node aNode : selectables.keySet()) {	
			Point2D d = selectables.get(aNode);
			gc.strokeOval(d.getX()-NODE_RADIUS, d.getY()-NODE_RADIUS, 
							NODE_RADIUS*2, NODE_RADIUS*2);
			gc.fillText(aNode.getName(), d.getX(), d.getY());
		}
		
		for (Arc anArc : myGraph.getUndirectedArcs()){
			Node[] nodes;
			nodes = anArc.getNodes();
			Point2D p1,p2;
			p1 = selectables.get(nodes[0]);
			p2 = selectables.get(nodes[1]);
			gc.strokeLine(p1.getX(),p1.getY(), p2.getX(),p2.getY());
		}
		
		for (Arc anArc : myGraph.getDirectedArcs()){
			Node[] nodes;
			nodes = anArc.getNodes();
			Point2D p1,p2;
			p1 = selectables.get(nodes[0]);
			p2 = selectables.get(nodes[1]);
			double x1 = p1.getX();
			double x2 = p2.getX();
			double y1 = p1.getY();
			double y2 = p2.getY();
			double r = 0.5 * p1.distance(p2);
			
			double startAngle = getStartAngle(p1, p2);
			
			gc.strokeArc(0.5 * (x1 + x2) - r, 
							 0.5 * (y1 + y2) - r,
							 2 * r, 2 * r, 
							 Math.toDegrees(startAngle),
							 180.0, ArcType.OPEN);
		}
			
	}
	
	/**
	 * Generate random coordinates for node
	 * @param aNode
	 * @return
	 */
	public Point2D getRandCoord(Node aNode) {
		// TODO Auto-generated method stub
		
//		GraphicsContext gc = getGraphicsContext2D();
		Random randNumber = new Random();
		int xCoordinate, yCoordinate; 

		int minX = 0; //(int) gc.getCanvas().getWidth() * (marginPercent) / 100;
		int minY = 0; //(int) gc.getCanvas().getHeight()* (marginPercent) / 100;
		int maxX = (int) getWidth(); // gc.getCanvas().getWidth() * (100 - marginPercent) / 100;
		int maxY = (int) getHeight(); //gc.getCanvas().getHeight()* (100 - marginPercent) / 100;

		xCoordinate = minX + randNumber.nextInt(maxX - minX);
		yCoordinate = minY + randNumber.nextInt(maxY - minY);
		
		System.out.println(aNode.getName() + ": " + "(" + xCoordinate + "," + yCoordinate + ")"  );
		
		return new Point2D(xCoordinate,yCoordinate);
	}
	
	
	/**
	 * Gives start angle for directed arcs.
	 * @param p1
	 * @param p2
	 * @return
	 */
	private double getStartAngle(Point2D p1, Point2D p2) {
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();
		
//		double startAngle = Math.atan((x2-x1)/(y2-y1)) + Math.PI / 2;
		double startAngle = Math.atan2((x2-x1), (y2-y1)) + Math.PI / 2;
		return startAngle;
	}
	
	/**
	 * Get circle that is created for individual node.
	 * @param p
	 * @return
	 */
	public Circle getCircle(Point2D p) {
		return new Circle(p.getX(), p.getY(), NODE_RADIUS);
	}
	
	/**
	 * Find if cursor within area of circle of node.
	 */
	public Node find(Point2D p)
	{
		for (Node aNode : selectables.keySet())
		{			
			Circle c = getCircle(selectables.get(aNode));
			try {
				if (c.contains(viewTransform.inverseTransform(p))) return aNode;
			} catch (NonInvertibleTransformException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public double getMinimumX() {

//		if (selectables.size() == 0) {
//			return 0;
//		}
		
		ArrayList<Double> x = new ArrayList<>();
		
		for (Point2D p: selectables.values()) {
			x.add(p.getX());
		}
		
		return Collections.min(x);
	}
	
	public double getMinimumY() {
		
//		if (selectables.size() == 0) {
//			return 0;
//		}

		ArrayList<Double> y = new ArrayList<>();
		
		for (Point2D p: selectables.values()) {
			y.add(p.getY());
		}
		
		return Collections.min(y);
	}
	
	public double getMaximumX() {
		
//		if (selectables.size() == 0) {
//			return getWidth();
//		}

		ArrayList<Double> x = new ArrayList<>();
		
		for (Point2D p: selectables.values()) {
			x.add(p.getX());
		}
		
		return Collections.max(x);
	}
	
	public double getMaximumY() {
		
//		if (selectables.size() == 0) {
//			return getHeight();
//		}

		ArrayList<Double> y = new ArrayList<>();
		
		for (Point2D p: selectables.values()) {
			y.add(p.getY());
		}
		
		return Collections.max(y);
	}
	
	public Rectangle2D getTransformedRect(Rectangle2D rect) {
		
		double minX = rect.getMinX();
		double minY = rect.getMinY();
		double width = rect.getWidth();
		double height = rect.getHeight();
		
		Rectangle temp = new Rectangle(minX, minY, width, height);

		temp.getTransforms().add(viewTransform);
		Bounds bounds = temp.getBoundsInParent();
		
		return new Rectangle2D(bounds.getMinX(), bounds.getMinY(),
						bounds.getWidth(), bounds.getHeight());
		
	}
	
	public Rectangle2D getBoundingBox() {
		
		if (selectables.size() < 2) {
			return new Rectangle2D(0, 0, getWidth(), getHeight());
		} else {
			
			double minX = getMinimumX();
			double minY = getMinimumY();
			double maxX = getMaximumX();
			double maxY = getMaximumY();
			double width = maxX - minX;
			double height = maxY - minY;
			
			if (width < 1 || height < 1) {
				width = getWidth();
				height = getHeight();
			}
			
			Rectangle2D bounds = new Rectangle2D(minX, minY, width, height);
			System.out.println(bounds.toString());
			
			return bounds;
		}
		
		
	}
	
	
	/**
	 * Rescale graph as well as reset orientation
	 */
	public void resetGraph() {
		
		double scaleX, scaleY;
		
//		worldMinX = getMinimumX();
//		worldMinY = getMinimumY();
//		worldMaxX = getMaximumX();
//		worldMaxY = getMaximumY();
//
//		System.out.println("Minimum :" + "(" + worldMinX + "," + worldMinY + ")");
//		System.out.println("Maximum :" + "(" + worldMaxX + "," + worldMaxY + ")");
		
//		if (selectables.size() < 2) {
//			scaleX = (getWidth() * (1 - margin)) / (getBoundingBox().getWidth());
//			scaleY = (getHeight() * (1 - margin)) / (getBoundingBox().getHeight());
//		} else {
			scaleX = (1 - margin);
			scaleY = (1 - margin);
//		}
		
		Affine mirror 
			= new Affine(1, 0, 0, -1, 0, getHeight());
		
		viewTransform = new Affine();
		viewTransform.createConcatenation(mirror);
		viewTransform.appendTranslation((margin / 2) * getWidth(), (margin / 2) * getHeight());
		viewTransform.appendScale(scaleX, scaleY);
//		if (selectables.size() > 1) {
//			viewTransform.appendTranslation(- getMinimumX(), - getMinimumY());
//		}
		
		rotationCounter = 0;
		
	}
	
	/**
	 * Zooms in/out from center of graph/canvas
	 */
	public void zoomFromCenter(double zoomRatioX, double zoomRatioY) {

		Affine oldTransform = viewTransform;

		Bounds canvasFrame = getLayoutBounds();			

		try {
			double rotation = rotationCounter;
			rotateGraphFromCenter(- rotation);
			
			Affine invViewTransform = viewTransform.createInverse();

			Bounds worldFrame = invViewTransform.transform(canvasFrame);
//			= invViewTransform.createTransformedShape(canvasFrame).getBounds2D();

			double worldCenterX = worldFrame.getMinX() + worldFrame.getWidth() / 2;
			double worldCenterY = worldFrame.getMinY() + worldFrame.getHeight() / 2;

			double scaleX = viewTransform.getMxx() * zoomRatioX;
			double scaleY = viewTransform.getMyy() * zoomRatioY;

			viewTransform = new Affine();
			viewTransform.appendTranslation(getWidth() / 2, getHeight() / 2);					
			viewTransform.appendScale(scaleX, scaleY);
			viewTransform.appendTranslation(- worldCenterX, - worldCenterY);

			rotateGraphFromCenter(rotation);
			
		} catch (NonInvertibleTransformException exp) {
			// TODO Auto-generated catch block

			viewTransform = oldTransform;
			GraphDialog.error(exp.getMessage());
			System.out.println(exp.getMessage());
			exp.printStackTrace();
		}				

	}
	
	/**
	 * Scales graph to fit x or y axis or both
	 */
	public void fitGraph(boolean fitXAxis, boolean fitYAxis) {
				
		Affine oldTransform = viewTransform;
		Rectangle2D bounds = getBoundingBox();
		Rectangle2D dataInCanvas = getTransformedRect(bounds);
			
		double zoomRatioX = 1; 
		double zoomRatioY = 1;
		double transX = 0;
		double transY = 0;
		
		if (fitXAxis) {
			zoomRatioX = (getWidth() * (1 - margin)) / dataInCanvas.getWidth();
			transX = getWidth() / 2 - (dataInCanvas.getWidth()/2 + dataInCanvas.getMinX());
		}
		
		if (fitYAxis) {
			zoomRatioY = (getHeight() * (1 - margin)) / dataInCanvas.getHeight();
			transY = getHeight() / 2 - (dataInCanvas.getHeight()/2 + dataInCanvas.getMinY());
		}
		
		viewTransform = new Affine();
		viewTransform.appendTranslation(transX, transY);
		viewTransform.createConcatenation(oldTransform);
		zoomFromCenter(zoomRatioX, zoomRatioY);
				
	}
	
	/**
	 * Rotate graph from center of canvas
	 */
	public void rotateGraphFromCenter(double count) {
		
		Point2D canvasCenter = new Point2D(getWidth() / 2, getHeight() / 2) ;
		Point2D worldCenter = new Point2D(0,0);
		
		Affine old = viewTransform;
		
		try {
			worldCenter = viewTransform.inverseTransform(canvasCenter);
			viewTransform.appendRotation(count * 90, worldCenter.getX(), worldCenter.getY());
		} catch (NonInvertibleTransformException e1) {
			// TODO Auto-generated catch block
			viewTransform = old;
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
	
		rotationCounter += count;
		rotationCounter = rotationCounter % 4;		
		
	}
	
	

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		resetGraph();
		draw();
	}

	@Override
	public void scaleToFit() {
		fitGraph(true, true);
		draw();
	}

	@Override
	public void fitVertical() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fitHorizontal() {

		
	}

	@Override
	public void zoomIn() {
		
		double zoomRatio = 1.20;
		zoomFromCenter(zoomRatio, zoomRatio);
		draw();
		
	}

	@Override
	public void zoomOut() {
		
		double zoomRatio = 0.80;
		zoomFromCenter(zoomRatio, zoomRatio);
		draw();
		
	}

	@Override
	public void panLeft() {
				
		double oldRotation = rotationCounter;
		rotateGraphFromCenter(-oldRotation);
		
		//shiftX is given as a fraction of the canvas width
		double shiftX = getWidth() * 0.2;	
		viewTransform.appendTranslation(-shiftX, 0);	
		
		rotateGraphFromCenter(oldRotation);
		
		draw();
	}

	@Override
	public void panRight() {
		
		double oldRotation = rotationCounter;
		rotateGraphFromCenter(-oldRotation);
		
		//shiftX is given as a fraction of the canvas width
		double shiftX = getWidth() * 0.2;	
		viewTransform.appendTranslation(shiftX, 0);	
		
		rotateGraphFromCenter(oldRotation);
		
		draw();
	}

	@Override
	public void panUp() {
		
		double oldRotation = rotationCounter;
		rotateGraphFromCenter(-oldRotation);
		
		double shiftY = getHeight() * 0.2;
		viewTransform.appendTranslation(0, -shiftY);
		
		rotateGraphFromCenter(oldRotation);
		
		draw();
	}

	@Override
	public void panDown() {
		
		double oldRotation = rotationCounter;
		rotateGraphFromCenter(-oldRotation);
		
		double shiftY = getHeight() * 0.2;
		viewTransform.appendTranslation(0, shiftY);
		
		rotateGraphFromCenter(oldRotation);
		
		draw();
	}

	@Override
	public void rotateCW() {
		rotateGraphFromCenter(1);
		draw();
	}

	@Override
	public void rotateCCW() {
		rotateGraphFromCenter(-1);
		draw();
	}

}
