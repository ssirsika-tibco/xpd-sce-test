/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.handlers;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.bx.validation.rules.mapping.helpers.BaseMappingsIssuesHelper;
import com.tibco.bx.validation.rules.mapping.helpers.SimpleMappingsBOMPrimitiveObjectHelper;
import com.tibco.bx.validation.rules.mapping.helpers.SimpleMappingsPrimitiveTypeHelper;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;

/**
 * This class is responsible for handling the case between process data to BOM
 * data. (i.e. source = Process data target = wsdl data)
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class ProcessToBomSimpleMappingsHandler implements
        IntfSimpleMappingsIssuesHandler {

    private Activity activity;

    private ConceptPath source;

    private ConceptPath target;

    /**
     * @param activity
     * @param source
     * @param target
     */
    public ProcessToBomSimpleMappingsHandler(Activity activity,
            ConceptPath source, ConceptPath target) {
        super();
        this.activity = activity;
        this.source = source;
        this.target = target;
    }

    /**
     * @see com.tibco.bx.validation.rules.mapping.handlers.IntfSimpleMappingsIssuesHandler#getIssues(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath)
     * 
     * @param activity
     * @param source
     * @param target
     * @return
     */
    public WebServiceJavaScriptMappingIssue[] getIssues() {

        Classifier targetType = target.getType();

        BasicType basicType =
                BaseMappingsIssuesHelper.getBasicType(activity, source);
        String sourceTypeStr =
                BaseMappingsIssuesHelper.getTypeStr(activity, source);

        if (null != sourceTypeStr) {

            String targetTypeName =
                    BaseMappingsIssuesHelper.getTypeName(targetType, target);

            if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(targetTypeName)) {
                return SimpleMappingsBOMPrimitiveObjectHelper
                        .handleTarget(activity, source, target, sourceTypeStr);
            }

            if (targetType instanceof PrimitiveType) {

                if (BaseMappingsIssuesHelper
                        .isSourceNameTargetNameEquals(sourceTypeStr,
                                targetTypeName)) {
                    return null;
                }

                return SimpleMappingsPrimitiveTypeHelper
                        .handlePrimitiveTypeTarget(activity, source, target);
            }

            if (!BaseMappingsIssuesHelper.doesProcessToBOMTypesMatch(basicType
                    .getType(), targetType)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
            return BaseMappingsIssuesHelper
                    .getProcessToBOMNecessaryWarnings(source, target);
        } else {
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.INVALID_TYPE);
        }
    }

}
