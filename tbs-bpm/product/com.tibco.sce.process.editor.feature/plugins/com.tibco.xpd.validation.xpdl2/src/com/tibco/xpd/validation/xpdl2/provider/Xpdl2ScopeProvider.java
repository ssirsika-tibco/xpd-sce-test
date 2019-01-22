/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Analyses the scope of the detected change, including any dependencies, and
 * adds the affected objects to the validation queue.
 * 
 * @author nwilson
 */
public class Xpdl2ScopeProvider implements IScopeProvider {

    /**
     * @param validationDestinationEnd
     *            The destination environment.
     * @param providerId
     *            The validation provider ID.
     * @param item
     *            The validation item.
     * @return A collection of objects that will need validation.
     * @see com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com.tibco.xpd.validation.engine.ValidationItem)
     */
    @Override
    public Collection<EObject> getAffectedObjects(
            Destination validationDestinationEnd, String providerId,
            IValidationItem item) {

        ArrayList<EObject> affectedObjects = new ArrayList<EObject>();

        /*
         * The given validation destination is now abstracted from the
         * destination stored in xpdl2 (via the global destination environment).
         * 
         * Therefore when asking 'is this validation dest environment enabled'
         * we must find the global dest envs that bind to it and ask if they are
         * enabled.
         */
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();

        String validationDestinationId = validationDestinationEnd.getId();

        Set<String> globalNames =
                preferences
                        .getGlobalDestinationsForValidationDestination(validationDestinationId);

        if (!validationDestinationEnd.isSelectable() || !globalNames.isEmpty()) {
            /*
             * If this is unselectable destination (like BPMN) then it won't be
             * in any global destination sets. OR if there are global
             * destinations that use this validation destination component. THEN
             * check for process / interface that have a global destination set
             * that uses the validation destination.
             */
            WorkingCopy wc = item.getWorkingCopy();
            EObject root = wc.getRootElement();
            if (root instanceof Package) {
                Package pckg = (Package) root;

                List<Process> processes = pckg.getProcesses();
                for (Process process : processes) {

                    if (!validationDestinationEnd.isSelectable()
                            || isEnabled(globalNames, process)) {

                        /*
                         * Sid XPD-2516: Check contributions to
                         * processEditorConfiguration
                         * /ValidationDestinationExclusions extension point
                         * before finally commiting items to the list for this
                         * validation-dest and provider.
                         */
                        if (!ProcessEditorConfigurationUtil
                                .isExcludedValidationProvider(process,
                                        validationDestinationId,
                                        providerId)) {
                            affectedObjects.add(process);
                            for (Object nextj : process.getActivitySets()) {
                                affectedObjects.add((EObject) nextj);
                            }
                        }
                    }
                }

                if (ProcessInterfaceUtil.getProcessInterfaces(pckg) != null) {
                    List<ProcessInterface> processInterfaces =
                            ProcessInterfaceUtil.getProcessInterfaces(pckg)
                                    .getProcessInterface();
                    for (ProcessInterface processInterface : processInterfaces) {
                        /*
                         * Go thru the process interface destination
                         * environments checking for enabled ones (non-user
                         * selectable ones are always enabled).
                         */
                        if (!validationDestinationEnd.isSelectable()
                                || isEnabled(globalNames, processInterface)) {
                            /*
                             * Sid XPD-2516: Check contributions to
                             * processEditorConfiguration
                             * /ValidationDestinationExclusions extension point
                             * before finally commiting items to the list for
                             * this validation-dest and provider.
                             */
                            if (!ProcessEditorConfigurationUtil
                                    .isExcludedValidationProvider(processInterface,
                                            validationDestinationId,
                                            providerId)) {
                                affectedObjects.add(processInterface);
                            }
                        }
                    }
                }

                /*
                 * Only add the package to be validated when there are processes
                 * or interfaces with destination set.
                 */
                if (!affectedObjects.isEmpty()) {
                    affectedObjects.add(0, pckg);
                }

            }
        }

        /* Remove any objects undesired by subclass */
        for (Iterator<EObject> iterator = affectedObjects.iterator(); iterator
                .hasNext();) {

            EObject object = iterator.next();
            if (!filterAffectedObject(object)) {

                iterator.remove();
            }
        }

        return affectedObjects;
    }

    /**
     * Last chance to alter the list of affected objects returned by the scope
     * provider.
     * 
     * @param eObject
     * @return true if object should be validated, false if it should not.
     */
    protected boolean filterAffectedObject(EObject eObject) {

        return true;
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
            }
        }
        return enabled;

    }

    /**
     * @param destination
     *            The process interface destination environment.
     * @param processInterface
     *            The process interface.
     * @return true if the environment is enabled for this process.
     */
    private boolean isEnabled(Set<String> globalNames,
            ProcessInterface processInterface) {
        boolean enabled = false;

        for (String name : globalNames) {
            if (DestinationUtil.isGlobalDestinationEnabled(processInterface,
                    name)) {
                enabled = true;
            }
        }
        return enabled;

    }
}
