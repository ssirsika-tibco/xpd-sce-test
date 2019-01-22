/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.CreationWizard;
import com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Data Field wizard Abstracted from NewDataFieldWizard so that subclass can
 * override destination container according to the selected one (i.e. Task
 * Library can force participants to be added to the first process because that
 * represents the library.
 * 
 * @author njpatel
 */
public abstract class AbstractNewDataFieldWizard extends CreationWizard
        implements INewWizard, TemplateFactory {

    private AbstractSpecialFolderFileSelectionPage containerSelectionPage;

    private String[] pageTitles = { Messages.NewDataFieldWizard_PAGETITLE };

    private String[] pageDescriptions =
            { Messages.NewDataFieldWizard_PAGEDESCRIPTION };

    private IWorkbench workbench;

    /**
     * New Data Field wizard
     */
    public AbstractNewDataFieldWizard() {
        // Pass the wizard name identifier to the selection page (it will add it
        // to the page-control's data when it is eventually created.
        containerSelectionPage = getFileSelectionPage();

        // Cannot do the following here. Control will not have been created yet
        // - so will cause an exception.
        // containerSelectionPage.getControl().setData("name",
        // "NewDataFieldPage");

        init(this, containerSelectionPage);

        setWindowTitle(getWizardTitle());

        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(Xpdl2ResourcesPlugin.getDefault()
                        .getImageRegistry().get(getDefaultPageImageName())));
    }

    /**
     * @return Default Page image file name.
     */
    protected String getDefaultPageImageName() {
        return Xpdl2ResourcesConsts.WIZARD_NEW_DATAFIELD;
    }

    /**
     * 
     * @return Wizard Title String, Subclass should override this method to
     *         provide appropriate title.
     */
    protected String getWizardTitle() {
        return Messages.NewDataFieldWizard_TITLE;
    }

    /**
     * @param locationPage
     * @return The Eobject that is to contain the new object.
     */
    protected abstract EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage locationPage);

    protected abstract AbstractSpecialFolderFileSelectionPage getFileSelectionPage();

    protected abstract void addExtraContainerPageListeners(
            AbstractSpecialFolderFileSelectionPage containerSelectionPage);

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        EObject container = getEContainer(containerSelectionPage);
        if (container == null) {
            return false;
        }
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);
        Command cmd = getCommand();
        wc.getEditingDomain().getCommandStack().execute(cmd);

        // Select the created data field in the project explorer
        if (input != null) {
            selectAndReveal(input);
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getWorkbench()
     */
    @Override
    public IWorkbench getWorkbench() {
        return workbench;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        containerSelectionPage.init(selection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getPageDescription(int)
     */
    @Override
    public String getPageDescription(int index) {
        if (index > -1 && index < pageDescriptions.length) {
            return pageDescriptions[index];
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getPageTitle(int)
     */
    @Override
    public String getPageTitle(int index) {
        if (index > -1 && index < pageTitles.length) {
            return pageTitles[index];
        } else {
            return null;
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
        if (container != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.NewDataFieldWizard_AddDataField_menu);
            cmd.append(AddCommand.create(wc.getEditingDomain(),
                    container,
                    Xpdl2Package.eINSTANCE.getDataFieldsContainer_DataFields(),
                    input));
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
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
        Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
        DataField input = fact.createDataField();
        input.setName(Messages.NewDataFieldWizard_3);
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewDataFieldWizard_3);
        // Set basic string type
        BasicType basicType = fact.createBasicType();
        basicType.setType(BasicTypeType.STRING_LITERAL);
        Length len = Xpdl2Factory.eINSTANCE.createLength();
        len.setValue("50"); //$NON-NLS-1$
        basicType.setLength(len);

        input.setDataType(basicType);

        return input;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.XpdSection.XpdSectionContainer#getInputContainer
     * ()
     */
    @Override
    public EObject getInputContainer() {
        return getEContainer(containerSelectionPage);
    }

    protected ModifyListener txtModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            if (e.getSource() instanceof Text) {

                /*
                 * XPD-7040 GSD: DataField can be element inside some other data
                 * type, hence use method getDataFieldForInput() which will
                 * extract and return DataField from the input
                 */
                DataField dataField = getDataFieldForInput();

                if (getInputContainer() != null && dataField != null) {

                    String baseName;

                    if (dataField.isCorrelation()) {
                        baseName = getNewCorrelationFieldName();
                    } else {
                        baseName = getNewFieldName();
                    }

                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil
                            .getDuplicateDisplayFieldOrParam(getInputContainer(),
                                    dataField,
                                    finalName) != null
                            || Xpdl2ModelUtil
                                    .getDuplicateFieldOrParam(getInputContainer(),
                                            dataField,
                                            NameUtil.getInternalName(finalName,
                                                    true)) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }

                    dataField
                            .setName(NameUtil.getInternalName(finalName, true));
                    Xpdl2ModelUtil.setOtherAttribute(dataField,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
                }
            }
        }
    };

    /**
     * @return name for new Correlation Data Field.
     */
    protected String getNewFieldName() {
        return Messages.NewDataFieldWizard_FieldName_value;
    }

    /**
     * @return name for new Data Field
     */
    protected String getNewCorrelationFieldName() {
        return Messages.NewCorrelationDataWizard_NewFieldName;
    }

    /**
     * @return the txtModifyListener
     */
    protected ModifyListener getTxtModifyListener() {
        return txtModifyListener;
    }

    /**
     * This method Returns {@link DataField} for the input data, the input might
     * not be a DataField type, but {@link DataField} might be obtained from the
     * input.This method will allow the subclasses to extrct and return
     * {@link DataField} as appropriate from their input element.By Default this
     * method returns only if input itself is instance of {@link DataField},
     * else returns null.
     * 
     * @return
     * 
     */
    protected DataField getDataFieldForInput() {

        if (input instanceof DataField) {
            return (DataField) input;
        }

        return null;
    }

    @Override
    public void createPageControls(Composite pageContainer) {
        // Create the page controls
        for (IWizardPage page : getPages()) {
            page.createControl(pageContainer);

            if (page == containerSelectionPage) {
                containerSelectionPage
                        .addPacakgeFileModifyListeners(txtModifyListener);

                addExtraContainerPageListeners(containerSelectionPage);
            }
            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }
    }

    /**
     * @param pageTitles
     *            the pageTitles to set
     */
    protected void setPageTitles(String[] pageTitles) {
        this.pageTitles = pageTitles;
    }

    /**
     * @param pageDescriptions
     *            the pageDescriptions to set
     */
    protected void setPageDescriptions(String[] pageDescriptions) {
        this.pageDescriptions = pageDescriptions;
    }

    /**
     * @param workbench
     *            the workbench to set
     */
    protected void setWorkbench(IWorkbench workbench) {
        this.workbench = workbench;
    }

}
