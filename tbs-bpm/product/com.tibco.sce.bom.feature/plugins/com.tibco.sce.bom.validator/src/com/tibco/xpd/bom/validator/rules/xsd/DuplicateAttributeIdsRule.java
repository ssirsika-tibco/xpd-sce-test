/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * XSD Validation Rule making it impossible to have 2 attributes with ID type in
 * a given Class
 * 
 * @author glewis
 */
public class DuplicateAttributeIdsRule extends AbstractXsdRule {

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        org.eclipse.uml2.uml.Class tempCls = (org.eclipse.uml2.uml.Class) o;
        String uri = tempCls.eResource().getURIFragment(tempCls);
        String name = tempCls.getName();

        EList<Property> allAttributes = tempCls.getAllAttributes();
        int idCount = 0;
        Iterator<Property> attIter = allAttributes.iterator();
        while (attIter.hasNext()) {
            Property property = attIter.next();
            Type type = property.getType();
            Type idType = PrimitivesUtil.getStandardPrimitiveTypeByName(tempCls
                    .eResource().getResourceSet(),
                    PrimitivesUtil.BOM_PRIMITIVE_ID_NAME);
            if (idType.equals(type)) {
                idCount++;
            }

        }

        if (idCount > 1) {
            scope.createIssue(XsdIssueIds.DUPLICATE_ATTRIBUTE_IDS,
                    BOMValidationUtil.getLocation((NamedElement) o), uri,
                    Collections.singleton(name));
        }
    }
}
