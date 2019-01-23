/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.refactor;

import org.eclipse.emf.ecore.EObject;
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

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.ui.actions.AbstractRenameAction;

/**
 * Rename action for GSD elements (Global signals and payload data).
 * 
 * @author sajain
 * @since Sep 14, 2015
 */
public class RenameGSDElementAction extends AbstractRenameAction {

    /**
     * Prefix in action context ID.
     */
    private static final String PREFIX = PlatformUI.PLUGIN_ID + "."; //$NON-NLS-1$

    /**
     * Action context ID.
     */
    private static final String CONTEXT_ID = PREFIX
            + "resource_navigator_rename_action_context"; //$NON-NLS-1$

    /**
     * Rename action for GSD elements (Global signals and payload data).
     * 
     * @param shell
     * @param tree
     */
    public RenameGSDElementAction(Shell shell, Tree tree) {
        super(shell, tree);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, CONTEXT_ID);
    }

    /**
     * Rename action for GSD elements (Global signals and payload data).
     * 
     * @param shell
     * @param tree
     * @param actionBars
     */
    public RenameGSDElementAction(Shell shell, Tree tree, IActionBars actionBars) {
        super(shell, tree, actionBars);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, CONTEXT_ID);
    }

    @Override
    protected boolean canRename(Object selectedItem) {
        return selectedItem instanceof GlobalSignal
                || selectedItem instanceof PayloadDataField;
    }

    @Override
    protected void doRename(final Object selectedItem, final String newName) {

        // Nothing to do here as we are overriding the run method to open the
        // refactor GSD element wizard.
    }

    @Override
    public void run() {

        Object selectedItem = getSelection().getFirstElement();

        if (selectedItem instanceof GlobalSignal
                || selectedItem instanceof PayloadDataField) {

            RenameGSDElementWizard refactoringWizard =
                    new RenameGSDElementWizard((EObject) (selectedItem));

            RefactoringWizardOpenOperation op =
                    new RefactoringWizardOpenOperation(refactoringWizard);

            try {

                Shell shell = getActiveShell();

                if (shell != null) {

                    op.run(shell,
                            Messages.RefactorGSDElementAction_RenameElement);
                }

            } catch (InterruptedException e) {

                GsdResourcePlugin.getDefault().getLogger().error(e);
            }
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

        if (selectedItem instanceof GlobalSignal) {

            name = ((GlobalSignal) selectedItem).getName();

        } else if (selectedItem instanceof PayloadDataField) {

            name = ((PayloadDataField) selectedItem).getName();

        }

        return name;
    }
}
