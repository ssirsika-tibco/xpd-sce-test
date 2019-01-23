/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions.saveas;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.MaaArchiveHandler;

/**
 * Save as action that will create a new .maa.
 * 
 * @author njpatel
 * 
 */
public class SaveAsMaaAction extends SaveAsAction {

    /**
     * @param shell
     * @param title
     */
    public SaveAsMaaAction(Shell shell) {
        super(shell, Messages.SaveAsMaaAction_label);
        setToolTipText(Messages.SaveAsMaaAction_tooltip);
        setImageDescriptor(getImageDescriptor());
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsAction#preExportCheck(java.io.File)
     * 
     * @param target
     * @return
     * @throws CoreException
     */
    @Override
    protected boolean preExportCheck(final File target) {
        if (target.exists()) {
            final Boolean[] overwrite = new Boolean[] { false };
            getShell().getDisplay().syncExec(new Runnable() {

                @Override
                public void run() {
                    overwrite[0] =
                            MessageDialog.openQuestion(getShell(),
                                    Messages.SaveAsMaaAction_fileAlreadyExists_dialog_title,
                                    String.format(Messages.SaveAsMaaAction_fileAlreadyExists_dialog_longdesc,
                                            target.getAbsoluteFile()));
                }
            });

            if (!overwrite[0]) {
                // User declined to overwrite file so quit here
                return false;
            }
        }
        return true;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsAction#export(com.tibco.xpd.rcp.internal.resources.IRCPContainer,
     *      java.io.File, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param resource
     * @param target
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void export(IRCPContainer resource, final File target,
            IProgressMonitor monitor) throws CoreException {

        MaaArchiveHandler handler = new MaaArchiveHandler(target);

        handler.archive(resource.getProjectResources(), monitor);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsAction#getTargetPath()
     * 
     * @return
     */
    @Override
    protected String getTargetPath() {
        FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
        dlg.setFilterExtensions(new String[] { "*.maa" }); //$NON-NLS-1$
        dlg.setText(Messages.SaveAsMaaAction_maaFilePicker_dialog_label);

        return dlg.open();
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
    }

}
