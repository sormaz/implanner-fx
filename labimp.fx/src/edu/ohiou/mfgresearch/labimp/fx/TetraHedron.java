package edu.ohiou.mfgresearch.labimp.fx;

import java.util.LinkedList;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape;
import javafx.scene.shape.TriangleMesh;

public class TetraHedron extends FXObject {

	public TetraHedron(DrawFXCanvas parentContainer) {
		super(parentContainer);

	}
	
	@Override
	public LinkedList getFXShapes() {
		// TODO Auto-generated method stub
		LinkedList<Shape> fxShape = new LinkedList();
//		fxShape.add(buildPyramid(100,200,Color.BLACK,true,false));
		return fxShape;
	}
	
    //Step 2a:  Create a general Pyramid TriangleMesh building method with height and hypotenuse
    private Group buildPyramid(float height, float hypotenuse, 
                                Color color, 
                                boolean ambient, 
                                boolean fill) {
        final TriangleMesh mesh = new TriangleMesh(); 
        //End Step 2a
        //Step 2b: Add 5 points, later we will build our faces from these
        mesh.getPoints().addAll(
            0,0,0,                   //Point 0: Top of Pyramid
            0,height,-hypotenuse/2,  //Point 1: closest base point to camera
            -hypotenuse/2,height,0,  //Point 2: left most base point to camera
            hypotenuse/2,height,0,   //Point 3: farthest base point to camera
            0,height,hypotenuse/2    //Point 4: right most base point to camera
        );//End Step 2b
        //Step 2c:
        //for now we'll just make an empty texCoordinate group
        mesh.getTexCoords().addAll(0,0); 
        //End Step 2c
        //Step 2d: Add the faces "winding" the points generally counter clock wise
        mesh.getFaces().addAll( //use dummy texCoords
            0,0,2,0,1,0,  // Vertical Faces "wind" counter clockwise
            0,0,1,0,3,0,  // Vertical Faces "wind" counter clockwise
            0,0,3,0,4,0,  // Vertical Faces "wind" counter clockwise
            0,0,4,0,2,0,  // Vertical Faces "wind" counter clockwise
            4,0,1,0,2,0,  // Base Triangle 1 "wind" clockwise because camera has rotated
            4,0,3,0,1,0   // Base Triangle 2 "wind" clockwise because camera has rotated
        ); //End Step 2d
        //Step 2e: Create a viewable MeshView to be added to the scene
        //To add a TriangleMesh to a 3D scene you need a MeshView container object
        MeshView meshView = new MeshView(mesh);
        //The MeshView allows you to control how the TriangleMesh is rendered
        meshView.setDrawMode(DrawMode.LINE); //show lines only by default
        meshView.setCullFace(CullFace.BACK); //Removing culling to show back lines
         //End Step 2e
        //Step 2f:  Add it to a group, this will be useful later
        Group pyramidGroup = new Group();
        pyramidGroup.getChildren().add(meshView);  
         //End Step 2f
        //Step 2g: Customizing your Pyramid
        if(null != color) {
            PhongMaterial material = new PhongMaterial(color);
            meshView.setMaterial(material);
        }
        if(ambient) {
            AmbientLight light = new AmbientLight(Color.WHITE);
            light.getScope().add(meshView);
            pyramidGroup.getChildren().add(light);            
        }
        if(fill) { 
            meshView.setDrawMode(DrawMode.FILL);
        }
        //End Step 2g
        
        return pyramidGroup;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TetraHedron th = new TetraHedron(null);
		th.display();
	}

}
