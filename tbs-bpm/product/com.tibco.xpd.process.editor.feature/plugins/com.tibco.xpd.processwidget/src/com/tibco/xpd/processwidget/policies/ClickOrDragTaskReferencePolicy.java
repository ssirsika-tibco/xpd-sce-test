/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.commands.internal.RevealReferencedEditPartCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetPolicy;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.CrossReferenceClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.GenericClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Click or drag gadget policy for set / goto referenced task on Reference Task.
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragTaskReferencePolicy extends
        AbstractClickOrDragGadgetPolicy {

    public static final String EDIT_POLICY_ID =
            "referenceTask.click.gadget.policy"; //$NON-NLS-1$

    private static final String CLICK_OR_DRAG_REF_TASK_GADGET_TYPE =
            "CLICK_OR_DRAG_REF_TASK_GADGET_TYPE"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    public ClickOrDragTaskReferencePolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain,
            EditPolicyEnablementProvider policyEnabledmentProvider) {
        super(editingDomain, BpmnClickOrDragGadgetAnchorFactory.INSTANCE,
                policyEnabledmentProvider, EDIT_POLICY_ID);
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    @Override
    protected Command createGadgetClickedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (CLICK_OR_DRAG_REF_TASK_GADGET_TYPE.equals(clickOrDragInfoType)
                && creq.getClickOrDragGadgetInfo() instanceof CrossReferenceClickOrDragGadgetInfo) {

            CrossReferenceClickOrDragGadgetInfo xRefInfo =
                    (CrossReferenceClickOrDragGadgetInfo) creq
                            .getClickOrDragGadgetInfo();

            return new RevealReferencedEditPartCommand((GraphicalEditPart) creq
                    .getHostEditPart(), xRefInfo.getReferencedEditPart());
        }

        return null;
    }

    @Override
    protected boolean canDrag(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (CLICK_OR_DRAG_REF_TASK_GADGET_TYPE.equals(clickOrDragInfoType)) {
            return true;
        }
        return false;
    }

    @Override
    protected Command createGadgetDraggedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {

        if (CLICK_OR_DRAG_REF_TASK_GADGET_TYPE.equals(clickOrDragInfoType)) {

            if (creq.getDragTarget() instanceof TaskEditPart) {
                TaskEditPart targetTask = (TaskEditPart) creq.getDragTarget();

                TaskEditPart taskEditPart = getTaskEditPart();
                TaskAdapter taskAdapter = taskEditPart.getActivityAdapter();

                if (targetTask != taskEditPart) {
                    return new EMFCommandWrapper(editingDomain, taskAdapter
                            .getSetReferencedTaskCommand(editingDomain,
                                    targetTask.getActivityAdapter().getId()));
                }
            }

            return UnexecutableCommand.INSTANCE;
        }
        return null;
    }

    @Override
    protected List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos() {
        List<AbstractClickOrDragGadgetInfo> gadgetInfos =
                new ArrayList<AbstractClickOrDragGadgetInfo>();

        TaskEditPart taskEditPart = getTaskEditPart();
        TaskAdapter taskAdapter = taskEditPart.getActivityAdapter();

        Image gadgetImage =
                ProcessWidgetPlugin.getDefault().getImageRegistry()
                        .get(ProcessWidgetConstants.IMG_TOOL_REFERENCETASK_16);

        if (TaskType.REFERENCE_LITERAL.equals(taskAdapter.getTaskType())) {
            TaskEditPart refdTask = null;
            String refdTaskId = taskAdapter.getReferencedTask();

            if (refdTaskId != null && refdTaskId.length() > 0
                    && taskAdapter.isReferencedTaskLocal()) {

                Collection<BaseGraphicalEditPart> tasks =
                        EditPartUtil.getAllActivitiesInProcess(taskEditPart
                                .getParentProcess(),
                                EditPartUtil.ACTIVITY_FILTER_TASKS);

                for (BaseGraphicalEditPart ep : tasks) {
                    if (ep instanceof TaskEditPart) {
                        TaskEditPart taskEP = (TaskEditPart) ep;

                        if (refdTaskId.equals(taskEP.getActivityAdapter()
                                .getId())) {
                            refdTask = taskEP;
                            break;
                        }
                    }
                }
            }

            if (refdTask != null) {
                // Loca reference is set so create gadget to drag jump to local
                // ref task or select new one.
                String desc =
                        String
                                .format(Messages.ClickOrDragTaskReferencePolicy_ClickGotoRefTask_tooltip,
                                        refdTask.getActivityAdapter().getName());

                gadgetInfos.add(new CrossReferenceClickOrDragGadgetInfo(
                        CLICK_OR_DRAG_REF_TASK_GADGET_TYPE, refdTask, desc,
                        gadgetImage,
                        BpmnClickOrDragGadgetAnchorFactory.INSTANCE));

            } else {
                // If it's not set at all create gadget to allow it to be
                // drag-set to local task.
                String desc =
                        Messages.ClickOrDragTaskReferencePolicy_DragToSetRefTask_tooltip;

                gadgetInfos.add(new GenericClickOrDragGadgetInfo(
                        CLICK_OR_DRAG_REF_TASK_GADGET_TYPE, desc, gadgetImage));
            }
        }

        return gadgetInfos;
    }

    private TaskEditPart getTaskEditPart() {
        EditPart host = getHost();
        if (!(host instanceof TaskEditPart)) {
            throw new RuntimeException("Host EditPart must be TaskEditPart"); //$NON-NLS-1$
        }
        return (TaskEditPart) host;
    }

}
