/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import java.util.Map;
import java.util.Set;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.IUMLElement;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Helper class for script to BOM mappings handler
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class ScriptMappingsHelper {

    /**
     * 
     * checks for mappings between target bom primitive object type and source
     * other types
     * 
     * @param activity
     * @param target
     * @param type
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    public static WebServiceJavaScriptMappingIssue[] handleBOMPrimitiveObj(
            Activity activity, ConceptPath target, IScriptRelevantData type) {

        Classifier targetType = target.getType();

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(type);

        /*
         * if target sub type defined then the script in the source return the
         * same sub type as target and it must be in the list of compatible
         * types
         */

        String targetObjectSubType =
                ConceptUtil.getObjectSubType(target, targetType);

        if (type != null && type.getType() != null) {
            if (type instanceof ITypeResolution) {
                Object extendedInfo =
                        ((ITypeResolution) type).getExtendedInfo();
                if (extendedInfo instanceof IUMLElement) {
                    Element element = ((IUMLElement) extendedInfo).getElement();
                    /* this tells if the source is an object with a sub type */
                    if (element instanceof Property) {
                        String sourceObjectSubType =
                                ConceptUtil
                                        .getObjectSubType((Property) element);

                        if (sourceObjectSubType.length() > 0
                                && targetObjectSubType.length() > 0) {

                            if (!JSTypesCompatabilityUtil
                                    .getCompatibleBOMObjectSubTypesMap()
                                    .get(sourceObjectSubType)
                                    .contains(targetObjectSubType)) {
                                return BaseMappingsIssuesHelper
                                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                            } else {
                                /*
                                 * if it finds the matching object sub type for
                                 * this case then need not validate the rest
                                 */
                                return null;
                            }
                        }
                    }
                }

                if (targetObjectSubType.length() > 0) {

                    /* for anyType target source bom class is allowed */
                    if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE
                            .equals(targetObjectSubType)) {
                        if (sourceType instanceof org.eclipse.uml2.uml.Class) {
                            return null;
                        }
                    }

                    /*
                     * anySimpleType source bom class or bom attribute of type
                     * bom class to target anySimpleType is not allowed
                     */
                    if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE
                            .equals(targetObjectSubType)) {
                        if (sourceType instanceof org.eclipse.uml2.uml.Class) {
                            if (JScriptUtils.isDynamicComplexType(sourceType)) {
                                return BaseMappingsIssuesHelper
                                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                            }
                        }
                    }

                    /*
                     * xsdAnyAttribute can be mapped only to xsdAnyAttribute, so
                     * return error if the source is not a xsdAnyAttribute
                     */
                    if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE
                            .equals(targetObjectSubType)) {
                        return BaseMappingsIssuesHelper
                                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }

                    /*
                     * xsdAny cannot be mapped only to process simple data
                     */
                    if (null == sourceType
                            && PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY
                                    .equals(targetObjectSubType)) {
                        return BaseMappingsIssuesHelper
                                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }
                }

            }
        }

        return null;
    }

    /**
     * checks for mappings between target enumeration and source other types
     * 
     * @param activity
     * @param target
     * @param type
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    @SuppressWarnings("restriction")
    public static WebServiceJavaScriptMappingIssue[] handleEnumeration(
            Activity activity, ConceptPath target, IScriptRelevantData type) {

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(type);
        String sourceTypeStr =
                BaseScriptToConceptPathMappingsHelper.getSourceTypeStr(type,
                        sourceType);

        /* If target is enumeration then source must be string. */

        String lhsTypeStr =
                BaseMappingsIssuesHelper
                        .convertSpecificToGenericType(sourceTypeStr);
        /*
         * Start XPD-7014: NOTE: The following check is required to handle
         * scenarios where a user defined Script has something like
         * "com_example_enummapping_StatusEnum.get("");" as its last statement
         * and hence the return statement. The Source data type contains the
         * full qualified type name "com.example.enummapping.StatusEnum" and
         * does not contain the type.Hence to check the target Enumeration type
         * match, following qualified name match is used.
         */
        String targetEnumQualifiedName =
                ProcessDataUtil
                        .getQualifiedNameForTypeCompatibilityCheck((Enumeration) target
                                .getType());

        if (targetEnumQualifiedName.equals(sourceTypeStr)) {
            return null;
        }
        /*
         * END XPD-7014: The target Enumeration can be mapped from a Source
         * Script returning something like com.abc.Enum.get("EnumValue");
         */

        Map<String, Set<String>> compatibleAssignmentOperatorTypesMap =
                N2JScriptDataTypeMapper.getInstance()
                        .convertSpecificMapToGeneric(N2JScriptDataTypeMapper
                                .getInstance()
                                .getCompatibleAssignmentTypesMap());

        if (compatibleAssignmentOperatorTypesMap != null) {
            Set<String> compatibleEqualityOperatorSet =
                    compatibleAssignmentOperatorTypesMap.get(lhsTypeStr);
            if (null != compatibleEqualityOperatorSet
                    && compatibleEqualityOperatorSet.size() > 0) {
                if (!PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                        .equals(sourceTypeStr)) {
                    return BaseMappingsIssuesHelper
                            .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                }
            }
        }

        if (type instanceof ITypeResolution) {

            return BaseScriptToConceptPathMappingsHelper
                    .getIssuesForUserDefinedScriptMappings(activity,
                            target,
                            type);

        }
        return null;
    }

    /**
     * checks for mappings between target primitive type and source other types
     * 
     * @param activity
     * @param target
     * @param type
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    public static WebServiceJavaScriptMappingIssue[] handlePrimitiveType(
            Activity activity, ConceptPath target, IScriptRelevantData type) {

        Classifier targetType = target.getType();
        String targetTypeName =
                BaseMappingsIssuesHelper.getTypeName(targetType, target);

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(type);
        String sourceTypeStr =
                BaseScriptToConceptPathMappingsHelper.getSourceTypeStr(type,
                        sourceType);

        /*
         * If source is not Primitive and target is primitive - then it is an
         * invalid mapping.
         */
        if (sourceType instanceof org.eclipse.uml2.uml.Class) {
            if (JScriptUtils.isDynamicComplexType(sourceType)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
        }

        if (BaseMappingsIssuesHelper
                .isSourceNameTargetNameEquals(sourceTypeStr, targetTypeName)) {
            return null;
        }

        if (type instanceof DefaultScriptRelevantData) {
            DefaultScriptRelevantData defaultScriptRelevantData =
                    (DefaultScriptRelevantData) type;
            return BaseScriptToConceptPathMappingsHelper
                    .getIssuesForUserDefinedScriptMappings(activity,
                            target,
                            defaultScriptRelevantData);
        }
        return null;
    }

}
