/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.ace.rules;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Rule to prevent the user from having message events in Process Interfaces:
 * "Incoming web-service message events. Use 'type none' instead and invoke
 * these thru run-time API if required."
 *
 * @author sajain
 * @since Jun 19, 2019
 */
public class ProcessInterfaceMessageEventsRule extends InterfaceBaseValidationRule{

    private static final String ID_IFC_MSG_EVENTS_NOT_SUPPORTED =
            "ace.interface.message.event.not.supported"; //$NON-NLS-1$
	
	@Override
	public void validate(ProcessInterface processInterface) {
        /*
         * Validate start methods.
         */
        List<StartMethod> allStartMethods = processInterface.getStartMethods();
        if (null != allStartMethods && !allStartMethods.isEmpty()) {
            for (StartMethod eachStartMethod : allStartMethods) {
                if (TriggerType.MESSAGE_LITERAL
                        .equals(eachStartMethod.getTrigger())) {
                    addIssue(ID_IFC_MSG_EVENTS_NOT_SUPPORTED, eachStartMethod);
                }
            }
        }

        /*
         * Validate intermediate methods.
         */
        List<IntermediateMethod> allIntermediateMethods =
                processInterface.getIntermediateMethods();
        if (null != allIntermediateMethods
                && !allIntermediateMethods.isEmpty()) {
            for (IntermediateMethod eachIntermediateMethod : allIntermediateMethods) {
                if (TriggerType.MESSAGE_LITERAL
                        .equals(eachIntermediateMethod.getTrigger())) {
                    addIssue(ID_IFC_MSG_EVENTS_NOT_SUPPORTED,
                            eachIntermediateMethod);
                }
            }
        }
	}

}
