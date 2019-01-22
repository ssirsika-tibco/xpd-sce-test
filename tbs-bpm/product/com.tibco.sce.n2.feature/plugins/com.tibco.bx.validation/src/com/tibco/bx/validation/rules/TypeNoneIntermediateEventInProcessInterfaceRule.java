/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Rule to make sure that we don't allow Type none intermediate events in a
 * process interface.
 * 
 * @author sajain
 * @since May 29, 2015
 */
public class TypeNoneIntermediateEventInProcessInterfaceRule extends
        InterfaceBaseValidationRule {

    /**
     * Intermediate Events of type 'None' are not supported in a Process
     * Interface.
     */
    private static final String TYPE_NONE_INTERMEDIATE_EVENTS_ARENT_SUPPORTED_IN_PROC_IFC =
            "bx.typeNoneIntermediateEventsNotSupportedInProcIfc"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(ProcessInterface processInterface) {

        List<IntermediateMethod> intermediateMethods =
                processInterface.getIntermediateMethods();

        for (IntermediateMethod intermediateMethod : intermediateMethods) {

            if (TriggerType.NONE_LITERAL
                    .equals(intermediateMethod.getTrigger())) {

                addIssue(TYPE_NONE_INTERMEDIATE_EVENTS_ARENT_SUPPORTED_IN_PROC_IFC,
                        intermediateMethod);

            }
        }
    }
}
