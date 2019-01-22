/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.xpath;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.ContentAssistElement;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;

/**
 * @author mtorres
 * 
 */
public class XPathContentAssistIconProvider implements IContentAssistIconProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider
     * #getIcon(com.tibco.xpd.script.model.client.ContentAssistElement)
     */
    public Image getIcon(ContentAssistElement contentAssistElement) {
        Image image = null;
        if (contentAssistElement != null) {
            image = Activator.getDefault().getImageRegistry().get(
                    JsConsts.XPATH_IMG_CLASS);
        }
        return image;
    }

}
