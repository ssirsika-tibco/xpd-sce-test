/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ObjectPluginAction;

import com.tibco.xpd.om.modeler.subdiagram.edit.commands.custom.ExpandCollapseOrgUnitNodeHierarchyCommand;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubEditPart;

/**
 * 
 * Expands or collapses the hierarchy of OrgUnits below the selected OrgUnit.
 * 
 * The action has no knowledge of the current state of the hierarchy i.e.
 * whether it is currently expanded or collapsed and just executes an
 * ExpandCollapseOrgUnitNodeHierarchyCommand. This command determines the
 * current state of the hierarchy and then inverts it.
 * 
 * @author rgreen
 * 
 */
public class ExpandCollapseOrgUnitsAction implements IObjectActionDelegate,
        IViewActionDelegate {

    private IWorkbenchPart targetPart;
    private OrgUnitSubEditPart orgUnitEP;

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    public void run(IAction action) {
        final Object model = orgUnitEP.getModel();
        TransactionalEditingDomain ed = orgUnitEP.getEditingDomain();

        Command cmd = new ExpandCollapseOrgUnitNodeHierarchyCommand(ed,
                orgUnitEP);
        ed.getCommandStack().execute(cmd);

    }

    @SuppressWarnings("restriction")
    public void selectionChanged(IAction action, ISelection selection) {
        if (action instanceof ObjectPluginAction) {
            ObjectPluginAction ac = (ObjectPluginAction) action;
            IStructuredSelection sel = (IStructuredSelection) ac.getSelection();
            Object firstElement = sel.getFirstElement();

            if (firstElement instanceof OrgUnitSubEditPart) {
                orgUnitEP = (OrgUnitSubEditPart) firstElement;
            }

        }
    }

    public void init(IViewPart view) {
        this.targetPart = view;
    }

}
