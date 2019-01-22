/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RectangularDropShadowLineBorder;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.EnumerationCustomFigure;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.EnumerationItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * @generated
 */
public class EnumerationEditPart extends ShapeNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 1004;

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
    public EnumerationEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    protected EnumerationFigure custFigure;

    public EnumerationFigure getCustFigure() {
        return custFigure;
    }

    /**
     * @generated
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
                                if (type == UMLElementTypes.EnumerationLiteral_2003) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(UMLVisualIDRegistry
                                                    .getType(EnumerationEnumLitCompartmentEditPart.VISUAL_ID));
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
                new EnumerationItemSemanticEditPolicy());
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
        custFigure = new EnumerationFigure();
        if (custFigure.getBackgroundColor() == null) {
            IPreferenceStore store =
                    (IPreferenceStore) BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                            .getPreferenceStore();
            Color bgColor =
                    DiagramColorRegistry
                            .getInstance()
                            .getColor(PreferenceConverter.getColor(store,
                                    IBOMPreferenceConstants.PREF_DIAGRAM_BG_COLOR));
            custFigure.setBackgroundColor(bgColor);
        }
        return primaryShape = custFigure;
    }

    /**
     * @generated
     */
    public EnumerationFigure getPrimaryShape() {
        return (EnumerationFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof EnumerationNameEditPart) {
            ((EnumerationNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureEnumerationNameLabel());
            return true;
        }
        if (childEditPart instanceof EnumerationSuperTypeNameEditPart) {
            ((EnumerationSuperTypeNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureEnumerationSuperTypeNameLabel());
            return true;
        }
        if (childEditPart instanceof EnumerationStereoTypeLabelEditPart) {
            ((EnumerationStereoTypeLabelEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureEnumerationStereoTypeLabelFigure());
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
        if (childEditPart instanceof EnumerationEnumLitCompartmentEditPart) {
            GridData constraintFFigureCompart =
                    new GridData(GridData.FILL_BOTH);
            constraintFFigureCompart.horizontalAlignment = GridData.FILL;
            // constraintFFigureCompart.heightHint = 30;
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
            this.getContentPane().add(child, constraintFFigureCompart, -1);

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
                .getType(EnumerationNameEditPart.VISUAL_ID));
    }

    /**
     * @param notification
     */
    private void handleSubDiagramNotifications(Notification notification) {

        // Scenario1: We are getting a notification from
        Object notifier = notification.getNotifier();
        int type = notification.getEventType();

        // Check that the notifier is this editparts node
        if (notifier instanceof Node) {
            Node node = (Node) notifier;
            Object model2 = getModel();

            if (node == model2) {
                // We've identified that the notifer is this editpart's node
                // Next check the notification type. We are looking for a
                // Generalization/Edge SET
                if (type == Notification.ADD) {
                    if (notification.getNewValue() != null
                            && notification.getNewValue() instanceof Edge) {
                        Edge edge = (Edge) notification.getNewValue();

                        if (edge.getElement() instanceof Generalization) {
                            Generalization gen =
                                    (Generalization) edge.getElement();

                            if (gen.eContainer() == resolveSemanticElement()) {
                                // This generalization is owned by this
                                // editparts semantic element. So, because we
                                // are now drawing an edge then we won't need a
                                // superclass label. So force a refreash.
                                getCustFigure()
                                        .rebuildFigureWithSuperClass(false);

                            }
                        }

                    }
                } else if (type == Notification.REMOVE) {
                    if (notification.getOldValue() != null
                            && notification.getOldValue() instanceof Edge) {
                        Edge edge = (Edge) notification.getOldValue();

                        // The edge has lost its reference to its semantic
                        // element so we will have to confirm its a
                        // Generalization link in another way:
                        String type2 = edge.getType();

                        // Can onlu have one Generalization per class so,
                        // assuming it is being removed it is safe to show the
                        // label
                        if (type2.equals(String
                                .valueOf(GeneralizationEditPart.VISUAL_ID))) {
                            getCustFigure().rebuildFigureWithSuperClass(true);
                        }
                    }
                }
            }

        }

    }

    /**
     * generated NOT
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#handlePropertyChangeEvent(java.beans.PropertyChangeEvent)
     */
    @Override
    protected void handleNotificationEvent(Notification notification) {

        // handle gradient change
        if (GradientFigureUtil.isGradientChange(notification)) {
            refreshVisuals();
            return;
        }

        Diagram diagramView = getDiagramView();

        if (BomUIUtil.isUserDiagram(diagramView)) {
            handleSubDiagramNotifications(notification);

        }

        EObject elem = resolveSemanticElement();

        if (notification.getNotifier() instanceof Generalization
                && notification.getEventType() == Notification.SET) {

            // This will be from one of the Generalizations we are listening too
            // so we ought to see if we need to refresh
            Object oldValue = notification.getOldValue();
            Object newValue = notification.getNewValue();

            if ((oldValue instanceof Enumeration || oldValue instanceof PrimitiveType)
                    && (newValue instanceof Enumeration || newValue instanceof PrimitiveType)) {

                // Looks like a Generalization's superclass existed in a BOM in
                // a referenced project and the project reference was broken at
                // some point, perhaps. We are here probably because a quickfix
                // has been applied to restore the project reference.
                Classifier oldSuperClass =
                        (Classifier) notification.getOldValue();
                Classifier newSuperClass =
                        (Classifier) notification.getNewValue();

                if (oldSuperClass.eIsProxy() && !newSuperClass.eIsProxy()) {

                    if (elem.eResource() == newSuperClass.eResource()) {
                        // The new superclass is on the same diagram so draw a
                        // connection
                        custFigure.rebuildFigureWithSuperClass(false);
                        refreshGeneralizations();
                    } else {
                        custFigure.rebuildFigureWithSuperClass(true);
                    }

                    List childEPs = getChildren();

                    for (Object object : childEPs) {
                        if (object instanceof EnumerationSuperTypeNameEditPart) {
                            EnumerationSuperTypeNameEditPart ep =
                                    (EnumerationSuperTypeNameEditPart) object;
                            ep.refresh();
                            break;
                        }
                    }
                }
            }

        }

        if (notification.getFeatureID(UMLPackage.class) == UMLPackage.GENERALIZATION_SET) {

            if (elem == notification.getNotifier()) {
                // The Generalization is being set for this editpart's class. We
                // want to add thos generalization to this editpart's semantic
                // listener list. This is so that this editpart can react to
                // changes to the genarlziations feature. This is imporatnt if
                // the parent class gets changed for example and the Class
                // header needs to show the superclass label.
                if (notification.getEventType() == Notification.ADD
                        && notification.getNewValue() != null) {
                    removeSemanticListeners();
                    addSemanticListeners();
                }

            }

            refreshGeneralizations();
            super.handleNotificationEvent(notification);
        } else {
            super.handleNotificationEvent(notification);
        }
    }

    @Override
    protected void addSemanticListeners() {
        super.addSemanticListeners();

        EObject elem = resolveSemanticElement();

        if (elem != null) {
            // We also want to add any generalizations that this Enumeration
            // owns
            if (elem instanceof Enumeration) {
                Enumeration en = (Enumeration) elem;

                if (!en.getGeneralizations().isEmpty()) {
                    Generalization generalization =
                            en.getGeneralizations().get(0);
                    addListenerFilter("SemanticElement", this, generalization);//$NON-NLS-1$
                }

            }

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
        } else if (ep instanceof PackagePackageContentsCompartmentEditPart) {
            ep.refresh();
        } else {
            // ep is going to be the package compartment editpart.
            // So lets get its parent which will be a package editpart
            EditPart ep2 = ep.getParent();
            List childEps = ep2.getChildren();

            for (Object object : childEps) {
                if (object instanceof PackagePackageContentsCompartmentEditPart) {
                    PackagePackageContentsCompartmentEditPart cptEp =
                            (PackagePackageContentsCompartmentEditPart) object;
                    cptEp.refresh();
                    break;
                }
            }
        }
    }

    /*
     * generated NOT
     * 
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart#refreshVisuals
     * ()
     */
    @Override
    public void refreshVisuals() {
        // Override the parent class so that we can set the header gradient

        // update gradient colors
        IPreferenceStore store =
                (IPreferenceStore) BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                        .getPreferenceStore();
        GradientFigureUtil
                .updateFigureGradient(getPrimaryView(),
                        custFigure,
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IBOMPreferenceConstants.PREF_ENUMERATION_GRAD_COLOR1)),
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IBOMPreferenceConstants.PREF_ENUMERATION_GRAD_COLOR2)));

        // call parent
        super.refreshVisuals();

    }

    /**
     * @generated NOT
     */
    public class EnumerationFigure extends EnumerationCustomFigure {

        /**
         * @generated NOT
         */
        private WrappingLabel fFigureEnumerationNameLabel;

        /**
         * @generated NOT
         */
        private WrappingLabel fFigureEnumerationSuperTypeNameLabel;

        /**
         * @generated NOT
         */
        private WrappingLabel fFigureEnumerationStereoTypeLabelFigure;

        /**
         * @generated
         */
        public EnumerationFigure() {

            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(90),
                    getMapMode().DPtoLP(60)));
            createContents();
        }

        /**
         * @generated NOT
         */
        private void createContents() {

            // fFigureEnumerationNameLabel = new WrapLabel();
            // fFigureEnumerationNameLabel.setText("<Enumeration>");
            //
            // this.add(fFigureEnumerationNameLabel);
            //
            // fFigureEnumerationSuperTypeNameLabel = new WrapLabel();
            // fFigureEnumerationSuperTypeNameLabel.setText("<supertype>");
            //
            // this.add(fFigureEnumerationSuperTypeNameLabel);
            //
            // fFigureEnumerationStereoTypeLabelFigure = new WrapLabel();
            // fFigureEnumerationStereoTypeLabelFigure.setText("<<stereotype>>");
            //
            // this.add(fFigureEnumerationStereoTypeLabelFigure);

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

        // /**
        // * @generated
        // */
        // public WrapLabel getFigureEnumerationNameLabel() {
        // return fFigureEnumerationNameLabel;
        // }
        //
        // /**
        // * @generated
        // */
        // public WrapLabel getFigureEnumerationSuperTypeNameLabel() {
        // return fFigureEnumerationSuperTypeNameLabel;
        // }
        //
        // /**
        // * @generated
        // */
        // public WrapLabel getFigureEnumerationStereoTypeLabelFigure() {
        // return fFigureEnumerationStereoTypeLabelFigure;
        // }

        /**
         * @generated NOT
         */
        public WrappingLabel getFigureEnumerationNameLabel() {

            // Call superclass to get WrapLabel
            return super.getClassNameLabel();
        }

        /**
         * @generated NOT
         */
        public WrappingLabel getFigureEnumerationSuperTypeNameLabel() {

            // Call superclass to get WrapLabel
            return super.getSuperClassNameLabel();
        }

        /**
         * @generated NOT
         */
        public WrappingLabel getFigureEnumerationStereoTypeLabelFigure() {
            return super.getStereoTypeLabel();
        }

    }

    @Override
    public Object getPreferredValue(EStructuralFeature feature) {
        Object result =
                GradientFigureUtil.getPreferedPrimitiveTypeGradientValue(this,
                        feature);
        if (result == null) {
            result = super.getPreferredValue(feature);
        }
        return result;
    }

}
