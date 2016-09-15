package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.scene.shape.Shape3D;

public class Shape3DObject extends FXObject {

	Shape3D shape3d;
	
	public Shape3DObject(Shape3D shape3d) {
		this.shape3d = shape3d;		
		defaultColor = "grey";
		defaultFillColor ="grey";
	}
	
	@Override
	public LinkedList<Shape3D> getFX3DShapes() {
		
		LinkedList<Shape3D> fxShapes = new LinkedList<>();
		fxShapes.add(shape3d);
		return fxShapes;
	}

}
