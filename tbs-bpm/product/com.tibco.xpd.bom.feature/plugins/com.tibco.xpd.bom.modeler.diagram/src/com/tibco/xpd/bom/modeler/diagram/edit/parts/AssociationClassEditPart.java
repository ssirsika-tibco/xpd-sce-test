/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RectangularDropShadowLineBorder;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.ClassifierCustomFigure;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.IGradientFigure;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.AssociationClassItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class AssociationClassEditPart extends ShapeNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 1005;

    /**
     * @generated
     */
    protected IFigure contentPane;

    /**
     * @generated
     */
    protected IFigure primaryShape;

    protected ClassifierFigure custFigure;

    public ClassifierFigure getCustFigure() {
        return custFigure;
    }

    /**
     * @generated
     */
    public AssociationClassEditPart(View view) {
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
                                if (type == UMLElementTypes.Property_2004) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(UMLVisualIDRegistry
                                                    .getType(AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID));
                                    return compartmentEditPart == null ? null
                                            : compartmentEditPart
                                                    .getCommand(request);
                                }
                                if (type == UMLElementTypes.Operation_2005) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(UMLVisualIDRegistry
                                                    .getType(AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID));
                                    return compartmentEditPart == null ? null
                                            : compartmentEditPart
                                                    .getCommand(request);
                                }
                            }
                            return super.getCommand(request);
                        }
                        return null;
                    }

                    @Override
                    protected ICommand getReparentViewCommand(
                            IGraphicalEditPart gep) {
                        // Disallow dropping views without semantic elements
                        // into this editpart
                        return null;
                    }

                    @Override
                    protected ICommand getReparentCommand(IGraphicalEditPart gep) {
                        View view = (View) gep.getModel();
                        EObject element = ViewUtil.resolveSemanticElement(view);

                        if (element instanceof AssociationClass) {
                            return null;
                        }

                        return super.getReparentCommand(gep);
                    }

                });
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new AssociationClassItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        // XXX need an SCR to runtime to have another abstract superclass that
        // would let children add reasonable editpolicies
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // EditPolicyRoles.CONNECTION_HANDLES_ROLE);
    }

    /**
     * @generated
     */
    protected LayoutEditPolicy createLayoutEditPolicy() {
        LayoutEditPolicy lep = new LayoutEditPolicy() {

            @Override
            protected EditPolicy createChildEditPolicy(EditPart child) {
                EditPolicy result =
                        child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
                if (result == null) {
                    result = new NonResizableEditPolicy();
                }
                return result;
            }

            @Override
            protected Command getMoveChildrenCommand(Request request) {
                return null;
            }

            @Override
            protected Command getCreateCommand(CreateRequest request) {
                return null;
            }
        };
        return lep;
    }

    /**
     * @generated NOT
     */
    protected IFigure createNodeShape() {
        custFigure = new ClassifierFigure();
        return primaryShape = custFigure;

    }

    /**
     * @generated
     */
    public ClassifierFigure getPrimaryShape() {
        return (ClassifierFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof AssociationClassNameEditPart) {
            ((AssociationClassNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureClassifierNameLabelFigure());
            return true;
        }
        if (childEditPart instanceof AssociationClassSuperClassNameEditPart) {
            ((AssociationClassSuperClassNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureClassifierSuperClassNameLabelFigure());
            return true;
        }
        if (childEditPart instanceof AssociationClassStereoTypeLabelEditPart) {
            ((AssociationClassStereoTypeLabelEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureClassifierStereoTypeLabelFigure());
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

        // If this is a compartment we need to add a GridData constraint so that
        // the control will always fill the horizontal space
        if (childEditPart instanceof AssociationClassClassifierAttributesCompartmentEditPart) {
            GridData constraintFFigureCompart =
                    new GridData(GridData.FILL_BOTH);
            constraintFFigureCompart.horizontalAlignment = GridData.FILL;
            // constraintFFigureCompart.heightHint = 30;
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
            this.getContentPane().add(child, constraintFFigureCompart, -1);

        } else if (childEditPart instanceof AssociationClassClassifierOperationsCompartmentEditPart) {
            GridData constraintFFigureCompart =
                    new GridData(GridData.FILL_BOTH);
            constraintFFigureCompart.horizontalAlignment = GridData.FILL;
            // constraintFFigureCompart.heightHint = 30;
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
            this.getContentPane().add(child, constraintFFigureCompart, -1);

        }

        else {
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
        // Set the drop shadow here
        result.setBorder(new RectangularDropShadowLineBorder());
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
        return getChildBySemanticHint(UMLVisualIDRegistry
                .getType(AssociationClassNameEditPart.VISUAL_ID));
    }

    @Override
    protected void refreshVisuals() {
        // update gradient colors
        Object fig = getFigure().getChildren().get(0);

        if (fig instanceof IGradientFigure) {
            IGradientFigure gradFig = (IGradientFigure) fig;

            IPreferenceStore store =
                    (IPreferenceStore) BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                            .getPreferenceStore();
            GradientFigureUtil
                    .updateFigureGradient(getPrimaryView(),
                            gradFig,
                            FigureUtilities.RGBToInteger(PreferenceConverter
                                    .getColor(store,
                                            IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR1)),
                            FigureUtilities.RGBToInteger(PreferenceConverter
                                    .getColor(store,
                                            IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR2)));
        }
        super.refreshVisuals();
    }

    @Override
    protected void handleNotificationEvent(Notification notification) {
        // handle gradient change
        if (GradientFigureUtil.isGradientChange(notification)) {
            refreshVisuals();
            return;
        }

        if (notification.getFeatureID(UMLPackage.class) == UMLPackage.GENERALIZATION_SET) {
            refreshGeneralizations();
            super.handleNotificationEvent(notification);
        } else {
            super.handleNotificationEvent(notification);
        }
    }

    /**
     * generated NOT
     * 
     */
    private void refreshGeneralizations() {
        // If add generalization command originates outside of the GMF
        // e.g. a low level EMF command, then their may not be a corresponding
        // command to generate an edge view. We have to refresh the containing
        // package which is responsible for updating all links. We do this by
        // calling the refresh() method of the semantic role editpolicy which in
        // turn call refreshSemantic() which is ultimately responsible for
        // synchronising edge views. Note that the semantic edit policy for the
        // CanvasEditPart was originally called for all packages. But this has
        // undesired effects on drawing connections for regular packages
        EditPart ep = this.getParent();

        if (ep instanceof CanvasPackageEditPart) {
            CanvasPackageEditPart cep = (CanvasPackageEditPart) ep;
            cep.refreshGeneralizationConnections();
        } else {
            // ep is going to be the package compartment editpart.
            // So lets get its parent which will be a package editpart
            EditPart ep2 = ep.getParent();
            if (ep2 instanceof PackageEditPart) {
                PackageEditPart pep = (PackageEditPart) ep2;
                pep.refresh();
            }
        }
    }

    /**
     * @generated NOT
     */
    public class ClassifierFigure extends ClassifierCustomFigure {

        /**
         * @generated
         */
        private WrappingLabel fFigureClassifierNameLabelFigure;

        /**
         * @generated
         */
        private WrappingLabel fFigureClassifierSuperClassNameLabelFigure;

        /**
         * @generated
         */
        private WrappingLabel fFigureClassifierStereoTypeLabelFigure;

        /**
         * @generated
         */
        public ClassifierFigure() {
            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(90),
                    getMapMode().DPtoLP(60)));
            createContents();
        }

        /**
         * @generated NOT
         */
        private void createContents() {

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
        public WrappingLabel getFigureClassifierNameLabelFigure() {
            return super.getClassifierNameLabel();
        }

        /**
         * @generated NOT
         */
        public WrappingLabel getFigureClassifierSuperClassNameLabelFigure() {
            return super.getSuperClassNameLabel();
        }

        /**
         * @generated NOT
         */
        public WrappingLabel getFigureClassifierStereoTypeLabelFigure() {
            return super.getStereoTypeLabel();
        }

    }

}
