/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.precompile.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.precompile.DisablePreCompileRunner;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * 
 * Wizard that comes up when the project is selected for disable pre compile.
 * 
 * @author bharge
 * @since 7 Nov 2014
 */
public class DisablePrecompileProjectWizard extends Wizard {

    IProject project;

    public DisablePrecompileProjectWizard(IProject project) {

        this.project = project;
        setNeedsProgressMonitor(true);
        setWindowTitle(Messages.DisablePrecompileProjectAction_DisablePrecompileWizard_title);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {

        addPage(new DisablePrecompileProjectPage(project));
    }

    /**
     * 
     * For the given project, get all the referencing projects that are pre
     * compiled
     * 
     * @param project
     *            - pre compiled project to be disabled for pre compile
     * @return list of referencing pre compiled projects for the given project
     */
    List<IProject> getReferencingPrecompileProjects(IProject project) {

        /* Get the referencing projects */
        Set<IProject> referencingProjects =
                ProjectUtil.getReferencingProjectsHierarchy(project,
                        new HashSet<IProject>());

        List<IProject> referencingPrecompileProjects =
                new ArrayList<IProject>();
        /* Check if the referencing projects are pre compiled */
        for (IProject refProject : referencingProjects) {

            if (ProjectUtil.isPrecompiledProject(refProject)) {

                referencingPrecompileProjects.add(refProject);
            }
        }
        return referencingPrecompileProjects;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {

        if (null != project) {

            List<IProject> referencingPrecompileProjects =
                    getReferencingPrecompileProjects(project);

            try {

                Set<IProject> allProjects = new HashSet<IProject>();
                allProjects.addAll(referencingPrecompileProjects);
                allProjects.add(project);

                /*
                 * Get the dependency graph so that disable is executed in the
                 * right order. If A depends on B, B depends on C, C depends on
                 * D and disable pre compile is called on D, then disable should
                 * be executed in the order D, C, B, A
                 */
                final List<IProject> sortedProjects =
                        ProjectUtil.getDependencySortedProjects(allProjects);

                DisablePreCompileRunner disablePrecompileRunner =
                        new DisablePreCompileRunner(sortedProjects);

                getContainer().run(true, true, disablePrecompileRunner);

            } catch (CoreException e) {

                XpdResourcesPlugin.getDefault().getLogger()
                        .error(e.getMessage());
            } catch (InvocationTargetException e1) {

                XpdResourcesPlugin.getDefault().getLogger()
                        .error(e1.getMessage());
            } catch (InterruptedException e1) {

                final String msg =
                        "It is recommended that a clean build of the workspace is performed as compiled artifacts may be in an inconsistent state.";
                if (null != e1.getMessage()) {

                    XpdResourcesPlugin.getDefault().getLogger()
                            .error(e1.getMessage());
                } else {

                    XpdResourcesPlugin.getDefault().getLogger().error(msg);
                }
                if (!XpdResourcesPlugin.isInHeadlessMode()) {

                    Display.getDefault().asyncExec(new Runnable() {

                        @Override
                        public void run() {

                            Shell shell =
                                    PlatformUI.getWorkbench()
                                            .getActiveWorkbenchWindow()
                                            .getShell();

                            MessageDialog.openWarning(shell,
                                    "User Cancelled",
                                    msg);
                        }
                    });
                }
            }
        }
        return true;
    }

    /**
     * Precompile page for a XPD project that determines the wizard to be
     * complete if the project has no error markers or has no unsaved changes
     * 
     * 
     * @author bharge
     * @since 27 Oct 2014
     */
    private class DisablePrecompileProjectPage extends WizardPage {

        IProject project;

        Button refProjDisablePreCompileBtn;

        /**
         * @param project
         */
        protected DisablePrecompileProjectPage(IProject project) {

            super(project.getName());
            this.project = project;
            setTitle(Messages.DisablePrecompileProjectAction_DisablePrecompileWizard_title);
            setDescription(Messages.DisablePrecompileProjectAction_DisablePrecompileWizard_desc);
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        public void createControl(Composite parent) {

            Composite root = new Composite(parent, SWT.NONE);
            root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            root.setLayout(new GridLayout());

            Label lbl = new Label(root, SWT.WRAP);
            lbl.setText(String
                    .format(Messages.DisablePreCompileProjectAction_DisablePrecompileProjectPage_disable_selectedProjectPreCompile_label,
                            project.getName()));

            /* Get the referencing projects */
            List<IProject> referencingProjects =
                    getReferencingPrecompileProjects(project);
            if (referencingProjects.size() > 0) {

                Label referencingProjectsLbl = new Label(root, SWT.WRAP);
                referencingProjectsLbl
                        .setText(Messages.DisablePreCompileProjectAction_DisablePrecompileProjectPage_disable_preCompile_label);
                Label refProjNamesLbl = new Label(root, SWT.NONE);
                StringBuffer sb = new StringBuffer("\t"); //$NON-NLS-1$
                for (IProject refProject : referencingProjects) {

                    if (ProjectUtil.isPrecompiledProject(refProject)) {

                        sb.append(refProject.getName()).append(","); //$NON-NLS-1$
                    }
                }
                String commaSeparatedRefProjectNames =
                        sb.substring(0, sb.lastIndexOf(",")); //$NON-NLS-1$
                refProjNamesLbl.setText(commaSeparatedRefProjectNames);

                refProjDisablePreCompileBtn = new Button(root, SWT.CHECK);
                refProjDisablePreCompileBtn
                        .setText(Messages.DisablePreCompileProjectAction_DisablePrecompileProjectPage_configureReferencingProjectsToDisablePreCompile);
                refProjDisablePreCompileBtn.setEnabled(true);
                refProjDisablePreCompileBtn
                        .addSelectionListener(new SelectionListener() {

                            @Override
                            public void widgetSelected(SelectionEvent e) {

                                doButtonSelected();
                            }

                            @Override
                            public void widgetDefaultSelected(SelectionEvent e) {

                                doButtonSelected();
                            }

                        });

                setPageComplete(false);
            } else {

                setPageComplete(true);
            }

            setControl(root);
        }

        /**
         * Sets page complete to true/false (enables/disables finish button)
         * based on the check box selection
         */
        private void doButtonSelected() {

            boolean selected = refProjDisablePreCompileBtn.getSelection();
            if (selected) {

                setPageComplete(true);
            } else {

                setPageComplete(false);
            }
        }
    }

}
