/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.decisions.internal.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractInvokedProcessParameterItemProvider;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author mtorres
 */
public class DecFlowParameterItemProvider extends
        AbstractInvokedProcessParameterItemProvider {

    /**
     * @param direction
     */
    public DecFlowParameterItemProvider(MappingDirection direction) {
        super(direction);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractInvokedProcessParameterItemProvider#getInvokedProcessOrInterface(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected EObject getInvokedProcessOrInterface(Activity activity) {
        return DecisionTaskObjectUtil.getDecisionFlow(activity);
    }

}