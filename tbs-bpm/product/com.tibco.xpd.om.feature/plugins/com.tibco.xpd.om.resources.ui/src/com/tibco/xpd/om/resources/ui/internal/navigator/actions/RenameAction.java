/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.actions.AbstractRenameAction;

/**
 * Rename action of the OM named elements in the Project Explorer.
 * 
 * @author wzurek
 */
public class RenameAction extends AbstractRenameAction {

    private static final Logger LOG = OMResourcesUIActivator.getDefault()
            .getLogger();

    private static final String PREFIX = PlatformUI.PLUGIN_ID + "."; //$NON-NLS-1$

    private static final String CONTEXT_ID = PREFIX
            + "resource_navigator_rename_action_context"; //$NON-NLS-1$

    public RenameAction(Shell shell, Tree tree) {
        super(shell, tree);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, CONTEXT_ID);
    }

    public RenameAction(Shell shell, Tree tree, IActionBars actionBars) {
        super(shell, tree, actionBars);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, CONTEXT_ID);
    }

    @Override
    protected boolean canRename(Object selectedItem) {
        // XPD-5300: Cannot rename a dynamic org unit
        return selectedItem instanceof NamedElement
                && !(selectedItem instanceof DynamicOrgUnit);
    }

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
                                OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME,
                                newName);
                boolean canExecute = cmd.canExecute();
                if (canExecute) {
                    ed.getCommandStack().execute(cmd);
                } else {
                    LOG.error("Cannot set OM element new name. Coommand cannot execute."); //$NON-NLS-1$
                }
            }
        }
    }

    @Override
    protected String getName(Object selectedItem) {
        String name = null;

        if (selectedItem instanceof NamedElement) {
            name = ((NamedElement) selectedItem).getDisplayName();
        }
        return name;
    }
}
