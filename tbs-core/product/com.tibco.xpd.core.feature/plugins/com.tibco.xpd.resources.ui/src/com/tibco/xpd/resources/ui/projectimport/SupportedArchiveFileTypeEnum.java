/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.ui.projectimport;

/**
 * 
 * 
 * @author patkinso
 * @since 26 Apr 2013
 */
public enum SupportedArchiveFileTypeEnum {

    ZIP(".zip"), TAR(".tar"), COMPRESSED_TAR(".tar.gz"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

    private String suffix = null;

    private SupportedArchiveFileTypeEnum(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

}
