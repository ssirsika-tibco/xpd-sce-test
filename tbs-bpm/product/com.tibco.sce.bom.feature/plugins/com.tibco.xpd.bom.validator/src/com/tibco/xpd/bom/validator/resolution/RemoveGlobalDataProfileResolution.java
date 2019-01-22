/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.resolution;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to remove global data profile on boms in a non-business data
 * project
 * 
 * 
 * @author bharge
 * @since 10 Dec 2013
 */
public class RemoveGlobalDataProfileResolution implements IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {

        IResource resource = marker.getResource();
        if (resource instanceof IFile) {

            IFile bomFile = (IFile) resource;
            if (BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(bomFile
                    .getFileExtension())) {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
                boolean isWorkingCopyDirty = wc.isWorkingCopyDirty();
                if (wc instanceof BOMWorkingCopy) {

                    final Model model = (Model) wc.getRootElement();
                    if (null != model) {

                        UnprotectedWriteOperation op =
                                new UnprotectedWriteOperation(
                                        XpdResourcesPlugin.getDefault()
                                                .getEditingDomain()) {

                                    @Override
                                    protected Object doExecute() {
                                        Profile globalDataProfile =
                                                GlobalDataProfileManager
                                                        .getInstance()
                                                        .getProfile();
                                        if (null != model
                                                && model.getAppliedProfiles()
                                                        .contains(globalDataProfile)) {

                                            model.unapplyProfile(globalDataProfile);
                                        }
                                        return model;
                                    }
                                };

                        op.execute();
                    }

                    if (!isWorkingCopyDirty) {

                        try {

                            wc.save();
                        } catch (IOException e) {

                            BOMValidatorActivator
                                    .getLogger()
                                    .error("Could not save model after removing the global data profile " //$NON-NLS-1$
                                            + e.toString());
                        }
                    }
                }
            }
        }
    }

}
