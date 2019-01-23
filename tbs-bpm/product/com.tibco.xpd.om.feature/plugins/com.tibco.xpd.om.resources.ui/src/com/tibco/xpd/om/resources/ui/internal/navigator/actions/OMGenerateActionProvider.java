/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * Generate action provider for the OM objects and file(s).
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class OMGenerateActionProvider extends CommonActionProvider {

    private GenerateLabelProperitesAction generatePropertiesAction;

    /**
     * Default constructor.
     */
    public OMGenerateActionProvider() {

    }

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
        generatePropertiesAction = new GenerateLabelProperitesAction();
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE,
                generatePropertiesAction);
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
     */
    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);
        if (context != null) {
            generatePropertiesAction
                    .selectionChanged((IStructuredSelection) context
                            .getSelection());
        }
    }
}
