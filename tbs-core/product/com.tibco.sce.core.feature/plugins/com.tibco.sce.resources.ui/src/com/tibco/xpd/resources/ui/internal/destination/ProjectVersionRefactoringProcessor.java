/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.destination;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ParticipantExtensionPoint;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactoringArguments;
import com.tibco.xpd.resources.refactoring.ProjectVersionRefactoringParticipant;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Processor for the project version refactoring. This will delegate refactoring
 * to all contributors (participants) to the
 * <code>com.tibco.xpd.resources.projectVersionRefactoringParticipant</code>
 * extension point.
 * 
 * @author njpatel
 * 
 */
public class ProjectVersionRefactoringProcessor extends RefactoringProcessor {

    private static final String PARTICIPANT_EXT_ID = "projectVersionRefactoringParticipant"; //$NON-NLS-1$
    private final IProject project;
    private final ProjectDetails details;

    /**
     * Processor for the project version refactoring.
     * 
     * @param project
     *            project being refactored
     * @param details
     *            the new project details
     */
    public ProjectVersionRefactoringProcessor(IProject project,
            ProjectDetails details) {
        this.project = project;
        this.details = details;
    }

    @Override
    public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws CoreException,
            OperationCanceledException {
        RefactoringStatus status = new RefactoringStatus();

        if (project == null) {
            status
                    .merge(RefactoringStatus
                            .createErrorStatus(Messages.ProjectVersionRefactoringProcessor_noProjectToRefactor_error_shortdesc));
        } else if (!project.isAccessible()) {
            status
                    .merge(RefactoringStatus
                            .createErrorStatus(String
                                    .format(
                                            Messages.ProjectVersionRefactoringProcessor_InaccessibleProject_error_shortdesc,
                                            project.getName())));
        }

        if (details == null) {
            status
                    .merge(RefactoringStatus
                            .createErrorStatus(Messages.ProjectVersionRefactoringProcessor_noProjectDetailsAvailable_error_shortdesc));
        }

        return status;
    }

    @Override
    public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
            throws CoreException, OperationCanceledException {

        return new RefactoringStatus();
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        return null;
    }

    @Override
    public Object[] getElements() {
        return new Object[] { details };
    }

    @Override
    public String getIdentifier() {
        return "com.tibco.xpd.resources.projectVersionRefactoring"; //$NON-NLS-1$
    }

    @Override
    public String getProcessorName() {
        return Messages.ProjectVersionRefactoringProcessor_ProjectVersionRefactoring_label;
    }

    @Override
    public boolean isApplicable() throws CoreException {
        return details != null;
    }

    @Override
    public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
            SharableParticipants sharedParticipants) throws CoreException {

        /*
         * Get all project details refactor participants
         */
        ParticipantExtensionPoint extPoint = new ParticipantExtensionPoint(
                XpdResourcesPlugin.ID_PLUGIN, PARTICIPANT_EXT_ID,
                ProjectVersionRefactoringParticipant.class);

        String[] destinations = null;

        // Get all selected global destinations
        if (details.getGlobalDestinations() != null
                && details.getGlobalDestinations().getDestination() != null) {
            List<String> globalDestinations = GlobalDestinationUtil
                    .getGlobalDestinations();
            List<String> availableDestinations = new ArrayList<String>();
            for (Destination dest : details.getGlobalDestinations()
                    .getDestination()) {
                if (dest.getType() != null
                        && globalDestinations.contains(dest.getType())) {
                    availableDestinations.add(dest.getType());
                }
            }
            destinations = availableDestinations
                    .toArray(new String[availableDestinations.size()]);
        }

        return extPoint.getParticipants(status, this, project,
                new ProjectVersionRefactoringArguments(project,
                        details.getId(), details.getVersion(), details
                                .getStatus(), destinations), null,
                new String[] { XpdConsts.PROJECT_NATURE_ID },
                sharedParticipants);
    }

}