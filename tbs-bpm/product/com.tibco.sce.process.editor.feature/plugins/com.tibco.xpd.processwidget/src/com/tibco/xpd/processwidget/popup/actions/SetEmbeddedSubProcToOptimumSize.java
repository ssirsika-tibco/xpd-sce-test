/**
 * SetFillColorAction.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.commands.internal.EmbSubProcOptimiseSizeCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * SetEmbeddedSubProcToOptimumSize
 * 
 */
public class SetEmbeddedSubProcToOptimumSize extends ActionDelegate {

    private EditingDomain editingDomain;

    TaskEditPart embeddedSubprocEP = null;

    public void run(IAction action) {
        if (embeddedSubprocEP != null && editingDomain != null) {

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.SetEmbeddedSubProcToOptimumSize_menu);

            appendOptimumSizeCommand(cmd, embeddedSubprocEP);

            // And finally execute the command.
            editingDomain.getCommandStack().execute(cmd);

        }
    }

    /**
     * We only need to perform an optimise size on the lowest level child
     * embedded sub-processes. Each of these will naturally optimise their
     * parent if necessary.
     * 
     * @param editingDomain2
     * @param mainCmd
     * @param embeddedSubprocEP2
     */
    private void appendOptimumSizeCommand(CompoundCommand mainCmd,
            TaskEditPart taskEP) {
        boolean recursed = false;

        // First of all, go thru the chil nodes recursing into any embedded
        // subflows to set their size FIRST - so that the size of this one will
        // be calculated correctly.
        List children = taskEP.getChildren();
        if (children != null) {
            for (Iterator iter = children.iterator(); iter.hasNext();) {
                AbstractEditPart child = (AbstractEditPart) iter.next();

                if (child instanceof TaskEditPart) {
                    TaskEditPart subTaskEP = (TaskEditPart) child;

                    if (subTaskEP.isEmbeddedSubProc()) {
                        // RECURS!
                        appendOptimumSizeCommand(mainCmd, subTaskEP);
                        recursed = true;
                    }
                }
            }
        }

        // Now add our own resize command - but only if we did not recurs.
        if (!recursed) {
            mainCmd.append(new EmbSubProcOptimiseSizeCommand(taskEP));
        }

    }

    public void selectionChanged(IAction action, ISelection selection) {

        embeddedSubprocEP = null;
        editingDomain = null;

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            if (sel.size() == 1) {

                Object obj = sel.getFirstElement();

                if (obj instanceof TaskEditPart) {
                    TaskEditPart taskEP = (TaskEditPart) obj;

                    if (taskEP.isEmbeddedSubProc()) {

                        // Only allow on top level embedded sub-proc
                        // (looks strange to user if mid-level one is done
                        // because, although the task will be sized, it will
                        // still end up being zoomed within it's parent).
                        if (taskEP.getChildren().size() > 0) {
                            embeddedSubprocEP = taskEP;
                            editingDomain = taskEP.getEditingDomain();
                        }
                    }
                }
            }
        }

        // If everything is ok create the command to change the transactional
        // status of the selected subprocess activity(s)
        if (embeddedSubprocEP != null) {
            action.setEnabled(true);
        } else {
            action.setEnabled(false);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionDelegate#dispose()
     */
    public void dispose() {
        embeddedSubprocEP = null;

        editingDomain = null;

        super.dispose();
    }

}
