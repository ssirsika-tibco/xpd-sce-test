/*
 * Copyright (c) TIBCO Software Inc 2004, 2009s. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.validator.GenericIssueIds;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule that checks if a Position has a feature and if so whether
 * there are too many instances of that feature in the parent OrgUnit.
 * 
 * @author Rob Green
 * @since 3.3
 */
public class PositionFeatureMultiplicityRule implements IValidationRule {

    public Class<?> getTargetClass() {
        return Position.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Position) {

            Position trgPosition = (Position) o;
            PositionFeature trgFeature = trgPosition.getFeature();
            if (trgFeature != null) {

                int upperBound = trgFeature.getUpperBound();

                if (upperBound == -1) {
                    // Unlimited multiplicity so no need to check
                    return;
                }

                EObject container = trgPosition.eContainer();

                if (container != null && container instanceof OrgUnit) {
                    OrgUnit containerOU = (OrgUnit) container;
                    EList<Position> positions = containerOU.getPositions();

                    // Collect a Map of all the Position Features (e.g. Managers
                    // and Members) in this OrgUnit
                    HashMap<Object, Integer> featureMap =
                            new HashMap<Object, Integer>();

                    for (Position position : positions) {
                        if (position.getFeature() != null) {
                            PositionFeature ps = position.getFeature();
                            int idealNumber = position.getIdealNumber();

                            if (!featureMap.containsKey(ps)) {

                                featureMap.put(ps, (Integer) idealNumber);
                            } else {
                                // This feature is already counted
                                Integer total = (Integer) featureMap.get(ps);
                                featureMap.put(ps, total + idealNumber);
                            }
                        }
                    }

                    // FInd the upper bound of our target Position's feature
                    // Pull the feature out of the Map
                    if (featureMap.containsKey(trgFeature)) {
                        Integer numFeatures = featureMap.get(trgFeature);

                        if (numFeatures > upperBound) {
                            // Create an issue
                            List<String> additionalMessages =
                                    new ArrayList<String>();
                            additionalMessages.add(trgFeature.getDisplayName());

                            additionalMessages.add(containerOU.getFeature()
                                    .getDisplayName());
                            createPositionIssue(scope,
                                    trgPosition,
                                    GenericIssueIds.POSITION_TYPE_EXCEEDED_MULTIPLICITY_UPPER_BOUND,
                                    additionalMessages);
                        }
                    }

                }

            }

        }

    }

    private void createPositionIssue(IValidationScope scope, Position position,
            String issueId, List<String> messages) {
        scope.createIssue(issueId, position.getDisplayName(), position
                .eResource().getURIFragment(position), messages);
    }
}
