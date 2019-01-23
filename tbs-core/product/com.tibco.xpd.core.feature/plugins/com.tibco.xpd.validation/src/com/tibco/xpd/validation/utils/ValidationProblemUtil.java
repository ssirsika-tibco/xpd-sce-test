/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.validation.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * Util class that provides methods to get problem markers of given destination
 * id and severity from a given resource.
 * 
 * @author njpatel
 * @since 3.3
 */
public final class ValidationProblemUtil {

    /**
     * Get the (validation marker type) problem markers for the given
     * destination id and marker severity.
     * 
     * @param destinationId
     *            id of the destination for which to get the markers (issues)
     * @param markerSeverity
     *            one of {@link IMarker#SEVERITY_ERROR},
     *            {@link IMarker#SEVERITY_WARNING} or
     *            {@link IMarker#SEVERITY_INFO}.
     * @param includeMarkerSubTypes
     *            <code>true</code> if the sub types of the validation marker
     *            should be included (this would be the case if a custom
     *            validation type is used in the validation provider).
     * @param resource
     *            the resource for which to get the problem markers
     * @return collection of markers, empty collection if none found
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(String destinationId,
            int markerSeverity, boolean includeMarkerSubTypes,
            IResource resource) throws CoreException {
        Collection<IMarker> problemMarkers =
                getProblemMarkers(destinationId,
                        includeMarkerSubTypes,
                        resource);

        List<IMarker> markers = new ArrayList<IMarker>();

        for (IMarker marker : problemMarkers) {
            int severity =
                    marker.getAttribute(IMarker.SEVERITY, markerSeverity);
            if (severity == markerSeverity) {
                markers.add(marker);
            }
        }

        return markers;
    }

    /**
     * Get all (validation marker type) problem markers for the given
     * destination id. This will include error, warning and info problems.
     * 
     * @param destinationId
     *            id of the destination for which to get the markers (issues)
     * @param includeMarkerSubTypes
     *            <code>true</code> if the sub types of the validation marker
     *            should be included (this would be the case if a custom
     *            validation type is used in the validation provider).
     * @param resource
     *            the resource for which to get the problem markers
     * @return
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(String destinationId,
            boolean includeMarkerSubTypes, IResource resource)
            throws CoreException {
        List<IMarker> markers = new ArrayList<IMarker>();
        if (destinationId != null && resource != null && resource.exists()) {
            IMarker[] resMarkers =
                    resource.findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                            includeMarkerSubTypes,
                            IResource.DEPTH_ZERO);

            if (resMarkers != null) {
                for (IMarker marker : resMarkers) {
                    Object destId = marker.getAttribute(IIssue.DESTINATION_ID);
                    if (destinationId.equals(destId)) {
                        markers.add(marker);
                    }
                }
            }
        }
        return markers;
    }

    /**
     * Get the (validation marker type) problem markers for the given
     * destination ids and marker severity.
     * 
     * @param destinationIds
     *            ids of the destination for which to get the markers (issues)
     * @param markerSeverity
     *            one of {@link IMarker#SEVERITY_ERROR},
     *            {@link IMarker#SEVERITY_WARNING} or
     *            {@link IMarker#SEVERITY_INFO}.
     * @param includeMarkerSubTypes
     *            <code>true</code> if the sub types of the validation marker
     *            should be included (this would be the case if a custom
     *            validation type is used in the validation provider).
     * @param resource
     *            the resource for which to get the problem markers
     * @return collection of markers, empty collection if none found
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(
            Collection<String> destinationIds, int markerSeverity,
            boolean includeMarkerSubTypes, IResource resource)
            throws CoreException {

        if (destinationIds != null && resource != null) {
            List<IMarker> markers = new ArrayList<IMarker>();
            for (String id : destinationIds) {
                markers.addAll(getProblemMarkers(id,
                        markerSeverity,
                        includeMarkerSubTypes,
                        resource));
            }
            return markers;
        }

        return getProblemMarkers(destinationIds,
                includeMarkerSubTypes,
                resource);

    }

    /**
     * Get all (validation marker type) problem markers for the given
     * destination ids. This will include error, warning and info problems.
     * 
     * @param destinationIds
     *            ids of the destination for which to get the markers (issues)
     * @param includeMarkerSubTypes
     *            <code>true</code> if the sub types of the validation marker
     *            should be included (this would be the case if a custom
     *            validation type is used in the validation provider).
     * @param resource
     *            the resource for which to get the problem markers
     * @return
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(
            Collection<String> destinationIds, boolean includeMarkerSubTypes,
            IResource resource) throws CoreException {
        if (destinationIds != null && !destinationIds.isEmpty()
                && resource != null && resource.exists()) {
            Set<IMarker> markers = new LinkedHashSet<IMarker>();

            for (String id : destinationIds) {
                markers.addAll(getProblemMarkers(id,
                        includeMarkerSubTypes,
                        resource));
            }
            return markers;
        }

        return new ArrayList<IMarker>(0);
    }

    /**
     * Get all problem markers of the given marker type for the given
     * destination id. This will include error, warning and info problems.
     * 
     * @param markerType
     *            the marker type to look for
     * @param destinationId
     *            id of the destination for which to get the markers (issues)
     * @param resource
     *            the resource for which to get the problem markers
     * @return
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(String markerType,
            String destinationId, IResource resource) throws CoreException {
        List<IMarker> markers = new ArrayList<IMarker>();

        if (markerType != null && destinationId != null && resource != null
                && resource.exists()) {
            IMarker[] findMarkers =
                    resource.findMarkers(markerType,
                            false,
                            IResource.DEPTH_ZERO);
            if (findMarkers != null) {
                for (IMarker marker : markers) {
                    Object destId = marker.getAttribute(IIssue.DESTINATION_ID);
                    if (destinationId.equals(destId)) {
                        markers.add(marker);
                    }
                }
            }

        }

        return markers;
    }

    /**
     * Get the problem markers of the given marker type for the given
     * destination id and marker severity.
     * 
     * @param markerType
     *            the marker type to look for
     * @param destinationId
     *            id of the destination for which to get the markers (issues)
     * @param markerSeverity
     *            one of {@link IMarker#SEVERITY_ERROR},
     *            {@link IMarker#SEVERITY_WARNING} or
     *            {@link IMarker#SEVERITY_INFO}.
     * @param resource
     *            the resource for which to get the problem markers
     * @return collection of markers, empty collection if none found
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(String markerType,
            String destinationId, int markerSeverity, IResource resource)
            throws CoreException {
        List<IMarker> markers = new ArrayList<IMarker>();
        Collection<IMarker> problemMarkers =
                getProblemMarkers(markerType, destinationId, resource);
        for (IMarker marker : problemMarkers) {
            int severity =
                    marker.getAttribute(IMarker.SEVERITY, markerSeverity);
            if (severity == markerSeverity) {
                markers.add(marker);
            }
        }

        return markers;
    }

    /**
     * Get all problem markers of the given marker type for the given
     * destination ids. This will include error, warning and info problems.
     * 
     * @param markerType
     *            the marker type to look for
     * @param destinationIds
     *            ids of the destination for which to get the markers (issues)
     * @param resource
     *            the resource for which to get the problem markers
     * @return
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(String markerType,
            Collection<String> destinationIds, IResource resource)
            throws CoreException {
        List<IMarker> markers = new ArrayList<IMarker>();

        if (markerType != null && destinationIds != null) {
            for (String id : destinationIds) {
                markers.addAll(getProblemMarkers(markerType, id, resource));
            }
        }

        return markers;
    }

    /**
     * Get the problem markers of the given marker type for the given
     * destination ids and marker severity.
     * 
     * @param markerType
     *            the marker type to look for
     * @param destinationIds
     *            ids of the destination for which to get the markers (issues)
     * @param markerSeverity
     *            one of {@link IMarker#SEVERITY_ERROR},
     *            {@link IMarker#SEVERITY_WARNING} or
     *            {@link IMarker#SEVERITY_INFO}.
     * @param resource
     *            the resource for which to get the problem markers
     * @return collection of markers, empty collection if none found
     * @throws CoreException
     */
    public static Collection<IMarker> getProblemMarkers(String markerType,
            Collection<String> destinationIds, int markerSeverity,
            IResource resource) throws CoreException {
        List<IMarker> markers = new ArrayList<IMarker>();
        Collection<IMarker> problemMarkers =
                getProblemMarkers(markerType, destinationIds, resource);
        for (IMarker marker : problemMarkers) {
            int severity =
                    marker.getAttribute(IMarker.SEVERITY, markerSeverity);
            if (severity == markerSeverity) {
                markers.add(marker);
            }
        }

        return markers;
    }

    /**
     * Create a MultiStatus from error markers on a project and its dependent
     * projects.
     * 
     * @param project
     *            the context project.
     * @return Multi-status with sub-statuses for each error markers on the
     *         project (or dependent projects) or {@link Status#OK_STATUS} if
     *         there are no errors.
     */
    public static IStatus getErrorMarkersStatus(IProject project) {
        String topStatusMessage =
                String.format(Messages.ValidationProblemUtil_topError_message,
                        project);
        Set<IProject> referencedProjectsHierarchy = new HashSet<IProject>();
        ProjectUtil.getReferencedProjectsHierarchy(project,
                referencedProjectsHierarchy);
        referencedProjectsHierarchy.add(project);
        MultiStatus multiStatus =
                new MultiStatus(ValidationActivator.PLUGIN_ID, 0, "", null); //$NON-NLS-1$
        for (IProject proj : referencedProjectsHierarchy) {
            if (!proj.isAccessible()) {
                continue;
            }
            IMarker[] markers;
            try {
                markers =
                        proj.findMarkers(null, true, IResource.DEPTH_INFINITE);
            } catch (CoreException e) {
                ValidationActivator.getDefault().getLogger().error(e);
                return new Status(IStatus.ERROR, ValidationActivator.PLUGIN_ID,
                        topStatusMessage);
            }
            for (int i = 0; i < markers.length; i++) {
                IMarker marker = markers[i];
                int severity =
                        marker.getAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_INFO);
                if (severity == IMarker.SEVERITY_ERROR) {
                    String message =
                            marker.getAttribute(IMarker.MESSAGE,
                                    Messages.ValidationProblemUtil_empty_label);
                    String location =
                            marker.getAttribute(IMarker.LOCATION,
                                    Messages.ValidationProblemUtil_empty_label);
                    Status status =
                            new Status(
                                    IStatus.ERROR,
                                    ValidationActivator.PLUGIN_ID,
                                    String.format("%1$s %2$s | %3$s %4$s | %5$s %6$s", //$NON-NLS-1$
                                            Messages.ValidationProblemUtil_project_label,
                                            proj.getName(),
                                            Messages.ValidationProblemUtil_message_label,
                                            message,
                                            Messages.ValidationProblemUtil_location_label,
                                            location));
                    multiStatus.add(status);
                }
            }
        }
        if (multiStatus.getSeverity() == IStatus.ERROR) {
            return new MultiStatus(multiStatus.getPlugin(),
                    multiStatus.getCode(), multiStatus.getChildren(),
                    topStatusMessage, null);
        }
        return Status.OK_STATUS;
    }
}
