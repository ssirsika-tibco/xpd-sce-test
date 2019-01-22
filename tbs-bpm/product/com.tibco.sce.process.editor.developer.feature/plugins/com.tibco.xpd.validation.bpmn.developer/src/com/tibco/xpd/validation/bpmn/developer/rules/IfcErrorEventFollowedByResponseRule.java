/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (17 Feb 2010)
 */
public class IfcErrorEventFollowedByResponseRule extends
        InterfaceBaseValidationRule {

    private static final String IFC_ERROR_FOLLOWED_BY_RESPONSE =
            "bpmn.dev.ifcErrResponseRequired"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule#validate(com.tibco.xpd.xpdExtension.ProcessInterface)
     * 
     * @param processInterface
     */
    @Override
    public void validate(ProcessInterface processInterface) {

        Boolean shouldGenerateWSDLForProcessInterfaceDestinations =
                ProcessDestinationUtil
                        .shouldGenerateWSDLForProcessInterfaceDestinations(processInterface);
        if (shouldGenerateWSDLForProcessInterfaceDestinations) {
            for (StartMethod startMethod : processInterface.getStartMethods()) {
                validateInterfaceMethod(startMethod);
            }
            for (IntermediateMethod intermediateMethod : processInterface
                    .getIntermediateMethods()) {
                if (TriggerType.MESSAGE_LITERAL.equals(intermediateMethod
                        .getTrigger())) {
                    validateInterfaceMethod(intermediateMethod);
                }
            }
        }

    }

    /**
     * @param interfaceMethod
     */
    private void validateInterfaceMethod(InterfaceMethod interfaceMethod) {
        if (!interfaceMethod.getErrorMethods().isEmpty()) {
            if (!ImplementInterfaceUtil.isRequestResponse(interfaceMethod)) {
                addIssue(IFC_ERROR_FOLLOWED_BY_RESPONSE, interfaceMethod);
            }
        }
    }

}
