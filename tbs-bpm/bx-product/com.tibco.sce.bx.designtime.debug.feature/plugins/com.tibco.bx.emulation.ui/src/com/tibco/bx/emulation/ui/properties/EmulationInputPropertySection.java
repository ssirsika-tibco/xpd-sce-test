package com.tibco.bx.emulation.ui.properties;

import com.tibco.bx.emulation.model.EmulationPackage;

public class EmulationInputPropertySection extends EmulationInOutPropertySection{
	
	public EmulationInputPropertySection() {
		super(EmulationPackage.eINSTANCE.getInput());
	}
}