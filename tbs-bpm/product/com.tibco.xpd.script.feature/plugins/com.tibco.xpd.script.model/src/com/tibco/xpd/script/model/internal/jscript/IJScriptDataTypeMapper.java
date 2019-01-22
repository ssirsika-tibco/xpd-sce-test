/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.jscript;

import java.util.Map;
import java.util.Set;

import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;

/**
 * @author mtorres
 * 
 */
public interface IJScriptDataTypeMapper extends IDataTypeMapper {

    /**
     * @return the Map that provides relationship between incompatible Equality
     *         operator types.
     */
    public Map<String, Set<String>> getInCompatibleEqualityOperatorTypesMap();

    /**
     * @return the Map that provides relationship between incompatible
     *         Comparison operator types.
     */
    public Map<String, Set<String>> getInCompatibleComparisonOperatorTypesMap();

    /**
     * @return the Map that provides relationship between compatible assignment
     *         types.
     */
    public Map<String, Set<String>> getCompatibleAssignmentTypesMap();

    /**
     * @return the Map that provides relationship between compatible minus
     *         types.
     */
    public Map<String, Set<String>> getCompatibleMinusOperatorTypesMap();

    /**
     * @return the Map that provides relationship between incompatible plus
     *         operator types.
     */
    public Map<String, Set<String>> getInCompatiblePlusOperatorTypesMap();

    /**
     * @return the Map that provides relationship between compatible
     *         Multiplication operator types.
     */
    public Map<String, Set<String>> getCompatibleMultiplicationOperatorTypesMap();

    /**
     * @return the Map that provides relationship between compatible Division
     *         operator types.
     */
    public Map<String, Set<String>> getCompatibleDivisionOperatorTypesMap();

    /**
     * @return the Map that provides relationship between compatible Mod
     *         operator types.
     */
    public Map<String, Set<String>> getCompatibleModOperatorTypesMap();

    /**
     * @return the Map that provides relationship between compatible
     *         Exponentiation operator types.
     */
    public Map<String, Set<String>> getCompatibleExponentiationOperatorTypesMap();

    /**
     * @return the Map that provides relationship between compatible Logical
     *         operator types.
     */
    public Map<String, Set<String>> getCompatibleLogicalOperatorTypesMap();

    /**
     * @return the Map that provides map between a type and its javascript type.
     */
    public Map<String, String> getJavaScriptType();

    /**
     * @return the Map that provides the conversion from a specific destination
     *         type to a equivalent generic one. ie: Float to Decimal
     **/
    public Map<String, String> getSpecificToGenericTypeConversionMap();

    /**
     * @return the Map converted to generic types for types that are equivalent
     *         ie: String -> Text
     **/
    public Map<String, Set<String>> convertSpecificMapToGeneric(
            Map<String, Set<String>> map);

    /**
     * @return
     */
    public Map<String, Set<String>> getCompatibleBitwiseOperatorTypesMap();
}
