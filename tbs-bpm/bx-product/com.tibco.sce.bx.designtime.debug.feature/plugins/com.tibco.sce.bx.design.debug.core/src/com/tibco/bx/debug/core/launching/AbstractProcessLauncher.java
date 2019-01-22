package com.tibco.bx.debug.core.launching;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.invoke.launcher.IProcessLauncher;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;

public abstract class AbstractProcessLauncher implements IProcessLauncher{

	protected IBxProcessListing processListing = null;
	
	protected EObject startActivity = null;
	
	public AbstractProcessLauncher(EObject startActivity , IBxProcessListing processListing) {
		super();
		this.startActivity = startActivity;
		this.processListing = processListing;
	}

	protected EObject getStartActivity() {
		return startActivity;
	}

	
	@Override
	public boolean isAvailable() {
		return processListing!= null;
	}

	@Override
	public void setProcessListing(IBxProcessListing processListing) {
		this.processListing = processListing;
	}
	
	
	public void setInput(Object input){
	     assert(input != null);
		
	}
	
   @Override
    public boolean isSOAPMessage() {
        return false;
    }

    @Override
    public void clean() {
        
    }

    @Override
    public void setSoapRequestMessage(String soapMessage) {
        
    }

}
