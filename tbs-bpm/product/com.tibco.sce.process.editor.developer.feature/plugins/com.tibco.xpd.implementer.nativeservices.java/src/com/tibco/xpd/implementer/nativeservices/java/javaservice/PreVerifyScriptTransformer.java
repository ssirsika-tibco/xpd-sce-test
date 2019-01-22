/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice;

import com.tibco.xpd.implementer.script.IScriptTransformer;

/**
 * @author nwilson
 */
public class PreVerifyScriptTransformer implements IScriptTransformer {

    /**
     * @param script
     * @return
     * @see com.tibco.xpd.implementer.script.IScriptTransformer#transform(java.lang.String)
     */
    public String transform(String script) {
        String transformed = script.replace(".in.", ".in1."); //$NON-NLS-1$ //$NON-NLS-2$
        transformed = transformed.replace(".in=", ".in1="); //$NON-NLS-1$ //$NON-NLS-2$
        return transformed;
    }

}
