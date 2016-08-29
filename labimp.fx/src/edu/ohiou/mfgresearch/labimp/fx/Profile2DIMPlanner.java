package edu.ohiou.mfgresearch.labimp.fx;

/**
 * Title:        Inlet Fan Case Fiper Demo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Ohio University
 * @author
 * @version 1.0
 */
import java.util.*;
import java.awt.Shape;
import java.awt.geom.*;

import javax.swing.JList;

import edu.ohiou.mfgresearch.labimp.basis.Draw2DApplet;
import edu.ohiou.mfgresearch.labimp.basis.Draw2DPanel;
import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.GUIApplet;
import edu.ohiou.mfgresearch.labimp.basis.ViewObject;
import edu.ohiou.mfgresearch.labimp.gtk2d.GtkConstants;
import edu.ohiou.mfgresearch.labimp.gtk2d.InvalidProfileException;

public class Profile2DIMPlanner extends ViewObject {
	LinkedList points;

	public Profile2DIMPlanner(Point2D [] inPoints) {
		points = new LinkedList ();
		for (int i = 0; i < inPoints.length; i++)
			points.add(inPoints[i]);
	}

	public Profile2DIMPlanner (double [] x, double [] y) throws Exception {
		if (x.length == y.length) {
			points = new LinkedList();
			Point2D.Double point;
			for (int i = 0; i < x.length; i++) {
				points.add(new Point2D.Double (x[i], y[i]));
			}
		}
		else throw new Exception ("Y array is different size from X array");
	}

	public Profile2DIMPlanner (LinkedList inPoints) {
		points = inPoints;
	}

	public Profile2DIMPlanner (Rectangle2D rectangle) {
		this (new LinkedList() );
		double x = rectangle.getX(), y = rectangle.getY(),
				w = rectangle.getWidth(), h = rectangle.getHeight();
		addPoint (new Point2D.Double(x,y) );
		addPoint (new Point2D.Double(x+w,y));
		addPoint (new Point2D.Double(x+w,y+h));
		addPoint (new Point2D.Double(x,y+h));
	}

	public void addPoint (Point2D point) {
		points.add(point);
	}

	public double getBoundingArea () {
		Rectangle2D rectangle = getBoundingBox();
		return rectangle.getWidth() * rectangle.getHeight();
	}

	@SuppressWarnings("rawtypes")
	public LinkedList getPointSet () {
		return points;
	}

	public Rectangle2D getBoundingBox () {
		double maxX=Double.MIN_VALUE, maxY=Double.MIN_VALUE,
				minX=Double.MAX_VALUE, minY=Double.MAX_VALUE;
		for (ListIterator itr = points.listIterator();itr.hasNext();) {
			Point2D.Double p = (Point2D.Double) itr.next();
			maxX = p.x > maxX ? p.x : maxX;
			maxY = p.y > maxY ? p.y : maxY;
			minX = p.x < minX ? p.x : minX;
			minY = p.y < minY ? p.y : minY;
		}
		return new Rectangle2D.Double (minX, minY, maxX-minX, maxY-minY);
	}

	public static void main (String [] args) {

		Point2D p1 = new Point2D.Double (1,1);
		Point2D p2 = new Point2D.Double (5,1);
		Point2D p3 = new Point2D.Double (2,2);
		Point2D p4 = new Point2D.Double (1,2);
		
		Point2D [] pts = {p1, p2, p3, p4};

		Profile2DIMPlanner target = new Profile2DIMPlanner (pts);
		GUIApplet applet = new Draw2DApplet(target);
		target.settApplet(applet);
		((Draw2DPanel)target.gettCanvas()).setDisplayStrings(true);

		target.display();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init () {
		panel = new ViewPanel();
		panel.add(new JList(points.toArray() ));
	}

	@SuppressWarnings({ "rawtypes" })
	public Collection giveSelectables() {
		return points;
	}

	@SuppressWarnings("unchecked")
	public LinkedList<Shape> getFillList() {	
		return getDrawList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList getDrawList () {
		Path2D fillShape;

		fillShape = new Path2D.Double();
		for (int i = 0; i < points.size(); i++) {

			double x, y;

			x = ((Point2D) points.get(i)).getX();
			y = ((Point2D) points.get(i)).getY();


			if (i == 0) {
				fillShape.moveTo(x, y);   
			} else{
				fillShape.lineTo(x, y);
			}
		}

		fillShape.closePath();

		LinkedList<Shape> list = new LinkedList<Shape>();
		list.add(fillShape);

		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList getStringList () {
		LinkedList strings = new LinkedList();
		strings.add(new DrawString (toString(), 0,0));
		return strings;
	}

	public String toString() {
		return points.toString();
	}

}
