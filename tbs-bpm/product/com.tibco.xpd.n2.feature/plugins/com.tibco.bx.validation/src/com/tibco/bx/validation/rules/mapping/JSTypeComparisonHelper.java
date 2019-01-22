package com.tibco.bx.validation.rules.mapping;

import com.tibco.bx.validation.rules.mapping.handlers.IntfScriptMappingsIssuesHandler;
import com.tibco.bx.validation.rules.mapping.handlers.IntfSimpleMappingsIssuesHandler;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;

/**
 * This is a comparison helper class to JS Mapping rule.
 * 
 * Mainly returns the issues depending on the compatibility of the types passed
 * to it.
 * 
 * @author rsomayaj
 * 
 */
public class JSTypeComparisonHelper {

    /**
     * @param activity
     * @param source
     * @param target
     * @return
     */
    public static WebServiceJavaScriptMappingIssue[] getTypeCompatabilityIssues(
            Activity activity, IScriptRelevantData type, ConceptPath target) {

        IntfScriptMappingsIssuesHandler mappingsCompatibilityHandler =
                MappingsIssuesFactory.INSTANCE
                        .getMappingsCompatibilityHandler(activity, type, target);

        if (null != mappingsCompatibilityHandler) {
            return mappingsCompatibilityHandler.getIssues();
        }

        return null;

    }

    /**
     * @param activity
     * @param source
     * @param target
     * @return
     */
    public static WebServiceJavaScriptMappingIssue[] getTypeCompatabilityIssues(
            Activity activity, ConceptPath source, ConceptPath target) {

        IntfSimpleMappingsIssuesHandler mappingsCompatibilityHandler =
                MappingsIssuesFactory.INSTANCE
                        .getMappingsCompatibilityHandler(activity,
                                source,
                                target);

        if (null != mappingsCompatibilityHandler) {
            return mappingsCompatibilityHandler.getIssues();
        }

        return null;

    }

}
