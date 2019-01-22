package com.tibco.xpd.script.model.internal.xpath;


import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;

public class XPathAbreviationsDefinitionReader extends DefaultJsClassDefinitionReader {

    private static final String FILE_NAME = "/model/XPathAbreviations.uml"; //$NON-NLS-1$

    @Override
    protected URI getURI() {
        URL entry = Activator.getDefault().getBundle().getEntry(
                XPathAbreviationsDefinitionReader.FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

    public Image getIcon() {
        Image image = Activator.getDefault().getImageRegistry().get(
                JsConsts.XPATH_IMG_CLASS);
        return image;
    }
    
    @Override
    protected IContentAssistIconProvider getContentAssistIconProvider() {
        return XPathUtil.getXPathContentAssistIconProvider();
    }

}
