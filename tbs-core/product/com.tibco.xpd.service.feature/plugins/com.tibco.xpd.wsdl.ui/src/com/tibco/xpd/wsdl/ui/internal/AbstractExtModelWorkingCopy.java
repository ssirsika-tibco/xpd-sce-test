/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.wsdl.ui.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * Extension to the {@link AbstractTransactionalWorkingCopy} to manage models
 * that are not owned by Studio (i.e. WSDL and XSD). This will handle the
 * reloading of these models when the file changes.
 * 
 * @author njpatel
 * @since 3.5.10
 */
public abstract class AbstractExtModelWorkingCopy extends
        AbstractTransactionalWorkingCopy {

    private static WorkspaceListener workspaceListener;

    static {
        /*
         * Single workspace listener that will handle the updating of all of
         * these working copies.
         */
        workspaceListener = new WorkspaceListener();

        ResourcesPlugin.getWorkspace()
                .addResourceChangeListener(workspaceListener,
                        IResourceChangeEvent.POST_CHANGE);
    }

    private final EPackage ePackage;

    /**
     * @param resources
     */
    public AbstractExtModelWorkingCopy(List<IResource> resources,
            EPackage ePackage) {
        super(resources);
        this.ePackage = ePackage;
    }

    /**
     * Reset the schema managed by this working copy. This will be called when a
     * referenced schema is reloaded.
     */
    protected abstract void resetSchema();

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#createAddResourceListener()
     * 
     * @return
     */
    @Override
    protected IResourceChangeListener createAddResourceListener() {
        // Do nothing here - the singleton workspace listener is used to monitor
        // changes.
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#createResourceChangeListener()
     * 
     * @return
     */
    @Override
    protected IResourceChangeListener createResourceChangeListener() {
        // Monitor changes locally using the singleton workspace listener
        workspaceListener.addWorkingCopy(this);
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     * 
     * @return
     */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return ePackage;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#dispose()
     * 
     */
    @Override
    public void dispose() {
        workspaceListener.removeWorkingCopy(this);
        super.dispose();
    }

    /**
     * Reload and re-cache the dependencies of this working copy.
     */
    private void reloadAndUpdateDependencies() {
        if (isLoaded()) {
            reLoad();
        } else {
            if (isInvalidFile()) {
                deleteInvalidFileMarker();
            }
            /*
             * If the user decides to change this model before any process loads
             * this working copy then there is a chance that the EMF resource
             * may already have been loaded in the resource set (through some
             * resolver or the process mapper). In this case the resource should
             * be unloaded so that when the working copy is loaded it will pick
             * up the latest model change (which it will be by the call to
             * doSaveDependencyCache below).
             */
            IResource resource = getFirstResource();
            if (resource != null && resource.exists()) {
                URI uri =
                        URI.createPlatformResourceURI(resource.getFullPath()
                                .toString(), true);
                Resource emfRes =
                        getEditingDomain().getResourceSet().getResource(uri,
                                false);
                if (emfRes != null && emfRes.isLoaded()) {
                    try {
                        unloadResource(emfRes);
                    } catch (InterruptedException e) {
                        WsdlUIPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Failed to unload the resource: %s", //$NON-NLS-1$
                                                resource.getFullPath()
                                                        .toString()));
                    }
                }
            }
        }
        try {
            doSaveDependencyCache();
        } catch (CoreException e) {
            WsdlUIPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            String.format("Failed to save dependencies of %s", //$NON-NLS-1$
                                    getFirstResource().getFullPath().toString()));
        }
    }

    /**
     * Singleton workspace listener for all WSDL and XSD working copies.
     * 
     */
    private static class WorkspaceListener implements IResourceChangeListener {

        private final Set<AbstractExtModelWorkingCopy> registeredWCs;

        /**
         * 
         */
        public WorkspaceListener() {
            registeredWCs =
                    Collections
                            .synchronizedSet(new HashSet<AbstractExtModelWorkingCopy>());
        }

        /**
         * Register the working copy with this listener.
         * 
         * @param wc
         */
        public void addWorkingCopy(AbstractExtModelWorkingCopy wc) {
            if (wc.getFirstResource() != null) {
                registeredWCs.add(wc);
            }
        }

        /**
         * Unregister the working copy form this listener.
         * 
         * @param wc
         */
        public void removeWorkingCopy(AbstractExtModelWorkingCopy wc) {
            registeredWCs.remove(wc);
        }

        /**
         * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
         * 
         * @param event
         */
        @Override
        public void resourceChanged(IResourceChangeEvent event) {
            // No point listening if no working copies are registered
            if (registeredWCs.isEmpty()) {
                return;
            }

            /*
             * Sid XPD-3965: This wasn't really the cause of this defect BUT I
             * noticed while investigating that because we were using using
             * Set<IFile> trhen we were not preserving the order in which
             * delta's happen.
             * 
             * Discussed this with Nilay and decided that although the order in
             * which workingCopy.reloadAndUpdateDependencies() was called
             * probably shouldn't be affected by order of reload it is probably
             * worth processing the reload in order that the files were changed.
             * 
             * So changing to a LinkedHashSet to preserve order.
             */
            final LinkedHashSet<IFile> removedFiles =
                    new LinkedHashSet<IFile>();
            final LinkedHashSet<IFile> changedFiles =
                    new LinkedHashSet<IFile>();

            try {
                event.getDelta().accept(new IResourceDeltaVisitor() {

                    @Override
                    public boolean visit(IResourceDelta delta)
                            throws CoreException {
                        IResource resource = delta.getResource();

                        if (resource instanceof IFile) {
                            if (delta.getKind() == IResourceDelta.REMOVED
                                    || delta.getKind() == IResourceDelta.REMOVED_PHANTOM) {
                                // File removed
                                removedFiles.add((IFile) resource);
                            } else if ((delta.getKind() == IResourceDelta.CHANGED && (delta
                                    .getFlags() & IResourceDelta.CONTENT) != 0)) {
                                // Content of file changed
                                changedFiles.add((IFile) resource);
                            }

                            return false;
                        }

                        return true;
                    }
                });
            } catch (CoreException e) {
                WsdlUIPlugin.getDefault().getLogger().error(e);
            }

            /*
             * Process all removed files - dispose the working copy (this will
             * also unregister the working copy from this listener).
             */
            for (IFile file : removedFiles) {
                AbstractExtModelWorkingCopy wc =
                        findRegisteredWorkingCopyFor(file);
                if (wc != null) {
                    wc.removeWorkingCopy(null);
                }
            }

            Set<IFile> affectedFiles = new HashSet<IFile>();

            /*
             * Process all changed files - reload working copies of these files.
             */
            for (IFile file : changedFiles) {
                AbstractExtModelWorkingCopy wc =
                        findRegisteredWorkingCopyFor(file);
                if (wc != null) {
                    affectedFiles.add(file);
                    wc.reloadAndUpdateDependencies();
                }
            }

            if (!affectedFiles.isEmpty()) {
                for (AbstractExtModelWorkingCopy wc : getAffectedWorkingCopies(affectedFiles)) {
                    wc.resetSchema();
                }
            }

        }

        /**
         * Get all working copies that will be affected by the change to the
         * given files. This will find all models that reference any file in the
         * set.
         * 
         * @param files
         * @return
         */
        private Set<AbstractExtModelWorkingCopy> getAffectedWorkingCopies(
                Set<IFile> files) {
            Set<AbstractExtModelWorkingCopy> wcs =
                    new HashSet<AbstractExtModelWorkingCopy>();

            for (IFile file : files) {
                for (IResource res : WorkingCopyUtil.getAffectedResources(file)) {
                    if (WsdlUIPlugin.WSDL_FILE_EXTENSION.equals(res
                            .getFileExtension())
                            || WsdlUIPlugin.XSD_FILE_EXTENSION.equals(res
                                    .getFileExtension())) {
                        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(res);
                        if (wc instanceof AbstractExtModelWorkingCopy) {
                            wcs.add((AbstractExtModelWorkingCopy) wc);
                        }
                    }
                }
            }

            return wcs;
        }

        /**
         * Find the registered working copy that is managing the given file.
         * 
         * @param file
         * @return working copy if found, <code>null</code> otherwise.
         */
        private AbstractExtModelWorkingCopy findRegisteredWorkingCopyFor(
                IFile file) {
            synchronized (registeredWCs) {
                for (AbstractExtModelWorkingCopy wc : registeredWCs) {
                    if (file.equals(wc.getFirstResource())) {
                        return wc;
                    }
                }
            }
            return null;
        }

    }

}
