/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.CatchWsdlErrorEventUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Filter for catch error intermediate events that are set to catch a specific
 * WSDL fault thrown by an activity that invokes a web service operation.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchWsdlThrownErrorEventFilter implements IFilter,
        IPluginContribution {
    @Override
    public boolean select(Object toTest) {
        if (!CapabilityUtil.isDeveloperActivityEnabled()) {
            return false;
        }

        IPluginContribution pluginContributon = this;
        if (pluginContributon != null
                && WorkbenchActivityHelper.filterItem(pluginContributon)) {
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
                if (ScriptGrammarFactory.JAVASCRIPT.equals(ScriptGrammarFactory
                        .getGrammarToUse(activity, DirectionType.OUT_LITERAL))
                        || ScriptGrammarFactory.XPATH
                                .equals(ScriptGrammarFactory
                                        .getGrammarToUse(activity,
                                                DirectionType.OUT_LITERAL))) {
                    if (TriggerType.ERROR_LITERAL.equals(event.getTrigger())) {
                        //
                        // Check if it's a catch all, catch by name or catch
                        // specific error thrown by end error event.
                        //
                        Object thrower =
                                BpmnCatchableErrorUtil
                                        .getErrorThrower(activity);
                        if (thrower instanceof Activity) {

                            result =
                                    CatchWsdlErrorEventUtil
                                            .isWsdlFaultThrowingActivityType((Activity) thrower);

                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    @Override
    public String getLocalId() {
        return "developer.catchWsdlErrorMapperOut"; //$NON-NLS-1$
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

}
