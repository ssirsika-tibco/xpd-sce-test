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

import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * Open the editor for the given {@link Organization}.
 * 
 * @author njpatel
 * 
 */
public class OpenOrganizationAction extends ModelAction {

    private final Organization eo;

    /**
     * @param window
     * @param label
     * @param image
     * @param org
     */
    public OpenOrganizationAction(IWorkbenchWindow window, String label,
            Image image, Organization org) {
        super(window, label, image);
        this.eo = org;
    }

    @Override
    public void run() {
        try {
            OrganizationModelDiagramEditorUtil.openDiagram(eo);
        } catch (PartInitException e) {
            ErrorDialog.openError(getWorkbenchWindow().getShell(),
                    Messages.OpenOMAction_openEditor_error_title,
                    Messages.OpenOMAction_openOrgEditor_error_message,
                    new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID, e
                            .getLocalizedMessage(), e));
        }
    }

}
