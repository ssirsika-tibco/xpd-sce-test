/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * Action to open the editor with the given id for a file.
 * 
 * @author njpatel
 * 
 */
public class OpenEditorForFileAction extends ModelAction {

    private final IFile file;

    private final String editorId;

    public OpenEditorForFileAction(IWorkbenchWindow window, String label,
            Image image, IFile file, String editorId) {
        super(window, label, image);
        this.file = file;
        this.editorId = editorId;
    }

    @Override
    public void run() {
        try {
            IDE.openEditor(getWorkbenchWindow().getActivePage(), file, editorId);
        } catch (PartInitException e) {
            ErrorDialog.openError(getWorkbenchWindow().getShell(),
                    Messages.OpenFileAction_openEditor_errorDialog_title,
                    Messages.OpenFileAction_openEditor_errorDialog_message,
                    new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID, e
                            .getLocalizedMessage(), e));
        }
    }

}
