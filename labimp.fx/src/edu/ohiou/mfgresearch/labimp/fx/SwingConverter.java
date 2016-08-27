package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.basis.ViewObject;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.transform.Scale;

public abstract class SwingConverter extends FXObject {

	public final DimensionSize dimensionSize;
	
	public enum DimensionSize {
		twoD, threeD
	}
	
	public SwingConverter(DrawFXCanvas parentContainer, DimensionSize dimensionSize) {
		super(parentContainer);
		this.dimensionSize = dimensionSize;
	}
	
	public abstract ViewObject getSwingTarget();
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

		try {
			getSwingTarget().init();
			SwingNode swingPanel = new SwingNode();
			swingPanel.setContent(getSwingTarget().gettPanel());
			viewPanel = new Pane(swingPanel);
		} catch (Exception e) {
			super.init();
		}
		

	}

	public Path getFXShapes(LinkedList swingShapes) {
		// TODO Auto-generated method stub
		Path sfx = new Path();
		
//		if(dimensionSize == DimensionSize.twoD) {
//			double scale = parentContainer.getScale();
//			sfx.setStrokeWidth(1/scale);
//		}
		
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
						
						double x, y;
						
//						if(dimensionSize==DimensionSize.threeD) {
							x = d[1];
							y = d[2];
//						} else {
//							Point2D p = new Point2D(d[1], d[2]);
//							Scale scale = Scale.scale(parentContainer.getScale(), 
//									parentContainer.getScale());	
//							
//							p = scale.deltaTransform(p);
//							
//							x = p.getX();
//							y = p.getY();
//						}

						MoveTo moveTo = new MoveTo();
						moveTo.setX(x);
						moveTo.setY(y);  
						sfx.getElements().add(moveTo);

					} else if (d[0] == PathIterator.SEG_LINETO) {
						
						double x, y;
						
//						if(dimensionSize==DimensionSize.threeD) {
							x = d[1];
							y = d[2];
//						} else {
//							Point2D p = new Point2D(d[1], d[2]);
//							Scale scale = Scale.scale(parentContainer.getScale(), 
//									parentContainer.getScale());	
//							
//							p = scale.deltaTransform(p);
//							
//							x = p.getX();
//							y = p.getY();
//						}

						LineTo lineTo = new LineTo();
						lineTo.setX(x);
						lineTo.setY(y);  
						sfx.getElements().add(lineTo);

					} else if (d[0] == PathIterator.SEG_CUBICTO) {
						
						double x1, y1, x2, y2, x3, y3;
						
//						if(dimensionSize==DimensionSize.threeD) {
							x1 = d[1];
							y1 = d[2];
							x2 = d[3];
							y2 = d[4];
							x3 = d[5];
							y3 = d[6];
//						} else {
//							Scale scale = Scale.scale(parentContainer.getScale(), 
//									parentContainer.getScale());	
//							
//							Point2D p = new Point2D(d[1], d[2]);
//							p = scale.deltaTransform(p);
//							
//							x1 = p.getX();
//							y1 = p.getY();
//							
//							p = new Point2D(d[3], d[4]);					
//							p = scale.deltaTransform(p);
//							
//							x2 = p.getX();
//							y2 = p.getY();
//							
//							p = new Point2D(d[5], d[6]);						
//							p = scale.deltaTransform(p);
//							
//							x3 = p.getX();
//							y3 = p.getY();
//						}

						CubicCurveTo ccTo = new CubicCurveTo(x1, y1, x2, y2, x3, y3); 
						sfx.getElements().add(ccTo);

					} else if (d[0] == PathIterator.SEG_QUADTO) {
						
						double x1, y1, x2, y2;
						
//						if(dimensionSize==DimensionSize.threeD) {
							x1 = d[1];
							y1 = d[2];
							x2 = d[3];
							y2 = d[4];
//						} else {
//							Scale scale = Scale.scale(parentContainer.getScale(), 
//									parentContainer.getScale());	
//							
//							Point2D p = new Point2D(d[1], d[2]);
//							p = scale.deltaTransform(p);
//							
//							x1 = p.getX();
//							y1 = p.getY();
//							
//							p = new Point2D(d[3], d[4]);					
//							p = scale.deltaTransform(p);
//							
//							x2 = p.getX();
//							y2 = p.getY();							
//						}

						QuadCurveTo qcTo = new QuadCurveTo(x1, y1, x2, y2);
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
