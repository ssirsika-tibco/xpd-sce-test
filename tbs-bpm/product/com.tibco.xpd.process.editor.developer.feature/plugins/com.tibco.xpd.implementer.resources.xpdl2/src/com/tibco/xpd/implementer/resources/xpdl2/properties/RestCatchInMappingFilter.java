package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.implementer.script.RestActivityAdapterFactory;
import com.tibco.xpd.implementer.script.RestActivityMessageProvider;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Selects only Catch Error events where the error is thrown from a REST Service
 * invocation activity.
 * 
 * @author nwilson
 */
public class RestCatchInMappingFilter implements IFilter {

    private RestServiceTaskAdapter rsta;

    public RestCatchInMappingFilter() {
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
                            RestActivityMessageProvider messageAdapter =
                                    RestActivityAdapterFactory
                                            .getInstance()
                                            .getMessageProvider((Activity) thrower);

                            result =
                                    messageAdapter != null
                                            && messageAdapter
                                                    .hasMappingIn((Activity) thrower);
                        }

                    }
                }
            }
        }

        return result;
    }
}