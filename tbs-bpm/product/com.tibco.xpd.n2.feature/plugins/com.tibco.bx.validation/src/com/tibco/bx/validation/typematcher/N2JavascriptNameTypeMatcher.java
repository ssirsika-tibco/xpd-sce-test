/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.typematcher;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.BpmnJavscriptNameMatcher;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.BasicType;

/**
 * Name matcher which also checks that the types match. Type matches are only
 * checked if they exactly match.
 * 
 * @author rsomayaj
 * @since 3.3 (17 Jun 2010)
 */
public class N2JavascriptNameTypeMatcher extends BpmnJavscriptNameMatcher {

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.BpmnJavscriptNameMatcher#typesMatch(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param sourceObject
     * @param targetObject
     * @return
     */
    @Override
    public boolean typesMatch(Object sourceObject, Object targetObject) {
        ConceptPath srcCp = getConceptPath(sourceObject);
        String sourceStr = getString(srcCp);

        ConceptPath tgtCp = getConceptPath(targetObject);
        String targetStr = getString(tgtCp);
        // Names and types matched
        if (sourceStr != null && targetStr != null) {
            if (sourceStr.equalsIgnoreCase(targetStr)) {
                if (srcCp.getType() != null
                        && srcCp.getType().equals(tgtCp.getType())) {
                    return true;
                } else if (srcCp.getType() == null) {
                    Object srcBaseType =
                            BasicTypeConverterFactory.INSTANCE
                                    .getBaseType(srcCp.getItem(), true);
                    Object tgtBaseType =
                            BasicTypeConverterFactory.INSTANCE
                                    .getBaseType(tgtCp.getItem(), true);
                    if (srcBaseType instanceof BasicType
                            && tgtBaseType instanceof BasicType) {
                        /*
                         * When both are BasicType's (or equivalent thereof)
                         * then we can do some sensible type and length
                         * comparisons.
                         */
                        if (((BasicType) srcBaseType).getType()
                                .equals(((BasicType) tgtBaseType).getType())) {
                            return true;
                        }
                    }

                }
            }
        }

        return false;
    }
}
