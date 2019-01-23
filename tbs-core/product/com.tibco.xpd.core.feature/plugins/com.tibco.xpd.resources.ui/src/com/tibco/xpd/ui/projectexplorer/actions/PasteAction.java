/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.ResourceTransfer;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Project Explorer paste action for the <code>SpecialFolder</code> object.
 * 
 * @author njpatel
 * 
 */
public class PasteAction extends SelectionListenerAction {

    /**
     * The id of this action.
     */
    public static final String ID = XpdResourcesUIActivator.ID + ".PasteAction";//$NON-NLS-1$

    private final Shell shell;

    private final Clipboard clipboard;

    public PasteAction(Shell shell, Clipboard clipboard) {
        super(Messages.PasteAction_action);
        this.shell = shell;
        this.clipboard = clipboard;

        setId(ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "HelpId"); //$NON-NLS-1$

    }

    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean ret = super.updateSelection(selection);

        if (ret) {
            ret = false;
            SpecialFolder target = getTarget();
            if (target != null && target.getFolder() != null) {
                // Check contents of clipboard
                IResource[] contents = (IResource[]) clipboard
                        .getContents(ResourceTransfer.getInstance());

                if (contents != null && contents.length > 0) {
                    // Make sure the contents are not projects
                    if (contents[0].getType() != IResource.PROJECT) {
                        ret = true;
                    }
                } else {
                    // Check for file transfer
                    TransferData[] transfers = clipboard.getAvailableTypes();
                    FileTransfer fileTransfer = FileTransfer.getInstance();
                    for (int i = 0; i < transfers.length && !ret; i++) {
                        if (fileTransfer.isSupportedType(transfers[i])) {
                            ret = true;
                        }
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public void run() {
        // Try a resource transfer
        IResource[] resources = (IResource[]) clipboard
                .getContents(ResourceTransfer.getInstance());

        if (resources != null && resources.length > 0) {
            SpecialFolder target = getTarget();

            if (target != null && target.getFolder() != null) {
                IFolder targetFolder = target.getFolder();

                if (targetFolder.isAccessible()) {
                    CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(
                            shell);
                    operation.copyResources(resources, targetFolder);
                }
            }
        } else {
            /*
             * Resource transfer not available so try file transfer
             */
            String[] fileNames = (String[]) clipboard.getContents(FileTransfer
                    .getInstance());

            if (fileNames != null) {
                SpecialFolder target = getTarget();

                if (target != null && target.getFolder() != null) {
                    IFolder targetFolder = target.getFolder();

                    if (targetFolder.isAccessible()) {
                        CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(
                                shell);
                        operation.copyFiles(fileNames, targetFolder);
                    }
                }
            }
        }
        /*
         * // try a resource transfer ResourceTransfer resTransfer =
         * ResourceTransfer.getInstance(); IResource[] resourceData =
         * (IResource[]) clipboard .getContents(resTransfer);
         * 
         * if (resourceData != null && resourceData.length > 0) { if
         * (resourceData[0].getType() == IResource.PROJECT) { // enablement
         * checks for all projects for (int i = 0; i < resourceData.length; i++) {
         * CopyProjectOperation operation = new CopyProjectOperation(
         * this.shell); operation.copyProject((IProject) resourceData[i]); } }
         * else { // enablement should ensure that we always have access to a
         * container IContainer container = getContainer();
         * 
         * CopyFilesAndFoldersOperation operation = new
         * CopyFilesAndFoldersOperation( this.shell);
         * operation.copyResources(resourceData, container); } return; } // try
         * a file transfer FileTransfer fileTransfer =
         * FileTransfer.getInstance(); String[] fileData = (String[])
         * clipboard.getContents(fileTransfer);
         * 
         * if (fileData != null) { // enablement should ensure that we always
         * have access to a container IContainer container = getContainer();
         * 
         * CopyFilesAndFoldersOperation operation = new
         * CopyFilesAndFoldersOperation( this.shell);
         * operation.copyFiles(fileData, container); }
         */
    }

    /**
     * Get the target of this paste action. This will be the first
     * <code>SpecialFolder</code> object in the selection
     * 
     * @param selection
     * @return
     */
    private SpecialFolder getTarget() {
        SpecialFolder target = null;
        IStructuredSelection selection = getStructuredSelection();

        if (selection != null) {
            for (Iterator<?> iter = selection.iterator(); iter.hasNext()
                    && target == null;) {
                Object obj = iter.next();

                if (obj instanceof SpecialFolder) {
                    target = (SpecialFolder) obj;
                }
            }
        }

        return target;
    }

}
