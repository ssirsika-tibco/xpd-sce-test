/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.StringTokenizer;

import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.util.MapperTreeItem;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (1 Sep 2010)
 */
public abstract class AbstractXPathNameMatcher extends AbstractTypeMatcher {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher#getNormalizedPath(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param contextObject
     * @param lhsObject
     * @return
     */
    @Override
    public MapperTreeItem getNormalizedPath(Object contextObject,
            Object mappingObject) {

        if (mappingObject instanceof ConceptPath) {
            return new JavaScriptMapperTreeItem(contextObject,
                    (ConceptPath) mappingObject);
        }
        if (mappingObject instanceof IWsdlPath) {
            return new XPathMapperTreeItem(contextObject,
                    (IWsdlPath) mappingObject);
        }
        return null;
    }

    /**
     * @param tgtLastToken
     * @return
     */
    protected String getAttrName(String tgtLastToken) {
        StringBuilder strBuilder = new StringBuilder(tgtLastToken);
        if (strBuilder.charAt(0) == '@') {
            // Avoiding the first character @
            strBuilder.deleteCharAt(0);
        }
        if (strBuilder.indexOf("[") != -1) { //$NON-NLS-1$
            // Not interested with the array delimiters
            strBuilder.delete(strBuilder.indexOf("["), //$NON-NLS-1$
                    strBuilder.indexOf("]") + 1); //$NON-NLS-1$
        }
        return strBuilder.toString();
    }

    /**
     * @param targetStringTokenizer
     * @return
     */
    protected String getLastToken(StringTokenizer stringTokenizer) {
        String lastToken = null;
        do {
            lastToken = stringTokenizer.nextToken();
        } while (stringTokenizer.hasMoreTokens());

        return lastToken;
    }

    /**
     * @param sourceStr
     * @param targetStr
     * @return
     */
    protected boolean doTypesMatch(String sourceStr, String targetStr) {
        StringTokenizer sourceStringTokenizer =
                new StringTokenizer(sourceStr, "."); //$NON-NLS-1$
        String srclastToken = getLastToken(sourceStringTokenizer);

        StringTokenizer targetStringTokenizer =
                new StringTokenizer(targetStr, "."); //$NON-NLS-1$
        String tgtLastToken = getLastToken(targetStringTokenizer);
        String attrName = getAttrName(tgtLastToken);
        if (srclastToken != null && attrName != null) {
            if (srclastToken.equalsIgnoreCase(attrName)) {
                return true;
            }
        }
        return false;
    }

}
