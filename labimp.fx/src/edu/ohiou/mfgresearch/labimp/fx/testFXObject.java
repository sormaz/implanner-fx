package edu.ohiou.mfgresearch.labimp.fx;

import java.awt.geom.Point2D;

import edu.ohiou.labimp.test.CubicCurveIMPlanner;
import edu.ohiou.labimp.test.Globe;
import edu.ohiou.labimp.test.Profile2DIMPlanner;
import edu.ohiou.mfgresearch.labimp.basis.Draw2DApplet;
import edu.ohiou.mfgresearch.labimp.basis.Draw2DPanel;
import edu.ohiou.mfgresearch.labimp.basis.GUIApplet;

public class testFXObject {

	public testFXObject() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//        Globe globe = new Globe(3, 0, 0, 0);
//        Swing3DConverter globeFX = new Swing3DConverter(globe);
//        globeFX.display();
		
//		Point2D p1 = new Point2D.Double (1,1);
//		Point2D p2 = new Point2D.Double (5,1);
//		Point2D p3 = new Point2D.Double (2,2);
//		Point2D p4 = new Point2D.Double (1,2);
//		
//		Point2D [] pts = {p1, p2, p3, p4};
//
//		Profile2DIMPlanner target = new Profile2DIMPlanner (pts);
//		Swing2DConverter profileFX = new Swing2DConverter(target);
//		profileFX.display();
		
		CubicCurveIMPlanner cc = new CubicCurveIMPlanner();
		Swing2DConverter ccFX = new Swing2DConverter(cc);
		ccFX.display();
	}

}
