/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.fx;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Properties;

import com.sun.javafx.collections.ListListenerHelper;

import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Arif
 *
 */
public abstract class FXObject implements DrawableFX {

	private BooleanProperty IsVisible = new SimpleBooleanProperty(true);
	static protected Properties properties = new Properties();
	protected ObservableList<DrawListener> listeners = FXCollections.observableArrayList();
	protected String defaultDrawMode = DrawMode.LINE.toString();
	protected String defaultCullFace = CullFace.BACK.toString();
	protected String defaultColor = "black";
	protected String defaultFillColor ="black";
	
	static {
		loadProperties(FXObject.class, "labimp.fx");
	}
	
	public void addListener(DrawListener listener) {
		if(listeners.contains(listener)) return;
		listeners.add(listener);
		listener.addTarget(this);
	}
	
	public void removeListener(DrawListener listener) {
		listeners.remove(listener);
	}
	
	public StringProperty name() {
		return new SimpleStringProperty(getClass().getSimpleName().toString());
	}
	
	static public Properties getProperties () {
		return properties;
	}
	
	public BooleanProperty getVisible() {
		return IsVisible;
	}
	
	@Override
	public void changeVisibility() {
		IsVisible.set(!IsVisible.get());
	}

	@Override
	public void display(String ... args) {
			
		if(listeners.isEmpty()) 
			addListener(new DrawFXPanel());		
		
//		listeners.get(0).display(args);
		
		listeners.stream().forEach(l -> l.display());
		
	}

	@Override
	public Pane getPanel() {
		// TODO Auto-generated method stub
		return new Pane();
	}
	
	public Color getStrokeColor() {
	    String propColor = properties.getProperty(this.getClass().getName() +
                ".color", defaultColor);
	    Color color;
	    try {
			color = Color.web(propColor); 
		} catch (Exception e) {
			color = Color.web(defaultColor);
		}
		return color;
	}
	
	public Color getFillColor() {
	    String propColor = properties.getProperty(this.getClass().getName() +
                ".fillColor", defaultFillColor);    
	    Color color;
	    try {
			color = Color.web(propColor); 
		} catch (Exception e) {
			color = Color.web(defaultFillColor);
		}
		return color;
	}
	
	public DrawMode getDrawMode() {
	    String drawModeString = properties.getProperty(this.getClass().getName() +
                ".drawMode", defaultDrawMode).toLowerCase();
	    
	    DrawMode drawMode;
	    if(drawModeString.equals("fill")) {
	    	drawMode = DrawMode.FILL;
	    } else if(drawModeString.equals("line")) {
	    	drawMode = DrawMode.LINE;
	    } else {
	    	drawMode = DrawMode.valueOf(defaultDrawMode);
	    }
	    return drawMode;
	}
	
	public CullFace getCullFace() {
	    String cullFaceString = properties.getProperty(this.getClass().getName() +
                ".cullFace", defaultCullFace).toLowerCase();
	    
	    CullFace cullFace;
	    if(cullFaceString.equals("back")) {
	    	cullFace = CullFace.BACK;
	    } else if(cullFaceString.equals("front")) {
	    	cullFace = CullFace.FRONT;
	    } else if(cullFaceString.equals("none")) {
	    	cullFace = CullFace.NONE;
	    } else {
	    	cullFace = CullFace.valueOf(defaultCullFace);
	    }    
	    return cullFace;
	}
	
	public LinkedList<Shape> getFXShapesWColor() {
		LinkedList<Shape> fxShapes = getFXShapes();
		fxShapes.forEach(s -> {
			s.setStroke(getStrokeColor());
		});
		return fxShapes;
	}
	
	public LinkedList<Shape3D> getFX3DShapesWColor() {
		
		DrawMode drawMode = getDrawMode();
		CullFace cullFace = getCullFace();
		Color fillColor = getFillColor();
		
		LinkedList<Shape3D> fx3DShapes = getFX3DShapes();
		fx3DShapes.forEach(s -> {
			s.setMaterial(new PhongMaterial(fillColor));
			s.setDrawMode(drawMode);
			s.setCullFace(cullFace);
		});
		return fx3DShapes;
	}
	
	public LinkedList<Shape> getFXFillShapesWColor() {
		LinkedList<Shape> fxFillShapes = getFXFillShapes();
		fxFillShapes.forEach(s -> {
			s.setStroke(Color.TRANSPARENT);
			s.setFill(getFillColor());
		});
		return fxFillShapes;
	}

	@Override
	public LinkedList<Shape> getFXShapes() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}
	
	@Override
	public LinkedList<Shape3D> getFX3DShapes() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

	@Override
	public LinkedList<Shape> getFXFillShapes() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

	@Override
	public LinkedList<Text> getFXStringList() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}

	@Override
	public LinkedList getFXSelectables() {
		// TODO Auto-generated method stub
		return new LinkedList<>();
	}
	
	  public static void loadProperties (Class c, String name) {
		   try {
			      URL resourcePropertyURL = c.getResource(
			      "/META-INF/properties/" + name + ".properties");
			      properties.load(resourcePropertyURL.openStream());
			      System.out.println(
			      "Properties loaded from " + c.getSimpleName() + " resource, " + resourcePropertyURL);
			    }
			    catch (Exception ex) {
			      System.err.println(
			      "\nProperties "  + name + " not loaded from FXObject resource file.");
			    }
			    try {
			      File propertyFile = new File(System.getProperty("user.home"),
			      name + ".properties");
			      properties.load(new FileInputStream(propertyFile));
			      System.out.println(
			      "Properties loaded from user home, file " + propertyFile);
			    }
			    catch (Exception ex) {
			      System.err.println(
			      "Properties " + name + " not loaded from user home.");
			    }
			    try {
			      File propertyFile = new File(System.getProperty("user.dir"),
			    		 name +  ".properties");
			      properties.load(new FileInputStream(propertyFile));
			      System.out.println(
			      "Properties loaded from current folder, file " + propertyFile);
			    }
			    catch (Exception ex) {
			      System.err.println(
			      "\nProperties " + name + " not loaded from current folder.");
			    }
	  }
	  
	  @Override
	  public String getToolTip() {
		  return getClass().getName();
	  }
	  
	  @Override
	  public String toString() {
		  return name().get();
	  }

}
