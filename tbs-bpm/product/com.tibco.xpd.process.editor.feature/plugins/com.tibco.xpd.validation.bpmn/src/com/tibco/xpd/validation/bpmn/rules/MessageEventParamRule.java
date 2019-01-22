/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author rsomayaj
 * 
 */
public class MessageEventParamRule extends InterfaceBaseValidationRule {

    private static String ID = "bpmn.processinterface.messageEventParam"; //$NON-NLS-1$

    @Override
    public void validate(ProcessInterface processInterface) {
        boolean marked = false;
        if (processInterface.getFormalParameters().isEmpty()) {
            List<StartMethod> startMethods = processInterface.getStartMethods();
            for (StartMethod startMethod : startMethods) {
                if (TriggerType.MESSAGE_LITERAL == startMethod.getTrigger()) {
                    addIssue(ID, processInterface);
                    marked = true;
                    break;
                }
            }
            if (!marked) {
                List<IntermediateMethod> intermediateMethods =
                        processInterface.getIntermediateMethods();
                for (IntermediateMethod intermediateMethod : intermediateMethods) {
                    if (TriggerType.MESSAGE_LITERAL == intermediateMethod
                            .getTrigger()) {
                        addIssue(ID, processInterface);
                        break;
                    }
                }
            }
        }
    }

}
