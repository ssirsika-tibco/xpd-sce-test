/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

import com.tibco.xpd.resources.ui.util.ShowViewUtil;

/**
 * @author wzurek
 */
public class ShowViewAction extends Action {

    private String viewId = ""; //$NON-NLS-1$

    public ShowViewAction(String viewId, String menuText, String tooltipText) {
        this.viewId = viewId;
        setId("ShowViewAction" + viewId); //$NON-NLS-1$

        setEnabled(true);
        setText(menuText);
        if (tooltipText != null) {
            setToolTipText(tooltipText);
        }

        if (PlatformUI.isWorkbenchRunning()) {
            IViewDescriptor desc = PlatformUI.getWorkbench().getViewRegistry()
                    .find(viewId);
            if (desc != null) {
                ImageDescriptor imageDescriptor = desc.getImageDescriptor();
                if (imageDescriptor != null) {
                    setImageDescriptor(imageDescriptor);
                }
            }
        }

    }

    @Override
    public void run() {
        ShowViewUtil.showOrDetachView(viewId, null, null);
    }

}
