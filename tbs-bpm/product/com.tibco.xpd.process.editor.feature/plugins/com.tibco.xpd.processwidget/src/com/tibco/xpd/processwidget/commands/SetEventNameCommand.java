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

import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * <b>SetTaskNameCommand</b>
 * <p>
 * Wrapper for change task name command.
 * </p>
 * <p>
 * This not only changes the task type but also...
 * </p>
 * <p>
 * <li>If the event is a link event and is the target of another link event then
 * the source of link is renamed if it still has the default "Link To: <event
 * name>" label.</li>
 * 
 * <p>
 * <b>NOTE: This command can only be used for model objects that have associated
 * EventAdapter.
 * 
 * 
 */
public class SetEventNameCommand {

    public static Command create(EditingDomain editingDomain,
            EObject eventObject, String newName) {

        EventAdapter ea = ProcessWidgetCommandUtil.getEventAdapter(eventObject);
        if (ea == null) {
            return UnexecutableCommand.INSTANCE;
        }

        CompoundCommand ccmd = new CompoundCommand();

        ccmd.setLabel(Messages.SetTaskNameCommand_menu);

        ccmd.append(ea.getSetNameCommand(editingDomain, newName));

        if (EventTriggerType.EVENT_LINK_CATCH_LITERAL == ea
                .getEventTriggerType()
                || EventTriggerType.EVENT_LINK_THROW_LITERAL == ea
                        .getEventTriggerType()) {
            // Its a link event, check for any other link events that reference
            // it.
            String currDefaultName =
                    Messages.SetEventNameCommand__LinkTo_Label + ea.getName();

            String currDefaultNameWithSpace =
                    Messages.SetEventNameCommand__LinkTo_Label
                            + " " + ea.getName(); //$NON-NLS-1$

            // Look for tasks that reference this task.
            Collection<EObject> srcEvents = ea.getSourceLinkEvents();

            for (EObject srcEvent : srcEvents) {
                EventAdapter srcAdp =
                        ProcessWidgetCommandUtil.getEventAdapter(srcEvent);
                if (srcAdp != null) {

                    String eventName = srcAdp.getName();

                    if (currDefaultName.equals(eventName)
                            || currDefaultNameWithSpace.equals(eventName)) {
                        // This tasks references our task and still has the
                        // default
                        // name
                        // "Reference: <task Name>" so rename it to new name.
                        ccmd.append(srcAdp.getSetNameCommand(editingDomain,
                                Messages.SetEventNameCommand__LinkTo_Label
                                        + " " + newName)); //$NON-NLS-1$
                    }
                }
            }

        }
        return ccmd;
    }

    /**
     * Use {@link SetEventNameCommand}.create() to create command.
     */
    private SetEventNameCommand() {
    }

}
