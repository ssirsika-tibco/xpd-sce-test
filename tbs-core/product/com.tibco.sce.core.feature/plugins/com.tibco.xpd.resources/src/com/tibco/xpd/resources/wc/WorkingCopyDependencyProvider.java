/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;

/**
 * Abstract class that can be implemented by a working copy dependency provider.
 * This implementation traverses through the model in the working copy to find
 * all objects that have references to build the dependency list.
 * 
 * @author njpatel
 * 
 */
public abstract class WorkingCopyDependencyProvider implements
        IWorkingCopyDependencyProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies
     * (com.tibco.xpd.resources.WorkingCopy)
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Set<IResource> resources = new HashSet<IResource>();

        if (wc != null && wc.getRootElement() != null) {
            EObject root = wc.getRootElement();
            if (root != null) {
                Resource resource = root.eResource();

                Set<URI> uris = new HashSet<URI>();

                if (resource != null) {
                    // Get references from the root element before processing
                    // its content
                    getReferences(root, resource, uris);

                    TreeIterator<EObject> iterator = root.eAllContents();
                    while (iterator.hasNext()) {
                        getReferences(iterator.next(), resource, uris);
                    }
                    for (URI uri : uris) {
                        if ("platform".equals(uri.scheme())) { //$NON-NLS-1$
                            IFile file =
                                    getFile(uri, resource.getResourceSet()
                                            .getURIConverter());
                            if (file != null) {
                                resources.add(file);
                            }
                        } else if (uri.isRelative() && uri.scheme() == null) {
                            // Unresolved proxy
                            IResource proxy = getProxyResource(uri);
                            if (proxy != null) {
                                resources.add(proxy);
                            }
                        }
                    }
                }
            }
        }
        return resources;
    }

    /**
     * Get a proxy resource for the given unresolved URI. Default implementation
     * returns <code>null</code>. Subclasses may override to provide a resource.
     * Use {@link DependencyProxyResource} to report unresolved proxies that
     * will be resolved, if possible, when the dependency is requested from the
     * respective working copy.
     * 
     * @param uri
     * @return proxy resource, <code>null</code> if no proxy resource should be
     *         reported.
     * 
     * @since 3.5.5
     */
    protected IResource getProxyResource(URI uri) {
        return null;
    }

    /**
     * Get any external references from the given EObject and add to the uris
     * set.
     * 
     * @param eo
     * @param resource
     * @param uris
     */
    private void getReferences(EObject eo, Resource resource, Set<URI> uris) {
        if (eo instanceof InternalEObject) {
            InternalEObject ieo = (InternalEObject) eo;
            EList<EReference> refs = ieo.eClass().getEAllReferences();
            for (EReference ref : refs) {
                if (ref.isContainer() || ref.isContainment()) {
                    continue;
                }
                if (ref.isMany()) {
                    Object value = ieo.eGet(ref, false);
                    Iterator<?> iter = null;
                    if (value instanceof InternalEList<?>) {
                        iter = ((InternalEList<?>) value).basicIterator();
                    } else if (value instanceof Collection<?>) {
                        iter = ((Collection<?>) value).iterator();
                    }

                    if (iter != null) {
                        while (iter.hasNext()) {
                            addURI(resource, iter.next(), uris);
                        }
                    }
                } else {
                    addURI(resource, ieo.eGet(ref, false), uris);
                }
            }
        }
    }

    /**
     * COPIED FROM org.eclipse.emf.workspace.util.WorkspaceSynchronizer
     * 
     * Finds the file corresponding to the specified URI, using a URI converter
     * if necessary (and provided) to normalize it.
     * 
     * @param uri
     *            a URI
     * @param converter
     *            an optional URI converter (may be <code>null</code>)
     * 
     * @return the file, if available in the workspace
     */
    private static IFile getFile(URI uri, URIConverter converter) {
        IFile result = null;

        if ("platform".equals(uri.scheme()) && (uri.segmentCount() > 2)) { //$NON-NLS-1$
            if ("resource".equals(uri.segment(0))) { //$NON-NLS-1$
                IPath path =
                        new Path(URI.decode(uri.path())).removeFirstSegments(1);

                result = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            }
        } else if (uri.isFile() && !uri.isRelative()) {
            result =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getFileForLocation(new Path(uri.toFileString()));
        } else {
            // normalize, to see whether may we can resolve it this time
            if (converter != null) {
                URI normalized = converter.normalize(uri);

                if (!uri.equals(normalized)) {
                    // recurse on the new URI
                    result = getFile(normalized, converter);
                }
            }
        }

        return result;
    }

    /**
     * Add URI of the object to the list of uris.
     * 
     * @param baseResource
     * @param obj
     * @param uris
     */
    private void addURI(Resource baseResource, Object obj, Set<URI> uris) {
        if (obj instanceof InternalEObject) {
            InternalEObject raweo = (InternalEObject) obj;
            if (raweo.eIsProxy()) {
                URI uri = raweo.eProxyURI();
                if (uri != null) {
                    uris.add(uri.trimFragment());
                }
            } else if (raweo.eResource() != null
                    && raweo.eResource() != baseResource) {
                uris.add(raweo.eResource().getURI());
            }
        }
    }
}
