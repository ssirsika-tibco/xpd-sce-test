/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.navigator.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.refactoring.RenameBOMElementWizard;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.actions.AbstractRenameAction;

/**
 * Rename action of BOM NamedElements in the Project Explorer.
 * 
 * @author wzurek
 */
public class RenameAction extends AbstractRenameAction {

    private static final String PREFIX = PlatformUI.PLUGIN_ID + "."; //$NON-NLS-1$

    private static final String CONTEXT_ID = PREFIX
            + "resource_navigator_rename_action_context"; //$NON-NLS-1$

    private static final Logger LOG = BOMResourcesPlugin.getDefault()
            .getLogger();

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
        return selectedItem instanceof NamedElement
                || selectedItem instanceof Diagram;
    }

    @Override
    protected void doRename(final Object selectedItem, final String newName) {
        if (newName != null && newName.length() > 0) {
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            Command cmd = null;

            if (selectedItem instanceof Diagram) {
                cmd =
                        SetCommand.create(ed,
                                selectedItem,
                                NotationPackage.eINSTANCE.getDiagram_Name(),
                                newName);
            }

            if (cmd != null) {
                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                } else {
                    LOG.error("Cannot set BOM element new name. Coommand cannot execute."); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.actions.AbstractRenameAction#run()
     * 
     */
    @Override
    public void run() {

        Object selectedItem = getSelection().getFirstElement();

        /*
         * XPD-7879: Saket: Trigger the LTK refactor framework to deal with
         * rename of BOM elements.
         */
        if (selectedItem instanceof NamedElement) {

            RenameBOMElementWizard refactoringWizard =
                    new RenameBOMElementWizard((NamedElement) (selectedItem));

            RefactoringWizardOpenOperation op =
                    new RefactoringWizardOpenOperation(refactoringWizard);

            try {

                Shell shell = getActiveShell();

                if (shell != null) {

                    op.run(shell,
                            com.tibco.xpd.bom.resources.ui.internal.Messages.RenameBOMElementWizard_RenameElementPageTitle);
                }

            } catch (InterruptedException e) {

                Activator.getDefault().getLogger().error(e);
            }

        } else {

            super.run();

        }

    }

    /**
     * Get workbench shell.
     * 
     * @return Workbench shell.
     */
    private Shell getActiveShell() {

        IWorkbench wb = PlatformUI.getWorkbench();

        if (null != wb) {
            IWorkbenchWindow win = wb.getActiveWorkbenchWindow();

            if (null != win) {

                IWorkbenchPage page = win.getActivePage();

                if (null != page) {

                    IWorkbenchPart part = page.getActivePart();

                    if (null != part) {

                        IWorkbenchPartSite partSite = part.getSite();

                        if (null != partSite) {

                            Shell shell = partSite.getShell();

                            return shell;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected String getName(Object selectedItem) {
        String name = null;
        if (selectedItem instanceof NamedElement) {
            name =
                    PrimitivesUtil
                            .getDisplayLabel(((NamedElement) selectedItem));
        } else if (selectedItem instanceof Diagram) {
            name = ((Diagram) selectedItem).getName();
        }
        return name;
    }
}
