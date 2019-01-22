/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.WsdlIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * WSDL validation rule to check operations have parameters of types that are
 * only within this bom scope Fails if referencing another type from another bom
 * 
 * @author glewis
 */
public class OperationOtherBOMReferenceNotAllowedRule extends AbstractWsdlRule {

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
            Type propertyType = parameter.getType();
            if (propertyType != null
                    && !(tempOperation.eResource().equals(propertyType
                            .eResource()))) {
                Collection<String> standardPrimtiveTypeNames =
                        PrimitivesUtil
                                .getStandardPrimtiveTypeNames(tempOperation
                                        .eResource().getResourceSet());
                boolean isIssue = true;
                for (String primName : standardPrimtiveTypeNames) {
                    PrimitiveType standardPrimType =
                            PrimitivesUtil
                                    .getStandardPrimitiveTypeByName(tempOperation
                                            .eResource().getResourceSet(),
                                            primName);
                    if (propertyType.equals(standardPrimType)) {
                        isIssue = false;
                        break;
                    }
                }
                if (isIssue) {
                    String paramName = parameter.getName();
                    if (paramName == null) {
                        paramName = "Return Type"; ////$NON-NLS-1$
                    }
                    scope
                            .createIssue(WsdlIssueIds.OPERATION_PARAMETER_BOM_REF_NOT_ALLOWED,
                                    BOMValidationUtil
                                            .getLocation((NamedElement) o),
                                    uri,
                                    Collections.singleton(paramName));
                }
            }

        }
    }
}
