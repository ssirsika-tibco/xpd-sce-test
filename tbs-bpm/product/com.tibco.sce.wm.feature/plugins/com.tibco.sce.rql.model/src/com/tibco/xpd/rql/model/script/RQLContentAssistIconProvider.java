/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rql.model.script;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.rql.model.RQLConsts;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.ContentAssistElement;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.sourceviewer.internal.util.RQLUtils;

/**
 * @author mtorres
 * 
 */
public class RQLContentAssistIconProvider implements IContentAssistIconProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider
     * #getIcon(com.tibco.xpd.script.model.client.ContentAssistElement)
     */
    public Image getIcon(ContentAssistElement contentAssistElement) {
        Image image = null;
        if (contentAssistElement instanceof JsClass) {
            JsClass jsClass = (JsClass) contentAssistElement;
            Class umlClass = jsClass.getUmlClass();
            if(umlClass != null){
                return RQLConsts.getOmIcon(umlClass);
            }
        }
        return image;
    }

}
