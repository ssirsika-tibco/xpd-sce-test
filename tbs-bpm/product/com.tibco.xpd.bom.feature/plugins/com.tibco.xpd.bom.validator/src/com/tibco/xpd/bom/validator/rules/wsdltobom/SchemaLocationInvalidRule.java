/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdltobom;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (11 Mar 2010)
 */
public class SchemaLocationInvalidRule extends
        AbstractAbsoluteSchemaLocationInvalidRule {

    /**
     * @see com.tibco.xpd.bom.validator.rules.wsdltobom.AbstractAbsoluteSchemaLocationInvalidRule#getAbsolutePathInvalidIssueId()
     * 
     * @return
     */
    @Override
    protected String getAbsolutePathInvalidIssueId() {
        return "wsdltobom.absoluteSchemaLocInvalid";
    }

}
