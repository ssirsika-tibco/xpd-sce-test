package com.tibco.xpd.rest.dragdrop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskSection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to handle drag and drop of REST Methods onto the Process Editor. The
 * method can be dropped onto a Land, an Activity or a Transition.
 * 
 * @author nwilson
 * @since 2 Mar 2015
 */
public class RestMethodDropContribution implements IDropObjectContribution {

    /**
     * {@inheritDoc}
     */
    @Override
    public DropTypeInfo getDropTypeInfo(Object targetContainerObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {
        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_NONE);
        if (dropObjects.size() == 1) {
            Object dropObject = dropObjects.get(0);

            /*
             * Sid XPD-7359 - be a little more picky aboout wht we allow drop
             * onto.
             */
            if (dropObject instanceof Method) {

                if (actualTargetObject instanceof Lane
                        || actualTargetObject instanceof Transition) {

                    dropType = new DropTypeInfo(DND.DROP_COPY);
                    dropType.setFeedbackRectangles(DiagramDropObjectUtils
                            .getTaskFeedbackRectangles(dropObjects.size()));

                } else if (actualTargetObject instanceof Activity) {
                    Activity activity = ((Activity) actualTargetObject);

                    /*
                     * XPD-7739 : Allow DND of method onto any task.
                     */
                    if (activity.getImplementation() != null
                            || activity.getEvent() instanceof EndEvent
                            || activity.getEvent() instanceof IntermediateEvent) {
                        dropType = new DropTypeInfo(DND.DROP_MOVE);

                    } else if (activity.getBlockActivity() != null) {
                        dropType = new DropTypeInfo(DND.DROP_COPY);
                        dropType.setFeedbackRectangles(DiagramDropObjectUtils
                                .getTaskFeedbackRectangles(dropObjects.size()));

                    }
                }
            }
        }

        return dropType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, Object targetObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {
        List<DropObjectPopupItem> items = new ArrayList<>();

        if (dropObjects.size() == 1
                && editingDomain instanceof TransactionalEditingDomain) {

            Object dropObject = dropObjects.get(0);
            if (!(dropObject instanceof EObject)
                    || !ProcessUIUtil.checkAndAddProjectReferences(Display
                            .getDefault().getActiveShell(),
                            (EObject) targetObject,
                            Collections.singleton((EObject) dropObject))) {
                return Collections.emptyList();
            }

            if (dropObject instanceof Method) {
                Method method = (Method) dropObject;
                if (actualTargetObject instanceof Lane
                        || (actualTargetObject instanceof Activity && ((Activity) actualTargetObject)
                                .getBlockActivity() != null)) {
                    // Pop up list of possible REST activities
                    addCreateActivityItems(items,
                            (EObject) actualTargetObject,
                            (GraphicalNode) actualTargetObject,
                            location,
                            method);

                } else if (actualTargetObject instanceof Transition
                        && (targetObject instanceof Lane || targetObject instanceof Activity)) {
                    // Pop up list of possible REST activities
                    addCreateActivityItems(items,
                            (EObject) actualTargetObject,
                            (GraphicalNode) targetObject,
                            location,
                            method);

                } else if (actualTargetObject instanceof Activity) {
                    // Check if this is already a REST Service Activity
                    Activity activity = (Activity) actualTargetObject;
                    RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

                    OtherElementsContainer container =
                            rsta.getRSOContainer(activity);

                    RestServiceOperation rso = rsta.getRSO(container);

                    if (rso == null) {
                        if (activity.getImplementation() != null) {
                            // Offer to convert to REST Service Activity
                            addConvertTaskItems(editingDomain,
                                    items,
                                    activity,
                                    method);

                        } else if (activity.getEvent() != null) {
                            // Convert to throw message event and assign.
                            addConvertEventItems(editingDomain,
                                    items,
                                    activity,
                                    method);
                        }
                    } else {
                        // Assign method to existing operation
                        Command cmd =
                                createAssignMethodCommand(editingDomain,
                                        activity,
                                        rso,
                                        method);

                        items.add(DropObjectPopupItem
                                .createCommandItem(cmd,
                                        Messages.RestMethodDropContribution_SetRestServiceMethodLabel,
                                        null));
                    }
                }
            }
        }
        return items;
    }

    /**
     * @param items
     *            The popu items created.
     * @param lane
     *            The lane to add the activity to.
     * @param location
     *            The drop location.
     * @param method
     *            The REST Service method dropped.
     */
    private void addCreateActivityItems(List<DropObjectPopupItem> items,
            EObject actualTargetObject, GraphicalNode targetContainerObject,
            Point location, Method method) {

        NodeGraphicsInfo nodeInfo = null;

        if (targetContainerObject != null) {
            nodeInfo =
                    Xpdl2ModelUtil.getNodeGraphicsInfo(targetContainerObject);
        }
        String laneId = nodeInfo != null ? nodeInfo.getLaneId() : null;

        Process process = Xpdl2ModelUtil.getProcess(actualTargetObject);

        Activity serviceTask =
                DiagramDropObjectUtils.createTaskObject(process,
                        laneId,
                        location,
                        TaskType.SERVICE_LITERAL);
		/*
		 * create a display label
		 */
		String acitivityDisplayName = String.format("%1$s - %2$s %3$s", //$NON-NLS-1$
				method.getName(), method.getHttpMethod().getName(), ((Resource) method.eContainer()).getName());

        RestServiceTaskSection.setRestActivityLabelAndName(serviceTask,
				acitivityDisplayName,
                process,
                true,
                null,
                null);

        Activity sendTask =
                DiagramDropObjectUtils.createTaskObject(process,
                        laneId,
                        location,
                        TaskType.SEND_LITERAL);
        RestServiceTaskSection.setRestActivityLabelAndName(sendTask,
				acitivityDisplayName,
                process,
                true,
                null,
                null);

        Activity throwMessageEvent =
                DiagramDropObjectUtils.createEventObject(process,
                        laneId,
                        location,
                        EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);
        RestServiceTaskSection.setRestActivityLabelAndName(throwMessageEvent,
				acitivityDisplayName,
                process,
                true,
                null,
                null);

        Activity throwMessageEndEvent =
                DiagramDropObjectUtils.createEventObject(process,
                        laneId,
                        location,
                        EventFlowType.FLOW_END_LITERAL,
                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);
        RestServiceTaskSection
                .setRestActivityLabelAndName(throwMessageEndEvent,
						acitivityDisplayName,
                        process,
                        true,
                        null,
                        null);

        setRSOMethod(method, serviceTask);
        setRSOMethod(method, sendTask);
        setRSOMethod(method, throwMessageEvent);
        setRSOMethod(method, throwMessageEndEvent);

        /*
         * XPD-7820: Add Rest Data mappers to new dropped rest activities.
         */
        addRestDataMapper(serviceTask);
        addRestDataMapper(sendTask);
        addRestDataMapper(throwMessageEvent);
        addRestDataMapper(throwMessageEndEvent);

        Collection<Object> serviceTaskObjects = new ArrayList<>();
        Collection<Object> sendTaskObjects = new ArrayList<>();
        Collection<Object> throwMessageEventObjects = new ArrayList<>();
        Collection<Object> throwMessageEndEventObjects = new ArrayList<>();

        serviceTaskObjects.add(serviceTask);
        sendTaskObjects.add(sendTask);
        throwMessageEventObjects.add(throwMessageEvent);
        throwMessageEndEventObjects.add(throwMessageEndEvent);

        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

		IFile file = WorkingCopyUtil.getFile(method);
        Service svc = rsta.getService(method);
        Participant participant =
				rsta.getParticipant(process.getPackage(), file);
        if (participant == null) {
			participant = rsta.createParticipant(svc.getName());
            serviceTaskObjects.add(participant);
            sendTaskObjects.add(participant);
            throwMessageEventObjects.add(participant);
            throwMessageEndEventObjects.add(participant);
        }

        serviceTask.setPerformers(rsta.createPerformers(participant.getId()));
        sendTask.setPerformers(rsta.createPerformers(participant.getId()));
        throwMessageEvent.setPerformers(rsta.createPerformers(participant
                .getId()));
        throwMessageEndEvent.setPerformers(rsta.createPerformers(participant
                .getId()));

        items.add(DropObjectPopupItem
                .createCreateDiagramObjectsItem(serviceTaskObjects,
                        Messages.RestMethodDropContribution_CreateRestServiceTaskLabel,
                        DiagramDropObjectUtils
                                .getImageForTaskType(TaskType.SERVICE_LITERAL)));

        items.add(DropObjectPopupItem
                .createCreateDiagramObjectsItem(sendTaskObjects,
                        Messages.RestMethodDropContribution_CreateRestSendTaskLabel,
                        DiagramDropObjectUtils
                                .getImageForTaskType(TaskType.SEND_LITERAL)));

        items.add(DropObjectPopupItem
                .createCreateDiagramObjectsItem(throwMessageEventObjects,
                        Messages.RestMethodDropContribution_CreateRestThrowMessageEventLabel,
                        DiagramDropObjectUtils
                                .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL)));

        if (!(actualTargetObject instanceof Transition)) {
            items.add(DropObjectPopupItem
                    .createCreateDiagramObjectsItem(throwMessageEndEventObjects,
                            Messages.RestMethodDropContribution_CreateRestThrowMessageEventLabel,
                            DiagramDropObjectUtils
                                    .getImageForEventType(EventFlowType.FLOW_END_LITERAL,
                                            EventTriggerType.EVENT_MESSAGE_THROW_LITERAL)));
        }
    }

    /**
     * Adds the Rest Data mapper to the passed Rest Activities.
     * 
     * @param activity
     */
    private void addRestDataMapper(Activity activity) {
		/* Sid ACE-8864 getIn/OutMapperContext() moved to RestServiceTaskAdapter */
		RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

		RestScriptDataMapperProvider inProvider = new RestScriptDataMapperProvider(MappingDirection.IN,
				rsta.getInMapperContext(activity));

        if (inProvider != null) {
            inProvider.getOrCreateScriptDataMapper(activity, null, null);
        }

        RestScriptDataMapperProvider outProvider =
				new RestScriptDataMapperProvider(MappingDirection.OUT, rsta.getOutMapperContext(activity));

        if (outProvider != null) {
            outProvider.getOrCreateScriptDataMapper(activity, null, null);
        }

    }

    /**
     * @param ted
     *            The editing domain.
     * @param items
     *            The drop items to add to.
     * @param activity
     *            The activity to convert.
     * @param method
     *            The REST Service method to assign.
     */
    private void addConvertTaskItems(final EditingDomain ted,
            final List<DropObjectPopupItem> items, final Activity activity,
            final Method method) {

        CompoundCommand serviceTaskCmd =
                new CompoundCommand(
                        Messages.RestMethodDropContribution_ConvertToRestServiceTaskLabel);
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        Task serviceTask = Xpdl2Factory.eINSTANCE.createTask();
        TaskService taskService = createTaskService(method);
        serviceTask.setTaskService(taskService);

        serviceTaskCmd.append(new SetCommand(ted, activity,
                Xpdl2Package.eINSTANCE.getActivity_Implementation(),
				serviceTask));
		rsta.addParticipantCommand(ted, serviceTaskCmd, activity, WorkingCopyUtil.getFile(method),
				rsta.getService(method).getName());

        CompoundCommand sendTaskCmd =
                new CompoundCommand(
                        Messages.RestMethodDropContribution_ConvertToRestSendTaskLabel);
        Task sendTask = Xpdl2Factory.eINSTANCE.createTask();
        TaskSend taskSend = createTaskSend(method);
        sendTask.setTaskSend(taskSend);
        sendTaskCmd.append(new SetCommand(ted, activity, Xpdl2Package.eINSTANCE
                .getActivity_Implementation(), sendTask));
		rsta.addParticipantCommand(ted, sendTaskCmd, activity, WorkingCopyUtil.getFile(method),
				rsta.getService(method).getName());

        /*
         * XPD-7739: If the current activity label is the default label then set
         * the Activity label to 'methodName - httpType resourcename'
         */
        if (RestServiceTaskSection
                .doesTaskOrEventLabelStartWithDefaultLabel(activity)) {

            String displayName = Xpdl2ModelUtil.getDisplayName(activity);

            String internalName = NameUtil.getInternalName(displayName, false);
            /*
             * If the current name is generated from the current label then we
             * should set the new name as well.
             */
            boolean shouldSetName =
                    internalName != null
                            && internalName.equals(activity.getName());

			/*
			 * create a display label
			 */
			String acitivityDisplayName = String.format("%1$s - %2$s %3$s", //$NON-NLS-1$
					method.getName(), method.getHttpMethod().getName(), ((Resource) method.eContainer()).getName());

            RestServiceTaskSection.setRestActivityLabelAndName(activity,
					acitivityDisplayName,
                    activity.getProcess(),
                    shouldSetName,
                    serviceTaskCmd,
                    ted);
            RestServiceTaskSection.setRestActivityLabelAndName(activity,
					acitivityDisplayName,
                    activity.getProcess(),
                    shouldSetName,
                    sendTaskCmd,
                    ted);
        }

        items.add(DropObjectPopupItem
                .createCommandItem(serviceTaskCmd,
                        Messages.RestMethodDropContribution_ConvertToRestServiceTaskLabel,
                        DiagramDropObjectUtils
                                .getImageForTaskType(TaskType.SERVICE_LITERAL)));
        items.add(DropObjectPopupItem.createCommandItem(sendTaskCmd,
                Messages.RestMethodDropContribution_ConvertToRestSendTaskLabel,
                DiagramDropObjectUtils
                        .getImageForTaskType(TaskType.SEND_LITERAL)));
    }

    /**
     * @param method
     * @return
     */
    private TaskService createTaskService(final Method method) {
        TaskService taskService = Xpdl2Factory.eINSTANCE.createTaskService();
        taskService.setMessageIn(Xpdl2Factory.eINSTANCE.createMessage());
        taskService.setMessageOut(Xpdl2Factory.eINSTANCE.createMessage());
        setRSOMethod(method, taskService);
        return taskService;
    }

    /**
     * @param method
     * @return
     */
    private TaskSend createTaskSend(final Method method) {
        TaskSend taskSend = Xpdl2Factory.eINSTANCE.createTaskSend();
        taskSend.setMessage(Xpdl2Factory.eINSTANCE.createMessage());
        setRSOMethod(method, taskSend);
        return taskSend;
    }

    /**
     * @param method
     * @param taskService
     */
    private void setRSOMethod(final Method method, Activity activity) {
        Implementation impl = activity.getImplementation();
        Event event = activity.getEvent();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService svc = task.getTaskService();
            if (svc != null) {
                setRSOMethod(method, svc);
            }
            TaskSend send = task.getTaskSend();
            if (send != null) {
                setRSOMethod(method, send);
            }
        } else if (event instanceof IntermediateEvent) {
            IntermediateEvent ie = (IntermediateEvent) event;
            ie.setImplementation(ImplementationType.OTHER_LITERAL);
            ie.getOtherAttributes()
                    .add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ImplementationType(),
                            RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME);
            TriggerResultMessage trm = ie.getTriggerResultMessage();
            trm.getOtherAttributes().clear();
            RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            rsta.setRSOMethod(trm, method);
        }
    }

    /**
     * @param method
     * @param taskService
     */
    private void setRSOMethod(final Method method, TaskService taskService) {
        taskService.setImplementation(ImplementationType.OTHER_LITERAL);
        taskService
                .getOtherAttributes()
                .add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ImplementationType(),
                        RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME);
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        rsta.setRSOMethod(taskService, method);
    }

    /**
     * @param method
     * @param taskSend
     */
    private void setRSOMethod(final Method method, TaskSend taskSend) {
        taskSend.setImplementation(ImplementationType.OTHER_LITERAL);
        taskSend.getOtherAttributes()
                .add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ImplementationType(),
                        RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME);
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        rsta.setRSOMethod(taskSend, method);
    }

    /**
     * @param ted
     *            The editing domain.
     * @param items
     *            The drop items to add to.
     * @param activity
     *            The activity to convert.
     * @param method
     *            The REST Service method to assign.
     */
    private void addConvertEventItems(final EditingDomain ted,
            final List<DropObjectPopupItem> items, final Activity activity,
            final Method method) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.RestMethodDropContribution_ConvertToRestThrowMessageEventLabel);

        /*
         * Sid XPD_7359 - Need to cope with EndEvents too.
         */
        TriggerResultMessage trm = createTriggerResultMessage(method);

        if (activity.getEvent() instanceof IntermediateEvent) {
            IntermediateEvent event =
                    Xpdl2Factory.eINSTANCE.createIntermediateEvent();

            event.setImplementation(ImplementationType.OTHER_LITERAL);
            event.getOtherAttributes()
                    .add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ImplementationType(),
                            RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME);
            event.setTriggerResultMessage(trm);
            event.setTrigger(TriggerType.MESSAGE_LITERAL);
            cmd.append(new SetCommand(ted, activity, Xpdl2Package.eINSTANCE
                    .getActivity_Event(), event));
        } else if (activity.getEvent() instanceof EndEvent) {
            EndEvent event = Xpdl2Factory.eINSTANCE.createEndEvent();
            event.setImplementation(ImplementationType.OTHER_LITERAL);
            event.getOtherAttributes()
                    .add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ImplementationType(),
                            RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME);
            event.setTriggerResultMessage(trm);
            event.setResult(ResultType.MESSAGE_LITERAL);
            cmd.append(new SetCommand(ted, activity, Xpdl2Package.eINSTANCE
                    .getActivity_Event(), event));
        }

		RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
		rsta.addParticipantCommand(ted, cmd, activity, WorkingCopyUtil.getFile(method),
				rsta.getService(method).getName());

        /*
         * XPD-7739: If the current activity label is the default label then set
         * the Activity label to 'methodName - httpType resourcename'
         */
        if (RestServiceTaskSection
                .doesTaskOrEventLabelStartWithDefaultLabel(activity)) {

            String displayName = Xpdl2ModelUtil.getDisplayName(activity);

            String internalName = NameUtil.getInternalName(displayName, false);
            /*
             * If the current name is generated from the current label then we
             * should set the new name as well.
             */
            boolean shouldSetName =
                    internalName != null
                            && internalName.equals(activity.getName());

			/*
			 * create a display label
			 */
			String activityDisplayName = String.format("%1$s - %2$s %3$s", //$NON-NLS-1$
					method.getName(), method.getHttpMethod().getName(), ((Resource) method.eContainer()).getName());

            RestServiceTaskSection.setRestActivityLabelAndName(activity,
					activityDisplayName,
                    activity.getProcess(),
                    shouldSetName,
                    cmd,
                    ted);
        }

        items.add(DropObjectPopupItem
                .createCommandItem(cmd,
                        Messages.RestMethodDropContribution_ConvertToRestThrowMessageEventLabel,
                        DiagramDropObjectUtils
                                .getImageForEventType(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL)));
    }

    /**
     * @param method
     * @return
     */
    private TriggerResultMessage createTriggerResultMessage(final Method method) {
        TriggerResultMessage trm =
                Xpdl2Factory.eINSTANCE.createTriggerResultMessage();
        trm.setMessage(Xpdl2Factory.eINSTANCE.createMessage());
        trm.setCatchThrow(CatchThrow.THROW);
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        rsta.setRSOMethod(trm, method);
        return trm;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param rso
     *            The REST Service Operation to modify.
     * @param method
     *            The Method to assign.
     * @return The command to perform the operation.
     */
    private Command createAssignMethodCommand(final EditingDomain ed,
            final Activity activity, final RestServiceOperation rso,
            final Method method) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.RestMethodDropContribution_SetRestServiceMethodLabel);
        Implementation impl = activity.getImplementation();
        Event event = activity.getEvent();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService taskService = task.getTaskService();
            if (taskService != null) {
                taskService = createTaskService(method);
                cmd.append(new SetCommand(ed, task, Xpdl2Package.eINSTANCE
                        .getTask_TaskService(), taskService));
            }
            TaskSend taskSend = task.getTaskSend();
            if (taskSend != null) {
                taskSend = createTaskSend(method);
                cmd.append(new SetCommand(ed, task, Xpdl2Package.eINSTANCE
                        .getTask_TaskSend(), taskSend));
            }

        } else if (event instanceof IntermediateEvent) {

            TriggerResultMessage trm = createTriggerResultMessage(method);
            cmd.append(new SetCommand(ed, event, Xpdl2Package.eINSTANCE
                    .getIntermediateEvent_TriggerResultMessage(), trm));
            cmd.append(new SetCommand(ed, event, Xpdl2Package.eINSTANCE
                    .getIntermediateEvent_Implementation(),
                    ImplementationType.OTHER_LITERAL));
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    event,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementationType(),
                    RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME));

        }
        /* Sid XPD-7359: Handle end events properly. */
        else if (event instanceof EndEvent) {
            TriggerResultMessage trm = createTriggerResultMessage(method);
            cmd.append(new SetCommand(ed, event, Xpdl2Package.eINSTANCE
                    .getEndEvent_TriggerResultMessage(), trm));

            cmd.append(new SetCommand(ed, event, Xpdl2Package.eINSTANCE
                    .getEndEvent_Implementation(),
                    ImplementationType.OTHER_LITERAL));

            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    event,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementationType(),
                    RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME));
        }
        return cmd;
    }

}
