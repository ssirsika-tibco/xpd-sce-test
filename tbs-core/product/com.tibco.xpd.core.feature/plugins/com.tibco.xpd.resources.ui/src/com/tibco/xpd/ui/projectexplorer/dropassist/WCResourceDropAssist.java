/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.dropassist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring;
import org.eclipse.ltk.internal.core.refactoring.resource.MoveResourcesProcessor;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ltk.ui.refactoring.resource.MoveResourcesWizard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.MoveFilesAndFoldersOperation;
import org.eclipse.ui.actions.ReadOnlyStateChecker;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.part.ResourceTransfer;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * This project explorer drop assist will check the dirty state of any files
 * that are being dragged that have a <code>WorkingCopy</code>. *
 * <p>
 * SID XPD-1600. WorkingCopyResourceMoveRefactorParticipant now handles checking
 * for and saving dirty working copies (provided that the drop assistant for the
 * given folder uses LTK refactoring wizard correctly).
 * 
 * So we no longer show a message box to ask the user here.
 * 
 * @since 3.0.1
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("restriction")
public class WCResourceDropAssist extends CommonDropAdapterAssistant {
    /** Status for failed drop operation. */
    protected static final IStatus STATUS_FAILED = new Status(IStatus.ERROR,
            XpdResourcesUIActivator.ID,
            Messages.WCResourceDropAssist_failed_message);

    /** Status for unknown refactoring operation return code. */
    protected static final IStatus STATUS_UNKNOWN = new Status(IStatus.ERROR,
            XpdResourcesUIActivator.ID,
            Messages.WCResourceDropAssist_unknown_error_message);

    @Override
    public boolean isSupportedType(TransferData aTransferType) {
        return super.isSupportedType(aTransferType)
                || FileTransfer.getInstance().isSupportedType(aTransferType);
    }

    @Override
    public IStatus handleDrop(CommonDropAdapter aDropAdapter,
            DropTargetEvent aDropTargetEvent, Object aTarget) {
        IStatus retStatus = Status.OK_STATUS;

        if (aDropAdapter.getCurrentTarget() != null
                && aDropTargetEvent.data != null) {
            TransferData currentTransfer = aDropAdapter.getCurrentTransfer();
            // Target should be a container
            IContainer targetContainer = null;
            if (aTarget instanceof IContainer) {
                targetContainer = (IContainer) aTarget;
            }

            if (targetContainer != null && targetContainer.isAccessible()) {
                // Check if it's a file transfer
                if (FileTransfer.getInstance().isSupportedType(currentTransfer)) {
                    retStatus =
                            performFileDrop(targetContainer,
                                    aDropTargetEvent.data);
                } else {
                    // This is drag from within Eclipse
                    IResource[] resources = null;

                    if (LocalSelectionTransfer.getTransfer()
                            .isSupportedType(currentTransfer)) {
                        // Transfer within Eclipse
                        resources =
                                getValidSelectedResources(LocalSelectionTransfer
                                        .getTransfer().getSelection());

                    } else if (ResourceTransfer.getInstance()
                            .isSupportedType(currentTransfer)) {
                        // Transfer from another Eclipse session
                        resources = (IResource[]) aDropTargetEvent.data;
                    }

                    if (resources != null && resources.length > 0) {
                        if (aDropAdapter.getCurrentOperation() == DND.DROP_COPY) {
                            retStatus =
                                    performResourceCopy(targetContainer,
                                            getShell(),
                                            resources);

                        } else {
                            /*
                             * SID XPD-1600.
                             * WorkingCopyResourceMoveRefactorParticipant now
                             * handles checking for and saving dirty working
                             * copies (provided that the drop assistant for the
                             * given folder uses LTK refactoring wizard
                             * correctly).
                             * 
                             * So we no longer show a message box to ask the
                             * user here.
                             */
                            retStatus =
                                    performResourceMove(targetContainer,
                                            resources);
                        }
                    }
                }
            } else {
                retStatus =
                        createStatus(IStatus.ERROR,
                                Messages.WCResourceDropAssist_unaccessibleContainer_error_shortdesc,
                                null);
            }
        } else {
            // Dnd has been cancelled as there is either no data or no drop
            // target
            retStatus = Status.CANCEL_STATUS;
        }

        return retStatus;
    }

    /**
     * Check if the resources list contains dirty files.
     * 
     * @param resources
     *            resources selected to drop
     * @return <code>true</code> if the selection contains a dirty file,
     *         <code>false</code> otherwise.
     */
    private boolean containsDirtyFiles(IResource[] resources) {
        final boolean[] isDirty = new boolean[] { false };

        if (resources != null) {
            for (IResource res : resources) {
                if (res instanceof IContainer) {
                    try {
                        ((IContainer) res).accept(new IResourceVisitor() {

                            @Override
                            public boolean visit(IResource resource)
                                    throws CoreException {

                                // If a dirty file has not been discovered
                                // then check the resource
                                if (!isDirty[0]) {
                                    if (resource instanceof IFile) {
                                        WorkingCopy wc =
                                                XpdResourcesPlugin
                                                        .getDefault()
                                                        .getWorkingCopy(resource);

                                        if (wc != null
                                                && wc.isWorkingCopyDirty()) {
                                            isDirty[0] = true;
                                        }
                                    } else {
                                        // Continue searching
                                        return true;
                                    }
                                }

                                return false;
                            }

                        });
                    } catch (CoreException e) {
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .error(e);
                    }
                } else if (res instanceof IFile) {
                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault().getWorkingCopy(res);

                    if (wc != null && wc.isWorkingCopyDirty()) {
                        isDirty[0] = true;
                        break;
                    }
                }
            }
        }

        return isDirty[0];
    }

    @Override
    public IStatus validateDrop(Object target, int operation,
            TransferData transferType) {
        IStatus retStatus = Status.OK_STATUS;

        // Target should be a container
        if (target instanceof IContainer) {
            IContainer targetContainer = (IContainer) target;

            if (targetContainer != null && targetContainer.isAccessible()) {
                String message = null;

                // Check if this is a drag within Eclipse
                if (LocalSelectionTransfer.getTransfer()
                        .isSupportedType(transferType)) {

                    IResource[] resources =
                            getValidSelectedResources(LocalSelectionTransfer
                                    .getTransfer().getSelection());

                    if (resources.length > 0) {
                        CopyFilesAndFoldersOperation dndOperation;

                        if (operation == DND.DROP_COPY) {
                            // Copy operation
                            dndOperation =
                                    new CopyFilesAndFoldersOperation(getShell());
                        } else {
                            // Move operation
                            dndOperation =
                                    new MoveFilesAndFoldersOperation(getShell());
                        }

                        message =
                                dndOperation
                                        .validateDestination(targetContainer,
                                                resources);
                    } else {
                        // Selection failed
                        message =
                                Messages.WCResourceDropAssist_errorOnDrop_error_shortdesc;
                    }

                } else if (FileTransfer.getInstance()
                        .isSupportedType(transferType)) {
                    // This is a file transfer
                    String[] sourceNames =
                            (String[]) FileTransfer.getInstance()
                                    .nativeToJava(transferType);

                    if (sourceNames == null) {
                        /*
                         * source names will be null on Linux. Use empty names
                         * to do destination validation. Fixes bug 29778
                         */
                        sourceNames = new String[0];
                    }
                    CopyFilesAndFoldersOperation copyOperation =
                            new CopyFilesAndFoldersOperation(getShell());
                    message =
                            copyOperation
                                    .validateImportDestination(targetContainer,
                                            sourceNames);
                }

                // If there is an error message then return it
                if (message != null) {
                    retStatus = createStatus(IStatus.ERROR, message, null);
                }
            } else {
                // Target folder not found or not accessible
                retStatus =
                        createStatus(IStatus.ERROR,
                                Messages.WCResourceDropAssist_unaccessibleContainer_error_shortdesc,
                                null);
            }
        } else {
            // Target not a container
            retStatus =
                    createStatus(IStatus.INFO,
                            Messages.WCResourceDropAssist_targetMustBeContainer_info_shortdesc,
                            null);
        }

        return retStatus;
    }

    /**
     * Create a status associated with this plugin.
     * 
     * @param severity
     * @param aMessage
     * @param exception
     * @return A status configured with this plugin's id and the given
     *         parameters.
     */
    private IStatus createStatus(int severity, String aMessage,
            Throwable exception) {
        return new Status(severity, XpdResourcesPlugin.ID_PLUGIN, IStatus.OK,
                aMessage != null ? aMessage
                        : Messages.WCResourceDropAssist_noMessage_shortdesc,
                exception);
    }

    /**
     * Returns a list of <code>IResource</code> objects founds in the
     * <i>selection</i>. The objects in the selection will be tested for whether
     * they are an instanceof of <code>IResource</code> or can adapt to it.
     * 
     * @return the resource selection from the <i>selection</i> if the it is an
     *         instanceof of <code>IStructuredSelection</code>, <b>null</b> will
     *         be returned otherwise.
     */
    private IResource[] getValidSelectedResources(ISelection selection) {

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection =
                    (IStructuredSelection) selection;
            List<IResource> selectedResources = new ArrayList<IResource>();

            for (Iterator<?> i = structuredSelection.iterator(); i.hasNext();) {
                Object o = i.next();
                IResource res = null;
                if (o instanceof IResource) {
                    res = (IResource) o;
                } else if (o instanceof IAdaptable) {
                    IAdaptable a = (IAdaptable) o;
                    res = (IResource) a.getAdapter(IResource.class);
                }

                if (res != null) {
                    selectedResources.add(res);
                }
            }
            return selectedResources.toArray(new IResource[selectedResources
                    .size()]);
        }

        return null;
    }

    /**
     * Performs a resource copy
     */
    private IStatus performResourceCopy(IContainer target, Shell shell,
            IResource[] sources) {

        CopyFilesAndFoldersOperation operation =
                new CopyFilesAndFoldersOperation(shell);
        operation.copyResources(sources, target);

        return Status.OK_STATUS;
    }

    /**
     * Performs a resource move
     */
    private IStatus performResourceMove(IContainer target, IResource[] sources) {

        ReadOnlyStateChecker checker =
                new ReadOnlyStateChecker(getShell(),
                        Messages.WCResourceDropAssist_checkMove_title,
                        Messages.WCResourceDropAssist_readOnlyFileDlg_message);

        sources = checker.checkReadOnlyResources(sources);

        IStatus status;
        RefactoringWizard refactoringWizard = new MoveResourcesWizard(sources) {
            @Override
            protected void addUserInputPages() {
            }
        };
        ((MoveResourcesProcessor) ((MoveRefactoring) refactoringWizard
                .getRefactoring()).getMoveProcessor()).setDestination(target);
        RefactoringWizardOpenOperation op =
                new RefactoringWizardOpenOperation(refactoringWizard);
        try {
            int rc =
                    op.run(getShell(),
                            Messages.WCResourceDropAssist_move_operation_label);
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
            status = Status.CANCEL_STATUS;
        }

        return status;
    }

    /**
     * Performs a drop using the FileTransfer transfer type.
     */
    private IStatus performFileDrop(final IContainer target, Object data) {
        final String[] names = (String[]) data;

        // Run the import operation asynchronously.
        // Otherwise the drag source (e.g., Windows Explorer) will be blocked
        // while the operation executes. Fixes bug 16478.
        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                getShell().forceActive();
                CopyFilesAndFoldersOperation operation =
                        new CopyFilesAndFoldersOperation(getShell());
                operation.copyFiles(names, target);
            }
        });
        return Status.OK_STATUS;
    }

}
