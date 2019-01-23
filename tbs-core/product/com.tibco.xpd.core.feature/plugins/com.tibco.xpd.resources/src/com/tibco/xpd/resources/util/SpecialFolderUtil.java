/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;

/**
 * Special Folder util class. This allows getting special folder relative paths
 * of resources.
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderUtil {

    private static Logger LOG = XpdResourcesPlugin.getDefault().getLogger();

    /**
     * Get the root special folder container of the given resource.
     * 
     * @param resource
     * @return special folder if the resource is contained in one,
     *         <code>null</code> otherwise.
     * 
     * @since 3.6
     */
    public static SpecialFolder getRootSpecialFolder(IResource resource) {
        SpecialFolder sf = null;

        if (resource != null) {
            ProjectConfig config = getProjectConfig(resource.getProject());
            if (config != null) {
                sf = config.getSpecialFolders().getFolderContainer(resource);
            }
        }

        return sf;
    }

    /**
     * Checks if the project contains a special folder of a particular kind
     * 
     * @param project
     * @return true if special folder of specified kind is found else returns
     *         false
     */
    public static boolean containsSpecialFolderOfKind(IProject project,
            String kind) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null && kind != null) {
            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(kind);
            return (folders != null && folders.size() > 0);
        } else {
            return false;
        }
    }

    /**
     * Checks if the project contains a special folder of a particular kind. If
     * isIgnoreEmpty is true then we only count the special folder if it has any
     * contents inside it.
     * 
     * @param project
     * @param kind
     * @param isIgnoreEmpty
     * @return true if special folder of specified kind is found else returns
     *         false
     */
    public static boolean containsSpecialFolderOfKind(IProject project,
            String kind, boolean isIgnoreEmpty) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null && kind != null) {
            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(kind);
            boolean isContainSpecialFolders =
                    (folders != null && folders.size() > 0);
            if (isContainSpecialFolders && isIgnoreEmpty) {
                int members = 0;
                for (SpecialFolder specialFolder : folders) {
                    IFolder folder = specialFolder.getFolder();
                    if (folder != null && folder.exists()) {
                        try {
                            members += folder.members().length;
                        } catch (CoreException e) {
                            LOG.error(e);
                        }
                    }
                }

                if (members == 0) {
                    return false;
                }
            }
            return isContainSpecialFolders;
        } else {
            return false;
        }
    }

    /**
     * Returns the kind of the special folder passed or null if it is not an
     * special folder or the folder doesn't exist
     * 
     * @param folder
     * @return String the kind of the special folder, null if it is not an
     *         special folder.
     */
    public static String getSpecialFolderKind(IFolder folder) {
        String kind = null;
        if (folder != null) {
            IProject project = folder.getProject();
            if (project != null) {
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
                if (config != null) {
                    EList<SpecialFolder> folders =
                            config.getSpecialFolders().getFolders();
                    if (folders != null && !folders.isEmpty()) {
                        for (SpecialFolder specialFolder : folders) {
                            IFolder specFolder = specialFolder.getFolder();
                            if (specFolder != null && specFolder.equals(folder)) {
                                kind = specialFolder.getKind();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return kind;
    }

    /**
     * Returns a special folder of a given kind for the project and creates a
     * new one with defaultFolderName if it doesn't already exist. This method
     * would create <b>derived</b> folder if it doesn't exist.
     * 
     * @param project
     *            the context project.
     * @param kind
     *            the special folder kind.
     * @param defaultFolderName
     *            the name of default special folder to be created in case it
     *            doesn't exist. Note that this will override any name already
     *            set in the project configuration file.
     * @return one of the special folders.
     */
    public static SpecialFolder getCreateSpecialFolderOfKind(IProject project,
            String kind, String defaultFolderName) {

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null && kind != null) {
            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(kind);
            if (folders != null && folders.size() > 0) {
                SpecialFolder sf = folders.get(0);
                if (sf.getFolder() == null) {
                    if (sf.getLocation() != null) {
                        createDerivedFolder(project, sf.getLocation());
                    } else {
                        createDerivedFolder(project, defaultFolderName);
                    }
                }
                return sf;
            } else {
                IFolder folder =
                        createDerivedFolder(project, defaultFolderName);
                config.getSpecialFolders().addFolder(folder, kind);
                return config.getSpecialFolders().getFolder(folder);
            }
        }
        return null;

    }

    /**
     * Creates folder in the project. If the folder is created it will be set as
     * derived.
     * 
     * @param project
     *            the project.
     * @param folderName
     *            the name of the folder.
     * @return the created folder.
     */
    private static IFolder createDerivedFolder(IProject project,
            String folderName) {
        final IFolder folder = project.getFolder(folderName);
        if (!folder.exists()) {
            try {
                folder.create(true, true, null);
                folder.setDerived(true);
            } catch (CoreException e) {
                LOG.error(e);
            }
        }
        return folder;
    }

    /**
     * Returns a special folder of a given kind for the project. This will only
     * return the first special folder it finds of the given kind. To get a list
     * of all special folders of a given kind use
     * <code>getAllSpecialFoldersOfKind</code>.
     * 
     * @see #getAllSpecialFoldersOfKind(IProject, String)
     * 
     * @param project
     * @param kind
     * @return
     */
    public static SpecialFolder getSpecialFolderOfKind(IProject project,
            String kind) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (null != config && null != kind) {
            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(kind);
            if (folders != null && folders.size() > 0) {
                return folders.get(0);
            }
        }
        return null;
    }

    /**
     * Get all {@link SpecialFolder}s of the given kind from the given project.
     * 
     * @param project
     * @param kind
     *            <code>SpecialFolder</code> kind.
     * @return List of <code>SpecialFolder</code>s. This list can be empty or
     *         <code>null</code> if no <code>SpecialFolder</code>s found.
     * 
     * @since 3.2
     */
    public static List<SpecialFolder> getAllSpecialFoldersOfKind(
            IProject project, String kind) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (null != config && null != kind) {
            return config.getSpecialFolders().getFoldersOfKind(kind);
        }
        return null;
    }

    /**
     * Get the parent of the given resource. If this resource is contained in a
     * special folder then the <code>SpecialFolder</code> will be returned,
     * otherwise its {@link IContainer} will be returned.
     * 
     * @param resource
     *            get parent of this resource
     * @return <code>SpecialFolder</code> or <code>IContainer</code> parent,
     *         <code>null</code> if no parent can be computed.
     * 
     * @since 3.1
     */
    public static Object getParent(IResource resource) {
        if (resource != null) {
            IContainer container = resource.getParent();

            if (container instanceof IFolder) {
                ProjectConfig config = getProjectConfig(container.getProject());

                if (config != null) {
                    SpecialFolder sf =
                            config.getSpecialFolders()
                                    .getFolder((IFolder) container);

                    return sf != null ? sf : container;
                }
            } else {
                return container;
            }
        }

        return null;
    }

    /**
     * Returns all resources in a given special folder of a certain kind
     * 
     * @param project
     *            - project to look at
     * @param kind
     *            - only look at special folders of this kind
     * @param extension
     *            - only return resources of this extension or if null then
     *            returns all resources
     * @return
     */
    public static ArrayList<IResource> getResourcesInSpecialFolderOfKind(
            IProject project, String kind, String extension) {
        ArrayList<IResource> resources = new ArrayList<IResource>();
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null && kind != null) {
            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(kind);
            Iterator<SpecialFolder> foldersIter = folders.iterator();
            while (foldersIter.hasNext()) {
                SpecialFolder specialFolder = foldersIter.next();
                try {
                    IFolder folder = specialFolder.getFolder();

                    if (folder != null && folder.isAccessible()) {
                        for (IResource resource : folder.members()) {
                            if (extension == null
                                    || extension.equals(resource
                                            .getFileExtension())) {
                                resources.add(resource);
                            }
                        }
                    }
                } catch (CoreException e) {
                    LOG.error(e);
                }
            }
        }
        return resources;
    }

    /**
     * Returns all resources in a given special folder of a certain kind with
     * infinite depth
     * 
     * @param project
     *            - project to look at
     * @param kind
     *            - only look at special folders of this kind
     * @param extension
     *            - only return resources of this extension or if null then
     *            returns all resources
     * @param includeContainers
     *            - if we want to include containers such as Folders
     * 
     * @see #getAllDeepResourcesInSpecialFolderKind(IProject, String,
     *      IResourceFilter)
     * @return
     * 
     */
    public static List<IResource> getAllDeepResourcesInSpecialFolderOfKind(
            IProject project, String kind, String extension,
            boolean includeContainers) {
        ArrayList<IResource> resources = new ArrayList<IResource>();
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null && kind != null) {
            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(kind);
            Iterator<SpecialFolder> foldersIter = folders.iterator();
            while (foldersIter.hasNext()) {
                SpecialFolder specialFolder = foldersIter.next();
                resources.addAll(getAllDeepResourcesInContainer(specialFolder
                        .getFolder(), extension, includeContainers));
            }
        }
        return resources;
    }

    /***
     * 
     * @param project
     *            - project to look at
     * @param kind
     *            - only look at special folders of this kind
     * @param filter
     *            - filter to tell what resources must be included
     * @return
     * @throws CoreException
     */
    public static Collection<IResource> getAllDeepResourcesInSpecialFolderKind(
            IProject project, String kind, IResourceFilter filter)
            throws CoreException {

        ArrayList<IResource> resources = new ArrayList<IResource>();
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (null != config && null != kind) {

            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(kind);
            Iterator<SpecialFolder> foldersIter = folders.iterator();

            while (foldersIter.hasNext()) {

                SpecialFolder specialFolder = foldersIter.next();
                resources.addAll(getAllDeepResourcesInContainer(specialFolder
                        .getFolder(), filter));
            }

        }

        return resources;
    }

    /**
     * 
     * @param container
     *            - container/folder to look at
     * @param filter
     *            - filter to tell what resources to include
     * @return
     * @throws CoreException
     */
    public static Collection<IResource> getAllDeepResourcesInContainer(
            final IContainer container, final IResourceFilter filter)
            throws CoreException {

        final Set<IResource> resources = new HashSet<IResource>();

        if (container != null && container.exists()) {
            container.accept(new IResourceVisitor() {

                @Override
                public boolean visit(IResource resource) throws CoreException {

                    if (resource.exists()) {

                        if (filter == null || filter.includeResource(resource)) {
                            resources.add(resource);
                            return true;
                        }

                        if (resource instanceof IFolder) {
                            return true;
                        }
                    }

                    return false;
                }
            });
        }

        return resources;
    }

    /**
     * Returns all resources in a given folder with infinite depth
     * 
     * @param folder
     *            - container to look at
     * @param extension
     *            - only return resources of this extension or if null then
     *            returns all resources
     * @param includeContainers
     *            - if we want to include containers such as Folders
     * 
     * @see #getAllDeepResourcesInContainer(IContainer, IResourceFilter)
     * @return
     * 
     */
    public static Collection<IResource> getAllDeepResourcesInContainer(
            final IContainer folder, final String extension,
            final boolean includeContainers) {

        try {
            return getAllDeepResourcesInContainer(folder,
                    new IResourceFilter() {

                        @Override
                        public boolean includeResource(IResource resource) {
                            if (resource.exists()) {
                                if (resource instanceof IFolder) {
                                    return includeContainers;
                                } else if (resource instanceof IFile) {
                                    if (extension == null
                                            || extension.equals(resource
                                                    .getFileExtension())) {
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                    });
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Collections.emptyList();

    }

    /**
     * Get special folder (of given kind) relative path of the given resource.
     * If the resource is not contained in the special folder of given kind then
     * project relative path will be returned. If given kind is
     * <code>null</code> then it will check for any kind of special folder.
     * 
     * @param res
     *            the resource to get relative path of
     * @param specialFolderKind
     *            container special folder kind (will check at any level). If
     *            <code>null</code> then it will check for any special folder
     *            container.
     * @param defaultToProjectRelativePath
     *            To return project relative path if the resource is not
     *            contained within a special folder.
     * 
     * @return Special folder relative path. If no special folder container then
     *         project relative path or <code>null</code> depending on
     *         defaultToProjectRelativePath
     */
    public static IPath getSpecialFolderRelativePath(IResource res,
            String specialFolderKind, boolean defaultToProjectRelativePath) {

        Assert.isNotNull(res, "res"); //$NON-NLS-1$

        IProject project = res.getProject();
        ProjectConfig config = getProjectConfig(project);
        if (config != null) {
            SpecialFolder container =
                    config.getSpecialFolders().getFolderContainer(res);
            if (container != null
                    && (specialFolderKind == null || container.getKind()
                            .equals(specialFolderKind))) {

                return getSpecialFolderRelativePath(container, res);
            }
        }

        if (defaultToProjectRelativePath) {
            return res.getProjectRelativePath();
        }

        return null;
    }

    /**
     * Get special folder (of given kind) relative path of the given resource.
     * If the resource is not contained in the special folder of given kind then
     * project relative path will be returned. If given kind is
     * <code>null</code> then it will check for any kind of special folder.
     * 
     * @param res
     *            the resource to get relative path of
     * @param specialFolderKind
     *            container special folder kind (will check at any level). If
     *            <code>null</code> then it will check for any special folder
     *            container.
     * @return Special folder relative path. If no special folder container then
     *         project relative path.
     */
    public static IPath getSpecialFolderRelativePath(IResource res,
            String specialFolderKind) {
        return getSpecialFolderRelativePath(res, specialFolderKind, true);
    }

    /**
     * Get special folder (of given kind) relative path of the given path. If
     * the resource is not contained in the special folder of given kind then
     * project relative path will be returned. If given kind is
     * <code>null</code> then it will check for any kind of special folder.
     * 
     * @param project
     *            <code>IProject</code>
     * @param projectRelativePath
     *            resource's project relative path
     * @param specialFolderKind
     *            containing special folder kind (will check at any level), if
     *            <code>null</code> then it will check for any kind of special
     *            folder.
     * @return Special folder relative path. If no special folder container then
     *         project relative path.
     */
    public static IPath getSpecialFolderRelativePath(IProject project,
            String projectRelativePath, String specialFolderKind) {
        IPath path = null;

        Assert.isNotNull(project, "project"); //$NON-NLS-1$
        Assert.isNotNull(projectRelativePath, "project relative path"); //$NON-NLS-1$

        IResource res = project.findMember(projectRelativePath);

        if (res != null) {
            path = getSpecialFolderRelativePath(res, specialFolderKind);
        } else {
            throw new NullPointerException(
                    String.format("Unable to find resource %s in project %s", //$NON-NLS-1$
                            projectRelativePath,
                            project.getName()));
        }

        return path;
    }

    /**
     * Get the special folder relative path of the resource containing the given
     * <code>EObject</code>. {@link WorkingCopyUtil#getFile(EObject)
     * WorkingCopyUtil#getFile(EObject)} will be used to get the file containing
     * the <code>EObject</code>.
     * 
     * @param eo
     *            get the relative path of the resource that contains this
     *            <code>EObject</code>
     * @param specialFolderKind
     *            containing special folder kind (will check at any level), if
     *            <code>null</code> then it will check for any kind of special
     *            folder.
     * @return Resource's path relative to the special folder of the given kind.
     *         If the special folder is not found then project relative path
     *         will be returned.
     */
    public static IPath getSpecialFolderRelativePath(EObject eo,
            String specialFolderKind) {
        IPath path = null;

        Assert.isNotNull(eo, "eo"); //$NON-NLS-1$

        IFile file = WorkingCopyUtil.getFile(eo);

        if (file != null) {
            path = getSpecialFolderRelativePath(file, specialFolderKind);
        } else {
            throw new NullPointerException(
                    "Cannot get file containing the given EObject."); //$NON-NLS-1$
        }

        return path;
    }

    /**
     * Get the special folder relative path of the resource containing the given
     * <code>EObject</code>. {@link WorkingCopyUtil#getFile(EObject)
     * WorkingCopyUtil#getFile(EObject)} will be used to get the file containing
     * the <code>EObject</code>.
     * 
     * @param eo
     *            get the relative path of the resource that contains this
     *            <code>EObject</code>
     * @return Resource's path relative to the special folder of the given kind.
     *         If the special folder is not found then project relative path
     *         will be returned.
     */
    public static IPath getSpecialFolderRelativePath(EObject eo) {
        return getSpecialFolderRelativePath(eo, null);
    }

    /**
     * Get the path of the resource relative to its containing special folder.
     * 
     * @param sFolder
     *            containing <code>SpecialFolder</code> of the resource
     * @param resource
     *            <code>IResource</code> to get path of
     * @return Special folder relative path of the resource. If this resource is
     *         not contained in the special folder then project relative path
     *         will be returned.
     */
    public static IPath getSpecialFolderRelativePath(SpecialFolder sFolder,
            IResource resource) {
        IPath path = null;

        Assert.isNotNull(sFolder, "special folder"); //$NON-NLS-1$
        Assert.isNotNull(resource, "resource"); //$NON-NLS-1$

        path = resource.getProjectRelativePath();

        if (sFolder != null && sFolder.getFolder() != null) {
            IPath sFolderPath = sFolder.getFolder().getProjectRelativePath();

            if (sFolderPath.isPrefixOf(path)) {

                path = path.removeFirstSegments(sFolderPath.segmentCount());
            }
        }

        return path;
    }

    /**
     * Get the <code>SpecialFolder</code> relative path of the given file. This
     * will use the special folder file binding information to determine which
     * special folder this file should belong in and if it does then its
     * relative path will be returned. If this file is in the wrong special
     * folder or not contained in a special folder then the project relative
     * path will be returned.
     * 
     * @see SpecialFolderFileBindingUtil
     * 
     * @param file
     *            file to get relative path of.
     * @return <code>SpecialFolder</code> relative path, or project relative
     *         path if not in a valid special folder.
     * 
     * @since 3.0
     */
    public static IPath getSpecialFolderRelativePath(IFile file) {
        IPath path = null;

        if (file != null) {
            List<String> kinds =
                    Arrays.asList(SpecialFolderFileBindingUtil.getInstance()
                            .getSpecialFolderKinds(file.getName()));

            if (!kinds.isEmpty()) {
                // Get special folder container of the given file
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(file.getProject());

                if (config != null) {
                    SpecialFolder sf =
                            config.getSpecialFolders().getFolderContainer(file);

                    // If this is the right special folder (based on file name
                    // binding then return path relative to it
                    if (sf != null && kinds.contains(sf.getKind())) {
                        path = getSpecialFolderRelativePath(sf, file);
                    }
                }
            }

            // If no path was determined relative to special folders the return
            // project relative path
            if (path == null) {
                path = file.getProjectRelativePath();
            }
        }

        return path;
    }

    /**
     * Resolve the given special folder relative path to an <code>IFile</code>.
     * This method won't take into consideration related projects so it's
     * equivalent of :
     * <code>resolveSpecialFolderRelativePath(project, specialFolderKind,
                specialFolderRelativePath, false)</code>.
     * 
     * @param project
     *            parent project
     * @param specialFolderKind
     *            kind of special folder containing the resource
     * @param specialFolderRelativePath
     *            path to the resource relative to the special folder
     * @return <code>IFile</code> if the path is resolved, <code>null</code>
     *         otherwise.
     */
    public static IFile resolveSpecialFolderRelativePath(IProject project,
            String specialFolderKind, String specialFolderRelativePath) {
        return resolveSpecialFolderRelativePath(project,
                specialFolderKind,
                specialFolderRelativePath,
                false);
    }

    /**
     * Resolve the given special folder relative path to an <code>IFile</code>.
     * 
     * @param project
     *            parent project
     * @param specialFolderKind
     *            kind of special folder containing the resource
     * @param specialFolderRelativePath
     *            path to the resource relative to the special folder
     * @param useReferencedProjects
     *            if the referenced projects should be taken into consideration.
     * @return <code>IFile</code> if the path is resolved, <code>null</code>
     *         otherwise.
     * @since 3.1
     */
    public static IFile resolveSpecialFolderRelativePath(IProject project,
            String specialFolderKind, String specialFolderRelativePath,
            boolean useReferencedProjects) {

        Assert.isNotNull(project, "Passed project parameter was 'null'."); //$NON-NLS-1$
        Assert.isNotNull(specialFolderKind,
                "Passed specialFolderKind parameter was 'null'."); //$NON-NLS-1$
        Assert.isNotNull(specialFolderRelativePath,
                "Passed specialFolderRelativePath parameter was 'null'."); //$NON-NLS-1$

        /*
         * SID - Use linked has set so that we preserve the main project at the
         * head of the list for resolving references!
         */
        Set<IProject> projects = new LinkedHashSet<IProject>();
        projects.add(project);
        if (useReferencedProjects) {
            // Get all referenced projects
            ProjectUtil.getReferencedProjectsHierarchy(project, projects);
        }

        // Get all special folders of the same kind from all projects
        for (IProject p : projects) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(p);
            if (config != null) {
                for (SpecialFolder sf : config.getSpecialFolders()
                        .getFoldersOfKind(specialFolderKind)) {
                    if (sf != null && sf.getFolder() != null) {
                        IResource member =
                                sf.getFolder()
                                        .findMember(specialFolderRelativePath);
                        if (member != null && member instanceof IFile) {
                            return (IFile) member;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Get unique filename from the container path given. This will check
     * whether container is a special folder or contained in a special folder.
     * <p>
     * If the container path is a special folder or contained in a special
     * folder then the special folder will be checked for whether it allows
     * duplicate resources. If it does then the container given will be checked
     * for unique filename (result will be same as output of
     * {@link #getUniqueFileNameInContainer(IContainer, String, String)
     * getUniqueFileNameInContainer}. Otherwise all special folders (including
     * referenced projects) will be searched for the unique file name (taking
     * special folder relative path into account if required).
     * </p>
     * <p>
     * If the container path is not a special folder, or contained in a special
     * folder, then the unique file name in the container will be returned (same
     * result as
     * {@link #getUniqueFileNameInContainer(IContainer, String, String)
     * getUniqueFileNameInContainer}).
     * </p>
     * <p>
     * The file name given will be searched for first. If file exists with the
     * same name then subsequent searches will be made with a number appended to
     * the file name.
     * </p>
     * 
     * @see #getUniqueFileNameInContainer(IContainer, String, String)
     * 
     * @param containerFullPath
     *            full path to the container the file will be created in.
     * @param fileName
     *            name of file.
     * @param fileExtension
     *            extension of file. This can be <code>null</code>.
     * @return unique file name.
     */
    public static String getUniqueFileName(IPath containerFullPath,
            String fileName, String fileExtension) {
        String uniqueName = null;

        if (containerFullPath != null && fileName != null) {
            IResource container =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .findMember(containerFullPath);
            if (container instanceof IContainer) {
                IProject project = container.getProject();
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);

                if (config != null) {
                    // Find the special folder this container is or belongs to
                    SpecialFolder sFolder =
                            config.getSpecialFolders()
                                    .getFolderContainer(container);

                    if (sFolder != null) {
                        ISpecialFolderModel info =
                                config.getSpecialFolders()
                                        .getFolderKindInfo(sFolder.getKind());

                        if (info != null) {
                            if (info.isDuplicateResourcesAllowed()) {
                                /*
                                 * Duplicate resources are allowed so find
                                 * unique file name within the container
                                 */
                                uniqueName =
                                        getUniqueFileNameInContainer((IContainer) container,
                                                fileName,
                                                fileExtension);
                            } else {
                                /*
                                 * Duplicate resources not allowed so find
                                 * unique file name from across all special
                                 * folders
                                 */
                                // Check if the path is a special folder or a
                                // container in a special folder
                                IPath containerPath = null;
                                if (container != sFolder.getFolder()) {
                                    containerPath =
                                            getSpecialFolderRelativePath(sFolder,
                                                    container);
                                }

                                uniqueName =
                                        getUniqueFileName(sFolder,
                                                containerPath,
                                                fileName,
                                                fileExtension);
                            }
                        }
                    } else {
                        // No special folder in container so find unique
                        // filename in container
                        uniqueName =
                                getUniqueFileNameInContainer((IContainer) container,
                                        fileName,
                                        fileExtension);
                    }
                }
            } else {
                throw new NullPointerException(
                        String.format("Unable to find resource %s", containerFullPath //$NON-NLS-1$
                                        .toString()));
            }
        }

        // If for some reason the unique file name isn't set then return the
        // given filename
        if (uniqueName == null) {
            uniqueName = fileName.trim();

            if (uniqueName != null && uniqueName.length() > 0
                    && fileExtension != null
                    && fileExtension.trim().length() > 0) {
                uniqueName += "." + fileExtension.trim(); //$NON-NLS-1$
            }
        }

        return uniqueName;
    }

    /**
     * Get a unique file name in the given special folder and optionally
     * container in the special folder. This will take into account whether the
     * special folder allows duplicate resources: if it does then it will just
     * find unique file name in the given container (will result in same output
     * as {@link #getUniqueFileNameInContainer(IContainer, String, String)
     * getUniqueFileNameInContainer}). If the special folder does not allow
     * duplicate resources then all special folders of the kind (including
     * referenced projects) will be searched for a unique file name (in the
     * container given).
     * <p>
     * The file name given will be searched for first. If file exists with the
     * same name then subsequent searches will be made with a number appended to
     * the file name.
     * </p>
     * 
     * @param sFolder
     *            special folder.
     * @param speciaFolderRelativeContainerPath
     *            path of container relative to the special folder. This can be
     *            <code>null</code> when the file is being created directly in
     *            the special folder.
     * @param fileName
     *            name of file.
     * @param fileExtension
     *            extension of file. This can be <code>null</code>.
     * @return unique file name
     */
    public static String getUniqueFileName(SpecialFolder sFolder,
            IPath speciaFolderRelativeContainerPath, String fileName,
            String fileExtension) {

        String uniqueName = fileName.trim();

        if (uniqueName != null && uniqueName.length() > 0) {
            String filePattern = uniqueName + "%d"; //$NON-NLS-1$

            if (fileExtension != null && fileExtension.trim().length() > 0) {
                uniqueName += "." + fileExtension.trim(); //$NON-NLS-1$
                filePattern += "." + fileExtension.trim(); //$NON-NLS-1$
            }

            IProject currProject = sFolder.getProject();

            if (currProject != null) {
                // Get all referenced projects
                Set<IProject> projects = new HashSet<IProject>();
                projects.add(currProject);
                ProjectUtil.getReferencedProjectsHierarchy(currProject,
                        projects);
                Set<SpecialFolder> sFolders = new HashSet<SpecialFolder>();

                // Get all special folders of the same kind from all projects
                for (IProject project : projects) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(project);

                    if (config != null) {
                        sFolders.addAll(config.getSpecialFolders()
                                .getFoldersOfKind(sFolder.getKind()));
                    }
                }

                boolean found = false;
                int idx = 1;
                while (!found) {
                    found = true;
                    // Search all special folders for the unique filename
                    for (SpecialFolder sf : sFolders) {
                        IFolder folder = sf.getFolder();

                        if (folder != null && folder.isAccessible()) {
                            IResource member = null;

                            if (speciaFolderRelativeContainerPath != null) {
                                // Find file in container
                                member =
                                        folder.findMember(speciaFolderRelativeContainerPath
                                                .append(uniqueName));
                            } else {
                                // Find file directly under the special folder
                                member = folder.findMember(uniqueName);
                            }

                            if (member != null) {
                                // File with the given name already exists
                                found = false;
                                uniqueName = String.format(filePattern, idx++);
                            }
                        }
                    }
                }
            }
        }

        return uniqueName;
    }

    /**
     * Get a unique file name in the given container. The container will be
     * checked for whether a file with the given file name exists. If it doesn't
     * then that file name will be returned (including extension if one is
     * provided). Otherwise, the file name will be appended by a number and the
     * number will be incremented until a file with that file name does not
     * exist in the container.
     * 
     * @param container
     *            the container to check
     * @param fileName
     *            name of the file
     * @param fileExtension
     *            extension of the file. This can be <code>null</code>.
     * @return unique file name
     */
    public static String getUniqueFileNameInContainer(IContainer container,
            String fileName, String fileExtension) {
        String uniqueName = fileName.trim();

        if (container != null && fileName != null) {
            String filePattern = uniqueName + "%d"; //$NON-NLS-1$
            if (fileExtension != null && fileExtension.trim().length() > 0) {
                uniqueName += "." + fileExtension.trim(); //$NON-NLS-1$
                filePattern += "." + fileExtension.trim(); //$NON-NLS-1$
            }

            int idx = 1;

            while (container.findMember(uniqueName) != null) {
                uniqueName = String.format(filePattern, idx++);
            }
        }
        return uniqueName;
    }

    /**
     * Get special folders of the given kind that are marked as generated.
     * 
     * @param project
     * @param kind
     *            special folder kind
     * @param generated
     *            the generated value set in the special folder,
     *            <code>null</code> to get all generated folders.
     * @return collection of special folder if found, empty collection
     *         otherwise.
     * @since 3.5.10
     */
    public static Collection<SpecialFolder> getGeneratedSpecialFoldersOfKind(
            IProject project, String kind, String generated) {
        List<SpecialFolder> folders = new ArrayList<SpecialFolder>();

        for (SpecialFolder sf : getAllSpecialFoldersOfKind(project, kind)) {
            if (sf.getGenerated() != null
                    && (generated == null || generated
                            .equals(sf.getGenerated()))) {
                folders.add(sf);
            }
        }
        return folders;
    }

    /**
     * Get project config for the given project.
     * 
     * @param project
     * @return <code>ProjectConfig</code>.
     */
    private static final ProjectConfig getProjectConfig(IProject project) {

        Assert.isNotNull(project, "project"); //$NON-NLS-1$

        return XpdResourcesPlugin.getDefault().getProjectConfig(project);
    }
}
