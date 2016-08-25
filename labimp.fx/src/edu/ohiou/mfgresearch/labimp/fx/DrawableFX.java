package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public interface DrawableFX {
	
	LinkedList<Shape> getFXShapes();
	
	LinkedList<Shape> getFXFillShapes();
	
	LinkedList<Text> getFXStringList();
	
	@SuppressWarnings("rawtypes")
	LinkedList getFXSelectables();
	
}
