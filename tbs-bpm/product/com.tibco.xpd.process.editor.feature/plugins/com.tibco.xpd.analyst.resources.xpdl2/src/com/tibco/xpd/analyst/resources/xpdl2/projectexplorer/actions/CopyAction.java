/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.Cursors;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.commands.AbstractWaitTillExecutionCommand;
import com.tibco.xpd.xpdl2.resources.XPDCopyToClipboardCommand;

/**
 * Project Explorer's Copy action
 * 
 * @author njpatel
 * 
 */
public class CopyAction extends BaseSelectionListenerAction {

    public static final String ID = Xpdl2ResourcesPlugin.PLUGIN_ID
            + ".CopyAction"; //$NON-NLS-1$

    public static final String PREFIX = PlatformUI.PLUGIN_ID + "."; //$NON-NLS-1$

    public static final String COPY_ACTION = PREFIX
            + "resource_navigator_copy_action_context"; //$NON-NLS-1$

    private CommandContainer commandContainer = null;

    private IStructuredSelection lastSelection = null;

    /**
     * Copy Action
     */
    public CopyAction() {
        super(Messages.CopyAction_Menu_title);
        setToolTipText(Messages.CopyAction_2);
        setId(ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, COPY_ACTION);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        lastSelection = selection;
        boolean bRet = super.updateSelection(selection);
        if (bRet) {
            bRet = false;
            if (selection != null && selection.size() > 0) {
                List selectionList = selection.toList();

                // Validate the selection.
                bRet = validateSelection(selectionList);

                if (bRet) {
                    List elementsToCopy = getElementsToCopy(selectionList);

                    if (elementsToCopy != null && !elementsToCopy.isEmpty()) {
                        // Get copy command
                        commandContainer = getCommandContainer(elementsToCopy);

                        // Check the command
                        if (commandContainer == null) {
                            bRet = false;
                        } else if (!commandContainer.getCommand().canExecute()) {
                            bRet = false;
                        }
                    } else {
                        bRet = false;
                    }

                    // If not successfull then clear command
                    if (!bRet) {
                        commandContainer = null;
                    }

                }
            }
        }

        return bRet;
    }

    /**
     * @param selection
     * @return final list of objects to pass to ActionUtil to get copy command
     *         for.
     */
    protected List getElementsToCopy(List selectionList) {
        return selectionList;
    }

    /**
     * @param selectionList
     * @return True if the list of selection objects is valid for copy to
     *         clipboard.
     */
    protected boolean validateSelection(List selectionList) {
        // Make sure the selection is of the same object types
        boolean bRet =
                ActionUtil.allEObjects(selectionList)
                        && ActionUtil.allObjectsOfSameType(selectionList);

        if (bRet) {
            // Make sure a logical node hasn't been selected
            bRet = !ActionUtil.isLogicalNodeInList(selectionList);

            if (bRet) {

                // Make sure the list doesn't contain a package
                bRet =
                        !ActionUtil.isClassTypeInList(selectionList,
                                Package.class);

                if (bRet) {
                    // Make sure all objects belong to the same parent
                    bRet = ActionUtil.eObjectsWithSameParent(selectionList);
                }
            }
        }
        return bRet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        if (lastSelection != null) {
            updateSelection(lastSelection);
        }
        if (commandContainer != null) {
            commandContainer.executeCommand();
        }
    }

    /**
     * This method creates CopyCommand and returns it for selected EObject.
     * 
     * @param eObjectList
     * @return <code>CommandContainer</code> if successful in creating copy
     *         command, <code>null</code> otherwise.
     */
    protected CommandContainer getCommandContainer(List eObjectList) {
        if (eObjectList.size() < 1) {
            return null;
        }
        Iterator iter = eObjectList.iterator();
        EObject eObject = (EObject) iter.next();
        EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(eObject);
        if (editingDomain != null) {

            // Delay actual building of the copy command until the user actually
            // wants to use it as it can take a while to build!
            CommandContainer container =
                    new CommandContainer(editingDomain,
                            new DelayTillExecCopyCommand(editingDomain,
                                    eObjectList));
            return container;
        }
        return null;
    }

    private class DelayTillExecCopyCommand extends
            AbstractWaitTillExecutionCommand {
        List eObjectList;

        public DelayTillExecCopyCommand(EditingDomain editingDomain,
                List eObjectList) {
            super(editingDomain);
            this.eObjectList = eObjectList;
        }

        @Override
        protected Command createCommand(EditingDomain editingDomain) {
            //
            // We have to be a little more clever about how we copy certain
            // objects.
            // For instance if we are copying processes then we must also copy
            // the pools and lanes that are used by those processes.
            //
            Command cmd = UnexecutableCommand.INSTANCE;

            Shell activeShell = Display.getDefault().getActiveShell();
            Cursor oldCursor = null;
            if (activeShell != null) {
                oldCursor = activeShell.getCursor();
                activeShell.setCursor(Cursors.WAIT);
            }

            try {

                Collection<EObject> copyList =
                        ActionUtil.getCopyElements(editingDomain,
                                eObjectList,
                                true);
                if (copyList != null && copyList.size() > 0) {
                    return new XPDCopyToClipboardCommand(editingDomain,
                            copyList,
                            XPDCopyToClipboardCommand
                                    .calculateSourceContextObject(eObjectList));
                }

            } finally {
                if (activeShell != null) {
                    activeShell.setCursor(oldCursor);
                }
            }
            return cmd;
        }

    }

}
