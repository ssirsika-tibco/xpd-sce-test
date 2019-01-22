/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.moveprocess;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.MoveProcessProcessor;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Participant that adds necessary project references if the process being moved
 * has dependencies on GSD project and post move the target project needs to add
 * dependency to the GSD project . Note: the processor for this participant is
 * {@link MoveProcessProcessor}
 * 
 * @author kthombar
 * @since Mar 25, 2015
 */
public class AddRequiredProjectReferencesDueToGsdProjectDependencyParticipant
        extends AbstractAddProjectReferencesForProcessMove {

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove#getChangeName()
     * 
     * @return
     */
    @Override
    protected String getChangeName() {

        return Messages.AddRequiredProjectReferencesDueToGsdProjectDependencyParticipant_AddProjectReferenceDueToGsdDependency_msg;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove#getAllProjectsThatTargetProjectMustReference(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param processToMove
     *            the process being moved
     * @param destinationResource
     *            the destination resource to which the project is being moved
     * @param allProjectsToReference
     *            Set which should be populated with all the GSD projects that
     *            the target Process project should reference.
     */
    @Override
    protected Set<IProject> getAllProjectsThatTargetProjectMustReference(
            Process processToMove, IResource destinationResource) {
        Set<IProject> allProjectsToReference = new HashSet<IProject>();

        /* add other project references. */
        addProjectReferencesDueToGsddependency(processToMove,
                destinationResource,
                allProjectsToReference);

        return allProjectsToReference;
    }

    /**
     * @param processToMove
     * @param destinationResource
     * @param allProjectsToReference
     */
    private void addProjectReferencesDueToGsddependency(Process processToMove,
            IResource destinationResource, Set<IProject> allProjectsToReference) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(processToMove);

        for (Activity activity : allActivitiesInProc) {
            if (GlobalSignalUtil.isGlobalSignalEvent(activity)) {
                GlobalSignal referencedGlobalSignal =
                        GlobalSignalUtil.getReferencedGlobalSignal(activity);

                if (referencedGlobalSignal != null) {
                    IProject gsdProject =
                            WorkingCopyUtil
                                    .getProjectFor(referencedGlobalSignal);
                    if (gsdProject != null && gsdProject.isAccessible()) {
                        allProjectsToReference.add(gsdProject);
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove#getAllProjectsThatShouldRefTargetProject(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param processToMove
     * @param destinationResource
     * @return
     */
    @Override
    protected Set<IProject> getAllProjectsThatShouldRefTargetProject(
            Process processToMove, IResource destinationResource) {
        /*
         * No such scenario can occur.
         */
        return Collections.EMPTY_SET;
    }

}
