/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.CdsConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.ContentAssistElement;
import com.tibco.xpd.script.model.internal.client.JsEnumeration;
import com.tibco.xpd.script.model.internal.client.JsEnumerationLiteral;
import com.tibco.xpd.script.model.internal.jscript.JsContentAssistIconProvider;

/**
 * @author mtorres
 * 
 */
public class CdsContentAssistIconProvider extends JsContentAssistIconProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider
     * #getIcon(com.tibco.xpd.script.model.client.ContentAssistElement)
     */
    @Override
    public Image getIcon(ContentAssistElement contentAssistElement) {
        if (XpdResourcesPlugin.isInHeadlessMode()) {
            return null;
        }
        Image superIcon = super.getIcon(contentAssistElement);
        Image image = null;
        if (contentAssistElement instanceof JsEnumeration) {
            image =
                    CDSActivator.getDefault().getImageRegistry()
                            .get(CdsConsts.ENUMERATION);
        } else if (contentAssistElement instanceof JsEnumerationLiteral) {
            image =
                    CDSActivator.getDefault().getImageRegistry()
                            .get(CdsConsts.ENUMERATION_LITERAL);
        } else if (contentAssistElement instanceof JsClass
                || contentAssistElement instanceof JsReference) {
            image =
                    CDSActivator.getDefault().getImageRegistry()
                            .get(CdsConsts.CDS_FACTORY);
        } else if (contentAssistElement instanceof JsAttribute) {
            image =
                    CDSActivator.getDefault().getImageRegistry()
                            .get(CdsConsts.ICON_JSATTRIBUTE);
        }

        if (image != null) {
            return image;
        } else {
            return superIcon;
        }
    }

    @Override
    public Image getDefaultIcon() {
        if (XpdResourcesPlugin.isInHeadlessMode()) {
            return null;
        }
        return CDSActivator.getDefault().getImageRegistry()
                .get(CdsConsts.CDS_FACTORY);
    }

}
