/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.assets;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;

/**
 * Business asset project asset configurator. This will create a Business Assets
 * folder in the given project.
 * 
 * @author njpatel
 * 
 */
public class BusinessAssetConfigurator implements IAssetConfigurator {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator#configure(org.eclipse.core.resources.IProject,
     *      java.lang.Object)
     */
    public void configure(IProject project, Object configuration)
            throws CoreException {

        if (configuration instanceof BusinessAssetsConfiguration) {
            String folderName = ((BusinessAssetsConfiguration) configuration)
                    .getFolderName();

            if (folderName != null && folderName.length() > 0) {
                // Create a business assets folder in the project
                IFolder folder = project.getFolder(folderName);

                if (!folder.exists()) {
                    folder.create(false, true, null);
                }
            }
        } else {
            throw new IllegalArgumentException("Wrong configuration received."); //$NON-NLS-1$
        }

    }

}
