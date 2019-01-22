/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.precompile.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
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

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.precompile.EnablePreCompileRunner;
import com.tibco.xpd.resources.precompile.PreCompileUtil;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Wizard that marks the project as pre-compiled project.
 * 
 * @author bharge
 * @since 20 May 2015
 */
public class EnablePrecompileProjectWizard extends Wizard {

    Logger logger = XpdResourcesUIActivator.getDefault().getLogger();

    IProject project;

    public EnablePrecompileProjectWizard(IProject project) {

        this.project = project;
        setNeedsProgressMonitor(true);
        setWindowTitle(Messages.EnablePrecompileProjectAction_EnablePrecompileWizard_title);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {

        addPage(new EnablePrecompileProjectPage(project));
    }

    /**
     * For the given project, get all the referenced projects that needs to be
     * pre-compiled
     * 
     * @param project
     *            - project to be enabled for pre-compile
     * @return list of referenced projects to be enabled for pre-compile
     * 
     */
    private List<IProject> getReferencedProjectsToPrecompile(IProject project) {

        /* Get the referenced projects */
        Set<IProject> referencedProjects =
                ProjectUtil.getReferencedProjectsHierarchy(project,
                        new HashSet<IProject>());

        List<IProject> refProjectsNotPrecompiled = new ArrayList<IProject>();
        /* Check if the referenced projects are xpd nature and are pre-compiled */
        for (IProject refProject : referencedProjects) {

            if (!ProjectUtil.isPrecompiledProject(refProject)
                    && ProjectUtil.isStudioProject(refProject)) {

                /*
                 * Check if the referenced projects has source artifacts that
                 * are pre-compilable
                 */
                List<IResource> resourcesEnabledForPrecompile =
                        new ArrayList<>();
                PreCompileUtil.getEnabledSourceArtifacts(refProject,
                        resourcesEnabledForPrecompile);
                if (!resourcesEnabledForPrecompile.isEmpty()) {

                    refProjectsNotPrecompiled.add(refProject);
                }
            }
        }
        return refProjectsNotPrecompiled;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {

        if (null != project) {

            List<IProject> refProjectsNotPrecompiled =
                    getReferencedProjectsToPrecompile(project);

            /*
             * sort projects into reverse dependency order (build lowest level
             * projects first (if A references B then build B first).
             */
            Set<IProject> allProjects =
                    new HashSet<IProject>(refProjectsNotPrecompiled);
            allProjects.add(project);
            try {

                final List<IProject> sortedProjects =
                        ProjectUtil.getDependencySortedProjects(allProjects);

                EnablePreCompileRunner enablePreCompileRunner =
                        new EnablePreCompileRunner(sortedProjects);

                getContainer().run(true, true, enablePreCompileRunner);

            } catch (InvocationTargetException e) {

                logger.error(e.getMessage());
            } catch (InterruptedException e) {

                logger.error(e.getMessage());
            } catch (final CoreException e) {

                logger.error(e.getMessage());
                if (!XpdResourcesPlugin.isInHeadlessMode()) {

                    Display.getDefault().asyncExec(new Runnable() {

                        @Override
                        public void run() {

                            Shell shell =
                                    PlatformUI.getWorkbench()
                                            .getActiveWorkbenchWindow()
                                            .getShell();
                            MessageDialog.openWarning(shell,
                                    Messages.EnablePrecompileProjectAction_EnablePrecompileWizard_title,
                                    e.getMessage());
                        }
                    });
                }
            }
        }

        return true;
    }

    /**
     * Pre-compile page for a XPD project that determines the wizard to be
     * complete if the project has no error markers or has no unsaved changes
     * 
     * @author bharge
     * @since 27 Oct 2014
     */
    private class EnablePrecompileProjectPage extends WizardPage {

        IProject project;

        Button refProjPreCompileBtn;

        /**
         * @param project
         */
        protected EnablePrecompileProjectPage(IProject project) {

            super(project.getName());
            this.project = project;
            setTitle(Messages.EnablePrecompileProjectAction_EnablePrecompileWizard_title);
            setDescription(Messages.EnablePrecompileProjectAction_EnablePrecompileWizard_desc);
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

            List<IProject> refProjectsNotPrecompiled =
                    getReferencedProjectsToPrecompile(project);

            Label lbl = new Label(root, SWT.WRAP);

            /*
             * Check if the current project or any of its referenced projects
             * are having error markers or are dirty
             */
            boolean isProjectValid = isProjectValid();

            if (isProjectValid) {

                boolean cannotPreCompile = false;

                /*
                 * Selected Project is valid. Now check if the selected project
                 * has assets that is not pre-compilable.
                 */
                List<IResource> resourcesEnabledForPrecompile =
                        new ArrayList<>();
                PreCompileUtil.getEnabledSourceArtifacts(project,
                        resourcesEnabledForPrecompile);
                if (resourcesEnabledForPrecompile.isEmpty()) {

                    cannotPreCompile = true;
                }

                if (cannotPreCompile) {

                    /* Raise an error on the page */
                    setErrorMessage(String
                            .format(Messages.EnablePreCompileProjectAction_EnablePreCompileProjectPage_RefProjectCannotBePrecompiled_error_message_label,
                                    project.getName()));
                    lbl.setText(Messages.EnablePreCompileProjectAction_EnablePreCompileProjectPage_SelectedProjectCannotBePrecompiled_error_message_label);
                    setPageComplete(false);

                } else {

                    /*
                     * Everything is fine. Show the message that selected
                     * project is being marked for pre-compile
                     */
                    lbl.setText(String
                            .format(Messages.EnablePreCompileProjectAction_EnablePrecompileProjectPage_enable_selectedProjectPreCompile_label,
                                    project.getName()));
                    String commaSeparatedProjectNames =
                            getProjectNamesForPrecompile(refProjectsNotPrecompiled);
                    /*
                     * If there are projects being referenced from the selected
                     * project, then a different message with the list of
                     * referenced projects is shown
                     */
                    if (commaSeparatedProjectNames.length() > 0) {

                        Label lbl2 = new Label(root, SWT.WRAP);
                        lbl2.setText(Messages.EnablePreCompileProjectAction_EnablePrecompileProjectPage_enable_RefProjectsPreCompile_label);

                        Label refProjNamesLbl = new Label(root, SWT.WRAP);
                        refProjNamesLbl.setText(commaSeparatedProjectNames);

                        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                        gd.widthHint = 160;
                        gd.horizontalIndent = 30;
                        refProjNamesLbl.setLayoutData(gd);

                        refProjPreCompileBtn = new Button(root, SWT.CHECK);
                        refProjPreCompileBtn
                                .setText(Messages.EnablePreCompileProjectAction_EnablePrecompileProjectPage_configureRefProjectsToPreCompile);
                        refProjPreCompileBtn.setEnabled(true);

                        gd = new GridData();
                        gd.horizontalIndent = 34;
                        gd.verticalIndent = 20;
                        refProjPreCompileBtn.setLayoutData(gd);

                        refProjPreCompileBtn
                                .addSelectionListener(new SelectionListener() {

                                    @Override
                                    public void widgetSelected(SelectionEvent e) {

                                        doButtonSelected();
                                    }

                                    @Override
                                    public void widgetDefaultSelected(
                                            SelectionEvent e) {

                                        doButtonSelected();
                                    }

                                });

                        setPageComplete(false);
                    } else {

                        setPageComplete(true);
                    }
                    Label disallowNewResourcesTxt = new Label(root, SWT.NONE);
                    disallowNewResourcesTxt
                            .setText(Messages.EnablePreCompileProjectAction_EnablePrecompileProjectPage_disallowNewResources_message);
                }
            } else {

                setErrorMessage(Messages.EnablePreCompileProjectAction_EnablePreCompileProjectPage_error_message);
                lbl.setText(Messages.EnablePreCompileProjectAction_EnablePreCompileProjectPage_error_message_label);

                StringBuffer sb = new StringBuffer();

                String commaSeparatedProjectNames =
                        getProjectNamesForPrecompile(refProjectsNotPrecompiled);
                if (commaSeparatedProjectNames.length() > 0) {

                    sb.append(commaSeparatedProjectNames);
                    sb.append(","); //$NON-NLS-1$
                } else {

                    sb.append("\t"); //$NON-NLS-1$
                }
                sb.append(project.getName());

                Label refProjNamesLbl = new Label(root, SWT.WRAP);
                refProjNamesLbl.setText(sb.toString());

                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                gd.widthHint = 160;
                gd.horizontalIndent = 30;
                refProjNamesLbl.setLayoutData(gd);

                setPageComplete(false);
            }

            setControl(root);
        }

        /**
         * @param refProjectsNotPrecompiled
         * @return comma separated project names for pre-compile
         */
        private String getProjectNamesForPrecompile(
                List<IProject> refProjectsNotPrecompiled) {

            StringBuffer sb = new StringBuffer();
            String commaSeparatedProjectNames = ""; //$NON-NLS-1$

            if (refProjectsNotPrecompiled.size() > 0) {

                for (IProject refProject : refProjectsNotPrecompiled) {

                    sb.append(refProject.getName()).append(", "); //$NON-NLS-1$
                }
                if (sb.length() > 1) {

                    commaSeparatedProjectNames =
                            sb.substring(0, sb.lastIndexOf(",")); //$NON-NLS-1$
                }
            }
            return commaSeparatedProjectNames;
        }

        /**
         * Sets page complete to true/false (enables/disables finish button)
         * based on the check box selection
         */
        private void doButtonSelected() {

            boolean selected = refProjPreCompileBtn.getSelection();
            if (selected) {

                setPageComplete(true);
            } else {

                setPageComplete(false);
            }
        }

        /**
         * Check if the project or referenced projects has error markers or is
         * dirty
         * 
         * @return <code>true</code> if the project (or it referenced projects)
         *         has no error markers or are not dirty
         */
        private boolean isProjectValid() {

            try {

                /* check if the project has any error markers */
                IMarker[] markers =
                        project.findMarkers(null,
                                true,
                                IResource.DEPTH_INFINITE);
                for (IMarker iMarker : markers) {

                    int severity =
                            iMarker.getAttribute(IMarker.SEVERITY,
                                    IMarker.SEVERITY_WARNING);
                    if (IMarker.SEVERITY_ERROR == severity) {

                        /*
                         * If a project has error markers then we do not allow
                         * the wizard to finish. But this particular problem
                         * marker
                         * 
                         * "This project is referenced from pre-compiled project(s) and must itself be configured to pre-compile."
                         * 
                         * must be ignored otherwise we cannot mark this project
                         * for enable pre-compile.
                         * 
                         * A key on the marker is added when problem marker is
                         * created in the ProjectValidator class. If we find
                         * that key defined with some value then ignore this
                         * validation problem
                         */
                        Object attribute =
                                iMarker.getAttribute(ProjectUtil.REFERRED_PROJECT_IS_NOT_PRECOMPILED_ISSUE_KEY);
                        if (null != attribute) {

                            continue;
                        }
                        return false;
                    }
                }

                /* check if any of the referenced projects has error markers */
                Set<IProject> referencedProjects =
                        ProjectUtil.getReferencedProjectsHierarchy(project,
                                new HashSet<IProject>());
                for (IProject refProject : referencedProjects) {

                    markers =
                            refProject.findMarkers(null,
                                    true,
                                    IResource.DEPTH_INFINITE);
                    for (IMarker iMarker : markers) {

                        int severity =
                                iMarker.getAttribute(IMarker.SEVERITY,
                                        IMarker.SEVERITY_WARNING);
                        if (IMarker.SEVERITY_ERROR == severity) {

                            return false;
                        }
                    }

                }

                /* check if the project is dirty */
                final Set<IResource> resources = new HashSet<IResource>();
                IResourceVisitor visitor = new IResourceVisitor() {

                    @Override
                    public boolean visit(IResource resource)
                            throws CoreException {

                        if (resource instanceof IFile) {

                            WorkingCopy workingCopy =
                                    WorkingCopyUtil.getWorkingCopy(resource);

                            if (null != workingCopy
                                    && workingCopy.isWorkingCopyDirty()) {

                                resources.add(resource);
                                return false;
                            }
                        }
                        return true;
                    }
                };

                project.accept(visitor);
                if (resources.size() > 0) {

                    return false;
                }

                /* check if any of the referenced projects is dirty */
                for (IProject refProject : referencedProjects) {

                    refProject.accept(visitor);
                    if (resources.size() > 0) {

                        return false;
                    }
                }

            } catch (CoreException e) {

            }
            return true;
        }
    }
}
