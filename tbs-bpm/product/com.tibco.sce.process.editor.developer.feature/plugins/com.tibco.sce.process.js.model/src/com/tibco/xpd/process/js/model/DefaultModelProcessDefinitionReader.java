/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.script.model.client.DefaultJsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.ui.internal.IProcessModelDefinitionReader;

/**
 * Default implementation of the Model definition reader that allows the contribution
 * of Models for content assist and script validation.
 * 
 * @author mtorres
 */
public class DefaultModelProcessDefinitionReader extends DefaultJsClassDefinitionReader
        implements IProcessModelDefinitionReader {
    
    private URI uri;
    
    private Image image;
    
    public DefaultModelProcessDefinitionReader() {
    }
    
    @Override
    protected URI getURI() {        
        return uri;
    }
    
    public Image getIcon() {        
        return image;
    }
    
    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    @Override
    protected IContentAssistIconProvider getContentAssistIconProvider() {
        return JScriptUtils.getJsContentAssistIconProvider();
    }
}
