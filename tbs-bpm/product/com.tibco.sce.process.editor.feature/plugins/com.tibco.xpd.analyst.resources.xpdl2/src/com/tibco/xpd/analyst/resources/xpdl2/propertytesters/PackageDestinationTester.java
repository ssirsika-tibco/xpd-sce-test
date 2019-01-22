/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Provides the following property testers:
 * <ul>
 * <li><strong>hasProcessWithGivenDestination</strong> - Check if a resource
 * contains packages with processes of the given destination environment(s).
 * <ul>
 * <li>For project: This will look for processes special folder and check if
 * there is any XPDL model with the given destination environment(s).</li>
 * <li>For folder: If the given folder is a processes special folder then check
 * if it contains an XPDL model with the given destination environment(s).</li>
 * <li>For file: If the file is an XPDL model then check if it has processes
 * with the given destination environment(s).</li> </ul</li>
 * </ul>
 * 
 * @author njpatel
 * @since 16 Jul 2013
 */
public class PackageDestinationTester extends PropertyTester {

    /**
     * Check if a given project contains processes of the given destination
     * environment(s).
     */
    private static final String HAS_PROCESS_WITH_GIVEN_DEST =
            "hasProcessWithGivenDestination"; //$NON-NLS-1$

    public PackageDestinationTester() {
    }

    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (receiver instanceof IProject) {
            return test((IProject) receiver, property, args, expectedValue);
        } else if (receiver instanceof IFolder) {
            SpecialFolder sf =
                    SpecialFolderUtil.getRootSpecialFolder((IFolder) receiver);
            if (sf != null
                    && Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND
                            .equals(sf.getKind())) {
                return test((IFolder) receiver, property, args, expectedValue);
            }
        } else if (receiver instanceof IFile) {
            return test((IFile) receiver, property, args, expectedValue);
        }
        return false;
    }

    /**
     * Test the given project.
     * 
     * @param project
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    private boolean test(IProject project, String property, Object[] args,
            Object expectedValue) {

        List<SpecialFolder> folders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

        if (folders != null) {
            for (SpecialFolder sf : folders) {
                IFolder folder = sf.getFolder();

                if (test(folder, property, args, expectedValue)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Test the given folder.
     * 
     * @param project
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    private boolean test(IFolder folder, String property, Object[] args,
            Object expectedValue) {
        if (folder != null && folder.isAccessible()) {
            try {
                /*
                 * Find the first XPDL package that has the processes of the
                 * given destination env.
                 */
                for (IResource res : folder.members()) {
                    if (res instanceof IFile
                            && Xpdl2ResourcesConsts.XPDL_EXTENSION.equals(res
                                    .getFileExtension())) {
                        if (test((IFile) res, property, args, expectedValue)) {
                            return true;
                        }
                    } else if (res instanceof IFolder) {
                        if (test((IFolder) res, property, args, expectedValue)) {
                            return true;
                        }
                    }
                }
            } catch (CoreException e) {
                Xpdl2ResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("Problem accessing contents of folder '%s'.", //$NON-NLS-1$
                                        folder.getFullPath().toString()));
            }
        }
        return false;
    }

    /**
     * Test the given file.
     * 
     * @param file
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    private boolean test(IFile file, String property, Object[] args,
            Object expectedValue) {

        if (HAS_PROCESS_WITH_GIVEN_DEST.equals(property)) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
            if (wc != null && wc.getRootElement() instanceof Package) {
                Set<Class> clazzes = new HashSet<Class>();
                clazzes.add(Process.class);
                Set<String> destinations =
                        DestinationUtil
                                .getEnabledGlobalDestinations((Package) wc
                                        .getRootElement(), clazzes);

                /*
                 * Check that all destinations provided in the argument are set
                 * in the package.
                 */
                if (!destinations.isEmpty()) {
                    boolean ret = true;
                    for (Object arg : args) {
                        if (!destinations.contains(arg)) {
                            ret = false;
                            break;
                        }
                    }
                    return ret;
                }
            }
        }

        return false;
    }
}
