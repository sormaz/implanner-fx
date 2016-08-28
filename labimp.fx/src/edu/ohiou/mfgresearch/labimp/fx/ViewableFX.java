package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public interface ViewableFX {
	
//	void init();
	
	void display();
	

	
	ObservableList<DrawableFX> getTargetList();
	
}
