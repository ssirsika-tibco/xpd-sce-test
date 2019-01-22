/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceCorrelationData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.bpmn.developer.EventHandlerCorrelationDataInitializerRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Event handler initialization rules for Global Signal Event handlers.
 * 
 * @author kthombar
 * @since Feb 19, 2015
 */
public class GlobalSignalEventHandlerCorrelationDataInitializerRule extends
        EventHandlerCorrelationDataInitializerRule {

    /**
     * @see com.tibco.xpd.validation.bpmn.developer.EventHandlerCorrelationDataInitializerRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        /*
         * Run validations only for signal event handlers and start signal event
         * subprocess.
         */
        if (EventObjectUtil
                .isEventHandlerOrEventSubProcessStartEventActivity(activity)
                && GlobalSignalUtil.isGlobalSignalEvent(activity)) {
            super.validate(activity);
        }
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.developer.EventHandlerCorrelationDataInitializerRule#getActivityInterfaceCorrelationData(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return all the Activity Interface Correlation Data.
     */
    @Override
    protected Collection<ActivityInterfaceCorrelationData> getActivityInterfaceCorrelationData(
            Activity activity) {
        /*
         * get the activity interface data.
         */
        Collection<ActivityInterfaceData> associatedData =
                ActivityInterfaceDataUtil.getActivityInterfaceData(activity);

        Set<ActivityInterfaceCorrelationData> activityInterfaceCorrelationData =
                new HashSet<ActivityInterfaceCorrelationData>();

        if (associatedData != null && !associatedData.isEmpty()) {
            for (ActivityInterfaceData eachAssociatedData : associatedData) {
                ProcessRelevantData processData = eachAssociatedData.getData();

                if (processData instanceof DataField
                        && ((DataField) processData).isCorrelation()) {
                    /*
                     * If the field is a correaltion field then add to list and
                     * return.
                     */
                    activityInterfaceCorrelationData
                            .add(new ActivityInterfaceCorrelationData(
                                    (DataField) processData,
                                    CorrelationMode.CORRELATE));
                }
            }
        }
        return activityInterfaceCorrelationData;
    }

    /**
     * @param eventHandler
     * @return all activities capable of initialising the specified event
     *         handler
     */
    @Override
    protected List<ActivityRef> getEventHandlerInitialisingActivities(
            Activity eventHandler) {

        List<ActivityRef> ret = Collections.<ActivityRef> emptyList();

        Object eo = eventHandler.getEvent().getEventTriggerTypeNode();
        if (eo instanceof TriggerResultSignal) {
            TriggerResultSignal triggerResultSignal = (TriggerResultSignal) eo;
            /*
             * get the event handler initializers
             */
            EventHandlerInitialisers evtHdlInitialisers =
                    (EventHandlerInitialisers) Xpdl2ModelUtil
                            .getOtherElement(triggerResultSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_EventHandlerInitialisers());

            if (evtHdlInitialisers != null) {

                List<ActivityRef> activityRefs =
                        evtHdlInitialisers.getActivityRef();
                if (activityRefs != null && !activityRefs.isEmpty()) {
                    /*
                     * collect the Activity Refs and return them.
                     */
                    ret = new ArrayList<ActivityRef>();
                    for (ActivityRef activityRef : activityRefs) {
                        ret.add(activityRef);
                    }
                }
            }
        }

        return ret;
    }
}
