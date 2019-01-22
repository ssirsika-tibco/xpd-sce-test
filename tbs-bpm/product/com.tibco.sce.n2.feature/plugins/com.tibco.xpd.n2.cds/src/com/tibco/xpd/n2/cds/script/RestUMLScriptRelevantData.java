/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;

/**
 * Identifies this script relevant data as coming from a REST payload model,
 * hence multi-instance fields are Arrays not Lists.
 * 
 * @author nwilson
 * @since 20 Aug 2015
 */
public class RestUMLScriptRelevantData extends DefaultUMLScriptRelevantData
        implements IRestScriptRelevantData {

    /**
     * @param name
     * @param className
     * @param isArray
     * @param jsClass
     */
    public RestUMLScriptRelevantData(String name, String className,
            boolean isArray, JsClass jsClass) {
        super(name, className, isArray, jsClass);
    }

}
