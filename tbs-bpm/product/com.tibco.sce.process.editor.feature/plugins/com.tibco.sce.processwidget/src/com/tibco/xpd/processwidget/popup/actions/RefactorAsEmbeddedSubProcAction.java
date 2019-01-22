/**
 * RefactorAsEmbeddedSubProcAction.java
 *
 * Action that refactors selected objects as an embedded sub-proc.
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.commands.internal.EmbSubProcOptimiseSizeCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.DataObjectEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.NoteEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * Action that refactors selected objects into independent sub-process.
 */
public class RefactorAsEmbeddedSubProcAction implements IActionDelegate {

    private EditingDomain editingDomain = null;

    private Set<Object> selectedObjects = null;

    private Set<BaseGraphicalEditPart> selectedEditParts = null;

    private ProcessDiagramAdapter processAdapter = null;

    private EditPartViewer editPartViewer = null;

    /**
     * Action that refactors selected objects into embedded sub-process.
     */
    public RefactorAsEmbeddedSubProcAction() {

    }

    @Override
    public void run(IAction action) {
        if (editingDomain != null && processAdapter != null
                && selectedObjects != null && selectedObjects.size() > 0) {

            ProcessWidgetImpl widget =
                    (ProcessWidgetImpl) selectedEditParts.iterator().next()
                            .getViewer()
                            .getProperty(ProcessWidgetConstants.PROP_WIDGET);

            List<Object> objs = new ArrayList<Object>(selectedObjects.size());
            for (Iterator iter = selectedObjects.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                objs.add(obj);
            }

            CreateAccessibleObjectCommand refactorCmd =
                    getRefactorCommand(objs, widget);

            if (refactorCmd != null) {
                Command cmd = new RefactorAndOptimiseSizeCommand(refactorCmd);

                editingDomain.getCommandStack().execute(cmd);

            }
        }
    }

    /**
     * Get the refactor command. Override this method in sub-classes to return
     * appropriate refactor commands.
     * 
     * @param widget
     * @param objs
     * @return Refactor Command.
     */
    protected CreateAccessibleObjectCommand getRefactorCommand(
            List<Object> objs, ProcessWidgetImpl widget) {

        return processAdapter
                .getRefactorAsEmbeddedSubProcCommand(editingDomain,
                        objs,
                        widget);
    }

    /**
     * @return Editing Domain
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * @return Process Adapter.
     */
    protected ProcessDiagramAdapter getProcessAdapter() {
        return processAdapter;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        selectedObjects = null;
        selectedEditParts = null;

        editingDomain = null;
        processAdapter = null;
        editPartViewer = null;

        if (!selection.isEmpty()) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            // Disclude objects parented by embedded sub-proces that are also
            // selected.
            List parentsOnlyList =
                    ToolUtilities.getSelectionWithoutDependants(sel.toList());

            selectedObjects = new HashSet<Object>(sel.size());
            selectedEditParts = new HashSet<BaseGraphicalEditPart>(sel.size());

            for (Iterator iter = parentsOnlyList.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (obj instanceof BaseFlowNodeEditPart
                        || obj instanceof DataObjectEditPart
                        || obj instanceof NoteEditPart) {

                    BaseGraphicalEditPart gep = (BaseGraphicalEditPart) obj;

                    selectedObjects.add(gep.getModel());
                    selectedEditParts.add(gep);

                    if (gep instanceof TaskEditPart) {
                        // Make sure that task border attached events are
                        // selected
                        Collection<EventEditPart> events =
                                ((TaskEditPart) gep).getAttachedEvents();

                        selectedEditParts.addAll(events);

                        for (Iterator iterator = events.iterator(); iterator
                                .hasNext();) {
                            EventEditPart evEP =
                                    (EventEditPart) iterator.next();

                            selectedObjects.add(evEP.getModel());
                        }
                    }

                    if (editingDomain == null) {
                        editingDomain = gep.getEditingDomain();
                    }

                    if (processAdapter == null) {
                        ProcessEditPart pep = gep.getParentProcess();

                        processAdapter =
                                (ProcessDiagramAdapter) gep
                                        .getAdapterFactory()
                                        .adapt(pep.getModel(),
                                                ProcessWidgetConstants.ADAPTER_TYPE);
                    }

                    if (editPartViewer == null) {
                        editPartViewer = gep.getViewer();
                    }

                } else {
                    System.err
                            .println("Refactor Emb Sub-Proc: popup menu extension is allowing invalid object selection."); //$NON-NLS-1$
                }
            }

            if (selectedObjects.size() > 0) {
                action.setEnabled(true);
            } else {
                action.setEnabled(false);
            }
        }
    }

    /**
     * RefactorAndOptimiseSizeCommand
     * 
     * Class that wraps up the behaviour of a refactor to sub-proc followed by a
     * set optimum size. This means that the adpater's refactor command doesn't
     * have to worry about laying out the new subproc.
     */
    private class RefactorAndOptimiseSizeCommand extends AbstractCommand {
        CreateAccessibleObjectCommand refactorCmd = null;

        Command optimiseSizeCommand = null;

        /**
         * 
         */
        public RefactorAndOptimiseSizeCommand(
                CreateAccessibleObjectCommand refactorCmd) {
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
            refactorCmd.execute();

            // Then optimise the embedded sub-proc size.
            EditPart ep =
                    (EditPart) editPartViewer.getEditPartRegistry()
                            .get(refactorCmd.getCreatedNode());

            if (ep instanceof TaskEditPart) {
                optimiseSizeCommand =
                        new EmbSubProcOptimiseSizeCommand((TaskEditPart) ep);

                if (optimiseSizeCommand.canExecute()) {
                    optimiseSizeCommand.execute();
                } else {
                    optimiseSizeCommand = null;
                }

            } else {
                System.err
                        .println("RefactorAndOptimiseSizeCommand: new subproc edit part does not exist."); //$NON-NLS-1$
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
            if (optimiseSizeCommand != null) {
                optimiseSizeCommand.undo();
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

            if (optimiseSizeCommand != null) {
                optimiseSizeCommand.redo();
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.common.command.AbstractCommand#getAffectedObjects()
         */
        @Override
        public Collection<?> getAffectedObjects() {
            return refactorCmd.getAffectedObjects();
        }

    }

    /**
     * @return Set of objects selected for refactor.
     */
    public Set<Object> getSelectedObjects() {
        return selectedObjects;
    }
}
