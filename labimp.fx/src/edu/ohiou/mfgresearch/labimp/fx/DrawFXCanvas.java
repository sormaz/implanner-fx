package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.Dimension;
import java.util.LinkedList;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFApplet;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawableWF;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class DrawFXCanvas extends VBox {	

	static final double DEFAULT_WIDTH = 650;
	static final double DEFAULT_HEIGHT = 550;
	
	private ObservableList<DrawableFX> targetList = FXCollections.observableArrayList();
	private DrawableFX activeTarget;
	private Point3D viewpoint;
	private double scale;
	private boolean showWCS;
	private Group targetGroup = new Group(); 
	private Group swing2DGroup = new Group(); 
	private Group swing3DGroup = new Group(); 
	private Group fx2DGroup = new Group(); 
	private Group fx3DGroup = new Group();
	private SubScene fx3DScene = new SubScene(fx3DGroup, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	private Pane canvas = new Pane(targetGroup);
	private final DrawWFPanel virtualPanel = new DrawWFPanel();

	public DrawFXCanvas() {
		this(FXCollections.observableArrayList());
	}

	public DrawFXCanvas(ObservableList<DrawableFX> targetList) {
		this(targetList, new Point3D(0, 0, 20), 50, false);
	}

	public DrawFXCanvas(ObservableList<DrawableFX> targetList, 
			Point3D viewpoint, double scale, boolean showWCS) {
		setTargetList(targetList);
		this.viewpoint = viewpoint;
		setScale(scale);
		this.showWCS = showWCS;
		init();			
	}
	
	public ObservableList<DrawableFX> getTargetList() {
		return targetList;
	}

	public void setTargetList(ObservableList<DrawableFX> targetList) {
		this.targetList = targetList;
		for(DrawableFX target: targetList) {
			((FXObject)target).setParentContainer(this);
		}
		updateView();
	}
	
	public void addTarget(DrawableFX target) {
		targetList.add(target);
		((FXObject)target).setParentContainer(this);
		updateView();
	}

	public DrawableFX getActiveTarget() {
		return activeTarget;
	}

	public void setActiveTarget(DrawableFX activeTarget) {
		
		if(targetList.contains(activeTarget)) {
			this.activeTarget = activeTarget;
		}
		
		if(!activeTarget.getVisible().get()) 
			activeTarget.changeVisibility();
		
		if(activeTarget instanceof Swing3DConverter) {
			virtualPanel.setTarget
			((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
			virtualPanel.createTargetTable();
		} else {
			virtualPanel.setTarget(null);
		}
		
		updateView();
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
		virtualPanel.setView(scale);
	}

	public Pane getCanvas() {
		return canvas;
	}

	public DrawWFPanel getVirtualPanel() {
		return virtualPanel;
	}

	private void init() {
		
		canvas.setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Rectangle clipRect = new Rectangle(canvas.getWidth(), canvas.getHeight());
		canvas.setClip(clipRect);
		
		clipRect.heightProperty().bind(canvas.heightProperty());
		clipRect.widthProperty().bind(canvas.widthProperty());
		
		fx3DScene.heightProperty().bind(canvas.heightProperty());
		fx3DScene.widthProperty().bind(canvas.widthProperty());
		
		Pane canvasControls = getToolbar();
		canvasControls.setPrefWidth(DEFAULT_WIDTH);
		
//		canvas.setStyle("-fx-border-width: 1; "
//				+ "-fx-border-style: solid;"
//				+ "-fx-border-color:black;");
		
//		canvasControls.setStyle("-fx-border-width: 1; "
//								+ "-fx-border-style: solid;"
//								+ "-fx-border-color:black;");
						
		VBox.setVgrow(canvas, Priority.ALWAYS);
		getChildren().addAll(canvas, canvasControls);
		
		virtualPanel.getDrawPanel().setSize
		(new Dimension((int)DEFAULT_WIDTH, (int)DEFAULT_HEIGHT));

		virtualPanel.setView(scale, viewpoint.getX(), 
				viewpoint.getY(), viewpoint.getZ());
		
        canvas.widthProperty().addListener(evt -> {
        	virtualPanel.getDrawPanel()
        		.setSize((int)canvas.getWidth(), (int)canvas.getHeight());
        	updateView();
        });
        canvas.heightProperty().addListener(evt -> {
        	virtualPanel.getDrawPanel()
        		.setSize((int)canvas.getWidth(), (int)canvas.getHeight());   	
        	updateView();
        });
		

		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
//				System.out.println("fx: Mouse Clicked " + e.getX() + ", " + e.getY());
				
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
//				System.out.println("fx: Mouse Pressed " + e.getX() + ", " + e.getY());
				
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
//				System.out.println("fx: Mouse Moved " + e.getX() + ", " + e.getY());
				
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
//				System.out.println("fx: Mouse Dragged " + e.getX() + ", " + e.getY());
				
				if(activeTarget instanceof Swing3DConverter) {
					virtualPanel.setTarget
					((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
				}
				
				((DrawWFPanel)virtualPanel.gettCanvas()).
				mouseDragged((int)e.getX(), (int)e.getY());	
				updateView();				
			}
		});
		
	    setOnScroll((e) -> {
	
	    	double zoomRatio = 1;
	    	if(e.getDeltaY() > 0) {
	    		zoomRatio = 1.05;
	    	} else {
	    		zoomRatio = 0.95;
	    	}
	    	
	    	setScale(scale * zoomRatio);
	    	
	    	updateView();
	    	
	    });

		updateView();
	}
	
	public void updateView() {

		targetGroup.getChildren().clear();
		swing2DGroup.getChildren().clear();
		swing3DGroup.getChildren().clear();
		fx2DGroup.getChildren().clear();
		fx3DGroup.getChildren().clear();
		
//		targetGroup.getTransforms().clear();
		swing2DGroup.getTransforms().clear();
		fx2DGroup.getTransforms().clear();
		fx3DGroup.getTransforms().clear();
		
		targetList.stream()
				.filter((t) -> t.getVisible().get()) 
				.forEach((target) -> { 
			
			if(target instanceof Swing2DConverter) {
				
				for (Shape s: target.getFXShapes()) {
					s.setStrokeWidth(1/scale);
					
					swing2DGroup.getChildren().add(s);
				}							
			}else if (target instanceof Swing3DConverter) {
				
				for (Shape s: target.getFXShapes()) {
					swing3DGroup.getChildren().add(s);
				}
				
			} else {
				fx2DGroup.getChildren().addAll(target.getFXShapes());	
				fx3DGroup.getChildren().addAll(target.getFX3DShapes());		
			} 
		});
		
		if(showWCS) {
			AxisFX axes = new AxisFX();
			axes.getFXShapes().stream()
				.forEach((axis) -> {
					axis.setStrokeWidth(1/scale);
					fx2DGroup.getChildren().add(axis);
				});
		}
			
		targetGroup.getChildren().add(fx3DScene);
		targetGroup.getChildren().add(swing2DGroup);
		targetGroup.getChildren().add(swing3DGroup);
		targetGroup.getChildren().add(fx2DGroup);

	
		Scale scale2D = new Scale(scale, scale);
		Scale scale3D = new Scale(scale, scale, scale);
		
		Affine mirror 
		= new Affine(1, 0, 0, 0, -1, canvas.getHeight());
		
		Translate moveToCenter = new Translate(canvas.getWidth() / 2,
												- canvas.getHeight() / 2);
		
		swing2DGroup.getTransforms().add(moveToCenter);
		swing2DGroup.getTransforms().add(mirror);
		swing2DGroup.getTransforms().add(scale2D);	

		fx2DGroup.getTransforms().add(moveToCenter);
		fx2DGroup.getTransforms().add(mirror);
		fx2DGroup.getTransforms().add(scale2D);
		
		fx3DGroup.getTransforms().add(moveToCenter);
		fx3DGroup.getTransforms().add(mirror);
		fx3DGroup.getTransforms().add(scale3D);
		
		if(activeTarget instanceof Swing3DConverter) {
			virtualPanel.setTarget
			((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
		}
	}

	private Pane getToolbar() {
		GridPane canvasControls = new GridPane();
		canvasControls.setAlignment(Pos.CENTER);
		
		Label viewLbl = new Label("View Point :");
		GridPane.setHalignment(viewLbl, HPos.CENTER);
		viewLbl.setPadding(new Insets(4, 4, 4, 4));
		TextField xTxt = new TextField();
		xTxt.setText(String.valueOf(viewpoint.getX()));
		TextField yTxt = new TextField();
		yTxt.setText(String.valueOf(viewpoint.getY()));
		TextField zTxt = new TextField();
		zTxt.setText(String.valueOf(viewpoint.getZ()));
		Label scaleLbl = new Label("Scale :");
		GridPane.setHalignment(scaleLbl, HPos.CENTER);
		scaleLbl.setPadding(new Insets(4, 4, 4, 4));
		TextField scaleTxt = new TextField();	
		scaleTxt.setText(String.valueOf(scale));
		scaleTxt.focusedProperty().addListener(new ChangeListener<Boolean>()
		{		
			double oldValue;
			
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
					oldValue = Double.valueOf(scaleTxt.getText());
		        }
		        else
		        {
		        	try {
						setScale(Double.valueOf(scaleTxt.getText()));
						updateView();
					} catch (Exception e) {
						scaleTxt.setText(String.valueOf(oldValue));
						setScale(Double.valueOf(scaleTxt.getText()));
					}				
		        }
		    }
		});

		CheckBox wcsCheck = new CheckBox("Show WorldCS");
		GridPane.setHalignment(wcsCheck, HPos.CENTER);
		wcsCheck.setPadding(new Insets(4, 4, 4, 4));
		wcsCheck.setOnAction((e) -> {
			showWCS = wcsCheck.isSelected();
			updateView();
		});
		Button redisplayBtn = new Button("ReDisplay");
		redisplayBtn.setPrefWidth(Double.MAX_VALUE);
		redisplayBtn.setOnAction((e) -> updateView());

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

}
