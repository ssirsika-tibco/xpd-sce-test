/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.rules.mapping.CatchGlobalSignalEventMappingRule;
import com.tibco.bx.validation.rules.scripts.BxJsCatchGlobalSignalMappingScriptRule;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.SetReferencedProjectResolution;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Generic rules for Global catch signal events.
 * <p>
 * Note that this class is designed in such a way that only those global catch
 * events which do not have any generic errors will have mapping validations run
 * through them.
 * <p>
 * The Catch Global Signal event Script Rules are defined in class
 * {@link BxJsCatchGlobalSignalMappingScriptRule}
 * 
 * @author kthombar
 * @since Feb 18, 2015
 */
public class CatchGlobalSignalEventGenericRule extends
        CatchGlobalSignalEventMappingRule {

    private Set<Activity> ignoreActivities = Collections.emptySet();

    /**
     * Catch global signal events are only supported for Business Processes.
     */
    private static final String ISSUE_CATCH_GLOBAL_SIGNAL_EVENT_ONLY_SUPPORTED_FOR_BIZ_PROCESS =
            "bx.catchGlobalSignalEventOnlySupportedForBusinessProcess"; //$NON-NLS-1$

    /**
     * Catch global signals are only supported for signal event handlers and
     * Event sub-process start signal events.
     */
    private static final String ISSUE_CATCH_GLOBAL_SIGNAL_EVENT_ONLY_SUPPORTED_AS_EVENT_HANDLER_AND_EVENT_SUB_PROC_START_EVENT =
            "bx.catchGlobalSignalEventOnlySupportedAsEventHandlersAndEventSubProcStartEvent"; //$NON-NLS-1$

    /**
     * Referenced global signal '%1$s' resides in an unreferenced project.
     */
    public static final String ISSUE_GLOBAL_SIGNAL_IN_NON_REFERENCED_PROJ =
            "bx.globalSignalInNonReferencedProject"; //$NON-NLS-1$

    /**
     * Global signal event must reference a specific global signal.
     */
    public static final String ISSUE_GLOBAL_SIGNAL_EVENT_MUST_REFERENCE_GLOBAL_SIGNAL =
            "bx.globalSignalEventMustReferenceGlobalSignal"; //$NON-NLS-1$

    /**
     * Global signal '%1$s' is no longer available
     */
    public static final String ISSUE_UNRESOLVED_GLOBAL_SIGNAL_REFERENCE =
            "bx.unresolvedGlobalSignalReference"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        ignoreActivities = new HashSet<Activity>();
        /*
         * pre validate catch signal events so that only those events which do
         * not have generic issue will have mapping validations run on.
         */
        preValidateCatchSignalEvents(process);

        super.validate(process);

        ignoreActivities = Collections.emptySet();
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#validateObject(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     */
    @Override
    protected void validateObject(EObject objectToValidate) {
        if (objectToValidate instanceof Activity) {
            Activity activity = (Activity) objectToValidate;

            if (GlobalSignalUtil.isGlobalSignalEvent(activity)
                    && !ignoreActivities.contains(activity)) {
                super.validateObject(activity);
            }
        }
    }

    /**
     * pre validate catch signal events so that only those events which do not
     * have generic issue will have mapping validations run on.
     * 
     * @param process
     */
    private void preValidateCatchSignalEvents(Process process) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : allActivitiesInProc) {

            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(activity);

            if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                    .equals(eventTriggerType)
                    && GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                validateGlobalCatchSignal(activity, process);
            }
        }
    }

    /**
     * Validates the Global Catch Signal Events
     * 
     * @param activity
     * @param process
     */
    private void validateGlobalCatchSignal(Activity activity, Process process) {

        /*
         * Go throgh the most basic checks first and return immediately so that
         * the most basic problems are raised and addressed first.
         */
        /*
         * catch global signals are only supported in business processes
         */
        if (!Xpdl2ModelUtil.isBusinessProcess(process)) {
            ignoreActivities.add(activity);
            addIssue(ISSUE_CATCH_GLOBAL_SIGNAL_EVENT_ONLY_SUPPORTED_FOR_BIZ_PROCESS,
                    activity);
            return;
        }
        /*
         * catch global signals are only supported as event handlers or as event
         * sub process start events.
         */
        if (!Xpdl2ModelUtil.isEventHandlerActivity(activity)
                && !Xpdl2ModelUtil.isEventSubProcessStartEvent(activity)) {
            ignoreActivities.add(activity);
            addIssue(ISSUE_CATCH_GLOBAL_SIGNAL_EVENT_ONLY_SUPPORTED_AS_EVENT_HANDLER_AND_EVENT_SUB_PROC_START_EVENT,
                    activity);
            return;
        }
        /*
         * raise problem if no global signal name is specified.
         */
        if (isEventSignalNameEmpty(activity)) {

            addIssue(ISSUE_GLOBAL_SIGNAL_EVENT_MUST_REFERENCE_GLOBAL_SIGNAL,
                    activity);

            ignoreActivities.add(activity);
            return;
        }

        /*
         * raise problem if the referenced global signal is no longer availale
         */
        String signalName = EventObjectUtil.getSignalName(activity);

        if (isGlobalSignalReferenceUnresolved(signalName)) {

            int indexOfHash = signalName.indexOf("#"); //$NON-NLS-1$

            if (indexOfHash != -1) {
                String unresolvedSignalName =
                        signalName.substring(indexOfHash + 1,
                                signalName.length())
                                + " (" //$NON-NLS-1$
                                + signalName.substring(0, indexOfHash) + ")"; //$NON-NLS-1$

                addIssue(ISSUE_UNRESOLVED_GLOBAL_SIGNAL_REFERENCE,
                        activity,
                        Collections.singletonList(unresolvedSignalName));

                ignoreActivities.add(activity);
                return;
            }
        }

        /*
         * raise problem marker if the GSD project containing the global signal
         * is not referenced
         */
        GlobalSignal globalSignal =
                GlobalSignalUtil.getReferencedGlobalSignal(activity);

        if (isGsdProjectNotReferenced(activity, globalSignal)) {

            Map<String, String> additional = new HashMap<String, String>();
            additional
                    .put(SetReferencedProjectResolution.PROJECTNAME_ADDITIONAL_INFO,
                            WorkingCopyUtil.getProjectFor(globalSignal)
                                    .getName());

            addIssue(ISSUE_GLOBAL_SIGNAL_IN_NON_REFERENCED_PROJ,
                    activity,
                    Collections.singletonList(globalSignal.getName()),
                    additional);

            ignoreActivities.add(activity);

            return;
        }
    }

    /**
     * @param globalSignalName
     * @return <code>true</code> if the passed global signal is no longer
     *         available else return <code>false</code>
     */
    public static boolean isGlobalSignalReferenceUnresolved(
            String globalSignalName) {
        if (globalSignalName != null && globalSignalName.length() != 0) {

            GlobalSignal globalSignalFromName =
                    GlobalSignalUtil
                            .getGlobalSignalFromName(globalSignalName);
            if (globalSignalFromName == null) {

                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the Signal Event Signal name is empty else
     *         return <code>false</code>
     */
    public static boolean isEventSignalNameEmpty(Activity activity) {

        String signalName = EventObjectUtil.getSignalName(activity);
        if (signalName == null || signalName.length() == 0) {

            return true;
        }
        return false;
    }

    /**
     * Check if Gsd project containing the global signal is NOT referenced by
     * the bpm project
     * 
     * @param activity
     * @param globalSignal
     * @return <code>true</code> if Gsd project containing the global signal is
     *         NOT referenced by the bpm project else return <code>false</code>
     */
    public static boolean isGsdProjectNotReferenced(Activity activity,
            GlobalSignal globalSignal) {

        if (globalSignal != null) {

            if (!isInSameProjectSet(activity, globalSignal)) {

                return true;
            }
        }
        return false;
    }

    /**
     * @param activity
     * @param globalSignal
     * 
     * @return true if the two objects are in the same cross-referencing set of
     *         projects or false if not.
     */
    private static boolean isInSameProjectSet(Activity activity,
            GlobalSignal globalSignal) {

        IProject referencerProject = WorkingCopyUtil.getProjectFor(activity);
        IProject referenceeProject =
                WorkingCopyUtil.getProjectFor(globalSignal);

        if (referencerProject != null && referenceeProject != null) {
            try {
                return ProjectUtil.isProjectReferenced(referencerProject,
                        referenceeProject);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
