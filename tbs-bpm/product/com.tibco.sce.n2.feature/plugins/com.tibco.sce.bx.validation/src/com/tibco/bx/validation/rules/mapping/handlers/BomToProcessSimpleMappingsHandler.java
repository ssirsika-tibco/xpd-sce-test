/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.handlers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.bx.validation.rules.mapping.helpers.BaseMappingsIssuesHelper;
import com.tibco.bx.validation.rules.mapping.helpers.SimpleMappingsBOMPrimitiveObjectHelper;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;

/**
 * 
 * This class is responsible for handling the case between BOM data to process
 * data. (i.e. source = wsdl and target = process)
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class BomToProcessSimpleMappingsHandler implements
        IntfSimpleMappingsIssuesHandler {

    private Activity activity;

    private ConceptPath source;

    private ConceptPath target;

    /**
     * @param activity
     * @param source
     * @param target
     */
    public BomToProcessSimpleMappingsHandler(Activity activity,
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

        Classifier sourceType = source.getType();

        String targetTypeStr =
                BaseMappingsIssuesHelper.getTypeStr(activity, target);
        BasicType basicType =
                BaseMappingsIssuesHelper.getBasicType(activity, target);

        if (null != targetTypeStr) {

            String sourceTypeName =
                    BaseMappingsIssuesHelper.getTypeName(sourceType, source);

            if (BaseMappingsIssuesHelper
                    .isSourceNameTargetNameEquals(sourceTypeName, targetTypeStr)) {
                return null;
            }

            if (sourceTypeName.equals(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME)) {
                return SimpleMappingsBOMPrimitiveObjectHelper
                        .handleSource(activity, source, target);

            }

            if (sourceType instanceof Enumeration) {
                return checkEnumAndPrimMappings(sourceType, basicType);
            }

            return checkOtherStuff(sourceType,
                    basicType,
                    targetTypeStr,
                    sourceTypeName);

        } else {
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.INVALID_TYPE);
        }

    }

    /**
     * @param targetTypeStr
     * @param sourceTypeName
     * @return
     */
    protected WebServiceJavaScriptMappingIssue[] checkOtherStuff(
            Classifier sourceType, BasicType basicType, String targetTypeStr,
            String sourceTypeName) {

        String rhsTypeStr =
                BaseMappingsIssuesHelper
                        .convertSpecificToGenericType(targetTypeStr);

        Map<String, Set<String>> compatibleAssignmentTypesMap =
                N2JScriptDataTypeMapper.getInstance()
                        .getCompatibleAssignmentTypesMap();

        compatibleAssignmentTypesMap =
                N2JScriptDataTypeMapper
                        .getInstance()
                        .convertSpecificMapToGeneric(compatibleAssignmentTypesMap);

        if (compatibleAssignmentTypesMap != null) {

            Iterator<String> iterator =
                    compatibleAssignmentTypesMap.keySet().iterator();
            Set<String> compatibleEqualityOperatorSet = null;

            while (iterator.hasNext()) {
                String next = iterator.next();
                if (next.equalsIgnoreCase(rhsTypeStr)) {
                    compatibleEqualityOperatorSet =
                            compatibleAssignmentTypesMap.get(next);
                    break;
                }
            }

            if (!BaseMappingsIssuesHelper
                    .isSourceTypeNameInCompatibleSet(compatibleEqualityOperatorSet,
                            sourceTypeName)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
        }

        return checkEnumAndPrimMappings(sourceType, basicType);
    }

    /**
     * @param sourceType
     * @param basicType
     * @return
     */
    protected WebServiceJavaScriptMappingIssue[] checkEnumAndPrimMappings(
            Classifier sourceType, BasicType basicType) {
        if (!BaseMappingsIssuesHelper.doesBOMToProcessTypesMatch(sourceType,
                basicType.getType())) {
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
        } else {
            return BaseMappingsIssuesHelper
                    .getBOMToProcessNecessaryWarnings(source, target);
        }
    }

}
