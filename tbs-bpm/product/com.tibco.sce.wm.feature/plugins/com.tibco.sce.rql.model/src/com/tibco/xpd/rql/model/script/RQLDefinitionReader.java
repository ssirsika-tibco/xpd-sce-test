/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.model.script;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.rql.model.RQLConsts;
import com.tibco.xpd.rql.model.RQLModelActivator;
import com.tibco.xpd.script.model.client.DefaultJsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.ui.internal.IProcessJsDefinitionReader;

/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class RQLDefinitionReader extends DefaultJsClassDefinitionReader
        implements IProcessJsDefinitionReader {

    @Override
    protected URI getURI() {
        URL entry =
                RQLModelActivator.getDefault().getBundle()
                        .getEntry(RQLConsts.RQL_MODEL_FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

    public Image getIcon() {
        return null;
    }

    @Override
    protected JsClass createJsClass(Class umlClass, Class multipleClass) {
        UmlRQLJsClass jsClass = new UmlRQLJsClass(umlClass, multipleClass);
        jsClass.setIcon(RQLConsts.getOmIcon(umlClass));
        jsClass.setContentAssistIconProvider(getContentAssistIconProvider());
        return jsClass;
    }
    
    @Override
    protected IContentAssistIconProvider getContentAssistIconProvider() {
        return RQLConsts.getRQLContentAssistIconProvider();
    }

}
