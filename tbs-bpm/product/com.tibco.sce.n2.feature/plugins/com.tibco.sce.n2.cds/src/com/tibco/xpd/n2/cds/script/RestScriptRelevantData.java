/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;

/**
 * Identifies this script relevant data as coming from a REST payload model,
 * hence multi-instance fields are Arrays not Lists.
 * 
 * @author nwilson
 * @since 20 Aug 2015
 */
public class RestScriptRelevantData extends DefaultScriptRelevantData implements
        IRestScriptRelevantData {

    /**
     * @param name
     * @param className
     * @param isArray
     */
    public RestScriptRelevantData(String name, String className, boolean isArray) {
        super(name, className, isArray);
    }

}
