/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.wsdltobom;

/**
 * Rule to validate the wsdl import URI location whilst wsdl to bom generation.
 * We allow only relative schema location and discourage the usage of absolute
 * schema location.
 * 
 * @author kthombar
 * @since 16-Feb-2014
 */
public class WsdlImportAbsoluteSchemaLocationInvalidRule extends
        AbstractWsdlAbsoluteSchemaLocationInvalidRule {

    /**
     * @see com.tibco.xpd.bom.validator.rules.wsdltobom.AbstractWsdlAbsoluteSchemaLocationInvalidRule#getAbsolutePathInvalidIssueId()
     * 
     * @return issue Id
     */
    @Override
    protected String getAbsolutePathInvalidIssueId() {
        /*
         * WSDL containing absolute URI to XSD cannot be converted to business
         * object model.
         */
        return "wsdltobom.absoluteSchemaLocInvalid"; //$NON-NLS-1$
    }

}
