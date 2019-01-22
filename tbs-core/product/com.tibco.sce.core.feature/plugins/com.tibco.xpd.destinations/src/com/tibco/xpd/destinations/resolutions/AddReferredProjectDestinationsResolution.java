/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.destinations.resolutions;

import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.destinations.internal.Messages;
import com.tibco.xpd.destinations.validation.AbstractRequiredDestinationsValidator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.util.SetProjectDestinationCommand;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Resolution to add required global destination of a referenced project to this
 * project.
 * 
 * @author bharge
 * @since 23 Sep 2011
 */
public class AddReferredProjectDestinationsResolution implements IResolution,
        IConfigurableResolutionLabel {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {

        IResource resource = marker.getResource();
        if (resource instanceof IProject) {

            IProject project = (IProject) resource;

            if (null != project) {
                CompoundCommand cCmd = new CompoundCommand();
                String destName = getDestinationName(marker);

                if (destName != null && destName.length() > 0) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(project);
                    EditingDomain editingDomain =
                            WorkingCopyUtil.getEditingDomain(config);

                    Set<IProject> referencedProjects;
                    try {
                        referencedProjects =
                                ProjectUtil2
                                        .getReferencedProjectsHierarchy(project,
                                                true);

                        boolean confirm =
                                getConfirmation(referencedProjects, destName);
                        if (confirm) {
                            for (IProject refProject : referencedProjects) {

                                Command cmd =
                                        new SetProjectDestinationCommand(
                                                refProject, destName);
                                cCmd.append(cmd);
                            }

                            if (cCmd != null && cCmd.canExecute()) {
                                editingDomain.getCommandStack().execute(cCmd);
                            }
                        }
                    } catch (CyclicDependencyException e) {
                    }
                }
            }
        }
    }

    /**
     * @param referencedProjects
     * @param destName
     * @return
     */
    private boolean getConfirmation(Set<IProject> referencedProjects,
            String destName) {

        StringBuffer message = new StringBuffer(destName);
        message.append(" "); //$NON-NLS-1$
        message.append(Messages.AddProjectDestResolution_RequiredDest_confirmation_dialog_message);

        StringBuffer projectNames = new StringBuffer("\n"); //$NON-NLS-1$
        for (IProject project : referencedProjects) {
            projectNames.append("\n\t" + project.getName()); //$NON-NLS-1$
            projectNames.append("\n"); //$NON-NLS-1$
        }

        message.append(projectNames.toString());

        return MessageDialog.openConfirm(Display.getDefault().getActiveShell(),
                Messages.AddProjectDestResolution_RequiredDest_title,
                message.toString());
    }

    /**
     * @param marker
     * @return
     */
    private String getDestinationName(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            return addInfo
                    .getProperty(AbstractRequiredDestinationsValidator.PROJECT_DEST_ADDINFOKEY);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getConfigurableResolutionLabel(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesLabel
     * @param marker
     * @return
     */
    @Override
    public String getConfigurableResolutionLabel(String propertiesLabel,
            IMarker marker) {
        return String.format(propertiesLabel, getDestinationName(marker));
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IConfigurableResolutionLabel#getConfigurableResolutionDescription(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesDescription
     * @param marker
     * @return
     */
    @Override
    public String getConfigurableResolutionDescription(
            String propertiesDescription, IMarker marker) {
        return String.format(propertiesDescription, getDestinationName(marker));
    }

}
