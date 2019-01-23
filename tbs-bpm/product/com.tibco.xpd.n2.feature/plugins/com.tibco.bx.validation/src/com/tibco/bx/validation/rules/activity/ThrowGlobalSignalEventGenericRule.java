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

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.rules.mapping.ThrowGlobalSignalEventMappingRule;
import com.tibco.bx.validation.rules.scripts.BxJsThrowGlobalSignalMappingScriptRule;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.SetReferencedProjectResolution;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Generic rules for Global Throw signal events.
 * <p>
 * Note that this class is designed in such a way that only those global throw
 * events which do not have any generic errors will have mapping validations run
 * through them.
 * <p>
 * The Throw Global Signal event Script Rules are defined in class
 * {@link BxJsThrowGlobalSignalMappingScriptRule}
 * 
 * 
 * @author kthombar
 * @since Feb 18, 2015
 */
public class ThrowGlobalSignalEventGenericRule extends
        ThrowGlobalSignalEventMappingRule {

    private Set<Activity> ignoreActivities = Collections.emptySet();

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
        preValidateThrowSignalEvents(process);

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
     * pre validate throw signal events so that only those events which do not
     * have generic issue will have mapping validations run on.
     * 
     * @param process
     */
    private void preValidateThrowSignalEvents(Process process) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : allActivitiesInProc) {

            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(activity);

            if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                    .equals(eventTriggerType)
                    && GlobalSignalUtil.isGlobalSignalEvent(activity)) {

                validateGlobalThrowSignal(activity, process);
            }
        }
    }

    /**
     * Validates the Global Catch Signal Events
     * 
     * @param activity
     * @param process
     */
    private void validateGlobalThrowSignal(Activity activity, Process process) {
        /*
         * Go throgh the most basic checks first and return immediately so that
         * the most basic problems are raised and addressed first.
         */

        /*
         * raise problem if no global signal name is specified.
         */
        if (CatchGlobalSignalEventGenericRule.isEventSignalNameEmpty(activity)) {

            addIssue(CatchGlobalSignalEventGenericRule.ISSUE_GLOBAL_SIGNAL_EVENT_MUST_REFERENCE_GLOBAL_SIGNAL,
                    activity);

            ignoreActivities.add(activity);
            return;
        }

        /*
         * raise problem if the referenced global signal is no longer availale
         */
        String signalName = EventObjectUtil.getSignalName(activity);

        if (CatchGlobalSignalEventGenericRule
                .isGlobalSignalReferenceUnresolved(signalName)) {

            int indexOfHash = signalName.indexOf("#"); //$NON-NLS-1$

            if (indexOfHash != -1) {
                String unresolvedSignalName =
                        signalName.substring(indexOfHash + 1,
                                signalName.length())
                                + " (" //$NON-NLS-1$
                                + signalName.substring(0, indexOfHash) + ")"; //$NON-NLS-1$

                addIssue(CatchGlobalSignalEventGenericRule.ISSUE_UNRESOLVED_GLOBAL_SIGNAL_REFERENCE,
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

        if (CatchGlobalSignalEventGenericRule
                .isGsdProjectNotReferenced(activity, globalSignal)) {

            Map<String, String> additional = new HashMap<String, String>();
            additional
                    .put(SetReferencedProjectResolution.PROJECTNAME_ADDITIONAL_INFO,
                            WorkingCopyUtil.getProjectFor(globalSignal)
                                    .getName());

            addIssue(CatchGlobalSignalEventGenericRule.ISSUE_GLOBAL_SIGNAL_IN_NON_REFERENCED_PROJ,
                    activity,
                    Collections.singletonList(globalSignal.getName()),
                    additional);

            ignoreActivities.add(activity);

            return;
        }
    }
}
