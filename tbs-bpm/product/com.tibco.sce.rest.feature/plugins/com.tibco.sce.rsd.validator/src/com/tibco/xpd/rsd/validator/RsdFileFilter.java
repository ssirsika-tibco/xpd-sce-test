/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.validator;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * Filter for RSD files inside a REST Special Folder.
 * 
 * @author nwilson
 * @since 9 Mar 2015
 */
public class RsdFileFilter implements IFileFilter {

    @Override
    public boolean accept(IFile file) {
        boolean ok = false;
        if (file != null
                && RsdWorkingCopy.RSD_FILE_EXTENSION.equalsIgnoreCase(file
                        .getFileExtension())) {
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
