/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.decisions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.nativeservices.decisions.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Drop object contribution for dropping decision flow onto bpm/pageflow
 * process.
 * 
 * @author aallway
 * @since 19 Sep 2011
 */
public class DecisionFlowToBpmProcessDropContribution implements
        IDropObjectContribution {

    public DecisionFlowToBpmProcessDropContribution() {
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution#getDropTypeInfo(java.lang.Object,
     *      java.util.List, org.eclipse.draw2d.geometry.Point, java.lang.Object,
     *      int)
     * 
     * @param targetContainerObject
     * @param dropObjects
     * @param location
     * @param actualTargetObject
     * @param userRequestedDropType
     * @return
     */
    @Override
    public DropTypeInfo getDropTypeInfo(Object targetContainerObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {
        //
        // Overall, if actual target is not lane, activity or transition then we
        // can't do anything.
        //
        // If it is and activity then we can either insert into emb sub-proc OR
        // convert task to decision service task (getTaskTypeStrict() will
        // return null for gateways and events so that will ensure we don't
        // allow that.
        //
        if (actualTargetObject instanceof Lane
                || (actualTargetObject instanceof Activity && (TaskObjectUtil
                        .getTaskTypeStrict((Activity) actualTargetObject) != null))
                || actualTargetObject instanceof Transition) {

            /*
             * For drop onto activity to convert to decision svc task then there
             * must be only one item
             */
            boolean ok = true;
            if (actualTargetObject instanceof Activity) {
                if (((Activity) actualTargetObject).getBlockActivity() == null) {
                    if (dropObjects.size() > 1) {
                        /*
                         * Can't convert task to call multiple decision
                         * services.!
                         */
                        ok = false;
                    }
                }
            }

            if (ok) {
                /*
                 * Only handle drop onto Business / Pageflow process.
                 */
                Process targetProcess =
                        Xpdl2ModelUtil.getProcess((EObject) actualTargetObject);

                if (Xpdl2ModelUtil.isBusinessProcess(targetProcess)
                        || Xpdl2ModelUtil.isPageflow(targetProcess)) {

                    /*
                     * Sid: Don't have time to do multi-sequence of tasks and
                     * rarelly used so this will have to be done later if
                     * reuired or anyone even notices.!
                     */
                    if (areAllDecisionFlows(dropObjects, targetProcess)
                            && dropObjects.size() == 1) {

                        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_COPY);

                        dropType.setFeedbackRectangles(DiagramDropObjectUtils
                                .getTaskFeedbackRectangles(dropObjects.size()));

                        return dropType;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param dropObjects
     * @return <code>true</code> if all are decision flows
     * 
     */
    private boolean areAllDecisionFlows(List<Object> dropObjects,
            Process targetProcess) {
        if (dropObjects.size() > 0) {

            for (Object object : dropObjects) {
                if (!(object instanceof Process)
                        || !DecisionFlowUtil.isDecisionFlow((Process) object)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution#getDropPopupItems(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object, java.util.List, org.eclipse.draw2d.geometry.Point,
     *      java.lang.Object, int)
     * 
     * @param editingDomain
     * @param targetObject
     * @param dropObjects
     * @param location
     * @param actualTargetObject
     * @param userRequestedDropType
     * @return
     */
    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, Object targetContainerObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType) {

        List<DropObjectPopupItem> items = new ArrayList<DropObjectPopupItem>();

        //
        // Overall, if actual target is not lane, activity or transition then we
        // can't do anything.
        //
        // If it is and activity then we can eithe insert into emb sub-proc OR
        // convert task to decision service task
        //
        if (actualTargetObject instanceof Lane
                || (actualTargetObject instanceof Activity && (TaskObjectUtil
                        .getTaskTypeStrict((Activity) actualTargetObject) != null))
                || actualTargetObject instanceof Transition) {

            /*
             * Only handle drop onto Business / Pageflow process.
             */
            Process targetProcess =
                    Xpdl2ModelUtil.getProcess((EObject) actualTargetObject);

            if (Xpdl2ModelUtil.isBusinessProcess(targetProcess)
                    || Xpdl2ModelUtil.isPageflow(targetProcess)) {

                /*
                 * Sid: Don't have time to do multi-sequence of tasks and
                 * rarelly used so this will have to be done later if reuired or
                 * anyone even notices.!
                 */
                if (areAllDecisionFlows(dropObjects, targetProcess)
                        && dropObjects.size() == 1
                        && checkProjectReferences(targetProcess, dropObjects)) {

                    if (actualTargetObject instanceof Activity
                            && (((Activity) actualTargetObject)
                                    .getBlockActivity()) == null) {
                        /*
                         * Target is an activity (which we know from
                         * getTaskTypeStrict() above must be a task or emb
                         * sub-proc) that is not an embedded sub-proc. So create
                         * command to set the task type
                         */
                        DropObjectPopupItem item =
                                createConvertToDecisionSvcTaskItem(editingDomain,
                                        (Activity) actualTargetObject,
                                        (Process) dropObjects.get(0));
                        if (item != null) {
                            items.add(item);
                        }

                    } else if (targetContainerObject instanceof Lane
                            || targetContainerObject instanceof Activity) {
                        /*
                         * Dropping new decision sservice tasks into
                         */
                        DropObjectPopupItem item =
                                createDecisionServiceTaskItem(editingDomain,
                                        targetProcess,
                                        targetContainerObject,
                                        location,
                                        dropObjects);
                        if (item != null) {
                            items.add(item);
                        }

                    }

                }
            }
        }
        return items;
    }

    /**
     * @param targetProcess
     * @param dropObjects
     */
    private boolean checkProjectReferences(Process targetProcess,
            List<Object> dropObjects) {
        List<EObject> dropEObjs = new ArrayList<EObject>();
        for (Object o : dropObjects) {

            if (o instanceof EObject) {
                dropEObjs.add((EObject) o);
            }
        }

        if (ProcessUIUtil.checkAndAddProjectReferences(Display.getDefault()
                .getActiveShell(), targetProcess, dropEObjs)) {
            return true;
        }
        return false;
    }

    /**
     * @param editingDomain
     * @param actualTargetObject
     * @param object
     * 
     * @return drop popup command item to convert task to decision service task.
     */
    private DropObjectPopupItem createConvertToDecisionSvcTaskItem(
            EditingDomain editingDomain, Activity taskActivity,
            Process decisionFlow) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.DecisionFlowToBpmProcessDropContribution_ConvertToInvokeDecSvc_menu);

        if (!TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(taskActivity))) {
            cmd.append(TaskObjectUtil.getSetTaskTypeCommandEx(editingDomain,
                    taskActivity,
                    TaskType.SERVICE_LITERAL,
                    taskActivity.getProcess(),
                    true,
                    true,
                    true));

        } else {
            TaskService taskService =
                    Xpdl2Factory.eINSTANCE.createTaskService();

            Task task = (Task) taskActivity.getImplementation();
            cmd.append(SetCommand.create(editingDomain,
                    task,
                    Xpdl2Package.eINSTANCE.getTask_TaskService(),
                    taskService));

        }

        cmd.append(new SetDecisionFlowCommand(editingDomain, taskActivity,
                taskActivity.getProcess(), decisionFlow));

        if (!taskActivity.getPerformerList().isEmpty()) {
            cmd.append(TaskObjectUtil
                    .selectOrClearActivityParticipantCommand(editingDomain,
                            taskActivity.getProcess(),
                            taskActivity,
                            true));
        }

        return DropObjectPopupItem
                .createCommandItem(cmd,
                        Messages.DecisionFlowToBpmProcessDropContribution_ConvertToInvokeDecSvc_menu,
                        NativeServicesDecisionPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(NativeServicesDecisionPlugin.IMG_DECISIONFLOW));
    }

    /**
     * @param editingDomain
     * @param targetProcess
     * @param targetContainerObject
     * @param location
     * @param dropObjects
     * 
     * @return drop popup item to create decision service tasks
     */
    private DropObjectPopupItem createDecisionServiceTaskItem(
            EditingDomain editingDomain, Process targetProcess,
            Object targetContainerObject, Point location,
            List<Object> dropObjects) {

        if (dropObjects.size() == 1) {
            /*
             * Create a single decision service task.
             */
            CompoundCommand cmd = new CompoundCommand();

            Process decisionFlow = (Process) dropObjects.get(0);

            Activity decisionServiceTask =
                    createNewDecisionServiceTask(editingDomain,
                            decisionFlow,
                            targetProcess,
                            targetContainerObject,
                            location,
                            cmd);

            Collection<Object> createObjs = new ArrayList<Object>();
            createObjs.add(decisionServiceTask);

            return DropObjectPopupItem
                    .createCreateDiagramObjectsItem(createObjs,
                            cmd,
                            Messages.DecisionFlowToBpmProcessDropContribution_CreateTask,
                            NativeServicesDecisionPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(NativeServicesDecisionPlugin.IMG_DECISIONFLOW));

        }

        return null;
    }

    /**
     * @param editingDomain
     * @param decisionFlow
     * @param targetProcess
     * @param targetContainerObject
     * @param location
     * @param cmd
     * @return
     */
    protected Activity createNewDecisionServiceTask(
            EditingDomain editingDomain, Process decisionFlow,
            Process targetProcess, Object targetContainerObject,
            Point location, CompoundCommand cmd) {
        Activity decisionServiceTask =
                createDecisionServiceTask(editingDomain,
                        targetProcess,
                        targetContainerObject,
                        location,
                        decisionFlow,
                        cmd);

        TaskService taskService =
                ((Task) decisionServiceTask.getImplementation())
                        .getTaskService();
        taskService.setImplementation(ImplementationType.OTHER_LITERAL);
        Xpdl2ModelUtil.setOtherAttribute(taskService,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ImplementationType(),
                TaskImplementationTypeDefinitions.DECISION_SERVICE);
        return decisionServiceTask;
    }

    /**
     * @param editingDomain
     * @param targetContainerObject
     * @param location
     * @param object
     * @param cmd
     * 
     * @return Create a new decision service task.
     */
    private Activity createDecisionServiceTask(EditingDomain editingDomain,
            Process targetProcess, Object targetContainerObject,
            Point location, Process decisionFlow, CompoundCommand cmd) {

        String laneId = null;
        if (targetContainerObject instanceof Lane) {
            laneId = ((Lane) targetContainerObject).getId();
        }

        /*
         * Create decision service task.
         */
        Activity decisionServiceTask =
                DiagramDropObjectUtils.createTaskObject(targetProcess,
                        laneId,
                        location,
                        TaskType.SERVICE_LITERAL);

        /*
         * Ensure name is unique.
         */
        Collection<Activity> existingActs =
                Xpdl2ModelUtil.getAllActivitiesInProc(targetProcess);
        String displayName =
                Xpdl2ModelUtil
                        .getUniqueDisplayNameInSet(Xpdl2ModelUtil
                                .getDisplayNameOrName(decisionFlow),
                                existingActs,
                                true);

        Xpdl2ModelUtil.setOtherAttribute(decisionServiceTask,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                displayName);

        String name =
                Xpdl2ModelUtil.getUniqueNameInSet(decisionFlow.getName(),
                        existingActs,
                        false);
        decisionServiceTask.setName(name);

        /*
         * Add the decison flow reference
         */
        SubFlow decFlowRef = Xpdl2Factory.eINSTANCE.createSubFlow();

        WorkingCopy externalWc =
                WorkingCopyUtil.getWorkingCopyFor(decisionFlow);
        Xpdl2WorkingCopyImpl wc =
                (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                        .getWorkingCopyFor(targetProcess);
        if (wc == externalWc) {
            decFlowRef.setProcessId(decisionFlow.getId());
        } else {
            String refId = wc.appendCreateReferenceCommand(externalWc, cmd);
            decFlowRef.setProcessId(decisionFlow.getId());
            decFlowRef.setPackageRefId(refId);
        }

        TaskService taskService =
                ((Task) decisionServiceTask.getImplementation())
                        .getTaskService();

        taskService.setImplementation(ImplementationType.OTHER_LITERAL);
        Xpdl2ModelUtil.setOtherAttribute(taskService,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ImplementationType(),
                TaskImplementationTypeDefinitions.DECISION_SERVICE);

        Xpdl2ModelUtil
                .setOtherElement(taskService, XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_DecisionService(), decFlowRef);

        return decisionServiceTask;
    }

    /**
     * Late exec command to set the reference to a decision flow from a service
     * task (and sets the sevice task type as well.
     * 
     * 
     * @author aallway
     * @since 20 Sep 2011
     */
    private class SetDecisionFlowCommand extends AbstractLateExecuteCommand {

        private Process decisionFlow;

        private Activity decisionServiceTask;

        private Process targetProcess;

        /**
         * @param editingDomain
         * @param taskActivity
         * @param decisionFlow
         */
        public SetDecisionFlowCommand(EditingDomain editingDomain,
                Activity taskActivity, Process targetProcess,
                Process decisionFlow) {
            super(editingDomain, taskActivity);

            this.decisionServiceTask = taskActivity;
            this.decisionFlow = decisionFlow;
            this.targetProcess = targetProcess;
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {
            CompoundCommand cmd = new CompoundCommand();

            SubFlow decFlowRef = Xpdl2Factory.eINSTANCE.createSubFlow();

            WorkingCopy externalWc =
                    WorkingCopyUtil.getWorkingCopyFor(decisionFlow);
            Xpdl2WorkingCopyImpl wc =
                    (Xpdl2WorkingCopyImpl) WorkingCopyUtil
                            .getWorkingCopyFor(targetProcess);

            if (wc == externalWc) {
                decFlowRef.setProcessId(decisionFlow.getId());
            } else {
                String refId = wc.appendCreateReferenceCommand(externalWc, cmd);
                decFlowRef.setProcessId(decisionFlow.getId());
                decFlowRef.setPackageRefId(refId);
            }

            TaskService taskService =
                    ((Task) decisionServiceTask.getImplementation())
                            .getTaskService();

            taskService.setImplementation(ImplementationType.OTHER_LITERAL);
            Xpdl2ModelUtil.setOtherAttribute(taskService,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementationType(),
                    TaskImplementationTypeDefinitions.DECISION_SERVICE);

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    taskService,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DecisionService(),
                    decFlowRef));

            return cmd;
        }
    }

}
