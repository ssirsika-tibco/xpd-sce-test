/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Dynamic Organization validation.
 * 
 * @author njpatel
 */
public class DynamicOrganizationRule implements IValidationRule {

    private static final String ISSUE_ONLY_ONE_ROOT =
            "om.dynamicOrg.OnlyOneRootUnitAllowed"; //$NON-NLS-1$

    private static final String ISSUE_ATLEAST_ONE_IDENT =
            "om.dynamicOrg.AtleastOneIdentifier"; //$NON-NLS-1$

    private static final String ISSUE_IDENT_HAS_TO_BE_UNIQUE =
            "om.dynamicOrg.IdentHasToBeUnique"; //$NON-NLS-1$

    private IValidationScope scope;

    @Override
    public Class<?> getTargetClass() {
        return Organization.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        this.scope = scope;
        if (obj instanceof Organization && ((Organization) obj).isDynamic()) {
            Organization dynamicOrg = (Organization) obj;

            /*
             * Only one root unit allowed
             */
            validateOneRootOrgUnit(dynamicOrg);
            /*
             * Validate the dynamic org identifiers
             */
            validateDynamicOrgIdentifiers(dynamicOrg);
        }
    }

    /**
     * @param dynamicOrg
     * @return
     */
    private void validateOneRootOrgUnit(Organization dynamicOrg) {
        /*
         * Find all the root org units
         */
        List<OrgUnit> roots = dynamicOrg.getSubUnits();
        if (roots.size() > 1) {
            for (OrgUnit orgUnit : roots) {
                createIssue(ISSUE_ONLY_ONE_ROOT, orgUnit);
            }
        }

        /*
         * XPD-8042: Raise the problem marker 'OM Generic : A Dynamic
         * Organization must have exactly one root Organization Unit.' on the
         * dynamic org itself if it doesn't have any org units at all.
         */
        if (roots.size() == 0) {
            createIssue(ISSUE_ONLY_ONE_ROOT, dynamicOrg);
        }

    }

    /**
     * @param dynamicOrg
     */
    private void validateDynamicOrgIdentifiers(Organization dynamicOrg) {
        EList<DynamicOrgIdentifier> identifiers =
                dynamicOrg.getDynamicOrgIdentifiers();

        if (identifiers.isEmpty()) {
            createIssue(ISSUE_ATLEAST_ONE_IDENT, dynamicOrg);
        } else {
            Set<String> labels = new HashSet<String>();
            Set<String> names = new HashSet<String>();

            boolean hasDuplicate = false;
            for (DynamicOrgIdentifier ident : identifiers) {
                String label = ident.getDisplayName();
                String name = ident.getName();

                if (labels.contains(label)) {
                    hasDuplicate = true;
                    break;
                } else {
                    labels.add(label);
                }

                if (names.contains(name)) {
                    hasDuplicate = true;
                    break;
                } else {
                    names.add(name);
                }
            }

            if (hasDuplicate) {
                createIssue(ISSUE_IDENT_HAS_TO_BE_UNIQUE, dynamicOrg);
            }
        }

    }

    /**
     * Create the given issue on the element.
     * 
     * @param issueId
     * @param element
     */
    private void createIssue(String issueId, NamedElement element) {
        scope.createIssue(issueId, element.getDisplayName(), element
                .eResource().getURIFragment(element));
    }

}
