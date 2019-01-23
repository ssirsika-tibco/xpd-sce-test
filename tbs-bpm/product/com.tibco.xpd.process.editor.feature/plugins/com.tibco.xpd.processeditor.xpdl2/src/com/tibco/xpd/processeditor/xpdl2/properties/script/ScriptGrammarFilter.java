/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import com.tibco.xpd.xpdl2.Activity;

/**
 * @author nwilson
 */
public interface ScriptGrammarFilter {

    /**
     * @param activity
     * @param grammar
     * @return
     */
    boolean select(Activity activity, String grammar);

}
