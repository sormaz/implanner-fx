package edu.ohiou.mfgresearch.labimp.fx.test;

import java.util.LinkedList;

import javax.vecmath.Point3d;

import edu.ohiou.mfgresearch.labimp.draw.BezierCurve;
import edu.ohiou.mfgresearch.labimp.fx.CubicCurveIMPlanner;
import edu.ohiou.mfgresearch.labimp.fx.FXObject;
import edu.ohiou.mfgresearch.labimp.fx.Swing2DConverter;
import edu.ohiou.mfgresearch.labimp.fx.Swing3DConverter;

public class TestBezierCurve extends FXObject {

	public static void main(String[] args) {
		
	    Point3d p1 = new Point3d(0,0,0);
	    Point3d p2 = new Point3d(1,1,0);
	    Point3d p3 = new Point3d(2,1,0);
	    Point3d p4 = new Point3d(3,0,0);
	    Point3d p5 = new Point3d(5,1,0);
	    Point3d p6= new Point3d(8,0,0);
	    LinkedList list = new LinkedList();
	    list.add(p1);
	    list.add(p2);
	    list.add(p3);
	    list.add(p4);
	    list.add(p5);
	    list.add(p6);
BezierCurve bc = new BezierCurve(list);
		Swing3DConverter tbc = new Swing3DConverter(bc);
			tbc.display();
		}
	

}
