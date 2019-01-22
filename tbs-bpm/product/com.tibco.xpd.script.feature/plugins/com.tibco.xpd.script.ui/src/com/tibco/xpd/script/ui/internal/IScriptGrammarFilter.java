/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal;

import org.eclipse.emf.ecore.EObject;

/**
 * @author nwilson
 */
public interface IScriptGrammarFilter {

    /**
     * @param activity
     * @param grammar
     * @return
     */
    boolean select(EObject eObject, String grammar);

}
