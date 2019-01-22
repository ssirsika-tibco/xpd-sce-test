/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.generic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;

import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate if Operation Parmeters name has valid name.
 * 
 * @author wzurek
 */
public class OperationParametersDuplicateRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return Operation.class;
    }

    @SuppressWarnings("unchecked")
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Operation) {
            Operation operation = (Operation) o;

            Set<String> set = new HashSet<String>();
            EList<Parameter> params = operation.getOwnedParameters();
            for (Parameter param : params) {
                if (param.getDirection() == ParameterDirectionKind.IN_LITERAL) {
                    boolean checkDuplicates = set.add(param.getName());
                    if (!checkDuplicates) {
                        scope.createIssue(
                                GenericIssueIds.OPERATION_DUPLICATE_PARAMETERS,
                                operation.getName(), operation.eResource()
                                        .getURIFragment(operation));
                        return;
                    }
                }
            }
        }
    }
}
