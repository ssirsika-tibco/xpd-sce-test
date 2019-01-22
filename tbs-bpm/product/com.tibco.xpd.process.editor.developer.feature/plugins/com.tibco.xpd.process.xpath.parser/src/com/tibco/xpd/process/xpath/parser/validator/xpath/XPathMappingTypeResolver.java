/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.process.xpath.parser.validator.xpath;

import java.util.Set;

import com.tibco.xpd.process.xpath.model.AbstractXPathMappingTypeResolver;
import com.tibco.xpd.process.xpath.model.XPathConsts;

/**
 * @author mtorres
 *
 */
public class XPathMappingTypeResolver extends AbstractXPathMappingTypeResolver {
 
  /**
     * @see com.tibco.xpd.process.xpath.model.AbstractXPathMappingTypeResolver#getValidBooleanWsToProcessMappingTypes()
     * 
     * @return
     */
    @Override
    protected Set<String> getValidBooleanWsToProcessMappingTypes() {
        Set<String> validTypes = super.getValidBooleanWsToProcessMappingTypes();
        validTypes.add(XPathConsts.XPATH_TYPE_BOOLEAN.toUpperCase());
        return validTypes;
    }  
}
