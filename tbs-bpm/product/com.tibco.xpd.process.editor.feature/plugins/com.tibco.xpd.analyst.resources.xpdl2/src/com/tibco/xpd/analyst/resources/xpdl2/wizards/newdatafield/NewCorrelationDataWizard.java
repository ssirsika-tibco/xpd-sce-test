/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.ProcessElementSelectionPage;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 * 
 */
public class NewCorrelationDataWizard extends NewDataFieldWizard {

    /**
     * 
     */
    public NewCorrelationDataWizard() {
        super();

        setWindowTitle(Messages.NewCorrelationDataWizard_title);
        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(Xpdl2ResourcesPlugin
                        .getDefault()
                        .getImageRegistry()
                        .get(Xpdl2ResourcesConsts.WIZARD_NEW_CORRELATIONDATAFIELD)));

    }

    /**
     * Create the new Correlation Data Field.
     * 
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield.NewDataFieldWizard#createTemplate()
     * 
     * @return The correlation data field.
     */
    @Override
    public EObject createTemplate() {
        DataField input = (DataField) super.createTemplate();
        input.setCorrelation(true);
        input.setName(Messages.NewCorrelationDataWizard_NewFieldName);
        input.setReadOnly(true);
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewCorrelationDataWizard_NewFieldName);

        return input;
    }

    @Override
    protected void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage) {
        if (containerSelectionPage instanceof ProcessElementSelectionPage) {
            ((ProcessElementSelectionPage) containerSelectionPage)
                    .addProcessOrInterfaceModifyListeners(getTxtModifyListener());
        }
    }

    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {
        // SID MR 40840 - Removed special "isCorrelationData" from the
        // PackageOrProcessSelection class (MR 39259). The correct fix for
        // MR39259 is to use the ProcessElementSelectionPage class for
        // correlation data fields so that the process MUST be selected.
        return new ProcessElementSelectionPage(true, false);
    }

}
