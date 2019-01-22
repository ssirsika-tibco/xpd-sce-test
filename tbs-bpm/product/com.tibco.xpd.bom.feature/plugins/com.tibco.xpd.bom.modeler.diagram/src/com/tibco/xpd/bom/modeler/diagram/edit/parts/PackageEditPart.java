/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
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
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures.PackageCustomFigure;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.OpenDiagramEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.PackageCanonicalEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.PackageItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.wc.InputStreamBOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @generated
 */
public class PackageEditPart extends ShapeNodeEditPart {

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
    public PackageEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    PackageFigure custFigure;

    public PackageFigure getCustFigure() {
        return custFigure;
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();

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

                                if (type == UMLElementTypes.Package_1001
                                        || type == UMLElementTypes.Class_1002
                                        || type == UMLElementTypes.PrimitiveType_1003
                                        || type == UMLElementTypes.Enumeration_1004
                                        || type == UMLElementTypes.AssociationClass_1006) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(UMLVisualIDRegistry
                                                    .getType(PackagePackageContentsCompartmentEditPart.VISUAL_ID));

                                    return compartmentEditPart == null ? null
                                            : compartmentEditPart
                                                    .getCommand(request);
                                }
                            }
                        }
                        return super.getCommand(request);
                    }

                });

        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new PackageItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());

        // Disallow opening of packages in read-only resources
        if (!isReadOnly()) {
            installEditPolicy(EditPolicyRoles.OPEN_ROLE,
                    new OpenDiagramEditPolicy());
        }

        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new PackageCanonicalEditPolicy());

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
        custFigure = new PackageFigure();

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
    public PackageFigure getPrimaryShape() {
        return (PackageFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof PackageNameEditPart) {
            ((PackageNameEditPart) childEditPart).setLabel(getPrimaryShape()
                    .getFigurePackageNameFigure());
            return true;
        }
        if (childEditPart instanceof PackageStereoTypeLabelEditPart) {
            ((PackageStereoTypeLabelEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigurePackageStereoTypeLabelFigure());
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
        if (childEditPart instanceof PackagePackageContentsCompartmentEditPart) {

            GridData constraintFFigureCompart =
                    new GridData(GridData.FILL_BOTH);
            constraintFFigureCompart.horizontalAlignment = GridData.FILL;
            constraintFFigureCompart.verticalAlignment = GridData.FILL;
            constraintFFigureCompart.horizontalSpan = 2;

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
     * @generated
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
                .getType(PackageNameEditPart.VISUAL_ID));
    }

    /**
     * generated NOT
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#handlePropertyChangeEvent(java.beans.PropertyChangeEvent)
     */
    @Override
    protected void handleNotificationEvent(Notification notification) {

        // handle gradient changes
        if (GradientFigureUtil.isGradientChange(notification)) {
            refreshVisuals();
            return;
        }
        super.handleNotificationEvent(notification);
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

        IPreferenceStore store =
                (IPreferenceStore) BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                        .getPreferenceStore();
        GradientFigureUtil
                .updateFigureGradient(getPrimaryView(),
                        custFigure,
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR1)),
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR2)));
        super.refreshVisuals();

    }

    /**
     * @generated
     */
    public class PackageFigure extends PackageCustomFigure {

        /**
         * @generated NOT
         */
        // private WrapLabel fFigurePackageNameFigure;
        /**
         * @generated
         */
        private WrappingLabel fFigurePackageNameFigure;

        /**
         * @generated
         */
        private WrappingLabel fFigurePackageStereoTypeLabelFigure;

        /**
         * @generated NOT
         */
        public PackageFigure() {

            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(220),
                    getMapMode().DPtoLP(150)));

            this.setPreferredSize(new Dimension(getMapMode().DPtoLP(220),
                    getMapMode().DPtoLP(150)));

            createContents();
        }

        /**
         * @generated NOT
         */
        private void createContents() {

            // fFigurePackageNameFigure = new WrapLabel();
            // fFigurePackageNameFigure.setText("<Package>");
            //
            // this.add(fFigurePackageNameFigure);

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
        public WrappingLabel getFigurePackageNameFigure() {
            return super.getPackageNameLabel();
        }

        /**
         * @generated NOT
         */
        public WrappingLabel getFigurePackageStereoTypeLabelFigure() {
            return super.getStereoTypeLabel();
        }

    }

    /**
     * generated NOT
     * 
     * @override
     */
    /** Invoke the editpart's refresh mechanism. */
    @Override
    public void refresh() {
        PackageCanonicalEditPolicy epol =
                (PackageCanonicalEditPolicy) getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
        epol.refresh();
        super.refresh();
    }

    @Override
    public Object getPreferredValue(EStructuralFeature feature) {
        Object result =
                GradientFigureUtil.getPreferedPackageGradientValue(this,
                        feature);
        if (result == null) {
            result = super.getPreferredValue(feature);
        }
        return result;
    }

    /**
     * Check if the resource being edited is read-only (resource history).
     * 
     * @return
     * @since 3.5
     */
    private boolean isReadOnly() {
        Diagram view = getDiagramView();
        if (view != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(view);
            if (wc == null || wc instanceof InputStreamBOMWorkingCopy) {
                return true;
            }
        }
        return false;
    }

}
