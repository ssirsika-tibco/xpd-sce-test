/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageOrProcessSelectionPage;

/**
 * New Data Field wizard
 * 
 * @author njpatel
 */
public class NewDataFieldWizard extends AbstractNewDataFieldWizard {

    @Override
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage) {
        return locationPage.getEContainer();
    }

    @Override
    protected void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage) {
        if (containerSelectionPage instanceof PackageOrProcessSelectionPage) {
            ((PackageOrProcessSelectionPage) containerSelectionPage)
                    .addProcessOrInterfaceModifyListeners(getTxtModifyListener());
        }
    }

    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {
        // SID MR 40840 - Removed special "isCorrelationData" from the
        // PackageOrProcessSelection class (MR 39259). The correct fix for
        // MR39259 is to use the ProcessElementSelectionPage class for
        // correlation data fields so that the process MUST be selected.

        return new PackageOrProcessSelectionPage("process.datafield.wizard"); //$NON-NLS-1$
    }

}