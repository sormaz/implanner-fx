package edu.ohiou.mfgresearch.labimp.fx.test;

import java.util.LinkedList;

import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import edu.ohiou.mfgresearch.labimp.fx.DrawFXCanvas;
import edu.ohiou.mfgresearch.labimp.fx.FXObject;

public class TestFXObject extends FXObject {
	
	//Arif Ahmed - Reference to parentContainer has been
	//removed from FXObject
//	public TestFXObject () {
//		this (new DrawFXCanvas());
//	}
//	public TestFXObject (DrawFXCanvas parentContainer) {
//		super(parentContainer);
//		
//	}
	
	public LinkedList<Shape3D> getFX3DShapes() {

		LinkedList<Shape3D> list = new  LinkedList<>();
		list.add(new Box(1,2,3));
		list.add(new Sphere(2));
		list.add(new Cylinder(1, 5));
		return list;
	}


	public LinkedList<Text> getFXStringList() {
		// TODO Auto-generated method stub
		LinkedList<Text> list = new LinkedList<Text>();
		list.add (new Text("DN Sormaz"));
		 return	list;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestFXObject tfxo = new TestFXObject();
		tfxo.display();
	}

}
