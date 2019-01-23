/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands.custom;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.commands.core.commands.RepositionEObjectCommand;
import org.eclipse.gmf.runtime.notation.View;

/**
 * A command that reorders a list of semantic elements and updates their order
 * in the corresponding compartment view.
 * 
 * @author rgreen
 */
public class BOMCompartmentRepositionEObjectCommand extends
        RepositionEObjectCommand {

    EditPart childToMove = null;

    int newIndex = 0;

    public BOMCompartmentRepositionEObjectCommand(
            TransactionalEditingDomain editingDomain, String label,
            EList elements, EObject element, int displacement) {
        super(editingDomain, label, elements, element, displacement);
    }

    public BOMCompartmentRepositionEObjectCommand(EditPart childToMove,
            TransactionalEditingDomain editingDomain, String label,
            EList elements, EObject element, int displacement, int newIndex) {
        super(editingDomain, label, elements, element, displacement);

        this.childToMove = childToMove;
        this.newIndex = newIndex;
    }

    public CommandResult doExecuteWithResult(IProgressMonitor progressMonitor,
            IAdaptable info) throws ExecutionException {
        CommandResult rs = super.doExecuteWithResult(progressMonitor, info);

        if (rs.getStatus() != null && rs.getStatus().isOK()) {
            EditPart compartment = childToMove.getParent();

            ViewUtil.repositionChildAt((View) compartment.getModel(),
                    (View) childToMove.getModel(),
                    newIndex);
            compartment.refresh();
        }

        return rs;
    }
}
