/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import org.eclipse.xsd.XSDSimpleTypeDefinition;

import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * This type matcher is for mappings of direction Input to proces or output from
 * service
 * 
 * This is in context to the source being IWsdlPath and the target being
 * ConceptPath
 * 
 * @author rsomayaj
 * @since 3.3 (11 Jun 2010)
 */
public class XpathMappingOutBpmnNameMatcher extends AbstractXPathNameMatcher {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher#typesMatch(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param sourceObject
     * @param targetObject
     * @return
     */
    @Override
    public boolean typesMatch(Object sourceObject, Object targetObject) {
        String sourceStr = null;
        String targetStr = null;
        // Source object should be IWsdlPath and target object should be
        // ConceptPath

        if (sourceObject instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) sourceObject;
            if (wsdlPath.getType() instanceof XSDSimpleTypeDefinition) {
                sourceStr = wsdlPath.getJavaScriptPath();
            }
        }

        if (targetObject instanceof ConceptPath) {
            ConceptPath conceptPath = (ConceptPath) targetObject;
            if (!(conceptPath.getChildren().isEmpty())) {
                return false;
            }
            targetStr = conceptPath.getPath();
        }

        if (sourceStr != null && targetStr != null) {
            return doTypesMatch(sourceStr, targetStr);

        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher#isApplicable(java.lang.Object,
     *      com.tibco.xpd.mapper.MappingDirection)
     * 
     * @param contextObject
     * @param mappingDirection
     * @return
     */
    @Override
    public boolean isApplicable(Object contextObject,
            MappingDirection mappingDirection) {
        if (MappingDirection.OUT.equals(mappingDirection)) {
            return true;
        }
        return false;
    }

}
