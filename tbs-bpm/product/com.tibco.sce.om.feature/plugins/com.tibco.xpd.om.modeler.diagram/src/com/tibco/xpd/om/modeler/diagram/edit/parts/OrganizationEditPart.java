package com.tibco.xpd.om.modeler.diagram.edit.parts;

import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OpenSubDiagramEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.preferences.custom.IOMPreferenceConstants;

/**
 * @generated
 */
public class OrganizationEditPart extends ShapeNodeEditPart {

    public static final String DYNAMIC = "isDynamic"; //$NON-NLS-1$

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

    private Cursor handCursor;

    /**
     * @generated
     */
    public OrganizationEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrganizationItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
        installEditPolicy(EditPolicyRoles.OPEN_ROLE,
                new OpenSubDiagramEditPolicy());
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
        OrganizationFigure figure = new OrganizationFigure();
        IPreferenceStore store =
                (IPreferenceStore) OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                        .getPreferenceStore();
        if (figure.getBackgroundColor() == null) {

            figure.setBackgroundColor(DiagramColorRegistry.getInstance()
                    .getColor(PreferenceConverter.getColor(store,
                            IOMPreferenceConstants.PREF_ORGUNIT_FILL_COLOR)));
        }

        return primaryShape = figure;
    }

    /**
     * Get a hand cursor - this will lazy load an instance of the cursor.
     * 
     * @return
     */
    private Cursor getHandCursor() {
        if (handCursor == null) {
            handCursor = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
        }
        return handCursor;
    }

    @Override
    public void deactivate() {
        if (handCursor != null) {
            handCursor.dispose();
        }
        super.deactivate();
    }

    /**
     * @generated
     */
    public OrganizationFigure getPrimaryShape() {
        return (OrganizationFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof OrganizationDisplayNameEditPart) {
            ((OrganizationDisplayNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrganizationFigureNameLabel());
            return true;
        }
        if (childEditPart instanceof OrganizationTypeNameEditPart) {
            ((OrganizationTypeNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrganizationFigureTypeLabel());
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
        if (childEditPart instanceof OrganizationOrgUnitCompartmentEditPart) {
            GridData constraintFFigureCompart = new GridData();
            constraintFFigureCompart.horizontalAlignment = GridData.FILL;
            constraintFFigureCompart.verticalAlignment = GridData.FILL;
            constraintFFigureCompart.horizontalSpan = 2;
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
            this.getContentPane().add(child, constraintFFigureCompart, -1);

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
        return getChildBySemanticHint(OrganizationModelVisualIDRegistry
                .getType(OrganizationDisplayNameEditPart.VISUAL_ID));
    }

    /**
     * Check if the underlying Organization is a Dynamic Organization.
     * 
     * @return
     */
    private boolean isDynamic() {
        EObject eo = resolveSemanticElement();
        if (eo instanceof Organization) {
            return ((Organization) eo).isDynamic();
        }
        return false;
    }

    /**
     * @generated NOT
     */
    public class OrganizationFigure extends RoundedRectangle {

        /**
         * @generated
         */
        private WrappingLabel fFigureOrganizationFigureNameLabel;

        /**
         * @generated
         */
        private WrappingLabel fFigureOrganizationFigureTypeLabel;

        private GridData constraintFFigureOrganizationFigureTypeLabel;

        private GridData constraintFFigureOrganizationFigureNameLabel;

        private WrappingLabel orgShortCutIcon;

        private GridData gdIcon;

        /**
         * @generated NOT
         */
        public OrganizationFigure() {

            GridLayout layoutThis = new GridLayout();
            layoutThis.numColumns = 2;
            layoutThis.makeColumnsEqualWidth = false;
            this.setLayoutManager(layoutThis);

            this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(30),
                    getMapMode().DPtoLP(30)));
            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(150),
                    getMapMode().DPtoLP(100)));

            createContents();
        }

        /**
         * @see org.eclipse.draw2d.RoundedRectangle#outlineShape(org.eclipse.draw2d.Graphics)
         * 
         * @param graphics
         */
        @Override
        protected void outlineShape(Graphics graphics) {
            if (isDynamic()) {
                /*
                 * For dynamic organization draw a custom dashed line around the
                 * border.
                 */
                graphics.setLineStyle(Graphics.LINE_CUSTOM);
                graphics.setLineDash(new int[] { 6, 4 });
            }
            super.outlineShape(graphics);
        }

        /**
         * @generated NOT
         */
        private void createContents() {

            // Type Label
            fFigureOrganizationFigureTypeLabel = new WrappingLabel();
            fFigureOrganizationFigureTypeLabel
                    .setText(Messages.OrganizationEditPart_type_label);

            constraintFFigureOrganizationFigureTypeLabel = new GridData();
            constraintFFigureOrganizationFigureTypeLabel.verticalAlignment =
                    GridData.CENTER;
            constraintFFigureOrganizationFigureTypeLabel.horizontalAlignment =
                    GridData.CENTER;
            constraintFFigureOrganizationFigureTypeLabel.horizontalIndent = 0;
            constraintFFigureOrganizationFigureTypeLabel.horizontalSpan = 2;
            constraintFFigureOrganizationFigureTypeLabel.grabExcessHorizontalSpace =
                    true;
            constraintFFigureOrganizationFigureTypeLabel.grabExcessVerticalSpace =
                    false;
            this.add(fFigureOrganizationFigureTypeLabel,
                    constraintFFigureOrganizationFigureTypeLabel);

            // Shortcut icon to OrgModel
            // icon
            orgShortCutIcon = new WrappingLabel();
            gdIcon = new GridData();
            gdIcon.grabExcessHorizontalSpace = false;
            gdIcon.grabExcessVerticalSpace = false;
            gdIcon.horizontalAlignment = SWT.CENTER;
            gdIcon.verticalSpan = 1;

            Label iconToolTip = new Label();
            iconToolTip
                    .setText(Messages.OrganizationEditPart_showOrgModel_action_tooltip);

            orgShortCutIcon.setToolTip(iconToolTip);

            Image img =
                    ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_ORG_MODEL_SHORTCUT2));

            orgShortCutIcon.setIcon(img, 1);
            orgShortCutIcon.setCursor(getHandCursor());
            orgShortCutIcon.addMouseListener(new OpenOrgDiagramMouseListener());

            this.add(orgShortCutIcon);
            this.setConstraint(orgShortCutIcon, gdIcon);

            // Name Label
            fFigureOrganizationFigureNameLabel = new WrappingLabel();
            fFigureOrganizationFigureNameLabel
                    .setText(Messages.OrganizationEditPart_label);

            constraintFFigureOrganizationFigureNameLabel = new GridData();
            constraintFFigureOrganizationFigureNameLabel.verticalAlignment =
                    GridData.CENTER;
            constraintFFigureOrganizationFigureNameLabel.horizontalAlignment =
                    GridData.CENTER;
            constraintFFigureOrganizationFigureNameLabel.horizontalIndent = 0;
            constraintFFigureOrganizationFigureNameLabel.horizontalSpan = 1;
            constraintFFigureOrganizationFigureNameLabel.verticalSpan = 1;
            constraintFFigureOrganizationFigureNameLabel.grabExcessHorizontalSpace =
                    true;
            constraintFFigureOrganizationFigureNameLabel.grabExcessVerticalSpace =
                    false;
            this.add(fFigureOrganizationFigureNameLabel,
                    constraintFFigureOrganizationFigureNameLabel);

            this.add(fFigureOrganizationFigureNameLabel);

        }

        private class OpenOrgDiagramMouseListener implements MouseListener {

            public OpenOrgDiagramMouseListener() {
            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                // Open diagram the correct way by calling the OPEN_ROLE
                // editpolicy. This will make sure that the diagram is only
                // opened if initialized properly. This is especially important
                // if the diagram hasn't already been created i.e. an
                // Organization has just been created in the top level diagram
                // and the user clicks straight away to drill down.
                SelectionRequest req = new SelectionRequest();
                req.setType(RequestConstants.REQ_OPEN);
                Command cmd = getCommand(req);

                if (cmd != null && cmd.canExecute()) {

                    /*
                     * If a diagram has already been created for this
                     * Organization then just execute the command as it will
                     * only open the correct editor, otherwise execute the
                     * command on the command stack as a new diagram will be
                     * created.
                     */
                    if (hasDiagramAlready()) {
                        cmd.execute();
                    } else {
                        IDiagramEditDomain ed = getDiagramEditDomain();
                        if (ed != null) {
                            ed.getDiagramCommandStack().execute(cmd);
                        }
                    }
                }
            }
        }

        /**
         * Check if this Organization has a {@link Diagram} already created for
         * it.
         * 
         * @return <code>true</code> if already created.
         */
        private boolean hasDiagramAlready() {
            View view = (View) getModel();
            Style link =
                    view.getStyle(NotationPackage.eINSTANCE
                            .getHintedDiagramLinkStyle());
            if (link instanceof HintedDiagramLinkStyle) {
                return ((HintedDiagramLinkStyle) link).getDiagramLink() != null;
            }
            return false;
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

        /**
         * @generated
         */
        public WrappingLabel getFigureOrganizationFigureNameLabel() {
            return fFigureOrganizationFigureNameLabel;
        }

        /**
         * @generated
         */
        public WrappingLabel getFigureOrganizationFigureTypeLabel() {
            return fFigureOrganizationFigureTypeLabel;
        }

        public void rebuildFigureWithTypeLabel(boolean showFeatureLabel) {

            List childFigs = getChildren();

            if (childFigs.contains(fFigureOrganizationFigureTypeLabel)) {
                this.remove(fFigureOrganizationFigureTypeLabel);
            }

            if (showFeatureLabel) {
                this.add(fFigureOrganizationFigureTypeLabel,
                        constraintFFigureOrganizationFigureTypeLabel,
                        0);
            }
        }

    }

}
