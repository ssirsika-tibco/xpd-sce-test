/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.util.SetProjectReferenceCommand;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Quick-fix resolution to set the reference to the Java project being used in
 * the POJO step.
 * 
 * @author njpatel
 * 
 */
public class SetReferencedProjectResolution extends
        AbstractWorkingCopyResolution {

    /**
     * Key for marker additional info for project name to reference.
     * 
     * All issues which wish to use this quick fix must specify this additional
     * info.
     */
    public static final String PROJECTNAME_ADDITIONAL_INFO = "project"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Properties prop = ValidationUtil.getAdditionalInfo(marker);
        if (prop != null) {
            Object value = prop.get(PROJECTNAME_ADDITIONAL_INFO);
            if (value instanceof String) {
                IProject refProject =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject((String) value);

                IResource resource = marker.getResource();

                if (resource != null && refProject != null) {
                    IProject project = resource.getProject();

                    if (project != null && refProject != null) {
                        return new SetProjectReferenceCommand(project,
                                refProject);
                    }
                }
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionLabel(java.lang.String, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        return String.format(propertiesLabel, getProjectRefName(marker));
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionDescription(java.lang.String,
     * org.eclipse.core.resources.IMarker)
     */
    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return String.format(propertiesDescription, getProjectRefName(marker));
    }

    private String getProjectRefName(IMarker marker) {
        String projectName = null;
        Properties prop = ValidationUtil.getAdditionalInfo(marker);
        if (prop != null) {
            Object value = prop.get(PROJECTNAME_ADDITIONAL_INFO);
            if (value instanceof String) {
                projectName = (String) value;
            }
        }
        return (projectName != null && projectName.length() > 0) ? projectName
                : "???"; //$NON-NLS-1$
    }
}
