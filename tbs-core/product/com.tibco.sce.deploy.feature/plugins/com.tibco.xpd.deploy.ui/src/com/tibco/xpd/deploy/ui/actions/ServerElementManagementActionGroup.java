/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;

import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerElementType;
import com.tibco.xpd.deploy.impl.OperationImpl;

/**
 * Group of actions for ServerElement. Actions are created dynamically based on
 * list of operation available for ServerElement.
 * <p>
 * <i>Created: 8 Aug 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerElementManagementActionGroup extends ActionGroup {

    private final StructuredViewer viewer;

    private final List<Action> actions;

    public ServerElementManagementActionGroup(StructuredViewer viewer) {
        this.viewer = viewer;
        actions = new ArrayList<Action>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
     * action.IMenuManager)
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        for (Action action : actions) {
            menu.add(action);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {
        // register global actions' handlers.
        for (Action action : actions) {
            if (action instanceof ServerElementAction) {
                ServerElementAction a = (ServerElementAction) action;
                if (a.getOperation() instanceof OperationImpl) {
                    OperationImpl o = (OperationImpl) a.getOperation();
                    if (o.getGlobalActionId() != null) {
                        actionBars
                                .setGlobalActionHandler(o.getGlobalActionId(),
                                        action);
                    }
                }
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.
     * ActionContext)
     */
    @Override
    public void setContext(ActionContext context) {
        super.setContext(context);
        if (context != null) {
            IStructuredSelection structuredSelection =
                    (IStructuredSelection) context.getSelection();
            actions.clear();
            if (structuredSelection.getFirstElement() instanceof ServerElement) {
                ServerElement serverElement =
                        (ServerElement) structuredSelection.getFirstElement();
                ServerElementType serverElementType =
                        serverElement.getServerElementType();
                if (serverElementType != null) {
                    for (Iterator<?> iter =
                            serverElementType.getOperations().iterator(); iter
                            .hasNext();) {
                        Operation operation = (Operation) iter.next();
                        ServerElementAction action =
                                new ServerElementAction(viewer, operation);
                        action.selectionChanged(structuredSelection);
                        actions.add(action);

                    }
                }
            }
        }
    }
}
