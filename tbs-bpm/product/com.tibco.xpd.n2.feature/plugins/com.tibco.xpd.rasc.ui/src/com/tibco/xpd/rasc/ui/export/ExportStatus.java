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
    WAITING("IMG_OBJS_INCOMPLETE_TSK"), RUNNING( //$NON-NLS-1$
            "IMG_TOOL_FORWARD"), FAILED_EXPORT( //$NON-NLS-1$
                    "IMG_OBJS_ERROR_TSK"), FAILED_VALIDATION( //$NON-NLS-1$
                            "IMG_OBJS_ERROR_TSK"), COMPLETE( //$NON-NLS-1$
                                    "IMG_OBJS_COMPLETE_TSK"); //$NON-NLS-1$

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
