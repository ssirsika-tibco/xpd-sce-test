/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.xpath.contentassist;

import org.eclipse.jface.text.templates.TemplateContextType;

/**
 * Context type declaration for XPath
 * 
 * @author rsomayaj
 * 
 */
public class XPathTemplateContextType extends TemplateContextType {

    public XPathTemplateContextType() {
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