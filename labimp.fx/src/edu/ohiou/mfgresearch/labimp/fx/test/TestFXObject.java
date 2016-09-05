package edu.ohiou.mfgresearch.labimp.fx.test;

import edu.ohiou.mfgresearch.labimp.fx.DrawFXCanvas;
import edu.ohiou.mfgresearch.labimp.fx.FXObject;

public class TestFXObject extends FXObject {
	
	public TestFXObject () {
		this (new DrawFXCanvas());
	}
	public TestFXObject (DrawFXCanvas parentContainer) {
		super(parentContainer);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestFXObject tfxo = new TestFXObject();
		tfxo.display();
	}

}
