/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

/**
 * This rule abstracts the similarities of requirements for validation of
 * mappings in activities that effectively map sub-process output data to
 * invoking process data.
 * <p>
 * Currently this includes...
 * <li>Reusable Sub-Process Task.</li>
 * <li>Decision Service Task.</li>
 * <p>
 * The rules and restrictions applicable to these situations are likely to
 * remain identical in most aspects.
 * 
 * @author aallway
 * @since 21 Dec 2010
 */
public abstract class AbstractProcess2ProcessJSOutputMappingRule extends
        AbstractProcessData2ProcessDataJSMappingRule {

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {

        super.performAdditionalMappingValidation(mapping,
                sourceObjectInTree,
                targetObjectInTree);

        /*
         * XPD-3793 DO NOT need to Check the mode of the target parameter (allow
         * output to IN mode associated param
         */

        return;
    }

}
