/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.Collections;
import java.util.Map;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.DefaultPojoJsClass;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodReturnParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.validation.bpmn.developer.baserules.AbstractJavaServiceOutputMappingRule;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Validate Java service task output mapping.
 * 
 * @author aallway
 * @since 3.3 (21 Jun 2010)
 */
public class JavaServiceOutputMappingRule extends
        AbstractJavaServiceOutputMappingRule {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractJavaServiceOutputMappingRule
     * #isJavaTypeAssignableToProcessDataType
     * (com.tibco.xpd.implementer.nativeservices
     * .java.javaservice.mapper.JavaMethodParameter, java.lang.Object)
     */
    @Override
    protected boolean isJavaTypeAssignableToProcessDataType(
            JavaMethodParameter javaMethodParameter, Object processDataObject) {
        if (processDataObject != null && javaMethodParameter != null) {
            if (processDataObject != null && javaMethodParameter != null) {
                /*
                 * Get the base type of the process side of data (might be field
                 * or might be property within complex type etc) - convert to
                 * basic type if possible.
                 */
                Object baseType =
                        BasicTypeConverterFactory.INSTANCE
                                .getBaseType(processDataObject, true);

                if (baseType instanceof BasicType) {
                    return BasicTypeToJavaTypeCompatability
                            .isJavaTypeAssignableToBasicType(javaMethodParameter,
                                    ((BasicType) baseType).getType());
                } else {
                    if (processDataObject instanceof Property) {
                        Property prop = (Property) processDataObject;

                        /* If the property is of type OBJECT */
                        if (prop.getType() != null) {

                            if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                    .equals(prop.getType().getName())) {

                                return BomTypeToJavaTypeCompatability
                                        .isJavaTypeAssignableToBomType(javaMethodParameter,
                                                PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME);
                            }

                            /*
                             * XPD-7014: Allow BOM.Enum Attribute <-> Java Text
                             * mappings
                             */
                            if (prop.getType() instanceof Enumeration) {

                                return BomTypeToJavaTypeCompatability
                                        .isJavaTypeAssignableToBomType(javaMethodParameter,
                                                PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME);

                            }

                        }
                    }
                }
            }

        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.developer.baserules.AbstractJavaServiceOutputMappingRule#isJavaTypeAssignableToProcessDataType(com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param javaMethodParameter
     * @param conceptPath
     * @param mapping
     * @return
     */
    @Override
    protected boolean isJavaTypeAssignableToProcessDataType(
            JavaMethodParameter javaMethodParameter, Object conceptPath,
            Object mapping) {

        boolean ret = true;
        if (conceptPath instanceof ConceptPath) {

            Object item = ((ConceptPath) conceptPath).getItem();
            ret =
                    isJavaTypeAssignableToProcessDataType(javaMethodParameter,
                            item);

            /* XPD-7014: Raise warning for Text-Enum mapping */
            if (ret) {

                if (item instanceof Property) {

                    Property prop = (Property) item;
                    ParameterTypeEnum javaType = javaMethodParameter.getType();
                    if (prop.getType() instanceof Enumeration
                            && ParameterTypeEnum.STRING.equals(javaType)) {

                        addIssue("bx.textToEnum.warning", //$NON-NLS-1$
                                getModelObjectForMapping(mapping),
                                Collections.EMPTY_LIST,
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                        javaMethodParameter.getPath(),
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        ((ConceptPath) conceptPath).getPath()));
                    }
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractJavaServiceOutputMappingRule
     * #isJavaTypeAssignableToProcessDataType
     * (com.tibco.xpd.implementer.nativeservices
     * .java.javaservice.mapper.JavaMethodParameter, java.lang.Object)
     */

    @Override
    protected boolean isScriptTypeAssignableToProcessDataType(
            IScriptRelevantData scriptType, Object processDataObject) {
        if (processDataObject != null && scriptType != null) {
            if (processDataObject != null && scriptType != null) {
                JavaMethodParameter javaMethodParameter =
                        getJavaMethodParameter(scriptType);

                /*
                 * Get the base type of the process side of data (might be field
                 * or might be property within complex type etc) - convert to
                 * basic type if possible.
                 */
                Object baseType =
                        BasicTypeConverterFactory.INSTANCE
                                .getBaseType(processDataObject, true);

                if (baseType instanceof BasicType) {
                    String baseTypeStr = null;
                    Map<BasicTypeType, String> processTypeToJavaScriptConversionMap =
                            N2JScriptDataTypeMapper.getInstance()
                                    .getProcessTypeToJavaScriptConversionMap();
                    if (processTypeToJavaScriptConversionMap != null) {
                        baseTypeStr =
                                processTypeToJavaScriptConversionMap
                                        .get(((BasicType) baseType).getType());
                    }
                    if (baseTypeStr != null) {
                        if (javaMethodParameter != null) {
                            return ScriptTypeToJavaTypeCompatability
                                    .isJavaScriptTypeAssignableToBasicType(javaMethodParameter,
                                            baseTypeStr);
                        } else {
                            String javaType = scriptType.getType();
                            return ScriptTypeToJavaTypeCompatability
                                    .isJavaScriptTypeAssignableToBasicType(javaType,
                                            baseTypeStr);
                        }
                    }
                } else if (baseType instanceof Class) {
                    String scriptTypeStr = JScriptUtils.getFQType(scriptType);
                    String targetTypeStr =
                            JScriptUtils.getFQType((Class) baseType);
                    if (scriptTypeStr != null && targetTypeStr != null
                            && scriptTypeStr.equals(targetTypeStr)) {
                        return true;
                    }
                } else {
                    if (processDataObject instanceof Property) {
                        Property prop = (Property) processDataObject;

                        /* If the property is of type OBJECT */
                        if (prop.getType() != null
                                && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                        .equals(prop.getType().getName())) {
                            if (javaMethodParameter != null) {
                                return BomTypeToJavaTypeCompatability
                                        .isJavaTypeAssignableToBomType(javaMethodParameter,
                                                PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME);
                            } else {
                                String javaType = scriptType.getType();
                                return BomTypeToJavaTypeCompatability
                                        .isJavaTypeAssignableToBomType(javaType,
                                                PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private JavaMethodParameter getJavaMethodParameter(IScriptRelevantData type) {
        if (type instanceof IUMLScriptRelevantData) {
            IUMLScriptRelevantData umlType = (IUMLScriptRelevantData) type;
            JsClass jsClass = umlType.getJsClass();
            if (jsClass instanceof DefaultPojoJsClass) {
                return ((DefaultPojoJsClass) jsClass).getContainerClass();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.developer.baserules.
     * AbstractJavaServiceOutputMappingRule
     * #isMappingToTargetLevelSupported(java.lang.Object)
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /* Currently restricted to simple field types and scripts only. */
        /* implementing same as webservice behaviour */
        /*
         * N2 currently does not support mapping to / from individual elements
         * in a sequence.
         */
        if (targetObjectInTree instanceof ConceptPath) {
            ConceptPath targetPath = (ConceptPath) targetObjectInTree;

            if (targetPath.isArrayItem()) {
                return false;
            }
        }
        return super.isMappingToTargetLevelSupported(targetObjectInTree);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.developer.baserules.AbstractJavaServiceOutputMappingRule#isMappingFromSourceLevelSupported(java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /*
         * XPD-3959 Data Mapping UI should validate against mapping individual
         * array elements
         */
        Object source = sourceObjectInTree;
        while (source instanceof JavaMethodReturnParameter) {
            if (JavaServiceMappingUtil
                    .isArray(((JavaMethodReturnParameter) source).getParent())) {
                return false;
            }
            source = ((JavaMethodReturnParameter) source).getParent();

        }
        return super.isMappingFromSourceLevelSupported(sourceObjectInTree);
    }

    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return true;
    }

    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {
        if (multiInstanceTarget instanceof ConceptPath) {
            if (((ConceptPath) multiInstanceTarget).getItem() instanceof Property) {
                Property property =
                        (Property) ((ConceptPath) multiInstanceTarget)
                                .getItem();

                Type type = property.getType();
                if (type != null
                        && PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(type
                                .getName())) {
                    return true;
                }
            }

        }
        return super.isSingleToMultiSupported(singleInstanceSource,
                multiInstanceTarget);
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
