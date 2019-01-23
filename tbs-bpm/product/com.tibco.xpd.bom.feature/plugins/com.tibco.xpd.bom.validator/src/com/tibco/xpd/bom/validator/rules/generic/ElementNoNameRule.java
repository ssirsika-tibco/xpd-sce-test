/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.generic;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.LiteralSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate if the element has a name.
 * 
 * @author wzurek
 */
public class ElementNoNameRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof NamedElement && !(o instanceof LiteralSpecification)) {
            NamedElement element = (NamedElement) o;
            String elementName = element.getName();
            String uri = element.eResource().getURIFragment(element);

            if (elementName == null || elementName.trim().length() == 0) {

                // Associations don't need to have a name
                if (element instanceof Association
                        && !(element instanceof AssociationClass)) {
                    return;
                }

                // special case for return type of operation parameter
                if (element instanceof Parameter
                        && ((Parameter) element).getDirection() != ParameterDirectionKind.IN_LITERAL) {
                    return;
                }

                if (o instanceof Property) {
                    Property prop = (Property) o;

                    if ((prop.eContainer() != null)
                            && (prop.eContainer() instanceof AssociationClass)) {
                        return;
                    }
                }

                scope.createIssue(GenericIssueIds.ELEMENT_NO_NAME,
                        BOMValidationUtil.getLocation(element),
                        uri);
            }
        }
    }
}
