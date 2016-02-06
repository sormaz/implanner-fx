/**
 * 
 */
package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;


/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class defines the panel in which the graphing is done.
 * @author Arif
 * @Date 11 Sep 2013
 * 
 */
@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements LayoutChangeListener {

	/**
	 * Drawable object containing the graph arcs and edges information
	 */
	private Drawable target;
	
	/**
	 * Canvas on which graph is drawn
	 */
	private DrawCanvas canvas;
	
	/**
	 * Map of color and shape of objects to be drawn in graph
	 */
	private HashMap<Color, ArrayList<Shape>> drawMap = new HashMap<Color, ArrayList<Shape>>();
	
	/**
	 * Map of shape and draw objects which can be selected 
	 */
	private HashMap<Shape, DrawObject> selectables = new HashMap<Shape, DrawObject>();
	
	/**
	 * Currently selected draw object
	 */
	private DrawObject current;
	
	/**
	 * The fraction of screen used for margins. A value of 0.2 indicates a 10% margin on the top, bottom, left and right.
	 */
	private double margin = 0.2;
	
	/**
	 * Transformation matrix for graph drawing
	 */
	private AffineTransform viewTransform = new AffineTransform();
	
	/**
	 * X and Y coordinates of previous mouse press event
	 */
	private double oldX, oldY;
	
	/**
	 * Keeps record of the number of rotations
	 */
	private double rotationCounter = 0;
	
	/**
	 * Graph color
	 */
	private final static Color GRAPH_COLOR = Color.RED;
	
	/**
	 * DrawPanel default width
	 */
	public final static int DEFAULT_WIDTH = 500;
	
	/**
	 * DrawPanel default height
	 */
	public final static int DEFAULT_HEIGHT = 500;
	
	
	/**
	 * Create new DrawPanel
	 */
	public DrawPanel(Drawable target, boolean showButtons) {
		
		setTarget(target);
	
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		canvas = new DrawCanvas();
		add(canvas, BorderLayout.CENTER);
		if (showButtons) {
			JToolBar buttons = getButtons();
			buttons.setFloatable(false);
			add(buttons, BorderLayout.SOUTH);
		}

		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
		addMouseWheelListener(new MouseWheelHandler());
		addComponentListener(new ResizeHandler());
		
	}

	/**
	 * Set target. Listen to layout change.
	 */
	public void setTarget(Drawable target) {
		this.target = target;
		target.addListener(this);		
	}
	
	/**
	 * Add color shape pair to draw map
	 */	
	public void addShape(Color color, Shape shape) {
		ArrayList<Shape> shapes = drawMap.get(color);
		if (shapes != null) {
			shapes.add(shape);
			drawMap.put(color, shapes);
		} else {
			ArrayList<Shape> newShapes = new ArrayList<Shape>();
			newShapes.add(shape);
			drawMap.put(color, newShapes);
		}	
	}

	/**
	 * Create transformed shape from view transform matrix
	 */
	public Shape createTransformedShape(Shape s) {
		return viewTransform.createTransformedShape(s);
	}
	
	/**
	 * Rescale graph as well as reset orientation
	 */
	public void resetGraph() {
		
		double worldMinX = target.getBoundingBox().getMinX();
		double worldMinY = target.getBoundingBox().getMinY();

		double scaleX 
			= (canvas.getWidth() * (1 - margin)) / target.getBoundingBox().getWidth();
		double scaleY
			= (canvas.getHeight() * (1 - margin)) / target.getBoundingBox().getHeight();

		AffineTransform mirror 
			= new AffineTransform(1, 0, 0, -1, 0, canvas.getHeight());
		
		viewTransform = new AffineTransform();
		viewTransform.concatenate(mirror);
		viewTransform.translate
				((margin / 2) * canvas.getWidth(), (margin / 2) * canvas.getHeight());
		viewTransform.scale(scaleX, scaleY);
		viewTransform.translate(- worldMinX, - worldMinY);
		
		rotationCounter = 0;

	}
	
	/**
	 * Zooms in/out from center of graph/canvas
	 */
	public void zoomFromCenter(double zoomRatioX, double zoomRatioY) {

		AffineTransform oldTransform = viewTransform;

		Rectangle2D canvasFrame = new Rectangle2D.Double
				(0, 0, canvas.getWidth(), canvas.getHeight());			

		try {
			double rotation = rotationCounter;
			rotateGraphFromCenter(- rotation);
			
			AffineTransform invViewTransform = viewTransform.createInverse();

			Rectangle2D worldFrame 
			= invViewTransform.createTransformedShape(canvasFrame).getBounds2D();

			double worldCenterX = worldFrame.getX() + worldFrame.getWidth() / 2;
			double worldCenterY = worldFrame.getY() + worldFrame.getHeight() / 2;

			double scaleX = viewTransform.getScaleX() * zoomRatioX;
			double scaleY = viewTransform.getScaleY() * zoomRatioY;

			viewTransform = new AffineTransform();
			viewTransform.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);					
			viewTransform.scale(scaleX, scaleY);
			viewTransform.translate(- worldCenterX, - worldCenterY);

			rotateGraphFromCenter(rotation);
			
		} catch (NoninvertibleTransformException exp) {
			// TODO Auto-generated catch block

			viewTransform = oldTransform;
			System.out.println(exp.getMessage());
			exp.printStackTrace();
		}				

	}
	
	/**
	 * Scales graph to fit x or y axis or both
	 */
	public void fitGraph(boolean fitXAxis, boolean fitYAxis) {
				
			AffineTransform oldTransform = viewTransform;
			Rectangle2D bounds = target.getBoundingBox();
			Rectangle2D dataInCanvas = viewTransform.createTransformedShape(bounds).getBounds2D();
				
			double zoomRatioX = 1; 
			double zoomRatioY = 1;
			double transX = 0;
			double transY = 0;
			
			if (fitXAxis) {
				zoomRatioX = (canvas.getWidth() * (1 - margin)) / dataInCanvas.getWidth();
				transX = canvas.getWidth() / 2 - dataInCanvas.getCenterX();
			}
			
			if (fitYAxis) {
				zoomRatioY = (canvas.getHeight() * (1 - margin)) / dataInCanvas.getHeight();
				transY = canvas.getHeight() / 2 - dataInCanvas.getCenterY();
			}
			
			viewTransform = new AffineTransform();
			viewTransform.translate(transX, transY);
			viewTransform.concatenate(oldTransform);
			zoomFromCenter(zoomRatioX, zoomRatioY);
				
	}
	
	/**
	 * Rotate graph from center of canvas
	 */
	public void rotateGraphFromCenter(double count) {
		
		Point2D canvasCenter = new Point2D.Double(canvas.getWidth() / 2, canvas.getHeight() / 2) ;
		Point2D worldCenter = new Point2D.Double();
		
		AffineTransform old = viewTransform;
		
		try {
			viewTransform.inverseTransform(canvasCenter, worldCenter);
			viewTransform.rotate(count * Math.PI / 2, worldCenter.getX(), worldCenter.getY());
		} catch (NoninvertibleTransformException e1) {
			// TODO Auto-generated catch block
			viewTransform = old;
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
	
		rotationCounter += count;
		rotationCounter = rotationCounter % 4;		
		
	}
	
	@Override
	public void layoutChanged() {
		// TODO Auto-generated method stub
		
		resetGraph();
		repaint();
	}
	
	/**
	 * Create map of shape and draw objects to be selected from target
	 */
	public void setSelectable() {
		// TODO Auto-generated method stub
		
		selectables.clear();
		
		for (Shape s : target.getSelectables().keySet()) {
			selectables.put(createTransformedShape(s), target.getSelectables().get(s));
		}
		
	}
	
	/**
	 * Create draw map from target
	 */
	public void setDrawMap() {
		
		drawMap.clear();
		
		for (Shape s : target.makeDrawList()) {
			addShape(GRAPH_COLOR, s);
		}
	}
	
	/**
	 * Find if cursor is over a selectable item
	 */
	public DrawObject find(Point2D p)
	{
		for (Shape s : selectables.keySet())
		{
			if (s.contains(p)) return selectables.get(s);
		}
		return null;
	}
	
	/**
	 * Canvas class used to draw the graph
	 */
	class DrawCanvas extends JComponent {
		
		/**
		 * Create new canvas
		 */
		private DrawCanvas() {
			Border etched = BorderFactory.createEtchedBorder();
			Border graphPanelBorder = BorderFactory.createTitledBorder(etched, "Draw Panel");
			setBorder(graphPanelBorder);
		}
		
		/**
		 * Paints Canvas. Creates draw map and selectables map. 
		 */
		public void paintComponent(Graphics g)
		{			
			super.paintComponent(g);
						
			Graphics2D g2 = (Graphics2D) g;
			
			setDrawMap();
			setSelectable();
			
			//Used to create grid
//			for (int x = getX(); x <= getWidth(); x += 10) {
//				for (int y = getY(); y <= getHeight(); y += 10) {
//					g2.drawString(".", x, y);
//				}
//			}
			
			//Used to create crosshair at the center
//			g2.drawLine(canvas.getWidth()/2 - 10, canvas.getHeight()/2, canvas.getWidth()/2 + 10, canvas.getHeight()/2);
//			g2.drawLine(canvas.getWidth()/2, canvas.getHeight()/2 - 10, canvas.getWidth()/2, canvas.getHeight()/2 + 10);
			
//			g2.drawLine(canvas.getWidth()/2, 0, canvas.getWidth()/2, canvas.getHeight());
//			g2.drawLine(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);

			
			AffineTransform oldTransform = g2.getTransform();
			
			AffineTransform newTransform = new AffineTransform(oldTransform);
			newTransform.concatenate(viewTransform);
			
			g2.setTransform(newTransform);
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
								RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			for (Color drawColor : drawMap.keySet()) {
				g2.setPaint(drawColor);
				
				for (Shape drawShape : drawMap.get(drawColor)) {
					g2.draw(drawShape);
				}
			}
				
			g2.setTransform(oldTransform);
			
			Point2D transLoc = new Point2D.Double();
			for (Point2D location : target.getGraphString().keySet()) {
				viewTransform.transform(location, transLoc);
				g2.drawString(target.getGraphString().get(location), 
								(int) transLoc.getX(), (int) transLoc.getY());
			}
			

			
		}
		
	}
	

	/**
	 * Class to handle mouse pressed and mouse released
	 */
	private class MouseHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent event)
		{		
			current = find(event.getPoint());

			oldX = event.getX();
			oldY = event.getY();		
			
		}
		
		public void mouseReleased(MouseEvent event) {

			if (SwingUtilities.isRightMouseButton(event)) {

				double newX = event.getX();
				double newY = event.getY();				
				
				AffineTransform oldTransform = viewTransform;
				
				Rectangle2D selectedFrame = new Rectangle2D.Double();
				selectedFrame.setFrameFromDiagonal(newX, newY, oldX, oldY);
				if (selectedFrame.isEmpty()) return;				
				
				double zoomRatioX = canvas.getWidth() / selectedFrame.getWidth();
				double zoomRatioY = canvas.getHeight() / selectedFrame.getHeight();
				
				double transX = canvas.getWidth() / 2 - selectedFrame.getCenterX();
				double transY = canvas.getHeight() / 2 -  selectedFrame.getCenterY();
				
				viewTransform = new AffineTransform();
				viewTransform.translate(transX, transY);
				viewTransform.concatenate(oldTransform);
				zoomFromCenter(zoomRatioX, zoomRatioY);
				
				repaint();

			}
			
		}
		
	}

	/**
	 * Class to handle mouse moved and mouse dragged
	 */
	private class MouseMotionHandler implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent event)
		{			
			if (find(event.getPoint()) == null) setCursor(Cursor.getDefaultCursor());
			else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}

		public void mouseDragged(MouseEvent event)
		{
			
			if (current != null)
			{
				int x = event.getX();
				int y = event.getY();
				Point2D p = new Point2D.Double(x, y);
				try {
					viewTransform.inverseTransform(p, p);
					current.setCenterPoint(p.getX(), p.getY());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				

			} else {
				
				if (SwingUtilities.isLeftMouseButton(event)) {
				
					setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

					double shiftX = event.getX() - oldX;
					double shiftY = event.getY() - oldY;
					
//					viewTransform.translate(shiftX, - shiftY);
					
					AffineTransform old = viewTransform;
					
					viewTransform = new AffineTransform();				
					viewTransform.translate(shiftX, shiftY);
					viewTransform.concatenate(old);
					
					oldX = event.getX();
					oldY = event.getY();

				} else if (SwingUtilities.isRightMouseButton(event)) {

					if (oldY == event.getY()) {
						setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					} else if (oldX == event.getX()) {
						setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
					} else if ((oldX > event.getX() && oldY > event.getY()) 
							|| (oldX < event.getX() && oldY < event.getY())) {
						setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
					} else if ((oldX > event.getX() && oldY < event.getY()) 
							|| (oldX < event.getX() && oldY > event.getY())) {
						setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
					}
				}
				
			}
			
			repaint();
		}
	}
	
	/**
	 * Class to handle mouse wheel rotation
	 */
	private class MouseWheelHandler implements MouseWheelListener {
		
		public void mouseWheelMoved(MouseWheelEvent e) {
			
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
				
				double zoomRatio = 1;
				if (e.getWheelRotation() < 0) {
					zoomRatio = 1.05;
				} else {
					zoomRatio = 0.95;
				}
				
				zoomFromCenter(zoomRatio, zoomRatio);
				repaint();
			}

		}
	}
	
	/**
	 * Class to handle DrawPanel resize
	 */
	private class ResizeHandler extends ComponentAdapter {

	    public void componentResized(ComponentEvent e) {
	        layoutChanged();           
	    }
    
	}
	
	/**
	 * Returns the graph built-in buttons toolbar 
	 */
	private JToolBar getButtons() {
		
		JButton zoomIn, zoomOut, scaleToFit, refresh, panLeft, panRight, panUp, panDown, fitVertical, fitHorizontal, rotateCW, rotateCCW;
		zoomIn = new JButton("Zoom In" , new ImageIcon("icons\\ZoomIn.png"));
		zoomOut = new JButton("Zoom Out" , new ImageIcon("icons\\ZoomOut.png"));
		scaleToFit = new JButton("Scale To Fit" , new ImageIcon("icons\\FullScreen.png"));
		refresh = new JButton("Refresh" , new ImageIcon("icons\\Refresh.png"));
		panLeft = new JButton("Pan Left" , new ImageIcon("icons\\Arrow2Left.png"));
		panRight = new JButton("Pan Right" , new ImageIcon("icons\\Arrow2Right.png"));
		panDown = new JButton("Pan Down" , new ImageIcon("icons\\Arrow2Down.png"));
		panUp = new JButton("Pan Up" , new ImageIcon("icons\\Arrow2Up.png"));
		fitVertical = new JButton("Fit Vertical" , new ImageIcon("icons\\FitVertical.png"));
		fitHorizontal = new JButton("Fit Horizontal" , new ImageIcon("icons\\FitHorizontal.png"));
		rotateCW = new JButton("Rotate CW" , new ImageIcon("icons\\ArrowCW.png"));
		rotateCCW = new JButton("Rotate CCW" , new ImageIcon("icons\\ArrowCCW.png"));

		
		zoomIn.setToolTipText("Zoom in");
		zoomOut.setToolTipText("Zoom out");
		scaleToFit.setToolTipText("Scale to fit view");
		refresh.setToolTipText("Reset graph");
		panLeft.setToolTipText("Move left");
		panRight.setToolTipText("Move right");
		panDown.setToolTipText("Move down");
		panUp.setToolTipText("Move up");
		fitVertical.setToolTipText("Fit vertical");
		fitHorizontal.setToolTipText("Fit horizontal");
		rotateCW.setToolTipText("Rotate clockwise");
		rotateCCW.setToolTipText("Rotate counter-clockwise");

		zoomIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				double zoomRatio = 1.20;
				zoomFromCenter(zoomRatio, zoomRatio);
				repaint();
				
			}
		});
		
		zoomOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				double zoomRatio = 0.80;
				zoomFromCenter(zoomRatio, zoomRatio);
				repaint();
				
			}
		});
		
		scaleToFit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				fitGraph(true, true);
				repaint();
				
			}
		});
		
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				resetGraph();
				repaint();
			}
		});
		
		panLeft.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//shiftX is given as a fraction of the canvas width
				double shiftX = canvas.getWidth() * 0.2;
				
				AffineTransform old = viewTransform;
				
				viewTransform = new AffineTransform();				
				viewTransform.translate(- shiftX, 0);
				viewTransform.concatenate(old);
				
				repaint();
				
			}
		});
		
		panRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//shiftX is given as a fraction of the canvas width
				double shiftX = canvas.getWidth() * 0.2;
				
				AffineTransform old = viewTransform;
				
				viewTransform = new AffineTransform();				
				viewTransform.translate(shiftX, 0);
				viewTransform.concatenate(old);
				
				repaint();
				
			}
		});
		
		panDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//shiftY is given as a fraction of the canvas height
				double shiftY = canvas.getHeight() * 0.2;
				
				AffineTransform old = viewTransform;
				
				viewTransform = new AffineTransform();				
				viewTransform.translate(0, shiftY);
				viewTransform.concatenate(old);
				
				repaint();
				
			}
		});
		
		panUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//shiftY is given as a fraction of the canvas height
				double shiftY = canvas.getHeight() * 0.2;
				
				AffineTransform old = viewTransform;
				
				viewTransform = new AffineTransform();				
				viewTransform.translate(0, - shiftY);
				viewTransform.concatenate(old);
			
				repaint();
				
			}
		});
		
		fitHorizontal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub			
				
				fitGraph(true, false);				
				repaint();
				
			}
		});
		
		fitVertical.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub			
				
				fitGraph(false, true);		
				repaint();
				
			}
		});

		rotateCW.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				
				rotateGraphFromCenter(-1);
				repaint();
				
			}
		});
		
		rotateCCW.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				rotateGraphFromCenter(1);
				repaint();
				
			}
		});

		JToolBar buttons = new JToolBar();
		
		buttons.setLayout(new GridLayout(2, 6));
		
		buttons.add(refresh);		
		buttons.add(scaleToFit);
		buttons.add(fitVertical);
		buttons.add(fitHorizontal);
		buttons.add(zoomIn);
		buttons.add(zoomOut);
		buttons.add(panLeft);
		buttons.add(panRight);
		buttons.add(panUp);
		buttons.add(panDown);
		buttons.add(rotateCW);
		buttons.add(rotateCCW);
		
		return buttons;
		
	}
	
}


