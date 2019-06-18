/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.pe.util;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author mtorres
 * 
 */
public class PEN2Utils {

    public static final String PE_MODEL_FILE_NAME = "/model/PEJavaScript.uml"; //$NON-NLS-1$

    public static final String CASE_SIGNAL_MODEL_FILE_NAME =
            "/model/CaseSignalAttributes.uml"; //$NON-NLS-1$

    public static final String PROCESS_FLOW_APPEND = "#ProcessFlow"; //$NON-NLS-1$

    public static final String PE_NATURE_ID = "com.tibco.xpd.n2.pe.peNature"; //$NON-NLS-1$

    public static String PE_OUTPUTFOLDER_NAME = ".peOut"; //$NON-NLS-1$  

    public static String PE_SPECIALFOLDER_KIND = "peOutput"; //$NON-NLS-1$    

    public static String PE_FILE_EXTENSION = "pe"; //$NON-NLS-1$  

    public static String DOCUMENT = "DOCUMENT"; //$NON-NLS-1$

    public static String HTTP = "HTTP"; //$NON-NLS-1$

    public static String JMS = "JMS"; //$NON-NLS-1$

    public static String LITERAL = "LITERAL"; //$NON-NLS-1$

    public static final String PE_ERROR_MARKER_ID = "PE_ERROR_MARKER_ID"; //$NON-NLS-1$

    public static final String PE_COMPOSITE_ERROR_MARKER_ID =
            "PE_COMPOSITE_ERROR_MARKER_ID"; //$NON-NLS-1$

    public static final String DEFAULT_PROCESS_COMPONENT_VERSION =
            "1.0.0.qualifier"; //$NON-NLS-1$

    public static IFolder getPeOutDestFolder(IProject project, boolean create) {
        if (project != null && project.isAccessible()) {
            IPath distFolderPath =
                    project.getFullPath().append(PE_OUTPUTFOLDER_NAME);
            IFolder distFolder =
                    project.getWorkspace().getRoot().getFolder(distFolderPath);
            if (distFolder == null || !distFolder.isAccessible()) {
                if (create) {
                    try {
                        ProjectUtil.createFolder(distFolder, false, null);
                        return distFolder;
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return distFolder;
            }
        }
        return null;
    }

    public static IFolder getPeBusinessProcessDestFolder(IResource resource) {
        if (resource != null && resource.getProject() != null) {
            return getPeBusinessProcessDestFolder(resource.getProject());
        }
        return null;
    }

    public static IFolder getPeBusinessProcessDestFolder(IProject project,
            boolean create) {
        return getPeDestFolder(project,
                N2PENamingUtils.BP_OUTPUTFOLDER_NAME,
                create);
    }

    /**
     * Returns folder where all .pe files are located for the page flows in the
     * project
     * 
     * @param resource
     * @param create
     * @return
     */
    public static IFolder getPePageFlowDestFolder(IResource resource,
            boolean create) {
        IFolder peOutDestFolder =
                getPeOutDestFolder(resource.getProject(), create);
        if (peOutDestFolder != null && peOutDestFolder.isAccessible()) {
            IFolder folder =
                    peOutDestFolder
                            .getFolder(N2PENamingUtils.PF_OUTPUTFOLDER_NAME);
            if (folder == null || !folder.isAccessible()) {
                if (create) {
                    try {
                        ProjectUtil.createFolder(folder, false, null);
                        return folder;
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return folder;
            }
        }
        return null;
    }

    private static IFolder getPeDestFolder(IResource resource,
            String folderName, boolean create) {
        if (resource != null && resource.getProject() != null) {
            return getPeDestFolder(resource.getProject(), folderName, create);
        }
        return null;
    }

    private static IFolder getPeDestFolder(IProject project, String folderName,
            boolean create) {
        if (project == null || !project.isAccessible()) {
            return null;
        }
        IPath distFolderPath =
                project.getFullPath().append(PE_OUTPUTFOLDER_NAME)
                        .append(folderName);
        IFolder distFolder =
                project.getWorkspace().getRoot().getFolder(distFolderPath);
        if (distFolder == null || !distFolder.isAccessible()) {
            try {
                if (create) {
                    ProjectUtil.createFolder(distFolder, false, null);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return distFolder;
    }

    public static String getPeFileName(IResource resource) {
        if (resource != null) {
            return resource.getName() + "." + PE_FILE_EXTENSION; //$NON-NLS-1$
        }
        return null;
    }

    public static Collection<IResource> getAllPeBusinessProcess(IProject project) {
        IFolder peBusinessProcessDestFolder =
                getPeBusinessProcessDestFolder(project, false);
        if (peBusinessProcessDestFolder != null) {
            return SpecialFolderUtil
                    .getAllDeepResourcesInContainer(peBusinessProcessDestFolder,
                            PE_FILE_EXTENSION,
                            false);
        }
        return Collections.emptyList();
    }

    public static boolean isOnlyPageFlowContainedInXpdl(IFile xpdlFile) {
        boolean isOnlyPageFlowContained = true;
        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
        if (workingCopy instanceof Xpdl2WorkingCopyImpl) {
            Xpdl2WorkingCopyImpl xpdlWC = (Xpdl2WorkingCopyImpl) workingCopy;
            Package xpdlPackage = (Package) xpdlWC.getRootElement();
            EList<Process> processes = xpdlPackage.getProcesses();
            for (Process process : processes) {
                boolean pageflow = Xpdl2ModelUtil.isPageflow(process);
                if (!pageflow) {
                    isOnlyPageFlowContained = false;
                    break;
                }
            }
        }
        return isOnlyPageFlowContained;
    }

    public static boolean isOnlyBusinessProcessContainedInXpdl(IFile xpdlFile) {
        boolean isOnlyBusinessProcessContained = true;
        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
        if (workingCopy instanceof Xpdl2WorkingCopyImpl) {
            Xpdl2WorkingCopyImpl xpdlWC = (Xpdl2WorkingCopyImpl) workingCopy;
            Package xpdlPackage = (Package) xpdlWC.getRootElement();
            EList<Process> processes = xpdlPackage.getProcesses();
            for (Process process : processes) {
                boolean pageflow = Xpdl2ModelUtil.isPageflow(process);
                if (pageflow) {
                    isOnlyBusinessProcessContained = false;
                    break;
                }
            }
        }
        return isOnlyBusinessProcessContained;
    }

    public static void copyBpelFilesToPageFlowDestFolder(
            IContainer stagingArea, IProject project) {
        try {
            IFolder bpelOutFolder =
                    BPELN2Utils.getBpelPageFlowOutDestFolder(project);
            if (bpelOutFolder != null && bpelOutFolder.isAccessible()
                    && stagingArea != null && stagingArea.isAccessible()) {
                IPath bpelFilesOut =
                        stagingArea
                                .getFullPath()
                                .append(BPELN2Utils.BPEL_ROOT_OUTPUTFOLDER_NAME);
                IFolder distFolder =
                        project.getWorkspace().getRoot()
                                .getFolder(bpelFilesOut);
                if (!distFolder.exists()) {
                    ProjectUtil.createFolder(distFolder, false, null);
                }
                bpelFilesOut = bpelFilesOut.append(bpelOutFolder.getName());
                distFolder =
                        project.getWorkspace().getRoot()
                                .getFolder(bpelFilesOut);
                if (distFolder.exists()) {
                    distFolder.delete(true, new NullProgressMonitor());
                }
                bpelOutFolder.copy(bpelFilesOut,
                        true,
                        new NullProgressMonitor());

            }
        } catch (CoreException e) {
            /*
             * addPeCompositeErrorMarker(project,
             * Messages.PECompositeContributor_problemsDuringDaaGeneration);
             */
        }
    }

    public static String getComponentName(IResource xpdlFile,
            String appendString) {
        String componentName = xpdlFile.getName();
        String fileExtension = xpdlFile.getFileExtension();
        if (fileExtension != null && fileExtension.length() > 0
                && componentName.length() > fileExtension.length() - 1) {
            componentName =
                    componentName.substring(0, componentName.length()
                            - fileExtension.length() - 1);
        }
        componentName =
                NameUtil.getInternalName(componentName, Boolean.TRUE)
                        + appendString;
        return componentName;
    }

}
