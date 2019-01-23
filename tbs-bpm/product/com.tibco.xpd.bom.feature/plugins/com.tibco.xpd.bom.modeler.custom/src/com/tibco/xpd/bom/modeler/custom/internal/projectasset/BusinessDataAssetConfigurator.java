/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.projectasset;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.internal.businessdata.BusinessDataProjectNature;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Asset configurator for the Business Data Asset.
 * 
 * @author njpatel
 */
public class BusinessDataAssetConfigurator implements IAssetConfigurator {

    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {

        // Apply the Business Data nature
        ProjectUtil.addNature(project, BusinessDataProjectNature.ID);

        /*
         * get the list of bom files in the project for which global data
         * profile needs applied. theoretically there should be only one bom as
         * this comes from new business data project wizard
         */
        final List<IResource> resources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                BOMResourcesPlugin.BOM_FILE_EXTENSION,
                                true);

        final Set<WorkingCopy> workingCopiesToSave = new HashSet<WorkingCopy>();

        final UnprotectedWriteOperation op =
                new UnprotectedWriteOperation(XpdResourcesPlugin.getDefault()
                        .getEditingDomain()) {

                    @Override
                    protected Object doExecute() {
                        for (IResource res : resources) {
                            WorkingCopy wc =
                                    WorkingCopyUtil.getWorkingCopy(res);
                            if (wc instanceof BOMWorkingCopy) {
                                boolean isDirty = wc.isWorkingCopyDirty();
                                if (applyGlobalDataProfile((BOMWorkingCopy) wc)
                                        && !isDirty) {
                                    workingCopiesToSave.add(wc);
                                }
                            }
                        }
                        return null;
                    }
                };

        op.execute();

        if (!workingCopiesToSave.isEmpty()) {
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    for (WorkingCopy wc : workingCopiesToSave) {
                        try {
                            wc.save();
                        } catch (IOException e) {
                            Activator
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            String.format("Business Data asset configurator failed to save working copy for: %s", //$NON-NLS-1$
                                                    wc.getEclipseResources()
                                                            .get(0)
                                                            .getFullPath()
                                                            .toString()));
                        }
                    }
                }
            },
                    null,
                    IWorkspace.AVOID_UPDATE,
                    null);
        }

    }

    /**
     * applies the global data profile for the given bom working copy
     * 
     * @param wc
     * @return <code>true</code> if the profile was applied.
     */
    private boolean applyGlobalDataProfile(BOMWorkingCopy wc) {

        final Model model = (Model) wc.getRootElement();

        if (model != null) {
            return GlobalDataProfileManager.getInstance()
                    .applyGlobalDataProfile(model);
        }

        return false;
    }

}
