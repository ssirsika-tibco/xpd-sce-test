package com.tibco.bx.debug.bpm.ui.annotations;

import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryProvider;

public class EMAnnotationFactoryProvider4Debugging implements AnnotationFactoryProvider{

	public AnnotationFactory getAnnotationFactory() {
		return DebugBPMUIActivator.getDefault().getAnnotationFactoryEdit4Debugging();
	}

}
