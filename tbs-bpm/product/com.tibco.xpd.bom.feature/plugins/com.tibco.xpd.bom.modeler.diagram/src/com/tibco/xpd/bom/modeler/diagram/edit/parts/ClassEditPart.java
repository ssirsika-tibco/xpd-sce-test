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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;
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
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
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
import org.eclipse.swt.graphics.RGB;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.custom.ConvertClassHeaderColourCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMEditPartUtils;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.ClassCustomFigure;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.BOMPopupBarEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.ClassItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * @generated
 */
public class ClassEditPart extends ShapeNodeEditPart {

    private Logger LOG = BOMDiagramEditorPlugin.getInstance().getLogger();

    /**
     * @generated NOT
     */
    private ResourceSetListener rsl;

    private final Class element;

    /**
     * @generated NOT
     * @return the element
     */
    public Class getElement() {
        return element;
    }

    /**
     * @generated
     */
    public static final int VISUAL_ID = 1002;

    /**
     * @generated
     */
    protected IFigure contentPane;

    /**
     * @generated
     */
    protected IFigure primaryShape;

    /**
     * @generated NOT
     */
    public ClassEditPart(View view) {
        super(view);
        element = (Class) resolveSemanticElement();
    }

    /**
     * @generated NOT
     */
    protected ClassFigure custFigure;

    public ClassFigure getCustFigure() {
        return custFigure;
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
                                if (type == UMLElementTypes.Property_2001) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(UMLVisualIDRegistry
                                                    .getType(ClassClassAttributesCompartmentEditPart.VISUAL_ID));
                                    return compartmentEditPart == null ? null
                                            : compartmentEditPart
                                                    .getCommand(request);
                                }
                                if (type == UMLElementTypes.Operation_2002) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(UMLVisualIDRegistry
                                                    .getType(ClassClassOperationsCompartmentEditPart.VISUAL_ID));
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

                        if (element instanceof Class) {
                            return null;
                        }
                        if (element instanceof Package) {
                            return null;
                        }
                        if (element instanceof PrimitiveType) {
                            return null;
                        }
                        if (element instanceof Operation) {
                            return null;
                        }
                        if (element instanceof Property) {
                            return null;
                        }
                        if (element instanceof Enumeration) {
                            return null;
                        }

                        return super.getReparentCommand(gep);
                    }
                });

        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new ClassItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        // Install a drag-drop edit policy
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy());
        // XXX need an SCR to runtime to have another abstract superclass that
        // would let children add reasonable editpolicies
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // EditPolicyRoles.CONNECTION_HANDLES_ROLE);

        /*
         * Handle first-class profile extensions.
         */
        removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
        installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE,
                new BOMPopupBarEditPolicy());
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
        custFigure = new ClassFigure();
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
    public ClassFigure getPrimaryShape() {
        return (ClassFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof ClassNameEditPart) {
            ((ClassNameEditPart) childEditPart).setLabel(getPrimaryShape()
                    .getFigureClassNameFigure());
            return true;
        }
        if (childEditPart instanceof SuperClassNameLabelEditPart) {
            ((SuperClassNameLabelEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureSuperClassNameLabelFigure());
            return true;
        }
        if (childEditPart instanceof ClassStereoTypeLabelEditPart) {
            ((ClassStereoTypeLabelEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureClassStereoTypeLabelFigure());
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
     * 
     *            Check for ClassClassAttributesCompartmentEditPart and set
     *            compartment constraints
     */
    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        if (addFixedChild(childEditPart)) {
            return;
        }

        // If this is a compartment we need to add a GridData constraint so that
        // the control will always fill the horizontal space
        if (childEditPart instanceof ClassClassAttributesCompartmentEditPart) {
            GridData constraintFFigureCompart =
                    new GridData(GridData.FILL_BOTH);
            constraintFFigureCompart.horizontalAlignment = GridData.FILL;
            constraintFFigureCompart.heightHint = 30;
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
            this.getContentPane().add(child, constraintFFigureCompart, -1);

        } else if (childEditPart instanceof ClassClassOperationsCompartmentEditPart) {
            GridData constraintFFigureCompart2 =
                    new GridData(GridData.FILL_HORIZONTAL);
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
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
     * 
     *            Set the drop shadow
     */
    protected NodeFigure createNodePlate() {
        DefaultSizeNodeFigure result =
                new DefaultSizeNodeFigure(getMapMode().DPtoLP(120),
                        getMapMode().DPtoLP(120));

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
                .getType(ClassNameEditPart.VISUAL_ID));
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
            if (notification.getOldValue() instanceof Class
                    && notification.getNewValue() instanceof Class) {

                /*
                 * Looks like a Generalization's superclass existed in a BOM in
                 * a referenced project and the project reference was broken at
                 * some point. We are here probably because a quickfix has been
                 * applied to restore the project reference. This can also
                 * happen when the user changes one of the ends of the
                 * Generalization by dragging it in the editor
                 */
                Class oldSuperClass = (Class) notification.getOldValue();
                Class newSuperClass = (Class) notification.getNewValue();

                if (oldSuperClass.eIsProxy() && !newSuperClass.eIsProxy()) {

                    ClassFigure figure = this.getCustFigure();

                    if (elem.eResource() == newSuperClass.eResource()) {
                        figure.rebuildFigureWithSuperClass(false);
                        refreshGeneralizations();

                    } else {
                        figure.rebuildFigureWithSuperClass(true);
                    }

                    List childEPs = getChildren();

                    for (Object object : childEPs) {
                        if (object instanceof SuperClassNameLabelEditPart) {
                            SuperClassNameLabelEditPart ep =
                                    (SuperClassNameLabelEditPart) object;
                            ep.refresh();
                            break;
                        }
                    }

                } else if (!oldSuperClass.eIsProxy()
                        && !newSuperClass.eIsProxy()) {
                    /*
                     * User probably dragged one end of the generalization and
                     * added to another class so refresh the generalizations.
                     * Refresh is required as the user may have done this in one
                     * of the diagrams so the other diagrams need to update if
                     * necessary.
                     */
                    refreshGeneralizations();
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
            // We also want to add any generalizations that this Class owns
            if (elem instanceof Class) {
                Class cl = (Class) elem;

                if (!cl.getGeneralizations().isEmpty()) {
                    Generalization generalization =
                            cl.getGeneralizations().get(0);
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
                                        IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR1)),
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR2)));
        // call parent
        super.refreshVisuals();

    }

    /**
     * @generated
     */
    public class ClassFigure extends ClassCustomFigure {

        /**
         * @generated NOT
         */
        // private WrapLabel fFigureClassNameFigure;
        /**
         * @generated NOT
         */
        private WrappingLabel fFigureClassNameFigure;

        /**
         * @generated NOT
         */
        private WrappingLabel fFigureSuperClassNameLabelFigure;

        /**
         * @generated NOT
         */
        private WrappingLabel fFigureClassStereoTypeLabelFigure;

        /**
         * @generated
         */
        public ClassFigure() {

            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(90),
                    getMapMode().DPtoLP(60)));
            createContents();
        }

        /**
         * @generated NOT
         */
        private void createContents() {

            // fFigureClassNameFigure = new WrapLabel();
            // fFigureClassNameFigure.setText("<<class>>");
            //
            // this.add(fFigureClassNameFigure);

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
         * 
         *            Generator generates WrapLabel
         */
        public WrappingLabel getFigureClassNameFigure() {
            return super.getClassNameLabel();
        }

        /**
         * @generated NOT
         * 
         *            Generator generates WrapLabel
         */
        public WrappingLabel getFigureSuperClassNameLabelFigure() {
            return super.getSuperClassNameLabel();
        }

        /**
         * @generated NOT
         * 
         * 
         *            Generator generates WrapLabel
         */
        public WrappingLabel getFigureClassStereoTypeLabelFigure() {
            return super.getStereoTypeLabel();
        }

        @Override
        public void setSuperClassName(String superClassName) {
            super.setSuperClassName(superClassName);
        }

        @Override
        public void removeSuperClassName() {
            super.removeSuperClassName();
        }

    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub

        if (rsl != null) {
            XpdResourcesPlugin.getDefault().getEditingDomain()
                    .removeResourceSetListener(rsl);
            rsl = null;
        }
        super.deactivate();
    }

    @Override
    public void activate() {
        super.activate();

        // Create a Listener to listen to all EMF events. We can sift through
        // them to find the addition of the stereotype

        rsl = new ClassEditPartResourceListener();
        XpdResourcesPlugin.getDefault().getEditingDomain()
                .addResourceSetListener(rsl);

    }

    /**
     * @author rgreen
     * 
     */
    class ClassEditPartResourceListener extends ResourceSetListenerImpl {

        @Override
        public org.eclipse.emf.common.command.Command transactionAboutToCommit(
                ResourceSetChangeEvent event) throws RollbackException {
            // Perform the core operation that we want to do
            org.eclipse.emf.common.command.Command opCmd =
                    performOperation(event, true);

            if (opCmd == null) {
                opCmd = super.transactionAboutToCommit(event);
            }

            return opCmd;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {

            performOperation(event, false);
        }

        public org.eclipse.emf.common.command.Command performOperation(
                ResourceSetChangeEvent event, boolean colourCheck) {
            // Get this element and resource
            EObject resolvedElem = resolveSemanticElement();
            // Make sure it is of class type, as that is all we will deal with
            if (!(resolvedElem instanceof Class) || (resolvedElem == null)) {
                // if cannot resolve, this part has been removed, need to remove
                // the listener.
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .removeResourceSetListener(this);
                return null;
            }
            Class thisClass = (Class) resolvedElem;

            // Loop through all the notification this event contains and
            // locate the one we are interested in.
            List<Notification> notifications = event.getNotifications();

            // Also need to refresh if a stereotype has changed
            if (notifications != null) {
                for (Notification notification : notifications) {
                    // Only deal with cases where the new value is a case class
                    if (!(notification.isTouch())) {
                        if (BOMEditPartUtils.isStereotypeBeingSet(notification)) {
                            // Found it! The resource change relates to
                            // this editparts semantic element.
                            // So set the stereotype label.

                            // NOTE: The following refresh is causing all sorts
                            // of problems when things are deleted - it results
                            // in null pointers and once it then fails we can't
                            // do anything with the BOM that remains without
                            // closing everything and re-opening it. There is no
                            // real comment here as to WHY it needs to do a
                            // forced refresh at this point so I will comment
                            // out this refresh and we can see what happens - I
                            // have not seen any ill effects of commenting it
                            // out from my testing
                            // ///////////////////////////
                            // refresh();

                            if (colourCheck) {

                                // Get the current colour of the header
                                ConvertClassHeaderColourCommand cmd =
                                        getHeaderColourCommand(thisClass);

                                if (cmd != null) {
                                    return cmd;
                                }
                            }
                        }
                    }
                }
            }

            /*
             * If the profile definition has changed then refresh to update the
             * stereotype label
             */
            if (isProfileChange(notifications) && !colourCheck) {
                try {
                    IGraphicalEditPart part =
                            getChildBySemanticHint(String
                                    .valueOf(ClassStereoTypeLabelEditPart.VISUAL_ID));
                    if (part != null) {
                        part.refresh();
                    }
                } catch (NullPointerException e) {
                    // Seems to throw an exception if it can't match anything
                    // It did this before, and I don't know enough about this
                    // area so just ignoring it for now (that in effect was what
                    // it did before)
                }
            }

            return null;
        }

        /**
         * Check if the profile definition has changed.
         * 
         * @param notifications
         * @return
         */
        private boolean isProfileChange(List<Notification> notifications) {
            EObject root = resolveSemanticElement();
            if (root instanceof Element) {
                // Get the model, and make sure it is not null
                Model modelValue = ((Element) root).getModel();
                if (modelValue != null) {
                    EList<Profile> profiles = modelValue.getAppliedProfiles();
                    for (Notification notification : notifications) {
                        if (!notification.isTouch()
                                && profiles
                                        .contains(notification.getNewValue())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Processes the given class to see if a command needs to be created to
         * change the colour of the header
         * 
         * @param thisClass
         * @return
         */
        private ConvertClassHeaderColourCommand getHeaderColourCommand(
                Class thisClass) {
            RGB currentColour =
                    FigureUtilities
                            .integerToRGB(((ShapeGradientStyle) (getPrimaryView()
                                    .getStyle(BomNotationPackage.Literals.SHAPE_GRADIENT_STYLE)))
                                    .getGradStartColor());

            // Get all the pre-defined colours that are used for headers
            IPreferenceStore store =
                    (IPreferenceStore) getDiagramPreferencesHint()
                            .getPreferenceStore();

            RGB localColour =
                    PreferenceConverter.getColor(store,
                            IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR1);
            RGB globalColour =
                    PreferenceConverter.getColor(store,
                            IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR3);
            RGB caseColour =
                    PreferenceConverter.getColor(store,
                            IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR4);

            ConvertClassHeaderColourCommand cmd = null;

            // We only want to change the colour if it is one of the default
            // colours, and is not a user defined one
            if (currentColour.equals(localColour)
                    || currentColour.equals(globalColour)
                    || currentColour.equals(caseColour)) {
                // Default colour is that of the local data class
                RGB headerColour = localColour;

                if (GlobalDataProfileManager.getInstance().isGlobal(thisClass)) {
                    headerColour = globalColour;
                } else if (GlobalDataProfileManager.getInstance()
                        .isCase(thisClass)) {
                    headerColour = caseColour;
                }

                // Need to change the gradient structural feature just like in
                // the property sheet, but this will need to be wrapped in a
                // command
                cmd =
                        new ConvertClassHeaderColourCommand(getEditingDomain(),
                                getPrimaryView(), headerColour, getCustFigure());

            }

            return cmd;
        }
    }

    @Override
    public Object getPreferredValue(EStructuralFeature feature) {
        EObject resolvedElem = resolveSemanticElement();
        Object result = null;

        // If there is a class object found, then we can check to see if this is
        // a case or global class type and set the colour to that value
        if (resolvedElem instanceof Class) {
            Class thisClass = (Class) resolvedElem;

            if (GlobalDataProfileManager.getInstance().isGlobal(thisClass)) {
                result =
                        GradientFigureUtil
                                .getPreferedGlobalClassGradientValue(this,
                                        feature);
            } else if (GlobalDataProfileManager.getInstance().isCase(thisClass)) {
                result =
                        GradientFigureUtil
                                .getPreferedCaseClassGradientValue(this,
                                        feature);
            }
        }

        // The default behaviour is to set to the local class colours
        if (result == null) {
            result =
                    GradientFigureUtil.getPreferedClassGradientValue(this,
                            feature);
            if (result == null) {
                result = super.getPreferredValue(feature);
            }
        }
        return result;
    }
}
