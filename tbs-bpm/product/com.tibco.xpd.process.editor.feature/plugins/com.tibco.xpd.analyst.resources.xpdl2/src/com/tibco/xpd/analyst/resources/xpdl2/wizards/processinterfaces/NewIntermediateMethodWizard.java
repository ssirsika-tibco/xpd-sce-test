/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.ProcessElementSelectionPage;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.CreationWizard;
import com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Intermediate Event Wizard
 * 
 * @author rsomayaj
 * 
 */
public class NewIntermediateMethodWizard extends CreationWizard implements
        INewWizard, TemplateFactory {

    private static final String WIZARD_NEW_INTERMEDIATE_METHOD =
            "icons/wizards/InterfaceInterEventWizard.png"; //$NON-NLS-1$

    private ProcessElementSelectionPage containerSelectionPage;

    private String[] pageTitles =
            { Messages.NewIntermediateMethodWizard_Page_title };

    private String[] pageDescriptions =
            { Messages.NewIntermediateMethodWizard_Page_desc };

    private IWorkbench workbench;

    private final static String DEFAULT_INTERMEDIATE_NAME =
            Messages.NewIntermediateMethodWizard_DefaultName_value;

    /**
     * New Formal Parameter wizard
     */
    public NewIntermediateMethodWizard() {
        containerSelectionPage = new ProcessElementSelectionPage(false, true);
        init(this, containerSelectionPage);
        setWindowTitle(Messages.NewIntermediateMethodWizard_Window_title);
        setDefaultPageImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(WIZARD_NEW_INTERMEDIATE_METHOD));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#performFinish()
     */
    public boolean performFinish() {
        EObject container = containerSelectionPage.getEContainer();
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
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        containerSelectionPage.init(selection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getPageDescription(int)
     */
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
    public Command createCommand(EObject input, IWizardPage locationPage) {
        ProcessElementSelectionPage spp =
                (ProcessElementSelectionPage) locationPage;
        EObject container = spp.getEContainer();
        if (container != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            CompoundCommand cmd = new CompoundCommand();

            cmd.append(AddCommand.create(wc.getEditingDomain(),
                    container,
                    XpdExtensionPackage.eINSTANCE
                            .getProcessInterface_IntermediateMethods(),
                    input));

            cmd
                    .setLabel(Messages.NewIntermediateMethodWizard_AddIntermediateMethodCmd_shortmsg);
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
    public EObject createTemplate() {
        IntermediateMethod intermediateMethod =
                XpdExtensionFactory.eINSTANCE.createIntermediateMethod();
        intermediateMethod.setName(NameUtil
                .getInternalName(DEFAULT_INTERMEDIATE_NAME, false));
        Xpdl2ModelUtil.setOtherAttribute(intermediateMethod,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                DEFAULT_INTERMEDIATE_NAME);
        intermediateMethod.setVisibility(Visibility.PUBLIC);
        return intermediateMethod;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#getWorkbench()
     */
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
    public EObject getInputContainer() {
        return containerSelectionPage.getEContainer();
    }

    private ModifyListener procOrIfcNameListener = new ModifyListener() {

        public void modifyText(ModifyEvent e) {
            if (e.getSource() instanceof Text) {
                if (getInputContainer() != null
                        && input instanceof IntermediateMethod) {
                    IntermediateMethod param = (IntermediateMethod) input;
                    String baseName =
                            Messages.NewIntermediateMethodWizard_IntermediateMethodName_value;
                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil
                            .getDuplicateIntermediateMethod(getInputContainer(),
                                    param,
                                    finalName) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }
                    param.setName(NameUtil.getInternalName(finalName, false));
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
                containerSelectionPage
                        .addProcessOrInterfaceModifyListeners(procOrIfcNameListener);
            }
            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }
    }

}
