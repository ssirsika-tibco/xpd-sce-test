/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.ImageCache;
import com.tibco.xpd.registry.ui.internal.Messages;

/**
 * Action to refresh the contents of a registry view item.
 * 
 * @author nwilson
 */
public class RefreshAction extends Action {
    /** Message title for remove search. */
    private static final String REFRESH_ACTION_TITLE = Messages.RefreshAction_label;

    /** Message text for remove search. */
    private static final String REFRESH_ACTION_TOOLTIP = Messages.RefreshAction_tooltip;

    /** The viewer to refresh. */
    private final StructuredViewer viewer;

    /** Action definition id used by commands. */
    private static final String ACTION_DEFINITION_ID = "org.eclipse.ui.file.refresh"; //$NON-NLS-1$

    /**
     * @param viewer
     *            The viewer to refresh.
     */
    public RefreshAction(StructuredViewer viewer) {
        super();
        this.viewer = viewer;
        setText(REFRESH_ACTION_TITLE);
        setImageDescriptor(Activator.getImageDescriptor(ImageCache.REFRESH));
        setToolTipText(REFRESH_ACTION_TOOLTIP);
        setActionDefinitionId(ACTION_DEFINITION_ID);

    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        ISelection selection = viewer.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structured = (IStructuredSelection) selection;
            if (structured.size() == 0) {
                viewer.refresh();
            } else {
                for (Object next : structured.toList()) {
                    viewer.refresh(next, true);
                }
            }
        }
    }
}
