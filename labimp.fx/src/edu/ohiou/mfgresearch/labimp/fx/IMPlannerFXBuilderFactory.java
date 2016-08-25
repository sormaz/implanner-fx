package edu.ohiou.mfgresearch.labimp.fx;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

public class IMPlannerFXBuilderFactory implements BuilderFactory {

	private final JavaFXBuilderFactory fxFactory = new JavaFXBuilderFactory();
	@Override
	public Builder<?> getBuilder(Class<?> type) {
	// You supply a Builder only for Item type
		
//	if (type == DrawPanelFX.class) {
//	return new DrawPanelFXBuilder();
//	} else if (type == DrawPanelTx.class) {
//	return new DrawPanelTxBuilder();
//	} else if (type == DrawPanel3D.class) {
//	return new DrawPanel3DBuilder();
//	}
	
	// Let the default Builder do the magic
	return fxFactory.getBuilder(type);
	}
	
//	public class DrawPanelFXBuilder implements Builder<DrawPanelFX> {
//		
//		/**
//		 * Graph model.
//		 */
//		private Graph graph;
//		public Graph getGraph() {
//			return graph;
//		}
//
//		public void setGraph(Graph graph) {
//			this.graph = graph;
//		}
//
//		@Override
//		public DrawPanelFX build() {
//			// TODO Auto-generated method stub
//			return new DrawPanelFX(graph);
//		}
//
//	}
//	
//	public class DrawPanelTxBuilder implements Builder<DrawPanelTx> {
//		
//		/**
//		 * Graph model.
//		 */
//		private Graph graph;
//		public Graph getGraph() {
//			return graph;
//		}
//
//		public void setGraph(Graph graph) {
//			this.graph = graph;
//		}
//
//		@Override
//		public DrawPanelTx build() {
//			// TODO Auto-generated method stub
//			return new DrawPanelTx(graph);
//		}
//
//	}
//	
//	public class DrawPanel3DBuilder implements Builder<DrawPanel3D> {
//		
//		/**
//		 * Graph model.
//		 */
//		private Graph graph;
//		public Graph getGraph() {
//			return graph;
//		}
//
//		public void setGraph(Graph graph) {
//			this.graph = graph;
//		}
//
//		@Override
//		public DrawPanel3D build() {
//			// TODO Auto-generated method stub
//			return new DrawPanel3D(graph);
//		}
//
//	}

}
