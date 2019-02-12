/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import java.util.Set;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Helper class for BomToBomSimpleMappingsHandler and
 * BomToProcessSimpleMappingsHandler. This class handles concept path (simple
 * java script) mapping issues if source or target is object type
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class SimpleMappingsBOMPrimitiveObjectHelper {

    /**
     * Basically checks the mapping compatibility between bom object sub type
     * and other types
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    @SuppressWarnings("restriction")
    public static WebServiceJavaScriptMappingIssue[] handleSource(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();

        Classifier targetType = target.getType();

        String sourceObjectSubType =
                ConceptUtil.getObjectSubType(source, sourceType);
        String targetObjectSubType =
                ConceptUtil.getObjectSubType(target, targetType);

        /*
         * If subType is defined then must check that the subType is permitted
         */
        if (sourceObjectSubType.length() > 0) {
            /*
             * Sid ACE-194 - we don't have XSD derived BOMs any more - removed
             * XSD base rules
             */

            if (JSTypesCompatabilityUtil.getCompatibleBOMObjectSubTypesMap()
                    .get(sourceObjectSubType).contains(targetObjectSubType)) {
                return BaseMappingsIssuesHelper.getWarnings(source, target);
            }

            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);

        } else { /* source has no sub type and target is not a sub type */
            if (sourceType instanceof PrimitiveType) {

                /*
                 * Sid ACE-194 - we don't have XSD derived BOMs any more -
                 * removed XSD base rules
                 */

            }
        }
        return null;
    }

    /**
     * Basically checks the mapping compatibility between bom object sub type
     * and other types
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    @SuppressWarnings("restriction")
    public static WebServiceJavaScriptMappingIssue[] handleTarget(
            Activity activity, ConceptPath source, ConceptPath target,
            String sourceTypeName) {

        Classifier targetType = target.getType();

        /*
         * target sub type is defined then source must be same sub type as
         * target, process data simple type, or bom attribute of base primitive
         * type, BOM attribute of type BOM class, BOM class
         */

        String targetObjectSubType =
                ConceptUtil.getObjectSubType(target, targetType);

        if (targetObjectSubType.length() > 0) {
            /*
             * Sid ACE-194 - we don't have XSD derived BOMs any more - removed
             * XSD base rules
             */

            Set<String> compatibleEqualityOperatorSet = JSTypesCompatabilityUtil
                    .getCompatibleBOMRHSSubTypesMap().get(targetObjectSubType);

            if (null != compatibleEqualityOperatorSet) {

                if (BaseMappingsIssuesHelper.isSourceTypeNameInCompatibleSet(
                        compatibleEqualityOperatorSet,
                        sourceTypeName)) {
                    return BaseMappingsIssuesHelper.getWarnings(source, target);
                }
            }
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
        }
        return null;
    }

}
