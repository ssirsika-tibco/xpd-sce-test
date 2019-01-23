/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.generic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate if Concept name has valid name.
 * 
 * Note:<br>
 * Validator to ensure no Duplicate names within a UML Package
 * 
 * @author rsomayaj
 */
public class PropertyMultiplicityRule implements IValidationRule {

    private static final String LOWER_LIMIT_EXCEEDS_UPPER_LIMIT = Messages.PropertyMultiplicityRule_LowerLimitExceedsUpper_label;
    private static final String UPPER_LIMIT_EQUAL_TO_ZERO = Messages.PropertyMultiplicityRule_UpperLimitEqualsZero_label;

    public Class<?> getTargetClass() {
        return Property.class;
    }

    @SuppressWarnings("unchecked")
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Property) {
            Property prop = (Property) o;
            List<String> additionalMessages = new ArrayList<String>();
            EObject container = prop.eContainer();

            if (prop.getUpper() == 0) {
                additionalMessages.add(UPPER_LIMIT_EQUAL_TO_ZERO);
                if (container instanceof Association) {
                    scope
                            .createIssue(GenericIssueIds.INVALID_MULTIPLICITY_ASSOCIATION,
                                    BOMValidationUtil
                                            .getLocation((NamedElement) container),
                                    container
                                            .eResource()
                                            .getURIFragment((NamedElement) container),
                                    additionalMessages);
                } else {
                    additionalMessages.add(prop.getName());
                    scope.createIssue(GenericIssueIds.INVALID_MULTIPLICITY,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource().getURIFragment(prop),
                            additionalMessages);
                }

                return;
            }

            if ((prop.getLowerValue() == null || prop.getUpperValue() == null)) {
                // one is unspecified - ok!
                return;
            } else if (prop.getUpper() == -1) {
                // upper is * - ok!
                return;
            }
            if (prop.getUpper() < prop.getLower()) {
                additionalMessages.add(LOWER_LIMIT_EXCEEDS_UPPER_LIMIT);
                if (container instanceof Association) {
                    scope
                            .createIssue(GenericIssueIds.INVALID_MULTIPLICITY_ASSOCIATION,
                                    BOMValidationUtil
                                            .getLocation((NamedElement) container),
                                    container
                                            .eResource()
                                            .getURIFragment((NamedElement) container),
                                    additionalMessages);
                } else {
                    additionalMessages.add(prop.getName());
                    scope.createIssue(GenericIssueIds.INVALID_MULTIPLICITY,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource().getURIFragment(prop),
                            additionalMessages);
                }
            }
        }
    }
}
