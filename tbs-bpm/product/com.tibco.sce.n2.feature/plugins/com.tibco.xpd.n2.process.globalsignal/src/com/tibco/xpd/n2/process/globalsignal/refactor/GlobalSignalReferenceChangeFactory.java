/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultSignal;

/**
 * Factory for Global signal reference change.
 * 
 * @author sajain
 * @since May 18, 2015
 */
public class GlobalSignalReferenceChangeFactory {

    /**
     * Activity referencing global signal.
     */
    private Activity activity;

    /**
     * The reference change.
     */
    private GlobalSignalReferenceChange change;

    /**
     * Rename arguments.
     */
    private RenameArguments args = null;

    /**
     * Element being refactored.
     */
    private EObject element = null;

    /**
     * Factory for Global signal reference change.
     * 
     * @param activity
     * @param args
     * @param element
     */
    public GlobalSignalReferenceChangeFactory(Activity activity,
            RenameArguments args, EObject element) {

        this.activity = activity;
        this.args = args;
        this.element = element;
    }

    /**
     * Get change in global signal reference.
     * 
     * @return Change in global signal reference.
     */
    public GlobalSignalReferenceChange getChange() {

        if (activity != null && activity.getEvent() != null) {

            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            TriggerResultSignal trs = null;

            if (activity.getEvent() instanceof IntermediateEvent) {

                IntermediateEvent intermediateEvent =
                        (IntermediateEvent) (activity.getEvent());

                trs = intermediateEvent.getTriggerResultSignal();

            } else if (activity.getEvent() instanceof StartEvent) {

                StartEvent startEvent = (StartEvent) (activity.getEvent());

                trs = startEvent.getTriggerResultSignal();

            }

            change =
                    new GlobalSignalReferenceChange(trs.getName(), args,
                            element, activity, editingDomain);

        }

        return change;
    }
}
