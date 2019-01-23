/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.CreationWizard;
import com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdInterfaceType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 * 
 */
public class NewProcessInterfaceWizard extends CreationWizard implements
        INewWizard, TemplateFactory {
    private static final String WIZARD_NEW_PROCESS_INTERFACE =
            "icons/wizards/ProcessInterfaceWizard.png"; //$NON-NLS-1$

    private static final String DEFAULT_START_METHOD_NAME =
            Messages.NewProcessInterfaceWizard_DefaultStartMethod_title;

    private PackageSelectionPage spp;

    private String[] pageTitles =
            { Messages.NewProcessInterfaceWizard_Page_title };

    private String[] pageDescriptions = {
            Messages.NewProcessInterfaceWizard_Page_desc,
            Messages.NewProcessInterfaceWizard_Page_desc };

    private IWorkbench workbench;

    public NewProcessInterfaceWizard() {
        spp = new PackageSelectionPage();
        init(this, spp);
        setWindowTitle(Messages.NewProcessInterfaceWizard_Dialog_title);
        setDefaultPageImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(WIZARD_NEW_PROCESS_INTERFACE));

    }

    @Override
    public boolean performFinish() {
        Command cmd = getCommand();
        EObject container = spp.getEContainer();

        if (container != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            if (wc != null) {
                EditingDomain ed = wc.getEditingDomain();

                ed.getCommandStack().execute(cmd);
                ProcessInterface processInterface = (ProcessInterface) input;
                IConfigurationElement facConfig =
                        XpdResourcesUIActivator
                                .getEditorFactoryConfigFor(processInterface);
                if (facConfig != null) {
                    String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();
                    try {
                        EditorInputFactory f =
                                (EditorInputFactory) facConfig
                                        .createExecutableExtension("factory"); //$NON-NLS-1$
                        IEditorInput input =
                                f.getEditorInputFor(processInterface);
                        page.openEditor(input, editorID);

                        // Select the new process in the Project Explorer
                        selectAndReveal(processInterface);

                    } catch (PartInitException e) {
                        // ignore
                    } catch (CoreException e) {
                        e.printStackTrace();
                        return false;
                    }

                    return true;
                }
            }
        }

        return true;
    }

    private void addDefaultStartMethod(ProcessInterface processInterface) {
        StartMethod startMethod =
                XpdExtensionFactory.eINSTANCE.createStartMethod();
        startMethod.setName(NameUtil.getInternalName(DEFAULT_START_METHOD_NAME,
                false));
        Xpdl2ModelUtil.setOtherAttribute(startMethod,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                DEFAULT_START_METHOD_NAME);
        startMethod.setVisibility(Visibility.PUBLIC);
        processInterface.getStartMethods().add(startMethod);
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
        spp.init(selection);
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
        PackageSelectionPage spp = (PackageSelectionPage) locationPage;
        EObject container = spp.getEContainer();

        if (container != null) {
            WorkingCopy workingCopy =
                    WorkingCopyUtil.getWorkingCopyFor(container);

            if (workingCopy != null) {
                CompoundCommand cmd = new CompoundCommand() {
                    @Override
                    public boolean canExecute() {
                        return true;
                    }
                };
                addDefaultStartMethod((ProcessInterface) input);
                ProcessInterfaces obj =
                        (ProcessInterfaces) ((com.tibco.xpd.xpdl2.Package) getInputContainer())
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ProcessInterfaces()
                                        .getName());
                if (obj == null) {
                    obj =
                            XpdExtensionFactory.eINSTANCE
                                    .createProcessInterfaces();
                    cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(workingCopy
                            .getEditingDomain(),
                            (com.tibco.xpd.xpdl2.Package) workingCopy
                                    .getRootElement(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessInterfaces(),
                            obj));
                }

                cmd.append(AddCommand.create(workingCopy.getEditingDomain(),
                        obj,
                        XpdExtensionPackage.eINSTANCE
                                .getProcessInterfaces_ProcessInterface(),
                        input));
                cmd.setLabel(Messages.NewProcessInterfaceWizard_AddProcessInterfaceCmd_label);
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
        ProcessInterface processInterface =
                XpdExtensionFactory.eINSTANCE.createProcessInterface();
        processInterface
                .setName(NameUtil
                        .getInternalName(Messages.NewProcessInterfaceWizard_DefaultName_value,
                                false));
        Xpdl2ModelUtil.setOtherAttribute(processInterface,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewProcessInterfaceWizard_DefaultName_value);
        /* Set the interface type to ProcessInterface enum type */
        processInterface
                .setXpdInterfaceType(XpdInterfaceType.PROCESS_INTERFACE);
        return processInterface;
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
        return spp.getEContainer();
    }

    private ModifyListener packageFileNameListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
            if (e.getSource() instanceof Text) {
                if (getInputContainer() != null
                        && input instanceof ProcessInterface) {
                    ProcessInterface processInterface =
                            (ProcessInterface) input;
                    String baseName =
                            ((Package) getInputContainer()).getName()
                                    + "-" + Messages.NewProcessInterfaceWizard_Interface_default_label; //$NON-NLS-1$
                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil
                            .getDuplicateDisplayProcessInterface(getInputContainer(),
                                    processInterface,
                                    finalName) != null
                            || Xpdl2ModelUtil
                                    .getDuplicateProcessInterface(getInputContainer(),
                                            processInterface,
                                            NameUtil.getInternalName(finalName,
                                                    false)) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }
                    processInterface.setName(NameUtil
                            .getInternalName(finalName, false));
                    Xpdl2ModelUtil.setOtherAttribute(processInterface,
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

            if (page == spp) {
                spp.addPacakgeFileModifyListeners(packageFileNameListener);
            }
            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }
    }
}