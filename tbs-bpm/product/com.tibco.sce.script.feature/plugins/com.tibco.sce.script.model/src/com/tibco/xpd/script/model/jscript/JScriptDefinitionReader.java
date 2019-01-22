package com.tibco.xpd.script.model.jscript;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;

public class JScriptDefinitionReader extends DefaultJsClassDefinitionReader {

    @Override
    protected URI getURI() {
        URL entry = Activator.getDefault().getBundle().getEntry(
                JsConsts.JAVASCRIPT_MODEL_FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

    public Image getIcon() {
        Image image = Activator.getDefault().getImageRegistry().get(
                JsConsts.JS_CLASS);
        return image;
    }
    
    @Override
    protected IContentAssistIconProvider getContentAssistIconProvider() {
        return JScriptUtils.getJsContentAssistIconProvider();
    }

}
