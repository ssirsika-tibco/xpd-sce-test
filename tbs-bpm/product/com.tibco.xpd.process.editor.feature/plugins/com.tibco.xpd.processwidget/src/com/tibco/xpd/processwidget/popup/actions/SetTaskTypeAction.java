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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.commands.ChangeTaskTypeCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * 
 * 
 * @author wzurek
 */
public class SetTaskTypeAction implements IActionDelegate {

    private final TaskType taskType;

    private List taskAdapters;

    private List<EditPart> editParts;

    private EditPartViewer viewer;

    /**
     * Use one the static subclasses for specific type
     */
    private SetTaskTypeAction(TaskType type) {
        super();
        this.taskType = type;
    }

    /*
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // do nothing
    }

    /*
     * @see IActionDelegate#run(IAction)
     */
    @Override
    public void run(IAction action) {
        if (taskAdapters != null) {
            TaskType newTaskType;
            if (action.isChecked()) {
                newTaskType = taskType;
            } else {
                newTaskType = TaskType.NONE_LITERAL;
            }

            if (taskAdapters.size() > 0) {
                EObject eo =
                        (EObject) ((TaskAdapter) taskAdapters.get(0))
                                .getTarget();
                IEditingDomainProvider ep =
                        (IEditingDomainProvider) EcoreUtil
                                .getExistingAdapter(eo.eResource(),
                                        IEditingDomainProvider.class);
                EditingDomain ed = ep.getEditingDomain();
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.SetTaskTypeAction_menu);

                for (Iterator iter = taskAdapters.iterator(); iter.hasNext();) {
                    TaskAdapter taskAdapter = (TaskAdapter) iter.next();

                    cmd.append(ChangeTaskTypeCommand.create(ed,
                            (EObject) taskAdapter.getTarget(),
                            newTaskType));
                }

                ed.getCommandStack().execute(cmd);

                //
                // Sid:
                // There can be a problem (not of our making) with
                // property sheets for the selected object (say a task).
                //
                // If there are different properties tabs for the new
                // task type then the General Tab normally detects the
                // change in task type and performs a refreshTabs().
                //
                // However if the last tab selected for a task was NOT
                // general and the task is deselected and reselected
                // then the general section is not necessarily reloaded.
                //
                // This means that when we change the typ[e the tabs
                // don't get changed. Therefore in order to force a
                // refresh we will perform force a reselect of the edipt
                // part which should filter up thru the system.

                // In order for selection change not to be ignored we
                // have to unset and reset the selection.
                viewer.getSelectionManager()
                        .setSelection(new StructuredSelection());
                viewer.getSelectionManager()
                        .setSelection(new StructuredSelection(
                                editParts.toArray()));
            }
        }
    }

    /*
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {
                taskAdapters = new ArrayList();
                editParts = new ArrayList<EditPart>();

                TaskType tt = null;

                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof TaskEditPart) {
                        viewer = ((EditPart) obj).getViewer();

                        TaskAdapter adapter =
                                (TaskAdapter) ((TaskEditPart) obj)
                                        .getModelAdapter();

                        tt = adapter.getTaskType();

                        if (!taskType.equals(tt)) {
                            taskAdapters.add(adapter);
                            editParts.add((EditPart) obj);
                        }

                    } else {
                        taskAdapters = null;
                        action.setEnabled(false);
                        action.setChecked(false);
                        return;
                    }
                }

                action.setEnabled(true);

                // If there are multiple selections then don't check any.
                if (sel.size() > 1) {
                    action.setChecked(false);
                } else if (tt != null) {
                    action.setChecked(taskType.equals(tt));
                }
                return;
            }
        }
        action.setEnabled(false);
        action.setChecked(false);
    }

    /**
     * ================================================================
     * <p>
     * Set to specific task type actions...
     * </p>
     * ================================================================
     */

    public static class UnspecifiedTask extends SetTaskTypeAction {
        public UnspecifiedTask() {
            super(TaskType.NONE_LITERAL);
        }
    }

    public static class UserTask extends SetTaskTypeAction {
        public UserTask() {
            super(TaskType.USER_LITERAL);
        }
    }

    public static class ManualTask extends SetTaskTypeAction {
        public ManualTask() {
            super(TaskType.MANUAL_LITERAL);
        }
    }

    public static class ServiceTask extends SetTaskTypeAction {
        public ServiceTask() {
            super(TaskType.SERVICE_LITERAL);
        }
    }

    public static class ScriptTask extends SetTaskTypeAction {
        public ScriptTask() {
            super(TaskType.SCRIPT_LITERAL);
        }
    }

    public static class SendTask extends SetTaskTypeAction {
        public SendTask() {
            super(TaskType.SEND_LITERAL);
        }
    }

    public static class ReceiveTask extends SetTaskTypeAction {
        public ReceiveTask() {
            super(TaskType.RECEIVE_LITERAL);
        }
    }

    public static class ReferenceTask extends SetTaskTypeAction {
        public ReferenceTask() {
            super(TaskType.REFERENCE_LITERAL);
        }
    }

    public static class SubProcessTask extends SetTaskTypeAction {
        public SubProcessTask() {
            super(TaskType.SUBPROCESS_LITERAL);
        }
    }

    public static class EmbeddedSubProcessTask extends SetTaskTypeAction {
        public EmbeddedSubProcessTask() {
            super(TaskType.EMBEDDED_SUBPROCESS_LITERAL);
        }
    }

    /**
     * Set task type to event subprocess.
     * 
     * @author sajain
     * @since Aug 12, 2014
     */
    public static class EventSubProcessTask extends SetTaskTypeAction {
        public EventSubProcessTask() {
            super(TaskType.EVENT_SUBPROCESS_LITERAL);
        }
    }

}
