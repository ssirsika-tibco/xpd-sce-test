/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 * 
 * 
 */

package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * 
 * All features should be typed
 * 
 * @author rgreen
 * 
 */
public class UntypedFeatureRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return OrgTypedElement.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof OrgTypedElement) {
            OrgTypedElement ote = (OrgTypedElement) o;

            if (ote instanceof OrgUnit || ote instanceof Position) {
                if (ote instanceof OrgUnit) {
                    OrgUnit ou = (OrgUnit) ote;

                    OrgUnitFeature ouFeature = ou.getFeature();

                    if (ouFeature != null) {
                        if (ouFeature.getFeatureType() == null) {
                            List<String> additionalMessages =
                                    new ArrayList<String>();
                            additionalMessages.add(ouFeature.getDisplayName());

                            createOUIssue(scope,
                                    ote,
                                    GenericIssueIds.ORGTYPEDELEMENT_FEATURE_TYPE_NOT_SET,
                                    additionalMessages);

                        }
                    }

                }

                if (ote instanceof Position) {
                    Position pos = (Position) ote;
                    PositionFeature pFeature = pos.getFeature();

                    if (pFeature != null) {
                        if (pFeature.getFeatureType() == null) {
                            List<String> additionalMessages =
                                    new ArrayList<String>();
                            additionalMessages.add(pFeature.getDisplayName());

                            createOUIssue(scope,
                                    ote,
                                    GenericIssueIds.ORGTYPEDELEMENT_FEATURE_TYPE_NOT_SET,
                                    additionalMessages);

                        }
                    }
                }

            }

        }
    }

    private void createOUIssue(IValidationScope scope, OrgTypedElement ote,
            String issueId, List<String> messages) {
        scope.createIssue(issueId, ote.getDisplayName(), ote.eResource()
                .getURIFragment(ote), messages);
    }

}
