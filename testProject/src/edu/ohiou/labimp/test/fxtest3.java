package edu.ohiou.labimp.test;

import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.basis.ViewObject;
import edu.ohiou.mfgresearch.labimp.fx.CubicCurveIMPlanner;
import edu.ohiou.mfgresearch.labimp.fx.Profile2DIMPlanner;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


public class fxtest3 extends Application {
	
	Point2D p1 = new Point2D.Double (0,0);
	Point2D p2 = new Point2D.Double (2,0);
	Point2D p3 = new Point2D.Double (2,2);
	Point2D p4 = new Point2D.Double (0,2);
	
	private ViewObject target;
	Group rotationGroup;
	
	double windowWidth = 800;
	double windowHeight = 600;
	
	public fxtest3() {	

	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		Point2D [] pts = {p1, p2, p3, p4};

		target = new Profile2DIMPlanner (pts);
		
		Path sfx = getFXShape(target.getDrawList());
		
		System.out.println(sfx.getLayoutBounds().toString());
		
		double scale = 50;
		
		sfx.setStrokeWidth(1/scale);
		
		sfx.setScaleX(scale);
		sfx.setScaleY(scale);
		
		Group test = new Group(sfx);
		
		System.out.println(test.getLayoutBounds().toString());
		
		rotationGroup = new Group(test);
		
//		Point2D start = new Point2D.Double(0,0);
//		Point2D end = new Point2D.Double(10,0);
//		Point2D ctrl1 = new Point2D.Double(4,3);
//		Point2D ctrl2 = new Point2D.Double(7,3);
		
		target = new CubicCurveIMPlanner();
		
		Path sfx2 = getFXShape(target.getDrawList());
		
		System.out.println(sfx2.getLayoutBounds().toString());	
		
		sfx2.setStrokeWidth(1/scale);
				
		Path sfx3 = getFXShape(target.getFillList());
		
		System.out.println(sfx3.getLayoutBounds().toString());	
		
		sfx3.setStrokeWidth(1/scale);

		Group test2 = new Group(sfx2, sfx3);
		
		Scale s = new Scale(scale, scale);
		
		test2.getTransforms().add(s);
		
		System.out.println(test2.getLayoutBounds().toString());
		
		rotationGroup.getChildren().add(test2);
		
//		Text t1 = new Text("(" + p1.getX() + ", " + p1.getY() + ")");
//		t1.setLayoutX(p1.getX());
//		t1.setLayoutY(p1.getY());
//		rotationGroup.getChildren().add(t1);
//		
//		Text t2 = new Text("(" + p2.getX() + ", " + p2.getY() + ")");
//		t2.setLayoutX(p2.getX());
//		t2.setLayoutY(p2.getY());
//		rotationGroup.getChildren().add(t2);
//		
//		Text t3 = new Text("(" + p3.getX() + ", " + p3.getY() + ")");
//		t3.setLayoutX(p3.getX());
//		t3.setLayoutY(p3.getY());
//		rotationGroup.getChildren().add(t3);
//		
//		Text t4 = new Text("(" + p4.getX() + ", " + p4.getY() + ")");
//		t4.setLayoutX(p4.getX());
//		t4.setLayoutY(p4.getY());
//		rotationGroup.getChildren().add(t4);

//		rotationGroup.setScaleX(10);
//		rotationGroup.setScaleY(10);
//		rotationGroup.setScaleZ(10);

		Scene scene = new Scene(rotationGroup, windowWidth, windowHeight, true);

		scene.setCamera(new PerspectiveCamera());
		stage.setScene(scene);
		stage.show();

	}
	
	public void updateView() {
		
		Path sfx = getFXShape(target.getDrawList());
		
		rotationGroup.getChildren().clear();
		rotationGroup.getChildren().add(sfx);
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
						
						System.out.println(moveTo);

					} else if (d[0] == PathIterator.SEG_LINETO) {

						LineTo lineTo = new LineTo();
						lineTo.setX(d[1]);
						lineTo.setY(d[2]);  
						sfx.getElements().add(lineTo);
						
						System.out.println(lineTo);

					} else if (d[0] == PathIterator.SEG_CUBICTO) {

						CubicCurveTo ccTo = new CubicCurveTo(d[1], d[2], d[3], d[4], d[5], d[6]); 
						sfx.getElements().add(ccTo);
						
						System.out.println(ccTo);

					} else if (d[0] == PathIterator.SEG_QUADTO) {

						QuadCurveTo qcTo = new QuadCurveTo(d[1], d[2], d[3], d[4]);
						sfx.getElements().add(qcTo);
						
						System.out.println(qcTo);

					} else if (d[0] == PathIterator.SEG_CLOSE) {

						ClosePath cp = new ClosePath();
						sfx.getElements().add(cp);
						
						System.out.println(cp);

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
