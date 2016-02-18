package graph.ui;


import java.util.HashMap;
import java.util.Random;

import edu.ohio.ent.cs5500.Arc;
import edu.ohio.ent.cs5500.Graph;
import edu.ohio.ent.cs5500.LayoutChangeListener;
import edu.ohio.ent.cs5500.Layouter;
import edu.ohio.ent.cs5500.Node;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

/**
 * This class defines the canvas where the nodes are drawn.
 * @author Arif
 */
public class DrawPanelFX extends Canvas implements LayoutChangeListener, Layouter{

	/**
	 * Graph model.
	 */
	private Graph myGraph;
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
	 * Constructor for graph canvas. Listeners are added and mouse behaviour is set. 
	 * @param g
	 */
	public DrawPanelFX(Graph g) {
		// TODO Auto-generated constructor stub

		myGraph = g;
		myGraph.addListener(this);

        // Redraw canvas when size changes.
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> makeLayout());
        heightProperty().addListener(evt -> makeLayout());	
     
        setOnMousePressed((MouseEvent me) -> {
        	double oldX, oldY;
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
				
				selectables.replace(current, selectables.get(current), p);				
			}
			
			draw();
			
        });
        
//        setOnMouseReleased((MouseEvent me) -> {
//        	
//        });
        
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
		draw();
	}

	@Override
	public void nodeDeleted(Node aNode) {
		// TODO Auto-generated method stub
		
		try {
			selectables.remove(aNode);
		}catch (Exception e) {
			GraphDialog.error(e.getMessage());
		}
		
		draw();
	}

	@Override
	public void arcAdded(Arc anArc) {
		// TODO Auto-generated method stub
		draw();
	}

	@Override
	public void arcDeleted(Arc anArc) {
		// TODO Auto-generated method stub
		draw();
	}

	@Override
	public void graphErased() {
		// TODO Auto-generated method stub
		selectables.clear();
		draw();
	}

	@Override
	public void layoutChanged() {
		// TODO Auto-generated method stub
		makeLayout();
	}
	
	@Override
	public void makeLayout() {
		// TODO Auto-generated method stub
		
		selectables.clear();
			
		for (Node aNode : myGraph.getNodes()) {		
			selectables.put
			(aNode, getRandCoord(aNode));
		}
		
		draw();

	}
	
	/**
	 * Draw graph nodes and arcs from list of selectables and graph model.
	 */
	private void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		double width = gc.getCanvas().getWidth();
		double height = gc.getCanvas().getHeight();
		gc.clearRect(0, 0, width, height);
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
		
		GraphicsContext gc = getGraphicsContext2D();
		Random randNumber = new Random();
		int xCoordinate, yCoordinate; 

		int marginPercent = 10;
		int minX = (int) gc.getCanvas().getWidth() * (marginPercent) / 100;
		int minY = (int) gc.getCanvas().getHeight()* (marginPercent) / 100;
		int maxX = (int) gc.getCanvas().getWidth() * (100 - marginPercent) / 100;
		int maxY = (int) gc.getCanvas().getHeight()* (100 - marginPercent) / 100;

		xCoordinate = minX + randNumber.nextInt(maxX - minX);
		yCoordinate = minY + randNumber.nextInt(maxY - minY);
		
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
			if (c.contains(p)) return aNode;
		}
		return null;
	}
	


}
