/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.rcp.internal.svn.SVNCheckOutWizard;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * 
 * 
 * @author kupadhya
 * @since 27 Nov 2012
 */
public class OpenProjectFromSVNURLAction extends OpenAction {

    /**
     * @param window
     * 
     */
    public OpenProjectFromSVNURLAction(IWorkbenchWindow window) {
        super(window, Messages.OpenProjectFromSVNURLAction_label,
                "open-projects-svnfolder"); //$NON-NLS-1$
        setId("newSvnProject"); //$NON-NLS-1$
        setToolTipText(Messages.OpenProjectFromSVNURLAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.SVN
                .getPath()));
        // Register for key-binding
        registerCommand("com.tibco.xpd.rcp.command.openSVNURL"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.OpenFolderAction#getSelection()
     * 
     * @return
     */
    @Override
    public String getSelection() {
        SVNCheckOutWizard chkoutWizard = new SVNCheckOutWizard();
        WizardDialog dlg =
                new WizardDialog(getWindow().getShell(), chkoutWizard);
        dlg.setPageSize(0, 200);
        dlg.setBlockOnOpen(true);
        if (dlg.open() == WizardDialog.OK) {
            String checkOutLocation = chkoutWizard.getCheckOutLocation();
            return checkOutLocation;
        } else {
            setIsActionCancelled(true);
            return null;
        }
    }
}
