/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

/**
 * @author nwilson
 */
public interface IScriptTransformer {

    /**
     * @param script The script to transform.
     * @return The transformed script.
     */
    String transform(String script);
}
