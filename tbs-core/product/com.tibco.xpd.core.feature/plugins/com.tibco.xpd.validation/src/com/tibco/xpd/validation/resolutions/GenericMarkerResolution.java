/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.resolutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * Wrapper class for resolutions that use the GenericResolutionGenerator. This
 * class should not be used directly. Instances are created from the contents of
 * the <b>resolution</b> extension point.
 * 
 * @author nwilson
 */
public class GenericMarkerResolution extends WorkbenchMarkerResolution {

    /** The resolution to use. */
    private IResolution resolution;

    /** The resolution label. */
    private String label;

    /** The resolution description. */
    private String description;

    /** The resolution image key. */
    private String imageKey;

    /** The resolution image. */
    private Image image;

    /** The logger. */
    private Logger log;

    private final String markerType;

    private IMarker marker;

    private final boolean canApplyToMultiple;

    private IProgressMonitor monitor;

    /**
     * @param markerType
     *            The marker type that this resolution corresponds to
     * @param label
     *            The resolution label.
     * @param resolution
     *            The resolution.
     * @param applyToMultiple
     *            <code>true</code> if this resolution can be applied to
     *            multiple (similar) validation problems.
     */
    public GenericMarkerResolution(String markerType, String label,
            IResolution resolution, boolean applyToMultiple) {
        this.markerType = markerType;
        this.label = label;
        this.resolution = resolution;
        this.canApplyToMultiple = applyToMultiple;
        log = ValidationActivator.getDefault().getLogger();
    }

    /**
     * @return The resolution description, or null if no description is defined.
     * @see org.eclipse.ui.IMarkerResolution2#getDescription()
     */
    public String getDescription() {
        if (resolution instanceof IConfigurableResolutionLabel
                && marker != null) {
            String s =
                    ((IConfigurableResolutionLabel) resolution)
                            .getConfigurableResolutionDescription(label == null ? "" : label, marker); //$NON-NLS-1$
            if (s != null && s.length() > 0) {
                return s;
            }
        }
        return description;
    }

    /**
     * @return The resolution image, or null if none is defined.
     * @see org.eclipse.ui.IMarkerResolution2#getImage()
     */
    public Image getImage() {
        if (image == null) {
            if (imageKey != null) {
                image =
                        ValidationActivator.getDefault().getImageRegistry()
                                .get(imageKey);
            }
        }
        return image;
    }

    /**
     * @return The label for the resolution.
     * @see org.eclipse.ui.IMarkerResolution#getLabel()
     */
    public String getLabel() {
        if (resolution instanceof IConfigurableResolutionLabel
                && marker != null) {

            String s =
                    ((IConfigurableResolutionLabel) resolution)
                            .getConfigurableResolutionLabel(label == null ? "" : label, marker); //$NON-NLS-1$
            if (s != null && s.length() > 0) {
                return s;
            }
        }
        return label;
    }

    @Override
    public void run(final IMarker[] markers, IProgressMonitor monitor) {
        // Collect all resources that may be affected by the resolution
        Set<IResource> resources = new HashSet<IResource>();
        for (IMarker marker : markers) {
            if (marker.exists()) {
                IResource resource = marker.getResource();
                if (resource != null && resource.exists()) {
                    resources.add(resource);
                }
            }
        }

        /*
         * Keep track of all working copies that may be affected by this
         * resolution and save them (if not dirty at this stage) after the
         * resolution
         */
        final Set<WorkingCopy> wcToSave = new HashSet<WorkingCopy>();
        for (IResource resource : resources) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);
            if (wc != null && !wc.isWorkingCopyDirty()) {
                wcToSave.add(wc);
            }
        }

        // Keep the copy of the monitor so that we can show progress in the
        // run(IMarker) method below.
        this.monitor = monitor;
        // Initialise the resolution before multiple problems are resolved
        if (resolution instanceof AbstractWorkingCopyResolution) {
            ((AbstractWorkingCopyResolution) resolution).init();
        }
        try {
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

                public void run(IProgressMonitor monitor) throws CoreException {
                    GenericMarkerResolution.super.run(markers, monitor);

                    // Save all working copies
                    for (WorkingCopy wc : wcToSave) {
                        if (wc.isWorkingCopyDirty()) {
                            try {
                                wc.save();
                            } catch (IOException e) {
                                ValidationActivator
                                        .getDefault()
                                        .getLogger()
                                        .error(e,
                                                String.format(Messages.GenericMarkerResolution_workingCopySaveFailedAfterQuickFix_error_longdesc,
                                                        wc.getName()));
                            }
                        }
                    }
                }

            },
                    monitor);
        } catch (CoreException e) {
            ValidationActivator
                    .getDefault()
                    .getLogger()
                    .error(e,
                            Messages.GenericMarkerResolution_errorsDuringQuickFix_error_message);
        } finally {
            /*
             * SID XPD-4053.Must set monitor to null after we've finished
             * otherwise a subsequent quick fix via tooltip etc will find a
             * monitor that's been disposed!
             */
            this.monitor = null;
        }

    }

    /**
     * @param marker
     *            The problem marker.
     * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
     */
    public void run(IMarker marker) {
        try {
            resolution.run(marker);
            if (monitor != null) {
                monitor.worked(1);
            }
        } catch (ResolutionException e) {
            log.error(e);
            MessageDialog.openError(null,
                    Messages.GenericMarkerResolution_0,
                    e.getMessage());
        }
    }

    /**
     * @param description
     *            The resolution description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param imageKey
     *            The resolution image key.
     */
    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    /**
     * Set the marker that is being resolved by this resolution. This marker
     * will subsequently be used to identify any similar markers that can be
     * resolved by this resolution.
     * 
     * @param marker
     */
    public void setMarkerToResolve(IMarker marker) {
        this.marker = marker;
    }

    @Override
    public IMarker[] findOtherMarkers(IMarker[] markers) {
        List<IMarker> markersToAdd = new ArrayList<IMarker>();
        if (markers != null && canApplyToMultiple) {
            String destId = getDestinationEnv(marker);
            for (IMarker m : markers) {
                try {
                    if (m != null && m.exists() && !m.equals(marker)) {
                        Object attObj = m.getAttribute(IIssue.ID);
                        if (attObj != null && attObj.equals(markerType)) {
                            attObj = getDestinationEnv(m);
                            // If the primary marker's destination env. matches
                            // this marker's then add to list
                            if ((destId == null && attObj == null)
                                    || (destId != null && destId.equals(attObj))) {
                                markersToAdd.add(m);
                            }
                        }
                    }
                } catch (CoreException e) {
                    ValidationActivator.getDefault().getLogger().error(e);
                }
            }
        }

        return markersToAdd.toArray(new IMarker[markersToAdd.size()]);
    }

    /**
     * Get the destination environment id from the given marker.
     * 
     * @param marker
     *            <code>IMarker</code>.
     * @return destination environment id if the marker has one,
     *         <code>null</code> otherwise.
     */
    private String getDestinationEnv(IMarker marker) {
        String envId = null;

        if (marker != null) {
            try {
                envId = (String) marker.getAttribute(IIssue.DESTINATION_ID);
            } catch (CoreException e) {
                ValidationActivator.getDefault().getLogger().error(e);
            }
        }

        return envId;
    }

    /**
     * Final chance for the marker resolution to dynamically allow itself to be
     * applicable for the given marker.
     * 
     * @param marker
     * @return true if resolution should be filtered in.
     * 
     * @since 3.2
     */
    boolean select(IMarker marker) {
        if (resolution instanceof IFilter) {
            return ((IFilter) resolution).select(marker);
        }
        return true;
    }

}
