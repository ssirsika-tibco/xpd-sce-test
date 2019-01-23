/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.script;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.implementer.nativeservices.java.JavaActivator;
import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.DefaultPojoJsAttribute;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.DefaultPojoJsClass;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.DefaultPojoJsMethod;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.DefaultPojoJsReference;
import com.tibco.xpd.script.model.internal.client.ContentAssistElement;
import com.tibco.xpd.script.model.internal.jscript.JsContentAssistIconProvider;

/**
 * @author mtorres
 * 
 */
public class PojoContentAssistIconProvider extends JsContentAssistIconProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider
     * #getIcon(com.tibco.xpd.script.model.client.ContentAssistElement)
     */
    public Image getIcon(ContentAssistElement contentAssistElement) {
        Image superIcon = super.getIcon(contentAssistElement);
        Image image = null;
        if (contentAssistElement instanceof DefaultPojoJsClass
                || contentAssistElement instanceof DefaultPojoJsReference) {
            image =
                    JavaActivator.getDefault().getImageRegistry()
                            .get(JavaConstants.ICON_BEANTYPE);
        } else if (contentAssistElement instanceof DefaultPojoJsMethod) {
            image =
                    JavaActivator.getDefault().getImageRegistry()
                            .get(JavaConstants.ICON_METHOD);
        } else if (contentAssistElement instanceof DefaultPojoJsAttribute) {
            image =
                    JavaActivator.getDefault().getImageRegistry()
                            .get(JavaConstants.ICON_SIMPLETYPE);
        }
        if (image != null) {
            return image;
        } else {
            return superIcon;
        }
    }

}
