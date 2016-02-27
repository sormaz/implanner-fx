package graph.ui;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

public class GraphBuilderFactory implements BuilderFactory {

	private final JavaFXBuilderFactory fxFactory = new JavaFXBuilderFactory();
	@Override
	public Builder<?> getBuilder(Class<?> type) {
	// You supply a Builder only for Item type
	if (type == DrawPanelFX.class) {
	return new DrawPanelFXBuilder();
	}
	// Let the default Builder do the magic
	return fxFactory.getBuilder(type);
	}

}
