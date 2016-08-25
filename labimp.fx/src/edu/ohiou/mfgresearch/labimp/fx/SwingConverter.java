package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

public class SwingConverter extends FXObject {

	public SwingConverter(DrawFXCanvas parentContainer) {
		super(parentContainer);
		// TODO Auto-generated constructor stub
	}

	public static Path getFXShapes(LinkedList swingShapes) {
		// TODO Auto-generated method stub
		Path sfx = new Path();
		
		for(Object s: swingShapes) {
			java.awt.Shape ss = (java.awt.Shape)s;
			if (ss instanceof java.awt.Shape) {
				double[] coords = new double[6];
				ArrayList<double[]> areaPoints = new ArrayList<double[]>();

				for (PathIterator pi = ss.getPathIterator(null); !pi.isDone(); pi.next()) {
					int type = pi.currentSegment(coords);

					double[] pathIteratorCoords = {type, coords[0], coords[1], 
							coords[2], coords[3], coords[4], coords[5]};
					areaPoints.add(pathIteratorCoords);
				}

				for(double[] d: areaPoints) {
					if(d[0] == PathIterator.SEG_MOVETO) {

						MoveTo moveTo = new MoveTo();
						moveTo.setX(d[1]);
						moveTo.setY(d[2]);  
						sfx.getElements().add(moveTo);

					} else if (d[0] == PathIterator.SEG_LINETO) {

						LineTo lineTo = new LineTo();
						lineTo.setX(d[1]);
						lineTo.setY(d[2]);  
						sfx.getElements().add(lineTo);

					} else if (d[0] == PathIterator.SEG_CUBICTO) {

						CubicCurveTo ccTo = new CubicCurveTo(d[1], d[2], d[3], d[4], d[5], d[6]); 
						sfx.getElements().add(ccTo);

					} else if (d[0] == PathIterator.SEG_QUADTO) {

						QuadCurveTo qcTo = new QuadCurveTo(d[1], d[2], d[3], d[4]);
						sfx.getElements().add(qcTo);

					} else if (d[0] == PathIterator.SEG_CLOSE) {

						ClosePath cp = new ClosePath();
						sfx.getElements().add(cp);

					}
				}
			}
		}
		return sfx;
	}

}
