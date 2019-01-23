/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.resources.internal.indexer;

import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.db.Column;
import com.tibco.xpd.resources.internal.db.ResourceDbException;
import com.tibco.xpd.resources.internal.db.impl.derby.ResourceDbDerby;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Manages the resource dependency index. This handles both EXPLICIT and
 * IMPLICIT dependencies.
 * <p>
 * <strong>** FOR INTERNAL USE ONLY **</strong>
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.5
 */
public class ResourceDependencyIndexer {

    public static final String INDEXER_ID =
            "com.tibco.xpd.resource.resourceDependencyIndex"; //$NON-NLS-1$

    public static final String COL_HOST = "host"; //$NON-NLS-1$

    public static final String COL_DEPENDSON = "dependsOn"; //$NON-NLS-1$

    public static final String COL_TYPE = "type"; //$NON-NLS-1$

    public static final String COL_SPECIALFOLDER = "specialFolder"; //$NON-NLS-1$

    /**
     * Type of dependency.
     */
    public enum Type {
        EXPLICIT,
        IMPLICIT,
        PROXY;
    }

    protected static ResourceDbDerby resourceDb;

    protected static String dependencyTableName;

    private final IResource resource;

    private final Type type;

    /**
     * Resource dependency indexer.
     * 
     * @param resource
     *            the host resource - all dependencies being handled by this
     *            instance will be for this resource.
     * @param type
     *            type of dependency.
     */
    public ResourceDependencyIndexer(IResource resource, Type type) {
        Assert.isNotNull(resource,
                "ResourceDependencyIndexer requires a resource"); //$NON-NLS-1$
        Assert.isNotNull(resource,
                "ResourceDependencyIndexer requires a dependency type"); //$NON-NLS-1$

        this.resource = resource;
        this.type = type;
    }

    /**
     * Initialize the resource dependency index.
     */
    public static void init(IndexerServiceImpl indexerService) {

        /*
         * SCF-178: This was originally done in a static block ("lazy-loaded")
         * in this class but this started causing SQL problems with the Derby
         * database after some performance work was done. This has now been
         * changed to an init method that gets called when this plug-in gets
         * loaded (which is probably better as this initialization now happens
         * at the same time as all the other indexers).
         */

        if (indexerService != null) {
            resourceDb = (ResourceDbDerby) indexerService.getResourceDb();

            dependencyTableName = resourceDb.getTableName(INDEXER_ID);
            /*
             * If the table is not present then create it.
             */
            initTable();
            initWorkspaceListener();
        }
    }

    /**
     * Get the host resource.
     * 
     * @return
     */
    public IResource getResource() {
        return resource;
    }

    /**
     * Get the dependency type.
     * 
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     * Update the dependencies of the host resource.
     * 
     * @param dependencyHandles
     *            resources that depend on the host resource.
     */
    public void updateDependencies(Collection<IResource> dependencyHandles) {
        if (dependencyHandles != null) {
            if (resource != null && resource.exists()) {
                String resourcePath = getPath(resource);

                try {
                    clearIndexer();

                    String[][] parameters = null;

                    if (dependencyHandles == null
                            || dependencyHandles.isEmpty()) {
                        // No dependencies so add a dummy entry so we know this
                        // working copy has been processed
                        parameters = new String[1][];
                        parameters[0] =
                                new String[] { resourcePath,
                                        "", type.toString(), "" }; //$NON-NLS-1$ //$NON-NLS-2$
                    } else {
                        parameters = new String[dependencyHandles.size()][4];
                        int x = 0;
                        for (IResource dep : dependencyHandles) {
                            parameters[x][0] = resourcePath;
                            parameters[x][1] = dep != null ? getPath(dep) : ""; //$NON-NLS-1$

                            if (dep instanceof DependencyProxyResource) {
                                String kind =
                                        ((DependencyProxyResource) dep)
                                                .getSpecialFolderKind();
                                parameters[x][2] = Type.PROXY.toString();
                                parameters[x][3] = kind != null ? kind : ""; //$NON-NLS-1$
                            } else {
                                parameters[x][2] = type.toString();
                                parameters[x][3] = ""; //$NON-NLS-1$
                            }

                            ++x;
                        }
                    }
                    // synchronized (resourceDb) {
                    resourceDb.insert(INDEXER_ID, parameters);
                    // }
                } catch (ResourceDbException e) {
                    e.printStackTrace();
                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    String.format("Failed to update the dependencies for '%s'", //$NON-NLS-1$
                                            resource.getFullPath().toString()));
                }
            }
        }
    }

    /**
     * Clear the indexer of all dependencies of the host resource (of the type
     * specified in the constructor).
     */
    public void clearIndexer() {
        clearIndexer(resource, type);
    }

    /**
     * Clear the given index item.
     * 
     * @param host
     *            host resource
     * @param dependsOn
     *            dependency resource
     * @param type
     *            type of dependency
     */
    protected static void clearIndexer(IFile host, IFile dependsOn, Type type) {
        if (host != null && dependsOn != null && type != null) {
            String params[];
            String sql;

            if (type == Type.EXPLICIT) {
                /*
                 * If clearing EXPLICIT items then also remove PROXY items
                 */
                sql =
                        String.format("DELETE FROM %s WHERE %s = ? AND %s = ? AND %s IN(?,?)", //$NON-NLS-1$
                                dependencyTableName,
                                COL_HOST,
                                COL_DEPENDSON,
                                COL_TYPE);

                params =
                        new String[] { getPath(host), getPath(dependsOn),
                                type.toString(), Type.PROXY.toString() };
            } else {
                sql =
                        String.format("DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ?", //$NON-NLS-1$
                                dependencyTableName,
                                COL_HOST,
                                COL_DEPENDSON,
                                COL_TYPE);
                params =
                        new String[] { getPath(host), getPath(dependsOn),
                                type.toString() };
            }

            try {
                // synchronized (resourceDb) {
                resourceDb.update(sql, params);
                // }
            } catch (ResourceDbException e) {
                e.printStackTrace();
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("Failed to clear the dependency for '%1$s' to '%2$s' (type: %3$s)", //$NON-NLS-1$
                                        host.getFullPath().toString(),
                                        dependsOn.getFullPath().toString(),
                                        type.toString()));
            }
        }
    }

    /**
     * Clear the indexer for the given resource.
     * 
     * @param resource
     *            Clear all dependencies where the host is this resource (or
     *            child of this resource).
     * @param type
     *            type of dependency to clear, <code>null</code> to clear all
     *            types.
     */
    protected static void clearIndexer(IResource resource, Type type) {
        if (resource != null) {
            String sql = null;
            String[] param = null;
            if (resource instanceof IContainer) {
                String resourcePath = getPath(resource) + IPath.SEPARATOR + "%"; //$NON-NLS-1$
                if (type != null) {
                    if (type == Type.EXPLICIT) {
                        /*
                         * If EXPLICIT type then also include PROXY
                         */
                        sql =
                                String.format("DELETE FROM %s WHERE %s LIKE ? AND %s IN (?,?)", //$NON-NLS-1$
                                        dependencyTableName,
                                        COL_HOST,
                                        COL_TYPE);
                        param =
                                new String[] { resourcePath, type.toString(),
                                        Type.PROXY.toString() };
                    } else {
                        sql =
                                String.format("DELETE FROM %s WHERE %s LIKE ? AND %s = ?", //$NON-NLS-1$
                                        dependencyTableName,
                                        COL_HOST,
                                        COL_TYPE);
                        param = new String[] { resourcePath, type.toString() };
                    }
                } else {
                    // No type specified so remove all dependency types
                    sql = String.format("DELETE FROM %s WHERE %s LIKE ?", //$NON-NLS-1$
                            dependencyTableName,
                            COL_HOST);
                    param = new String[] { resourcePath };
                }
            } else if (resource instanceof IFile) {
                String resourcePath = getPath(resource);
                if (type != null) {
                    if (type == Type.EXPLICIT) {
                        /*
                         * If EXPLICIT type then also include PROXY
                         */
                        sql =
                                String.format("DELETE FROM %s WHERE %s = ? AND %s IN (?,?)", //$NON-NLS-1$
                                        dependencyTableName,
                                        COL_HOST,
                                        COL_TYPE);
                        param =
                                new String[] { resourcePath, type.toString(),
                                        Type.PROXY.toString() };
                    } else {
                        sql =
                                String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", //$NON-NLS-1$
                                        dependencyTableName,
                                        COL_HOST,
                                        COL_TYPE);
                        param = new String[] { resourcePath, type.toString() };
                    }
                } else {
                    // No type set so remove all types
                    sql = String.format("DELETE FROM %s WHERE %s = ?", //$NON-NLS-1$
                            dependencyTableName,
                            COL_HOST);
                    param = new String[] { resourcePath };
                }
            }

            if (sql != null && param != null) {
                try {
                    // synchronized (resourceDb) {
                    resourceDb.update(sql, param);
                    // }
                } catch (ResourceDbException e) {
                    e.printStackTrace();
                    if (type != null) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to clear the dependencies (type: %1$s) for '%2$s'", //$NON-NLS-1$
                                                type.toString(),
                                                resource.getFullPath()
                                                        .toString()));
                    } else {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to clear the dependencies for '%1$s'", //$NON-NLS-1$
                                                resource.getFullPath()
                                                        .toString()));
                    }
                }
            }
        }
    }

    /**
     * Get all the dependencies of the host resource (of type specified in the
     * constructor).
     * 
     * @return all dependency resource handles, empty collection if none found
     *         and <code>null</code> if this resource has not had its
     *         dependencies stored yet.
     */
    public Collection<IResource> getDependencyHandles() {
        return getDependencyHandles(false);
    }

    /**
     * Get all the dependencies of the host resource (of type specified in the
     * constructor).
     * 
     * @param includeUnresolvedProxies
     *            <code>true</code> to also include any unresolved proxy
     *            resources.
     * 
     * @return all dependency resource handles, empty collection if none found
     *         and <code>null</code> if this resource has not had its
     *         dependencies stored yet.
     */
    public Collection<IResource> getDependencyHandles(
            boolean includeUnresolvedProxies) {
        if (resource != null) {
            try {
                /*
                 * If EXPLICIT dependencies are required then include PROXY
                 * resources too (try to resolve proxy resources - if they
                 * cannot be resolved then ignore them).
                 */
                if (type == Type.EXPLICIT) {
                    return getExplicitAndProxyResources(includeUnresolvedProxies);
                }

                String sql =
                        String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", //$NON-NLS-1$
                                COL_DEPENDSON,
                                dependencyTableName,
                                COL_HOST,
                                COL_TYPE);

                List<IResource> dependency = new ArrayList<IResource>();

                String[][] result;
                // synchronized (resourceDb) {
                result =
                        resourceDb.execute(1,
                                sql,
                                getPath(resource),
                                type.toString());
                // }

                if (result != null && result.length > 0) {
                    IWorkspaceRoot root =
                            ResourcesPlugin.getWorkspace().getRoot();
                    for (String[] res : result) {
                        String path = res[0];
                        /*
                         * If this resource has no dependencies then we will
                         * receive one entry with the dependency set to an empty
                         * string
                         */
                        if (path != null && path.length() > 0) {
                            dependency.add(root.getFile(new Path(path)));
                        }
                    }
                }

                return dependency;

            } catch (ResourceDbException e) {
                // Ignore connection failures
                if (!(e.getCause() instanceof SQLNonTransientConnectionException)) {
                    e.printStackTrace();
                    if (type != null) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to get the dependencies (type: %1$s) for '%2$s'", //$NON-NLS-1$
                                                type.toString(),
                                                resource.getFullPath()
                                                        .toString()));
                    } else {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to get the dependencies for '%1$s'", //$NON-NLS-1$
                                                resource.getFullPath()
                                                        .toString()));
                    }
                }
            }
        }
        // This working copy has not had its dependencies calculated yet
        return null;
    }

    /**
     * Get all dependencies marked as proxies.
     * 
     * @return Map containing all resources marked as proxies with the resources
     *         that have dependencies on these.
     * @throws ResourceDbException
     */
    public static Map<IPath, Collection<IFile>> getProxyResources()
            throws ResourceDbException {
        Map<IPath, Collection<IFile>> proxies =
                new HashMap<IPath, Collection<IFile>>();

        String sql = String.format("SELECT %s, %s FROM %s WHERE %s = ?", //$NON-NLS-1$
                COL_HOST,
                COL_DEPENDSON,
                dependencyTableName,
                COL_TYPE);

        String[][] result;
        // synchronized (resourceDb) {
        result = resourceDb.execute(2, sql, Type.PROXY.toString());
        // }

        if (result != null && result.length > 0) {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            for (String[] row : result) {
                String host = row[0];
                String dependency = row[1];

                if (host != null && !host.isEmpty() && dependency != null
                        && !dependency.isEmpty()) {
                    IPath dependencyPath = new Path(dependency);
                    Collection<IFile> hosts = proxies.get(dependencyPath);
                    if (hosts == null) {
                        hosts = new HashSet<IFile>();
                        proxies.put(dependencyPath, hosts);
                    }
                    hosts.add(root.getFile(new Path(host)));
                }
            }
        }

        return proxies;
    }

    /**
     * Get the EXPLICIT and PROXY dependencies. For PROXY dependencies this
     * method will try to resolve them and include the resolved resource in the
     * list, or if it can't be resolved include the proxy.
     * 
     * @param includeUnresolvedProxyResources
     *            <code>true</code> to include unresolved proxies.
     * 
     * @return list of dependencies, empty list if none found.
     * @throws ResourceDbException
     */
    private Collection<IResource> getExplicitAndProxyResources(
            boolean includeUnresolvedProxyResources) throws ResourceDbException {

        String sql =
                String.format("SELECT %s, %s, %s FROM %s WHERE %s = ? AND %s IN (?,?)", //$NON-NLS-1$
                        COL_DEPENDSON,
                        COL_TYPE,
                        COL_SPECIALFOLDER,
                        dependencyTableName,
                        COL_HOST,
                        COL_TYPE);

        String[][] result;
        // synchronized (resourceDb) {
        result =
                resourceDb.execute(3,
                        sql,
                        getPath(resource),
                        Type.EXPLICIT.toString(),
                        Type.PROXY.toString());
        // }

        if (result != null && result.length > 0) {
            List<IResource> dependency = new ArrayList<IResource>();

            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

            for (String[] res : result) {
                String path = res[0];
                Type type = Type.valueOf(res[1]);
                String sfKind = res[2];

                if (path != null && path.length() > 0) {

                    if (type == Type.EXPLICIT) {
                        dependency.add(root.getFile(new Path(path)));

                    } else if (type == Type.PROXY) {

                        IFile file =
                                resolveProxy(resource.getProject(),
                                        path,
                                        sfKind);

                        if (file != null) {
                            updateProxyDependencyEntry(getPath(resource),
                                    path,
                                    sfKind,
                                    file);
                            dependency.add(file);
                        } else if (includeUnresolvedProxyResources) {
                            dependency.add(new DependencyProxyResource(
                                    new Path(path), sfKind));
                        }
                    }
                }
            }
            return dependency;
        }
        // This working copy has not had its dependencies calculated yet
        return null;
    }

    /**
     * Update the proxy entry in the database with the resolved dependency.
     * 
     * @param hostPath
     * @param dependsOnPath
     * @param sfKind
     * @param file
     * @throws ResourceDbException
     */
    private void updateProxyDependencyEntry(String hostPath,
            String dependsOnPath, String sfKind, IFile file)
            throws ResourceDbException {
        String sql =
                String.format("UPDATE %1$s SET %2$s=?,%3$s=?,%4$s=?,%5$s=? WHERE %2$s=? AND %3$s=? AND %4$s=? AND %5$s=?", //$NON-NLS-1$
                        dependencyTableName,
                        COL_HOST,
                        COL_DEPENDSON,
                        COL_TYPE,
                        COL_SPECIALFOLDER);

        resourceDb.update(sql,
                hostPath,
                getPath(file),
                Type.EXPLICIT.toString(),
                "", //$NON-NLS-1$
                hostPath,
                dependsOnPath,
                Type.PROXY.toString(),
                sfKind);

    }

    /**
     * Resolve the given path in the given project (or any of the referenced
     * projects).
     * 
     * @param project
     * @param path
     * @param sfKind
     * @return {@link IFile} if the path can be resolved to a physical file in
     *         the workspace, <code>null</code> otherwise.
     */
    private IFile resolveProxy(IProject project, String path, String sfKind) {
        IFile file = null;
        if (project != null && path != null) {
            if (sfKind != null && !sfKind.isEmpty()) {
                file =
                        SpecialFolderUtil
                                .resolveSpecialFolderRelativePath(project,
                                        sfKind,
                                        path,
                                        true);
            } else {
                /*
                 * As no special folder kind has been specified then check
                 * project relative
                 */
                file = resolveProjectRelativePath(project, path);
            }
        }
        return file;
    }

    /**
     * Resolve the given path against the project or any referenced projects.
     * 
     * @param project
     * @param path
     * @return IFile if the path can be resolved, <code>null</code> otherwise.
     */
    private IFile resolveProjectRelativePath(IProject project, String path) {
        // Search the project given
        IResource member = project.findMember(path);
        if (member instanceof IFile) {
            return (IFile) member;
        }

        // Search all referenced projects
        try {
            for (IProject refProject : ProjectUtil2
                    .getReferencedProjectsHierarchy(project, false)) {

                member = refProject.findMember(path);
                if (member instanceof IFile) {
                    return (IFile) member;
                }

            }
        } catch (CyclicDependencyException e) {
            // Do nothing
        }

        return null;
    }

    /**
     * Get the resources that depend on the given resource.
     * 
     * @param dependsOn
     * @param type
     *            type of dependency, <code>null</code> for all types.
     * @return collection of resources, empty collection if none found.
     */
    public static Collection<IResource> getInverseDependencies(
            IResource dependsOn, Type type) {
        List<IResource> resources = new ArrayList<IResource>();

        if (dependsOn != null) {
            String sql;
            String param[];
            if (type != null) {
                sql =
                        String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", //$NON-NLS-1$
                                COL_HOST,
                                resourceDb.getTableName(INDEXER_ID),
                                COL_DEPENDSON,
                                COL_TYPE);
                param = new String[] { getPath(dependsOn), type.toString() };
            } else {
                sql = String.format("SELECT %s FROM %s WHERE %s = ?", //$NON-NLS-1$
                        COL_HOST,
                        resourceDb.getTableName(INDEXER_ID),
                        COL_DEPENDSON);
                param = new String[] { getPath(dependsOn) };
            }

            try {
                String[][] result;

                // synchronized (resourceDb) {
                result = resourceDb.execute(1, sql, param);
                // }

                if (result != null && result.length > 0) {
                    IWorkspaceRoot root =
                            ResourcesPlugin.getWorkspace().getRoot();
                    for (String[] res : result) {
                        String path = res[0];
                        if (path != null) {
                            IResource member = root.findMember(path);
                            if (member instanceof IFile) {
                                resources.add(member);
                            }
                        }
                    }
                }
            } catch (ResourceDbException e) {
                /*
                 * Ignore connection exceptions as this will happen when Studio
                 * is being shut-down while build is running
                 */
                if (!(e.getCause() instanceof SQLNonTransientConnectionException)) {
                    e.printStackTrace();
                    if (type != null) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to get the inverse dependencies (type: %1$s) for '%2$s'", //$NON-NLS-1$
                                                type.toString(),
                                                dependsOn.getFullPath()
                                                        .toString()));
                    } else {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to get the inverse dependencies for '%1$s'", //$NON-NLS-1$
                                                dependsOn.getFullPath()
                                                        .toString()));
                    }
                }
            }
        }

        return resources;
    }

    /**
     * Get the path to store in the indexer for the given resource.
     * 
     * @param resource
     * @return
     */
    protected static String getPath(IResource resource) {
        return resource.getFullPath().toPortableString();
    }

    /**
     * Initialise the dependency table. This will create the table if it doesn't
     * already exist.
     */
    private static void initTable() {

        // SCF-173: Index the HOST column for performance improvement
        Column cols[] = new Column[] { new Column(COL_HOST, "4096", true),//$NON-NLS-1$
                new Column(COL_DEPENDSON, "4096"), //$NON-NLS-1$
                new Column(COL_TYPE, "128"), //$NON-NLS-1$
                new Column(COL_SPECIALFOLDER, "512") }; //$NON-NLS-1$

        try {
            resourceDb.initializeTable(INDEXER_ID,
                    getBundleVersion(),
                    cols,
                    null);
        } catch (ResourceDbException e) {
            e.printStackTrace();
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(e, "Initialisation of the dependency indexer failed"); //$NON-NLS-1$
        }

    }

    /**
     * Get the bundle version of this plug-in.
     * 
     * @return
     */
    private static String getBundleVersion() {
        Bundle bundle = XpdResourcesPlugin.getDefault().getBundle();
        if (bundle != null) {
            Dictionary<?, ?> dict = bundle.getHeaders();
            if (dict != null) {
                Object bundleVersion = dict.get("Bundle-version"); //$NON-NLS-1$
                if (bundleVersion instanceof String) {
                    return (String) bundleVersion;
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Initialise the workspace listener. This will listen for resources being
     * deleted and remove the entries in the indexer related to these resources.
     */
    private static void initWorkspaceListener() {
        ResourcesPlugin.getWorkspace()
                .addResourceChangeListener(new IResourceChangeListener() {

                    @Override
                    public void resourceChanged(IResourceChangeEvent event) {
                        try {
                            event.getDelta()
                                    .accept(new WorkspaceResourceDeltaVisitor());
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                },
                        IResourceChangeEvent.POST_CHANGE);
    }

    /**
     * Listen to deletion on the workspace to clear the indexer. This will be
     * handled by the dispose of the individual working copies but if the
     * working copy for the resource is not loaded then it could get left in the
     * indexer.
     * 
     */
    private static class WorkspaceResourceDeltaVisitor implements
            IResourceDeltaVisitor {

        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            if (delta.getKind() == IResourceDelta.REMOVED) {
                IResource resource = delta.getResource();
                if (resource != null) {
                    clearIndexer(resource, null);
                    return false;
                }
            }
            return true;
        }
    }

}
