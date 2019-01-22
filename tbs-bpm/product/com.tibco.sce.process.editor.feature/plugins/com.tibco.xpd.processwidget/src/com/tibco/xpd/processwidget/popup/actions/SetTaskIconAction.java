/**
 * SetFillColorAction.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.resources.ui.util.ImagePicker;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * SetTaskIconAction
 * 
 * 
 * @author aallway
 * @since 3.3 (2 Oct 2009)
 */
public class SetTaskIconAction extends ActionDelegate {
    boolean cmd_ok = false;

    List<TaskEditPart> selectionList = null;

    private EditingDomain editingDomain;

    @Override
    public void run(IAction action) {
        if (editingDomain != null && cmd_ok && selectionList.size() > 0) {
            Command cmd = pickTaskIcon(editingDomain, selectionList);
            if (cmd != null) {
                /* And finally execute the command. */
                editingDomain.getCommandStack().execute(cmd);
            }
        }

        return;
    }

    public static Command pickTaskIcon(EditingDomain editingDomain,
            List<TaskEditPart> editParts) {
        CompoundCommand cmd = null;

        if (!editParts.isEmpty()) {
            TaskEditPart ep = editParts.get(0);

            TaskAdapter adp = ep.getActivityAdapter();
            if (adp != null && ep.getModel() instanceof EObject) {

                EObject model = (EObject) ep.getModel();
                IProject project = WorkingCopyUtil.getProjectFor(model);
                if (project != null) {

                    ImagePicker picker =
                            new ImagePicker(PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getShell());

                    picker.setInput(project);

                    String curIconPath = adp.getTaskIcon();
                    if (curIconPath != null && curIconPath.length() > 0) {
                        IFile curIcon = project.getFile(new Path(curIconPath));
                        picker.setInitialSelection(curIcon);
                    }

                    if (picker.open() == picker.OK) {
                        Object pick = picker.getResult()[0];
                        if (pick instanceof IFile) {

                            cmd = new CompoundCommand();

                            for (Object o : editParts) {
                                TaskAdapter adp2 =
                                        ((TaskEditPart) o).getActivityAdapter();

                                cmd.append(adp2
                                        .getSetTaskIconCommand(editingDomain,
                                                ((IFile) pick)
                                                        .getProjectRelativePath()
                                                        .toString()));
                            }

                        }
                    }
                }
            }
        }

        return cmd;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        cmd_ok = true;

        if (selectionList == null) {
            selectionList = new ArrayList<TaskEditPart>();
        } else {
            selectionList.clear();
        }

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {

                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof TaskEditPart) {
                        TaskEditPart taskEP = (TaskEditPart) obj;
                        if (taskEP.isEmbeddedSubProc()
                                && !taskEP.isCollapsedEmbeddedSubproc()) {
                            cmd_ok = false;
                            break;
                        }

                        Object adp = taskEP.getModelAdapter();

                        if (!(adp instanceof TaskAdapter)) {
                            cmd_ok = false;
                            break;
                        } else {
                            // It's ok, add to list of selections.
                            selectionList.add((TaskEditPart) obj);
                            editingDomain = taskEP.getEditingDomain();
                        }
                    } else {
                        cmd_ok = false;
                        break;
                    }
                }
            }
        } else {
            cmd_ok = false;
        }

        // If everything is ok create the command to change the transactional
        // status of the selected subprocess activity(s)
        if (cmd_ok) {
            action.setEnabled(true);
        } else {
            action.setEnabled(false);
            selectionList.clear();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionDelegate#dispose()
     */
    @Override
    public void dispose() {
        if (selectionList != null) {
            selectionList.clear();
            selectionList = null;
        }

        editingDomain = null;

        super.dispose();
    }

}
