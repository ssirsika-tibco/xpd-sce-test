/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The ActivityScopeProvider is intended to provide performance benefits for
 * rules that can run in isolation on a particular activity.
 * <p>
 * If an Activity or an element nested inside the activity changes then only
 * that particular activity is re-validated.
 * <p>
 * If a Package changes (usually indicating a clean build) then all activities
 * in the Package are revalidated.
 * <p>
 * Note that any rules associted with this scope provider should extend
 * ActivityValidationRule. If the relationship to other activities is more
 * complicated (for example if a change in another activity will require this
 * activity to be revalidated) then you should use a different scope provider.
 * 
 * @author nwilson
 */
public class ActivityScopeProvider implements IScopeProvider {

    /**
     * @param destination
     *            The destination environment.
     * @param providerId
     *            The validation provider ID.
     * @param item
     *            The validation item.
     * @return A collection of objects that will need validation.
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com.tibco.xpd.validation.engine.ValidationItem)
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Collection<EObject> affected = new HashSet<EObject>();
        Collection<EObject> objects = item.getObjects();
        // The given validation destination is now abstracted from the
        // destination stored in xpdl2 (via the global destination environment).
        //
        // Therefore when asking 'is this validation dest environment enabled'
        // we must find the global dest envs that bind to it and ask if they
        // are enabled.
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        Set<String> globalNames =
                preferences
                        .getGlobalDestinationsForValidationDestination(destination
                                .getId());
        for (EObject eo : objects) {
            affected.addAll(getAffectedObjects(eo,
                    destination,
                    providerId,
                    globalNames));
        }
        return affected;
    }

    /**
     * @param eo
     *            The object to get affected items for.
     * @param providerId
     * @param globalNames
     * @return The affected items.
     */
    private Collection<EObject> getAffectedObjects(EObject eo,
            Destination destination, String providerId, Set<String> globalNames) {
        Collection<EObject> affected = new HashSet<EObject>();
        EObject top = eo;

        if (top instanceof Activity) {
            Process process = ((Activity) top).getProcess();
            if (process != null) {
                if (!destination.isSelectable() || isEnabled(globalNames, process)) {
                    /*
                     * Sid XPD-2516: Check contributions to
                     * processEditorConfiguration
                     * /ValidationDestinationExclusions extension point before
                     * finally commiting items to the list for this
                     * validation-dest and provider.
                     */
                    if (!ProcessEditorConfigurationUtil
                            .isExcludedValidationProvider(process,
                                    destination.getId(),
                                    providerId)) {
                        affected.add(top);
                    }
                }
            }
        } else if (top instanceof com.tibco.xpd.xpdl2.Package) {
            Package pckg = (Package) top;
            List<Process> processes = pckg.getProcesses();
            for (Process process : processes) {
                if (!destination.isSelectable()
                        || isEnabled(globalNames, process)) {
                    /*
                     * Sid XPD-2516: Check contributions to
                     * processEditorConfiguration
                     * /ValidationDestinationExclusions extension point before
                     * finally commiting items to the list for this
                     * validation-dest and provider.
                     */
                    if (!ProcessEditorConfigurationUtil
                            .isExcludedValidationProvider(process,
                                    destination.getId(),
                                    providerId)) {
                        for (Activity activity : process.getActivities()) {
                            affected.add(activity);
                        }
                        for (ActivitySet set : process.getActivitySets()) {
                            for (Activity activity : set.getActivities()) {
                                affected.add(activity);
                            }
                        }
                    }
                }
            }
        } else if (eo != null) {
            Activity activity = Xpdl2ModelUtil.getParentActivity(eo);
            if (activity != null) {
                affected =
                        getAffectedObjects(activity,
                                destination,
                                providerId,
                                globalNames);
            }
        }
        return affected;
    }

    /**
     * @param destination
     *            The process destination environment.
     * @param process
     *            The process.
     * @return true if the environment is enabled for this process.
     */
    private boolean isEnabled(Set<String> globalNames, Process process) {
        boolean enabled = false;

        for (String name : globalNames) {
            if (DestinationUtil.isGlobalDestinationEnabled(process, name)) {
                enabled = true;
                break;
            }
        }
        return enabled;

    }

}
