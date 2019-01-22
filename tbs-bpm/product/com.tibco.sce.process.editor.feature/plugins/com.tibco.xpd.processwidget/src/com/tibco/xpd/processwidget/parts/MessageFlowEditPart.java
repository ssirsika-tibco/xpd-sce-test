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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.figures.BpmnFlowRouter;
import com.tibco.xpd.processwidget.figures.MessageFlowFigure;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.AssociationOnlyNodeFlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowConnectionBendpointEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowConnectionEndpointPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;

/**
 * Graphical representation of MessageFlow
 * 
 * Note: Implements NodeEditPart interface because you can connect associations
 * to sequence flow.
 */
public class MessageFlowEditPart extends BaseConnectionEditPart implements
        HoverProvider {

    /**
     * The Constructor
     * 
     * @param adapterFactory
     */
    public MessageFlowEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.message_sel"); //$NON-NLS-1$
    }

    private MessageFlowAdapter getMessageFlow() {
        return (MessageFlowAdapter) getModelAdapter();
    }

    @Override
    protected IFigure createFigure() {
        PolylineConnection fig = new MessageFlowFigure();
        fig.setBackgroundColor(ColorConstants.white);
        fig.setForegroundColor(ColorConstants.darkBlue);

        BpmnFlowRouter r = new BpmnFlowRouter();
        fig.setConnectionRouter(r);

        fig.setToolTip(new TooltipFigure(this));
        return fig;
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();

        // Can now link associations TO sequence flows.
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new AssociationOnlyNodeFlowConnectionEditPolicy(
                        getEditingDomain()));

        installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
                new FlowConnectionBendpointEditPolicy(getAdapterFactory(),
                        getEditingDomain()));
        installEditPolicy(EditPolicy.CONNECTION_ROLE,
                new FlowConnectionEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
                new FlowConnectionEndpointPolicy());

        installEditPolicy("Animation", new ConnectionsAnimatorEditPolicy(getProcessWidget() //$NON-NLS-1$
                                .getEditPolicyEnablementProvider()) {
                    @Override
                    protected IFigure createFeedbackFigure() {

                        ImageFigure f = new ImageFigure();
                        f.setImage(ProcessWidgetPlugin.getDefault()
                                .getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_LETTER));
                        return f;
                    }
                });

    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();
    }

    @Override
    public HoverInfo getHoverInfo() {
        HoverInfo info =
                new HoverInfo(Messages.MessageFlowEditPart_Hover_tooltip);
        MessageFlowAdapter fa = getMessageFlow();

        String name = fa.getName();
        info.addProperty(Messages.MessageFlowEditPart_HoverName_label,
                name != null ? name
                        : Messages.MessageFlowEditPart_HoverNotSet_label);

        NamedElementAdapter s =
                (NamedElementAdapter) fa.getAdapterFactory()
                        .adapt(fa.getSourceNode(),
                                ProcessWidgetConstants.ADAPTER_TYPE);
        NamedElementAdapter d =
                (NamedElementAdapter) fa.getAdapterFactory()
                        .adapt(fa.getTargetNode(),
                                ProcessWidgetConstants.ADAPTER_TYPE);

        info.addProperty(Messages.MessageFlowEditPart_HoverFrom_label,
                s.getName());
        info.addProperty(Messages.MessageFlowEditPart_HoverTo_label,
                d.getName());

        info.setDocumentationURL(fa.getDocumentationURL(), this);
        return info;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        return ProcessWidgetColors.MESSAGE_FLOW_LINE;
    }

    /**
     * Check if message flow connection is valid between the 2 objects and
     * return reason for invalidity if so.
     * 
     * @param connectionSource
     * @param connectionTarget
     * @param currentConnection
     *            Existing connection if it's a reconnect OR null if this is a
     *            new connection
     * 
     * @return String reason if invalid else null if valid.
     */
    public static String isInvalidConnection(
            BaseGraphicalEditPart connectionSource,
            BaseGraphicalEditPart connectionTarget,
            ConnectionEditPart currentConnection) {

        if (connectionSource != null && connectionTarget != null) {
            // Message flows must cross pool boundaries.
            BaseGraphicalEditPart srcParent;

            if (connectionSource instanceof PoolEditPart) {
                srcParent = connectionSource;
            } else {
                srcParent = connectionSource.getParentPool();
            }

            BaseGraphicalEditPart tgtParent;

            if (connectionTarget instanceof PoolEditPart) {
                tgtParent = connectionTarget;
            } else {
                tgtParent = connectionTarget.getParentPool();
            }

            if (srcParent == tgtParent) {

                // If one of the objects is a Pool then don't show error (else
                // it appears as soon as you start creating a message flow)
                if (connectionSource instanceof PoolEditPart
                        || connectionTarget instanceof PoolEditPart) {
                    return ""; //$NON-NLS-1$
                }
                return Messages.MessageFlowEditPart_CannotConenctInSamePool_message;
            }
        }

        // Gateway cannot be source or target of connection.
        if (connectionTarget instanceof GatewayEditPart
                || connectionSource instanceof GatewayEditPart) {
            return Messages.MessageFlowEditPart_CannotConnectGateway_message;
        }

        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseConnectionEditPart#shouldBeVisible
     * ()
     */
    @Override
    protected boolean shouldBeVisible() {
        // Message flows can connect to parent pool when target object is
        // closed.
        return true;
    }

}
