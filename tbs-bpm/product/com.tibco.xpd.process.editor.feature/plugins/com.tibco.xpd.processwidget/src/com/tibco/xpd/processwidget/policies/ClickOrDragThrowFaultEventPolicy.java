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
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
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
 * Click or drag gadget policy for set / goto between request / throw fault
 * message event actvitity pairs.
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragThrowFaultEventPolicy extends
        AbstractClickOrDragGadgetPolicy {

    public static final String EDIT_POLICY_ID =
            "throwFaultEvent.click.gadget.policy"; //$NON-NLS-1$

    private static final String CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE =
            "CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    public ClickOrDragThrowFaultEventPolicy(AdapterFactory adapterFactory,
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
        if (CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE
                .equals(clickOrDragInfoType)
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
        if (CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE
                .equals(clickOrDragInfoType)) {

            if (isEndErrorEventActivity(getHost())) {
                /*
                 * Can drag gadget from end error event as long as it's not
                 * implementing an interface event.
                 */
                if (!isImplementingActivity(getHost())) {
                    return true;
                }
            } else if (isIncomingRequestTypeActivity(getHost())) {
                /*
                 * Can always add new error events to request activity provided
                 * whether host is implementing interface request or not.
                 */
                return true;
            }
        }
        return false;
    }

    @Override
    protected Command createGadgetDraggedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {

        if (CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE
                .equals(clickOrDragInfoType)) {
            // Can never drag from implementing reply activity.
            String requestActId = null;
            EditPart errorEventActivity = null;

            if (isEndErrorEventActivity(getHost())) {
                if (!isImplementingActivity(getHost())) {
                    if (isIncomingRequestTypeActivity(creq.getDragTarget())) {
                        /*
                         * Drag from end error event to incoming request.
                         */
                        requestActId = getActvitityId(creq.getDragTarget());
                        errorEventActivity = getHost();
                    }
                }
            } else if (isIncomingRequestTypeActivity(getHost())) {

                if (isEndErrorEventActivity(creq.getDragTarget())) {
                    if (!isImplementingActivity(creq.getDragTarget())) {
                        /*
                         * Drag from incoming request to end error event.
                         */
                        requestActId = getActvitityId(getHost());
                        errorEventActivity = creq.getDragTarget();
                    }
                }
            }

            if (requestActId != null && errorEventActivity != null) {
                return getSetRequestActIdCommand(errorEventActivity,
                        requestActId);
            }

            return UnexecutableCommand.INSTANCE;
        }
        return null;
    }

    @Override
    protected List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos() {
        List<AbstractClickOrDragGadgetInfo> gadgetInfos =
                new ArrayList<AbstractClickOrDragGadgetInfo>();        
        if (isIncomingRequestTypeActivity(getHost()) && !isDecisionFlow(getHost())) {
            /*
             * Create gadgets for selecting end error event activities for this
             * request activity.
             */
            Image gadgetImage =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_ERROR_EVENT_THROW_ICON);

            Collection<BaseFlowNodeEditPart> errorEventActs =
                    getThrowErrorEventsForRequestActivity(getHost());

            if (!errorEventActs.isEmpty()) {
                for (BaseGraphicalEditPart errorEventAct : errorEventActs) {
                    FlowNodeAdapter adp =
                            ((BaseFlowNodeEditPart) errorEventAct)
                                    .getFlowNodeAdapter();
                    String desc =
                            String
                                    .format(Messages.ClickOrDragThrowFaultEventPolicy_ClickToGoToErrorEvent_tooltip,
                                            adp.getName());

                    gadgetInfos.add(new CrossReferenceClickOrDragGadgetInfo(
                            CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE,
                            errorEventAct, desc, gadgetImage,
                            BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
                }
            } else {
                String desc =
                        Messages.ClickOrDragThrowFaultEventPolicy_ClickToSelectErrorEvent_tooltip;

                gadgetInfos.add(new GenericClickOrDragGadgetInfo(
                        CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE, desc,
                        gadgetImage));
            }

        } else if (isEndErrorEventActivity(getHost())) {
            /*
             * Create gadgets for selecting request activity for this error end
             * event activity.
             */
            Image gadgetImage =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_MESSAGE_EVENT_CATCH_ICON);

            BaseFlowNodeEditPart requestAct = getRequestActivity(getHost());
            if (requestAct != null) {
                FlowNodeAdapter adp = (requestAct).getFlowNodeAdapter();
                String desc =
                        String
                                .format(Messages.ClickOrDragReplyActivityPolicy_ClickToGoToRequest_tooltip,
                                        adp.getName());

                gadgetInfos.add(new CrossReferenceClickOrDragGadgetInfo(
                        CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE, requestAct,
                        desc, gadgetImage,
                        BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
            } else {
                String desc =
                        Messages.ClickOrDragThrowFaultEventPolicy_DragToSelectRequestForThrowError_tooltip;

                gadgetInfos.add(new GenericClickOrDragGadgetInfo(
                        CLICK_OR_DRAG_THROWFAULT_ACT_GADGET_TYPE, desc,
                        gadgetImage));
            }

        }

        return gadgetInfos;
    }
    
    private boolean isDecisionFlow(EditPart editPart) {
        if (editPart instanceof EventEditPart) {
            EventEditPart eventEditPart = (EventEditPart) editPart;
            return ProcessWidgetType.DECISION_FLOW.equals(eventEditPart
                    .getProcessWidgetType());
        }
        return false;
    }

    /**
     * @param errorEventActivity
     * @return The request activity for the given reply activity.
     */
    private BaseFlowNodeEditPart getRequestActivity(EditPart errorEventActivity) {
        String requestActId = null;

        if (errorEventActivity instanceof EventEditPart) {
            EventAdapter adp =
                    ((EventEditPart) errorEventActivity).getEventAdapter();
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
     * @return List of activities that throw fault message errors for the given
     *         request activity.
     */
    private Collection<BaseFlowNodeEditPart> getThrowErrorEventsForRequestActivity(
            EditPart requestActivity) {
        Collection<String> errorEventActIds = null;

        if (requestActivity instanceof TaskEditPart) {
            TaskAdapter adp =
                    ((TaskEditPart) requestActivity).getActivityAdapter();
            if (adp != null) {
                errorEventActIds = adp.getThrowErrorIdsForRequestActivity();
            }
        } else if (requestActivity instanceof EventEditPart) {
            EventAdapter adp =
                    ((EventEditPart) requestActivity).getEventAdapter();
            if (adp != null) {
                errorEventActIds = adp.getThrowErrorIdsForRequestActivity();
            }
        }

        List<BaseFlowNodeEditPart> errorEventEditParts =
                new ArrayList<BaseFlowNodeEditPart>();
        ProcessEditPart parentProcess =
                ((BaseGraphicalEditPart) getHost()).getParentProcess();

        if (errorEventActIds != null && errorEventActIds.size() > 0
                && parentProcess != null) {
            Collection<BaseGraphicalEditPart> activityEPs =
                    EditPartUtil.getAllActivitiesInProcess(parentProcess,
                            EditPartUtil.ACTIVITY_FILTER_EVENTS);
            /*
             * For each reply activity
             */
            for (String id : errorEventActIds) {
                /*
                 * Find the edit part for it.
                 */
                for (BaseGraphicalEditPart act : activityEPs) {
                    if (act instanceof BaseFlowNodeEditPart) {
                        BaseProcessAdapter adp =
                                ((BaseFlowNodeEditPart) act).getModelAdapter();
                        if (adp instanceof NamedElementAdapter) {
                            if (id.equals(((NamedElementAdapter) adp).getId())) {
                                errorEventEditParts
                                        .add((BaseFlowNodeEditPart) act);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return errorEventEditParts;
    }

    /**
     * @param dragTarget
     * @param requestActId
     * @return Command to set the given throw error event activity to reply to
     *         given request activity
     */
    private Command getSetRequestActIdCommand(EditPart errorEventActivity,
            String requestActId) {

        if (errorEventActivity instanceof EventEditPart) {
            EventAdapter adp =
                    ((EventEditPart) errorEventActivity).getEventAdapter();
            if (adp != null) {
                return new EMFCommandWrapper(
                        editingDomain,
                        adp
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

    private boolean isEndErrorEventActivity(EditPart editPart) {
        if (editPart instanceof EventEditPart) {
            EventEditPart eventEditPart = (EventEditPart) editPart;

            EventAdapter adapter = eventEditPart.getEventAdapter();
            if (adapter != null) {
                if (EventFlowType.FLOW_END_LITERAL
                        .equals(adapter.getFlowType())) {
                    if (EventTriggerType.EVENT_ERROR_LITERAL.equals(adapter
                            .getEventTriggerType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
