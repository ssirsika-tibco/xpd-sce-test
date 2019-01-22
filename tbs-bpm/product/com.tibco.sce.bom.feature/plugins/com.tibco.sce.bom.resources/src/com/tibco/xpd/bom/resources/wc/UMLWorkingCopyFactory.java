/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.wc;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Working copy factory for UML models that are contained in a BOM special
 * folder.
 * 
 * @author njpatel
 * 
 */
public class UMLWorkingCopyFactory implements WorkingCopyFactory {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse.core.resources.IResource)
     */
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new UMLWorkingCopy(Collections.singletonList(resource));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core.resources.IResource)
     */
    public boolean isFactoryFor(IResource resource) {
        boolean isFactory = false;

        if (resource instanceof IFile) {
            String ext = ((IFile) resource).getFileExtension();

            isFactory = ext.equalsIgnoreCase(UMLResource.FILE_EXTENSION);

            if (isFactory) {
                // Confirm that file is in BOM special folder
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(resource.getProject());

                if (config != null) {
                    SpecialFolder sf = config.getSpecialFolders()
                            .getFolderContainer(resource);

                    // Return false if resource is not contained in a special
                    // folder or is contained in a non-BOM special folder
                    isFactory = sf != null
                            && sf.getKind().equals(
                                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
                }
            }
        }

        return isFactory;
    }

}
