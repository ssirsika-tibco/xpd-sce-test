package com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class OrganizationSubBadgeEditPart extends BadgeEditPart {
    // resource listener

    public static final String VISUAL_ID = "OrganizationSubBadge"; //$NON-NLS-1$

    private Cursor handCursor;

    /**
     * constructor
     * 
     * @param view
     *            the view controlled by this edit part
     */
    public OrganizationSubBadgeEditPart(View view) {
        super(view);
    }

    protected IFigure createNodeShape() {
        OrganizationBadgeFigure figure = new OrganizationBadgeFigure();
        return primaryShape = figure;
    }

    public OrganizationBadgeFigure getPrimaryShape() {
        return (OrganizationBadgeFigure) primaryShape;
    }

    /**
     * Creates a note figure.
     */
    protected NodeFigure createNodeFigure() {
        IMapMode mm = getMapMode();
        // Insets insets = new Insets(mm.DPtoLP(5), mm.DPtoLP(5), mm.DPtoLP(5),
        // mm.DPtoLP(5));
        // NoteFigure noteFigure = new NoteFigure(mm.DPtoLP(100), mm.DPtoLP(56),
        // insets);

        NodeFigure figure = createNodePlate();
        figure.setLayoutManager(new StackLayout());
        IFigure shape = createNodeShape();
        figure.add(shape);
        contentPane = setupContentPane(shape);
        return figure;
    }

    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof OrganizationNameSubBadgeEditPart) {
            ((OrganizationNameSubBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrganizationNameLabel());
            return true;
        }
        if (childEditPart instanceof LabelOrgModelSubBadgeEditPart) {
            ((LabelOrgModelSubBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureLabelOrgModel());
            return true;
        }
        if (childEditPart instanceof OrgModelNameSubBadgeEditPart) {
            ((OrgModelNameSubBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgModelNameValueLabel());
            return true;
        }
        if (childEditPart instanceof LabelOrgTypeSubBadgeEditPart) {
            ((LabelOrgTypeSubBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureLabelOrgType());
            return true;
        }
        if (childEditPart instanceof OrgTypeSubBadgeEditPart) {
            ((OrgTypeSubBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureOrgTypeLabel());
            return true;
        }

        return false;
    }

    protected void addChildVisual(EditPart childEditPart, int index) {
        if (addFixedChild(childEditPart)) {
            return;
        }
        super.addChildVisual(childEditPart, -1);
    }

    @Override
    protected void handleNotificationEvent(Notification notification) {
        // handle gradient change
        if (GradientFigureUtil.isGradientChange(notification)) {
            refreshVisuals();
        } else {
            super.handleNotificationEvent(notification);
        }
    }

    /**
     * Get a hand cursor. This will lazy load an instance of the cursor.
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

    public class OrganizationBadgeFigure extends NodeFigure {

        private NodeFigure baseRectFig;

        private WrappingLabel organizationNameLabel;

        private WrappingLabel labelOrgModel;

        private WrappingLabel orgModelNameLabel;

        private WrappingLabel labelOrgType;

        private WrappingLabel orgModelTypeLabel;

        private WrappingLabel orgModelShortCutIcon;

        public OrganizationBadgeFigure() {
            GridLayout gl = new GridLayout();
            gl.numColumns = 1;
            gl.marginWidth = 0;
            gl.marginHeight = 0;
            this.setLayoutManager(gl);
            createContents();
            this.setOpaque(true);

        }

        private void createContents() {

            baseRectFig = new NodeFigure();
            this.add(baseRectFig);

            // Set a 3 Column Grid
            GridLayout gradGridLayout = new GridLayout();
            gradGridLayout.numColumns = 3;
            gradGridLayout.marginWidth = 10;
            gradGridLayout.marginHeight = 10;
            baseRectFig.setLayoutManager(gradGridLayout);

            /*
             * If in read-only then add a label indicating this
             */
            if (isReadOnly()) {
                WrappingLabel label =
                        new WrappingLabel(
                                Messages.OrganizationSubBadgeEditPart_readOnly_label);
                GridData data =
                        new GridData(SWT.CENTER, SWT.CENTER, true, false);
                data.horizontalSpan = 3;
                baseRectFig.add(label);
                baseRectFig.setConstraint(label, data);
            }

            // Organization value label
            organizationNameLabel = new WrappingLabel();
            GridData gd1 = new GridData();
            gd1.horizontalAlignment = SWT.CENTER;
            gd1.grabExcessHorizontalSpace = true;
            gd1.grabExcessVerticalSpace = true;
            gd1.horizontalSpan = 3;
            baseRectFig.add(organizationNameLabel);
            baseRectFig.setConstraint(organizationNameLabel, gd1);
            organizationNameLabel.setEnabled(true);

            // Model tag label
            labelOrgModel = new WrappingLabel();
            labelOrgModel.setText("");
            baseRectFig.add(labelOrgModel);
            GridData gdOrgModelLabel = new GridData();
            gdOrgModelLabel.horizontalAlignment = SWT.LEFT;
            baseRectFig.setConstraint(labelOrgModel, gdOrgModelLabel);

            // Model value label
            orgModelNameLabel = new WrappingLabel();
            baseRectFig.add(orgModelNameLabel);
            GridData gdOrgModelNameValueLabel = new GridData();
            gdOrgModelNameValueLabel.horizontalAlignment = SWT.LEFT;
            baseRectFig.setConstraint(orgModelNameLabel,
                    gdOrgModelNameValueLabel);

            // Shortcut icon to OrgModel icon
            orgModelShortCutIcon = new WrappingLabel();
            GridData gdIcon = new GridData();
            gdIcon.grabExcessHorizontalSpace = true;
            gdIcon.grabExcessVerticalSpace = true;
            gdIcon.horizontalAlignment = SWT.CENTER;

            Label iconToolTip = new Label();
            iconToolTip.setText("");

            orgModelShortCutIcon.setCursor(getHandCursor());
            orgModelShortCutIcon.setToolTip(iconToolTip);
            /*
             * If editing a read-only resource (resource history) then don't
             * show the shortcut icon as it will not work. This is because the
             * Organization Model is not going to be a file but an input stream.
             */
            orgModelShortCutIcon.setVisible(!isReadOnly());

            Image img =
                    ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_ORG_MODEL_SHORTCUT));

            orgModelShortCutIcon.setIcon(img, 1);
            orgModelShortCutIcon
                    .addMouseListener(new SwitchDiagramMouseListener());

            baseRectFig.add(orgModelShortCutIcon);
            baseRectFig.setConstraint(orgModelShortCutIcon, gdIcon);

            // Type tag label
            labelOrgType = new WrappingLabel();
            labelOrgType.setText("");
            baseRectFig.add(labelOrgType);
            GridData gdOrgTypeLabel = new GridData();
            gdOrgTypeLabel.horizontalAlignment = SWT.LEFT;
            baseRectFig.setConstraint(labelOrgType, gdOrgTypeLabel);

            // Type value label
            orgModelTypeLabel = new WrappingLabel();
            orgModelTypeLabel.setText("");
            baseRectFig.add(orgModelTypeLabel);
            GridData gdOrgTypeValueLabel = new GridData();
            gdOrgTypeValueLabel.horizontalSpan = 2;
            gdOrgTypeValueLabel.horizontalAlignment = SWT.LEFT;
            baseRectFig.setConstraint(orgModelTypeLabel, gdOrgTypeValueLabel);

        }

        private class SwitchDiagramMouseListener implements MouseListener {

            public SwitchDiagramMouseListener() {

            }

            public void mouseDoubleClicked(MouseEvent me) {
            }

            public void mousePressed(MouseEvent me) {
                // Open the main (parent) orgmodel diagram
                EObject element = resolveSemanticElement();
                EObject objToOpen = element;
                IWorkbenchPage page =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();

                if (page != null) {
                    while (objToOpen instanceof Organization) {
                        objToOpen = element.eContainer();
                        if (objToOpen == null) {
                            return;
                        }
                    }

                    if (objToOpen instanceof OrgModel) {
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

            public void mouseReleased(MouseEvent me) {
            }

        }

        public WrappingLabel getFigureOrgModelNameValueLabel() {
            return orgModelNameLabel;
        }

        public void setOrgModelNameValueLabel(
                WrappingLabel orgModelNameValueLabel) {
            this.orgModelNameLabel = orgModelNameValueLabel;
        }

        public WrappingLabel getFigureOrganizationNameLabel() {
            return organizationNameLabel;
        }

        public void setOrganizationNameLabel(WrappingLabel organizationNameLabel) {
            this.organizationNameLabel = organizationNameLabel;
        }

        public WrappingLabel getFigureLabelOrgModel() {
            return labelOrgModel;
        }

        public void setLabelOrgModel(WrappingLabel lblOrg) {
            this.labelOrgModel = lblOrg;
        }

        public WrappingLabel getFigureLabelOrgType() {
            return labelOrgType;
        }

        public void setLabelOrgType(WrappingLabel lblOrgType) {
            this.labelOrgType = lblOrgType;
        }

        public WrappingLabel getFigureOrgTypeLabel() {
            return orgModelTypeLabel;
        }

        public void setOrgTypeLabel(WrappingLabel OrgTypeLabel) {
            this.orgModelTypeLabel = OrgTypeLabel;
        }

        /**
         * @generated
         */
        private boolean myUseLocalCoordinates = false;

        /**
         * @generated
         */
        protected boolean useLocalCoordinates() {
            return myUseLocalCoordinates;
        }

        /**
         * @generated
         */
        protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
            myUseLocalCoordinates = useLocalCoordinates;
        }

    }
}
