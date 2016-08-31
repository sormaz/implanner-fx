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
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.TriangleMesh;

public class TetraHedron extends FXObject {
	
	public float height;
	public float hypotenuse;
	
	public TetraHedron() {
		this(10, 20);
	}
	
	public TetraHedron(float height, float hypotenuse) {
		this(null, height, hypotenuse);
	}

	public TetraHedron(DrawFXCanvas parentContainer, float height, float hypotenuse) {
		super(parentContainer);
		this.height = height;
		this.hypotenuse = hypotenuse;
	}
	
	@Override
	public LinkedList<Shape3D> getFX3DShapes() {
		// TODO Auto-generated method stub
		LinkedList<Shape3D> fxShape = new LinkedList();
		fxShape.add(buildPyramid());
		return fxShape;
	}
	
    private MeshView buildPyramid() {
        final TriangleMesh mesh = new TriangleMesh(); 

        mesh.getPoints().addAll(
            0,0,0,                   //Point 0: Top of Pyramid
            0,height,-hypotenuse/2,  //Point 1: closest base point to camera
            -hypotenuse/2,height,0,  //Point 2: left most base point to camera
            hypotenuse/2,height,0,   //Point 3: farthest base point to camera
            0,height,hypotenuse/2    //Point 4: right most base point to camera
        );

        mesh.getTexCoords().addAll(0,0); 

        mesh.getFaces().addAll( //use dummy texCoords
            0,0,2,0,1,0,  // Vertical Faces "wind" counter clockwise
            0,0,1,0,3,0,  // Vertical Faces "wind" counter clockwise
            0,0,3,0,4,0,  // Vertical Faces "wind" counter clockwise
            0,0,4,0,2,0,  // Vertical Faces "wind" counter clockwise
            4,0,1,0,2,0,  // Base Triangle 1 "wind" clockwise because camera has rotated
            4,0,3,0,1,0   // Base Triangle 2 "wind" clockwise because camera has rotated
        ); 
        
        MeshView meshView = new MeshView(mesh);

        meshView.setDrawMode(DrawMode.LINE); //show lines only by default
        meshView.setCullFace(CullFace.BACK); //Removing culling to show back lines
        
        return meshView;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TetraHedron th = new TetraHedron();
		th.display();
	}

}
