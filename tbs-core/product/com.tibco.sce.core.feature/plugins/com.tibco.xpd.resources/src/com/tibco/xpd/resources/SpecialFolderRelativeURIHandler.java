/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;

import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderFileBindingUtil;

/**
 * URIHandler implementation to resolve the URI's relative to the resource's
 * special folder. The file extension of the resource will determine which
 * special folder kinds to resolve against. If no special folders are found then
 * the project will be used as the root.
 * <p>
 * If the resource is not found in a project then its referenced projects will
 * be searched.
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.0
 */
public class SpecialFolderRelativeURIHandler extends URIHandlerImpl {

    private static final String PATHMAP = "pathmap:"; //$NON-NLS-1$

    private IProject baseProject;

    private SpecialFolderFileBindingUtil specialFolderBindingUtil;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.ecore.xmi.XMLResource$URIHandler#deresolve(org.eclipse
     * .emf.common.util.URI)
     */
    @Override
    public URI deresolve(URI uri) {
        // No need to store a path for same-resource references.
        if (uri.trimFragment().equals(baseURI))
            return super.deresolve(uri);

        // Ignore pathmap uris
        if (!uri.toString().startsWith(PATHMAP)) {
            String fragment = null;

            if (uri.hasFragment()) {
                fragment = uri.fragment();
                uri = uri.trimFragment();
            }
            IFile file = getFile(uri);

            // Get the special folder relative path of the file
            if (file != null) {
                IPath relativePath = null;
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(file.getProject());

                if (config != null) {
                    SpecialFolder sf =
                            config.getSpecialFolders().getFolderContainer(file);

                    if (sf != null) {
                        IFolder sfFolder = sf.getFolder();

                        if (sfFolder != null) {

                            relativePath = file.getFullPath()
                                    .removeFirstSegments(sfFolder.getFullPath()
                                            .segmentCount());
                        }
                    }
                }

                // If the file is not contained in a special folder then make
                // path relative to project NOT TRUE
                // FIXME: consider using query string for storing the special
                // folder relative patch
                if (relativePath == null) {
                    relativePath = new Path(file.getFullPath().lastSegment());
                }

                // Encode each segment in the relative path to create a valid
                // URI
                uri = URI.createURI(
                        URI.encodeSegment(relativePath.segment(0), false));
                if (relativePath.segmentCount() > 1) {
                    for (int idx = 1; idx < relativePath
                            .segmentCount(); idx++) {
                        uri = uri.appendSegment(
                                URI.encodeSegment(relativePath.segment(idx),
                                        false));
                    }
                }
            }

            if (fragment != null) {
                uri = uri.appendFragment(fragment);
            }
        }
        return uri;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.ecore.xmi.XMLResource$URIHandler#resolve(org.eclipse.
     * emf.common.util.URI)
     */
    @Override
    public URI resolve(URI uri) {

        if (uri.isCurrentDocumentReference()) {
            return super.resolve(uri);
        }

        // Ignore pathmap uris
        if (!(uri.toString().startsWith(PATHMAP) || uri.isPlatform())) {
            String fragment = null;

            if (uri.hasFragment()) {
                fragment = uri.fragment();
                uri = uri.trimFragment();
            }

            String uriStr = URI.decode(uri.toString());
            // Check if the file name has been bound with any special folders
            String[] kinds = getSpecialFolderKinds(uri);

            if (baseProject != null) {

                /*
                 * Sid XPD-1605: Loading the project hierarchy can be very hefty
                 * (there are two java plugin's for each bom namespace for a
                 * start!).
                 * 
                 * So Always check in the same local project first before
                 * looking in referenced projects (and then when we do look in
                 * referenced projects, only bother with XpdNature ones only).
                 */
                IFile file = resolveURIInProject(baseProject, uriStr, kinds);

                if (file == null) {
                    /* Not in same project check referenced projects. */
                    Set<IProject> projects = new LinkedHashSet<IProject>();

                    ProjectUtil.getReferencedProjectsHierarchy(baseProject,
                            projects,
                            true);

                    /*
                     * Resolve the URI against special folders in the project,
                     * or referenced projects. For each project if the file is
                     * not found in the special folder then check against the
                     * project
                     */
                    for (IProject project : projects) {

                        file = resolveURIInProject(project, uriStr, kinds);

                        if (file != null) {
                            break;
                        }
                    }

                }

                if (file != null) {
                    uri = URI.createPlatformResourceURI(
                            file.getFullPath().toString(),
                            true);
                }

            }

            if (fragment != null) {
                uri = uri.appendFragment(fragment);
            }
        }

        return uri;
    }

    /**
     * @param project
     * @param uriStr
     * @param kinds
     * @return The file in the given project if it can be resolved as a file in
     *         one of the given folder kinds.
     */
    private IFile resolveURIInProject(IProject project, String uriStr,
            String[] kinds) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        IFile file = null;

        if (config != null) {
            Set<SpecialFolder> sFolders = new HashSet<SpecialFolder>();
            if (kinds.length > 0) {
                // Only look in special folder of kinds bound with this
                // file name
                for (String kind : kinds) {
                    sFolders.addAll(
                            config.getSpecialFolders().getFoldersOfKind(kind));
                }
            } else {
                sFolders.addAll(config.getSpecialFolders().getFolders());
            }

            if (!sFolders.isEmpty()) {
                for (SpecialFolder sf : sFolders) {
                    IFolder folder = sf.getFolder();

                    if (folder != null) {
                        IResource member = folder.findMember(uriStr);

                        if (member instanceof IFile) {
                            file = (IFile) member;
                            break;
                        }
                    }
                }
            } else if (kinds.length == 0) {
                // No special folders in this project and no special
                // folder binding so try project
                // root
                file = resolveInProjectRoot(project, uriStr);
            }
        } else if (kinds.length == 0) {
            // No config found and no special folder binding so try to
            // resolve in project root
            file = resolveInProjectRoot(project, uriStr);
        }
        return file;
    }

    /**
     * Resolve the given file uri in the root of the project.
     * 
     * @param project
     *            project to look in
     * @param filePath
     *            path of file
     * @return <code>IFile</code> if file found, <code>null</code> otherwise.
     */
    private IFile resolveInProjectRoot(IProject project, String filePath) {
        IFile file = null;

        if (project != null && filePath != null) {
            IResource resource = project.findMember(filePath);

            if (resource instanceof IFile) {
                file = (IFile) resource;
            }
        }

        return file;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.ecore.xmi.XMLResource$URIHandler#setBaseURI(org.eclipse
     * .emf.common.util.URI)
     */
    @Override
    public void setBaseURI(URI uri) {
        super.setBaseURI(uri);
        if (uri != null) {
            IFile file = getFile(uri);
            if (file != null) {
                baseProject = file.getProject();
            }
        }
    }

    /**
     * Find the special folder kind that the resource with the given uri will
     * potentially be found in. This will be determined by the special folder
     * file bindings.
     * 
     * @param uri
     * @return
     */
    private String[] getSpecialFolderKinds(URI uri) {

        URI theUri = uri.trimFragment();

        if (specialFolderBindingUtil == null) {
            specialFolderBindingUtil =
                    SpecialFolderFileBindingUtil.getInstance();
        }

        return specialFolderBindingUtil
                .getSpecialFolderKinds(theUri.lastSegment());
    }

    /**
     * Get the <code>IFile</code> from the given uri.
     * 
     * @param uri
     *            <code>URI</code> to resolve to an IFile.
     * @return an <code>IFile</code> if the uri can be resolved,
     *         <code>null</code> otherwise.
     */
    private IFile getFile(URI uri) {
        IFile file = null;

        if (uri.isPlatform()) {
            if ("resource".equals(uri.segment(0))) { //$NON-NLS-1$
                IPath path =
                        new Path(URI.decode(uri.path())).removeFirstSegments(1);
                file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            }
        } else if (uri.isFile()) {
            if (uri.isRelative()) {
                IResource res = ResourcesPlugin.getWorkspace().getRoot()
                        .findMember(uri.toFileString());

                if (res instanceof IFile) {
                    file = (IFile) res;
                }
            } else {
                file = ResourcesPlugin.getWorkspace().getRoot()
                        .getFileForLocation(new Path(uri.toFileString()));
            }
        }

        return file;
    }

}
