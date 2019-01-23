/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.jscript.JScriptDataTypeMapper;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * @author mtorres
 * 
 */
public class N2JScriptDataTypeMapper extends JScriptDataTypeMapper {

    
    private Map<String, Set<String>> ASSIGNMENT_TYPES_MAP = null;
    
    private Map<BasicTypeType, String> PROCESSTYPE_TO_JAVASCRIPT = null;
    
    private Map<String, String> SPECIFIC_TO_GENERIC = null;
    
    private static N2JScriptDataTypeMapper INSTANCE = null;
    
    protected N2JScriptDataTypeMapper(){
        super();
    }

    public static N2JScriptDataTypeMapper getInstance(){
        if(INSTANCE == null){
            INSTANCE = new N2JScriptDataTypeMapper();
        }
        return INSTANCE;
    }
    
    @Override
    public Map<String, Set<String>> getCompatibleAssignmentTypesMap() {
        if (ASSIGNMENT_TYPES_MAP != null) {
            return ASSIGNMENT_TYPES_MAP;
        }
        ASSIGNMENT_TYPES_MAP = new HashMap<String, Set<String>>();

        ASSIGNMENT_TYPES_MAP.putAll(JSTypesCompatabilityUtil.getCompatibleBOMtoBOMTypesMap());
        
        Map<BasicTypeType, Set<String>> compatibleProcessToBOMTypesMap =
                JSTypesCompatabilityUtil.getCompatibleProcessToBOMTypesMap();
        
        for (BasicTypeType type : compatibleProcessToBOMTypesMap.keySet()) {
            Set<String> newTypes = compatibleProcessToBOMTypesMap.get(type);
            if (newTypes != null && !newTypes.isEmpty()) {
                String jsType =
                        getProcessTypeToJavaScriptConversionMap().get(type);
                if (jsType != null) {
                    Set<String> existingTypes =
                            ASSIGNMENT_TYPES_MAP.get(jsType);
                    if (existingTypes == null) {
                        existingTypes = new HashSet<String>();
                        ASSIGNMENT_TYPES_MAP.put(jsType, newTypes);
                    } else {
                        existingTypes.addAll(newTypes);
                    }
                }
            }
        }
        
        Map<String, Set<String>> compatibleCDStoCDSTypesMap =
                JSTypesCompatabilityUtil.getCompatibleCDStoCDSTypesMap();

        for (String type : compatibleCDStoCDSTypesMap.keySet()) {
            Set<String> newTypes = compatibleCDStoCDSTypesMap.get(type);
            if (newTypes != null && !newTypes.isEmpty()) {
                Set<String> existingTypes = ASSIGNMENT_TYPES_MAP.get(type);
                if (existingTypes == null) {
                    existingTypes = new HashSet<String>();
                    ASSIGNMENT_TYPES_MAP.put(type, newTypes);
                } else {
                    existingTypes.addAll(newTypes);
                }
            }
        }
        
        return ASSIGNMENT_TYPES_MAP;
    }
    
    
    public Map<BasicTypeType, String> getProcessTypeToJavaScriptConversionMap(){
        if (PROCESSTYPE_TO_JAVASCRIPT != null) {
            return PROCESSTYPE_TO_JAVASCRIPT;
        }
        PROCESSTYPE_TO_JAVASCRIPT = new HashMap<BasicTypeType, String>();
        
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.BOOLEAN_LITERAL, JsConsts.BOOLEAN);
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.INTEGER_LITERAL, JsConsts.INTEGER);
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.FLOAT_LITERAL, JsConsts.FLOAT);
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.STRING_LITERAL, JsConsts.STRING);
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.DATE_LITERAL, JsConsts.DATE);
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.TIME_LITERAL, JsConsts.TIME);
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.DATETIME_LITERAL, JsConsts.DATETIME);
        PROCESSTYPE_TO_JAVASCRIPT.put(BasicTypeType.PERFORMER_LITERAL, JsConsts.STRING);

        return PROCESSTYPE_TO_JAVASCRIPT;
    }
    
    public Map<String, String> getJavaScriptType() {
       return CDSUtils.getN2JavaScriptType();
    }
    
    @Override
    public Map<String, String> getSpecificToGenericTypeConversionMap() {
        if (SPECIFIC_TO_GENERIC != null) {
            return SPECIFIC_TO_GENERIC;
        }
        SPECIFIC_TO_GENERIC = new HashMap<String, String>();

        SPECIFIC_TO_GENERIC.put(JsConsts.FLOAT,
                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        SPECIFIC_TO_GENERIC.put(JsConsts.NUMBER,
                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        SPECIFIC_TO_GENERIC.put(JsConsts.STRING,
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        SPECIFIC_TO_GENERIC.put(JsConsts.PERFORMER,
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        SPECIFIC_TO_GENERIC.put(JsConsts.BOM_DATE,
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);
        SPECIFIC_TO_GENERIC.put(JsConsts.DATETIMELC,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME);
        SPECIFIC_TO_GENERIC.put(JsConsts.DATETIMETZLC,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME);
        SPECIFIC_TO_GENERIC.put(JsConsts.DATETIMETZ,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME);

        return SPECIFIC_TO_GENERIC;
    }
    
}
