/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * Menu action to create a new draft of a project.
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class CreateNewDraftAction extends BaseSelectionListenerAction {

    /**
     * @param text
     */
    protected CreateNewDraftAction(String text) {
        super(text);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     *
     */
    @Override
    public void run() {
        // TODO Implement for ACE-2032
    }

}
