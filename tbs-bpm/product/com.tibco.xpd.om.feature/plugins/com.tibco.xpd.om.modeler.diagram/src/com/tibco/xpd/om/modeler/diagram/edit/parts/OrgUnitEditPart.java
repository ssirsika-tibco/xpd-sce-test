package com.tibco.xpd.om.modeler.diagram.edit.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ViewComponentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrgUnitItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelTextSelectionEditPolicy;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.preferences.custom.IOMPreferenceConstants;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitCustomEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.OrgUnitCustomFigure;

public class OrgUnitEditPart extends OrgUnitCustomEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 2001;

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
    public OrgUnitEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy() {
                    @Override
                    public Command getCommand(Request request) {
                        if (understandsRequest(request)) {
                            if (request instanceof CreateViewAndElementRequest) {
                                CreateElementRequestAdapter adapter =
                                        ((CreateViewAndElementRequest) request)
                                                .getViewAndElementDescriptor()
                                                .getCreateElementRequestAdapter();
                                IElementType type =
                                        (IElementType) adapter
                                                .getAdapter(IElementType.class);
                                if (type == OrganizationModelElementTypes.Position_2002) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(OrganizationModelVisualIDRegistry
                                                    .getType(OrgUnitPositionCompartmentEditPart.VISUAL_ID));
                                    return compartmentEditPart == null ? null
                                            : compartmentEditPart
                                                    .getCommand(request);
                                }
                            }
                            return super.getCommand(request);
                        }
                        return null;
                    }
                });
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrgUnitItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        // XXX need an SCR to runtime to have another abstract superclass that
        // would let children add reasonable editpolicies
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // EditPolicyRoles.CONNECTION_HANDLES_ROLE);

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
        OrgUnitFigure figure = new OrgUnitFigure(this);
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
                                    IOMPreferenceConstants.PREF_ORGUNIT_LINE_COLOR));
            border.setColor(lineColor);
        }
        return primaryShape = figure;
    }

    /**
     * @generated
     */
    @Override
    public OrgUnitFigure getPrimaryShape() {
        return (OrgUnitFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof OrgUnitDisplayNameEditPart) {
            ((OrgUnitDisplayNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgUnitFigureNameLabel());
            return true;
        }
        if (childEditPart instanceof OrgUnitFeatureNameEditPart) {
            ((OrgUnitFeatureNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgUnitFigureFeatureLabel());
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
     * @generated
     */
    // protected NodeFigure createNodePlate() {
    // DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode()
    // .DPtoLP(20), getMapMode().DPtoLP(20));
    // return result;
    // }
    /**
     * @generated NOT
     */
    protected NodeFigure createNodePlate() {
        DefaultSizeNodeFigure result =
                new DefaultSizeNodeFigure(getMapMode().DPtoLP(20), getMapMode()
                        .DPtoLP(20));

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

        /*
         * If this is a root orgunit in a dynamic organization then set double
         * border.
         */
        if (isDynamicOrganization() && isRootOrgUnit()) {
            setBorder(figure, true);
        }

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
                .getType(OrgUnitDisplayNameEditPart.VISUAL_ID));
    }

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
     * @see com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitCustomEditPart#handleNotificationEvent(org.eclipse.emf.common.notify.Notification)
     * 
     * @param event
     */
    @Override
    protected void handleNotificationEvent(Notification event) {
        super.handleNotificationEvent(event);

        /*
         * XPD-5300: If in a dynamic organization and the status of an orgunit
         * changes from a sub-unit to a root org unit then the border needs to
         * be update accordingly.
         */
        if (event.getFeature() == OMPackage.eINSTANCE
                .getOrgUnit_IncomingRelationships()) {
            if (isDynamicOrganization()) {
                EObject element = resolveSemanticElement();
                if (element instanceof OrgUnit
                        && event.getNotifier() == element) {

                    setBorder(getFigure(), isRootOrgUnit());
                }
            }
        }
    }

    public class OrgUnitFigure extends OrgUnitCustomFigure {

        /**
         * @generated
         */
        private WrappingLabel fFigureOrgUnitFigureNameLabel;

        /**
         * @generated
         */
        private WrappingLabel fFigureOrgUnitFigureFeatureLabel;

        /**
         * @generated NOT
         */
        public OrgUnitFigure() {
            // Dummy constructor to satisfy the model generator
            super(null);
        }

        /**
         * @generated NOT
         */
        public OrgUnitFigure(OrgUnitEditPart ep) {
            super(ep);
            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(75),
                    getMapMode().DPtoLP(20)));
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
         * @generated NOT
         */
        public WrappingLabel getFigureOrgUnitFigureNameLabel() {
            // return fFigureOrgUnitFigureNameLabel;
            return super.getNameLabel();
        }

        /**
         * @generated NOT
         */
        public WrappingLabel getFigureOrgUnitFigureFeatureLabel() {
            return super.getFeatureLabel();
        }

    }

}
