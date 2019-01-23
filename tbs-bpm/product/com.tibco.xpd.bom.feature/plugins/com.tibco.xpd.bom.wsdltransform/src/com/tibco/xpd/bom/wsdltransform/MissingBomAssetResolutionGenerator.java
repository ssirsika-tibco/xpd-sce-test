package com.tibco.xpd.bom.wsdltransform;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.ui.projectexplorer.actions.specialfolder.AssetConfigAction;

/**
 * Supply resolution for project requiring the BOM asset but it being missing -
 * deemed to be required but missing due to an attempt to auto-generate BOMs
 * 
 * @author patkinso
 * @since 21 Aug 2012
 */
public class MissingBomAssetResolutionGenerator implements
        IMarkerResolutionGenerator2 {

    /**
     * Runs special folder asset wizard: will ensure BOM asset is added to
     * project
     */
    private class AddBomAssetResolution extends WorkbenchMarkerResolution {

        /**
         * @see org.eclipse.ui.IMarkerResolution2#getDescription()
         * 
         * @return
         */
        public String getDescription() {
            return Messages.MissingBomAssetResolutionGenerator_AddBOMfolderResolution_label;
        }

        /**
         * @see org.eclipse.ui.IMarkerResolution2#getImage()
         * 
         * @return
         */
        public Image getImage() {
            return null;
        }

        /**
         * @see org.eclipse.ui.IMarkerResolution#getLabel()
         * 
         * @return
         */
        public String getLabel() {
            return Messages.MissingBomAssetResolutionGenerator_AddBOMfolderResolution_label;
        }

        /**
         * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
         * 
         * @param marker
         */
        public void run(IMarker marker) {

            if (marker != null && marker.exists()
                    && (marker.getResource() instanceof IProject)) {

                Shell shell =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();

                IProject proj = (IProject) marker.getResource();
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault().getProjectConfig(proj);
                String bomAssetId =
                        com.tibco.xpd.bom.resources.ui.Activator.BOM_ASSET_ID;
                IProjectAsset bomAsset = config.getAssetById(bomAssetId);

                // find kind for bom asset
                Iterator<ISpecialFolderModel> it =
                        config.getSpecialFolders().getFolderKindInfo()
                                .iterator();
                ISpecialFolderModel info = null;
                boolean found;
                for (found = false; (it.hasNext() && !found);) {
                    info = it.next();
                    if ((info.getProjectAssetId() != null)
                            && (info.getProjectAssetId().equals(bomAsset
                                    .getId()))) {
                        found = true;
                    }
                }

                // run wizard
                if (found) {

                    AssetConfigAction action =
                            new AssetConfigAction(shell, null, null, info,
                                    config.getAssetById(info
                                            .getProjectAssetId()));

                    // set default special folder to a generated BOMs folder
                    IFolder bomFolder =
                            proj.getFolder(Messages.default_bomfolder_name);
                    List<IFolder> selection =
                            Arrays.asList(new IFolder[] { bomFolder });
                    action.updateSelection(new StructuredSelection(selection));
                    action.run();

                    try {
                        proj.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException e) {
                    }
                }
            }

        }

        /**
         * @see org.eclipse.ui.views.markers.WorkbenchMarkerResolution#findOtherMarkers(org.eclipse.core.resources.IMarker[])
         * 
         * @param markers
         * @return
         */
        @Override
        public IMarker[] findOtherMarkers(IMarker[] markers) {
            return new IMarker[0];
        }

    }

    /**
     * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @return
     */
    public IMarkerResolution[] getResolutions(IMarker marker) {

        return new IMarkerResolution[] { new AddBomAssetResolution() };
    }

    /**
     * @see org.eclipse.ui.IMarkerResolutionGenerator2#hasResolutions(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @return
     */
    public boolean hasResolutions(IMarker marker) {
        return true;
    }

}
