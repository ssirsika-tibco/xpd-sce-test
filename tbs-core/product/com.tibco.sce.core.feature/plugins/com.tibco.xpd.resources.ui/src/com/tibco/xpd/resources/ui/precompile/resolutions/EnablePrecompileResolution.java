/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.precompile.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.precompile.ui.EnablePrecompileProjectWizard;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution class that enables pre-compile on referenced projects if the user
 * disables pre-compile on any of the referenced projects in the pre-compilation
 * tree
 * 
 * This resolution needs {@link EnablePrecompileProjectWizard} and because
 * validation plug-in cannot depend on this plug-in, this resolution class
 * exists in this plug-in and contribution is from com.tibco.xpd.validation
 * plugin.
 * 
 * @author bharge
 * @since 19 May 2015
 */
public class EnablePrecompileResolution implements IResolution {

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

            final IProject project = (IProject) resource;
            if (project.isAccessible()) {

                final Shell shell =
                        XpdResourcesPlugin.getStandardDisplay()
                                .getActiveShell();

                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        /* Show the enable pre compile project wizard */
                        WizardDialog dialog =
                                new WizardDialog(shell,
                                        new EnablePrecompileProjectWizard(
                                                project));
                        dialog.open();
                    }
                });
            }
        }
    }

}
