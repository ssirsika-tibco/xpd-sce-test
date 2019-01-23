/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.script.model.client.JsAttribute;

/**
 * This interface provides a wrapper for the Enumeration literal in UML model
 * 
 * @author mtorres
 * 
 */
public interface JsEnumerationLiteral extends JsAttribute {
    Enumeration getOwner();

    void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider);

    IContentAssistIconProvider getContentAssistIconProvider();
}
