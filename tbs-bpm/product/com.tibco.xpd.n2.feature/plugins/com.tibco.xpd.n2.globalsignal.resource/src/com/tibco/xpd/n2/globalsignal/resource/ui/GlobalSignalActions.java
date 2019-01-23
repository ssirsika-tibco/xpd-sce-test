/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers.AbstractXpdl2EditActionProvider;

/**
 * Actions to be added over Global Signals (Copy, paste and delete).
 * 
 * @author sajain
 * @since Feb 17, 2015
 */
public class GlobalSignalActions extends AbstractXpdl2EditActionProvider {

    @Override
    protected BaseSelectionListenerAction createDeleteAction(Shell shell) {

        /*
         * Delete action.
         */
        return new DeleteAction(shell);
    }

    @Override
    protected BaseSelectionListenerAction createPasteAction(Shell shell) {

        /*
         * Paste action.
         */
        return new GSDPasteAction();
    }

    @Override
    protected BaseSelectionListenerAction createCopyAction(Shell shell) {

        /*
         * Copy action.
         */
        return new GSDCopyAction();
    }

}
