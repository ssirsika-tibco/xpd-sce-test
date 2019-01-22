/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;

/**
 * Implemented by classes to allow them to provide actions to a contribution
 * manager based on a source Control. This is used to add actions to
 * implementations of BaseTreeControl.
 * 
 * @author nwilson
 * @since 17 Feb 2015
 */
public interface CommandViewerActionProvider {
    void addCommandViewerActions(Control source, IContributionManager manager,
            StructuredViewer viewer);
}