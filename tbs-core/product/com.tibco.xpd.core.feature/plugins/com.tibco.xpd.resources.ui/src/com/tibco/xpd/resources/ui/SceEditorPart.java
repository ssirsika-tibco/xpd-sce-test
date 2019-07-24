/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.resources.ui;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.EditorPart;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * Extended EditorPart to allow the tab title to be programmatically refreshed.
 *
 * @author nwilson
 * @since 23 Jul 2019
 */
public abstract class SceEditorPart extends EditorPart implements IRefreshableTitle {

    @Override
    public void refreshTitle() {
        setPartName(getTitleText());
        firePropertyChange(PROP_TITLE);
    }

    /**
     * @see com.tibco.xpd.resources.ui.SceEditorPart#getTitleText()
     */
    protected String getTitleText() {
        String title = ""; //$NON-NLS-1$
        IEditorInput input = getEditorInput();
        WorkingCopy workingCopy = getWorkingCopy();
        title = input.getName();
        if (workingCopy != null && workingCopy.isReadOnly()) {
            title += " " + "[Read Only]"; //$NON-NLS-1$
        }
        return title;
    }

    protected abstract WorkingCopy getWorkingCopy();
}
