package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

public class SphereFX extends FXObject {
	
	private double radius;
	
	public SphereFX() {
		this(5);
	}
	
	public SphereFX(double radius) {
		this(radius, null);
	}

	public SphereFX(double radius, DrawFXCanvas parentContainer) {
		super(parentContainer);
		setRadius(radius);
	}
		
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public LinkedList<Shape3D> getFX3DShapes() {
		// TODO Auto-generated method stub
		LinkedList<Shape3D> fxShape = new LinkedList();
		Sphere sphere = new Sphere(radius);
		fxShape.add(sphere);
		return fxShape;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SphereFX th = new SphereFX(7);
		th.display();
	}

}
