/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution to refactor duplicate {@link Activity} names in a
 * {@link Process}.
 * 
 * @author sajain
 * @since Apr 25, 2014
 */
public class SetUniqueActivityNameContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$

            /*
             * Sid XPD-6230 No need to set task name if it's judst a repeat of
             * the contribution plugin.xml desc' as that is output by framework
             * anyway.
             */

            for (Process eachProcess : processes) {

                List<NamedElement> allProcessActivities =
                        new ArrayList<NamedElement>();

                /*
                 * Fetch all activities from processes.
                 */
                allProcessActivities.addAll(eachProcess.getActivities());

                /*
                 * Fetch all activities from activity sets.
                 */
                EList<ActivitySet> allActivitySets =
                        eachProcess.getActivitySets();
                for (ActivitySet eachActivitySet : allActivitySets) {
                    allProcessActivities
                            .addAll(eachActivitySet.getActivities());
                }

                if (allProcessActivities != null
                        && !allProcessActivities.isEmpty()) {
                    refactorDuplicateActivityNames(allProcessActivities);
                }

            }

            for (ProcessInterface eachProcessInterface : processInterfaces) {

                List<NamedElement> allProcessInterfaceMethods =
                        new ArrayList<NamedElement>();
                allProcessInterfaceMethods.addAll(eachProcessInterface
                        .getStartMethods());
                allProcessInterfaceMethods.addAll(eachProcessInterface
                        .getIntermediateMethods());

                if (allProcessInterfaceMethods != null
                        && !allProcessInterfaceMethods.isEmpty()) {
                    refactorDuplicateActivityNames(allProcessInterfaceMethods);
                }
            }

            monitor.worked(1);

            /*
             * XPD-6370: Main framework now reports status with each conversion
             * description. so no need to do it here also.
             */
            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Refactor duplicate {@link NamedElement} names.
     * 
     * @param allProcessActivities
     *            List of activities and methods in processes/process
     *            interfaces.
     */
    private void refactorDuplicateActivityNames(
            List<NamedElement> allProcessActivities) {
        Set<String> activityNames = new HashSet<String>();

        for (NamedElement eachActivity : allProcessActivities) {
            String name = eachActivity.getName();

            if (null == name || name.isEmpty()) {
                /*
                 * XPD-6229 Events that are not atached to any activities should
                 * not have null names, they should be given their default
                 * names.
                 */
                setNullEventNamesToDefaultNames(eachActivity);
                name = eachActivity.getName();
            }

            if (name != null && !name.isEmpty() && activityNames.contains(name)) {
                /*
                 * Duplicate name detected, make it unique.
                 */

                name =
                        Xpdl2ModelUtil.getUniqueNameInSet(name,
                                allProcessActivities,
                                false);
                eachActivity.setName(name);
            }

            activityNames.add(name);
        }
    }

    /**
     * Checks if the passed {@link NamedElement} is an {@link Event} . If it is
     * then sets the name and display name of the Event based on its Trigger
     * Type.
     * 
     * @param namedElement
     */
    private void setNullEventNamesToDefaultNames(NamedElement namedElement) {

        if (namedElement instanceof Activity) {

            Activity activity = (Activity) namedElement;
            String displayName = null;
            Event event = activity.getEvent();

            if (event != null) {

                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(activity);

                if (!EventObjectUtil.isAttachedToTask(activity)) {
                    /*
                     * we dont wish to add names to events attached to Tasks,
                     * un-necessary loss of visibility
                     */
                    if (EventTriggerType.EVENT_NONE_LITERAL
                            .equals(eventTriggerType)) {
                        /*
                         * If there is no Trigger Type set that indicates the
                         * event is purely either a Start/Intermediate/End event
                         */
                        if (event instanceof StartEvent) {
                            displayName =
                                    Messages.SetUniqueActivityNameContribution_StartEvent_label;
                        } else if (event instanceof IntermediateEvent) {
                            displayName =
                                    Messages.SetUniqueActivityNameContribution_IntermediateEvent_label;
                        } else if (event instanceof EndEvent) {
                            displayName =
                                    Messages.SetUniqueActivityNameContribution_EndEvent_label;
                        }

                    } else {
                        /* set the event trigger type to display name */
                        displayName = eventTriggerType.toString();
                    }
                }
            }

            if (displayName != null) {

                activity.setName(NameUtil.getInternalName(displayName, false));
                Xpdl2ModelUtil.setOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        displayName);
            }
        }
    }
}
