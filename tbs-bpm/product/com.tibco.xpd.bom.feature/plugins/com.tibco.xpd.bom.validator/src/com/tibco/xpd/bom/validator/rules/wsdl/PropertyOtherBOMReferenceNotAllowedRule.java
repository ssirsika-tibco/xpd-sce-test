/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdl;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.WsdlIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * A WSDL validation rule ensuring properties only have reference to other
 * classifiers within same BOM Model
 * 
 * @author glewis
 */
public class PropertyOtherBOMReferenceNotAllowedRule extends AbstractWsdlRule {

    @Override
    public Class<?> getTargetClass() {
        return Property.class;
    }

    @Override
    public void performWSDLValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        Property tempProperty = (Property) o;
        String uri = tempProperty.eResource().getURIFragment(tempProperty);
        IFile bomFile = WorkspaceSynchronizer.getFile(tempProperty.eResource());

        Type propertyType = tempProperty.getType();
        if (propertyType != null
                && !(tempProperty.eResource().equals(propertyType.eResource()))) {
            Collection<String> standardPrimtiveTypeNames = PrimitivesUtil
                    .getStandardPrimtiveTypeNames(tempProperty.eResource()
                            .getResourceSet());
            boolean isIssue = true;
            for (String primName : standardPrimtiveTypeNames) {
                PrimitiveType standardPrimType = PrimitivesUtil
                        .getStandardPrimitiveTypeByName(tempProperty
                                .eResource().getResourceSet(), primName);
                if (propertyType.equals(standardPrimType)) {
                    isIssue = false;
                    break;
                }
            }
            if (isIssue) {
                scope.createIssue(WsdlIssueIds.PROPERTY_BOM_REF_NOT_ALLOWED,
                        BOMValidationUtil.getLocation((NamedElement) o), uri,
                        Collections.singleton(tempProperty.getName()));
            }
        }
    }
}
