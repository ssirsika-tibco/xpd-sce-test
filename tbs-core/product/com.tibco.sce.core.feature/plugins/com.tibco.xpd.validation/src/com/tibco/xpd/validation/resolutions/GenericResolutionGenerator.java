/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.resolutions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.internal.engine.Issue;

/**
 * A generic resolution generator that uses the resolutions defined in the
 * <code>com.tibco.xpd.validation.resolution</code> extension point and returns
 * all of the resolutions that are associated with the issue id.
 * 
 * @author nwilson
 */
public class GenericResolutionGenerator implements IMarkerResolutionGenerator2 {

    /**
     * @param marker
     *            The problem marker.
     * @return An array of resolutions.
     * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
     */
    public IMarkerResolution[] getResolutions(IMarker marker) {
        IMarkerResolution[] resolutions = new IMarkerResolution[0];
        try {
            String id = (String) marker.getAttribute(Issue.ID);
            if (id != null) {
                resolutions =
                        ValidationManager.getInstance().getResolutions(id);

                if (resolutions != null) {
                    List<IMarkerResolution> filtered =
                            new ArrayList<IMarkerResolution>();

                    for (IMarkerResolution markerResolution : resolutions) {

                        if (markerResolution instanceof GenericMarkerResolution) {
                            GenericMarkerResolution gmr =
                                    (GenericMarkerResolution) markerResolution;

                            if (!gmr.select(marker)) {
                                // Filter out GenericMarkerResolutions that do
                                // not want to be included - for this marker at
                                // this time.
                                continue;

                            } else {
                                // Set marker to resolve for
                                // GenericMarkerResolution
                                gmr.setMarkerToResolve(marker);
                            }
                        }

                        /*
                         * Sid XPD-2151. {@link GenericMarkerResolution}'s are
                         * cached so that the same class instance is returned
                         * for all markers.
                         * 
                         * Therefore we need to wrap the one instance of
                         * resolution in a separate wrapper class instance for
                         * each individual marker we are called for so that the
                         * label / description are cached and therefore
                         * appropriately personalised to a given marker (when
                         * that description contains variable info specific to
                         * each instance of marker).
                         */
                        IMarkerResolution cacheLabelWrapper;

                        if (markerResolution instanceof WorkbenchMarkerResolution) {
                            cacheLabelWrapper =
                                    new CachedLabelWorkbenchMarkerResolution(
                                            (WorkbenchMarkerResolution) markerResolution);
                        } else if (markerResolution instanceof IMarkerResolution2) {
                            cacheLabelWrapper =
                                    new CachedLabelMarkerResolution2(
                                            (IMarkerResolution2) markerResolution);
                        } else {
                            cacheLabelWrapper =
                                    new CachedLabelMarkerResolution(
                                            markerResolution);
                        }

                        filtered.add(cacheLabelWrapper);
                    }

                    resolutions =
                            filtered.toArray(new IMarkerResolution[filtered
                                    .size()]);
                }
            }
        } catch (CoreException e) {
            // Ignore.
        }
        return resolutions;
    }

    /**
     * @param marker
     *            The resolution marker.
     * @return true if there are resolutions.
     * @see org.eclipse.ui.IMarkerResolutionGenerator2#hasResolutions(org.eclipse.core.resources.IMarker)
     */
    public boolean hasResolutions(IMarker marker) {
        boolean hasResolutions = false;
        try {
            String id = (String) marker.getAttribute(Issue.ID);
            if (id != null) {
                hasResolutions =
                        ValidationManager.getInstance().getResolutions(id).length > 0;
            }
        } catch (CoreException e) {
            // Ignore.
        }
        return hasResolutions;
    }

    /**
     * Sid XPD-2151. {@link GenericMarkerResolution}'s are cached so that the
     * same class instance is returned for all markers.
     * <p>
     * This is a problem for the resolutions that
     * {@link GenericMarkerResolution} contains which very their description
     * based on the marker for which they are being used (i.e. use additional
     * info associated with the marker to add variable data to the resolution
     * label / description).
     * <p>
     * In that case when resolutions for mutliple markers are accessed
     * simultaneously, a resolution used for multiple markers will all get the
     * same description (according the the last marker set on the one resolution
     * instance).
     * <p>
     * This wrapper class delegates to the underlying IMarkerResolution but
     * caches the label and description etc immediately so that when the marker
     * is reset in the underlying resolution it will not effect this instance of
     * the resolution for its marker.
     * 
     * @author aallway
     * @since 3.5.2
     */
    private static class CachedLabelMarkerResolution implements
            IMarkerResolution {
        private IMarkerResolution delegateResolution = null;

        private String label;

        /**
         * @param delegateResolution
         */
        public CachedLabelMarkerResolution(IMarkerResolution delegateResolution) {
            super();
            this.delegateResolution = delegateResolution;
            cacheInfo();
        }

        /**
         * Cache the label information (which may change depending on the
         * marker) for the currently set marker.
         */
        private void cacheInfo() {
            label = delegateResolution.getLabel();
        }

        /**
         * @return
         * @see org.eclipse.ui.IMarkerResolution#getLabel()
         */
        public String getLabel() {
            if (label != null) {
                return label;
            }
            return delegateResolution.getLabel();
        }

        /**
         * @param marker
         * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
         */
        public void run(IMarker marker) {
            delegateResolution.run(marker);
        }

    }

    /**
     * Sid XPD-2151. {@link GenericMarkerResolution}'s are cached so that the
     * same class instance is returned for all markers.
     * <p>
     * This is a problem for the resolutions that
     * {@link GenericMarkerResolution} contains which very their description
     * based on the marker for which they are being used (i.e. use additional
     * info associated with the marker to add variable data to the resolution
     * label / description).
     * <p>
     * In that case when resolutions for mutliple markers are accessed
     * simultaneously, a resolution used for multiple markers will all get the
     * same description (according the the last marker set on the one resolution
     * instance).
     * <p>
     * This wrapper class delegates to the underlying IMarkerResolution but
     * caches the label and description etc immediately so that when the marker
     * is reset in the underlying resolution it will not effect this instance of
     * the resolution for its marker.
     * 
     * @author aallway
     * @since 3.5.2
     */
    private static class CachedLabelWorkbenchMarkerResolution extends
            WorkbenchMarkerResolution {
        private WorkbenchMarkerResolution delegateResolution = null;

        private String label;

        private String description;

        private Image image;

        /**
         * @param delegateResolution
         */
        public CachedLabelWorkbenchMarkerResolution(
                WorkbenchMarkerResolution delegateResolution) {
            super();
            this.delegateResolution = delegateResolution;
            cacheInfo();
        }

        /**
         * Cache the label, description,image information (which may change
         * depending on the marker) for the currently set marker.
         */
        private void cacheInfo() {
            label = delegateResolution.getLabel();
            description = delegateResolution.getDescription();
            image = delegateResolution.getImage();
        }

        /**
         * @return
         * @see org.eclipse.ui.IMarkerResolution#getLabel()
         */
        public String getLabel() {
            if (label != null) {
                return label;
            }
            return delegateResolution.getLabel();
        }

        /**
         * @return
         * @see org.eclipse.ui.IMarkerResolution2#getDescription()
         */
        public String getDescription() {
            if (description != null) {
                return description;
            }
            return delegateResolution.getDescription();
        }

        /**
         * @see org.eclipse.ui.views.markers.WorkbenchMarkerResolution#run(org.eclipse.core.resources.IMarker[],
         *      org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param markers
         * @param monitor
         */
        @Override
        public void run(IMarker[] markers, IProgressMonitor monitor) {
            delegateResolution.run(markers, monitor);
        }

        /**
         * @param marker
         * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
         */
        public void run(IMarker marker) {
            delegateResolution.run(marker);
        }

        /**
         * @return
         * @see org.eclipse.ui.IMarkerResolution2#getImage()
         */
        public Image getImage() {
            if (image != null) {
                return image;
            }
            return delegateResolution.getImage();
        }

        /**
         * @see org.eclipse.ui.views.markers.WorkbenchMarkerResolution#findOtherMarkers(org.eclipse.core.resources.IMarker[])
         * 
         * @param markers
         * @return
         */
        @Override
        public IMarker[] findOtherMarkers(IMarker[] markers) {
            return delegateResolution.findOtherMarkers(markers);
        }

    }

    /**
     * Sid XPD-2151. {@link GenericMarkerResolution}'s are cached so that the
     * same class instance is returned for all markers.
     * <p>
     * This is a problem for the resolutions that
     * {@link GenericMarkerResolution} contains which very their description
     * based on the marker for which they are being used (i.e. use additional
     * info associated with the marker to add variable data to the resolution
     * label / description).
     * <p>
     * In that case when resolutions for mutliple markers are accessed
     * simultaneously, a resolution used for multiple markers will all get the
     * same description (according the the last marker set on the one resolution
     * instance).
     * <p>
     * This wrapper class delegates to the underlying IMarkerResolution but
     * caches the label and description etc immediately so that when the marker
     * is reset in the underlying resolution it will not effect this instance of
     * the resolution for its marker.
     * 
     * @author aallway
     * @since 3.5.2
     */
    private static class CachedLabelMarkerResolution2 implements
            IMarkerResolution2 {
        private IMarkerResolution2 delegateResolution = null;

        private String label;

        private String description;

        private Image image;

        /**
         * @param delegateResolution
         */
        public CachedLabelMarkerResolution2(
                IMarkerResolution2 delegateResolution) {
            super();
            this.delegateResolution = delegateResolution;
            cacheInfo();
        }

        /**
         * Cache the label, description,image information (which may change
         * depending on the marker) for the currently set marker.
         */
        private void cacheInfo() {
            label = delegateResolution.getLabel();
            description = delegateResolution.getDescription();
            image = delegateResolution.getImage();
        }

        /**
         * @return
         * @see org.eclipse.ui.IMarkerResolution#getLabel()
         */
        public String getLabel() {
            if (label != null) {
                return label;
            }
            return delegateResolution.getLabel();
        }

        /**
         * @return
         * @see org.eclipse.ui.IMarkerResolution2#getDescription()
         */
        public String getDescription() {
            if (description != null) {
                return description;
            }
            return delegateResolution.getDescription();
        }

        /**
         * @param marker
         * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
         */
        public void run(IMarker marker) {
            delegateResolution.run(marker);
        }

        /**
         * @return
         * @see org.eclipse.ui.IMarkerResolution2#getImage()
         */
        public Image getImage() {
            if (image != null) {
                return image;
            }
            return delegateResolution.getImage();
        }

    }
}
