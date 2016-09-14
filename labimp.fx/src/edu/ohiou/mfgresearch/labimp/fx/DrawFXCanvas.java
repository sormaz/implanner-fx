package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.LinkedList;

import com.sun.javafx.application.PlatformImpl;

import edu.ohiou.mfgresearch.labimp.draw.DrawWFApplet;
import edu.ohiou.mfgresearch.labimp.draw.DrawWFPanel;
import edu.ohiou.mfgresearch.labimp.draw.DrawableWF;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class DrawFXCanvas extends VBox implements DrawListener{	

	private static final double DEFAULT_WIDTH = 650;
	private static final double DEFAULT_HEIGHT = 550;
	
	private static final double XAXIS_LENGTH = 10;
	private static final double YAXIS_LENGTH = 10;
	private static final double ZAXIS_LENGTH = 10;
	
	private static final double DEFAULT_SCALE = 5;
	private static final 
			Point3D DEFAULT_VIEWPOINT = new Point3D(0, 0, 100);
	
	private static final double CAMERA_NEAR_CLIP = 0.1;
	private static final double CAMERA_FAR_CLIP = 10000.0;
	
	private static double fieldOfView = 90;
	
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;
	
	private MouseMode mouseMode = MouseMode.MODIFY_VIEW;
	
	private ObservableList<DrawableFX> targetList = FXCollections.observableArrayList();
	private DrawableFX activeTarget;
	
	private SimpleDoubleProperty viewPointX = new SimpleDoubleProperty();
	private SimpleDoubleProperty viewPointY = new SimpleDoubleProperty();
	private SimpleDoubleProperty viewPointZ = new SimpleDoubleProperty();	
	private SimpleDoubleProperty scale = new SimpleDoubleProperty();	
	private SimpleBooleanProperty showWCS = new SimpleBooleanProperty();
	
	private Xform root = new Xform(); 
	private Xform fxRoot = new Xform();
	private Xform swingRoot = new Xform();
	private Xform correctedYZGroup = new Xform();
	private Xform swing2DGroup = new Xform(); 
	private Xform swing3DGroup = new Xform(); 
	private Xform fx2DGroup = new Xform(); 
	private Xform fx3DGroup = new Xform();
	private SubScene fxScene = new SubScene(fxRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	private SubScene swingScene = new SubScene(swingRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	private Pane canvas = new Pane(root);
	
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
	private final Xform cameraXform = new Xform();
	private final Xform cameraXform2 = new Xform();
	private final Xform cameraXform3 = new Xform();
	
	private final DrawWFPanel virtualPanel = new DrawWFPanel();
	
	public enum MouseMode {
	    MODIFY_VIEW, MODIFY_TARGET
	}

	public DrawFXCanvas() {
		this(FXCollections.observableArrayList());
	}

	public DrawFXCanvas(ObservableList<DrawableFX> targetList) {
		this(targetList, DEFAULT_VIEWPOINT, DEFAULT_SCALE, false);
	}

	public DrawFXCanvas(ObservableList<DrawableFX> targetList, 
			Point3D viewpoint, double scale, boolean showWCS) {
		setViewpoint(viewpoint);
		setScale(scale);
		this.showWCS.set(showWCS);
		init();			
		setTargetList(targetList);
	}
	
	public ObservableList<DrawableFX> getTargetList() {
		return targetList;
	}

	public void setTargetList(ObservableList<DrawableFX> targetList) {
		
		this.targetList.stream()
				.forEach(target -> target.removeListener(this));	
		
		this.targetList = targetList;
		for(DrawableFX target: targetList) {
			target.addListener(this);
		}
		updateView();
	}
	
	public void addTarget(DrawableFX target) {
		if(targetList.contains(target)) return;
		targetList.add(target);
		target.addListener(this);
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
		return new Point3D
			(viewPointX.get(), viewPointY.get(), viewPointZ.get());
	}

	public void setViewpoint(Point3D viewpoint) {
		viewPointX.set(viewpoint.getX());
		viewPointY.set(viewpoint.getY());
		viewPointZ.set(viewpoint.getZ());
	}

	public double getScale() {
		return scale.get();
	}

	public void setScale(double scale) {
		this.scale.set(scale);
		virtualPanel.setScale(scale);
		virtualPanel.createTargetTable();
	}

	public Pane getCanvas() {
		return canvas;
	}

	@Override
	public DrawWFPanel getVirtualPanel() {
		return virtualPanel;
	}

	private void init() {
		
		PlatformImpl.startup(() -> {});
		
		setupPanels();

        setEventHandlers();
        
        setupGroups();
        
        buildCamera();

	}
	
	private void setupGroups() {
		
		root.getChildren().add(fxScene);
		root.getChildren().add(swingScene);
		fxRoot.getChildren().add(correctedYZGroup);
		
		Scale mirrorYZ = new Scale(1, -1, -1);
		correctedYZGroup.getTransforms().add(mirrorYZ);
		
		correctedYZGroup.getChildren()
				.addAll(swing2DGroup, fx2DGroup, fx3DGroup);	
		
		swingRoot.getChildren()
				.addAll(swing3DGroup);
	}
	
	private void buildCamera() {
		System.out.println("buildCamera()");
		fxRoot.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
	
		camera.setFieldOfView(fieldOfView);
		
		camera.setNearClip(CAMERA_NEAR_CLIP);
		camera.setFarClip(CAMERA_FAR_CLIP);
		
		fxScene.setCamera(camera);
		
		setCameraFromViewPoint();
		
//		getViewPointFromCamera();
		
	}
	
	private void setupPanels() {
		canvas.setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Rectangle clipRect = new Rectangle(canvas.getWidth(), canvas.getHeight());
		canvas.setClip(clipRect);
		
		clipRect.heightProperty().bind(canvas.heightProperty());
		clipRect.widthProperty().bind(canvas.widthProperty());
		
		fxScene.heightProperty().bind(canvas.heightProperty());
		fxScene.widthProperty().bind(canvas.widthProperty());
		
		swingScene.heightProperty().bind(canvas.heightProperty());
		swingScene.widthProperty().bind(canvas.widthProperty());
		
		Pane canvasControls = getToolbar();
		canvasControls.setPrefWidth(DEFAULT_WIDTH);
						
		VBox.setVgrow(canvas, Priority.ALWAYS);
		getChildren().addAll(canvas, canvasControls);
		
		virtualPanel.getDrawPanel().setSize
		(new Dimension((int)DEFAULT_WIDTH, (int)DEFAULT_HEIGHT));

		virtualPanel.setView(scale.get(), viewPointX.get(), 
				viewPointY.get(), viewPointZ.get());
		
        canvas.widthProperty().addListener(evt -> {
        	virtualPanel.getDrawPanel()
        		.setSize((int)canvas.getWidth(), (int)canvas.getHeight());
        	virtualPanel.setViewTransform();
        	virtualPanel.createTargetTable();
        	updateView();
        });
        
        canvas.heightProperty().addListener(evt -> {
        	virtualPanel.getDrawPanel()
        		.setSize((int)canvas.getWidth(), (int)canvas.getHeight());  
        	virtualPanel.setViewTransform();
        	virtualPanel.createTargetTable();
        	updateView();
        });
	}
	
	private void setEventHandlers() {
		
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
				
				mousePosX = e.getSceneX();
				mousePosY = e.getSceneY();
				mouseOldX = e.getSceneX();
				mouseOldY = e.getSceneY();
				
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
						mouseMode = MouseMode.MODIFY_TARGET;
					} else {
						mouseMode = MouseMode.MODIFY_VIEW;
					}		
					
				} else {
					
				}		
				
				if (mouseMode == MouseMode.MODIFY_TARGET) {
					getScene().setCursor(Cursor.CROSSHAIR);
				} else {
					getScene().setCursor(Cursor.DEFAULT);
				}
				
			}
		});
		
		EventHandler<MouseEvent> onMouseDragged = e -> {
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			mousePosX = e.getSceneX();
			mousePosY = e.getSceneY();
			mouseDeltaX = (mousePosX - mouseOldX); 
			mouseDeltaY = (mousePosY - mouseOldY); 

			if (mouseMode == MouseMode.MODIFY_TARGET) {
				if(activeTarget instanceof Swing3DConverter) {
					virtualPanel.setTarget
					((DrawableWF)((Swing3DConverter) activeTarget).getSwingTarget());
					((DrawWFPanel)virtualPanel.gettCanvas()).
					modifyTargetPoint((int)e.getX(), (int)e.getY());
				} else {
					
				}	
				
				return;
			} 

			if (e.isPrimaryButtonDown()) {
				
				System.out.println("Old rxAngle Angle: " + cameraXform.rx.getAngle());
				System.out.println("Old ryAngle Angle: " + cameraXform.ry.getAngle());
				
				cameraXform.ry.setAngle(cameraXform.ry.getAngle() + mouseDeltaX);  
				cameraXform.rx.setAngle(cameraXform.rx.getAngle() - mouseDeltaY);  
				
				System.out.println("New rxAngle Angle: " + cameraXform.rx.getAngle());
				System.out.println("New ryAngle Angle: " + cameraXform.ry.getAngle());
				
				setViewPointFromCamera();
			}
			else if (e.isSecondaryButtonDown()) {
				double z = camera.getTranslateZ();
				System.out.println("Old z: " + z);
				double newZ = z + mouseDeltaX;
				camera.setTranslateZ(newZ);
				System.out.println("New z: " + newZ);
			
				setViewPointFromCamera();
				
			}
			
			updateView();
		};

		setOnMouseDragged(onMouseDragged);

		
	    setOnScroll((e) -> {
	
	    	double zoomRatio = 1;
	    	if(e.getDeltaY() > 0) {
	    		zoomRatio = 1.05;
	    	} else {
	    		zoomRatio = 0.95;
	    	}
	    	
	    	setScale(scale.get() * zoomRatio);
	    	
	    	updateView();
	    	
	    });
		
	}
	
	private void setViewPointFromCamera() {		
		
		double zTranslate = Math.abs(camera.getTranslateZ());
		double rxAngle = cameraXform.rx.getAngle();
		double ryAngle = cameraXform.ry.getAngle();
		
		double x = - zTranslate * Math.cos(Math.toRadians(rxAngle)) * Math.sin(Math.toRadians(ryAngle));
		double y = - zTranslate * Math.sin(Math.toRadians(rxAngle));
		double z = zTranslate * Math.cos(Math.toRadians(rxAngle)) * Math.cos(Math.toRadians(ryAngle));
		
		System.out.println("zTranslate: " + zTranslate);
		System.out.println("rxAngle: " + rxAngle);
		System.out.println("ryAngle: " + ryAngle);
		System.out.println("Viewpoint: (" + x + ", " + y + ", " + z + ")");
		System.out.println("");
		
		setViewpoint(new Point3D(x, y, z));
		virtualPanel.setView(scale.get(), x, y, z);
	}
	
	private void setCameraFromViewPoint() {
		
		double x = viewPointX.get();
		double y = viewPointY.get();
		double z = viewPointZ.get();
		
		double zTranslate 
		= Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));	
		
		double ryAngle, rxAngle;
		
		if(zTranslate > 0) {
			ryAngle = - Math.toDegrees(Math.atan2(x , z));	
			rxAngle 
				= - Math.toDegrees(Math.asin(y / zTranslate));
		} else {
			ryAngle = 0;
			rxAngle = 0;		
		}
		
		System.out.println("Viewpoint: (" + x + ", " + y + ", " + z + ")");
		System.out.println("zTranslate: " + zTranslate);
		System.out.println("ryAngle: " + ryAngle);
		System.out.println("rxAngle: " + rxAngle);
		
		camera.setTranslateZ(-zTranslate);	
		cameraXform.ry.setAngle(ryAngle);  
		cameraXform.rx.setAngle(rxAngle); 	
		
	}
	
	
	@Override
	public void updateView() {

		swing2DGroup.getChildren().clear();
		swing3DGroup.getChildren().clear();
		fx2DGroup.getChildren().clear();
		fx3DGroup.getChildren().clear();
		
		targetList.stream()
				.filter((t) -> t.getVisible().get()) 
				.forEach((target) -> { 
			
			if(target instanceof Swing2DConverter) {
				
				for (Shape s: target.getFXShapes()) {
					s.setStrokeWidth(1/scale.get());
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
		
		if(showWCS.get()) {
			AxisFX axes = new AxisFX(XAXIS_LENGTH * scale.get(), 
									YAXIS_LENGTH * scale.get(), 
									ZAXIS_LENGTH * scale.get());
			Xform axesGroup = new Xform();
			axesGroup.getChildren().addAll(axes.getFX3DShapes());
			axesGroup.setScale(1/scale.get());
			fx3DGroup.getChildren().addAll(axesGroup);
		}
		
		swing2DGroup.setScale(scale.get());
		fx2DGroup.setScale(scale.get());
		fx3DGroup.setScale(scale.get());
		
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
		xTxt.setText(String.valueOf(viewPointX.get()));
		viewPointX.addListener((e,o,n) -> {
			DecimalFormat df = new DecimalFormat("#.00");
			xTxt.setText(df.format(viewPointX.get()));
		});
					
		TextField yTxt = new TextField();
		yTxt.setText(String.valueOf(viewPointY.get()));
		viewPointY.addListener((e,o,n) -> {
			DecimalFormat df = new DecimalFormat("#.00");
			yTxt.setText(df.format(viewPointY.get()));
		});
		
		TextField zTxt = new TextField();
		zTxt.setText(String.valueOf(viewPointZ.get()));
		viewPointZ.addListener((e,o,n) -> {
			DecimalFormat df = new DecimalFormat("#.00");
			zTxt.setText(df.format(viewPointZ.get()));
		});
		
		xTxt.focusedProperty().addListener(new ChangeListener<Boolean>()
		{		
			double oldValue;
			
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue) {
					oldValue = Double.valueOf(xTxt.getText());
		        } else {
		        	try {
		        		viewPointX.set(Double.valueOf(xTxt.getText()));
		        		setCameraFromViewPoint();
					} catch (Exception e) {
		        		viewPointX.set(oldValue);
		        		setCameraFromViewPoint();
					}				
		        }
		    }
		});
		
		xTxt.focusedProperty().addListener(new ChangeListener<Boolean>()
		{		
			double oldValue;
			
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue) {
					oldValue = Double.valueOf(xTxt.getText());
		        } else {
		        	try {
		        		viewPointX.set(Double.valueOf(xTxt.getText()));
		        		setCameraFromViewPoint();
		        		virtualPanel.setView(scale.get(), 
		        							viewPointX.get(), 
		        							viewPointY.get(), 
		        							viewPointZ.get());
		        		updateView();
					} catch (Exception e) {
		        		viewPointX.set(oldValue);
		        		setCameraFromViewPoint();
		        		virtualPanel.setView(scale.get(), 
			    							viewPointX.get(), 
			    							viewPointY.get(), 
			    							viewPointZ.get());
		        		updateView();
					}				
		        }
		    }
		});
		
		yTxt.focusedProperty().addListener(new ChangeListener<Boolean>()
		{		
			double oldValue;
			
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue) {
					oldValue = Double.valueOf(yTxt.getText());
		        } else {
		        	try {
		        		viewPointY.set(Double.valueOf(yTxt.getText()));
		        		setCameraFromViewPoint();
		        		virtualPanel.setView(scale.get(), 
    							viewPointX.get(), 
    							viewPointY.get(), 
    							viewPointZ.get());
		        		updateView();
					} catch (Exception e) {
		        		viewPointY.set(oldValue);
		        		setCameraFromViewPoint();
		        		virtualPanel.setView(scale.get(), 
    							viewPointX.get(), 
    							viewPointY.get(), 
    							viewPointZ.get());
		        		updateView();
					}				
		        }
		    }
		});
		
		zTxt.focusedProperty().addListener(new ChangeListener<Boolean>()
		{		
			double oldValue;
			
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue) {
					oldValue = Double.valueOf(zTxt.getText());
		        } else {
		        	try {
		        		viewPointZ.set(Double.valueOf(zTxt.getText()));
		        		setCameraFromViewPoint();
		        		virtualPanel.setView(scale.get(), 
    							viewPointX.get(), 
    							viewPointY.get(), 
    							viewPointZ.get());
		        		updateView();
					} catch (Exception e) {
		        		viewPointZ.set(oldValue);
		        		setCameraFromViewPoint();
		        		virtualPanel.setView(scale.get(), 
    							viewPointX.get(), 
    							viewPointY.get(), 
    							viewPointZ.get());
		        		updateView();
					}				
		        }
		    }
		});
		
		Label scaleLbl = new Label("Scale :");
		GridPane.setHalignment(scaleLbl, HPos.CENTER);
		scaleLbl.setPadding(new Insets(4, 4, 4, 4));
		TextField scaleTxt = new TextField();	
		scaleTxt.setText(String.valueOf(scale.get()));
		
		scale.addListener((e,o,n) -> {
			DecimalFormat df = new DecimalFormat("#.00");
			scaleTxt.setText(df.format(scale.get()));
		});
		
		scaleTxt.focusedProperty().addListener(new ChangeListener<Boolean>()
		{		
			double oldValue;
			
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue) {
					oldValue = Double.valueOf(scaleTxt.getText());
		        } else {
		        	try {
		        		if(Double.valueOf(scaleTxt.getText()) <= 0) {
		        			throw new Exception("Scale must be greater than zero.");
		        		}
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
		wcsCheck.selectedProperty().bindBidirectional(showWCS);
		wcsCheck.setOnAction((e) -> {updateView();});
		
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

	@Override
	public Pane getView() {
		return this;
	}

	@Override
	public void display() {
		ApplicationLauncherExternal app = new ApplicationLauncherExternal();	
		app.setListener(this);
		app.launch(this);			
	}
	
}
