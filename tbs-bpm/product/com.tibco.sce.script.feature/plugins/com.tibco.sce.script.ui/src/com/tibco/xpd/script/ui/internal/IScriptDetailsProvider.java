/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author nwilson
 */
public interface IScriptDetailsProvider {
    /**
     * 
     * @param input
     * @return
     */
    EObject getRelevantEObject(EObject input);

    /**
     * 
     * @param input
     * @return
     */
    String getScript(EObject input);

    /**
     * 
     * @return
     */
    String getDecriptionLabel();

    /**
     * 
     * @param ed
     * @param input
     * @param script
     * @param grammar
     * @return
     */
    Command getSetScriptCommand(EditingDomain ed, EObject input, String script,
            String grammar);

    /**
     * Gets the command to set the script grammar.
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param scriptGrammar
     * @return
     */
    Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject scriptContainer, String scriptGrammar);

    /**
     * Returns TRUE is the input object is a new script
     * 
     * @param object
     * @return
     */
    Boolean isNewScriptInformation(EObject object);
}
