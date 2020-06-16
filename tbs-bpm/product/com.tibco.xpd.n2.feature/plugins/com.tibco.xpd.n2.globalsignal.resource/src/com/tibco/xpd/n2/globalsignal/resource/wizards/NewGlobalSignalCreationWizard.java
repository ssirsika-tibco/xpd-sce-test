/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.wizards;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.util.GSDModelUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.CreationWizard;
import com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New global signal creation wizard.
 * 
 * @author sajain
 * @since Feb 3, 2015
 */
public class NewGlobalSignalCreationWizard extends CreationWizard implements
        INewWizard, TemplateFactory {

    private GSDSpecialFolderFileSelectionPage fileSelectionPage;

    private String[] pageTitles = { Messages.NewGlobalSignalWizard_PAGETITLE };

    private String[] pageDescriptions =
            { Messages.NewGlobalSignalWizard_PAGEDESCRIPTION };

    private IWorkbench workbench;

    GlobalSignal input;

    /**
     * New Global Signal wizard
     */
    public NewGlobalSignalCreationWizard() {

        /*
         * Pass the wizard name identifier to the selection page (it will add it
         * to the page-control's data when it is eventually created.
         */
        fileSelectionPage = getWizardPage();

        init(this, fileSelectionPage);

        setWindowTitle(Messages.NewGlobalSignalWizard_TITLE);

        setDefaultPageImageDescriptor(
                GsdResourcePlugin.getBundledImageDescriptor(GsdResourcePlugin.GLOBAL_SIGNAL_WIZARD_IMAGE));

    }

    /**
     * @param newGlobalSignalWizardPage
     * @return The Eobject that is to contain the new object.
     */
    protected EObject getEContainer(
            AbstractSpecialFolderFileSelectionPage newGlobalSignalWizardPage) {
        return newGlobalSignalWizardPage.getEContainer();
    }

    protected GSDSpecialFolderFileSelectionPage getWizardPage() {

        return new GSDSpecialFolderFileSelectionPage(
                Messages.NewGlobalSignalWizard_FileSelectionPage_Title,
                Messages.NewGlobalSignalWizard_FileSelectionPage_Desc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#performFinish()
     */
    @Override
    public boolean performFinish() {

        EObject container = getEContainer(fileSelectionPage);

        if (container == null) {
            return false;
        }

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);
        Command cmd = getCommand();

        wc.getEditingDomain().getCommandStack().execute(cmd);

        /*
         * Select the created global signal in the project explorer
         */
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

        if (container != null && container instanceof GlobalSignalDefinitions) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.NewGlobalSignalWizard_AddGlobalSignal_menu);

            cmd.append(AddCommand.create(wc.getEditingDomain(),
                    (container),
                    GlobalSignalDefinitionPackage.eINSTANCE
                            .getGlobalSignalDefinitions_GlobalSignals(),
                    input));
            return cmd;

        } else if (container instanceof GlobalSignal) {

            WorkingCopy wc1 =
                    WorkingCopyUtil.getWorkingCopyFor(getInputContainer());

            if (wc1.getRootElement() instanceof GlobalSignalDefinitions) {

                GlobalSignalDefinitions gsds =
                        (GlobalSignalDefinitions) (wc1.getRootElement());

                WorkingCopy wc2 = WorkingCopyUtil.getWorkingCopyFor(gsds);

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.NewGlobalSignalWizard_AddGlobalSignal_menu);

                cmd.append(AddCommand.create(wc2.getEditingDomain(),
                        gsds,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignalDefinitions_GlobalSignals(),
                        input));
                return cmd;
            }

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
        GlobalSignalDefinitionFactory fact =
                GlobalSignalDefinitionFactory.eINSTANCE;
        input = fact.createGlobalSignal();

        /*
         * Create a Default Correlation field.
         */
        PayloadDataField pdf =
                GlobalSignalDefinitionFactory.eINSTANCE
                        .createPayloadDataField();

        pdf.setName(NameUtil
                .getInternalName(Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName,
                        true));

        Xpdl2ModelUtil.setOtherAttribute(pdf,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName);

        /*
         * Set basic string type
         */
        BasicType basicType = Xpdl2Factory.eINSTANCE.createBasicType();
        basicType.setType(BasicTypeType.STRING_LITERAL);
        Length len = Xpdl2Factory.eINSTANCE.createLength();
        len.setValue("50"); //$NON-NLS-1$
        basicType.setLength(len);

        pdf.setDataType(basicType);
        pdf.setCorrelation(true);

        /*
         * Add default correlation field to the global signal.
         */
        input.getPayloadDataFields().add(pdf);

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
        return getEContainer(fileSelectionPage);
    }

    @Override
    public void createPageControls(Composite pageContainer) {

        /*
         * Create the page controls
         */
        for (IWizardPage page : getPages()) {
            page.createControl(pageContainer);

            /*
             * Page is responsible for ensuring the created control is
             * accessible via getControl.
             */
            Assert.isNotNull(page.getControl());
        }
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param workbench
     * @param selection
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        fileSelectionPage.init(selection);

        /*
         * Make the global signal names unique.
         */
        String baseName;

        baseName = Messages.NewGlobalSignalWizard_SignalName_value;

        String finalName = baseName;

        int idx = 1;

        while (GSDModelUtil.getDuplicateGlobalSignal(getInputContainer(),
                input,
                NameUtil.getInternalName(finalName, true)) != null) {

            idx++;
            finalName = baseName + idx;
        }

        input.setName(NameUtil.getInternalName(finalName, true));
    }

}