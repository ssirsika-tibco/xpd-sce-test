/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;

/**
 * @author rsomayaj
 * 
 */
public class WebServiceXPathScriptProvider extends
        AbstractWebServiceScriptProvider {

    /**
     * @param grammar
     */
    public WebServiceXPathScriptProvider(String grammar) {
        super(grammar);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractWebServiceScriptProvider#getGrammar()
     * 
     * @return
     */
    @Override
    protected String getGrammar() {
        return ProcessXPathConsts.XPATH_GRAMMAR;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractWebServiceScriptProvider#getDefaultDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultDestination() {
        return ProcessXPathConsts.XPATH_DESTINATION;
    }
}
