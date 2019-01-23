/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.internal.ui.IPreferenceIds;
import org.eclipse.team.internal.ui.TeamUIPlugin;
import org.tigris.subversion.subclipse.core.ISVNLocalResource;
import org.tigris.subversion.subclipse.core.ISVNRemoteFolder;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.core.SVNProviderPlugin;
import org.tigris.subversion.subclipse.core.SVNTeamProvider;
import org.tigris.subversion.subclipse.core.resources.LocalResourceStatus;
import org.tigris.subversion.subclipse.core.resources.SVNWorkspaceRoot;
import org.tigris.subversion.subclipse.core.util.Util;
import org.tigris.subversion.subclipse.ui.ISVNUIConstants;
import org.tigris.subversion.subclipse.ui.SVNUIPlugin;
import org.tigris.subversion.svnclientadapter.ISVNClientAdapter;
import org.tigris.subversion.svnclientadapter.ISVNDirEntry;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNNodeKind;
import org.tigris.subversion.svnclientadapter.SVNRevision;
import org.tigris.subversion.svnclientadapter.SVNStatusKind;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * 
 * 
 * @author kupadhya
 * @since 12 Dec 2012
 */
public class SVNUtils {

    /**
     * 
     */
    private static final QualifiedName EXPORT_WITH_SVN_INFO =
            new QualifiedName(RCPActivator.PLUGIN_ID, ".ExportWithSVNInfo"); //$NON-NLS-1$

    private static final String SVN_NATURE_PROPERTY =
            "org.tigris.subversion.subclipse.core.svnnature"; //$NON-NLS-1$

    /**
     * Client adapter implementation identifier Copied constant from
     * org.tigris.subversion.svnclientadapter.svnkit.SvnKitClientAdapterFactory
     */
    private static final String SVNKIT_CLIENT = "svnkit"; //$NON-NLS-1$

    public static boolean isXPDProjectPresent(ISVNRemoteFolder remoteFolder) {
        boolean toReturn = false;
        try {
            ISVNClientAdapter svnClient =
                    remoteFolder.getRepository().getSVNClient();
            ISVNDirEntry[] rootFiles =
                    svnClient.getList(remoteFolder.getUrl(),
                            SVNRevision.HEAD,
                            false);
            for (int j = 0; j < rootFiles.length; j++) {
                if ((rootFiles[j].getNodeKind() == SVNNodeKind.FILE)
                        && (".config".equals(rootFiles[j].getPath()))) { //$NON-NLS-1$
                    toReturn = true;
                    break;
                }
            }
        } catch (SVNClientException e) {
        } catch (SVNException e) {
        }
        return toReturn;
    }

    /**
     * returns true if all projects in the array are under SVN
     * 
     * @param project
     * @return
     */
    public static boolean areAllProjectsUnderSVNControl(IProject[] project) {
        if (project == null || project.length < 1) {
            return false;
        }
        boolean toReturn = true;
        for (int index = 0; index < project.length; index++) {
            toReturn = isProjectUnderSVN(project[index]);
            if (!toReturn) {
                toReturn = false;
                break;
            }
        }
        return toReturn;
    }

    public static boolean isProjectUnderSVN(IProject project) {
        try {
            if (project.getPersistentProperties()
                    .containsValue(SVNUtils.SVN_NATURE_PROPERTY)) {
                return true;
            }
        } catch (CoreException e) {
        }
        return false;
    }

    /**
     * @return
     */
    public static ImageDescriptor getSVNWizardImageDescriptor() {
        return XpdResourcesUIActivator
                .getImageDescriptor("icons/ProjectWizardPage.png"); //$NON-NLS-1$
    }

    /**
     * 
     * @param projectArr
     * @return
     */
    public static boolean areProjectsInConflictWithSVN(IProject[] projectArr) {
        boolean toReturn = false;
        for (int index = 0; index < projectArr.length; index++) {
            IProject iProject = projectArr[index];
            toReturn = isProjectInConflictWithSVN(iProject);
            if (toReturn) {
                break;
            }
        }
        return toReturn;
    }

    /**
     * 
     * @param project
     * @return
     */
    public static boolean isProjectInConflictWithSVN(IProject project) {
        String conflictSessionProperty = getConflictSessionProperty(project);
        if (conflictSessionProperty != null) {
            return true;
        }
        // check whether any of the project resources are in the conflict state.
        final Map<IResource, Boolean> resourceStatusMap =
                new HashMap<IResource, Boolean>();
        try {
            project.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource resource) throws CoreException {
                    if (resource instanceof IFile) {
                        ISVNLocalResource svnResourceFor =
                                SVNWorkspaceRoot.getSVNResourceFor(resource);
                        if (SVNStatusKind.CONFLICTED == svnResourceFor
                                .getStatus().getStatusKind()) {
                            resourceStatusMap.put(resource, Boolean.TRUE);
                        }
                        return false;
                    }
                    return true;
                }
            });
        } catch (CoreException e) {
        } finally {
            if (!resourceStatusMap.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * stores persistent property whether the project is in conflict state
     * 
     * @param project
     * @param value
     * @throws CoreException
     */
    public static void storeConflictSessionProperty(IProject[] project,
            String value) {
        try {
            for (int index = 0; index < project.length; index++) {
                if (project[index] != null && project[index].isOpen()) {
                    project[index].setPersistentProperty(EXPORT_WITH_SVN_INFO,
                            value);
                }
            }
        } catch (CoreException e) {
        }

    }

    /**
     * stores persistent property whether the project is in conflict state
     * 
     * @param project
     * @param value
     * @throws CoreException
     */
    public static String getConflictSessionProperty(IProject project) {
        try {
            if (project != null && project.isOpen()) {
                return project.getPersistentProperty(EXPORT_WITH_SVN_INFO);
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * hack to force couple of SVN preferences: 1. only show dirty flag &
     * project name (no SVN URL) 2. show SVN console on all svn intercations.
     * 
     */
    public static void forceSVNPreferenceStoreSettings() {
        IPreferenceStore preferenceStore =
                SVNUIPlugin.getPlugin().getPreferenceStore();
        preferenceStore.setValue(ISVNUIConstants.PREF_PROJECTTEXT_DECORATION,
                "{dirty_flag}{name}"); //$NON-NLS-1$
        preferenceStore.setValue(ISVNUIConstants.PREF_CONSOLE_SHOW_ON_MESSAGE,
                Boolean.TRUE);
        // disabling pop up dialog for asking whether perspective should be
        // opened
        IPreferenceStore teamUIPluginStore =
                TeamUIPlugin.getPlugin().getPreferenceStore();
        teamUIPluginStore
                .setDefault(IPreferenceIds.SYNCHRONIZING_COMPLETE_PERSPECTIVE,
                        MessageDialogWithToggle.NEVER);
        // forcing SVN Kit to be used
        SVNProviderPlugin.getPlugin().getSVNClientManager()
                .setSvnClientInterface(SVNUtils.SVNKIT_CLIENT);

    }

    /**
     * Get the SVN repository URL of the given resource.
     * 
     * @param resource
     * @return
     * @throws SVNException
     */
    public static String getSVNRepositoryURL(IResource resource)
            throws SVNException {
        SVNTeamProvider svnProvider =
                (SVNTeamProvider) RepositoryProvider.getProvider(resource
                        .getProject(), SVNProviderPlugin.getTypeId());
        if (svnProvider != null) {
            ISVNLocalResource svnResource =
                    SVNWorkspaceRoot.getSVNResourceFor(resource);

            if (svnResource != null) {
                LocalResourceStatus status = svnResource.getStatus();
                if (status != null) {
                    return Util.unescape(status.getUrlString());
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param studioProjectArr
     * @param fileExtensionsList
     * @return
     */
    public static List<WorkingCopy> getAllDirtyWorkingCopiesInProjectArray(
            final IResource[] studioProjectArr,
            final List<String> fileExtensionsList) {
        final List<WorkingCopy> dirtyWCList = new ArrayList<WorkingCopy>();
        for (int index = 0; index < studioProjectArr.length; index++) {
            getAllDirtyWorkingCopiesInAProject(studioProjectArr[index],
                    fileExtensionsList,
                    dirtyWCList);
        }
        return dirtyWCList;
    }

    /**
     * Return all working copies that are dirty in a project
     * 
     * @param studioProject
     * @param fileExtensionsList
     * @return
     */
    public static List<WorkingCopy> getAllDirtyWorkingCopiesInAProject(
            final IResource studioProject,
            final List<String> fileExtensionsList,
            final List<WorkingCopy> dirtyWCList) {
        try {
            studioProject.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource resource) throws CoreException {
                    if (resource instanceof IFile) {
                        IFile fileResource = (IFile) resource;
                        String fileExtension = fileResource.getFileExtension();
                        if (fileExtensionsList != null
                                && fileExtensionsList.contains(fileExtension)) {
                            WorkingCopy workingCopy =
                                    WorkingCopyUtil
                                            .getWorkingCopy(fileResource);
                            if (workingCopy != null) {
                                boolean workingCopyDirty =
                                        workingCopy.isWorkingCopyDirty();
                                if (workingCopyDirty) {
                                    dirtyWCList.add(workingCopy);
                                }
                            }

                        }
                        return false;
                    }
                    return true;
                }
            });
        } catch (CoreException e) {
        }
        return dirtyWCList;
    }

    public static void saveAllDirtyWC(List<WorkingCopy> wcList) {
        if (wcList == null) {
            return;
        }
        for (WorkingCopy workingCopy : wcList) {
            try {
                workingCopy.save();
            } catch (IOException e) {
            }
        }
    }

    public static List<String> getUserEditableRCPFileExtensionList() {
        List<String> toReturn = new ArrayList<String>();
        toReturn.add("xpdl"); //$NON-NLS-1$
        toReturn.add("bom"); //$NON-NLS-1$
        toReturn.add("om"); //$NON-NLS-1$
        return toReturn;
    }

}
