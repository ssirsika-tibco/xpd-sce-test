/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

import com.tibco.xpd.branding.internal.Messages;
import com.tibco.xpd.branding.wp.samples.SampleProjects.PostInstallStep;
import com.tibco.xpd.branding.wp.samples.SampleProjects.Project;

public class SampleProjectsIntroAction implements IIntroAction {

    public void run(final IIntroSite site, final Properties params) {
        if (params.getProperty("projectId") == null) { //$NON-NLS-1$
            throw new IllegalArgumentException(
                    Messages.SampleProjectsIntroAction_ProjectIDNotSet_message);
        }
        if (!SampleProjects.INSTANCE
                .containsId(params.getProperty("projectId"))) { //$NON-NLS-1$
            throw new IllegalArgumentException(
                    Messages.SampleProjectsIntroAction_NonExistentProjectIDDefinition_message);
        }

        Runnable r = new Runnable() {
            public void run() {

                Project project =
                        SampleProjects.INSTANCE.getProject(params
                                .getProperty("projectId")); //$NON-NLS-1$

                Shell currentShell =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();
                Context context = new ContextImpl(project);
                InstallSampleWizard wizard = new InstallSampleWizard(context);
                WizardDialog wizardContainer =
                        new WizardDialog(currentShell, wizard);
                int result = wizardContainer.open();
                if (result == WizardDialog.OK) {
                    Collection<PostInstallStep> postInstallSteps =
                            project.getPostInstallSteps();
                    for (PostInstallStep step : postInstallSteps) {
                        step.execute(site);
                    }
                }

            }
        };

        Shell currentShell =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        currentShell.getDisplay().asyncExec(r);
    }

    class ContextImpl implements Context {

        private final Project project;

        private final Map<String, String> attribs;

        public ContextImpl(Project project) {
            this.project = project;
            attribs = new HashMap<String, String>();
        }

        public String get(String key) {
            return attribs.get(key);
        }

        public Project getProject() {
            return project;
        }

        public void put(String key, String value) {
            attribs.put(key, value);
        }

    }

}
