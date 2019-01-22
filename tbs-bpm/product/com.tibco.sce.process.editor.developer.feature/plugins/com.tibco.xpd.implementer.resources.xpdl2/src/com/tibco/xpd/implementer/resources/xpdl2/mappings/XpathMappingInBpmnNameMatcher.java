/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import org.eclipse.xsd.XSDSimpleTypeDefinition;

import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * This type matcher is for mappings of direction Input to service
 * 
 * This is in context to the source being ConceptPath and the target being
 * IWsdlPath
 * 
 * @author rsomayaj
 * @since 3.3 (11 Jun 2010)
 */
public class XpathMappingInBpmnNameMatcher extends AbstractXPathNameMatcher {

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
        // Source object should be ConceptPath and target object should be
        // IWsdlPath
        if (sourceObject instanceof ConceptPath) {
            ConceptPath conceptPath = (ConceptPath) sourceObject;
            if (!(conceptPath.getChildren().isEmpty())) {
                return false;
            }
            sourceStr = conceptPath.getPath();
        }
        if (targetObject instanceof IWsdlPath) {
            IWsdlPath wsdlPath = (IWsdlPath) targetObject;
            if (wsdlPath.getType() instanceof XSDSimpleTypeDefinition) {
                targetStr = wsdlPath.getJavaScriptPath();
            }
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
        if (MappingDirection.IN.equals(mappingDirection)) {
            return true;
        }
        return false;
    }

}
