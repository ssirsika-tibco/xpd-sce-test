/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.n2.daa.test.junit.DAAContentsTest;
import com.tibco.xpd.n2.globalsignal.resource.util.GSDModelUtil;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * provides utilities for junit daa generation and comparing zip contents for
 * generated and gold daa
 * 
 * @author bharge
 * @since 29 Nov 2013
 */
public class DAAUtil {

    /**
     * 
     * @param project
     * @param daaZip
     * @return daa qualifier
     */
    public static String getDAABuildQualifier(IProject project,
            ZipFile daaZip) {

        String buildQualifier = null;
        String projectId = ProjectUtil.getProjectId(project);
        String entryPath = "plugins/" + projectId + "_"; //$NON-NLS-1$ //$NON-NLS-2$
        /*
         * for a business data project or global-signal project the
         * composite/app name will have project id suffixed with "-" + <major
         * version of the project>
         * 
         * for instance for a project with id "com.example.firstbizdataproject"
         * and version "1.0.0.qualifier" say, app name will be
         * "com.example.firstbizdataproject-1"
         */
        if (BOMUtils.isBusinessDataProject(project)
                || GSDModelUtil.isGsdProject(project)) {

            String appName = BOMUtils.getAppName(project, projectId);
            entryPath = "plugins/" + appName + "_"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        Enumeration<? extends ZipEntry> zipEntries = daaZip.entries();
        while (zipEntries.hasMoreElements()) {

            ZipEntry zipEntry = zipEntries.nextElement();
            String entryName = zipEntry.getName();
            if (entryName.startsWith(entryPath)) {

                int indexOf = entryName.indexOf('/', entryPath.length());
                buildQualifier =
                        entryName.substring(entryPath.length(), indexOf);
                break;
            }
        }

        return buildQualifier;
    }

    /**
     * 
     * @param project
     * @return gold daa for the given project
     * @throws CoreException
     */
    public static IFile getGoldDAA(IProject project) {

        IFolder folder = project.getFolder(DAAContentsTest.GOLD_DAA_FOLDER);
        if (!folder.exists()) {

            return null;
        }
        final List<IFile> daaFiles = new ArrayList<IFile>();
        try {

            folder.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource resource) throws CoreException {

                    if (resource instanceof IFolder) {

                        return true;
                    } else if (resource instanceof IFile) {

                        IFile file = (IFile) resource;
                        if (DAAContentsTest.DAA
                                .equals(file.getFileExtension())) {

                            daaFiles.add(file);
                        }
                        return false;
                    }
                    return false;
                }
            });
        } catch (CoreException e) {

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        if (daaFiles.size() > 0) {

            return daaFiles.get(0);
        } else {

            return null;
        }
    }
}
