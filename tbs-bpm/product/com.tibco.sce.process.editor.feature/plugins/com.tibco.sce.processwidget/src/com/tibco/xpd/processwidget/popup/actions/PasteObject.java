/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.popup.actions;

import java.util.Collection;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.actions.CutCopyPasteActionHandler;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.commands.internal.XPDPasteCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;

/**
 * @author wzurek
 */
public class PasteObject implements IActionDelegate {

    Command cmd = UnexecutableCommand.INSTANCE;

    private EditingDomain ed;

    /*
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run(IAction action) {
        if (ed != null && cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);

            // Select the model objects.
            if (cmd instanceof XPDPasteCommand) {
                IEditorPart edit =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage().getActiveEditor();
                ProcessWidget widget =
                        (ProcessWidget) edit.getAdapter(ProcessWidget.class);
                if (widget instanceof ProcessWidgetImpl) {
                    ((ProcessWidgetImpl) widget)
                            .setSelFromPastedObjects((XPDPasteCommand) cmd);
                }
            }

        }
        cmd = UnexecutableCommand.INSTANCE;
        ed = null;
    }

    /*
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                Object obj = sel.getFirstElement();
                if (obj instanceof ModelAdapterEditPart) {
                    ModelAdapterEditPart ep = (ModelAdapterEditPart) obj;
                    Object target = ep.getModel();
                    EditPartViewer viewer = ep.getViewer();

                    ModelAdapterEditPart processEP =
                            (ModelAdapterEditPart) viewer.getContents();
                    ProcessDiagramAdapter diag =
                            (ProcessDiagramAdapter) processEP.getModelAdapter();
                    ed = processEP.getEditingDomain();

                    Point location =
                            (Point) viewer
                                    .getProperty(ProcessWidgetConstants.VIEWPROP_FLOWCONTAINER_LASTCLICKPOS);

                    ModelAdapterEditPart targetContainer = null;
                    if (ep instanceof SequenceFlowEditPart) {
                        // If this is a paste into a sequence flow then the
                        // SequenceFlowLayoutEditPolicy should have saved the
                        // container into
                        // which we should really paste the objects.
                        targetContainer =
                                (ModelAdapterEditPart) viewer
                                        .getProperty(ProcessWidgetConstants.VIEWPROP_SEQFLOW_LASTCLICKFLOWCONTAINER);
                    }

                    if (CutCopyPasteActionHandler
                            .isClipboardContentSuitableForTargetContext((ProcessWidgetImpl) viewer
                                    .getProperty(ProcessWidgetConstants.PROP_WIDGET))) {
                        Collection clipboardObjects =
                                ProcessClipboardUtils.getClipboard();

                        cmd =
                                new XPDPasteCommand(ed, diag, ep, location,
                                        targetContainer, clipboardObjects);

                        action.setEnabled(cmd.canExecute());
                        return;
                    }
                }
            }
        }
        action.setEnabled(false);
        ed = null;
        cmd = null;
    }
}
