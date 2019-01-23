/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.Collections;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * XSD Validation Rule warns user that they will lose the default value upon xsd
 * export
 * 
 * @author glewis
 */
public class PrimitiveTypeDefaultLostRule extends AbstractXsdRule {

    @Override
    public Class<?> getTargetClass() {
        return PrimitiveType.class;
    }

    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        PrimitiveType primitiveType = (PrimitiveType) o;
        String uri = primitiveType.eResource().getURIFragment(primitiveType);

        if (primitiveType.getGenerals().size() > 0) {
            Classifier classifier = primitiveType.getGenerals().get(0);
            Type boolType = PrimitivesUtil.getStandardPrimitiveTypeByName(
                    primitiveType.eResource().getResourceSet(),
                    PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
            if (boolType.equals(classifier)) {
                Object val = PrimitivesUtil
                        .getFacetPropertyValue(
                                primitiveType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE);
                if (val != null && val instanceof Boolean) {
                    scope
                            .createIssue(
                                    XsdIssueIds.PRIMITIVETYPE_DEFAULT_VALUE_LOST,
                                    BOMValidationUtil
                                            .getLocation((NamedElement) o),
                                    uri, Collections.singleton(primitiveType
                                            .getName()));
                }
            }
        }
    }
}
