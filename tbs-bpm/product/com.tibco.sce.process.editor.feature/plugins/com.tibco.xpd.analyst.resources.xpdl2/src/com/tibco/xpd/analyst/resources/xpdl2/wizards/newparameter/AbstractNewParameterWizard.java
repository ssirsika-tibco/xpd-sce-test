/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.newparameter;

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
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Parameter wizard Abstracted from NewParameterWizard so that subclass can
 * override destination container according to the selected one.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractNewParameterWizard extends CreationWizard
        implements INewWizard, TemplateFactory {

    private AbstractSpecialFolderFileSelectionPage containerSelectionPage;

    private String[] pageTitles = { Messages.NewParameterWizard_PAGETITLE };

    private String[] pageDescriptions =
            { Messages.NewParameterWizard_PAGEMESSAGE };

    private IWorkbench workbench;

    /**
     * New Formal Parameter wizard
     */
    public AbstractNewParameterWizard() {
        containerSelectionPage = getFileSelectionPage();
        init(this, containerSelectionPage);
        setWindowTitle(Messages.NewParameterWizard_TITLE);
        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(Xpdl2ResourcesPlugin.getDefault()
                        .getImageRegistry()
                        .get(Xpdl2ResourcesConsts.WIZARD_NEW_FORMAL_PARAMETER)));
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
        if (cmd.canExecute()) {
            wc.getEditingDomain().getCommandStack().execute(cmd);

            // Select the new parameter in the Project Explore
            if (input != null) {
                selectAndReveal(input);
            }

            return true;
        } else {
            return false;
        }
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

        /*
         * XPD-708: Default to mandatory now (when used for message activities
         * input parameters are also required)
         * 
         * 
         * boolean defaultMandatoryValue = false; // Default process interface
         * formal parameter mandatory value to true if (getInputContainer()
         * instanceof ProcessInterface) { defaultMandatoryValue = true; } else
         * if (getInputContainer() instanceof Process) { defaultMandatoryValue =
         * false; }
         * 
         * FormalParameter parameter = (FormalParameter) input;
         * parameter.setRequired(defaultMandatoryValue);
         */
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

            cmd.append(AddCommand.create(wc.getEditingDomain(),
                    container,
                    Xpdl2Package.eINSTANCE
                            .getFormalParametersContainer_FormalParameters(),
                    input));

            cmd.setLabel(Messages.NewParameterWizard_AddParameter_menu);
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
        FormalParameter input = fact.createFormalParameter();
        input.setName(Messages.NewParameterWizard_3);
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewParameterWizard_3);
        // Set basic string type
        BasicType basicType = fact.createBasicType();
        basicType.setType(BasicTypeType.STRING_LITERAL);
        Length len = Xpdl2Factory.eINSTANCE.createLength();
        len.setValue("50"); //$NON-NLS-1$
        basicType.setLength(len);

        input.setDataType(basicType);

        // Default to INOUT
        input.setMode(ModeType.INOUT_LITERAL);

        /*
         * XPD-708: Default to mandatory now (when used for message activities
         * input parameters are also required)
         */
        input.setRequired(true);

        return input;
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
     * @see
     * com.tibco.xpd.ui.properties.XpdSection.XpdSectionContainer#getInputContainer
     * ()
     */
    @Override
    public EObject getInputContainer() {
        return getEContainer(containerSelectionPage);
    }

    private ModifyListener textModifyListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            if (e.getSource() instanceof Text) {
                if (getInputContainer() != null
                        && input instanceof FormalParameter) {
                    FormalParameter param = (FormalParameter) input;
                    String baseName =
                            Messages.NewParameterWizard_ParameterName_value;
                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil
                            .getDuplicateFieldOrParam(getInputContainer(),
                                    param,
                                    finalName) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }
                    param.setName(finalName);
                    Xpdl2ModelUtil.setOtherAttribute(param,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
                }
            }
        }
    };

    @Override
    public void createPageControls(Composite pageContainer) {
        // Create the page controls
        for (IWizardPage page : getPages()) {
            page.createControl(pageContainer);

            if (page == containerSelectionPage) {
                addExtraContainerPageListeners(containerSelectionPage);
            }
            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }
    }

    public ModifyListener getTextModifyListener() {
        return textModifyListener;
    }

}
