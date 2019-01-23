/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation of Dynamic OrgUnits.
 * 
 * @author njpatel
 */
public class DynamicOrgUnitRule implements IValidationRule {

    private static final String ISSUE_DYNAMIC_ORG_REF =
            "om.dynamicOrgUnit.DynamicOrgRef"; //$NON-NLS-1$

    private static final String ISSUE_ONLY_ONE_SUBDYNAMICUNIT =
            "om.dynamicOrgUnit.onlyOneSubDynamicUnit"; //$NON-NLS-1$

    private static final String ISSUE_DYN_ORGUNIT_CANNOT_BE_ROOT =
            "om.dynamicOrgUnit.dynamicUnitCannotBeRoot"; //$NON-NLS-1$

    private IValidationScope scope;

    @Override
    public Class<?> getTargetClass() {
        /*
         * This is OrgUnit rather than DynamicOrgUnit as we want to also
         * validate that org units don't have more than one dynamic orgunit.
         */
        return OrgUnit.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        this.scope = scope;
        if (obj instanceof DynamicOrgUnit) {
            DynamicOrgUnit dynOrgUnit = (DynamicOrgUnit) obj;

            // Dynamic organization has to be referenced
            if (dynOrgUnit.getDynamicOrganization() == null) {
                createIssue(ISSUE_DYNAMIC_ORG_REF, dynOrgUnit);
            }

            // Dynamic OrgUnit cannot be root (has to have an incoming
            // relationship)
            if (dynOrgUnit.getIncomingHierachicalRelationship() == null) {
                createIssue(ISSUE_DYN_ORGUNIT_CANNOT_BE_ROOT, dynOrgUnit);
            }

        } else if (obj instanceof OrgUnit) {
            /*
             * Only one dynamic orgunit allowed as a subunit.
             */
            int count = 0;
            OrgUnit orgUnit = (OrgUnit) obj;
            for (OrgUnit subUnit : orgUnit.getSubUnits()) {
                if (subUnit instanceof DynamicOrgUnit) {
                    if (++count > 1) {
                        break;
                    }
                }
            }

            if (count > 1) {
                createIssue(ISSUE_ONLY_ONE_SUBDYNAMICUNIT, orgUnit);
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
