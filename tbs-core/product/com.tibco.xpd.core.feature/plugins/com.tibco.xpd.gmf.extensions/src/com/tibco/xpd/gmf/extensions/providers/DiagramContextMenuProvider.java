/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.gmf.extensions.providers;

import java.util.Set;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.ui.actions.ShowPropertiesViewAction;

/**
 * @author aallway
 * 
 */
public class DiagramContextMenuProvider extends
        org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider {

    private String[] defaultExclusionList = { "showPropertiesViewAction" //$NON-NLS-1$
    };

    /**
     * @param part
     * @param viewer
     */
    public DiagramContextMenuProvider(IWorkbenchPart part, EditPartViewer viewer) {
        super(part, viewer);

        Set exclusions = getExclusionSet();
        for (int i = 0; i < defaultExclusionList.length; i++) {
            exclusions.add(defaultExclusionList[i]);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    @Override
    public void buildContextMenu(IMenuManager menu) {
        super.buildContextMenu(menu);

        menu.prependToGroup("propertiesGroup", new ShowPropertiesViewAction()); //$NON-NLS-1$
    }
}
