/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessGroup;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Allows moving/refactoring of process to different Process Group/Package/Xpdl
 * Resource by performing Drag and Drop.
 * 
 * 
 * @author kthombar
 * @since 14-Sep-2014
 */
public class DNDProcessToPackage extends CommonDropAdapterAssistant {

    /** Status for failed drop operation. */
    protected static final IStatus STATUS_FAILED = new Status(IStatus.ERROR,
            Xpdl2ResourcesPlugin.PLUGIN_ID,
            Messages.DNDProcessToPackage_MoveProcessFailedError_desc);

    /** Status for unknown refactoring operation return code. */
    protected static final IStatus STATUS_UNKNOWN = new Status(IStatus.ERROR,
            Xpdl2ResourcesPlugin.PLUGIN_ID,
            Messages.DNDProcessToPackage_ProblemDuringProcessMove_desc);

    private Process processToDND;

    private IFile targetFile;

    /**
     * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#validateDrop(java.lang.Object,
     *      int, org.eclipse.swt.dnd.TransferData)
     * 
     * @param target
     * @param operation
     * @param transferType
     * @return
     */
    @Override
    public IStatus validateDrop(Object target, int operation,
            TransferData transferType) {

        ISelection selection =
                LocalSelectionTransfer.getTransfer().getSelection();

        List<Process> validSelectedProcesses =
                getValidSelectedProcesses(selection);

        if (!validSelectedProcesses.isEmpty()
                && validSelectedProcesses.size() == 1) {
            /*
             * We allow SINGLE process drop to either a ProcessGroup or a
             * Package or xpdl file.
             */
            processToDND = validSelectedProcesses.get(0);

            if (target instanceof Package) {

                targetFile = WorkingCopyUtil.getFile((Package) target);

            } else if (target instanceof ProcessGroup) {

                Object parent = ((ProcessGroup) target).getParent();

                if (parent instanceof Package) {

                    targetFile = WorkingCopyUtil.getFile((Package) parent);
                }
            } else if (target instanceof IFile) {

                targetFile = (IFile) target;
            }

            if (targetFile != null && processToDND != null) {

                Package packageOfDraggedProcess = processToDND.getPackage();

                Package packageOfResource =
                        (Package) WorkingCopyUtil.getWorkingCopy(targetFile)
                                .getRootElement();

                if (!packageOfDraggedProcess.getId()
                        .equals(packageOfResource.getId())) {

                    /*
                     * we should prevent dragging processes and dropping them to
                     * its own package.
                     */

                    return Status.OK_STATUS;
                }
            }
        }
        return Status.CANCEL_STATUS;
    }

    /**
     * Returns a list of Process objects founds in the <i>selection</i>. The
     * objects in the selection will be tested for whether they are an
     * instanceof of Process
     * 
     * @return
     */
    private List<Process> getValidSelectedProcesses(ISelection selection) {

        List<Process> selectedProcesses = new ArrayList<Process>();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection =
                    (IStructuredSelection) selection;

            for (Iterator<?> i = structuredSelection.iterator(); i.hasNext();) {
                Object o = i.next();
                if (o instanceof Process) {
                    selectedProcesses.add((Process) o);
                }
            }
        }
        return selectedProcesses;
    }

    /**
     * Moves the selected processes to the target package.
     * 
     * @see org.eclipse.ui.navigator.CommonDropAdapterAssistant#handleDrop(org.eclipse.ui.navigator.CommonDropAdapter,
     *      org.eclipse.swt.dnd.DropTargetEvent, java.lang.Object)
     * 
     * @param aDropAdapter
     * @param aDropTargetEvent
     * @param aTarget
     * @return
     */
    @Override
    public IStatus handleDrop(CommonDropAdapter aDropAdapter,
            DropTargetEvent aDropTargetEvent, Object target) {

        IStatus status = null;
        if (processToDND != null && targetFile != null) {

            /*
             * If there are any dirty(unsaved) editors then ask the user to save
             * them, if the user does not choose to save then do not proceed.
             */
            if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
                return status;
            }

            RefactoringWizard refactoringWizard =
                    new MoveProcessWizard(processToDND);

            ((MoveProcessProcessor) ((MoveRefactoring) refactoringWizard
                    .getRefactoring()).getMoveProcessor())
                    .setDestinationResource(targetFile);

            RefactoringWizardOpenOperation op =
                    new RefactoringWizardOpenOperation(refactoringWizard);

            try {
                int rc =
                        op.run(getShell(),
                                Messages.DNDProcessToPackage_MoveProcessWizard_title);

                switch (rc) {

                case RefactoringWizardOpenOperation.INITIAL_CONDITION_CHECKING_FAILED:

                    status = STATUS_FAILED;
                    break;

                case IDialogConstants.CANCEL_ID:

                    status = Status.CANCEL_STATUS;
                    break;

                case IDialogConstants.OK_ID:

                    status = Status.OK_STATUS;
                    break;

                default:

                    status = STATUS_UNKNOWN;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return status;
    }
}
