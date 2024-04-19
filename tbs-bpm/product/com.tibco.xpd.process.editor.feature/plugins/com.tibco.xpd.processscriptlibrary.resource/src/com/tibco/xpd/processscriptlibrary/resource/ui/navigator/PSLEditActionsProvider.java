/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.ui.navigator;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.CopyAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.PasteAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers.AbstractXpdl2EditActionProvider;

/**
 * Edit Actions Provider for a Process Script Library Function (Copy, paste and delete).
 *
 * @author nkelkar
 * @since Jan 17, 2024
 */
public class PSLEditActionsProvider extends AbstractXpdl2EditActionProvider {

	/**
	 * 
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers.AbstractXpdl2EditActionProvider#createDeleteAction(org.eclipse.swt.widgets.Shell)
	 *
	 * @param shell
	 * @return
	 */
	@Override
    protected BaseSelectionListenerAction createDeleteAction(Shell shell) {
        return new DeleteAction(shell);
    }

	/**
	 * 
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers.AbstractXpdl2EditActionProvider#createPasteAction(org.eclipse.swt.widgets.Shell)
	 *
	 * @param shell
	 * @return
	 */
    @Override
    protected  BaseSelectionListenerAction createPasteAction(Shell shell) {
		return new PasteAction();
    }

	/**
	 * 
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers.AbstractXpdl2EditActionProvider#createCopyAction(org.eclipse.swt.widgets.Shell)
	 *
	 * @param shell
	 * @return
	 */
    @Override
    protected BaseSelectionListenerAction createCopyAction(Shell shell) {
		return new CopyAction();
    }

}
