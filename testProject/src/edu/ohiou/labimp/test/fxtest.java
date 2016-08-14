package edu.ohiou.labimp.test;


import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class fxtest extends Application {

	@SuppressWarnings("unused")
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		//		Group rotationGroup = new Group(red, green, blue);
		
		Group rotationGroup = new Group();
		rotationGroup.setTranslateX(125);
		rotationGroup.setTranslateY(125);
		rotationGroup.setRotationAxis(Rotate.Y_AXIS);
		

		
//		Point2D p1 = new Point2D.Double (100,100);
//		Point2D p2 = new Point2D.Double (500,100);
//		Point2D p3 = new Point2D.Double (200,200);
//		Point2D p4 = new Point2D.Double (100,200);
//		Point2D [] pts = {p1, p2, p3, p4};
//		Profile2DIMPlanner target = new Profile2DIMPlanner (pts);
		
//		CubicCurveIMPlanner target  = new CubicCurveIMPlanner(
//					new Point2D.Double(50,500), 
//					new Point2D.Double(150,200), 
//					new Point2D.Double(300,200), 
//					new Point2D.Double(500,500));

		Globe target = new Globe(10);

//		for(Object s: target.getDrawList()) {		
		for(Object s: target.getShapeList(new DrawWFPanel(target))) {
			java.awt.Shape ss = (java.awt.Shape)s;
			if (ss instanceof java.awt.Shape) {
//				java.awt.geom.Path2D sp = (java.awt.geom.Path2D)ss;
				Path sfx = new Path();

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
						double x, y;

						x = d[1];
						y = d[2];

						MoveTo moveTo = new MoveTo();
						moveTo.setX(x);
						moveTo.setY(y);  

						sfx.getElements().add(moveTo);

					} else if (d[0] == PathIterator.SEG_LINETO) {
						double x, y;

						x = d[1];
						y = d[2];

						LineTo lineTo = new LineTo();
						lineTo.setX(x);
						lineTo.setY(y);  

						sfx.getElements().add(lineTo);
					} else if (d[0] == PathIterator.SEG_CUBICTO) {
						double x1, y1, x2, y2, x3, y3;

						x1 = d[1];
						y1 = d[2];
						x2 = d[3];
						y2 = d[4];
						x3 = d[5];
						y3 = d[6];

						CubicCurveTo ccTo = new CubicCurveTo(x1, y1, x2, y2, x3, y3); 

						sfx.getElements().add(ccTo);
					} else if (d[0] == PathIterator.SEG_QUADTO) {
						double x1, y1, x2, y2;

						x1 = d[1];
						y1 = d[2];
						x2 = d[3];
						y2 = d[4];

						QuadCurveTo qcTo = new QuadCurveTo(x1, y1, x2, y2);

						sfx.getElements().add(qcTo);
					} else if (d[0] == PathIterator.SEG_CLOSE) {
						ClosePath cp = new ClosePath();
						sfx.getElements().add(cp);
					}
				}
						
				Text t0 = new Text();
				t0.setText("0, 0");
				
				Text t1 = new Text();
				t1.setTranslateX(100);
				t1.setTranslateY(100);
				t1.setText("100, 100");
				
				Text t2 = new Text();
				t2.setTranslateX(500);
				t2.setTranslateY(100);
				t2.setText("500, 100");
				
				Text t3 = new Text();
				t3.setTranslateX(200);
				t3.setTranslateY(200);
				t3.setText("200, 200");
				
				Text t4 = new Text();
				t4.setTranslateX(100);
				t4.setTranslateY(200);
				t4.setText("100, 200");
				
				rotationGroup.getChildren().addAll(sfx, t0, t1, t2, t3, t4);
			}
		}

		Slider s1 = new Slider(0,360,0);
		s1.setBlockIncrement(1);
		s1.setTranslateX(225);
		s1.setTranslateY(575);
		rotationGroup.rotateProperty().bind(s1.valueProperty());

		Group root = new Group(rotationGroup, s1);



		//		//Create scene without a depth buffer
		//		Scene scene = new Scene(root, 600, 600);

		//Create scene with a depth buffer
		Scene scene = new Scene(root, 800, 800, true);

		//		//Disable depth test from this node and its children
		//		rotationGroup.setDepthTest(DepthTest.DISABLE);

		scene.setCamera(new PerspectiveCamera());
		stage.setScene(scene);
		stage.show();


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
