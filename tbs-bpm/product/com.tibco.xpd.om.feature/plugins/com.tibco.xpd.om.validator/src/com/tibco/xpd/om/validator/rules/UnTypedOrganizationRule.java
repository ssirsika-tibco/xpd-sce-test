package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class UnTypedOrganizationRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof NamedElement) {

            if (o instanceof Organization) {
                Organization org = (Organization) o;

                if (org.getType() == null) {
                    String location = org.getName();
                    String fragment = org.eResource().getURIFragment(org);
                    List<String> additionalMessages = new ArrayList<String>();
                    additionalMessages.add(org.getName());

                    scope.createIssue(GenericIssueIds.UNTYPED_ORGANIZATION,
                            location,
                            fragment,
                            additionalMessages);
                }

            }
            if (o instanceof OrgUnitFeature) {
                OrgUnitFeature orgUnitFeature = (OrgUnitFeature) o;
                OrgUnitType featureType = orgUnitFeature.getFeatureType();
                if (null == featureType) {
                    String fragment =
                            orgUnitFeature.eResource()
                                    .getURIFragment(orgUnitFeature);
                    String location = orgUnitFeature.getName();
                    scope.createIssue(GenericIssueIds.UNTYPED_MEMBER,
                            location,
                            fragment,
                            Arrays.asList(orgUnitFeature.getDisplayName()));
                }
            }
            if (o instanceof PositionFeature) {
                PositionFeature positionFeature = (PositionFeature) o;
                PositionType featureType = positionFeature.getFeatureType();
                if (null == featureType) {
                    String fragment =
                            positionFeature.eResource()
                                    .getURIFragment(positionFeature);
                    String location = positionFeature.getName();
                    scope.createIssue(GenericIssueIds.UNTYPED_MEMBER,
                            location,
                            fragment,
                            Arrays.asList(positionFeature.getDisplayName()));
                }
            }
        }
    }

}
