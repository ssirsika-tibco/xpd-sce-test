package com.tibco.xpd.deploy.ui.wizards.deploy;

import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Extended Wizard Selection Page so we can have extra methods to perform as
 * this class is used from an extension point and we need to ensure extra
 * functionality for the future.
 * 
 * @author glewis
 * 
 */
public abstract class ExtAddWizardSelectionPage extends AbstractXpdWizardPage {

    public ExtAddWizardSelectionPage() {
        this(""); //$NON-NLS-1$
    }

    public ExtAddWizardSelectionPage(String pageName) {
        super(pageName);
    }

    @Override
    public void createControl(Composite parent) {
    }

    /**
     * Used to perform any operations on the server after the containing wizard
     * has finished with this page.
     * 
     * @param server
     */
    public abstract void performFinish(Server server);

}
