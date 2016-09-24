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
	public static int lineThickness = 1;
	
	static {
		String thickness = getProperties().getProperty("lineThickness", "2");
		lineThickness = Integer.parseInt(thickness);
	}
	
	public AxisFX() {
		this(5, 6, 7);
	}

	public AxisFX(double xLength, double yLength, double zLength) {
		this(null, xLength, yLength, zLength);
	}
	
	public AxisFX(DrawFXCanvas parentContainer, 
			double xLength, double yLength, double zLength) {
		listeners.add(parentContainer);
		this.xLength = xLength;
		this.yLength = yLength;
		this.zLength = zLength;
	}
	
	public LinkedList<Shape3D> getFX3DShapes() {
		
		LinkedList<Shape3D> shapes = new LinkedList<>();
		
        Box xAxis = new Box(xLength, 1, 1);
        Box yAxis = new Box(1, yLength, 1);
        Box zAxis = new Box(1, 1, zLength);
		
        xAxis.setTranslateX(xLength / 2);
        yAxis.setTranslateY(yLength / 2);
        zAxis.setTranslateZ(zLength / 2);
        
		shapes.add(xAxis);
		shapes.add(yAxis);
		shapes.add(zAxis);
		
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(blueMaterial);
        zAxis.setMaterial(greenMaterial);

		return shapes;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AxisFX axes = new AxisFX();
		axes.display();
	}

}
