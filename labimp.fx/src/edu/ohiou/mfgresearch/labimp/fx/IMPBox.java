package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFApplet;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import edu.ohiou.mfgresearch.labimp.gtk3d.Ellipse;
import edu.ohiou.mfgresearch.labimp.gtk3d.Line3d;
import edu.ohiou.mfgresearch.labimp.gtk3d.LineSegment;
import edu.ohiou.mfgresearch.labimp.gtk3d.WorldCS;

public class IMPBox extends ImpObject {
	
	private Point3d center = new Point3d(); 
	private double width;
	private double height;
	private double depth;
	
	public IMPBox() {
		this(3.0);	
	}
	
	public IMPBox(double cubeLength) {
		this(cubeLength, cubeLength, cubeLength);
	}
	
	public IMPBox(double width, double height, double depth) {
		this.width = width;
		this.height = height;	
		this.depth = depth;
	}
	
	public void init() {
		panel = new BoxPanel(this);
	}
	
	public LinkedList getPointSet() {
		LinkedList pointSet = new LinkedList<>();
		pointSet.add(center);
		return pointSet;
	}
	
	public LinkedList getShapeList(DrawWFPanel canvas) {
		LinkedList shapeList = new LinkedList();
		try {		
			LineSegment line1 =
					new LineSegment(center, 
							new Point3d(center.x + width, center.y, center.z));
			LineSegment line2 =
					new LineSegment(center, 
							new Point3d(center.x, center.y + height, center.z));
			LineSegment line3 =
					new LineSegment(new Point3d(center.x + width, center.y, center.z), 
							new Point3d(center.x + width, center.y + height, center.z));
			LineSegment line4 =
					new LineSegment(new Point3d(center.x, center.y + height, center.z), 
							new Point3d(center.x + width, center.y + height, center.z));

			LineSegment line5 =
					new LineSegment(new Point3d(center.x, center.y, center.z + depth), 
							new Point3d(center.x + width, center.y, center.z + depth));
			LineSegment line6 =
					new LineSegment(new Point3d(center.x, center.y, center.z + depth), 
							new Point3d(center.x, center.y + height, center.z + depth));
			LineSegment line7 =
					new LineSegment(new Point3d(center.x + width, center.y, center.z + depth), 
							new Point3d(center.x + width, center.y + height, center.z + depth));
			LineSegment line8 =
					new LineSegment(new Point3d(center.x, center.y + height, center.z + depth), 
							new Point3d(center.x + width, center.y + height, center.z + depth));
			
			LineSegment line9 =
					new LineSegment(center, 
							new Point3d(center.x, center.y, center.z + depth));
			LineSegment line10 =
					new LineSegment(new Point3d(center.x, center.y + height, center.z), 
							new Point3d(center.x, center.y + height, center.z + depth));
			LineSegment line11 =
					new LineSegment(new Point3d(center.x + width, center.y, center.z), 
							new Point3d(center.x + width, center.y, center.z + depth));
			LineSegment line12 =
					new LineSegment(new Point3d(center.x + width, center.y + height, center.z), 
							new Point3d(center.x + width, center.y + height, center.z + depth));

			shapeList.addAll(line1.getShapeList(canvas));
			shapeList.addAll(line2.getShapeList(canvas));
			shapeList.addAll(line3.getShapeList(canvas));
			shapeList.addAll(line4.getShapeList(canvas));
			shapeList.addAll(line5.getShapeList(canvas));
			shapeList.addAll(line6.getShapeList(canvas));
			shapeList.addAll(line7.getShapeList(canvas));
			shapeList.addAll(line8.getShapeList(canvas));
			shapeList.addAll(line9.getShapeList(canvas));
			shapeList.addAll(line10.getShapeList(canvas));
			shapeList.addAll(line11.getShapeList(canvas));
			shapeList.addAll(line12.getShapeList(canvas));
		} catch (Exception e) {
			return new LinkedList<>();
		}


		return shapeList;
	}
	
	public static void main (String [] args) {
		IMPBox t = new IMPBox();
		
		DrawWFApplet da = new DrawWFApplet(t);
		da.display();
	}
	
	static class BoxPanel extends ViewPanel {
		
		private JTextField widthText, heigthText, depthText;
		
		public double getBoxWidth() { 
			return Double.parseDouble(widthText.getText()); }
		public double getBoxHeight() { 
			return Double.parseDouble(heigthText.getText()); }
		public double getBoxDepth() { 
			return Double.parseDouble(depthText.getText()); }
	
		public BoxPanel(IMPBox box) {
			
			this.object = box;
			
			widthText = new JTextField
					(String.valueOf(box.width));
			heigthText = new JTextField
					(String.valueOf(box.height));
			depthText = new JTextField
					(String.valueOf(box.depth));
			
			widthText.addFocusListener(new FocusAdapter() {
			    public void focusLost(FocusEvent e) {
			    	box.width = getBoxWidth();
			    	box.repaint();
			    }
			});
			
			heigthText.addFocusListener(new FocusAdapter() {
			    public void focusLost(FocusEvent e) {
			    	box.height = getBoxHeight();
			    	box.repaint();
			    }
			});
			
			depthText.addFocusListener(new FocusAdapter() {
			    public void focusLost(FocusEvent e) {
			    	box.depth = getBoxDepth();
			    	box.repaint();
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
					("Width: "), c);
			c.gridx = 0;
			c.gridy = 1;			
			this.add(new JLabel
					("Height: "), c);
			c.gridx = 0;
			c.gridy = 2;
			this.add(new JLabel
					("Depth: "), c);
			c.weightx = 0.5;
			c.insets = new Insets(5,10,5,200);
			c.gridx = 1;
			c.gridy = 0;
			this.add(widthText, c);
			c.gridx = 1;
			c.gridy = 1;
			this.add(heigthText, c);
			c.gridx = 1;
			c.gridy = 2;
			this.add(depthText, c);
		}			
	}		
}
