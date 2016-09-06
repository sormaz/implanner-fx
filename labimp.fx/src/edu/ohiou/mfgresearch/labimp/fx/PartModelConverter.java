package edu.ohiou.mfgresearch.labimp.fx;

import java.io.File;

import edu.ohiou.mfgresearch.implanner.geometry.PartModel;
import edu.ohiou.mfgresearch.implanner.parts.MfgPartModel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PartModelConverter extends Swing3DConverter {
	
	public PartModelConverter(File file) {
		this(new MfgPartModel(new PartModel(file)));
	}

	public PartModelConverter(ImpObject swingTarget) {
		super(swingTarget);
	}
	
	public StringProperty name() {
		return new SimpleStringProperty
				(((MfgPartModel)getSwingTarget()).getPartName());
	}

}
