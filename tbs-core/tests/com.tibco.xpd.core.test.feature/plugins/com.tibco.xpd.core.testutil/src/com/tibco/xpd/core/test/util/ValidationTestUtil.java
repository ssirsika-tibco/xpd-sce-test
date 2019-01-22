/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * ValidationTestUtil
 * 
 * 
 * @author aallway
 * @since 3.3 (24 Jul 2009)
 */
public class ValidationTestUtil {

    /**
     * Get a List of IMarkers that indicate an ERROR type problem.
     * 
     * @param resource
     *            An XPDL file or project that contains the markers
     * @return A List of IMarker that have severity ERROR
     */
    public static List<IMarker> getProblemMarkers(IResource resource) {
        List<IMarker> markerList = new ArrayList<IMarker>();
        try {
            IMarker[] findMarkers =
                    resource.findMarkers(IMarker.PROBLEM,
                            true,
                            IResource.DEPTH_ZERO);
            // Enumerate the markers and find the markers that indicate Error
            for (IMarker marker : findMarkers) {
                // System.out.println(marker.getAttribute("destinationId"));
                markerList.add(marker);
            }// end for
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return markerList;
    }

    /**
     * Get a List of IMarkers that indicate an ERROR type problem and are
     * specific to the specified destination.
     * 
     * @param resource
     *            An XPDL file or project that contains the markers
     * @param destination
     *            The destination of the rule being validated. Destination can
     *            be iPE or iPM or BPMN
     * 
     * @return A List of IMarker that have severity ERROR
     */
    public static List<IMarker> getProblemMarkersByDest(IResource resource,
            String destination) {
        List<IMarker> markerList = new ArrayList<IMarker>();
        try {
            IMarker[] findMarkers =
                    resource.findMarkers(IMarker.PROBLEM,
                            true,
                            IResource.DEPTH_ZERO);
            // Enumerate the markers and find the markers that indicate Error
            for (IMarker marker : findMarkers) {
                // Now check the DestinationId of the marker
                // and only return markers for specified destination
                String destId = (String) marker.getAttribute("destinationId"); //$NON-NLS-1$
                if (destId != null && destId.indexOf(destination) > -1) {
                    markerList.add(marker);
                }
            }// end for

        } catch (CoreException e) {
            e.printStackTrace();
        }
        return markerList;
    }
}
