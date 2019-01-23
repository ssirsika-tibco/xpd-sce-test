/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.Map;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.validation.bpmn.developer.baserules.AbstractJavaServiceInputMappingRule;
import com.tibco.xpd.xpdl2.BasicType;

/**
 * Validate Java Service Task input mappings.
 * 
 * @author aallway
 * @since 3.3 (16 Jun 2010)
 */
public class JavaServiceInputMappingRule extends
        AbstractJavaServiceInputMappingRule {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractJavaServiceInputMappingRule
     * #isBasicTypeAssignableToJavaType(com.tibco.xpd.xpdl2.BasicType,
     * com.tibco.
     * xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter
     * )
     */
    @Override
    protected boolean isProcessDataTypeAssignableToJavaType(
            Object processDataObject, JavaMethodParameter javaMethodParameter) {

        if (processDataObject != null && javaMethodParameter != null) {
            /*
             * Get the base type of the process side of data (might be field or
             * might be property within complex type etc) - convert to basic
             * type if possible.
             */
            Object baseType =
                    BasicTypeConverterFactory.INSTANCE
                            .getBaseType(processDataObject, true);

            if (baseType instanceof BasicType) {
                return BasicTypeToJavaTypeCompatability
                        .isBasicTypeAssignableToJavaType(((BasicType) baseType)
                                .getType(), javaMethodParameter);
            } else {
                if (processDataObject instanceof Property) {
                    Property prop = (Property) processDataObject;

                    // If the property is of type OBJECT
                    if (prop.getType() != null) {

                        if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                .equals(prop.getType().getName())) {

                            return BomTypeToJavaTypeCompatability
                                    .isBomTypeAssignableToJavaType(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME,
                                            javaMethodParameter);
                        }

                        /*
                         * XPD-7014: Allow BOM.Enum Attribute <-> Java Text
                         * mappings
                         */
                        if (prop.getType() instanceof Enumeration) {

                            return BomTypeToJavaTypeCompatability
                                    .isBomTypeAssignableToJavaType(PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME,
                                            javaMethodParameter);

                        }
                    }
                }
            }

        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractJavaServiceInputMappingRule
     * #isScriptDataTypeAssignableToJavaType(IScriptRelevantData, com.tibco.
     * xpd.
     * implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter )
     */
    @Override
    protected boolean isScriptDataTypeAssignableToJavaType(
            IScriptRelevantData scriptType,
            JavaMethodParameter javaMethodParameter) {

        if (scriptType != null && javaMethodParameter != null) {
            if (!JScriptUtils.isDynamicComplexType(scriptType)) {
                String type = scriptType.getType();
                if (type != null) {
                    Map<String, String> specificToGenericTypeConversionMap =
                            N2JScriptDataTypeMapper.getInstance()
                                    .getSpecificToGenericTypeConversionMap();
                    String genericType =
                            specificToGenericTypeConversionMap.get(type);
                    if (genericType != null) {
                        type = genericType;
                    }
                    return ScriptTypeToJavaTypeCompatability
                            .isScriptTypeAssignableToJavaType(type,
                                    javaMethodParameter);
                }
            } else if (JScriptUtils.isXsdAny(scriptType)) {
                return BomTypeToJavaTypeCompatability
                        .isBomTypeAssignableToJavaType(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME,
                                javaMethodParameter);
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractJavaServiceInputMappingRule
     * #isMappingFromSourceLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /* Currently restricted to simple field types and scripts only. */
        // implementing same as webservice behaviour
        if (sourceObjectInTree instanceof ConceptPath) {
            ConceptPath sourcePath = (ConceptPath) sourceObjectInTree;

            if (sourcePath.isArrayItem()) {
                return false;
            }
        }

        return super.isMappingFromSourceLevelSupported(sourceObjectInTree);

    }

    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /* Disallow mapping to individual array elements. */
        if (targetObjectInTree instanceof JavaMethodParameter) {
            JavaMethodParameter param =
                    (JavaMethodParameter) targetObjectInTree;
            Object paramParent = param.getParent();
            if (paramParent instanceof JavaMethodParameter) {
                JavaMethodParameter methodParamParent =
                        (JavaMethodParameter) paramParent;
                if (methodParamParent.isArray()) {
                    return false;
                }
            }
        }
        return super.isMappingToTargetLevelSupported(targetObjectInTree);
    }

    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.developer.baserules.AbstractJavaServiceInputMappingRule#isMultiToSingleSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        if (multiInstanceSource instanceof ConceptPath) {
            if (((ConceptPath) multiInstanceSource).getItem() instanceof Property) {
                Property property =
                        (Property) ((ConceptPath) multiInstanceSource)
                                .getItem();

                Type type = property.getType();
                if (type != null
                        && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(type
                                .getName())) {
                    return true;
                }
            }
        }
        return super.isMultiToSingleSupported(multiInstanceSource,
                singleInstanceTarget);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#isUntypedScriptListToArrayMappingAllowed(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    public boolean isUntypedScriptListToArrayMappingAllowed(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        return true;
    }

}
