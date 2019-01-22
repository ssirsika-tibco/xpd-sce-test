/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationNameEditPart;

public class BOMLabelDirectEditPolicy extends LabelDirectEditPolicy {

    @Override
    protected Command getDirectEditCommand(DirectEditRequest edit) {
        Command cmd = super.getDirectEditCommand(edit);
        if (cmd != null) {
            String labelText = (String) edit.getCellEditor().getValue();
            if ((labelText.equals(""))) { //$NON-NLS-1$
                EditPart hostEP = this.getHost();

                if (!(hostEP instanceof AssociationNameEditPart)) {
                    cmd = null;
                }
            }
        }
        return cmd;
    }
}
