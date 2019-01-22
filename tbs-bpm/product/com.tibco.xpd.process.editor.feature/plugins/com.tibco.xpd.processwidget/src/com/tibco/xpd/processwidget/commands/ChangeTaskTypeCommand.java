/**
 * ChangeTaskTypeCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * <b>ChangeTaskTypeCommand</b>
 * <p>
 * Wrapper for change task type command.
 * </p>
 * <p>
 * This not only changes the task type but also...
 * </p>
 * <li>Resets the task name to default for new task type IF the task currently
 * has default name for current task type</li> <li>Does the same thing with Fill
 * and Line color</li> <li>Later should do the same for other default things on
 * task</li>
 * 
 */
public class ChangeTaskTypeCommand {

    public static Command create(EditingDomain editingDomain,
            EObject activityObject, TaskType newTaskType) {

        TaskAdapter taskAdapter =
                ProcessWidgetCommandUtil.getTaskAdapter(activityObject);
        if (taskAdapter == null || newTaskType.equals(taskAdapter.getTaskType())) {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand ccmd = new CompoundCommand();

        ccmd.setLabel(Messages.ChangeTaskTypeCommand_SetType_menu);

        ccmd.append(taskAdapter.getSetTaskTypeCommand(editingDomain,
                newTaskType));

        return ccmd;
    }

    /**
     * Use {@link ChangeTaskTypeCommand}.create() to create command.
     */
    private ChangeTaskTypeCommand() {
    }

}
