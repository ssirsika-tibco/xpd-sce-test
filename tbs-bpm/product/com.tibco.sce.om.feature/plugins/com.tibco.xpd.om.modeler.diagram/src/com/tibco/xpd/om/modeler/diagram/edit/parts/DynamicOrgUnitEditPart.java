package com.tibco.xpd.om.modeler.diagram.edit.parts;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ViewComponentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.om.modeler.diagram.edit.policies.DynamicOrgUnitItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelTextSelectionEditPolicy;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.preferences.custom.IOMPreferenceConstants;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitCustomEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.OrgUnitCustomFigure;

/**
 * @generated
 */
public class DynamicOrgUnitEditPart extends OrgUnitCustomEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 2003;

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
    public DynamicOrgUnitEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new DynamicOrgUnitItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        // XXX need an SCR to runtime to have another abstract superclass that
        // would let children add reasonable editpolicies
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);

        installEditPolicy(EditPolicy.COMPONENT_ROLE,
                new ViewComponentEditPolicy() {

                    // Disable all deletes from diagram
                    @Override
                    protected boolean shouldDeleteSemantic() {
                        return false;
                    }

                    @Override
                    protected Command getDeleteCommand(GroupRequest request) {
                        return null;
                    }

                });

        /*
         * Don't allow the user to drag the source end of the Dynamic Org
         * reference from one dynamic orgunit to another (target is ok).
         */
        removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new GraphicalNodeEditPolicy() {
                    /**
                     * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
                     * 
                     * @param request
                     * @return
                     */
                    @Override
                    protected Command getReconnectSourceCommand(
                            ReconnectRequest request) {
                        if (request.getConnectionEditPart() instanceof DynamicOrgReferenceEditPart) {

                            if (request.getConnectionEditPart().getSource() != request
                                    .getTarget()) {
                                return UnexecutableCommand.INSTANCE;
                            }
                        }

                        return super.getReconnectSourceCommand(request);
                    }
                });
    }

    /**
     * @generated
     */
    protected LayoutEditPolicy createLayoutEditPolicy() {

        ConstrainedToolbarLayoutEditPolicy lep =
                new ConstrainedToolbarLayoutEditPolicy() {

                    @Override
                    protected EditPolicy createChildEditPolicy(EditPart child) {
                        if (child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE) == null) {
                            if (child instanceof ITextAwareEditPart) {
                                return new OrganizationModelTextSelectionEditPolicy();
                            }
                        }
                        return super.createChildEditPolicy(child);
                    }
                };
        return lep;
    }

    /**
     * @generated NOT
     */
    protected IFigure createNodeShape() {
        DynamicOrgUnitFigure figure = new DynamicOrgUnitFigure(this);
        IPreferenceStore store =
                (IPreferenceStore) OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                        .getPreferenceStore();
        if (figure.getBackgroundColor() == null) {

            figure.setBackgroundColor(DiagramColorRegistry.getInstance()
                    .getColor(PreferenceConverter.getColor(store,
                            IOMPreferenceConstants.PREF_ORGUNIT_FILL_COLOR)));

        }

        LineBorder border = (LineBorder) figure.getBorder();
        if (border != null && border.getColor() == null) {
            Color lineColor =
                    DiagramColorRegistry
                            .getInstance()
                            .getColor(PreferenceConverter.getColor(store,
                                    IOMPreferenceConstants.PREF_DYN_ORG_REF_LINE_COLOR));
            border.setColor(lineColor);
        }

        return primaryShape = figure;
    }

    /**
     * @generated
     */
    @Override
    public DynamicOrgUnitFigure getPrimaryShape() {
        return (DynamicOrgUnitFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof DynamicOrgUnitDisplayNameEditPart) {
            ((DynamicOrgUnitDisplayNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureDynamicOrgUnitFigureNameLabel());
            return true;
        }
        return false;
    }

    /**
     * @generated
     */
    protected boolean removeFixedChild(EditPart childEditPart) {

        return false;
    }

    /**
     * @generated
     */
    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        if (addFixedChild(childEditPart)) {
            return;
        }
        super.addChildVisual(childEditPart, -1);
    }

    /**
     * @generated
     */
    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        if (removeFixedChild(childEditPart)) {
            return;
        }
        super.removeChildVisual(childEditPart);
    }

    /**
     * @generated
     */
    @Override
    protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {

        return super.getContentPaneFor(editPart);
    }

    /**
     * @generated NOT
     */
    protected NodeFigure createNodePlate() {
        DefaultSizeNodeFigure result =
                new DefaultSizeNodeFigure(getMapMode().DPtoLP(20), getMapMode()
                        .DPtoLP(20));

        LineBorder border = (LineBorder) result.getBorder();
        if (border != null && border.getColor() == null) {
            IPreferenceStore store =
                    (IPreferenceStore) OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                            .getPreferenceStore();
            Color lineColor =
                    DiagramColorRegistry
                            .getInstance()
                            .getColor(PreferenceConverter.getColor(store,
                                    IOMPreferenceConstants.PREF_DYN_ORG_REF_LINE_COLOR));
            border.setColor(lineColor);
        }

        return result;
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */
    @Override
    protected NodeFigure createNodeFigure() {
        NodeFigure figure = createNodePlate();
        figure.setLayoutManager(new StackLayout());
        IFigure shape = createNodeShape();
        figure.add(shape);
        contentPane = setupContentPane(shape);

        // Set double border
        setBorder(figure, true);

        return figure;
    }

    /**
     * Default implementation treats passed figure as content pane. Respects
     * layout one may have set for generated figure.
     * 
     * @param nodeShape
     *            instance of generated figure class
     * @generated
     */
    protected IFigure setupContentPane(IFigure nodeShape) {
        if (nodeShape.getLayoutManager() == null) {
            ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
            layout.setSpacing(getMapMode().DPtoLP(5));
            nodeShape.setLayoutManager(layout);
        }
        return nodeShape; // use nodeShape itself as contentPane
    }

    /**
     * @generated
     */
    @Override
    public IFigure getContentPane() {
        if (contentPane != null) {
            return contentPane;
        }
        return super.getContentPane();
    }

    /**
     * @generated
     */
    @Override
    public EditPart getPrimaryChildEditPart() {
        return getChildBySemanticHint(OrganizationModelVisualIDRegistry
                .getType(DynamicOrgUnitDisplayNameEditPart.VISUAL_ID));
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart#refreshVisuals()
     * 
     */
    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();

        // update gradient colors
        IPreferenceStore store =
                (IPreferenceStore) OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                        .getPreferenceStore();
        GradientFigureUtil
                .updateFigureGradient(getPrimaryView(),
                        getPrimaryShape(),
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IOMPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR)),
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IOMPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR)));
    }

    /**
     * @generated
     */
    public class DynamicOrgUnitFigure extends OrgUnitCustomFigure {

        /**
         * @generated
         */
        // private WrappingLabel fFigureDynamicOrgUnitFigureNameLabel;

        /**
         * @param ep
         * @generated
         */
        public DynamicOrgUnitFigure(OrgUnitCustomEditPart ep) {
            super(ep);

            ToolbarLayout layoutThis = new ToolbarLayout();
            layoutThis.setStretchMinorAxis(true);
            layoutThis.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);

            layoutThis.setSpacing(0);
            layoutThis.setVertical(true);

            this.setLayoutManager(layoutThis);

            this.setLineStyle(Graphics.LINE_DASH);
        }

        /**
         * @generated
         */
        private boolean myUseLocalCoordinates = false;

        /**
         * @generated
         */
        @Override
        protected boolean useLocalCoordinates() {
            return myUseLocalCoordinates;
        }

        /**
         * @generated
         */
        @Override
        protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
            myUseLocalCoordinates = useLocalCoordinates;
        }

        /**
         * @generated
         */
        public WrappingLabel getFigureDynamicOrgUnitFigureNameLabel() {
            return getNameLabel();
        }

    }

}
