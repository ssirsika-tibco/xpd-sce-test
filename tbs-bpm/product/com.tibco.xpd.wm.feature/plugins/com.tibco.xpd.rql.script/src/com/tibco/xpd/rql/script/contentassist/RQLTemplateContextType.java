/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.script.contentassist;

import org.eclipse.jface.text.templates.TemplateContextType;

/**
 * Declaration for RQL Context Type
 * 
 * @author rsomayaj
 * 
 */
public class RQLTemplateContextType extends TemplateContextType {

    public RQLTemplateContextType() {
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