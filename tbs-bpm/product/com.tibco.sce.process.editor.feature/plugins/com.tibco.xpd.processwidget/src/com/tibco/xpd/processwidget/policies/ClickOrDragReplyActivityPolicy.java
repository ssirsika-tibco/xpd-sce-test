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
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.FlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.commands.internal.RevealReferencedEditPartCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetPolicy;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.CrossReferenceClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.GenericClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Click or drag gadget policy for set / goto between request / reply actvitity
 * pairs.
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragReplyActivityPolicy extends
        AbstractClickOrDragGadgetPolicy {

    public static final String EDIT_POLICY_ID =
            "replyActivity.click.gadget.policy"; //$NON-NLS-1$

    private static final String CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE =
            "CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    public ClickOrDragReplyActivityPolicy(AdapterFactory adapterFactory,
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
        if (CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE.equals(clickOrDragInfoType)
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
        if (CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE.equals(clickOrDragInfoType)) {
            //
            // Don't allow select of request activity for a reply activity
            // that's an event implementing a process interface event.
            boolean isImplementingReplyActivity = false;
            if (isImplementingActivity(getHost())) {
                if (isOutgoingReplyTypeActivity(getHost())) {
                    isImplementingReplyActivity = true;
                }
            }

            if (!isImplementingReplyActivity) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Command createGadgetDraggedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {

        if (CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE.equals(clickOrDragInfoType)) {
            // Can never drag from implementing reply activity.
            String requestActId = null;
            EditPart replyActivity = null;

            if (isIncomingRequestTypeActivity(getHost())) {
                //
                // Dragging from a request type activity
                // Can't change request activity of an implementing reply
                // activity...
                if (isOutgoingReplyTypeActivity(creq.getDragTarget())) {
                    if (!isImplementingActivity(creq.getDragTarget())) {
                        //
                        // To an activity capable of being a reply.
                        requestActId = getActvitityId(getHost());
                        replyActivity = creq.getDragTarget();
                    }
                }

            } else if (isOutgoingReplyTypeActivity(getHost())) {
                //
                // Dragging from a reply type activity
                // (Cannot set the request activity of an implementing reply
                // activity.
                if (!isImplementingActivity(getHost())
                        && isIncomingRequestTypeActivity(creq.getDragTarget())) {
                    //
                    // To a request type activity.
                    requestActId = getActvitityId(creq.getDragTarget());
                    replyActivity = getHost();
                }
            }

            if (requestActId != null && replyActivity != null) {
                return getSetRequestActIdCommand(replyActivity, requestActId);
            }

            return UnexecutableCommand.INSTANCE;
        }
        return null;
    }

    @Override
    protected List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos() {
        List<AbstractClickOrDragGadgetInfo> gadgetInfos =
                new ArrayList<AbstractClickOrDragGadgetInfo>();

        if (isIncomingRequestTypeActivity(getHost())) {
            //
            // Create gadgets for selecting reply activities for this
            // request activity.
            Image gadgetImage =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_MESSAGE_EVENT_THROW_ICON);

            Collection<BaseFlowNodeEditPart> replyActs =
                    getReplyActvitiesForRequestActivity(getHost());

            if (!replyActs.isEmpty()) {
                for (BaseGraphicalEditPart replyAct : replyActs) {
                    FlowNodeAdapter adp =
                            ((BaseFlowNodeEditPart) replyAct)
                                    .getFlowNodeAdapter();
                    String desc =
                            String
                                    .format(Messages.ClickOrDragReplyActivityPolicy_ClickToGoToReply_tooltip,
                                            adp.getName());

                    gadgetInfos.add(new CrossReferenceClickOrDragGadgetInfo(
                            CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE, replyAct,
                            desc, gadgetImage,
                            BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
                }
            } else {
                String desc =
                        Messages.ClickOrDragReplyActivityPolicy_DragDropToSelectReply_tooltip;

                gadgetInfos
                        .add(new GenericClickOrDragGadgetInfo(
                                CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE, desc,
                                gadgetImage));
            }

        } else if (isOutgoingReplyTypeActivity(getHost())) {
            //
            // Create gadgets for selecting request activity for this
            // reply activity.
            Image gadgetImage =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_MESSAGE_EVENT_CATCH_ICON);

            BaseFlowNodeEditPart requestAct = getRequestActivity(getHost());
            if (requestAct != null) {
                FlowNodeAdapter adp =
                        ((BaseFlowNodeEditPart) requestAct)
                                .getFlowNodeAdapter();
                String desc =
                        String
                                .format(Messages.ClickOrDragReplyActivityPolicy_ClickToGoToRequest_tooltip,
                                        adp.getName());

                gadgetInfos.add(new CrossReferenceClickOrDragGadgetInfo(
                        CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE, requestAct, desc,
                        gadgetImage,
                        BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
            } else {
                String desc =
                        Messages.ClickOrDragReplyActivityPolicy_DragToSelectRequest_tooltip;

                gadgetInfos
                        .add(new GenericClickOrDragGadgetInfo(
                                CLICK_OR_DRAG_REPLY_ACT_GADGET_TYPE, desc,
                                gadgetImage));
            }

        }

        return gadgetInfos;
    }

    /**
     * @param replyActivity
     * @return The request activity for the given reply activity.
     */
    private BaseFlowNodeEditPart getRequestActivity(EditPart replyActivity) {
        String requestActId = null;

        if (replyActivity instanceof TaskEditPart) {
            TaskAdapter adp =
                    ((TaskEditPart) replyActivity).getActivityAdapter();
            if (adp != null) {
                requestActId = adp.getRequestActivityId();
            }
        } else if (replyActivity instanceof EventEditPart) {
            EventAdapter adp =
                    ((EventEditPart) replyActivity).getEventAdapter();
            if (adp != null) {
                requestActId = adp.getRequestActivityId();
            }
        }

        if (requestActId != null && requestActId.length() > 0) {
            return EditPartUtil
                    .findActivityEditPart(((BaseGraphicalEditPart) getHost())
                            .getParentProcess(), requestActId);

        }

        return null;
    }

    /**
     * @param host
     * @return List of activities that reply to the given request activity.
     */
    private Collection<BaseFlowNodeEditPart> getReplyActvitiesForRequestActivity(
            EditPart requestActivity) {
        Collection<String> replyActIds = null;

        if (requestActivity instanceof TaskEditPart) {
            TaskAdapter adp =
                    ((TaskEditPart) requestActivity).getActivityAdapter();
            if (adp != null) {
                replyActIds = adp.getReplyActivityIds();
            }
        } else if (requestActivity instanceof EventEditPart) {
            EventAdapter adp =
                    ((EventEditPart) requestActivity).getEventAdapter();
            if (adp != null) {
                replyActIds = adp.getReplyActivityIds();
            }
        }

        List<BaseFlowNodeEditPart> replies =
                new ArrayList<BaseFlowNodeEditPart>();
        ProcessEditPart parentProcess =
                ((BaseGraphicalEditPart) getHost()).getParentProcess();

        if (replyActIds != null && replyActIds.size() > 0
                && parentProcess != null) {
            Collection<BaseGraphicalEditPart> activityEPs =
                    EditPartUtil.getAllActivitiesInProcess(parentProcess,
                            EditPartUtil.ACTIVITY_FILTER_EVENTS
                                    | EditPartUtil.ACTIVITY_FILTER_TASKS);
            //
            // For each reply activity
            for (String id : replyActIds) {
                //
                // Find the edit part for it.
                for (BaseGraphicalEditPart act : activityEPs) {
                    if (act instanceof BaseFlowNodeEditPart) {
                        BaseProcessAdapter adp =
                                ((BaseFlowNodeEditPart) act).getModelAdapter();
                        if (adp instanceof NamedElementAdapter) {
                            if (id.equals(((NamedElementAdapter) adp).getId())) {
                                // Add to list of replies.
                                replies.add((BaseFlowNodeEditPart) act);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return replies;
    }

    /**
     * @param dragTarget
     * @param requestActId
     * @return Command to set the given reply activity to reply to given request
     *         activity
     */
    private Command getSetRequestActIdCommand(EditPart replyActivity,
            String requestActId) {

        if (replyActivity instanceof TaskEditPart) {
            TaskAdapter adp =
                    ((TaskEditPart) replyActivity).getActivityAdapter();
            if (adp != null) {
                return new EMFCommandWrapper(
                        editingDomain,
                        adp
                                .getSetRequestActivityForReplyActivityCommand(editingDomain,
                                        requestActId));
            }
        } else if (replyActivity instanceof EventEditPart) {
            EventAdapter adp =
                    ((EventEditPart) replyActivity).getEventAdapter();
            if (adp != null) {
                return new EMFCommandWrapper(editingDomain, adp
                        .getSetRequestActivityCommand(editingDomain,
                                requestActId));

            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param editPart
     * @return true if given edit part represents and actvitity that can receive
     *         incoming requests.
     */
    private boolean isIncomingRequestTypeActivity(EditPart editPart) {
        if (editPart instanceof TaskEditPart) {
            TaskEditPart taskEP = (TaskEditPart) editPart;

            TaskAdapter adapter = taskEP.getActivityAdapter();
            if (adapter != null) {
                if (TaskType.RECEIVE_LITERAL.equals(adapter.getTaskType())) {
                    return true;
                }
            }
        } else if (editPart instanceof EventEditPart) {
            EventEditPart eventEP = (EventEditPart) editPart;

            EventAdapter adapter = eventEP.getEventAdapter();
            if (adapter != null) {
                if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL.equals(adapter
                        .getEventTriggerType())) {
                    if (!EventFlowType.FLOW_END_LITERAL.equals(adapter
                            .getFlowType())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param editPart
     * @return true if given edit part represents and actvitity that could be
     *         configured as a reply actvitiy
     */
    private boolean isOutgoingReplyTypeActivity(EditPart editPart) {
        if (editPart instanceof TaskEditPart) {
            TaskEditPart taskEP = (TaskEditPart) editPart;

            TaskAdapter adapter = taskEP.getActivityAdapter();
            if (adapter != null) {
                if (TaskType.SEND_LITERAL.equals(adapter.getTaskType())) {
                    return true;
                }
            }
        } else if (editPart instanceof EventEditPart) {
            EventEditPart eventEP = (EventEditPart) editPart;

            EventAdapter adapter = eventEP.getEventAdapter();
            if (adapter != null) {
                if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL.equals(adapter
                        .getEventTriggerType())) {
                    if (!EventFlowType.FLOW_START_LITERAL.equals(adapter
                            .getFlowType())) {
                        return true;
                    }
                } else if (EventTriggerType.EVENT_NONE_LITERAL.equals(adapter
                        .getEventTriggerType())) {
                    if (EventFlowType.FLOW_END_LITERAL.equals(adapter
                            .getFlowType())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private String getActvitityId(EditPart ep) {
        if (ep instanceof BaseFlowNodeEditPart) {
            FlowNodeAdapter adp =
                    ((BaseFlowNodeEditPart) ep).getFlowNodeAdapter();
            if (adp != null) {
                return adp.getId();
            }
        }
        return null;
    }

    private boolean isImplementingActivity(EditPart editPart) {
        if (editPart instanceof EventEditPart) {
            EventEditPart eventEditPart = (EventEditPart) editPart;

            EventAdapter adapter = eventEditPart.getEventAdapter();
            if (adapter != null) {
                return adapter.isImplementingEvent();
            }
        }
        return false;
    }

}
