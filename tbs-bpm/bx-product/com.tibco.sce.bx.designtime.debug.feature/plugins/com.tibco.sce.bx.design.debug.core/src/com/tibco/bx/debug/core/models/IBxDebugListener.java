package com.tibco.bx.debug.core.models;

public interface IBxDebugListener {

	void bxThreadChanged(BxDebugEvent event);
	void bxTargetChanged(BxDebugEvent event);
	boolean isAvailable();
	
}
