/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.resources.internal.indexer.ResourceDependencyIndexer;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Extension of the {@link ResourceDependencyIndexer} to index implicit
 * dependencies that are added to validation issues.
 * <p>
 * <strong>** FOR INTERNAL USE ONLY **</strong>
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.5
 */
public final class ImplicitDependencyIndexer extends ResourceDependencyIndexer {

    public ImplicitDependencyIndexer(IResource resource) {
        super(resource, Type.IMPLICIT);
    }

    /**
     * Update the implicit dependency indexer for the given resource if the
     * issues contain a LINKED_RESOURCE additional info.
     * 
     * @param issues
     */
    public void updateImplicitDependenciesFromIssues(Collection<IIssue> issues) {
        if (getResource() != null && issues != null && !issues.isEmpty()) {

            Set<IResource> files = new HashSet<IResource>();

            for (IIssue issue : issues) {
                if (issue instanceof Issue) {
                    Map<String, String> info =
                            ((Issue) issue).getAdditionalInfo();
                    if (info != null && !info.isEmpty()) {
                        files.addAll(getResourcesFromLinkedResource(info
                                .get(ValidationActivator.LINKED_RESOURCE)));
                    }
                }
            }

            if (!files.isEmpty()) {
                updateDependencies(files);
            }
        }
    }

    /**
     * If the marker contains a <code>LINKED_RESOURCE</code> attribute in the
     * additional information then update the indexer with this information.
     * 
     * @param marker
     */
    public static void updateImplicitDependenciesFromMarker(IMarker marker) {
        if (marker != null && marker.exists()) {
            IResource res = marker.getResource();
            if (res instanceof IFile && res.exists()) {
                Properties info = ValidationUtil.getAdditionalInfo(marker);
                if (info != null) {
                    Object value =
                            info.get(ValidationActivator.LINKED_RESOURCE);
                    if (value instanceof String) {
                        Set<IFile> resSet =
                                getResourcesFromLinkedResource((String) value);
                        if (!resSet.isEmpty()) {
                            new ImplicitDependencyIndexer(res)
                                    .updateDependencies(new HashSet<IResource>(
                                            resSet));
                        }
                    }
                }
            }
        }
    }

    /**
     * Clear the implicit dependencies from the indexer for the given marker (if
     * it has a LINKED_RESOURCE set).
     * 
     * @param marker
     */
    public static void clearDependencies(IMarker marker) {
        if (marker != null) {
            IResource resource = marker.getResource();
            if (resource instanceof IFile) {
                IFile host = (IFile) resource;
                Properties info = ValidationUtil.getAdditionalInfo(marker);
                if (info != null) {
                    Object value =
                            info.get(ValidationActivator.LINKED_RESOURCE);
                    if (value instanceof String) {
                        Set<IFile> linkedResources =
                                getResourcesFromLinkedResource((String) value);

                        for (IFile linkedRes : linkedResources) {
                            clearIndexer(host, linkedRes, Type.IMPLICIT);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the resources that depend on the given resource.
     * 
     * @param dependsOn
     * @param type
     *            type of dependency, <code>null</code> for all types.
     * @return
     */
    public static Collection<IResource> getInverseDependencies(
            IResource dependsOn) {
        return getInverseDependencies(dependsOn, Type.IMPLICIT);
    }

    /**
     * Clear all entries from the indexer related to resources from the given
     * project (this will only clear the IMPLICIT dependencies).
     * 
     * @param project
     */
    public static void clearIndexer(IProject project) {
        if (project != null) {
            clearIndexer(project, Type.IMPLICIT);
        }
    }

    /**
     * Get the files that are set as the linked resources
     * 
     * @param value
     *            comma-separated URIs of the linked files.
     * @return
     */
    private static Set<IFile> getResourcesFromLinkedResource(String value) {
        Set<IFile> files = new HashSet<IFile>();

        if (value != null && value.length() > 0) {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

            for (String path : value.split(",")) { //$NON-NLS-1$
                URI uri = URI.createURI(path);

                if (uri != null) {
                    path = uri.toPlatformString(true);
                    if (path != null) {
                        IFile file = root.getFile(new Path(path));

                        if (file != null && file.exists()) {
                            files.add(file);
                        }
                    }
                }
            }
        }

        return files;
    }

}
