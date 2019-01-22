/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon;

import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.rcp.internal.Messages;

public enum RibbonConsts {

    ACTION_SAVE("save", Messages.RibbonConsts_save_action), //$NON-NLS-1$
    ACTION_FIND("find", Messages.RibbonConsts_find_action), //$NON-NLS-1$
    ACTION_CUT("cut", Messages.RibbonConsts_cut_action), //$NON-NLS-1$
    ACTION_COPY("copy", Messages.RibbonConsts_copy_action), //$NON-NLS-1$
    ACTION_PASTE("paste", Messages.RibbonConsts_paste_action), //$NON-NLS-1$
    ACTION_DELETE(ActionFactory.DELETE.getId(),
            Messages.RibbonConsts_delete_action), ACTION_UNDO(
            "undo", Messages.RibbonConsts_undo_action), //$NON-NLS-1$
    ACTION_REDO("redo", Messages.RibbonConsts_redo_action), //$NON-NLS-1$
    ACTION_PRINT("print", Messages.RibbonConsts_print_action), //$NON-NLS-1$
    ACTION_RENAME(ActionFactory.RENAME.getId(),
            Messages.RibbonConsts_rename_action);

    private final String id;

    private final String label;

    RibbonConsts(String id, String label) {
        this.id = id;
        this.label = label;

    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
