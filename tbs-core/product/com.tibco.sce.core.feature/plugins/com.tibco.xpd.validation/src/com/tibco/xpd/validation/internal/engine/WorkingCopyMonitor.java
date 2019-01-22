/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.StrictCompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.OverrideableCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.resources.IWorkingCopyCreationListener;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * Working copy monitor. This will listen for working copy creation and when a
 * working copy is created it will start listening to changes. When a working
 * copy is changed it will be added to the validation queue.
 * 
 * @author njpatel
 */
public class WorkingCopyMonitor implements PropertyChangeListener,
        IWorkingCopyCreationListener {

    /** The builder ID. */
    private static final String BUILDER_ID = ValidationActivator.PLUGIN_ID
            + ".validationBuilder"; //$NON-NLS-1$

    /** The validation queue. */
    private ValidationQueue queue;

    /** Supported file extensions. */
    private IFileFilter filter;

    /** Set of registered working copies. */
    private Set<WorkingCopy> registeredWorkingCopies =
            new HashSet<WorkingCopy>();

    /** Register working copy for validation */
    private Set<WorkingCopy> registerWorkingCopyForValidation =
            new HashSet<WorkingCopy>();

    /**
     * Default constructor.
     */
    public WorkingCopyMonitor() {
        filter = ValidationManager.getInstance().getFileFilter();
        queue = ValidationManager.getInstance().getValidationQueue();

        // Listen to creation of working copies
        XpdResourcesPlugin.getDefault().addWorkingCopyCreationListener(this,
                true);
    }

    /**
     * Dipose off all resources.
     */
    public void dispose() {
        XpdResourcesPlugin.getDefault().removeWorkingCopyCreationListener(this);

        for (WorkingCopy wc : registeredWorkingCopies) {
            if (wc != null) {
                wc.removeListener(this);
            }
        }

        registeredWorkingCopies.clear();
    }

    /**
     * Add the working copy to local cache and start listening to it's property
     * changes.
     * 
     * @param wc
     */
    private void addWorkingCopy(WorkingCopy wc, boolean registerForValidation) {
        if (wc != null && !registeredWorkingCopies.contains(wc)) {
            wc.addListener(this);
            registeredWorkingCopies.add(wc);
            if (registerForValidation) {
                registerWorkingCopyForValidation.add(wc);
            }
        }
    }

    /**
     * Remove working copy from local cache and stop listening to it's property
     * changes.
     * 
     * @param wc
     */
    private void removeWorkingCopy(WorkingCopy wc) {
        if (wc != null && registeredWorkingCopies.contains(wc)) {
            wc.removeListener(this);
            registeredWorkingCopies.remove(wc);
            registerWorkingCopyForValidation.remove(wc);
        }
    }

    /**
     * @param ev
     *            The event.
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent ev) {
        Object source = ev.getSource();
        if (source instanceof WorkingCopy
                && !(source instanceof ProjectConfigWorkingCopy)) {
            WorkingCopy wc = (WorkingCopy) source;
            TRACE(String
                    .format("Property change event '%s' received from working copy '%s'", //$NON-NLS-1$
                            ev.getPropertyName(),
                            wc.getName()));

            /*
             * If a working copy is being removed then stop listening to it.
             */
            if (WorkingCopy.PROP_REMOVED.equals(ev.getPropertyName())) {
                removeWorkingCopy(wc);
            }

            // If this event happens during a project import then ignore it
            if (XpdResourcesPlugin.getDefault().isProjectsImportInProgress()
                    || isProjectMigrationInProgress(wc)) {
                return;
            }

            /*
             * Index the working copy
             */
            TRACE("Calling indexer..."); //$NON-NLS-1$
            IndexerServiceImpl service = getIndexerService();
            if (service != null) {
                service.processWorkingCopyPropertyChange(ev);
            }
            TRACE("Returned from indexer"); //$NON-NLS-1$

            if (!WorkingCopy.PROP_REMOVED.equals(ev.getPropertyName())
                    && shouldValidate(wc)) {
                if (registerWorkingCopyForValidation.contains(wc)
                        && !isBuilderDisabled(wc) && !isAutoBuildDisabled()) {
                    // If this working copy is registered for validation then
                    // run validation
                    TRACE("Preparing for live validation"); //$NON-NLS-1$

                    if (!wc.isInvalidFile()
                            && (WorkingCopy.CHANGED
                                    .equals(ev.getPropertyName()) || WorkingCopy.PROP_DIRTY
                                    .equals(ev.getPropertyName()))) {

                        // Get set of the affected objects
                        Set<EObject> affectedObjects =
                                getAffectedObjects(ev, wc);
                        if (!affectedObjects.isEmpty()) {
                            Collection<Notification> notifications = null;

                            if (ev instanceof NotificationPropertyChangeEvent) {
                                notifications =
                                        ((NotificationPropertyChangeEvent) ev)
                                                .getNotifications();
                            }
                            TRACE("Queueing item to validate"); //$NON-NLS-1$
                            if (notifications != null) {
                                queue.add(wc,
                                        affectedObjects,
                                        false,
                                        notifications);
                            } else {
                                if (WorkingCopy.PROP_DIRTY.equals(ev
                                        .getPropertyName())
                                        && wc.isWorkingCopyDirty()) {
                                    /*
                                     * Only queue up validation if the working
                                     * copy has been made dirty. If the working
                                     * copy was saved then the build will take
                                     * care of the validation.
                                     */
                                    queue.add(wc, affectedObjects, false);
                                }
                            }
                        }

                        // XPD-2889: must not perform live validation on
                        // dependency change or reload. The validation builder
                        // will handle these cases.
                    } else if (wc.isWorkingCopyDirty()) {
                        if (WorkingCopy.PROP_DEPENDENCY_CHANGED.equals(ev
                                .getPropertyName())) {
                            Collection<EObject> affected =
                                    new ArrayList<EObject>(1);
                            affected.add(wc.getRootElement());
                            TRACE("Queueing item to validate"); //$NON-NLS-1$
                            queue.add(wc, affected, true);
                        } else if (WorkingCopy.PROP_RELOADED.equals(ev
                                .getPropertyName()) && !wc.isInvalidFile()) {
                            Collection<EObject> affected =
                                    new ArrayList<EObject>(1);
                            affected.add(wc.getRootElement());
                            TRACE("Queueing item to validate"); //$NON-NLS-1$
                            queue.add(wc, affected, true);
                        }
                    }
                    TRACE("Completed preparation for live validation"); //$NON-NLS-1$
                }
            }
            TRACE("Completed property change event."); //$NON-NLS-1$
        }
    }

    /**
     * Get the list of affected objects that triggered this change event. If
     * this is a resource set change event then the list of objects will be
     * acquired from the notifications. Otherwise, the command stack will be
     * used to get the list.
     * 
     * @param event
     *            Property change event
     * @param wc
     *            Working copy
     * @return set of <code>EObject</code>s that caused the change event.
     */
    private Set<EObject> getAffectedObjects(PropertyChangeEvent event,
            WorkingCopy wc) {
        Set<EObject> objs = new HashSet<EObject>();

        if (event != null
                && event.getNewValue() instanceof ResourceSetChangeEvent) {
            ResourceSetChangeEvent ev =
                    (ResourceSetChangeEvent) event.getNewValue();
            List<?> notifications = ev.getNotifications();

            if (notifications != null) {
                for (Object next : notifications) {
                    if (next instanceof Notification) {
                        Object notifier = ((Notification) next).getNotifier();

                        if (notifier instanceof EObject) {
                            objs.add((EObject) notifier);
                        }
                    }
                }
            }

        } else if (wc != null) {
            Command mostRecentCmd =
                    wc.getEditingDomain().getCommandStack()
                            .getMostRecentCommand();

            if (mostRecentCmd != null
                    && WorkingCopy.CHANGED.equals(event.getPropertyName())) {
                Collection affected = mostRecentCmd.getAffectedObjects();
                if (affected != null) {
                    for (Object o : affected) {
                        // This may be a wrapped object
                        o = AdapterFactoryEditingDomain.unwrap(o);
                        if (o instanceof EObject) {
                            objs.add((EObject) o);
                        }
                    }
                }
                addCommandOwners(objs, mostRecentCmd);
            } else {
                // No validation to do, return an empty set.
                return objs;
            }
        }

        if (objs.isEmpty() && wc != null && wc.getRootElement() != null) {
            // Ignore dirty flag changes.
            if (!"DIRTY".equals(event.getPropertyName())) { //$NON-NLS-1$
                objs.add(wc.getRootElement());
            }
        }

        return objs;
    }

    /**
     * Collects the owner objects associated with a command.
     * 
     * @param objs
     *            The collection to which owner objects should be added.
     * @param cmd
     *            The command to analyse.
     * @since 3.2
     */
    private void addCommandOwners(Set<EObject> objs, Command cmd) {
        if (cmd instanceof StrictCompoundCommand) {
            // Special treatment for StrictCompoundCommand: only the last
            // sub-command thereof affects the model.
            List<Command> commandList =
                    ((CompoundCommand) cmd).getCommandList();
            if (!commandList.isEmpty())
                addCommandOwners(objs, commandList.get(commandList.size() - 1));
        } else if (cmd instanceof CompoundCommand) {
            for (Command c : ((CompoundCommand) cmd).getCommandList())
                addCommandOwners(objs, c);
        } else if (cmd instanceof CommandWrapper) {
            Command wrappedCmd = ((CommandWrapper) cmd).getCommand();
            if (wrappedCmd != null)
                addCommandOwners(objs, wrappedCmd);
        } else if (cmd instanceof AddCommand) {
            objs.add(((AddCommand) cmd).getOwner());
        } else if (cmd instanceof MoveCommand) {
            objs.add(((MoveCommand) cmd).getOwner());
        } else if (cmd instanceof PasteFromClipboardCommand) {
            Object owner =
                    AdapterFactoryEditingDomain
                            .unwrap(((PasteFromClipboardCommand) cmd)
                                    .getOwner());
            if (owner instanceof EObject)
                objs.add((EObject) owner);
        } else if (cmd instanceof RemoveCommand) {
            objs.add(((RemoveCommand) cmd).getOwner());
        } else if (cmd instanceof ReplaceCommand) {
            objs.add(((ReplaceCommand) cmd).getOwner());
        }

        // Overrideable commands may have affected additional objects.
        if (cmd instanceof OverrideableCommand) {
            Command overrideCmd = ((OverrideableCommand) cmd).getOverride();
            if (overrideCmd != null)
                addCommandOwners(objs, overrideCmd);
        }
    }

    /**
     * Check if the auto build feature is disabled in the workspace.
     * 
     * @return <code>true</code> if auto build is disabled, <code>false</code>
     *         otherwise.
     */
    private boolean isAutoBuildDisabled() {
        boolean disabled = false;

        // Check the build automatically option
        IWorkspaceDescription description =
                ResourcesPlugin.getWorkspace().getDescription();

        if (description != null) {
            disabled = !description.isAutoBuilding();
        }

        return disabled;
    }

    /**
     * Check if the validation builder has been disabled for the project of the
     * working copy resource.
     * 
     * @param wc
     *            The working copy.
     * @return <code>true</code> if the validation builder has been disabled,
     *         <code>false</code> otherwise.
     */
    private boolean isBuilderDisabled(WorkingCopy wc) {
        boolean disabled = false;

        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);

            if (resource != null) {
                IProjectDescription description;
                try {
                    description = resource.getProject().getDescription();

                    if (description != null) {
                        ICommand[] buildSpec = description.getBuildSpec();
                        disabled = true;

                        for (ICommand spec : buildSpec) {

                            if (spec.getBuilderName().equals(BUILDER_ID)) {
                                disabled = false;
                                break;
                            }
                        }
                    }
                } catch (CoreException e) {
                    // Project may have been deleted.
                    disabled = true;
                }
            }
        }

        return disabled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.IWorkingCopyCreationListener#workingCopyCreated
     * (com.tibco.xpd.resources.WorkingCopy)
     */
    public void workingCopyCreated(WorkingCopy wc) {
        if (wc != null && !(wc instanceof ProjectConfigWorkingCopy)) {
            TRACE(String.format("Working copy '%s' created event received.", wc //$NON-NLS-1$
                    .getName()));

            /*
             * Index the working copy (not when the project is being imported).
             */
            if (!XpdResourcesPlugin.getDefault().isProjectsImportInProgress()
                    && !isProjectMigrationInProgress(wc)) {
                IndexerServiceImpl service = getIndexerService();
                if (service != null) {
                    service.initialiseIndexer(wc);
                }
            }

            List<IResource> resources = wc.getEclipseResources();

            if (resources != null && resources.get(0) instanceof IFile) {
                addWorkingCopy(wc, filter.accept((IFile) resources.get(0)));
            }
            TRACE("Completed working copy create event."); //$NON-NLS-1$
        }
    }

    /**
     * @param wc
     * @return
     */
    private boolean isProjectMigrationInProgress(WorkingCopy wc) {
        List<IResource> resources = wc.getEclipseResources();
        if (!resources.isEmpty()) {
            IResource resource = resources.get(0);
            if (resource != null) {
                return XpdResourcesPlugin.getDefault()
                        .isProjectMigrationInProgress(resource.getProject());
            }
        }
        return false;
    }

    /**
     * Check if this working copy should be validated.
     * 
     * @param wc
     * @return <code>true</code> if the working copy should be queued up
     *         validation.
     */
    private boolean shouldValidate(WorkingCopy wc) {
        if (wc.getEclipseResources() != null
                && !wc.getEclipseResources().isEmpty()) {
            IResource resource = wc.getEclipseResources().get(0);
            // Do not validate derived/hidden resources
            while (resource != null && !(resource instanceof IWorkspaceRoot)) {
                if (resource.getName().startsWith(".")) { //$NON-NLS-1$
                    return false;
                }
                resource = resource.getParent();
            }
        }

        return true;
    }

    /**
     * Get the indexer service.
     * 
     * @return
     */
    private IndexerServiceImpl getIndexerService() {
        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();
        return (IndexerServiceImpl) (service instanceof IndexerServiceImpl ? service
                : null);
    }

    /**
     * Add the given message to the trace.
     * 
     * @param message
     */
    private static void TRACE(String message) {
        ValidationActivator
                .getDefault()
                .getLogger()
                .trace(Logger.CATEGORY_BUILD,
                        String.format("[%d]Monitor: ", Thread.currentThread() //$NON-NLS-1$
                                .getId()) + message);
    }

}
