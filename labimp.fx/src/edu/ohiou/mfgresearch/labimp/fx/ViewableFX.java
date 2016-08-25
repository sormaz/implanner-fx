package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.scene.layout.Pane;

public interface ViewableFX {
	
	void init();
	
	void display();
	
	Pane getPanel();
	
	LinkedList<DrawableFX> getTargetList();
	
}
