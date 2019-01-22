/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ViewComponentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IPolygonAnchorableFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.BadgeItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.InputStreamBOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @generated
 */
public class BadgeEditPart extends ShapeNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 1007;

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
    public BadgeEditPart(View view) {
        super(view);
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
                                if (type == UMLElementTypes.ProfileApplication_2006) {
                                    EditPart compartmentEditPart =
                                            getChildBySemanticHint(UMLVisualIDRegistry
                                                    .getType(PackageBadgeProfilesCompartmentEditPart.VISUAL_ID));
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
                new BadgeItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        // XXX need an SCR to runtime to have another abstract superclass that
        // would let children add reasonable editpolicies
        // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.
        // EditPolicyRoles.CONNECTION_HANDLES_ROLE);

        // This View doesn't have semantic elements so use a component edit
        // policy that only gets a command to delete the view
        installEditPolicy(EditPolicy.COMPONENT_ROLE,
                new ViewComponentEditPolicy() {

                    @Override
                    protected boolean shouldDeleteSemantic() {
                        // TODO Auto-generated method stub
                        // return super.shouldDeleteSemantic();
                        return false;
                    }

                    @Override
                    protected Command getDeleteCommand(GroupRequest request) {
                        // TODO Auto-generated method stub
                        // return super.getDeleteCommand(request);
                        return null;
                    }

                });

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
        NodeFigure figure = null;
        EObject elem = resolveSemanticElement();

        String bgColor = IBOMPreferenceConstants.PREF_BADGE_BG_COLOR;
        if (elem instanceof Model) {
            Model model = (Model) elem;

            if (isSubDiagram()) {
                // Create Sub-Diagram badge
                figure = new BadgeSubDiagramFigure(false);
                bgColor = IBOMPreferenceConstants.PREF_BADGE_SUBDIAG_BG_COLOR;

            } else if (FirstClassProfileManager.getInstance()
                    .isFirstClassProfileApplied(model)) {
                figure = new BadgeModelFigure(false);
            } else {
                figure = new BadgeModelFigure(true);
            }
        } else if (elem instanceof Package) {
            figure = new BadgePackageFigure(false);
            bgColor = IBOMPreferenceConstants.PREF_BADGE_PACKAGE_BG_COLOR;
        }

        if (figure.getBackgroundColor() == null) {
            IPreferenceStore store =
                    (IPreferenceStore) BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                            .getPreferenceStore();

            figure.setBackgroundColor(DiagramColorRegistry.getInstance()
                    .getColor(PreferenceConverter.getColor(store, bgColor)));
        }
        return primaryShape = figure;
    }

    private boolean isSubDiagram() {
        Object modelNode = getModel();

        if (modelNode instanceof Node) {
            Node node = (Node) modelNode;
            EObject container = node.eContainer();

            if (container instanceof Diagram) {
                if (BomUIUtil.isUserDiagram((Diagram) container)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * @generated
     */
    public BadgeFigure getPrimaryShape() {
        return (BadgeFigure) primaryShape;
    }

    @Override
    protected List getModelChildren() {
        // TODO Auto-generated method stub
        return super.getModelChildren();
    }

    /**
     * @generated NOT
     */
    protected boolean addFixedChild(EditPart childEditPart) {

        BadgeFigure fig = getPrimaryShape();

        if (fig instanceof BadgeModelFigure) {
            BadgeModelFigure modFig = (BadgeModelFigure) fig;

            if (childEditPart instanceof BadgeModelNameEditPart) {
                ((BadgeModelNameEditPart) childEditPart).setLabel(modFig
                        .getFigureModelNameLabelFigure());
                return true;
            }
            if (childEditPart instanceof BadgeLabelAuthorEditPart) {
                ((BadgeLabelAuthorEditPart) childEditPart).setLabel(modFig
                        .getFigureLabelAuthorLabelFigure());
                return true;
            }
            if (childEditPart instanceof BadgeAuthorEditPart) {
                ((BadgeAuthorEditPart) childEditPart).setLabel(modFig
                        .getFigureAuthorLabelFigure());
                return true;
            }
            if (childEditPart instanceof BadgeLabelModelIconEditPart) {
                ((BadgeLabelModelIconEditPart) childEditPart).setLabel(modFig
                        .getFigureModelIconLabelFigure());
                return true;
            }
            if (childEditPart instanceof BadgeLabelDateCreatedEditPart) {
                ((BadgeLabelDateCreatedEditPart) childEditPart).setLabel(modFig
                        .getFigureLabelDateCreatedLabelFigure());
                return true;
            }
            if (childEditPart instanceof BadgeDateCreatedEditPart) {
                ((BadgeDateCreatedEditPart) childEditPart).setLabel(modFig
                        .getFigureDateCreatedLabelFigure());
                return true;
            }
            if (childEditPart instanceof BadgeLabelDateModifiedEditPart) {
                ((BadgeLabelDateModifiedEditPart) childEditPart)
                        .setLabel(modFig
                                .getFigureLabelDateModifiedLabelFigure());
                return true;
            }
            if (childEditPart instanceof BadgeDateModifiedEditPart) {
                ((BadgeDateModifiedEditPart) childEditPart).setLabel(modFig
                        .getFigureDateModifiedLabelFigure());
                return true;
            }
            if (childEditPart instanceof PackageBadgeProfilesCompartmentEditPart) {
                GridData constraintFFigureCompart =
                        new GridData(GridData.FILL_BOTH);
                constraintFFigureCompart.horizontalAlignment = GridData.FILL;
                constraintFFigureCompart.horizontalSpan = 3;
                IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
                this.getContentPane().add(child, constraintFFigureCompart, -1);
                return true;
            }

        }

        if (fig instanceof BadgePackageFigure) {

            BadgePackageFigure pkgFig = (BadgePackageFigure) fig;

            if (childEditPart instanceof BadgeModelNameEditPart) {
                ((BadgeModelNameEditPart) childEditPart).setLabel(pkgFig
                        .getFigureModelNameLabelFigure());
                return true;
            }

            if (childEditPart instanceof PackageBadgeProfilesCompartmentEditPart) {
                IFigure pane = pkgFig.getFigureProfilesCompartmentBaseFigure();
                setupContentPane(pane); // FIXME each comparment should handle
                // his
                // content pane in his own way
                pane.add(((PackageBadgeProfilesCompartmentEditPart) childEditPart)
                        .getFigure());
                return true;
            }
        }

        if (fig instanceof BadgeSubDiagramFigure) {

            BadgeSubDiagramFigure subDiagFig = (BadgeSubDiagramFigure) fig;

            if (childEditPart instanceof BadgeDiagramNameEditPart) {
                ((BadgeDiagramNameEditPart) childEditPart).setLabel(subDiagFig
                        .getFigureDiagramNameLabelFigure());
                return true;
            }

            if (childEditPart instanceof BadgeModelNameEditPart) {
                ((BadgeModelNameEditPart) childEditPart).setLabel(subDiagFig
                        .getFigureModelNameLabelFigure());
                return true;
            }

            if (childEditPart instanceof BadgeLabelModelIconEditPart) {
                ((BadgeLabelModelIconEditPart) childEditPart)
                        .setLabel(subDiagFig.getFigureModelIconLabelFigure());
                return true;
            }

            if (childEditPart instanceof PackageBadgeProfilesCompartmentEditPart) {
                IFigure pane =
                        subDiagFig.getFigureProfilesCompartmentBaseFigure();
                setupContentPane(pane); // FIXME each comparment should handle
                // his
                // content pane in his own way
                pane.add(((PackageBadgeProfilesCompartmentEditPart) childEditPart)
                        .getFigure());
                return true;
            }
        }

        return false;
    }

    /**
     * @generated NOT
     */
    protected boolean removeFixedChild(EditPart childEditPart) {

        /*
         * TEMPORARY !!!
         */

        if (childEditPart instanceof PackageBadgeProfilesCompartmentEditPart) {

            BadgeFigure fig = getPrimaryShape();

            if (fig instanceof BadgeModelFigure) {
                BadgeModelFigure modFig = (BadgeModelFigure) fig;

                IFigure pane = modFig.getFigureProfilesCompartmentBaseFigure();
                setupContentPane(pane); // FIXME each comparment should handle
                // his
                // content pane in his own way
                pane.remove(((PackageBadgeProfilesCompartmentEditPart) childEditPart)
                        .getFigure());
                return true;
            }

            if (fig instanceof BadgeModelFigure) {
                BadgePackageFigure modFig = (BadgePackageFigure) fig;

                IFigure pane = modFig.getFigureProfilesCompartmentBaseFigure();
                setupContentPane(pane); // FIXME each comparment should handle
                // his
                // content pane in his own way
                pane.remove(((PackageBadgeProfilesCompartmentEditPart) childEditPart)
                        .getFigure());
                return true;

            }

        }
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

        /*
         * TEMPORARY
         */
        if (editPart instanceof PackageBadgeProfilesCompartmentEditPart) {

            BadgeFigure fig = getPrimaryShape();

            if (fig instanceof BadgeModelFigure) {
                BadgeModelFigure modFig = (BadgeModelFigure) fig;

                return modFig.getFigureProfilesCompartmentBaseFigure();
            }

            if (fig instanceof BadgeModelFigure) {
                BadgePackageFigure pkgFig = (BadgePackageFigure) fig;

                return pkgFig.getFigureProfilesCompartmentBaseFigure();
            }
        }

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
                .getType(BadgeModelNameEditPart.VISUAL_ID));
    }

    @Override
    protected void handleNotificationEvent(Notification notification) {
        // TODO Auto-generated method stub
        super.handleNotificationEvent(notification);
    }

    /**
     * @generated NOT
     */
    public class BadgeModelFigure extends BadgeFigure implements
            IPolygonAnchorableFigure {

        private WrappingLabel fFigureModelNameLabelFigure;

        private WrappingLabel fFigureLabelAuthorLabelFigure;

        private WrappingLabel fFigureAuthorLabelFigure;

        private WrappingLabel fFigureLabelDateCreatedLabelFigure;

        private WrappingLabel fFigureDateCreatedLabelFigure;

        private WrappingLabel fFigureLabelDateModifiedLabelFigure;

        private WrappingLabel fFigureDateModifiedLabelFigure;

        private WrappingLabel fFigureModelIconLabelFigure;

        private RectangleFigure fFigureProfilesCompartmentBaseFigure;

        private boolean isShowProfiles = true;

        public BadgeModelFigure(boolean showProfiles) {
            super();
            isShowProfiles = showProfiles;
            createContents();
        }

        /**
         * @generated NOT
         */
        private void createContents() {

            /*
             * If opening a read-only resource then add label to badge
             */
            if (isReadOnly()) {
                WrappingLabel label =
                        new WrappingLabel(Messages.BadgeEditPart_readOnly_label);
                GridData data =
                        new GridData(SWT.CENTER, SWT.CENTER, true, false);
                data.horizontalSpan = 3;
                add(label);
                setConstraint(label, data);
            }

            // Model label
            GridData gdModelName = new GridData();
            gdModelName.horizontalAlignment = SWT.CENTER;
            gdModelName.grabExcessHorizontalSpace = true;
            gdModelName.horizontalSpan = 3;

            fFigureModelNameLabelFigure = new WrappingLabel();
            fFigureModelNameLabelFigure.setText("Model"); //$NON-NLS-1$
            this.add(fFigureModelNameLabelFigure);
            this.setConstraint(fFigureModelNameLabelFigure, gdModelName);

            // Author Label
            GridData gdLabelAuthor = new GridData();
            fFigureLabelAuthorLabelFigure = new WrappingLabel();
            fFigureLabelAuthorLabelFigure
                    .setText(Messages.BadgeEditPart_Author_label);
            this.add(fFigureLabelAuthorLabelFigure);
            this.setConstraint(fFigureLabelAuthorLabelFigure, gdLabelAuthor);

            // Author name
            GridData gdAuthor = new GridData();
            fFigureAuthorLabelFigure = new WrappingLabel();
            fFigureAuthorLabelFigure.setText("<Author>"); //$NON-NLS-1$
            this.add(fFigureAuthorLabelFigure);
            this.setConstraint(fFigureAuthorLabelFigure, gdAuthor);

            // Icon
            GridData gdIcon = new GridData();
            gdIcon.horizontalAlignment = SWT.LEFT;
            gdIcon.verticalAlignment = SWT.TOP;
            gdIcon.grabExcessHorizontalSpace = true;
            gdIcon.verticalSpan = 3;
            gdIcon.horizontalIndent = 15;
            fFigureModelIconLabelFigure = new WrappingLabel();
            this.add(fFigureModelIconLabelFigure);
            this.setConstraint(fFigureModelIconLabelFigure, gdIcon);

            // Date created label
            GridData gdLabelDateCreated = new GridData();
            fFigureLabelDateCreatedLabelFigure = new WrappingLabel();
            fFigureLabelDateCreatedLabelFigure
                    .setText(Messages.BadgeEditPart_DateCreated_label);
            this.add(fFigureLabelDateCreatedLabelFigure);
            setConstraint(fFigureLabelDateCreatedLabelFigure,
                    gdLabelDateCreated);

            // Date created
            GridData gdDateCreated = new GridData();
            fFigureDateCreatedLabelFigure = new WrappingLabel();
            fFigureDateCreatedLabelFigure.setText("<Date Created>"); //$NON-NLS-1$
            this.add(fFigureDateCreatedLabelFigure);
            setConstraint(fFigureDateCreatedLabelFigure, gdDateCreated);

            // Date modified label
            GridData gdLabelDateMod = new GridData();
            fFigureLabelDateModifiedLabelFigure = new WrappingLabel();
            fFigureLabelDateModifiedLabelFigure
                    .setText(Messages.BadgeEditPart_DateModified_label);
            this.add(fFigureLabelDateModifiedLabelFigure);
            setConstraint(fFigureLabelDateModifiedLabelFigure, gdLabelDateMod);

            // Date modified
            GridData gdDateMod = new GridData();
            fFigureDateModifiedLabelFigure = new WrappingLabel();
            fFigureDateModifiedLabelFigure.setText("<Date Modified>"); //$NON-NLS-1$
            this.add(fFigureDateModifiedLabelFigure);
            setConstraint(fFigureDateModifiedLabelFigure, gdDateMod);

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
        protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
            myUseLocalCoordinates = useLocalCoordinates;
        }

        public WrappingLabel getFigureModelNameLabelFigure() {
            return fFigureModelNameLabelFigure;
        }

        public WrappingLabel getFigureLabelAuthorLabelFigure() {
            return fFigureLabelAuthorLabelFigure;
        }

        public WrappingLabel getFigureAuthorLabelFigure() {
            return fFigureAuthorLabelFigure;
        }

        public WrappingLabel getFigureLabelDateCreatedLabelFigure() {
            return fFigureLabelDateCreatedLabelFigure;
        }

        public WrappingLabel getFigureDateCreatedLabelFigure() {
            return fFigureDateCreatedLabelFigure;
        }

        public WrappingLabel getFigureLabelDateModifiedLabelFigure() {
            return fFigureLabelDateModifiedLabelFigure;
        }

        public WrappingLabel getFigureDateModifiedLabelFigure() {
            return fFigureDateModifiedLabelFigure;
        }

        public WrappingLabel getFigureModelIconLabelFigure() {
            return fFigureModelIconLabelFigure;
        }

        public RectangleFigure getFigureProfilesCompartmentBaseFigure() {
            return fFigureProfilesCompartmentBaseFigure;
        }

    }

    /**
     * @generated NOT
     */
    public class BadgeSubDiagramFigure extends BadgeFigure implements
            IPolygonAnchorableFigure {

        private WrappingLabel fFigureDiagramNameLabelFigure;

        private WrappingLabel fFigureModelNameLabelFigure;

        private WrappingLabel fFigureModelIconLabelFigure;

        private boolean isShowProfiles = true;

        private WrappingLabel shortcutLabelFigure;

        private WrappingLabel linkToModelLabelFigure;

        private RectangleFigure fFigureProfilesCompartmentBaseFigure;

        public BadgeSubDiagramFigure(boolean showProfiles) {
            super();
            isShowProfiles = showProfiles;
            createContents();
        }

        /**
         * @generated NOT
         */
        private void createContents() {

            // Diagram Name
            GridData gdDiagramName = new GridData();
            gdDiagramName.horizontalAlignment = SWT.CENTER;
            gdDiagramName.grabExcessHorizontalSpace = true;
            gdDiagramName.horizontalSpan = 2;
            gdDiagramName.verticalAlignment = SWT.BOTTOM;
            gdDiagramName.grabExcessVerticalSpace = true;
            fFigureDiagramNameLabelFigure = new WrappingLabel();
            fFigureDiagramNameLabelFigure.setText("Diagram"); //$NON-NLS-1$
            this.add(fFigureDiagramNameLabelFigure);
            this.setConstraint(fFigureDiagramNameLabelFigure, gdDiagramName);

            // Icon
            GridData gdIcon = new GridData();
            gdIcon.horizontalAlignment = SWT.LEFT;
            gdIcon.verticalAlignment = SWT.TOP;
            gdIcon.grabExcessHorizontalSpace = true;
            gdIcon.verticalSpan = 3;
            gdIcon.horizontalIndent = 10;
            fFigureModelIconLabelFigure = new WrappingLabel();
            this.add(fFigureModelIconLabelFigure);
            this.setConstraint(fFigureModelIconLabelFigure, gdIcon);

            // Model label
            GridData gdModelName = new GridData();
            gdModelName.horizontalAlignment = SWT.CENTER;
            gdModelName.grabExcessHorizontalSpace = true;
            gdModelName.grabExcessVerticalSpace = true;
            gdModelName.horizontalSpan = 2;

            fFigureModelNameLabelFigure = new WrappingLabel();
            fFigureModelNameLabelFigure.setText("Model"); //$NON-NLS-1$
            this.add(fFigureModelNameLabelFigure);
            this.setConstraint(fFigureModelNameLabelFigure, gdModelName);

            // "Hyperlink" to model
            linkToModelLabelFigure = new WrappingLabel();
            GridData gdLink = new GridData();
            gdLink.grabExcessHorizontalSpace = true;
            gdLink.grabExcessVerticalSpace = true;
            gdLink.horizontalAlignment = SWT.RIGHT;
            // gdLink.horizontalSpan = 2;

            Label hyperlinkToolTip = new Label();
            hyperlinkToolTip
                    .setText(Messages.BadgeEditPart_openModelEditor_hyperlink_tooltip);
            linkToModelLabelFigure.setToolTip(hyperlinkToolTip);

            linkToModelLabelFigure
                    .setForegroundColor(DiagramColorConstants.diagramBlue);
            linkToModelLabelFigure.setTextUnderline(true);
            linkToModelLabelFigure
                    .setText(Messages.BadgeEditPart_returnToMode_hyperlink_label);
            linkToModelLabelFigure
                    .addMouseListener(new SwitchDiagramMouseListener());

            this.add(linkToModelLabelFigure);
            this.setConstraint(linkToModelLabelFigure, gdLink);

            // Shortcut icon to OrgModel
            // icon
            shortcutLabelFigure = new WrappingLabel();
            GridData gdShortcut = new GridData();
            gdShortcut.grabExcessHorizontalSpace = true;
            gdShortcut.grabExcessVerticalSpace = true;
            gdShortcut.horizontalAlignment = SWT.LEFT;

            Label shortcutToolTip = new Label();
            shortcutToolTip
                    .setText(Messages.BadgeEditPart_openModelEditor_hyperlink_tooltip);
            shortcutLabelFigure.setToolTip(shortcutToolTip);

            Image img =
                    com.tibco.xpd.bom.resources.ui.Activator.getDefault()
                            .getImageRegistry().get(BOMImages.BADGESHORTCUT);

            shortcutLabelFigure.setIcon(img, 1);
            shortcutLabelFigure
                    .addMouseListener(new SwitchDiagramMouseListener());

            this.add(shortcutLabelFigure);
            this.setConstraint(shortcutLabelFigure, gdShortcut);

        }

        private class SwitchDiagramMouseListener implements MouseListener {

            public SwitchDiagramMouseListener() {

            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                // Open the main (parent) orgmodel diagram
                EObject element = resolveSemanticElement();
                EObject objToOpen = null;
                IWorkbenchPage page =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();

                if (page != null) {
                    if (element instanceof Package) {

                        objToOpen = ((Package) element).getModel();

                        if (objToOpen == null) {
                            return;
                        } else {
                            IFile file = WorkingCopyUtil.getFile(objToOpen);

                            if (file != null) {
                                try {
                                    IDE.openEditor(page, file);
                                } catch (PartInitException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }

            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

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
        protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
            myUseLocalCoordinates = useLocalCoordinates;
        }

        public WrappingLabel getFigureDiagramNameLabelFigure() {
            return fFigureDiagramNameLabelFigure;
        }

        public WrappingLabel getFigureModelNameLabelFigure() {
            return fFigureModelNameLabelFigure;
        }

        public WrappingLabel getFigureModelIconLabelFigure() {
            return fFigureModelIconLabelFigure;
        }

        public RectangleFigure getFigureProfilesCompartmentBaseFigure() {
            return fFigureProfilesCompartmentBaseFigure;
        }

    }

    /**
     * @generated NOT
     */
    public class BadgePackageFigure extends BadgeFigure implements
            IPolygonAnchorableFigure {

        private WrappingLabel fFigurePackageNameLabelFigure;

        private WrappingLabel fFigureLabelModelLabelFigure;

        private WrappingLabel fFigureModelLabelFigure;

        private WrappingLabel fFigurePackageIconLabelFigure;

        private RectangleFigure fFigureProfilesCompartmentBaseFigure;

        private boolean isShowProfiles = true;

        private WrappingLabel linkToModelLabelFigure;

        private WrappingLabel shortcutLabelFigure;

        /**
         * @generated NOT
         */
        public BadgePackageFigure(boolean showProfiles) {
            super();
            isShowProfiles = showProfiles;
            createContents();
        }

        /**
         * @generated NOT
         */
        private void createContents() {

            /*
             * If opening a read-only resource then add label to badge
             */
            if (isReadOnly()) {
                WrappingLabel label =
                        new WrappingLabel(Messages.BadgeEditPart_readOnly_label);
                GridData data =
                        new GridData(SWT.CENTER, SWT.CENTER, true, false);
                data.horizontalSpan = 3;
                add(label);
                setConstraint(label, data);
            }

            // Package Name
            GridData gdPackageName = new GridData();
            gdPackageName.horizontalAlignment = SWT.CENTER;
            gdPackageName.grabExcessHorizontalSpace = true;
            gdPackageName.horizontalSpan = 3;

            fFigurePackageNameLabelFigure = new WrappingLabel();
            fFigurePackageNameLabelFigure.setText("Model"); //$NON-NLS-1$
            this.add(fFigurePackageNameLabelFigure);
            this.setConstraint(fFigurePackageNameLabelFigure, gdPackageName);

            // "Hyperlink" to model
            linkToModelLabelFigure = new WrappingLabel();
            GridData gdLink = new GridData();
            gdLink.grabExcessHorizontalSpace = true;
            gdLink.grabExcessVerticalSpace = true;
            gdLink.horizontalAlignment = SWT.RIGHT;
            gdLink.horizontalSpan = 2;

            Label hyperlinkToolTip = new Label();
            hyperlinkToolTip
                    .setText(Messages.BadgeEditPart_openModelEditor_hyperlink_tooltip);
            linkToModelLabelFigure.setToolTip(hyperlinkToolTip);

            linkToModelLabelFigure
                    .setForegroundColor(DiagramColorConstants.diagramBlue);
            linkToModelLabelFigure.setTextUnderline(true);
            linkToModelLabelFigure
                    .setText(Messages.BadgeEditPart_returnToMode_hyperlink_label);
            linkToModelLabelFigure
                    .addMouseListener(new SwitchDiagramMouseListener());

            this.add(linkToModelLabelFigure);
            this.setConstraint(linkToModelLabelFigure, gdLink);

            // Shortcut icon to OrgModel
            // icon
            shortcutLabelFigure = new WrappingLabel();
            GridData gdShortcut = new GridData();
            gdShortcut.grabExcessHorizontalSpace = true;
            gdShortcut.grabExcessVerticalSpace = true;
            gdShortcut.horizontalAlignment = SWT.LEFT;

            Label shortcutToolTip = new Label();
            shortcutToolTip
                    .setText(Messages.BadgeEditPart_openModelEditor_hyperlink_tooltip);
            shortcutLabelFigure.setToolTip(shortcutToolTip);

            Image img =
                    com.tibco.xpd.bom.resources.ui.Activator.getDefault()
                            .getImageRegistry().get(BOMImages.BADGESHORTCUT);

            shortcutLabelFigure.setIcon(img, 1);
            shortcutLabelFigure
                    .addMouseListener(new SwitchDiagramMouseListener());

            this.add(shortcutLabelFigure);
            this.setConstraint(shortcutLabelFigure, gdShortcut);

        }

        private class SwitchDiagramMouseListener implements MouseListener {

            public SwitchDiagramMouseListener() {

            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                // Open the main (parent) orgmodel diagram
                EObject element = resolveSemanticElement();
                EObject objToOpen = null;
                IWorkbenchPage page =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();

                if (page != null) {
                    if (element instanceof Package) {

                        objToOpen = ((Package) element).getModel();

                        if (objToOpen == null) {
                            return;
                        } else {
                            IFile file = WorkingCopyUtil.getFile(objToOpen);

                            if (file != null) {
                                try {
                                    IDE.openEditor(page, file);
                                } catch (PartInitException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }

            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

        }

        private boolean myUseLocalCoordinates = false;

        @Override
        protected boolean useLocalCoordinates() {
            return myUseLocalCoordinates;
        }

        protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
            myUseLocalCoordinates = useLocalCoordinates;
        }

        public WrappingLabel getFigureModelNameLabelFigure() {
            return fFigurePackageNameLabelFigure;
        }

        public WrappingLabel getFigureLabelModelLabelFigure() {
            return fFigureLabelModelLabelFigure;
        }

        public WrappingLabel getFigureModelLabelFigure() {
            return fFigureModelLabelFigure;
        }

        public WrappingLabel getFigurePackageIconLabelFigure() {
            return fFigurePackageIconLabelFigure;
        }

        public RectangleFigure getFigureProfilesCompartmentBaseFigure() {
            return fFigureProfilesCompartmentBaseFigure;
        }

    }

    /**
     * @generated NOT
     * 
     *            Abstract class for the Badge figure. Creates a basic
     *            NodeFigure with a 3 column gridlayout and the characteristic
     *            UML Model label shape. Currently it is left to the extending
     *            classes to implement all the children BUT there is scope to
     *            move some of these into this superclass e.g. Name, Icon and
     *            Profile compartment.
     * 
     */
    public abstract class BadgeFigure extends NodeFigure implements
            IPolygonAnchorableFigure {

        public BadgeFigure() {
            super();
            GridLayout layout = new GridLayout();
            layout.numColumns = 3;
            layout.marginWidth = 10;
            layout.marginHeight = 10;
            setLayoutManager(layout);
        }

        private boolean myUseLocalCoordinates = false;

        @Override
        protected boolean useLocalCoordinates() {
            return myUseLocalCoordinates;
        }

        @Override
        protected void paintBorder(Graphics g) {

            Rectangle r = getBounds().getCopy();
            r.shrink(getLineWidth() / 2, getLineWidth() / 2);

            PointList p = getPointList(r);
            g.setLineWidth(getLineWidth());
            g.setLineStyle(getLineStyle());
            g.drawPolyline(p);

            // PointList corner = new PointList();
            // corner.addPoint(r.x + r.width - getClipWidth(), r.y);
            // corner.addPoint(r.x + r.width - getClipWidth(), r.y
            // + getClipHeight());
            // corner.addPoint(r.x + r.width, r.y + getClipHeight());
            // g.drawPolyline(corner);

        }

        @Override
        public void paintFigure(Graphics graphics) {
            super.paintFigure(graphics);
            Rectangle r = getBounds();
            PointList p = getPointList(r);
            graphics.fillPolygon(p);

        }

        /**
         * Method getPointList.
         * 
         * @param r
         * @return PointList
         */
        protected PointList getPointList(Rectangle r) {
            PointList p = new PointList();
            p.addPoint(r.x, r.y);
            p.addPoint(r.x + r.width - 1, r.y);
            p.addPoint(r.x + r.width - 1, r.y + r.height
                    - MapModeUtil.getMapMode(this).DPtoLP(20));
            p.addPoint(r.x + r.width - MapModeUtil.getMapMode(this).DPtoLP(20),
                    r.y + r.height - 1);
            p.addPoint(r.x, r.y + r.height - 1);
            p.addPoint(r.x, r.y);

            return p;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.gmf.runtime.draw2d.ui.figures.IPolygonAnchorableFigure
         * #getPolygonPoints()
         */
        @Override
        public PointList getPolygonPoints() {
            return getPointList(getBounds());
        }
    }

}
