package com.tibco.xpd.om.validator.rules;

import static com.tibco.xpd.om.validator.GenericIssueIds.INVALID_MEMBER;
import static com.tibco.xpd.om.validator.GenericIssueIds.MEMBER_WITHOUT_PARENT;

import java.util.Arrays;

import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class OrgUnitMemberRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return OrgUnit.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof OrgUnit) {
            OrgUnit orgUnit = (OrgUnit) o;
            if (orgUnit.getFeature() != null) {
                if (orgUnit.getParentOrgUnit() == null) {
                    if (!isFeatureOfOrganization(orgUnit)) {
                        createOUIssue(scope, orgUnit, MEMBER_WITHOUT_PARENT);
                    }
                } else if (!isFeatureOfParentOrgUnit(orgUnit)) {
                    createOUIssue(scope, orgUnit, INVALID_MEMBER);
                }
            }
        }
    }

    private boolean isFeatureOfParentOrgUnit(OrgUnit orgUnit) {
        OrgUnitFeature f = orgUnit.getFeature();
        OrgUnit parentOU = orgUnit.getParentOrgUnit();
        if (f != null && parentOU != null && parentOU.getType() != null
                && parentOU.getOrgUnitType().getOrgUnitFeatures().contains(f)) {
            return true;
        }
        return false;
    }

    private boolean isFeatureOfOrganization(OrgUnit orgUnit) {
        OrgUnitFeature f = orgUnit.getFeature();
        if (f != null
                && orgUnit.getOrganization() != null
                && orgUnit.getOrganization().getOrganizationType() != null
                && orgUnit.getOrganization().getOrganizationType()
                        .getOrgUnitFeatures().contains(f)) {
            return true;
        }
        return false;
    }

    private void createOUIssue(IValidationScope scope, OrgUnit orgUnit,
            String issueId) {
        scope.createIssue(issueId, orgUnit.getDisplayName(), orgUnit
                .eResource().getURIFragment(orgUnit), Arrays.asList(orgUnit
                .getDisplayName(), ((OrgElementType) orgUnit.getFeature()
                .eContainer()).getDisplayName()));
    }
}
