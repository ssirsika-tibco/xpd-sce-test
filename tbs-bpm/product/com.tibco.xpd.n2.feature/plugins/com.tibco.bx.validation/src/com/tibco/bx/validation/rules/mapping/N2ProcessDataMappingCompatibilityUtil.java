/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.validation.rules.mapping.helpers.BaseScriptToConceptPathMappingsHelper;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsAttribute;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Utility class for checking nominal process data type to process data type
 * compatibility for script mapping.
 * 
 * @author mtorres
 * @since 16 Dec 2010
 */
public class N2ProcessDataMappingCompatibilityUtil {

    /**
     * Check compatibility of the source and target data type.
     * 
     * @param srcDataType
     * @param targetDataType
     * @param directionType
     * 
     * @return OK, WRONGTYPE or WRONGSIZE
     */
    @SuppressWarnings("restriction")
    public static MappingTypeCompatibility checkTypeCompatibility(
            IScriptRelevantData srcDataType, ConceptPath targetDataType) {

        MappingTypeCompatibility match = MappingTypeCompatibility.WRONGTYPE;

        if (srcDataType != null && targetDataType != null) {

            Object targetType = BasicTypeConverterFactory.INSTANCE
                    .getBaseType(targetDataType.getItem(), true);

            if (JScriptUtils.isDynamicComplexType(srcDataType)) {
                match = handleSourceComplexType(srcDataType,
                        targetDataType,
                        match,
                        targetType);
            } else if (JsConsts.ARRAY.equals(getSrcDataTypeStr(srcDataType))) {
                // XPD-7352: If source is a List we can't check types.
                match = MappingTypeCompatibility.OK;
            } else if (targetType instanceof Enumeration) {

                /*
                 * XPD-7014: Allow Script Enumerations assignments Enum<-> Enum
                 */
                Classifier sourceType = BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(srcDataType);

                if (targetType.equals(sourceType)) {
                    /* Same Enum */
                    return MappingTypeCompatibility.OK;
                }

                /*
                 * NOTE: The following check is required to handle scenarios
                 * where a user defined Script has something like
                 * "com_example_enummapping_StatusEnum.get("");" as its last
                 * statement and hence the return statement. The Source data
                 * type contains the full qualified type name
                 * "com.example.enummapping.StatusEnum" and does not contain the
                 * type.Hence to check the target Enumeration type match,
                 * following qualified name match is used.
                 */
                String targetQualifiedName = ProcessDataUtil
                        .getQualifiedNameForTypeCompatibilityCheck(
                                (Enumeration) targetType);

                String sourceTypeStr = BaseScriptToConceptPathMappingsHelper
                        .getSourceTypeStr(srcDataType, sourceType);

                if (targetQualifiedName.equals(sourceTypeStr)) {

                    return MappingTypeCompatibility.OK;
                }

            } else {
                match = handleSourceBasicType(srcDataType,
                        targetDataType,
                        match,
                        targetType);
            }
        }
        return match;
    }

    /**
     * Check compatibility of the script relevant data with the basic target type.
     * 
     * @param srcDataType source data type defined by {@link IScriptRelevantData}
     * @param targetDataType {@link BasicType}
     * 
     * @return OK, WRONGTYPE or WRONGSIZE
     */
    public static MappingTypeCompatibility checkTypeCompatibility(
            IScriptRelevantData srcDataType, BasicType targetDataType) {
    	  MappingTypeCompatibility match = MappingTypeCompatibility.WRONGTYPE;

          if (srcDataType != null && targetDataType != null) {

              if (JScriptUtils.isDynamicComplexType(srcDataType)) {
            	  return MappingTypeCompatibility.WRONGTYPE;
              } else {
            	  // second argument is passed as a null , because targetType is BasicType so need to check for XSD types. 
            	  return handleSourceBasicType(srcDataType,
                          null,
                          match,
                          targetDataType);
              }
          }
          
          return match;
    }
    /**
     * Checks whether the Integer size of source formal parameter/data field is
     * incompatible with BOM primitive Integer type target.
     * 
     * @param srcObject
     *            - ConceptPath
     * @param trgtObject
     *            - ConceptPath
     * @return <code>true</code> if size of srcObject (Integer type formal
     *         parameter/data field) is greater than trgtObject (BOM primitive
     *         Integer type), <code>false</code> otherwise.
     */
    public static boolean incompatibleIntTypeDataLength(Object srcObject,
            Object trgtObject) {

        if (srcObject instanceof ConceptPath
                && trgtObject instanceof ConceptPath) {
            Object srcItem = ((ConceptPath) srcObject).getItem();

            // get source object integer type length
            int srcIntLength = 9;
            boolean isSrcItemIntegerType = false;
            if (srcItem instanceof ProcessRelevantData) {

                DataType srcItemType =
                        ((ProcessRelevantData) srcItem).getDataType();
                if (srcItemType instanceof BasicType) {

                    BasicType srcItemBasicType = (BasicType) srcItemType;
                    if (BasicTypeType.INTEGER_LITERAL
                            .equals(srcItemBasicType.getType())
                            && srcItemBasicType.getPrecision() != null) {
                        srcIntLength =
                                srcItemBasicType.getPrecision().getValue();
                        isSrcItemIntegerType = true;
                    }
                }
            }

            if (isSrcItemIntegerType) {
                // get target object primitive type integer length
                ConceptPath trgtCp = (ConceptPath) trgtObject;
                if (trgtCp.getType() instanceof PrimitiveType) {
                    PrimitiveType pt = (PrimitiveType) trgtCp.getType();
                    if (pt != null) {
                        PrimitiveType basePType =
                                PrimitivesUtil.getBasePrimitiveType(pt);

                        ResourceSet rSet = XpdResourcesPlugin.getDefault()
                                .getEditingDomain().getResourceSet();

                        if (basePType == PrimitivesUtil
                                .getStandardPrimitiveTypeByName(rSet,
                                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME)) {

                            // Check Integer Length
                            Object facetPropertyValue =
                                    PrimitivesUtil.getFacetPropertyValue(pt,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);

                            if (facetPropertyValue instanceof Integer) {
                                Integer maxIntLen =
                                        (Integer) facetPropertyValue;

                                if (srcIntLength > maxIntLen) {
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
     * @param srcDataType
     * @param targetDataType
     * @param match
     * @param targetType
     * @return
     */
    private static MappingTypeCompatibility handleSourceBasicType(
            IScriptRelevantData srcDataType, ConceptPath targetDataType,
            MappingTypeCompatibility match, Object targetType) {

        String srcDataTypeStr = getSrcDataTypeStr(srcDataType);

        if (targetType instanceof BasicType) {
            match = handleTargetBasicType(srcDataType,
                    match,
                    targetType,
                    srcDataTypeStr);
        } else {
            match = BOMXSDTypesHandler.handleXSDTypes(srcDataType,
                    targetDataType,
                    srcDataType,
                    match);
        }
        return match;
    }

    /**
     * 
     * @param srcDataType
     * @return
     */
    private static String getSrcDataTypeStr(IScriptRelevantData srcDataType) {

        String srcDataTypeStr = srcDataType.getType();

        Map<String, String> specificToGenericTypeConversionMap =
                N2JScriptDataTypeMapper.getInstance()
                        .getSpecificToGenericTypeConversionMap();

        if (specificToGenericTypeConversionMap != null) {
            String genericType =
                    specificToGenericTypeConversionMap.get(srcDataTypeStr);
            if (genericType != null) {
                srcDataTypeStr = genericType;
            }
        }
        return srcDataTypeStr;
    }

    /**
     * 
     * @param srcDataType
     * @param match
     * @param targetType
     * @param srcDataTypeStr
     * @return
     */
    private static MappingTypeCompatibility handleTargetBasicType(
            IScriptRelevantData srcDataType, MappingTypeCompatibility match,
            Object targetType, String srcDataTypeStr) {

        Map<String, String> specificToGenericTypeConversionMap =
                N2JScriptDataTypeMapper.getInstance()
                        .getSpecificToGenericTypeConversionMap();

        Map<BasicTypeType, String> processTypeToJavaScriptConversionMap =
                N2JScriptDataTypeMapper.getInstance()
                        .getProcessTypeToJavaScriptConversionMap();

        /*
         * XPD-7873: Add the check for compatible assignment types in mappings
         */
        Map<String, Set<String>> compatibleAssignmentOperatorTypesMap =
                N2JScriptDataTypeMapper.getInstance()
                        .getCompatibleAssignmentTypesMap();
        compatibleAssignmentOperatorTypesMap = N2JScriptDataTypeMapper
                .getInstance().convertSpecificMapToGeneric(
                        compatibleAssignmentOperatorTypesMap);

        if (processTypeToJavaScriptConversionMap != null) {
            String targetTypeStr = processTypeToJavaScriptConversionMap
                    .get(((BasicType) targetType).getType());
            if (specificToGenericTypeConversionMap != null) {
                String genericType =
                        specificToGenericTypeConversionMap.get(targetTypeStr);
                if (genericType != null) {
                    targetTypeStr = genericType;
                }

                if (compatibleAssignmentOperatorTypesMap != null) {

                    /*
                     * XPD-7897: Mapping from source to target in terms of
                     * assignment is target = source, so get the assignment map
                     * for target and check if source is compatible to be
                     * assigned to target!
                     */
                    Set<String> compatibleEqualityOperatorSet =
                            compatibleAssignmentOperatorTypesMap
                                    .get(targetTypeStr);

                    if (compatibleEqualityOperatorSet != null
                            && compatibleEqualityOperatorSet
                                    .contains(srcDataTypeStr)) {

                        return MappingTypeCompatibility.OK;
                    }
                }
            }
            /*
             * XPD-7920: Now that we are checking the compatibility assignment
             * from the map (see above XPD-7897), the following checks may not
             * be required. But I prefer to leave this as it is as a fall back
             * mechanism (to avoid any regressions).
             */
            if (srcDataTypeStr != null && targetTypeStr != null
                    && targetTypeStr.equals(srcDataTypeStr)) {
                match = MappingTypeCompatibility.OK;
            } else if (JsConsts.FLOAT.equals(srcDataTypeStr)
                    && JsConsts.INTEGER.equals(targetTypeStr)) {
                match = MappingTypeCompatibility.OK;
            } else if (JsConsts.INTEGER.equals(srcDataTypeStr)
                    && JsConsts.FLOAT.equals(targetTypeStr)) {
                match = MappingTypeCompatibility.OK;
            } else if (JsConsts.DATETIME.equals(srcDataTypeStr)
                    && JsConsts.DATETIMETZ.equals(targetTypeStr)) {
                match = MappingTypeCompatibility.OK;
            } else if (JsConsts.DATETIMETZ.equals(srcDataTypeStr)
                    && JsConsts.DATETIME.equals(targetTypeStr)) {
                match = MappingTypeCompatibility.OK;
            }
        }

        return match;

    }

    /**
     * 
     * @param srcDataType
     * @param targetDataType
     * @param match
     * @param targetType
     * @return
     */
    private static MappingTypeCompatibility handleSourceComplexType(
            IScriptRelevantData srcDataType, ConceptPath targetDataType,
            MappingTypeCompatibility match, Object targetType) {

        if (srcDataType instanceof IUMLScriptRelevantData) {

            IUMLScriptRelevantData iumlDataType =
                    (IUMLScriptRelevantData) srcDataType;
            JsClass jsClass = iumlDataType.getJsClass();

            if (jsClass != null && jsClass.getUmlClass() != null) {

                Class umlClass = jsClass.getUmlClass();
                if (umlClass == targetType) {
                    match = MappingTypeCompatibility.OK;
                } else if (null != umlClass && targetType instanceof Class) {
                    /*
                     * XPD-2181: we check the sub to super type compatibility
                     * here. (super to sub type is checked in
                     * checkSubTypeCompatibility())
                     */
                    if (JScriptUtils.isDynamicComplexType(umlClass)
                            && JScriptUtils
                                    .isDynamicComplexType((Class) targetType)) {
                        boolean isLHSSubType = JScriptUtils.isSubType(umlClass,
                                (Class) targetType);
                        if (isLHSSubType) {
                            match = MappingTypeCompatibility.OK;
                        }
                    }
                } else {
                    /*
                     * sourceType is bom class, targetType is null (because
                     * targetDataType is bom object subtype xsd type) so they
                     * dont match. get the bom object sub type and allow the
                     * mappings
                     */
                    match = BOMXSDTypesHandler.handleXSDTypes(srcDataType,
                            targetDataType,
                            umlClass,
                            match);
                }
            }
        }
        return match;
    }

    /**
     * This class handles user defined script mapping issues when targetData is
     * object type
     * 
     * @author bharge
     * 
     */
    private static class BOMXSDTypesHandler {

        /**
         * 
         * @param srcDataType
         * @param targetData
         * @param sourceType
         * @param match
         * @return
         */
        protected static MappingTypeCompatibility handleXSDTypes(
                IScriptRelevantData srcDataType, ConceptPath targetData,
                Object sourceType, MappingTypeCompatibility match) {

            String targetObjectSubType = ConceptUtil
                    .getObjectSubType(targetData, targetData.getType());

            if (targetObjectSubType.length() > 0) {
                if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match = handleXSDAny(srcDataType,
                            targetData,
                            sourceType,
                            match);

                } else if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match = handleXSDAnyAttribute(srcDataType,
                            targetData,
                            sourceType,
                            match);

                } else if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match = handleXSDAnySimpleType(srcDataType,
                            targetData,
                            sourceType,
                            match);

                } else if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE
                        .equalsIgnoreCase(targetObjectSubType)) {
                    match = handleXSDAnyType(srcDataType,
                            targetData,
                            sourceType,
                            match);
                }
            }
            return match;
        }

        /**
         * 
         * @param srcDataType
         * @param target
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAnyType(
                IScriptRelevantData srcDataType, ConceptPath target,
                Object sourceType, MappingTypeCompatibility match) {

            Object extendedInfo = null;

            /* match ok if it is bom class */
            if (sourceType instanceof org.eclipse.uml2.uml.Class) {
                match = MappingTypeCompatibility.OK;
            } else {
                if (srcDataType instanceof DefaultScriptRelevantData) {
                    DefaultScriptRelevantData defaultScriptRelevantData =
                            (DefaultScriptRelevantData) srcDataType;
                    extendedInfo = defaultScriptRelevantData.getExtendedInfo();
                } else {
                    extendedInfo =
                            ((ITypeResolution) srcDataType).getExtendedInfo();
                }
                if (null != extendedInfo) {

                    if (extendedInfo instanceof DefaultJsAttribute) {

                        DefaultJsAttribute defaultJsAttribute =
                                (DefaultJsAttribute) extendedInfo;
                        Element element = defaultJsAttribute.getElement();

                        if (element instanceof Property) {
                            Property property = (Property) element;
                            Type srcScriptType = property.getType();

                            if (srcScriptType instanceof PrimitiveType
                                    || srcScriptType instanceof org.eclipse.uml2.uml.Class) {

                                /* match ok if it is same object sub type */
                                if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                        .equals(srcScriptType.getName())) {

                                    String sourceObjectSubType = ConceptUtil
                                            .getObjectSubType(property);
                                    String targetObjectSubType =
                                            ConceptUtil.getObjectSubType(target,
                                                    target.getType());

                                    if (sourceObjectSubType.length() > 0
                                            && targetObjectSubType
                                                    .length() > 0) {

                                        if (JSTypesCompatabilityUtil
                                                .getCompatibleBOMObjectSubTypesMap()
                                                .get(sourceObjectSubType)
                                                .contains(
                                                        targetObjectSubType)) {
                                            match = MappingTypeCompatibility.OK;
                                        }

                                    }

                                } else {
                                    /*
                                     * match ok if it is bom primitive type or
                                     * bom class
                                     */
                                    match = MappingTypeCompatibility.OK;
                                }
                            }
                        }
                    } else if (extendedInfo instanceof Boolean) {
                        /*
                         * TODO: when script is process simple type mapped to
                         * target object sub type extendedInfo is shown as
                         * instance of Boolean; i could as well assign match ok
                         * even without checking for the condition!
                         */
                        match = MappingTypeCompatibility.OK;
                    }

                }
            }

            return match;
        }

        /**
         * 
         * @param srcDataType
         * @param target
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAnySimpleType(
                IScriptRelevantData srcDataType, ConceptPath target,
                Object sourceType, MappingTypeCompatibility match) {

            Object extendedInfo = null;
            if (srcDataType instanceof DefaultScriptRelevantData) {
                DefaultScriptRelevantData defaultScriptRelevantData =
                        (DefaultScriptRelevantData) srcDataType;
                extendedInfo = defaultScriptRelevantData.getExtendedInfo();
            } else {
                extendedInfo =
                        ((ITypeResolution) srcDataType).getExtendedInfo();
            }
            if (null != extendedInfo) {

                if (extendedInfo instanceof DefaultJsAttribute) {

                    DefaultJsAttribute defaultJsAttribute =
                            (DefaultJsAttribute) extendedInfo;
                    Element element = defaultJsAttribute.getElement();

                    if (element instanceof Property) {
                        Property property = (Property) element;
                        Type srcScriptType = property.getType();

                        if (srcScriptType instanceof PrimitiveType) {

                            if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                    .equals(srcScriptType.getName())) {

                                String sourceObjectSubType =
                                        ConceptUtil.getObjectSubType(property);
                                String targetObjectSubType =
                                        ConceptUtil.getObjectSubType(target,
                                                target.getType());

                                if (sourceObjectSubType.length() > 0
                                        && targetObjectSubType.length() > 0) {

                                    if (JSTypesCompatabilityUtil
                                            .getCompatibleBOMObjectSubTypesMap()
                                            .get(sourceObjectSubType)
                                            .contains(targetObjectSubType)) {
                                        match = MappingTypeCompatibility.OK;
                                    }

                                }

                            } else {
                                match = MappingTypeCompatibility.OK;
                            }
                        }
                    }
                } else if (extendedInfo instanceof Boolean) {
                    /*
                     * TODO: when script is process simple type mapped to target
                     * object sub type extendedInfo is shown as instance of
                     * Boolean; i could as well assign match ok even without
                     * checking for the condition!
                     */
                    match = MappingTypeCompatibility.OK;
                }
            }
            if (sourceType instanceof BasicType) {
                match = MappingTypeCompatibility.OK;
            }
            return match;
        }

        /**
         * 
         * @param srcDataType
         * @param target
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAnyAttribute(
                IScriptRelevantData srcDataType, ConceptPath target,
                Object sourceType, MappingTypeCompatibility match) {

            Object extendedInfo = null;
            if (srcDataType instanceof DefaultScriptRelevantData) {
                DefaultScriptRelevantData defaultScriptRelevantData =
                        (DefaultScriptRelevantData) srcDataType;
                extendedInfo = defaultScriptRelevantData.getExtendedInfo();
            } else {
                extendedInfo =
                        ((ITypeResolution) srcDataType).getExtendedInfo();
            }
            if (null != extendedInfo) {

                if (extendedInfo instanceof DefaultJsAttribute) {

                    DefaultJsAttribute defaultJsAttribute =
                            (DefaultJsAttribute) extendedInfo;
                    Element element = defaultJsAttribute.getElement();

                    if (element instanceof Property) {
                        Property property = (Property) element;
                        Type srcScriptType = property.getType();

                        if (srcScriptType instanceof PrimitiveType) {

                            if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                    .equals(srcScriptType.getName())) {

                                String sourceObjectSubType =
                                        ConceptUtil.getObjectSubType(property);
                                String targetObjectSubType =
                                        ConceptUtil.getObjectSubType(target,
                                                target.getType());

                                if (sourceObjectSubType.length() > 0
                                        && targetObjectSubType.length() > 0) {

                                    if (JSTypesCompatabilityUtil
                                            .getCompatibleBOMObjectSubTypesMap()
                                            .get(sourceObjectSubType)
                                            .contains(targetObjectSubType)) {
                                        match = MappingTypeCompatibility.OK;
                                    }

                                }

                            }
                        }
                    }
                }
            }

            return match;
        }

        /**
         * 
         * @param srcDataType
         * @param target
         * @param sourceType
         * @param match
         * @return
         */
        private static MappingTypeCompatibility handleXSDAny(
                IScriptRelevantData srcDataType, ConceptPath target,
                Object sourceType, MappingTypeCompatibility match) {

            if (sourceType instanceof org.eclipse.uml2.uml.Class) {
                match = MappingTypeCompatibility.OK;
            }
            return match;
        }

    }
}
