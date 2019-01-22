/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.javascript.contentassist;

import org.eclipse.jface.text.templates.TemplateContextType;

/**
 * Template Context resolver definition
 * 
 * @author rsomayaj
 * 
 */
public class JSTemplateContextType extends TemplateContextType {

    public JSTemplateContextType() {
        addGlobalResolvers();
    }

    private void addGlobalResolvers() {
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.Cursor());
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.WordSelection());
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.LineSelection());
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.Dollar());
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.Date());
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.Year());
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.Time());
        addResolver(new org.eclipse.jface.text.templates.GlobalTemplateVariables.User());
    }

}