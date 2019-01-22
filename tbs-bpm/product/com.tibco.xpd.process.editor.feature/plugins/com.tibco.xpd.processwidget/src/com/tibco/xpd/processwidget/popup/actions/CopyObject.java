/**
 * CopyObject.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */

package com.tibco.xpd.processwidget.popup.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.commands.internal.CopyCutClipboardCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * @author wzurek
 */
public class CopyObject implements IActionDelegate {

    Command cmd = UnexecutableCommand.INSTANCE;

    /*
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        // Copy to clipboard should NOT go thru the undo command stack!

        if (cmd != null && cmd.canExecute()) {
            cmd.execute();
        }
        cmd = UnexecutableCommand.INSTANCE;
    }

    /*
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        boolean success = true;

        if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            cmd = UnexecutableCommand.INSTANCE;

            if (sel.getFirstElement() instanceof ModelAdapterEditPart) {
                // Get the stuff we can get from any old edit part
                // that's still there.
                ModelAdapterEditPart ep = (ModelAdapterEditPart) sel
                        .getFirstElement();

                ProcessWidgetImpl widget = (ProcessWidgetImpl) ep.getViewer()
                        .getProperty(ProcessWidgetConstants.PROP_WIDGET);

                cmd = new CopyCutClipboardCommand(sel.toList(), widget, false);

                if (!cmd.canExecute()) {
                    success = false;
                }
            }
        }

        if (success) {
            action.setEnabled(true);
        } else {
            action.setEnabled(false);
            cmd = UnexecutableCommand.INSTANCE;
        }
    }
}
