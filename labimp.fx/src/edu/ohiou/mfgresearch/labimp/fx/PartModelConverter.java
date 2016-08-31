package edu.ohiou.mfgresearch.labimp.fx;

import java.io.File;

import edu.ohiou.mfgresearch.implanner.geometry.PartModel;
import edu.ohiou.mfgresearch.labimp.draw.ImpObject;

public class PartModelConverter extends Swing3DConverter {
	
	public PartModelConverter(File file) {
		this(new PartModel(file));
	}

	public PartModelConverter(ImpObject swingTarget) {
		this(null, swingTarget);
	}

	public PartModelConverter(DrawFXCanvas parentContainer, ImpObject swingTarget) {
		super(parentContainer, swingTarget);
	}

}
