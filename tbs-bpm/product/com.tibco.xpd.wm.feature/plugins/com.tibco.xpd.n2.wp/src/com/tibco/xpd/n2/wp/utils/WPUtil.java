/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.wp.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.pe.util.PEN2Utils;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.wm.pageflow.component.PFEUtil.CapabilityIdVersion;

/**
 * @author kupadhya
 * 
 */
public class WPUtil {

    public static final String SERVICE_ARCHIVE_DESC_ROOT_FOLDER_NAME = "wp";//$NON-NLS-1$

    public static final String SERVICE_ARCHIVE_DESC_NAME = "wpModel.xml";//$NON-NLS-1$

    public static final String WP_RESOURCES_FOLDERNAME =
            SERVICE_ARCHIVE_DESC_ROOT_FOLDER_NAME;

    public static final String PRESENTATION_MODEL_PACKAGE_ID =
            "com.tibco.n2.model.wp"; //$NON-NLS-1$        

    public static final String WP_OUT_FOLDERNAME = ".wpModules"; //$NON-NLS-1$

    public static final String WP_FILE_EXTENSION = "wp"; //$NON-NLS-1$

    public static final String WP_REQUIRED_CAPABILITY_ID =
            "com.tibco.amx.capability.implementation.wp"; //$NON-NLS-1$

    public static final String WEBAPP_MODEL_PACKAGE_ID =
            "com.tibco.amf.sca.model.implementationtype.webapp"; //$NON-NLS-1$        

    public static IFolder getWpOutDestFolder(IProject project) {
        if (project != null) {
            return getWpDestFolder(project, "");//$NON-NLS-1$
        }
        return null;
    }

    public static IResource getArchiveDescriptorResource(IContainer stagingArea) {
        if (stagingArea != null && stagingArea.exists()) {
            IPath sadFolderPath =
                    new Path(SERVICE_ARCHIVE_DESC_ROOT_FOLDER_NAME);
            IFolder folder = stagingArea.getFolder(sadFolderPath);
            if (folder != null && folder.exists()) {
                try {
                    folder.refreshLocal(IResource.DEPTH_INFINITE, null);
                    IFile file =
                            folder.getFile(new Path(SERVICE_ARCHIVE_DESC_NAME));
                    return file;
                } catch (CoreException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    private static IFolder getWpDestFolder(IProject project, String folderName) {
        if (project != null) {
            IPath distFolderPath =
                    project.getFullPath().append(WP_OUT_FOLDERNAME)
                            .append(folderName);
            IFolder distFolder =
                    project.getWorkspace().getRoot().getFolder(distFolderPath);
            return distFolder;
        }
        return null;
    }

    public static ImportPackage getPresentationImportPackage() {
        ImportPackage ip = OsgiFactory.eINSTANCE.createImportPackage();
        ip.setName(WPUtil.PRESENTATION_MODEL_PACKAGE_ID);
        VersionRange vr = OsgiFactory.eINSTANCE.createVersionRange();
        vr.setLower("2.0.0"); //$NON-NLS-1$
        vr.setLowerIncluded(true);
        vr.setUpper("3.0.0"); //$NON-NLS-1$
        vr.setUpperIncluded(false);
        ip.setRange(vr);
        return ip;
    }

    public static void addWPRequiredCapability(IProject project,
            Requirements requirements, String qualifierReplacer) {
        EList<RequiredCapability> requiredCapabilities =
                requirements.getRequiredCapabilities();
        RequiredCapability rc =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        rc.setId(WP_REQUIRED_CAPABILITY_ID);
        rc.setType(CapabilityType.FACTORY);
        rc.setVersion("1.0.0"); //$NON-NLS-1$
        requiredCapabilities.add(rc);
        List<RequiredCapability> pageflowComponentCapabilityList =
                getPageflowComponentCapabilityList(project, qualifierReplacer);
        requiredCapabilities.addAll(pageflowComponentCapabilityList);
        EList<RequiredCapability> rcList = rc.getWiths();
        rcList.addAll(pageflowComponentCapabilityList);

    }

    public static List<RequiredCapability> getPageflowComponentCapabilityList(
            IProject project, String qualifierReplacer) {
        List<RequiredCapability> requiresCapability =
                new ArrayList<RequiredCapability>();
        List<IResource> xpdlResources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                N2PENamingUtils.XPDL_FILE_EXTENSION,
                                false);
        Set<IResource> xpdlFiles = new HashSet<IResource>();
        if (xpdlResources != null && !xpdlResources.isEmpty()) {
            for (final IResource resource : xpdlResources) {
                if (!(resource instanceof IFile)) {
                    continue;
                }
                IFile xpdlFile = (IFile) resource;
                IFile[] bpelFiles = BPELN2Utils.getPageFlowBpelFiles(xpdlFile);
                if (bpelFiles.length < 1) {
                    continue;
                }
                xpdlFiles.add(xpdlFile);
            }
        }
        for (IResource eachXPDL : xpdlFiles) {

            RequiredCapability createRequiredCapability =
                    createRequiredCapability(project,
                            eachXPDL,
                            qualifierReplacer);
            requiresCapability.add(createRequiredCapability);

        }
        return requiresCapability;
    }

    public static ImportPackage getWebAppImportPackage() {
        ImportPackage ip = OsgiFactory.eINSTANCE.createImportPackage();
        ip.setName(WPUtil.WEBAPP_MODEL_PACKAGE_ID);
        VersionRange vr = OsgiFactory.eINSTANCE.createVersionRange();
        vr.setLower("1.0.0"); //$NON-NLS-1$
        vr.setLowerIncluded(true);
        vr.setUpper("2.0.0"); //$NON-NLS-1$
        vr.setUpperIncluded(false);
        ip.setRange(vr);
        return ip;
    }

    public static boolean doesWpResourcesFolderExist(IProject project) {
        IFolder wpResourcesFolder = getWpResourcesFolder(project);
        if (wpResourcesFolder != null && wpResourcesFolder.exists()) {
            return true;
        }
        return false;
    }

    public static IFolder getWpResourcesFolder(IProject project) {
        if (project != null) {
            IFolder moduleOutputFolder =
                    N2PENamingUtils.getModuleOutputFolder(project, false);
            if (moduleOutputFolder != null && moduleOutputFolder.exists()) {
                IFolder resourcesFolder =
                        moduleOutputFolder.getFolder(WP_RESOURCES_FOLDERNAME);
                if (resourcesFolder != null && resourcesFolder.exists()) {
                    return resourcesFolder;
                }
            }
        }
        return null;
    }

    private static RequiredCapability createRequiredCapability(
            IProject project, IResource xpdlFile, String qualifierReplacer) {
        String componentName =
                PEN2Utils.getComponentName(xpdlFile, PFEUtil.PAGE_FLOW_APPEND
                        .substring(1));
        CapabilityIdVersion providedCapabilityId =
                com.tibco.xpd.wm.pageflow.component.PFEUtil
                        .getProvidedCapabilityId(project,
                                qualifierReplacer,
                                componentName);
        RequiredCapability requiredCapability =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        requiredCapability.setId(providedCapabilityId.getId());
        requiredCapability.setType(CapabilityType.CUSTOM);
        requiredCapability.setVersion(providedCapabilityId.getVersion());
        return requiredCapability;
    }
}
