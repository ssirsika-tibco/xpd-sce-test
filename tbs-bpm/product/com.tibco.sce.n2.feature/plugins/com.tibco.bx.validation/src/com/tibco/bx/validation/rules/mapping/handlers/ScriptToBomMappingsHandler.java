/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.handlers;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.bx.validation.rules.mapping.helpers.BaseMappingsIssuesHelper;
import com.tibco.bx.validation.rules.mapping.helpers.BaseScriptToConceptPathMappingsHelper;
import com.tibco.bx.validation.rules.mapping.helpers.ScriptMappingsHelper;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Activity;

/**
 * This class is responsible for handling all script to BOM type mappings
 * (including bom primitive object, primitive type, enumeration and Class)
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class ScriptToBomMappingsHandler implements
        IntfScriptMappingsIssuesHandler {

    private Activity activity;

    private IScriptRelevantData type;

    private ConceptPath target;

    /**
     * @param activity
     * @param type
     * @param target
     */
    public ScriptToBomMappingsHandler(Activity activity,
            IScriptRelevantData type, ConceptPath target) {
        super();
        this.activity = activity;
        this.type = type;
        this.target = target;
    }

    /**
     * @see com.tibco.bx.validation.rules.mapping.handlers.IntfScriptMappingsIssuesHandler#getIssues(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath)
     * 
     * @param activity
     * @param type
     * @param target
     * @return
     */
    public WebServiceJavaScriptMappingIssue[] getIssues() {

        String targetTypeName =
                BaseMappingsIssuesHelper.getTypeName(target.getType(), target);
        Classifier targetType = target.getType();

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(type);
        String sourceTypeStr =
                BaseScriptToConceptPathMappingsHelper.getSourceTypeStr(type,
                        sourceType);
       
        if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(targetTypeName)) {
            return ScriptMappingsHelper.handleBOMPrimitiveObj(activity,
                    target,
                    type);

        } else if (targetType instanceof Enumeration) {
            return ScriptMappingsHelper.handleEnumeration(activity,
                    target,
                    type);

        } else if (targetType instanceof PrimitiveType) {

            /*
             * this is to be done here to handle cases for union, otherwise this
             * check for target primitive type is being done in
             * doesScriptTypeMatch.
             */
            return ScriptMappingsHelper.handlePrimitiveType(activity,
                    target,
                    type);

        } else if (sourceType instanceof Class && targetType instanceof Class) {

            if (JScriptUtils.isDynamicComplexType(sourceType)
                    && JScriptUtils.isDynamicComplexType(targetType)) {

                String targetTypeStr = JScriptUtils.getFQType(targetType);

                if (!sourceTypeStr.equals(targetTypeStr)) {
                    /* Incompatible Bom classes */
                    return BaseMappingsIssuesHelper
                            .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                }

                Class sourceClass = (Class) sourceType;
                Class targetClass = (Class) targetType;

                boolean isLHSSubType =
                        JScriptUtils.isSubType(sourceClass, targetClass);
                if (isLHSSubType) {
                    return null;
                }

                boolean isRHSSubType =
                        JScriptUtils.isSubType(targetClass, sourceClass);
                if (isRHSSubType) {
                    return BaseMappingsIssuesHelper
                            .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                }
            }
        } else {

            if (!BaseScriptToConceptPathMappingsHelper
                    .doesScriptTypeMatch(target, sourceTypeStr, targetType)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
        }

        return null;
    }
}
