/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.Cursors;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.CopyAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.commands.AbstractWaitTillExecutionCommand;

/**
 * GSD copy action.
 * 
 * @author sajain
 * @since Feb 17, 2015
 */
public class GSDCopyAction extends CopyAction {

    /**
     * GSD Copy action.
     */
    public GSDCopyAction() {

        super();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.CopyAction#getCommandContainer(java.util.List)
     * 
     * @param eObjectList
     * @return
     */
    @Override
    protected CommandContainer getCommandContainer(List eObjectList) {

        if (eObjectList.size() < 1) {
            return null;
        }

        Iterator iter = eObjectList.iterator();

        EObject eObject = (EObject) iter.next();

        EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(eObject);

        if (editingDomain != null) {

            /*
             * Delay actual building of the copy command until the user actually
             * wants to use it as it can take a while to build!
             */
            CommandContainer container =
                    new CommandContainer(editingDomain,
                            new DelayTillExecCopyCommand(editingDomain,
                                    eObjectList));

            return container;
        }

        return null;
    }

    /**
     * Command to delay copy of selected objects to the clipboard until copy is
     * executed.
     * 
     * @author sajain
     * @since Feb 25, 2015
     */
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

            Command cmd = UnexecutableCommand.INSTANCE;

            Shell activeShell = Display.getDefault().getActiveShell();
            Cursor oldCursor = null;

            if (activeShell != null) {

                oldCursor = activeShell.getCursor();
                activeShell.setCursor(Cursors.WAIT);
            }

            try {

                Collection<EObject> copyList =
                        getCopyElements(editingDomain, eObjectList, true);

                if (copyList != null && copyList.size() > 0) {

                    return new GSDCopyToClipboardCommand(editingDomain,
                            copyList,
                            GSDCopyToClipboardCommand
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

    /**
     * Return the actual list of elements required for the given set of selected
     * eobjects.
     * <p>
     * The actual list may need extra supporting elements (such as Pools for the
     * processes etc)
     * 
     * @param editingDomain
     * 
     * @param objectList
     * @param wantReferencedProjectEntries
     * @return
     */
    public static Collection<EObject> getCopyElements(
            EditingDomain editingDomain, Collection objectList,
            boolean wantReferencedProjectEntries) {

        IProject project = null;

        /*
         * Final copy list.
         */
        Set<EObject> copyList = new HashSet<EObject>(objectList.size());

        for (Iterator iter = objectList.iterator(); iter.hasNext();) {

            Object obj = iter.next();

            if (obj instanceof GlobalSignal) {

                copyList.add((EObject) obj);

                if (project == null) {
                    project = WorkingCopyUtil.getProjectFor((EObject) obj);
                }

            } else if (obj instanceof PayloadDataField) {

                if (project == null) {
                    project = WorkingCopyUtil.getProjectFor((EObject) obj);
                }

                copyList.add((EObject) obj);

            }
        }

        /*
         * Create copies of the objects
         */
        Command cmd = CopyCommand.create(editingDomain, copyList);

        if (!cmd.canExecute()) {
            return null;
        }

        cmd.execute();

        Collection copied = cmd.getResult();

        if (wantReferencedProjectEntries) {

            if (project != null) {
                copied.addAll(ActionUtil
                        .getExternalProjectReferencesForObjects(copyList,
                                project));
            }
        }

        return copied;
    }

    /**
     * @param selectionList
     * @return True if the list of selection objects is valid for copy to
     *         clipboard.
     */
    @Override
    protected boolean validateSelection(List selectionList) {
        // Make sure the selection is of the same object types
        // TODO
        // boolean bRet =
        // ActionUtil.allEObjects(selectionList)
        // && ActionUtil.allObjectsOfSameType(selectionList);

        // if (bRet) {
        // Make sure a logical node hasn't been selected
        boolean bRet = !ActionUtil.isLogicalNodeInList(selectionList);

        if (bRet) {

            // Make sure the list doesn't contain a package
            bRet = !ActionUtil.isClassTypeInList(selectionList, Package.class);

            if (bRet) {
                // Make sure all objects belong to the same parent
                bRet = ActionUtil.eObjectsWithSameParent(selectionList);
            }
        }
        // }
        return bRet;
    }

}
