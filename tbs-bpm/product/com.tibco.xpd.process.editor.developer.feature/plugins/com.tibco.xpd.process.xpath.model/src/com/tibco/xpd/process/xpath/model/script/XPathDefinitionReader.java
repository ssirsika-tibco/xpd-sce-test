package com.tibco.xpd.process.xpath.model.script;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.process.xpath.model.Activator;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.script.model.client.DefaultJsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.xpath.XPathUtil;
import com.tibco.xpd.script.ui.internal.IProcessJsDefinitionReader;

public class XPathDefinitionReader extends DefaultJsClassDefinitionReader
        implements IProcessJsDefinitionReader {

    private static final String FILE_NAME = "/model/xpath/XPath.uml"; //$NON-NLS-1$

    @Override
    protected URI getURI() {
        URL entry =
                Activator.getDefault().getBundle()
                        .getEntry(XPathDefinitionReader.FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

    public Image getIcon() {
        Image image =
                Activator.getDefault().getImageRegistry()
                        .get(ProcessXPathConsts.XPATH_IMG_CLASS);
        return image;
    }
    
    @Override
    protected IContentAssistIconProvider getContentAssistIconProvider() {
        return XPathUtil.getXPathContentAssistIconProvider();
    }

}
