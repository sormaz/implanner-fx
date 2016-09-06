package edu.ohiou.mfgresearch.labimp.fx;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import javafx.scene.layout.Pane;

public interface DrawListener {
	
	public void display();
	
	public void updateView();
	
	public void addTarget(DrawableFX target);
	
	public DrawWFPanel getVirtualPanel();
	
	public Pane getView();

}
