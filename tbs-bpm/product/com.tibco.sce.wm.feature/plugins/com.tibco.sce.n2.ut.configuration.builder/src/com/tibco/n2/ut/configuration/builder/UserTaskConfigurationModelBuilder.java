/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import com.tibco.n2.ut.model.DistributionStrategyType;
import com.tibco.n2.ut.model.DocumentRoot;
import com.tibco.n2.ut.model.EntityType;
import com.tibco.n2.ut.model.ModeType;
import com.tibco.n2.ut.model.TypeType;
import com.tibco.n2.ut.model.UserTaskDataModelType;
import com.tibco.n2.ut.model.UserTaskDynamicOrgAttributesType;
import com.tibco.n2.ut.model.UserTaskExtendedAttributeType;
import com.tibco.n2.ut.model.UserTaskExtendedAttributesType;
import com.tibco.n2.ut.model.UserTaskGroupMembershipType;
import com.tibco.n2.ut.model.UserTaskGroupMembershipsType;
import com.tibco.n2.ut.model.UserTaskParameterType;
import com.tibco.n2.ut.model.UserTaskParametersType;
import com.tibco.n2.ut.model.UserTaskParticipantType;
import com.tibco.n2.ut.model.UserTaskParticipantsType;
import com.tibco.n2.ut.model.UsertaskFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Handle the conversion of User Tasks to the User Task EMF Model
 * 
 */
public class UserTaskConfigurationModelBuilder {

    // Prefix applied to the Activity ID to get the auto-generated Work Model
    public static final String WORK_MODEL_PREFIX = "WM_";

    public UserTaskConfigurationModelBuilder() {

    }

    /**
     * Generates the appropriate EMF model for a User Task
     * 
     * @param activity
     *            Activity to convert
     * @return
     */
    public UserTaskDataModelType convertToUserTaskDataModel(Activity activity) {
        UsertaskFactory factory = UsertaskFactory.eINSTANCE;
        DocumentRoot root = factory.createDocumentRoot();
        UserTaskDataModelType userTaskDataModel =
                factory.createUserTaskDataModelType();

        // Gather group memberships
        userTaskDataModel
                .setUserTaskGroupMemberships(gatherGroupMemberships(activity));

        // Convert allocation method
        userTaskDataModel
                .setDistributionStrategy(convertDistributionStrategy(activity));

        // Convert parameters
        userTaskDataModel.setUserTaskParameters(convertParameters(activity));

        // Convert participants
        UserTaskParticipantsType participants =
                getParticipants(factory, activity);
        userTaskDataModel.setUserTaskParticipants(participants);

        // Convert extended attributes
        UserTaskExtendedAttributesType extendedAttribs =
                getExtendedAttributes(factory, activity);

        if (extendedAttribs != null) {
            userTaskDataModel.setUserTaskExtendedAttributes(extendedAttribs);
        }

        // Get the version range for this process, as that will match the Work
        // Model Version range
        String workModelVersionRange =
                ProcessUtils.getProcessVersionRange(activity);
        if ((workModelVersionRange != null)
                && (workModelVersionRange.length() > 0)) {
            userTaskDataModel.setWorkModelVersionRange(workModelVersionRange);
        }

        // Set the work Model ID based on the activityID
        // We already know that this is created from the activity ID prepended
        // with WM_
        userTaskDataModel.setWorkModelID(WORK_MODEL_PREFIX + activity.getId());

        // Populate the priority
        String priority = getPriority(activity);
        // Only set if the priority is set
        if (priority != null) {
            // Studio actually only allows integers to be input
            userTaskDataModel.setUserTaskPriority(Integer.parseInt(priority));
        }

        root.setUserTaskDataModel(userTaskDataModel);

        return root.getUserTaskDataModel();
    }

    /**
     * Determines whether one or more of the given ActivityRefs is a reference
     * to the given Activity.
     * 
     * @param refs
     * @param activity
     * @return
     */
    private boolean activityRefsIncludeActivity(List<ActivityRef> refs,
            Activity activity) {
        boolean result = false;
        for (ActivityRef activityRef : refs) {
            if (activityRef.getActivity().getId().equals(activity.getId())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Helper method to construct a group membership records with the supplied
     * data
     * 
     * @param id
     * @param description
     * @param type
     * @return
     */
    private UserTaskGroupMembershipType createGroupMembership(String id,
            String description, TypeType type) {
        // Create a new UT group membership
        UserTaskGroupMembershipType result =
                UsertaskFactory.eINSTANCE.createUserTaskGroupMembershipType();
        result.setId(id);
        result.setDescription(description);
        result.setType(type);
        return result;
    }

    /**
     * Determines which groups our activity is a member, constructing an
     * appropriate model fragment.
     * 
     * @param activity
     * @return
     */
    private UserTaskGroupMembershipsType gatherGroupMemberships(
            Activity activity) {
        // Create container for groups
        UserTaskGroupMembershipsType groups =
                UsertaskFactory.eINSTANCE.createUserTaskGroupMembershipsType();

        Process proc = activity.getProcess();
        Object prpObj =
                Xpdl2ModelUtil.getOtherElement(proc,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());

        if (prpObj instanceof ProcessResourcePatterns) {
            ProcessResourcePatterns prp = (ProcessResourcePatterns) prpObj;

            // See if there are any Separation of duties patterns
            for (SeparationOfDutiesActivities group : prp
                    .getSeparationOfDutiesActivities()) {
                if (activityRefsIncludeActivity(group.getActivityRef(),
                        activity)) {
                    UserTaskGroupMembershipType groupMembership =
                            createGroupMembership(group.getId(),
                                    group.getName(),
                                    TypeType.SEPARATIONOFDUTIES_LITERAL);
                    groups.getUserTaskGroupMembership().add(groupMembership);
                }
            }

            for (RetainFamiliarActivities retain : prp
                    .getRetainFamiliarActivities()) {
                if (activityRefsIncludeActivity(retain.getActivityRef(),
                        activity)) {
                    UserTaskGroupMembershipType groupMembership =
                            createGroupMembership(retain.getId(),
                                    retain.getName(),
                                    TypeType.RETAINFAMILIAR_LITERAL);
                    groups.getUserTaskGroupMembership().add(groupMembership);
                }
            }
        }

        // Find activity sets containing our process
        List<String> setIds = new ArrayList<String>();
        for (Object actSetObj : activity.getProcess().getActivitySets()) {
            ActivitySet actSet = (ActivitySet) actSetObj;
            if (actSet.getActivity(activity.getId()) != null) {
                setIds.add(actSet.getId());
            }
        }

        // Starting with the activities that are immediate children of the
        // process, recurse into
        // embedded sub-procs and create group memberships for any that contain
        // our activity
        List<UserTaskGroupMembershipType> memberships =
                new ArrayList<UserTaskGroupMembershipType>();
        traverseNestedSubProcsToFindChainingGroups(activity.getProcess()
                .getActivities(), setIds, memberships);
        groups.getUserTaskGroupMembership().addAll(memberships);

        return groups;
    }

    /**
     * Finds chained embedded sub-procs that are associated with one of the
     * supplied activity set ids. When one is found, a new group membership is
     * added to the supplied memberships list. Recursively calls self for
     * activities within each sub-proc, in the hope of finding nested embedded
     * sub-procs.
     * 
     * @param activities
     * @param containingSetIds
     * @param memberships
     */
    private void traverseNestedSubProcsToFindChainingGroups(
            List<Activity> activities, List<String> containingSetIds,
            List<UserTaskGroupMembershipType> memberships) {
        for (Activity activity : activities) {
            // If the activity has an associated block activity, then
            // we can assume it's an embedded sub-proc
            BlockActivity blac = activity.getBlockActivity();
            if (blac != null) {
                // It's an embedded sub-proc
                // If it's chained and our activity belongs to it, create a
                // group membership
                Object isChainedObj =
                        Xpdl2ModelUtil.getOtherAttribute(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_IsChained());
                if (isChainedObj instanceof Boolean
                        && ((Boolean) isChainedObj).booleanValue()) {
                    if (containingSetIds.contains(blac.getActivitySetId())) {
                        UserTaskGroupMembershipType utGroup =
                                createGroupMembership(activity.getId(),
                                        activity.getName(),
                                        TypeType.CHAINING_LITERAL);
                        memberships.add(utGroup);
                    }
                }

                // Continue to tunnel into the sub-proc in the hope of finding
                // nested sub-procs.
                ActivitySet set =
                        activity.getProcess()
                                .getActivitySet(blac.getActivitySetId());
                traverseNestedSubProcsToFindChainingGroups(set.getActivities(),
                        containingSetIds,
                        memberships);
            }
        }
    }

    /**
     * Converts the XPDL representation of an activity's allocation method to
     * one for our model.
     * 
     * @param activity
     * @return
     */
    private DistributionStrategyType convertDistributionStrategy(
            Activity activity) {
        Object objARP =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());

        DistributionStrategyType distributionStrategy =
                DistributionStrategyType.ALLOCATE;

        // The existence of an ActivityResourcePatterns has already been
        // verified in UserActivityResourcePatternRule so we should never
        // fail here - but just in case we continue to check
        if (objARP instanceof ActivityResourcePatterns) {
            AllocationStrategy as =
                    ((ActivityResourcePatterns) objARP).getAllocationStrategy();
            if (as != null) {
                if (AllocationType.OFFER_ALL == as.getOffer()) {
                    distributionStrategy = DistributionStrategyType.OFFER;
                }
            }
        }

        return distributionStrategy;
    }

    /**
     * Reads the XPDL representation of an activity's priority
     * 
     * @param activity
     * @return
     */
    private String getPriority(Activity activity) {
        Object objARP =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());

        // This is currently stored in a String - however the value is actually
        // numeric - if the interface changes then this may be able to be
        // changed
        // to an Integer Object
        String priority = null;

        if (objARP instanceof ActivityResourcePatterns) {
            WorkItemPriority wiPriority =
                    ((ActivityResourcePatterns) objARP).getWorkItemPriority();
            if (wiPriority != null) {
                priority = wiPriority.getInitialPriority();
            }
        }

        return priority;
    }

    /**
     * Reads the XPDL representation of an activity's allocateToOfferSetMemberIdentifier
     * 
     * @param activity
     * @return performer field name as a String
     */
    private String getAllocateToOfferSetMemberField(Activity activity) {
        Object objARP =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());

        String allocateToOfferSetMemberField = null;
        if (objARP instanceof ActivityResourcePatterns) {
        	AllocationStrategy allocationStrategy = ((ActivityResourcePatterns)objARP).getAllocationStrategy();
        	if (AllocationType.ALLOCATE_OFFER_SET_MEMBER == allocationStrategy.getOffer()) {
            	allocateToOfferSetMemberField = allocationStrategy.getAllocateToOfferSetMemberIdentifier();
            }
        }

        return allocateToOfferSetMemberField;
    }
    
    /**
     * Extracts the parameter details from the supplied XPDL Activity and
     * constructs an equivalent representation for our EMF model.
     * 
     * @param activity
     * @return our EMF representation
     */
    private UserTaskParametersType convertParameters(Activity activity) {
        // Create a container for the parameters in our model
        UserTaskParametersType utParams =
                UsertaskFactory.eINSTANCE.createUserTaskParametersType();

        // Get the activity's associated parameters from the XPDL
        Object apsObj =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());

        if (apsObj instanceof AssociatedParameters
                && ((AssociatedParameters) apsObj).getAssociatedParameter()
                        .size() > 0) {
            // For each parameter...
            for (Object apObj : ((AssociatedParameters) apsObj)
                    .getAssociatedParameter()) {
                if (apObj instanceof AssociatedParameter) {
                    // Create a parameter object and populate with
                    // details from the XPDL.
                    // Note: We're not interested in the parameter's description
                    AssociatedParameter ap = (AssociatedParameter) apObj;

                    UserTaskParameterType utParam =
                            UsertaskFactory.eINSTANCE
                                    .createUserTaskParameterType();
                    utParam.setName(ap.getFormalParam());
                    utParam.setMode(convertToUTModeType(ap.getMode()));
                    utParam.setMandatory(ap.isMandatory());

                    // Add it to our list
                    utParams.getUserTaskParameter().add(utParam);
                }
            }
        } else {
            /*
             * Sid XPD-2087: Only use all data implicitly if implicit
             * association has not been disabled.
             */
            if (!ProcessInterfaceUtil.isImplicitAssociationDisabled(activity)) {
                // This is an indication that implicit process
                // data scoping needs to be done

                /*
                 * Sid XPD-2087: This used to get all data for process (ignoring
                 * activity data), I don't think that was correct.
                 */
                List<ProcessRelevantData> allProcessRelevantData =
                        ProcessInterfaceUtil
                                .getAllAvailableRelevantDataForActivity(activity);
                // ProcessInterfaceUtil.getAllProcessRelevantData(xpdlProcess);

                convertPRDListToUserTaskParameters(utParams,
                        allProcessRelevantData);
            }
        }
        return utParams;
    }

    /**
     * Iterates through list of process relevant data list & create
     * UserTaskParameter
     * 
     * @param utParams
     * @param associatedProcessData
     */
    private void convertPRDListToUserTaskParameters(
            UserTaskParametersType utParams,
            List<ProcessRelevantData> associatedProcessData) {
        for (ProcessRelevantData processRelevantData : associatedProcessData) {
            if (processRelevantData instanceof DataField) {
                convertDataFieldToUserTaskParameter(utParams,
                        (DataField) processRelevantData);
            } else if (processRelevantData instanceof FormalParameter) {
                convertFormalParameterToUserTaskParameter(utParams,
                        (FormalParameter) processRelevantData);
            }
        }
    }

    /**
     * Converts DataFields to UserTask Parameters
     * 
     * @param utParams
     * @param dataFields
     */
    private void convertDataFieldToUserTaskParameter(
            UserTaskParametersType utParams, DataField dataField) {

        UserTaskParameterType utParam =
                UsertaskFactory.eINSTANCE.createUserTaskParameterType();
        utParam.setName(dataField.getName());
        if(dataField.getDataType() instanceof RecordType)
        {
            // DataFields does not have Mode.IN is assumed as default for case reference
            utParam.setMode(com.tibco.n2.ut.model.ModeType.IN_LITERAL);
            
        }else{
            
            // DataFields does not have Mode.Hence inout is assumed as default
            utParam.setMode(com.tibco.n2.ut.model.ModeType.INOUT_LITERAL);
        }
         
        // DataFields does not have 'mandatory' flag.Hence we assume false.
        utParam.setMandatory(false);
        utParams.getUserTaskParameter().add(utParam);

    }

    /**
     * Converts FormalParameters to UserTask Parameters
     * 
     * @param utParams
     * @param formalParameters
     */
    private void convertFormalParameterToUserTaskParameter(
            UserTaskParametersType utParams, FormalParameter formalParameter) {
        UserTaskParameterType utParam =
                UsertaskFactory.eINSTANCE.createUserTaskParameterType();
        utParam.setName(formalParameter.getName());
        utParam.setMode(convertToUTModeType(formalParameter.getMode()));
        utParam.setMandatory(formalParameter.isRequired());
        utParams.getUserTaskParameter().add(utParam);

    }

    /**
     * Converts an XPDL representation of parameter mode (in/out/inout) to one
     * for our EMF model.
     * 
     * @param mode
     *            The mode to map
     * @return
     */
    private ModeType convertToUTModeType(com.tibco.xpd.xpdl2.ModeType mode) {
        ModeType utMode = null;
        // Each value for one enumeration maps to one in the other.
        switch (mode.getValue()) {
        case com.tibco.xpd.xpdl2.ModeType.IN:
            utMode = com.tibco.n2.ut.model.ModeType.IN_LITERAL;
            break;
        case com.tibco.xpd.xpdl2.ModeType.OUT:
            utMode = com.tibco.n2.ut.model.ModeType.OUT_LITERAL;
            break;
        case com.tibco.xpd.xpdl2.ModeType.INOUT:
            utMode = com.tibco.n2.ut.model.ModeType.INOUT_LITERAL;
            break;
        }

        // The value will have already been verified in UserActivityModeTypeRule
        // to ensure that it was one of the values listed above, so in theory
        // we should never get to this point without a value being set
        return utMode;
    }

    private UserTaskExtendedAttributesType getExtendedAttributes(
            UsertaskFactory factory, Activity activity) {
        UserTaskExtendedAttributesType type = null;

        List<ExtendedAttribute> extendedAttributes =
                activity.getExtendedAttributes();

        // Only add an Extended Attribute section if we have any
        if (extendedAttributes.size() > 0) {
            type = factory.createUserTaskExtendedAttributesType();

            for (ExtendedAttribute attribute : extendedAttributes) {
                UserTaskExtendedAttributeType attributeType =
                        factory.createUserTaskExtendedAttributeType();
                attributeType.setName(attribute.getName());
                attributeType.setValue(attribute.getValue());
                type.getUserTaskExtendedAttribute().add(attributeType);
            }
        }

        return type;
    }

    /**
     * Gets the Participant information for a given activity
     * 
     * @param factory
     *            EMF Model factory
     * @param activity
     *            Activity to check participants of
     * @return The User Task Participants
     */
    private UserTaskParticipantsType getParticipants(UsertaskFactory factory,
            Activity activity) {
        UserTaskParticipantsType participantsType =
                factory.createUserTaskParticipantsType();

    	// Set allocateToOfferSetParticipant
        String allocateToOfferSetMemberField = getAllocateToOfferSetMemberField(activity);
        if (allocateToOfferSetMemberField != null) {
        	participantsType.setAllocateToOfferSetParticipant(allocateToOfferSetMemberField);
        }
        
        // A check has already been made in the validation
        // UserTaskParticipantRule
        // There is at least one participant
        Performers performersList = activity.getPerformers();
        if (performersList != null) {
            List<Performer> performers = performersList.getPerformers();
            for (Performer performer : performers) {
                UserTaskParticipantType participantType =
                        factory.createUserTaskParticipantType();

                // Get this participant from the process based on the performer
                // value
                Participant participant =
                        activity.getProcess()
                                .getParticipant(performer.getValue());

                if (participant != null) {
                    ExternalReference externalReference =
                            participant.getExternalReference();

                    if (externalReference != null) {
                        // Get the Model Element from the Organisational Model
                        ModelElement omModelElement =
                                UserActivityParticipantUtils
                                        .getOMModelElement(externalReference);
                        if (omModelElement != null) {
                            // Check to see if this is a query
                            String orgQuery =
                                    UserActivityParticipantUtils
                                            .getOrgQuery(omModelElement);
                            if ((orgQuery != null) && (orgQuery.length() > 0)) {
                                participantsType
                                        .setUserTaskParticipantQuery(orgQuery);
                                // Currently only support single query strings
                                break;
                            } else {
                                // Get the version number of the organisational
                                // model
                                Integer version =
                                        UserActivityParticipantUtils
                                                .getOMModelVersion(omModelElement);
                                if (version != null) {
                                    participantType.setModelVersion(version);
                                }
                                participantType.setUniqueId(participant
                                        .getExternalReference().getXref());
                                participantType
                                        .setEntity(UserActivityParticipantUtils
                                                .getEntityType(omModelElement));

                                // If this is a reference to a Dynamic Org
                                // entity, get the attributes needed
                                // at runtime to work out which Dynamic Org is
                                // to be used
                                UserTaskDynamicOrgAttributesType attrs =
                                        UserActivityParticipantUtils
                                                .getDynamicOrgAttrs(factory,
                                                        activity.getProcess(),
                                                        participant,
                                                        omModelElement);
                                if (attrs != null) {
                                    participantType
                                            .setDynamicOrgAttributes(attrs);
                                }
                                participantsType.getUserTaskParticipant()
                                        .add(participantType);
                            }
                        }
                    } else {
                        // This is a name based allocation
                        ParticipantType type =
                                participant.getParticipantType().getType();

                        participantType.setEntity(UserActivityParticipantUtils
                                .getEntityType(type));

                        // Check if this is RQL
                        if (type == ParticipantType.RESOURCE_SET_LITERAL) {
                            Object otherElement =
                                    Xpdl2ModelUtil
                                            .getOtherElement(participant
                                                    .getParticipantType(),
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_ParticipantQuery());

                            if ((otherElement != null)
                                    && (otherElement instanceof Expression)) {
                                participantType
                                        .setRqlQuery(((Expression) otherElement)
                                                .getText());
                            }
                        }

                        participantsType.getUserTaskParticipant()
                                .add(participantType);
                    }
                } else {
                    // This suggests that the participant is based on a
                    // performer data field or a parameter field
                    ProcessRelevantData participantField =
                            UserActivityParticipantUtils
                                    .getDynamicParticipant(activity,
                                            performer.getValue());

                    // Check to see if the participant was found
                    if (participantField != null) {
                        participantType.setEntity(EntityType.FIELD_LITERAL);
                        participantType
                                .setPerformer(participantField.getName());
                        participantsType.getUserTaskParticipant()
                                .add(participantType);
                    }
                }
            }
        }

        return participantsType;
    }
}
