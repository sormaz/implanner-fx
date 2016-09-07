package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.ViewObject;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class Swing3DConverter extends SwingConverter {
	
	protected ImpObject swingTarget;
	
	@Override
	public ViewObject getSwingTarget() {
		return swingTarget;
	}

	public Swing3DConverter(ImpObject swingTarget) {
		this.swingTarget = swingTarget;
	}
	
	public LinkedList<Shape> getFXShapes() {
		try {
			return getFXShapes(listeners.get(0));
		} catch (Exception e) {
			return new LinkedList<>();
		}
	}
	
	public LinkedList<Shape> getFXShapes(DrawListener listener) {
		if(!listeners.contains(listener)) return new LinkedList<Shape>();
		DrawWFPanel virtualPanel = listener.getVirtualPanel();
		virtualPanel.setTarget(swingTarget);
		Path swingPath = getFXShapes(swingTarget.getShapeList(virtualPanel));
		
		LinkedList<Shape> fxShapes = new LinkedList<Shape>();
		fxShapes.add(swingPath);
		return fxShapes;
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList getFXSelectables() {
		LinkedList swingSelectables = (LinkedList)swingTarget.getPointSet();	
		LinkedList selectables = new LinkedList<>();
		
		for(Object o: swingSelectables) {
			if (o instanceof java.awt.geom.Point2D) {
				selectables.add(new Point2D(((java.awt.geom.Point2D) o).getX(), 
											((java.awt.geom.Point2D) o).getY()));
			}
			if (o instanceof javax.vecmath.Point3d) {
				selectables.add(new Point3D(((javax.vecmath.Point3d) o).getX(), 
											((javax.vecmath.Point3d) o).getY(),
											((javax.vecmath.Point3d) o).getZ()));
			}
		}
		return selectables;
	}
	
	@Override
	public String toString() {
	    return name().get();
	}

}