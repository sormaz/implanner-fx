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
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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

//				GridPane grid = new GridPane();
//				grid.setAlignment(Pos.CENTER);
//				grid.setHgap(10);
//				grid.setVgap(10);
//				grid.setPadding(new Insets(25, 25, 25, 25));
				
				double tRange = 500;
				double aRange = 180;
				double sRange = 3;
						
				Slider s11 = new Slider(t.xProperty().get()-tRange,t.xProperty().get()+tRange,t.xProperty().get());
				s11.setShowTickLabels(true);
				t.xProperty().bind(s11.valueProperty());
				Slider s12 = new Slider(t.yProperty().get()-tRange,t.yProperty().get()+tRange,t.yProperty().get());
				s12.setShowTickLabels(true);
				t.yProperty().bind(s12.valueProperty());
				Slider s13 = new Slider(t.zProperty().get()-tRange,t.zProperty().get()+tRange,t.zProperty().get());
				s13.setShowTickLabels(true);
				t.zProperty().bind(s13.valueProperty());				
				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER);
//				hbox.setPadding(new Insets(25, 25, 25, 25));	
				hbox.getChildren().addAll(s11,s12,s13);
							
				Slider s21 = new Slider(p.xProperty().get()-tRange,p.xProperty().get()+tRange,p.xProperty().get());
				s21.setShowTickLabels(true);
				p.xProperty().bind(s21.valueProperty());
				Slider s22 = new Slider(p.yProperty().get()-tRange,p.yProperty().get()+tRange,p.yProperty().get());
				s22.setShowTickLabels(true);
				p.yProperty().bind(s22.valueProperty());
				Slider s23 = new Slider(p.zProperty().get()-tRange,p.zProperty().get()+tRange,p.zProperty().get());
				s23.setShowTickLabels(true);
				p.zProperty().bind(s23.valueProperty());				
				HBox hbox2 = new HBox();
				hbox2.setAlignment(Pos.CENTER);
//				hbox2.setPadding(new Insets(25, 25, 25, 25));	
				hbox2.getChildren().addAll(s21,s22,s23);
				
				Slider s30 = new Slider(rx.angleProperty().get()-aRange,
						rx.angleProperty().get()+aRange,rx.angleProperty().get());
				s30.setShowTickLabels(true);
				rx.angleProperty().bind(s30.valueProperty());	
				HBox hbox3 = new HBox();
				hbox3.setAlignment(Pos.CENTER);
//				hbox3.setPadding(new Insets(25, 25, 25, 25));				
				hbox3.getChildren().addAll(s30);
				
				Slider s40 = new Slider(ry.angleProperty().get()-aRange,
						ry.angleProperty().get()+aRange,ry.angleProperty().get());
				s40.setShowTickLabels(true);
				ry.angleProperty().bind(s40.valueProperty());
				HBox hbox4 = new HBox();
				hbox4.setAlignment(Pos.CENTER);
//				hbox4.setPadding(new Insets(25, 25, 25, 25));	
				hbox4.getChildren().addAll(s40);
				
				Slider s50 = new Slider(rz.angleProperty().get()-aRange,
						rz.angleProperty().get()+aRange,rz.angleProperty().get());
				s50.setShowTickLabels(true);
				rz.angleProperty().bind(s50.valueProperty());
				HBox hbox5 = new HBox();
				hbox5.setAlignment(Pos.CENTER);
//				hbox5.setPadding(new Insets(25, 25, 25, 25));	
				hbox5.getChildren().addAll(s50);
				
				Slider s61 = new Slider(s.xProperty().get()-sRange,s.xProperty().get()+sRange,s.xProperty().get());
				s61.setShowTickLabels(true);
				s.xProperty().bind(s61.valueProperty());
				Slider s62 = new Slider(s.yProperty().get()-sRange,s.yProperty().get()+sRange,s.yProperty().get());
				s62.setShowTickLabels(true);
				s.yProperty().bind(s62.valueProperty());
				Slider s63 = new Slider(s.zProperty().get()-sRange,s.zProperty().get()+sRange,s.zProperty().get());
				s63.setShowTickLabels(true);
				s.zProperty().bind(s63.valueProperty());				
				HBox hbox6 = new HBox();
				hbox6.setAlignment(Pos.CENTER);
//				hbox.setPadding(new Insets(25, 25, 25, 25));
				hbox6.getChildren().addAll(s61,s62,s63);
				
							
				Slider s71 = new Slider(ip.xProperty().get()-tRange,ip.xProperty().get()+tRange,ip.xProperty().get());
				s71.setShowTickLabels(true);
				ip.xProperty().bind(s71.valueProperty());
				Slider s72 = new Slider(ip.yProperty().get()-tRange,ip.yProperty().get()+tRange,ip.yProperty().get());
				s72.setShowTickLabels(true);
				ip.yProperty().bind(s72.valueProperty());
				Slider s73 = new Slider(ip.zProperty().get()-tRange,ip.zProperty().get()+tRange,ip.zProperty().get());
				s73.setShowTickLabels(true);
				ip.zProperty().bind(s73.valueProperty());				
				HBox hbox7 = new HBox();
				hbox7.setAlignment(Pos.CENTER);
//				hbox.setPadding(new Insets(25, 25, 25, 25));
				hbox7.getChildren().addAll(s71,s72,s73);
				
				FlowPane flow = new FlowPane(Orientation.VERTICAL); 
				flow.setAlignment(Pos.CENTER);
				flow.setPadding(new Insets(25, 25, 25, 25));	
				flow.setVgap(2);
				flow.setHgap(2);
				flow.getChildren().addAll(hbox, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7);
				
				Scene scene = new Scene(flow, 
									500, 
									400);
				window.setScene(scene);

				window.setX 
					(event.getSceneX());

				window.setY
					(event.getSceneY());

				window.setTitle(((Xform)event.getSource()).name);
				window.show();
		        }
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
