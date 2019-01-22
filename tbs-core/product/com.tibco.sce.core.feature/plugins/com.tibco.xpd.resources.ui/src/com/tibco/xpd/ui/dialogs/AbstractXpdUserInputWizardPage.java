/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.ui.dialogs;

import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

/**
 * XPD Base class for all user input wizard pages. This class provides default
 * handling for dialog help, and sub-class can override
 * {@link com.tibco.xpd.ui.dialogs.AbstractXpdWizardResourceImportPage#getHelpContextId()
 * getHelpContextId()} to provide proper context help
 * 
 * @author rsawant
 * @since 18-Apr-2013
 */
public abstract class AbstractXpdUserInputWizardPage extends
        UserInputWizardPage {

    /**
     * @param name
     */
    public AbstractXpdUserInputWizardPage(String name) {
        super(name);
    }

    /**
     * No-arg constructor for extending classes
     */
    public AbstractXpdUserInputWizardPage() {
        this(""); //$NON-NLS-1$
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
