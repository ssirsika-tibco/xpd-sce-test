/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInput;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * Action to open a given {@link ProcessInterface}.
 * 
 * @author njpatel
 * 
 */
public class OpenProcessInterfaceAction extends ModelAction {

    private final ProcessInterface process;

    private final WorkingCopy wc;

    public OpenProcessInterfaceAction(IWorkbenchWindow window, String label,
            Image image, WorkingCopy wc, ProcessInterface process) {
        super(window, label, image);
        this.wc = wc;
        this.process = process;
    }

    @Override
    public void run() {
        try {

            IDE.openEditor(getWorkbenchWindow().getActivePage(),
                    new ProcessInterfaceEditorInput(wc, process),
                    RCPConsts.PROCESS_INTERFACE_EDITOR_ID);

        } catch (PartInitException e) {
            ErrorDialog
                    .openError(getWorkbenchWindow().getShell(),
                            Messages.OpenProcessInterfaceAction_errorOpeningEditor_dialog_title,
                            Messages.OpenProcessInterfaceAction_errorOpeningEditor_dialog_message,
                            new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID, e
                                    .getLocalizedMessage(), e));
        }
    }
}
