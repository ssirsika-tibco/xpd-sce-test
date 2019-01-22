/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SetProcessesAndPageflowsToBPMDestinationResolution
 * <p>
 * This class sets all the process(es) and pageflow(s) to the BPM Destination(s)
 * set on their project.
 * <p>
 * 
 * @author sajain
 * @since Dec 18, 2012
 */
public class SetBPMDestinationResolution extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target != null) {
            IProject project = WorkingCopyUtil.getProjectFor(target);

            if (project != null) {
                Package pkg = Xpdl2ModelUtil.getPackage(target);

                if (pkg != null) {
                    EList<Process> processes = pkg.getProcesses();

                    Collection<String> n2DestinationNames =
                            GlobalDestinationHelper
                                    .getGlobalDestinationNamesForId(N2Utils.N2_GLOBAL_DESTINATION_ID);
                    Collection<String> allDestinationsEnabledOnProject =
                            GlobalDestinationUtil
                                    .getEnabledGlobalDestinations(project, true);
                    Collection<String> n2DestinationsEnabledOnProject =
                            new ArrayList<String>();
                    CompoundCommand cmd = new CompoundCommand();

                    /*
                     * There may be more than one BPM destination defined, so
                     * get all that are enabled for project.
                     */
                    for (String globalDestinationName : n2DestinationNames) {
                        for (String globalDestinationNameEnabled : allDestinationsEnabledOnProject) {
                            if (globalDestinationName
                                    .equals(globalDestinationNameEnabled)) {
                                n2DestinationsEnabledOnProject
                                        .add(globalDestinationName);
                            }
                        }
                    }

                    /*
                     * Add all enabled BPM destinations to process that are not
                     * already set.
                     */
                    for (String globalDestinationNameEnabledOnProject : n2DestinationsEnabledOnProject) {
                        for (Process process : processes) {
                            if (!DestinationUtil
                                    .isGlobalDestinationEnabled(process,
                                            globalDestinationNameEnabledOnProject)) {
                                cmd.append(DestinationUtil
                                        .getSetDestinationEnabledCommand(editingDomain,
                                                process,
                                                globalDestinationNameEnabledOnProject,
                                                true));
                            }
                        }
                    }
                    return cmd;

                }
            }
        }
        return null;
    }
}
