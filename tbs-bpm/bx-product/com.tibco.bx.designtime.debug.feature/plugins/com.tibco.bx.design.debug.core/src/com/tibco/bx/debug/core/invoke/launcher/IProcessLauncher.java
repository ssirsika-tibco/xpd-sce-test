package com.tibco.bx.debug.core.invoke.launcher;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public interface IProcessLauncher {

	public Object launch(ILauncherEventHandler handle);
	
	public boolean isAvailable();
	
	public boolean isSOAPMessage();
	
	public void setProcessListing(IBxProcessListing processListing);
	
	public void setInput(Object input);
	
	public void setSoapRequestMessage(String soapMessage);
	
	public void clean();
	
	public ProcessTemplate getProcessTemplate();
}
