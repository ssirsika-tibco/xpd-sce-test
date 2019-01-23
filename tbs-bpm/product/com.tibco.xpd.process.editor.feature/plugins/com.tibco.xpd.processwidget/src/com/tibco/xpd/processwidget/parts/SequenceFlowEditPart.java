/*
 * 
 */

package com.tibco.xpd.processwidget.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.figures.BpmnFlowRouter;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.AssociationOnlyNodeFlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowConnectionEndpointPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.processwidget.policies.SequenceFlowLayoutEditPolicy;
import com.tibco.xpd.processwidget.policies.sortConnection.AbstractSortAndBendConnectionPolicy;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * SequenceFlow edit part
 * 
 * Note: Implements NodeEditPart interface because you can connect associations
 * to sequence flow.
 */
public class SequenceFlowEditPart extends BaseConnectionEditPart implements
        HoverProvider {

    // SID - point templates moved to FlowConnectionFigure.

    public SequenceFlowEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.flow_sel"); //$NON-NLS-1$
    }

    @Override
    protected IFigure createFigure() {
        PolylineConnection fig = new SequenceFlowFigure();
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

        installEditPolicy(EditPolicy.LAYOUT_ROLE,
                new SequenceFlowLayoutEditPolicy(getAdapterFactory(),
                        getEditingDomain()));

        // Can now link associations TO sequence flows.
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new AssociationOnlyNodeFlowConnectionEditPolicy(
                        getEditingDomain()));

        installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
                new SequenceFlowSortAndBendPolicy(getAdapterFactory(),
                        getEditingDomain()));

        installEditPolicy(EditPolicy.CONNECTION_ROLE,
                new FlowConnectionEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
                new FlowConnectionEndpointPolicy());
        installEditPolicy("Animation", new ConnectionsAnimatorEditPolicy( //$NON-NLS-1$
                getProcessWidget().getEditPolicyEnablementProvider()));
        // installEditPolicy("Hover", new HoverInfoEditPolicy());

    }

    public SequenceFlowAdapter getSequenceFlowAdapter() {
        return (SequenceFlowAdapter) getModelAdapter();

    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();

        SequenceFlowAdapter fa = getSequenceFlowAdapter();

        // SID - Painting setup such as start / end cap decoration and color now
        // moved to FlowConnectionFigure.
        IFigure iFig = getFigure();

        if (iFig instanceof SequenceFlowFigure) {

            SequenceFlowFigure fig = (SequenceFlowFigure) iFig;

            // set target decoration
            SequenceFlowType flowType = fa.getFlowType();

            if (SequenceFlowType.CONDITIONAL_LITERAL.equals(flowType)
                    && getSource() instanceof GatewayEditPart) {
                // Don't show diamond decorator for sequence flow from gateways.
                fig.setFlowType(SequenceFlowType.UNCONTROLLED_LITERAL);

            } else {
                fig.setFlowType(flowType);
            }

        }

    }

    @Override
    public HoverInfo getHoverInfo() {

        HoverInfo info =
                new HoverInfo(Messages.SequenceFlowEditPart_Hover_tooltip);
        SequenceFlowAdapter fa = getSequenceFlowAdapter();
        EObject targetNode = fa.getTargetNode();
        EObject sourceNode = fa.getSourceNode();

        if (targetNode == null || sourceNode == null) {
            // System.out
            //                    .println("ERROR: Sequence flow source/target object missing."); //$NON-NLS-1$
        } else {
            String name = fa.getName();
            info.addProperty(Messages.SequenceFlowEditPart_HoverName_label,
                    name != null ? name
                            : Messages.SequenceFlowEditPart_HoverNotSet_label);

            NamedElementAdapter s =
                    (NamedElementAdapter) fa.getAdapterFactory()
                            .adapt(sourceNode,
                                    ProcessWidgetConstants.ADAPTER_TYPE);
            NamedElementAdapter d =
                    (NamedElementAdapter) fa.getAdapterFactory()
                            .adapt(targetNode,
                                    ProcessWidgetConstants.ADAPTER_TYPE);

            if (s != null && d != null) {
                info.addProperty(Messages.SequenceFlowEditPart_HoverFrom_label,
                        s.getName());
                info.addProperty(Messages.SequenceFlowEditPart_HoverTo_label,
                        d.getName());
                info.addProperty(Messages.SequenceFlowEditPart_HoverType_label,
                        fa.getFlowType().toString());

                String condition = fa.getCondition();
                if (condition != null) {
                    info.addProperty(Messages.SequenceFlowEditPart_HoverData_label,
                            condition);
                }
            }
        }

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
        if (SequenceFlowType.CONDITIONAL_LITERAL
                .equals(getSequenceFlowAdapter().getFlowType())) {
            return ProcessWidgetColors.CONDITIONAL_SEQ_FLOW_LINE;
        } else if (SequenceFlowType.DEFAULT_LITERAL
                .equals(getSequenceFlowAdapter().getFlowType())) {
            return ProcessWidgetColors.DEFAULT_SEQ_FLOW_LINE;
        }

        return ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE;
    }

    /**
     * Check if sequence flow connection is valid between the 2 objects and
     * return reason for invalidity if so.
     * 
     * @param connectionSource
     * @param connectionTarget
     * 
     * @return String reason if invalid else null if valid.
     */
    public static String isInvalidConnection(
            BaseGraphicalEditPart connectionSource,
            BaseGraphicalEditPart connectionTarget) {

        if (connectionSource != null && connectionTarget != null) {
            // Sequence flows cannot cross pool / embedded sub-proc boundaries.
            BaseGraphicalEditPart srcParent = connectionSource.getParentPool();
            BaseGraphicalEditPart tgtParent = connectionTarget.getParentPool();

            if (srcParent != tgtParent) {
                return Messages.BaseGraphicalEditPart_CannotConnectDiffPools_message;
            }

            srcParent = connectionSource.getParentPoolOrTask();
            tgtParent = connectionTarget.getParentPoolOrTask();
            if (srcParent != tgtParent) {
                if (srcParent instanceof TaskEditPart
                        || tgtParent instanceof TaskEditPart) {
                    return Messages.BaseGraphicalEditPart_BadMoveAcrossBoundary_message;
                }
            }
        }

        // Cannot connect To a start event...
        if (connectionTarget instanceof EventEditPart
                && ((EventEditPart) connectionTarget).getEventFlowType() == EventFlowType.FLOW_START) {
            return Messages.SequenceFlowEditPart_CannotConenctToStart_message;
        }

        // Cannot connect From a start event...
        if (connectionSource instanceof EventEditPart
                && ((EventEditPart) connectionSource).getEventFlowType() == EventFlowType.FLOW_END) {
            return Messages.SequenceFlowEditPart_CannotConenctFromEnd_message;
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
        boolean visible = true;

        // If source/target object is in a closed pool then hide sequence
        // flow (we'll check both but nominally sequence flows can't cross pools
        // anyway).
        EditPart source = getSource();

        boolean sourceInClosedParent = EditPartUtil.isInClosedParent(source);

        EditPart target = getTarget();

        boolean targetInClosedParent = EditPartUtil.isInClosedParent(target);

        // If the sequence flow is between objects in the same lane and
        // that lane is closed then hide it.
        // Or the source and target lanes are both closed then hide it.
        if (sourceInClosedParent && targetInClosedParent) {
            visible = false;
        }

        return visible;
    }

    private class SequenceFlowSortAndBendPolicy extends
            AbstractSortAndBendConnectionPolicy {

        /**
         * @param adapterFactory
         * @param editingDomain
         */
        public SequenceFlowSortAndBendPolicy(AdapterFactory adapterFactory,
                EditingDomain editingDomain) {
            super(adapterFactory, editingDomain);
        }

        @Override
        protected Command getSortSwapConnectionsCommand(
                ConnectionEditPart connToSwapWith) {

            if (getHost() instanceof SequenceFlowEditPart) {
                SequenceFlowAdapter adp =
                        ((SequenceFlowEditPart) getHost())
                                .getSequenceFlowAdapter();

                if (adp != null) {
                    return new EMFCommandWrapper(
                            getEditingDomain(),
                            adp.getSwapSequenceFlowOrderCommand(getEditingDomain(),
                                    connToSwapWith.getModel()));
                }
            }

            return null;
        }

        @Override
        protected List<ConnectionEditPart> getSortedConnectionSet() {
            if (getHost() instanceof SequenceFlowEditPart) {
                SequenceFlowAdapter adp =
                        ((SequenceFlowEditPart) getHost())
                                .getSequenceFlowAdapter();

                if (adp != null) {
                    List<Object> modelFlows = adp.getOutgoingFlowInOrder();
                    if (modelFlows != null) {
                        List<ConnectionEditPart> connEPs =
                                new ArrayList<ConnectionEditPart>();

                        for (Object mf : modelFlows) {
                            Object ep =
                                    getViewer().getEditPartRegistry().get(mf);
                            if (ep instanceof ConnectionEditPart) {
                                connEPs.add((ConnectionEditPart) ep);
                            }
                        }

                        return connEPs;
                    }
                }
            }

            return Collections.EMPTY_LIST;
        }

    }

}
