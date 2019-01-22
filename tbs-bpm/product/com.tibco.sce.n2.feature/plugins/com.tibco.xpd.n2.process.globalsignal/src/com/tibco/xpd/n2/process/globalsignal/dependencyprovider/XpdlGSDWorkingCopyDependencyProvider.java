/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.dependencyprovider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Xpdl2 working copies dependency provider to Global Signal Model.
 * 
 * @author kthombar
 * @since Mar 17, 2015
 */
public class XpdlGSDWorkingCopyDependencyProvider implements
        IWorkingCopyDependencyProvider {

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass()
     * 
     * @return
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return Xpdl2WorkingCopyImpl.class;
    }

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {

        Set<IResource> resources = new HashSet<IResource>();
        /*
         * Check if the Working copy exists and is not invalid.
         */
        if (wc != null && wc.isExist() && !wc.isInvalidFile()) {
            EObject element = wc.getRootElement();

            if (element instanceof Package) {
                /*
                 * get the package
                 */
                Package pkg = (Package) element;
                EList<Process> allProcessesInPackage = pkg.getProcesses();

                if (allProcessesInPackage != null) {
                    /*
                     * get all the processes in the package
                     */
                    for (Process process : allProcessesInPackage) {
                        /*
                         * get all the activities in the process.
                         */
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(process);

                        for (Activity activity : allActivitiesInProc) {
                            /*
                             * if the activity is a global signal activity then
                             * get the referenced global signal.
                             */
                            if (GlobalSignalUtil.isGlobalSignalEvent(activity)) {
                                /*
                                 * XPD-7580: We should fetch the GSD File
                                 * manually without the help of indexer because
                                 * if one indexer depends upon other we cannot
                                 * be sure that the other indexer has indexed
                                 * the related stuff which might cause problems.
                                 */
                                IFile gsdFile = getReferencedGSDFile(activity);

                                if (gsdFile != null
                                        && !resources.contains(gsdFile)) {

                                    resources.add(gsdFile);

                                }
                            }
                        }
                    }
                }
            }
        }
        return resources;
    }

    /**
     * Gets the referenced GSD File by the activity else return
     * <code>null</code> if the GSD File cannot be fetched
     * 
     * @param activity
     *            the activity.
     * @return the referenced GSD File by the activity else return
     *         <code>null</code> if the GSD File cannot be fetched
     */
    private IFile getReferencedGSDFile(Activity activity) {
        IFile gsdFile = null;
        String signalName = EventObjectUtil.getSignalName(activity);

        if (signalName != null) {
            gsdFile = getGsdFileFromSignalName(signalName);
        }
        return gsdFile;
    }

    /**
     * Gets the referenced GSD File (may or may not exist) for the passed signal
     * name, returns <code>null</code> if the GSD project does not exist or if
     * the GSD special folder is not available.
     * 
     * @param globalSignalQualifiedName
     *            the global signal internal model name.
     * @return the referenced GSD File (may or may not exist) for the passed
     *         signal name, returns <code>null</code> if the GSD project does
     *         not exist or if the GSD special folder is not available.
     */
    private IFile getGsdFileFromSignalName(String globalSignalQualifiedName) {

        int indexOfSlash = globalSignalQualifiedName.indexOf("/"); //$NON-NLS-1$
        int indexOfHash = globalSignalQualifiedName.indexOf("#"); //$NON-NLS-1$

        /*
         * As per design the internal signal should have '/' and '#'
         */
        if (indexOfSlash != -1 && indexOfHash != -1) {

            String projectId =
                    globalSignalQualifiedName.substring(0, indexOfSlash);

            if (projectId != null && !projectId.isEmpty()) {

                String gsdSpecialFolderRelativePath =
                        globalSignalQualifiedName.substring(indexOfSlash + 1,
                                indexOfHash);

                if (gsdSpecialFolderRelativePath != null
                        && !gsdSpecialFolderRelativePath.isEmpty()) {

                    /*
                     * get all the studio projects
                     */
                    IProject[] allStudioProjects =
                            ProjectUtil.getAllStudioProjects();

                    for (IProject studioProject : allStudioProjects) {

                        if (studioProject.isAccessible()
                                && projectId.equals(ProjectUtil
                                        .getProjectId(studioProject))) {

                            /*
                             * get all GSD special folders in the project
                             */
                            List<SpecialFolder> allSpecialFoldersOfKind =
                                    SpecialFolderUtil
                                            .getAllSpecialFoldersOfKind(studioProject,
                                                    GsdConstants.GSD_SPECIAL_FOLDER_KIND);
                            /*
                             * Fetch the GSD file based upon the path that we
                             * got from the signal name. This path is relative
                             * to GSD special folder. Note that we just need to
                             * return the GSD file, not necessary that it should
                             * exist physically.
                             */
                            for (SpecialFolder eachGsdFpecialFolder : allSpecialFoldersOfKind) {

                                IFolder folder =
                                        eachGsdFpecialFolder.getFolder();

                                if (folder != null) {

                                    IFile gsdFile =
                                            folder.getFile(new Path(
                                                    gsdSpecialFolderRelativePath));

                                    if (gsdFile != null) {

                                        return gsdFile;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
