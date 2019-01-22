/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.wizards.pageflowgen.CaseDataProcessGenerationWizard;
import com.tibco.xpd.implementer.resources.xpdl2.wizards.pageflowgen.CaseDataGenerationWizardPage;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.ProcessPackageAddedNotificationCommand;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Abstract class that provides common functionality for all case class specific
 * process generation
 * 
 * @author bharge
 * @since 23 Jan 2014
 */
public abstract class AbstractCaseDataProcessGeneratorAction implements
        IObjectActionDelegate {

    private Shell shell;

    protected List<Class> caseClassSelectionList;

    /**
     * contribution id of the fragment for business service fragments
     */
    protected static String BUSINESS_SERVICE_FRAGMENT_ROOT_CATEGORY_ID =
            "com.tibco.xpd.processeditor.fragments.businessservice"; //$NON-NLS-1$

    /**
     * contribution id of the fragment for case action fragments
     */
    protected static String CASE_SERVICE_FRAGMENT_ROOT_CATEGORY_ID =
            "com.tibco.xpd.processeditor.fragments.caseservice"; //$NON-NLS-1$

    /**
     * contribution id of the fragment for service process fragments
     */
    protected static String SERVICE_PROCESS_FRAGMENT_ROOT_CATEGORY_ID =
            "com.tibco.xpd.processeditor.fragments.serviceprocess"; //$NON-NLS-1$

    /* package */static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    @Override
    public void run(IAction action) {

        if (!caseClassSelectionList.isEmpty()) {

            List<IProject> allBPMProcessProjects = getBPMProcessProjects();
            /*
             * if there is only one project in the workspace which is a bom only
             * project then ask the user to import/create business process
             * project before generating the business/case service
             */
            String dialogTitle = null;
            String messageText = null;
            ProcessWidgetType processWidgetType = getProcessWidgetType();

            if (ProcessWidgetType.CASE_SERVICE.equals(processWidgetType)) {

                dialogTitle = Messages.GenerateCaseService_title;
                messageText = Messages.GenerateCaseAction_NoBPMProject_msg;

            } else if (ProcessWidgetType.BUSINESS_SERVICE
                    .equals(processWidgetType)) {

                dialogTitle =
                        Messages.GenerateCaseClassPageflow_NoBPMProject_title;
                messageText =
                        Messages.GenerateCaseClassPageflow_NoBPMProject_msg;

            } else if (ProcessWidgetType.SERVICE_PROCESS
                    .equals(processWidgetType)) {

                dialogTitle = Messages.GenerateServiceProcess_title;
                messageText = Messages.GenerateServiceProcess_NoBPMProject_msg;
            }
            if (allBPMProcessProjects.isEmpty()) {
                /*
                 * show dialog asking user to import/create a bpm process
                 * project before generating the business/case service
                 */
                MessageDialog.openInformation(shell, dialogTitle, messageText);
                return;
            }

            final CaseDataProcessGenerationWizard pageflowGenWizard =
                    new CaseDataProcessGenerationWizard(allBPMProcessProjects,
                            caseClassSelectionList, processWidgetType,
                            getWizardPageTitle());
            WizardDialog wizardDialog =
                    new WizardDialog(PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getShell(),
                            pageflowGenWizard);
            wizardDialog.open();
            if (wizardDialog.getReturnCode() == Dialog.OK) {

                /*
                 * XPD-5893: Show progress while generating business services.
                 */

                final String jobDesc = dialogTitle;

                WorkspaceJob wsJob = new WorkspaceJob(jobDesc) {

                    @Override
                    public IStatus runInWorkspace(IProgressMonitor monitor)
                            throws CoreException {

                        try {

                            monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);

                            Package newOrExistingXpdlPkg =
                                    getOrCreateXpdlPackage(pageflowGenWizard);

                            CompoundCommand cmdToCreateProcess =
                                    cmdThatCanOrCannotBeUndoable(pageflowGenWizard);

                            final List<Process> processList =
                                    generateNewProcesses(newOrExistingXpdlPkg,
                                            cmdToCreateProcess);

                            if (processList != null && !processList.isEmpty()) {

                                final Process lastProcess =
                                        processList.get(processList.size() - 1);
                                /*
                                 * select the last process in the explorer and
                                 * show it in the diagram editor (if we open all
                                 * the generated processes then it may close
                                 * some of the existing open editors without
                                 * warning if they aren't dirty as the studio
                                 * only supports limited number of open editors
                                 * at a time.
                                 */
                                UIJob job =
                                        new UIJob(PlatformUI.getWorkbench()
                                                .getDisplay(), jobDesc) {

                                            @Override
                                            public IStatus runInUIThread(
                                                    IProgressMonitor monitor) {

                                                try {

                                                    int totalWork =
                                                            processList.size();
                                                    monitor.beginTask(jobDesc,
                                                            totalWork);
                                                    selectAndReveal(lastProcess);
                                                    return Status.OK_STATUS;
                                                } finally {

                                                    monitor.done();
                                                }
                                            }
                                        };
                                job.setUser(true);
                                job.schedule();
                            }
                            return Status.OK_STATUS;
                        } finally {

                            monitor.done();
                        }
                    }
                };

                wsJob.setUser(true);
                wsJob.schedule();
            }
        }
    }

    /**
     * Generate the new business/case service processes to update, delete,
     * create case data.
     * 
     * @param newOrExistingXpdlPkg
     *            container package.
     * @param cmdToCreateProcess
     *            command to create processes.
     * @return a set of newly created XPDL processes.
     */
    protected abstract List<Process> generateNewProcesses(
            Package newOrExistingXpdlPkg, CompoundCommand cmdToCreateProcess);

    /**
     * If the user chooses to generate a business/case service for a case class
     * in a new xpdl package, then undo must not be allowed (because we dont
     * prefer to delete the newly created xpdl file). So this method helps
     * creating a undoable compound command
     * 
     * @param pageflowGenWizard
     * @return CompoundCommand - undoable CompoundCommand if the xpdl package is
     *         new, normal CompoundCommand otherwise
     */
    protected CompoundCommand cmdThatCanOrCannotBeUndoable(
            CaseDataProcessGenerationWizard pageflowGenWizard) {

        CaseDataGenerationWizardPage pageflowWizardPage =
                pageflowGenWizard.getPageflowWizardPage();
        /*
         * If the user chooses to generate a business service for a case class
         * in a new xpdl package, then undo must not be allowed (because we dont
         * prefer to delete the newly added xpdl file). So create a undoable
         * compound command
         */
        if (pageflowWizardPage.isCreateNewPackage()) {

            return new UndoableProcessPackageFileCommand();
        }
        /*
         * If the user chooses to generate a business service for a case class
         * in an existing xpdl package, then undo must be able to remove the
         * newly added process so we create a normal compound command that is
         * undoable
         */
        return new CompoundCommand();
    }

    /**
     * @return list of bpm process projects
     */
    public List<IProject> getBPMProcessProjects() {

        List<IProject> bpmProcessProjects = new ArrayList<IProject>();
        IProject[] allStudioProjects = ProjectUtil.getAllStudioProjects();

        for (IProject iProject : allStudioProjects) {

            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(iProject);
            List<IFolder> xpdlFolders =
                    projectConfig
                            .getSpecialFolders()
                            .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
            if (!xpdlFolders.isEmpty()) {

                bpmProcessProjects.add(iProject);
            }
        }

        return bpmProcessProjects;
    }

    /**
     * @param newProcess
     */
    public void selectAndReveal(Process newProcess) {

        IConfigurationElement facConfig =
                XpdResourcesUIActivator.getEditorFactoryConfigFor(newProcess);

        if (facConfig != null) {

            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            try {

                EditorInputFactory f =
                        (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$
                IEditorInput input = f.getEditorInputFor(newProcess);
                page.openEditor(input, editorID);

                /** Select the new process in the Project Explorer */
                if (newProcess != null) {

                    IWorkbench workbench = PlatformUI.getWorkbench();

                    if (workbench != null) {

                        BasicNewXpdResourceWizard.selectAndReveal(newProcess,
                                workbench.getActiveWorkbenchWindow());
                    }
                }

            } catch (PartInitException e) {

                LOG.error(e.getMessage());
            } catch (CoreException e) {

                e.printStackTrace();
            }

        }

    }

    /**
     * creates a new package if it doesn't already exist
     * 
     * @param pageflowGenWizard
     * @return xpdl2 Package
     */
    protected Package getOrCreateXpdlPackage(
            CaseDataProcessGenerationWizard pageflowGenWizard) {

        Package newOrExistingXpdlPkg = null;

        CaseDataGenerationWizardPage pageflowWizardPage =
                pageflowGenWizard.getPageflowWizardPage();
        if (pageflowWizardPage.isCreateNewPackage()) {

            /* create a new xpdl file and new package */
            newOrExistingXpdlPkg =
                    createNewPackage(pageflowGenWizard, pageflowWizardPage);
        } else {

            /* get existing package */
            Xpdl2WorkingCopyImpl xpdl2WorkingCopy =
                    (Xpdl2WorkingCopyImpl) getWorkingCopy(pageflowWizardPage
                            .getExistingXpdlFile());
            EObject rootElement = xpdl2WorkingCopy.getRootElement();
            if (rootElement instanceof Package) {

                newOrExistingXpdlPkg = (Package) rootElement;
            }
        }
        return newOrExistingXpdlPkg;
    }

    /**
     * @param pageflowGenWizard
     * @param pageflowWizardPage
     * @return
     */
    private Package createNewPackage(
            CaseDataProcessGenerationWizard pageflowGenWizard,
            CaseDataGenerationWizardPage pageflowWizardPage) {

        IProject project = pageflowWizardPage.getProject();

        Package xpdl2Package =
                GenerateProcessUtil.createPackage(pageflowWizardPage
                        .getFileName(), project);
        IFolder folder = pageflowWizardPage.getProcessPackageFolder();

        String pathName = folder.getFullPath() + "/" //$NON-NLS-1$
                + pageflowWizardPage.getFileName();
        URI uri = URI.createPlatformResourceURI(pathName, true);
        IFile file = folder.getFile(pageflowWizardPage.getFileName());

        ResourceSet rset = new ResourceSetImpl();
        Resource resource = rset.createResource(uri);

        DocumentRoot docRoot = Xpdl2Factory.eINSTANCE.createDocumentRoot();

        docRoot.setPackage(xpdl2Package);
        resource.getContents().add(docRoot);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {

            resource.save(os, null);
        } catch (IOException e) {
            try {

                throw new InvocationTargetException(e);
            } catch (InvocationTargetException e1) {

                LOG.error(e1.getMessage());
            }
        }
        try {

            file.create(new ByteArrayInputStream(os.toByteArray()),
                    true,
                    new NullProgressMonitor());
        } catch (CoreException e1) {

            LOG.error(e1.getMessage());
        }

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);

        Package pck = (Package) wc.getRootElement();

        /*
         * make a dummy edit on the file so that pre-commit listeners at least
         * receive some kind of notification of a created package
         */
        if (pck != null) {

            ProcessPackageAddedNotificationCommand packageAdded =
                    new ProcessPackageAddedNotificationCommand(
                            wc.getEditingDomain(), pck);
            wc.getEditingDomain().getCommandStack().execute(packageAdded);
            try {
                wc.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pck;
    }

    /**
     * Copied from:
     * {@linkcom.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractSpecialFolderFileSelectionPage}
     * 
     * Get's the working copy from the item passed in. The item should be either
     * an IFile or EObject
     * 
     * @param item
     *            IFile or Eobject
     * @return If successful then WorkingCopy, <strong>null</strong> otherwise
     */
    private WorkingCopy getWorkingCopy(Object item) {

        WorkingCopy wc = null;

        if (item != null) {

            if (item instanceof IFile) {

                XpdProjectResourceFactory fact =
                        XpdResourcesPlugin
                                .getDefault()
                                .getXpdProjectResourceFactory(((IFile) item).getProject());
                if (fact != null) {

                    wc = fact.getWorkingCopy((IFile) item);
                    /* If this is not an XPDL2 package then reset wc */
                    if (wc != null
                            && wc.getWorkingCopyEPackage() != Xpdl2Package.eINSTANCE) {

                        wc = null;
                    }
                }

            } else if (item instanceof EObject) {

                wc = WorkingCopyUtil.getWorkingCopyFor((EObject) item);
            }
        }

        return wc;
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param action
     * @param selection
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {

        boolean enabled = false;
        if (selection instanceof StructuredSelection && !selection.isEmpty()) {

            List<?> list = ((StructuredSelection) selection).toList();
            caseClassSelectionList = new ArrayList<Class>();
            for (Object object : list) {

                if (object instanceof ClassEditPart) {

                    ClassEditPart editPart = (ClassEditPart) object;
                    Class umlCls = editPart.getElement();
                    if (BOMGlobalDataUtils.isCaseClass(umlCls)) {

                        caseClassSelectionList.add(umlCls);
                        enabled = true;
                    }
                }
            }
        }
        action.setEnabled(enabled);
    }

    /**
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     * 
     * @param action
     * @param targetPart
     */
    @Override
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {

        shell = targetPart.getSite().getShell();
    }

    /**
     * This command gets created when the user chooses to generate a
     * business/case service for a case class in a new xpdl file. When a new
     * xpdl file gets created, we must not allow the undo operation to be
     * performed.
     * 
     * @author bharge
     * @since 24 Feb 2014
     */
    class UndoableProcessPackageFileCommand extends CompoundCommand {

        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#canUndo()
         * 
         * @return
         */
        @Override
        public boolean canUndo() {

            return false;
        }
    }

    /**
     * 
     * @return title for the wizard page based on the action selected
     */
    protected abstract String getWizardPageTitle();

    /**
     * 
     * @return the type of process (i.e., Business Service or Case Service)
     */
    protected abstract ProcessWidgetType getProcessWidgetType();

}
