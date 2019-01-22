/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Rectangle;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.Xpdl2ProcessWidgetAdapterFactory;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.SetEndNoneEventReplyCommand;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * XPD-1343 : This resolution will help set all of the end none events to
 * message reply activities of the given request activity.
 * 
 * @author rsomayaj
 * @since 7 Jan 2011
 */
public class SetAllEndEventsAsReplyResolution extends
        AbstractWorkingCopyResolution {

    /**
     * 
     */
    public SetAllEndEventsAsReplyResolution() {
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand cmd = new CompoundCommand();

        if (target instanceof Activity) {
            Activity requestActivity = (Activity) target;
            Process process = requestActivity.getProcess();

            if (process != null) {
                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity act : allActivitiesInProc) {

                    EventTriggerType eventTriggerType =
                            EventObjectUtil.getEventTriggerType(act);
                    EventFlowType flowType = EventObjectUtil.getFlowType(act);

                    // If the Activity is an End event of type none
                    if (EventTriggerType.EVENT_NONE_LITERAL
                            .equals(eventTriggerType)
                            && EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                        cmd.append(SetEndNoneEventReplyCommand
                                .create(editingDomain, act, requestActivity
                                        .getId()));
                    }
                }
            }
            if (cmd.isEmpty()) {
                // Assuming that if the command list is empty - then there are
                // no end events whose trigger type is none, in which case - we
                // need to add an end message event.
                cmd.append(AddEndMessageReplyEventCommand.create(editingDomain,
                        requestActivity));
            }

            if (cmd.isEmpty()) {
                // this is just to check again - in normal circumstances - we
                // should reach in here, because we check above if the command
                // is empty, and if it is add an end message event as a reply
                // activity
                return null;
            }
            return cmd;
        }
        return null;
    }

    private static class AddEndMessageReplyEventCommand extends CompoundCommand {
        private final EditingDomain editingDomain;

        private final Activity requestActivity;

        static Command create(EditingDomain editingDomain,
                Activity requestActivity) {
            return new AddEndMessageReplyEventCommand(editingDomain,
                    requestActivity);
        }

        /**
         * 
         */
        private AddEndMessageReplyEventCommand(EditingDomain editingDomain,
                Activity requestActivity) {
            this.editingDomain = editingDomain;
            this.requestActivity = requestActivity;

        }

        /**
         * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
         * 
         * @return
         */
        @Override
        public boolean canExecute() {
            return true;
        }

        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#execute()
         * 
         */
        @Override
        public void execute() {
            EObject container = Xpdl2ModelUtil.getContainer(requestActivity);
            if (container instanceof Lane) {
                Xpdl2ProcessWidgetAdapterFactory xpdl2ProcessWidgetAdapterFactory =
                        new Xpdl2ProcessWidgetAdapterFactory();
                Object adaptedObj =
                        xpdl2ProcessWidgetAdapterFactory.adapt(container,
                                ProcessWidgetConstants.ADAPTER_TYPE);

                CreateAccessibleObjectCommand createEventCommand = null;
                if (adaptedObj instanceof LaneAdapter) {
                    Rectangle objectBounds =
                            Xpdl2ModelUtil.getObjectBounds(requestActivity);
                    LaneAdapter laneAdapter = (LaneAdapter) adaptedObj;
                    WidgetRGB fillColor =
                            ProcessWidgetColors.getInstance(laneAdapter)
                                    .getGraphicalNodeColor(null,
                                            ProcessWidgetColors.END_EVENT_FILL);
                    WidgetRGB lineColor =
                            ProcessWidgetColors.getInstance(laneAdapter)
                                    .getGraphicalNodeColor(null,
                                            ProcessWidgetColors.END_EVENT_LINE);
                    createEventCommand =
                            laneAdapter
                                    .getCreateEventCommand(editingDomain,
                                            EventFlowType.FLOW_END_LITERAL,
                                            EventTriggerType.EVENT_NONE_LITERAL,
                                            new Point(
                                                    (objectBounds.x + objectBounds.width)
                                                            + (ProcessWidgetConstants.END_EVENT_SIZE)
                                                            + 20,
                                                    objectBounds.y
                                                            + ProcessWidgetConstants.END_EVENT_SIZE
                                                            * 2),
                                            new Dimension(
                                                    ProcessWidgetConstants.END_EVENT_SIZE,
                                                    ProcessWidgetConstants.END_EVENT_SIZE),
                                            fillColor.toString(),
                                            lineColor.toString());

                    if (createEventCommand != null) {
                        appendAndExecute(createEventCommand);

                        Collection<?> result = getResult();
                        for (Object object : result) {
                            // Find the end event that is newly added. There
                            // should
                            // be only one end message event.
                            if (object instanceof Activity) {
                                Activity endEventActivity = (Activity) object;
                                appendAndExecute(SetEndNoneEventReplyCommand
                                        .create(editingDomain,
                                                endEventActivity,
                                                requestActivity.getId()));

                            }
                        }
                    }
                }
            } else {
                appendAndExecute(new CreateReplyActivityCommand(editingDomain,
                        requestActivity));
            }

        }
    }
}