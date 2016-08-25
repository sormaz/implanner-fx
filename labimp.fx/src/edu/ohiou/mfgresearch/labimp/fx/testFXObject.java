package edu.ohiou.mfgresearch.labimp.fx;

import edu.ohiou.labimp.test.Globe;

public class testFXObject {

	public testFXObject() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Globe globe = new Globe(3, 0, 0, 0);
        Swing3DConverter globeFX = new Swing3DConverter(globe);
        globeFX.display();
	}

}
