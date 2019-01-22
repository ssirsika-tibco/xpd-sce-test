/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.navigator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.rsd.NamedElement;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;
import com.tibco.xpd.ui.actions.AbstractRenameAction;

/**
 * Rename action of the named elements in the Project Explorer.
 * 
 * @author jarciuch
 * @since 27 Feb 2015
 */
public class RsdRenameAction extends AbstractRenameAction {

    private static final Logger LOG = RsdUIPlugin.getLogger();

    private static final String CONTEXT_ID =
            "org.eclipse.ui.resource_navigator_rename_action_context"; //$NON-NLS-1$

    /**
     * Creates a rename action.
     * 
     * @param shell
     *            a context shell.
     * @param tree
     *            a context tree.
     */
    public RsdRenameAction(Shell shell, Tree tree) {
        super(shell, tree);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, CONTEXT_ID);
    }

    /**
     * Creates a rename action.
     * 
     * @param shell
     *            a context shell.
     * @param tree
     *            a context tree.
     * @param actionBars
     *            action bars for registration of global actions.
     */
    public RsdRenameAction(Shell shell, Tree tree, IActionBars actionBars) {
        super(shell, tree, actionBars);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, CONTEXT_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean canRename(Object selectedItem) {
        return selectedItem instanceof NamedElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRename(Object selectedItem, String newName) {
        if (selectedItem instanceof NamedElement && newName != null
                && newName.length() > 0) {
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            if (ed != null) {
                Command cmd =
                        SetCommand.create(ed,
                                selectedItem,
                                RsdPackage.eINSTANCE.getNamedElement_Name(),
                                newName);
                boolean canExecute = cmd.canExecute();
                if (canExecute) {
                    ed.getCommandStack().execute(cmd);
                } else {
                    LOG.error("Cannot set RSD element new name. Coommand cannot execute."); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName(Object selectedItem) {
        if (selectedItem instanceof NamedElement) {
            return ((NamedElement) selectedItem).getName();
        }
        return null;
    }
}
