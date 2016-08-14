package edu.ohiou.labimp.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import edu.ohiou.mfgresearch.labimp.basis.ViewObject.ViewPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFApplet;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import edu.ohiou.mfgresearch.labimp.gtk3d.LineSegment;

public class Globe extends ImpObject {
	
	private Point3d center; 
	private double radius;
	private int linesOfLongitude = 12;
	private int linesOfLatitude = 8;
	
	public Globe() {
		this(3.0);	
	}
	
	public Globe(double radius) {
		this(radius, new Point3d(0,0,0));
	}
	
	public Globe(double radius, Point3d center) {
		this.radius = radius;
		this.center = center;	
	}
	
	public Globe(double radius, double centerX, double centerY, double centerZ) {
		this(radius, new Point3d(centerX, centerY, centerZ));	
	}
	
	public void init() {
		panel = new GlobePanel(this);
	}
	
	public LinkedList getPointSet() {
		LinkedList pointSet = new LinkedList<>();
		pointSet.add(center);
		return pointSet;
	}
	
	public LinkedList getShapeList(DrawWFPanel canvas) {
		LinkedList shapeList = new LinkedList();
		
		double startAngle = 0;
		double endAngle = 2*Math.PI;
		double deviationAngle;
		
		LinkedList<Ellipse> ellipses = new LinkedList<>();
		for(int i=0; i < linesOfLongitude; i++){
			deviationAngle = i * Math.PI / linesOfLongitude;
			Ellipse e = new Ellipse(radius, radius, center, new Vector3d(0,1,0), 
									new Vector3d((Math.cos(deviationAngle)),
												0,(Math.sin(deviationAngle))), 
												startAngle, endAngle);
			ellipses.add(e);
		}
		
		for(int i=0; i <= linesOfLatitude / 2; i++){
			
			deviationAngle = i * Math.PI/ linesOfLatitude;
			Point3d c = new Point3d(center.getX(),
									center.getY() + 
									radius * Math.sin(deviationAngle),
									center.getZ());
			double r = radius * Math.cos(deviationAngle);
			
			Ellipse e1 = new Ellipse(r, r, c, new Vector3d(1,0,0), 
					new Vector3d(0,0,1), startAngle, endAngle);
			ellipses.add(e1);
			
			c = new Point3d(center.getX(),
							center.getY() + 
							radius * Math.sin(-deviationAngle),
							center.getZ());
			
			Ellipse e2 = new Ellipse(r, r, c, new Vector3d(1,0,0), 
					new Vector3d(0,0,1), startAngle, endAngle);
			ellipses.add(e2);			
		}		
			
		for (Ellipse e: ellipses) {
			for (Object a : e.getShapeList(canvas)){
			shapeList.add(a);
			}			
		}

		return shapeList;
	}
	
	public static void main (String [] args) {
		Globe t = new Globe();
		
		DrawWFApplet da = new DrawWFApplet(t);
		da.display();
	}
	
	static class GlobePanel extends ViewPanel {
		
		private Globe globeObject;	
		private JTextField radText, longText, latText;
		
		public double getR() { 
			return Double.parseDouble(radText.getText()); }
		public int getLongLines() { 
			return (int)Double.parseDouble(longText.getText()); }
		public int getLatLines() { 
			return (int)Double.parseDouble(latText.getText()); }
	
		public GlobePanel(Globe object) {
			
			globeObject = object;
			
			radText = new JTextField
					(String.valueOf(globeObject.radius));
			longText = new JTextField
					(String.valueOf(globeObject.linesOfLongitude));
			latText = new JTextField
					(String.valueOf(globeObject.linesOfLatitude));
			
			radText.addFocusListener(new FocusAdapter() {
			    public void focusLost(FocusEvent e) {
			        globeObject.radius = getR();
			        globeObject.repaint();
			    }
			});
			
			longText.addFocusListener(new FocusAdapter() {
			    public void focusLost(FocusEvent e) {
			        globeObject.linesOfLongitude = getLongLines();
			        globeObject.repaint();
			    }
			});
			
			latText.addFocusListener(new FocusAdapter() {
			    public void focusLost(FocusEvent e) {
			        globeObject.linesOfLatitude = getLatLines();
			        globeObject.repaint();
			    }
			});
			
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(5,200,5,10);
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.gridx = 0;
			c.gridy = 0;
			this.add(new JLabel
					("Radius: "), c);
			c.gridx = 0;
			c.gridy = 1;			
			this.add(new JLabel
					("Number of Longitudinal lines: "), c);
			c.gridx = 0;
			c.gridy = 2;
			this.add(new JLabel
					("Numer of Latitude lines: "), c);
			c.weightx = 0.5;
			c.insets = new Insets(5,10,5,200);
			c.gridx = 1;
			c.gridy = 0;
			this.add(radText, c);
			c.gridx = 1;
			c.gridy = 1;
			this.add(longText, c);
			c.gridx = 1;
			c.gridy = 2;
			this.add(latText, c);
		}			
	}		
}
