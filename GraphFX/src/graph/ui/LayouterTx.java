package graph.ui;

import edu.ohio.ent.cs5500.Layouter;

public interface LayouterTx extends Layouter {

	void refresh();
	void scaleToFit();
	void fitVertical();
	void fitHorizontal();
	void zoomIn();
	void zoomOut();
	void panLeft();
	void panRight();
	void panUp();
	void panDown();
	void rotateCW();
	void rotateCCW();
	
}
