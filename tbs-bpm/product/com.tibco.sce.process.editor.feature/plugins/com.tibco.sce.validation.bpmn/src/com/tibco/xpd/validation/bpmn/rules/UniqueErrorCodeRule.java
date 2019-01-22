/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;

/**
 * Error Codes need to be unique in an interface method, this is to maintain
 * unique fault names that are generated for these error events.
 * 
 * @author rsomayaj
 * 
 */
public class UniqueErrorCodeRule extends InterfaceBaseValidationRule {

    private static final String UNIQUE_ERROR_CODES_ID =
            "bpmn.procifc.uniqueErrorCodes"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule#validate(com.tibco.xpd.xpdExtension.ProcessInterface)
     * 
     * @param processInterface
     */
    @Override
    public void validate(ProcessInterface processInterface) {
        for (StartMethod startMethod : processInterface.getStartMethods()) {
            validateErrorCodes(startMethod);
        }
        for (IntermediateMethod intermediateMethod : processInterface
                .getIntermediateMethods()) {
            validateErrorCodes(intermediateMethod);
        }
    }

    private void validateErrorCodes(InterfaceMethod interfaceMethod) {
        Map<String, Integer> errorCodes = new HashMap<String, Integer>();
        // Don't want to raise the same error more than once.
        for (ErrorMethod err : interfaceMethod.getErrorMethods()) {
            if (errorCodes.keySet().contains(err.getErrorCode())) {
                Integer count = errorCodes.get(err.getErrorCode());
                if (count != null && count.intValue() == 0) {
                    errorCodes.put(err.getErrorCode(), new Integer(1));
                    addIssue(UNIQUE_ERROR_CODES_ID,
                            interfaceMethod,
                            Collections.singletonList(err.getErrorCode()));
                }
            } else {
                errorCodes.put(err.getErrorCode(), new Integer(0));
            }
        }
    }
}
