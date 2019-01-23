package com.tibco.bx.debug.ui.views.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.ui.actions.AbstractInvokeActionProxy;

public abstract class AbstractInvokePane extends Composite {
    
    protected List<AbstractInvokeActionProxy> startActiveInvokes;
    
    private EObject startActivity;
    
    private BxDebugTarget bxTarget; 
    
    private IViewSite viewSite;
    
    private String endpointUrl;
    
    public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public BxDebugTarget getTarget() {
    	if(bxTarget == null){
    		bxTarget = TargetManager.getDefault().getCurrentTarget();
    	}
		return bxTarget;
	}

	public AbstractInvokePane(Composite parent, int style, IViewSite viewSite) {
        super(parent, style);
        this.viewSite = viewSite;
        startActiveInvokes = new ArrayList<AbstractInvokeActionProxy>();
    }
    
    public void setTarget(BxDebugTarget target){
    	bxTarget = target;
    	if(target == null || target.isDisconnected() 
    					  || target.isTerminated()){
    		clearCache();
    	}
    }
    
    public void selectionChanged(SelectionChangedEvent event){
    	updateStatusMessage(""); //$NON-NLS-1$
    	doSelectionChanged(event);
    }
    
    public EObject getStartActivity() {
        return startActivity;
    }
    
   public void setStartActivity(EObject startActivity ) {
        if(startActivity != this.startActivity) {
            this.startActivity = startActivity;
            inputChange();
        }
    }
   
   protected void updateStatusMessage(String message){
   	if(viewSite != null){
   		IStatusLineManager status = viewSite.getActionBars().getStatusLineManager();
   		status.setMessage(message);
   	}
   }
   
   public abstract void doSelectionChanged(SelectionChangedEvent event);
   public abstract void clearCache();
   public abstract void inputChange();
   
   public abstract List<AbstractInvokeActionProxy> getInvokeActions();

   public abstract void clear();
}
