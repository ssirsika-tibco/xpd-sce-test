package com.tibco.bx.debug.core.sourcelookup;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;

/**
 * Process source lookup director. For Process source lookup there is one source
 * lookup participant. 
 */
public class BxSourceLookupDirector extends AbstractSourceLookupDirector {
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceLookupDirector#initializeParticipants()
	 */
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[]{new BxSourceLookupParticipant()});
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.sourcelookup.ISourceLookupDirector#getSourceElement(java.lang.Object)
	 */
	public Object getSourceElement(Object element) {
		if(element instanceof IBxStackFrame||element instanceof IBxThread){
			return element;
		}else{
			return super.getSourceElement(element);
		}
			
	}

	@Override
	public void launchRemoved(ILaunch launch) {
		IDebugTarget[] targets = launch.getDebugTargets();
		for (IDebugTarget target : targets) {
			if(target instanceof BxDebugTarget){
				((BxDebugTarget)target).dispose();
			}
		}
		super.launchRemoved(launch);
	}
	
	
}
