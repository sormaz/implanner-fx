package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.Dimension;
import java.util.LinkedList;

import edu.ohiou.labimp.test.Globe;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFApplet;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawableWF;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

public class DrawFXCanvas extends VBox {	

	private LinkedList<DrawableFX> targetList = new LinkedList<>();
	private DrawableFX activeTarget;
	private Point3D viewpoint;
	private double scale;
	private boolean showWCS;
	private Group targetGroup = new Group(); 
	private Group swing2DGroup = new Group(); 
	private Group swing3DGroup = new Group(); 
	private Group fxGroup = new Group(); 
	private Pane canvas = new Pane(targetGroup);
	private DrawWFPanel virtualPanel = new DrawWFPanel();;
	static double defaultWidth = 650;
	static double defaultHeight = 550;

	public LinkedList<DrawableFX> getTargetList() {
		return targetList;
	}

	public void setTargetList(LinkedList<DrawableFX> targetList) {
		this.targetList = targetList;
		for(DrawableFX target: targetList) {
			((FXObject)target).setParentContainer(this);
		}
	}

	public DrawableFX getActiveTarget() {
		return activeTarget;
	}

	public void setActiveTarget(DrawableFX activeTarget) {
		
		if(targetList.contains(activeTarget)) {
			this.activeTarget = activeTarget;
		}
	}

	public Point3D getViewpoint() {
		return viewpoint;
	}

	public void setViewpoint(Point3D viewpoint) {
		this.viewpoint = viewpoint;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Pane getCanvas() {
		return canvas;
	}

	public DrawWFPanel getVirtualPanel() {
		return virtualPanel;
	}

	public DrawFXCanvas() {
		this(new LinkedList<>());
	}

	public DrawFXCanvas(LinkedList<DrawableFX> targetList) {
		this(targetList, new Point3D(0, 0, 20), 50, false);
	}

	public DrawFXCanvas(LinkedList<DrawableFX> targetList, 
			Point3D viewpoint, double scale, boolean showWCS) {
		setTargetList(targetList);
		this.viewpoint = viewpoint;
		this.scale = scale;
		this.showWCS = showWCS;
		init();	
		
//        canvas.widthProperty().addListener(evt -> {
//        	virtualPanel.setSize((int)canvas.getWidth(), virtualPanel.getHeight());
//        	virtualPanel.repaint();
//        	updateView();
//        });
//        canvas.heightProperty().addListener(evt -> {
//        	virtualPanel.setSize(virtualPanel.getWidth(), (int)canvas.getHeight());
//        	virtualPanel.repaint();
//        	updateView();
//        });
	}

	private void init() {
		canvas.setPrefSize(defaultWidth, defaultHeight);
		Pane canvasControls = getToolbar();
		canvasControls.setPrefWidth(defaultWidth);
		VBox.setVgrow(canvas, Priority.ALWAYS);
		getChildren().addAll(canvas, canvasControls);
		
		virtualPanel.getDrawPanel().setSize
		(new Dimension((int)defaultWidth, (int)defaultHeight));

		//		System.out.println(defaultWidth + " " + defaultHeight);
		//		System.out.println(getWidth() + " " + getHeight());

		virtualPanel.setView(scale, viewpoint.getX(), 
				viewpoint.getY(), viewpoint.getZ());



		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Clicked " + e.getX() + ", " + e.getY());
				
				if(activeTarget instanceof Swing3DConverter) {
					virtualPanel.setTarget
					((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
				}
					
				((DrawWFPanel)virtualPanel.gettCanvas()).
				mouseClicked((int)e.getX(), (int)e.getY());
				updateView();

			}
		});

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Pressed " + e.getX() + ", " + e.getY());
				
				if(activeTarget instanceof Swing3DConverter) {
					virtualPanel.setTarget
							((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
				}	
				
				((DrawWFPanel)virtualPanel.gettCanvas()).
				mousePressed((int)e.getX(), (int)e.getY());	
				updateView();

			}
		});

		setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Moved " + e.getX() + ", " + e.getY());
				
				if(activeTarget instanceof Swing3DConverter) {
					virtualPanel.setTarget
					((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
					
					((DrawWFPanel)virtualPanel.gettCanvas()).
					mouseMoved((int)e.getX(), (int)e.getY());

					if (((DrawWFPanel)virtualPanel.gettCanvas()).mouseMode 
										== DrawWFPanel.MODIFY_TARGET) {
						getScene().setCursor(Cursor.CROSSHAIR);
					} else {
						getScene().setCursor(Cursor.DEFAULT);
					}		
					
				} else {
					
				}		
				
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fx: Mouse Dragged " + e.getX() + ", " + e.getY());
				
				if(activeTarget instanceof Swing3DConverter) {
					virtualPanel.setTarget
					((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
				}
				
				((DrawWFPanel)virtualPanel.gettCanvas()).
				mouseDragged((int)e.getX(), (int)e.getY());	
				updateView();				
			}
		});

		updateView();
	}

	public void updateView() {

		targetGroup.getChildren().clear();
		swing2DGroup.getChildren().clear();
		swing3DGroup.getChildren().clear();
		fxGroup.getChildren().clear();
		
//		targetGroup.getTransforms().clear();
		swing2DGroup.getTransforms().clear();
//		swing3DGroup.getTransforms().clear();
		fxGroup.getTransforms().clear();
		
		for(DrawableFX target: targetList) {
			
			if(target instanceof Swing2DConverter) {
				
				for (Object o: target.getFXShapes()) {
					Shape s = (Shape)o;
					s.setStrokeWidth(1/scale);
					
					System.out.println(s.toString());
					System.out.println(swing2DGroup.getChildren().toString());
					
					swing2DGroup.getChildren().add(s);
				}							
			}else if (target instanceof Swing3DConverter) {
				
				for (Object o: target.getFXShapes()) {
					Shape s = (Shape)o;
					swing3DGroup.getChildren().add(s);
				}
				
			} else {
				fxGroup.getChildren().addAll(target.getFXShapes());
				fxGroup.getChildren().addAll(target.getFX3DShapes());
			}
		}
		
		Scale s = new Scale(scale, scale);
		swing2DGroup.getTransforms().add(s);
		
		Scale s2 = new Scale(scale, scale, scale);
		fxGroup.getTransforms().add(s);
			
		targetGroup.getChildren().add(swing2DGroup);
		targetGroup.getChildren().add(swing3DGroup);
		targetGroup.getChildren().add(fxGroup);
		
		if(activeTarget instanceof Swing3DConverter) {
			virtualPanel.setTarget
			((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
		}

	}

	private Pane getToolbar() {
		GridPane canvasControls = new GridPane();
		canvasControls.setAlignment(Pos.CENTER);

		Label viewLbl = new Label("View Point :");
		viewLbl.setPadding(new Insets(4, 4, 4, 4));
		TextField xTxt = new TextField();
		TextField yTxt = new TextField();
		TextField zTxt = new TextField();
		Label scaleLbl = new Label("Scale :");
		scaleLbl.setPadding(new Insets(4, 4, 4, 4));
		TextField scaleTxt = new TextField();	
		CheckBox wcsCheck = new CheckBox("Show WorldCS");
		wcsCheck.setPadding(new Insets(4, 4, 4, 4));
		Button redisplayBtn = new Button("ReDisplay");
		redisplayBtn.setPrefWidth(Double.MAX_VALUE);

		canvasControls.add(viewLbl, 0, 0, 2, 1);
		canvasControls.add(xTxt, 2, 0, 1, 1);
		canvasControls.add(yTxt, 3, 0, 1, 1);
		canvasControls.add(zTxt, 4, 0, 1, 1);
		canvasControls.add(scaleLbl, 5, 0, 1, 1);
		canvasControls.add(scaleTxt, 6, 0, 1, 1);
		canvasControls.add(wcsCheck, 7, 0, 3, 1);
		canvasControls.add(redisplayBtn, 10, 0, 2, 1);

		for (int i = 0; 
				i < GridPane.getColumnIndex(redisplayBtn) + 
				GridPane.getColumnSpan(redisplayBtn) ; 
				i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPrefWidth(100);
			canvasControls.getColumnConstraints().add(column);
		}

		RowConstraints row = new RowConstraints();
		row.setPrefHeight(30);
		canvasControls.getRowConstraints().add(row);

		return canvasControls;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
