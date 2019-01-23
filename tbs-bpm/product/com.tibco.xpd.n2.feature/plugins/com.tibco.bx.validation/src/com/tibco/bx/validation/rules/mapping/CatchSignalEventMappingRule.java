/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.process.js.parser.util.ScriptMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException.GetSignalPayloadExceptionType;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractCatchSignalEventMappingRule;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule for Catch Signal Event (signal payload to attached-to-task
 * work item data) mappings.
 * 
 * 
 * @author aallway
 * @since 8 May 2012
 */
public class CatchSignalEventMappingRule extends
        AbstractCatchSignalEventMappingRule {

    private static final String ISSUE_INCONSISTENT_THROW_SIGNAL_PAYLOADS =
            "bx.inconsistentSignalPayloadsForThrow"; //$NON-NLS-1$

    private static final String ISSUE_CATCH_WITH_INCONSISTENT_PAYLOADS_ON_THROWERS =
            "bx.inconsistentSignalPayloadsForCatch"; //$NON-NLS-1$

    private static final String ISSUE_SIGNAL_MAPPING_ON_USERTASK_ONLY =
            "bx.signalMappingOnUserTaskBoundaryOnly"; //$NON-NLS-1$

    Set<Activity> ignoreActivities = Collections.emptySet();

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        ignoreActivities = new HashSet<Activity>();

        preValidateSignalEvents(process);

        super.validate(process);

        ignoreActivities = Collections.emptySet();
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#validateObject(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     */
    @Override
    protected void validateObject(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;
        /*
         * XPD-7075: Perform mapping validations only if the activity is Local
         * Signal Event and its not to be ignored.
         */
        if (EventObjectUtil.isLocalSignalEvent(activity)
                && !ignoreActivities.contains(activity)) {
            super.validateObject(activity);
        }
    }

    /**
     * Various issues prior to validating mappings themselves.
     * <p>
     * This may add activities to {@link #ignoreActivities} to force mappings
     * rules not to be run.
     * 
     * @param process
     * 
     */
    private void preValidateSignalEvents(Process process) {
        /*
         * Signal data mapping only supported on user task boundary.
         */
        validateCatchesAreOnUserTask(process);

        /*
         * All throw signal events with the same signal name in the same process
         * (including events in embedded sub-processes) must have the same
         * associated data set.
         */
        Map<String, Set<Activity>> signalNameToEventSetMap =
                getSignalNameToEventSetMap(process);

        for (Entry<String, Set<Activity>> signalNameAndEvent : signalNameToEventSetMap
                .entrySet()) {
            validateSignalPayloads(signalNameAndEvent.getKey(),
                    signalNameAndEvent.getValue());
        }
    }

    /**
     * Adds any catch signal with mappings that is not attached to task boundary
     * ot the {@link #ignoreActivities} list because it's not worth telling user
     * about broken mappings caused by this.
     * <p>
     * Adds the issue for signal mappings only supported on user task boundary
     * event.
     * 
     * @param process
     */
    private void validateCatchesAreOnUserTask(Process process) {
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            /*
             * XPD-7075 Support this validation only for Local Signals
             */
            if (EventObjectUtil.isLocalSignalEvent(activity)
                    && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(EventObjectUtil
                                    .getEventTriggerType(activity))) {

                List<DataMapping> dataMappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.OUT_LITERAL);

                if (!dataMappings.isEmpty()) {
                    boolean isAttachedToUserTask = false;

                    Activity taskAttachedTo =
                            EventObjectUtil.getTaskAttachedTo(activity);
                    if (taskAttachedTo != null) {
                        if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                                .getTaskTypeStrict(taskAttachedTo))) {
                            isAttachedToUserTask = true;
                        }
                    }

                    if (!isAttachedToUserTask
                            || !EventObjectUtil.isNonCancellingEvent(activity)) {
                        ignoreActivities.add(activity);
                        addIssue(ISSUE_SIGNAL_MAPPING_ON_USERTASK_ONLY,
                                activity);
                    }
                }
            }
        }
    }

    /**
     * Check all "throwers" in set define the same payload, if not, add an issue
     * to each of them and a slightly different issue to the catchers (because
     * the throwers can have a quick fix allowing copy one's payload definition
     * to all others, but the catch signal can't have that).
     * 
     * @param signalName
     * @param eventSet
     */
    private void validateSignalPayloads(String signalName,
            Set<Activity> eventSet) {

        List<Activity> throwerSet = new ArrayList<Activity>();
        List<Activity> catcherSet = new ArrayList<Activity>();

        try {
            /* Separate catchers and throwers. */

            for (Activity activity : eventSet) {
                if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    throwerSet.add(activity);
                } else {
                    catcherSet.add(activity);
                }
            }

            if (throwerSet.size() > 1) {
                /* Throws exception when payload problem is found. */
                EventObjectUtil.validateSignalPayLoads(throwerSet);
            }

        } catch (GetSignalPayloadException e) {
            if (e.getType() == GetSignalPayloadExceptionType.INCONSISTENT_PAYLOADS) {

                for (Activity signalThrower : throwerSet) {
                    addIssue(ISSUE_INCONSISTENT_THROW_SIGNAL_PAYLOADS,
                            signalThrower,
                            Collections.singletonList(signalName));
                }

                for (Activity signalCatcher : catcherSet) {
                    ignoreActivities.add(signalCatcher);

                    addIssue(ISSUE_CATCH_WITH_INCONSISTENT_PAYLOADS_ON_THROWERS,
                            signalCatcher,
                            Collections.singletonList(signalName));
                }
            }
        }
    }

    /**
     * @param process
     * @return A map of unique signal names and the set of throwers and catchers
     *         for that signal.
     */
    private Map<String, Set<Activity>> getSignalNameToEventSetMap(
            Process process) {
        Map<String, Set<Activity>> signalNameToEventSetMap =
                new HashMap<String, Set<Activity>>();

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            /*
             * XPD-7075 Support this validation only for Local Signal Events.
             */
            if (EventObjectUtil.isLocalSignalEvent(activity)) {
                EventTriggerType triggerType =
                        EventObjectUtil.getEventTriggerType(activity);
                if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                        .equals(triggerType)
                        || EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                .equals(triggerType)) {

                    String signalName = EventObjectUtil.getSignalName(activity);

                    if (signalName != null && !signalName.isEmpty()) {
                        /* Get / create set of throwers for this signal. */
                        Set<Activity> signalSet = null;

                        signalSet = signalNameToEventSetMap.get(signalName);

                        if (signalSet == null) {
                            signalSet = new HashSet<Activity>();
                            signalNameToEventSetMap.put(signalName, signalSet);
                        }

                        signalSet.add(activity);
                    }
                }
            }
        }
        return signalNameToEventSetMap;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#isScriptTypeToProcessDataCompatible(com.tibco.xpd.script.model.client.IScriptRelevantData,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath)
     * 
     * @param scriptType
     * @param target
     * @return
     */
    @Override
    protected MappingTypeCompatibility isScriptTypeToProcessDataCompatible(
            IScriptRelevantData scriptType, ConceptPath target) {
        return N2ProcessDataMappingCompatibilityUtil
                .checkTypeCompatibility(scriptType, target);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingFromSourceLevelSupported(java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /*
         * <XPD-4008> Temporary restriction until BX-2470 completed - to prevent
         * mapping from child content of complex data. For XPD-4010 just remove
         * this <XPD-4008> block as child content mapping should now be
         * permissible.
         */
        if (sourceObjectInTree instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) sourceObjectInTree;

            ConceptPath parent = path.getParent();
            if (parent != null) {
                return false;
            }
        }
        /*
         * </XPD-4008>
         */

        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingToTargetLevelSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /*
         * For now (at least) we will only support whole-data mapping
         */
        if (targetObjectInTree instanceof ConceptPath) {
            if (((ConceptPath) targetObjectInTree).getItem() instanceof ProcessRelevantData) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#parseScript(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected ScriptInformationParsed parseScript(Object mapping) {
        if (mapping instanceof Mapping
                && ((Mapping) mapping).getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping =
                    (DataMapping) ((Mapping) mapping).getMappingModel();
            String strScript =
                    ProcessScriptUtil.getDataMappingScript(dataMapping);

            Activity activity = Xpdl2ModelUtil.getParentActivity(dataMapping);
            ScriptInformation scriptInformation =
                    ProcessScriptUtil
                            .getScriptInformationFromDataMapping(dataMapping);
            if (strScript == null || strScript.trim().length() < 1) {
                return new ScriptInformationParsed(scriptInformation, null);
            }

            Process process = Xpdl2ModelUtil.getProcess(activity);
            if (activity != null && scriptInformation != null
                    && process != null) {
                return ScriptMappingUtil
                        .parseScript(mapping,
                                getValidationStrategyList(process, activity),
                                getProcessDestinationList(process),
                                getScriptRelevantDataTypeMap(process,
                                        scriptInformation),
                                getScriptType());
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSOutputMappingRule#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#isUntypedScriptListToArrayMappingAllowed(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    public boolean isUntypedScriptListToArrayMappingAllowed(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        return true;
    }
}
