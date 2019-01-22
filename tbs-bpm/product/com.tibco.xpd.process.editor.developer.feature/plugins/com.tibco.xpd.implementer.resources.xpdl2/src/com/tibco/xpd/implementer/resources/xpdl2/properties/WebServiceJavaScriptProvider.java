/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.process.js.model.ProcessJsConsts;

/**
 * @author rsomayaj
 * 
 */
public class WebServiceJavaScriptProvider extends
        AbstractWebServiceScriptProvider {

    /**
     * @param grammar
     */
    public WebServiceJavaScriptProvider(String grammar) {
        super(grammar);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractWebServiceScriptProvider#getDefaultDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultDestination() {
        return ProcessJsConsts.JSCRIPT_DESTINATION;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractWebServiceScriptProvider#getGrammar()
     * 
     * @return
     */
    @Override
    protected String getGrammar() {
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }

}
