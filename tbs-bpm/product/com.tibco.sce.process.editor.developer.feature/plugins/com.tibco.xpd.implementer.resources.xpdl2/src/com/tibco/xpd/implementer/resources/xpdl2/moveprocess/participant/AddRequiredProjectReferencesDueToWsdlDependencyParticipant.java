/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.moveprocess.participant;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.MoveProcessProcessor;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants.AbstractAddProjectReferencesForProcessMove;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Participant that adds necessary project references if the process being moved
 * has dependencies on wsdls and post move the wsdls are left out in different
 * project (i.e. not target project). Note: the processor for this participant
 * is {@link MoveProcessProcessor}
 * 
 * @author kthombar
 * @since 28-Sep-2014
 */
public class AddRequiredProjectReferencesDueToWsdlDependencyParticipant extends
        AbstractAddProjectReferencesForProcessMove {

    /**
     * 
     * @param refactoredProcess
     * @return {@link Set} of Projects that the destination project should
     *         reference because the process being moved references wsdls from
     *         those projects, empty Set if there are no projects to reference.
     */
    @Override
    protected Set<IProject> getAllProjectsThatTargetProjectMustReference(
            Process refactoredProcess, IResource destinationResource) {

        Set<IProject> allProjectsToReference = new HashSet<IProject>();

        /* add other project references. */
        addProjectReferencesDueToWsdldependency(refactoredProcess,
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
    private void addProjectReferencesDueToWsdldependency(Process sourceProcess,
            IResource destinationResource, Set<IProject> allProjectsToReference) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(sourceProcess);

        Set<IFile> alreadyVisitedWsdlFile = new HashSet<IFile>();

        for (Activity activity : allActivitiesInProc) {

            /*
             * Get the wsdl file referenced by the activity
             */
            IFile wsdlFile = Xpdl2WsdlUtil.getWsdlFile(activity);

            if (wsdlFile != null && !alreadyVisitedWsdlFile.contains(wsdlFile)) {

                alreadyVisitedWsdlFile.add(wsdlFile);

                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopy(wsdlFile);

                if (workingCopy instanceof WsdlWorkingCopy) {

                    WsdlWorkingCopy wsdlWC = (WsdlWorkingCopy) workingCopy;

                    if (!wsdlWC.isWsdlStudioGenerated()) {
                        /*
                         * We are interested only in non-generated wsdl files
                         */
                        IProject wsdlFileParentProject = wsdlFile.getProject();

                        if (wsdlFileParentProject != null
                                && wsdlFileParentProject != destinationResource
                                        .getProject()) {
                            /*
                             * If post refactor the wsdl files and the activity
                             * that referrences it are in differenct project
                             * then add project reference if already not
                             * present.
                             */
                            allProjectsToReference.add(wsdlFileParentProject);
                        }
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
        return Messages.AddRequiredProjectReferencesDueToWsdlDependencyParticipant_AddProjectReferencesDueToWsdlDependency_msg;
    }
}
