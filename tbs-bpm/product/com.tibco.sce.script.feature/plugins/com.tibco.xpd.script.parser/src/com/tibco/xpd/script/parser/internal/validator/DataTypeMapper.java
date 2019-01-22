/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

public class DataTypeMapper {
    /**
     * The key is of the Map is antlr type and the value is type Staffware type
     */
    private static Map<Integer, String> paramTypeMap =
            new HashMap<Integer, String>();
    static {
        paramTypeMap.put(JScriptTokenTypes.STRING_LITERAL, JsConsts.STRING);
        paramTypeMap.put(JScriptTokenTypes.NUM_INT, JsConsts.INTEGER);
        paramTypeMap.put(JScriptTokenTypes.NUM_LONG, JsConsts.INTEGER);
        paramTypeMap.put(JScriptTokenTypes.NUM_FLOAT, JsConsts.FLOAT);
        paramTypeMap.put(JScriptTokenTypes.NUM_DOUBLE, JsConsts.FLOAT);
        paramTypeMap.put(JScriptTokenTypes.LITERAL_true, JsConsts.BOOLEAN);
        paramTypeMap.put(JScriptTokenTypes.LITERAL_false, JsConsts.BOOLEAN);
        paramTypeMap.put(JScriptTokenTypes.BOOLEAN, JsConsts.BOOLEAN);
        paramTypeMap.put(JScriptTokenTypes.DATE, JsConsts.DATE);
        paramTypeMap.put(JScriptTokenTypes.TIME, JsConsts.TIME);
        paramTypeMap.put(JScriptTokenTypes.DATETIME, JsConsts.DATETIME);
        paramTypeMap.put(JScriptTokenTypes.NUMBER, JsConsts.NUMBER);
        paramTypeMap.put(JScriptTokenTypes.ARRAY_TYPE, JsConsts.ARRAY);
        paramTypeMap.put(JsConsts.OBJECT_AST, JsConsts.OBJECT);
    }

    private static Map<String, Integer> returnTypeMap =
            new HashMap<String, Integer>();
    static {
        returnTypeMap.put(JsConsts.STRING, JScriptTokenTypes.STRING_LITERAL);
        returnTypeMap.put(JsConsts.FLOAT, JScriptTokenTypes.NUM_FLOAT);
        returnTypeMap.put(JsConsts.INTEGER, JScriptTokenTypes.NUM_INT);
        returnTypeMap.put(JsConsts.NUMBER, JScriptTokenTypes.NUM_INT);
        returnTypeMap.put(JsConsts.REFERENCE, JScriptTokenTypes.STRING_LITERAL);
        returnTypeMap.put(JsConsts.BOOLEAN, JScriptTokenTypes.BOOLEAN);
        returnTypeMap.put(JsConsts.PERFORMER, JScriptTokenTypes.STRING_LITERAL);
        returnTypeMap.put(JsConsts.OBJECT, JScriptTokenTypes.STRING_LITERAL);
        returnTypeMap.put(JsConsts.DATE, JScriptTokenTypes.DATE);
        returnTypeMap.put(JsConsts.TIME, JScriptTokenTypes.TIME);
        returnTypeMap.put(JsConsts.ARRAY, JScriptTokenTypes.ARRAY_TYPE);
        returnTypeMap.put(JsConsts.DATETIME, JScriptTokenTypes.DATETIME);
        returnTypeMap.put(JsConsts.UNDEFINED_DATA_TYPE,
                JScriptTokenTypes.UNDEFINE_DATA_TYPE);
        returnTypeMap.put(JsConsts.VAR_TYPE, JScriptTokenTypes.VAR_TYPE);
    }

    private static Map<String, String> defaultValueMap =
            new HashMap<String, String>();
    static {
        defaultValueMap.put(JsConsts.STRING, "1"); //$NON-NLS-1$		
        defaultValueMap.put(JsConsts.INTEGER, "1"); //$NON-NLS-1$
        defaultValueMap.put(JsConsts.BOOLEAN, "true"); //$NON-NLS-1$
        defaultValueMap.put(JsConsts.DATETIME, "1"); //$NON-NLS-1$		
        defaultValueMap.put(JsConsts.FLOAT, "1.0"); //$NON-NLS-1$
        defaultValueMap.put(JsConsts.NUMBER, "1"); //$NON-NLS-1$
        defaultValueMap.put(JsConsts.OBJECT, "1"); //$NON-NLS-1$
        defaultValueMap.put(JsConsts.UNDEFINED_DATA_TYPE, ""); //$NON-NLS-1$
        defaultValueMap.put(JsConsts.REFERENCE, "1"); //$NON-NLS-1$
    }

    public static String getCorrespondingDataType(Integer astType) {
        String toReturn = paramTypeMap.get(astType);
        if (toReturn == null) {
            toReturn = JsConsts.UNDEFINED_DATA_TYPE;
        }
        return toReturn;
    }

    public static Integer mapReturnDataType(String actualReturnType) {
        Integer toReturn = returnTypeMap.get(actualReturnType);
        if (toReturn == null) {
            toReturn = JScriptTokenTypes.UNDEFINE_DATA_TYPE;
        }
        return toReturn;
    }

    public static String getDefaultValue(String astType) {
        return defaultValueMap.get(astType);
    }

    public static Map<String, String> getDefaultValue(
            Map<String, IScriptRelevantData> varMap) {
        Map<String, String> toReturn = new HashMap<String, String>();
        Set<Entry<String, IScriptRelevantData>> entrySet = varMap.entrySet();
        for (Entry<String, IScriptRelevantData> eachEntry : entrySet) {
            String strVarName = eachEntry.getKey();
            IScriptRelevantData dataType = eachEntry.getValue();
            if (dataType != null) {
                String dataTypeType = dataType.getType();
                String strDefaultValue = defaultValueMap.get(dataTypeType);
                if (strDefaultValue != null) {
                    toReturn.put(strVarName, strDefaultValue);
                } else {
                    toReturn.put(strVarName, "1"); //$NON-NLS-1$
                }
            }
        }
        return toReturn;
    }

    private static final List<String> symbolTableKeyWords =
            new ArrayList<String>();
    static {
        symbolTableKeyWords.add("this");//$NON-NLS-1$
    };

    public static List<String> getSymbolTableKeyWords() {
        return symbolTableKeyWords;
    }

}
