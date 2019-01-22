/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.editor;

import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandlerProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandlerContext;

/**
 * Provides copy, cut, paste global action handler.
 *
 * @author jarciuch
 * @since 20 Feb 2015
 */
public class RsdCopyPasteActionHandlerProvider extends
        AbstractGlobalActionHandlerProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public IGlobalActionHandler getGlobalActionHandler(
            IGlobalActionHandlerContext context) {
        return new CopyPasteActionHandler();
    }

}
