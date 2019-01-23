/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author rsomayaj
 * 
 */
public class UpdateMappingsCommandFactory {

    /**
     * 
     */
    private static final String JAVA_SCRIPT = "JavaScript"; //$NON-NLS-1$

    private static final String XPATH = "XPath"; //$NON-NLS-1$

    /**
     * 
     * @param editingDomain
     * @param eObj
     * @return
     */
    public static UpdateMappingsCommand getUpdateMappingsCommand(
            EditingDomain editingDomain, EObject eObj) {
        if (JAVA_SCRIPT.equals(getScriptGrammar(eObj))) {
            return new UpdateJavaScriptMappingsCommand(editingDomain, eObj);
        } else if (XPATH.equals(getScriptGrammar(eObj))) {
            return new UpdateXPathMappingCommand(editingDomain, eObj);
        }
        return null;
    }

    /**
     * @return
     */
    protected static String getScriptGrammar(EObject eObj) {
        // TODO - Logic to understand whether this eobject needs to follow which
        // script grammar goes in here
        // HardCoded to Javascript at the moment.
        return JAVA_SCRIPT;
    }

}
