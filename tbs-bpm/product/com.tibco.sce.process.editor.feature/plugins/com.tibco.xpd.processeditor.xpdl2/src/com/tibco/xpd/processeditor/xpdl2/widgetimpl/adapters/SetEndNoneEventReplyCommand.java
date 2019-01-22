/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.commands.ChangeEventTriggerTypeCommand;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * This command is written to change an End event of type None to a reply
 * activity. For an activity to be a Reply activity, it should
 * 
 * (i) be an event of type message, and,
 * 
 * (ii) have a request activity reference to the request activity
 * 
 * @author rsomayaj
 * @since 7 Jan 2011
 */
public class SetEndNoneEventReplyCommand extends CompoundCommand {

    private final EditingDomain editingDomain;

    private final Activity activity;

    private final String requestActivityId;

    private SetEndNoneEventReplyCommand(EditingDomain editingDomain,
            Activity activity, String requestActivityId) {
        this.editingDomain = editingDomain;
        this.activity = activity;
        this.requestActivityId = requestActivityId;

    }

    public static Command create(EditingDomain ed, Activity activity,
            String requestActivityId) {
        return new SetEndNoneEventReplyCommand(ed, activity, requestActivityId);
    }

    /**
     * @see org.eclipse.emf.common.command.CompoundCommand#execute()
     * 
     */
    @Override
    public void execute() {
        appendAndExecute(ChangeEventTriggerTypeCommand.create(editingDomain,
                activity,
                EventTriggerType.EVENT_MESSAGE_THROW_LITERAL));

        appendAndExecute(ReplyActivityUtil
                .getSetRequestActivityForReplyActivityCommand(editingDomain,
                        activity,
                        requestActivityId));
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     * 
     * @return
     */
    @Override
    public boolean canExecute() {
        return activity != null && requestActivityId != null;
    }
}
