/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.jscript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * @author mtorres
 * 
 */
public class JScriptDataTypeMapper implements IJScriptDataTypeMapper {

    private Map<String, Set<String>> EQUALITY_OPERATOR_TYPES_MAP = null;

    private Map<String, Set<String>> COMPARISON_OPERATOR_TYPES_MAP = null;

    private Map<String, Set<String>> ASSIGNMENT_TYPES_MAP = null;

    private Map<String, Set<String>> MINUS_OPERATOR_TYPES_MAP;

    private Map<String, Set<String>> PLUS_OPERATOR_TYPES_MAP;

    private Map<String, Set<String>> MULTIPLICATION_OPERATOR_TYPES_MAP;

    private Map<String, Set<String>> DIVISION_OPERATOR_TYPES_MAP;

    private Map<String, Set<String>> MOD_OPERATOR_TYPES_MAP;

    private Map<String, Set<String>> EXPONENTIATION_OPERATOR_TYPES_MAP;

    private Map<String, Set<String>> LOGICAL_OPERATOR_TYPES_MAP;

    private Map<String, Set<String>> BITWISE_OPERATOR_TYPES_MAP;

    private Map<String, String> SPECIFIC_TO_GENERIC = null;

    private List<String> symbolTableKeyWords = null;

    private List<String> symbolTableFutureKeyWords = null;

    private static JScriptDataTypeMapper INSTANCE = null;

    protected JScriptDataTypeMapper() {

    }

    /**
     * 
     * @return instance of this class
     */
    public static JScriptDataTypeMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JScriptDataTypeMapper();
        }
        return INSTANCE;
    }

    @Override
    /**
     * 
     * @see com.tibco.xpd.script.model.internal.client.IDataTypeMapper#getSymbolTableKeyWords()
     *
     * @return
     */
    public List<String> getSymbolTableKeyWords() {
        if (symbolTableKeyWords == null) {
            symbolTableKeyWords = new ArrayList<String>();
            symbolTableKeyWords.add("this");//$NON-NLS-1$
            symbolTableKeyWords.add("with");//$NON-NLS-1$
            symbolTableKeyWords.add("while");//$NON-NLS-1$
            symbolTableKeyWords.add("void");//$NON-NLS-1$
            symbolTableKeyWords.add("var");//$NON-NLS-1$
            symbolTableKeyWords.add("typeof");//$NON-NLS-1$
            symbolTableKeyWords.add("try");//$NON-NLS-1$
            symbolTableKeyWords.add("throw");//$NON-NLS-1$
            symbolTableKeyWords.add("switch");//$NON-NLS-1$
            symbolTableKeyWords.add("return");//$NON-NLS-1$
            symbolTableKeyWords.add("new");//$NON-NLS-1$
            symbolTableKeyWords.add("instanceof");//$NON-NLS-1$
            symbolTableKeyWords.add("in");//$NON-NLS-1$
            symbolTableKeyWords.add("if");//$NON-NLS-1$
            symbolTableKeyWords.add("function");//$NON-NLS-1$
            symbolTableKeyWords.add("for");//$NON-NLS-1$
            symbolTableKeyWords.add("finally");//$NON-NLS-1$
            symbolTableKeyWords.add("else");//$NON-NLS-1$
            symbolTableKeyWords.add("do");//$NON-NLS-1$
            symbolTableKeyWords.add("delete");//$NON-NLS-1$
            symbolTableKeyWords.add("default");//$NON-NLS-1$
            symbolTableKeyWords.add("continue");//$NON-NLS-1$
            symbolTableKeyWords.add("catch");//$NON-NLS-1$
            symbolTableKeyWords.add("case");//$NON-NLS-1$
            symbolTableKeyWords.add("break");//$NON-NLS-1$
        }
        return symbolTableKeyWords;
    }

    @Override
    /**
     * 
     * @see com.tibco.xpd.script.model.internal.client.IDataTypeMapper#getSymbolTableFutureKeyWords()
     *
     * @return
     */
    public List<String> getSymbolTableFutureKeyWords() {
        if (symbolTableFutureKeyWords == null) {
            symbolTableFutureKeyWords = new ArrayList<String>();
            symbolTableFutureKeyWords.add("abstract");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("super");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("static");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("short");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("public");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("protected");//$NON-NLS-1$ $
            symbolTableFutureKeyWords.add("private");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("package");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("native");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("long");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("interface");//$NON-NLS-1$ $
            symbolTableFutureKeyWords.add("int");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("import");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("implements");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("goto");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("float");//$NON-NLS-1$ $
            symbolTableFutureKeyWords.add("final");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("extends");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("export");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("enum");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("double");//$NON-NLS-1$ $
            symbolTableFutureKeyWords.add("debugger");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("const");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("class");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("char");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("byte");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("transient");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("volatile");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("throws");//$NON-NLS-1$
            symbolTableFutureKeyWords.add("synchronized");//$NON-NLS-1$
        }
        return symbolTableFutureKeyWords;
    }

    @Override
    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getInCompatibleEqualityOperatorTypesMap()
     *
     * @return
     */
    public Map<String, Set<String>> getInCompatibleEqualityOperatorTypesMap() {
        if (EQUALITY_OPERATOR_TYPES_MAP != null) {
            return EQUALITY_OPERATOR_TYPES_MAP;
        }
        EQUALITY_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        return EQUALITY_OPERATOR_TYPES_MAP;
    }

    @Override
    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getInCompatibleComparisonOperatorTypesMap()
     *
     * @return
     */
    public Map<String, Set<String>> getInCompatibleComparisonOperatorTypesMap() {
        if (COMPARISON_OPERATOR_TYPES_MAP != null) {
            return COMPARISON_OPERATOR_TYPES_MAP;
        }
        COMPARISON_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        return COMPARISON_OPERATOR_TYPES_MAP;
    }

    @Override
    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getCompatibleAssignmentTypesMap()
     *
     * @return
     */
    public Map<String, Set<String>> getCompatibleAssignmentTypesMap() {
        if (ASSIGNMENT_TYPES_MAP != null) {
            return ASSIGNMENT_TYPES_MAP;
        }
        ASSIGNMENT_TYPES_MAP = new HashMap<String, Set<String>>();

        ASSIGNMENT_TYPES_MAP.putAll(
                JSTypesCompatabilityUtil.getCompatibleJavaScriptTypesMap());

        return ASSIGNMENT_TYPES_MAP;
    }

    @Override
    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getJavaScriptType()
     *
     * @return
     */
    public Map<String, String> getJavaScriptType() {
        return JScriptUtils.getDefaultJavaScriptTypeMap();
    }

    @Override
    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getCompatibleMinusOperatorTypesMap()
     *
     * @return
     */
    public Map<String, Set<String>> getCompatibleMinusOperatorTypesMap() {
        if (MINUS_OPERATOR_TYPES_MAP != null) {
            return MINUS_OPERATOR_TYPES_MAP;
        }
        MINUS_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> integerAllowed = new HashSet<String>(Arrays
                .asList(JsConsts.INTEGER, JsConsts.NUMBER, JsConsts.DECIMAL));
        MINUS_OPERATOR_TYPES_MAP.put(JsConsts.INTEGER, integerAllowed);
        MINUS_OPERATOR_TYPES_MAP.put(JsConsts.NUMBER, integerAllowed);
        MINUS_OPERATOR_TYPES_MAP.put(JsConsts.DECIMAL, integerAllowed);

        return MINUS_OPERATOR_TYPES_MAP;
    }

    /**
     * @return the Map that provides relationship between incompatible plus
     *         operator types.
     */
    @Override
    public Map<String, Set<String>> getInCompatiblePlusOperatorTypesMap() {
        if (PLUS_OPERATOR_TYPES_MAP != null) {
            return PLUS_OPERATOR_TYPES_MAP;
        }
        PLUS_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        return PLUS_OPERATOR_TYPES_MAP;
    }

    /**
     * @return the Map that provides relationship between compatible
     *         Multiplication operator types.
     */
    @Override
    public Map<String, Set<String>> getCompatibleMultiplicationOperatorTypesMap() {
        if (MULTIPLICATION_OPERATOR_TYPES_MAP != null) {
            return MULTIPLICATION_OPERATOR_TYPES_MAP;
        }
        MULTIPLICATION_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> integerAllowed = new HashSet<String>(Arrays
                .asList(JsConsts.INTEGER, JsConsts.NUMBER, JsConsts.DECIMAL));
        MULTIPLICATION_OPERATOR_TYPES_MAP.put(JsConsts.INTEGER, integerAllowed);
        MULTIPLICATION_OPERATOR_TYPES_MAP.put(JsConsts.DECIMAL, integerAllowed);
        MULTIPLICATION_OPERATOR_TYPES_MAP.put(JsConsts.NUMBER, integerAllowed);

        return MULTIPLICATION_OPERATOR_TYPES_MAP;
    }

    /**
     * @return the Map that provides relationship between compatible Division
     *         operator types.
     */
    @Override
    public Map<String, Set<String>> getCompatibleDivisionOperatorTypesMap() {
        if (DIVISION_OPERATOR_TYPES_MAP != null) {
            return DIVISION_OPERATOR_TYPES_MAP;
        }
        DIVISION_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> integerAllowed = new HashSet<String>(Arrays
                .asList(JsConsts.INTEGER, JsConsts.NUMBER, JsConsts.DECIMAL));
        DIVISION_OPERATOR_TYPES_MAP.put(JsConsts.INTEGER, integerAllowed);
        DIVISION_OPERATOR_TYPES_MAP.put(JsConsts.NUMBER, integerAllowed);
        DIVISION_OPERATOR_TYPES_MAP.put(JsConsts.DECIMAL, integerAllowed);

        return DIVISION_OPERATOR_TYPES_MAP;
    }

    /**
     * @return the Map that provides relationship between compatible Mod
     *         operator types.
     */
    @Override
    public Map<String, Set<String>> getCompatibleModOperatorTypesMap() {
        if (MOD_OPERATOR_TYPES_MAP != null) {
            return MOD_OPERATOR_TYPES_MAP;
        }
        MOD_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> integerAllowed = new HashSet<String>(Arrays
                .asList(JsConsts.INTEGER, JsConsts.NUMBER, JsConsts.DECIMAL));
        MOD_OPERATOR_TYPES_MAP.put(JsConsts.INTEGER, integerAllowed);
        MOD_OPERATOR_TYPES_MAP.put(JsConsts.NUMBER, integerAllowed);
        MOD_OPERATOR_TYPES_MAP.put(JsConsts.DECIMAL, integerAllowed);

        return MOD_OPERATOR_TYPES_MAP;
    }

    /**
     * @return the Map that provides relationship between compatible
     *         Exponentiation operator types.
     */
    @Override
    public Map<String, Set<String>> getCompatibleExponentiationOperatorTypesMap() {
        if (EXPONENTIATION_OPERATOR_TYPES_MAP != null) {
            return EXPONENTIATION_OPERATOR_TYPES_MAP;
        }
        EXPONENTIATION_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> integerAllowed = new HashSet<String>(
                Arrays.asList(JsConsts.INTEGER, JsConsts.NUMBER));
        EXPONENTIATION_OPERATOR_TYPES_MAP.put(JsConsts.INTEGER, integerAllowed);
        EXPONENTIATION_OPERATOR_TYPES_MAP.put(JsConsts.NUMBER, integerAllowed);
        EXPONENTIATION_OPERATOR_TYPES_MAP.put(JsConsts.DECIMAL, integerAllowed);

        return EXPONENTIATION_OPERATOR_TYPES_MAP;
    }

    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getSpecificToGenericTypeConversionMap()
     * 
     * @return
     */
    @Override
    public Map<String, String> getSpecificToGenericTypeConversionMap() {
        if (SPECIFIC_TO_GENERIC != null) {
            return SPECIFIC_TO_GENERIC;
        }
        SPECIFIC_TO_GENERIC = new HashMap<String, String>();

        SPECIFIC_TO_GENERIC.put(JsConsts.FLOAT, JsConsts.DECIMAL);
        SPECIFIC_TO_GENERIC.put(JsConsts.NUMBER, JsConsts.DECIMAL);
        SPECIFIC_TO_GENERIC.put(JsConsts.INT, JsConsts.INTEGER);
        SPECIFIC_TO_GENERIC.put(JsConsts.STRING, JsConsts.TEXT);
        SPECIFIC_TO_GENERIC.put(JsConsts.PERFORMER, JsConsts.TEXT);
        SPECIFIC_TO_GENERIC.put(JsConsts.DATETIMELC, JsConsts.DATETIME);
        SPECIFIC_TO_GENERIC.put(JsConsts.DATETIMETZLC, JsConsts.DATETIMETZ);
        SPECIFIC_TO_GENERIC.put(JsConsts.DATETIMETZ, JsConsts.DATETIMETZ);

        return SPECIFIC_TO_GENERIC;
    }

    /**
     * Goes thru each specific key in the given map, gets the generic type for
     * it. Gets the values for the converted generic key type and creates a new
     * converted generic map.
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#convertSpecificMapToGeneric(java.util.Map)
     * 
     * @param map
     *            containing specific key value pairs
     * @return <code>Map<String, Set<String></code> with converted (specific to
     *         generic) key value pairs
     */
    @Override
    public Map<String, Set<String>> convertSpecificMapToGeneric(
            Map<String, Set<String>> map) {
        if (map != null) {
            Map<String, Set<String>> convertedMap =
                    new HashMap<String, Set<String>>();
            for (String type : map.keySet()) {
                if (type != null) {
                    Set<String> newTypes = map.get(type);
                    if (newTypes != null && !newTypes.isEmpty()) {
                        String convertedType =
                                getSpecificToGenericTypeConversionMap()
                                        .get(type);
                        if (convertedType != null) {
                            newTypes.addAll(map.get(convertedType));
                        } else {
                            convertedType = type;
                        }
                        Set<String> convertedNewTypes = new HashSet<String>();
                        for (String newType : newTypes) {
                            if (newType != null) {
                                String convertedNewType =
                                        getSpecificToGenericTypeConversionMap()
                                                .get(newType);
                                if (convertedNewType == null) {
                                    convertedNewType = newType;
                                }
                                convertedNewTypes.add(convertedNewType);
                            }
                        }
                        convertedMap.put(convertedType, convertedNewTypes);
                    }
                }
            }
            return convertedMap;
        }
        return Collections.emptyMap();
    }

    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getCompatibleLogicalOperatorTypesMap()
     * 
     * @return
     */
    @Override
    public Map<String, Set<String>> getCompatibleLogicalOperatorTypesMap() {
        if (LOGICAL_OPERATOR_TYPES_MAP != null) {
            return LOGICAL_OPERATOR_TYPES_MAP;
        }
        LOGICAL_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> booleanAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.BOOLEAN));
        LOGICAL_OPERATOR_TYPES_MAP.put(JsConsts.BOOLEAN, booleanAllowed);

        return LOGICAL_OPERATOR_TYPES_MAP;
    }

    /**
     * 
     * @see com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper#getCompatibleBitwiseOperatorTypesMap()
     * 
     * @return
     */
    @Override
    public Map<String, Set<String>> getCompatibleBitwiseOperatorTypesMap() {
        if (BITWISE_OPERATOR_TYPES_MAP != null) {
            return BITWISE_OPERATOR_TYPES_MAP;
        }
        BITWISE_OPERATOR_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> integerAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.INTEGER));
        BITWISE_OPERATOR_TYPES_MAP.put(JsConsts.INTEGER, integerAllowed);

        return BITWISE_OPERATOR_TYPES_MAP;
    }
}
