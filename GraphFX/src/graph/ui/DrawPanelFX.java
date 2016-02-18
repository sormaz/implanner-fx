package graph.ui;

import java.util.HashMap;
import java.util.Random;

import edu.ohio.ent.cs5500.Arc;
import edu.ohio.ent.cs5500.Graph;
import edu.ohio.ent.cs5500.LayoutChangeListener;
import edu.ohio.ent.cs5500.Layouter;
import edu.ohio.ent.cs5500.Node;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class DrawPanelFX extends Canvas implements LayoutChangeListener, Layouter{

	private Graph myGraph;
	private HashMap<Point2D, Node> selectables = new HashMap<Point2D, Node>();
	private double nodeRadius = 15;
	
	public DrawPanelFX(Graph g) {
		// TODO Auto-generated constructor stub

		myGraph = g;
		myGraph.addListener(this);

        // Redraw canvas when size changes.
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> makeLayout());
        heightProperty().addListener(evt -> makeLayout());	
     
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
	public void nodeAdded(edu.ohio.ent.cs5500.Node aNode) {
		// TODO Auto-generated method stub
		
		GraphicsContext gc = getGraphicsContext2D();
		Random randNumber = new Random();
		int xCoordinate, yCoordinate; 
		
		int minX = 0;
		int minY = 0;
		int maxX = (int) gc.getCanvas().getWidth();
		int maxY = (int) gc.getCanvas().getHeight();	
		xCoordinate = minX + randNumber.nextInt(maxX - minX);
		yCoordinate = minY + randNumber.nextInt(maxY - minY);
		
		selectables.put(new Point2D(xCoordinate, yCoordinate), aNode);
		draw();
	}

	@Override
	public void nodeDeleted(edu.ohio.ent.cs5500.Node aNode) {
		// TODO Auto-generated method stub
		
		try {
			selectables.remove(getSelectable(aNode));
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
		GraphicsContext gc = getGraphicsContext2D();
		
		Random randNumber = new Random();
		int xCoordinate, yCoordinate; 
		
		int minX = 0;
		int minY = 0;
		int maxX = (int)gc.getCanvas().getWidth();;
		int maxY = (int)gc.getCanvas().getHeight();	
		
		for (Node aNode : myGraph.getNodes()) {

			xCoordinate = minX + randNumber.nextInt(maxX - minX);
			yCoordinate = minY + randNumber.nextInt(maxY - minY);
			
			selectables.put
			(new Point2D(xCoordinate, yCoordinate), aNode);
		}
		
		draw();

	}
	
	private void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		double width = gc.getCanvas().getWidth();
		double height = gc.getCanvas().getHeight();
		gc.clearRect(0, 0, width, height);
		gc.setStroke(Color.BLUE);
		
		for (Point2D d : selectables.keySet()) {		
			gc.strokeOval(d.getX()-nodeRadius, d.getY()-nodeRadius, 
							nodeRadius*2, nodeRadius*2);
			gc.fillText(selectables.get(d).getName(), d.getX(), d.getY());
		}
		
		for (Arc anArc : myGraph.getUndirectedArcs()){
			Node[] nodes;
			nodes = anArc.getNodes();
			Point2D p1,p2;
			p1 = getSelectable(nodes[0]);
			p2 = getSelectable(nodes[1]);
			gc.strokeLine(p1.getX(),p1.getY(), p2.getX(),p2.getY());
		}
		
		for (Arc anArc : myGraph.getDirectedArcs()){
			Node[] nodes;
			nodes = anArc.getNodes();
			Point2D p1,p2;
			p1 = getSelectable(nodes[0]);
			p2 = getSelectable(nodes[1]);
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
	
	private double getStartAngle(Point2D p1, Point2D p2) {
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();
		
//		double startAngle = Math.atan((x2-x1)/(y2-y1)) + Math.PI / 2;
		double startAngle = Math.atan2((x2-x1), (y2-y1)) + Math.PI / 2;
		return startAngle;
	}
	
	private Point2D getSelectable(Node aNode) {
		
		for(Point2D p: selectables.keySet()){
			if (selectables.get(p) == aNode) {
				return  p;
			}
		}
		
		return null;
	}

}
