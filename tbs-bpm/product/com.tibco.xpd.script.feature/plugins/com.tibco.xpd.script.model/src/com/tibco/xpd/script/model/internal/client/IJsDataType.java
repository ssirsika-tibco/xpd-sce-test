/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import org.eclipse.uml2.uml.DataType;

import com.tibco.xpd.script.model.client.JsClass;

/**
 * This interface provides a wrapper for the Enumeration
 * in UML model
 * 
 * @author mtorres
 * 
 */
public interface IJsDataType extends JsClass{
    void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider);

    IContentAssistIconProvider getContentAssistIconProvider();
    
    DataType getDataType();
}
