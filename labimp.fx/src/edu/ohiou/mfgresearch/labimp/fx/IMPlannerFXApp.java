package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.LinkedList;

import javax.vecmath.Point3d;

import edu.ohiou.mfgresearch.labimp.gtk3d.Polygon3d;
import edu.ohiou.mfgresearch.labimp.gtk3d.Torus;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IMPlannerFXApp extends Application{

	private ObservableList<DrawableFX> getTargetList() {
		
		ObservableList<DrawableFX> targetList = FXCollections.observableArrayList();
		
        TetraHedron th = new TetraHedron(2,4);
        
        Globe globe = new Globe(3, 0, 0, 0);
        Swing3DConverter globeFX = new Swing3DConverter(globe);
        
        IMPBox box = new IMPBox(3);
        Swing3DConverter boxFX = new Swing3DConverter(box);
        
        Torus torus = new Torus(5,1);
        Swing3DConverter torusFX = new Swing3DConverter(torus);
        
        SphereFX spFX = new SphereFX(3);
        
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
        
    	Point2D p11 = new Point2D.Double (-2,-2);
    	Point2D p21 = new Point2D.Double (2,-2);
    	Point2D p31 = new Point2D.Double (2,2);
    	Point2D p41 = new Point2D.Double (-2,2);
		
		Point2D [] pts = {p11, p21, p31, p41};

		Profile2DIMPlanner target = new Profile2DIMPlanner (pts);
		Swing2DConverter profileFX = new Swing2DConverter(target);
		
		CubicCurveIMPlanner cc = new CubicCurveIMPlanner();
		Swing2DConverter ccFX = new Swing2DConverter(cc);
		
        targetList.add(globeFX);
        targetList.add(boxFX);
        targetList.add(torusFX);
        targetList.add(polygonFX);
        targetList.add(th);
        targetList.add(spFX);
        targetList.add(profileFX);        
        targetList.add(ccFX);
        
        return targetList;
	}
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
            
            DrawFXPanel panel = new DrawFXPanel();   
            
            ObservableList<DrawableFX> targetList = getTargetList();
            
            panel.setTargetList(targetList);

            Scene myScene = new Scene(panel);
            primaryStage.setTitle("IMPlannerFX");
            primaryStage.setScene(myScene);
            primaryStage.show();
            
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		System.out.println(Arrays.toString(e.getStackTrace()));
    	}
    
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	
}
