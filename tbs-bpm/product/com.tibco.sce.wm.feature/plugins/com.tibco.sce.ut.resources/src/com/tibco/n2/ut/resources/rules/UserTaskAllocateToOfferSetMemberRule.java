/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.n2.ut.resources.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to validate 'Allocate to Offer-Set Member' distribution strategy.
 * Following validations are being taken care of here:
 * <p>
 * 1. User task must define a Member Identifier performer field if 'Allocate to
 * Offer-set Member' is set.
 * <p>
 * 2. The Allocate to Offer-set Member Identifier should be of type 'Performer'.
 * <p>
 * 3. Performer field used for allocate to offer-set member identifier (user
 * task '%1$s') cannot have RQL initial value.
 * <p>
 * 4. Array performer field can not be used to define the 'Member Identifier'
 * for 'Allocate to Offer-set Member'.
 * <p>
 * 5. "Allocate to Offer-set Member" cannot be used in conjunction with either
 * of the "Separation of Duties", "Chaining" or "Retain Familiar" patterns.
 * <p>
 * 
 * @author sajain
 * @since Dec 9, 2014
 */
public class UserTaskAllocateToOfferSetMemberRule extends
        ProcessActivitiesValidationRule {

    /**
     * User task must define a Member Identifier performer field if 'Allocate to
     * Offer-set Member' is set.
     */
    private static final String ISSUE_ATOSM_WITHOUT_MEMBER_IDENTIFIER_PERFORMER =
            "wm.atosmWithoutMemberIdPerformerField"; //$NON-NLS-1$

    /**
     * The Allocate to Offer-set Member Identifier should be of type
     * 'Performer'.
     */
    private static final String ISSUE_ATOSM_ID_MUST_BE_PERFORMER =
            "wm.atosmIdMustBePerformer"; //$NON-NLS-1$

    /**
     * Performer field used for allocate to offer-set member identifier cannot
     * have RQL initial value.
     */
    private static final String ISSUE_PERFORMER_FIELD_IN_ATOSM_ID_CANNOT_HAVE_RQL =
            "wm.performerFieldInATOSMCannotHaveRQL"; //$NON-NLS-1$

    /**
     * Array performer field can not be used to define the 'Member Identifier'
     * for 'Allocate to Offer-set Member'.
     */
    private static final String ISSUE_ARRAY_PF_CANNOT_BE_USED_IN_ATOSM =
            "wm.arrayPerfFieldCannotBeUsedInATOSM"; //$NON-NLS-1$

    /**
     * "Allocate to Offer-set Member" cannot be used in conjunction with either
     * of the "Separation of Duties", "Chaining" or "Retain Familiar" patterns.
     */
    private static final String ISSUE_ATOSM_CANNOT_BE_USED_WITH_SOD_CHAINING_RF =
            "wm.atosmCannotBeUsedWithSODChainingRF"; //$NON-NLS-1$

    /**
     * "RQL" string.
     */
    private static final String RQL = "RQL"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity act) {

        /*
         * Validate only for user tasks.
         */
        if (TaskType.USER_LITERAL.equals(TaskObjectUtil.getTaskTypeStrict(act))) {

            /*
             * Fetch activity resource patterns.
             */
            Object obj =
                    Xpdl2ModelUtil
                            .getOtherElement(act, XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ActivityResourcePatterns());

            if (obj instanceof ActivityResourcePatterns) {
                ActivityResourcePatterns actResPatterns =
                        (ActivityResourcePatterns) obj;

                /*
                 * Fetch allocation strategy.
                 */
                AllocationStrategy allocationStrategy =
                        actResPatterns.getAllocationStrategy();

                if (allocationStrategy != null) {

                    /*
                     * Fetch allocation type.
                     */
                    AllocationType allocationType =
                            allocationStrategy.getOffer();

                    /*
                     * Proceed only if the allocation type is set to 'Allocate
                     * to offer-set member'.
                     */
                    if (AllocationType.ALLOCATE_OFFER_SET_MEMBER
                            .equals(allocationType)) {

                        /*
                         * Vaidate against invalid configurations like
                         * Separation of duties, retain familiar and chaning
                         * which cannot be used in conjunction with 'Allocate to
                         * Offer-Set Member'.
                         */
                        validateAgainstInvalidConfig(act);

                        /*
                         * User task must define a Member Identifier performer
                         * field if 'Allocate to Offer-set Member' is set.
                         */
                        if (allocationStrategy
                                .getAllocateToOfferSetMemberIdentifier() == null) {

                            List<String> msgs = new ArrayList<String>();

                            msgs.add(act.getName());

                            addIssue(ISSUE_ATOSM_WITHOUT_MEMBER_IDENTIFIER_PERFORMER,
                                    act,
                                    msgs);
                        } else {

                            /*
                             * Validate that the member identifier field is of
                             * performer type and is NOT an array.
                             */
                            validateMemberIdentifierField(act,
                                    allocationStrategy);
                        }
                    }
                }
            }
        }
    }

    /**
     * Vaidate against invalid configurations like Separation of duties, retain
     * familiar and chaining which cannot be used in conjunction with 'Allocate
     * to Offer-Set Member'.
     * 
     * @param act
     *            Activity to look into.
     */
    private void validateAgainstInvalidConfig(Activity act) {

        /*
         * Fetch process.
         */
        Process process = act.getProcess();

        SeparationOfDutiesActivities sepOfDutiesActivities = null;

        RetainFamiliarActivities retainFamActivities = null;

        /*
         * Fetch process resource patterns.
         */
        Object other =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());

        if (other instanceof ProcessResourcePatterns) {

            ProcessResourcePatterns processResourcePatterns =
                    (ProcessResourcePatterns) other;

            /*
             * 1. Validate against 'Separation of duties'.
             */

            /*
             * Go through all activities with 'separation of duties' set.
             */
            for (Object next : processResourcePatterns
                    .getSeparationOfDutiesActivities()) {

                if (next instanceof SeparationOfDutiesActivities) {

                    sepOfDutiesActivities = (SeparationOfDutiesActivities) next;

                    /*
                     * Go through each SOD activity references.
                     */
                    for (Object nextRef : sepOfDutiesActivities
                            .getActivityRef()) {

                        if (nextRef instanceof ActivityRef) {

                            ActivityRef activityRef = (ActivityRef) nextRef;

                            if (act.getId().equals(activityRef.getIdRef())) {

                                /*
                                 * "Allocate to Offer-set Member" cannot be used
                                 * in conjunction with of the
                                 * "Separation of Duties".
                                 */
                                addIssue(ISSUE_ATOSM_CANNOT_BE_USED_WITH_SOD_CHAINING_RF,
                                        act);
                            }

                        }
                    }
                }
            }

            /*
             * 2. Validate against 'Retain familiar'.
             */

            /*
             * Go through all activities with 'retain familiar' set.
             */
            for (Object next : processResourcePatterns
                    .getRetainFamiliarActivities()) {

                if (next instanceof RetainFamiliarActivities) {

                    retainFamActivities = (RetainFamiliarActivities) next;

                    /*
                     * Go through each RF activity references.
                     */
                    for (Object nextRef : retainFamActivities.getActivityRef()) {

                        if (nextRef instanceof ActivityRef) {

                            ActivityRef activityRef = (ActivityRef) nextRef;

                            if (act.getId().equals(activityRef.getIdRef())) {

                                /*
                                 * "Allocate to Offer-set Member" cannot be used
                                 * in conjunction with of the "Retain familiar".
                                 */
                                addIssue(ISSUE_ATOSM_CANNOT_BE_USED_WITH_SOD_CHAINING_RF,
                                        act);
                            }
                        }
                    }
                }
            }
        }

        /*
         * 3. Validate against "Chaining". A user task is considered to be
         * "Chaining" when it's a part of a sub-process which is set to 'Chained
         * Execution'.
         */

        /*
         * Check if flow container is an activity set.
         */
        if (act.getFlowContainer() instanceof ActivitySet) {

            ActivitySet actSet = (ActivitySet) (act.getFlowContainer());

            /*
             * Fetch embedded/event sub-process containing the user task
             * activity.
             */
            Activity parentSubproc =
                    Xpdl2ModelUtil.getEmbSubProcActivityForActSet(process,
                            actSet.getId());

            if (parentSubproc != null) {

                /*
                 * Check if it's chained.
                 */
                if (Xpdl2ModelUtil.getOtherAttributeAsBoolean(parentSubproc,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsChained())) {

                    /*
                     * "Allocate to Offer-set Member" cannot be used in
                     * conjunction with either of the "Separation of Duties",
                     * "Chaining" or "Retain Familiar" patterns.
                     */
                    addIssue(ISSUE_ATOSM_CANNOT_BE_USED_WITH_SOD_CHAINING_RF,
                            act);
                }
            }
        }
    }

    /**
     * Validate that the member identifier field is of performer type, is NOT an
     * array and doesn't have an initial RQL value.
     * 
     * @param act
     *            Activity to look into.
     * @param allocationStrategy
     *            Allocation strategy.
     */
    private void validateMemberIdentifierField(Activity act,
            AllocationStrategy allocationStrategy) {

        /*
         * Fetch all the data in activity's scope.
         */
        List<ProcessRelevantData> allDataInActivityScope =
                ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(act);

        ProcessRelevantData foundField = null;

        /*
         * Traverse through all activity relevant data.
         */
        for (ProcessRelevantData eachProcessRelevantData : allDataInActivityScope) {

            /*
             * Look for data whose name is exactly the same as that of what we
             * entered as the member identifier performer field for Allocate To
             * Offer-Set Member.
             */
            if (allocationStrategy.getAllocateToOfferSetMemberIdentifier()
                    .equals(eachProcessRelevantData.getName())) {

                /*
                 * Field found, so get hold of it and break out.
                 */
                foundField = eachProcessRelevantData;
                break;
            }
        }

        if (foundField != null) {

            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(foundField);

            if (basicType != null) {

                /*
                 * The basic type must be of type performer.
                 */
                if (!BasicTypeType.PERFORMER_LITERAL
                        .equals(basicType.getType())) {

                    addIssue(ISSUE_ATOSM_ID_MUST_BE_PERFORMER, act);
                }

                /*
                 * Fetch Participant query.
                 */
                Object pQueryObject =
                        Xpdl2ModelUtil.getOtherElement(basicType,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery());

                /*
                 * Performer field used for allocate to offer-set member
                 * identifier cannot have RQL initial value.
                 */
                if (pQueryObject instanceof Expression) {

                    Expression expression = (Expression) pQueryObject;

                    if (RQL.equals(expression.getScriptGrammar())) {

                        List<String> msgs = new ArrayList<String>();

                        msgs.add(act.getName());

                        addIssue(ISSUE_PERFORMER_FIELD_IN_ATOSM_ID_CANNOT_HAVE_RQL,
                                foundField,
                                msgs);
                    }
                }
            }

            /*
             * Array performer field can not be used to define the 'Member
             * Identifier' for 'Allocate to Offer-set Member'.
             */
            if (foundField.isIsArray()) {
                addIssue(ISSUE_ARRAY_PF_CANNOT_BE_USED_IN_ATOSM, act);
            }

        } else {

            /*
             * No field found, so add issue.
             */

            addIssue(ISSUE_ATOSM_ID_MUST_BE_PERFORMER, act);
        }

    }
}
