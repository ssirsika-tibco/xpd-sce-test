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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter.AssociationDirectionType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.figures.AssociationConnectionFigure;
import com.tibco.xpd.processwidget.figures.BpmnDirectRouter;
import com.tibco.xpd.processwidget.figures.BpmnFlowRouter;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.AssociationOnlyNodeFlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.policies.FlowConnectionBendpointEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowConnectionEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowConnectionEndpointPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;

/**
 * Connection between Graphical Nodes
 * 
 * @author wzurek
 */
public class AssociationEditPart extends BaseConnectionEditPart implements
        HoverProvider {

    /**
     * AssociationAnimatorPolicy
     * 
     */
    private final class AssociationAnimatorPolicy extends
            ConnectionsAnimatorEditPolicy {

        public AssociationAnimatorPolicy(
                EditPolicyEnablementProvider policyEnablementProvider) {
            super(policyEnablementProvider);
        }

        private class CycleImageFigure extends ImageFigure {
            ImageRegistry ir;

            String[] imgs;

            int cycle = 0;

            long lastTime;

            int height;

            /**
             * 
             */
            public CycleImageFigure(ImageRegistry ir, String[] imgs) {
                super();
                this.ir = ir;
                this.imgs = imgs;
                lastTime = System.currentTimeMillis();

                Image image = ir.get(imgs[cycle]);
                setImage(image);

                height = image.getBounds().height;

                setPreferredSize(image.getBounds().width, height);

            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.draw2d.Figure#setBounds(org.eclipse.draw2d.geometry
             * .Rectangle)
             */
            @Override
            public void setBounds(Rectangle rect) {
                // rect.y -= (height / 2);
                super.setBounds(rect);
            }

            public void cycle() {
                if (System.currentTimeMillis() > (lastTime + 50)) {
                    cycle++;
                    if (cycle >= imgs.length) {
                        cycle = 0;
                    }
                    setImage(ir.get(imgs[cycle]));

                    lastTime = System.currentTimeMillis();
                }

            }
        }

        // Festive fun...
        String[] imgsLeft = { ProcessWidgetConstants.IMG_1,
                ProcessWidgetConstants.IMG_2, ProcessWidgetConstants.IMG_3,
                ProcessWidgetConstants.IMG_4, ProcessWidgetConstants.IMG_5,
                ProcessWidgetConstants.IMG_6, ProcessWidgetConstants.IMG_7,
                ProcessWidgetConstants.IMG_8, ProcessWidgetConstants.IMG_9,
                ProcessWidgetConstants.IMG_10, };

        String[] imgsRight = { ProcessWidgetConstants.IMG_R1,
                ProcessWidgetConstants.IMG_R2, ProcessWidgetConstants.IMG_R3,
                ProcessWidgetConstants.IMG_R4, ProcessWidgetConstants.IMG_R5,
                ProcessWidgetConstants.IMG_R6, ProcessWidgetConstants.IMG_R7,
                ProcessWidgetConstants.IMG_R8, ProcessWidgetConstants.IMG_R9,
                ProcessWidgetConstants.IMG_R10, };

        /**
         * Create feedback figure that is animated on the path. Clients may
         * extend.
         * 
         * @return newly created figure
         */
        @Override
        protected IFigure createFeedbackFigure() {
            String name = getAssociation().getName();
            if (name != null
                    && (name.contains("snow") || name.contains("sled"))) { //$NON-NLS-1$ //$NON-NLS-2$
                CycleImageFigure imgFig =
                        new CycleImageFigure(ProcessWidgetPlugin.getDefault()
                                .getImageRegistry(), imgsRight);

                return imgFig;
            } else {
                SmoothElipse el = new SmoothElipse();
                el.setPreferredSize(5, 5);
                el.setFill(true);
                el.setBackgroundColor(ColorConstants.white);
                el.setForegroundColor(ColorConstants.darkBlue);
                return el;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy
         * #incCycle()
         */
        @Override
        public void incCycle(IFigure feedbackFigure, Point thisP, Point nextP) {
            if (feedbackFigure instanceof CycleImageFigure) {
                CycleImageFigure fig = (CycleImageFigure) feedbackFigure;

                double angle = XPDLineUtilities.getAngleOfLine(thisP, nextP);
                if (angle > 90 && angle < 270) {
                    fig.imgs = imgsLeft;
                } else {
                    fig.imgs = imgsRight;
                }
                fig.cycle();
            }
        }
    }

    private boolean currentlyCompensationAssociation = false;

    public AssociationEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.assoc_sel"); //$NON-NLS-1$
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();

        // Although you cannot connect anything from / to an association itself
        // we will add the connection edit policy to associations so that
        // getTargetEditPart() in edit policy can add error tool tip saying that
        // this is not possible.
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new AssociationOnlyNodeFlowConnectionEditPolicy(
                        getEditingDomain()));

        installEditPolicy(EditPolicy.CONNECTION_ROLE,
                new FlowConnectionEditPolicy());

        installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
                new FlowConnectionBendpointEditPolicy(getAdapterFactory(),
                        getEditingDomain()));
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
                new FlowConnectionEndpointPolicy());

        installEditPolicy("Animation", new AssociationAnimatorPolicy( //$NON-NLS-1$
                getProcessWidget().getEditPolicyEnablementProvider()));

    }

    private String getNodeText(NamedElementAdapter node) {

        if (node instanceof NoteAdapter) {
            StringBuffer name =
                    new StringBuffer(((NoteAdapter) node).getText());

            if (name.length() > 32) {
                name.setLength(32);
                name.append("..."); //$NON-NLS-1$
            }
            return (name.toString());
        }

        String name = node.getName();

        if (name != null) {
            return name;
        }

        return ""; //$NON-NLS-1$
    }

    @Override
    public HoverInfo getHoverInfo() {
        HoverInfo info =
                new HoverInfo(Messages.AssociationEditPart_Hover_tooltip);
        AssociationAdapter fa = getAssociation();

        String name = fa.getName();
        info.addProperty(Messages.AssociationEditPart_HoverName_label,
                name != null ? name
                        : Messages.AssociationEditPart_HoverNotSet_label);

        NamedElementAdapter s =
                (NamedElementAdapter) fa.getAdapterFactory()
                        .adapt(fa.getSourceNode(),
                                ProcessWidgetConstants.ADAPTER_TYPE);
        NamedElementAdapter d =
                (NamedElementAdapter) fa.getAdapterFactory()
                        .adapt(fa.getTargetNode(),
                                ProcessWidgetConstants.ADAPTER_TYPE);

        info.addProperty(Messages.AssociationEditPart_HoverFrom_label,
                getNodeText(s));

        info.addProperty(Messages.AssociationEditPart_HoverTo_label,
                getNodeText(d));

        info.setDocumentationURL(fa.getDocumentationURL(), this);

        return info;
    }

    @Override
    protected IFigure createFigure() {
        PolylineConnection f = new AssociationConnectionFigure();
        f.setBackgroundColor(ColorConstants.white);
        f.setForegroundColor(ColorConstants.darkBlue);
        f.setToolTip(new TooltipFigure(this));
        f.setConnectionRouter(new BpmnDirectRouter());
        return f;
    }

    public AssociationAdapter getAssociation() {
        return (AssociationAdapter) getModelAdapter();
    }

    /**
     * Return true if this association is a compensation association. (i.e. is
     * from an compensation event on border of task to a non-artifact object).
     * 
     * @return
     */
    public boolean isCompensationAssocation() {
        boolean isCompAss = false;

        if (getSource() instanceof EventEditPart) {
            EventEditPart eventEP = (EventEditPart) getSource();

            if (eventEP.isAttachedToTaskBorder()
                    && eventEP.getEventTriggerType() == EventTriggerType.EVENT_COMPENSATION_CATCH) {

                EditPart tgt = getTarget();

                if (tgt instanceof BaseFlowNodeEditPart) {
                    isCompAss = true;
                }
            }
        }

        return isCompAss;
    }

    @Override
    protected void refreshVisuals() {
        AssociationAdapter aa = getAssociation();

        // Use direct router for association unless from compensation event on
        // task border to activity.
        boolean nowCompensationAssociation = isCompensationAssocation();

        AssociationConnectionFigure pc =
                (AssociationConnectionFigure) getFigure();

        if (nowCompensationAssociation != currentlyCompensationAssociation) {
            if (nowCompensationAssociation) {
                pc.setConnectionRouter(new BpmnFlowRouter());
            } else {
                pc.setConnectionRouter(new BpmnDirectRouter());
            }

            // If changing status then refresh target edit part so that
            // compensation marker gets removed.
            if (getTarget() instanceof TaskEditPart) {
                ((TaskEditPart) getTarget()).refreshVisuals();
            }

            currentlyCompensationAssociation = nowCompensationAssociation;
        }

        if (nowCompensationAssociation) {
            pc.setAssociationDirectionType(AssociationAdapter.DIRECTION_TO_TARGET);
        } else {
            AssociationDirectionType ad = aa.getAssociationDirection();

            pc.setAssociationDirectionType(ad);
        }

        super.refreshVisuals();

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
        return ProcessWidgetColors.ASSOCIATION_LINE;
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
        // If source/target object is in a closed pool then hide sequence
        // flow (we'll check both but nominally sequence flows can't cross pools
        // anyway).
        EditPart source = getSource();

        boolean sourceIsVisible = true;

        if (source instanceof BaseGraphicalEditPart) {
            sourceIsVisible = !EditPartUtil.isInClosedParent(source);
        } else if (source instanceof BaseConnectionEditPart) {
            sourceIsVisible =
                    ((BaseConnectionEditPart) source).shouldBeVisible();
        }

        EditPart target = getTarget();

        boolean targetIsVisible = true;

        if (target instanceof BaseGraphicalEditPart) {
            targetIsVisible = !EditPartUtil.isInClosedParent(target);
        } else if (target instanceof BaseConnectionEditPart) {
            targetIsVisible =
                    ((BaseConnectionEditPart) target).shouldBeVisible();
        }

        return (sourceIsVisible || targetIsVisible);

    }

}
