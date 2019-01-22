package com.tibco.xpd.resources.projectconfig.util;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Command to add a given global destination to a given project when executed.
 * 
 * @author aallway
 * @since 3.3 (21 Oct 2009)
 */
public class SetProjectDestinationCommand extends AbstractCommand {
    private final IProject project;

    private final String globalDestinationId;

    private boolean destinationAdded = false;

    private boolean projectDetailsAdded = false;

    public SetProjectDestinationCommand(IProject project, String dest) {
        super();
        this.project = project;
        this.globalDestinationId = dest;

        setLabel(String
                .format(Messages.AddProjectDestinationResolution_AddDestToProject_menu,
                        globalDestinationId));
    }

    @Override
    public boolean canExecute() {
        return project != null && project.exists()
                && globalDestinationId != null
                && globalDestinationId.length() > 0;
    }

    public void execute() {
        addDestinationToProject();
    }

    @Override
    public void undo() {
        removeDestinationFromProject();
    }

    public void redo() {
        addDestinationToProject();
    }

    /**
     * Add the destination to the project.
     */
    private void addDestinationToProject() {
        boolean found = false;

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        ProjectDetails details = config.getProjectDetails();

        if (details == null) {
            details = ProjectConfigFactory.eINSTANCE.createProjectDetails();
            details.setId(ProjectUtil.getDefaultProjectLifecycleId(project
                    .getName()));
            projectDetailsAdded = true;
        }
        Destinations destinationsElem = details.getGlobalDestinations();
        if (destinationsElem != null) {
            for (Destination destination : destinationsElem.getDestination()) {
                if (globalDestinationId.equals(destination.getType())) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {

            ProjectDetails newDetails =
                    (ProjectDetails) EcoreUtil.copy(details);

            Destination newDestination =
                    ProjectConfigFactory.eINSTANCE.createDestination();

            newDestination.setType(globalDestinationId);

            if (newDetails.getGlobalDestinations() == null) {
                newDetails.setGlobalDestinations(ProjectConfigFactory.eINSTANCE
                        .createDestinations());
            }

            newDetails.getGlobalDestinations().getDestination()
                    .add(newDestination);

            ProjectConfigWorkingCopy wc =
                    (ProjectConfigWorkingCopy) WorkingCopyUtil
                            .getWorkingCopyFor(config);

            try {
                wc.setDetails(newDetails);
                destinationAdded = true;

            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to set new project config details.", e); //$NON-NLS-1$
            }
        }

        return;
    }

    /**
     * Remove the destination from project (undo)
     */
    private void removeDestinationFromProject() {
        if (destinationAdded) {
            boolean found = false;

            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            ProjectDetails details = config.getProjectDetails();

            ProjectDetails newDetails =
                    (ProjectDetails) EcoreUtil.copy(details);

            if (projectDetailsAdded) {
                newDetails = null;
                found = true;
            } else {
                Destinations destinationsElem =
                        newDetails.getGlobalDestinations();
                if (destinationsElem != null) {
                    for (Iterator<Destination> iterator =
                            destinationsElem.getDestination().iterator(); iterator
                            .hasNext();) {
                        Destination destination = iterator.next();
                        if (globalDestinationId.equals(destination.getType())) {
                            iterator.remove();

                            found = true;
                        }
                    }
                }
            }

            if (found) {
                ProjectConfigWorkingCopy wc =
                        (ProjectConfigWorkingCopy) WorkingCopyUtil
                                .getWorkingCopyFor(config);

                try {
                    wc.setDetails(newDetails);
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Failed to remove new project config details.", e); //$NON-NLS-1$
                }
            }

            destinationAdded = false;
        }

        return;
    }

}