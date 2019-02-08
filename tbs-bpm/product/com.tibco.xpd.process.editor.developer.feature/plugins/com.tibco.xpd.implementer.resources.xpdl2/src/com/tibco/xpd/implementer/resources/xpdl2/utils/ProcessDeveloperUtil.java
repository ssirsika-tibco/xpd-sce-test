/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.utils;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.wsdl.Definition;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.providers.DataFilterPickerProviderHelper.DataPickerItem;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.actions.CloseOpenProcessEditorCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ResetDefaultActivityColourCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * General utilities for process developer feature.
 * 
 * @author aallway
 * @since 3.3 (6 May 2010)
 */
public class ProcessDeveloperUtil {

    /**
     * Create and return the command to set the given business process
     * configured send task activity to invoke the wsdl operation for the given
     * business process activity.
     * 
     * @param editingDomain
     * @param activity
     * @param requestActivity
     * 
     * @return Command or UnexecutableCommand on error.
     */
    public static Command getSetBusinessProcessCommand(
            EditingDomain editingDomain, Activity activity,
            Activity requestActivity) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Get the biz process operation name from the given business process.
     * 
     * @param process
     */
    public static String getBusinessProcessOperationName(Process process,
            Activity requestActivity) {
        for (Activity activity : Xpdl2ModelUtil
                .getAllActivitiesInProc(process)) {
            if (activity == requestActivity && isRequestActivity(activity)) {
                return activity.getName();
            }
        }
        return null;
    }

    /**
     * @param activity
     * 
     * @return true if its a message start activity, receive-task or a catch
     *         message intermediate event (with/without in-flow)
     */
    public static boolean isRequestActivity(Activity activity) {

        Event event = activity.getEvent();

        if (event != null && event
                .getEventTriggerTypeNode() instanceof TriggerResultMessage) {
            TriggerResultMessage trm =
                    (TriggerResultMessage) event.getEventTriggerTypeNode();

            if (event instanceof StartEvent) {
                // its a Message start activity
                return true;

            } else if (event instanceof IntermediateEvent) {

                if (!CatchThrow.THROW.equals(trm.getCatchThrow())) {

                    // Its Catch-Message Intermediate event
                    return true;
                }
            }
        } else if (activity.getImplementation() instanceof Task) {

            Task task = (Task) activity.getImplementation();

            if (task.getTaskReceive() != null) {

                return true;
            }
        }

        return false;
    }

    public static Activity getFirstProcessMessageStartActivity(
            Process process) {
        for (Activity activity : process.getActivities()) {
            if (Xpdl2ModelUtil.isMessageProcessStartActivity(activity)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * Open a picker to let the user select a REST Service particpant and if the
     * user says OK, then return the command to add the selected participant for
     * the REST service activity.
     * 
     * @param editingDomain
     * @param activity
     * @return
     */
    public static CompoundCommand getSetRESTServiceParticipantFromPickerCommand(
            EditingDomain editingDomain, Activity activity) {
        /*
         * Participant picker.
         */
        DataFilterPicker picker =
                new DataFilterPicker(Display.getDefault().getActiveShell(),
                        DataPickerType.PARTICIPANTS, false);

        picker.setScope(activity);

        /*
         * Rest service task adapter instance.
         */
        final RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

        /*
         * Add filter on the picker to only let REST service participants go.
         */
        picker.addFilter(new IFilter() {

            @Override
            public boolean select(Object toTest) {

                boolean isRest = false;

                if (toTest instanceof DataPickerItem) {

                    DataPickerItem dpi = (DataPickerItem) toTest;

                    Object item = dpi.getItem();

                    if (item instanceof Participant) {

                        Participant participant = (Participant) item;

                        isRest = rsta.isRestParticipant(participant);
                    }
                }

                return isRest;
            }
        });

        int ret = picker.open();

        if (ret == DataFilterPicker.OK) {

            Object newPerf = picker.getFirstResult();

            if (newPerf instanceof Participant) {

                Participant participant = (Participant) newPerf;

                Performers performers =
                        rsta.createPerformers(participant.getId());

                CompoundCommand cc = new CompoundCommand(
                        Messages.RestServiceTaskSection_EndpointSetCommand);

                cc.append(new SetCommand(editingDomain, activity,
                        Xpdl2Package.eINSTANCE.getActivity_Performers(),
                        performers));

                return cc;
            }
        }

        return null;
    }

    /**
     * Utility which tells you whether the WSDL file that the activity uses is
     * one that is generated for a non-API task.
     * 
     * @param activity
     * @return true if the WSDL has value for a tibex:ServiceTask attribute in
     *         its Definition, false otherwise.
     */
    public static Boolean isWebServiceOperationGenerated(Activity activity) {
        IFile wsdlFile = Xpdl2WsdlUtil.getWsdlFile(activity);

        if (wsdlFile != null && wsdlFile.isAccessible()) {
            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(wsdlFile);
            if (workingCopy != null
                    && workingCopy.getRootElement() instanceof Definition) {
                Definition definition =
                        (Definition) workingCopy.getRootElement();
                String attribute = definition.getElement()
                        .getAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);
                if (attribute != null && attribute.length() > 0) {
                    return Boolean.TRUE;
                }
            }
        }

        return Boolean.FALSE;
    }

    /**
     * Convert any activity that has default colour for pageflow process to use
     * default colour for business service.
     */
    public static CompoundCommand setDefaultBusinessServiceColorCommand(
            EditingDomain ed, Process pageflowProcess) {
        CompoundCommand cmd =
                new CloseOpenProcessEditorCommand(pageflowProcess);
        ProcessWidgetColors businessServiceColours = ProcessWidgetColors
                .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
        ProcessWidgetColors pageflowColours = ProcessWidgetColors
                .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(pageflowProcess);

        for (Activity act : allActivitiesInProc) {
            cmd.append(new ResetDefaultActivityColourCommand(ed, act,
                    pageflowColours, businessServiceColours));
        }
        return cmd;
    }

    /**
     * Convert any activity that has default colour for business service to use
     * default colour for pageflow process.
     */
    public static CompoundCommand setDefaultPageflowColorCommand(
            EditingDomain ed, Process pageflowProcess) {
        CompoundCommand cmd =
                new CloseOpenProcessEditorCommand(pageflowProcess);
        ProcessWidgetColors businessServiceColours = ProcessWidgetColors
                .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
        ProcessWidgetColors pageflowColours = ProcessWidgetColors
                .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(pageflowProcess);

        for (Activity act : allActivitiesInProc) {
            cmd.append(new ResetDefaultActivityColourCommand(ed, act,
                    businessServiceColours, pageflowColours));
        }
        return cmd;
    }

}
