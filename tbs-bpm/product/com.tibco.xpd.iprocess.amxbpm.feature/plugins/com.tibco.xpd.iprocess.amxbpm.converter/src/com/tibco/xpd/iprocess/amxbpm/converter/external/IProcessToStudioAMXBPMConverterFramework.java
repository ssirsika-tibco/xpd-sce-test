/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.external;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.customer.api.iprocess.internal.IProcessToBPMConversionExtension;
import com.tibco.xpd.customer.api.iprocess.internal.IProcessToBPMConversionExtensionPointManager;
import com.tibco.xpd.customer.api.iprocess.internal.IProcessToBPMLifeCycleExtension;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.cleanup.ResourceCleanupAndSave;
import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager.DEBUG_MODE;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.converter.moveprocess.ProcessPackageSeparator;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IProcessXpdlValidator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xsd.XSDWorkingCopy;

/**
 * Converter Framework class, which provides conversions of iProcess XPDL to AMX
 * BPM XPDL at two levels. The first Conversion will handle IPM XPDL and produce
 * AMX BPM XPDL for Studio. The Second Conversion will take Studio iProcess XPDL
 * and produce Studio AMX BPM XPDL. Conversion starts from Migration of the IPM
 * XPDL to IPS, and conclude at the final cleanup of the temp/duplicate/empty
 * resources, and save of the final BPM XPDL files. The framework will work on
 * List of XPDL iProcess files, and convert them to BPM XPDLs with each process
 * saved in its own package/xpdl. The Framework will return IStatus ,
 * representing each error [like errors in conversion, errors in file
 * save],warnings , Info [ignored processes, Saved Converted BPM XPDL files
 * list].
 * 
 * Stages of the Conversion Framework. 1. Create Temp Working hidden Process
 * Package Special Folder 2. Copy IPM XPDLs to be imported to the temp folder.
 * 3.Convert IPM XPDLs to Studio iProcess XPDLs. 4. Migrate to current Studio
 * Format. 5. Separate processes into their own package. 6. Execute Conversion
 * Extensions. 7. Final Clean up. The First conversion [IPM to Studio AMX BPM]
 * starts from step 1 through 7. The Second conversion [Studio iProcess to
 * Studio AMX BPM] will start from 5 through 7.
 * 
 * NOTE: Wizard/Tool using this Framework should use
 * {@link IProcessXpdlValidator} to validate the File before passing on to this
 * Framework, to make sure that only valid IPM files are received by this
 * framework.
 * 
 * 
 * @author aprasad
 * @since 10-Apr-2014
 */

public class IProcessToStudioAMXBPMConverterFramework implements
        ITransformationStylesheetsProvider,
        ITransformationCharsetEncodingProvider {

    /**
     * Name of the Hidden Process Packages Special folder, to which the iProcess
     * files will be copied for temp conversion purposes, the final destination
     * of the converted XPDLs will be the process packages special folder
     * selected in the Import Wizard Page.
     */
    public static final String TEMP_PROCESS_PACKAGES_FOLDER = ".iProcessXpdls"; //$NON-NLS-1$

    /**
     * xslt used for IPM to IPS transformation
     */
    private URL[] xslts;

    /**
     * Temporary folder '.iProcessXpdls' used as the working area for Import,
     * where the files are copied by the Wizard.
     */
    private IFolder tempImportFolder;

    /**
     * Char encoding used while transformation of IPS to IPM
     */
    private String currentTransformingInputCharEncoding = null;

    /**
     * Collection of Converted XPDLs for BPM Destination.
     */
    private List<IFile> convertedXPDLsForBPM;

    /**
     * The extension point manager from which we load extension contributions.
     */
    private IProcessToBPMConversionExtensionPointManager extensionPointManager;

    /**
     * Debug Manager , to be used for writing debug files for various debug
     * levels.
     */
    private DebugResourcesManager debugManager;

    /**
     * Map of process name and its originating iProcess XPDL, to be filled by
     * Separate Processes API.
     */
    private Map<String, String> processOriginatingXpdl =
            new LinkedHashMap<String, String>();

    /**
     * Constructor takes parent project of the process package special folder
     * and the Debug mode, read the debug mode from the preference , or else
     * pass {@link DEBUG_MODE}.NONE as default.
     * 
     * @param targetProject
     *            , parent project of the import target process packages special
     *            folder.
     * @param debugPreference
     *            , debug mode preference .
     */
    public IProcessToStudioAMXBPMConverterFramework(IProject targetProject,
            DEBUG_MODE debugPreference) {
        super();

        // Custom Conversions Manager
        extensionPointManager =
                new IProcessToBPMConversionExtensionPointManager();

        // initialise debug manager
        debugManager =
                new DebugResourcesManager(targetProject, debugPreference);
    }

    /**
     * First Conversion to convert the iPM XPDLs to Studio AMX BPM XPDLs.It
     * takes the iPM XPDL {@link IResource}s as input. Use this method , to
     * convert IPM XPDL to AMX BPM XPDL.Throws exception if target folder is not
     * a Process package Special Folder.The method starts with creating the temp
     * working hidden Process Package Special Folder, and copying iProcess xpdls
     * to be imported to the temp folder.
     * 
     * @param importedXPDLs
     *            , {@link Collection} of IPM XPDLs to be converted for BPM
     *            Destination.
     * @param targetFolder
     *            , target Process Packages special Folder to which the
     *            converted BPM XPDLs will be saved.
     * @param monitor
     * @return {@link IStatus} representing the Errors/Warnings/Info encountered
     *         in the Conversion of iProcess XPDL to BPM XPDL.
     * @throws OperationCanceledException
     * @throws CoreException
     */
    public IStatus convertIPMToStudioAMXBPM(Collection<File> iPMXPDLs,
            IFolder targetFolder, IProgressMonitor monitor)
            throws OperationCanceledException, CoreException {
        // latest Individual conversion sub-step status
        IStatus convertStatus;
        // Collective/Root Conversion Status
        MultiStatus returnStatus;
        try {

            monitor.beginTask(Messages.IProcessToStudioAMXBPMConverterFramework_MsgStartIPMToBPMConversion,
                    5);
            // sometimes beingTask does not reflect the task name
            monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_MsgStartIPMToBPMConversion);

            String specialFolderKind =
                    SpecialFolderUtil.getSpecialFolderKind(targetFolder);

            // Do not allow conversion when target folder is not a process
            // packages special folder.
            if (specialFolderKind == null
                    || !Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND
                            .equals(specialFolderKind)) {

                return new Status(
                        Status.ERROR,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        Messages.IProcessToStudioAMXBPMConverterFramework_ErrorTargetFolderNotProcessPackagesFolder);

            }

            /*
             * Set up an OK multi status. If we add an ERROR status child then
             * whole status will become ERROR (then same for Warning / INFO and
             * finally OK). i.e. multi status inherits most important code of
             * all nested children.
             */
            returnStatus =
                    new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                            "", null); //$NON-NLS-1$

            if (iPMXPDLs != null && !iPMXPDLs.isEmpty()) {
                // Create Hidden temp working folder
                tempImportFolder = getOrCreateTempContainer(targetFolder);

                // Import/copy Files to Temp folder in Workspace
                Collection<IFile> importedXPDLs =
                        performCopyXPDLsToTempFolder(tempImportFolder,
                                iPMXPDLs,
                                new SubProgressMonitor(monitor, 1));

                // 1. Convert IPM XPDL to Studio iProcess

                Collection<IFile> studioIProcessXPDLs = new ArrayList<IFile>();
                /*
                 * XPD-6361: Saket: Adding time stamp indicating the start of
                 * conversion from iPM to iPS.
                 */
                logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_IPMToIPS_TimeStamp,
                        Messages.LogInfoStatus_Started);

                convertStatus =
                        convertIPMToIPS(importedXPDLs,
                                studioIProcessXPDLs,
                                tempImportFolder,
                                new SubProgressMonitor(monitor, 1));

                logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_IPMToIPS_TimeStamp,
                        Messages.LogInfoStatus_Completed);

                // Collect Status
                returnStatus.add(convertStatus);

                if (returnStatus.getSeverity() == IStatus.ERROR) {
                    // return on Error
                    return returnStatus;
                }

                if (monitor.isCanceled()) {
                    monitor.done();
                    throw new OperationCanceledException();
                }
                /*
                 * XPD-6361: Saket: Adding time stamp indicating the start of
                 * conversion from iPM to iPS.
                 */
                logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_Migration_TimeStamp,
                        Messages.LogInfoStatus_Started);

                // 2. Migrate XPDL to current Studio Format.
                convertStatus =
                        migrateToCurrentStudioVersion(studioIProcessXPDLs,
                                new SubProgressMonitor(monitor, 1));

                logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_Migration_TimeStamp,
                        Messages.LogInfoStatus_Completed);
                // collect status
                returnStatus.add(convertStatus);

                // / Write Debug files post Migrations
                returnStatus
                        .add(debugManager
                                .writeDebugFileforIPMToIPSConversion(studioIProcessXPDLs));

                if (returnStatus.getSeverity() == IStatus.ERROR) {
                    return returnStatus;
                }

                if (monitor.isCanceled()) {
                    monitor.done();
                    throw new OperationCanceledException();
                }

                // Invoke any life-cycle listeners.
                returnStatus
                        .add(invokeLifeCycleListeners_ImportComplete(studioIProcessXPDLs,
                                new SubProgressMonitor(monitor, 1)));

                if (returnStatus.getSeverity() == IStatus.ERROR) {
                    return returnStatus;
                }

                if (monitor.isCanceled()) {
                    monitor.done();
                    throw new OperationCanceledException();
                }

                // Call to Second API for Conversion of Studio iProcess XPDL to
                // Studio AMX BPM XPDL
                convertStatus =
                        convertStudioIProcessToStudioAMXBPM(studioIProcessXPDLs,
                                targetFolder,
                                new SubProgressMonitor(monitor, 1));

                returnStatus.add(convertStatus);

            } else {
                return new Status(
                        IStatus.ERROR,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        Messages.IProcessToStudioAMXBPMConverterFramework_ImportXPDLsListNullOrEmptyMsg);
            }
        } catch (CoreException ex) {

            return new Status(IStatus.ERROR,
                    IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                    ex.getLocalizedMessage(), ex);
        } finally {
            cleanUpTempFolder();
            monitor.done();
        }
        return returnStatus;
    }

    /**
     * Logs the TimeStap with the given Message String.
     * 
     * @param msg
     */
    public static void logTimeStampMsg(String status, String msg) {

        IProcessAMXBPMConverterPlugin
                .getDefault()
                .getLogger()
                .info(String.format(Messages.IProcessToStudioAMXBPMConverterFramework_TimeStampLog_msg,
                        status,
                        msg,
                        Objects.toString(System.currentTimeMillis())));
    }

    /**
     * The Second Conversion API, which converts the Studio iProcess XPDLs to
     * Studio AMX BPM XPDLs.Takes Studio iProcess XPDL resources as input. Use
     * this method to convert Studio iProcess XPDL to Studio AMX BPM XPDL.
     * 
     * @param studioIProcessXPDLs
     *            , Studio XPDL for iProcess destination.
     * @param targetFolder
     *            , target location for the converted XPDL resources.
     * @param monitor
     * @return {@link IStatus} representing the Errors/Warnings/Info encountered
     *         in the Conversion of Studio iProcess XPDL to BPM XPDL.
     * @throws OperationCanceledException
     * @throws CoreException
     */
    public IStatus convertStudioIProcessToStudioAMXBPM(
            Collection<IFile> studioIProcessXPDLs, IFolder targetFolder,
            IProgressMonitor monitor) throws OperationCanceledException,
            CoreException {
        // Status of Individual Step
        IStatus convertStatus;
        // Collective/Root Status
        MultiStatus returnStatus;
        try {

            /*
             * Set up an OK multi status. If we adds an ERROR status child then
             * whole status will become ERROR (then same for Warning / INFO and
             * finally OK). i.e. multi status inherits most important code of
             * all nested children.
             */
            returnStatus =
                    new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                            "", null); //$NON-NLS-1$

            monitor.beginTask("", 5); //$NON-NLS-1$

            // Extract Packages from the XPDL
            Collection<Package> studioIProcessXPDLPackages =
                    getXPDLPackages(studioIProcessXPDLs);

            /*
             * Get the version of the target Project. This version will be set
             * as the Package version
             */
            String projectVersion = getProjectVersion(targetFolder);

            // 3. Separate Processes to their own packages.

            /*
             * XPD-6361: Saket: Adding time stamp indicating the start of
             * separation of process packages.
             */
            logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_SeparateProcessPackages_TimeStamp,
                    Messages.LogInfoStatus_Started);

            ProcessPackageSeparator processSeperator =
                    new ProcessPackageSeparator(studioIProcessXPDLPackages,
                            projectVersion);

            Set<Package> seperatedProcessPackages =
                    new LinkedHashSet<Package>();

            convertStatus =
                    processSeperator.separateProcesses(new SubProgressMonitor(
                            monitor, 1),
                            seperatedProcessPackages,
                            processOriginatingXpdl);

            logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_SeparateProcessPackages_TimeStamp,
                    Messages.LogInfoStatus_Completed);
            // collect status
            returnStatus.add(convertStatus);

            // Write Debug files after for separated processes
            convertStatus =
                    debugManager
                            .writeDebugFilesForSeparatedProcesses(seperatedProcessPackages);
            returnStatus.add(convertStatus);

            if (returnStatus.getSeverity() == IStatus.ERROR) {
                // return on Error
                return returnStatus;
            }

            if (monitor.isCanceled()) {
                monitor.done();
                throw new OperationCanceledException();
            }

            // Invoke contributed life-cycle listeners.
            returnStatus
                    .add(invokeLifeCycleListeners_PkgSeparateComplete(seperatedProcessPackages,
                            new SubProgressMonitor(monitor, 1)));

            if (returnStatus.getSeverity() == IStatus.ERROR) {
                // return on Error
                return returnStatus;
            }

            if (monitor.isCanceled()) {
                monitor.done();
                throw new OperationCanceledException();
            }

            // NOTE: From here on the Framework will work on only Packages and
            // not IResources ,till the final clean up stage where the packages
            // will be saved to files, and some might get discarded.Also XPDLs
            // worked on will be removed,

            // * 4. Execute Conversion Extensions.

            /*
             * XPD-6361: Saket: Adding time stamp indicating the start of
             * execution of conversion extensions.
             */
            logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_ExcecuteExtensions_TimeStamp,
                    Messages.LogInfoStatus_Started);

            convertStatus =
                    doCustomConversions(seperatedProcessPackages,
                            new SubProgressMonitor(monitor, 1));

            logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_ExcecuteExtensions_TimeStamp,
                    Messages.LogInfoStatus_Completed);
            // collect status
            returnStatus.add(convertStatus);

            if (returnStatus.getSeverity() == IStatus.ERROR) {
                // return on error
                return returnStatus;
            }

            if (monitor.isCanceled()) {
                monitor.done();
                throw new OperationCanceledException();
            }

            // Invoke contributed life-cycle listeners.
            returnStatus
                    .add(invokeLifeCycleListeners_ConversionExtensionsComplete(seperatedProcessPackages,
                            new SubProgressMonitor(monitor, 1)));

            if (returnStatus.getSeverity() == IStatus.ERROR) {
                // return on Error
                return returnStatus;
            }

            if (monitor.isCanceled()) {
                monitor.done();
                throw new OperationCanceledException();
            }

            // 5. Final Clean up.

            /*
             * XPD-6361: Saket: Adding time stamp indicating the start of final
             * clean up and save.
             */
            logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_FinalCleanUpAndSave_TimeStamp,
                    Messages.LogInfoStatus_Started);
            convertStatus =
                    doFinalCleanUpAndSave(seperatedProcessPackages,
                            processOriginatingXpdl,
                            targetFolder,
                            studioIProcessXPDLs,
                            new SubProgressMonitor(monitor, 1));

            logTimeStampMsg(Messages.IProcessToStudioAMXBPMConverterFramework_FinalCleanUpAndSave_TimeStamp,
                    Messages.LogInfoStatus_Completed);
            // collect status
            returnStatus.add(convertStatus);

        } catch (CoreException e) {

            return createErrorStatus(e.getLocalizedMessage(), e);

        } finally {

            monitor.done();
        }

        return returnStatus;

    }

    /**
     * * This method deals with all the resources that the IPS xpdls being
     * converted depend upon. For now this method does the following
     * <p>
     * Gets all the Wsdl's / Xsd's that the XPDL depend upon and copies them to
     * the target project Service descriptor special folder.
     * <p>
     * Gets all the Forms referenced by the user task and copies then to the
     * target Project Forms Special folder.
     * 
     * @param studioIProcessXPDLs
     *            the Studio iProcess Xpdl's
     * @param targetFolder
     *            the target process package folder.
     * @param monitor
     * @return {@link IStatus} of the dependent files copied to target Project
     * @throws CoreException
     */
    private IStatus performDependentResourceCopy(
            Collection<IFile> studioIProcessXPDLs, IFolder targetFolder,
            IProgressMonitor monitor) throws CoreException {

        MultiStatus status =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        try {
            monitor.beginTask("", 1); //$NON-NLS-1$
            monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_CopyingWsdlXsdMonitor_desc1);
            /*
             * Copy the dependent Wsdl and Xsd to the target BPM project Service
             * descriptor folder.
             */
            copyDependentWsdlAndXsdResources(studioIProcessXPDLs,
                    targetFolder,
                    monitor,
                    status);
            /*
             * Copy the dependent forms to the target BPM project FORMS folder.
             */
            copyDependentFormResources(studioIProcessXPDLs,
                    targetFolder,
                    monitor,
                    status);

            monitor.worked(1);

        } finally {

            monitor.done();
        }
        return status;
    }

    /**
     * Copies the dependent Forms resources to the target project Forms special
     * folder. Note that we copy the entire contents of the parent folder of the
     * referenced FORM as the parent folder may contain additional data which
     * might be required.
     * 
     * @param studioIProcessXPDLs
     *            the Studio iProcess Xpdl files.
     * @param targetFolder
     *            the target Process Packages Folder to which the Xpdls are to
     *            be converted and saved.
     * @param monitor
     *            the progression monitor to track the progress
     * @param status
     * @throws CoreException
     */
    private void copyDependentFormResources(
            Collection<IFile> studioIProcessXPDLs, IFolder targetFolder,
            IProgressMonitor monitor, MultiStatus status) throws CoreException {

        Set<IResource> dependentFormResources = new HashSet<IResource>();

        for (IResource eachResource : studioIProcessXPDLs) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(eachResource);

            if (wc != null) {
                /*
                 * XPD-6742 : need to check if working copy is not null because
                 * during IPM to BPM conversion the studioIProcessXpdls are not
                 * associated with the model.
                 */
                EObject rootElement = wc.getRootElement();

                if (rootElement instanceof Package) {

                    Package pkg = (Package) rootElement;

                    EList<Process> processes = pkg.getProcesses();

                    for (Process process : processes) {
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(process);

                        for (Activity activity : allActivitiesInProc) {

                            IFile userTaskFormFile =
                                    TaskObjectUtil
                                            .getUserTaskFormFile(activity);

                            if (userTaskFormFile != null) {

                                IContainer parentFormsFolder =
                                        userTaskFormFile.getParent();
                                /*
                                 * Add the parent folder of the dependent form
                                 * as we want to copy the entire contents of the
                                 * parent folder to the destination
                                 */
                                if (parentFormsFolder != null) {
                                    dependentFormResources
                                            .add(parentFormsFolder);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!dependentFormResources.isEmpty()) {

            String copiedFileNames =
                    copyDependentResources(targetFolder.getProject(),
                            dependentFormResources,
                            monitor,
                            TaskObjectUtil.FORMS_KIND);

            if (!copiedFileNames.isEmpty()) {
                status.add(new Status(
                        IStatus.INFO,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        String.format(Messages.IProcessToStudioAMXBPMConverterFramework_FormsResourcesCopied_msg,
                                copiedFileNames)));
            }
        }
    }

    /**
     * Copies the Resources passed under the Target project Special folder in a
     * similar way as they were in the source project.
     * 
     * @param targetBpmProject
     *            the target BPM project
     * @param resourcesToCopy
     *            the resources (Files and Folders) to copy
     * @param monitor
     *            the progress monitor to track progress
     * @param specialFolderKind
     *            the Special folder to which the files/folders should be copied
     * @return the comma separated file/folder names which were copied to the
     *         target folder, caller of this method can use this string to
     *         output status
     * @throws CoreException
     */
    private String copyDependentResources(IProject targetBpmProject,
            Set<IResource> resourcesToCopy, IProgressMonitor monitor,
            String specialFolderKind) throws CoreException {
        /**
         * Stores and returns the comma separated names of the copied
         * Files/Folders, so that the user can use it in the output message.
         */
        StringBuffer copiedFileNames = new StringBuffer();

        IFolder targetProjectSpecialFolder = null;

        List<SpecialFolder> allSpecialFoldersOfKind =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(targetBpmProject,
                        specialFolderKind);

        for (SpecialFolder specialFolder : allSpecialFoldersOfKind) {
            /*
             * Scan through all the special folders to get the first
             * non-generated special folder
             */
            String generated = specialFolder.getGenerated();

            if (generated == null || generated.isEmpty()) {
                /*
                 * break if we found the first non-generated special folder.
                 */
                targetProjectSpecialFolder = specialFolder.getFolder();
                break;
            }
        }

        if (targetProjectSpecialFolder != null) {

            int size = resourcesToCopy.size(), indexForComma = 1;

            for (IResource eachResource : resourcesToCopy) {

                IFolder parentFolder = targetProjectSpecialFolder;

                IPath specialFolderRelativePath =
                        SpecialFolderUtil
                                .getSpecialFolderRelativePath(eachResource,
                                        specialFolderKind);

                if (specialFolderRelativePath == null
                        || specialFolderRelativePath.isEmpty()) {
                    continue;
                }
                /*
                 * the last segment has the name of the resource to copy file
                 * name
                 */
                String nameOfResourceToCopy =
                        specialFolderRelativePath.lastSegment();

                /*
                 * remove the last segment as it is the name of the resource to
                 * copy
                 */
                IPath pathWithFileNameRemoved =
                        specialFolderRelativePath.removeLastSegments(1);

                if (pathWithFileNameRemoved != null
                        && !pathWithFileNameRemoved.isEmpty()) {

                    IPath pathConatiningFolderHierarchy =
                            pathWithFileNameRemoved;

                    if (pathConatiningFolderHierarchy != null
                            && !pathConatiningFolderHierarchy.isEmpty()) {
                        /* create folders below service descriptor */
                        String[] segments =
                                pathConatiningFolderHierarchy.segments();

                        for (String eachFolderName : segments) {
                            IFolder folder =
                                    parentFolder.getFolder(eachFolderName);
                            if (!folder.exists()) {
                                /*
                                 * if the folder does not exist, create it
                                 */
                                folder.create(true, true, monitor);

                            }
                            /*
                             * parent Folder = new folder created/ exising
                             * folder
                             */
                            parentFolder = folder;
                        }
                    }
                }

                if (!(parentFolder.getFile(nameOfResourceToCopy).exists() || parentFolder
                        .getFolder(nameOfResourceToCopy).exists())) {
                    /*
                     * Copy the resource only if it already does not exists in
                     * the target folder.
                     */
                    IPath fullPath =
                            parentFolder.getFullPath()
                                    .append(nameOfResourceToCopy);

                    eachResource.copy(fullPath, true, monitor);
                    /*
                     * Add the copied resource name to the buffer
                     */
                    copiedFileNames.append(nameOfResourceToCopy);
                    if (indexForComma != size) {
                        copiedFileNames.append(", "); //$NON-NLS-1$
                    }
                } else if (parentFolder.getFolder(nameOfResourceToCopy)
                        .exists()) {
                    /*
                     * XPD-6683: if the target folder to copy already exist then
                     * recursively copy all the Resources present in the folder
                     * which do not exist.
                     */
                    IFolder alreadyExistingFolderInTargetBpmProject =
                            parentFolder.getFolder(nameOfResourceToCopy);

                    IFolder iProcessProjectFolderMembersTocopy =
                            (IFolder) eachResource;

                    recursivelyCopyFolderContents(iProcessProjectFolderMembersTocopy,
                            alreadyExistingFolderInTargetBpmProject,
                            monitor);

                    /*
                     * Add the copied resource name to the buffer
                     */
                    copiedFileNames.append(nameOfResourceToCopy);
                    if (indexForComma != size) {
                        copiedFileNames.append(", "); //$NON-NLS-1$
                    }
                }
                indexForComma++;
            }
        }

        return copiedFileNames.toString();
    }

    /**
     * Recursively copies all the contents on all the sub-folder that already
     * exist in the BPM project.
     * 
     * @param monitor
     * @param iProcessProjectFolderMembersTocopy
     * @param alreadyExistingFolderInTargetBpmProject
     * @throws CoreException
     */
    private void recursivelyCopyFolderContents(
            IFolder sourceiProcessProjectFolderToCopy,
            IFolder targetBpmProjectFolder, IProgressMonitor monitor)
            throws CoreException {

        IResource[] resourcesInsideFolderToCopy =
                sourceiProcessProjectFolderToCopy.members();
        /*
         * copy all the members in the folder.
         */
        for (IResource resToCopy : resourcesInsideFolderToCopy) {

            String nameOfResToCopy = resToCopy.getName();

            if ((resToCopy instanceof IFile)
                    && !(targetBpmProjectFolder.getFile(nameOfResToCopy)
                            .exists())) {

                IPath fullPath =
                        targetBpmProjectFolder.getFullPath()
                                .append(nameOfResToCopy);

                resToCopy.copy(fullPath, true, monitor);

            } else if (resToCopy instanceof IFolder) {

                if (!targetBpmProjectFolder.getFolder(nameOfResToCopy).exists()) {
                    IPath fullPath =
                            targetBpmProjectFolder.getFullPath()
                                    .append(nameOfResToCopy);

                    resToCopy.copy(fullPath, true, monitor);
                } else {

                    recursivelyCopyFolderContents((IFolder) resToCopy,
                            targetBpmProjectFolder.getFolder(nameOfResToCopy),
                            monitor);
                }
            }
        }
    }

    /**
     * Copies the dependent WSDL and XSD resources to the target Project Service
     * Descriptor special folder.
     * 
     * @param studioIProcessXPDLs
     *            the Studio iProcess Xpdl files.
     * @param targetFolder
     *            the target Process Packages Folder to which the Xpdls are to
     *            be converted and saved.
     * @param monitor
     *            the progression monitor to track the progress
     * @param status
     * @throws CoreException
     */
    private void copyDependentWsdlAndXsdResources(
            Collection<IFile> studioIProcessXPDLs, IFolder targetFolder,
            IProgressMonitor monitor, MultiStatus status) throws CoreException {

        /**
         * Stores all the dependent Wsdl's that the xpdls being converted
         * require.
         */
        Set<IResource> dependentWsdlsOrXsdResources = new HashSet<IResource>();

        for (IResource eachResource : studioIProcessXPDLs) {
            /*
             * Scan through all the xpdls being converted and get all deep
             * dependencies
             */
            Set<IResource> deepDependencies =
                    WorkingCopyUtil.getDeepDependencies(eachResource);

            for (IResource iResource : deepDependencies) {
                /*
                 * we are only interested in Wsdl and XSD files
                 */
                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopy(iResource);

                if (workingCopy instanceof WsdlWorkingCopy
                        || workingCopy instanceof XSDWorkingCopy) {

                    dependentWsdlsOrXsdResources.add(iResource);
                }
            }
        }

        if (!dependentWsdlsOrXsdResources.isEmpty()) {

            IProject project = targetFolder.getProject();

            /*
             * copy dependent wsdl/xsd to the target project service descriptor
             * folder.
             */
            String copiedFileNames =
                    copyDependentResources(project,
                            dependentWsdlsOrXsdResources,
                            monitor,
                            Activator.WSDL_SPECIALFOLDER_KIND);

            if (!copiedFileNames.isEmpty()) {
                status.add(new Status(
                        IStatus.INFO,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        String.format(Messages.IProcessToStudioAMXBPMConverterFramework_WsdlXsdResourcesCopied_msg,
                                copiedFileNames)));
            }
        }
    }

    /**
     * 
     * @param targetFolder
     *            the Folder whose Project version is to be found
     * @return the Project version of the passed targetFolder.
     */
    private String getProjectVersion(IFolder targetFolder) {

        String projVersion = null;
        IContainer parentContainer = targetFolder.getParent();
        if (parentContainer instanceof IProject) {

            IProject targetProject = (IProject) parentContainer;
            projVersion = ProjectUtil.getProjectVersion(targetProject);
        }
        /*
         * If the project version is null then return the default version i.e.
         * 1.0.0.qualifier
         */
        return projVersion != null ? projVersion : "1.0.0.qualifier"; //$NON-NLS-1$
    }

    /**
     * Converts/transforms IPM XPDL to IPS XPDL.
     * 
     * @param iPMXPDLs
     *            , IPM Xpdls to be converted to BPM XPDLs.
     * @param convertedXPDLs
     *            , list to be populated with converted xpdls.
     * @param tempWorkingFolder
     *            , temporary Working folder to be used in the conversion.
     * @param monitor
     * @return IStatus representing the errors, warnings and information for the
     *         caller.
     * @throws OperationCanceledException
     * @throws CoreException
     */

    private IStatus convertIPMToIPS(Collection<IFile> iPMXPDLs,
            Collection<IFile> convertedXPDLs, IFolder tempWorkingFolder,
            IProgressMonitor monitor) throws OperationCanceledException,
            CoreException {

        // If cancel was clicked in the dialog then quit here
        if (monitor.isCanceled()) {
            monitor.done();
            throw new OperationCanceledException();
        }
        Thread current = Thread.currentThread();
        ClassLoader oldLoader = current.getContextClassLoader();
        current.setContextClassLoader(getClass().getClassLoader());

        IFile fe;
        File inputFile;

        final IContainer destination = tempWorkingFolder;
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream byteInputStream = null;

        try {

            monitor.beginTask(Messages.IProcessToStudioAMXBPMConverterFramework_MsgTransformIPMToIPS,
                    iPMXPDLs.size());
            // sometimes begintask does not reflects the task name
            monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_MsgTransformIPMToIPS);

            ImportExportTransformer transformer =
                    new ImportExportTransformer(this);

            // Process each selected file
            for (Iterator<IFile> iter = iPMXPDLs.iterator(); iter.hasNext();) {
                // If cancel was clicked in the dialog then quit here
                if (monitor.isCanceled()) {
                    monitor.done();
                    throw new OperationCanceledException();
                }

                fe = iter.next();

                inputFile = new File(fe.getLocationURI());

                // Update progress monitor
                monitor.subTask(inputFile.getName());

                // Get the destination file to create
                final IFile outputFile =
                        destination.getFile(new Path(
                                getOutputIPSFileName(inputFile)));

                // If we can progress with transforming the file then do
                // so Prepare the streams

                // Save the input character encoding for the current
                // input file. transformer.performTransformation() will call
                // getInput/OutputCharsetEncoding() methods dring its
                // processing.
                currentTransformingInputCharEncoding =
                        AbstractImportWizard.getXmlEncoding(inputFile);

                inputStream = new FileInputStream(inputFile);
                outputStream = new ByteArrayOutputStream();
                // Transform the file
                transformer.performTransformation(inputStream, outputStream);

                ByteArrayInputStream resultInStream =
                        new ByteArrayInputStream(outputStream.toByteArray());

                if (!outputFile.exists()) {

                    outputFile.create(resultInStream,
                            IResource.FORCE,
                            new NullProgressMonitor());

                } else {

                    outputFile.setContents(resultInStream,
                            IResource.FORCE,
                            new NullProgressMonitor());

                    /*
                     * SID SIA-46: Sometimes (timing dependent) setContents
                     * doesn't cause a reload to happen (or at least not one
                     * then propagates out a working copy reloaded notification
                     * (probably because we're in a workspce mod op). So Force a
                     * reload on the working copy to ensure editors are closed
                     * etc.
                     */
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(outputFile);
                    if (wc != null) {
                        wc.reLoad();
                    }
                }

                // Close all streams
                inputStream.close();
                outputStream.close();

                monitor.worked(1);

                outputFile.refreshLocal(IResource.DEPTH_ZERO, monitor);
                // add to the collection for the caller
                convertedXPDLs.add(outputFile);

                // If cancel was clicked in the dialog then stop process
                if (monitor.isCanceled()) {
                    monitor.done();
                    throw new OperationCanceledException();
                }

            } // end for (Next import resource).

            transformer = null;
        } catch (OperationCanceledException iE) {
            // rethrow and let the convert method handle it
            throw iE;
        } catch (Exception e) {

            return createErrorStatus(e.getLocalizedMessage(), e);

        } finally {
            current.setContextClassLoader(oldLoader);

            // Close all streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

                if (byteInputStream != null) {
                    byteInputStream.close();
                }
            } catch (IOException ioe) {
                ; // Do nothing as closing streams
            }

            monitor.done();
        }

        return Status.OK_STATUS;
    }

    /**
     * @param e
     * @return
     */
    private IStatus createErrorStatus(String message, Exception e) {
        IStatus status =
                new Status(IStatus.ERROR,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID, message, e);
        return status;
    }

    /**
     * Migrates the Studio XPDL to current version of Studio.
     * 
     * @param studioIProcessXPDLs
     *            , XPDL resource to be migrated.
     * @param monitor
     * @return {@link IStatus} representing the Errors/Warnings/Info encountered
     *         in the migration.
     * @throws OperationCanceledException
     * @throws CoreException
     */

    private IStatus migrateToCurrentStudioVersion(
            Collection<IFile> studioIProcessXPDLs, IProgressMonitor monitor)
            throws OperationCanceledException, CoreException {

        if (studioIProcessXPDLs != null) {
            try {
                monitor.beginTask(Messages.IProcessToStudioAMXBPMConverterFramework_MsgMigrateToCurrentStudioVersion,
                        studioIProcessXPDLs.size());
                // sometimes begintask does not reflects the task name
                monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_MsgMigrateToCurrentStudioVersion);

                for (IResource iResource : studioIProcessXPDLs) {

                    monitor.subTask(iResource.getName());
                    XpdlMigrate.migrate(iResource, true);
                    monitor.worked(1);

                    // If cancel was clicked in the dialog then quit here
                    if (monitor.isCanceled()) {
                        monitor.done();
                        throw new OperationCanceledException();
                    }

                }

            } catch (OperationCanceledException e) {
                // rethrow
                throw e;
            } catch (Exception e) {
                return createErrorStatus(e.getLocalizedMessage(), e);
            } finally {
                monitor.done();
            }
        }

        return Status.OK_STATUS;
    }

    /**
     * Extracts and returns the XPDL {@link Package} from the given XPDL
     * {@link IFile}s.
     * 
     * @param studioIProcessXPDLs
     * @return XPDL {@link Package} from the given {@link IResource}, returns
     *         empty list if this is not a valid XPDL resource.
     */
    private Collection<Package> getXPDLPackages(
            Collection<IFile> studioIProcessXPDLs) {

        Collection<Package> packages = new ArrayList<Package>();

        for (IFile xpdlFile : studioIProcessXPDLs) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);

            if (wc != null) {

                EObject rootElement = wc.getRootElement();

                if (rootElement instanceof Package) {

                    packages.add((Package) rootElement);
                }
            }
        }

        return packages;
    }

    /**
     * Uses {@link IProcessToBPMConversionExtensionPointManager} to load and run
     * the custom conversions on the XPDL packages.
     * 
     * @param xpdlPackages
     *            , iProcess XPDL {@link Package} to convert.
     * @param monitor
     * @return {@link IStatus} representing the Errors/Warnings/Info encountered
     *         in the Custom Conversions.
     * @throws OperationCanceledException
     */

    private IStatus doCustomConversions(Collection<Package> xpdlPackages,
            IProgressMonitor monitor) throws OperationCanceledException {

        /*
         * Sid XPD-6230. Move to constructor and created a field for ext point
         * manager to support additional use for life-cycle listener extensions.
         */
        // get Custom conversion extension contributions
        List<IProcessToBPMConversionExtension> convExtensions =
                extensionPointManager.getConverterExtensions();

        // return when there are no conversions to run
        if (convExtensions == null || convExtensions.size() == 0) {
            return new Status(
                    IStatus.INFO,
                    IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                    Messages.IProcessToStudioAMXBPMConverterFramework_NoCustomConversionAvailableMsg);
        }

        List<Process> processes = new LinkedList<Process>();
        List<ProcessInterface> processeInterfacesList =
                new LinkedList<ProcessInterface>();

        // collect all processes to convert
        for (Package xpdlPackage : xpdlPackages) {
            processes.addAll(xpdlPackage.getProcesses());
            // XPD-6155: Handle Process Interface
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(xpdlPackage);

            if (processInterfaces != null) {
                processeInterfacesList.addAll(processInterfaces
                        .getProcessInterface());
            }
        }

        try {
            /*
             * Set up an OK multi status. If we add an ERROR status child then
             * whole status will become ERROR (then same for Warning / INFO and
             * finally OK). i.e. multi status inherits most important code of
             * all nested children.
             */
            MultiStatus status =
                    new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                            "", null); //$NON-NLS-1$

            monitor.beginTask(Messages.IProcessToStudioAMXBPMConverterFramework_MsgRunCustomConversions,
                    convExtensions.size());
            monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_MsgRunCustomConversions);

            /* XPD-6231: Make lists passed to extension plugins unmodifiable */
            List<Process> unmodifiableProcessList =
                    Collections.unmodifiableList(processes);
            List<ProcessInterface> unmodifiableInterfaceList =
                    Collections.unmodifiableList(processeInterfacesList);

            int conversionExtIdx = 1;
            for (IProcessToBPMConversionExtension iProcessToBPMConversionExtension : convExtensions) {

                if (monitor.isCanceled()) {
                    monitor.done();
                    throw new OperationCanceledException();
                }

                /* Use description not id (for display in status msg */
                monitor.subTask(iProcessToBPMConversionExtension
                        .getDescription());

                AbstractIProcessToBPMContribution abstractIProcessToBPMContribution =
                        iProcessToBPMConversionExtension
                                .getAbstractIProcessToBPMContribution();

                /*
                 * XPD-6370: do 'executing extension' status message in the
                 * converter framework as the extension already has a
                 * description so no need to repeant in each contribution.
                 */
                status.add(new Status(
                        IStatus.INFO,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        String.format(Messages.IProcessToStudioAMXBPMConverterFramework_ExecutingConversion_msg,
                                iProcessToBPMConversionExtension
                                        .getDescription())));

                /* XPD-6231: Make lists passed to extension plugins unmodifiable */
                IStatus convertStat =
                        abstractIProcessToBPMContribution
                                .convert(unmodifiableProcessList,
                                        unmodifiableInterfaceList,
                                        new SubProgressMonitor(monitor, 1));

                /*
                 * Cannot predict what the contribution will do with progress
                 * message (if it set main task name then we will not reset it
                 * until our next call to setTaskname.
                 * 
                 * So Reset the task names after to clear things down.
                 */
                monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_MsgRunCustomConversions);
                monitor.subTask(iProcessToBPMConversionExtension
                        .getDescription());

                if (convertStat == null) {
                    /*
                     * As potentially calling 3rd party code - it's better to
                     * ensure against things like this)
                     */
                    convertStat =
                            new Status(
                                    IStatus.WARNING,
                                    IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                                    String.format(Messages.IProcessToStudioAMXBPMConverterFramework_ConversionContributionStatusNullMsg,
                                            iProcessToBPMConversionExtension
                                                    .getId(),
                                            iProcessToBPMConversionExtension
                                                    .getDescription()));
                }

                /* Collect the conversion statuses. */
                status.add(convertStat);

                // Write Debug files for this custom conversion
                status.add(debugManager
                        .writeDebugFilesForSpecificCustomConversion(xpdlPackages,
                                String.format("%02d_%s", //$NON-NLS-1$
                                        conversionExtIdx,
                                        iProcessToBPMConversionExtension
                                                .getId())));

                conversionExtIdx++;

                if (status.getSeverity() == IStatus.ERROR) {
                    /* When we find error stop now. */
                    return status;
                }
            }

            // Write Debug files after all custom conversions
            status.add(debugManager
                    .writeDebugFilesForAllCustomConversions(xpdlPackages));

            return status;

        } finally {
            monitor.done();
        }

    }

    /**
     * Final clean up step of the Conversion, discards the duplicate processes
     * [already existing in the workspace],saves final processes to their
     * respective XPDL file. Removes the Empty XPDLs/packages and removes the
     * XPDLs on which the conversion has been performed.
     * 
     * @param seperatedProcessPackages
     *            , Converted processes in their own packages.
     * @param processOriginatingXpdl
     *            , Map of Process/Interface name and their originating iProcess
     *            xpdl name.
     * @param targetFolder
     *            target location for the converted XPDL resources.
     * @param studioIProcessXPDLs
     *            Studio XPDL for iProcess destination.
     * @param monitor
     * @return {@link IStatus} elements representing Errors/Warning/Info
     *         encountered while Cleanup and Save.
     * @throws CoreException
     */
    private IStatus doFinalCleanUpAndSave(
            Collection<Package> seperatedProcessPackages,
            Map<String, String> processOriginatingXpdl, IFolder targetFolder,
            Collection<IFile> studioIProcessXPDLs, IProgressMonitor monitor)
            throws CoreException {
        // List to populate with saved converted XPDL.
        convertedXPDLsForBPM = new LinkedList<IFile>();

        List<Package> ignoredPackages = new ArrayList<Package>();

        Set<Package> packagesToSave = new HashSet<Package>();
        packagesToSave.addAll(seperatedProcessPackages);

        MultiStatus status =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        ResourceCleanupAndSave cleanUp =
                new ResourceCleanupAndSave(packagesToSave,
                        processOriginatingXpdl, targetFolder);

        try {
            monitor.beginTask(Messages.IProcessToStudioAMXBPMConverterFramework_SavingXPDLToWorkspace_msg,
                    3);
            monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_SavingXPDLToWorkspace_msg);

            status.add(cleanUp.startCleanupAndSave(new SubProgressMonitor(
                    monitor, 1), convertedXPDLsForBPM, ignoredPackages));

            if (status.getSeverity() == IStatus.ERROR) {
                // return on Error
                return status;
            }

            /*
             * XPD-6882 : Moved the call to copy dependent resources here
             * because we would want all dependent resources to be copied before
             * we invoke the lifecycle listener method 'conversionComplete()' so
             * that other teams(such as FORMS) will have these dependent
             * resources available.
             */
            // copy dependent resources to target project
            status.add(performDependentResourceCopy(studioIProcessXPDLs,
                    targetFolder,
                    new SubProgressMonitor(monitor, 1)));

            if (status.getSeverity() == IStatus.ERROR) {
                // return on Error
                return status;
            }

            // Invoke contributed life-cycle listeners.
            status.add(invokeLifeCycleListeners_ConversionsComplete(convertedXPDLsForBPM,
                    ignoredPackages,
                    new SubProgressMonitor(monitor, 1)));

            if (status.getSeverity() == IStatus.ERROR) {
                // return on Error
                return status;
            }

        } catch (Exception e) {
            return createErrorStatus(e.getMessage(), e);
        } finally {
            // finally cleanup the temp folder
            cleanUpTempFolder();
            monitor.done();

        }

        return status;
    }

    /**
     * Removes all files from the temporary working folder.
     * 
     * @throws CoreException
     */
    private void cleanUpTempFolder() throws CoreException {

        if (tempImportFolder != null && tempImportFolder.exists()) {
            /*
             * delete files only if the folder exists.
             */
            IResource[] members = tempImportFolder.members();

            /*
             * Delete all the files in the temporary folder.
             */
            for (IResource eachresource : members) {
                eachresource.delete(false, new NullProgressMonitor());
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider#getCharsetEncodingForStream(com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider.EncodingForStream)
     * 
     * @param encodingForStream
     * @return character set encoding to be used for transformation of IPS to
     *         IPM.
     */
    @Override
    public String getCharsetEncodingForStream(
            EncodingForStream encodingForStream) {

        if (EncodingForStream.INPUT.equals(encodingForStream)) {
            // Base the input character set encoding on the value in the
            // <output> header of the current input file.
            if (currentTransformingInputCharEncoding != null
                    && currentTransformingInputCharEncoding.length() > 0) {

                return currentTransformingInputCharEncoding;
            }

            return null;
        }

        // For output and xslts assume UTF-8 for now (subclass can always
        // override if it wants to.
        return "UTF-8"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXslts()
     * 
     * @return URLs of the xslts used in the transformation of IPS XPDL to IPM
     *         XPDL.
     */
    @Override
    public URL[] getXslts() {
        if (xslts == null) {
            xslts =
                    new URL[] { IProcessAMXBPMConverterPlugin.getDefault()
                            .getIpmToIpsXsltUrl() };
        }
        return xslts;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getSystemId()
     * 
     * @return
     */
    @Override
    public String getSystemId() {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXsltParameters()
     * 
     * @return xslt parameters used in IPS to IPM transformation.
     */
    @Override
    public Map<String, String> getXsltParameters() {

        Map<String, String> parameters = new HashMap<String, String>();

        IContainer destination = tempImportFolder;

        if (destination != null) {
            IProject project = destination.getProject();

            if (project != null) {
                parameters.put("project", project.getName()); //$NON-NLS-1$
            }
        }
        return parameters;
    }

    /**
     * Create a <code>CoreException</code> to wrap the given
     * <code>Throwable</code> object
     * 
     * @param throwable
     * @return , CoreException wrapping the given {@link Throwable}.
     */
    private CoreException generateCoreException(Throwable throwable) {
        return new CoreException(new Status(IStatus.ERROR,
                IProcessAMXBPMConverterPlugin.PLUGIN_ID, IStatus.OK,
                throwable.getLocalizedMessage(), throwable));
    }

    /**
     * Get the destination file name. This will replace the file extension of
     * the given source file with '.xpdl'.<br>
     * This method can be overwritten by the subclass to rename the imported
     * files.
     * 
     * @param srcFile
     *            , input iprocess XPDL File
     * @return String , filename to be used for copying the iProcess resource to
     *         temp folder.
     */
    protected String getOutputIPSFileName(File srcFile) {

        String szFileName = srcFile.getName();
        String szRetVal = null;

        // fix the file name before returning , here we are looking at something
        // abc.xpdl, where as the temp folder has something like '.abc.xpdl1'
        szRetVal = szFileName.substring(1, szFileName.length() - 1);

        return szRetVal;
    }

    /**
     * @return the {@link Collection} of XPDLs saved with the converted
     *         processes for BPM Destination.
     */
    public Collection<IFile> getConvertedXPDLsForBPM() {
        return convertedXPDLsForBPM;
    }

    /**
     * 
     * Copies the xpdl files to be imported , to the tempFolder.For further
     * processing of conversion the files will be picked from here.
     * 
     * @param tempFolder
     * @param xpdlFilesToCopy
     * @return copied xpdl files.
     * @throws OperationCanceledException
     * @throws CoreException
     */
    private Collection<IFile> performCopyXPDLsToTempFolder(IFolder tempFolder,
            Collection<File> xpdlFilesToCopy, IProgressMonitor monitor)
            throws OperationCanceledException, CoreException {

        // If cancel was clicked in the dialog then quit here
        if (monitor.isCanceled()) {
            monitor.done();
            throw new OperationCanceledException();
        }

        Collection<IFile> copiedXpdlFiles = new LinkedList<IFile>();
        File inputFile;
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream byteInputStream = null;

        try {

            monitor.beginTask(Messages.IProcessToStudioAMXBPMConverterFramework_MsgImportingIProcessXPDLs,
                    xpdlFilesToCopy.size());
            monitor.setTaskName(Messages.IProcessToStudioAMXBPMConverterFramework_MsgImportingIProcessXPDLs);

            // Process each selected file
            for (Iterator<File> iter = xpdlFilesToCopy.iterator(); iter
                    .hasNext();) {
                inputFile = iter.next();

                String outputFileName = getOutputFileName(inputFile);
                // Update progress monitor
                monitor.subTask(outputFileName);

                // Get the destination file to create
                final IFile outputFile =
                        tempFolder.getFile(new Path(outputFileName));

                // Prepare the streams
                inputStream = new FileInputStream(inputFile);

                outputStream = new ByteArrayOutputStream();
                ByteArrayInputStream resultInStream = null;

                int byteLength = inputStream.available();
                byte[] b = new byte[byteLength];
                inputStream.read(b);

                resultInStream = new ByteArrayInputStream(b);

                if (!outputFile.exists()) {
                    outputFile.create(resultInStream,
                            IResource.FORCE,
                            new NullProgressMonitor());
                } else {
                    outputFile.setContents(resultInStream,
                            IResource.FORCE,
                            new NullProgressMonitor());

                    /*
                     * SID SIA-46: Sometimes (timing dependent) setContents
                     * doesn't cause a reload to happen (or at least not one
                     * then propagates out a working copy reloaded notification
                     * (probably because we're in a workspce mod op). So Force a
                     * reload on the working copy to ensure editors are closed
                     * etc.
                     */
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(outputFile);
                    if (wc != null) {
                        wc.reLoad();
                    }
                }

                // Close all streams
                inputStream.close();
                outputStream.close();
                copiedXpdlFiles.add(outputFile);
                monitor.worked(1);

                outputFile.refreshLocal(IResource.DEPTH_ZERO, monitor);

                // If cancel was clicked in the dialog then stop process
                if (monitor.isCanceled()) {
                    break;
                }

            } // end for (Next import resource).

        } catch (CoreException e) {
            throw e;
        } catch (Exception e) {

            // Throw core exception
            throw generateCoreException(e);

        } finally {
            // Close all streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

                if (byteInputStream != null) {
                    byteInputStream.close();
                }
            } catch (IOException ioe) {
                ; // Do nothing as closing streams
            }

            monitor.done();
        }

        return copiedXpdlFiles;

    }

    /**
     * @param srcFile
     * @return output file name in format '.filename.xpdl1' for the xpdl file
     *         copied into the temp folder, the file is then saved as
     *         filename.xpdl, after IPM to IPS transformation
     */
    protected String getOutputFileName(File srcFile) {
        String szFileName = srcFile.getName();
        String szRetVal = null;

        szRetVal = szFileName.substring(0, szFileName.lastIndexOf('.'));
        szRetVal += Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT;
        return "." + szRetVal + "1"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Returns the temporary container resource created parallel to the import
     * target container specified in the Import Wizard Page's name entry field,
     * or <code>null</code> if such a container does not exist or could not be
     * created.
     * 
     * @param targetFolder
     *            , target folder to which the imported/converted xpdls will be
     *            saved.Temporary folder is created under the project of the
     *            target folder.
     * @return the temporary container resource created/located parallel to the
     *         specified target import container name entry field, or
     *         <code>null</code> if could not be created.
     * @throws CoreException
     */

    private IFolder getOrCreateTempContainer(IFolder targetFolder)
            throws CoreException {

        // Import will save the XPDL files to a temporary Processes package
        // hidden special folder.

        // Create Temp Process Packages Special Folder and return
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        final IPath path = targetFolder.getFullPath();

        if (workspace.getRoot().exists(path)) {

            // find selected Process Packages Special Folder
            IResource resource = workspace.getRoot().findMember(path);
            // get its Parent Project
            IProject iProject = resource.getProject();

            if (iProject != null && iProject.exists()) {
                // remove the last segment for the selected process packages
                // special folder, the temp folder will be created parallel to
                // the special folder selected as the import target.
                IPath specialFolderContainerPath = iProject.getFullPath();
                try {

                    return createHiddenSpecialFolder(specialFolderContainerPath,
                            iProject);

                } catch (CoreException e) {

                    IProcessAMXBPMConverterPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    String.format(Messages.IProcessToStudioAMXBPMConverterFramework_ErrorCreatingTempProcessPackageFolder,
                                            TEMP_PROCESS_PACKAGES_FOLDER));
                    // throw it to be displayed to user
                    throw e;
                }
            }

        }

        return null;
    }

    /**
     * Creates the temporary hidden Process Packages special folder. All
     * non-existing parent folders will also be created.The path should not be a
     * special folder, as a special folder can't contain another special
     * folder.Throws Exception when given path is a special folder.
     * 
     * @param path
     *            , path under which the temp folder will be created, generally
     *            under the project but may also exist under another older in a
     *            project.
     * @param iProject
     * @return IFolder temporary special folder, when created successfully, else
     *         returns null.
     * @throws CoreException
     */
    private static IFolder createHiddenSpecialFolder(IPath path,
            IProject iProject) throws CoreException {

        // if path is a special folder return with appropriate Exception
        IFolder pathFolder = iProject.getFolder(path);
        String specialFolderKind =
                SpecialFolderUtil.getSpecialFolderKind(pathFolder);

        if (specialFolderKind != null) {
            // given path is a special folder
            // throw exception with appropriate error
            IStatus status =
                    new Status(
                            Status.ERROR,
                            IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                            Messages.IProcessToStudioAMXBPMConverterFramework_ErrorTempSpecialFolderCreationUnderSpecialFolder);

            throw new CoreException(status);

        }

        // Create path for Hidden temp Import Folder
        IPath tempFolderPath = (IPath) path.clone();

        // append the Temp Hidden Process Packages folder
        tempFolderPath = tempFolderPath.append(TEMP_PROCESS_PACKAGES_FOLDER);

        // get temp folder
        IFolder folder = iProject.getFolder(TEMP_PROCESS_PACKAGES_FOLDER);

        if (folder != null && !folder.exists()) {

            IContainer parent = folder.getParent();

            if (!parent.exists()) {
                if (parent instanceof IFolder) {
                    // Create the parent
                    createHiddenSpecialFolder(((IFolder) parent).getProjectRelativePath(),
                            iProject);
                }

            }

            if (!folder.exists()) {
                folder.create(false, true, null);
            }

            folder.setHidden(true);

            // Mark as Special Folder
            // Get the project config
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(iProject);

            // Create a process package special folder
            SpecialFolder existingSpecialFolder =
                    projectConfig.getSpecialFolders().getFolder(folder);

            if (existingSpecialFolder == null) {

                projectConfig.getSpecialFolders().addFolder(folder,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
            }

        }
        return folder;
    }

    /**
     * Execute any LifeCycleListener contributions for the import-and-migrate
     * complete milestone.
     * 
     * @param studioIProcessXPDLs
     * 
     * @return Status.
     * @throws OperationCanceledException
     */
    private IStatus invokeLifeCycleListeners_ImportComplete(
            Collection<IFile> studioIProcessXPDLs, IProgressMonitor monitor)
            throws OperationCanceledException {
        MultiStatus status =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        try {
            List<IProcessToBPMLifeCycleExtension> listeners =
                    extensionPointManager.getLifeCycleListenerExtensions();

            if (listeners.size() > 0) {
                monitor.beginTask("", listeners.size()); //$NON-NLS-1$

                Collection<IFile> immutableProcessPackagesList =
                        Collections.unmodifiableCollection(studioIProcessXPDLs);

                for (IProcessToBPMLifeCycleExtension lifeCycleListener : listeners) {
                    if (monitor.isCanceled()) {
                        monitor.done();
                        throw new OperationCanceledException();
                    }

                    /*
                     * Call listener contribution
                     */
                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    SubProgressMonitor subMonitor =
                            new SubProgressMonitor(monitor, 1);

                    IStatus contributorStatus =
                            lifeCycleListener
                                    .getLifeCycleListener()
                                    .importAndMigrationComplete(immutableProcessPackagesList,
                                            subMonitor);

                    if (contributorStatus != null) {
                        /*
                         * We won't be too fussy about the status return from
                         * the listener methods as the implementer is likely to
                         * only implement the methods that they are really
                         * interested in.
                         */
                        status.add(contributorStatus);
                    }

                    /*
                     * Call done on the sub prog meter in case contribution did
                     * not. And reset the monitor messages back again in case
                     * contribution messed those up.
                     */
                    subMonitor.done();

                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    if (status.getSeverity() == IStatus.ERROR) {
                        return status;
                    }
                }
            }
        } finally {
            monitor.done();
        }

        return status;
    }

    /**
     * Execute any LifeCycleListener contributions for the process package
     * separation complete milestone.
     * 
     * @param studioIProcessPackages
     * 
     * @return Status.
     * @throws OperationCanceledException
     */
    private IStatus invokeLifeCycleListeners_PkgSeparateComplete(
            Collection<Package> studioIProcessPackages, IProgressMonitor monitor)
            throws OperationCanceledException {
        MultiStatus status =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        try {
            List<IProcessToBPMLifeCycleExtension> listeners =
                    extensionPointManager.getLifeCycleListenerExtensions();

            if (listeners.size() > 0) {
                monitor.beginTask("", listeners.size()); //$NON-NLS-1$

                Collection<Package> immutableProcessPackagesList =
                        Collections
                                .unmodifiableCollection(studioIProcessPackages);

                for (IProcessToBPMLifeCycleExtension lifeCycleListener : listeners) {
                    if (monitor.isCanceled()) {
                        monitor.done();
                        throw new OperationCanceledException();
                    }

                    /*
                     * Call listener contribution
                     */
                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    SubProgressMonitor subMonitor =
                            new SubProgressMonitor(monitor, 1);

                    IStatus contributorStatus =
                            lifeCycleListener
                                    .getLifeCycleListener()
                                    .packageSeparationComplete(immutableProcessPackagesList,
                                            subMonitor);

                    /*
                     * Call done on the sub prog meter in case contribution did
                     * not. And reset the monitor messages back again in case
                     * contribution messed those up.
                     */
                    subMonitor.done();

                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    if (contributorStatus != null) {
                        /*
                         * We won't be too fussy about the status return from
                         * the listener methods as the implementer is likely to
                         * only implement the methods that they are really
                         * interested in.
                         */
                        status.add(contributorStatus);
                    }

                    if (status.getSeverity() == IStatus.ERROR) {
                        return status;
                    }
                }
            }
        } finally {
            monitor.done();
        }

        return status;
    }

    /**
     * Execute any LifeCycleListener contributions for the
     * conversion-extensions-complete milestone.
     * 
     * @param studioIProcessPackages
     * 
     * @return Status.
     * @throws OperationCanceledException
     */
    private IStatus invokeLifeCycleListeners_ConversionExtensionsComplete(
            Collection<Package> studioIProcessPackages, IProgressMonitor monitor)
            throws OperationCanceledException {
        MultiStatus status =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        try {
            List<IProcessToBPMLifeCycleExtension> listeners =
                    extensionPointManager.getLifeCycleListenerExtensions();

            if (listeners.size() > 0) {
                monitor.beginTask("", listeners.size()); //$NON-NLS-1$

                /* Make sure we pass immutable lists to extension methods. */
                Collection<Package> immutableProcessPackagesList =
                        Collections
                                .unmodifiableCollection(studioIProcessPackages);

                List<AbstractIProcessToBPMContribution> cvtExtensions =
                        new ArrayList<AbstractIProcessToBPMContribution>();

                for (IProcessToBPMConversionExtension cvtExtension : extensionPointManager
                        .getConverterExtensions()) {
                    cvtExtensions.add(cvtExtension
                            .getAbstractIProcessToBPMContribution());
                }

                Collection<AbstractIProcessToBPMContribution> immutableCvtExtensionsList =
                        Collections.unmodifiableCollection(cvtExtensions);

                /* Notify listeners. */
                for (IProcessToBPMLifeCycleExtension lifeCycleListener : listeners) {
                    if (monitor.isCanceled()) {
                        monitor.done();
                        throw new OperationCanceledException();
                    }

                    /*
                     * Call listener contribution
                     */
                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    SubProgressMonitor subMonitor =
                            new SubProgressMonitor(monitor, 1);

                    IStatus contributorStatus =
                            lifeCycleListener
                                    .getLifeCycleListener()
                                    .conversionExtensionsComplete(immutableProcessPackagesList,
                                            immutableCvtExtensionsList,
                                            subMonitor);
                    /*
                     * Call done on the sub prog meter in case contribution did
                     * not. And reset the monitor messages back again in case
                     * contribution messed those up.
                     */
                    subMonitor.done();

                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    if (contributorStatus != null) {
                        /*
                         * We won't be too fussy about the status return from
                         * the listener methods as the implementer is likely to
                         * only implement the methods that they are really
                         * interested in.
                         */
                        status.add(contributorStatus);
                    }

                    if (status.getSeverity() == IStatus.ERROR) {
                        return status;
                    }
                }
            }
        } finally {
            monitor.done();
        }

        return status;
    }

    /**
     * Execute any LifeCycleListener contributions for the conversion-completed
     * milestone.
     * 
     * @param convertedFiles
     * 
     * @param convertedPackages
     * @param ignoredPackages2
     * 
     * @return Status.
     * @throws OperationCanceledException
     */
    private IStatus invokeLifeCycleListeners_ConversionsComplete(
            List<IFile> convertedFiles, List<Package> ignoredPackages,
            IProgressMonitor monitor) throws OperationCanceledException {
        MultiStatus status =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        try {
            List<IProcessToBPMLifeCycleExtension> listeners =
                    extensionPointManager.getLifeCycleListenerExtensions();

            if (listeners.size() > 0) {
                monitor.beginTask("", listeners.size()); //$NON-NLS-1$

                /* Make sure we pass immutable lists to extension methods. */
                List<Package> convertedPackages = new ArrayList<Package>();
                for (IFile cvtFile : convertedFiles) {
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(cvtFile);
                    if (wc != null && wc.getRootElement() instanceof Package) {
                        convertedPackages.add((Package) wc.getRootElement());
                    }
                }

                Collection<Package> immutableConvertedPkgsList =
                        Collections.unmodifiableCollection(convertedPackages);

                Collection<Package> immutableIgnoredPackagesList =
                        Collections.unmodifiableCollection(ignoredPackages);

                Collection<IFile> immutableConvertedFilesList =
                        Collections.unmodifiableCollection(convertedFiles);

                /* Notify listeners. */
                for (IProcessToBPMLifeCycleExtension lifeCycleListener : listeners) {
                    if (monitor.isCanceled()) {
                        monitor.done();
                        throw new OperationCanceledException();
                    }

                    /*
                     * Call listener contribution
                     */
                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    SubProgressMonitor subMonitor =
                            new SubProgressMonitor(monitor, 1);

                    IStatus contributorStatus =
                            lifeCycleListener
                                    .getLifeCycleListener()
                                    .conversionComplete(immutableConvertedFilesList,
                                            immutableConvertedPkgsList,
                                            immutableIgnoredPackagesList,
                                            subMonitor);

                    /*
                     * Call done on the sub prog meter in case contribution did
                     * not. And reset the monitor messages back again in case
                     * contribution messed those up.
                     */
                    subMonitor.done();

                    monitor.subTask(String
                            .format(Messages.IProcessToStudioAMXBPMConverterFramework_InvokeLifeCycleListeners_msg,
                                    lifeCycleListener.getDescription()));

                    if (contributorStatus != null) {
                        /*
                         * We won't be too fussy about the status return from
                         * the listener methods as the implementer is likely to
                         * only implement the methods that they are really
                         * interested in.
                         */
                        status.add(contributorStatus);
                    }

                    if (status.getSeverity() == IStatus.ERROR) {
                        return status;
                    }
                }
            }
        } finally {
            monitor.done();
        }

        return status;
    }
}
