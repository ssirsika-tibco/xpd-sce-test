/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.precompile.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Action that is responsible to call the {@link EnablePrecompileProjectWizard}
 * that marks the project as a pre-compiled project.
 * 
 * @author bharge
 * @since 29 May 2015
 */
public class EnablePreCompileProjectAction extends BaseSelectionListenerAction {

    IProject project;

    /**
     * @param text
     */
    protected EnablePreCompileProjectAction(String text) {

        super(text);
    }

    /**
     * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param selection
     * @return
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {

        if (selection instanceof StructuredSelection) {

            Object firstElement =
                    ((StructuredSelection) selection).getFirstElement();
            if (firstElement instanceof IProject) {

                project = (IProject) firstElement;

                /*
                 * If the project is already a pre-compile project, then disable
                 * the menu option
                 */
                boolean isPreCompiled =
                        ProjectUtil.isPrecompiledProject(project);
                if (isPreCompiled) {

                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {

        if (null != project) {

            Shell shell =
                    XpdResourcesPlugin.getStandardDisplay().getActiveShell();
            /* Show the enable pre-compile project wizard */
            WizardDialog dialog =
                    new WizardDialog(shell, new EnablePrecompileProjectWizard(
                            project));
            dialog.open();
        }
    }
}
