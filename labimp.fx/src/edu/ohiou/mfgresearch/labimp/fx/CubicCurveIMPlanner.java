package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.vecmath.Point3d;

import edu.ohiou.mfgresearch.labimp.basis.Draw2DApplet;
import edu.ohiou.mfgresearch.labimp.basis.Draw2DPanel;
import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.GUIApplet;
import edu.ohiou.mfgresearch.labimp.basis.ViewObject;

public class CubicCurveIMPlanner extends ViewObject {

	Point2D start = new Point2D.Double(0,0);
	Point2D end = new Point2D.Double(10,0);
	Point2D ctrl1 = new Point2D.Double(4,3);
	Point2D ctrl2 = new Point2D.Double(7,3);

	public CubicCurveIMPlanner() {
	}
	
	public CubicCurveIMPlanner(Point2D start, Point2D ctrl1, Point2D ctrl2, Point2D end) {
		this.start = start;
		this.ctrl1 = ctrl1;
		this.ctrl2 = ctrl2;
		this.end = end;	
	}

	public LinkedList<Shape> geetDrawList() {
		LinkedList<Shape> list = new LinkedList<Shape>();
		list.add(new CubicCurve2D.Double(start.getX(),start.getY(), 
											ctrl1.getX(),ctrl1.getY(),
											ctrl2.getX(),ctrl2.getY(),
											end.getX(),end.getY()));
		return list;
	}

	public LinkedList<Shape> geetFillList() {
		LinkedList<Shape> list = new LinkedList<Shape>();

		list.add(new Ellipse2D.Double(start.getX()-0.1, start.getY()-0.1,0.2,0.2));
		list.add(new Ellipse2D.Double(end.getX()-0.1, end.getY()-0.1,0.2,0.2));
		list.add(new Ellipse2D.Double(ctrl1.getX()-0.1, ctrl1.getY()-0.1,0.2,0.2));
		list.add(new Ellipse2D.Double(ctrl2.getX()-0.1, ctrl2.getY()-0.1,0.2,0.2));
		return list;
	}

	public LinkedList<DrawString> geetStringList() {
		LinkedList<DrawString> stringList = new LinkedList<DrawString>();
		try {
			stringList.add(new DrawString("This is an example of a cubic curve.", 2.5f, -1, 0.4));
			stringList.add (new DrawString ("Start Pt", 
					(float)start.getX(),(float)start.getY() - 0.2f) );
			stringList.add (new DrawString ("End Pt", 
					(float)end.getX(),(float)end.getY() - 0.2f) );
			stringList.add (new DrawString ("Control Pt 1", 
					(float)ctrl1.getX(),(float)ctrl1.getY() + 0.2f) );
			stringList.add (new DrawString ("Control Pt 2", 
					(float)ctrl2.getX(),(float)ctrl2.getY() + 0.2f) );
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
		return stringList;
	}

	public Collection giveSelectables() {
		Collection toReturn = new ArrayList();
		toReturn.add(start);	
		toReturn.add(end);
		toReturn.add(ctrl1);	
		toReturn.add(ctrl2);	
		return toReturn;
	}

	public static void main(String[] args) {
		CubicCurveIMPlanner cc = new CubicCurveIMPlanner();
		Draw2DApplet da = new Draw2DApplet(cc);
		((Draw2DPanel)cc.geettCanvas()).setDisplayStrings(true);
		cc.display();

	}

}
