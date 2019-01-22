/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * ProcessDestSubsetOfProjectDestRule
 * <p>
 * Rule to check that the selected Process Destinations are a subset of the
 * Project destinations.
 * <p>
 * Some builders are based on whether the process has a particular destination
 * set, others (such as BOM->XSD are based on project destination selection.
 * <p>
 * If you have a global destination selected in process that causes xpdl->wsdl
 * builds then you would also need the same set in project to get other things
 * such as BOM->XSD to built).
 * 
 * @author aallway
 * @since 3.3 (16 Oct 2009)
 */
public class PackageDestSubsetOfProjectDestRule extends PackageValidationRule {

    public static final String PKGDEST_NOT_IN_PROJECTDEST =
            "bpmn.packageDestNotInProjectDests"; //$NON-NLS-1$ 

    /*
     * Issue additional infokey for String destination Id of destination in
     * question.
     */
    public static final String BAD_PKGDEST_ADDINFOKEY =
            "invalid.package.destination"; //$NON-NLS-1$ 

    /*
     * Sid XPD-4165. Apologies for the copy of this constant (From
     * SimulationConstants) unfortunately feature dependencies would not allow
     * this import.
     */
    private static final String SIMULATION_DESTINATION = "Simulation"; //$NON-NLS-1$

    @Override
    public void validate(Package pckg) {
        IProject project = WorkingCopyUtil.getProjectFor(pckg);
        if (project != null) {
            /* Get all destinations set on project. */
            Set<String> projectDestinations =
                    GlobalDestinationUtil.getEnabledGlobalDestinations(project,
                            true);

            EList<Process> processes = pckg.getProcesses();
            for (Process process : processes) {
                Set<String> processDestinations =
                        DestinationUtil.getEnabledGlobalDestinations(process);

                processDestinations.removeAll(projectDestinations);

                /*
                 * Sid XPD-4165: Do not enforce
                 * "must have destination set in project if set in process" for
                 * simulation. Simulation destination is only really there to
                 * ensure that specific processes that user wishes to perform
                 * simulation on are valid to be run as such. But most processes
                 * won't be valid so don't want to set it by defaulty for new
                 * processes (which will happen once the simulaiton dest is set
                 * in project.
                 */
                if (processDestinations.contains(SIMULATION_DESTINATION)) {
                    processDestinations.remove(SIMULATION_DESTINATION);
                }

                if (processDestinations.size() > 0) {
                    for (String badDest : processDestinations) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(badDest);
                        messages.add(process.getName());
                        Map<String, String> addInfo =
                                new HashMap<String, String>();
                        addInfo.put(BAD_PKGDEST_ADDINFOKEY, badDest);
                        addIssue(PKGDEST_NOT_IN_PROJECTDEST,
                                process,
                                messages,
                                addInfo);
                    }
                }
            }

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pckg);
            if (processInterfaces != null) {
                List<ProcessInterface> processInterfaceList =
                        processInterfaces.getProcessInterface();

                for (ProcessInterface processInterface : processInterfaceList) {
                    Set<String> procIntfDestinations =
                            DestinationUtil
                                    .getEnabledGlobalDestinations(processInterface);

                    /*
                     * Sid XPD-4165: Do not enforce
                     * "must have destination set in project if set in process"
                     * for simulation.
                     */
                    if (procIntfDestinations.contains(SIMULATION_DESTINATION)) {
                        procIntfDestinations.remove(SIMULATION_DESTINATION);
                    }

                    procIntfDestinations.removeAll(projectDestinations);
                    if (procIntfDestinations.size() > 0) {
                        for (String badDest : procIntfDestinations) {
                            List<String> messages = new ArrayList<String>();
                            messages.add(badDest);
                            messages.add(processInterface.getName());
                            Map<String, String> addInfo =
                                    new HashMap<String, String>();
                            addInfo.put(BAD_PKGDEST_ADDINFOKEY, badDest);
                            addIssue(PKGDEST_NOT_IN_PROJECTDEST,
                                    processInterface,
                                    messages,
                                    addInfo);
                        }
                    }
                }
            }
        }

        return;
    }
}
