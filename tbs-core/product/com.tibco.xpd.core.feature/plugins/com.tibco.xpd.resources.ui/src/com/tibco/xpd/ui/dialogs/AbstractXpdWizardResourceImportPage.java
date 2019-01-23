/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.ui.dialogs;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardResourceImportPage;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

/**
 * XPD Base class for all wizard resource import pages. This class provides
 * default handling for dialog help, and sub-class can override
 * {@link com.tibco.xpd.ui.dialogs.AbstractXpdWizardResourceImportPage#getHelpContextId()
 * getHelpContextId()} to provide proper context help
 * 
 * @author rsawant
 * @since 18-Apr-2013
 */
public abstract class AbstractXpdWizardResourceImportPage extends
        WizardResourceImportPage {

    /**
     * @param name
     * @param selection
     */
    protected AbstractXpdWizardResourceImportPage(String name,
            IStructuredSelection selection) {
        super(name, selection);
    }

    /**
     * Returns specific help page context ID. Extending class can override this
     * method to provide specific context id.
     * 
     * @Note If not overridden, default implementation returns <code>null</code>
     * 
     * @return Help context id if available, otherwise <code>null</code>
     */
    protected String getHelpContextId() {
        return null;
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#performHelp() Extednig classes
     */
    @SuppressWarnings("restriction")
    @Override
    public void performHelp() {
        String contextId = getHelpContextId();
        if (contextId == null) {
            contextId = IWorkbenchHelpContextIds.MISSING;
        }

        PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextId);
    }
}
