/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.script.parser.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Class to provide access to a Nashorn script engine instance.
 *
 * @author nwilson
 * @since 7 Feb 2019
 */
public class ScriptEngineUtil {
    private static ScriptEngine engine =
            new ScriptEngineManager().getEngineByName("nashorn"); //$NON-NLS-1$

    public static ScriptEngine getScriptEngine() {
        return engine;
    }

}
