/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.IValidationListener;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.ValidationEvent;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.EMFValidator;
import com.tibco.xpd.validation.internal.LiveValidationItem;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.provider.IFileFilter;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IPreferenceGroup;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.provider.IssueInfo;

/**
 * Validation engine.
 * 
 * @author nwilson
 */
public class ValidationEngine {

    /** The validation job. */
    private Job validate;

    /** Working copy monitor */
    private WorkingCopyMonitor workingCopyMonitor;

    /** A list of validation providers. */
    private final Map<String, Provider> providers;

    /** Map of issue infos. */
    private final Map<String, IssueInfo> issueInfos;

    private final List<IPreferenceGroup> prefGroups;

    /** The list of available destination environments. */
    private Collection<Destination> destinations;

    /** Workspace resource validation providers * */
    private final Map<String /* provider id */, Collection<WorkspaceResourceValidatorExt>> resourceValidationProviders;

    public static final Object FAMILY_VALIDATE_ITEM = new Object();

    /**
     * Listeners for validation events - this is a synchronised set.
     * 
     * @see Collections#synchronizedSet(Set)
     */
    private final Set<IValidationListener> listeners;

    private static final Logger log = LoggerFactory
            .createLogger(ValidationActivator.PLUGIN_ID);

    /**
     * Constructor.
     */
    public ValidationEngine() {
        providers = new HashMap<String, Provider>();
        issueInfos = new HashMap<String, IssueInfo>();
        destinations = new ArrayList<Destination>();
        listeners =
                Collections.synchronizedSet(new HashSet<IValidationListener>());
        prefGroups = new ArrayList<IPreferenceGroup>();
        resourceValidationProviders =
                new HashMap<String, Collection<WorkspaceResourceValidatorExt>>();
    }

    /**
     * @param provider
     *            The provider to add.
     */
    public void addProvider(Provider provider) {
        providers.put(provider.getId(), provider);
    }

    /**
     * @param info
     *            The IssueInfo to register.
     */
    public void addIssueInfo(IssueInfo info) {
        issueInfos.put(info.getId(), info);
    }

    /**
     * Add the workspace resource validation provider.
     * 
     * @param providerId
     *            workspace resource validation provider id.
     * @param validators
     *            validator extensions that are registered for this provider.
     */
    public void addWorkspaceResourceValidationProvider(String providerId,
            Collection<WorkspaceResourceValidatorExt> validators) {
        if (providerId != null && validators != null) {
            resourceValidationProviders.put(providerId, validators);
        }
    }

    /**
     * Get the workspace resource validators for the given workspace resource
     * validation provider id.
     * 
     * @param providerId
     *            workspace resource validation provider id.
     * @return collection of workspace resource validator extensions,
     *         <code>null</code> if none found.
     */
    public Collection<WorkspaceResourceValidatorExt> getResourceValidators(
            String providerId) {
        if (providerId != null) {
            return resourceValidationProviders.get(providerId);
        }

        return null;
    }

    /**
     * Add the preference group
     * 
     * @param group
     *            the <code>IPreferenceGroup</code> to register.
     */
    public void addPreferenceGroup(IPreferenceGroup group) {
        prefGroups.add(group);
    }

    /**
     * Get the preference groups with the given <i>preferenceId</i>.
     * 
     * @param preferenceId
     * @return Array of <code>{@link IPreferenceGroup}</code> objects, empty
     *         list if no preference groups are found with the given
     *         <i>preferenceId</i>.
     */
    public IPreferenceGroup[] getPreferenceGroupsByPreferenceId(
            String preferenceId) {
        List<IPreferenceGroup> groups = new ArrayList<IPreferenceGroup>();

        if (preferenceId != null) {
            for (IPreferenceGroup group : prefGroups) {
                if (group.getPreferenceId() != null
                        && group.getPreferenceId().equals(preferenceId)) {
                    groups.add(group);
                }
            }
        } else {
            throw new NullPointerException("Preference id is null."); //$NON-NLS-1$
        }

        return groups.toArray(new IPreferenceGroup[groups.size()]);
    }

    /**
     * Starts the validation engine.
     */
    public void start() {
        if (validate == null) {
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                // Run this in UI thread to avoid deadlock on Studio startup
                XpdResourcesPlugin.getStandardDisplay()
                        .asyncExec(new Runnable() {
                            public void run() {
                                destinations =
                                        ValidationManager.getInstance()
                                                .getDestinations();
                                validate = new ValidationJob();
                                validate.setSystem(true);
                                validate.schedule();

                                // Start the working copy monitor
                                if (PlatformUI.isWorkbenchRunning()) {
                                    workingCopyMonitor =
                                            new WorkingCopyMonitor();
                                }
                            }
                        });
            } else {
                destinations =
                        ValidationManager.getInstance().getDestinations();
                validate = new ValidationJob();
                validate.setSystem(true);
                validate.schedule();

                // Start the working copy monitor
                workingCopyMonitor = new WorkingCopyMonitor();
            }
        }
    }

    /**
     * Stops the validation engine.
     */
    public void stop() {
        while (validate != null && validate.cancel() == false) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
        validate = null;

        if (workingCopyMonitor != null) {
            workingCopyMonitor.dispose();
        }
    }

    /**
     * Validates the given object.
     * 
     * @param item
     *            The item to validate.
     * @return The issues generated.
     */
    public Collection<IIssue> validate(IValidationItem item) {
        /*
         * If clean is called then run Batch rather than Live validation. This
         * ensures the whole models gets re-validated
         */
        if (item.getClean()) {
            // Run EMF batch validation
            EMFValidator.getInstance().runBatchValidation(item);
        } else {
            // Run EMF live validation
            EMFValidator.getInstance().runLiveValidation(item);
        }
        return validateItem(item, destinations);
    }

    public Collection<IIssue> validate(IValidationItem item,
            Collection<Destination> destinations) {
        // Run EMF batch validation
        EMFValidator.getInstance().runBatchValidation(item);
        return validateItem(item, destinations);
    }

    /**
     * Validates the given object.
     * 
     * @param item
     *            The item to validate.
     * @param destinations
     *            The destination environments to validate against.
     * @return The issues generated.
     */
    private Collection<IIssue> validateItem(IValidationItem item,
            Collection<Destination> destinations) {
        IValidationScope scope = new ValidationScope();
        Logger logger = null;

        WorkingCopy wc = item.getWorkingCopy();

        /*
         * In case of live validation, the item will be an instance of
         * LiveValidationItem, which contains notifications.
         */

        boolean isLiveValidation = false;
        if (item instanceof LiveValidationItem) {
            isLiveValidation = true;
        }
        scope.setIsLiveValidation(isLiveValidation);

        // Check that the validation activator is still active
        if (wc != null && wc.getEclipseResources() != null
                && !wc.getEclipseResources().isEmpty()
                && ValidationActivator.getDefault() != null) {
            try {
                long dbTime = 0;
                int dbProviders = 0;
                logger = ValidationActivator.getDefault().getLogger();
                if (logger.isDebugEnabled()) {
                    dbTime = System.currentTimeMillis();
                    logger.debug("Validation started"); //$NON-NLS-1$
                }

                IResource resource = wc.getEclipseResources().get(0);
                if (resource instanceof IFile) {
                    IFile file = (IFile) resource;
                    if (item.affectMarkers() && item.getClean()) {
                        deleteMarkers(resource);
                    }
                    List<String> destinationIds = new ArrayList<String>();
                    for (Destination destination : destinations) {
                        destinationIds.add(destination.getId());
                        IFileFilter filter =
                                ValidationManager.getInstance()
                                        .getFileFilter(destination);
                        if (filter == null || filter.accept(file)) {
                            scope.setCurrentDestination(destination);
                            Collection<String> providerIds =
                                    destination.getValidationProviders();
                            for (String providerId : providerIds) {
                                Provider provider = providers.get(providerId);
                                if (provider != null) {
                                    scope.setCurrentProviderId(providerId);
                                    /*
                                     * The live model change validation and the
                                     * builder jobs can both get here to ask the
                                     * providers to validate so sync will stop
                                     * both the jobs from running the same
                                     * provider simultaneously.
                                     */
                                    synchronized (provider) {
                                        dbProviders++;
                                        try {
                                            provider.validate(destination,
                                                    scope,
                                                    item);
                                        } catch (Throwable t) {
                                            /*
                                             * Catch any exceptions thrown by
                                             * the provider, log it and carry on
                                             * with next provider
                                             */
                                            logger.error(t);
                                        }
                                    }
                                }
                            }
                        }

                    }
                    if (item.affectMarkers()) {
                        Collection<IIssue> issues = scope.getIssues();
                        /*
                         * Update the implicit dependency indexer
                         */
                        updateImplicitDependenciesFromIssues(resource, issues);

                        updateMarkers(resource, issues, destinationIds, scope);
                    }
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("Validation time: " //$NON-NLS-1$
                            + (System.currentTimeMillis() - dbTime)
                            + "; Providers: " //$NON-NLS-1$
                            + dbProviders);
                }
            } catch (Throwable t) {
                if (logger != null) {
                    try {
                        logger.error(t);
                    } catch (Throwable e) {
                        // Ignore
                    }
                }
            }
        }
        return scope.getIssues();
    }

    /**
     * @param resource
     *            The resource to delete markers for.
     */
    private void deleteMarkers(IResource resource) {
        try {
            if (resource.exists()) {
                resource.deleteMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                        true,
                        IResource.DEPTH_INFINITE);

                // Clear any implicit dependencies from the indexer
                clearImplicitDependencies(resource);
            }
        } catch (CoreException e) {
            ValidationActivator.getDefault().getLogger().error(e);
        }
    }

    /**
     * @param resource
     *            The resource to update.
     * @param issuesFromThisRun
     *            The collected issues.
     * @param destinationIds
     *            list of destination ids of validation destinations that were
     *            validated.
     * @param scope
     *            The validation scope.
     */
    private void updateMarkers(final IResource resource,
            final Collection<IIssue> issuesFromThisRun,
            final List<String> destinationIds, final IValidationScope scope) {
        IWorkspace workspace = resource.getWorkspace();

        final Collection<String> affectedUris = scope.getAffectedUris();

        try {
            workspace.run(new IWorkspaceRunnable() {

                public void run(IProgressMonitor monitor) throws CoreException {

                    final HashSet<IIssue> existingMarkers =
                            new HashSet<IIssue>();

                    final HashSet<IIssue> brandNewMarkers =
                            new HashSet<IIssue>();
                    final HashSet<IIssue> reRaisedMarkers =
                            new HashSet<IIssue>();

                    try {
                        IMarker[] markers =
                                resource.findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                                        true,
                                        IResource.DEPTH_ZERO);
                        for (int i = 0; i < markers.length; i++) {
                            existingMarkers.add(new Issue(markers[i]));
                        }
                    } catch (CoreException e) {
                        // Refresh all of them.
                        deleteMarkers(resource);
                    }
                    for (IIssue issue : issuesFromThisRun) {
                        IssueInfo info = issueInfos.get(issue.getId());
                        if (info == null) {
                            log.error("Missing issue definition for id " + issue.getId()); //$NON-NLS-1$
                        } else {
                            issue.createMessage(info.getMessage());
                            if (existingMarkers.contains(issue)) {
                                /*
                                 * IF the validation found an issue that was an
                                 * existing issue then remove it from oldMarkers
                                 * (a little below *A ). This is because when we
                                 * get to *B below don't want to remove markers
                                 * for issues that have been re-raised on this
                                 * validation
                                 */
                                reRaisedMarkers.add(issue);
                            } else {
                                brandNewMarkers.add(issue);
                            }
                        }
                    }

                    /*
                     * *A: Complete create of "oldMarkersNotReRaised" by
                     * removing all re-raised issues from set of oldMarkers
                     */
                    final HashSet<IIssue> oldMarkersNotReRaised =
                            existingMarkers;
                    for (IIssue issue : reRaisedMarkers) {
                        oldMarkersNotReRaised.remove(issue);
                    }

                    final WorkingCopy workingCopy =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(resource);
                    if (workingCopy == null) {
                        /*
                         * special folder configuration has been probably
                         * changed and there is no working copy for this
                         * resource anymore
                         */
                        return;
                    }
                    EObject root = workingCopy.getRootElement();
                    if (root == null) {
                        /*
                         * the file became invalid, we cannot validate it (but
                         * working copy should manage to add invalid file marker
                         * to it)
                         */
                        return;
                    }

                    final ArrayList<IMarker> toDelete =
                            new ArrayList<IMarker>();

                    /*
                     * *B: Go through list of old markers that were raised
                     * originally on this resource BUT were not re-raised by
                     * this validation iteration.
                     * 
                     * That could mean that on re-validation the problem no
                     * longer existed OR it could mean that the affectedObject
                     * that the problem is on was NOT revalidated this time (or
                     * not revalidated for this destination environment).
                     * 
                     * So we will remove the oldMarkerNotReRaised if we DID
                     * re-validate its affectedURI this time or it is no longer
                     * in model.
                     */
                    for (IIssue oldIssueNotReRaised : oldMarkersNotReRaised) {
                        String uri = oldIssueNotReRaised.getUri();
                        EObject target = workingCopy.resolveEObject(uri);

                        if (oldIssueNotReRaised instanceof Issue) {
                            /*
                             * Always remove markers for objects that are no
                             * longer in model. If the oldMarker (issue) object
                             * no longer contained in a model (target == null)
                             * then remove the marker.
                             */
                            if (target == null) {

                                toDelete.add(((Issue) oldIssueNotReRaised)
                                        .getMarker());
                            } else {
                                /*
                                 * if its live validation run, then we need to
                                 * check whether this issue needs to be
                                 * preserved, i.e., if it was raised by a
                                 * run-on-save-only rule or the rule didn't
                                 * re-run during this validation
                                 */
                                if (scope.isLiveValidation()) {
                                    if (oldIssueNotReRaised
                                            .isValidateOnSaveOnly()) {
                                        /*
                                         * Don't delete issue raised by save
                                         * only rule, when doing live
                                         * validation.
                                         */
                                        continue;

                                    } else {
                                        /*
                                         * If its not a run-on-save-only rule
                                         * then don't delete marker if the rule
                                         * was not re-executed in this
                                         * validation run.
                                         */
                                        if (!scope
                                                .wasRuleExecutedForTheIssue(oldIssueNotReRaised)) {
                                            continue;
                                        }
                                    }
                                }

                                if ( /*
                                      * If we revalidated (affectedURIs) the
                                      * object that the oldMarker (issue) was
                                      * raised for, then the problem doesn't
                                      * exist any more so remove the marker. *
                                      */
                                affectedUris.contains(oldIssueNotReRaised
                                        .getAffectedUri())) {
                                    /*
                                     * Only remove marker if it came from one of
                                     * the destinations that has just been
                                     * validated.
                                     */
                                    if (destinationIds
                                            .contains(oldIssueNotReRaised
                                                    .getDestinationId())) {
                                        toDelete.add(((Issue) oldIssueNotReRaised)
                                                .getMarker());
                                    }
                                }
                            }
                        }
                    }
                    final ArrayList<NewMarkerInfo> toAdd =
                            new ArrayList<NewMarkerInfo>();
                    for (IIssue issue : brandNewMarkers) {
                        IssueInfo info = issueInfos.get(issue.getId());
                        if (info != null) {
                            try {
                                String message = issue.getMessage();
                                Map<String, Object> attributes = null;

                                if (issue instanceof Issue) {
                                    // If marker attributes have been provided
                                    // in the issue then initialise with that
                                    attributes =
                                            ((Issue) issue)
                                                    .getMarkerAttributes();
                                }

                                if (attributes == null) {
                                    attributes = new HashMap<String, Object>();
                                }

                                attributes.put(IMarker.MESSAGE, message);
                                attributes.put(IMarker.SEVERITY,
                                        issue.getSeverity());
                                attributes
                                        .put(IMarker.LOCATION, issue.getUri());
                                attributes.put(IIssue.AFFECTED_URI,
                                        issue.getAffectedUri());
                                attributes.put(IIssue.ID, issue.getId());
                                attributes.put(IIssue.DESTINATION_ID,
                                        issue.getDestinationId());
                                attributes.put(IIssue.VALIDATE_ON_SAVE_ONLY,
                                        issue.isValidateOnSaveOnly());
                                attributes.put(IIssue.PROVIDER_ID,
                                        issue.getProviderId());
                                attributes.put(IIssue.RULE_ID,
                                        issue.getRuleId());
                                if (issue.getAdditionalInfoString() != null) {
                                    attributes
                                            .put(XpdConsts.VALIDATION_MARKER_ADDITIONAL_INFO,
                                                    issue.getAdditionalInfoString());
                                }

                                NewMarkerInfo marker =
                                        new NewMarkerInfo(
                                                issue.getMarkerType(),
                                                attributes);
                                toAdd.add(marker);
                            } catch (Throwable t) {
                                ValidationActivator.getDefault().getLogger()
                                        .error(t);
                            }
                        }
                    }

                    //
                    // THis used to be the start of the Workspacce runnable.
                    //

                    for (IMarker marker : toDelete) {
                        /*
                         * If this marker has a LINKED_RESOURCE set (ie implicit
                         * dependency) then it needs to be removed from the
                         * indexer
                         */
                        clearImplicitDependencies(marker);

                        marker.delete();
                    }
                    for (NewMarkerInfo info : toAdd) {
                        IMarker marker = resource.createMarker(info.getType());
                        marker.setAttributes(info.getAttributes());
                    }
                    final ValidationEvent event =
                            new ValidationEvent(resource, issuesFromThisRun);
                    workingCopy.clearSeverityCache();
                    // Thread?
                    Job notify = new Job("") { //$NON-NLS-1$
                                @Override
                                protected IStatus run(IProgressMonitor monitor) {
                                    notifyListeners(event);
                                    return Status.OK_STATUS;
                                }
                            };
                    notify.setSystem(true);
                    notify.schedule();
                }

            },
                    resource,
                    IWorkspace.AVOID_UPDATE,
                    null);
        } catch (Throwable e) {
            ValidationActivator.getDefault().getLogger().error(e);
        }
    }

    /**
     * Update the implicit dependency indexer for the given resource if any
     * issues have a LINKED_RESOURCE (implicit dependency).
     * 
     * @param resource
     * @param issues
     * 
     * @since 3.5
     */
    private void updateImplicitDependenciesFromIssues(IResource resource,
            Collection<IIssue> issues) {
        if (resource != null) {
            new ImplicitDependencyIndexer(resource)
                    .updateImplicitDependenciesFromIssues(issues);
        }
    }

    /**
     * Clear the implicit dependency indexer for the given resource.
     * 
     * @param resource
     * 
     * @since 3.5
     */
    private void clearImplicitDependencies(IResource resource) {
        if (resource != null) {
            new ImplicitDependencyIndexer(resource).clearIndexer();
        }
    }

    /**
     * Clear the implicit dependency indexer for the given resource.
     * 
     * @param resource
     * 
     * @since 3.5
     */
    private void clearImplicitDependencies(IMarker marker) {
        ImplicitDependencyIndexer.clearDependencies(marker);
    }

    /**
     * @param listener
     *            The listener to add.
     */
    public void addValidationListener(IValidationListener listener) {
        listeners.add(listener);
    }

    /**
     * @param listener
     *            The listener to remove.
     */
    public void removeValidationListener(IValidationListener listener) {
        listeners.remove(listener);
    }

    /**
     * @param event
     *            The validation event.
     */
    private void notifyListeners(ValidationEvent event) {
        synchronized (listeners) {
            for (IValidationListener listener : listeners) {
                listener.validationEvent(event);
            }
        }
    }

    /**
     * Add a trace message.
     * 
     * @param message
     */
    private static final void TRACE(String message) {
        log.trace(Logger.CATEGORY_BUILD, String.format("[%d]%s", Thread //$NON-NLS-1$
                .currentThread().getId(), message));
    }

    /**
     * @author nwilson
     */
    class ValidationJob extends Job {
        /** The validation queue. */
        private final ValidationQueue queue;

        /**
         * Constructor.
         */
        public ValidationJob() {
            super(Messages.ValidationEngine_Validation);
            queue = ValidationManager.getInstance().getValidationQueue();
        }

        /**
         * @param monitor
         *            The progres monitor.
         * @return Status.OK.
         * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
         */
        @Override
        protected IStatus run(IProgressMonitor monitor) {
            int waitTime = 50;
            while (!monitor.isCanceled()) {
                /*
                 * If queue is empty then don't ask for the next item in the
                 * queue as it is a blocking call. If blocked then we cannot
                 * cancel this job.
                 */
                if (queue.isEmpty()) {

                    try {
                        Thread.sleep(waitTime);

                        /*
                         * If nothing is added to the queue for some time then
                         * keep adding time to the sleep (to max of 1sec). Once
                         * an item is added to the queue the sleep time will be
                         * reset back to 50ms
                         */
                        if (waitTime < 1000) {
                            waitTime *= 2;
                            if (waitTime > 1000) {
                                waitTime = 1000;
                            }
                        }

                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                    continue;
                }
                /*
                 * XPD-3641: As we are creating background jobs to validate the
                 * "validation item" this thread is free immediately to get the
                 * next item in the validation queue. This means that the
                 * WorkingCopyMonitor does not get a chance to accumulate
                 * incoming events for the same working copy and therefore we
                 * end up with multiple validation jobs queued up for the same
                 * working copy. Therefore, we now wait for any validations
                 * already queued up to complete before scheduling another live
                 * validation.
                 */
                try {
                    Job.getJobManager().join(FAMILY_VALIDATE_ITEM, null);
                } catch (OperationCanceledException e1) {
                    // IGNORE
                } catch (InterruptedException e1) {
                    // IGNORE
                }

                // Reset wait time
                waitTime = 50;

                try {
                    LiveValidationItem next = queue.getNext();

                    if (next != null) {
                        String title =
                                Messages.ValidationEngine_liveValidationJob_label;
                        WorkingCopy wc = next.getWorkingCopy();
                        if (wc != null && wc.getEclipseResources() != null) {
                            title =
                                    String.format(Messages.ValidationEngine_validation_job_label,
                                            wc.getEclipseResources().get(0)
                                                    .getFullPath().toString());
                        }

                        ValidateItemJob job = new ValidateItemJob(title, next);
                        job.setRule(ResourcesPlugin.getWorkspace()
                                .getRuleFactory().buildRule());
                        job.schedule();
                    }
                } catch (InterruptedException e) {
                    // Carry on, interruption is fine.
                }
                if (queue.isEmpty()) {
                    // On shutdown the ValidationActivator could be null
                    if (ValidationActivator.getDefault() != null) {
                        ValidationActivator
                                .getDefault()
                                .setStatus(ValidationActivator.ValidatorStatus.READY);
                    }
                }
            }

            monitor.done();

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            return Status.OK_STATUS;
        }

    }

    /**
     * Job to validate the next item in the validation queue (live validation).
     * 
     * @author njpatel
     * @since 3.2
     */
    private class ValidateItemJob extends Job {

        private final LiveValidationItem item;

        public ValidateItemJob(String title, LiveValidationItem item) {
            super(title);
            this.item = item;
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            WorkingCopy wc = item.getWorkingCopy();
            if (wc.isExist()) {
                TRACE(String.format("ValidationItem: Validating '%s'", //$NON-NLS-1$
                        wc != null ? wc.getEclipseResources().get(0) : "")); //$NON-NLS-1$
                long start = System.currentTimeMillis();
                validate(item);
                TRACE(String
                        .format("ValidationItem: Completed validating in %d ms.", //$NON-NLS-1$
                                System.currentTimeMillis() - start));
            }
            return Status.OK_STATUS;
        }

        @Override
        public boolean belongsTo(Object family) {
            return FAMILY_VALIDATE_ITEM == family;
        }
    }

    /**
     * @author nwilson
     */
    class NewMarkerInfo {
        /** The marker type. */
        private final String type;

        /** The attributes. */
        private final Map<String, Object> attributes;

        /**
         * @param type
         *            The marker type.
         * @param attributes
         *            The attributes.
         */
        public NewMarkerInfo(String type, Map<String, Object> attributes) {
            this.type = type;
            this.attributes = attributes;
        }

        /**
         * @return The marker type.
         */
        public String getType() {
            return type;
        }

        /**
         * @return The attributes.
         */
        public Map<String, Object> getAttributes() {
            return attributes;
        }
    }

    /**
     * @param id
     *            The issue ID.
     * @return The associated IssueInfo.
     */
    public IssueInfo getIssueInfo(String id) {
        return issueInfos.get(id);
    }

    /**
     * Get all registerd issues.
     * 
     * @return Array of <code>IssueInfo</code> objects. Empty list if no issues
     *         registered.
     */
    public IssueInfo[] getAllIssues() {
        return issueInfos.values().toArray(new IssueInfo[issueInfos.values()
                .size()]);
    }
}
