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

import com.tibco.xpd.processeditor.xpdl2.ProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action to open a given {@link Process}.
 * 
 * @author njpatel
 * 
 */
public class OpenProcessAction extends ModelAction {

    private final Process process;

    private final WorkingCopy wc;

    public OpenProcessAction(IWorkbenchWindow window, String label,
            Image image, WorkingCopy wc, Process process) {
        super(window, label, image);
        this.wc = wc;
        this.process = process;
    }

    @Override
    public void run() {
        try {
            IDE.openEditor(getWorkbenchWindow().getActivePage(),
                    new ProcessEditorInput(wc, process),
                    ProcessDiagramEditor.EDITOR_ID);
        } catch (PartInitException e) {
            ErrorDialog
                    .openError(getWorkbenchWindow().getShell(),
                            Messages.OpenProcessAction_errorOpeningEditor_dialog_title,
                            Messages.OpenProcessAction_errorOpeningEditor_dialog_message,
                            new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID, e
                                    .getLocalizedMessage(), e));
        }
    }
}
