/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Validation rule to check that array parameters are not present in the process
 * interface BPM destination packages for WSDL operation generating events.
 * 
 * @author rsomayaj
 * @since 3.3 (5 May 2010)
 */
public class ProcessIfcArrayParamsNotSupportedRule extends
        InterfaceBaseValidationRule {

    /**
     * Array parameters are not supported, instead please create a business
     * object class to contain the array.
     */
    private static final String ARRAY_NOT_SUPPORTED =
            "cds.process.arrayfieldsNotAllowed"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule#validate(com.tibco.xpd.xpdExtension.ProcessInterface)
     * 
     * @param processInterface
     */
    @Override
    public void validate(ProcessInterface processInterface) {
        List<StartMethod> startMethods = processInterface.getStartMethods();
        for (StartMethod startMethod : startMethods) {
            if (TriggerType.MESSAGE_LITERAL.equals(startMethod.getTrigger())) {
                validateInterfaceMethodForAssocParams(startMethod);
            }
        }

        List<IntermediateMethod> intermediateMethods =
                processInterface.getIntermediateMethods();
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            // None Intermediate methods are not used for WSDL generation.
            if (TriggerType.MESSAGE_LITERAL.equals(intermediateMethod
                    .getTrigger())) {
                validateInterfaceMethodForAssocParams(intermediateMethod);
            }
        }

    }

    /**
     * @param startMethod
     */
    private void validateInterfaceMethodForAssocParams(
            InterfaceMethod interfaceMethod) {
        List<FormalParameter> interfaceMethodAssociatedFormalParameters =
                ProcessInterfaceUtil
                        .getInterfaceMethodAssociatedFormalParameters(interfaceMethod);
        validateParamsIfArray(interfaceMethod,
                interfaceMethodAssociatedFormalParameters);
        List<ErrorMethod> errorMethods = interfaceMethod.getErrorMethods();

        for (ErrorMethod errorMethod : errorMethods) {
            List<FormalParameter> errorMethodAssociatedFormalParameters =
                    ProcessInterfaceUtil
                            .getErrorMethodAssociatedFormalParameters(errorMethod);
            validateParamsIfArray(errorMethod,
                    errorMethodAssociatedFormalParameters);
        }

    }

    /**
     * @param formalParams
     */
    private void validateParamsIfArray(EObject eobj,
            List<FormalParameter> formalParams) {
        for (FormalParameter formalParameter : formalParams) {
            if (formalParameter.isIsArray()) {
                addIssue(ARRAY_NOT_SUPPORTED, eobj);
            }
        }
    }
}
