/**
 * Copyright 2006 TIBCO Software Inc.
 */

package com.tibco.xpd.xpdl2.resources;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;

/**
 * Working copy factory for XPDL format files with .xpdl extension.
 * 
 * @author aallway
 * @since 3.2
 */
public class Xpdl2WorkingCopyFactory extends AbstractXpdl2WorkingCopyFactory {

    /**
     * extension of the xpdl file.
     */
    public static final String XPDL_EXTENSION =
            Xpdl2ResourcesConsts.XPDL_EXTENSION;

    @Override
    public String getFileExtension() {
        return XPDL_EXTENSION;
    }

    @Override
    public String getSpecialFolderKind() {
        return Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND;
    }

    @Override
    public Xpdl2FileType getXpdl2FileType() {
        return Xpdl2FileType.PROCESS;
    }
}
