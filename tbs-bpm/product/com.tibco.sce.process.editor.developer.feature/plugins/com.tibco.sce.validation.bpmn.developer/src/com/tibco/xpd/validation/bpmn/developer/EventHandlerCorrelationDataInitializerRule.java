package com.tibco.xpd.validation.bpmn.developer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceCorrelationData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ActivityPickerFilters;
import com.tibco.xpd.processeditor.xpdl2.util.ActivityPickerFilters.ValidEventHandlerInitialiserActivityFilter;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class EventHandlerCorrelationDataInitializerRule extends
        ProcessActivitiesValidationRule {

    private static final String START_EVTS_INITIALIZER_ISSUE =
            "bpmn.dev.eventHandlerCorrelationDataInitialization"; //$NON-NLS-1$

    private static final String INITIALIZE_ACTIVITIES_CONSTRAINT_ISSUE =
            "bpmn.dev.eventHandlerCorrelationDataInitializeActivityConstraint"; //$NON-NLS-1$

    private static final String INITIALIZE_ACTIVITY_MISSING_ISSUE =
            "bpmn.dev.eventHandlerCorrelationDataInitializeActivitiesMissing"; //$NON-NLS-1$

    public static final String ACTIVITY_REF_ID = "hanging_activity_ref_id_"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        /*
         * ABPM-911: Saket: Event subprocess start events should also be
         * considered as event handlers now.
         */
        if (EventObjectUtil
                .isEventHandlerOrEventSubProcessStartEventActivity(activity)) {

            List<ActivityRef> postStartInitResetActivities =
                    getEventHandlerInitialisingActivities(activity);

            if (!postStartInitResetActivities.isEmpty()) {
                ValidEventHandlerInitialiserActivityFilter validEvtHdlInitialiserFilter =
                        new ActivityPickerFilters.ValidEventHandlerInitialiserActivityFilter();

                Set<String> hangingActivityRefs = new HashSet<String>();

                // ensure evt handler's correlation data initialised by
                // one or more non-start evts
                for (ActivityRef initStartActivityRef : postStartInitResetActivities) {

                    Activity initActivity = initStartActivityRef.getActivity();
                    if (initActivity == null) {
                        hangingActivityRefs
                                .add(initStartActivityRef.getIdRef());

                    } else if (!validEvtHdlInitialiserFilter
                            .select(initActivity)) {

                        // raise error-level issue: display activity name on
                        // issue; pass in activityRefId to be deleted
                        String k = ACTIVITY_REF_ID + 1;
                        String v = initStartActivityRef.getIdRef();
                        Map<String, String> additionalInfo =
                                Collections.<String, String> singletonMap(k, v);

                        addIssue(INITIALIZE_ACTIVITIES_CONSTRAINT_ISSUE,
                                activity,
                                Collections.<String> singletonList(initActivity
                                        .getName()),
                                additionalInfo);
                    }
                }

                if (!hangingActivityRefs.isEmpty()) {

                    // raise error-level issue: passing in activityRefIds to
                    // delete
                    Map<String, String> additionalInfo =
                            new HashMap<String, String>();

                    Iterator<String> it = hangingActivityRefs.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        String k = ACTIVITY_REF_ID + (++i);
                        String v = it.next();
                        additionalInfo.put(k, v);
                    }

                    addIssue(INITIALIZE_ACTIVITY_MISSING_ISSUE,
                            activity,
                            Collections.<String> singletonList(String
                                    .valueOf(hangingActivityRefs.size())),
                            additionalInfo);
                }

            } else {

                /*
                 * Ensure all correlation data will be initialised by all start
                 * events (Sid XPD-3925: regardless of whether they are message
                 * start activities or not - also, make sure it's start of
                 * process activities not necessarily start of embedded
                 * sub-process activties (not sure whether process engine
                 * initialises emb sub proc event handlers straight away as well
                 * - so best be on safe side.
                 */
                List<Activity> startActivities =
                        getAllStartActivities(activity.getProcess());

                /*
                 * Sid XPD-3925, work with sets of activity NOT name, otherwise
                 * duplciate name or no name will mess things up.
                 */
                Map<String, Set<Activity>> correlationDataToStartActivties =
                        getCorrelationDataStartEvtInitialisers(startActivities);

                // consider correlation data for the given event handler
                Collection<ActivityInterfaceCorrelationData> eventHandlerCorrelationData =
                        getActivityInterfaceCorrelationData(activity);

                for (ActivityInterfaceCorrelationData correlationData : eventHandlerCorrelationData) {
                    String correlationDataName =
                            correlationData.getDataField().getName();

                    /*
                     * Compare the number of startActivities with the number of
                     * thoise that actually init' the data, it's a problem if
                     * all don't.
                     */
                    Set<Activity> correlationDataInitialisers =
                            correlationDataToStartActivties
                                    .get(correlationDataName);

                    if (correlationDataInitialisers == null
                            || correlationDataInitialisers.isEmpty()
                            || correlationDataInitialisers.size() != startActivities
                                    .size()) {

                        StringBuilder lstOfStartActivities =
                                new StringBuilder();

                        for (Activity startActivity : startActivities) {
                            if (correlationDataInitialisers == null
                                    || !correlationDataInitialisers
                                            .contains(startActivity)) {
                                if (lstOfStartActivities.length() != 0) {
                                    lstOfStartActivities.append("; "); //$NON-NLS-1$
                                }
                                lstOfStartActivities.append('\'');
                                lstOfStartActivities.append(Xpdl2ModelUtil
                                        .getDisplayNameOrName(startActivity));
                                lstOfStartActivities.append('\'');
                            }
                        }

                        // raise error-level issue
                        addIssue(START_EVTS_INITIALIZER_ISSUE,
                                activity,
                                Arrays.asList(new String[] {
                                        correlationDataName,
                                        lstOfStartActivities.toString() }));
                    }
                }

            }
        }
    }

    /**
     * @param eventHandler
     * @return all activities capable of initialising the specified event
     *         handler
     */
    protected List<ActivityRef> getEventHandlerInitialisingActivities(
            Activity eventHandler) {

        List<ActivityRef> ret = Collections.<ActivityRef> emptyList();

        Object eo = eventHandler.getEvent().getEventTriggerTypeNode();
        if (eo instanceof TriggerResultMessage) {
            TriggerResultMessage trm = (TriggerResultMessage) eo;

            EventHandlerInitialisers evtHdlInitialisers =
                    (EventHandlerInitialisers) Xpdl2ModelUtil
                            .getOtherElement(trm, XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_EventHandlerInitialisers());

            if (evtHdlInitialisers != null) {

                List<ActivityRef> activityRefs =
                        evtHdlInitialisers.getActivityRef();
                if (activityRefs != null && !activityRefs.isEmpty()) {

                    ret = new ArrayList<ActivityRef>();
                    for (ActivityRef activityRef : activityRefs) {
                        ret.add(activityRef);
                    }
                }
            }
        }

        return ret;
    }

    /**
     * SID XPD-3925: Actually we need to return ALL start activities regardless
     * of whether there incoming request. In essence we need to ensure that
     * correlation data is mapped in all start activties - if there's one that
     * isn't incoming request then it just means thats a start that it's not
     * mapped in.
     * 
     * @param flowContainer
     * @return List of all start activities with incoming request and
     *         correlation data settings
     */
    private List<Activity> getAllStartActivities(FlowContainer flowContainer) {

        List<Activity> ret = new ArrayList<Activity>();

        for (Activity activity : flowContainer.getActivities()) {

            /*
             * ABPM-911: Saket: Event subprocess start events should also be
             * considered as event handlers now.
             */
            if (Xpdl2ModelUtil.isStartProcessActivity(activity)
                    && !EventObjectUtil.isEventSubProcessStartEvent(activity)) {
                ret.add(activity);
            }
        }

        return ret;
    }

    /**
     * Constructs a look up table mapping names of correlation data to the set
     * of activities that will initialise them.
     * 
     * @param startActivities
     *            All start actvities in a given flow container.
     * @return map of correlation data name to start activity names that
     *         initialise it.
     */
    private Map<String, Set<Activity>> getCorrelationDataStartEvtInitialisers(
            List<Activity> startActivities) {

        Map<String, Set<Activity>> ret = new HashMap<String, Set<Activity>>();

        for (Activity startAct : startActivities) {

            if (Xpdl2ModelUtil.isMessageProcessStartActivity(startAct)) {

                for (ActivityInterfaceCorrelationData corrData : ActivityInterfaceDataUtil
                        .getActivityInterfaceCorrelationData(startAct)) {

                    switch (corrData.getCorrelationMode()) {
                    case INITIALIZE: // fall-through white list
                    case JOIN: {
                        String key = corrData.getDataField().getName();
                        Set<Activity> startEvtsCollection = ret.get(key);
                        if (startEvtsCollection == null) {
                            startEvtsCollection = new HashSet<Activity>();
                            ret.put(key, startEvtsCollection);
                        }
                        startEvtsCollection.add(startAct);
                    }
                    }
                    ;
                }
            }
        }
        return ret;
    }

    /**
     * 
     * @param activity
     * @return the Correlation Data associated with the Activity.
     */
    protected Collection<ActivityInterfaceCorrelationData> getActivityInterfaceCorrelationData(
            Activity activity) {
        return ActivityInterfaceDataUtil
                .getActivityInterfaceCorrelationData(activity);
    }

}
