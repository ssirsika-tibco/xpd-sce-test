/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.actions;

import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * @author wzurek
 */
public class ShowPropertiesViewAction extends ShowViewAction {

    private final static String VIEW_ID = "org.eclipse.ui.views.PropertySheet"; //$NON-NLS-1$

    public ShowPropertiesViewAction() {
        super(VIEW_ID, Messages.ShowPropertiesViewAction_title, Messages.ShowPropertiesViewAction_title);

    }

}
