/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.logger;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Logger objects factory. 
 * 
 * <i>Created: Nov 21, 2006</i>.
 * 
 * @author mmaciuki
 */
public final class LoggerFactory {

    /**
     * TODO comment me.
     * 
     */
    private static Map<String, Logger> logCache = new HashMap<String, Logger>();

    /**
     * TODO comment me.
     */
    private LoggerFactory() {
    }

    /**
     * Returns logger object for given plugin ID <br>
     * Note: the bundle have to be started and loaded in order to log any
     * entries.
     * 
     * @param pluginId
     *            pluginId
     * @return Logger
     */
    public static synchronized Logger createLogger(String pluginId) {
        Logger result;
        if (logCache.containsKey(pluginId)) {
            result = logCache.get(pluginId);
        } else {
            Bundle bundle = Platform.getBundle(pluginId);
            ILog eclipseLog = Platform.getLog(bundle);
            result = new Logger(eclipseLog, pluginId);
            logCache.put(pluginId, result);
        }
        return result; 
    }
}
