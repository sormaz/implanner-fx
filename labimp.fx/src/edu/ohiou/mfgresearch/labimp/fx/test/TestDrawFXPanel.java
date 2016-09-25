package edu.ohiou.mfgresearch.labimp.fx.test;

import java.io.IOException;

import edu.ohiou.mfgresearch.labimp.fx.DrawFXPanel;
import edu.ohiou.mfgresearch.labimp.fx.Globe;
import edu.ohiou.mfgresearch.labimp.fx.SphereFX;
import edu.ohiou.mfgresearch.labimp.fx.Swing3DConverter;
import edu.ohiou.mfgresearch.labimp.gtk3d.Polygon3d;

import javax.vecmath.Point3d;

import edu.ohiou.mfgresearch.labimp.draw.*;

public class TestDrawFXPanel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point3d p1 = new Point3d(0, 0, 0);
		Point3d p2 = new Point3d(1, 0, 0);
		Point3d p3 = new Point3d(1, 1, 0);
		Point3d p4 = new Point3d(0, 1, 0);
		Point3d p5 = new Point3d(0, 0, 1);
		Point3d p6 = new Point3d(1, 0, 1);
		Point3d p7 = new Point3d(1, 1, 1);
		Point3d p8 = new Point3d(0, 1, 1);

		Polygon3d poly2 = new Polygon3d();

		poly2.addPoint(p1).addPoint(p5).addPoint(p6).addPoint(p2);
		Swing3DConverter sc = new Swing3DConverter(poly2);
		DrawFXPanel panel = new DrawFXPanel();
		panel.addTarget(sc);
		panel.addTarget(new SphereFX());
		panel.display(args);
		try {
			System.out.println("Enter:->");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panel.addTarget(new Swing3DConverter(new Globe()));
	}

}
