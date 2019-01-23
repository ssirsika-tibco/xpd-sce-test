/**
 * SetTaskNameCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * <b>SetTaskNameCommand</b>
 * <p>
 * Wrapper for change task name command.
 * </p>
 * <p>
 * This not only changes the task type but also...
 * </p>
 * <li>If the task is a referenced by a reference task then the reference task
 * is renamed if it still has default name given to it when reference was set
 * ("Reference: <task name>")</li>
 * 
 */
public class SetTaskNameCommand {

    public static Command create(EditingDomain editingDomain,
            EObject activityObject, String newName) {

        TaskAdapter ta = ProcessWidgetCommandUtil.getTaskAdapter(activityObject);
        if (ta == null) {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand ccmd = new CompoundCommand();

        ccmd.setLabel(Messages.SetTaskNameCommand_menu);


        ccmd.append(ta.getSetNameCommand(editingDomain, newName));

        String currDefaultName = Messages.SetTaskNameCommand_Reference_label
                + ta.getName();
        String currDefaultNameWithSpace = Messages.SetTaskNameCommand_Reference_label
                + " " + ta.getName(); //$NON-NLS-1$

        // Look for tasks that reference this task.
        Collection<EObject> refTasks = ta.getReferencingTasks();

        for (EObject eo : refTasks) {
            TaskAdapter otherTaskAdp = ProcessWidgetCommandUtil.getTaskAdapter(eo);
            if (otherTaskAdp != null) {
                String taskName = otherTaskAdp.getName();
    
                if (currDefaultName.equals(taskName) || currDefaultNameWithSpace.equals(taskName)) {
                    // This tasks references our task and still has the default name
                    // "Reference: <task Name>" so rename it to new name.
                    ccmd.append(otherTaskAdp.getSetNameCommand(
                            editingDomain,
                            Messages.SetTaskNameCommand_Reference_label + " " + newName)); //$NON-NLS-1$
                }
            }
        }

        return ccmd;
    }

    /**
     * Use {@link SetTaskNameCommand}.create() to create command.
     */
    private SetTaskNameCommand() {
    }

}
