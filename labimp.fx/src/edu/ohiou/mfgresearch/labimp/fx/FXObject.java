/**
 * 
 */
package edu.ohiou.mfgresearch.labimp.fx;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Properties;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Text;

/**
 * @author Arif
 *
 */
public abstract class FXObject implements DrawableFX {

	private BooleanProperty IsVisible = new SimpleBooleanProperty(true);
	protected DrawFXCanvas parentContainer;
	static protected Properties properties = new Properties();
	
	static {
		loadProperties(FXObject.class, "labimp.fx");
	}
	
	public FXObject(DrawFXCanvas parentContainer) {
		setParentContainer(parentContainer);
	}
	
	public void setParentContainer(DrawFXCanvas parentContainer) {
		this.parentContainer = parentContainer;
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
		parentContainer.updateView();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub	
		ApplicationLauncherExternal app = new ApplicationLauncherExternal();	
		app.setTarget(this);
		app.launch(this);		
	}

	@Override
	public Pane getPanel() {
		// TODO Auto-generated method stub
		return new Pane();
	}

	@Override
	public ObservableList<DrawableFX> getTargetList() {
		// TODO Auto-generated method stub
		return FXCollections.observableArrayList();
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

}
