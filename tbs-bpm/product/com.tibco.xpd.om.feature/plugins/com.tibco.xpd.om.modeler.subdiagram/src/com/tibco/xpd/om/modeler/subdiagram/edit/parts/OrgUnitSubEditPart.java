package com.tibco.xpd.om.modeler.subdiagram.edit.parts;

import java.util.Map;

import org.eclipse.draw2d.GridData;
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
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.PasteViewRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitCustomEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.OrgUnitCustomFigure;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrgUnitSubItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrganizationModelTextSelectionEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.custom.OMContainerEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.custom.OrgUnitRelGraphicalNodeEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.DiagramUtils;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.resources.ui.clipboard.OMClipboardHelper;

public class OrgUnitSubEditPart extends OrgUnitCustomEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 1001;

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
    public OrgUnitSubEditPart(View view) {
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
                                if (type == OrganizationModelElementTypes.Position_2001) {

                                    // If we are creating a typed position we
                                    // need to check if
                                    // it is a valid type for this containing
                                    // OrgUnit
                                    Object obj =
                                            request.getExtendedData()
                                                    .get(IOrganizationModelDiagramConstants.OMCreationToolFeature);

                                    if (obj != null
                                            && obj instanceof PositionFeature) {
                                        PositionFeature posFeature =
                                                (PositionFeature) obj;
                                        EObject elem = resolveSemanticElement();

                                        if (elem instanceof OrgUnit) {
                                            OrgUnit ou = (OrgUnit) elem;
                                            if (ou.getFeature() != null) {
                                                if (posFeature.eContainer() != ou
                                                        .getFeature()
                                                        .getFeatureType()) {
                                                    return UnexecutableCommand.INSTANCE;
                                                }
                                            } else {
                                                return UnexecutableCommand.INSTANCE;
                                            }
                                        }
                                    }

                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(OrganizationModelVisualIDRegistry
                                                    .getType(OrgUnitPositionSubCompartmentEditPart.VISUAL_ID));
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
                new OrgUnitSubItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        // XXX need an SCR to runtime to have another abstract superclass that
        // would let children add reasonable editpolicies
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // EditPolicyRoles.CONNECTION_HANDLES_ROLE);

        removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);

        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new OrgUnitRelGraphicalNodeEditPolicy());

        /*
         * Container edit policy has been installed to control the behaviour of
         * a paste request. The paste, if appropriate, should be redirected to
         * the child container edit part.
         */
        installEditPolicy(EditPolicy.CONTAINER_ROLE,
                new OMContainerEditPolicy() {
                    @Override
                    protected Command getPasteCommand(PasteViewRequest request) {
                        Map<?, ?> data = request.getExtendedData();
                        /*
                         * If the intended target hint is provided for this
                         * paste then check it and redirect the paste request
                         * appropriately.
                         */
                        if (data != null && !data.isEmpty()) {
                            Object hint = data.get(OMClipboardHelper.OM_HINT);
                            String type =
                                    OrganizationModelVisualIDRegistry
                                            .getType(VISUAL_ID);
                            if (hint != null) {
                                EditPart editPart = getHost();
                                if (editPart != null
                                        && editPart.getModel() instanceof View) {
                                    Diagram diagram =
                                            ((View) editPart.getModel())
                                                    .getDiagram();
                                    if (diagram != null) {
                                        hint =
                                                OMClipboardHelper
                                                        .getViewHint(diagram);
                                    }
                                }

                                if (hint.equals(type)) {
                                    // Get type of the child container
                                    hint =
                                            OrganizationModelVisualIDRegistry
                                                    .getType(OrgUnitPositionSubCompartmentEditPart.VISUAL_ID);
                                }
                                /*
                                 * Any paste should happen in the compartment of
                                 * this edit part
                                 */
                                IGraphicalEditPart ep =
                                        getChildBySemanticHint((String) hint);

                                if (ep != null) {
                                    return ep.getCommand(request);
                                }
                            }

                            /*
                             * Paste into parent as the hint implies that the
                             * paste does not belong to the orgunit or its
                             * children
                             */
                            EditPart parent = getParent();

                            if (parent != null) {
                                return parent.getCommand(request);
                            }
                        }
                        return super.getPasteCommand(request);
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
                (IPreferenceStore) OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                        .getPreferenceStore();
        if (figure.getBackgroundColor() == null) {

            figure.setBackgroundColor(DiagramColorRegistry.getInstance()
                    .getColor(PreferenceConverter.getColor(store,
                            IOMSubPreferenceConstants.PREF_ORGUNIT_FILL_COLOR)));
        }

        LineBorder border = (LineBorder) figure.getBorder();
        if (border != null && border.getColor() == null) {
            Color lineColor =
                    DiagramColorRegistry
                            .getInstance()
                            .getColor(PreferenceConverter.getColor(store,
                                    IOMSubPreferenceConstants.PREF_ORGUNIT_LINE_COLOR));
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
        if (childEditPart instanceof OrgUnitSubDisplayNameEditPart) {
            ((OrgUnitSubDisplayNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgUnitFigureNameLabel());
            return true;
        }
        if (childEditPart instanceof OrgUnitSubFeatureNameEditPart) {
            ((OrgUnitSubFeatureNameEditPart) childEditPart)
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
     * @generated NOT
     */
    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        if (addFixedChild(childEditPart)) {
            return;
        }

        if (childEditPart instanceof OrgUnitPositionSubCompartmentEditPart) {
            GridData constraintFFigureCompart2 =
                    new GridData(GridData.FILL_BOTH);
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
            constraintFFigureCompart2.horizontalAlignment = GridData.FILL;
            this.getContentPane().add(child, constraintFFigureCompart2, -1);

        } else {
            super.addChildVisual(childEditPart, -1);
        }

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
                new DefaultSizeNodeFigure(getMapMode().DPtoLP(40), getMapMode()
                        .DPtoLP(40));

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
                .getType(OrgUnitSubDisplayNameEditPart.VISUAL_ID));
    }

    @Override
    protected void handleNotificationEvent(Notification event) {
        super.handleNotificationEvent(event);

        if (event.getEventType() == Notification.ADD) {
            if (event.getNewValue() instanceof OrgUnitRelationship) {
                OrgUnitRelationship rel =
                        (OrgUnitRelationship) event.getNewValue();

                // If we are the source of the relationship then force an expand
                // of all views in the hierarchy. Otherwise, if the hierarchy is
                // already collapsed, the expand/collpase icons will be out of
                // synch
                if (rel.getFrom() == resolveSemanticElement()) {

                    Object model = getModel();

                    if (model instanceof Node) {
                        Node node = (Node) model;
                        if (DiagramUtils
                                .isOrgUnitHierarchyCollapsed((Node) getModel())) {
                            expandHierarchyViews();
                        }
                    }
                }
            }
        }
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

    /**
     * @generated NOT
     */
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
        public OrgUnitFigure(OrgUnitSubEditPart ep) {
            super(ep);
            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(120),
                    getMapMode().DPtoLP(50)));

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

    /**
     * 
     * Extends GraphicalNodeEDitPolicy so that we can override the
     * getRoutingAdjustment() method which resets a Tree style to the connector
     * style defined in the preference store. We don't want this to happen.
     * 
     * @author rgreen
     * 
     */
    // class OrgUnitRelGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
    //
    // @Override
    // protected Command getRoutingAdjustment(IAdaptable connection,
    // String connectionHint, Routing currentRouterType,
    // EditPart target) {
    // Command cmd = null;
    // if (connectionHint == null || target == null
    // || target.getModel() == null
    // || ((View) target.getModel()).getElement() == null)
    // return null;
    // // check if router needs to change type due to reorient.
    // String targetHint = ViewUtil
    // .getSemanticElementClassId((View) target.getModel());
    // Routing newRouterType = null;
    // if (target instanceof ITreeBranchEditPart
    // && connectionHint.equals(targetHint)) {
    // newRouterType = Routing.TREE_LITERAL;
    // ChangePropertyValueRequest cpvr = new ChangePropertyValueRequest(
    // StringStatics.BLANK, Properties.ID_ROUTING,
    // newRouterType);
    // Command cmdRouter = target.getCommand(cpvr);
    // if (cmdRouter != null)
    // cmd = cmd == null ? cmdRouter : cmd.chain(cmdRouter);
    // } else {
    // // if (currentRouterType.equals(Routing.TREE_LITERAL)) {
    // // IPreferenceStore store = (IPreferenceStore)
    // // ((IGraphicalEditPart) getHost())
    // // .getDiagramPreferencesHint().getPreferenceStore();
    // // newRouterType = Routing.get(store
    // // .getInt(IPreferenceConstants.PREF_LINE_STYLE));
    // // }
    // }
    // if (newRouterType != null) {
    // // add commands for line routing. Convert the new connection and
    // // also the targeted connection.
    // ICommand spc = new SetPropertyCommand(getEditingDomain(),
    // connection, Properties.ID_ROUTING, StringStatics.BLANK,
    // newRouterType);
    // Command cmdRouter = new ICommandProxy(spc);
    // if (cmdRouter != null) {
    // cmd = cmd == null ? cmdRouter : cmd.chain(cmdRouter);
    // }
    // }
    // return cmd;
    // }
    //
    // }
}
