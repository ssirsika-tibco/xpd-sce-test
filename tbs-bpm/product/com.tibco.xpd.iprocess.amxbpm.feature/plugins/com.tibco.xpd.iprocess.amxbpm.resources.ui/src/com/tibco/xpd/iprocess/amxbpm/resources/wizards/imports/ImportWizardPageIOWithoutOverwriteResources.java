/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.resources.wizards.imports;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider;
import com.tibco.xpd.ui.importexport.importwizard.pages.ImportWizardPageIO;

/**
 * Import Wizard Page, without the Overwrite Resources check box option, as we
 * do not want to allow overwriting existing Processes.This wizard creates a
 * temporary hidden process packages special folder '.iProcessXpdls', parallel
 * to the selected process packages folder , for conversion purposes and copies
 * the files selected for import to that temporary folder for the conversion to
 * start.
 * 
 * @author aprasad
 * @since 04-Apr-2014
 */
public class ImportWizardPageIOWithoutOverwriteResources extends
        ImportWizardPageIO {

    /**
     * @param selection
     * @param provider
     */
    public ImportWizardPageIOWithoutOverwriteResources(
            IStructuredSelection selection, IImportWizardPageProvider provider) {

        super(selection, provider);
    }

    /**
     * Create the options specification widgets.Does nothing, as we do not
     * intend to allow overwriting existing processes in workspace.
     * 
     * @param parent
     *            org.eclipse.swt.widgets.Composite
     */
    @Override
    protected void createOptionsGroup(Composite parent) {
        // As we do not allow overwriting existing processes , we do not need
        // the options groups and the Overwrite Resources option
        // for Import/Convert iProcess to AMX BPM XPDL.

    }

}
