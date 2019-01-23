/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rest.schema.validator;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.rest.schema.JsonSchemaWorkingCopy;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * Filter for JSD files in a REST Special Folder.
 * 
 * @author nwilson
 * @since 12 Mar 2015
 */
public class JsonSchemaFileFilter implements IFileFilter {

    /**
     * @see com.tibco.xpd.validation.provider.IFileFilter
     *      #accept(org.eclipse.core.resources.IFile)
     * 
     * @param file
     *            The file to check.
     * @return true if it is a JSD file in a REST special folder.
     */
    @Override
    public boolean accept(IFile file) {
        boolean ok = false;
        if (file != null
                && JsonSchemaWorkingCopy.JSD_FILE_EXTENSION
                        .equalsIgnoreCase(file.getFileExtension())) {
            SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(file);
            if (sf != null
                    && RestServicesUtil.REST_SPECIAL_FOLDER_KIND.equals(sf
                            .getKind())) {
                ok = true;
            }
        }
        return ok;
    }

}
