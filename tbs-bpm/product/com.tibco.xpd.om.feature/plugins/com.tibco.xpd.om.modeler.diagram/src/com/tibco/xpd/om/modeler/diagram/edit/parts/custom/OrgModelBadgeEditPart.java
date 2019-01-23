package com.tibco.xpd.om.modeler.diagram.edit.parts.custom;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.BadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.GradientFigureUtil;

public class OrgModelBadgeEditPart extends BadgeEditPart {

    protected IFigure primaryShape;

    protected IFigure contentPane;

    public static final String VISUAL_ID = "OrgModelBadge"; //$NON-NLS-1$

    /**
     * constructor
     * 
     * @param view
     *            the view controlled by this edit part
     */
    public OrgModelBadgeEditPart(View view) {
        super(view);
    }

    protected IFigure createNodeShape() {
        OrgModelBadgeFigure figure = new OrgModelBadgeFigure();
        return primaryShape = figure;
    }

    /**
     * Creates a node figure.
     */
    protected NodeFigure createNodeFigure() {
        NodeFigure figure = createNodePlate();
        figure.setLayoutManager(new StackLayout());
        IFigure shape = createNodeShape();
        figure.add(shape);
        contentPane = setupContentPane(shape);
        return figure;
    }

    public OrgModelBadgeFigure getPrimaryShape() {
        return (OrgModelBadgeFigure) primaryShape;
    }

    protected void handleNotificationEvent(Notification notification) {
        // handle gradient change
        if (GradientFigureUtil.isGradientChange(notification)) {
            refreshVisuals();
            return;
        } else {
            super.handleNotificationEvent(notification);
        }
    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();
    }

    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof OrgModelNameBadgeEditPart) {
            ((OrgModelNameBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureOrgModelNameLabel());
            return true;
        }
        if (childEditPart instanceof LabelAuthorBadgeEditPart) {
            ((LabelAuthorBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureLabelAuthor());
            return true;
        }
        if (childEditPart instanceof OrgModelAuthorBadgeEditPart) {
            ((OrgModelAuthorBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgModelAuthorLabelValue());
            return true;
        }
        if (childEditPart instanceof LabelVersionBadgeEditPart) {
            ((LabelVersionBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureLabelVersion());
            return true;
        }
        if (childEditPart instanceof OrgModelVersionBadgeEditPart) {
            ((OrgModelVersionBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgModelVersionLabelValue());
            return true;
        }
        if (childEditPart instanceof LabelDateCreatedBadgeEditPart) {
            ((LabelDateCreatedBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureLabelCreationDate());
            return true;
        }
        if (childEditPart instanceof OrgModelDateCreatedBadgeEditPart) {
            ((OrgModelDateCreatedBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgModelCreationDateLabelValue());
            return true;
        }
        if (childEditPart instanceof LabelDateModifiedBadgeEditPart) {
            ((LabelDateModifiedBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureLabelLastModified());
            return true;
        }
        if (childEditPart instanceof OrgModelDateModifiedBadgeEditPart) {
            ((OrgModelDateModifiedBadgeEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureOrgModelLastModifiedLabelValue());
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

    public class OrgModelBadgeFigure extends NodeFigure {

        private NodeFigure baseRectFigure;

        private WrappingLabel orgModelNameLabel;

        private WrappingLabel labelAuthor;

        private WrappingLabel labelCreationDate;

        private WrappingLabel labelLastModified;

        private WrappingLabel labelVersion;

        private WrappingLabel orgModelVersionLabelValue;

        private WrappingLabel orgModelAuthorLabelValue;

        private WrappingLabel orgModelCreationDateLabelValue;

        private WrappingLabel orgModelLastModifiedLabelValue;

        private WrappingLabel orgModelIcon;

        public OrgModelBadgeFigure() {
            // Setup a gridlayout
            GridLayout gl = new GridLayout();
            gl.numColumns = 1;
            gl.marginWidth = 0;
            gl.marginHeight = 0;
            this.setLayoutManager(gl);
            createContents();
            this.setOpaque(true);

        }

        /**
         * @generated
         */
        private void createContents() {

            baseRectFigure = new NodeFigure();
            this.add(baseRectFigure);

            // Set a 3 Column Grid
            GridLayout gradGridLayout = new GridLayout();
            gradGridLayout.numColumns = 3;
            gradGridLayout.marginWidth = 10;
            gradGridLayout.marginHeight = 10;
            baseRectFigure.setLayoutManager(gradGridLayout);

            /*
             * If in read-only then add a label indicating this
             */
            if (isReadOnly()) {
                WrappingLabel label = new WrappingLabel(Messages.OrgModelBadgeEditPart_readOnly_label);
                GridData data =
                        new GridData(SWT.CENTER, SWT.CENTER, true, false);
                data.horizontalSpan = 3;
                baseRectFigure.add(label);
                baseRectFigure.setConstraint(label, data);
            }

            // Model label
            orgModelNameLabel = new WrappingLabel();
            GridData gdOrgModelName = new GridData();
            gdOrgModelName.horizontalAlignment = SWT.CENTER;
            gdOrgModelName.grabExcessHorizontalSpace = true;
            gdOrgModelName.horizontalSpan = 3;
            baseRectFigure.add(orgModelNameLabel);
            baseRectFigure.setConstraint(orgModelNameLabel, gdOrgModelName);

            // Author label
            labelAuthor = new WrappingLabel();
            labelAuthor.setText(Messages.OrgModelBadgeEditPart_Author_label2);
            GridData gdLabelAuthor = new GridData();
            gdLabelAuthor.horizontalAlignment = SWT.LEFT;
            gdLabelAuthor.grabExcessHorizontalSpace = true;
            gdLabelAuthor.grabExcessVerticalSpace = true;
            gdLabelAuthor.verticalAlignment = SWT.BOTTOM;
            baseRectFigure.add(labelAuthor);
            baseRectFigure.setConstraint(labelAuthor, gdLabelAuthor);

            orgModelAuthorLabelValue = new WrappingLabel();
            GridData gdAuthorValue = new GridData();
            gdAuthorValue.horizontalAlignment = SWT.LEFT;
            gdAuthorValue.grabExcessHorizontalSpace = true;
            gdAuthorValue.grabExcessVerticalSpace = true;
            gdAuthorValue.verticalAlignment = SWT.BOTTOM;
            baseRectFigure.add(orgModelAuthorLabelValue);
            baseRectFigure.setConstraint(orgModelAuthorLabelValue,
                    gdAuthorValue);

            // icon
            orgModelIcon = new WrappingLabel();
            GridData gdIcon = new GridData();
            gdIcon.grabExcessHorizontalSpace = true;
            gdIcon.grabExcessVerticalSpace = true;
            gdIcon.horizontalIndent = 10;
            gdIcon.horizontalAlignment = SWT.RIGHT;
            gdIcon.verticalSpan = 3;

            Image img =
                    ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                            .getImageObject(OMModelImages.IMAGE_ORG_MODEL64));

            orgModelIcon.setIcon(img, 1);
            baseRectFigure.add(orgModelIcon);
            baseRectFigure.setConstraint(orgModelIcon, gdIcon);

            // Version label
            labelVersion = new WrappingLabel();
            labelVersion.setText(Messages.OrgModelBadgeEditPart_version_label);
            GridData gdLabelVersion = new GridData();
            gdLabelVersion.horizontalAlignment = SWT.LEFT;
            gdLabelVersion.grabExcessHorizontalSpace = true;
            gdLabelVersion.grabExcessVerticalSpace = true;
            baseRectFigure.add(labelVersion);
            baseRectFigure.setConstraint(labelVersion, gdLabelVersion);

            orgModelVersionLabelValue = new WrappingLabel();
            GridData gdVersionValue = new GridData();
            gdVersionValue.horizontalAlignment = SWT.LEFT;
            gdVersionValue.grabExcessHorizontalSpace = true;
            gdVersionValue.grabExcessVerticalSpace = true;
            baseRectFigure.add(orgModelVersionLabelValue);
            baseRectFigure.setConstraint(orgModelVersionLabelValue,
                    gdVersionValue);

            // Create date label
            labelCreationDate = new WrappingLabel();
            labelCreationDate
                    .setText(Messages.OrgModelBadgeEditPart_Author_labelDateCreated_label);
            GridData gdLabelCreateDate = new GridData();
            gdLabelCreateDate.horizontalAlignment = SWT.LEFT;
            gdLabelCreateDate.grabExcessHorizontalSpace = true;
            gdLabelCreateDate.grabExcessVerticalSpace = true;
            baseRectFigure.add(labelCreationDate);
            baseRectFigure.setConstraint(labelCreationDate, gdLabelCreateDate);

            orgModelCreationDateLabelValue = new WrappingLabel();
            GridData gdCreateDateValue = new GridData();
            gdCreateDateValue.horizontalAlignment = SWT.LEFT;
            gdCreateDateValue.grabExcessHorizontalSpace = true;
            gdCreateDateValue.grabExcessVerticalSpace = true;
            baseRectFigure.add(orgModelCreationDateLabelValue);
            baseRectFigure.setConstraint(orgModelCreationDateLabelValue,
                    gdCreateDateValue);

            // Modified date label
            labelLastModified = new WrappingLabel();
            labelLastModified
                    .setText(Messages.OrgModelBadgeEditPart_Author_labelDateModified_label);
            GridData gdLabelModifiedDate = new GridData();
            gdLabelModifiedDate.horizontalAlignment = SWT.LEFT | SWT.BOTTOM;
            baseRectFigure.add(labelLastModified);
            baseRectFigure
                    .setConstraint(labelLastModified, gdLabelModifiedDate);

            orgModelLastModifiedLabelValue = new WrappingLabel();
            GridData gdModifiedDateValue = new GridData();
            gdModifiedDateValue.horizontalAlignment = SWT.LEFT;

            baseRectFigure.add(orgModelLastModifiedLabelValue);
            baseRectFigure.setConstraint(orgModelLastModifiedLabelValue,
                    gdModifiedDateValue);

        }

        public WrappingLabel getFigureOrgModelNameLabel() {
            return orgModelNameLabel;
        }

        public void setOrgModelNameLabel(WrappingLabel orgModelNameLabel) {
            this.orgModelNameLabel = orgModelNameLabel;
        }

        public WrappingLabel getOrgModelVersionLabelValue() {
            return orgModelVersionLabelValue;
        }

        public WrappingLabel getFigureLabelAuthor() {
            return labelAuthor;
        }

        public void setLabelAuthor(WrappingLabel lblAuthor) {
            this.labelAuthor = lblAuthor;
        }

        public WrappingLabel getFigureOrgModelAuthorLabelValue() {
            return orgModelAuthorLabelValue;
        }

        public void setOrgModelAuthorLabelValue(
                WrappingLabel orgModelAuthorLabelValue) {
            this.orgModelAuthorLabelValue = orgModelAuthorLabelValue;
        }

        public WrappingLabel getFigureLabelCreationDate() {
            return labelCreationDate;
        }

        public void setLabelCreationDate(WrappingLabel lblCreationDate) {
            this.labelCreationDate = lblCreationDate;
        }

        public WrappingLabel getFigureOrgModelCreationDateLabelValue() {
            return orgModelCreationDateLabelValue;
        }

        public void setOrgModelCreationDateLabelValue(
                WrappingLabel orgModelCreationDateLabelValue) {
            this.orgModelCreationDateLabelValue =
                    orgModelCreationDateLabelValue;
        }

        public WrappingLabel getFigureLabelLastModified() {
            return labelLastModified;
        }

        public void setLabelLastModified(WrappingLabel lblLastModified) {
            this.labelLastModified = lblLastModified;
        }

        public WrappingLabel getFigureOrgModelLastModifiedLabelValue() {
            return orgModelLastModifiedLabelValue;
        }

        public void setOrgModelLastModifiedLabelValue(
                WrappingLabel orgModelLastModifiedLabelValue) {
            this.orgModelLastModifiedLabelValue =
                    orgModelLastModifiedLabelValue;
        }

        public WrappingLabel getFigureOrgModelVersionLabelValue() {
            return orgModelVersionLabelValue;
        }

        public void setOrgModelVersionLabelValue(
                WrappingLabel orgModelVersionLabelValue) {
            this.orgModelVersionLabelValue = orgModelVersionLabelValue;
        }

        public WrappingLabel getFigureLabelVersion() {
            return labelVersion;
        }

        public void setLabelVersion(WrappingLabel lblVersionLabel) {
            this.labelVersion = lblVersionLabel;
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

        private GridLayout createBadgeWrapLabelLayout() {
            GridLayout gl = new GridLayout();
            gl.numColumns = 1;
            gl.marginHeight = 12;
            gl.marginWidth = 20;
            return gl;
        }
    }
}
