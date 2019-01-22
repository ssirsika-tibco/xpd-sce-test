/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperLabelProvider;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.UnprocessedTextRestMapperTreeItem;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Base REST mapping rule to convert REST specific parameter class types to
 * ConceptPath types to support type checking.
 * 
 * @author nwilson
 * @since 8 May 2015
 */
public abstract class AbstractRestDataMapperMappingRule extends
        AbstractN2DataMapperMappingRule {

    /**
     * @see com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule#getConceptPath(com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent)
     * 
     * @param wcc
     * @return
     */
    @Override
    protected ConceptPath getConceptPath(WrappedContributedContent wcc) {
        ConceptPath cp = super.getConceptPath(wcc);
        if (cp == null) {
            Object contributed = wcc.getContributedObject();
            if (contributed instanceof RestParamTreeItem) {
                RestParamTreeItem item = (RestParamTreeItem) contributed;
                DataType dataType = item.getParam().getDataType();
                String typeName =
                        RestMapperLabelProvider.getPrimitiveType(dataType);
                Classifier type = convertType(typeName);
                cp = new ConceptPath(null, type, type);
            } else if (contributed instanceof UnprocessedTextRestMapperTreeItem) {
                Classifier type =
                        convertType(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
                cp = new ConceptPath(null, contributed, type);
            }
        }
        return cp;
    }

    /**
     * For JSON Formatted Text check that the script return type is Text.
     * 
     * @param target
     *            The target concept path.
     * @param returnType
     *            The source script return type.
     * @return the type check result.
     */
    @Override
    protected MappingTypeCompatibility overrideScriptMappingTypeCompatibilityCheck(
            ConceptPath target, IScriptRelevantData returnType) {
        MappingTypeCompatibility result;
        if (target.getItem() instanceof UnprocessedTextRestMapperTreeItem) {
            if (JsConsts.STRING.equals(returnType.getType())
                    && !returnType.isArray()) {
                result = MappingTypeCompatibility.OK;
            } else {
                result = MappingTypeCompatibility.WRONGTYPE;
            }
        } else {
            /*
             * Sid XPD-7925. Call super, which calls
             * N2ProcessDataMappingCompatibilityUtil.checkTypeCompatibility()
             * anyway BUT also may do other things (like allowing
             * "return null;")
             */
            result =
                    super.overrideScriptMappingTypeCompatibilityCheck(target,
                            returnType);
        }

        return result;
    }

    /**
     * If the source or target are JSON Formatted Text then check that the
     * process data is Text.
     * 
     * @see com.tibco.bx.validation.rules.datamapper.AbstractN2DataMapperMappingRule#overrideTypeCompatibilityCheck(com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath)
     * 
     * @param source
     *            The source item.
     * @param target
     *            The target item.
     * @return the compatibility check result.
     */
    @Override
    protected JavaScriptTypeCompatibilityResult overrideTypeCompatibilityCheck(
            ConceptPath source, ConceptPath target) {
        BasicType processDataType = null;
        if (source.getItem() instanceof UnprocessedTextRestMapperTreeItem
                || target.getItem() instanceof UnprocessedTextRestMapperTreeItem) {
            if (source.getItem() instanceof UnprocessedTextRestMapperTreeItem
                    && !target.isArray()) {
                processDataType =
                        BasicTypeConverterFactory.INSTANCE.getBasicType(target
                                .getItem());
            } else if (target.getItem() instanceof UnprocessedTextRestMapperTreeItem
                    && !source.isArray()) {
                processDataType =
                        BasicTypeConverterFactory.INSTANCE.getBasicType(source
                                .getItem());
            }
            if (processDataType != null) {
                if (BasicTypeType.STRING_LITERAL.equals(processDataType
                        .getType())) {
                    return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                }
            }
            return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
        }
        return super.overrideTypeCompatibilityCheck(source, target);
    }

    /**
     * @param typeName
     *            The BOM standard type name.
     * @return The matching primitive UML type.
     */
    private Classifier convertType(String typeName) {
        ResourceSet rs =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        PrimitiveType type =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs, typeName);
        return type;
    }

}
