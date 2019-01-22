/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import org.eclipse.datatools.connectivity.internal.ui.wizards.CPWizardSelectionPage;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author rsomayaj
 * 
 */
public class XpdCpWizardSelectionPage extends CPWizardSelectionPage {

    /**
     * @param id
     */
    public XpdCpWizardSelectionPage(String id) {
        super(id);
        setNeedMediation(true);
    }

    /**
     * @see org.eclipse.datatools.connectivity.internal.ui.wizards.CPWizardSelectionPage#getNextPage()
     * 
     * @return
     */
    @Override
    public IWizardPage getNextPage() {
        IWizardPage nextPage = super.getNextPage();
        IWizardNode selNode = getSelectedNode();
        if (selNode != null) {
            IWizard wiz = selNode.getWizard();
            if (wiz instanceof Wizard) {
                Wizard wizard = (Wizard) wiz;
                IWizardPage[] pages = wizard.getPages();
                IWizardPage previousPage = null;
                IWizardPage summaryWizardPage = null;
                IWizardPage existingPage = null;
                for (IWizardPage wizPage : pages) {
                    if ("SummaryWizardPage".equals(wizPage.getName())) { //$NON-NLS-1$
                        previousPage = wizPage.getPreviousPage();
                        summaryWizardPage = wizPage;
                    }
                    if ("TestWizardPage".equals(wizPage.getName())) { //$NON-NLS-1$
                        existingPage = wizPage;
                    }
                }
                if (summaryWizardPage != null && existingPage == null) {
                    TestWizardPage page = new TestWizardPage();
                    wizard.addPage(page);
                }

                if (summaryWizardPage.getControl() != null) {
                    summaryWizardPage.setVisible(false);

                }
            }
        }
        return nextPage;
    }

    /**
     * @see org.eclipse.datatools.connectivity.internal.ui.wizards.BaseWizardPage#onWizardNext()
     * 
     * @return
     */
    @Override
    public boolean onWizardNext() {
        return super.onWizardNext();
    }

    /**
     * @see org.eclipse.datatools.connectivity.internal.ui.wizards.CPWizardSelectionPage#onSetActive()
     * 
     */
    @Override
    public void onSetActive() {
        // TODO Auto-generated method stub
        super.onSetActive();
    }
}
