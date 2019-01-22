/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.model.internal.jscript;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.script.model.JsConsts;

/**
 * @author mtorres
 * 
 */
public class JSTypesCompatabilityUtil {

    protected static Map<String, Set<String>> JAVASCRIPT_PRIMITIVETYPES_MAP;

    /**
     * @return the Map that provides relationship between compatible JavaScript
     *         types.
     */
    public static Map<String, Set<String>> getCompatibleJavaScriptTypesMap() {
        if (JAVASCRIPT_PRIMITIVETYPES_MAP != null) {
            return JAVASCRIPT_PRIMITIVETYPES_MAP;
        }
        JAVASCRIPT_PRIMITIVETYPES_MAP = new HashMap<String, Set<String>>();
        // Valid Boolean Assignments
        HashSet<String> booleanAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.BOOLEAN,
                        JsConsts.STRING,
                        JsConsts.STRING_LITERAL,
                        JsConsts.TEXT));
        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.BOOLEAN, booleanAllowed);

        // Valid Integer Assignments
        HashSet<String> integerAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.INTEGER,
                        JsConsts.BIGINTEGER,
                        JsConsts.BIGDECIMAL,
                        JsConsts.BOOLEAN,
                        JsConsts.FLOAT,
                        JsConsts.DECIMAL,
                        JsConsts.STRING,
                        JsConsts.TEXT));

        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.INTEGER, integerAllowed);

        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.DECIMAL, integerAllowed);

        // Valid Text Assignments
        HashSet<String> textAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.BOOLEAN,
                        JsConsts.INTEGER,
                        JsConsts.BIGINTEGER,
                        JsConsts.BIGDECIMAL,
                        JsConsts.FLOAT,
                        JsConsts.STRING,
                        JsConsts.TEXT,
                        JsConsts.URI,
                        JsConsts.ID));

        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.TEXT, textAllowed);

        // Valid Date Assignments
        HashSet<String> dateAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.TEXT,
                        JsConsts.DATE,
                        JsConsts.DATETIME,
                        JsConsts.DATETIMETZ));
        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.DATE, dateAllowed);

        // Valid Time Assignments
        HashSet<String> timeAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.TEXT, JsConsts.TIME));
        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.TIME, timeAllowed);

        HashSet<String> dateTimeAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.DATE,
                        JsConsts.DATETIME,
                        JsConsts.TEXT,
                        JsConsts.TIME));

        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.DATETIME, dateTimeAllowed);

        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.DATETIMETZ, dateTimeAllowed);

        HashSet<String> bigIntegerAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.BOOLEAN,
                        JsConsts.INTEGER,
                        JsConsts.BIGINTEGER,
                        JsConsts.DECIMAL,
                        JsConsts.BIGDECIMAL,
                        JsConsts.TEXT));
        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.BIGINTEGER,
                bigIntegerAllowed);

        HashSet<String> bigDecimalAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.BOOLEAN,
                        JsConsts.INTEGER,
                        JsConsts.BIGINTEGER,
                        JsConsts.DECIMAL,
                        JsConsts.BIGDECIMAL,
                        JsConsts.TEXT));
        JAVASCRIPT_PRIMITIVETYPES_MAP.put(JsConsts.BIGDECIMAL,
                bigDecimalAllowed);

        return JAVASCRIPT_PRIMITIVETYPES_MAP;
    }

}
