/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class UMLReorientConnectionViewCommand extends
        AbstractTransactionalCommand {

    /**
     * @generated
     */
    private IAdaptable edgeAdaptor;

    /**
     * @generated
     */
    public UMLReorientConnectionViewCommand(
            TransactionalEditingDomain editingDomain, String label) {
        super(editingDomain, label, null);
    }

    /**
     * @generated
     */
    public List getAffectedFiles() {
        View view = (View) edgeAdaptor.getAdapter(View.class);
        if (view != null) {
            return getWorkspaceFiles(view);
        }
        return super.getAffectedFiles();
    }

    /**
     * @generated
     */
    public IAdaptable getEdgeAdaptor() {
        return edgeAdaptor;
    }

    /**
     * @generated
     */
    public void setEdgeAdaptor(IAdaptable edgeAdaptor) {
        this.edgeAdaptor = edgeAdaptor;
    }

    /**
     * @generated
     */
    protected CommandResult doExecuteWithResult(
            IProgressMonitor progressMonitor, IAdaptable info) {
        assert null != edgeAdaptor : "Null child in UMLReorientConnectionViewCommand"; //$NON-NLS-1$
        Edge edge = (Edge) getEdgeAdaptor().getAdapter(Edge.class);
        assert null != edge : "Null edge in UMLReorientConnectionViewCommand"; //$NON-NLS-1$
        View tempView = edge.getSource();
        edge.setSource(edge.getTarget());
        edge.setTarget(tempView);
        return CommandResult.newOKCommandResult();
    }
}
