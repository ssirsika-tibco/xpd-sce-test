/**
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectsCommand;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.commands.internal.MakeSpaceInParentCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * Action that refactors content of sub-process in place of a task that invokes
 * it.
 * 
 * @author aallway
 */
public class RefactorIndiSubInlineAction implements IActionDelegate {

    private EditingDomain editingDomain = null;

    private TaskEditPart subProcTaskEditPart = null;

    private BaseGraphicalEditPart subProcTaskParent = null;

    private TaskAdapter taskAdapter = null;

    private EditPartViewer editPartViewer = null;

    /**
     * Action that refactors selected objects into independent sub-process.
     */
    public RefactorIndiSubInlineAction() {

    }

    @Override
    public void run(IAction action) {
        if (editingDomain != null && taskAdapter != null
                && subProcTaskEditPart != null) {

            Shell shell = new Shell(Display.getCurrent());

            ProcessWidgetImpl callingProcessWidget =
                    (ProcessWidgetImpl) subProcTaskEditPart.getViewer()
                            .getProperty(ProcessWidgetConstants.PROP_WIDGET);

            ProcessWidgetImpl subProcessWidget =
                    getSubProcessWidget(callingProcessWidget, shell);

            CreateAccessibleObjectsCommand refactorCmd =
                    taskAdapter
                            .getInlineSubProcessContentCommand(editingDomain,
                                    callingProcessWidget,
                                    subProcessWidget);

            if (refactorCmd != null) {
                doIt(refactorCmd);
            }

            if (!shell.isDisposed()) {
                shell.dispose();
            }
        }
    }

    private ProcessWidgetImpl getSubProcessWidget(
            ProcessWidgetImpl callingProcessWidget, Shell shell) {
        EObject process = taskAdapter.getSubprocess();

        ProcessWidgetImpl pWidget =
                new ProcessWidgetImpl(false,
                        callingProcessWidget.getProcessWidgetType());

        pWidget.setEditingDomain(editingDomain);
        pWidget.setAdapterFactory(callingProcessWidget.getAdapterFactory());
        pWidget.setPreferences(ProcessWidgetPlugin.getDefault()
                .getPluginPreferences());

        /* Input must now be set before control create. */
        pWidget.setInput(process);
        pWidget.createControl(shell);

        return pWidget;
    }

    private void doIt(CreateAccessibleObjectsCommand refactorCmd) {

        if (refactorCmd.canExecute()) {

            RefactorAndLayoutCommand cmd =
                    new RefactorAndLayoutCommand(refactorCmd);

            editingDomain.getCommandStack().execute(cmd);

        }

        return;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        taskAdapter = null;
        subProcTaskEditPart = null;
        subProcTaskParent = null;

        editingDomain = null;
        editPartViewer = null;

        if (!selection.isEmpty()
                && ((IStructuredSelection) selection).size() == 1) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            Object obj = sel.getFirstElement();

            if (obj instanceof TaskEditPart) {
                subProcTaskEditPart = (TaskEditPart) obj;
                taskAdapter = subProcTaskEditPart.getActivityAdapter();
                subProcTaskParent =
                        (BaseGraphicalEditPart) subProcTaskEditPart.getParent();
                editPartViewer = subProcTaskEditPart.getViewer();
                editingDomain = subProcTaskEditPart.getEditingDomain();
            } else {
                System.err
                        .println("Refactor Emb Sub-Proc: popup menu extension is allowing invalid object selection."); //$NON-NLS-1$
            }

            if (subProcTaskEditPart != null && taskAdapter != null
                    && subProcTaskParent != null && editPartViewer != null
                    && taskAdapter.isInlineEnabled()) {
                action.setEnabled(true);
            } else {
                action.setEnabled(false);
            }
        }
    }

    /**
     * RefactorAndLayoutCommand
     * 
     * Class that wraps up the behaviour of a refactor to sub-proc followed by a
     * resize to normal task size and shuffling objects around the refactored
     * sub-proc to account for space no longer used.
     */
    private class RefactorAndLayoutCommand extends AbstractCommand {
        CreateAccessibleObjectsCommand refactorCmd = null;

        Command adjustLayoutCommand = null;

        /**
         * 
         */
        public RefactorAndLayoutCommand(
                CreateAccessibleObjectsCommand refactorCmd) {
            this.refactorCmd = refactorCmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#getLabel()
         */
        @Override
        public String getLabel() {
            return refactorCmd.getLabel();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#execute()
         */
        @Override
        public void execute() {
            // On execute, execute the refactor command first.
            if (refactorCmd.canExecute()) {
                refactorCmd.execute();

                // Get the edit parts for just created objects (not bothered
                // about
                // connection type nodes.
                List<BaseGraphicalEditPart> inlinedEditParts =
                        new ArrayList<BaseGraphicalEditPart>();

                Map editPartRegistry = editPartViewer.getEditPartRegistry();

                for (EObject obj : refactorCmd.getCreatedObjects()) {
                    Object ep = editPartRegistry.get(obj);

                    if (ep instanceof BaseGraphicalEditPart) {
                        inlinedEditParts.add((BaseGraphicalEditPart) ep);
                    }
                }

                if (inlinedEditParts.size() > 0) {

                    //
                    // Make space for the new sub-proc content in parent.
                    Rectangle contentBounds = null;

                    for (BaseGraphicalEditPart gep : inlinedEditParts) {
                        Rectangle bnds = EditPartUtil.getModelBounds(gep);

                        if (contentBounds == null) {
                            contentBounds = bnds.getCopy();
                        } else {
                            contentBounds.union(bnds);
                        }
                    }

                    adjustLayoutCommand =
                            new MakeSpaceInParentCommand(editingDomain,
                                    contentBounds, subProcTaskParent,
                                    ProcessWidgetConstants.TASK_WIDTH_SIZE / 2,
                                    ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                    inlinedEditParts);

                    adjustLayoutCommand.execute();

                } else {
                    System.err
                            .println("RefactorIndiSubInlineAction: inlined subproc content edit parts do not exist."); //$NON-NLS-1$
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
         */
        @Override
        protected boolean prepare() {
            return refactorCmd.canExecute();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         */
        @Override
        public void undo() {
            if (adjustLayoutCommand != null) {
                adjustLayoutCommand.undo();
            }

            refactorCmd.undo();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#redo()
         */
        @Override
        public void redo() {
            refactorCmd.redo();

            if (adjustLayoutCommand != null) {
                adjustLayoutCommand.redo();
            }

        }

    }

}
