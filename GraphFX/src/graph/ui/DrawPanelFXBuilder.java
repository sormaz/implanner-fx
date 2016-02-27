package graph.ui;

import edu.ohio.ent.cs5500.Graph;
import javafx.util.Builder;

public class DrawPanelFXBuilder implements Builder<DrawPanelFX> {
	
	/**
	 * Graph model.
	 */
	private Graph graph;
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	@Override
	public DrawPanelFX build() {
		// TODO Auto-generated method stub
		return new DrawPanelFX(graph);
	}

}
