/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdl;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;

import com.tibco.xpd.bom.validator.WsdlIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * WSDL validation rule to check multiplicity in a parameter in an operation.
 * Fails if the param is an array basically!
 * 
 * @author glewis
 */
public class OperationParamMultiplicityNotSupportedRule extends
        AbstractWsdlRule {

    @Override
    public Class<?> getTargetClass() {
        return Operation.class;
    }

    @Override
    public void performWSDLValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        Operation tempOperation = (Operation) o;
        String uri = tempOperation.eResource().getURIFragment(tempOperation);

        EList<Parameter> ownedParameters = tempOperation.getOwnedParameters();
        Iterator<Parameter> iterator = ownedParameters.iterator();
        while (iterator.hasNext()) {
            Parameter parameter = iterator.next();
            if (parameter.getUpper() > 1 || parameter.getUpper() == -1) {
                if (parameter.getName() == null) {
                    scope
                            .createIssue(
                                    WsdlIssueIds.MULTIPLICITY_NOT_SUPPORTED_OUTPUT_PARAM,
                                    BOMValidationUtil
                                            .getLocation((NamedElement) o),
                                    uri, Collections.singleton(tempOperation
                                            .getName()));
                } else {
                    scope
                            .createIssue(
                                    WsdlIssueIds.MULTIPLICITY_NOT_SUPPORTED_INPUT_PARAM,
                                    BOMValidationUtil
                                            .getLocation((NamedElement) o),
                                    uri, Collections.singleton(parameter
                                            .getName()));
                }
            }
        }
    }
}
