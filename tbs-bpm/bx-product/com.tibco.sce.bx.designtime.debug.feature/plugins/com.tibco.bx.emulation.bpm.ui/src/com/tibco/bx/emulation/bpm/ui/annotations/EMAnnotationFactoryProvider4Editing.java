package com.tibco.bx.emulation.bpm.ui.annotations;

import com.tibco.bx.emulation.bpm.ui.EmulationBPMUIActivator;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryProvider;

public class EMAnnotationFactoryProvider4Editing implements AnnotationFactoryProvider{

	public AnnotationFactory getAnnotationFactory() {
		return EmulationBPMUIActivator.getDefault().getAnnotationFactoryEdit4Editing();
	}

}
