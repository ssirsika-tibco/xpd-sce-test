/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.implementer.script.RestActivityAdapterFactory;
import com.tibco.xpd.implementer.script.RestActivityMessageProvider;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Abstract filter which selects only Catch Error events where the error is thrown from a supported Service
 * invocation activity.
 * 
 * @author ssirsika
 * @since 24 Oct 2024
 */
public abstract class AbstractRestCatchInMappingFilter implements IFilter {

	protected RestServiceTaskAdapter rsta;

	public AbstractRestCatchInMappingFilter() {
		rsta = new RestServiceTaskAdapter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean select(Object toTest) {
	    if (!CapabilityUtil.isDeveloperActivityEnabled()) {
	        return false;
	    }
	
	    EObject eo = null;
	    boolean result = false;
	    if (toTest instanceof EObject) {
	        eo = (EObject) toTest;
	    } else if (toTest instanceof IAdaptable) {
	        eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
	    }
	
	    // Check it's a catch Error intermediate event.
	    if (eo != null && eo instanceof Activity) {
	        Activity activity = (Activity) eo;
	
	        if (activity.getEvent() instanceof IntermediateEvent) {
	            IntermediateEvent event =
	                    (IntermediateEvent) activity.getEvent();
	
	            if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {
	                //
	                // Check if it's a catch all, catch by name or catch
	                // specific error thrown by end error event.
	                //
	                Object thrower =
	                        BpmnCatchableErrorUtil.getErrorThrower(activity);
	                if (thrower instanceof Activity) {
	
	                    if (rsta.isRestServiceImplementation((Activity) thrower)) {
	                    	//Add an extra check to ensure activity has an supported associated service
	        				RsoType rsoType = rsta.getRsoType((Activity) thrower);
	
							if (getSupportedRSOType().equals(rsoType)) {
	
								RestActivityMessageProvider messageAdapter = RestActivityAdapterFactory.getInstance()
										.getMessageProvider((Activity) thrower);
	
								result = messageAdapter != null && messageAdapter.hasMappingIn((Activity) thrower);
							}
	                    }
	
	                }
	            }
	        }
	    }
	
	    return result;
	}

	/**
	 * Child class should provide supported {@link RsoType} for respective filter
	 * @return
	 */
	protected abstract RsoType getSupportedRSOType();
}