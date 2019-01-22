/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.pde.internal.ui.wizards.tools.ConvertProjectToPluginOperation;

import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Quick-fix resolution to convert a Java project into a Plug-in project.
 * 
 * @author njpatel
 * 
 */
public class ConvertToPlugInProjectResolution implements IResolution {

    private static final String PDE_NATURE = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$

    @SuppressWarnings("restriction")
    public void run(IMarker marker) throws ResolutionException {
        Properties prop = ValidationUtil.getAdditionalInfo(marker);
        if (prop != null) {
            Object value = prop.get("project"); //$NON-NLS-1$
            if (value instanceof String) {
                IProject project =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject((String) value);
                try {
                    if (project != null && project.exists()
                            && !project.hasNature(PDE_NATURE)) {
                        /*
                         * Forced to used internal code as there is no public
                         * API to do this at the moment
                         */
                        ConvertProjectToPluginOperation op =
                                new ConvertProjectToPluginOperation(
                                        new IProject[] { project }, false);
                        op.run(new NullProgressMonitor());

                        // Force the re-build of this resource so the error
                        // marker can be removed
                        IResource resource = marker.getResource();
                        if (resource != null && resource.exists()) {
                            resource.touch(null);
                        }

                    }
                } catch (CoreException e) {
                    throw new ResolutionException(
                            String
                                    .format(Messages.ConvertToPlugInProjectResolution_unableToConvertToPluginProject_error_message,
                                            project.getName()), e);
                } catch (InvocationTargetException e) {
                    throw new ResolutionException(
                            String
                                    .format(Messages.ConvertToPlugInProjectResolution_unableToConvertToPluginProject_error_message,
                                            project.getName()), e);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        }
    }
}
