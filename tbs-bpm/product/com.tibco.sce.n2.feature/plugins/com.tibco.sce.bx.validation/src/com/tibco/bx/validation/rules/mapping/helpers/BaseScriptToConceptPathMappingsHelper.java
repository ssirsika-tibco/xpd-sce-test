/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.DefaultJsAttribute;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Helper class for script to concept path mappings
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class BaseScriptToConceptPathMappingsHelper {

    /**
     * 
     * @param sourceType
     * @param targetType
     * @return
     */
    public static boolean isBomClassToBomClassMapping(Classifier sourceType,
            Classifier targetType) {
        if (sourceType instanceof Class && targetType instanceof Class) {
            if (JScriptUtils.isDynamicComplexType(sourceType)
                    && JScriptUtils.isDynamicComplexType(targetType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * For the given source script relevant data get the classifier
     * 
     * @param type
     * @return the (BOM) classifier for the script relevant data
     */
    public static Classifier getSourceScriptTypeClassifier(
            IScriptRelevantData type) {
        if (type instanceof IUMLScriptRelevantData) {
            IUMLScriptRelevantData iumlType = (IUMLScriptRelevantData) type;
            JsClass jsClass = iumlType.getJsClass();
            if (jsClass != null) {
                return jsClass.getUmlClass();
            }
        }
        return null;
    }

    /**
     * get the base primitive type name, fully qualified name or classifier name
     * for the given script relevant data and classifier
     * 
     * @param type
     * @param sourceType
     * @return base primitive type name or fully qualified name of the
     *         classifier or name of the classifier
     */
    public static String getSourceTypeStr(IScriptRelevantData type,
            Classifier sourceType) {

        String sourceTypeStr = null;

        if (sourceType != null) {
            PrimitiveType sourceBasePrimitiveType = null;

            if (sourceType instanceof PrimitiveType) {
                // Extract the base primitive type
                sourceBasePrimitiveType =
                        PrimitivesUtil
                                .getBasePrimitiveType((PrimitiveType) sourceType);
                if (sourceBasePrimitiveType != null) {
                    sourceTypeStr = sourceBasePrimitiveType.getName();
                }
            } else if (sourceType instanceof org.eclipse.uml2.uml.Class) {
                sourceTypeStr = JScriptUtils.getFQType(sourceType);
            }

            if (sourceTypeStr == null) {
                sourceTypeStr = sourceType.getName();
            }
        } else {
            sourceTypeStr = JScriptUtils.getFQType(type);
        }
        return sourceTypeStr;
    }

    /**
     * checks if the mapping is between non union and union
     * 
     * @param activity
     * @param type
     * @param target
     * @return <code>true</code> if the mapping is between non union to union
     *         <code>false</code> otherwise
     */
    public static boolean isNonUnionToUnionMapping(Activity activity,
            IScriptRelevantData type, ConceptPath target) {

        boolean isSourceUnion = false;
        boolean isTargetUnion = false;
        Classifier targetType = target.getType();

        if (targetType instanceof org.eclipse.uml2.uml.DataType) {

            org.eclipse.uml2.uml.DataType targetDataType =
                    (org.eclipse.uml2.uml.DataType) targetType;

            isTargetUnion = XSDUtil.isUnion(targetDataType);

            DefaultScriptRelevantData defaultScriptRelevantData =
                    (DefaultScriptRelevantData) type;

            Object extendedInfo = defaultScriptRelevantData.getExtendedInfo();

            if (extendedInfo instanceof DefaultJsAttribute) {

                DefaultJsAttribute defaultJsAttribute =
                        (DefaultJsAttribute) extendedInfo;
                Element element = defaultJsAttribute.getElement();

                /*
                 * XPD-1977: if target is union then source must be member type
                 * of the target union type members
                 */
                if (element instanceof Property) {
                    Property property = (Property) element;
                    Type srcScriptType = property.getType();

                    if (srcScriptType instanceof PrimitiveType) {
                        PrimitiveType sourceDataType =
                                (PrimitiveType) srcScriptType;
                        isSourceUnion = XSDUtil.isUnion(sourceDataType);
                    }
                }
            }
        }
        if (!isSourceUnion && isTargetUnion) {
            return true;
        }

        return false;
    }

    /**
     * Basically checks the mappings between source and target combination of
     * union types (target could be union, enumeration or primitive type)
     * 
     * @param activity
     * @param target
     * @param type
     * @return WebServiceJavaScriptMappingIssue[]
     */
    @SuppressWarnings("restriction")
    public static WebServiceJavaScriptMappingIssue[] getIssuesForUserDefinedScriptMappings(
            Activity activity, ConceptPath target, IScriptRelevantData type) {

        String targetTypeName =
                BaseMappingsIssuesHelper.getTypeName(target.getType(), target);
        Classifier targetType = target.getType();

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(type);
        String sourceTypeStr =
                BaseScriptToConceptPathMappingsHelper.getSourceTypeStr(type,
                        sourceType);

        if (type instanceof ITypeResolution) {
            boolean isSourceUnion = false;
            boolean isTargetUnion = false;

            org.eclipse.uml2.uml.DataType sourceDataType =
                    JScriptUtils.getDataType(type);
            if (null != sourceDataType) {
                isSourceUnion = XSDUtil.isUnion(sourceDataType);
            }

            if (targetType instanceof PrimitiveType) {
                org.eclipse.uml2.uml.DataType targetDataType =
                        (org.eclipse.uml2.uml.DataType) targetType;

                isTargetUnion = XSDUtil.isUnion(targetDataType);

                if (isSourceUnion && isTargetUnion) {
                    if (!sourceDataType.equals(targetDataType)) {
                        return BaseMappingsIssuesHelper
                                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }
                } else if (isSourceUnion && !isTargetUnion) {
                    return BaseMappingsIssuesHelper
                            .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                } else if (!isSourceUnion && isTargetUnion) {

                    List<org.eclipse.uml2.uml.DataType> targetUnionMemberTypes =
                            XSDUtil.getUnionMemberTypes(targetDataType);

                    if (null != sourceDataType) {
                        /*
                         * XPD-2174: allow mapping when source is a bom
                         * attribute of simple type that matches one of the
                         * member types of union.
                         */
                        for (org.eclipse.uml2.uml.DataType dataType : targetUnionMemberTypes) {

                            String typeName =
                                    BaseMappingsIssuesHelper
                                            .getTypeName(dataType, target);

                            if (sourceDataType.getName() != null
                                    && typeName != null) {

                                Set<String> compatibleTypes =
                                        JSTypesCompatabilityUtil
                                                .getCompatibleBOMtoBOMTypesMap()
                                                .get(sourceDataType.getName());
                                /*
                                 * Compares if both the types are of a base type
                                 * primitive type.
                                 */
                                /*
                                 * if one of the member types of the union is
                                 * matching exit out of the loop
                                 */
                                if (sourceDataType.getName().equals(typeName)) {
                                    break;
                                } else if (null != compatibleTypes
                                        && !compatibleTypes.contains(typeName)) {
                                    return BaseMappingsIssuesHelper
                                            .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                                }
                            }
                        }
                    }

                } else { /* both are not union */
                    if (!doesScriptTypeMatch(target, sourceTypeStr, targetType)) {
                        return BaseMappingsIssuesHelper
                                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }
                }

            } else {
                if (!isTargetUnion) {
                    if (targetType instanceof Enumeration) {
                        if (!targetType.equals(sourceDataType)) {
                            return BaseMappingsIssuesHelper
                                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                        }
                    } else if (!PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                            .equals(targetTypeName)) {
                        return BaseMappingsIssuesHelper
                                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }
                }
            }

        }

        return null;
    }

    /**
     * Comparing Script Types to BOM Primitive types
     * 
     * @param target
     * @param dataType
     * @param type
     * @return <code>true</code> if the types match <code>false</code> otherwise
     */
    public static Boolean doesScriptTypeMatch(ConceptPath target,
            String scriptType, Object targetType) {
        String targetTypeStr = null;
        if (targetType instanceof PrimitiveType) {
            targetTypeStr =
                    BaseMappingsIssuesHelper
                            .getTypeName((Classifier) targetType, target);

        } else if (targetType instanceof org.eclipse.uml2.uml.Class) {
            targetTypeStr = JScriptUtils.getFQType((Class) targetType);

        } else if (targetType instanceof BasicType) {
            BasicType targetBasicType = (BasicType) targetType;
            Map<BasicTypeType, String> processTypeToJavaScriptConversionMap =
                    N2JScriptDataTypeMapper.getInstance()
                            .getProcessTypeToJavaScriptConversionMap();
            if (processTypeToJavaScriptConversionMap != null
                    && targetBasicType.getType() != null) {
                targetTypeStr =
                        processTypeToJavaScriptConversionMap
                                .get(targetBasicType.getType());
            }
        }

        if (scriptType != null && targetTypeStr != null) {
            String lhsTypeStr =
                    BaseMappingsIssuesHelper
                            .convertSpecificToGenericType(scriptType);
            String rhsTypeStr =
                    BaseMappingsIssuesHelper
                            .convertSpecificToGenericType(targetTypeStr);
            @SuppressWarnings("restriction")
            Map<String, Set<String>> compatibleAssignmentOperatorTypesMap =
                    N2JScriptDataTypeMapper
                            .getInstance()
                            .convertSpecificMapToGeneric(N2JScriptDataTypeMapper
                                    .getInstance()
                                    .getCompatibleAssignmentTypesMap());

            if (compatibleAssignmentOperatorTypesMap != null) {
                Set<String> compatibleEqualityOperatorSet =
                        compatibleAssignmentOperatorTypesMap.get(rhsTypeStr);
                if (compatibleEqualityOperatorSet != null
                        && compatibleEqualityOperatorSet.contains(lhsTypeStr)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

}
