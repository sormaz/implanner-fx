package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.vecmath.Point3d;

import edu.ohiou.mfgresearch.labimp.gtk3d.Polygon3d;
import edu.ohiou.mfgresearch.labimp.gtk3d.Torus;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class testApplication extends Application {

	public testApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        primaryStage.setTitle("DrawCanvasFX");
        
        TetraHedron th = new TetraHedron();
              
        Globe globe = new Globe(3, 0, 0, 0);
        Swing3DConverter globeFX = new Swing3DConverter(globe);
        
        Torus torus = new Torus(5,1);
        Swing3DConverter torusFX = new Swing3DConverter(torus);
        
		Point3d p1 = new Point3d(0, 0, 0);
		Point3d p2 = new Point3d(1, 0, 0);
		Point3d p3 = new Point3d(1, 1, 0);
		Point3d p4 = new Point3d(0, 1, 0);
		Point3d p5 = new Point3d(0, 0, 1);
		Point3d p6 = new Point3d(1, 0, 1);
		Point3d p7 = new Point3d(1, 1, 1);
		Point3d p8 = new Point3d(0, 1, 1);
		Polygon3d polygon = new Polygon3d().addPoint(p1).addPoint(p5).addPoint(p6).addPoint(p2);
        Swing3DConverter polygonFX = new Swing3DConverter(polygon);
        
    	Point2D p11 = new Point2D.Double (0,0);
    	Point2D p21 = new Point2D.Double (2,0);
    	Point2D p31 = new Point2D.Double (2,2);
    	Point2D p41 = new Point2D.Double (0,2);
		
		Point2D [] pts = {p11, p21, p31, p41};

		Profile2DIMPlanner target = new Profile2DIMPlanner (pts);
		Swing2DConverter profileFX = new Swing2DConverter(target);
		
		CubicCurveIMPlanner cc = new CubicCurveIMPlanner();
		Swing2DConverter ccFX = new Swing2DConverter(cc);
		
		ObservableList<DrawableFX> targetList = FXCollections.observableArrayList();
        targetList.add(globeFX);
        targetList.add(torusFX);
        targetList.add(polygonFX);
        targetList.add(th);
        targetList.add(profileFX);        
        targetList.add(ccFX);
        DrawFXCanvas canvas = new DrawFXCanvas(targetList);
//        canvas.setActiveTarget(th);
        canvas.setActiveTarget(globeFX);
//        canvas.setActiveTarget(torusFX);
//        canvas.setActiveTarget(polygonFX);
                
//		Point2D p1 = new Point2D.Double (0,0);
//		Point2D p2 = new Point2D.Double (10,0);
//		Point2D p3 = new Point2D.Double (4,3);
//		Point2D p4 = new Point2D.Double (7,3);
//		
//		Point2D [] pts = {p1, p2, p3, p4};
//
//		Profile2DIMPlanner target = new Profile2DIMPlanner (pts);
//		Swing2DConverter profileFX = new Swing2DConverter(target);
//		
//		CubicCurveIMPlanner cc = new CubicCurveIMPlanner();
//		Swing2DConverter ccFX = new Swing2DConverter(cc);
//		
//		LinkedList<DrawableFX> targetList = new LinkedList<>();
//		targetList.add(profileFX);
//		targetList.add(ccFX);
//		DrawFXCanvas canvas = new DrawFXCanvas(targetList);\
        
        VBox vbox = new VBox();
        Pane p = new Pane();
        
        vbox.getChildren().addAll(canvas, p);
        
        Scene myScene = new Scene(vbox);
        primaryStage.setScene(myScene);
        primaryStage.show();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
