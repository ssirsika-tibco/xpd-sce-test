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
    WAITING("IMG_OBJS_INCOMPLETE_TSK"), //$NON-NLS-1$
    RUNNING("IMG_TOOL_FORWARD"), //$NON-NLS-1$
    NO_CONTENT("IMG_OBJS_INFO_TSK"), //$NON-NLS-1$
    FAILED_EXPORT("IMG_OBJS_ERROR_TSK"), //$NON-NLS-1$
    FAILED_VALIDATION("IMG_OBJS_ERROR_TSK"), //$NON-NLS-1$
    COMPLETE("IMG_OBJS_COMPLETE_TSK"); //$NON-NLS-1$

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
