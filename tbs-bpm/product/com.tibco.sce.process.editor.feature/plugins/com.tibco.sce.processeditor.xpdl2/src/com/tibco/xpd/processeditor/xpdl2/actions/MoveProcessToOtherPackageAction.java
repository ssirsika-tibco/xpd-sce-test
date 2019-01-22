/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.MoveProcessWizard;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action to Move process to different package.
 * 
 * 
 * @author kthombar
 * @since 14-Sep-2014
 */
@SuppressWarnings("restriction")
public class MoveProcessToOtherPackageAction extends
        BaseSelectionListenerAction {

    private Process selectedProcess;

    /**
     * @param text
     */
    public MoveProcessToOtherPackageAction() {
        super(Messages.MoveProcessToOtherPackageAction_MoveProcessAction_desc);
        setImageDescriptor(Xpdl2ProcessEditorPlugin
                .getImageDescriptor(ProcessEditorConstants.ICON_MOVE_PROCESS));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {

        selectedProcess = null;

        if (selection.size() == 1) {

            @SuppressWarnings("rawtypes")
            Iterator iterator = selection.iterator();
            while (iterator.hasNext()) {

                Object next = iterator.next();

                /*
                 * get all the selected processes.
                 */
                if (next instanceof Process) {

                    selectedProcess = (Process) next;
                    break;
                }
            }
        }
        return selectedProcess != null;
    }

    @Override
    public void run() {
        if (selectedProcess != null) {

            /*
             * If there are any dirty(unsaved) editors then ask the user to save
             * them, if the user does not choose to save then do not proceed.
             */
            if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
                return;
            }

            RefactoringWizard refactoringWizard =
                    new MoveProcessWizard(selectedProcess);
            RefactoringWizardOpenOperation op =
                    new RefactoringWizardOpenOperation(refactoringWizard);

            try {
                int rc =
                        op.run(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.MoveProcessToOtherPackageAction_MoveProcessWizard_title);
                if (rc == RefactoringWizardOpenOperation.INITIAL_CONDITION_CHECKING_FAILED) {
                    throw new InterruptedException(
                            Messages.MoveProcessToOtherPackageAction_MoveProcessInterruptedError_desc1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}