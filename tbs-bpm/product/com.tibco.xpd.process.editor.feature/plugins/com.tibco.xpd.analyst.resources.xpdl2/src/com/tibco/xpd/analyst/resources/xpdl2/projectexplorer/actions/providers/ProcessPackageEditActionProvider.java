/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.CopyAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.PasteAction;


/**
 * Project Explorer's Edit action provider - this will provide copy, paste and
 * delete actions.
 * 
 * @author njpatel
 * 
 */
public class ProcessPackageEditActionProvider extends AbstractXpdl2EditActionProvider {

    @Override
    protected BaseSelectionListenerAction createDeleteAction(Shell shell) {
        return new DeleteAction(shell);
    }

    @Override
    protected  BaseSelectionListenerAction createPasteAction(Shell shell) {
        return new PasteAction();
    }

    @Override
    protected BaseSelectionListenerAction createCopyAction(Shell shell) {
        return new CopyAction();
    }
    
}
