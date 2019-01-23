/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Rectangle;

import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2LaneAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2TaskAdapter;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 *Command to create a reply activity for a request API activity.
 * 
 *This command was pulled out of the {@link CreateReplyActivityResolution}
 * 
 * @author rsomayaj
 * @since 14 Jan 2011
 */
public class CreateReplyActivityCommand extends CompoundCommand {

    private EditingDomain editingDomain;

    private Activity requestActivity;

    /**
     * @param editingDomain
     * @param requestActivity
     */
    public CreateReplyActivityCommand(EditingDomain editingDomain,
            Activity requestActivity) {
        super();
        this.editingDomain = editingDomain;
        this.requestActivity = requestActivity;

        setLabel(Messages.CreateReplyActivityResolution_CreateRewplyActivity_menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.CompoundCommand#execute()
     */
    @Override
    public void execute() {
        EObject container = Xpdl2ModelUtil.getContainer(requestActivity);

        Rectangle objectBounds =
                Xpdl2ModelUtil.getObjectBounds(requestActivity);
        Point location =
                new Point((objectBounds.x + objectBounds.width / 2)
                        + (ProcessWidgetConstants.TASK_WIDTH_SIZE / 2) + 20,
                        (objectBounds.y + objectBounds.height / 2)
                                + (ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2)
                                + 20);

        CreateAccessibleObjectCommand createTaskCommand = null;

        if (container instanceof Lane) {
            createTaskCommand =
                    Xpdl2LaneAdapter.getCreateTaskInLaneCommand(editingDomain,
                            (Lane) container,
                            TaskType.SEND_LITERAL,
                            location,
                            new Dimension(
                                    ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                    ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                            ProcessWidgetColors.getInstance()
                                    .getGraphicalNodeColor(null,
                                            ProcessWidgetColors.TASK_FILL)
                                    .toString(),
                            ProcessWidgetColors.getInstance()
                                    .getGraphicalNodeColor(null,
                                            ProcessWidgetColors.TASK_LINE)
                                    .toString());

        } else if (container instanceof ActivitySet) {
            Activity embSubProcActivity =
                    Xpdl2ModelUtil
                            .getEmbSubProcActivityForActSet(requestActivity
                                    .getProcess(), ((ActivitySet) container)
                                    .getId());

            createTaskCommand =
                    Xpdl2TaskAdapter
                            .getCreateTaskInEmbeddedSubProcCommand(editingDomain,
                                    embSubProcActivity,
                                    TaskType.SEND_LITERAL,
                                    location,
                                    new Dimension(
                                            ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                            ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                                    ProcessWidgetColors
                                            .getInstance()
                                            .getGraphicalNodeColor(null,
                                                    ProcessWidgetColors.TASK_FILL)
                                            .toString(),
                                    ProcessWidgetColors
                                            .getInstance()
                                            .getGraphicalNodeColor(null,
                                                    ProcessWidgetColors.TASK_LINE)
                                            .toString());

        }

        if (createTaskCommand != null) {
            appendAndExecute(createTaskCommand);

            Command setReplyActivityCmd =
                    ReplyActivityUtil
                            .getSetRequestActivityForReplyActivityCommand(editingDomain,
                                    (Activity) createTaskCommand
                                            .getCreatedNode(),
                                    requestActivity.getId());
            if (setReplyActivityCmd != null) {
                appendAndExecute(setReplyActivityCmd);
                return;
            }
        }

        appendAndExecute(UnexecutableCommand.INSTANCE);
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    @Override
    public boolean canExecute() {
        return true;
    }
}