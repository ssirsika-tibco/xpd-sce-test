/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */
package com.tibco.xpd.rasc.ui.export;

/**
 * Status of a project export used for displaying the correct icon.
 *
 * @author nwilson
 * @since 14 Mar 2019
 */
public enum ExportStatus {
    WAITING("IMG_OBJS_INCOMPLETE_TSK"), RUNNING("IMG_TOOL_FORWARD"), FAILED( //$NON-NLS-1$ //$NON-NLS-2$
            "IMG_OBJS_ERROR_TSK"), COMPLETE("IMG_OBJS_COMPLETE_TSK"); //$NON-NLS-1$ //$NON-NLS-2$

    private String iconName;

    ExportStatus(String iconName) {
        this.iconName = iconName;
    }

    /**
     * @return The icon name.
     */
    public String getIconName() {
        return iconName;
    }
}
