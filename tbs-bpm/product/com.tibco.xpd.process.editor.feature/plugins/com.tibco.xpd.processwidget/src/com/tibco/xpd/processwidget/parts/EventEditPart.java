/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.commands.SetEventNameCommand;
import com.tibco.xpd.processwidget.figures.EventFigure;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.figures.anchors.CircumferenceAnchor;
import com.tibco.xpd.processwidget.figures.anchors.EventActivityAnchor;
import com.tibco.xpd.processwidget.figures.anchors.NESWAnchor;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.ClickOrDragLinkEventPolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragReplyActivityPolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragSignalEventPolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragThrowFaultEventPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.processwidget.policies.cycletypegadget.CycleEventTypeGadgetPolicy;
import com.tibco.xpd.processwidget.tools.AutoSizingDirectEditLocator;
import com.tibco.xpd.processwidget.tools.FlowConnectionToolEntry;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Representation of BPMN's Event. All types of Events are represented by this
 * Edit Part
 * 
 * @author wzurek
 */
public class EventEditPart extends BaseFlowNodeEditPart implements
        HoverProvider, NodeEditPart {

    // unless fixed location.

    private ConnectionAnchor associationAnchor;

    private boolean attachToBorderOnLastRefresh = false;

    private int triggerTypeOnLastRefresh = -1;

    private ActivityHyperLinkListener activityHyperLinkListener =
            new ActivityHyperLinkListener(this);

    public EventEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.event_sel"); //$NON-NLS-1$
    }

    @Override
    public Translatable translateToModel(Translatable t) {
        ShapeWithDescriptionFigure f = (ShapeWithDescriptionFigure) getFigure();
        Point offset = f.getReferencePointOffset();
        t.performTranslate(offset.x, offset.y);
        return t;
    }

    public EventAdapter getEventAdapter() {
        return (EventAdapter) getModelAdapter();
    }

    @Override
    protected IFigure createFigure() {
        EventFigure fig = new EventFigure();
        fig.setEllipseSize(20, 1);
        fig.setToolTip(new TooltipFigure(this));
        return fig;
    }

    /**
     * Override default bounds based locator with locator thats take care of
     * bounds of the text instead of the whole activity
     */
    @Override
    protected CellEditorLocator getDirectEditEditorLocator() {
        return new AutoSizingDirectEditLocator() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.processwidget.parts.AutoSizingDirectEditLocator
             * #getTextBoundsLocation()
             */
            @Override
            public Rectangle getTextBoundsLocation() {
                IFigure tfig =
                        ((ShapeWithDescriptionFigure) getFigure())
                                .getTextFigure();
                Rectangle textBnds = tfig.getBounds().getCopy();

                tfig.translateToAbsolute(textBnds);

                return textBnds;
            }

            @Override
            public int getDesiredWidth() {
                IFigure tfig =
                        ((ShapeWithDescriptionFigure) getFigure())
                                .getTextFigure();
                Dimension size = tfig.getSize();
                return size.width;
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getDirectEditStyle
     * ()
     */
    @Override
    protected int getDirectEditStyle() {
        return SWT.WRAP | SWT.CENTER;
    }

    /**
     * Get the event flow type
     * 
     * @return EventFlowType.FLOW_START | FLOW_INTERMEDIATE | FLOW_END
     */
    public int getEventFlowType() {
        return getEventAdapter().getFlowType().getValue();
    }

    /**
     * get event trigger type.
     * 
     * @return EventTriggerType.xxxxx
     */
    public int getEventTriggerType() {
        return getEventAdapter().getEventTriggerType().getValue();
    }

    /**
     * Clone event figure.
     * 
     * @return The cloned event figure.
     */
    public IFigure cloneFigure() {

        EventAdapter ea = getEventAdapter();

        if (ea != null && ea.getProcess() != null) {

            IFigure f = createFigure();

            setupVisualAppearance((EventFigure) f, getEventAdapter());

            return f;
        }

        return null;
    }

    @Override
    protected void refreshVisuals() {
        EventFigure f = (EventFigure) getFigure();

        /*
         * Unset the figure used as hyperlink listener (the icon fig) in case we
         * end up without one.
         */
        activityHyperLinkListener.setHyperlinkFigure(null);

        boolean nowAttachedToBorder = isAttachedToTaskBorder();

        EventAdapter ea = getEventAdapter();
        f.setText(ea.getName());

        TaskEditPart borderAttachmentTask = getBorderAttachmentTask();
        if (borderAttachmentTask != null) {
            f.setAttachedToTaskFigure((TaskFigure) borderAttachmentTask
                    .getFigure());
        } else {
            f.setAttachedToTaskFigure(null);
        }

        /*
         * Set up visual appearance of the event figure.
         */
        setupVisualAppearance(f, ea);

        int triggerType = ea.getEventTriggerType().getValue();

        /*
         * If we have an icon figure then make it the hyperlink action figure.
         * The hyperlink is handled via contribution to
         * activityHyperLinkProvider extension point.
         */
        if (f.getIconFigure() != null) {
            activityHyperLinkListener.setHyperlinkFigure(f.getIconFigure());
        }

        IFigure p = f.getParent();
        if (p != null) {
            Rectangle r = EditPartUtil.getModelBounds(this);
            Dimension preferredSize = f.getPreferredSize();

            Point center = r.getCenter();
            Point refPtOffset = f.getReferencePointOffset();

            r.x = center.x - refPtOffset.x;
            r.y = center.y - refPtOffset.y;

            r.setSize(preferredSize);

            LayoutManager layout = p.getLayoutManager();
            if (!r.equals(layout.getConstraint(f))) {
                layout.setConstraint(f, r);
                layout.layout(p);
                p.revalidate();
            }
        }

        if (nowAttachedToBorder != attachToBorderOnLastRefresh
                || triggerTypeOnLastRefresh != triggerType) {
            // Need to refresh outgoing associations (could be change of router
            // type for compensation association.
            List connections = getSourceConnections();

            for (Iterator iter = connections.iterator(); iter.hasNext();) {
                Object conn = iter.next();

                if (conn instanceof AssociationEditPart) {
                    AssociationEditPart assEP = (AssociationEditPart) conn;

                    assEP.refreshVisuals();
                }
            }
        }

        attachToBorderOnLastRefresh = nowAttachedToBorder;
        triggerTypeOnLastRefresh = triggerType;
    }

    /**
     * Setup the visual appearance of the specified event figure according to
     * the event adapter.
     * 
     * @param f
     *            The event figure.
     * @param ea
     *            The event adapter.
     */
    private void setupVisualAppearance(EventFigure f, EventAdapter ea) {
        switch (getEventFlowType()) {
        default:
        case EventFlowType.FLOW_START:
            // f.setEventType(EventFigure.EVENT_TYPE_START);
            f.setEllipseSize(ProcessWidgetConstants.START_EVENT_SIZE, 1);
            f.setInnerEllipseSize(0, 0);
            if (ea.isNonInterruptingEvent()) {
                f.setLineDashing(true);
            } else {
                f.setLineDashing(false);
            }
            f.repaint();

            break;

        case EventFlowType.FLOW_INTERMEDIATE:
            f.setEllipseSize(ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE, 1);
            f.setInnerEllipseSize(ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE - 4,
                    1);

            if (ea.isNonCancellingEvent()) {
                f.setLineDashing(true);
            } else {
                f.setLineDashing(false);
            }

            break;
        case EventFlowType.FLOW_END:
            f.setEllipseSize(ProcessWidgetConstants.END_EVENT_SIZE - 2, 3);
            f.setInnerEllipseSize(0, 0);
            f.setLineDashing(false);

            break;
        }

        int triggerType = ea.getEventTriggerType().getValue();

        /*
         * Sid XPD-2516: Allow overrides from processEditorConfiguration
         * extension point.
         */
        ProcessEditorObjectType processEditorObjectType =
                ea.getEventTriggerType()
                        .getProcessEditorObjectType(ea.getFlowType());

        Image overrideImage =
                getActivityIconOverride(processEditorObjectType, getModel());

        if (overrideImage != null) {
            f.setImage(overrideImage, 0, 0);

        } else {
            ImageRegistry ir =
                    ProcessWidgetPlugin.getDefault().getImageRegistry();

            switch (triggerType) {
            case EventTriggerType.EVENT_CANCEL:
                if (EventFlowType.FLOW_INTERMEDIATE == getEventFlowType()) {
                    f.setImage(ir
                            .get(ProcessWidgetConstants.IMG_CANCEL_EVENT_CATCH_ICON),
                            0,
                            1);
                } else {
                    f.setImage(ir
                            .get(ProcessWidgetConstants.IMG_CANCEL_EVENT_THROW_ICON));
                }
                break;
            case EventTriggerType.EVENT_COMPENSATION_CATCH:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_COMPENSATION_EVENT_CATCH_ICON));
                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_COMPENSATION_EVENT_THROW_ICON));
                break;
            case EventTriggerType.EVENT_ERROR:
                if (EventFlowType.FLOW_INTERMEDIATE == getEventFlowType()) {
                    f.setImage(ir
                            .get(ProcessWidgetConstants.IMG_ERROR_EVENT_CATCH_ICON),
                            1,
                            1);
                } else {
                    f.setImage(ir
                            .get(ProcessWidgetConstants.IMG_ERROR_EVENT_THROW_ICON),
                            1,
                            0);
                }
                break;
            case EventTriggerType.EVENT_LINK_CATCH:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_LINK_EVENT_CATCH_ICON),
                        1,
                        0);
                break;
            case EventTriggerType.EVENT_LINK_THROW:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_LINK_EVENT_THROW_ICON),
                        1,
                        0);
                break;
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_MESSAGE_EVENT_CATCH_ICON));
                break;
            case EventTriggerType.EVENT_MESSAGE_THROW:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_MESSAGE_EVENT_THROW_ICON));
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_MULTIPLE_EVENT_CATCH_ICON),
                        1,
                        0);
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_MULTIPLE_EVENT_THROW_ICON),
                        1,
                        0);
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_CONDITIONAL_EVENT_ICON));
                break;
            case EventTriggerType.EVENT_TERMINATE:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_TERMINATE_EVENT_ICON),
                        1,
                        0);
                break;
            case EventTriggerType.EVENT_TIMER:
                f.setImage(ir.get(ProcessWidgetConstants.IMG_TIMER_EVENT_ICON));
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_SIGNAL_EVENT_CATCH_ICON));
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_SIGNAL_EVENT_THROW_ICON));
                break;

            case EventTriggerType.EVENT_NONE:
                /*
                 * Sid ACE-2019: If this is an incoming request activity then
                 * use the alternative centre icon. That is - all intermediate
                 * incoming request events and start request events inside event
                 * sub-processes
                 */
                BaseGraphicalEditPart parentEditPart = getParentLaneOrTask();

                if (EventFlowType.FLOW_INTERMEDIATE == getEventFlowType()
                        || (EventFlowType.FLOW_START == getEventFlowType() && parentEditPart instanceof TaskEditPart
                                && ((TaskEditPart) parentEditPart).isEventSubProc())) {
                    f.setImage(ir.get(ProcessWidgetConstants.IMG_INCOMING_REQUEST_EVENT_ICON));
                }
                break;

            default:
                f.setImage(null);
            }
        }

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(ea);

        // Get colors for object.
        WidgetRGB fillColor =
                colors.getGraphicalNodeColor(ea, getFillColorIDForPartType());
        WidgetRGB borderColor =
                colors.getGraphicalNodeColor(ea, getLineColorIDForPartType());

        f.setBackgroundColor(fillColor.getColor());
        f.setForegroundColor(borderColor.getColor());
    }

    @Override
    protected void createEditPolicies() {

        super.createEditPolicies();

        // SID - we now use new gadgets for highlighting AND selecting
        // references (on selected object) so no need for old HighlightRefPolicy
        //
        // installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
        // new EventRefHighlightPolicy());

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
            @Override
            protected org.eclipse.gef.commands.Command getDirectEditCommand(
                    DirectEditRequest request) {
                EventEditPart eventEP = (EventEditPart) getHost();
                EventAdapter ea = eventEP.getEventAdapter();

                EditingDomain editingDomain =
                        AdapterFactoryEditingDomain.getEditingDomainFor(ea
                                .getTarget());

                String val = (String) request.getCellEditor().getValue();

                return new EMFCommandWrapper(editingDomain, SetEventNameCommand
                        .create(editingDomain,
                                (EObject) eventEP.getModel(),
                                val));
            }

            @Override
            protected void showCurrentEditValue(DirectEditRequest request) {
            }

        });

        return;
    }

    @Override
    protected void createClickOrDragGadgetPolicies() {
        ProcessWidget processWidget = getProcessWidget();

        // Install the change type policy before the standard ones (so that it
        // always stays in the same place visually
        installEditPolicy(CycleEventTypeGadgetPolicy.EDIT_POLICY_ID,
                new CycleEventTypeGadgetPolicy(getAdapterFactory(),
                        getEditingDomain(), processWidget
                                .getEditPolicyEnablementProvider()));

        super.createClickOrDragGadgetPolicies();

        installEditPolicy(ClickOrDragLinkEventPolicy.EDIT_POLICY_ID,
                new ClickOrDragLinkEventPolicy(getAdapterFactory(),
                        getEditingDomain(), processWidget
                                .getEditPolicyEnablementProvider()));

        installEditPolicy(ClickOrDragSignalEventPolicy.EDIT_POLICY_ID,
                new ClickOrDragSignalEventPolicy(getAdapterFactory(),
                        getEditingDomain(), processWidget
                                .getEditPolicyEnablementProvider()));


        /*
         * Sid ACE-1350 we don't do incoming request/reply activity anymore for
         * ACE so hide the gadget enablement options we no longer support
         */
        if (false) {
            installEditPolicy(ClickOrDragReplyActivityPolicy.EDIT_POLICY_ID,
                    new ClickOrDragReplyActivityPolicy(getAdapterFactory(),
                            getEditingDomain(),
                            processWidget.getEditPolicyEnablementProvider()));

            installEditPolicy(ClickOrDragThrowFaultEventPolicy.EDIT_POLICY_ID,
                    new ClickOrDragThrowFaultEventPolicy(getAdapterFactory(),
                            getEditingDomain(),
                            processWidget.getEditPolicyEnablementProvider()));
        }
        return;
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refresh();
    }

    @Override
    public HoverInfo getHoverInfo() {
        HoverInfo info = new HoverInfo(Messages.EventEditPart_Hover_tooltip);
        EventAdapter eventAdapter = getEventAdapter();

        String name;
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            name =
                    String.format("%s (%s)", eventAdapter.getName(), eventAdapter.getTokenName()); //$NON-NLS-1$
        } else {
            name = eventAdapter.getName();
        }

        if (name != null && name.length() > 0) {
            info.addProperty(Messages.EventEditPart_HoverName_label, name);
        }

        info.addProperty(Messages.EventEditPart_HoverType_label, eventAdapter
                .getFlowType().toString());

        if (eventAdapter.getFlowType().equals(EventFlowType.FLOW_END_LITERAL)) {
            info.addProperty(Messages.EventEditPart_HoverResult_label,
                    eventAdapter.getEventTriggerType().toString());
        } else {
            info.addProperty(Messages.EventEditPart_HoverTrigger_label,
                    eventAdapter.getEventTriggerType().toString());
        }

        if (EventTriggerType.EVENT_ERROR_LITERAL.equals(eventAdapter
                .getEventTriggerType())) {
            String errorCode = eventAdapter.getErrorCode();
            if (errorCode == null) {
                errorCode = ""; //$NON-NLS-1$
            }

            if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(eventAdapter
                    .getFlowType())) {
                if (errorCode.length() < 1) {
                    errorCode =
                            Messages.EventEditPart_HoverTrigger_catchalllabel;
                }

                info.addProperty(Messages.EventEditPart_HoverTrigger_catcherrorcodelabel,
                        errorCode);
            } else {
                info.addProperty(Messages.EventEditPart_HoverTrigger_throwerrorcodelabel,
                        errorCode);

            }

        } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                .equals(eventAdapter.getEventTriggerType())) {
            String signalName = eventAdapter.getSignalName();
            if (signalName == null) {
                signalName = ""; //$NON-NLS-1$
            }

            if (signalName.length() < 1) {
                signalName = Messages.EventEditPart_CatchAllSignals_label;
            }

            info.addProperty(Messages.EventEditPart_CatchSignal_label,
                    signalName);

        } else if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                .equals(eventAdapter.getEventTriggerType())) {
            String signalName = eventAdapter.getSignalName();
            if (signalName == null) {
                signalName = ""; //$NON-NLS-1$
            }

            info.addProperty(Messages.EventEditPart_ThrowSignal_label,
                    signalName);

        } else if (EventTriggerType.EVENT_TIMER_LITERAL.equals(eventAdapter
                .getEventTriggerType())) {

            info.addProperty(eventAdapter.getTriggerTimerType(),
                    eventAdapter.getDeadlineCondition());

        }

        info.setDocumentationURL(eventAdapter.getDocumentationURL(), this);
        return info;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchorFromEditPart(connection, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getXpdTargetConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchorFromEditPart(connection, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdSourceConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdTargetConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public ConnectionAnchor getXpdTargetConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, false);
    }

    /**
     * Get the appropriate source/target connection anchor according to the
     * given connection edit part.
     * 
     * @return
     */
    private ConnectionAnchor getAnchorFromEditPart(
            ConnectionEditPart connection, boolean isSource) {
        ConnectionAnchor anchor = null;

        IFigure fig = ((ShapeWithDescriptionFigure) getFigure()).getShape();

        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        if (connection instanceof BaseConnectionEditPart) {
            Double fixedPos;

            if (isSource) {
                fixedPos =
                        ((BaseConnectionEditPart) connection)
                                .getStartAnchorPosition();
            } else {
                fixedPos =
                        ((BaseConnectionEditPart) connection)
                                .getEndAnchorPosition();
            }

            if (fixedPos != null) {
                anchor = new CircumferenceAnchor(fig, fixedPos);
            }
        }

        if (anchor == null) {
            // Not a fixed line anchor location
            boolean isFromCatchCompensation = false;
            if (!isSource && connection.getSource() instanceof EventEditPart) {
                EventEditPart ev = (EventEditPart) connection.getSource();
                if (ev.isAttachedToTaskBorder()) {
                    if (EventFlowType.FLOW_INTERMEDIATE == ev
                            .getEventFlowType()
                            && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                    .getEventTriggerType()) {
                        isFromCatchCompensation = true;
                    }
                }
            }

            if (connection instanceof AssociationEditPart
                    && !isFromCatchCompensation) {
                // XPDL21 Investigate whats with event Compensation catch
                if (isSource
                        && isAttachedToTaskBorder()
                        && getEventTriggerType() == EventTriggerType.EVENT_COMPENSATION_CATCH
                        && connection.getTarget() instanceof BaseFlowNodeEditPart) {
                    anchor =
                            new EventActivityAnchor((EventFigure) getFigure(),
                                    isSource);

                } else {
                    if (associationAnchor == null) {
                        associationAnchor = new EllipseAnchor(fig);
                    }
                    anchor = associationAnchor;
                }

            } else {
                anchor =
                        new EventActivityAnchor((EventFigure) getFigure(),
                                isSource);

            }
        }

        return anchor;
    }

    /**
     * Get the appropriate source/target connection anchor according to the
     * given create/retarget connection request.
     * 
     * @return
     */
    private ConnectionAnchor getAnchorFromRequest(Request request,
            boolean isSource) {
        ConnectionAnchor anchor = null;

        IFigure fig = ((ShapeWithDescriptionFigure) getFigure()).getShape();

        if (request instanceof DropRequest) {

            // If it's on border of event use a circumference anchor
            // for specific attachment position to border
            Point location = null;

            if (isSource && request instanceof CreateConnectionRequest) {
                // We may be asked for start connection anchor many times,
                // we have to use the original location clicked NOT the
                // current location whilst drag line is in progress.
                location =
                        (Point) request
                                .getExtendedData()
                                .get(FlowConnectionToolEntry.REQ_EXT_DATA_INITIAL_LOCATION);
            }

            if (location == null) {
                location = ((DropRequest) request).getLocation();
            }

            // We'll count this as a fixed anchor if the mouse is within a
            // few pixels of radius
            // We'll count this as a fixed anchor if the mouse is within a
            // few pixels of any line
            Point relLocation = location.getCopy();
            fig.translateToRelative(relLocation);

            if (((ShapeWithDescriptionFigure) getFigure())
                    .borderContainsPoint(relLocation)) {

                anchor = new CircumferenceAnchor(fig, location);

            }

        }

        if (anchor == null) {
            // Not a fixed position anchor so get the default positioning one
            // appropriate to connection type.
            boolean isFromCatchCompensation = false;

            if (!isSource && request instanceof CreateConnectionRequest) {
                CreateConnectionRequest creq =
                        (CreateConnectionRequest) request;

                if (creq.getSourceEditPart() instanceof EventEditPart) {
                    EventEditPart ev = (EventEditPart) creq.getSourceEditPart();
                    if (ev.isAttachedToTaskBorder()) {
                        if (EventFlowType.FLOW_INTERMEDIATE == ev
                                .getEventFlowType()
                                && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                        .getEventTriggerType()) {
                            isFromCatchCompensation = true;
                        }
                    }
                }
            }
            // For associations use a plain chop box anchor.
            if (!isFromCatchCompensation
                    && (((request instanceof CreateConnectionRequest) && ((CreateConnectionRequest) request)
                            .getNewObjectType() == AssociationAdapter.class) || ((request instanceof ReconnectRequest) && ((ReconnectRequest) request)
                            .getConnectionEditPart() instanceof AssociationEditPart))) {

                EditPart targetEditPart;

                if (request instanceof CreateConnectionRequest) {
                    targetEditPart =
                            ((CreateConnectionRequest) request)
                                    .getTargetEditPart();
                } else {
                    targetEditPart = ((ReconnectRequest) request).getTarget();
                }

                if (isAttachedToTaskBorder()
                        && getEventTriggerType() == EventTriggerType.EVENT_COMPENSATION_CATCH
                        && targetEditPart instanceof BaseFlowNodeEditPart) {
                    anchor =
                            new EventActivityAnchor((EventFigure) getFigure(),
                                    isSource);

                } else {
                    if (associationAnchor == null) {
                        associationAnchor = new EllipseAnchor(fig);
                    }

                    anchor = associationAnchor;
                }
            }
            // For sequence / message flow use special activity anchor
            else {
                anchor =
                        new EventActivityAnchor((EventFigure) getFigure(),
                                isSource);
            }
        }

        return anchor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        String colorID = null;

        switch (getEventFlowType()) {
        case EventFlowType.FLOW_INTERMEDIATE:
            colorID = ProcessWidgetColors.INTERMEDIATE_EVENT_FILL;
            break;
        case EventFlowType.FLOW_END:
            colorID = ProcessWidgetColors.END_EVENT_FILL;
            break;
        case EventFlowType.FLOW_START:
        default:
            colorID = ProcessWidgetColors.START_EVENT_FILL;
            break;
        }

        return colorID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        String colorID = null;

        switch (getEventFlowType()) {
        case EventFlowType.FLOW_INTERMEDIATE:
            colorID = ProcessWidgetColors.INTERMEDIATE_EVENT_LINE;
            break;
        case EventFlowType.FLOW_END:
            colorID = ProcessWidgetColors.END_EVENT_LINE;
            break;
        case EventFlowType.FLOW_START:
        default:
            colorID = ProcessWidgetColors.START_EVENT_LINE;
            break;
        }

        return colorID;
    }

    /**
     * Get the event object edit part that this link event is linked to. If this
     * edit part is not a link event or it has not been linked to another link
     * event then null is returned.
     * 
     */
    public EventEditPart getLinkedToEditPart() {
        EventEditPart eventEP = null;

        EventAdapter adp = getEventAdapter();

        if (EventTriggerType.EVENT_LINK_THROW_LITERAL.equals(adp
                .getEventTriggerType())) {
            String eventId = adp.getLinkEventId();

            if (eventId != null) {

                ProcessDiagramAdapter procAdapter =
                        (ProcessDiagramAdapter) adp.getAdapterFactory()
                                .adapt(adp.getProcess(),
                                        ProcessWidgetConstants.ADAPTER_TYPE);

                Object eventObj = procAdapter.getModelObjectById(eventId);
                if (eventObj != null) {
                    EditPart ep =
                            (EditPart) getViewer().getEditPartRegistry()
                                    .get(eventObj);

                    if (ep instanceof EventEditPart) {
                        eventEP = (EventEditPart) ep;
                    }
                }
            }
        }

        return eventEP;
    }

    /**
     * Get a list of objects that this event links to by reference for purpose
     * of hover feedback (i.e. link events show line to event linked to)
     * 
     * @return
     */
    private Collection<EventEditPart> getLinkFeedbackDestinations() {
        List<EventEditPart> returnEventParts = new ArrayList<EventEditPart>();

        EventAdapter thisEventAdp = getEventAdapter();
        if (EventTriggerType.EVENT_LINK_CATCH_LITERAL.equals(thisEventAdp
                .getEventTriggerType())) {
            EventEditPart linkPart = getLinkedToEditPart();
            if (linkPart != null) {
                returnEventParts.add(linkPart);
            }
        } else if (EventTriggerType.EVENT_ERROR_LITERAL.equals(thisEventAdp
                .getEventTriggerType())) {

            boolean thisIsTaskEvent = isAttachedToTaskBorder();

            String thisErrorCode = thisEventAdp.getErrorCode();
            if (thisErrorCode == null) {
                thisErrorCode = ""; //$NON-NLS-1$
            }

            // If in-flow event with no error code the it has no links.
            // (Attach to border events with no errorcode link to ALL
            // in-flow events with the same errorcode.
            if (thisErrorCode.length() > 0 || thisIsTaskEvent) {

                // Find all the other error events with the same error code.
                ProcessDiagramAdapter procAdapter =
                        (ProcessDiagramAdapter) thisEventAdp
                                .getAdapterFactory()
                                .adapt(thisEventAdp.getProcess(),
                                        ProcessWidgetConstants.ADAPTER_TYPE);

                List<NamedElementAdapter> eventList =
                        procAdapter
                                .getActivityList(ProcessDiagramAdapter.ACTIVITY_LIST_EVENTS);

                for (Iterator iter = eventList.iterator(); iter.hasNext();) {
                    NamedElementAdapter nameAdp =
                            (NamedElementAdapter) iter.next();
                    boolean bInclude = false;

                    if (nameAdp instanceof EventAdapter) {
                        EventAdapter eventAdp = (EventAdapter) nameAdp;

                        if (EventTriggerType.EVENT_ERROR_LITERAL
                                .equals(eventAdp.getEventTriggerType())) {

                            // If our event is attached to task border then only
                            // want in-flow errors.
                            if (thisIsTaskEvent) {
                                if (!eventAdp.isAttachedToTask()) {
                                    // When attached error event has no
                                    // errorcode then it links to all.
                                    if (thisErrorCode.length() == 0
                                            || thisErrorCode.equals(eventAdp
                                                    .getErrorCode())) {
                                        bInclude = true;
                                    }
                                }
                            } else {
                                // If our event is in-flow then only want task
                                // border events that will pick up this error.
                                if (eventAdp.isAttachedToTask()) {
                                    String otherErrorCode =
                                            eventAdp.getErrorCode();

                                    // When attached error event has no
                                    // errorcode then it links to all.
                                    if (otherErrorCode == null
                                            || otherErrorCode.length() < 1
                                            || thisErrorCode
                                                    .equals(otherErrorCode)) {
                                        bInclude = true;
                                    }

                                }
                            }
                        }
                        // If we have decided to include the event in links to
                        // feedback then add it to return list.
                        if (bInclude) {
                            Object modelObj = eventAdp.getTarget();

                            EditPart ep =
                                    (EditPart) getViewer()
                                            .getEditPartRegistry()
                                            .get(modelObj);

                            if (ep instanceof EventEditPart && ep != this) {
                                returnEventParts.add((EventEditPart) ep);
                            }

                        }
                    }

                }
            }
        }

        return returnEventParts;
    }

    /**
     * Is this event (intermediate) attached to border of a task.
     * 
     * @return true if intermediate event attached to task border
     */
    public boolean isAttachedToTaskBorder() {
        return getEventAdapter().isAttachedToTask();
    }

    /**
     * Get the edit part for the task that this event is attached to.
     * 
     * @return Edit part of task that this event is attached to or <b>null</b>
     *         if not attached to border.
     */
    public TaskEditPart getBorderAttachmentTask() {
        TaskEditPart ret = null;

        Object task = getEventAdapter().getBorderAttachmentTask();

        if (task != null) {
            EditPart ep =
                    (EditPart) getViewer().getEditPartRegistry().get(task);

            if (ep instanceof TaskEditPart) {
                ret = (TaskEditPart) ep;
            }
        }

        return ret;
    }

    private class AttachedBorderNESWAnchor extends NESWAnchor {

        /**
         * 
         */
        public AttachedBorderNESWAnchor(IFigure fig) {
            super(fig);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processwidget.figures.anchors.NESWAnchor#
         * getConnectionAnchorDirection(org.eclipse.draw2d.geometry.Point)
         */
        @Override
        public int getConnectionAnchorDirection(Point anchorOrReferencePoint) {
            TaskEditPart attachTask = getBorderAttachmentTask();

            if (isAttachedToTaskBorder() && attachTask != null) {
                // See which directions are available.
                boolean eastAvailable = false;
                boolean westAvailable = false;
                boolean northAvailable = false;
                boolean southAvailable = false;

                Rectangle bnds = getFigure().getBounds();

                Rectangle taskBnds =
                        ((TaskFigure) attachTask.getFigure()).getHandleBounds();

                if (bnds.right() > taskBnds.right()) {
                    eastAvailable = true;
                } else if (bnds.x < taskBnds.x) {
                    westAvailable = true;
                }

                if (bnds.bottom() > taskBnds.bottom()) {
                    southAvailable = true;
                } else if (bnds.y < taskBnds.y) {
                    northAvailable = true;
                }

                int defPos =
                        super.getConnectionAnchorDirection(anchorOrReferencePoint);

                if (defPos == PositionConstants.EAST && eastAvailable
                        || defPos == PositionConstants.WEST && westAvailable
                        || defPos == PositionConstants.NORTH && northAvailable
                        || defPos == PositionConstants.SOUTH && southAvailable) {
                    return defPos;
                }

                // Default direction is not available, pick another.
                if (defPos == PositionConstants.EAST) {
                    if (southAvailable) {
                        return PositionConstants.SOUTH;
                    } else if (northAvailable) {
                        return PositionConstants.NORTH;
                    } else {
                        return PositionConstants.WEST;
                    }
                } else if (defPos == PositionConstants.WEST) {
                    if (southAvailable) {
                        return PositionConstants.SOUTH;
                    } else if (northAvailable) {
                        return PositionConstants.NORTH;
                    } else {
                        return PositionConstants.EAST;
                    }

                } else if (defPos == PositionConstants.NORTH) {
                    if (eastAvailable) {
                        return PositionConstants.EAST;
                    } else if (westAvailable) {
                        return PositionConstants.WEST;
                    } else {
                        return PositionConstants.SOUTH;
                    }

                } else {
                    if (eastAvailable) {
                        return PositionConstants.EAST;
                    } else if (westAvailable) {
                        return PositionConstants.WEST;
                    } else {
                        return PositionConstants.NORTH;
                    }

                }

            } else {
                return super
                        .getConnectionAnchorDirection(anchorOrReferencePoint);
            }
        }
    }

    /**
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#removeChildVisual(org.eclipse.gef.EditPart)
     * 
     * @param childEditPart
     */
    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        /* Detach the hyperlink listener from the figure. */
        if (activityHyperLinkListener != null) {
            activityHyperLinkListener.setHyperlinkFigure(null);
        }

        super.removeChildVisual(childEditPart);
    }
}
