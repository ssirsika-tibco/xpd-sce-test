package com.tibco.bx.emulation.core.invoke;

public interface IEmulationRunnerListener {
	
	void updateExecutionStatus(int eventType, String message, Object data);

}
