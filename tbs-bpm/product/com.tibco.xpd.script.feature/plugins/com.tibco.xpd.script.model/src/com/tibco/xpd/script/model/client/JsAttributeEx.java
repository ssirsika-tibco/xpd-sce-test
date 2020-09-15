/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

/**
 * Sid ACE-4574 Extension of {@link JsAttribute} that supports an extended 'get script relevant data' function with
 * support for arrays (by flagging to the function whether the content assist processor is asking for the script
 * relevant data (functions, properties) for a multiple (like array) or single instance field).
 *
 * @author aallway
 * @since 15 Sep 2020
 */
public interface JsAttributeEx extends JsAttribute {

    /**
     * This function will be used in preference to {@link JsAttribute#getScriptRelevantData()} when this interface is
     * implemented.
     * 
     * Returns an appropriate script relevant data for the property type. Provided to support dynamic type checking in
     * TIBCO Form product. The default implementation returns null.
     * 
     * @param multiple
     *            For expressions involving index of operations, this will/should be passed as false to get correct code
     *            completions.
     * 
     * @return Appropriate script relevant data for the property type.
     */
    public IScriptRelevantData getScriptRelevantDataEx(boolean isMultiple);
}
