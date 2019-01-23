/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.rules;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.engine.ImplicitDependencyIndexer;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * Utility class for accessing additional information stored against a problem
 * marker.
 * 
 * @author nwilson
 */
public final class ValidationUtil {

    /**
     * Private constructor.
     */
    private ValidationUtil() {
    }

    /**
     * @param marker
     *            The marker.
     * @return The additional info properties.
     */
    public static Properties getAdditionalInfo(IMarker marker) {
        Properties properties = null;
        try {
            String info =
                    (String) marker
                            .getAttribute(XpdConsts.VALIDATION_MARKER_ADDITIONAL_INFO);
            if (info != null) {
                properties = new Properties();
                properties.load(new ByteArrayInputStream(info.getBytes()));
            }
        } catch (CoreException e) {
            /*
             * We shouldn't log this as the marker could have been removed by
             * another thread before the call to getAttribute. Carry on and
             * return null.
             * ValidationActivator.getDefault().getLogger().error(e);
             */
        } catch (IOException e) {
            ValidationActivator.getDefault().getLogger().error(e);
        }
        return properties;
    }

    /**
     * Calculate complete set of working copies affected by given working copy.
     * It will check the whole tree of dependencies.
     * 
     * @param wc
     * @return
     */
    public static Set<WorkingCopy> calculateAffectedWorkingCopies(WorkingCopy wc) {
        Set<IFile> resources = new HashSet<IFile>();
        resources = computeDependencyTree(resources);

        Set<WorkingCopy> result = new HashSet<WorkingCopy>();
        for (IResource res : resources) {
            WorkingCopy affectedWC =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(res);
            if (affectedWC != null) {
                result.add(affectedWC);
            }
        }
        // add source wc if not existing in the set
        if (!result.contains(wc)) {
            result.add(wc);
        }
        return result;
    }

    /**
     * @param resources
     * @return
     * @throws CoreException
     */
    private static Set<IFile> computeDependencyTree(Set<IFile> newForValidation) {
        Set<IFile> forValidation = new HashSet<IFile>();
        while (!newForValidation.isEmpty()) {
            for (IFile res : newForValidation.toArray(new IFile[0])) {
                if (!forValidation.contains(res)) {
                    forValidation.add(res);

                    Set<IResource> affected = new HashSet<IResource>();
                    affected.addAll(WorkingCopyUtil.getAffectedResources(res));
                    affected.addAll(ValidationUtil.getDependentResources(res));

                    for (IResource nres : affected) {
                        if (nres instanceof IFile
                                && !forValidation.contains(nres)
                                && !newForValidation.contains(nres)) {
                            newForValidation.add((IFile) nres);
                        }
                    }
                }
                newForValidation.remove(res);
            }
        }
        return forValidation;
    }

    /**
     * Get resources that are implicitly dependent on the given resource. This
     * will check the additional info in the validation marker for the
     * dependencies.
     * 
     * @param res
     *            get dependencies on this resource
     * @param filter
     *            filter the returned files list
     * @return set of files that have an implicit dependency on the given
     *         resource, empty set if none.
     * @throws CoreException
     */
    public static Set<IFile> getImplicitDependentResources(final IResource res,
            final IFileFilter filter) {
        Set<IFile> result = new HashSet<IFile>();

        for (IResource dep : ImplicitDependencyIndexer
                .getInverseDependencies(res)) {
            if (dep instanceof IFile && dep.exists()) {
                result.add((IFile) dep);
            }
        }
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(res);
        if (wc != null) {
            addResourcesFromReferences((Set) wc.getAttributes()
                    .get(ValidationActivator.LINKED_RESOURCE),
                    result);
        }

        return result;
    }

    /**
     * Get the resources that the given resource depends on.
     * 
     * @param resource
     * @return
     */
    private static Set<IFile> getDependentResources(IResource resource) {
        Set<IFile> resources = new HashSet<IFile>();

        if (resource != null && resource.exists()) {
            Collection<IResource> handles =
                    new ImplicitDependencyIndexer(resource)
                            .getDependencyHandles();
            if (handles != null) {
                for (IResource handle : handles) {
                    if (handle instanceof IFile && handle.exists()) {
                        resources.add((IFile) handle);
                    }
                }
            }
        }

        return resources;
    }

    private static void addResourcesFromReferences(Collection<String> paths,
            Set<IFile> resources) {
        if (paths != null) {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            for (String path : paths) {
                URI uri = URI.createURI(path);

                if (uri != null) {
                    path = uri.toPlatformString(true);
                    if (path != null) {
                        IFile file = root.getFile(new Path(path));

                        if (file != null && file.exists()) {
                            resources.add(file);
                        }
                    }
                }
            }
        }
    }

    /**
     * Sid XPD-3914: Returns set of files which look like they may have a PROXY
     * dependency on one or more of the files to possibly generated by
     * {@link #doBuild(int, Map, IProgressMonitor, Set)}
     * <p>
     * Nominally filesToValidate returned by doBuild() will also contain the
     * files that the builder has generated (if any) EXPECIALLY if they are
     * things that are user visible and therefore referencable (such as
     * generated BOMs).
     * <p>
     * Basically checks the dependency index for any file depended-upon in a
     * PROXY(file doesn't exist yet) type entry that looks like it has the same
     * special-folder-relative path as a file returned by doBuild(). The set of
     * files dependent upon these PROXY type entries is compiled and then
     * returned.
     * 
     * @param filesToValidate
     * 
     * @return Set of files that look like they may depend on one of the files
     *         returned by doBuild(), but the dependency was initially
     *         unresolved because the file hadn't been generated yet.
     */
    public static Set<IResource> getPossibleProxyDependents(
            Collection<IResource> filesToValidate) {
        Set<IResource> newFilesToValidate = new HashSet<IResource>();

        Map<IPath, Collection<IFile>> proxyDependencies =
                WorkingCopyUtil.getProxyDependencies();

        for (IResource possiblyNewResource : filesToValidate) {
            if (possiblyNewResource instanceof IFile) {
                /*
                 * Get the special folder relative path for the file that may
                 * have just been created and check if it's listed as an
                 * unresolved proxy in dependency index. I don't this it's
                 * guaranteed that the PROXY in the index _is_ special folder
                 * relative but it's a fair assumption in most cases.
                 */
                IPath possiblyNewResourcePath =
                        SpecialFolderUtil
                                .getSpecialFolderRelativePath(possiblyNewResource,
                                        null);

                if (possiblyNewResourcePath != null) {
                    /*
                     * See if the possiblyNewResource looks like it may be one
                     * of the currently unresolved proxy entries in dependency
                     * index.
                     */
                    Collection<IFile> filesDependentOnProxy =
                            proxyDependencies.get(possiblyNewResourcePath);

                    if (filesDependentOnProxy != null
                            && !filesDependentOnProxy.isEmpty()) {
                        /*
                         * The files dependent on this proxy may be affected by
                         * this file having been created, so we should re-index
                         * and validate it.
                         */
                        newFilesToValidate.addAll(filesDependentOnProxy);
                    }
                }
            }
        }
        return newFilesToValidate;
    }
}
