/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.cleanup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.ImportDisplayStatus;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Begins the Resource Clean-up operation sequentially as follows.
 * <p>
 * 1. Checks for empty packages and packages having duplicate
 * processes/interfaces and discards them
 * <p>
 * 2. All packages having non duplicate processes are written to xpdl
 * {@link IResource} and are saved to the process package special folder
 * <p>
 * 3. In case the write and save xpdl operation fails midway then ensures that
 * the previously saved xpdl resources to the process package folder are deleted
 * so that there are no partial files or broken references.
 * <p>
 * 4. Returns a List of {@link IStatus} which will help track the progress of
 * the clean-up.
 * 
 * @author kthombar
 * @since 10-Apr-2014
 */

public class ResourceCleanupAndSave {

    private Set<Package> processPackages;

    private IFolder targetSpecialFolder;

    /**
     * Map containing process/interface name , its originating iPorcess xpdl
     * file name.
     */
    private Map<String, String> processOrInterfaceOriginatingXPDL;

    /**
     * Constructor to which the Set of {@link Package}/es to save to xpdl and
     * the process package folder under which the xpdl's are to be saved are
     * passed
     * 
     * @param processPackages
     *            packages which are to be saved
     * @param processOrInterfaceOriginatingXPDL
     *            , Map of Process/Interface name and their originating iProcess
     *            xpdl name.
     * @param targetSpecialFolder
     *            process package special folder under which the xpdl's will be
     *            saved.
     */
    public ResourceCleanupAndSave(Set<Package> processPackages,
            Map<String, String> processOrInterfaceOriginatingXPDL,
            IFolder targetSpecialFolder) {

        this.processPackages = processPackages;
        this.targetSpecialFolder = targetSpecialFolder;

        this.processOrInterfaceOriginatingXPDL =
                processOrInterfaceOriginatingXPDL;
    }

    /**
     * Begins the Resource Clean-up operation sequentially as follows.
     * <p>
     * 1. Checks for empty packages and packages having duplicate
     * processes/interfaces and discards them
     * <p>
     * 2. All packages having non duplicate processes are written to xpdl
     * {@link IResource} and are saved to the process package special folder
     * <p>
     * 3. In case the write and save xpdl operation fails midway then ensures
     * that the previously saved xpdl resources to the process package folder
     * are deleted so that there are no partial files or broken references.
     * <p>
     * 4. Returns a List of {@link IStatus} which will help track the progress
     * of the clean-up.
     * 
     * @param pm
     *            progress monitor to track the progress.
     * @param convertedFiles
     *            {@link IResource} List that will be populated with the
     *            converted files.
     * @param ignoredPackages
     *            {@link IResource} List that will be populated with the process
     *            packages that were not output because they were empty (process
     *            removed by conversion extensions) or whose process already
     *            existed in workspace.
     * 
     * @return {@link IStatus} to track the status of the clean-up
     * @throws Exception
     *             the caller of the method is expected to handle exception and
     *             to display appropriate message to the user.
     */
    public IStatus startCleanupAndSave(IProgressMonitor pm,
            List<IFile> convertedFiles, List<Package> ignoredPackages)
            throws Exception {

        MultiStatus returnStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        /**
         * Map of duplicate process to the process which is already present in
         * the workspace(due to which it is marked as duplicate)
         */
        Map<EObject, EObject> duplicateEobjectsToIgnore =
                new HashMap<EObject, EObject>();

        /**
         * Set that contains the actual packages which will be written to the
         * final process package folder.
         */
        Set<Package> finalPackagesToSave = new HashSet<Package>();

        try {

            pm.beginTask("", 4); //$NON-NLS-1$   

            pm.subTask(Messages.FinalResourceCleanUp_ProgressMonitor_desc1);
            // XPD-6166: Message list of ignored Processes/Process Interfaces
            List<String> ignoredProcessesOrInterfaces =
                    new LinkedList<String>();

            for (Package pkg : processPackages) {

                if (!isPackageEmptyOrProcessDuplicate(pkg,
                        duplicateEobjectsToIgnore,
                        ignoredProcessesOrInterfaces)) {
                    /*
                     * If the package is not empty and if the process/interfaces
                     * are not duplicates then save the packages
                     */
                    finalPackagesToSave.add(pkg);

                } else {
                    /* For support of new life-cycle listeners. */
                    ignoredPackages.add(pkg);
                }
            }
            /*
             * XPD-6166: Add ImportDisplayStatus Message for ignored
             * process/process interface .
             */
            if (!ignoredProcessesOrInterfaces.isEmpty()) {
                ignoredProcessesOrInterfaces
                        .add(0,
                                Messages.FinalResourceCleanUp_IgnoreDuplicateProcesses_msg);

                String[] msgStringArr =
                        ignoredProcessesOrInterfaces
                                .toArray(new String[ignoredProcessesOrInterfaces
                                        .size()]);

                returnStatus.add(new ImportDisplayStatus(IStatus.INFO,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID, msgStringArr));
            }

            pm.worked(1);

            pm.subTask(Messages.FinalResourceCleanUp_ProgressMonitor_desc2);

            if (!duplicateEobjectsToIgnore.isEmpty()) {

                fixReferencesDueToDuplicates(duplicateEobjectsToIgnore);
            }
            pm.worked(1);

            if (pm.isCanceled()) {
                /*
                 * check if we are cancelling the progress.
                 */
                pm.done();
                throw new OperationCanceledException();
            }

            /*
             * XPD-6463 - make the package names unique so that they do not
             * conflict with the names of the XPDL resources which are already
             * present in the target process packages special folder.
             */
            makePackageNamesUnique(finalPackagesToSave);

            /*
             * Finally get all the projects in workspace that we want to add
             * project reference to.
             */
            Set<IProject> projectsToReference =
                    getProjectsToReference(finalPackagesToSave);

            for (Package eachPackageToSave : finalPackagesToSave) {
                /* save the files */
                pm.subTask(String
                        .format(Messages.FinalResourceCleanUp_ProgressMonitor_desc3,
                                Xpdl2ModelUtil
                                        .getDisplayNameOrName(eachPackageToSave)));

                convertedFiles.add(createAndSaveXpdlFile(eachPackageToSave));
            }
            returnStatus.add(populateSavedFileStatus(convertedFiles));
            pm.worked(1);

            /*
             * Finally add the project references if nothing above fails.
             */
            pm.subTask(Messages.FinalResourceCleanUp_ProgressMonitor_desc4);
            if (!projectsToReference.isEmpty()) {
                returnStatus.add(addProjectReference(targetSpecialFolder
                        .getProject(), projectsToReference));
            }
            pm.subTask(""); //$NON-NLS-1$
            pm.worked(1);

        } catch (Exception e) {

            /*
             * In case any exceptions thrown by the code above then we would
             * want to revert all the previously written xpdls, so that there
             * are no partial xpdls or broken refernces.
             */
            try {

                returnStatus.add(new Status(IStatus.ERROR,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        Messages.FinalResourceCleanUp_ProblemDuringSave_msg));

                deleteXpdlsFromProcessPackage(convertedFiles, pm);

            } catch (CoreException exception) {
                IProcessAMXBPMConverterPlugin
                        .getDefault()
                        .getLogger()
                        .error(Messages.FinalResourceCleanUp_ProblemsDuringXpdlDeletion_message);
            }

            IProcessAMXBPMConverterPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            Messages.FinalResourceCleanUp_ProblemDuringSaveException_desc);

            return returnStatus;

        } finally {
            pm.done();
        }
        return returnStatus;
    }

    /**
     * XPD-6426 : gets all the {@link IProject}'s in the workspace which will be
     * referenced by the Project to which the converted files will be saved.
     * 
     * @param finalPackagesToSave
     *            the Packages which are about to be saved.
     * @return all the {@link IProject}'s in the workspace which will be
     *         referenced by the Project to which the converted files will be
     *         saved.
     */
    private Set<IProject> getProjectsToReference(
            Set<Package> finalPackagesToSave) {
        /**
         * stores all the projects that we wish to add project reference to
         */
        Set<IProject> projectsToReference = new HashSet<IProject>();
        /**
         * Stores the already visited process / interface id's so we do not
         * re-fetch their projects in workspace.
         */
        Set<String> idAlreadyVisited = new HashSet<String>();

        /**
         * The target project to which the files will be written post
         * conversion.
         */
        IProject targetProject = targetSpecialFolder.getProject();

        for (Package eachPkg : finalPackagesToSave) {

            EList<Process> processes = eachPkg.getProcesses();
            if (processes != null && !processes.isEmpty()) {
                /*
                 * We are intersted only in processes
                 */
                /*
                 * Each package will have only 1 process.
                 */
                Process process = processes.get(0);

                Object otherElement =
                        Xpdl2ModelUtil
                                .getOtherElement(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementedInterface());

                if (otherElement instanceof ImplementedInterface) {
                    /*
                     * Move ahead only if the process implements an interface.
                     */
                    ImplementedInterface implementedIfc =
                            (ImplementedInterface) otherElement;

                    String processInterfaceId =
                            implementedIfc.getProcessInterfaceId();

                    if (processInterfaceId != null
                            && !processInterfaceId.isEmpty()
                            && !idAlreadyVisited.contains(processInterfaceId)) {

                        idAlreadyVisited.add(processInterfaceId);
                        /*
                         * Get the BPM Process/Interface if it is already
                         * present in workspace.
                         */
                        EObject bpmProcOrIfcInWorkspaceById =
                                IpmImportUtil
                                        .getBpmProcOrIfcInWorkspaceById(processInterfaceId);

                        if (bpmProcOrIfcInWorkspaceById != null) {

                            IProject projectInWorkspace =
                                    WorkingCopyUtil
                                            .getProjectFor(bpmProcOrIfcInWorkspaceById);

                            if (!targetProject.equals(projectInWorkspace)
                                    && !projectsToReference
                                            .contains(projectInWorkspace)) {
                                /*
                                 * Add project to reference.
                                 */
                                projectsToReference.add(projectInWorkspace);
                            }
                        }
                    }
                }

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                for (Activity eachActivity : allActivitiesInProc) {

                    Implementation impl = eachActivity.getImplementation();

                    if (impl instanceof SubFlow) {
                        /*
                         * We are interested only in Re-usable Sub proc
                         * activities.
                         */
                        SubFlow subFlow = (SubFlow) impl;

                        String processId = subFlow.getProcessId();

                        if (processId != null && !processId.isEmpty()
                                && !idAlreadyVisited.contains(processId)) {

                            idAlreadyVisited.add(processId);
                            /*
                             * Get the BPM Process/Interface if it is already
                             * present in workspace.
                             */
                            EObject bpmProcOrIfcInWorkspaceById =
                                    IpmImportUtil
                                            .getBpmProcOrIfcInWorkspaceById(processId);

                            if (bpmProcOrIfcInWorkspaceById != null) {

                                IProject projectFor =
                                        WorkingCopyUtil
                                                .getProjectFor(bpmProcOrIfcInWorkspaceById);

                                if (!targetProject.equals(projectFor)
                                        && !projectsToReference
                                                .contains(projectFor)) {
                                    /*
                                     * Add project to reference.
                                     */
                                    projectsToReference.add(projectFor);
                                }
                            }
                        }
                    }
                }
            }
        }
        return projectsToReference;
    }

    /**
     * helper method to populate the {@link IStatus} with all the Xpdl files
     * saved successfully to Process Package special folder.
     * 
     * @param xpdlsSavedToProcessPackage
     * @return IStatus representing Errors/Warnings/Infos related to Save.
     */
    private IStatus populateSavedFileStatus(
            List<IFile> xpdlsSavedToProcessPackage) {

        if (!xpdlsSavedToProcessPackage.isEmpty()) {
            // XPD-6166: Message List of Saved Files
            StringBuffer savedFileNames = new StringBuffer();
            // used for grouping file names 4 in a row.
            int i = 0;

            for (IResource savedFile : xpdlsSavedToProcessPackage) {

                // XPD-6166: add names separator when required
                String savedResourceName = savedFile.getName();

                i =
                        IpmImportUtil.appendValue(i,
                                savedFileNames,
                                savedResourceName);

            }
            // XPD-6166: use ImportDisplayStatus to display this message to user

            return new ImportDisplayStatus(
                    IStatus.INFO,
                    IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                    new String[] {
                            Messages.FinalResourceCleanUp_FilesSavedSuccessfully_msg,
                            savedFileNames.toString() });
        }

        return new ImportDisplayStatus(
                IStatus.ERROR,
                IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                new String[] { Messages.ResourceCleanupAndSave_NoFilesSavedErrMsg });

    }

    /**
     * fixes the references by replacing any reference to the EObject being
     * referenced by the EObject already present in the workspace.
     * 
     * @param ignoredProcessesMap
     *            Map of duplicate EObject to their identical EObjects which are
     *            already present in the workspace.
     */
    private void fixReferencesDueToDuplicates(
            Map<EObject, EObject> ignoredProcessesMap) {

        Set<EObject> keySet = ignoredProcessesMap.keySet();

        for (EObject keyObject : keySet) {

            EObject valueObject = ignoredProcessesMap.get(keyObject);

            if (keyObject instanceof Process) {

                Process processToDiscard = (Process) keyObject;

                IFile file = WorkingCopyUtil.getFile(valueObject);

                updateReferences(processToDiscard.getPackage().getName(),
                        processToDiscard.getId(),
                        new Path(file.getName()).removeFileExtension()
                                .toString(),
                        ((NamedElement) valueObject).getId(),
                        valueObject);

            } else if (keyObject instanceof ProcessInterface) {

                ProcessInterface interfacetToDiscard =
                        (ProcessInterface) keyObject;

                Package pkg = Xpdl2ModelUtil.getPackage(interfacetToDiscard);

                IFile file = WorkingCopyUtil.getFile(valueObject);

                updateReferences(pkg.getName(),
                        interfacetToDiscard.getId(),
                        new Path(file.getName()).removeFileExtension()
                                .toString(),
                        ((NamedElement) valueObject).getId(),
                        valueObject);
            }
        }
    }

    /**
     * Incase of failure this method ensures that all the xpdls previously
     * written to the process package folder will be deleted.
     * 
     * @param xpdlsSavedToProcessPackage
     *            List of xpdl files which we want to delete from process
     *            package folder in case of failure
     * @param pm
     * @throws CoreException
     */
    private void deleteXpdlsFromProcessPackage(
            List<IFile> xpdlsSavedToProcessPackage, IProgressMonitor pm)
            throws CoreException {

        try {
            pm.beginTask("", xpdlsSavedToProcessPackage.size()); //$NON-NLS-1$

            for (IResource eaxhXpdlFile : xpdlsSavedToProcessPackage) {

                pm.setTaskName(String
                        .format(Messages.FinalResourceCleanUp_DeleteXpdlResourcesProgressMonitor_desc,
                                eaxhXpdlFile.getName()));

                eaxhXpdlFile.delete(true, pm);

                pm.worked(1);
            }
        } finally {
            pm.done();
        }
    }

    /**
     * This method does the following
     * <p>
     * 1. Checks if the {@link Package} passed is empty or has duplicate
     * process/interface in it.
     * <p>
     * 2.If there are duplicate process/interface then populate the passed
     * {@link Map} with the duplicate process/interface to the identical
     * processes/interfaces which are already present in the workspace and this
     * method returns <code>true</code>.
     * <p>
     * 3.If there are no duplicates then return <code>false</code>
     * 
     * @param pkg
     *            the package whose processes/interfaces are to be check for
     *            duplicacy
     * 
     * @param duplicateEobjectsToIgnore
     *            {@link Map} which will be populated with all the duplicate
     *            processes/interfaces to the identical processes/interfaces
     *            which are already present in the workspace.
     * @param ignoredProcessesOrInterfaces
     *            , String for Message list of ignored Process/Process
     *            Interface, names of ignored Process/Process Interface will be
     *            appended to this.
     * 
     * @return <code>true</code> if the {@link Package} passed is empty or has
     *         duplicate process/interface(i.e. it is already present in
     *         workspace), else return <code>false</code>
     */
    private boolean isPackageEmptyOrProcessDuplicate(Package pkg,
            Map<EObject, EObject> duplicateEobjectsToIgnore,
            List<String> ignoredProcessesOrInterfaces) {

        boolean emptyOrDuplicate = false;

        EList<Process> processes = pkg.getProcesses();

        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(pkg);

        EList<ProcessInterface> processInterface = null;

        if (processInterfaces != null) {
            processInterface = processInterfaces.getProcessInterface();
        }

        // return if package is empty
        if (processes.isEmpty() && processInterface.isEmpty()) {
            return true;
        }
        if (!processes.isEmpty()) {
            /* we have only 1 process per package */
            Process process = processes.get(0);

            /* Get all processes with same name form workspace */
            /* XPD-6166 Status message Display changes */
            String processName = process.getName();

            EObject duplicateBPMProcessInWorkspace =
                    IpmImportUtil
                            .getBPMDestinationProcessOrIFCInWorkspace(processName,
                                    ProcessResourceItemType.PROCESS.name());
            /*
             * A process with same name already exits , ignore the current
             * process
             */
            if (duplicateBPMProcessInWorkspace != null) {
                /* collect ignored process */
                duplicateEobjectsToIgnore.put(process,
                        duplicateBPMProcessInWorkspace);

                /*
                 * processName1 (Import.xpdl) already exists in: Project/Process
                 * Packages/Workspace.xpdl
                 */
                ignoredProcessesOrInterfaces
                        .add(getIgnoredProcessMessage((NamedElement) duplicateBPMProcessInWorkspace));
                emptyOrDuplicate = true;

            }
        }

        if (processInterface != null && !processInterface.isEmpty()) {
            /* we have only 1 interface per package */

            ProcessInterface processIFC = processInterface.get(0);

            String processIFCName = processIFC.getName();

            EObject duplicateBPMProcessIFCInWorkspace =
                    IpmImportUtil
                            .getBPMDestinationProcessOrIFCInWorkspace(processIFCName,
                                    ProcessResourceItemType.PROCESSINTERFACE
                                            .name());

            /*
             * A process with same name already exits , ignore the current
             * process
             */

            if (duplicateBPMProcessIFCInWorkspace != null) {
                // collect ignored process
                ignoredProcessesOrInterfaces
                        .add(getIgnoredProcessMessage((NamedElement) duplicateBPMProcessIFCInWorkspace));

                duplicateEobjectsToIgnore.put(processIFC,
                        duplicateBPMProcessIFCInWorkspace);

                emptyOrDuplicate = true;

            }
        }
        return emptyOrDuplicate;
    }

    /**
     * Returns message String for ignored process.
     * 
     * @param processOrInterface
     * @return
     */
    private String getIgnoredProcessMessage(NamedElement processOrInterface) {
        /*
         * ProcessName (originating iProcessXPDL) already exists in:
         * Project/Process Packages/Workspace.xpdl"
         */
        IFile workspaceXpdl = WorkingCopyUtil.getFile(processOrInterface);

        String elementName = processOrInterface.getName();

        String message =
                IpmImportUtil.DISP_MSG_INDENTATION
                        + String.format(Messages.ResourceCleanupAndSave_ProcessAlreadyExistsInWorkspace_msg,
                                elementName,
                                processOrInterfaceOriginatingXPDL
                                        .get(elementName),
                                workspaceXpdl.getFullPath());

        return message;
    }

    /**
     * adds references to the projects
     * 
     * @param project
     *            the project to which the packages will be saved
     * @param projectsToReference
     *            the List project to which references need to be added
     * @return {@link IStatus} representing Error/Warning/Info status message.
     * @throws CoreException
     */

    private IStatus addProjectReference(IProject project,
            Set<IProject> projectsToReference) throws CoreException {

        StringBuffer projectsRefList = new StringBuffer();
        MultiStatus returnStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        IStatus.OK, "", null); //$NON-NLS-1$

        int i = 0;
        for (IProject projectToRefer : projectsToReference) {

            if (!ProjectUtil.isProjectReferenced(projectToRefer, project)) {
                /* proceed ahead only if there arises no cyclic dependency */
                if (!ProjectUtil.isProjectReferenced(project, projectToRefer)) {
                    /* if the project is already referenced then ignore */
                    ProjectUtil.addReferenceProject(project, projectToRefer);

                    /*
                     * XPD-6166: collect referenced project's name, add
                     * separator if required
                     */

                    i =
                            IpmImportUtil.appendValue(i,
                                    projectsRefList,
                                    projectToRefer.getName());
                }
            } else {
                returnStatus
                        .add(new Status(
                                IStatus.WARNING,
                                IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                                String.format(Messages.FinalResourceCleanUp_CyclicProjectDependency_msg,
                                        project.getName(),
                                        projectToRefer.getName())));
            }
        }
        /* XPD-6166: Format status for added project references */
        if (!projectsRefList.toString().isEmpty()) {
            /* use ImportDisplayStatus to display this message to user. */

            returnStatus
                    .add(new ImportDisplayStatus(
                            IStatus.INFO,
                            IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                            new String[] {
                                    Messages.FinalResourceCleanUp_ProjectReferenceAdded_msg,
                                    projectsRefList.toString() }));

        }
        return returnStatus;
    }

    /**
     * updates the references, we update references under 2 scenarios
     * <p>
     * 1.When we discard a process/interface because it is already present in
     * the workspace(having destination BPM), we need to update any re-usable
     * sub process that references the process/interface being discarded.
     * <p>
     * 2. When the xpdl file being written to the Process Packages folder has
     * name conflicts with other xpdl file, in this case we change the name of
     * the xpdl file being written(i.e. xpdlFileName_intergerIncrement), in this
     * case we need to update any references to the xpdl file.
     * 
     * <p>
     * Updating references includes Updating the External Package refrences as
     * well as the subflow element reference of the re-usable sub process
     * 
     * @param oldPkgName
     *            the old package name to be replaced, this parameter can be
     *            null, if it is null then the package references will not be
     *            updated
     * @param oldProcessId
     *            the old process id which needs to be replaced, this parameter
     *            can be null, if it is null then the process id will not be
     *            updated while reference fixing will not be updated
     * @param newPkgName
     *            the new package name which should now be refernced, this
     *            parameter can be null, if it is null then the package
     *            references will not be updated
     * @param newProcessId
     *            the new process id that the re-usable sub-process should
     *            reference, this parameter can be null, if it is null then the
     *            process id will not be updated while reference fixing will not
     *            be updated
     * @param processOrIfcAlreadyInWorkspace
     *            the process or interface already present in workspace
     * @return <code>true</code> if any references were actually updated else if
     *         no references were updated return <code>false</code>
     */
    private boolean updateReferences(String oldPkgName, String oldProcessId,
            String newPkgName, String newProcessId,
            EObject processOrIfcAlreadyInWorkspace) {

        boolean isReferenceUpdated = false;
        /*
         * Check if the package which will be discarded is referenced by some
         * other package.
         */
        for (Package eachPkg : processPackages) {
            EList<ExternalPackage> externalPackagesRef =
                    eachPkg.getExternalPackages();

            for (ExternalPackage eachExternalPackageRef : externalPackagesRef) {
                /*
                 * If the packages have external package refernce to the package
                 * which is being discarded that means that there is a
                 * sub-process which references the package.
                 */
                if (eachExternalPackageRef.getHref().equals(oldPkgName)) {

                    /*
                     * First we fix the external package reference reference.
                     */
                    fixExternalPackageRef(eachExternalPackageRef, newPkgName);
                    /*
                     * Next we go deep to fetch the reusable sub-process which
                     * references the process from the package being discarded.
                     */
                    fixReusableSubProcReferences(eachPkg,
                            oldPkgName,
                            oldProcessId,
                            newPkgName,
                            newProcessId);
                    /*
                     * If the process implements an process interface , fix all
                     * such references.
                     */

                    fixProcessImplementingInterfaceReferences(eachPkg,
                            oldPkgName,
                            oldProcessId,
                            newPkgName,
                            newProcessId,
                            processOrIfcAlreadyInWorkspace);

                    isReferenceUpdated = true;
                }
            }

            /*
             * Sid XPD-6652 removed call to fix WSDL location updates. This
             * should only be done for the WSDL location of generated request
             * activities and that should ONLY be for package that is being
             * renamed. Previously we were calling this for every package that
             * got renamed AND always changing the WSLD location reference to
             * the new package name **REGARDLESS** of whether it had anything to
             * do with the package or its SWDL location.
             */
        }
        return isReferenceUpdated;
    }

    /**
     * Fixes the references when a process implements a process interface. Fixes
     * the process references as well as the activity which implement the
     * process interface activities references.
     * 
     * @param pkg
     *            the package that has the reference to the package being
     *            discarded
     * @param oldPkgName
     *            the old package name to be replaced, this parameter can be
     *            null, if it is null then the package references will not be
     *            updated
     * @param oldProcessId
     *            the old process id which needs to be replaced, this parameter
     *            can be null, if it is null then the process id will not be
     *            updated while reference fixing will not be updated
     * @param newPkgName
     *            the new package name which will replace the old name, this
     *            parameter can be null, if it is null then the package
     *            references will not be updated
     * @param newProcessId
     *            the new process id which will replace the old process id, this
     *            parameter can be null, if it is null then the process id will
     *            not be updated while reference fixing will not be updated
     * @param processInterface
     *            the interface that the process implements.
     */
    private void fixProcessImplementingInterfaceReferences(Package pkg,
            String oldPkgName, String oldProcessId, String newPkgName,
            String newProcessId, EObject processOrIfc) {

        EList<Process> processes = pkg.getProcesses();

        if (processes != null && !processes.isEmpty()) {

            /* we know a package can have only 1 process */
            Process process = processes.get(0);

            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementedInterface());

            if (otherElement instanceof ImplementedInterface) {
                /*
                 * Move ahead only if the process implements an interface.
                 */
                ImplementedInterface implementedIfc =
                        (ImplementedInterface) otherElement;

                if (oldProcessId != null && newProcessId != null) {
                    /*
                     * Update the Implemented interface id
                     */
                    String processInterfaceId =
                            implementedIfc.getProcessInterfaceId();

                    if (processInterfaceId != null
                            && processInterfaceId.equals(oldProcessId)) {

                        implementedIfc.setProcessInterfaceId(newProcessId);
                        /*
                         * Update the activity references if the activity is
                         * inherited from the process interface
                         */
                        IpmImportUtil
                                .updateImplementedActivityRefernces(process,
                                        processOrIfc);

                    }
                }

                if (oldPkgName != null && newPkgName != null) {
                    /*
                     * Update the package reference.
                     */
                    String packageRef = implementedIfc.getPackageRef();

                    if (packageRef != null && packageRef.equals(oldPkgName)) {

                        implementedIfc.setPackageRef(newPkgName);
                    }
                }
            }
        }
    }

    /**
     * Fixes WSDL file reference for the Message Events/Tasks set to Generate.
     * <p>
     * XPD-6652 PAssing in oldPkgName so that we are absolutely sure only to
     * replace what we are expecting to replace (i.e. don't accidentally replace
     * WSDL locations trhat weren't already the default WSDL location for the
     * old pkg name).
     * 
     * @param eachPkg
     * @param oldPkgName
     * @param newPkgName
     */
    private void fixGeneratingMessageActivities(Package eachPkg,
            String oldPkgName, String newPkgName) {
        /*
         * start-message event / intermediate-message event / receive task and
         * their reply message types (throw message end event (configured as
         * reply not invoke), throw message intermediate event(configured as
         * reply not invoke), send task(configured as reply not invoke))
         * 
         * There are xpdExt's that should make it clear which events are
         * generating request activities and replies to them - let me know if it
         * isn't.
         */

        EList<Process> processes = eachPkg.getProcesses();

        if (processes != null && !processes.isEmpty()) {
            /* get process, Each Package has only one process */
            Process process = processes.get(0);
            /*
             * Get all TriggerEventMessage for following start-message event /
             * intermediate-message event / receive task and their reply message
             * types (throw message end event (configured as reply not invoke),
             * throw message intermediate event(configured as reply not invoke),
             * send task(configured as reply not invoke))
             */

            Collection<EObject> allGeneratingMessageActivitiesEvents =
                    getAllGeneratingMessageActivitiesEvents(process
                            .getActivities());

            fixWsdlReferences(allGeneratingMessageActivitiesEvents,
                    oldPkgName,
                    newPkgName);
        }
    }

    /**
     * Replace WSDL location reference that were the default generated ones for
     * the old pkg name with the default generated one for the new pkg name.
     * <p>
     * XPD-6652 PAssing in oldPkgName so that we are absolutely sure only to
     * replace what we are expecting to replace (i.e. don't accidentally replace
     * WSDL locations trhat weren't already the default WSDL location for the
     * old pkg name).
     * 
     * @param allGeneratingMessageActivitiesEvents
     * @param oldPkgName
     * @param newPkgName
     */
    @SuppressWarnings("deprecation")
    private void fixWsdlReferences(
            Collection<EObject> allGeneratingMessageActivitiesEvents,
            String oldPkgName, String newPkgName) {

        String oldPkgWsdlFileName =
                Xpdl2ModelUtil.getWsdlFileName(String.format("%1s.xpdl", //$NON-NLS-1$
                        oldPkgName));
        String newPkgWsdlFileName =
                Xpdl2ModelUtil.getWsdlFileName(String.format("%1s.xpdl", //$NON-NLS-1$
                        newPkgName));

        for (EObject eObject : allGeneratingMessageActivitiesEvents) {

            if (eObject instanceof WebServiceOperation) {

                WebServiceOperation wso = (WebServiceOperation) eObject;

                if (wso.getService() != null
                        && wso.getService().getEndPoint() != null) {

                    EndPoint endPnt = wso.getService().getEndPoint();

                    ExternalReference wsdlRef = endPnt.getExternalReference();

                    if (wsdlRef == null) {

                        wsdlRef =
                                Xpdl2Factory.eINSTANCE
                                        .createExternalReference();

                        endPnt.setExternalReference(wsdlRef);
                    }

                    if (oldPkgWsdlFileName.equals(wsdlRef.getLocation())) {
                        wsdlRef.setLocation(newPkgWsdlFileName);
                    }
                }

            } else if (eObject instanceof PortTypeOperation) {

                PortTypeOperation portTypeOp = (PortTypeOperation) eObject;

                ExternalReference wsdlRef = portTypeOp.getExternalReference();

                if (wsdlRef == null) {

                    wsdlRef = Xpdl2Factory.eINSTANCE.createExternalReference();

                    portTypeOp.setExternalReference(wsdlRef);
                }

                if (oldPkgWsdlFileName.equals(wsdlRef.getLocation())) {
                    wsdlRef.setLocation(newPkgWsdlFileName);
                }
            }

        }

    }

    /**
     * Get all start-message event / intermediate-message event / receive task
     * and their reply message types (throw message end event (configured as
     * reply not invoke), throw message intermediate event(configured as reply
     * not invoke), send task(configured as reply not invoke))
     * 
     * @param process
     * @return {@link Collection} of EObject representing
     *         WebServiceOpertaion/PortType for following , start-message event
     *         / intermediate-message event / receive task and their reply
     *         message types (throw message end event (configured as reply not
     *         invoke), throw message intermediate event(configured as reply not
     *         invoke), send task(configured as reply not invoke))
     */
    @SuppressWarnings("unchecked")
    private Collection<EObject> getAllGeneratingMessageActivitiesEvents(
            Collection<Activity> activities) {

        if (activities == null) {
            return Collections.EMPTY_LIST;
        }

        Collection<EObject> webServOpPortTypeCollection =
                new ArrayList<EObject>();

        for (Activity activity : activities) {

            if (activity instanceof ActivitySet) {

                webServOpPortTypeCollection
                        .addAll(getAllGeneratingMessageActivitiesEvents(((ActivitySet) activity)
                                .getActivities()));

                continue;
            }

            Event event = activity.getEvent();

            boolean webServiceRelatedEvent =
                    EventObjectUtil.isWebServiceRelatedEvent(activity);
            /* Handle web Service related Events */
            if (webServiceRelatedEvent) {

                if (event.getEventTriggerTypeNode() instanceof TriggerResultMessage) {

                    TriggerResultMessage trm =
                            (TriggerResultMessage) event
                                    .getEventTriggerTypeNode();

                    Object otherAttribute =
                            Xpdl2ModelUtil.getOtherAttribute(trm,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Generated());

                    Boolean isGenerated =
                            (otherAttribute instanceof Boolean) ? (Boolean) otherAttribute
                                    : false;

                    if (isGenerated) {
                        boolean collect = false;
                        if (CatchThrow.THROW.equals(trm.getCatchThrow())) {
                            /*
                             * throw message end event (configured as reply not
                             * invoke), throw message intermediate
                             * event(configured as reply not invoke),
                             */
                            String requestActivityId =
                                    ThrowErrorEventUtil
                                            .getRequestActivityId(activity);

                            if (requestActivityId != null
                                    && !requestActivityId.isEmpty()) {

                                collect = true;
                            }

                        } else {
                            collect = true;

                        }

                        if (collect) {
                            webServOpPortTypeCollection.add(trm
                                    .getWebServiceOperation());

                            Object otherElement =
                                    Xpdl2ModelUtil
                                            .getOtherElement(trm,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_PortTypeOperation());

                            if (otherElement instanceof EObject) {
                                webServOpPortTypeCollection
                                        .add((EObject) otherElement);
                            }
                        }
                    }
                }
            } else if (activity.getImplementation() instanceof Task) {
                TaskType taskTypeStrict =
                        com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil
                                .getTaskTypeStrict(activity);

                Task task = (Task) activity.getImplementation();

                /* send task(configured as reply not invoke)) */
                if (TaskType.SEND_LITERAL.equals(taskTypeStrict)) {

                    TaskSend taskSend = task.getTaskSend();

                    if (taskSend != null) {

                        Object otherAttribute =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(taskSend,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ReplyToActivityId());

                        if (otherAttribute instanceof String) {
                            String replyToActId = (String) otherAttribute;

                            if (replyToActId != null && !replyToActId.isEmpty()) {
                                /*
                                 * Collect the wesServiceOperation and
                                 * PortTypeOperation
                                 */
                                webServOpPortTypeCollection.add(taskSend
                                        .getWebServiceOperation());

                                Object otherElement =
                                        Xpdl2ModelUtil
                                                .getOtherElement(taskSend,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_PortTypeOperation());
                                if (otherElement instanceof EObject) {
                                    webServOpPortTypeCollection
                                            .add((EObject) otherElement);
                                }
                            }

                        }
                    }
                    /*
                     * Receive task and their reply message types [Throw events
                     * replying to the receive task covered in event block
                     * above]
                     */
                } else if (TaskType.RECEIVE_LITERAL.equals(taskTypeStrict)) {

                    TaskReceive receiveTask = task.getTaskReceive();

                    if (receiveTask != null) {

                        /*
                         * Collect the wesServiceOperation and PortTypeOperation
                         */
                        webServOpPortTypeCollection.add(receiveTask
                                .getWebServiceOperation());

                        Object otherElement =
                                Xpdl2ModelUtil
                                        .getOtherElement(receiveTask,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_PortTypeOperation());
                        if (otherElement instanceof EObject) {
                            webServOpPortTypeCollection
                                    .add((EObject) otherElement);
                        }
                    }
                }

            }

        }

        return webServOpPortTypeCollection;
    }

    /**
     * fixes the reusable sub proc reference. Replaces the old id and old
     * package name that the reusable subprocess referenced by the new id and
     * package name
     * 
     * @param pkg
     *            the package that has the reference to the package being
     *            discarded
     * @param oldPkgName
     *            the old package name to be replaced, this parameter can be
     *            null, if it is null then the package references will not be
     *            updated
     * @param oldProcessId
     *            the old process id which needs to be replaced, this parameter
     *            can be null, if it is null then the process id will not be
     *            updated while reference fixing will not be updated
     * @param newPkgName
     *            the new package name which will replace the old name, this
     *            parameter can be null, if it is null then the package
     *            references will not be updated
     * @param newProcessId
     *            the new process id which will replace the old process id, this
     *            parameter can be null, if it is null then the process id will
     *            not be updated while reference fixing will not be updated
     */
    private void fixReusableSubProcReferences(Package pkg, String oldPkgName,
            String oldProcessId, String newPkgName, String newProcessId) {
        /* we know a package can have only 1 process */
        Process process = pkg.getProcesses().get(0);

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity eachActivity : allActivitiesInProc) {

            Implementation impl = eachActivity.getImplementation();

            if (impl instanceof SubFlow) {

                SubFlow subFlow = (SubFlow) impl;

                if (newProcessId != null && oldProcessId != null) {

                    String processId = subFlow.getProcessId();
                    if (processId != null && processId.equals(oldProcessId)) {
                        subFlow.setProcessId(newProcessId);
                    }
                }

                if (oldPkgName != null && newPkgName != null) {

                    String packageRefId = subFlow.getPackageRefId();

                    if (packageRefId != null && packageRefId.equals(oldPkgName)) {
                        subFlow.setPackageRefId(newPkgName);
                    }
                }
            }
        }
    }

    /**
     * fixes the External Package reference
     * 
     * @param eachExternalPackageRef
     *            the External Package whose refernce is to be updated.
     * @param newPkgName
     *            the new package name which is now to be referenced.
     */
    private void fixExternalPackageRef(ExternalPackage eachExternalPackageRef,
            String newPkgName) {
        eachExternalPackageRef.setHref(newPkgName);

        ExtendedAttribute extendedAttribute =
                eachExternalPackageRef.getExtendedAttributes().get(0);

        extendedAttribute.setValue("/" + newPkgName //$NON-NLS-1$
                + Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT);

    }

    /**
     * XPD-6463 : Make the package names unique by checking if their name
     * clashes with any xpdl file names which are already present in the target
     * process package special folder. If the name clashes then the new name set
     * will be newName = oldName_1 .._2 ..3 so on.
     * <p>
     * If a package name was changed then we fix the all the references which we
     * referencing the package by the old name so that they now reference the
     * package by the new name.
     * 
     * @param finalPackagesToSave
     *            the {@link Package}'es whose name is to be made unique.
     * @throws CoreException
     */
    private void makePackageNamesUnique(Set<Package> finalPackagesToSave)
            throws CoreException {

        IFolder folder = targetSpecialFolder;

        List<String> existingPackageNames = new ArrayList<String>();
        /*
         * get all the package names that are present in the Target Process
         * package folder.
         */
        existingPackageNames.addAll(getCaseInsensitiveXpdlFileNames(Arrays
                .asList(folder.members())));

        for (Package pkg : finalPackagesToSave) {

            String xpdlFileNameFromPackageName = pkg.getName();

            if (existingPackageNames.contains(xpdlFileNameFromPackageName
                    .toLowerCase())) {
                /* If the package name exists then make it unique. */
                String uniqueFileName =
                        getUniqueFileName(xpdlFileNameFromPackageName,
                                existingPackageNames);

                pkg.setName(uniqueFileName);

                /*
                 * now as the package file name has changed there might be some
                 * broken references which referenced this xpdl with the old
                 * name
                 */
                updateReferences(xpdlFileNameFromPackageName, null,
                /*
                 * we pass old process id as null because we only want to update
                 * the package name and are not concerned with the process id
                 */
                uniqueFileName, null, null
                /*
                 * we pass new process id as null because we only want to update
                 * the package name and are not concerned with the process id
                 */
                );

                /*
                 * XPD-6652 - moved XPD-6112 fix here so that we ONLY replace
                 * generated WSDL locations in the package whose name has
                 * changed NOT every WSDL location in every package being
                 * imported. XPD-6112 : Fix the WSDL reference for the Message
                 * Event Activities which are set to Generate
                 */
                fixGeneratingMessageActivities(pkg,
                        xpdlFileNameFromPackageName,
                        uniqueFileName);

                xpdlFileNameFromPackageName = uniqueFileName;
            }
            existingPackageNames.add(xpdlFileNameFromPackageName.toLowerCase());
        }
    }

    /**
     * Creates an XPDL file based on the package name, adds the package to the
     * xpdl file and saves the XPDL to the Process package folder. We throw
     * exceptions from this method as in case if the create and save xpdl
     * operation fails we do not want the process package special folder to have
     * only partial converted xpdl resources. Hence if this method throws
     * exception then the files previously saved to the process package should
     * 
     * @param pkg
     *            the package which should be written to the XPDL.
     * @return xpdl file that was just created
     * @throws IOException
     * @throws CoreException
     */
    private IFile createAndSaveXpdlFile(Package pkg) throws IOException,
            CoreException {

        IFolder folder = targetSpecialFolder;

        String xpdlFileNameFromPackageName =
                pkg.getName()
                        + Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT;

        IFile file = folder.getFile(xpdlFileNameFromPackageName);

        URI uri =
                URI.createPlatformResourceURI(xpdlFileNameFromPackageName, true);

        ResourceSet rset = new ResourceSetImpl();

        Resource resource = rset.createResource(uri);

        DocumentRoot docRoot = Xpdl2Factory.eINSTANCE.createDocumentRoot();

        docRoot.setPackage(pkg);

        resource.getContents().add(docRoot);

        ByteArrayOutputStream bOS = new ByteArrayOutputStream();

        resource.save(bOS, null);

        file.create(new ByteArrayInputStream(bOS.toByteArray()),
                true,
                new NullProgressMonitor());

        WorkingCopy resourceWorkingCopy = WorkingCopyUtil.getWorkingCopy(file);

        resourceWorkingCopy.save();

        return file;
    }

    /**
     * 
     * @param allXpdlsUnderProcessPackage
     *            all XPDL files under the target Process Package special
     *            folder.
     * @return all the names(IN LOWER CASE) of the xpdl files present under the
     *         Process Package folder.
     */
    private List<String> getCaseInsensitiveXpdlFileNames(
            List<IResource> allXpdlsUnderProcessPackage) {

        List<String> existingResourceNames = new ArrayList<String>();

        for (IResource eachResource : allXpdlsUnderProcessPackage) {
            /*
             * get all the file names which are present in the Process Packages
             * folder.
             */
            /*
             * we get the names in lower case because we do not want xpdl with
             * same names and different cases either.
             */
            existingResourceNames.add(new Path(eachResource.getName())
                    .removeFileExtension().toString().toLowerCase());
        }
        return existingResourceNames;
    }

    /**
     * 
     * @param xpdlFileNameFromPackageName
     *            the existing file name
     * @param alreadyPresentXpdlFileNames
     *            the list of resources already present in the process package
     *            folder
     * @return unique file name that is not present in the target Process
     *         Package folder.
     */
    private String getUniqueFileName(String xpdlFileNameFromPackageName,
            List<String> alreadyPresentXpdlFileNames) {

        String uniqueResourceName = xpdlFileNameFromPackageName;

        for (int i = 1;; i++) {
            /* increment the filename */
            String temporaryName = uniqueResourceName + "_" + i; //$NON-NLS-1$
            if (!alreadyPresentXpdlFileNames.contains(temporaryName
                    .toLowerCase())) {
                /* found the unique file name */
                uniqueResourceName = temporaryName;
                break;
            }
        }
        return uniqueResourceName;
    }
}
