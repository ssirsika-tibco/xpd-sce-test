/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.wizards.AddExtrasToNewProcessCommand;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class that provides common functionality for generating service
 * process or business service from an activity (start type none or incoming
 * request activity) in a business process
 * 
 * @author bharge
 * @since 24 Feb 2015
 */
public abstract class AbstractActivityProcessGeneratorAction implements
        IObjectActionDelegate {

    private Activity generatorActivity;

    /**
     * @return the activity from which the process is being generated
     */
    public Activity getGeneratorActivity() {

        return generatorActivity;
    }

    /**
     * Set the activity on which the process is being generated
     * 
     * @param activity
     *            the activity to set
     */
    public void setGeneratorActivity(Activity activity) {

        this.generatorActivity = activity;
    }

    private Process newProcess;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    @Override
    public void run(IAction action) {

        if (null != generatorActivity) {

            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(generatorActivity);

            Package xpdlPackage = Xpdl2ModelUtil.getPackage(generatorActivity);
            Xpdl2WorkingCopyImpl xpdl2WC =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(xpdlPackage);

            CompoundCommand createProcessCommand =
                    new CompoundCommand(getCommandLabel());

            newProcess = generateNewProcess(createProcessCommand, xpdl2WC);

            if (null != createProcessCommand
                    && createProcessCommand.canExecute()) {

                editingDomain.getCommandStack().execute(createProcessCommand);
                try {
                    xpdl2WC.save();

                    /*
                     * Sid XPD-7697 - Moved selectAndReveal from below (used to
                     * be called whether or not command was exec'd
                     */
                    selectAndReveal(newProcess);

                } catch (IOException e) {

                    LOG.error(e);
                }
            }

            if (null != xpdl2WC
                    && xpdl2WC
                            .getAttributes()
                            .containsKey(AddExtrasToNewProcessCommand.TEMPLATEDATA)) {

                xpdl2WC.getAttributes()
                        .remove(AddExtrasToNewProcessCommand.TEMPLATEDATA);
            }
        }
    }

    /**
     * Generate the new business service/service process for start event type
     * none or incoming request activity to invoke the business process from
     * where this process gets generated
     * 
     * @param createProcessCommand
     * @param xpdl2WC
     */
    protected abstract Process generateNewProcess(
            CompoundCommand createProcessCommand, Xpdl2WorkingCopyImpl xpdl2WC);

    /**
     * Show the new generated process in the editor
     * 
     * @param generatedNewProcess
     */
    private void selectAndReveal(Process generatedNewProcess) {

        IConfigurationElement facConfig =
                XpdResourcesUIActivator
                        .getEditorFactoryConfigFor(generatedNewProcess);

        if (facConfig != null) {

            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            try {

                EditorInputFactory f =
                        (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$
                IEditorInput input = f.getEditorInputFor(generatedNewProcess);
                page.openEditor(input, editorID);

                /** Select the new process in the Project Explorer */
                if (generatedNewProcess != null) {

                    IWorkbench workbench = PlatformUI.getWorkbench();

                    if (workbench != null) {

                        BasicNewXpdResourceWizard
                                .selectAndReveal(generatedNewProcess,
                                        workbench.getActiveWorkbenchWindow());
                    }
                }

            } catch (PartInitException e) {

                LOG.error(e.getMessage());
            } catch (CoreException e) {

                e.printStackTrace();
            }
        }

    }

    /**
     * 
     * @return String - label for the command
     */
    protected abstract String getCommandLabel();

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param action
     * @param selection
     */
    @Override
    public abstract void selectionChanged(IAction action, ISelection selection);

    /**
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     * 
     * @param action
     * @param targetPart
     */
    @Override
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // do nothing
    }

}
