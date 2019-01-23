package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.gmf.runtime.common.ui.services.action.global.AbstractGlobalActionHandlerProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandlerContext;

public class CopyPasteActionHandlerProvider extends
        AbstractGlobalActionHandlerProvider {

    @Override
    public IGlobalActionHandler getGlobalActionHandler(
            IGlobalActionHandlerContext context) {
        return new CopyPasteActionHandler();
    }

}
