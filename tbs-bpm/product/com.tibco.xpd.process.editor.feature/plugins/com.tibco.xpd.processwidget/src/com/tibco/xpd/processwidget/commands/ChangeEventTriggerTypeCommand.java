/**
 * ChangeTaskTypeCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * <b>ChangeEventTriggerType</b>
 * <p>
 * Wrapper for change task type command.
 * </p>
 * <p>
 * This not only changes the event type but also...
 * </p>
 * <li>Resets the task name to default for new task type IF the task currently
 * has default name for current task type</li>
 * 
 * <p>
 * <b>NOTE: This command can only be used for model objects that have associated
 * EventAdapter.
 * 
 */
public class ChangeEventTriggerTypeCommand {

    public static Command create(EditingDomain editingDomain,
            EObject eventObject, EventTriggerType newTriggerType) {

        EventAdapter eventAdapter =
                ProcessWidgetCommandUtil.getEventAdapter(eventObject);
        if (eventAdapter == null) {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand ccmd = new CompoundCommand();

        ccmd
                .setLabel(Messages.ChangeEventTriggerTypeCommand_SetTriggerType_menu);

        ccmd.append(eventAdapter.getSetEventTriggerTypeCommand(editingDomain,
                newTriggerType));

        return ccmd;

    }

    /**
     * Use {@link ChangeEventTriggerTypeCommand}.create() to create command.
     */
    private ChangeEventTriggerTypeCommand() {
    }

}
