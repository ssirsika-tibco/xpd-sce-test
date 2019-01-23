/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.handlers;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.bx.validation.rules.mapping.helpers.BaseMappingsIssuesHelper;
import com.tibco.bx.validation.rules.mapping.helpers.SimpleMappingsBOMPrimitiveObjectHelper;
import com.tibco.bx.validation.rules.mapping.helpers.SimpleMappingsEnumerationHelper;
import com.tibco.bx.validation.rules.mapping.helpers.SimpleMappingsPrimitiveTypeHelper;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;

/**
 * This class is responsible for handling the case between BOM to BOM Type
 * mapping
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class BomToBomSimpleMappingsHandler implements
        IntfSimpleMappingsIssuesHandler {

    private Activity activity;

    private ConceptPath source;

    private ConceptPath target;

    /**
     * @param activity
     * @param source
     * @param target
     */
    public BomToBomSimpleMappingsHandler(Activity activity, ConceptPath source,
            ConceptPath target) {
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

        Classifier sourceType = source.getType();
        String sourceTypeName =
                BaseMappingsIssuesHelper.getTypeName(sourceType, source);

        Classifier targetType = target.getType();
        String targetTypeName =
                BaseMappingsIssuesHelper.getTypeName(targetType, target);

        /*
         * if subType of Object primitive type is defined then it must be in the
         * list of compatible types (which contains the compatible subtypes).
         */
        if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(sourceTypeName)) {
            return SimpleMappingsBOMPrimitiveObjectHelper
                    .handleSource(activity, source, target);

        } else if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                .equals(targetTypeName)) {
            return SimpleMappingsBOMPrimitiveObjectHelper
                    .handleTarget(activity, source, target, sourceTypeName);

        } else if (sourceType instanceof PrimitiveType) {
            return SimpleMappingsPrimitiveTypeHelper.handleSource(activity,
                    source,
                    target);

        } else if (sourceType instanceof Enumeration) {
            return SimpleMappingsEnumerationHelper.handleSource(activity,
                    source,
                    target);
        } else if (targetType instanceof PrimitiveType) {
            return SimpleMappingsPrimitiveTypeHelper.handleTarget(activity,
                    source,
                    target);

        } else if (targetType instanceof org.eclipse.uml2.uml.Class
                || targetType instanceof Enumeration) {
            return SimpleMappingsEnumerationHelper.handleTarget(activity,
                    source,
                    target);
        }

        return null;
    }

}
