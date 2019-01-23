/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.moveprocess.participant;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Participant that adds necessary project references if the process being moved
 * has dependencies on REST methods from REST Service Projects and post move the
 * REST Project needs to be referenced by the target BPM project.
 * 
 * 
 * @author kthombar
 * @since Aug 24, 2015
 */
public class AddRequiredProjectReferencesDueToRestDependencyParticipant extends
        AbstractAddProjectReferencesForProcessMove {

    /**
     * 
     * @param refactoredProcess
     * @return {@link Set} of REST Service Projects that the destination project
     *         should reference because the process being moved references REST
     *         method from those projects, empty Set if there are no projects to
     *         reference.
     */
    @Override
    protected Set<IProject> getAllProjectsThatTargetProjectMustReference(
            Process refactoredProcess, IResource destinationResource) {

        Set<IProject> allProjectsToReference = new HashSet<IProject>();

        /* add other project references. */
        addProjectReferencesDueToRestdependency(refactoredProcess,
                destinationResource,
                allProjectsToReference);

        return allProjectsToReference;
    }

    /**
     * 
     * @param sourceProcess
     *            the process being moved
     * @param destinationResource
     *            the destination resource to which the project is being moved
     * @param allProjectsToReference
     *            Set which should be populated with all the projects that the
     *            target project should reference.
     */
    private void addProjectReferencesDueToRestdependency(Process sourceProcess,
            IResource destinationResource, Set<IProject> allProjectsToReference) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(sourceProcess);

        for (Activity activity : allActivitiesInProc) {

            if (RestServiceTaskUtil.isRESTServiceActivity(activity)) {

                Method restMethod = RestServiceTaskUtil.getRESTMethod(activity);

                if (restMethod != null) {
                    IProject restServiceProject =
                            WorkingCopyUtil.getProjectFor(restMethod);

                    if (restServiceProject != null
                            && restServiceProject.isAccessible()) {
                        allProjectsToReference.add(restServiceProject);
                    }
                }
            }
        }
    }

    /**
     * 
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove#getAllProjectsThatShouldRefTargetProject(com.tibco.xpd.xpdl2.Process)
     * 
     * @param refactoredProcess
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Set<IProject> getAllProjectsThatShouldRefTargetProject(
            Process refactoredProcess, IResource destinationResource) {
        /*
         * No such scenario can occur.
         */
        return Collections.EMPTY_SET;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove#getChangeName()
     * 
     * @return
     */
    @Override
    protected String getChangeName() {
        return Messages.AddRequiredProjectReferencesDueToRestDependencyParticipant_AddProjReferenceDueToRestDependencyChange_msg;
    }
}
