/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test.supporting;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.ui.wizards.datatransfer.ZipFileStructureProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Helper class for importing zipped projects from the "resources" folder.
 * 
 * @author nwilson
 * @since 27 May 2015
 */
public class DataMapperProjectImporter {

    /**
     * Imports a project from the "resources" folder. The name must match both
     * the zip file name (".zip" will be appended) and the project name.
     * 
     * @param name
     *            The project name.
     */
    public IProject importProject(String name) {
        Bundle bundle = FrameworkUtil.getBundle(getClass());
        URL zipLocation = bundle.getEntry("resources/" + name + ".zip"); //$NON-NLS-1$ //$NON-NLS-2$
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject project = root.getProject(name);
        try {
            ZipFile zipFile =
                    new ZipFile(FileLocator.toFileURL(zipLocation).getFile());
            ZipFileStructureProvider structureProvider =
                    new ZipFileStructureProvider(zipFile);
            ImportOperation op =
                    new ImportOperation(project.getFullPath(),
                            structureProvider.getRoot(), structureProvider,
                            null);
            op.run(null);
            project.open(null);
        } catch (InvocationTargetException | InterruptedException
                | CoreException | IOException e) {
            fail(e.getMessage());
        }
        TestUtil.waitForBuilds(120);
        return project;
    }

    /**
     * @param project
     *            The project.
     * @param path
     *            The XPDL file name.
     * @return The EMF resource for the file.
     */
    public Package getPackage(IProject project, String path) {
        IFile file =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                        path);
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
        return (Package) wc.getRootElement();
    }

    /**
     * @param pkg
     *            The package to search.
     * @param name
     *            The process name.
     * @return The matching process or null.
     */
    public Process getProcess(Package pkg, String name) {
        Process process = null;
        if (name != null) {
            for (Process next : pkg.getProcesses()) {
                if (name.equals(next.getName())) {
                    process = next;
                    break;
                }
            }
        }
        return process;
    }

    /**
     * @param process
     *            The process to search.
     * @param name
     *            The activity name.
     * @return The matching activity or null.
     */
    public Activity getActivity(Process process, String name) {
        Activity activity = null;
        if (name != null) {
            for (Activity next : process.getActivities()) {
                if (name.equals(next.getName())) {
                    activity = next;
                    break;
                }
            }
        }
        return activity;
    }
}
