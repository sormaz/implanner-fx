package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;

public class AxisFX extends FXObject {
	
	private double xLength, yLength, zLength;
	
	public AxisFX() {
		this(5, 6, 7);
	}

	public AxisFX(double xLength, double yLength, double zLength) {
		this(null, xLength, yLength, zLength);
	}
	
	public AxisFX(DrawFXCanvas parentContainer, 
			double xLength, double yLength, double zLength) {
		super(parentContainer);
		this.xLength = xLength;
		this.yLength = yLength;
		this.zLength = zLength;
	}
	
	public LinkedList<Shape> getFXShapes() {
		LinkedList<Shape> axes = new LinkedList<>();
		
		Line xAxis = new Line(0, 0, xLength, 0);
		Line yAxis = new Line(0, 0, 0, yLength);
		Line zAxis = new Line(0, 0, xLength, 0);
		
		zAxis.setRotationAxis(Rotate.Y_AXIS);
		zAxis.setRotate(90);
		
		xAxis.setStroke(Color.RED);
		yAxis.setStroke(Color.GREEN);
		zAxis.setStroke(Color.BLUE);
		
		axes.add(xAxis);
		axes.add(yAxis);
		axes.add(zAxis);
		
		return axes;
	}
	
//	public LinkedList<Shape3D> getFX3DShapes() {
//		
//		LinkedList<Shape3D> shapes = new LinkedList<>();
//		
//        Box xAxis = new Box(xLength, 1, 1);
//        Box yAxis = new Box(1, yLength, 1);
//        Box zAxis = new Box(1, 1, zLength);
//		
//		shapes.add(xAxis);
//		shapes.add(yAxis);
//		shapes.add(zAxis);
//		
//        final PhongMaterial redMaterial = new PhongMaterial();
//        redMaterial.setDiffuseColor(Color.DARKRED);
//        redMaterial.setSpecularColor(Color.RED);
//
//        final PhongMaterial greenMaterial = new PhongMaterial();
//        greenMaterial.setDiffuseColor(Color.DARKGREEN);
//        greenMaterial.setSpecularColor(Color.GREEN);
//
//        final PhongMaterial blueMaterial = new PhongMaterial();
//        blueMaterial.setDiffuseColor(Color.DARKBLUE);
//        blueMaterial.setSpecularColor(Color.BLUE);
//        
//        xAxis.setMaterial(redMaterial);
//        yAxis.setMaterial(greenMaterial);
//        zAxis.setMaterial(blueMaterial);
//
//		return shapes;
//	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AxisFX axes = new AxisFX();
		axes.display();
	}

}
