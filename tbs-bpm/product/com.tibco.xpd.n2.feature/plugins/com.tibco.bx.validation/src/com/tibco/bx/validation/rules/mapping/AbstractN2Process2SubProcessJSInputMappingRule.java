/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.properties.AbstractInvokedProcessParameterItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;

/**
 * 
 * 
 * @author nwilson
 * @since 13 Apr 2015
 */
public abstract class AbstractN2Process2SubProcessJSInputMappingRule extends
        AbstractN2Process2ProcessJSInputMappingRule {

    /**
     * 
     * @return The mapping target tree item provider.
     */
    protected abstract AbstractInvokedProcessParameterItemProvider doCreateMappingTargetItemProvider();

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createTargetInfoProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        final AbstractInvokedProcessParameterItemProvider currentActivityTargetItemProvider =
                doCreateMappingTargetItemProvider();

        /*
         * Sub-class info provider to count Mandatory flag as
         * "minimum = 1 for target parameters."
         */
        return new ProcessDataMappingRuleInfoProvider(
                currentActivityTargetItemProvider) {
            /**
             * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#getMinimumInstances(java.lang.Object)
             * 
             * @param objectInTree
             * @return
             */
            @Override
            public int getMinimumInstances(Object objectInTree) {
                if (objectInTree instanceof ConceptPath) {
                    if (currentActivityTargetItemProvider
                            .isMandatoryParameter((ConceptPath) objectInTree)) {
                        return 1;
                    }
                    return 0;
                }

                return super.getMinimumInstances(objectInTree);
            }
        };
    }

}
