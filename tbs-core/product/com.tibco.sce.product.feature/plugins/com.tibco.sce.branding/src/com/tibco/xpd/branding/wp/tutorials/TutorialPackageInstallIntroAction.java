/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.tutorials;

import java.util.Collection;
import java.util.Properties;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

import com.tibco.xpd.branding.internal.Messages;
import com.tibco.xpd.branding.wp.samples.postinstall.PostInstallStep;

/**
 * 
 * 
 * @author rgreen
 *
 */
public class TutorialPackageInstallIntroAction implements IIntroAction {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.intro.config.IIntroAction#run(org.eclipse.ui.intro.IIntroSite
     * , java.util.Properties)
     */
    public void run(final IIntroSite site, final Properties params) {

        if (params.getProperty("projectId") == null) { //$NON-NLS-1$
            throw new IllegalArgumentException(
                    Messages.TutorialPackageInstallIntroAction_ProjectNameNotSet_message);
        }

        Runnable r = new Runnable() {
            public void run() {

                TutorialPackage tutorialPackage = TutorialPackageManager
                        .getINSTANCE().getPackageById(
                                params.getProperty("projectId")); //$NON-NLS-1$

                Shell currentShell = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell();

                TutorialPackageInstallWizard wizard = new TutorialPackageInstallWizard(
                        tutorialPackage, params.getProperty("target")); //$NON-NLS-1$
                WizardDialog wizardContainer = new WizardDialog(currentShell,
                        wizard);
                int result = wizardContainer.open();
                if (result == WizardDialog.OK) {
                    Collection<PostInstallStep> postInstallSteps = tutorialPackage
                            .getPostInstallSteps();
                    for (PostInstallStep step : postInstallSteps) {
                        step.execute(site);
                    }
                }

            }
        };

        Shell currentShell = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell();
        currentShell.getDisplay().asyncExec(r);
    }

}
