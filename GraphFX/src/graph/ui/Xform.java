/*
 * Copyright (c) 2013, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package graph.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Xform extends Group {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public enum RotateOrder {
		XYZ, XZY, YXZ, YZX, ZXY, ZYX
	}

	public Translate t  = new Translate(); 
	public Translate p  = new Translate(); 
	public Translate ip = new Translate(); 
	public Rotate rx = new Rotate();
	{ rx.setAxis(Rotate.X_AXIS); }
	public Rotate ry = new Rotate();
	{ ry.setAxis(Rotate.Y_AXIS); }
	public Rotate rz = new Rotate();
	{ rz.setAxis(Rotate.Z_AXIS); }
	public Scale s = new Scale();

	public Xform(String name) { 
		super(); 
		this.name = name;
		createContextMenu();
		getTransforms().addAll(t, rz, ry, rx, s); 
	}

	public Xform(String name, RotateOrder rotateOrder) { 
		super(); 
		this.name = name;
		createContextMenu();
		// choose the order of rotations based on the rotateOrder
		switch (rotateOrder) {
		case XYZ:
			getTransforms().addAll(t, p, rz, ry, rx, s, ip); 
			break;
		case XZY:
			getTransforms().addAll(t, p, ry, rz, rx, s, ip); 
			break;
		case YXZ:
			getTransforms().addAll(t, p, rz, rx, ry, s, ip); 
			break;
		case YZX:
			getTransforms().addAll(t, p, rx, rz, ry, s, ip);  // For Camera
			break;
		case ZXY:
			getTransforms().addAll(t, p, ry, rx, rz, s, ip); 
			break;
		case ZYX:
			getTransforms().addAll(t, p, rx, ry, rz, s, ip); 
			break;
		}
	}

	public void createContextMenu() {
		
//		final ContextMenu contextMenu = new ContextMenu();
//		contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
//			public void handle(WindowEvent e) {
//				
//			}
//		});
//		contextMenu.setOnShown(new EventHandler<WindowEvent>() {
//			public void handle(WindowEvent e) {
//				
//			}
//		});
//
//		MenuItem item1 = new MenuItem("About");
//		item1.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//				System.out.println("About");
//			}
//		});
//		MenuItem item2 = new MenuItem("Preferences");
//		item2.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//				System.out.println("Preferences");
//			}
//		});
//		contextMenu.getItems().addAll(item1, item2);
			
		addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
		    public void handle(MouseEvent event) {
//		        if (event.getClickCount() == 2) {
//		            contextMenu.show((Node)event.getSource(), event.getScreenX(), event.getScreenY());
//		        }
		
		        if (event.getClickCount() == 2) {
					Stage window = new Stage();
					window.setMinHeight(250);
					window.setMinWidth(300);

//					GridPane grid = new GridPane();
//					grid.setAlignment(Pos.CENTER);
//					grid.setHgap(10);
//					grid.setVgap(10);
//					grid.setPadding(new Insets(25, 25, 25, 25));
					
					double tRange = 500;
					double aRange = 180;
					double sRange = 3;
							
					Slider tx = new Slider(t.xProperty().get()-tRange,t.xProperty().get()+tRange,t.xProperty().get());
					tx.setShowTickLabels(true);
					t.xProperty().bind(tx.valueProperty());
					Slider ty = new Slider(t.yProperty().get()-tRange,t.yProperty().get()+tRange,t.yProperty().get());
					ty.setShowTickLabels(true);
					t.yProperty().bind(ty.valueProperty());
					Slider tz = new Slider(t.zProperty().get()-tRange,t.zProperty().get()+tRange,t.zProperty().get());
					tz.setShowTickLabels(true);
					t.zProperty().bind(tz.valueProperty());				
					VBox initTranslate = new VBox();
					initTranslate.setAlignment(Pos.CENTER);
//					hbox.setPadding(new Insets(25, 25, 25, 25));	
					Label initTranLbl = new Label("Translation");
					initTranslate.getChildren().addAll(initTranLbl,tx,ty,tz);
								
					Slider px = new Slider(p.xProperty().get()-tRange,p.xProperty().get()+tRange,p.xProperty().get());
					px.setShowTickLabels(true);
					p.xProperty().bind(px.valueProperty());
					Slider py = new Slider(p.yProperty().get()-tRange,p.yProperty().get()+tRange,p.yProperty().get());
					py.setShowTickLabels(true);
					p.yProperty().bind(py.valueProperty());
					Slider pz = new Slider(p.zProperty().get()-tRange,p.zProperty().get()+tRange,p.zProperty().get());
					pz.setShowTickLabels(true);
					p.zProperty().bind(pz.valueProperty());				
					VBox pivot = new VBox();
					pivot.setAlignment(Pos.CENTER);
//					hbox2.setPadding(new Insets(25, 25, 25, 25));
					Label pivotLbl = new Label("Pivot");
					pivot.getChildren().addAll(pivotLbl,px,py,pz);
					
					Slider rxSlider = new Slider(rx.angleProperty().get()-aRange,
							rx.angleProperty().get()+aRange,rx.angleProperty().get());
					rxSlider.setShowTickLabels(true);
					rx.angleProperty().bind(rxSlider.valueProperty());	
//					HBox rotateX = new HBox();
//					rotateX.setAlignment(Pos.CENTER);
//					hbox3.setPadding(new Insets(25, 25, 25, 25));				
//					rotateX.getChildren().addAll(rxSlider);
					
					Slider rySlider = new Slider(ry.angleProperty().get()-aRange,
							ry.angleProperty().get()+aRange,ry.angleProperty().get());
					rySlider.setShowTickLabels(true);
					ry.angleProperty().bind(rySlider.valueProperty());
//					HBox rotateY = new HBox();
//					rotateY.setAlignment(Pos.CENTER);
//					hbox4.setPadding(new Insets(25, 25, 25, 25));	
//					rotateY.getChildren().addAll(rySlider);
					
					Slider rzSlider = new Slider(rz.angleProperty().get()-aRange,
							rz.angleProperty().get()+aRange,rz.angleProperty().get());
					rzSlider.setShowTickLabels(true);
					rz.angleProperty().bind(rzSlider.valueProperty());
//					HBox rotateZ = new HBox();
//					rotateZ.setAlignment(Pos.CENTER);
//					hbox5.setPadding(new Insets(25, 25, 25, 25));	
//					rotateZ.getChildren().addAll(rzSlider);
					
					VBox rotate = new VBox();
					rotate.setAlignment(Pos.CENTER);
					Label rotateLbl = new Label("Rotation");
					rotate.getChildren().addAll(rotateLbl,rxSlider,rySlider,rzSlider);
					
					Slider sx = new Slider(s.xProperty().get()-sRange,s.xProperty().get()+sRange,s.xProperty().get());
					sx.setShowTickLabels(true);
					s.xProperty().bind(sx.valueProperty());
					Slider sy = new Slider(s.yProperty().get()-sRange,s.yProperty().get()+sRange,s.yProperty().get());
					sy.setShowTickLabels(true);
					s.yProperty().bind(sy.valueProperty());
					Slider sz = new Slider(s.zProperty().get()-sRange,s.zProperty().get()+sRange,s.zProperty().get());
					sz.setShowTickLabels(true);
					s.zProperty().bind(sz.valueProperty());				
					VBox scale = new VBox();
					scale.setAlignment(Pos.CENTER);
//					hbox.setPadding(new Insets(25, 25, 25, 25));
					Label scaleLbl = new Label("Scaling");
					scale.getChildren().addAll(scaleLbl,sx,sy,sz);
					
								
					Slider ipx = new Slider(ip.xProperty().get()-tRange,ip.xProperty().get()+tRange,ip.xProperty().get());
					ipx.setShowTickLabels(true);
					ip.xProperty().bind(ipx.valueProperty());
					Slider ipy = new Slider(ip.yProperty().get()-tRange,ip.yProperty().get()+tRange,ip.yProperty().get());
					ipy.setShowTickLabels(true);
					ip.yProperty().bind(ipy.valueProperty());
					Slider ipz = new Slider(ip.zProperty().get()-tRange,ip.zProperty().get()+tRange,ip.zProperty().get());
					ipz.setShowTickLabels(true);
					ip.zProperty().bind(ipz.valueProperty());				
					VBox inversePivot = new VBox();
					inversePivot.setAlignment(Pos.CENTER);
//					hbox.setPadding(new Insets(25, 25, 25, 25));
					Label invPivotLbl = new Label("Inverse Pivot");
					inversePivot.getChildren().addAll(invPivotLbl,ipx,ipy,ipz);
					
					FlowPane flow = new FlowPane(Orientation.HORIZONTAL); 
					flow.setAlignment(Pos.CENTER);
					flow.setPadding(new Insets(25, 25, 25, 25));	
					flow.setVgap(2);
					flow.setHgap(2);
					flow.getChildren().addAll(initTranslate, pivot, rotate, scale, inversePivot);
					
					Scene scene = new Scene(flow, 
										500, 
										400);
					window.setScene(scene);

					window.setX 
						(event.getSceneX());

					window.setY
						(event.getSceneY());

					window.setTitle(name);
					window.show();		        }
		    }
		});
	}

	public void setTranslate(double x, double y, double z) {
		t.setX(x);
		t.setY(y);
		t.setZ(z);
	}

	public void setTranslate(double x, double y) {
		t.setX(x);
		t.setY(y);
	}

	// Cannot override these methods as they are final:
	// public void setTranslateX(double x) { t.setX(x); }
	// public void setTranslateY(double y) { t.setY(y); }
	// public void setTranslateZ(double z) { t.setZ(z); }
	// Use these methods instead:
	public void setTx(double x) { t.setX(x); }
	public void setTy(double y) { t.setY(y); }
	public void setTz(double z) { t.setZ(z); }

	public void setRotate(double x, double y, double z) {
		rx.setAngle(x);
		ry.setAngle(y);
		rz.setAngle(z);
	}

	public void setRotateX(double x) { rx.setAngle(x); }
	public void setRotateY(double y) { ry.setAngle(y); }
	public void setRotateZ(double z) { rz.setAngle(z); }
	public void setRx(double x) { rx.setAngle(x); }
	public void setRy(double y) { ry.setAngle(y); }
	public void setRz(double z) { rz.setAngle(z); }

	public void setScale(double scaleFactor) {
		s.setX(scaleFactor);
		s.setY(scaleFactor);
		s.setZ(scaleFactor);
	}

	public void setScale(double x, double y, double z) {
		s.setX(x);
		s.setY(y);
		s.setZ(z);
	}

	// Cannot override these methods as they are final:
	// public void setScaleX(double x) { s.setX(x); }
	// public void setScaleY(double y) { s.setY(y); }
	// public void setScaleZ(double z) { s.setZ(z); }
	// Use these methods instead:
	public void setSx(double x) { s.setX(x); }
	public void setSy(double y) { s.setY(y); }
	public void setSz(double z) { s.setZ(z); }

	public void setPivot(double x, double y, double z) {
		p.setX(x);
		p.setY(y);
		p.setZ(z);
		ip.setX(-x);
		ip.setY(-y);
		ip.setZ(-z);
	}

	public void reset() {
		t.setX(0.0);
		t.setY(0.0);
		t.setZ(0.0);
		rx.setAngle(0.0);
		ry.setAngle(0.0);
		rz.setAngle(0.0);
		s.setX(1.0);
		s.setY(1.0);
		s.setZ(1.0);
		p.setX(0.0);
		p.setY(0.0);
		p.setZ(0.0);
		ip.setX(0.0);
		ip.setY(0.0);
		ip.setZ(0.0);
	}

	public void resetTSP() {
		t.setX(0.0);
		t.setY(0.0);
		t.setZ(0.0);
		s.setX(1.0);
		s.setY(1.0);
		s.setZ(1.0);
		p.setX(0.0);
		p.setY(0.0);
		p.setZ(0.0);
		ip.setX(0.0);
		ip.setY(0.0);
		ip.setZ(0.0);
	}

	@Override public String toString() {
		return "Xform[t = (" +
				t.getX() + ", " +
				t.getY() + ", " +
				t.getZ() + ")  " +
				"r = (" +
				rx.getAngle() + ", " +
				ry.getAngle() + ", " +
				rz.getAngle() + ")  " +
				"s = (" +
				s.getX() + ", " + 
				s.getY() + ", " + 
				s.getZ() + ")  " +
				"p = (" +
				p.getX() + ", " + 
				p.getY() + ", " + 
				p.getZ() + ")  " +
				"ip = (" +
				ip.getX() + ", " + 
				ip.getY() + ", " + 
				ip.getZ() + ")]";
	}
}
