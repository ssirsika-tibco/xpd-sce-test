/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.gmf.runtime.common.core.util.Trace;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.internal.XpdResourcesDebugOptions;
import com.tibco.xpd.resources.internal.indexer.ResourceDependencyIndexer;
import com.tibco.xpd.resources.internal.indexer.ResourceDependencyIndexer.Type;
import com.tibco.xpd.resources.internal.wc.WorkingCopyProviderAdapter;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Abstract implementation of the working copy.
 * <p>
 * Fragment of Edtior's code: <br>
 * 
 * <code>
 * <pre>
 *        XPDProjectResourceFactory factory;
 *        IResource res;
 *        
 *        init(...) {
 *           super.init(...);
 *           factory = XPDResourcePlugin().
 *           getXPDProjectResourceFactory(res.getProject());
 *           WorkingCopy wc = factory.getWorkingCopy(res);
 *           wc.addListener(self);
 *        }  
 *          
 *        someEditingActivity() {
 *           WorkingCopy wc = factory.getWorkingCopy(res);
 *           Package pck = (Package)wc.getRootObject();
 *           pck.setId(&quot;1&quot;); // or better use commands!!
 *        }
 *         
 *        doSave() {
 *           WorkingCopy wc = factory.getWorkingCopy(res);
 *           wc.save();
 *        }
 *          
 *        dispose() {
 *           wc.removeListener(self);
 *        }
 * </pre>        
 * </code>
 * </p>
 * <p>
 * Note: (Change introduced in 3.0) The {@link WorkingCopy#CHANGED} event will
 * not have its new value set to the dirty state of the working copy - it will
 * either be <code>null</code> or the trigger {@link EventObject event}.
 * </p>
 */
public abstract class AbstractWorkingCopy
        implements WorkingCopy, CommandStackListener {

    private static ILock lock = Job.getJobManager().newLock();

    /** cache trace option */
    private static final boolean trace =
            Trace.shouldTrace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.WORKING_COPY);

    private final List<Notification> notifications =
            new ArrayList<Notification>();

    public static final String MARKER_VERSIONISSUE = "version-issue"; //$NON-NLS-1$

    public static final String MARKER_NAMEISSUE = "name-issue"; //$NON-NLS-1$

    /**
     * Eclipse resource that contains the model, can be empty if the resource is
     * outside eclipse.
     */
    private final List<IResource> resources;

    /**
     * Editing domain for the model.
     */
    private EditingDomain editingDomain;

    /**
     * ItempProvidedAdapter factory.
     */
    private AdapterFactory adapterFactory;

    /**
     * Root EObject of the main model .
     */
    // XPD-1128: Make root element directly available to sub-class (to allow
    // setting).
    protected EObject rootElement;

    /**
     * Saveable for this working copy.
     */
    private WorkingCopySaveable saveable = null;

    private final WCContentAdapter contentAdapter = new WCContentAdapter();

    /**
     * WorkingCopy listeners.
     */
    private final Set<PropertyChangeListener> listeners //
            = new HashSet<PropertyChangeListener>();

    /**
     * user defined attributes.
     */
    private final Map<Object, Object> attributes =
            new HashMap<Object, Object>();

    /**
     * resource modification stamps at the time as they were loaded.
     */
    private final Map<IResource, Long> modificationStamps =
            new HashMap<IResource, Long>();

    /**
     * Flag that indicate that resource changes should be ignored (for example
     * during saving of the files).
     */
    private boolean ignoreResourceChanges;

    /**
     * Invalid flag.
     */
    private boolean isInvalid = false;

    /**
     * map EPackage to resource locator, it is required for
     * {@link #getMetaText(EObject)}.
     */
    private final Map<EPackage, ResourceLocator> locators = //
            new HashMap<EPackage, ResourceLocator>();

    /** Cached attributes map. */
    private Map<String, String> cachedAttributes;

    /** Synchronisation lock for the uriToEObject map. */
    private final Object resolveLock = new Object();

    /** Cache of URIs to EObjects. */
    private Map<String, EObject> uriToEObject;

    /** Synchronisation lock for the eObjectToSeverity map. */
    private final Object severityLock = new Object();

    /** Cache of EObjects to severity. */
    private final Map<EObject, Integer> eObjectToSeverity;

    /** Flag to indicate if the resource dirty flag is currently set. */
    private boolean dirty = false;

    /** Cache of dependencies for use when refreshing resource. */
    private List<IResource> dependenciesToCheckOnNextResourceChangeEvent;

    private final IResourceChangeListener resourceListener;

    /**
     * The resource dependency indexer to store the explicit dependencies.
     * 
     * @since 3.5
     */
    private ResourceDependencyIndexer dependencyIndexer;

    /**
     * Set to <code>true</code> if this working copy has failed to load because
     * it needs migrating.
     * 
     * @since 3.5
     */
    private Boolean isVersionProblem;

    private WorkingCopyProviderAdapter wcAdapter;

    /**
     * Set to <code>true</code> when this working copy is being removed (due to
     * underlying file being deleted).
     */
    private boolean isBeingRemoved = false;

    /*
     * Sid XPD-7543: Allow UI notifications to be disabled in circumstances
     * where we're building models in background jobs and don't want nasty lock
     * ups.
     */
    private boolean notificationsEnabled = true;

    /**
     * Clears the indexer entries for the dependency rows created for the
     * resource this working copy is based on.
     * 
     * @since 3.5
     */
    protected void clearDependencies() {
        if (dependencyIndexer != null) {
            dependencyIndexer.clearIndexer();
        }
    }

    /**
     * The Constructor. Construct working copy that contains Eclipse resources.
     * 
     * @param resources
     *            Eclipse resources that represents the working copy
     */
    public AbstractWorkingCopy(List<IResource> resources) {
        this.resources = resources;

        IResource res = getFirstResource();
        if (res != null) {
            dependencyIndexer = new ResourceDependencyIndexer(resources.get(0),
                    Type.EXPLICIT);
        }

        eObjectToSeverity = new HashMap<EObject, Integer>();
        refreshModificationStamps(resources);
        resourceListener = createResourceChangeListener();

        if (resourceListener != null) {
            ResourcesPlugin.getWorkspace().addResourceChangeListener(
                    resourceListener,
                    IResourceChangeEvent.PRE_BUILD
                            | IResourceChangeEvent.PRE_CLOSE
                            | IResourceChangeEvent.PRE_DELETE
                            | IResourceChangeEvent.POST_CHANGE);
        }
    }

    /**
     * Refresh modification stamps to current values.
     * 
     * @param resources
     *            resources to update
     */
    private void refreshModificationStamps(List<IResource> resources) {
        // store modification stamps of the model's files
        for (IResource res : resources) {
            modificationStamps.put(res, res.getLocalTimeStamp());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#getEclipseResources()
     */
    @Override
    public List<IResource> getEclipseResources() {
        return resources;
    }

    /**
     * Lazy create editing domain.
     * 
     * @return ediitng domain
     */
    @Override
    public EditingDomain getEditingDomain() {
        if (editingDomain == null) {
            editingDomain = createEditingDomain();
            registerEditingDomainListeners(editingDomain);
        }
        return editingDomain;
    }

    /**
     * Register listeners to editing domain. Default implementation adds
     * listener to the editing domain's command stack.
     * 
     * Subclasses my extend or change the bahaviour.
     * 
     * @param ed
     *            newly created editing domain
     */
    protected void registerEditingDomainListeners(EditingDomain ed) {
        ed.getCommandStack().addCommandStackListener(this);
    }

    /**
     * @see WorkingCopy#getAdapterFactory()()
     * 
     * @return {@link AdapterFactory}
     */
    @Override
    public AdapterFactory getAdapterFactory() {
        if (adapterFactory == null) {
            adapterFactory = createAdapterFactory();
        }
        return adapterFactory;
    }

    /**
     * Create EditingDomain.
     * 
     * @return newly created editing domain
     */
    protected abstract EditingDomain createEditingDomain();

    /**
     * Create AdapterFactory.
     * 
     * @return newly created adapter factory
     */
    protected abstract AdapterFactory createAdapterFactory();

    /**
     * @see WorkingCopy#isWorkingCopyDirty()
     * 
     * @return if working copy need to be saved
     */
    @Override
    public boolean isWorkingCopyDirty() {
        if (editingDomain == null) {
            return false;
        }
        return ((BasicCommandStack) editingDomain.getCommandStack())
                .isSaveNeeded();
    }

    @Override
    public void save() throws IOException {
        try {
            ignoreResourceChanges = true;
            IWorkspace ws = ResourcesPlugin.getWorkspace();
            clearUriCache();
            IWorkspaceRunnable saveAction = new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    try {

                        /*
                         * If Project is in pre-compilation mode, check whether
                         * the file that backs this working copy should be
                         * allowed to be changed or not (i.e. effectively read
                         * only).
                         * 
                         * This will Iterate thru pre-compile contributions
                         * looking at the "SourceArtifact" Expression filter
                         * element in each.
                         * 
                         * Each Expression filter will be passed this working
                         * copy's file. If any return true it means that this
                         * working copy is considered to be for a file that can
                         * affect a derived pre-compiled file AND THEREFORE must
                         * not be allowed to be changed.
                         */

                        IProject project = null;
                        final IResource firstResource = getFirstResource();
                        if (null != firstResource) {

                            project = firstResource.getProject();
                        }

                        if (null != project) {

                            if (ProjectUtil.isPrecompiledProject(project)
                                    && com.tibco.xpd.resources.precompile.PreCompileContributorManager
                                            .getInstance()
                                            .isPrecompiledSourceArtefact(
                                                    firstResource)) {

                                /*
                                 * AysncExec Message Dialog Resource is read
                                 * only because it is the source for a
                                 * pre-compiled artifact.
                                 */
                                if (!XpdResourcesPlugin.isInHeadlessMode()) {

                                    Display.getDefault()
                                            .asyncExec(new Runnable() {

                                                @Override
                                                public void run() {

                                                    Shell shell = PlatformUI
                                                            .getWorkbench()
                                                            .getActiveWorkbenchWindow()
                                                            .getShell();
                                                    String msg = String.format(
                                                            Messages.AbstractWorkingCopy_precompile_project_readonly_msg,
                                                            firstResource
                                                                    .getName());
                                                    MessageDialog
                                                            .openInformation(
                                                                    shell,
                                                                    Messages.AbstractWorkingCopy_precompile_project_readonly_title,
                                                                    msg);
                                                }
                                            });
                                }
                                return;

                            }
                        }

                        doSave();
                        doSaveDependencyCache();

                        /*
                         * XPD-1128: Check we have a command stack and editing
                         * domain before using it (may no thave if created frokm
                         * temp stream.
                         */
                        if (editingDomain != null && editingDomain
                                .getCommandStack() instanceof BasicCommandStack) {
                            ((BasicCommandStack) editingDomain
                                    .getCommandStack()).saveIsDone();
                        }
                        dirty = isWorkingCopyDirty();
                        refreshModificationStamps(resources);
                    } catch (IOException e) {
                        throw new CoreException(new Status(IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN, IStatus.OK,
                                e.getMessage(), e));
                    }
                }
            };
            try {
                ws.run(saveAction, null);
                // getSaveActionSchedulingRule(),
                // IWorkspace.AVOID_UPDATE, null);

            } catch (CoreException e) {
                throw new IOException(e.getStatus().getMessage(), e);
            }
        } finally {
            fireWCDirtyFlagChanged();
            ignoreResourceChanges = false;
        }
    }

    /**
     * @throws CoreException
     *             If there was a problem saving the cache.
     */
    protected void doSaveDependencyCache() throws CoreException {
        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (!getEclipseResources().isEmpty() && dependencyIndexer != null) {
            List<IResource> deps = computeDependenciesFromModel();
            dependencyIndexer.updateDependencies(deps);
        }
    }

    /**
     * @return The cache marker.
     * @throws CoreException
     *             If there was a problem finding cache markers.
     */
    private IMarker getDependencyCacheMarker() throws CoreException {
        IMarker marker = null;
        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (!getEclipseResources().isEmpty()) {
            IResource res = getEclipseResources().get(0);
            if (res.exists()) {
                IMarker[] markers =
                        res.findMarkers(XpdConsts.WORKING_COPY_CACHE_MARKER,
                                false,
                                IResource.DEPTH_ZERO);
                if (markers.length > 0) {
                    marker = markers[0];
                }
            }
        }
        return marker;
    }

    /**
     * Compute list of eclipse resources on which this WorkingCopy depends. It
     * can assume that model is already loaded, and this method will be invoked
     * only when needed (when model has changed since last time dependencies has
     * been computed).
     * 
     * @return list of dependencies
     */
    protected List<IResource> computeDependenciesFromModel() {
        List<IResource> resources = new ArrayList<IResource>();
        Class<? extends WorkingCopy> cls = getClass();
        Set<IWorkingCopyDependencyProvider> providers =
                XpdResourcesPlugin.getDefault().getDependencyProviders(cls);

        if (providers != null) {
            for (IWorkingCopyDependencyProvider provider : providers) {
                Collection<IResource> dependencies =
                        provider.getDependencies(this);
                for (IResource resource : dependencies) {
                    /*
                     * SCF-270 behave better if provider returns null resource.
                     */
                    if (resource != null) {
                        if (!resources.contains(resource)) {
                            resources.add(resource);
                        }
                    }
                }
            }
        }

        return resources;
    }

    /**
     * Do the actual save of the file.
     * 
     * @throws IOException
     *             when save failed
     */
    protected abstract void doSave() throws IOException;

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#addListener(java.beans.
     * PropertyChangeListener)
     */
    @Override
    public void addListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#removeListener(java.beans.
     * PropertyChangeListener)
     */
    @Override
    public void removeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * There is no listeners of the working copy, the model can be unloaded from
     * the memory. Depends on the implementation.
     */
    protected void cleanup() {

        /*
         * uninstall adapters
         */
        Collection<Resource> resources = getResources();
        if (resources != null && !resources.isEmpty()) {
            for (Resource resource : resources) {
                uninstallAdapters(resource);
            }
        }

        rootElement = null;
        clearUriCache();

        /*
         * SCF-326 - If we're clearing the isInvalid flag then we should also
         * clear the isVersionProblem flag otherwise it is possible it gets left
         * around after a call to reload() and then strange things can happen.
         */
        isVersionProblem = null;

        isInvalid = false;

        if (editingDomain != null) {
            editingDomain.getCommandStack().flush();
        }
    };

    /**
     * Set property value.
     * 
     * @param key
     *            property key only PROP_READONLY is valid, all other will
     *            return IllegalArgumentException.
     * @param value
     *            value of the property
     */
    @Override
    public void setProperty(String key, Object value) {
        if (PROP_READONLY.equals(key)) {
            setReadOnly((Boolean) value);
        } else {
            throw new IllegalArgumentException(String.format(
                    Messages.AbstractWorkingCopy_invalidProperty_shortdesc,
                    new Object[] { key }));
        }
    }

    /**
     * Returnc first resource or null.
     * 
     * @return main resource or null
     */
    protected IResource getFirstResource() {
        return resources != null && resources.size() > 0 ? resources.get(0)
                : null;
    }

    /**
     * Returns value of the property.
     * 
     * @param key
     *            property name
     * @return property value
     */
    @Override
    public Object getProperty(String key) {
        if (PROP_DIRTY.equals(key)) {
            return isWorkingCopyDirty();
        }
        if (PROP_READONLY.equals(key)) {
            // TODO may load the model, validate if the usage is valid
            return getEditingDomain().isReadOnly(getRootElement().eResource());
        }
        throw new IllegalArgumentException(MessageFormat.format(
                Messages.AbstractWorkingCopy_invalidProperty_shortdesc,
                new Object[] { key }));
    }

    /**
     * @return true when underlaying resource exists
     */
    @Override
    public boolean isExist() {
        return (resources.size() == 0) || resources.get(0).exists();
    }

    /**
     * notification from command stack, propagate to listeners.
     * 
     * @param event
     *            command stack event
     */
    @Override
    public void commandStackChanged(EventObject event) {
        // throw away cache of cached attributes
        cachedAttributes = null;
        if (dirty != isWorkingCopyDirty()) {
            // fire notifications
            fireWCModelChanged(event);
            fireWCDirtyFlagChanged();
            dirty = isWorkingCopyDirty();
        } else {
            fireWCModelChanged(event);
        }
    }

    /**
     * Fire event that undelaying file has changed (or user requested reload)
     * and the model needs to be reoladed.
     * 
     * If the model is dirty, it ask the user if it want to discard the changes.
     */
    protected void fireWCChanged() {
        if (trace) {
            Trace.trace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.WORKING_COPY,
                    "Fire WC changed externaly on '" + getName()); //$NON-NLS-1$
        }
        if (isWorkingCopyDirty()) {

            final boolean[] b = new boolean[] { true };
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                XpdResourcesPlugin.getStandardDisplay()
                        .syncExec(new Runnable() {
                            @Override
                            public void run() {
                                String msg = String.format(
                                        Messages.AbstractWorkingCopy_reloadStaleFile_shortdesc,
                                        new Object[] {
                                                getFirstResource().getName() });
                                b[0] = MessageDialog.openQuestion(
                                        XpdResourcesPlugin.getStandardDisplay()
                                                .getActiveShell(),
                                        Messages.AbstractWorkingCopy_warning_title,
                                        msg);

                            }
                        });
            }
            if (b[0]) {
                cleanup();
                fireWCChanged();
            }
        } else {
            fireEvent(WorkingCopy.PROP_RELOADED, null, null);
        }
    }

    /**
     * Fire remove event when file has been removed and all views should close
     * and destroy themselves.
     * 
     * @deprecated Use {@link #fireWCRemoved(IPath)} instead.
     */
    @Deprecated
    protected void fireWCRemoved() {
        fireWCRemoved(null);
    }

    /**
     * Fire remove event when file has been removed and all views should close
     * and destroy themselves. If a new path is provided then this will be added
     * to the new value of the property - indicating to the listeners that a
     * move has occurred.
     * 
     * @param newPath
     *            In case this is a move then the newPath will be set to the new
     *            location, otherwise set to <code>null</code>.
     * 
     * @since 3.0
     */
    protected void fireWCRemoved(final IPath newPath) {
        if (trace) {
            Trace.trace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.WORKING_COPY,
                    "Fire WC removed on '" + getName()); //$NON-NLS-1$
        }

        fireEvent(WorkingCopy.PROP_REMOVED, null, newPath);
    }

    /**
     * Fire dirty property change event.
     */
    protected void fireWCDirtyFlagChanged() {
        if (trace) {
            Trace.trace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.WORKING_COPY,
                    "Fire WC dirty flag chnged on '" + getName()); //$NON-NLS-1$
        }
        fireEvent(WorkingCopy.PROP_DIRTY, null, isWorkingCopyDirty());
    }

    /**
     * Fire property change event.
     * <p>
     * This will set the new value of the PropertyChangeEvent to
     * <code>null</code> and not to the state of the dirty flag.
     * </p>
     */
    protected void fireWCModelChanged() {
        fireWCModelChanged(null);
    }

    /**
     * Fire property change event.
     * 
     * @param event
     *            event that triggered to model change. This can be
     *            <code>null</code>.
     * 
     * @since 3.0
     */
    protected void fireWCModelChanged(final EventObject event) {
        if (trace) {
            Trace.trace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.WORKING_COPY,
                    "Fire WC model changed on '" + getName()); //$NON-NLS-1$
        }

        // Pass the resource set change notifications to the listeners
        updateUriCache(event);
        fireEvent(new NotificationPropertyChangeEvent(this, WorkingCopy.CHANGED,
                null, event, getNotifications(event)));
    }

    private void updateUriCache(EventObject event) {
        if (event instanceof ResourceSetChangeEvent) {
            ResourceSetChangeEvent setEvent = (ResourceSetChangeEvent) event;
            for (Notification notification : setEvent.getNotifications()) {
                if (notification.getEventType() == Notification.REMOVE
                        || notification.getEventType() == Notification.SET) {
                    Object old = notification.getOldValue();
                    if (old instanceof EObject) {
                        EObject eo = (EObject) old;
                        synchronized (resolveLock) {
                            if (uriToEObject != null) {
                                for (Iterator<Entry<String, EObject>> iterator =
                                        uriToEObject.entrySet()
                                                .iterator(); iterator
                                                        .hasNext();) {
                                    Entry<String, EObject> entry =
                                            iterator.next();
                                    //
                                    // Remove the deleted object from cache and
                                    // any object that is a descendent of it.
                                    if (eo == entry.getValue() || EcoreUtil
                                            .isAncestor(eo, entry.getValue())) {
                                        iterator.remove();
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Clean up the model and notify listeners that they should reconnect.
     */
    @Override
    public void reLoad() {
        if (isInvalidFile()) {
            deleteInvalidFileMarker();
        }
        cleanup();
        fireWCChanged();
    }

    /**
     * Check if the contained <code>Resource</code> is read only.
     * 
     * @return <code>true</code> when it is read-only.
     */
    @Override
    public boolean isReadOnly() {
        if (isLoaded()) {
            /*
             * If Project is in pre-compilation mode, check whether the file
             * that backs this working copy should be allowed to be changed or
             * not (i.e. effectively read only).
             * 
             * This will Iterate thru pre-compile contributions looking at the
             * "SourceArtifact" Expression filter element in each.
             * 
             * Each Expression filter will be passed this working copy's file.
             * If any return true it means that this working copy is considered
             * to be for a file that can affect a derived pre-compiled file AND
             * THEREFORE must not be allowed to be changed.
             */

            IResource firstResource = getFirstResource();
            IProject project = null;
            if (null != firstResource) {

                project = firstResource.getProject();
            }
            if (null != project) {
                // Mark as read-only if the project is locked for production.
                try {
                    if (project.hasNature(XpdConsts.LOCKED_FOR_PRODUCTION_NATURE)) {
                        return true;
                    }
                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }

                if (ProjectUtil.isPrecompiledProject(project)) {

                    if (com.tibco.xpd.resources.precompile.PreCompileContributorManager
                            .getInstance()
                            .isPrecompiledSourceArtefact(firstResource)) {

                        return true;
                    }
                }
            }

            EObject root = getRootElement();
            return getEditingDomain().isReadOnly(root.eResource());
        }
        return false;
    }

    /**
     * Lazy created savable of this working copy.
     * 
     * @return savable of this working copy.
     */
    @Override
    public Saveable getSaveable() {
        if (saveable == null) {
            saveable = new WorkingCopySaveable(this);
        }
        return saveable;
    }

    /**
     * Fire read-only property change.
     */
    protected void fireWCReadOnlyChanged() {
        if (trace) {
            Trace.trace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.WORKING_COPY,
                    "Fire WC read only changed on '" + getName()); //$NON-NLS-1$
        }
        fireEvent(WorkingCopy.PROP_READONLY, null, isReadOnly());
    }

    /**
     * Notify all listeners with the event.
     * 
     * @param event
     *            event
     */
    private void notifyListeners(PropertyChangeEvent event) {
        for (PropertyChangeListener listener : new ArrayList<PropertyChangeListener>(
                listeners)) {
            listener.propertyChange(event);
        }
    }

    /**
     * It asks subclass to compute dependency from the model or if model is not
     * loaded - it retrive the data from cache (resource marker).
     * 
     * @see #computeDependenciesFromModel()
     * @see #getDependencyHandles()
     * 
     * @return dependency list
     */
    @Override
    public List<IResource> getDependency() {
        List<IResource> handles = getDependencyHandles();

        /*
         * Sid MR 42595: Don't destroy original handles list, it may contain
         * dependencies that don't initially exist but do after build!
         */
        List<IResource> dependenciesList = new ArrayList<IResource>();

        for (Iterator<IResource> iter = handles.iterator(); iter.hasNext();) {
            IResource resource = iter.next();
            if (resource.isAccessible()) {
                dependenciesList.add(resource);
            }
        }

        return dependenciesList;
    }

    /**
     * Get the list of resources this working copy depends on. This will return
     * the file handles - files may not exist in the workspace. Use
     * {@link #getDependency()} to get list of resources that exist.
     * 
     * @see #getDependency()
     * 
     * @return list of dependent resource handles.
     * @since 3.0
     */
    protected List<IResource> getDependencyHandles() {
        return getDependencyHandles(false);
    }

    /**
     * Get the list of resources this working copy depends on. This will return
     * the file handles - files may not exist in the workspace.
     * 
     * @param includeUnresolvedProxies
     *            <code>true</code> to also include unresolved proxies.
     * 
     * @return list of dependent resource handles.
     */
    private List<IResource> getDependencyHandles(
            boolean includeUnresolvedProxies) {
        // If model has been modified, compute dependencies from the model
        if (isLoaded() && isWorkingCopyDirty()) {
            return computeDependenciesFromModel();
        } else if (dependencyIndexer != null) {
            // Check if this working copy has indexed the dependencies, if not
            // then calculate from the model and store in indexer
            Collection<IResource> dependencyHandles = dependencyIndexer
                    .getDependencyHandles(includeUnresolvedProxies);
            if (dependencyHandles == null) {
                if (!isLoaded() && (isInvalidFile() || isInvalidVersion())) {
                    /*
                     * Model is invalid / or wrong version so don't attempt to
                     * load it again
                     */
                    return new ArrayList<IResource>(0);
                }
                // Not indexed so do it now
                List<IResource> dependencies = computeDependenciesFromModel();
                dependencyIndexer.updateDependencies(dependencies);
                return dependencies;
            } else {
                return new ArrayList<IResource>(dependencyHandles);
            }
        }

        return new ArrayList<IResource>(0);
    }

    /**
     * Access to attributes stored together with WorkingCopy.
     * 
     * @return direct reference to attributes map.
     */
    @Override
    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    /**
     * Checks if the underlying files are invalid.
     * 
     * @return false
     */
    @Override
    public boolean isInvalidFile() {
        return isInvalid || hasInvalidFileMarker();
    }

    /**
     * Check if the model is of the right version (if supported).
     * 
     * @return <code>true</code> if this model is of the current version,
     *         <code>false</code> if of an older version.
     * 
     * @since 3.5
     */
    public boolean isInvalidVersion() {
        if (isVersionProblem == null) {
            /*
             * Check if there are any invalid file markers with the invalid
             * version attribute set.
             */
            for (IMarker marker : getInvalidFileMarkers()) {
                try {
                    Object value = marker.getAttribute(MARKER_VERSIONISSUE);
                    if (value instanceof Boolean && ((Boolean) value)) {
                        isVersionProblem = true;
                        break;
                    }
                } catch (CoreException e) {
                    // Do nothing
                }
            }

            // If no invalid version marker found then set to false
            if (isVersionProblem == null) {
                isVersionProblem = false;
            }
        }
        return isVersionProblem;
    }

    /**
     * Not implemented, do nothing.
     * 
     * @param readOnly
     *            will be ignored
     */
    @Override
    public void setReadOnly(boolean readOnly) {
    }

    /**
     * Create resource listener, that check if given resource change event
     * affects this working copy, and if so it fire appropriate events. If this
     * method returns <code>null</code> then no resource change listener will be
     * registered.
     * 
     * @return resource change listener
     */
    protected IResourceChangeListener createResourceChangeListener() {
        return new IResourceChangeListener() {
            @Override
            public void resourceChanged(IResourceChangeEvent event) {
                /*
                 * XPD-1128: May not have an actual resource backing a working
                 * copy created from temporary input stream (for things like
                 * history revisions)
                 */
                if (getEclipseResources().isEmpty()
                        || getEclipseResources().get(0) == null) {
                    return;
                }

                if (isIgnoreResourceChanges()) {
                    return;
                }

                IResourceDelta delta = event.getDelta();

                if (delta != null) {
                    boolean rebuildDependencies = false;
                    IResourceDelta d1 = delta.findMember(
                            getEclipseResources().get(0).getFullPath());
                    if (d1 != null) {
                        refreshResource(delta);
                    }
                    if ((event.getType()
                            & IResourceChangeEvent.PRE_BUILD) == 0) { // not
                                                                      // PRE_BUILD
                        if (dependenciesToCheckOnNextResourceChangeEvent != null) {
                            for (IResource res : dependenciesToCheckOnNextResourceChangeEvent) {
                                /*
                                 * If the dependency list contains an unresolved
                                 * proxy then see if the this delta contains a
                                 * resource that this proxy resolves to. If it
                                 * does then fire dependency change and rebuild
                                 * the dependencies.
                                 */
                                if (res instanceof DependencyProxyResource) {
                                    ProxyResolverDeltaVisitor visitor =
                                            new ProxyResolverDeltaVisitor(
                                                    (DependencyProxyResource) res);
                                    try {
                                        delta.accept(visitor);
                                        if (visitor.matchFound()) {
                                            rebuildDependencies = true;
                                            fireDependencyChange(event);
                                            break;
                                        }
                                    } catch (CoreException e) {
                                        XpdResourcesPlugin.getDefault()
                                                .getLogger().error(e);
                                    }
                                } else {
                                    IResourceDelta d2 =
                                            delta.findMember(res.getFullPath());
                                    if (d2 != null) {
                                        if (d2.getKind() != IResourceDelta.CHANGED
                                                || (d2.getFlags()
                                                        & (IResourceDelta.CONTENT)) != 0) {
                                            fireDependencyChange(event);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Recalculate dependencies if the content of the file
                    // being managed by this working copy has changed
                    if (rebuildDependencies || (d1 != null
                            && d1.getKind() == IResourceDelta.CHANGED
                            && (d1.getFlags() & IResourceDelta.CONTENT) != 0)) {
                        /*
                         * Sid MR 42595: For
                         * "set of dependencies to compare with next resource change delta"
                         * - make sure we include ALL resources NOT just those
                         * that currently exist.
                         * 
                         * In this way, if a file depends on another file that
                         * doesn't exist YET but is subsequently created, then
                         * we will be revalidated.
                         */
                        dependenciesToCheckOnNextResourceChangeEvent =
                                getDependencyHandles(true);
                    }
                }

            }

            /**
             * Update the listeners with the changed delta.
             * 
             * @param delta
             */
            private void refreshResource(IResourceDelta delta) {
                /*
                 * XPD-1128: May not have an actual resource backing a working
                 * copy created from temporary input stream (for things like
                 * history revisions)
                 */
                if (getEclipseResources().isEmpty()) {
                    return;
                }

                if (delta != null) {
                    boolean shouldReload = false;
                    boolean shouldRemove = false;
                    IPath newPath = null;
                    IResource resource = null;

                    for (IResource res : getEclipseResources()) {
                        IResourceDelta d = delta.findMember(res.getFullPath());
                        if (d != null) {
                            switch (d.getKind()) {
                            case IResourceDelta.CHANGED:
                                long stamp = res.getLocalTimeStamp();
                                if (stamp != modificationStamps.get(res)) {
                                    modificationStamps.put(res, stamp);
                                    shouldReload = true;
                                    resource = res;
                                } else {
                                    // System.out
                                    // .println("["
                                    // + Thread.currentThread()
                                    // .getId()
                                    // +
                                    // "] **ABSTRACTWORKINGCOPY CHANEG FILE
                                    // ALREADY LOADED WC: "
                                    // + stamp + " "
                                    // + res.getFullPath());
                                }
                                break;
                            case IResourceDelta.REMOVED:
                                // Check if this is a move, if it is then set
                                // the newPath
                                if ((IResourceDelta.MOVED_TO
                                        & d.getFlags()) != 0) {
                                    newPath = d.getMovedToPath();
                                }
                                shouldRemove = true;
                                break;
                            default:
                            }

                        }
                    }
                    if (shouldReload) {
                        // System.out.println("[" +
                        // Thread.currentThread().getId()
                        // + "]==> ABSTRACTWORKINGCOPY RESCHANGE WC: "
                        // + resource.getFullPath());

                        // XPD-2887: must also clear any cached dependencies.
                        if (!isWorkingCopyDirty()
                                || shouldDiscardChanges(resource)) {
                            cleanup();
                            clearDependencies();
                            revalidateFile();
                            fireWCChanged();
                        }

                        // System.out.println("[" +
                        // Thread.currentThread().getId()
                        // + "]<== ABSTRACTWORKINGCOPY RESCHANGE WC: "
                        // + resource.getFullPath());

                    } else if (shouldRemove) {
                        removeWorkingCopy(newPath);
                    }
                }
            }
        };
    }

    /**
     * Called when the underlying file is deleted from the workspace. This will
     * remove the resource from the editing domain and fire "remove" event to
     * clear indexers and remove this working copy from the project cache.
     * 
     * @param newPath
     *            if the resource is being removed because it is being moved
     *            then this will be the new path of this resource (this path
     *            will be added to the remove event fired).
     * @since 3.5.10
     */
    protected void removeWorkingCopy(IPath newPath) {
        setIsBeingRemoved(true);
        try {
            cleanup();
            // If the new path is set then this will indicate to
            // the listeners that a move has occurred
            fireWCRemoved(newPath);

            // Update dependency index
            clearDependencies();

            dispose();
        } finally {
            setIsBeingRemoved(false);
        }
    }

    /**
     * Set the flag that will indicate whether this working copy is being
     * unloaded as a result of the underlying file being removed from the
     * workspace.
     * 
     * @param isBeingRemoved
     */
    private synchronized void setIsBeingRemoved(boolean isBeingRemoved) {
        this.isBeingRemoved = isBeingRemoved;
    }

    /**
     * Get the value of the "isBeingRemoved" flag.
     * 
     * @return <code>true</code> if this working copy is being removed/unloaded
     *         as a result of the file being deleted from the workspace.
     */
    private synchronized boolean isBeingRemoved() {
        return isBeingRemoved;
    }

    /**
     * Fire dependency change event. The default implements will just call
     * {@link #fireDependencyEvent()}. Subclasses may override if they wish to
     * process this event.
     * 
     * @param event
     *            resource change event
     * @since 3.3
     */
    protected void fireDependencyChange(IResourceChangeEvent event) {
        fireDependencyEvent();
    }

    /**
     * Fire dependency change event.
     * 
     * @see #fireDependencyChange(IResourceChangeEvent)
     */
    protected void fireDependencyEvent() {
        if (trace) {
            Trace.trace(XpdResourcesPlugin.getDefault(),
                    XpdResourcesDebugOptions.WORKING_COPY,
                    "Fire WC dependency changed on '" + getName()); //$NON-NLS-1$
        }
        fireEvent(PROP_DEPENDENCY_CHANGED, null, null);
    }

    /**
     * It shows UI popup dialog and ask if the user wants keep or discard
     * changes when file has been changed outside Eclipse.
     * 
     * @param res
     *            Resource that needs reloading.
     * @return true, when the model should be reloaded.
     */
    protected boolean shouldDiscardChanges(final IResource res) {
        final boolean[] b = new boolean[1];
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            Display.getDefault().syncExec(new Runnable() {
                @Override
                public void run() {
                    Shell s = PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getShell();
                    b[0] = MessageDialog.openQuestion(s,
                            Messages.AbstractWorkingCopy_warning_title,
                            String.format(
                                    Messages.AbstractWorkingCopy_reloadStaleFile_shortdesc,
                                    res != null ? res.getName() : "")); //$NON-NLS-1$
                }
            });
            return b[0];
        } else {
            // Will assume we want to reload file in headless mode.
            return true;
        }

    }

    /**
     * Returns flag that indicate whether resource changes should be ignored.
     * 
     * @return true, when resource changes should be ignored.
     */
    protected boolean isIgnoreResourceChanges() {
        return ignoreResourceChanges;
    }

    /**
     * Set whether resource changes should be ignored.
     * 
     * @param ignore
     *            <code>true</code> if resource changes/events should be
     *            ignored, <code>false</code> otherwise.
     */
    protected void setIgnoreResourceChanges(boolean ignore) {
        ignoreResourceChanges = ignore;
    }

    /**
     * Returns list of InvalidFileMarker (can be empty).
     * 
     * @return list of markers (can be empty)
     */
    protected List<IMarker> getInvalidFileMarkers() {
        IMarker[] markerArr;
        List<IMarker> result = new ArrayList<IMarker>();

        try {
            for (IResource res : getEclipseResources()) {
                if (res.exists()) {
                    markerArr = res.findMarkers(XpdConsts.INVALID_FILE_MARKER,
                            true,
                            IResource.DEPTH_INFINITE);
                    if (markerArr != null && markerArr.length > 0) {
                        for (int index = 0; index < markerArr.length; index++) {
                            IMarker tempMarker = markerArr[index];
                            if (tempMarker != null && tempMarker.exists()) {
                                result.add(tempMarker);
                            }
                        }
                    }
                }
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }

        return result;
    }

    /**
     * Remove invalid file marker.
     */
    protected void deleteInvalidFileMarker() {

        isInvalid = false;
        isVersionProblem = false;

        final IWorkspaceRunnable op = new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                removeInvalidFileMarkers();
            }
        };

        /*
         * Schedule a background job to delete the markers - this is required as
         * no changes to resources can be made during a POST_CHANGE event
         */
        new Job(Messages.AbstractWorkingCopy_deleteInvalidFileMarkers_shortdesc) {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    op.run(monitor);
                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
                return Status.OK_STATUS;
            }
        }.schedule();
    }

    /**
     * Deletes all invalid file markers attached to resource.
     * 
     * @since v3.5.3
     */
    public void removeInvalidFileMarkers() {
        List<IMarker> invalidFileMarkers = getInvalidFileMarkers();
        for (IMarker marker : invalidFileMarkers) {
            try {
                marker.delete();
            } catch (CoreException e) {
                XpdResourcesPlugin.log(e);
            }
        }
    }

    /**
     * Make sure that IMarkers are updated when file has changed.
     * 
     */
    protected void revalidateFile() {
        if (hasInvalidFileMarker()) {
            deleteInvalidFileMarker();
        }
        // cause file to reload
        getRootElement();
    }

    /**
     * Returns true if any of the resources has invalid file marker.
     * 
     * @return true if any of the resources has invalid file marker
     */
    protected boolean hasInvalidFileMarker() {
        return !getInvalidFileMarkers().isEmpty();
    }

    /**
     * Create invalid file marker on the resource.
     * 
     * @param res
     *            one of WorkingCopy resources
     * @throws IllegalArgumentException
     *             If resource is not in the Working Copy.
     */
    protected void createInvalidFileMarker(final IResource res, String detail) {
        createInvalidFileMarker(res, Collections.singleton(detail));
    }

    /**
     * Create invalid file marker on the resource.
     * 
     * @param res
     *            one of WorkingCopy resources
     * @param details
     *            extra information to add to the marker description
     * @throws IllegalArgumentException
     *             If resource is not in the Working Copy.
     */
    protected void createInvalidFileMarker(final IResource res,
            final Collection<String> details) {
        createInvalidFileMarker(res, details, InvalidFileMarkerKind.OTHER);
    }

    /**
     * Create invalid file marker on the resource.
     * 
     * @param res
     *            one of WorkingCopy resources
     * @param details
     *            extra information to add to the marker description
     * @throws IllegalArgumentException
     *             If resource is not in the Working Copy.
     */
    protected void createInvalidFileNameMarker(final IResource res,
            String reasonString) {
        createInvalidFileMarker(res,
                Collections.singleton(reasonString),
                InvalidFileMarkerKind.NAME_INVALID);
    }

    /**
     * Create an invalid version file marker on the resource.
     * 
     * @param res
     *            the working copy resource
     * 
     * @since 3.5
     */
    protected void createInvalidVersionFileMarker(IResource res) {
        isVersionProblem = true;

        if (res == null) {
            return;
        }

        boolean doCreateMarker = true;

        /*
         * if the container project has already got a Migration marker then
         * don't bother adding a migration marker at the file level.
         */
        try {
            IMarker[] markers = res.getProject().findMarkers(
                    XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                    true,
                    IResource.DEPTH_ZERO);
            if (markers.length > 0) {
                doCreateMarker = false;
            }
        } catch (CoreException e1) {
            // Do nothing and continue
        }

        if (doCreateMarker) {
            createInvalidFileMarker(res,
                    Collections.singleton(""), //$NON-NLS-1$
                    InvalidFileMarkerKind.VERSION_INVALID);
        }
    }

    /**
     * Create an invalid file marker on the file.
     * 
     * @param res
     * @param details
     * @param isVersionIssue
     *            <code>true</code> if this file is invalid because it's of an
     *            old version.
     */
    private void createInvalidFileMarker(final IResource res,
            final Collection<String> details,
            final InvalidFileMarkerKind markerKind) {

        /*
         * If project import is in progress then do not create invalid file
         * marker. This method will be called if the project being imported is
         * from an older version of Studio and therefore will be migrated during
         * import.
         */
        if (res == null || XpdResourcesPlugin.getDefault()
                .isProjectsImportInProgress()) {
            return;
        }

        if (!getEclipseResources().contains(res)) {
            throw new IllegalArgumentException(
                    Messages.AbstractWorkingCopy_failedToCreateMarkerForResourceNotInWorkingCopy_shortdesc);
        }

        if (details != null && !details.isEmpty()) {
            isInvalid = true;

            // try {
            final IWorkspaceRunnable action = new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    String markerType = XpdConsts.INVALID_FILE_MARKER;
                    String projectNameAttrib = XpdConsts.MARKER_ATT_PROJECTNAME;
                    /*
                     * If adding an invalid version marker then re-check that it
                     * has still got a version problem before adding the marker.
                     */

                    if (!hasInvalidFileMarker() && res.exists()
                            && (!InvalidFileMarkerKind.VERSION_INVALID
                                    .equals(markerKind)
                                    || isInvalidVersion())) {

                        for (String detailStr : details) {
                            IMarker marker = res.createMarker(markerType);

                            String msg =
                                    Messages.AbstractWorkingCopy_problemLoadingFile_shortdesc;

                            if (InvalidFileMarkerKind.VERSION_INVALID
                                    .equals(markerKind)) {
                                msg += Messages.AbstractWorkingCopy_fileCreatedInEarlierStudio_error_shortdesc;

                            } else if (detailStr != null
                                    && detailStr.length() > 0) {
                                msg += String.format(
                                        Messages.AbstractWorkingCopy_detail_shortdesc,
                                        detailStr);
                            }

                            marker.setAttribute(IMarker.MESSAGE, msg);

                            marker.setAttribute(IMarker.SEVERITY,
                                    IMarker.SEVERITY_ERROR);
                            marker.setAttribute(IMarker.LOCATION,
                                    res.getProjectRelativePath().toString());
                            String projName = res.getProject().getName();
                            marker.setAttribute(projectNameAttrib, projName);

                            if (InvalidFileMarkerKind.VERSION_INVALID
                                    .equals(markerKind)) {
                                marker.setAttribute(MARKER_VERSIONISSUE, true);

                            } else if (InvalidFileMarkerKind.NAME_INVALID
                                    .equals(markerKind)) {
                                marker.setAttribute(MARKER_NAMEISSUE, true);
                            }
                        }
                    }
                }
            };

            /*
             * Schedule a background job to add the markers - this is required
             * as no changes to resources can be made during a POST_CHANGE event
             */
            Job job = new Job(
                    Messages.AbstractWorkingCopy_createInvalidFileMarkers_shortdesc) {
                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    try {
                        action.run(monitor);
                    } catch (CoreException e) {
                        XpdResourcesPlugin.getDefault().getLogger().error(e,
                                String.format(
                                        Messages.AbstractWorkingCopy_exceptionCreatingMarkers_shortdesc,
                                        new Object[] { res.getFullPath() }));
                    }
                    return Status.OK_STATUS;
                }
            };

            job.setRule(res);
            job.schedule();
        }
    }

    /**
     * Access root element. Calling this method may cause model to load into
     * memory.
     * <p>
     * NOTE: the root element will be loaded using the UI Thread (since 3.0).
     * This is done as resource set listeners and the working copy event
     * triggers run inside the UI Thread.
     * </p>
     * <p>
     * NOTE: in headless mode standard eclipse locking mechanism will be used
     * instead running (and synchronizing on) the UI Thread. (since 3.3)
     * </p>
     * 
     * @return model root element, may be null if the file is invalid.
     */
    @Override
    public final EObject getRootElement() {

        if (rootElement == null || rootElement.eIsProxy()) {

            /*
             * If this working copy is being unloaded because the IResource is
             * deleted then return null.
             */
            if (isBeingRemoved()) {
                return null;
            }

            try {
                lock.acquire();
                rootElement = internal_getRootElement();
            } finally {
                lock.release();
            }

            if (rootElement != null) {
                /*
                 * Sid MR 42595: For
                 * "set of dependencies to compare with next resource change delta"
                 * - make sure we include ALL resources NOT just those that
                 * currently exist.
                 * 
                 * In this way, if a file depends on another file that doesn't
                 * exist YET but is subsequently created, then we will be
                 * revalidated.
                 */
                dependenciesToCheckOnNextResourceChangeEvent =
                        getDependencyHandles(true);
            }

        }

        return rootElement;
    }

    /**
     * This is the internal method that loads the root element. Note that this
     * method runs inside the UI Thread.
     * 
     * @see #getRootElement()
     * 
     * @return model root element, may be null if the file is invalid.
     */
    private EObject internal_getRootElement() {
        if (rootElement == null || rootElement.eIsProxy()) {
            try {

                if (!isInvalidFile()) {

                    if (checkFileName()) {

                        long start = 0;
                        if (trace) {
                            start = System.currentTimeMillis();
                        }

                        rootElement = doLoadModel();

                        if (trace) {
                            String msg = "Loaded '" + getName() //$NON-NLS-1$
                                    + "', loading time: " //$NON-NLS-1$
                                    + (System.currentTimeMillis() - start);
                            Trace.trace(XpdResourcesPlugin.getDefault(), msg);
                        }
                    }
                }

                if (rootElement != null) {
                    /*
                     * XPD-391: When root element is loaded then store the
                     * timestamp of the resource it was loaded from - so if we
                     * get a subsequent change event etc cuase by the same
                     * trigger somewhere it then change event can compare actual
                     * timestamp with stored one.
                     */
                    refreshModificationStamps(getEclipseResources());

                    Collection<Resource> resources = getResources();
                    // Install adapters for the managed resources
                    if (resources != null) {
                        for (Resource res : resources) {
                            installAdapters(res);
                        }
                    }

                }
            } catch (InvalidFileException e) {
                if (getFirstResource() != null && getFirstResource().exists()) {
                    if (e instanceof InvalidVersionException) {
                        createInvalidVersionFileMarker(getFirstResource());
                    } else {
                        createInvalidFileMarker(getFirstResource(),
                                e.getMessage());
                    }
                }
                if (trace) {
                    String msg = "Created invalid file marker for: '" //$NON-NLS-1$
                            + getName() + "'"; //$NON-NLS-1$
                    Trace.trace(XpdResourcesPlugin.getDefault(), msg);
                }

            } finally {
                // Clear notifications
                clearNotifications();
            }
        }
        return rootElement;
    }

    /**
     * @return <code>true</code> If the file name is valid for this resource
     *         type or <code>false</code> if it is not (a marker is created).
     */
    private boolean checkFileName() {
        IResource firstResource = getFirstResource();

        if (firstResource != null) {

            /* Check the file name first. */

            String invalidFileNameString = doCheckFileName(firstResource);

            if (invalidFileNameString != null
                    && invalidFileNameString.length() > 0) {

                createInvalidFileNameMarker(firstResource,
                        invalidFileNameString);

                return false;
            }

        }
        return true;
    }

    /**
     * Check that the resource name/path is valid for this working copy's
     * resource type.
     * <p>
     * If the name is valid then return <code>null</code> or <code>""</code>.
     * <p>
     * If the name is invalid then return a string explaning why.
     * <p>
     * <b>It is highly recommended that sub-class implementations of this method
     * call the default implementation if their own check shoiws the file name
     * 
     * @param resource
     * 
     * @return <code>null</code> or <code>""</code> if file name is <b>ok</b>
     *         else a string containing the reason that it is not.
     * 
     * @since v3.5.3
     */
    protected String doCheckFileName(IResource resource) {
        /*
         * There is an issue with underlying eclipse when
         * org.eclipse.emf.common.CommonPlugin.asLocalURI(CommonPlugin.java:85)
         * is called when there is a % in the file path, it throws an
         * IllegalArgumentException.
         * 
         * So we have to ensure that the user doesn't try to do anything with
         * the file - so easy way is to prevent load into working copy.
         */
        if (resource.getName().contains("%")) { //$NON-NLS-1$
            return Messages.AbstractWorkingCopy_PercentInFileName_message;
        }
        return null;
    }

    /**
     * Get the <code>Resource</code>s being managed by this working copy.
     * <p>
     * Subclasses may override. The default implementation returns all resources
     * from the editing domain's resourceSet.
     * </p>
     * 
     * @return collection of <code>Resource</code>s. This can be
     *         <code>null</code>.
     */
    protected Collection<Resource> getResources() {
        ResourceSet resourceSet = getEditingDomain().getResourceSet();
        Collection<Resource> resources = null;

        if (resourceSet != null) {
            resources = resourceSet.getResources();
        }

        return resources;
    }

    /**
     * Install all required adapters on the resource, required for
     * WorkingCopyUtil. Adapters will not be installed if they already exists.
     * 
     * @param res
     *            resource where install the adapter
     */
    protected void installAdapters(Resource res) {
        Adapter ad = EcoreUtil.getExistingAdapter(res,
                WorkingCopyProviderAdapter.class);
        if (ad == null) {
            if (wcAdapter == null) {
                wcAdapter = new WorkingCopyProviderAdapter(this);
            }
            res.eAdapters().add(wcAdapter);
        }

        installEContentAdapter(res);
    }

    /**
     * Install the EContent adapter to get list of notifications from the
     * resource.
     * 
     * @since 3.1
     * 
     * @param res
     *            resource to monitor.
     */
    protected void installEContentAdapter(Resource res) {
        // Add content adapter
        if (!res.eAdapters().contains(contentAdapter)) {
            res.eAdapters().add(contentAdapter);
        }
    }

    /**
     * Uninstall the registered adapters.
     * 
     * @see #installAdapters(Resource)
     * @param res
     */
    protected void uninstallAdapters(Resource res) {
        if (res != null) {
            if (wcAdapter != null) {
                res.eAdapters().remove(wcAdapter);
            }
            if (contentAdapter != null) {
                res.eAdapters().remove(contentAdapter);
            }
        }
    }

    /**
     * Get the resource change notifications. This is collected for the working
     * copy that does not use the single transactional editing domain. The
     * resources that use the single transactional editing domain manages it's
     * on resource set change listener that provides the notifications.
     * 
     * @param event
     * 
     * @since 3.1
     * 
     * @return collection of <code>Notification</code>s.
     */
    protected Collection<Notification> getNotifications(EventObject event) {
        List<Notification> currNotifications =
                new ArrayList<Notification>(notifications);
        clearNotifications();

        return currNotifications;
    }

    /**
     * Clear the resource change notifications.
     * 
     * @see #getNotifications(EventObject)
     * 
     * @since 3.1
     */
    private void clearNotifications() {
        notifications.clear();
    }

    /**
     * default implementation of isLoaded(), chacks if root element is null.
     * 
     * @return true, when the model is loaded
     */
    @Override
    public boolean isLoaded() {
        return rootElement != null && !rootElement.eIsProxy();
    }

    /**
     * Load working copy models to memory.
     * 
     * Tle implementation can throw InvalidModelException or manage the
     * InvalidFileMarker on its own and return null<br>
     * Note: implmentation should call installAdapterss(Resource) for each
     * loaded EMF resource. This is required for WorkingCopyUtil.
     * 
     * @return root element
     * @throws InvalidFileException
     *             when the file is invalid
     */
    protected abstract EObject doLoadModel() throws InvalidFileException;

    /**
     * Register resourcelocator for given EPackage, it is required for
     * {@link #getMetaText(EObject)} to work.
     * 
     * @param pck
     *            EPackage to register
     * @param locator
     *            resource locator (usually plugin class of the model.edit
     *            plugin)
     */
    protected void registerResourceProvider(EPackage pck,
            ResourceLocator locator) {
        locators.put(pck, locator);
    }

    /**
     * Get meta-data name of the EObject, it takes trnaslateble description of
     * the EClass from the model.edit plugin.
     * <p>
     * Example: for EObject of type Activity it will return 'Activity'
     * 
     * 
     * @param eo
     *            object to test
     * @return meta-data description
     */
    @Override
    public String getMetaText(EObject eo) {
        ResourceLocator locator = locators.get(eo.eClass().getEPackage());
        return locator == null ? WorkingCopyUtil.getText(eo)
                : locator.getString("_UI_" + eo.eClass().getName() + "_type"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Default implementation use file marker to store values of the attributes
     * when file is not loaded.
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#getCachedAttribute(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getCachedAttribute(String key) {
        if (isLoaded()) {
            String val = null;
            if (cachedAttributes != null) {
                val = cachedAttributes.get(key);
            } else {
                cachedAttributes = new HashMap<String, String>();
            }
            if (val == null) {
                val = computeCachedAttributeValue(key);
                cachedAttributes.put(key, val);
            }
            return val;
        }
        if (cachedAttributes != null) {
            return cachedAttributes.get(key);
        }
        try {
            IMarker cacheMarker = getDependencyCacheMarker();
            if (cacheMarker == null) {
                if (!isInvalidFile()) {
                    // load the model because marker is not present
                    return computeCachedAttributeValue(key);
                }
                return ""; //$NON-NLS-1$
            }
            String val = String.valueOf(
                    cacheMarker.getAttribute(XpdConsts.DEPENDENCY_CACHE_ATT));

            ByteArrayInputStream stream =
                    new ByteArrayInputStream(val.getBytes());
            Properties props = new Properties();
            try {
                props.load(stream);
                cachedAttributes = new HashMap<String, String>();
                cachedAttributes.putAll((Map) props);
                if (!cachedAttributes.containsKey(key)) {
                    // migration problem! - the attribute was introduced after
                    // the file was created. need to load the model.
                    val = computeCachedAttributeValue(key);
                    cachedAttributes.put(key, val);
                    return val;
                }
                return cachedAttributes.get(key);
            } catch (IOException e) {
                // ignore
            }

        } catch (CoreException e) {
            // ignore
        }
        return null;
    }

    /**
     * Returns list of the cached attributes IDs. The attributes with these ids
     * will be cached.
     * 
     * @return list of the ids
     */
    protected List<String> getCachedAttributesIds() {
        return new ArrayList<String>();
    }

    /**
     * Compute cached property value from the model.
     * 
     * @param id
     *            id of the property
     * @return the computed falue
     */
    protected String computeCachedAttributeValue(String id) {
        return null;
    }

    /**
     * Name of the WorkingCopy - default implementation returns the name of the
     * first resource.
     * 
     * @return name of working copy
     */
    @Override
    public String getName() {
        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (getFirstResource() != null) {
            return getFirstResource().getName();
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Image of the WorkingCopy - default implementation returns image of the
     * first resource.
     * 
     * @return the image of the working copy
     */
    @Override
    public Image getImage() {
        /*
         * XPD-1128: May not have an actual resource backing a working copy
         * created from temporary input stream (for things like history
         * revisions)
         */
        if (getFirstResource() == null) {
            return null;
        }

        WorkbenchLabelProvider lp = new WorkbenchLabelProvider();
        Image img = lp.getImage(getFirstResource());
        lp.dispose();
        return img;

    }

    /**
     * Resolve the uri to an <code>EObject</code>.
     * 
     * @param uri
     *            The uri to resolve to an EObject.
     * @return The resolved EObject, or <code>null</code> if it is not found.
     */
    @Override
    public EObject resolveEObject(String uri) {
        EObject eo = null;

        if (uri != null && uri.length() > 0) {
            EObject root = getRootElement();
            if (root != null && root.eResource() != null) {
                Resource resource = root.eResource();

                // Need just the fragment of the uri
                if (uri.startsWith("platform:")) { //$NON-NLS-1$
                    URI _uri = URI.createURI(uri);
                    uri = _uri.fragment();
                }
                synchronized (resolveLock) {

                    if (uriToEObject == null) {
                        uriToEObject = new HashMap<String, EObject>();
                        TreeIterator<?> i = rootElement.eAllContents();
                        while (i.hasNext()) {
                            Object next = i.next();
                            if (next instanceof EObject) {
                                EObject nextEo = (EObject) next;
                                String newUri = resource.getURIFragment(nextEo);
                                uriToEObject.put(newUri, nextEo);
                            }
                        }

                    }
                    eo = uriToEObject.get(uri);

                    if (eo == null) {
                        /*
                         * Cache doesn't contain the EObject so look it up in
                         * the resource and if found update the cache
                         */
                        eo = resource.getEObject(uri);

                        if (eo != null) {
                            uriToEObject.put(uri, eo);
                        }
                    }
                }
            }
        }
        return eo;
    }

    /**
     * Clears the URI to EObject cache.
     */
    @Override
    public void clearUriCache() {
        synchronized (resolveLock) {
            uriToEObject = null;
        }
    }

    /**
     * Get the target EObject of this marker. By default this will look at the
     * Location attribute of the Marker for the URI.
     * 
     * @param marker
     * @return resolved EObject, <code>null</code> if not found.
     * @since 3.6.1
     */
    public EObject getTarget(IMarker marker) {
        if (marker != null) {
            String uri = marker.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
            if (uri != null && !uri.trim().isEmpty()) {
                return resolveEObject(uri);
            }

        }
        return null;
    }

    /**
     * Get the severity of the problem marker.
     * 
     * @param eo
     *            The EObject.
     * @param er
     *            The resource.
     * @return The problem severity.
     * @throws CoreException
     *             If there was a problem finding the markers.
     */
    @Override
    public int getSeverity(EObject eo, IResource er) throws CoreException {
        int severity = 0;
        synchronized (severityLock) {
            Integer cached = eObjectToSeverity.get(eo);
            if (cached == null) {
                IMarker[] markers = er.findMarkers(IMarker.PROBLEM,
                        true,
                        IResource.DEPTH_ZERO);
                if (markers != null) {
                    for (IMarker marker : markers) {
                        EObject markedEO = getTarget(marker);
                        if (markedEO != null
                                && EcoreUtil.isAncestor(eo, markedEO)) {
                            int priority =
                                    marker.getAttribute(IMarker.SEVERITY, -1);
                            if (priority == IMarker.SEVERITY_WARNING) {
                                severity = IMarker.SEVERITY_WARNING;
                                eObjectToSeverity.put(eo,
                                        new Integer(severity));
                            } else if (priority == IMarker.SEVERITY_ERROR) {
                                severity = IMarker.SEVERITY_ERROR;
                                eObjectToSeverity.put(eo,
                                        new Integer(severity));
                                break;
                            }
                        }
                    }
                }
            } else {
                severity = cached;
            }
        }
        return severity;
    }

    /**
     * Clears the severity cache.
     */
    @Override
    public void clearSeverityCache() {
        synchronized (severityLock) {
            eObjectToSeverity.clear();
        }
    }

    /**
     * Migrate this model to the latest version (if supported). This will do
     * nothing if {@link #isInvalidVersion()} return <code>false</code>.
     * <p>
     * This will call {@link #doMigrateToLatestVersion()} and then
     * {@link #reLoad()} to cause a reload (and reload notification) of the
     * model.
     * </p>
     * 
     * @throws CoreException
     * 
     * @since 3.5
     */
    public final void migrate() throws CoreException {
        if (isInvalidVersion()) {
            try {
                doMigrateToLatestVersion();
                isVersionProblem = false;
                reLoad();
            } catch (UnsupportedOperationException e) {
                System.err.println(
                        String.format("Migration is not supported by %s", //$NON-NLS-1$
                                getClass().getName()));
                XpdResourcesPlugin.getDefault().getLogger()
                        .error(String.format("Migration is not supported by %s", //$NON-NLS-1$
                                getClass().getName()));
            } catch (CoreException e) {
                createInvalidFileMarker(getFirstResource(),
                        e.getLocalizedMessage());
                throw e;
            }
        }
    }

    /**
     * Carry out the migration of this model to the latest version.
     * <p>
     * Default implementation does nothing (throws
     * {@link UnsupportedOperationException}). Subclasses that support migration
     * should override this method (especially if <code>true</code> will be
     * reported by {@link #isInvalidVersion()}).
     * </p>
     * 
     * @throws CoreException
     */
    protected void doMigrateToLatestVersion() throws CoreException {
        throw new UnsupportedOperationException();
    }

    /**
     * Dispose off all resources and listeners. Subclasses may override this
     * method but the super method must be called.
     * 
     * @since 3.0 this method is public. It is required by working copy
     *        framework and clients (excluding subclasses) should never call
     *        this method.
     */
    public void dispose() {
        /**
         * Clear the URI cache and unload the EMF resources. This will be called
         * when the resources associated with this working copy are deleted.
         */
        clearUriCache();
        cleanup();

        // Unregister the workspace resource listener if one was registered
        if (resourceListener != null) {
            ResourcesPlugin.getWorkspace()
                    .removeResourceChangeListener(resourceListener);
        }
        // Unload the EMF resources
        if (!isInvalidFile() && isLoaded()) {
            EObject rootElement = getRootElement();

            if (rootElement != null) {
                Collection<Resource> resources = getResources();
                if (resources != null) {
                    for (Resource res : resources) {
                        res.unload();
                    }
                } else {
                    Resource resource = rootElement.eResource();

                    if (resource != null) {
                        resource.unload();
                    }
                }
            }
        }
    }

    /**
     * Fire a {@link PropertyChangeEvent} with the given property name, old and
     * new value.
     * 
     * @param propertyName
     *            event property name
     * @param oldValue
     *            the old value of the property
     * @param newValue
     *            the new value of the property
     */
    private void fireEvent(String propertyName, Object oldValue,
            Object newValue) {
        if (propertyName != null) {
            fireEvent(new PropertyChangeEvent(this, propertyName, oldValue,
                    newValue));
        }
    }

    /**
     * Notify the registered listeners with the given event.
     * 
     * @param event
     *            <code>PropertyChangeEvent</code>.
     */
    private void fireEvent(final PropertyChangeEvent event) {

        // Clear the collected notifications
        clearNotifications();

        /*
         * Sid XPD-7543: Allow UI notifications to be disabled in circumstances
         * where we're building models in background jobs and don't want nasty
         * lock ups.
         * 
         * Nick XPD-8428: Also disable UI notifications if we're in a ModalContext
         * for the same reason.
         */
        if (event != null && notificationsEnabled) {
            if (!XpdResourcesPlugin.isInHeadlessMode() && !ModalContext
                    .isModalContextThread(Thread.currentThread())) {
                XpdResourcesPlugin.getStandardDisplay()
                        .syncExec(new Runnable() {
                            @Override
                            public void run() {
                                notifyListeners(event);
                            }
                        });
            } else {
                try {
                    lock.acquire();
                    notifyListeners(event);
                } finally {
                    lock.release();
                }
            }
        }
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        IResource resource = getFirstResource();
        if (resource != null) {
            return String.format("WorkingCopy for: %s", //$NON-NLS-1$
                    resource.getFullPath().toString());
        }
        String name = getName();

        return name != null ? String.format("WorkingCopy for: %s", getName()) //$NON-NLS-1$
                : super.toString();
    }

    /**
     * Content adapter to build a list of notifications for this working copy's
     * resource.
     * 
     * @author njpatel
     * 
     */
    private class WCContentAdapter extends EContentAdapter {
        @Override
        public void notifyChanged(Notification notification) {

            notifications.add(notification);

            super.notifyChanged(notification);
        }
    }

    /**
     * Little enum to tell createInvalidaFileMarker what type of marker is being
     * created.
     * 
     * @author aallway
     * @since 27 Jan 2012
     */
    private enum InvalidFileMarkerKind {
        VERSION_INVALID, NAME_INVALID, OTHER
    }

    /**
     * Unload the given resource and remove it from the associated resource set.
     * NOTE: The actual work is performed by {@link #doUnloadResource(Resource)}
     * . This method calls {@link #doUnloadResource(Resource)} and ensures that
     * the proper synchronization is applied.
     * 
     * @see #doUnloadResource(Resource)
     * @see #loadResource(IResource)
     * 
     * @param res
     *            {@link Resource} to unload
     * @throws InterruptedException
     * @since 3.2 (updated 3.5.20)
     */
    protected final void unloadResource(final Resource res)
            throws InterruptedException {
        try {
            lock.acquire();
            doUnloadResource(res);
        } finally {
            lock.release();
        }
    }

    /**
     * Unload the given resource and remove it from its associated resource set.
     * NOTE: Please don't call this method directly, and instead use
     * {@link AbstractWorkingCopy#unloadResource(Resource)} as it will ensure
     * appropriate locking.
     * 
     * @see #unloadResource(IResource)
     * 
     * @param res
     *            {@link Resource} to unload
     * @throws InterruptedException
     * @since 3.5.20
     */
    protected void doUnloadResource(final Resource res)
            throws InterruptedException {
        if (res != null) {
            res.unload();
            if (res.getResourceSet() != null
                    && res.getResourceSet().getResources() != null) {
                res.getResourceSet().getResources().remove(res);
            }
        }
    }

    /**
     * Sid XPD-7543: Allow UI notifications to be disabled in circumstances
     * where we're building models in background jobs and don't want nasty lock
     * ups caused by firing notifications which is done on the UI thread.
     * 
     * @param notificationsEnabled
     *            <code>true</code> to enable UI notifications else false.
     */
    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    /**
     * Resource delta visitor that will check the resource delta to see if any
     * of the resources added resolves the last reported dependency proxy (if
     * any).
     * 
     */
    private class ProxyResolverDeltaVisitor implements IResourceDeltaVisitor {

        private final DependencyProxyResource proxy;

        private boolean found = false;

        /**
         * 
         */
        public ProxyResolverDeltaVisitor(DependencyProxyResource proxy) {
            this.proxy = proxy;
        }

        /**
         * Check if the delta contains a resource that the proxy resolves to.
         * 
         * @return
         */
        public boolean matchFound() {
            return found;
        }

        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         * 
         * @param delta
         * @return
         * @throws CoreException
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            if (!found) {
                if (delta.getKind() == IResourceDelta.ADDED) {
                    IResource resource = delta.getResource();
                    if (resource instanceof IFile) {
                        if (proxy.getSpecialFolderKind() != null) {
                            SpecialFolder sf = SpecialFolderUtil
                                    .getRootSpecialFolder(resource);
                            if (sf != null && proxy.getSpecialFolderKind()
                                    .equals(sf.getKind())) {
                                IPath relativePath = SpecialFolderUtil
                                        .getSpecialFolderRelativePath(resource,
                                                sf.getKind());
                                found = relativePath
                                        .equals(proxy.getFullPath());
                            }

                        } else if (resource.getProjectRelativePath()
                                .equals(proxy.getFullPath())) {
                            found = true;
                        }

                    }
                }
            }
            return !found;
        }

    }
}
