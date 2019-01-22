/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author NWilson
 * 
 */
public class ProcessIfcInterfaceMessageMethodFilter implements IFilter {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
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

        if (eo instanceof InterfaceMethod
                && ((InterfaceMethod) eo).getTrigger() == TriggerType.MESSAGE_LITERAL
                && hasMappings((InterfaceMethod) eo)) {
            result = true;
        }
        return result;
    }

    /**
     * @param eo
     * @return
     */
    private boolean hasMappings(InterfaceMethod ifcMethod) {
        return true;
    }
}
