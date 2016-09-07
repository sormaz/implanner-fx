package edu.ohiou.mfgresearch.labimp.fx;

import java.io.File;
import java.util.LinkedList;

import javax.swing.JPanel;

import edu.ohiou.mfgresearch.implanner.features.MfgFeature;
import edu.ohiou.mfgresearch.implanner.features.MfgPartModeFpnPanel;
import edu.ohiou.mfgresearch.implanner.geometry.PartModel;
import edu.ohiou.mfgresearch.implanner.geometry.Stock;
import edu.ohiou.mfgresearch.implanner.parts.MfgPartModel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PartModelConverter extends Swing3DConverter {
	
	public PartModelConverter(File file) {
		this(new MfgPartModel(new PartModel(file)));
	}

	public PartModelConverter(ImpObject swingTarget) {
		super(swingTarget);
	}
	
	public void setStock(File stkFile) {
		((MfgPartModel)getSwingTarget()).setStock(new Stock(stkFile));
	}
	
	public StringProperty name() {
		return new SimpleStringProperty
				(((MfgPartModel)getSwingTarget()).getPartName());
	}
	
	public ObservableList<Swing3DConverter> getFeatureList() {
		
		ObservableList<Swing3DConverter> featureList 
				= FXCollections.observableArrayList();
		
		((MfgPartModel)getSwingTarget())
				.getFeatureList().forEach(f -> {
			
					Swing3DConverter s = new Swing3DConverter(f);
					featureList.add(s);
					
		});
		
		return featureList;
		
	}
	
	@Override
	public Pane getPanel() {
		
		StackPane viewPanel = new StackPane();
		
		JPanel jp;
		
		if(getSwingTarget().gettPanel() == null) {
			jp = new MfgPartModeFpnPanel((MfgPartModel)getSwingTarget());
		} else {
			jp = getSwingTarget().gettPanel();
		}

		SwingNode n = new SwingNode();
		n.setContent(jp);

		viewPanel.getChildren().add(new StackPane(n));
		
		viewPanel.widthProperty().addListener(evt -> {
			viewPanel.getChildren().clear();
			n.setContent(jp);
			viewPanel.getChildren().add(new StackPane(n));
		});
		
		viewPanel.heightProperty().addListener(evt -> {
			viewPanel.getChildren().clear();
			n.setContent(jp);
			viewPanel.getChildren().add(new StackPane(n));
		});
		
		return viewPanel;
		
	}

}
