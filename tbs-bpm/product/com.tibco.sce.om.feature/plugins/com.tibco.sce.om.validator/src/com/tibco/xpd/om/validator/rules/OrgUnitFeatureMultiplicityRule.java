/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule that checks if an OrgUnit has a feature and if so whether
 * there are too many instances of that feature in the OrgModel.
 * 
 * @author Rob Green
 * @since 3.3
 */
public class OrgUnitFeatureMultiplicityRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return OrgUnit.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof OrgUnit) {
            OrgUnit trgOU = (OrgUnit) o;
            OrgUnit rootOU = getRootOrgUnit(trgOU);
            OrgUnitFeature targetFeature = trgOU.getFeature();

            // We only have to validate if there is a feature set on this
            // OrgUnit
            if (targetFeature != null) {
                int upperBound = targetFeature.getUpperBound();
                if (upperBound == -1) {
                    // Unlimited multiplicity so no need to check
                    return;
                }

                if (trgOU == rootOU) {
                    // This will be root elements i.e. features of the
                    // Organization
                    Organization org = trgOU.getOrganization();
                    EList<OrgUnit> allOrgUnits = org.getUnits();
                    int occurencesOfFeature = 1;

                    for (OrgUnit orgUnit : allOrgUnits) {
                        if (orgUnit.getFeature() != null && orgUnit != trgOU) {
                            OrgUnitFeature feature2 = orgUnit.getFeature();

                            if (targetFeature == feature2) {
                                // feature2 MUST have a type set otherwise its
                                // invalid anyway
                                if (feature2.getFeatureType() == null) {
                                    continue;
                                }

                                occurencesOfFeature++;
                                if (occurencesOfFeature > upperBound) {

                                    List<String> additionalMessages =
                                            new ArrayList<String>();
                                    additionalMessages.add(targetFeature
                                            .getDisplayName());

                                    additionalMessages.add(targetFeature
                                            .getFeatureType().getDisplayName());

                                    createOUIssue(scope,
                                            trgOU,
                                            GenericIssueIds.ORGUNIT_TYPE_EXCEEDED_MULTIPLICITY_UPPER_BOUND,
                                            additionalMessages);
                                }
                            }

                        }
                    }
                } else {

                    OrgUnitType orgUnitType = rootOU.getOrgUnitType();

                    if (orgUnitType != null) {
                        EList<OrgUnitFeature> rootOrgUnitFeatures =
                                orgUnitType.getOrgUnitFeatures();
                        if (rootOrgUnitFeatures.contains(targetFeature)) {
                            if (trgOU != rootOU) {
                                // Gather how many instances of this feature
                                // exist in this hierarchy tree
                                int total =
                                        getTotalFeatureNumberInHierarchy(rootOU,
                                                targetFeature);

                                if (total > upperBound) {
                                    List<String> additionalMessages =
                                            new ArrayList<String>();
                                    additionalMessages.add(targetFeature
                                            .getDisplayName());

                                    additionalMessages.add(targetFeature
                                            .getFeatureType().getDisplayName());

                                    createOUIssue(scope,
                                            trgOU,
                                            GenericIssueIds.ORGUNIT_TYPE_EXCEEDED_MULTIPLICITY_UPPER_BOUND,
                                            additionalMessages);
                                }
                            }
                        }
                    }
                }

            }

        }

    }

    /**
     * @param rootOU
     * @param feature
     * @return
     */
    private int getTotalFeatureNumberInHierarchy(OrgUnit rootOU,
            OrgUnitFeature feature) {
        int total = 0;
        List<OrgUnit> lstOUs = new ArrayList<OrgUnit>();

        List<OrgUnit> subUnits = getChildOrgUnits(rootOU, lstOUs);

        for (OrgUnit orgUnit : subUnits) {
            OrgUnitFeature subFeature = orgUnit.getFeature();
            if (subFeature != null && subFeature == feature) {
                total++;
            }
        }

        return total;
    }

    /**
     * @param rootOU
     * @param lstOUs
     * @return
     */
    private List<OrgUnit> getChildOrgUnits(OrgUnit rootOU, List<OrgUnit> lstOUs) {

        EList<OrgUnit> subUnits = rootOU.getSubUnits();

        for (OrgUnit orgUnit : subUnits) {
            getChildOrgUnits(orgUnit, lstOUs);
            lstOUs.add(orgUnit);
        }

        return lstOUs;
    }

    /**
     * @param targetOrgUnit
     * @return
     */
    private OrgUnit getRootOrgUnit(OrgUnit targetOrgUnit) {
        OrgUnit root = null;
        OrgUnit parentOrgUnit = targetOrgUnit.getParentOrgUnit();

        if (parentOrgUnit == null) {
            root = targetOrgUnit;
        } else {
            root = getRootOrgUnit(parentOrgUnit);
        }
        return root;
    }

    private void createOUIssue(IValidationScope scope, OrgUnit orgUnit,
            String issueId, List<String> messages) {
        scope.createIssue(issueId, orgUnit.getDisplayName(), orgUnit
                .eResource().getURIFragment(orgUnit), messages);
    }
}
