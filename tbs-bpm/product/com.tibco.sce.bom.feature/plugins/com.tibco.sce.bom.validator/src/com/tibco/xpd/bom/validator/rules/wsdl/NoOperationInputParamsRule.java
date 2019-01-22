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
import org.eclipse.uml2.uml.ParameterDirectionKind;

import com.tibco.xpd.bom.validator.WsdlIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * WSDL validation rule to check if there are any input parameters in an
 * operation. Fails if operation has no input params
 * 
 * @author glewis
 */
public class NoOperationInputParamsRule extends AbstractWsdlRule {

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
        int nInputParams = 0;
        while (iterator.hasNext()) {
            Parameter parameter = iterator.next();
            if (parameter.getDirection().equals(
                    ParameterDirectionKind.IN_LITERAL)) {
                nInputParams++;
            }
        }

        if (nInputParams == 0) {
            scope.createIssue(WsdlIssueIds.NO_OPERATION_INPUT_PARAM,
                    BOMValidationUtil.getLocation((NamedElement) o), uri,
                    Collections.singleton(tempOperation.getName()));
        }
    }
}
