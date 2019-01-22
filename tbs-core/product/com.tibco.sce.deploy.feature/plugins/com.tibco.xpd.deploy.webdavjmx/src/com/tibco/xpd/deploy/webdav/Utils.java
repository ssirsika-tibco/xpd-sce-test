/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * 
 * Utility class for the deployment plugin.
 * 
 * @since 1.0
 * @author TIBCO Software, Inc.
 */
public final class Utils {

    /** ID for the special forms folder type. */
    public static final String FORMS_FOLDER_TYPE_ID = "forms"; //$NON-NLS-1$

    /**
     * Mapping of file path to IFile. The deployment framework allows us to only
     * exchange the file path as string. Ideally it should handle IFile. For
     * now, we are using the map to resolve the file path back to its IFile.
     **/
    private static Map<String, IFile> mPathToIFileMap;

    /**
     * Gets the IFile resource for the given file path passed by the deployment
     * wizard.
     * 
     * @param path
     *            - file path
     * @return IFile resource corresponding to the path or null if not found.
     * @since 1.1
     */
    public static IFile getIFileForPath(String path) {
        if (path != null) {
            if (mPathToIFileMap == null) {
                return Utils.getFormIFileForPath(path);
            }
            if (mPathToIFileMap.containsKey(path)) {
                return mPathToIFileMap.get(path);
            } else {
                return Utils.getFormIFileForPath(path);
            }
        }
        return null;
    }

    private static IFile getFormIFileForPath(String path) {
        Object[] projects = getAllProjectsWithValidFormsFolders();
        for (int i = 0; i < projects.length; i++) {
            List<IResource> resourcesList =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind((IProject) projects[i],
                                    FORMS_FOLDER_TYPE_ID,
                                    null,
                                    false);
            if (resourcesList != null && !resourcesList.isEmpty()) {
                for (IResource resource : resourcesList) {
                    if (resource instanceof IFile
                            && isResourceForPath(path, resource)) {
                        return (IFile) resource;
                    }
                }
            }

        }
        return null;
    }

    public static boolean isResourceForPath(String path, IResource resource) {
        boolean isFileForPath = false;
        if (path != null && resource != null) {
            // Check the portable string
            if (resource.getRawLocation() != null
                    && resource.getRawLocation().toPortableString() != null
                    && resource.getRawLocation().toPortableString()
                            .equals(path)) {
                return true;
            }
            try {
                // Check the location url string
                if (resource.getLocationURI() != null
                        && resource.getLocationURI().toURL() != null
                        && resource.getLocationURI().toURL().toString() != null
                        && resource.getLocationURI().toURL().toString()
                                .equals(path)) {
                    return true;
                }
            } catch (MalformedURLException e) {
                // Ignore
            }
        }
        return isFileForPath;
    }

    /**
     * 
     * Updates the PathToIFileMap with this entry.
     * 
     * @param path
     *            - file path to the IFile resource
     * @param file
     *            - IFile resource
     * @since 1.1
     */
    public static void putIntoPathToIFileMap(String path, IFile file) {

        if (mPathToIFileMap == null) {
            mPathToIFileMap = new HashMap<String, IFile>();
        }
        mPathToIFileMap.put(path, file);
    }

    /**
     * 
     * Initializes the Path To IFile Map.
     * 
     * @since 1.1
     */
    public static void clearPathToIFileMap() {
        mPathToIFileMap = null;
    }

    /**
     * 
     * Gets all projects with valid forms folders for a given workspace.
     * 
     * @return array of IProject objects.
     * @since 1.0
     */
    public static Object[] getAllProjectsWithValidFormsFolders() {
        List<IProject> list = new ArrayList<IProject>();

        IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = wsRoot.getProjects();
        for (IProject element : projects) {
            List<SpecialFolder> forms = getFormsSpecialFolders(element);
            if (forms.size() == 0) {
                continue;
            } else {
                list.add(element);
            }
        }
        return list.toArray();
    }

    /**
     * 
     * Gets all special folders of type forms for a given project.
     * 
     * @param project
     *            - given project
     * @return List of SpecialFolder objects.
     * @since 1.0
     */
    public static List<SpecialFolder> getFormsSpecialFolders(IProject project) {
        List<SpecialFolder> list = new ArrayList<SpecialFolder>();

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            for (SpecialFolder folder : config.getSpecialFolders().getFolders()) {
                if (folder.getKind().equalsIgnoreCase(FORMS_FOLDER_TYPE_ID))
                    list.add(folder);
            }
        }

        return list;
    }

    /**
     * 
     * Returns true of the folder contains a valid forms folder (special
     * folder).
     * 
     * @param folder
     *            - given IFolder
     * @return true if this folder contains a valid forms folder.
     * @since 1.0
     */
    public static boolean isContainsFormsFolder(IFolder folder) {
        boolean foundFormFile = false;
        try {
            if (folder.getName().equalsIgnoreCase("META-INF")) { //$NON-NLS-1$
                return true;
            }
            IResource[] members = folder.members();
            for (IResource element : members) {
                if (element instanceof IFolder) {
                    foundFormFile = isContainsFormsFolder((IFolder) element);
                    if (foundFormFile) {
                        return foundFormFile;
                    }
                } else if (element instanceof IFile) {
                    if (element.getName().indexOf(".form") != -1) { //$NON-NLS-1$
                        foundFormFile = true;
                        return foundFormFile;
                    }
                }
            }
        } catch (CoreException ce) {
            return foundFormFile;
        }
        return foundFormFile;
    }

    /**
     * 
     * Returns true of the folder contains a valid project(special folder).
     * 
     * @param folder
     *            - given IFolder
     * @return true if this folder contains a valid forms folder.
     * @since 1.0
     */
    public static boolean isContainsProject(IFolder folder) {
        return false;
    }

    public static boolean isValidProjectWithFormsFolder(String projectName) {
        Object[] projects = getAllProjectsWithValidFormsFolders();
        for (Object element : projects) {
            IProject project = (IProject) element;
            if (project.getName().equalsIgnoreCase(projectName))
                return true;
        }
        return false;
    }

    public static boolean isValidTargetFolder(IProject project,
            String folderName) {
        List<SpecialFolder> formsFolders =
                Utils.getFormsSpecialFolders(project);
        if (formsFolders.size() == 0) {
            return true;
        }

        for (int i = 0; i < formsFolders.size(); i++) {
            IFolder folder = formsFolders.get(i).getFolder();
            if (folder.getName().equalsIgnoreCase(folderName))
                return false;
        }
        return true;
    }

    /**
     * Private ctor enforces 'static helper' pattern.
     */
    private Utils() {
    }

    /**
     * Finds workspace IResource for the string containing local file system
     * path compatible URI.
     * 
     * <p>
     * <b>NOTE:</b> There might me many workspace IResources mapped to a system
     * file/directory. This method will only provide one of them and it's not
     * determined which one would it be.
     * </p>
     * 
     * @param url
     *            the string representing URI to the local file system
     *            file/directory. It also can be an absolute path.
     * @return
     * @throws MalformedURLException
     *             if the string is not a valid URL.
     * @throws URISyntaxException
     *             if the string is not a valid URI.
     * @throws IllegalArgumentException
     *             if local URI is incorrect.
     * @author Jan Arciuchiewicz
     * @since 3.3
     */
    public static IResource getIResourceFromURL(String url)
            throws MalformedURLException, URISyntaxException {
        File file = null;
        try {
            URI uri = new URL(url).toURI();
            file = new File(uri);
        } catch (MalformedURLException e) {
            // Check if the parameter is a path to an existing system resource.
            file = new File(url);
            if (!file.exists()) {
                throw e;
            }
        }
        Assert.isNotNull(file);
        IPath resourceLocPath = new Path(file.getAbsolutePath());
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        if (file.isFile()) {
            IFile[] files = root.findFilesForLocation(resourceLocPath);
            if (files.length > 0 && files[0].exists()) {
                return files[0];
            }
        } else if (file.isDirectory()) {
            IContainer[] containers =
                    root.findContainersForLocation(resourceLocPath);
            if (containers.length > 0 && containers[0] instanceof IFolder
                    && containers[0].exists()) {
                return containers[0];
            }
        }
        throw new IllegalArgumentException(String
                .format(Messages.Utils_correspondingIResourceMissing_message,
                        url));
    }

    /**
     * Determines if resource is a WebDAV special folder.
     * 
     * @param resource
     *            resource to ask.
     * @return true if resource is in a WebDAV special folder.
     * @author Jan Arciuchiewicz
     * @since 3.3
     */
    public static boolean isInDAVSpecialFolder(IResource resource) {
        if (resource instanceof IFile || resource instanceof IFolder) {
            SpecialFolders specialFolders =
                    XpdResourcesPlugin.getDefault().getProjectConfig(resource
                            .getProject()).getSpecialFolders();
            if (specialFolders != null) {
                EList<IFolder> davSpecialFolders =
                        specialFolders
                                .getEclipseIFoldersOfKind(WebDAVPlugin.WEBDAV_DEPLOYABLE_KIND);
                for (IFolder sf : davSpecialFolders) {
                    if (sf.getFullPath().isPrefixOf(resource.getFullPath())
                            && !sf.equals(resource)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}