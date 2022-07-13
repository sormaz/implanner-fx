package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.ViewObject;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class Swing2DConverter extends SwingConverter {

	ViewObject swingTarget;
	
	@Override
	public ViewObject getSwingTarget() {
		// TODO Auto-generated method stub
		return swingTarget;
	}

	public Swing2DConverter(ViewObject swingTarget) {
		this.swingTarget = swingTarget;
	}
	
	public LinkedList<Shape> getFXShapes() {
		Path swingPath = getFXShapes(swingTarget.geetDrawList());
		
		LinkedList<Shape> fxShapes = new LinkedList();
		fxShapes.add(swingPath);
		
		return fxShapes;
	}
	
	public LinkedList<Shape> getFXFillShapes() {
		Path swingPath = getFXShapes(swingTarget.geetFillList());
		
		LinkedList<Shape> fxShapes = new LinkedList<>();
		fxShapes.add(swingPath);
		return fxShapes;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList getFXSelectables() {
		LinkedList swingSelectables = (LinkedList)swingTarget.giveSelectables();	
		LinkedList selectables = new LinkedList<>();
		
		for(Object o: swingSelectables) {
			if (o instanceof java.awt.geom.Point2D) {
				selectables.add(new Point2D(((java.awt.geom.Point2D) o).getX(), 
											((java.awt.geom.Point2D) o).getY()));
			}
		}
		return selectables;
	}

}
