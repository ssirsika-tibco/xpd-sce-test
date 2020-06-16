/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.wizards;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield.AbstractNewDataFieldWizard;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.util.GSDModelUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New payload data creation wizard.
 * 
 * @author sajain
 * @since Feb 5, 2015
 */
public class NewPayloadDataCreationWizard extends AbstractNewDataFieldWizard {

    /**
     * Constructor for Basic Settings
     */
    public NewPayloadDataCreationWizard() {

        super();

        /* Set page Titles */
        setPageTitles(new String[] { Messages.NewPayloadDataCreationWizard_PageTitle });

        /* Set Page Descriptions */
        setPageDescriptions(new String[] { Messages.NewPayloadDataCreationWizard_PageDescription });

        setDefaultPageImageDescriptor(
                GsdResourcePlugin.getBundledImageDescriptor(GsdResourcePlugin.GLOBAL_PAYLOAD_DATA_WIZARD_IMAGE));

    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield.AbstractNewDataFieldWizard#getEContainer(com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage)
     * 
     * @param locationPage
     * @return
     */
    @Override
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage) {
        return locationPage.getEContainer();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield.AbstractNewDataFieldWizard#getFileSelectionPage()
     * 
     * @return
     */
    @Override
    protected AbstractSpecialFolderFileSelectionPage getFileSelectionPage() {

        return new GSDSpecialFolderFileSelectionPage(
                Messages.NewPayloadDataWizard_FileSelectionPage_Title,
                Messages.NewPayloadDataWizard_FileSelectionPage_Desc);
    }

    @Override
    protected void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage) {

        if (containerSelectionPage instanceof GSDSpecialFolderFileSelectionPage) {

            ((GSDSpecialFolderFileSelectionPage) containerSelectionPage)
                    .addSignalModifyListeners(getTxtModifyListener());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory#createCommand
     * (org.eclipse.emf.ecore.EObject, org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public Command createCommand(EObject input, IWizardPage locationPage) {

        AbstractSpecialFolderFileSelectionPage spp =
                (AbstractSpecialFolderFileSelectionPage) locationPage;

        EObject container = getEContainer(spp);

        if (container != null && container instanceof GlobalSignal) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.NewPayloadDataWizard_AddPayloadData_menu);

            cmd.append(AddCommand.create(wc.getEditingDomain(),
                    (container),
                    GlobalSignalDefinitionPackage.eINSTANCE
                            .getGlobalSignal_PayloadDataFields(),
                    input));
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @return Default Page image name.
     */
    @Override
    protected String getDefaultPageImageName() {

        return Xpdl2ResourcesConsts.WIZARD_NEW_DATAFIELD;
    }

    /**
     * 
     * @return Wizard Title String, Subclass should override this method to
     *         provide appropriate title.
     */
    @Override
    protected String getWizardTitle() {

        return Messages.NewPayloadDataCreationWizard_2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory#createTemplate
     * ()
     */
    @Override
    public EObject createTemplate() {

        /* Creates Payload Data Template */
        PayloadDataField payloadDataField =
                GlobalSignalDefinitionFactory.eINSTANCE
                        .createPayloadDataField();

        payloadDataField
                .setName(Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName);

        Xpdl2ModelUtil.setOtherAttribute(payloadDataField,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName);

        // Set basic string type
        BasicType basicType = Xpdl2Factory.eINSTANCE.createBasicType();
        basicType.setType(BasicTypeType.STRING_LITERAL);
        Length len = Xpdl2Factory.eINSTANCE.createLength();
        len.setValue("50"); //$NON-NLS-1$
        basicType.setLength(len);

        payloadDataField.setDataType(basicType);

        return payloadDataField;
    }

    /**
     * @return
     * 
     */
    @Override
    protected DataField getDataFieldForInput() {

        if (input instanceof PayloadDataField) {
            return ((PayloadDataField) input);
        }

        return null;
    }

    /**
     * @return name for new Correlation Data Field.
     */
    @Override
    protected String getNewFieldName() {
        return Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName;
    }

    /**
     * @return name for new Data Field
     */
    @Override
    protected String getNewCorrelationFieldName() {
        return Messages.NewPayloadDataCreationWizard_CorrelationPayloadDataDefaultName;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield.AbstractNewDataFieldWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param workbench
     * @param selection
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {

        super.init(workbench, selection);

        if (input instanceof PayloadDataField) {

            /*
             * Make the payload data names unique.
             */
            String baseName;

            baseName =
                    Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName;

            String finalName = baseName;

            int idx = 1;

            while (GSDModelUtil.getDuplicatePayloadData(getInputContainer(),
                    (PayloadDataField) input,
                    NameUtil.getInternalName(finalName, true)) != null) {

                idx++;
                finalName = baseName + idx;
            }

            ((PayloadDataField) input).setName(NameUtil
                    .getInternalName(finalName, true));

            Xpdl2ModelUtil
                    .setOtherAttribute(((PayloadDataField) input),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
        }
    }
}
