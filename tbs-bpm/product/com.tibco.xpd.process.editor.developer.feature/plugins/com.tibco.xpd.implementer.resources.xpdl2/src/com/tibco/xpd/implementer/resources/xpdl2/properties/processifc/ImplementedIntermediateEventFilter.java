/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author NWilson
 * 
 */
public class ImplementedIntermediateEventFilter implements IFilter {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        boolean result = false;
        if (!CapabilityUtil.isDeveloperActivityEnabled()) {
            return false;
        }
        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }

        if (eo instanceof Activity) {
            Activity act = (Activity) eo;

            if (ProcessInterfaceUtil.isEventImplemented(act)
                    && (EventObjectUtil.getFlowType(act) == EventFlowType.FLOW_INTERMEDIATE_LITERAL)) {

                IntermediateMethod implementedIntermediateMethod =
                        ProcessInterfaceUtil
                                .getImplementedIntermediateMethod(act);
                if (implementedIntermediateMethod != null) {

                    if (TriggerType.MESSAGE_LITERAL == implementedIntermediateMethod
                            .getTrigger()
                    /*
                     * Sid XPD-2087: Should not matter whether the method has
                     * mappings or not as to whether we show the section!
                     */
                    /* && hasMappings(implementedStartMethod) */) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param eo
     * @return
     */
    private boolean hasMappings(InterfaceMethod ifcMethod) {
        if (ifcMethod.getTriggerResultMessage() != null) {
            Message message = ifcMethod.getTriggerResultMessage().getMessage();
            if (message != null) {
                List<DataMapping> dataMappings = message.getDataMappings();
                for (DataMapping dataMapping : dataMappings) {
                    if (DirectionType.OUT_LITERAL == dataMapping.getDirection()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
