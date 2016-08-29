package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.ohiou.mfgresearch.labimp.basis.DrawString;
import edu.ohiou.mfgresearch.labimp.basis.ViewObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

public abstract class SwingConverter extends FXObject {

	public SwingConverter(DrawFXCanvas parentContainer) {
		super(parentContainer);
	}
	
	public abstract ViewObject getSwingTarget();
	
	public StringProperty name() {
		return new SimpleStringProperty
				(getSwingTarget().getClass().getSimpleName().toString());
	}
	
//	@Override
//	public void init() {
//		// TODO Auto-generated method stub
//
//		try {
//			getSwingTarget().init();
//			SwingNode swingPanel = new SwingNode();
//			swingPanel.setContent(getSwingTarget().gettPanel());
//			viewPanel = new Pane(swingPanel);
//		} catch (Exception e) {
//			super.init();
//		}
//		
//
//	}
//	
	@Override
	public Pane getPanel() {
		
		StackPane viewPanel = new StackPane();
		
		if(getSwingTarget().gettPanel() == null) {
			getSwingTarget().init();
		}

		JPanel jp = getSwingTarget().gettPanel();
		SwingNode n = new SwingNode();
		n.setContent(jp);

		viewPanel.getChildren().add(new StackPane(n));
		
		return viewPanel;
		
	}
	
	public LinkedList<Text> getFXStringList() {
		LinkedList<DrawString> swingStrings = getSwingTarget().getStringList();

		LinkedList<Text> fxStrings = new LinkedList<>();
		
		for(DrawString ds: swingStrings) {
			Text fxText = new Text();
			fxText.setText(ds.getContent());
			fxText.setX(ds.gettPosition().getX());
			fxText.setY(ds.gettPosition().getY());
			
			if(!Double.isNaN(ds.getSize())) {
				fxText.setScaleX(ds.getSize());
				fxText.setScaleY(ds.getSize());
			}	
			fxStrings.add(fxText);
			
		}
		return fxStrings;
	}

	public Path getFXShapes(LinkedList swingShapes) {
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
						
						double x, y;

							x = d[1];
							y = d[2];

						MoveTo moveTo = new MoveTo();
						moveTo.setX(x);
						moveTo.setY(y);  
						sfx.getElements().add(moveTo);

					} else if (d[0] == PathIterator.SEG_LINETO) {
						
						double x, y;
						
							x = d[1];
							y = d[2];

						LineTo lineTo = new LineTo();
						lineTo.setX(x);
						lineTo.setY(y);  
						sfx.getElements().add(lineTo);

					} else if (d[0] == PathIterator.SEG_CUBICTO) {
						
						double x1, y1, x2, y2, x3, y3;

							x1 = d[1];
							y1 = d[2];
							x2 = d[3];
							y2 = d[4];
							x3 = d[5];
							y3 = d[6];

						CubicCurveTo ccTo = new CubicCurveTo(x1, y1, x2, y2, x3, y3); 
						sfx.getElements().add(ccTo);
						
					} else if (d[0] == PathIterator.SEG_QUADTO) {	
						
						double x1, y1, x2, y2;
							x1 = d[1];
							y1 = d[2];
							x2 = d[3];
							y2 = d[4];
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
