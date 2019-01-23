/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.deploy.implementationprovider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import com.tibco.bx.composite.xpdl.core.it.BxGlobalSignalImplementationTypeProvider;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Global Signal Implementation type provider.
 * 
 * @author kthombar
 * @since Mar 12, 2015
 */
public class GlobalSignalImplementationTypeProvider extends
        BxGlobalSignalImplementationTypeProvider {

    /**
     * @see com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider#isSupported(java.lang.String)
     * 
     * @param path
     * @return
     */
    @Override
    public boolean isSupported(String path) {

        IFolder folder =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFolder(new Path(path));

        if (folder != null) {
            String specialFolderKind =
                    SpecialFolderUtil.getSpecialFolderKind(folder);

            if (GsdConstants.GSD_SPECIAL_FOLDER_KIND.equals(specialFolderKind)) {
                /*
                 * return true if the passed path is to GSD Special folder.
                 */
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.bx.composite.xpdl.core.it.BxGlobalSignalImplementationTypeProvider#getGlobalSignalDefinitions(org.eclipse.core.resources.IFolder)
     * 
     * @param folder
     * @return
     */
    @Override
    public Collection<GlobalSignalDefinitions> getGlobalSignalDefinitions(
            IProject project) {

        List<GlobalSignalDefinitions> globalSignalDefinitions =
                new ArrayList<GlobalSignalDefinitions>();

        if (project != null) {
            /*
             * get all the gsd reources in the project
             */
            List<IResource> gsdResources =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(project,
                                    GsdConstants.GSD_SPECIAL_FOLDER_KIND,
                                    GsdConstants.GSD_FILE_EXTENSION,
                                    false);

            if (gsdResources != null && !gsdResources.isEmpty()) {

                for (IResource iResource : gsdResources) {

                    WorkingCopy workingCopy =
                            WorkingCopyUtil.getWorkingCopy(iResource);
                    if (workingCopy != null) {

                        if (workingCopy.getRootElement() instanceof GlobalSignalDefinitions) {
                            /*
                             * Getb the working copy , fetch the root element
                             * and add it to the list.
                             */
                            globalSignalDefinitions
                                    .add((GlobalSignalDefinitions) workingCopy
                                            .getRootElement());
                        }
                    }
                }
            }
        }
        return globalSignalDefinitions;
    }
}
