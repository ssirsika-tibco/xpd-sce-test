/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.ProjectSelectPage;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplate;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.wizards.BasicNewBpmResourceWizard;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.resources.ProcessPackageAddedNotificationCommand;

/**
 * New Package wizard
 * 
 * @author njpatel
 */
public class NewPackageWizard extends BasicNewBpmResourceWizard {

    private static final String PROCESS_EDITOR_ID =
            "com.tibco.xpd.processeditor.xpdl2.editor"; //$NON-NLS-1$

    /*
     * / This caches an instance of the model factory. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    protected Xpdl2Factory xpdlFactoriy = Xpdl2Factory.eINSTANCE;

    // Wizard pages
    private ProjectSelectPage projectPage;

    private PackageInformationPage infoPage;

    private PackageTemplateSelectionPage templateSelectionPage;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private IWorkbench workbench;

    // Set to true if this wizard is called from an RCP application
    private boolean isRCP = false;

    // // This will contain the package information that the wizard
    // // pages can use to update it's content. This information
    // // will also be used by the template at the end to create
    // // a package
    // private Map<String, String> input = new HashMap<String, String>();

    /**
     * New Package wizard
     */
    public NewPackageWizard() {
        super();
        setNeedsProgressMonitor(true);

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
        super.init(workbench, selection);

        setWindowTitle(Messages.NewPackageWizard_TITLE);

        setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
                .getImageDescriptor(Xpdl2ResourcesPlugin.getDefault()
                        .getImageRegistry()
                        .get(Xpdl2ResourcesConsts.WIZARD_NEW_PACKAGE)));

    }

    /**
     * Do the work after everything is specified. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return
     * @generated
     */
    @Override
    public boolean performFinish() {
        /*
         * If the package is going to be created in a subfolder within a
         * packages folder that does not exist then we need to create it
         */
        if (projectPage.createPackageFolder()) {

            if (projectPage.getPackageObject() == null) {
                projectPage.createPackage();
            }

            final String packageFileName =
                    projectPage.getPackagesFolderContainer().getFullPath()
                            + "/" + projectPage.getPackageFileName(); //$NON-NLS-1$

            if (projectPage.getPackageObject() != null) {
                final Object[] objToReveal = new Object[] { null };
                final ProcessEditorInput[] editorInput =
                        new ProcessEditorInput[] { null };

                try {
                    final IFolder folder =
                            (IFolder) projectPage.getPackagesFolderContainer();

                    if (templateSelectionPage.getTemplateElements() != null) {
                        /*
                         * XPD-7247: We now allow new process to implement
                         * interfaces from other non-referenced projects. Hence
                         * if the interface resides in a non referenced project
                         * then on clicking finish the user will be shown a
                         * dialog to add project reference , if the user selects
                         * no when finish should not do anything.
                         */
                        Object templateElement =
                                templateSelectionPage.getTemplateElements();

                        /*
                         * not checking is the template element is instance of
                         * ProcessInterface, as the should work for any template
                         * EOject which is in non referenced project.
                         */
                        if (folder.getProject() != null
                                && (templateElement instanceof EObject)
                                && WorkingCopyUtil
                                        .getProjectFor((EObject) templateElement) != null) {

                            if (!ProcessUIUtil
                                    .checkAndAddProjectReference(getShell(),
                                            folder.getProject(),
                                            WorkingCopyUtil
                                                    .getProjectFor((EObject) templateElement))) {
                                return false;
                            }
                        }
                    }

                    getContainer().run(true,
                            false,
                            new WorkspaceModifyOperation() {

                                @Override
                                protected void execute(IProgressMonitor monitor)
                                        throws CoreException,
                                        InvocationTargetException,
                                        InterruptedException {
                                    Process process = null;

                                    monitor.beginTask(String
                                            .format(Messages.NewPackageWizard_creatingPackage_progress_message,
                                                    projectPage
                                                            .getPackageFileName()),
                                            5);

                                    final Package xpdl2Package =
                                            projectPage.getPackageObject();

                                    /*
                                     * Accessing UI components on the wizard
                                     * page
                                     */
                                    getShell().getDisplay()
                                            .syncExec(new Runnable() {
                                                @Override
                                                public void run() {
                                                    infoPage.updatePackageObject(xpdl2Package);
                                                }
                                            });
                                    monitor.worked(1);
                                    if (templateSelectionPage.getProcess() != null) {
                                        process =
                                                templateSelectionPage
                                                        .getProcess();

                                        xpdl2Package.getProcesses()
                                                .add(process);
                                        if (templateSelectionPage
                                                .getTemplateElements() != null) {
                                            templateSelectionPage
                                                    .addTemplateElements(xpdl2Package,
                                                            process);

                                        } else {
                                            templateSelectionPage
                                                    .initializeProcess(process);
                                        }
                                    }
                                    /*
                                     * add project destination to all the
                                     * processes
                                     * 
                                     * Sid XPD-4165. Should not automatically
                                     * add project destinations into new
                                     * processes in Studio for Analysts because
                                     * 
                                     * (a) destinations are not exposed to user
                                     * in Studio for Analysts and
                                     * 
                                     * (b) running a simulation in studio for
                                     * analysts forces the simulation
                                     * destination on in the project and thus if
                                     * we inherited project destinations then
                                     * all future packages created would stat to
                                     * have simulation on automatically and we
                                     * want the user to have to explicitly
                                     * enable simual;tion for each process.
                                     */
                                    if (!XpdResourcesPlugin.isRCP()) {
                                        for (Process proc : xpdl2Package
                                                .getProcesses()) {
                                            addProjectDestinations(proc);
                                        }
                                    }

                                    monitor.worked(1);
                                    URI uri =
                                            URI.createPlatformResourceURI(packageFileName,
                                                    true);
                                    IFile file =
                                            folder.getFile(projectPage
                                                    .getPackageFileName());

                                    if (templateSelectionPage
                                            .getTemplateElements() instanceof PackageTemplate) {
                                        PackageTemplate pkgTemplate =
                                                (PackageTemplate) templateSelectionPage
                                                        .getTemplateElements();
                                        IProject project =
                                                projectPage
                                                        .getPackagesFolderContainer()
                                                        .getProject();
                                        pkgTemplate.fixFinalPackage(project,
                                                xpdl2Package,
                                                file);
                                    }
                                    monitor.worked(1);

                                    ResourceSet rset = new ResourceSetImpl();
                                    Resource resource =
                                            rset.createResource(uri);

                                    DocumentRoot docRoot =
                                            Xpdl2Factory.eINSTANCE
                                                    .createDocumentRoot();

                                    docRoot.setPackage(xpdl2Package);
                                    resource.getContents().add(docRoot);

                                    monitor.worked(1);

                                    ByteArrayOutputStream os =
                                            new ByteArrayOutputStream();
                                    try {
                                        resource.save(os, null);
                                    } catch (IOException e) {
                                        throw new InvocationTargetException(e);
                                    }
                                    file.create(new ByteArrayInputStream(os
                                            .toByteArray()),
                                            true,
                                            new NullProgressMonitor());

                                    WorkingCopy wc =
                                            XpdResourcesPlugin.getDefault()
                                                    .getWorkingCopy(file);

                                    Package pck = (Package) wc.getRootElement();

                                    /*
                                     * make a dummy edit on the file so that
                                     * pre-commit listeners at least receive
                                     * some kind of notification of a created
                                     * package
                                     */
                                    if (pck != null) {
                                        ProcessPackageAddedNotificationCommand packageAdded =
                                                new ProcessPackageAddedNotificationCommand(
                                                        wc.getEditingDomain(),
                                                        pck);
                                        wc.getEditingDomain().getCommandStack()
                                                .execute(packageAdded);
                                        try {
                                            wc.save();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    monitor.worked(1);
                                    if (pck.getProcesses().isEmpty()) {
                                        objToReveal[0] = pck;
                                    } else {
                                        EObject procToOpenName =
                                                EMFSearchUtil.findInList(pck
                                                        .getProcesses(),
                                                        Xpdl2Package.eINSTANCE
                                                                .getNamedElement_Name(),
                                                        templateSelectionPage
                                                                .getProcessToOpen());
                                        Process p = null;
                                        if (procToOpenName instanceof Process) {
                                            p = (Process) procToOpenName;

                                        } else if (p == null) {
                                            p = pck.getProcesses().get(0);
                                        }

                                        editorInput[0] =
                                                new ProcessEditorInput(wc, p);
                                        objToReveal[0] = p;
                                    }
                                    monitor.done();
                                }
                            });
                } catch (InvocationTargetException e) {
                    ErrorDialog
                            .openError(getShell(),
                                    Messages.NewPackageWizard_errorCreatingPackage_errorDlg_title,
                                    Messages.NewPackageWizard_errorCreatingPackage_errorDlg_message,
                                    e.getCause() instanceof CoreException ? ((CoreException) e
                                            .getCause()).getStatus()
                                            : new Status(
                                                    IStatus.ERROR,
                                                    Xpdl2ProcessEditorPlugin.ID,
                                                    e.getLocalizedMessage(), e
                                                            .getCause()));
                    LOG.error(e.getCause() != null ? e.getCause() : e,
                            Messages.NewPackageWizard_exceptionDuringCreation_error_shortdesc);
                } catch (InterruptedException e) {
                    LOG.error(e);
                }

                if (objToReveal[0] != null) {
                    selectAndReveal(objToReveal[0]);
                }
                if (editorInput[0] != null) {
                    try {
                        IDE.openEditor(workbench.getActiveWorkbenchWindow()
                                .getActivePage(),
                                editorInput[0],
                                PROCESS_EDITOR_ID);
                    } catch (PartInitException e) {
                        LOG.error(e,
                                String.format(Messages.NewPackageWizard_cannotOpenEditor_error_shortdesc,
                                        editorInput[0].getProcess().getName()));
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * @param process
     */
    private void addProjectDestinations(Process process) {
        if (null != process && null != projectPage.getPackagesFolderContainer()) {
            IProject project =
                    projectPage.getPackagesFolderContainer().getProject();
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (null != config) {
                if (null != config.getProjectDetails()) {
                    Destinations globalDestinations =
                            config.getProjectDetails().getGlobalDestinations();
                    if (null != globalDestinations) {
                        EList<Destination> destinations =
                                globalDestinations.getDestination();
                        if (destinations.size() > 0) {
                            for (Destination destination : destinations) {
                                String type = destination.getType();

                                if (type != null) {
                                    ExtendedAttribute att =
                                            Xpdl2Factory.eINSTANCE
                                                    .createExtendedAttribute();
                                    att.setName(DestinationUtil.DESTINATION_ATTRIBUTE);
                                    att.setValue(type);
                                    process.getExtendedAttributes().add(att);
                                }
                            }
                        } else {
                            String destinationFromPreferences =
                                    UserInfoUtil.getProjectPreferences(project)
                                            .getDestination();
                            if (null != destinationFromPreferences
                                    && destinationFromPreferences.length() > 0) {
                                ExtendedAttribute att =
                                        Xpdl2Factory.eINSTANCE
                                                .createExtendedAttribute();
                                att.setName(DestinationUtil.DESTINATION_ATTRIBUTE);
                                att.setValue(destinationFromPreferences);
                                process.getExtendedAttributes().add(att);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Set whether this wizard is being called from an RCP application.
     * <p>
     * <strong>NOTE: THIS IS FOR INTERNAL USE ONLY.</strong>
     * </p>
     * 
     * @param isRCP
     * @since 3.5
     */
    public void setIsRCP(boolean isRCP) {
        this.isRCP = isRCP;
    }

    @Override
    public IWizardPage getStartingPage() {
        IWizardPage page = super.getStartingPage();

        // Don't show file selection page if in RCP
        if (isRCP && page == projectPage) {
            page = getNextPage(page);
        }

        return page;
    }

    @Override
    public IWizardPage getPreviousPage(IWizardPage page) {
        IWizardPage previousPage = super.getPreviousPage(page);

        // Don't show file selection page if in RCP
        if (isRCP && previousPage == projectPage) {
            previousPage = null;
        }

        return previousPage;
    }

    /**
     * The framework calls this to create the contents of the wizard. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     */
    @Override
    public void addPages() {
        // Project and package file selection page
        projectPage = new ProjectSelectPage();
        projectPage.init(getSelection());
        addPage(projectPage);

        // Package information page
        infoPage = new PackageInformationPage();
        addPage(infoPage);

        // Template selection page

        templateSelectionPage = new PackageTemplateSelectionPage();
        addPage(templateSelectionPage);
    }

}
