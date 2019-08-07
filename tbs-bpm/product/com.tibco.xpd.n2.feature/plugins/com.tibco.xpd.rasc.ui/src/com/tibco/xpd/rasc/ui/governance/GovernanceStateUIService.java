/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.ui.IRefreshableTitle;

/**
 * Service for UI related governance state utilities.
 *
 * @author nwilson
 * @since 6 Aug 2019
 */
public class GovernanceStateUIService {

    /**
     * Update the editor tab label text.
     */
    public void refreshEditorLabels() {
        for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
            for (IWorkbenchPage page : window.getPages()) {
                for (IEditorReference ref : page.getEditorReferences()) {
                    IEditorPart editor = ref.getEditor(false);
                    if (editor instanceof IRefreshableTitle) {
                        ((IRefreshableTitle) editor).refreshTitle();
                    }
                }
            }
        }
    }

}
