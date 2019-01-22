/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * Utility class for checking nominal process data type to process data type
 * compatibility for mapping.
 * 
 * @author aallway
 * @since 16 Dec 2010
 */
public class ProcessDataMappingCompatibilityUtil {

    /**
     * Check compatibility between source and target that have super type sub
     * type relationship between them
     * 
     * @param srcDataType
     * @param targetDataType
     * @return
     */
    public static boolean checkSubTypeCompatibility(
            IScriptRelevantData srcDataType, ConceptPath targetDataType) {

        if (srcDataType != null && targetDataType != null) {
            Object targetType =
                    BasicTypeConverterFactory.INSTANCE
                            .getBaseType(targetDataType.getItem(), true);

            if (null == targetType) {
                targetType = targetDataType.getType();
            }

            if (JScriptUtils.isDynamicComplexType(srcDataType)) {
                if (srcDataType instanceof IUMLScriptRelevantData) {
                    IUMLScriptRelevantData iumlDataType =
                            (IUMLScriptRelevantData) srcDataType;
                    JsClass jsClass = iumlDataType.getJsClass();

                    if (jsClass != null && jsClass.getUmlClass() != null) {
                        Class umlClass = jsClass.getUmlClass();
                        if (null != umlClass && targetType instanceof Class) {
                            if (JScriptUtils.isDynamicComplexType(umlClass)
                                    && JScriptUtils
                                            .isDynamicComplexType((Class) targetType)) {
                                boolean isRHSSubType =
                                        JScriptUtils
                                                .isSubType((Class) targetType,
                                                        umlClass);
                                if (isRHSSubType) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * Check compatibility between two concept paths that have super type sub
     * type relationship between them
     * 
     * @param srcData
     * @param targetData
     * @param directionType
     * @return
     */
    public static boolean checkSubTypeCompatibility(ConceptPath srcData,
            ConceptPath targetData, DirectionType directionType) {

        if (isSrcTargetDataItemsNotNull(srcData, targetData)) {
            Object sourceType = srcData.getType();
            Object targetType = targetData.getType();

            if (sourceType instanceof Class && targetType instanceof Class) {
                if (JScriptUtils.isDynamicComplexType((Class) sourceType)
                        && JScriptUtils
                                .isDynamicComplexType((Class) targetType)) {
                    Class sourceClass = (Class) sourceType;
                    Class targetClass = (Class) targetType;

                    /* XPD-2181: */

                    boolean isRHSSubType =
                            JScriptUtils.isSubType(targetClass, sourceClass);
                    if (isRHSSubType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check compatibility of the to process data concept paths.
     * 
     * @param srcData
     * @param targetData
     * @param directionType
     * 
     * @return OK, WRONGTYPE or WRONGSIZE
     */
    public static MappingTypeCompatibility checkTypeCompatibility(
            ConceptPath srcData, ConceptPath targetData,
            DirectionType directionType) {

        MappingTypeCompatibility match = MappingTypeCompatibility.WRONGTYPE;

        if (isSrcTargetDataItemsNotNull(srcData, targetData)) {

            // XPD-5705 Case Ref types can ONLY be mapped with Case Ref types
            // and not BOM type process data
            boolean validCaseRefMapping =
                    !isCaseRefToFromNonCaseRef(srcData, targetData);
            // valid
            if (!validCaseRefMapping) {
                return MappingTypeCompatibility.WRONGTYPE;
            }

            Object sourceType =
                    BasicTypeConverterFactory.INSTANCE.getBaseType(srcData
                            .getItem(), true);
            Object targetType =
                    BasicTypeConverterFactory.INSTANCE.getBaseType(targetData
                            .getItem(), true);

            if (sourceType instanceof BasicType
                    && targetType instanceof BasicType) {
                return BasicTypeHandler
                        .handleSourceTargetBasicTypes(sourceType,
                                targetType,
                                directionType);
            } else if (sourceType == targetType) {
                /*
                 * Otherwise, if not BasicType's (or equivalent thereof) then
                 * they must be EXACTLY then same!
                 */
                match = MappingTypeCompatibility.OK;
            } else if (sourceType instanceof Class
                    && targetType instanceof Class) {

                /*
                 * XPD-2181: we check the sub to super type compatibility here.
                 * (super to sub type is checked in checkSubTypeCompatibility()
                 */
                Class sourceClass = (Class) sourceType;
                Class targetClass = (Class) targetType;

                if (JScriptUtils.isDynamicComplexType(sourceClass)
                        && JScriptUtils.isDynamicComplexType(targetClass)) {
                    boolean isLHSSubType =
                            JScriptUtils.isSubType(sourceClass, targetClass);
                    if (isLHSSubType) {
                        match = MappingTypeCompatibility.OK;
                    }
                }

            }/*
              * XPD-7014: Allow BOM.Enumeration Attribute <-> Text mapping
              */
            else if ((sourceType instanceof BasicType
                    && BasicTypeType.STRING_LITERAL
                            .equals(((BasicType) sourceType).getType()) && targetType instanceof Enumeration)
                    || (targetType instanceof BasicType
                            && BasicTypeType.STRING_LITERAL
                                    .equals(((BasicType) targetType).getType()) && sourceType instanceof Enumeration)) {

                return MappingTypeCompatibility.OK;
            } else {
                /*
                 * sourceType is bom class, targetType is null because
                 * targetData is bom object subtype xsd type
                 */
                if (null != sourceType && null == targetType) {
                    match =
                            BOMXSDTypesHandler.handleXSDTypes(srcData,
                                    targetData,
                                    sourceType,
                                    match);
                }
            }
        }
        return match;
    }

    /**
     * Return <code>true</code> if the mapping is a case-ref type data to/from a
     * non-case-ref type data, <code>false</code> otherwise
     * 
     * @param srcData
     * @param targetData
     * 
     * @return <code>true</code> if the mapping is a case-ref type data to/from
     *         a non-case-ref type data, <code>false</code> otherwise
     */
    private static boolean isCaseRefToFromNonCaseRef(ConceptPath srcData,
            ConceptPath targetData) {

        if (isCaseReferenceField(srcData) == isCaseReferenceField(targetData)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Return <code>true</code> if the specified concept path's item is of type
     * RecordType, <code>false</code> otherwise.
     * 
     * @param conceptPath
     * 
     * @return <code>true</code> if the specified concept path's item is of type
     *         RecordType, <code>false</code> otherwise.
     */
    private static boolean isCaseReferenceField(ConceptPath conceptPath) {

        Object cpItem = conceptPath.getItem();

        if (cpItem instanceof ProcessRelevantData) {
            ProcessRelevantData prd = (ProcessRelevantData) cpItem;
            DataType dataType = prd.getDataType();
            return (dataType instanceof RecordType);
        }

        return false;

    }

    /**
     * 
     * @param srcData
     * @param targetData
     * @return
     */
    private static boolean isSrcTargetDataItemsNotNull(ConceptPath srcData,
            ConceptPath targetData) {
        return srcData != null && srcData.getItem() != null
                && targetData != null && targetData.getItem() != null;
    }

    // XPD-5969: BasicTypeHandler moved to its own class to be accessed/reused
    // from other locations to raise truncation warnings on mappings.

    /**
     * This class handles concept path simple mappings when sourceType is bom
     * class and targetData is a bom object with xsd* subtype
     * 
     * @author bharge
     * 
     */
    private static class BOMXSDTypesHandler {

        /**
         * 
         * @param srcData
         * @param targetData
         * @param sourceType
         * @param match
         * @return
         */
        protected static MappingTypeCompatibility handleXSDTypes(
                ConceptPath srcData, ConceptPath targetData, Object sourceType,
                MappingTypeCompatibility match) {

            String targetObjectSubType =
                    ConceptUtil.getObjectSubType(targetData,
                            targetData.getType());

            if (targetObjectSubType.length() > 0) {
                if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match =
                            handleXSDAny(srcData, targetData, sourceType, match);
                } else if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match =
                            handleXSDAnyAttribute(srcData,
                                    targetData,
                                    sourceType,
                                    match);
                } else if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match =
                            handleXSDAnySimpleType(srcData,
                                    targetData,
                                    sourceType,
                                    match);
                } else if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match =
                            handleXSDAnyType(srcData,
                                    targetData,
                                    sourceType,
                                    match);
                }
            }
            return match;
        }

        /**
         * 
         * @param srcData
         * @param targetData
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAnyType(
                ConceptPath srcData, ConceptPath targetData, Object sourceType,
                MappingTypeCompatibility match) {

            if (sourceType instanceof BasicType
                    || sourceType instanceof org.eclipse.uml2.uml.Class) {
                match = MappingTypeCompatibility.OK;
            }
            return match;
        }

        /**
         * 
         * @param srcData
         * @param targetData
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAnySimpleType(
                ConceptPath srcData, ConceptPath targetData, Object sourceType,
                MappingTypeCompatibility match) {

            if (sourceType instanceof BasicType) {
                match = MappingTypeCompatibility.OK;
            }
            return match;
        }

        /**
         * 
         * @param srcData
         * @param targetData
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAnyAttribute(
                ConceptPath srcData, ConceptPath targetData, Object sourceType,
                MappingTypeCompatibility match) {

            return match;
        }

        /**
         * 
         * @param srcData
         * @param targetData
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAny(
                ConceptPath srcData, ConceptPath targetData, Object sourceType,
                MappingTypeCompatibility match) {

            if (sourceType instanceof org.eclipse.uml2.uml.Class) {
                match = MappingTypeCompatibility.OK;
            }
            return match;
        }
    }
}
