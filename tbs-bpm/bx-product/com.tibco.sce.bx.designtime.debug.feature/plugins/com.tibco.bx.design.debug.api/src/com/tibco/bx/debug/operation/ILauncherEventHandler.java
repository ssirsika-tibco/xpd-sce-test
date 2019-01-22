package com.tibco.bx.debug.operation;

public interface ILauncherEventHandler {
	void setInvokeType(boolean isSoapType);
    void beforeLauncher(String requestSoap);
    void afterLauncher(String responseSoap);
    boolean isFinished();
}
