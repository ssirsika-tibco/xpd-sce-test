/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import org.eclipse.datatools.connectivity.internal.ui.wizards.CPWizardSelectionPage;
import org.eclipse.datatools.connectivity.internal.ui.wizards.NewCPWizard;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author rsomayaj
 * 
 */
public class XpdCPWizard extends NewCPWizard {

    private XpdCpWizardSelectionPage mProfilePage;

    /**
     * @see org.eclipse.datatools.connectivity.internal.ui.wizards.NewCPWizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        mProfilePage =
                new XpdCpWizardSelectionPage(CPWizardSelectionPage.class
                        .getName());
        addPage(mProfilePage);
    }

    /**
     * @see Wizard#performFinish
     */
    public boolean performFinish() {
        if (mProfilePage != null && !mProfilePage.getControl().isDisposed()) {
            IWizardNode selectedNode = mProfilePage.getSelectedNode();
            if (selectedNode == null)
                return false;

            IWizard wizard = selectedNode.getWizard();

            if (wizard == null) {
                return false;
            } else if (wizard.canFinish())
                return wizard.performFinish();
        }
        // mStore.setValue(DONNT_SHOW_INRO, mIntroPage.isHideIntro());
        return true;
    }

    /**
     * @see org.eclipse.datatools.connectivity.internal.ui.wizards.BaseWizard#mediatePage(org.eclipse.jface.wizard.IWizardPage)
     * 
     * @param wizardPage
     */
    @Override
    public void mediatePage(IWizardPage wizardPage) {
        super.mediatePage(wizardPage);
    }
}
