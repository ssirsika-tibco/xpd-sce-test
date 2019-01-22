/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate the {@link OrgUnitRelationship}.
 * 
 * @author njpatel
 */
public class AssociationRule implements IValidationRule {

    private static final String ISSUE_ASSOC_NOT_USED =
            "om.OrgUnitRelationship.associationNoLongerUsedInRuntime"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return OrgUnitRelationship.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof OrgUnitRelationship) {
            OrgUnitRelationship relationship = (OrgUnitRelationship) obj;

            /*
             * Non-hierarchical associations have no meaning at runtime any
             * longer.
             */
            if (!relationship.isIsHierarchical()) {
                scope.createIssue(ISSUE_ASSOC_NOT_USED,
                        relationship.getDisplayName(),
                        relationship.eResource().getURIFragment(relationship));
            }
        }
    }
}
