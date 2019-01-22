/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities to accurately assess a process activities interface.
 * <p>
 * If you are interested only in the read-only 'final reckoning' of the data
 * associated with a given activity or interface event (regardless of the raw
 * data set in the activity model itself) then this is the class to use.
 * 
 * @author aallway
 * @since 17 Aug 2011
 */
public class ActivityInterfaceDataUtil {

    /**
     * This method takes into account all things that effect what is implicitly
     * or explicitly associated with a given activity.
     * <p>
     * This includes returning only FormalParameters for api activities. Taking
     * into account activities that implement interface events (no implicit
     * local parameter association) and so on.
     * <p>
     * Where possible, this method should now be used in preference to other
     * methods such as {@link #getActivityAssociatedParameters(Activity)} which
     * do not take these factors into account but leave the caller to rememeber
     * to do something about these thigns as necessary.
     * 
     * @param activity
     * @return The activity interface data (explicitly of implicitly associated
     *         data) for the given activity
     */
    public static Collection<ActivityInterfaceData> getActivityInterfaceData(
            Activity activity) {

        Set<ActivityInterfaceData> activityInterfaceData =
                new HashSet<ActivityInterfaceData>();

        Map<String, ProcessRelevantData> allAvailableRelevantDataMapForActivity =
                ProcessInterfaceUtil
                        .getAllAvailableRelevantDataMapForActivity(activity);

        boolean isApiActivity = Xpdl2ModelUtil.isProcessAPIActivity(activity);
        boolean isImplementingEvent =
                ProcessInterfaceUtil.isEventImplemented(activity);

        /*
         * Handle activity explicitly associated data.
         */
        List<AssociatedParameter> activityAssociatedParameters =
                ProcessInterfaceUtil.getActivityAssociatedParameters(activity);

        if (!activityAssociatedParameters.isEmpty()) {

            for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                ProcessRelevantData data =
                        allAvailableRelevantDataMapForActivity
                                .get(associatedParameter.getFormalParam());

                /**
                 * SID XPD-4470: During code review spotted that this method
                 * USED TO filter out explicit references to data-fields for
                 * process API activities.
                 * 
                 * In general, utility methods like this shoudl reflect what's
                 * in the model and not make judgements on what should be in
                 * model for semantic correctness.
                 */
                if (data != null) {
                    activityInterfaceData.add(new ActivityInterfaceData(
                            associatedParameter, data));
                }
            }

        } else {
            /*
             * Associate data implicitly.
             * 
             * Except For events implementing interface methods we DO NOT
             * implicitly associate local process defined parameters.
             * 
             * And except for when activity is configured to disable implicit
             * assocaition.
             */
            if (!isImplementingEvent
                    && !ProcessInterfaceUtil
                            .isImplicitAssociationDisabled(activity)) {

                boolean isUserTask = false;

                if (activity.getImplementation() instanceof Task) {
                    Task task = (Task) activity.getImplementation();
                    if (task.getTaskUser() != null) {
                        isUserTask = true;
                    }
                }

                for (ProcessRelevantData data : allAvailableRelevantDataMapForActivity
                        .values()) {
                    /*
                     * Only add formal params for api activities, all data for
                     * others.
                     */
                    if ((isApiActivity && data instanceof FormalParameter)
                            || (!isApiActivity && data != null)) {

                        if (isUserTask
                                && data.getDataType() instanceof RecordType) {
                            /*
                             * XPD-6009: if the activity is an user task and the
                             * data refers a Case Class then the Mode Type and
                             * mandatory flag should be as it is for
                             * FormalParamters "WHEREAS" for DataField the Mode
                             * Type should be "IN" and Mandatory Flag should be
                             * "false"
                             */
                            if (data instanceof FormalParameter) {
                                /* set mode and mandatory as it is */
                                activityInterfaceData
                                        .add(new ActivityInterfaceData(data,
                                                ((FormalParameter) data)
                                                        .getMode(),
                                                ((FormalParameter) data)
                                                        .isRequired()));
                            } else {
                                /* set Mode = "IN", Mandatory = "false" */
                                activityInterfaceData
                                        .add(new ActivityInterfaceData(data,
                                                ModeType.IN_LITERAL, false));
                            }
                        } else {
                            activityInterfaceData
                                    .add(new ActivityInterfaceData(data));
                        }
                    }
                }
            }
        }

        /*
         * If this is an activity implementing an interface event then include
         * the explicitly or implicitly associated parameters from interface.
         */
        if (isImplementingEvent) {
            Collection<ActivityInterfaceData> interfaceData = null;

            InterfaceMethod implementedMethod =
                    ProcessInterfaceUtil.getImplementedMethod(activity);
            if (implementedMethod != null) {
                interfaceData =
                        getInterfaceEventInterfaceData(implementedMethod);
            } else {
                ErrorMethod errorMethod =
                        ProcessInterfaceUtil
                                .getImplementedErrorMethod(activity);
                if (errorMethod != null) {
                    interfaceData = getInterfaceEventInterfaceData(errorMethod);
                }
            }

            if (interfaceData != null) {
                for (ActivityInterfaceData ifcData : interfaceData) {
                    /*
                     * Note that 'nominally', if the interface method as
                     * explicit associations then these should be replicated in
                     * the implementing activity (which is a bit gross but
                     * historically there so hey-ho), however they may be out of
                     * synch). So for this method's purpose we will get the MOST
                     * up to date info and always pick up stuff from the
                     * interface (the set.add() will replace the original
                     * explicit out-of-date association in activity if
                     * necessary).
                     */
                    if (activityInterfaceData.contains(ifcData)) {
                        activityInterfaceData.add(ifcData);
                    }

                    /* Add the interface data to the implementing event data. */
                    activityInterfaceData.add(ifcData);
                }
            }
        }

        return activityInterfaceData;
    }

    /**
     * For a given activity, return its associated correlation data. Both
     * correlation data explicitly and implicitly specified for an activity is
     * considered.
     * 
     * @param activity
     * @return
     */
    public static Collection<ActivityInterfaceCorrelationData> getActivityInterfaceCorrelationData(
            Activity activity) {

        Set<ActivityInterfaceCorrelationData> activityInterfaceCorrelationData =
                Collections.<ActivityInterfaceCorrelationData> emptySet();

        if (activity.getProcess() != null
                && Xpdl2ModelUtil.canHaveCorrelationAssociated(activity)
                && !ProcessInterfaceUtil
                        .isImplicitCorrelationAssociationDisabled(activity)) {

            activityInterfaceCorrelationData =
                    new HashSet<ActivityInterfaceCorrelationData>();

            List<DataField> correlationDataFields =
                    ProcessInterfaceUtil.getCorrelationDataFields(activity
                            .getProcess());

            AssociatedCorrelationFields associatedCorrelations =
                    (AssociatedCorrelationFields) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedCorrelationFields());

            if (associatedCorrelations != null
                    && !associatedCorrelations.getAssociatedCorrelationField()
                            .isEmpty()) {

                /* Explicit association. */
                for (AssociatedCorrelationField assoc : associatedCorrelations
                        .getAssociatedCorrelationField()) {
                    /* Find matching data field for each association. */
                    for (DataField data : correlationDataFields) {
                        if (data.getName().equals(assoc.getName())) {
                            activityInterfaceCorrelationData
                                    .add(new ActivityInterfaceCorrelationData(
                                            assoc, data));
                            break;
                        }
                    }
                }
            } else {

                /*
                 * Implicit association, calculate default correlation mode for
                 * this activity. Correlation mode determined based upon logic
                 * of provider
                 */

                /*
                 * XPD-6751: Saket: Modified
                 * Xpdl2ModelUtil.isMessageProcessStartActivity(activity) to
                 * return true for only those activities that are in the main
                 * process and therefore we don't need to have
                 * Xpdl2ModelUtil.isEventSubProcessStartEvent(activity)
                 * alongside it.
                 */
                CorrelationMode correlMode =
                        ((Xpdl2ModelUtil
                                .isMessageProcessStartActivity(activity)) ? CorrelationMode.INITIALIZE
                                : CorrelationMode.CORRELATE);

                for (DataField data : correlationDataFields) {
                    activityInterfaceCorrelationData
                            .add(new ActivityInterfaceCorrelationData(data,
                                    correlMode));
                }

            }

        }

        return activityInterfaceCorrelationData;
    }

    /**
     * This method takes into account all things that effect what is implicitly
     * associated with a given interface event.
     * 
     * @param associatedParametersContainer
     * 
     * @return The activity interface data (explicitly or implicitly associated
     *         data) for the given interface event
     */
    public static Collection<ActivityInterfaceData> getInterfaceEventInterfaceData(
            AssociatedParametersContainer processInterfaceEvent) {

        Set<ActivityInterfaceData> interfaceData =
                new HashSet<ActivityInterfaceData>();

        ProcessInterface processInterface =
                (ProcessInterface) Xpdl2ModelUtil
                        .getAncestor(processInterfaceEvent,
                                ProcessInterface.class);
        if (processInterface != null) {
            Map<String, ProcessRelevantData> formalParameters =
                    ProcessInterfaceUtil.getNameToDataMap(processInterface
                            .getFormalParameters());

            /*
             * Handle explicitly associated parameters.
             */
            EList<AssociatedParameter> associatedParameters =
                    processInterfaceEvent.getAssociatedParameters();
            if (associatedParameters != null && !associatedParameters.isEmpty()) {

                for (AssociatedParameter associatedParameter : associatedParameters) {

                    ProcessRelevantData param =
                            formalParameters.get(associatedParameter
                                    .getFormalParam());

                    interfaceData.add(new ActivityInterfaceData(
                            associatedParameter, param));
                }

            } else {
                /*
                 * Implicitly associate all parameters unless configured to
                 * disable implicit associaiton.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(processInterfaceEvent)) {
                    for (ProcessRelevantData param : formalParameters.values()) {
                        interfaceData.add(new ActivityInterfaceData(param));
                    }
                }
            }

        }
        return interfaceData;

    }

}
