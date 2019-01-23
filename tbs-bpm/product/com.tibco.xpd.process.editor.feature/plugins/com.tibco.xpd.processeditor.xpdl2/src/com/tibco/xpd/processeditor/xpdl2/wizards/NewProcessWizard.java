package com.tibco.xpd.processeditor.xpdl2.wizards;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.CreationWizard;
import com.tibco.xpd.ui.properties.CreationWizard.TemplateFactory;
import com.tibco.xpd.ui.properties.SectionWizardPage;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * New Process wizard.
 * 
 * @author njpatel
 */
public abstract class NewProcessWizard extends CreationWizard implements
        INewWizard, TemplateFactory {

    private static final String DESTINATIONS_TAB_ID =
            "com.tibco.xpd.processeditor.propertyTabs.destinations"; //$NON-NLS-1$

    private final Logger LOG = XpdResourcesPlugin.getDefault().getLogger();

    private PackageSelectionPage spp;

    private String[] pageTitles =
            { Messages.NewProcessWizard_WizardTitle_label };

    private String[] pageDescriptions = {
            Messages.NewProcessWizard_WizardPageDesc_shortdesc,
            Messages.NewProcessWizard_WizardPageDesc_shortdesc };

    private IWorkbench workbench;

    /**
     * New Process wizard
     */
    public NewProcessWizard() {
        spp = new PackageSelectionPage();
        init(this, spp);
        setWindowTitle(getProcessWizardTitle());
        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(getProcessWizardIcon()));
    }

    protected abstract String getProcessWizardTitle();

    protected abstract Image getProcessWizardIcon();

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.CreationWizard#performFinish()
     */
    @Override
    public boolean performFinish() {

        /* Get the original command for creating the default new process. */
        Command createDefaultProcessCmd = getCommand();

        EObject container = spp.getEContainer();

        if (container != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);

            if (wc != null) {
                EditingDomain ed = wc.getEditingDomain();
                Process process = (Process) input;

                /*
                 * Create a command that will perform the creation of the
                 * default process AND THEN IF NECESSARY add extra elements for
                 * implementing selected interface OR from fragment template.
                 */
                Command addNewProcCommand =
                        createFinalAddProcessCommand(ed,
                                wc,
                                process,
                                createDefaultProcessCmd);

                if (addNewProcCommand.canExecute()) {
                    /*
                     * XPD-7247: We now allow new process to implement
                     * interfaces from other non-referenced projects. Hence if
                     * the interface resides in a non referenced project then on
                     * clicking finish the user will be shown a dialog to add
                     * project reference , if the user selects no when finish
                     * should not do anything.
                     */
                    if (wc.getAttributes()
                            .containsKey(AddExtrasToNewProcessCommand.TEMPLATEDATA)) {
                        Object templateElement =
                                wc.getAttributes()
                                        .get(AddExtrasToNewProcessCommand.TEMPLATEDATA);
                        /*
                         * not checking is the template element is instance of
                         * ProcessInterface, as the should work for any template
                         * EOject which is in non referenced project.
                         */
                        if (templateElement instanceof EObject) {
                            if (!ProcessUIUtil
                                    .checkAndAddProjectReference(getShell(),
                                            container,
                                            (EObject) templateElement)) {
                                return false;
                            }
                        }
                    }
                }

                ed.getCommandStack().execute(addNewProcCommand);

                /*
                 * XPD-5834: all wizards which use AddExtrasToNewProcessCommand
                 * command, are removing the fragment template data from wc in
                 * their respective wizard life cycle, except NewProcessWizard.
                 * 
                 * so changed NewProcessWizard.performFinish() to remove this
                 * template data in its wizard life cycle.
                 * 
                 * this will help in avoid getting empty process creation
                 * problems when multiple case classes are selected for
                 * generating business service (the problem in case of multi
                 * case class selection was - only one business service was
                 * having the activities and transitions and rest all were
                 * getting generated with no activities/transitions because they
                 * were being removed from the wc here!)
                 */
                if (null != wc
                        && wc.getAttributes()
                                .containsKey(AddExtrasToNewProcessCommand.TEMPLATEDATA)) {
                    wc.getAttributes()
                            .remove(AddExtrasToNewProcessCommand.TEMPLATEDATA);
                }

                // XPD-74
                appendAndExecuteAdditionalCommands(ed, wc, process);
                IConfigurationElement facConfig =
                        XpdResourcesUIActivator
                                .getEditorFactoryConfigFor(process);

                if (facConfig != null) {
                    String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();
                    try {
                        EditorInputFactory f =
                                (EditorInputFactory) facConfig
                                        .createExecutableExtension("factory"); //$NON-NLS-1$
                        IEditorInput input = f.getEditorInputFor(process);
                        page.openEditor(input, editorID);

                        // Select the new process in the Project Explorer
                        if (process != null) {
                            selectAndReveal(process);
                        }

                    } catch (PartInitException e) {
                        LOG.error(e.getMessage());
                    } catch (CoreException e) {
                        e.printStackTrace();
                        return false;
                    }

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param ed
     * @param wc
     * @param process
     */
    protected void appendAndExecuteAdditionalCommands(EditingDomain ed,
            WorkingCopy wc, Process process) {
        // do nothing
    }

    /**
     * One last chance to Add the processes and finalise it.
     * 
     * @param ed
     * @param process
     * @return
     */
    protected Command createFinalAddProcessCommand(EditingDomain ed,
            WorkingCopy containerWorkingCopy, Process process,
            Command createDefaultProcessCmd) {
        return new AddExtrasToNewProcessCommand(ed, containerWorkingCopy,
                process, createDefaultProcessCmd);
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
        rememberSelectionForInterface(selection);
    }

    /*
	 * 
	 */
    private void rememberSelectionForInterface(IStructuredSelection selection) {
        if (selection != null
                && selection.getFirstElement() instanceof ProcessInterface) {
            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopyFor(spp.getEContainer());
            wc.getAttributes().put("PROCESS_INTERFACE_SELECTION", //$NON-NLS-1$
                    selection.getFirstElement());
        }
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
                CompoundCommand cmd = new CompoundCommand();

                /*
                 * PropertySection contribution filter needs to be able to
                 * distinguish between new decision flow process for decision
                 * flow file and new business process for normal xpdl.
                 * 
                 * The filter usually works on whether the decision flow process
                 * is in a xpdl model but new process are in the model yet!.
                 * 
                 * So we set a known predefined ID on the business process here
                 * so that the filter can detect that and filter in processes
                 * with this ID..
                 * 
                 * Then just before we create the command we will set it to
                 * something unique again.
                 */
                ((Process) input).eSet(Xpdl2Package.eINSTANCE
                        .getUniqueIdElement_Id(), EcoreUtil.generateUUID());

                cmd.append(AddCommand.create(workingCopy.getEditingDomain(),
                        workingCopy.getRootElement(),
                        Xpdl2Package.eINSTANCE.getPackage_Processes(),
                        input));
                cmd.setLabel(Messages.NewProcessWizard_AddProcessCmd_label);
                Pool pool =
                        ElementsFactory
                                .createPool(Messages.ProcessPropertySection_DefaultPool_value,
                                        ((Process) input).getId());

                Lane lane = Xpdl2Factory.eINSTANCE.createLane();
                lane.setName(Messages.ProcessPropertySection_DefaultLane_label);
                Xpdl2ModelUtil.setOtherAttribute(lane,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        Messages.ProcessPropertySection_DefaultLane_label);

                NodeGraphicsInfo gNode =
                        Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);
                gNode.setHeight(300);

                pool.getLanes().add(lane);

                cmd.append(AddCommand.create(workingCopy.getEditingDomain(),
                        workingCopy.getRootElement(),
                        Xpdl2Package.eINSTANCE.getPackage_Pools(),
                        pool));
                return cmd;
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    private ModifyListener packageFileNameListener = new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {

            if (e.getSource() instanceof Text) {

                if (getInputContainer() != null && input instanceof Process) {

                    Process process = (Process) input;
                    String baseName =
                            ((Package) getInputContainer()).getName()
                                    + "-" + Messages.PackageTemplateSelectionPage_ProcessDefault_value; //$NON-NLS-1$

                    String finalName = baseName;
                    int idx = 1;
                    while (Xpdl2ModelUtil
                            .getDuplicateProcess((Package) getInputContainer(),
                                    process,
                                    finalName) != null) {
                        idx++;
                        finalName = baseName + idx;
                    }

                    Xpdl2ModelUtil.setOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
                    process.setName(NameUtil.getInternalName(finalName, false));
                    /*
                     * XPD-6766: Calling setInput on wizard pages so that the
                     * process will be created with the default selected
                     * fragment data when the xpdl/process package selection
                     * changes (clicking on back button from new process wizard
                     * or File->New->Other process type). SectionWizardPage
                     * makes use of property sections. See
                     * ProcessPropertySection setInput().
                     */
                    for (IWizardPage page : getPages()) {

                        if (page instanceof SectionWizardPage) {

                            ((SectionWizardPage) page).setInput(process);
                            /*
                             * If we don't set refresh to true the new process's
                             * label and name after xpdl selection change will
                             * not be reflected in the wizard
                             */
                            ((SectionWizardPage) page)
                                    .setRefreshOnSetVisible(true);
                        }
                    }
                }
            }
        }
    };

    @Override
    public void createPageControls(Composite pageContainer) {
        // Create the page controls
        for (IWizardPage page : getPages()) {

            setDestinationsTabAutoRefresh(page);

            page.createControl(pageContainer);

            if (page == spp) {
                spp.addPacakgeFileModifyListeners(packageFileNameListener);
            }
            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }

    }

    /**
     * @param page
     */
    private void setDestinationsTabAutoRefresh(IWizardPage page) {
        if (page instanceof SectionWizardPage) {
            SectionWizardPage wizPage = (SectionWizardPage) page;

            if (wizPage.getTabDescriptor() instanceof ITabDescriptor) {
                ITabDescriptor tab =
                        (ITabDescriptor) wizPage.getTabDescriptor();

                if (DESTINATIONS_TAB_ID.equals(tab.getId())) {
                    wizPage.setRefreshOnSetVisible(true);
                }
            }
        }
    }

    @Override
    public abstract EObject createTemplate();

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

}
