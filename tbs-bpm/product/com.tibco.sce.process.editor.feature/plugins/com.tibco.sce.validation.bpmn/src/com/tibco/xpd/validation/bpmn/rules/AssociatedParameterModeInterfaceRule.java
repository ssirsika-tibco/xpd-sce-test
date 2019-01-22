/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author NWilson
 * 
 */
public class AssociatedParameterModeInterfaceRule extends
        InterfaceBaseValidationRule {

    private static final String MODE = "bpmn.invalidAssociatedParameterMode"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule#validate(com.tibco.xpd.xpdExtension.ProcessInterface)
     * 
     * @param processInterface
     */
    @Override
    public void validate(ProcessInterface processInterface) {
        for (StartMethod start : processInterface.getStartMethods()) {
            validateAssociatedParameters(start);
        }
        for (IntermediateMethod intermediate : processInterface
                .getIntermediateMethods()) {
            if (TriggerType.MESSAGE_LITERAL.equals(intermediate.getTrigger())) {
                validateAssociatedParameters(intermediate);
            }
        }
    }

    private void validateAssociatedParameters(
            AssociatedParametersContainer container) {
        for (AssociatedParameter assoc : container.getAssociatedParameters()) {
            ProcessRelevantData data =
                    ProcessInterfaceUtil
                            .getProcessRelevantDataFromAssociatedParam(assoc);
            if (data instanceof FormalParameter) {
                FormalParameter formal = (FormalParameter) data;
                ModeType assocMode = assoc.getMode();
                ModeType paramMode = formal.getMode();
                if (assocMode == null || !assocMode.equals(paramMode)) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(assoc.getFormalParam());
                    addIssue(MODE, container, messages);
                }
            }
        }
    }

}
