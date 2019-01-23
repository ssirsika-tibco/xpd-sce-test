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
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.commands.internal.RevealReferencedEditPartCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.AbstractClickOrDragGadgetPolicy;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.CrossReferenceClickOrDragGadgetInfo;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Click / Drag gadget policy for Link Events
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragLinkEventPolicy extends AbstractClickOrDragGadgetPolicy {

    public static final String EDIT_POLICY_ID = "linkEvent.click.gadget.policy"; //$NON-NLS-1$

    private static final String CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE =
            "CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    public ClickOrDragLinkEventPolicy(AdapterFactory adapterFactory,
            EditingDomain editingDomain,
            EditPolicyEnablementProvider policyEnabledmentProvider) {
        super(editingDomain, BpmnClickOrDragGadgetAnchorFactory.INSTANCE,
                policyEnabledmentProvider, EDIT_POLICY_ID);
        this.adapterFactory = adapterFactory;
        this.editingDomain = editingDomain;
    }

    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    @Override
    protected boolean canDrag(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE.equals(clickOrDragInfoType)) {
            return true;
        }
        return false;
    }

    @Override
    protected Command createGadgetClickedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (creq.getClickOrDragGadgetInfo() instanceof CrossReferenceClickOrDragGadgetInfo) {
            if (CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE
                    .equals(clickOrDragInfoType)) {

                CrossReferenceClickOrDragGadgetInfo xRefInfo =
                        (CrossReferenceClickOrDragGadgetInfo) creq
                                .getClickOrDragGadgetInfo();

                return new RevealReferencedEditPartCommand(
                        (GraphicalEditPart) creq.getHostEditPart(), xRefInfo
                                .getReferencedEditPart());
            }
        }
        return null;
    }

    @Override
    protected Command createGadgetDraggedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        Command cmd = null;

        if (creq.getClickOrDragGadgetInfo() instanceof AbstractClickOrDragGadgetInfo) {
            if (CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE
                    .equals(clickOrDragInfoType)) {

                cmd = createDragToLinkEventCommand(creq);
            }
        }
        return cmd;
    }

    /**
     * @param creq
     * @return
     */
    private Command createDragToLinkEventCommand(ClickOrDragGadgetRequest creq) {
        if (creq.getDragTarget() instanceof EventEditPart) {
            EventEditPart dragTargetEvent =
                    (EventEditPart) creq.getDragTarget();

            EventAdapter hostAdp = getEventEditPart().getEventAdapter();
            EventAdapter dragTargetAdp = dragTargetEvent.getEventAdapter();
            ProcessDiagramAdapter processAdp =
                    (ProcessDiagramAdapter) getAdapterFactory().adapt(hostAdp
                            .getProcess(),
                            ProcessWidgetConstants.ADAPTER_TYPE);

            if (hostAdp != null && dragTargetAdp != null && processAdp != null) {

                // Ok we have a drag gadget from one link event to
                // another.
                if (EventTriggerType.EVENT_LINK_THROW_LITERAL.equals(hostAdp
                        .getEventTriggerType())
                        && EventTriggerType.EVENT_LINK_CATCH_LITERAL
                                .equals(dragTargetAdp.getEventTriggerType())) {
                    // Drag from throw onto catch.
                    return new EMFCommandWrapper(getEditingDomain(), hostAdp
                            .getSetEventLinkIdCommand(getEditingDomain(),
                                    dragTargetAdp.getId(),
                                    processAdp.getId()));

                } else if (EventTriggerType.EVENT_LINK_THROW_LITERAL
                        .equals(dragTargetAdp.getEventTriggerType())
                        && EventTriggerType.EVENT_LINK_CATCH_LITERAL
                                .equals(hostAdp.getEventTriggerType())) {
                    // Drag from catch link onto throw link.
                    return new EMFCommandWrapper(
                            getEditingDomain(),
                            dragTargetAdp
                                    .getSetEventLinkIdCommand(getEditingDomain(),
                                            hostAdp.getId(),
                                            processAdp.getId()));
                }

            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected List<AbstractClickOrDragGadgetInfo> getClickOrDragGadgetInfos() {
        List<AbstractClickOrDragGadgetInfo> gadgetInfos =
                new ArrayList<AbstractClickOrDragGadgetInfo>();

        EventEditPart eventEditPart = getEventEditPart();
        EventAdapter adapter = eventEditPart.getEventAdapter();

        if (EventTriggerType.EVENT_LINK_THROW_LITERAL.equals(adapter
                .getEventTriggerType())) {
            addThrowLinkGadgets(gadgetInfos, eventEditPart, adapter);

        } else if (EventTriggerType.EVENT_LINK_CATCH_LITERAL.equals(adapter
                .getEventTriggerType())) {
            addCatchLinkGadgets(gadgetInfos, eventEditPart, adapter);
        }

        return gadgetInfos;
    }

    /**
     * @param gadgetInfos
     * @param eventEditPart
     * @param adapter
     */
    private void addCatchLinkGadgets(
            List<AbstractClickOrDragGadgetInfo> gadgetInfos,
            EventEditPart eventEditPart, EventAdapter adapter) {
        // Return the link events that reference this event
        // Get a list of all error events in same process.
        Image gadgetImage =
                ProcessWidgetPlugin.getDefault().getImageRegistry()
                        .get(ProcessWidgetConstants.IMG_LINK_EVENT_THROW_ICON);

        Collection<BaseGraphicalEditPart> events =
                EditPartUtil.getAllActivitiesInProcess(eventEditPart
                        .getParentProcess(),
                        EditPartUtil.ACTIVITY_FILTER_EVENTS);

        String thisId = adapter.getId();

        boolean foundThrow = false;

        for (BaseGraphicalEditPart ep : events) {
            if (ep instanceof EventEditPart) {
                EventEditPart sourceLinkEvent = (EventEditPart) ep;

                if (EventTriggerType.EVENT_LINK_THROW == sourceLinkEvent
                        .getEventTriggerType()) {
                    String linkId =
                            sourceLinkEvent.getEventAdapter().getLinkEventId();

                    if (thisId.equals(linkId)) {
                        foundThrow = true;

                        String desc =
                                String
                                        .format(Messages.ClickOrDragEventReferencePolicy_ClickToGoToSourceLink_tooltip,
                                                sourceLinkEvent
                                                        .getEventAdapter()
                                                        .getName());

                        gadgetInfos
                                .add(new ShapeWithDescCrossReferenceClickOrDragGadgetInfo(
                                        CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE,
                                        sourceLinkEvent,
                                        desc,
                                        gadgetImage,
                                        BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
                    }
                }
            }
        }

        if (!foundThrow) {
            String desc =
                    Messages.ClickOrDragEventReferencePolicy_DragDropToSelectThrowLink_tooltip;

            gadgetInfos.add(new ShapeWithDescGenericClickOrDragGadgetInfo(
                    CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE, desc, gadgetImage));
        }
    }

    /**
     * @param gadgetInfos
     * @param eventEditPart
     * @param adapter
     */
    private void addThrowLinkGadgets(
            List<AbstractClickOrDragGadgetInfo> gadgetInfos,
            EventEditPart eventEditPart, EventAdapter adapter) {
        Image gadgetImage =
                ProcessWidgetPlugin.getDefault().getImageRegistry()
                        .get(ProcessWidgetConstants.IMG_LINK_EVENT_CATCH_ICON);

        EventEditPart targetLinkEvent = eventEditPart.getLinkedToEditPart();
        if (targetLinkEvent != null) {
            String desc =
                    String
                            .format(Messages.ClickOrDragEventReferencePolicy_ClickToGoToTargetLink_tooltip,
                                    targetLinkEvent.getEventAdapter().getName());

            gadgetInfos
                    .add(new ShapeWithDescCrossReferenceClickOrDragGadgetInfo(
                            CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE,
                            targetLinkEvent, desc, gadgetImage,
                            BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
        } else {
            String desc =
                    Messages.ClickOrDragEventReferencePolicy_DragToSelectCatchLink_tooltip;

            gadgetInfos.add(new ShapeWithDescGenericClickOrDragGadgetInfo(
                    CLICK_OR_DRAG_LINK_EVENT_GADGET_TYPE, desc, gadgetImage));
        }
    }

    private EventEditPart getEventEditPart() {
        EditPart host = getHost();
        if (!(host instanceof EventEditPart)) {
            throw new RuntimeException("Host EditPart must be EventEditPart"); //$NON-NLS-1$
        }
        return (EventEditPart) host;
    }

}
