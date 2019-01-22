package com.tibco.bx.debug.core.models;

public interface IBxMarker {

	/**
	 * marker id
	 */
	public static final String MARKER_TYPE = "com.tibco.bx.debug.core.bx.markerType.Breakpoint"; //$NON-NLS-1$

	public static final String PROCESS_ID = "processId";//$NON-NLS-1$
	
	public static final String MODEL_TYPE = "modelType";//$NON-NLS-1$
	/**
	 * ENTRY/RETURN/BOTH
	 */
	public static final String BREAKWHEN = "breakWhen";//$NON-NLS-1$
	
	public static final String CONDITION = "condition";//$NON-NLS-1$
	
	public static final String CONDITION_ENABLED = "conditionEnabled";//$NON-NLS-1$
	
	public static final String CONDITIONLANGUAGE = "ConditionLanguage";//$NON-NLS-1$
	
	public static final String SKIP_ENABLED = "SkipEnabled";//$NON-NLS-1$
}
