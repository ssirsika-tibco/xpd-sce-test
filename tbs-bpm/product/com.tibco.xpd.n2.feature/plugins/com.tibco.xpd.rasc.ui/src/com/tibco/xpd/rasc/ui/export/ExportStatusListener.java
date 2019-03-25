/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import org.eclipse.core.resources.IProject;

/**
 * Listener interface for status events.
 *
 * @author nwilson
 * @since 20 Mar 2019
 */
public interface ExportStatusListener {

    /**
     * @param project
     *            The project.
     * @param status
     *            The export status.
     * @param message
     *            The associated message.
     */
    void setStatus(IProject project, ExportStatus status, String message);

}
