/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation.resolution;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.validation.ProjectLifecycleValidator;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution for the duplicate project id issue. This resolution will
 * allow user to change the id of the project the problem is reported on.
 * 
 * @author njpatel
 * 
 */
public class ChangeProjectIdResolution extends AbstractWorkingCopyResolution {

    private IProject project;

    private final Pattern idPattern = Pattern
            .compile("(^[a-zA-Z])|(^[a-zA-Z][\\w\\._-]*[\\w&&[^._-]])"); //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#run
     * (org.eclipse.core.resources.IMarker)
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {
        if (project != null) {
            final Set<String> ids = getIds();
            String id = null;
            try {
                Object attr =
                        marker.getAttribute(ProjectLifecycleValidator.ATT_PROJECT_ID);
                if (attr instanceof String) {
                    id = (String) attr;
                }
            } catch (CoreException e) {
                // Do nothing
            }
            InputDialog dlg =
                    new InputDialog(
                            XpdResourcesPlugin.getStandardDisplay()
                                    .getActiveShell(),
                            Messages.ChangeProjectIdResolution_changeLifecycleId_dialog_title,
                            String.format(Messages.ChangeProjectIdResolution_changeLifecycleId_dialog_longdesc,
                                    project.getName()), id,
                            new IInputValidator() {

                                public String isValid(String newText) {
                                    String msg = null;
                                    if (newText.length() > 0) {
                                        /*
                                         * SCF-137 - Kapil: The special
                                         * character '-' has been added to
                                         * Project-id as it is supported by
                                         * runtime. So now the Project-id can be
                                         * something like :
                                         * com.example.project-myproj
                                         */
                                        Matcher matcher =
                                                idPattern.matcher(newText);
                                        if (ids.contains(newText)) {
                                            msg =
                                                    Messages.ChangeProjectIdResolution_idAlreadyUsed_error_shortdesc;
                                        } else if (!matcher.matches()) {
                                            msg =
                                                    Messages.ChangeProjectIdResolution_idFormatIncorrect_error_shortdesc;
                                        }
                                    } else {
                                        msg =
                                                Messages.ChangeProjectIdResolution_idCannotBeBlank_error_shortdesc;
                                    }
                                    return msg;
                                }

                            });
            if (dlg.open() == InputDialog.OK) {
                setProjectId(project, dlg.getValue());
            }
        }
    }

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        // Have overridden run method so this method will not get called
        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        try {
            IProject project = getProject(getProjectLocation(marker));
            setProject(project);
            if (project != null) {
                return String.format(propertiesLabel, project.getName());
            }
        } catch (CoreException e) {
            // Do nothing
        }
        return Messages.ChangeProjectIdResolution_changeProjectId_resolution_label;
    }

    /**
     * Set the project that is being corrected.
     * 
     * @param project
     */
    protected void setProject(IProject project) {
        this.project = project;
    }

    /**
     * Get the location of the project in interest from the marker.
     * 
     * @param marker
     * @return
     * @throws CoreException
     */
    protected String getProjectLocation(IMarker marker) throws CoreException {
        Object attr = marker.getAttribute(IMarker.LOCATION);
        return attr instanceof String ? (String) attr : null;
    }

    /**
     * Get the project at the given location
     * 
     * @param location
     * @return
     * @throws CoreException
     */
    protected IProject getProject(String location) throws CoreException {
        IProject project = null;

        if (location != null) {
            IResource res =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .findMember(new Path(location));
            if (res instanceof IProject) {
                project = (IProject) res;
            }
        }

        return project;
    }

    /**
     * Get all the lifecycle ids currently in use.
     * 
     * @return
     */
    private Set<String> getIds() {
        Set<String> ids = new HashSet<String>();
        XpdResourcesPlugin plugin = XpdResourcesPlugin.getDefault();
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
                .getProjects()) {
            try {
                if (project.isAccessible()
                        && project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    ProjectConfig config = plugin.getProjectConfig(project);
                    if (config != null && config.getProjectDetails() != null) {
                        String id = config.getProjectDetails().getId();
                        if (id != null && id.length() > 0) {
                            ids.add(id);
                        }
                    }
                }
            } catch (CoreException e) {
                ValidationActivator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format(Messages.ChangeProjectIdResolution_natureCheckFail_error_message,
                                        project.getName()));
            }
        }
        return ids;
    }

    /**
     * Set the project's id.
     * 
     * @param project
     * @param value
     */
    private void setProjectId(IProject project, String value) {
        if (project != null && value != null) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null && config.getProjectDetails() != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(config);
                if (wc != null) {
                    config.getProjectDetails().setId(value);
                    try {
                        wc.save();
                    } catch (IOException e) {
                        ValidationActivator
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format(Messages.ChangeProjectIdResolution_saveProjectFail_error_message,
                                                project.getName()));
                    }
                }
            }
        }
    }
}
