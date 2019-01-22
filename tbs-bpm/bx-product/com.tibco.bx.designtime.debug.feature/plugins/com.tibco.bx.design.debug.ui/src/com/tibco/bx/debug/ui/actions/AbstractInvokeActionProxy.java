package com.tibco.bx.debug.ui.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;

import com.tibco.bx.debug.ui.views.internal.IProcessTabPaneCreator;

public abstract class AbstractInvokeActionProxy extends Action {

    private InvokeAction proxy;
    
    private EObject startActivity;
    
    protected IProcessTabPaneCreator processTabPaneCreator;
    
    public AbstractInvokeActionProxy(String text) {
        super(text);
    }
    
    public void setProxy(InvokeAction proxy) {
        this.proxy = proxy;
    }
    
    protected EObject getStartActivity() {
		return startActivity;
	}

	public void setStartActivity(EObject startActivity){
    	this.startActivity = startActivity;
    }
    
    protected InvokeAction getProxy() {
		return proxy;
	}

	public void setProcessTabPaneCreator(
            IProcessTabPaneCreator processTabPaneCreator) {
        this.processTabPaneCreator = processTabPaneCreator;
    }


}
