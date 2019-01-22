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
 * Click / Drag gadget policy for Signal Events
 * 
 * @author aallway
 * @since 3.2
 */
public class ClickOrDragSignalEventPolicy extends
        AbstractClickOrDragGadgetPolicy {

    public static final String EDIT_POLICY_ID =
            "signalEvent.click.gadget.policy"; //$NON-NLS-1$

    private static final String CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE =
            "CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    public ClickOrDragSignalEventPolicy(AdapterFactory adapterFactory,
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
    protected Command createGadgetClickedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (creq.getClickOrDragGadgetInfo() instanceof CrossReferenceClickOrDragGadgetInfo) {
            if (CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE
                    .equals(clickOrDragInfoType)) {

                CrossReferenceClickOrDragGadgetInfo xRefInfo =
                        (CrossReferenceClickOrDragGadgetInfo) creq
                                .getClickOrDragGadgetInfo();

                return new RevealReferencedEditPartCommand(
                        (GraphicalEditPart) creq.getHostEditPart(),
                        xRefInfo.getReferencedEditPart());
            }
        }
        return null;
    }

    @Override
    protected boolean canDrag(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        if (CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE.equals(clickOrDragInfoType)) {
            return true;
        }
        return false;
    }

    @Override
    protected Command createGadgetDraggedCommand(String clickOrDragInfoType,
            ClickOrDragGadgetRequest creq) {
        Command cmd = null;

        if (creq.getClickOrDragGadgetInfo() instanceof AbstractClickOrDragGadgetInfo) {
            AbstractClickOrDragGadgetInfo clickOrDragInfo =
                    creq.getClickOrDragGadgetInfo();

            if (CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE
                    .equals(clickOrDragInfoType)) {

                return createDragToSignalEventCommand(creq);
            }
        }
        return cmd;
    }

    /**
     * @param creq
     * @return COmmand (if possible) to set a signal reference
     */
    private Command createDragToSignalEventCommand(ClickOrDragGadgetRequest creq) {
        if (creq.getDragTarget() instanceof EventEditPart) {
            EventEditPart dragTargetEvent =
                    (EventEditPart) creq.getDragTarget();

            EventAdapter hostAdp = getEventEditPart().getEventAdapter();
            EventAdapter dragTargetAdp = dragTargetEvent.getEventAdapter();

            if (dragTargetAdp.isLocalSignalEvent()) {
                /*
                 * XPD-7075: Allow drag only if the drag target is a local
                 * signal event.
                 */

                if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL.equals(hostAdp
                        .getEventTriggerType())
                        && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                .equals(dragTargetAdp.getEventTriggerType())) {
                    // Dragging throw onto catch = set catch to catch this
                    // throw.
                    String signalName = hostAdp.getSignalName();
                    if (signalName != null) {
                        return new EMFCommandWrapper(
                                editingDomain,
                                dragTargetAdp
                                        .getSetSignalNameCommand(getEditingDomain(),
                                                signalName));
                    }

                } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(hostAdp.getEventTriggerType())
                        && EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                                .equals(dragTargetAdp.getEventTriggerType())) {
                    // Dragging catch onto throw = set catch to catch thre
                    // target
                    // throw.
                    String signalName = dragTargetAdp.getSignalName();
                    if (signalName != null) {
                        return new EMFCommandWrapper(
                                editingDomain,
                                hostAdp.getSetSignalNameCommand(getEditingDomain(),
                                        signalName));
                    }
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

        if (adapter.isLocalSignalEvent()) {
            /*
             * XPD-7075: Show Click or Drag gadgets only for local signal
             * events.
             */
            if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL.equals(adapter
                    .getEventTriggerType())) {
                addThrowSignalGadgets(gadgetInfos, eventEditPart, adapter);

            } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                    .equals(adapter.getEventTriggerType())) {
                addCatchSignalGadgets(gadgetInfos, eventEditPart, adapter);

            }
        }

        return gadgetInfos;
    }

    /**
     * 
     * @param gadgetInfos
     * @param eventEditPart
     * @param adapter
     */
    private void addCatchSignalGadgets(
            List<AbstractClickOrDragGadgetInfo> gadgetInfos,
            EventEditPart eventEditPart, EventAdapter adapter) {

        String signalName = adapter.getSignalName();
        if (signalName == null) {
            signalName = ""; //$NON-NLS-1$
        }

        Image gadgetImage =
                ProcessWidgetPlugin
                        .getDefault()
                        .getImageRegistry()
                        .get(ProcessWidgetConstants.IMG_SIGNAL_EVENT_THROW_ICON);

        // Check for events that already catch this signal.
        Collection<BaseGraphicalEditPart> events =
                EditPartUtil.getAllActivitiesInProcess(eventEditPart
                        .getParentProcess(),
                        EditPartUtil.ACTIVITY_FILTER_EVENTS);

        boolean someAdded = false;

        for (BaseGraphicalEditPart ep : events) {
            if (ep instanceof EventEditPart
                    && EventTriggerType.EVENT_SIGNAL_THROW == ((EventEditPart) ep)
                            .getEventTriggerType()) {
                EventAdapter targetAdp = ((EventEditPart) ep).getEventAdapter();

                String caughtSignal = targetAdp.getSignalName();

                if ((caughtSignal != null && caughtSignal.length() != 0)
                        && (signalName.length() == 0 || signalName
                                .equals(caughtSignal))) {
                    // Event catches all or specifically catches this thrown
                    // one
                    someAdded = true;

                    String desc =
                            String.format(Messages.ClickOrDragEventReferencePolicy_ClickToGotoSignal_tooltip,
                                    targetAdp.getName());

                    gadgetInfos
                            .add(new ShapeWithDescCrossReferenceClickOrDragGadgetInfo(
                                    CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE, ep,
                                    desc, gadgetImage,
                                    BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
                }
            }
        }

        if (!someAdded) {
            String desc =
                    Messages.ClickOrDragEventReferencePolicy_DragDropToSetSignal_tooltip;

            gadgetInfos.add(new ShapeWithDescGenericClickOrDragGadgetInfo(
                    CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE, desc, gadgetImage));
        }

        return;
    }

    /**
     * 
     * @param gadgetInfos
     * @param eventEditPart
     * @param adapter
     */
    private void addThrowSignalGadgets(
            List<AbstractClickOrDragGadgetInfo> gadgetInfos,
            EventEditPart eventEditPart, EventAdapter adapter) {

        String signalName = adapter.getSignalName();
        if (signalName != null && signalName.length() > 0) {
            Image gadgetImage =
                    ProcessWidgetPlugin
                            .getDefault()
                            .getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_SIGNAL_EVENT_CATCH_ICON);

            // Check for events that already catch this signal.
            Collection<BaseGraphicalEditPart> events =
                    EditPartUtil.getAllActivitiesInProcess(eventEditPart
                            .getParentProcess(),
                            EditPartUtil.ACTIVITY_FILTER_EVENTS);

            boolean someAdded = false;

            for (BaseGraphicalEditPart ep : events) {
                if (ep instanceof EventEditPart
                        && EventTriggerType.EVENT_SIGNAL_CATCH == ((EventEditPart) ep)
                                .getEventTriggerType()) {
                    EventAdapter targetAdp =
                            ((EventEditPart) ep).getEventAdapter();

                    String caughtSignal = targetAdp.getSignalName();
                    if (caughtSignal == null || caughtSignal.length() == 0
                            || signalName.equals(caughtSignal)) {
                        // Event catches all or specifically catches this thrown
                        // one
                        someAdded = true;

                        String desc =
                                String.format(Messages.ClickOrDragEventReferencePolicy_ClickToGoToSignalCatcher_tooltip,
                                        targetAdp.getName());

                        gadgetInfos
                                .add(new ShapeWithDescCrossReferenceClickOrDragGadgetInfo(
                                        CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE,
                                        ep,
                                        desc,
                                        gadgetImage,
                                        BpmnClickOrDragGadgetAnchorFactory.INSTANCE));
                    }
                }
            }

            if (!someAdded) {
                String desc =
                        Messages.ClickOrDragEventReferencePolicy_DragDropToSelectSignal_tooltip;
                gadgetInfos.add(new ShapeWithDescGenericClickOrDragGadgetInfo(
                        CLICK_OR_DRAG_SIGNAL_EVENT_GADGET_TYPE, desc,
                        gadgetImage));
            }
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
