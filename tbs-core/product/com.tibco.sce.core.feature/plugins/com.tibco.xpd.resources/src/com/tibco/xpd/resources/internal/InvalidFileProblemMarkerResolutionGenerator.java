/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.eclipse.ui.actions.RenameResourceAction;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Marker resolution generator for an invalid version marker raised in working
 * copies. This will call the migrate in the {@link AbstractWorkingCopy} to
 * migrate the model to the latest version.
 * 
 * @author njpatel
 * @since 3.5
 */
public class InvalidFileProblemMarkerResolutionGenerator implements
        IMarkerResolutionGenerator2 {

    @Override
    public IMarkerResolution[] getResolutions(IMarker marker) {
        if (isInvalidVersionMarker(marker)) {
            return new IMarkerResolution[] { new MigrateResolution(marker) };

        } else if (isInvalidFileNameMarker(marker)) {
            return new IMarkerResolution[] { new RenameResourceResolution(
                    marker) };
        }
        return new IMarkerResolution[0];
    }

    @Override
    public boolean hasResolutions(IMarker marker) {
        /*
         * Check if this is an invalid version or file name marker
         */
        return isInvalidVersionMarker(marker)
                || isInvalidFileNameMarker(marker);
    }

    /**
     * Check if the given marker is an invalid version marker.
     * 
     * @param marker
     * @return <code>true</code> if it is.
     */
    private boolean isInvalidVersionMarker(IMarker marker) {
        try {
            if (marker != null && marker.exists()) {
                Object value =
                        marker.getAttribute(AbstractWorkingCopy.MARKER_VERSIONISSUE);

                if (value instanceof Boolean && ((Boolean) value)) {
                    IResource resource = marker.getResource();
                    if (resource != null) {
                        WorkingCopy wc =
                                WorkingCopyUtil.getWorkingCopy(resource);
                        return wc instanceof AbstractWorkingCopy;
                    }
                }
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);

        }
        return false;
    }

    /**
     * Check if the given marker is an invalid file name marker.
     * 
     * @param marker
     * @return <code>true</code> if it is.
     */
    private boolean isInvalidFileNameMarker(IMarker marker) {
        try {
            if (marker != null && marker.exists()) {
                Object value =
                        marker.getAttribute(AbstractWorkingCopy.MARKER_NAMEISSUE);

                if (value instanceof Boolean && ((Boolean) value)) {
                    IResource resource = marker.getResource();
                    if (resource != null) {
                        WorkingCopy wc =
                                WorkingCopyUtil.getWorkingCopy(resource);
                        return wc instanceof AbstractWorkingCopy;
                    }
                }
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);

        }
        return false;
    }

    /**
     * Migration resolution to migrate a working copy to the latest version.
     */
    private class MigrateResolution extends WorkbenchMarkerResolution {

        private final IMarker marker;

        public MigrateResolution(IMarker marker) {
            this.marker = marker;
        }

        @Override
        public IMarker[] findOtherMarkers(IMarker[] markers) {
            List<IMarker> otherMarkers = new ArrayList<IMarker>();

            for (IMarker marker : markers) {
                try {
                    if (this.marker != marker
                            && marker.exists()
                            && XpdConsts.INVALID_FILE_MARKER.equals(marker
                                    .getType())
                            && isInvalidVersionMarker(marker)) {
                        otherMarkers.add(marker);
                    }
                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
            }

            return otherMarkers.toArray(new IMarker[otherMarkers.size()]);
        }

        @Override
        public String getDescription() {
            return Messages.InvalidFileProblemMarkerResolutionGenerator_MigrateModelDescription_message;
        }

        @Override
        public Image getImage() {
            return null;
        }

        @Override
        public String getLabel() {
            return Messages.InvalidFileProblemMarkerResolutionGenerator_MigrateModel_label;
        }

        @Override
        public void run(IMarker marker) {
            if (marker != null && marker.exists()) {
                try {
                    WorkingCopy wc =
                            WorkingCopyUtil
                                    .getWorkingCopy(marker.getResource());
                    if (wc instanceof AbstractWorkingCopy) {
                        ((AbstractWorkingCopy) wc).migrate();
                    }
                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                    ErrorDialog
                            .openError(XpdResourcesPlugin.getStandardDisplay()
                                    .getActiveShell(),
                                    Messages.InvalidFileProblemMarkerResolutionGenerator_MigrationError_title,
                                    String.format(Messages.InvalidFileProblemMarkerResolutionGenerator_MigrateError_message,
                                            marker.getResource().getFullPath()
                                                    .toString()),
                                    new Status(IStatus.ERROR,
                                            XpdResourcesPlugin.ID_PLUGIN, e
                                                    .getLocalizedMessage(), e));
                }
            }
        }
    }

    /**
     * Rename resource resolution - pops up rename dialog and renames resoruce.
     * 
     * @author aallway
     * @since v3.5.3
     */
    private class RenameResourceResolution extends WorkbenchMarkerResolution
            implements IShellProvider {

        private final IMarker marker;

        public RenameResourceResolution(IMarker marker) {
            this.marker = marker;
        }

        @Override
        public IMarker[] findOtherMarkers(IMarker[] markers) {
            return new IMarker[0];
        }

        @Override
        public String getDescription() {
            return Messages.InvalidFileProblemMarkerResolutionGenerator_RenameFile_label;
        }

        @Override
        public Image getImage() {
            return null;
        }

        @Override
        public String getLabel() {
            return Messages.InvalidFileProblemMarkerResolutionGenerator_RenameFile_label;
        }

        @Override
        public void run(IMarker marker) {
            if (marker != null && marker.exists()) {

                IResource resource = marker.getResource();
                if (resource != null && resource.exists()) {

                    RenameResourceAction action =
                            new RenameResourceAction(this);

                    action.selectionChanged(new StructuredSelection(resource));

                    action.run();
                }
            }
        }

        /**
         * @see org.eclipse.jface.window.IShellProvider#getShell()
         * 
         * @return
         */
        @Override
        public Shell getShell() {
            return Display.getDefault().getActiveShell();
        }
    }

}
