/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 1 Apr 2008</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class DiscriminatorPatternRule extends FlowContainerValidationRule {

    private static final String COMPLEX_MERGE_INCOMING_TRANSITION =
            "bpmn.complexMergeWithGreaterWaitThanIncomingTransitions_1"; //$NON-NLS-1$

    private static final String NO_AND_SPLIT_SPECIFIED =
            "bpmn.complexMergeWithNoAndSplit"; //$NON-NLS-1$

    private static final String MORE_THAN_ONE_OUT_TRANISITION =
            "bpmn.complexMergeWithMoreThanOneOutTransition"; //$NON-NLS-1$

    private static final String STRUCTURED_ID = "Structured"; //$NON-NLS-1$

    private List<String> andSplitNameList = null;

    @Override
    public void validate(FlowContainer container) {
        List<Activity> activities = container.getActivities();
        if (activities == null) {
            return;
        }
        List<Activity> complexMergeActivities = new ArrayList<Activity>();
        List<Activity> andSplitActivities = new ArrayList<Activity>();
        for (Activity activity : activities) {
            boolean isStructuredDiscriminator =
                    isStructuredDiscriminator(activity);
            if (isStructuredDiscriminator) {
                complexMergeActivities.add(activity);
            }
            boolean andSplit = isAndSplit(activity);
            if (andSplit) {
                andSplitActivities.add(activity);
            }
        }
        // checks if there is any complex merge activity with discriminator
        // pattern. If none then does not enforce
        // any of the validation rules.
        if (complexMergeActivities.size() < 1) {
            return;
        }
        for (Activity activity : complexMergeActivities) {
            StructuredDiscriminator structuredDiscriminator =
                    getStructuredDiscriminator(activity);
            if (structuredDiscriminator == null) {
                continue;
            }
            BigInteger waitForIncomingPath =
                    structuredDiscriminator.getWaitForIncomingPath();
            int incomingTransitions =
                    getIncomingTransitions(activity, container);
            if (waitForIncomingPath == null
                    || waitForIncomingPath.intValue() < 1
                    || waitForIncomingPath.intValue() >= incomingTransitions) {
                addIssue(COMPLEX_MERGE_INCOMING_TRANSITION,
                        activity,
                        Collections.singletonList(Integer
                                .toString(incomingTransitions)));
            }
            int outgoingTransitions =
                    getOutgoingTransitions(activity, container);
            if (outgoingTransitions > 1) {
                addIssue(MORE_THAN_ONE_OUT_TRANISITION, activity);
            }
            String upStreamParallelSplit =
                    structuredDiscriminator.getUpStreamParallelSplit();
            if (upStreamParallelSplit == null
                    || upStreamParallelSplit.length() == 0) {
                addIssue(NO_AND_SPLIT_SPECIFIED, activity);
            }

        }

    }

    private boolean isAndSplit(Activity activity) {
        Route route = activity.getRoute();
        if (route != null) {
            JoinSplitType gatewayType =
                    Xpdl2ModelUtil.safeGetGatewayType(activity);
            ;
            if (gatewayType != null) {
                if (JoinSplitType.PARALLEL_LITERAL.equals(gatewayType)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getIncomingTransitions(Activity activity,
            FlowContainer flowContainer) {
        return activity.getIncomingTransitions().size();
    }

    private int getOutgoingTransitions(Activity activity,
            FlowContainer flowContainer) {
        return activity.getOutgoingTransitions().size();
    }

    private boolean isStructuredDiscriminator(Activity activity) {
        boolean toReturn = false;
        Discriminator discriminator = getDiscriminator(activity);
        if (discriminator == null) {
            return toReturn;
        }
        String discriminatorType = discriminator.getDiscriminatorType();
        if (STRUCTURED_ID.equals(discriminatorType)) {
            toReturn = true;
        }
        return toReturn;

    }

    private StructuredDiscriminator getStructuredDiscriminator(Activity activity) {
        Discriminator discriminator = getDiscriminator(activity);
        StructuredDiscriminator toReturn = null;
        if (discriminator == null) {
            return toReturn;
        }
        toReturn = discriminator.getStructuredDiscriminator();
        return toReturn;
    }

    private Discriminator getDiscriminator(Activity activity) {
        Route route = activity.getRoute();
        if (route == null) {
            return null;
        }
        Discriminator discriminator =
                (Discriminator) Xpdl2ModelUtil.getOtherElement(route,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Discriminator());
        return discriminator;
    }

}
