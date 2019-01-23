/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.logger;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Logger using Eclipse tracing options. This class helps dealing with Eclipse
 * logging system and has debug options that help tracing problems.<br>
 * Debug logs are printed on System.out with class name, file name and line
 * information, formatted to make easy use when appear in Eclipse console.<br>
 * <br>
 * If it is reasonable feel free to overwrite logging methods in your plugin's
 * implementation. <br>
 * Search eclipse help for "running with tracing" to get more informations.<br>
 * Check <a href="http://wiki.tibco.com/StudioWiki/Logger">wiki page</a><br>
 * 
 * <pre>
 * Logger log = LoggerFactory.createLogger(&quot;my.plugin.id&quot;)
 * </pre>
 * 
 * @author mmaciuki
 */
public class Logger {

    /**
     * Logger category for "warn". Value: warn
     */
    public static final String CATEGORY_GENERAL_WARNING = "warn"; //$NON-NLS-1$

    /**
     * Logger category for "info". Value: info
     */
    public static final String CATEGORY_GENERAL_INFO = "info"; //$NON-NLS-1$

    /**
     * Logger category for "debug". Value: debug
     */
    public static final String CATEGORY_GENERAL_DEBUG = "debug"; //$NON-NLS-1$

    /**
     * Logger category for "build". Value: build
     */
    public static final String CATEGORY_BUILD = "build"; //$NON-NLS-1$

    /**
     * Indicates whether the platform is in debug mode
     */
    private static boolean IN_DEBUG = Platform.inDebugMode();

    /**
     * Eclipse logger that will be used for errors.
     */
    private final ILog pluginLogger;

    /**
     * Plugin id associated with logger instance.
     */
    private final String pluginId;

    /**
     * Category status cache
     */
    private final Map<String, Boolean> categories =
            new HashMap<String, Boolean>();

    /**
     * Logger instance associated with Eclipse logger and specific plugin id.
     * 
     * @param log
     *            Eclipse log instance that should be used by this instance.
     * @param pluginId
     *            Each plugin have to hold own instance associated with it's
     *            plugin id.
     */
    Logger(ILog log, String pluginId) {
        this.pluginLogger = log;
        this.pluginId = pluginId;
    }

    /**
     * Check if tracing for category is enabled.
     * 
     * @param category
     *            Logging category name.
     * @return true if category is enabled.
     */
    public boolean isCategoryEnabled(String category) {
        Boolean value = categories.get(category);

        if (value == null) {
            String debugOption =
                    Platform.getDebugOption(pluginId + "/" + category); //$NON-NLS-1$
            value = Boolean.valueOf(debugOption);
            categories.put(category, value);
        }

        return value != null ? value.booleanValue() : false;
    }

    /**
     * Check if debug tracing is enabled.
     * 
     * @return true if enabled
     */
    public boolean isDebugEnabled() {
        boolean result = IN_DEBUG;

        if (result) {
            result = isCategoryEnabled(CATEGORY_GENERAL_DEBUG);
        }

        return result;
    }

    /**
     * Log debug message (if debug tracing enabled).
     * 
     * @param message
     *            Message to print on debug output.
     */
    public void debug(String message) {
        if (isDebugEnabled()) {
            StackTraceElement stackElement = new Exception().getStackTrace()[1];
            String msg = buildMessage(stackElement, message);
            if (shouldLogToEclipseErrorLog()) {
                XpdResourcesPlugin.getDefault().getLog()
                        .log(new Status(IStatus.INFO, pluginId, msg));
            } else {
                System.out.println(msg);
            }

        }
    }

    /**
     * Log debug message and exception (if debug tracing enabled).
     * 
     * @param e
     *            Exception to log.
     * 
     * @param message
     *            Message to print on debug output.
     */
    public void debug(Throwable e, String message) {
        if (isDebugEnabled()) {
            StackTraceElement stackElement = new Exception().getStackTrace()[1];
            String msg = buildMessage(stackElement, message);
            if (shouldLogToEclipseErrorLog()) {
                XpdResourcesPlugin.getDefault().getLog()
                        .log(new Status(IStatus.INFO, pluginId, msg, e));
            } else {
                System.out.println(msg);
                e.printStackTrace(System.out);
            }
        }
    }

    private boolean shouldLogToEclipseErrorLog() {
        String debugOption =
                Platform.getDebugOption("com.tibco.xpd.resources/logToEclipseLog"); //$NON-NLS-1$
        return debugOption != null ? Boolean.valueOf(debugOption)
                .booleanValue() : false;
    }

    /**
     * Log info message.
     * 
     * @param message
     *            Message to print on debug output.
     */
    public void info(String message) {
        pluginLogger.log(LogStatusHelper.createInfo(pluginId, message));
        debug(message);
    }

    /**
     * Log info message (if category tracing enabled).
     * 
     * @param message
     *            Message to log on info output.
     * @param category
     *            Category for message.
     */

    public void info(String message, String category) {
        if (isCategoryEnabled(CATEGORY_GENERAL_INFO)
                && isCategoryEnabled(category)) {
            info(message);
        }
    }

    /**
     * Log warning message.
     * 
     * @param message
     *            Message to print on debug output.
     */
    public void warn(String message) {
        pluginLogger.log(LogStatusHelper.createWarn(pluginId, message));
        debug(message);
    }

    /**
     * Log warning message (if category tracing enabled).
     * 
     * @param message
     *            Message to log on info output.
     * @param category
     *            Category for message.
     */
    public void warn(String message, String category) {
        if (isCategoryEnabled(CATEGORY_GENERAL_WARNING)
                && isCategoryEnabled(category)) {
            warn(message);
        }
    }

    /**
     * Logs warning message.
     * 
     * @param e
     *            Exception thrown.
     * @param message
     *            Message to log on info output.
     */
    public void warn(Throwable e, String message) {
        pluginLogger.log(new Status(IStatus.WARNING, pluginId, 0, message, e));
        debug(e, message);
    }

    /**
     * Log error message.
     * 
     * @param message
     *            Message to log on error output.
     */
    public void error(String message) {
        StackTraceElement stackElement = new Exception().getStackTrace()[1];
        String msg = buildMessage(stackElement, message);
        pluginLogger.log(LogStatusHelper.createError(pluginId, msg.toString()));
        debug(message);
    }

    /**
     * Add class name and line number information (hit point).
     * 
     * @param stackElement
     *            Element from trace stack
     * @param message
     *            Message to print
     * @return Formatted message
     */
    private String buildMessage(StackTraceElement stackElement, String message) {
        StringBuilder msg = new StringBuilder();

        msg.append(stackElement);
        msg.append(" "); //$NON-NLS-1$
        msg.append(message);
        return msg.toString();
    }

    /**
     * Log exception in error log.
     * 
     * @param ex
     *            Exception to log.
     */
    public void error(Throwable ex) {
        pluginLogger.log(LogStatusHelper.createError(pluginId, ex));
        debug(ex, ex.getMessage());
    }

    /**
     * Log exception and message in error log.
     * 
     * @param ex
     *            Exception to log.
     * @param message
     *            Message to log on error output.
     */
    public void error(Throwable ex, String message) {
        pluginLogger.log(LogStatusHelper.createError(pluginId, ex, message));
        debug(ex, message);
    }

    /**
     * Outputs "hit point" information. Useful when need to trace execution
     * flow.
     */
    public void debug() {
        if (isDebugEnabled()) {
            StackTraceElement stackElement = new Exception().getStackTrace()[1];
            String msg = buildMessage(stackElement, ""); //$NON-NLS-1$
            System.out.println(msg);
        }
    }

    /**
     * Log the status. This allows reporting of multiple status results which
     * will be displayed as a group in the error log.
     * 
     * @param status
     *            {@link Status} or {@link MultiStatus}.
     * @since 3.2
     */
    public void log(IStatus status) {
        pluginLogger.log(status);

        if (!status.isOK()) {
            debug(status.getMessage());
        }
    }

    /**
     * Trace the message. The message will be traced only if platform is in a
     * tracing (debug) mode and the plug-in's tracing flag (determined by
     * category) has been set to "true". Tracing flag should have a format:
     * pluginId/category=true.
     * 
     * @param category
     *            The debug category specific for plug-in. If the message
     * @param message
     *            The message to be printed to the output.
     */
    public void trace(String category, String message) {
        if (Platform.inDebugMode() && isCategoryEnabled(category)) {
            if (shouldLogToEclipseErrorLog()) {
                XpdResourcesPlugin.getDefault().getLog()
                        .log(new Status(IStatus.INFO, pluginId, message));
            } else {
                System.out.println(message);
            }
        }
    }

    /**
     * Trace the message. The message will be traced only if platform is in a
     * tracing (debug) mode and the plug-in's tracing flag (determined by
     * category) has been set to "true". Tracing flag should have a format:
     * pluginId/category=true.
     * 
     * @param category
     *            The debug category specific for plug-in. If the message
     * @param message
     *            The message to be printed to the output.
     * @param e
     *            The accompanying exception to be traced.
     */
    public void trace(String category, String message, Throwable e) {
        if (Platform.inDebugMode() && isCategoryEnabled(category)) {
            if (shouldLogToEclipseErrorLog()) {
                XpdResourcesPlugin.getDefault().getLog()
                        .log(new Status(IStatus.INFO, pluginId, message, e));
            } else {
                System.out.println(message);
                e.printStackTrace(System.out);
            }
        }
    }

    /**
     * @return the pluginLogger
     */
    public ILog getPluginLogger() {
        return pluginLogger;
    }
}
