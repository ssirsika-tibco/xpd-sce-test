/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IValidationListener;
import org.eclipse.emf.validation.service.ValidationEvent;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.EMFValidator;
import com.tibco.xpd.validation.internal.engine.ImplicitDependencyIndexer;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * The EMF validation listener. This class can be used, as is, in the extension
 * <code>org.eclipse.emf.validation.validationListeners</code> for a given
 * client context. <b>Note</b>, this class should only be used when the listener
 * is bound to a SINGLE client context.
 * <p>
 * This listener will create a problem marker for each validation result
 * {@link IConstraintStatus status}.
 * </p>
 * <p>
 * Problem markers created by this listener will have their messages prefixed
 * with the client context label. This will be either:
 * <ol>
 * <li>Prefix specified by {@link #setPrefix(String)} if this class is extended,
 * </li>
 * <li>The last segment of the bound client context id if this class is used as
 * is,</li>
 * <li>The prefix parameter specified in plugin.xml where this class is used<br/>
 * (syntax:
 * <code>com.tibco.xpd.validation.listener.EMFValidationListener: -prefix <i>&lt;prefix&gt;</i>).</code>
 * </li>
 * </ol>
 * </p>
 * 
 * @author njpatel
 * 
 */
public class EMFValidationListener implements IValidationListener,
        IExecutableExtension {

    private String prefix;

    private String clientContextId;

    /**
     * URI attribute of the problem marker.
     */
    protected static final String URI_ATTR = IIssue.AFFECTED_URI;

    /**
     * Constraint id attribute of the problem marker.
     */
    protected static final String CONSTRAINT_ID_ATTR = IIssue.ID;

    /**
     * Context id attribute of the problem marker.
     */
    protected static final String CONTEXT_ID_ATTR = IIssue.DESTINATION_ID;

    private static final String PREFIX_PROP = "prefix"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.validation.service.IValidationListener#validationOccurred
     * (org.eclipse.emf.validation.service.ValidationEvent)
     */
    public void validationOccurred(ValidationEvent event) {
        // Get the working copy being validated
        WorkingCopy wc = getWorkingCopy(event);

        try {
            List<IConstraintStatus> validationResults =
                    event.getValidationResults();
            if (validationResults != null) {
                Map<IResource, List<IConstraintStatus>> validatedResources =
                        getValidatedResources(validationResults);

                // If there is a working copy then check if it's resources are
                // already
                // added to the validated resources map
                if (wc != null && wc.getEclipseResources() != null) {
                    for (IResource res : wc.getEclipseResources()) {
                        if (!validatedResources.keySet().contains(res)) {
                            validatedResources.put(res, null);
                        }
                    }
                }

                // Process each resource to update it's markers
                for (Entry<IResource, List<IConstraintStatus>> entry : validatedResources
                        .entrySet()) {
                    processMarkers(entry.getKey(), entry.getValue(), wc);
                }
            }

        } catch (CoreException e) {
            ValidationActivator.getDefault().getLogger().error(e);
        }
    }

    /**
     * Get the {@link WorkingCopy}from the given event.
     * 
     * @param event
     *            {@link ValidationEvent}
     * @return <code>WorkingCopy</code> if found, <code>null</code> otherwise.
     */
    protected WorkingCopy getWorkingCopy(ValidationEvent event) {
        WorkingCopy wc = null;

        if (event != null) {
            Object value =
                    event.getClientData().get(EMFValidator.LISTENER_WC_KEY);
            wc = (WorkingCopy) (value instanceof WorkingCopy ? value : null);
        }

        return wc;
    }

    /**
     * Process the given results into markers.
     * 
     * @param resource
     *            resource being validated
     * @param results
     *            constraint results
     * @param wc
     *            working copy of the resource being validated
     * @throws CoreException
     */
    protected void processMarkers(IResource resource,
            List<IConstraintStatus> results, WorkingCopy wc)
            throws CoreException {
        ResourceSet rSet = null;

        if (wc != null && wc.getEditingDomain() != null) {
            rSet = wc.getEditingDomain().getResourceSet();
        }

        ResourcesPlugin.getWorkspace().run(new UpdateMarkersRunnable(resource,
                results, rSet),
                resource,
                IWorkspace.AVOID_UPDATE,
                null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        // Set the prefix of the messages in the properties view for this
        // (client context) listener
        if (data instanceof String) {
            String[] split = ((String) data).split("\\s+"); //$NON-NLS-1$

            if (split.length > 1) {
                for (int x = 0; x < split.length; x++) {
                    if (split[x].equalsIgnoreCase("-" + PREFIX_PROP)) { //$NON-NLS-1$
                        prefix = split[x + 1];
                        break;
                    }
                }
            }
        } else if (data instanceof Map<?, ?>) {
            prefix = (String) ((Map<?, ?>) data).get(PREFIX_PROP);
        }

        IConfigurationElement[] elements = config.getChildren("clientContext"); //$NON-NLS-1$

        if (elements != null && elements.length > 0) {
            clientContextId = elements[0].getAttribute("id"); //$NON-NLS-1$

            if (prefix == null) {
                prefix = clientContextId;

                if (prefix != null && prefix.lastIndexOf('.') > 0) {
                    prefix = prefix.substring(prefix.lastIndexOf('.') + 1);
                }
            }
        } else {
            throw new NullPointerException(
                    "No Client context associated with this validation listener"); //$NON-NLS-1$
        }
    }

    /**
     * Set the problem marker's message prefix.
     * 
     * @param prefix
     */
    protected void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Get the id of the problem marker type to create on the resource. The
     * default implementation will return the EMF Validation marker id.
     * Subclasses may provide their own marker types.
     * 
     * @return marker type id.
     * @since 3.3
     */
    protected String getMarkerType() {
        return EMFValidator.MARKER_ID;
    }

    /**
     * Add custom attributes to the marker. Default implementation does nothing,
     * subclasses may extend to add additional information to the marker.
     * 
     * @param marker
     *            problem marker
     * @param status
     *            the constraint status that caused this marker to be created
     * @throws CoreException
     * 
     * @since 3.3
     */
    protected void addCustomAttributes(IMarker marker, IConstraintStatus status)
            throws CoreException {
        // Do nothing
    }

    /**
     * Create or update a marker related to the given status.
     * 
     * @param resource
     *            to create marker on
     * @param status
     *            constraint status
     * @param marker
     *            a marker if an existing marker related to the status already
     *            exists, <code>null</code> otherwise. If an existing marker is
     *            provided this will be update, otherwise a new marker will be
     *            created.
     * @throws CoreException
     */
    protected void createOrUpdateMarker(IResource resource,
            IConstraintStatus status, IMarker marker) throws CoreException {
        if (resource != null && status != null && !status.isOK()) {
            EObject target = status.getTarget();

            if (target != null && target.eResource() != null) {
                String id = target.eResource().getURIFragment(target);
                URI uri = EcoreUtil.getURI(target);

                if (uri != null && id != null) {
                    if (marker == null) {
                        marker = resource.createMarker(getMarkerType());
                    }
                    marker.setAttribute(IMarker.LOCATION, id);
                    marker.setAttribute(URI_ATTR, uri.toString());
                    marker.setAttribute(CONSTRAINT_ID_ATTR, status
                            .getConstraint().getDescriptor().getId());
                    marker.setAttribute(CONTEXT_ID_ATTR, clientContextId);
                    marker.setAttribute(IMarker.MESSAGE, addPrefix(status
                            .getMessage()));

                    // Allow subclasses to add any custom attributes to the
                    // marker
                    addCustomAttributes(marker, status);

                    // Update the implicit dependency indexer if required
                    updateImplicitDependencyIndex(marker);

                    // Set severity
                    switch (status.getSeverity()) {
                    case IStatus.INFO:
                        marker.setAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_INFO);
                        marker.setAttribute(IMarker.PRIORITY,
                                IMarker.PRIORITY_LOW);
                        break;
                    case IStatus.WARNING:
                        marker.setAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_WARNING);
                        marker.setAttribute(IMarker.PRIORITY,
                                IMarker.PRIORITY_NORMAL);
                        break;
                    case IStatus.ERROR:
                    case IStatus.CANCEL:
                        marker.setAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_ERROR);
                        marker.setAttribute(IMarker.PRIORITY,
                                IMarker.PRIORITY_HIGH);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Update the implicit dependency index with <code>LINKED_RESOURCE</code>s
     * applied to this marker, if any.
     * 
     * @param marker
     * 
     * @since 3.5
     */
    private void updateImplicitDependencyIndex(IMarker marker) {
        ImplicitDependencyIndexer.updateImplicitDependenciesFromMarker(marker);
    }

    /**
     * Add prefix to the status message.
     * 
     * @param message
     *            status message
     * @return status message with prefix if applicable.
     */
    private String addPrefix(String message) {
        if (message != null && prefix != null && prefix.length() > 0) {
            message = String.format("%s: %s", prefix, message); //$NON-NLS-1$
        }
        return message;
    }

    /**
     * Get the mapping of the resources that were validated and thier
     * corresponding constraint results.
     * 
     * @param results
     *            constraint results
     * @return
     */
    private Map<IResource, List<IConstraintStatus>> getValidatedResources(
            List<IConstraintStatus> results) {
        Map<URI, IResource> resources = new HashMap<URI, IResource>();
        Map<IResource, List<IConstraintStatus>> targetResourceMap =
                new HashMap<IResource, List<IConstraintStatus>>();

        if (results != null) {
            for (IConstraintStatus status : results) {
                if (status.isMultiStatus()) {
                    IStatus[] children = status.getChildren();

                    for (IStatus child : children) {
                        if (child instanceof IConstraintStatus) {
                            addTargetResource(resources,
                                    targetResourceMap,
                                    (IConstraintStatus) child);
                        }
                    }
                } else {
                    addTargetResource(resources, targetResourceMap, status);
                }
            }
        }

        return targetResourceMap;
    }

    /**
     * Get the target resource that was validated in the given constraint status
     * and update the maps accordingly.
     * 
     * @param resources
     * @param targetResourceMap
     * @param cs
     */
    private void addTargetResource(Map<URI, IResource> resources,
            Map<IResource, List<IConstraintStatus>> targetResourceMap,
            IConstraintStatus cs) {
        EObject target = cs.getTarget();
        if (target != null && target.eResource() != null) {
            IResource resource = null;
            URI uri = target.eResource().getURI();
            resource = resources.get(uri);

            if (resource == null) {
                resource = WorkingCopyUtil.getFile(target);
                if (resource != null) {
                    resources.put(uri, resource);
                }
            }

            if (resource != null) {
                List<IConstraintStatus> constraints =
                        targetResourceMap.get(resource);

                if (constraints == null) {
                    constraints = new ArrayList<IConstraintStatus>();
                    targetResourceMap.put(resource, constraints);
                }
                constraints.add(cs);
            }
        }
    }

    /**
     * A runnable that will update the resource markers.
     * 
     * @author njpatel
     * 
     */
    private class UpdateMarkersRunnable implements IWorkspaceRunnable {

        private final IResource resource;

        private final List<IConstraintStatus> results;

        private final ResourceSet rSet;

        /**
         * A runnable that will update the resource markers.
         * 
         * @param resource
         *            resource to update
         * @param results
         *            constraint results
         * @param rSet
         *            resource set
         */
        public UpdateMarkersRunnable(IResource resource,
                List<IConstraintStatus> results, ResourceSet rSet) {
            this.resource = resource;
            this.results = results;
            this.rSet = rSet;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core
         * .runtime.IProgressMonitor)
         */
        public void run(IProgressMonitor monitor) throws CoreException {
            if (resource != null && resource.isAccessible()) {
                // Get existing marker
                List<IMarker> existingMarkers = getExistingMarkers(resource);

                if (results != null) {
                    for (IConstraintStatus status : results) {
                        boolean createMarker = false;
                        /*
                         * Check if there is a marker that already exists
                         * related to this constraint
                         */
                        IMarker marker =
                                findRelatedMarker(existingMarkers, status);

                        if (marker != null) {
                            boolean deleteExistingMarker = false;
                            if (status.isOK()) {
                                // Status is now OK so delete marker
                                deleteExistingMarker = true;
                            } else {
                                /*
                                 * Check if the marker's severity and message
                                 * have changed - if so update the marker
                                 */
                                if (hasStatusChanged(marker, status)) {
                                    /*
                                     * Message or status has changed so update
                                     * marker
                                     */
                                    createMarker = true;
                                }
                            }

                            if (deleteExistingMarker) {
                                existingMarkers.remove(marker);
                                marker.delete();
                            }
                        } else {
                            // Create a new marker
                            createMarker = true;
                        }

                        if (createMarker) {
                            createOrUpdateMarker(resource, status, marker);
                        }
                    }
                }

                /*
                 * Check if the remaining existing markers are valid and if not
                 * delete them
                 */
                if (rSet != null) {
                    for (IMarker marker : existingMarkers) {
                        String uri = (String) marker.getAttribute(URI_ATTR);

                        if (uri == null
                                || rSet.getEObject(URI.createURI(uri), false) == null) {
                            // Marker no longer valid
                            marker.delete();
                        }
                    }
                }
            }
        }

        /**
         * Check if the status has changed between the existing marker and the
         * new result.
         * 
         * @param marker
         *            existing marker related to this constraint
         * @param status
         *            new result of this constraint
         * @return <code>true</code> if the marker is still valid,
         *         <code>false</code> otherwise.
         * @throws CoreException
         */
        private boolean hasStatusChanged(IMarker marker,
                IConstraintStatus status) throws CoreException {
            /*
             * Check if the message has changed
             */
            String markerMessage =
                    (String) marker.getAttribute(IMarker.MESSAGE);
            String statusMessage = addPrefix(status.getMessage());

            if (markerMessage != null && statusMessage != null
                    && !markerMessage.equals(statusMessage)) {
                // Message has changed
                return true;
            }

            /*
             * Check if the status has changed
             */
            int statusSeverity = status.getSeverity();

            Object attr = marker.getAttribute(IMarker.SEVERITY);
            int markerSeverity;
            if (attr instanceof Integer) {
                markerSeverity = ((Integer) attr).intValue();
            } else {
                markerSeverity = new Integer(attr.toString()).intValue();
            }

            switch (markerSeverity) {
            case IMarker.SEVERITY_INFO:
                return statusSeverity != IStatus.INFO;

            case IMarker.SEVERITY_WARNING:
                return statusSeverity != IStatus.WARNING;

            case IMarker.SEVERITY_ERROR:
                return statusSeverity != IStatus.CANCEL
                        || statusSeverity != IStatus.ERROR;
            }

            return false;
        }

        /**
         * Get existing markers of the given resource.
         * 
         * @param resource
         *            resource to check
         * @return existing markers, empty list if none found.
         * @throws CoreException
         */
        private List<IMarker> getExistingMarkers(IResource resource)
                throws CoreException {
            List<IMarker> existingMarkers = new ArrayList<IMarker>();
            IMarker[] markers =
                    resource.findMarkers(getMarkerType(),
                            false,
                            IResource.DEPTH_ZERO);

            for (IMarker marker : markers) {
                String id = (String) marker.getAttribute(CONTEXT_ID_ATTR);

                if (id != null && id.equals(clientContextId)) {
                    existingMarkers.add(marker);
                }
            }

            return existingMarkers;
        }

        /**
         * Find a marker related to the given constraint status from the list of
         * markers.
         * 
         * @param markers
         *            list of markers to search through
         * @param status
         *            constrain status
         * @return marker related to the given status, <code>null</code> if none
         *         found.
         * @throws CoreException
         */
        private IMarker findRelatedMarker(List<IMarker> markers,
                IConstraintStatus status) throws CoreException {
            IMarker relatedMarker = null;
            String uri = EcoreUtil.getURI(status.getTarget()).toString();

            for (IMarker marker : markers) {
                String value = (String) marker.getAttribute(CONSTRAINT_ID_ATTR);

                if (value != null
                        && value.equals(status.getConstraint().getDescriptor()
                                .getId())) {
                    // ID matches so check URI
                    value = (String) marker.getAttribute(URI_ATTR);

                    if (value != null && value.equals(uri)) {
                        // Match found
                        relatedMarker = marker;
                        break;
                    }
                }
            }

            return relatedMarker;
        }

    }
}
