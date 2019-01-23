/**
 * 
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * @author wzurek
 * 
 */
public class ChangeParticipantAction implements IActionDelegate {

    private TaskAdapter taskAdapter;

    /**
     * 
     */
    public ChangeParticipantAction() {
        super();
    }

    public void run(IAction action) {

        IEditorPart edit = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor();
        ProcessWidget widget = (ProcessWidget) edit
                .getAdapter(ProcessWidget.class);
        EditingDomain ed = (EditingDomain) edit.getAdapter(EditingDomain.class);
        DataFilterPicker picker =
                new DataFilterPicker(widget.getSite().getShell(),
                        DataPickerType.PARTICIPANTS, true);        
        Object[] newTarget = null;
        if (widget.getInput() != null) {
            picker.setScope(widget.getInput());
            if (taskAdapter != null) {
                picker
                        .setInitialSelections(taskAdapter
                                .getActivityPerformers());
            }
            if (picker.open() == DataPicker.OK) {
                newTarget = picker.getResult();
            }
        }
		if (newTarget != null) {
            Command cmd;

            List<EObject> perfs = new ArrayList<EObject>();
			for (int i = 0; i < newTarget.length; i++) {
				if (newTarget[i] instanceof EObject) {
					perfs.add((EObject)newTarget[i]);
				}
			}
			cmd = taskAdapter.getSetPerformersCommand(ed,
					perfs.toArray(new EObject[0]));
			if (cmd != null && cmd.canExecute()) {
			    ed.getCommandStack().execute(cmd);
			}
		}

    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                Object obj = sel.getFirstElement();
                if (obj instanceof TaskEditPart) {
                    taskAdapter = (TaskAdapter) ((TaskEditPart) obj)
                            .getModelAdapter();
                    //action.setText("Change participant...");
                    action.setEnabled(true);
                    return;
                }
            }
        }
        taskAdapter = null;
        action.setEnabled(false);
    }
}
