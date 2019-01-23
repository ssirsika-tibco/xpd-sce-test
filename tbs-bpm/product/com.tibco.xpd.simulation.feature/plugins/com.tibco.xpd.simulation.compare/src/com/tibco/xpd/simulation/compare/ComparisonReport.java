/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;

/**
 * @author nwilson
 */
public abstract class ComparisonReport extends Canvas {
    /**
     * @param parent The parent composite.
     */
    public ComparisonReport(final Composite parent) {
        super(parent, SWT.NONE);
        GridLayout grid = new GridLayout();
        grid.marginWidth = 0;
        grid.horizontalSpacing = 0;
        grid.verticalSpacing = 0;
        grid.marginHeight = 0;
        setLayout(grid);
    }
    
    /**
     * @param input The input for the editor.
     * @param filter The resources to include.
     */
    public void setInput(final IEditorInput input, final Collection filter) {
    	if (input != null) {
			IResource[] inputs = (IResource[]) input.getAdapter(IResource[].class);
			if (inputs != null) {
				if (filter != null) {
					ArrayList<IResource> filteredList = new ArrayList<IResource>();
					for (int i = 0; i < inputs.length; i++) {
						if (filter.contains(inputs[i])) {
							filteredList.add(inputs[i]);
						}
					}
					inputs = new IResource[filteredList.size()];
					filteredList.toArray(inputs);
				}
				setInput(inputs);
			}
    	}
    }

	/**
	 * @param inputs The resources to compare.
	 */
	public abstract void setInput(IResource[] inputs);
}
