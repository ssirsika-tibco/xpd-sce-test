/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.validator;

import com.tibco.xpd.n2.cds.script.RestUMLScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.IScriptRelevantDataFactory;

/**
 * Overridden to provide support for Rest payload Array types.
 * 
 * @author nwilson
 * @since 21 Aug 2015
 */
public class N2RestJScriptValidationStrategy extends
        N2JScriptFunctionValidationStrategy {

    private IScriptRelevantDataFactory scriptRelevantDataFactory = null;

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.AbstractValidationStrategy#getScriptRelevantDataFactory()
     * 
     * @return a RestUMLScriptRelevantDataFactory.
     */
    @Override
    protected IScriptRelevantDataFactory getScriptRelevantDataFactory() {
        if (scriptRelevantDataFactory == null) {
            scriptRelevantDataFactory = new RestUMLScriptRelevantDataFactory();
        }
        return scriptRelevantDataFactory;
    }

}
