package edu.ohiou.labimp.test;

import java.awt.Dimension;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.vecmath.Point3d;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFApplet;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import edu.ohiou.mfgresearch.labimp.gtk3d.Polygon3d;
import edu.ohiou.mfgresearch.labimp.gtk3d.Torus;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


public class fxtest2 extends Application {
	
	private LinkedList<ImpObject> target = new LinkedList<>();
	private DrawWFPanel dp;
	Group rotationGroup = new Group();
	
	double windowWidth = 800;
	double windowHeight = 600;
	
	public fxtest2() {
				
//		target = new Globe();
//		target = new Torus (20,1);
		
		Point3d p1 = new Point3d(0, 0, 0);
		Point3d p2 = new Point3d(1, 0, 0);
		Point3d p3 = new Point3d(1, 1, 0);
		Point3d p4 = new Point3d(0, 1, 0);
		Point3d p5 = new Point3d(0, 0, 1);
		Point3d p6 = new Point3d(1, 0, 1);
		Point3d p7 = new Point3d(1, 1, 1);
		Point3d p8 = new Point3d(0, 1, 1);
//		target = new Polygon3d();
//		((Polygon3d)target).addPoint(p1).addPoint(p5).addPoint(p6).addPoint(p2);
		

		target.add(new Torus (5,1));
		target.add(new Globe(1));
		target.add(new Polygon3d().addPoint(p1).addPoint(p5).addPoint(p6).addPoint(p2));
		
		dp = new DrawWFPanel();
		dp.getDrawPanel().setSize(new Dimension((int)windowWidth, (int)windowHeight));
//		dp.setView(50, 0, 15, 20);
//		dp.setView(5, 0, 0, 20);
		dp.setView(50, 0, 0, 20);
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		updateView();

		Pane root = new Pane(rotationGroup);

		//		//Create scene without a depth buffer
		//		Scene scene = new Scene(root, 600, 600);

		//Create scene with a depth buffer
		Scene scene = new Scene(root, windowWidth,
				windowHeight, true);

		//		//Disable depth test from this node and its children
		//		rotationGroup.setDepthTest(DepthTest.DISABLE);

		scene.setCamera(new PerspectiveCamera());
		stage.setScene(scene);
		stage.show();

		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Clicked " + e.getX() + ", " + e.getY());
				((DrawWFPanel)dp.gettCanvas()).
				mouseClicked((int)e.getX(), (int)e.getY());
				updateView();
			}
		});

		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Pressed " + e.getX() + ", " + e.getY());
				((DrawWFPanel)dp.gettCanvas()).
				mousePressed((int)e.getX(), (int)e.getY());	
				updateView();
			}
		});

		root.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Moved " + e.getX() + ", " + e.getY());
				((DrawWFPanel)dp.gettCanvas()).
				mouseMoved((int)e.getX(), (int)e.getY());	
				
				if (((DrawWFPanel)dp.gettCanvas()).mouseMode == DrawWFPanel.MODIFY_TARGET) {
					root.getScene().setCursor(Cursor.CROSSHAIR);
				} else {
					root.getScene().setCursor(Cursor.DEFAULT);
				}
			}
		});

		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Dragged " + e.getX() + ", " + e.getY());
				((DrawWFPanel)dp.gettCanvas()).
				mouseDragged((int)e.getX(), (int)e.getY());	
				updateView();
			}
		});
	}
	
	public void updateView() {
		
		Path sfx = new Path();
		rotationGroup.getChildren().clear();
		
		for(ImpObject io: target) {
			dp.setTarget(io);
			sfx = getFXShape(io.getShapeList(dp));
			rotationGroup.getChildren().add(sfx);
		}	
	}
	
	public static Path getFXShape(LinkedList swingShapeList) {
		
		Path sfx = new Path();
		
		for(Object s: swingShapeList) {
			java.awt.Shape ss = (java.awt.Shape)s;
			if (ss instanceof java.awt.Shape) {
				double[] coords = new double[6];
				ArrayList<double[]> areaPoints = new ArrayList<double[]>();

				for (PathIterator pi = ss.getPathIterator(null); !pi.isDone(); pi.next()) {
					int type = pi.currentSegment(coords);

					double[] pathIteratorCoords = {type, coords[0], coords[1], 
							coords[2], coords[3], coords[4], coords[5]};
					areaPoints.add(pathIteratorCoords);
				}

				for(double[] d: areaPoints) {
					if(d[0] == PathIterator.SEG_MOVETO) {

						MoveTo moveTo = new MoveTo();
						moveTo.setX(d[1]);
						moveTo.setY(d[2]);  
						sfx.getElements().add(moveTo);

					} else if (d[0] == PathIterator.SEG_LINETO) {

						LineTo lineTo = new LineTo();
						lineTo.setX(d[1]);
						lineTo.setY(d[2]);  
						sfx.getElements().add(lineTo);

					} else if (d[0] == PathIterator.SEG_CUBICTO) {

						CubicCurveTo ccTo = new CubicCurveTo(d[1], d[2], d[3], d[4], d[5], d[6]); 
						sfx.getElements().add(ccTo);

					} else if (d[0] == PathIterator.SEG_QUADTO) {

						QuadCurveTo qcTo = new QuadCurveTo(d[1], d[2], d[3], d[4]);
						sfx.getElements().add(qcTo);

					} else if (d[0] == PathIterator.SEG_CLOSE) {

						ClosePath cp = new ClosePath();
						sfx.getElements().add(cp);

					}
				}
			}
		}
		return sfx;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
