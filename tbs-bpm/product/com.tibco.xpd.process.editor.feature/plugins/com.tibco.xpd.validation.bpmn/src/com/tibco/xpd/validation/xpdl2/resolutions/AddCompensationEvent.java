/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2EventAdapter;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class AddCompensationEvent extends AbstractWorkingCopyResolution {

    /** Add Compensation Event command name */
    private static final String COMMAND = "addCompensationEventCommand"; //$NON-NLS-1$

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
        CompoundCommand cCmd =
                new CompoundCommand(ResolutionMessages.getText(COMMAND));
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            Event event = activity.getEvent();
            TriggerResultCompensation resultCompensation = null;
            if (event instanceof EndEvent) {
                resultCompensation =
                        ((EndEvent) event).getTriggerResultCompensation();
            } else if (event instanceof IntermediateEvent) {
                resultCompensation =
                        ((IntermediateEvent) event)
                                .getTriggerResultCompensation();
            }
            if (null != resultCompensation
                    && null != resultCompensation.getActivityId()) {
                /**
                 * create an activity of type intermediate event with trigger
                 * type as CATCH
                 * */
                Process process = activity.getProcess();
                String laneId = null;

                EObject container = Xpdl2ModelUtil.getContainer(activity);
                if (container instanceof Lane) {
                    laneId = ((Lane) container).getId();
                } else if (container instanceof ActivitySet) {
                    laneId = ((ActivitySet) container).getId();
                }

                Dimension objSize;
                String fillColorId;
                String lineColorId;
                objSize =
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE);

                fillColorId = ProcessWidgetColors.INTERMEDIATE_EVENT_FILL;
                lineColorId = ProcessWidgetColors.INTERMEDIATE_EVENT_LINE;

                ProcessWidgetType processType =
                        TaskObjectUtil.getProcessType(process);

                WidgetRGB fillColor =
                        ProcessWidgetColors.getInstance(processType)
                                .getGraphicalNodeColor(null, fillColorId);
                WidgetRGB lineColor =
                        ProcessWidgetColors.getInstance(processType)
                                .getGraphicalNodeColor(null, lineColorId);

                Activity createEvent =
                        ElementsFactory
                                .createEvent(new Point(),
                                        objSize,
                                        laneId,
                                        EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                        EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL,
                                        fillColor.toString(),
                                        lineColor.toString(),
                                        process);

                cCmd.append(new CompensationCommand(editingDomain, process,
                        createEvent, null, null));

                Xpdl2EventAdapter adapter = new Xpdl2EventAdapter();
                /**
                 * setting the target as newly created event and attaching that
                 * event to resultCompensation.getActivityId()
                 * */
                adapter.setTarget(createEvent);
                cCmd.append(new CompensationCommand(editingDomain, null, null,
                        adapter, resultCompensation.getActivityId()));
            }
        }
        return cCmd;
    }

    private static class CompensationCommand extends CompoundCommand {
        /**
         * RS -MR40055- Guessed the position as a percent relative to the border
         * of the task being attached to.
         */
        private static final double POSITION_WRT_TASK_BORDER = 22.1875;

        private EditingDomain ed;

        private Process process;

        private Activity createdEventActivity;

        private Xpdl2EventAdapter adapter;

        private String taskId;

        public CompensationCommand(EditingDomain ed, Process process,
                Activity createdEventActivity, Xpdl2EventAdapter adapter,
                String taskId) {
            super();
            this.ed = ed;
            this.process = process;
            this.createdEventActivity = createdEventActivity;
            this.adapter = adapter;
            this.taskId = taskId;
        }

        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#execute()
         * 
         */
        @Override
        public void execute() {
            if (null != process && null != createdEventActivity) {
                super.appendAndExecute(AddCommand.create(ed,
                        process,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        createdEventActivity));
            }
            if (null != adapter && null != taskId) {
                super.appendAndExecute(adapter
                        .getSetTaskBorderAttachmentCommand(ed,
                                taskId,
                                new Double(POSITION_WRT_TASK_BORDER)));
            }
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

    }
}
