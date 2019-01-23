/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.bpel.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import com.tibco.bx.xpdl2bpel.converter.XPDL2BPEL;
import com.tibco.bx.xpdl2bpel.converter.XPDL2WSDL;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.n2.bpel.BpelBuilderActivator;
import com.tibco.xpd.n2.bpel.nature.BPELNature;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdlgen.WsdlGenBuilderTransformer;
import com.tibco.xpd.xpdExtension.RESTServices;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author kupadhya
 * 
 */
public class BPELN2Utils {

    /**
     * 
     */
    private static final String WSDL_FILE_EXTENSION = "wsdl";

    /**
     * 
     */
    private static final String BPEL_FILE_EXTENSION = "bpel";

    public static final String BPEL_NATURE_ID = BPELNature.NATURE_ID;

    public static final String BPEL_EXTENSION = ".bpel"; //$NON-NLS-1$

    public static String BPEL_ROOT_OUTPUTFOLDER_NAME = ".processOut"; //$NON-NLS-1$

    public static String BP_OUTPUTFOLDER_NAME = "process"; //$NON-NLS-1$

    public static String PF_OUTPUTFOLDER_NAME = "pageflow"; //$NON-NLS-1$

    public static final String BPEL_ERROR_MARKER_ID = "BPEL_ERROR_MARKER_ID"; //$NON-NLS-1$

    /**
     * convert xpdl file
     * 
     * @param file
     *            : the xpdl file
     * @param distFolder
     *            : the dist folder of conversion.if the folder not exist then
     *            it will be created
     * @param monitor
     * @throws CoreException
     */
    public static void buildXPDLFile(IResource xpdlFile,
            IFolder businessProcessDestFolder, IFolder pageFlowDestFolder,
            IProgressMonitor monitor) throws CoreException {

        try {
            monitor.beginTask("", IProgressMonitor.UNKNOWN); //$NON-NLS-1$

            IFile file = (IFile) xpdlFile;
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);

            if (wc != null) {

                EObject root = wc.getRootElement();
                if (root instanceof Package) {

                    Package xpdlPackage = (Package) root;
                    EList<com.tibco.xpd.xpdl2.Process> processes =
                            xpdlPackage.getProcesses();
                    if (processes != null) {

                        for (com.tibco.xpd.xpdl2.Process process : processes) {

                            XPDL2BPEL converter = new XPDL2BPEL(file);
                            if (process != null
                                    && XPDLUtils.hasN2Destination(process)) {

                                if (Xpdl2ModelUtil.isPageflow(process)) {

                                    IStatus status =
                                            generatePageflowBpelFiles(pageFlowDestFolder,
                                                    monitor,
                                                    process,
                                                    converter);
                                    if (!status.isOK()) {

                                        throw new CoreException(status);
                                    }
                                } else if (Xpdl2ModelUtil
                                        .isServiceProcess(process)) {

                                    IStatus status =
                                            generateServiceProcessBpelFiles(pageFlowDestFolder,
                                                    businessProcessDestFolder,
                                                    monitor,
                                                    process,
                                                    converter);
                                    if (!status.isOK()) {
                                        throw new CoreException(status);
                                    }

                                    /*
                                     * Sid XPD-7397 for service process that's
                                     * publish as REST service, generate
                                     * pageflow for hidden extension process.
                                     */
                                    status =
                                            generateBpelFileForRestServiceExtnElement(pageFlowDestFolder,
                                                    monitor,
                                                    file,
                                                    process);
                                    if (!status.isOK()) {
                                        throw new CoreException(status);
                                    }

                                } else {

                                    IStatus status =
                                            generateBusinessProcessBpelFiles(businessProcessDestFolder,
                                                    monitor,
                                                    process,
                                                    converter);
                                    if (!status.isOK()) {

                                        throw new CoreException(status);
                                    }

                                    /*
                                     * Sid/Ali XPD-4187. Try-outs. If we have a
                                     * REST Service (pageflow process) extension
                                     * element then try converting it to bpel.
                                     */
                                    status =
                                            generateBpelFileForRestServiceExtnElement(pageFlowDestFolder,
                                                    monitor,
                                                    file,
                                                    process);
                                    if (!status.isOK()) {

                                        throw new CoreException(status);
                                    }
                                }
                            }
                        }

                        markGeneratedFilesAsDerived(businessProcessDestFolder);
                        markGeneratedFilesAsDerived(pageFlowDestFolder);

                    }
                }
            }
        } catch (Exception e) {
            BpelBuilderActivator.getDefault().getLogger()
                    .error(e, "Failed converting Xpdl to Bpel");

        } finally {

            monitor.done();
        }
    }

    /**
     * Generate bpel files for Rest Services
     * 
     * @param pageFlowDestFolder
     * @param monitor
     * @param file
     * @param process
     * @throws CoreException
     */
    private static IStatus generateBpelFileForRestServiceExtnElement(
            IFolder pageFlowDestFolder, IProgressMonitor monitor, IFile file,
            com.tibco.xpd.xpdl2.Process process) throws CoreException {

        IStatus status = Status.OK_STATUS;
        RESTServices restServices =
                (RESTServices) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_RESTServices());

        if (restServices != null && !restServices.getRESTServices().isEmpty()) {

            if (!pageFlowDestFolder.exists()) {

                createFolder(pageFlowDestFolder, new SubProgressMonitor(
                        monitor, 1));
            }

            for (Process restServiceProcess : restServices.getRESTServices()) {

                XPDL2BPEL restServiceConverter = new XPDL2BPEL(file);
                status =
                        restServiceConverter
                                .convertProcessToBPEL(restServiceProcess,
                                        pageFlowDestFolder.getFullPath()
                                                .toOSString(),
                                        true);
                if (!status.isOK()) {

                    return status;
                }
            }
        }
        return status;
    }

    /**
     * Generate bpel files for Business Processes
     * 
     * @param businessProcessDestFolder
     * @param monitor
     * @param process
     * @param converter
     * @return
     * @throws CoreException
     */
    private static IStatus generateBusinessProcessBpelFiles(
            IFolder businessProcessDestFolder, IProgressMonitor monitor,
            com.tibco.xpd.xpdl2.Process process, XPDL2BPEL converter)
            throws CoreException {

        boolean shouldCopyGeneratedWsdl = false;
        if (hasInternalJmxDebug(process)) {

            shouldCopyGeneratedWsdl = true;
        }
        if (!businessProcessDestFolder.exists()) {

            createFolder(businessProcessDestFolder, new SubProgressMonitor(
                    monitor, 1));
        }
        if (shouldCopyGeneratedWsdl) {

            String operationName = getStartEventName(process);
            if (operationName != null) {

                XPDL2WSDL.generateWSDL(businessProcessDestFolder,
                        process,
                        operationName,
                        new SubProgressMonitor(monitor, 1));
            }
        }
        IStatus status =
                converter.convertProcessToBPEL(process,
                        businessProcessDestFolder.getFullPath().toOSString(),
                        false);
        if (!status.isOK()) {

            throw new CoreException(status);
        }
        return status;
    }

    /**
     * Generate bpel files for Pageflow processes
     * 
     * @param pageFlowDestFolder
     * @param monitor
     * @param process
     * @param converter
     * @throws CoreException
     */
    private static IStatus generatePageflowBpelFiles(
            IFolder pageFlowDestFolder, IProgressMonitor monitor,
            com.tibco.xpd.xpdl2.Process process, XPDL2BPEL converter)
            throws CoreException {

        if (!pageFlowDestFolder.exists()) {

            createFolder(pageFlowDestFolder, new SubProgressMonitor(monitor, 1));
        }
        IStatus status =
                converter.convertProcessToBPEL(process, pageFlowDestFolder
                        .getFullPath().toOSString(), true);
        return status;

    }

    /**
     * Generates bpel files for Service Processes
     * 
     * @param pageFlowDestFolder
     * @param businessProcessDestFolder
     * @param monitor
     * @param process
     * @param converter
     * @return
     * @throws CoreException
     */
    private static IStatus generateServiceProcessBpelFiles(
            IFolder pageFlowDestFolder, IFolder businessProcessDestFolder,
            IProgressMonitor monitor, com.tibco.xpd.xpdl2.Process process,
            XPDL2BPEL converter) throws CoreException {

        IStatus status = Status.OK_STATUS;
        if (ProcessInterfaceUtil.isPageflowEngineServiceProcess(process)) {

            status =
                    generatePageflowBpelFiles(pageFlowDestFolder,
                            monitor,
                            process,
                            converter);

        }

        if (status.isOK()) {

            if (ProcessInterfaceUtil.isProcessEngineServiceProcess(process)) {

                status =
                        generateBusinessProcessBpelFiles(businessProcessDestFolder,
                                monitor,
                                process,
                                converter);
            }
        }

        return status;
    }

    /**
     * Goes thru each resource in the given folder and marks them as derived
     * 
     * @param bpelOutFolder
     * @throws CoreException
     */
    private static void markGeneratedFilesAsDerived(IFolder bpelOutFolder)
            throws CoreException {

        if (!bpelOutFolder.isAccessible()) {
            return;
        }
        IResource[] members = bpelOutFolder.members();
        for (IResource eachResource : members) {

            if (!eachResource.isAccessible()) {

                continue;
            }
            if (BPEL_FILE_EXTENSION.equals(eachResource.getFileExtension())) {

                eachResource.setDerived(Boolean.TRUE);
            } else if (WSDL_FILE_EXTENSION.equals(eachResource
                    .getFileExtension())) {

                eachResource.setDerived(Boolean.TRUE);
            }
        }
    }

    /**
     * Checks if the extended attribute "InternalJmxDebug" is set
     * 
     * @param process
     * @return <code>true</code> if the extended attribute "InternalJmxDebug" is
     *         set
     */
    private static boolean hasInternalJmxDebug(
            com.tibco.xpd.xpdl2.Process process) {

        if (process != null) {

            return WsdlGenBuilderTransformer
                    .doesContainRequiredExtendedAttribute(process);
        }
        return false;
    }

    /**
     * 
     * @param process
     * @return
     */
    private static String getStartEventName(com.tibco.xpd.xpdl2.Process process) {
        List<Activity> startEventsForProcess =
                Xpdl2ModelUtil.getStartEventsForProcess(process);
        if (startEventsForProcess.iterator().hasNext()) {
            Activity startEvent = startEventsForProcess.iterator().next();
            if (startEvent != null) {
                return startEvent.getName();
            }
        }
        return null;
    }

    /**
     * A simple way to convert an object that might be null to a String.
     * 
     * @param object
     *            An object to run toString on.
     * @return A String representation of the object parameter.
     */
    public static String toString(java.lang.Object object) {
        return ((object == null) ? "" : object.toString()); //$NON-NLS-1$
    }

    /**
     * 
     * @param eObject
     * @return
     */
    public static EObject clone(EObject eObject) {
        Copier copier = new Copier();
        EObject result = copier.copy(eObject);
        copier.copyReferences();
        return result;
    }

    /**
     * create folder
     * 
     * @param folder
     *            : the folder will be created
     * @param monitor
     * @throws CoreException
     */
    public static void createFolder(IFolder folder, IProgressMonitor monitor)
            throws CoreException {
        while (!folder.getParent().exists()) {
            createFolder((IFolder) folder.getParent(), monitor);
        }
        if (!folder.exists()) {
            folder.create(true, true, monitor);
            folder.setDerived(Boolean.TRUE);
        }
        return;
    }

    /**
     * 
     * @param absolutePath
     */
    public static void createFolder(String absolutePath) {
        File file = new File(absolutePath);
        while (!file.getParentFile().exists()) {
            createFolder(file.getParent());
        }
        if (!file.exists() || file.isFile()) {
            file.mkdir();
        }
    }

    /**
     * 
     * @param file
     * @return
     */
    public static String getFileName(IFile file) {
        String name = file.getName();
        name =
                name.substring(0, name.length()
                        - file.getFileExtension().length() - 1);
        return name;
    }

    /**
     * get the Process Model of BPEL from a bpelFile
     * 
     * @param bpelFile
     * @return
     * @throws IOException
     */
    public static Process getProcess(IFile bpelFile) throws IOException {
        if (!bpelFile.exists()) {
            return null;
        }
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bpelFile);
        if (wc != null && !wc.isInvalidFile() && wc.isLoaded()) {
            EObject rootElement = wc.getRootElement();
            if (rootElement instanceof Process) {
                return (Process) rootElement;
            }
        }

        return null;
    }

    /**
     * get the Package Model from a xpdlFile
     * 
     * @param xpdlFile
     * @return
     * @throws IOException
     */
    public static Package getProcessPackage(IFile xpdlFile) throws IOException {
        if (!xpdlFile.exists()) {
            return null;
        }
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (wc != null && !wc.isInvalidFile() && wc.isLoaded()) {
            EObject rootElement = wc.getRootElement();
            if (rootElement instanceof Package) {
                return (Package) rootElement;
            }
        }
        return null;
    }

    /**
     * returns a folder for a given path
     * 
     * @param container
     *            : the folder container
     * @param path
     *            : the folder path
     * 
     * @return {@link IFolder} the folder
     */
    private static IFolder getFolder(IContainer container, IPath path) {
        if (path != null && container != null) {
            IFolder distFolder =
                    container.getProject().getWorkspace().getRoot()
                            .getFolder(path);
            return distFolder;
        }
        return null;
    }

    /**
     * returns the destination folder for the generated bpel files The folder
     * will be appended to the given container.
     * 
     * @param container
     *            : the folder container
     * 
     * @return {@link IFolder} the folder
     */
    public static IFolder getBpelOutDestFolder(IContainer container) {
        if (container != null) {
            IPath distFolderPath =
                    container.getFullPath().append(BPEL_ROOT_OUTPUTFOLDER_NAME);
            return getFolder(container, distFolderPath);
        }
        return null;
    }

    /**
     * returns the destination folder for the generated pageflow bpel files The
     * folder will be appended to the given container.
     * 
     * @param container
     *            : the folder container
     * 
     * @return {@link IFolder} the folder
     */
    public static IFolder getBpelPageFlowOutDestFolder(IContainer container) {
        if (container != null) {
            IPath distFolderPath =
                    container.getFullPath().append(BPEL_ROOT_OUTPUTFOLDER_NAME)
                            .append(BPELN2Utils.PF_OUTPUTFOLDER_NAME);
            return getFolder(container, distFolderPath);
        }
        return null;
    }

    /**
     * returns the destination folder for the generated businessprocess bpel
     * files The folder will be appended to the given container.
     * 
     * @param container
     *            : the folder container
     * 
     * @return {@link IFolder} the folder
     */
    public static IFolder getBpelBusinessProcessOutDestFolder(
            IContainer container) {
        if (container != null) {
            IPath distFolderPath =
                    container.getFullPath().append(BPEL_ROOT_OUTPUTFOLDER_NAME)
                            .append(BPELN2Utils.BP_OUTPUTFOLDER_NAME);
            return getFolder(container, distFolderPath);
        }
        return null;
    }

    /**
     * get the dest folder for the business processes according to the xpdl file
     * or a folder notice that,if the file is not xpdl file, the result will be
     * null
     * 
     * @param resource
     *            : IFolder type or IFile type
     * @return folder the dest folder after convert null: when the file is not
     *         xpdl file Note that: the folder may be not exist.
     * 
     */
    public static IFolder getBpelBusinessProcessXpdlDestFolder(
            IContainer container, IResource resource) {
        if (resource != null && container != null) {
            IPath distFolderPath =
                    container.getFullPath().append(BPEL_ROOT_OUTPUTFOLDER_NAME)
                            .append(BPELN2Utils.BP_OUTPUTFOLDER_NAME)
                            .append(resource.getName());
            return getFolder(container, distFolderPath);
        }
        return null;
    }

    /**
     * get the dest folder for the page flow processes according to the xpdl
     * file or a folder notice that,if the file is not xpdl file, the result
     * will be null
     * 
     * @param resource
     *            : IFolder type or IFile type
     * @return folder the dest folder after convert null: when the file is not
     *         xpdl file Note that: the folder may be not exist.
     * 
     */
    public static IFolder getBpelPageFlowXpdlDestFolder(IContainer container,
            IResource resource) {
        if (resource != null && container != null) {
            IPath distFolderPath =
                    container.getFullPath().append(BPEL_ROOT_OUTPUTFOLDER_NAME)
                            .append(BPELN2Utils.PF_OUTPUTFOLDER_NAME)
                            .append(resource.getName());
            return getFolder(container, distFolderPath);
        }
        return null;
    }

    /**
     * 
     * @param xpdlFile
     * @return
     */
    public static IFile[] getGeneratedBpelFiles(IFile xpdlFile) {
        IProject xpdlProject = xpdlFile.getProject();
        // checking whether there are any business processes
        String xpdlFileName = xpdlFile.getName();
        IPath bpFolderPath =
                new Path(BPEL_ROOT_OUTPUTFOLDER_NAME)
                        .append(BPELN2Utils.BP_OUTPUTFOLDER_NAME)
                        .append(xpdlFileName);

        IFolder bpFolder = xpdlProject.getFolder(bpFolderPath);
        IFile[] businessProcessBpelFiles =
                BPELN2Utils.getGeneratedBpelFiles(bpFolder);
        int totalLength = 0;
        if (businessProcessBpelFiles.length > 0) {
            totalLength = businessProcessBpelFiles.length;
        }
        // checking whether there are any page flow processes
        IPath pfFolderPath =
                new Path(BPEL_ROOT_OUTPUTFOLDER_NAME)
                        .append(BPELN2Utils.PF_OUTPUTFOLDER_NAME)
                        .append(xpdlFileName);
        IFolder pfFolder = xpdlProject.getFolder(pfFolderPath);
        IFile[] pageFlowBpelFiles = BPELN2Utils.getGeneratedBpelFiles(pfFolder);
        if (pageFlowBpelFiles.length > 0) {
            totalLength += pageFlowBpelFiles.length;
        }
        IFile destFolder[] = new IFile[totalLength];
        System.arraycopy(businessProcessBpelFiles,
                0,
                destFolder,
                0,
                businessProcessBpelFiles.length);
        System.arraycopy(pageFlowBpelFiles,
                0,
                destFolder,
                businessProcessBpelFiles.length,
                pageFlowBpelFiles.length);

        return destFolder;

    }

    /**
     * 
     * @param xpdlFile
     * @return
     */
    public static IFile[] getPageFlowBpelFiles(IFile xpdlFile) {
        IFolder destFolder =
                BPELN2Utils.getBpelPageFlowXpdlDestFolder(N2PENamingUtils
                        .getModuleOutputFolder(xpdlFile.getProject(), false),
                        xpdlFile);
        IFile[] bpelFiles = BPELN2Utils.getGeneratedBpelFiles(destFolder);
        return bpelFiles;

    }

    /**
     * 
     * @param xpdlFile
     * @return
     */
    public static IFile[] getBusinessProcessBpelFiles(IFile xpdlFile) {
        IFolder destFolder =
                BPELN2Utils
                        .getBpelBusinessProcessXpdlDestFolder(N2PENamingUtils
                                .getModuleOutputFolder(xpdlFile.getProject(),
                                        false), xpdlFile);
        IFile[] bpelFiles = BPELN2Utils.getGeneratedBpelFiles(destFolder);
        return bpelFiles;
    }

    /**
     * 
     * @param bpelFilesFolder
     * @return
     */
    private static IFile[] getGeneratedBpelFiles(IFolder bpelFilesFolder) {
        final List<IFile> bpelFiles = new ArrayList<IFile>();
        if (bpelFilesFolder != null && bpelFilesFolder.exists()) {
            try {
                bpelFilesFolder.accept(new IResourceVisitor() {
                    public boolean visit(IResource resource)
                            throws CoreException {
                        if (resource != null && resource instanceof IFile) {
                            if (BPEL_FILE_EXTENSION.equals(resource
                                    .getFileExtension())) {
                                bpelFiles.add((IFile) resource);
                            }
                            return false;
                        }
                        return true;

                    }
                });
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        IFile[] fileArr = new IFile[bpelFiles.size()];
        bpelFiles.toArray(fileArr);
        return fileArr;
    }
}
