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
		this(null, swingTarget);
	}
	
	public Swing3DConverter(DrawFXCanvas parentContainer, 
					ImpObject swingTarget) {
		super(parentContainer);
		this.swingTarget = swingTarget;
	}
	
	public LinkedList<Shape> getFXShapes() {
		DrawWFPanel virtualPanel = parentContainer.getVirtualPanel();
		virtualPanel.setTarget(swingTarget);
		Path swingPath = getFXShapes(swingTarget.getShapeList(virtualPanel));
		
		LinkedList<Shape> fxShapes = new LinkedList();
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

}