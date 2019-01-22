/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bds.designtime.validator.rules;

import com.tibco.xpd.bom.validator.rules.wsdltobom.AbstractWsdlAbsoluteSchemaLocationInvalidRule;

/**
 * Rule to validate the wsdl import URI location at design time. We allow only
 * relative schema location and discourage the usage of absolute schema
 * location.
 * 
 * @author kthombar
 * @since 16-Feb-2014
 */
public class WsdlImportAbsoluteSchemaLocationInvalidRule extends
        AbstractWsdlAbsoluteSchemaLocationInvalidRule {

    /**
     * 
     * @see com.tibco.xpd.bom.validator.rules.wsdltobom.AbstractWsdlAbsoluteSchemaLocationInvalidRule#getAbsolutePathInvalidIssueId()
     * 
     * @return issue Id
     */
    @Override
    protected String getAbsolutePathInvalidIssueId() {
        /* WSDLs may not refer to absolute URIs */
        return "cds.wsdlbom.absolutePathInvalid"; //$NON-NLS-1$
    }

}
