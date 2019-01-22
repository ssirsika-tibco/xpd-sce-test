package com.tibco.xpd.om.modeler.subdiagram.edit.parts;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitCustomEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.HyperLinkWrappingLabel;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.HyperLinkWrappingLabel.IHyperLinkListener;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.OrgUnitCustomFigure;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.DynamicOrgUnitItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrganizationModelTextSelectionEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;

/**
 * @generated
 */
public class DynamicOrgUnitEditPart extends OrgUnitCustomEditPart {

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
     * @generated
     */
    public DynamicOrgUnitEditPart(View view) {
        super(view);
    }

    /**
     * @generated
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
        if (childEditPart instanceof DynamicOrgReferenceLabelEditPart) {
            ((DynamicOrgReferenceLabelEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getDynamicOrgReferenceLinkLabel());
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
     * @generated
     */
    public class DynamicOrgUnitFigure extends OrgUnitCustomFigure {

        /**
         * @generated
         */
        // private WrappingLabel fFigureDynamicOrgUnitFigureNameLabel;

        /**
         * @generated NOT
         */
        private HyperLinkWrappingLabel fDynamicOrgReferenceLinkLabel;

        /**
         * @param ep
         * @generated
         */
        public DynamicOrgUnitFigure(OrgUnitCustomEditPart ep) {
            super(ep);

            this.setMinimumSize(new Dimension(getMapMode().DPtoLP(120),
                    getMapMode().DPtoLP(50)));
        }

        /**
         * @generated
         */
        @Override
        protected void createContents() {

            super.createContents();

            fDynamicOrgReferenceLinkLabel = new HyperLinkWrappingLabel();
            fDynamicOrgReferenceLinkLabel
                    .setHyperLinkListener(new IHyperLinkListener() {

                        @Override
                        public void hyperLinkActivated() {
                            openDynamicOrgEditor();
                        }
                    });

            GridData data = new GridData(SWT.LEFT, SWT.CENTER, true, true);
            data.horizontalIndent = 5;

            this.add(fDynamicOrgReferenceLinkLabel, data);

        }

        /**
         * Open the referenced dynamic organization.
         */
        private void openDynamicOrgEditor() {
            EObject element = resolveSemanticElement();
            if (element instanceof DynamicOrgUnit) {
                DynamicOrgReference reference =
                        ((DynamicOrgUnit) element).getDynamicOrganization();
                if (reference != null && reference.getTo() != null) {
                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();
                    try {
                        OMResourcesUIActivator.openEditor(page,
                                reference.getTo());
                    } catch (CoreException e) {
                        ErrorDialog
                                .openError(page.getActivePart().getSite()
                                        .getShell(),
                                        Messages.DynamicOrgUnitEditPart_errorOpeningDynOrg_dlg_title,
                                        e.getLocalizedMessage(),
                                        e.getStatus());
                    }
                }
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
        @Override
        protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
            myUseLocalCoordinates = useLocalCoordinates;
        }

        /**
         * @generated
         */
        public WrappingLabel getFigureDynamicOrgUnitFigureNameLabel() {
            // return fFigureDynamicOrgUnitFigureNameLabel;
            return getNameLabel();
        }

        /**
         * @generated NOT
         */
        public HyperLinkWrappingLabel getDynamicOrgReferenceLinkLabel() {
            return fDynamicOrgReferenceLinkLabel;
        }

    }

}
