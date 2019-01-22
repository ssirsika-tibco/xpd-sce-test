package com.tibco.bx.debug.bpm.core.models;

public interface IBxNodeType {

    static String TASK = "task"; //$NON-NLS-1$
	
	static String EVENT = "event";//$NON-NLS-1$
	static String GATEWAY = "gateway";//$NON-NLS-1$
	
	static String EMBEDDED_SUBPROCESS = "embedded_subProcess";//$NON-NLS-1$
	
	static String INDENPENDENT_SUBPROCESS = "independent_subProcess";//$NON-NLS-1$
	
	static String TRACK = "track";//$NON-NLS-1$
}
