/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.commands.internal;

import java.util.Collection;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * Command to set the diagram editing selection on the owner of the given edit
 * parts TO the given edit parts.
 * 
 * @author aallway
 * @since 3.2
 */
public class SelectEditPartsCommand extends Command {

    private Collection<EditPart> editParts;

    private EditPartViewer viewer;

    public SelectEditPartsCommand(EditPartViewer viewer,
            Collection<EditPart> editParts) {
        super();
        this.editParts = editParts;
        this.viewer = viewer;
    }

    @Override
    public void execute() {
        if (editParts != null) {
            viewer.getSelectionManager().setSelection(new StructuredSelection(
                    editParts.toArray()));
        }

        return;
    }

}
