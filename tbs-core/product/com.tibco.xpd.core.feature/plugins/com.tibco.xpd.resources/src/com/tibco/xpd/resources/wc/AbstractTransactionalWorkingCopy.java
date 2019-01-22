/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.TriggeredOperations;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.emf.workspace.ResourceUndoContext;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;

import com.tibco.xpd.resources.TransactionalWorkingCopyImpl;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdConfigOption;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Abstract implementation of {@link WorkingCopy} that utilizes a transactional
 * editing domain.
 * <p>
 * It is recommended that access of the transactional editing domain's
 * resourceSet should be done through a read-only transaction. Therefore, any
 * loading and saving of resources should use
 * <code>((TransactionalEditingDomain)getEditingDomain()).runExclusive(Runnable runnable);</code>
 * </p>
 * <p>
 * Since 3.1 a save can be done using a read-write transaction - see method
 * {@link #setUseWriteTransactionToSave(boolean) setUseWriteTransactionToSave}
 * for more details.
 * </p>
 * <p>
 * Since 3.2 a default implementation of {@link #doLoadModel()} is provided.
 * Please see the method for more details. A default implementation of this
 * abstract class is also provided - {@link TransactionalWorkingCopyImpl} -
 * which can be used as is or extended for a more specific
 * <code>WorkingCopy</code> requirements.
 * </p>
 * 
 * @see TransactionalEditingDomain#runExclusive(Runnable)
 * @see TransactionalWorkingCopyImpl
 * 
 * @since 3.0
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractTransactionalWorkingCopy extends
        AbstractWorkingCopy implements TransactionalWorkingCopy {

    private final WorkingCopyResourceSetListener listener;

    /* XPD-1140: use base type. */
    private IOperationHistoryListener opHistoryListener;

    private boolean isDirty = false;

    private SaveContext lastSaveContext;

    private final IResourceChangeListener resourceChangeListener;

    /**
     * Set to <code>true</code> if a write transaction should be used to save
     * this resource
     */
    private boolean useWriteTransactionOnSave = false;

    private IUndoContext undoContext;

    /**
     * The Constructor.
     * 
     * @param resources
     *            set of resources for this working copy
     */
    public AbstractTransactionalWorkingCopy(List<IResource> resources) {
        super(resources);

        listener = new WorkingCopyResourceSetListener();

        /*
         * XPD-1140: Allow subclass to decide not to listen for operation
         * history.
         */
        opHistoryListener = createOperationHistoryListener();
        if (opHistoryListener != null) {
            IOperationHistory history = getOperationHistory();
            if (history != null) {
                history.addOperationHistoryListener(opHistoryListener);
            }
        }

        // Resource listener to re-resolve references if dependent model
        // added/moved
        resourceChangeListener = createAddResourceListener();

        /*
         * XPD-1140: Allow subclass to decide not to listen for resource
         * changes.
         */
        if (resourceChangeListener != null) {
            ResourcesPlugin.getWorkspace()
                    .addResourceChangeListener(resourceChangeListener,
                            IResourceChangeEvent.POST_BUILD);
        }
    }

    /**
     * Load the model from the resource managed by this working copy. This loads
     * the eclipse resource into the global resource set and calls
     * {@link #getModelFromResource(Resource)} to load the model from the
     * resource.
     * <p>
     * Subclasses may override this method if they wish to tailor the loading of
     * the resource or simply override {@link #getModelFromResource(Resource)}
     * if the first object in the resource is not the model.
     * </p>
     * 
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doLoadModel()
     * @see #getModelFromResource(Resource)
     * @see #loadResource(IResource)
     * @see #unloadResource(Resource)
     * 
     * @since 3.2
     */
    @Override
    protected EObject doLoadModel() throws InvalidFileException {
        IResource resource = getFirstResource();
        EObject eo = null;
        if (resource != null) {
            Resource res = null;
            try {
                res = loadResource(resource);
            } catch (Exception e) {
                res = null;
                if (e instanceof InvalidFileException) {
                    throw (InvalidFileException) e;
                }

                throw new InvalidFileException(e);
            }

            if (res != null) {
                eo = getModelFromResource(res);

                // If model was not loaded then throw exception and unload the
                // resource if loaded
                if (eo == null) {
                    if (res != null) {
                        try {
                            unloadResource(res);
                        } catch (InterruptedException e) {
                            throw new InvalidFileException(e.getCause());
                        }
                    }
                    throw new InvalidFileException();
                }
            }
        }
        return eo;
    }

    /**
     * Load the first element from the the resource's
     * {@link Resource#getContents() contents}. Subclasses may override if the
     * model is not the first item in the content list.
     * 
     * @param res
     *            {@link Resource}
     * @return first {@link EObject element} in the resource's contents. Should
     *         return <code>null</code> if the underlying model is invalid.
     * @since 3.2
     */
    protected EObject getModelFromResource(Resource res) {
        if (res != null && res.getContents() != null
                && !res.getContents().isEmpty()) {
            return res.getContents().get(0);
        }
        return null;
    }

    /**
     * Load the given resource into the global resource set (all resources that
     * are loaded into the global resourceset will be done using a read-write
     * transaction).
     * 
     * @see #unloadResource(Resource)
     * 
     * @param resource
     *            {@link IResource} to load
     * @return {@link Resource} if loaded successfully, <code>null</code>
     *         otherwise.
     * @throws InvalidFileException
     * @throws InterruptedException
     * @since 3.2
     */
    protected Resource loadResource(IResource resource)
            throws InvalidFileException {
        Resource res = null;
        EditingDomain ed = getEditingDomain();
        if (resource != null && resource.exists() && ed != null) {
            URI uri =
                    URI.createPlatformResourceURI(resource.getFullPath()
                            .toString(), true);
            try {
                setIgnoreResourceChanges(true);

                // SCF-168: Unload invalid resource, if exists
                Resource oldResource =
                        ed.getResourceSet().getResource(uri, false);

                if (oldResource != null && oldResource.isLoaded()) {
                    /*
                     * Check if errors are actual error severity, don't worry
                     * about warnings else we'll keep reloading the resource
                     * over and over.
                     * 
                     * Only then do we need to force an unload of a previously
                     * invalid resource.
                     */
                    EList<Diagnostic> errors = oldResource.getErrors();
                    if (errors != null && !errors.isEmpty()) {
                        IStatus status = getErrorStatus(errors, resource);

                        if (status.getSeverity() == IStatus.ERROR) {
                            try {
                                /*
                                 * XPD-204 Remove Old resource (used to remove
                                 * res which was always null at this point)
                                 */
                                unloadResource(oldResource);
                            } catch (InterruptedException e) {
                                // Do nothing as we are unloading resource
                                // after error
                            }
                        }
                    }
                }
                res = ed.loadResource(uri.toString());

                if (res != null) {
                    // Report errors if any
                    EList<Diagnostic> errors = res.getErrors();
                    if (errors != null && !errors.isEmpty()) {
                        IStatus status = getErrorStatus(errors, resource);
                        if (status != null) {
                            if (!status.isOK()) {

                                logErrorMessage(status);
                            }

                            if (status.getSeverity() == IStatus.ERROR) {
                                // Error reported so unload the resource and
                                // throw exception
                                try {
                                    unloadResource(res);
                                    res = null;
                                } catch (InterruptedException e) {
                                    // Do nothing as we are unloading resource
                                    // after error
                                }
                                throw new InvalidFileException(
                                        new CoreException(status));
                            }
                        }
                    }
                    // Report any warnings
                    EList<Diagnostic> warnings = res.getWarnings();

                    if (warnings != null && !warnings.isEmpty()) {
                        logWarnings(warnings, resource);
                    }
                }

            } finally {
                setIgnoreResourceChanges(false);
            }
        }
        return res;
    }

    /**
     * Provides the default implementation of logging the message.
     * Implementation classes have the choice of logging the message based on
     * the problem/exception
     * 
     * @param status
     *            - {@link Status} object that has the error message to log
     */
    protected void logErrorMessage(IStatus status) {

        XpdResourcesPlugin.getDefault().getLogger().log(status);
    }

    /**
     * Get the error {@link IStatus Status} from the errors reported during the
     * loading of this resource. The default implementation will create an ERROR
     * status for each {@link Diagnostic} error reported. Subclasses may
     * override if they wish to analyze the errors before reporting the status.
     * 
     * @param errors
     *            Errors reported during the load of the resource
     * @param resource
     *            resource being loaded
     * @return error Status - if it's ERROR level then the resource will be
     *         unloaded, WARNING and INFO will be reported in the logs and the
     *         resource will not be unloaded
     * @since 3.3
     */
    protected IStatus getErrorStatus(EList<Diagnostic> errors,
            IResource resource) {
        List<IStatus> status = new ArrayList<IStatus>();
        for (Diagnostic error : errors) {
            status.add(new Status(IStatus.ERROR, XpdResourcesPlugin.ID_PLUGIN,
                    error.getMessage(), null));
        }

        return new MultiStatus(
                XpdResourcesPlugin.ID_PLUGIN,
                0,
                status.toArray(new IStatus[status.size()]),
                String.format(Messages.AbstractTransactionalWorkingCopy_errorDuringLoad_message,
                        resource.getFullPath().toString()), null);
    }

    /**
     * Log the warnings received during the loading of the resource.
     * 
     * @param warnings
     * @param resource
     */
    private void logWarnings(EList<Diagnostic> warnings, IResource resource) {
        List<IStatus> status = new ArrayList<IStatus>();
        for (Diagnostic warning : warnings) {
            status.add(new Status(IStatus.WARNING,
                    XpdResourcesPlugin.ID_PLUGIN, warning.getMessage(), null));
        }

        String message =
                String.format(Messages.AbstractTransactionalWorkingCopy_warningsDuringLoad_message,
                        resource.getFullPath().toString());
        XpdResourcesPlugin
                .getDefault()
                .getLogger()
                .log(new MultiStatus(XpdResourcesPlugin.ID_PLUGIN, 0,
                        status.toArray(new IStatus[status.size()]), message,
                        null));
    }

    /**
     * Unload the given resource and remove it from the global resource set.
     * NOTE: Please don't call this method directly, instead use
     * {@link AbstractWorkingCopy#unloadResource(Resource)} as it will ensure
     * appropriate locking. This runs in a read-only transaction.
     * 
     * @see #doUnloadResource(Resource)
     * @see #loadResource(IResource)
     * 
     * @param res
     *            {@link Resource} to unload
     * @throws InterruptedException
     * @since 3.5.20
     */
    @Override
    protected void doUnloadResource(final Resource res)
            throws InterruptedException {
    	
    	final EditingDomain ed = getEditingDomain();
        
        if (res != null && ed instanceof TransactionalEditingDomain) {
        	Transaction transaction = ((InternalTransactionalEditingDomain) ed).getActiveTransaction();
        
        	// (AMBW-26642, AMBW-26114 , AMBW-25784) If the active transaction is in write-transaction, unload the resource without starting another
    		// write transaction to avoid deadlock.
        
			if ( XpdConfigOption.IN_MEMORY_INDEX_DB.isOn() && transaction != null && !transaction.isReadOnly()) {
				res.unload();
				ed.getResourceSet().getResources().remove(res);
				return;
				
			}else{
		        ((TransactionalEditingDomain) ed).runExclusive(new Runnable() {
	                @Override
	                public void run() {
	                    res.unload();
	                    ed.getResourceSet().getResources().remove(res);
	                }
	            });
			}
        }
    }

    /**
     * Set whether to use a write transaction to save this model. This should be
     * used when there is a chance this model, or a referenced model, will be
     * updated during the save. By default a read-only transaction is used.
     * 
     * @param useWriteTransaction
     *            <code>true</code> to use read-write transaction, otherwise
     *            read-only transaction will be used to save.
     * @since 3.1
     */
    protected void setUseWriteTransactionToSave(boolean useWriteTransaction) {
        this.useWriteTransactionOnSave = useWriteTransaction;
    }

    /**
     * Create editing domain for given EditingDomainID, or return already
     * existing one. If created, the resource set is configured with adapter
     * factory.
     * 
     * @return TransactionalEditingDomain
     */
    @Override
    protected EditingDomain createEditingDomain() {
        String domainId = getEditingDomainID();
        TransactionalEditingDomain result = null;

        if (domainId != null) {
            result =
                    TransactionalEditingDomain.Registry.INSTANCE
                            .getEditingDomain(domainId);

            if (result == null) {
                throw new IllegalArgumentException(
                        String.format(Messages.AbstractTransactionalWorkingCopy_noEditingDomainFound_message,
                                domainId));
            }
        } else {
            throw new NullPointerException(
                    Messages.AbstractTransactionalWorkingCopy_editingDomainIsNull_message);
        }

        return result;
    }

    @Override
    protected AdapterFactory createAdapterFactory() {
        AdapterFactory adapterFactory = null;
        EditingDomain ed = getEditingDomain();

        if (ed instanceof AdapterFactoryEditingDomain) {
            adapterFactory =
                    ((AdapterFactoryEditingDomain) ed).getAdapterFactory();
        }

        return adapterFactory;
    }

    @Override
    public boolean isWorkingCopyDirty() {
        return isDirty;
    }

    @Override
    protected void cleanup() {
        Collection<Resource> resources = getResources();

        // Flush the operational history
        IOperationHistory history = getOperationHistory();
        if (history != null && undoContext != null) {
            history.dispose(undoContext, true, true, true);
            undoContext = null;
        }

        /*
         * Let the super method unregister adapters before we unload the
         * resource.
         */
        super.cleanup();

        // Unload the resources
        if (resources != null) {
            setIgnoreResourceChanges(true);
            try {
                for (Resource resource : resources) {
                    unloadResource(resource);
                }
            } catch (InterruptedException e) {
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("Problem unloading resource from working copy for '%s'", //$NON-NLS-1$
                                        getName()));
            } finally {
                setIgnoreResourceChanges(false);
            }
        }

        // Reset the dirty flag
        isDirty = false;
    }

    /**
     * Get the operational history from the editing domain's command stack.
     * 
     * @return <code>IOperationHistory</code> if available, <code>null</code>
     *         otherwise.
     */
    protected IOperationHistory getOperationHistory() {
        IOperationHistory history = null;

        if (getEditingDomain() != null
                && getEditingDomain().getCommandStack() instanceof IWorkspaceCommandStack) {
            history =
                    ((IWorkspaceCommandStack) getEditingDomain()
                            .getCommandStack()).getOperationHistory();
        }

        return history;
    }

    @Override
    public void dispose() {

        if (listener != null) {
            ((TransactionalEditingDomain) getEditingDomain())
                    .removeResourceSetListener(listener);
        }

        if (opHistoryListener != null) {
            IOperationHistory history = getOperationHistory();

            if (history != null) {
                history.removeOperationHistoryListener(opHistoryListener);
            }
        }

        if (resourceChangeListener != null) {
            ResourcesPlugin.getWorkspace()
                    .removeResourceChangeListener(resourceChangeListener);
        }

        super.dispose();
    }

    /**
     * Change the behaviour to ignore notifications from command stack and
     * listen to OperationHistory.
     * 
     * @param ed
     *            transactional editing domain
     */
    @Override
    protected void registerEditingDomainListeners(EditingDomain ed) {
        ((TransactionalEditingDomain) ed).addResourceSetListener(listener);
    }

    /**
     * Create and register operation history listener
     */
    protected IOperationHistoryListener createOperationHistoryListener() {
        WorkingCopyOpHistoryListener opHistoryListener =
                new WorkingCopyOpHistoryListener();
        return opHistoryListener;
    }

    /**
     * Undo context of the operation history.
     * 
     * @return <code>ResourceUndoContext</code> if the resource is available,
     *         <code>null</code> otherwise.
     */
    @Override
    public IUndoContext getUndoContext() {
        if (undoContext == null) {
            Collection<Resource> resources = getResources();
            if (resources != null && resources.size() > 0) {
                Resource resource = resources.iterator().next();

                if (resource != null) {
                    undoContext =
                            new ResourceUndoContext(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    resource);
                }
            }
        }

        return undoContext;
    }

    /**
     * Provide the transactional editing domain ID for this WorkingCopy. Default
     * implementation will return the shared default transactional editing
     * domain id. Subclasses may override if a different editing domain is
     * required.
     * 
     * @return editing domain ID for this working copy
     */
    protected String getEditingDomainID() {
        return XpdConsts.EDITING_DOMAIN_ID;
    }

    /**
     * Save the resource.
     * <p>
     * Since 3.2 this will use a read-write transaction for save if
     * {@link #setUseWriteTransactionToSave(boolean) use write transaction to
     * save} is set.
     * </p>
     * 
     * @throws IOException
     *             forwarded exception from {@link Resource#save(Map)
     *             Resource.save} method.
     */
    @Override
    protected void doSave() throws IOException {
        Collection<Resource> resources = getResources();

        if (resources != null && resources.size() > 0) {

            SaveRunnable runnable = new SaveRunnable(resources);
            Transaction tx = null;
            try {
                if (!useWriteTransactionOnSave) {
                    // Use read-only transaction to save
                    ((TransactionalEditingDomain) getEditingDomain())
                            .runExclusive(runnable);
                } else {
                    // Use read-write transaction to save
                    tx =
                            ((InternalTransactionalEditingDomain) getEditingDomain())
                                    .startTransaction(false, null);

                    runnable.run();
                }
            } catch (InterruptedException e) {
                throw new IOException(e.getLocalizedMessage());
            } finally {
                if (tx != null) {
                    // Commit transaction
                    try {
                        tx.commit();
                    } catch (RollbackException e) {
                        throw new IOException(e.getLocalizedMessage());
                    }
                }
            }

            // Add save context
            if (runnable.getStatus().isOK()) {
                isDirty = false;
                addSaveContext();
            } else {
                throw new IOException(runnable.getStatus().getException()
                        .getLocalizedMessage());
            }
        }
    }

    /**
     * Add save undo context to the last command in the stack when the resource
     * is saved. This will act as a marker of the last save point in the command
     * stack. This save context will be used to determine the dirty state of the
     * resource when undo/redo is called.
     */
    private void addSaveContext() {
        IOperationHistory history = getOperationHistory();
        IUndoContext undoContext = getUndoContext();
        if (history != null && undoContext != null) {
            IUndoableOperation operation =
                    history.getUndoOperation(undoContext);

            // Clear last save context
            lastSaveContext = null;

            if (operation != null) {
                // Create new save context
                lastSaveContext = new SaveContext();
                operation.addContext(lastSaveContext);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#getResources()
     */
    @Override
    protected Collection<Resource> getResources() {
        // Get the resource if the model is loaded
        if (isLoaded()) {
            EObject element = getRootElement();

            if (element != null) {
                Resource resource = element.eResource();

                if (resource != null) {
                    return Collections.singletonList(resource);
                }
            }
        }

        return null;
    }

    @Override
    protected final void installEContentAdapter(Resource res) {
        /*
         * Override the super method as this working copy uses the transactional
         * editing domain and it does not need the EContentAdapter to gather the
         * resource change notifications - this is provided by the resource set
         * listener.
         */
    }

    @Override
    protected final Collection<Notification> getNotifications(EventObject event) {
        if (event instanceof ResourceSetChangeEvent) {
            List<Notification> notifications =
                    ((ResourceSetChangeEvent) event).getNotifications();
            /*
             * XPD-4168: Return a copy of the notifications to avoid any
             * concurrent modification exceptions
             */
            if (notifications != null) {
                return new ArrayList<Notification>(notifications);
            } else {
                return new ArrayList<Notification>(0);
            }
        }

        return super.getNotifications(event);
    }

    /**
     * Get the notification filters to apply to the resource set listener.
     * <p>
     * Default implementation filters for notifiers of type
     * <code>Resource</code> or <code>EObject</code>. Subclasses may add to the
     * filter list.
     * </p>
     * 
     * @return array of <code>INotificationFilter</code>s that will determine
     *         whether a notification will be processed. If this is
     *         <code>null</code> then no notifications will be processed and if
     *         this is an empty array then all notifications will be processed.
     */
    protected INotificationFilter[] getNotificationFilters() {
        INotificationFilter[] filter =
                new INotificationFilter[] { new INotificationFilter() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @seecom.tibco.xpd.resources.wc.
                     * AbstractTransactionalWorkingCopy. INotificationFilter
                     * #matches(org.eclipse.emf.common.notify.Notification)
                     */
                    @Override
                    public boolean matches(Notification notification) {
                        Object notifier = notification.getNotifier();

                        return notifier instanceof Resource
                                || notifier instanceof EObject;
                    }
                } };

        return filter;
    }

    /**
     * Calculate the broken references from the model (searches for proxy
     * elements).
     * 
     * @return Collection of paths to the resources.
     */
    protected Collection<IPath> getBrokenReferences() {
        Set<IPath> result = new HashSet<IPath>();

        List<IResource> eclipseResources = getEclipseResources();

        if (eclipseResources != null && !eclipseResources.isEmpty()) {
            IResource res = eclipseResources.get(0);
            try {
                IMarker[] markers =
                        res.findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                                true,
                                IResource.DEPTH_ZERO);

                if (markers != null) {
                    for (IMarker marker : markers) {
                        Properties prop = getAdditionalInfo(marker);

                        if (prop != null) {
                            String path =
                                    prop.getProperty(XpdConsts.VALIDATION_BROKEN_REF_INFO_KEY);
                            if (path != null) {
                                result.add(new Path(path));
                            }
                        }
                    }
                }

            } catch (CoreException e) {
                // Do nothing
            }
        }

        return result;
    }

    /**
     * Get the special folder relative path. This will use the special folder
     * file binding information to determine the special folder of the given
     * file. If this file is not contained in a special folder then this will
     * return project relative path.
     * 
     * @param file
     *            file to get relative path of
     * @return relative path.Os
     */
    private IPath getRelativePath(IFile file) {
        return SpecialFolderUtil.getSpecialFolderRelativePath(file);
    }

    /**
     * Create the add resource listener that will re-resolve references when a
     * dependent resource is moved or added.
     * 
     * @return workspace resource change listener.
     */
    protected IResourceChangeListener createAddResourceListener() {
        return new IResourceChangeListener() {

            private boolean doFireDepencyEvent;

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.core.resources.IResourceChangeListener#resourceChanged
             * (org.eclipse.core.resources.IResourceChangeEvent)
             */
            @Override
            public void resourceChanged(final IResourceChangeEvent event) {
                doFireDepencyEvent = false;
                /*
                 * XPD-1128: May not have an actual resource backing a working
                 * copy created from temporary input stream (for things like
                 * history revisions)
                 */
                if (!getEclipseResources().isEmpty()) {

                    final IResource eclipseRes = getEclipseResources().get(0);
                    if (eclipseRes != null) {
                        // Only interested in referenced projects
                        final Set<IProject> referencedProjects =
                                new HashSet<IProject>();
                        referencedProjects.add(eclipseRes.getProject());
                        ProjectUtil.getReferencedProjectsHierarchy(eclipseRes
                                .getProject(), referencedProjects);

                        try {
                            event.getDelta()
                                    .accept(new IResourceDeltaVisitor() {

                                        @Override
                                        public boolean visit(
                                                IResourceDelta delta)
                                                throws CoreException {

                                            // If dependency event already fired
                                            // then no
                                            // point checking any further
                                            if (doFireDepencyEvent) {
                                                return false;
                                            }

                                            IResource resource =
                                                    delta.getResource();

                                            if (resource instanceof IProject
                                                    || delta.getKind() == IResourceDelta.ADDED) {
                                                if (resource instanceof IProject) {
                                                    // If referenced project was
                                                    // opened then
                                                    // fire dependency changed
                                                    if ((delta.getFlags() & IResourceDelta.OPEN) != 0
                                                            && resource
                                                                    .isAccessible()
                                                            && !resource
                                                                    .equals(eclipseRes
                                                                            .getProject())
                                                            && referencedProjects
                                                                    .contains(resource)) {
                                                        doFireDepencyEvent =
                                                                true;
                                                        return false;
                                                    }

                                                    // Continue with delta if
                                                    // referenced
                                                    // project
                                                    return referencedProjects
                                                            .contains(resource);
                                                } else if (resource instanceof IFile) {
                                                    boolean shouldReResolve =
                                                            false;
                                                    /*
                                                     * Check if a dependent
                                                     * resource was moved
                                                     */
                                                    if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0
                                                            && delta.getMovedFromPath() != null) {
                                                        IPath path =
                                                                delta.getMovedFromPath();

                                                        List<IResource> dependency =
                                                                getDependencyHandles();

                                                        for (IResource res : dependency) {
                                                            /*
                                                             * Should re-resolve
                                                             * this model if a
                                                             * dependency has
                                                             * been moved or
                                                             * restored
                                                             */
                                                            if (shouldReResolve =
                                                                    (res.getFullPath()
                                                                            .equals(path) || (resource
                                                                            .exists() && res
                                                                            .equals(resource)))) {
                                                                break;
                                                            }
                                                        }
                                                    }

                                                    if (!shouldReResolve) {
                                                        /*
                                                         * Check broken
                                                         * references to see if
                                                         * this resource is the
                                                         * one that resolves the
                                                         * broken refs
                                                         */
                                                        IPath relativePath =
                                                                getRelativePath((IFile) resource);
                                                        if (relativePath != null) {
                                                            Collection<IPath> brokenReferences =
                                                                    getBrokenReferences();

                                                            if (brokenReferences
                                                                    .contains(relativePath)) {
                                                                shouldReResolve =
                                                                        true;
                                                            }
                                                        }
                                                    }

                                                    if (shouldReResolve) {
                                                        doFireDepencyEvent =
                                                                true;
                                                    }
                                                    return false;
                                                }
                                            }
                                            return true;
                                        }

                                    });
                        } catch (CoreException e) {
                            XpdResourcesPlugin.getDefault().getLogger()
                                    .error(e);
                        }
                    }

                    if (doFireDepencyEvent) {
                        if (!XpdResourcesPlugin.isInHeadlessMode()) {
                            XpdResourcesPlugin.getStandardDisplay()
                                    .asyncExec(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                dependencyChanged(event);
                                            } catch (CoreException e) {
                                                XpdResourcesPlugin.getDefault()
                                                        .getLogger().error(e);
                                            }
                                        }
                                    });
                        } else {
                            try {
                                dependencyChanged(event);
                            } catch (CoreException e) {
                                XpdResourcesPlugin.getDefault().getLogger()
                                        .error(e);
                            }
                        }
                    }
                }
            }

            private void dependencyChanged(IResourceChangeEvent event)
                    throws CoreException {
                // Re-resolve this resource
                Collection<Resource> emfResources = getResources();

                if (emfResources != null && !emfResources.isEmpty()) {
                    XpdResourcesPlugin.getDefault()
                            .revalidateReferences(emfResources.iterator()
                                    .next());

                    doSaveDependencyCache();
                    fireDependencyChange(event);
                }

            }

        };
    }

    /**
     * Resource set listener.
     * 
     * @author njpatel
     * 
     */
    protected class WorkingCopyResourceSetListener extends
            ResourceSetListenerImpl {

        private final INotificationFilter[] filters;

        /**
         * Constructor.
         */
        public WorkingCopyResourceSetListener() {
            filters = getNotificationFilters();
        }

        @Override
        public boolean isPostcommitOnly() {
            // Only interested in post-commit notifications
            return true;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {

            if (isIgnoreResourceChanges() || !isLoaded()) {
                return;
            }

            List<?> notifications = event.getNotifications();

            if (notifications != null) {
                Collection<Resource> resources = getResources();

                if (resources != null && !resources.isEmpty()) {
                    Resource resource = resources.iterator().next();

                    for (Object next : notifications) {
                        Notification notification = (Notification) next;

                        if (!notification.isTouch() && filter(notification)) {
                            Resource src = null;

                            // Get the affected resource
                            if (notification.getNotifier() instanceof Resource) {
                                src = (Resource) notification.getNotifier();
                            } else if (notification.getNotifier() instanceof EObject) {
                                src =
                                        ((EObject) notification.getNotifier())
                                                .eResource();
                            }

                            if (resource == src) {
                                if (src.isLoaded()) {
                                    // Indicate resource has changed
                                    commandStackChanged(event);
                                } else {
                                    // Resource has been unloaded so cleanup and
                                    // fire change event
                                    cleanup();
                                    fireWCChanged();
                                }

                                break;
                            }
                        }
                    }
                }
            }
        }

        /**
         * Determine whether this notification should be processed based on the
         * notification filters set.
         * 
         * @param notification
         *            resource set notification
         * @return <code>true</code> if notification should be processed,
         *         <code>false</code> otherwise.
         */
        private boolean filter(Notification notification) {
            boolean process = false;

            if (filters != null) {
                process = true;

                for (INotificationFilter filter : filters) {
                    if (!(process = filter.matches(notification))) {
                        // Notification filtered out
                        break;
                    }
                }
            }

            return process;
        }
    }

    /**
     * Notification filter. This will be used by the resource set listener to
     * determine if a notification should be processed.
     * 
     * @author njpatel
     * 
     */
    protected interface INotificationFilter {
        /**
         * Determine whether the notification should be processed.
         * 
         * @param notification
         * @return <code>true</code> if notification should be processed,
         *         <code>false</code> otherwise.
         */
        public boolean matches(Notification notification);
    }

    /**
     * Implementation of the <code>RunnableWithResult</code> to save the
     * resources managed by this working copy.
     * 
     * @author njpatel
     * 
     */
    private class SaveRunnable extends RunnableWithResult.Impl<Resource> {

        private final Collection<Resource> resources;

        /**
         * Constructor.
         * 
         * @param resources
         *            array of resources to save.
         */
        public SaveRunnable(Collection<Resource> resources) {
            this.resources = resources;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            if (resources != null) {
                IStatus status =
                        new Status(
                                IStatus.OK,
                                XpdResourcesPlugin.ID_PLUGIN,
                                0,
                                Messages.AbstractTransactionalWorkingCopy_savedResources_message,
                                null);
                try {
                    for (Resource resource : resources) {
                        resource.save(null);
                    }
                } catch (IOException e) {
                    status =
                            new Status(IStatus.ERROR,
                                    XpdResourcesPlugin.ID_PLUGIN, 0,
                                    e.getLocalizedMessage(), e.getCause());
                } finally {
                    setStatus(status);
                }
            }
        }
    }

    /**
     * Check if the given object is an instance of class
     * AbstractWorkspaceOperation. This method is used instead of instanceof
     * because instantiating AbstractWorkspaceOperation causes the UI to load
     * and this is causing problems for CLIs.
     * 
     * @param o
     * @return
     * @since 3.6
     */
    private boolean isAbstractWorkspaceOperationInstance(Object o) {
        Class<?> c = o.getClass();
        while (c != null) {
            if ("org.eclipse.ui.ide.undo.AbstractWorkspaceOperation". //$NON-NLS-1$
                    equals(c.getCanonicalName())) {
                return true;
            }

            c = c.getSuperclass();
        }

        return false;
    }

    /**
     * Operational history listener that will determine the dirty state of this
     * resource.
     * 
     * @author njpatel
     * 
     */
    private class WorkingCopyOpHistoryListener implements
            IOperationHistoryListener {

        @Override
        public void historyNotification(OperationHistoryEvent event) {
            boolean dirtyState = isDirty;
            IUndoableOperation operation = event.getOperation();

            // If this is a workspace operation (create, move, etc of a
            // resource) then ignore
            if (isAbstractWorkspaceOperationInstance(operation)) {
                return;
            }

            /*
             * If this is a triggered operation then get the underlying
             * operation that triggered it. This type of command is typically
             * used when changes are made to the advanced properties page of a
             * model being edited in a GMF-based editor.
             */
            if (operation instanceof TriggeredOperations) {
                operation =
                        ((TriggeredOperations) operation)
                                .getTriggeringOperation();
            }

            IUndoContext undoContext = getUndoContext();

            if (operation != null && undoContext != null) {
                if (event.getEventType() == OperationHistoryEvent.DONE) {
                    if (hasContext(operation, undoContext)) {
                        // This resource has been changed
                        dirtyState = true;
                    }
                } else if (event.getEventType() == OperationHistoryEvent.UNDONE) {
                    IOperationHistory history = event.getHistory();

                    IUndoableOperation undoOperation =
                            history.getUndoOperation(undoContext);

                    if (undoOperation != null) {
                        // If undone to last save point then resource is not
                        // dirty
                        dirtyState =
                                lastSaveContext != null ? !(undoOperation
                                        .hasContext(lastSaveContext)) : true;
                    } else if (lastSaveContext != null) {
                        /*
                         * A save has happened on this context so this must be
                         * dirty as the resource has 'undone' to a point before
                         * the last save point
                         */
                        dirtyState = true;
                    } else {
                        /*
                         * No more undos and no saves have happened in this
                         * resource so not dirty
                         */
                        dirtyState = false;
                    }
                } else if (event.getEventType() == OperationHistoryEvent.REDONE) {
                    if (operation.hasContext(undoContext)) {
                        // If redone to the last save point then resource is not
                        // dirty, else it is
                        dirtyState =
                                !(lastSaveContext != null && operation
                                        .hasContext(lastSaveContext));
                    }
                }

                /*
                 * Sometimes a command is executed that have the undo context of
                 * this resource but this resource hasn't been modified.
                 * Therefore, this extra check has been added so as not to
                 * report a dirty state when resource hasn't changed. This is
                 * temp. fix as it would be better to investigate why this
                 * resource's undo context was added in the first place.
                 */
                if (dirtyState) {
                    Collection<Resource> resources = getResources();

                    if (resources != null && resources.size() > 0
                            && !resources.iterator().next().isModified()) {
                        dirtyState = false;
                    }
                }

                // If dirty state has changed then notify listeners
                if (isDirty != dirtyState) {
                    isDirty = dirtyState;
                    fireWCDirtyFlagChanged();
                }
            }
        }
    }

    /**
     * Check if the given operation has the context. If this is a composite
     * command then all children will be checked if any has the given context.
     * 
     * @param operation
     * @param context
     * @return
     */
    private boolean hasContext(IUndoableOperation operation,
            IUndoContext context) {
        if (operation != null && context != null) {
            /*
             * If operation has the context then return true, otherwise if a
             * composite command then check all children
             */
            if (operation.hasContext(context)) {
                return true;
            } else if (operation instanceof ICompositeCommand) {
                ListIterator<?> iterator =
                        ((ICompositeCommand) operation).listIterator();
                while (iterator.hasNext()) {
                    Object next = iterator.next();
                    if (next instanceof IUndoableOperation) {
                        if (hasContext((IUndoableOperation) next, context)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the additional info from the given marker.
     * 
     * @param marker
     * @return
     */
    private Properties getAdditionalInfo(IMarker marker) {
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
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        } catch (IOException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return properties;
    }

    /**
     * An undo context used to mark the save point in the command stack.
     * 
     * @author njpatel
     * 
     */
    private class SaveContext implements IUndoContext {

        @Override
        public String getLabel() {
            return "Save Context"; //$NON-NLS-1$
        }

        @Override
        public boolean matches(IUndoContext context) {
            return this == context;
        }
    }
}
