/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.AssociationClassDanglingNodeItemSemanticEditPolicy;

/**
 * @generated
 */
public class AssociationClassDanglingNodeEditPart extends ShapeNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 1006;

    /**
     * @generated
     */
    protected IFigure contentPane;

    /**
     * @generated
     */
    protected IFigure primaryShape;

    /**
     * @generated
     */
    public AssociationClassDanglingNodeEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new AssociationClassDanglingNodeItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        // XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
    }

    /**
     * @generated
     */
    protected LayoutEditPolicy createLayoutEditPolicy() {
        LayoutEditPolicy lep = new LayoutEditPolicy() {

            protected EditPolicy createChildEditPolicy(EditPart child) {
                EditPolicy result = child
                        .getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
                if (result == null) {
                    result = new NonResizableEditPolicy();
                }
                return result;
            }

            protected Command getMoveChildrenCommand(Request request) {
                return null;
            }

            protected Command getCreateCommand(CreateRequest request) {
                return null;
            }
        };
        return lep;
    }

    /**
     * @generated
     */
    protected IFigure createNodeShape() {
        AssociationClassDanglingNodeFigure figure = new AssociationClassDanglingNodeFigure();
        return primaryShape = figure;
    }

    /**
     * @generated
     */
    public AssociationClassDanglingNodeFigure getPrimaryShape() {
        return (AssociationClassDanglingNodeFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected NodeFigure createNodePlate() {
        DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode()
                .DPtoLP(10), getMapMode().DPtoLP(10));
        return result;
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model
     * so you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */
    protected NodeFigure createNodeFigure() {
        NodeFigure figure = createNodePlate();
        figure.setLayoutManager(new StackLayout());
        IFigure shape = createNodeShape();
        figure.add(shape);
        contentPane = setupContentPane(shape);
        return figure;
    }

    /**
     * Default implementation treats passed figure as content pane.
     * Respects layout one may have set for generated figure.
     * @param nodeShape instance of generated figure class
     * @generated
     */
    protected IFigure setupContentPane(IFigure nodeShape) {
        return nodeShape; // use nodeShape itself as contentPane
    }

    /**
     * @generated
     */
    public IFigure getContentPane() {
        if (contentPane != null) {
            return contentPane;
        }
        return super.getContentPane();
    }

    /**
     * @generated
     */
    public class AssociationClassDanglingNodeFigure extends Shape {

        /**
         * @generated
         */
        public AssociationClassDanglingNodeFigure() {
            this.addPoint(new Point(getMapMode().DPtoLP(20), getMapMode()
                    .DPtoLP(0)));
            this.addPoint(new Point(getMapMode().DPtoLP(40), getMapMode()
                    .DPtoLP(20)));
            this.addPoint(new Point(getMapMode().DPtoLP(20), getMapMode()
                    .DPtoLP(40)));
            this.addPoint(new Point(getMapMode().DPtoLP(0), getMapMode()
                    .DPtoLP(20)));
            this.setFill(true);
        }

        /**
         * @generated
         */
        private final PointList myTemplate = new PointList();
        /**
         * @generated
         */
        private Rectangle myTemplateBounds;

        /**
         * @generated
         */
        public void addPoint(Point point) {
            myTemplate.addPoint(point);
            myTemplateBounds = null;
        }

        /**
         * @generated
         */
        protected void fillShape(Graphics graphics) {
            Rectangle bounds = getBounds();
            graphics.pushState();
            graphics.translate(bounds.x, bounds.y);
            graphics.fillPolygon(scalePointList());
            graphics.popState();
        }

        /**
         * @generated
         */
        protected void outlineShape(Graphics graphics) {
            Rectangle bounds = getBounds();
            graphics.pushState();
            graphics.translate(bounds.x, bounds.y);
            graphics.drawPolygon(scalePointList());
            graphics.popState();
        }

        /**
         * @generated
         */
        private Rectangle getTemplateBounds() {
            if (myTemplateBounds == null) {
                myTemplateBounds = myTemplate.getBounds().getCopy().union(0, 0);
                //just safety -- we are going to use this as divider 
                if (myTemplateBounds.width < 1) {
                    myTemplateBounds.width = 1;
                }
                if (myTemplateBounds.height < 1) {
                    myTemplateBounds.height = 1;
                }
            }
            return myTemplateBounds;
        }

        /**
         * @generated
         */
        private int[] scalePointList() {
            Rectangle pointsBounds = getTemplateBounds();
            Rectangle actualBounds = getBounds();

            float xScale = ((float) actualBounds.width) / pointsBounds.width;
            float yScale = ((float) actualBounds.height) / pointsBounds.height;

            if (xScale == 1 && yScale == 1) {
                return myTemplate.toIntArray();
            }
            int[] scaled = (int[]) myTemplate.toIntArray().clone();
            for (int i = 0; i < scaled.length; i += 2) {
                scaled[i] = (int) Math.floor(scaled[i] * xScale);
                scaled[i + 1] = (int) Math.floor(scaled[i + 1] * yScale);
            }
            return scaled;
        }

    }

}
