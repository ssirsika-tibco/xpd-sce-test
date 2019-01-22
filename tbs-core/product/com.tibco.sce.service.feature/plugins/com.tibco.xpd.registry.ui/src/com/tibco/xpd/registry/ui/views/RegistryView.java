/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.views;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.tibco.xpd.registry.ui.selector.RegistryServiceSelector;

/**
 * @author nwilson
 */

public class RegistryView extends ViewPart {
    /** The tree view for the registry. */
    private RegistryServiceSelector viewer;

    /**
     * The constructor.
     */
    public RegistryView() {
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     * 
     * @param parent
     *            The parent component.
     */
    @Override
    public void createPartControl(final Composite parent) {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        parent.setLayout(layout);
        viewer = new RegistryServiceSelector(parent, SWT.NONE);
        IActionBars bars = getViewSite().getActionBars();
        IToolBarManager toolBarManager = bars.getToolBarManager();
        viewer.contributeActions(toolBarManager);
        viewer.registerGlobalActions(bars);
        viewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
        viewer.setFocus();
    }

    /**
     * @return The composite containing viewer for this view.
     */
    public RegistryServiceSelector getRegistryServiceSelector() {
        return viewer;
    }
}
