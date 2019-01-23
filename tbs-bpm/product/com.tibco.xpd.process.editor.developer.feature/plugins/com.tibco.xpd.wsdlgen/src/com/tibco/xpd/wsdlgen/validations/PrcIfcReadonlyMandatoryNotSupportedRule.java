/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.validations;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule that warns the user if the process interface methods or error
 * methods have any readonly or mandatory parameters associated with them.
 * 
 * @author rsomayaj
 * @since 3.3 (6 Jul 2010)
 */
public class PrcIfcReadonlyMandatoryNotSupportedRule extends
        InterfaceBaseValidationRule {

    private static final String READONLY_IGNORED =
            "bpmn.warning.readonlyignored";

    private static final String NON_MANDATORY_STILL_REQD_MESSAGE_PART =
            "bpmn.warning.nonMandatoryInsignificant";

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
                validateReadonlyParameters(startMethod,
                        ProcessInterfaceUtil
                                .getInterfaceMethodAssociatedFormalParameters(startMethod));

                validateMandatoryParameters(processInterface, startMethod);
                List<ErrorMethod> errorMethods = startMethod.getErrorMethods();
                for (ErrorMethod errorMethod : errorMethods) {
                    validateReadonlyParameters(errorMethod,
                            ProcessInterfaceUtil
                                    .getErrorMethodAssociatedFormalParameters(errorMethod));
                    validateMandatoryParameters(processInterface, errorMethod);
                }
            }
        }

        List<IntermediateMethod> intermediateMethods =
                processInterface.getIntermediateMethods();
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            if (TriggerType.MESSAGE_LITERAL.equals(intermediateMethod
                    .getTrigger())) {
                validateReadonlyParameters(intermediateMethod,
                        ProcessInterfaceUtil
                                .getInterfaceMethodAssociatedFormalParameters(intermediateMethod));

                validateMandatoryParameters(processInterface,
                        intermediateMethod);
                List<ErrorMethod> errorMethods =
                        intermediateMethod.getErrorMethods();
                for (ErrorMethod errorMethod : errorMethods) {
                    validateReadonlyParameters(errorMethod,
                            ProcessInterfaceUtil
                                    .getErrorMethodAssociatedFormalParameters(errorMethod));
                    validateMandatoryParameters(processInterface, errorMethod);
                }
            }
        }

    }

    /**
     * @param processInterface
     * @param assocParamsContainer
     */
    private void validateMandatoryParameters(ProcessInterface processInterface,
            AssociatedParametersContainer assocParamsContainer) {
        List<AssociatedParameter> associatedParameters =
                assocParamsContainer.getAssociatedParameters();
        if (associatedParameters.isEmpty()) {
            // Use all formal Parameters of the process interface
            List<FormalParameter> formalParameters =
                    processInterface.getFormalParameters();
            for (FormalParameter formalParameter : formalParameters) {
                if (!(formalParameter.isRequired())) {
                    addIssue(NON_MANDATORY_STILL_REQD_MESSAGE_PART,
                            assocParamsContainer,
                            Collections.singletonList(Xpdl2ModelUtil
                                    .getDisplayName(formalParameter)));
                }
            }
        } else {
            /*
             * Sid XPD-2087: Only use all data implicitly if implicit
             * association has not been disabled.
             */
            if (!ProcessInterfaceUtil
                    .isImplicitAssociationDisabled(assocParamsContainer)) {
                for (AssociatedParameter associatedParameter : associatedParameters) {
                    if (!associatedParameter.isMandatory()) {
                        ProcessRelevantData processRelevantDataFromAssociatedParam =
                                ProcessInterfaceUtil
                                        .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                        addIssue(NON_MANDATORY_STILL_REQD_MESSAGE_PART,
                                assocParamsContainer,
                                Collections.singletonList(Xpdl2ModelUtil
                                        .getDisplayName(processRelevantDataFromAssociatedParam)));
                    }
                }
            }
        }
    }

    /**
     * @param eObject
     *            - either {@link InterfaceMethod} or {@link ErrorMethod}
     * @param list
     *            of formal parameters associated with the interface/error
     *            method
     */
    private void validateReadonlyParameters(EObject eObject,
            List<FormalParameter> interfaceMethodAssociatedFormalParameters) {

        for (FormalParameter formalParameter : interfaceMethodAssociatedFormalParameters) {
            if (formalParameter.isReadOnly()) {
                addIssue(READONLY_IGNORED, eObject);
            }
        }
    }
}
