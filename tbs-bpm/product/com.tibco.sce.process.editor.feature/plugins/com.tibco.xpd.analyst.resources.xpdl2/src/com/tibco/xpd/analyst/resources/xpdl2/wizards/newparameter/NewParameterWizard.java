/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.newparameter;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.ProcessElementSelectionPage;

/**
 * New Formal Parameter Wizard
 * 
 * @author njpatel
 * 
 */
public class NewParameterWizard extends AbstractNewParameterWizard {

    @Override
    protected void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage) {
        if (containerSelectionPage instanceof ProcessElementSelectionPage) {
            ((ProcessElementSelectionPage) containerSelectionPage)
                    .addProcessOrInterfaceModifyListeners(getTextModifyListener());
        }
    }

    @Override
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage) {
        return locationPage.getEContainer();
    }

    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {
        return new ProcessElementSelectionPage(true, true);
    }

}
