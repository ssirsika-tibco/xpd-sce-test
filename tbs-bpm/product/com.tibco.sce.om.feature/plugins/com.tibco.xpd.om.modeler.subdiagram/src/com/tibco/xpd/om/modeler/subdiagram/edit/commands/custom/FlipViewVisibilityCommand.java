package com.tibco.xpd.om.modeler.subdiagram.edit.commands.custom;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.View;

public class FlipViewVisibilityCommand extends RecordingCommand {

    View view;

    public FlipViewVisibilityCommand(TransactionalEditingDomain domain,
            View view) {
        super(domain);
        this.view = view;
    }

    @Override
    public void doExecute() {

        if (view.isVisible()) {
            view.setVisible(false);
        } else {
            view.setVisible(true);
        }

    }

}
