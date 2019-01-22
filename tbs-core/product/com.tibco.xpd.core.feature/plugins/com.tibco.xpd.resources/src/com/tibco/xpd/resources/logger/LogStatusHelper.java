/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.internal.Messages;

/**
 * Helper class for building Eclipse {@link IStatus} objects.
 *  
 * <i>Created: Nov 14, 2006</i>.
 * 
 * @author mmaciuki
 */
public final class LogStatusHelper {

    /**
     * Message used when no message is provided.
     */
    private static final String DEFAULT_EXCEPTION_MESSAGE = Messages.LogStatusHelper_unexpectedException_summary;

    /**
     * 
     */
    private LogStatusHelper() {
    }

    /**
     * Create message.
     * 
     * @param severity
     *            Message severity. Choose from: {@link IStatus#CANCEL},
     *            {@link IStatus#ERROR}, {@link IStatus#INFO}, {@link IStatus#OK},
     *            {@link IStatus#WARNING}
     * @param pluginId
     *            the unique identifier of the relevant plug-in
     * @param code
     *            the plug-in-specific status code, or <code>OK</code>
     * @param message
     *            a human-readable message, localized to the current locale
     * @param exception
     *            a low-level exception, or <code>null</code> if not applicable
     * @return Eclipse {@link IStatus} object.
     */
    static IStatus createStatus(int severity, String pluginId, int code, String message,
            Throwable exception) {
        return new Status(severity, pluginId, code, message, exception);
    }

    /**
     * Create info message.
     * 
     * @param pluginId
     *            the unique identifier of the relevant plug-in
     * @param message
     *            a human-readable message, localized to the current locale
     * @return Eclipse {@link IStatus} object.
     */
    public static IStatus createInfo(String pluginId, String message) {
        return createStatus(IStatus.INFO, pluginId, IStatus.OK, message, null);
    }


    /**
     * Create warning message.
     * 
     * @param pluginId
     *            the unique identifier of the relevant plug-in
     * @param message
     *            a human-readable message, localized to the current locale
     * @return Eclipse {@link IStatus} object.
     */
    public static IStatus createWarn(String pluginId, String message) {
        return createStatus(IStatus.WARNING, pluginId, IStatus.OK, message, null);
    };
    
    /**
     * Create error message.
     * 
     * @param pluginId
     *            the unique identifier of the relevant plug-in
     * @param message
     *            a human-readable message, localized to the current locale
     * @return Eclipse {@link IStatus} object.
     */
    public static IStatus createError(String pluginId, String message) {
        return createStatus(IStatus.ERROR, pluginId, IStatus.OK, message, null);
    }

    /**
     * Create error message.
     * 
     * @param pluginId
     *            the unique identifier of the relevant plug-in
     * @param ex
     *            a low-level exception, or <code>null</code> if not applicable
     * @return Eclipse {@link IStatus} object.
     */
    public static IStatus createError(String pluginId, Throwable ex) {
        return createStatus(IStatus.ERROR, pluginId, IStatus.OK,
                DEFAULT_EXCEPTION_MESSAGE, ex);
    }

    /**
     * Create error message.
     * 
     * @param pluginId
     *            the unique identifier of the relevant plug-in
     * @param ex
     *            a low-level exception, or <code>null</code> if not applicable
     * @param message
     *            a human-readable message, localized to the current locale
     * @return Eclipse {@link IStatus} object.
     */
    public static IStatus createError(String pluginId, Throwable ex, String message) {
        return createStatus(IStatus.ERROR, pluginId, IStatus.OK, message, ex);
    }
}
