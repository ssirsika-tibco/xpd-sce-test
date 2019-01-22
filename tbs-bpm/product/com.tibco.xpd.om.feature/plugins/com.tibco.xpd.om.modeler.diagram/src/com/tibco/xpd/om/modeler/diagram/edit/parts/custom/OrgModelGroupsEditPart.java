package com.tibco.xpd.om.modeler.diagram.edit.parts.custom;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.BadgeEditPart;

public class OrgModelGroupsEditPart extends BadgeEditPart {

    public static final int VISUAL_ID = 1050; //$NON-NLS-1$

    /**
     * constructor
     * 
     * @param view
     *            the view controlled by this edit part
     */
    public OrgModelGroupsEditPart(View view) {
        super(view);
    }

    protected IFigure createNodeShape() {
        OrgModelGroupsFigure figure = new OrgModelGroupsFigure();
        return primaryShape = figure;
    }

    public OrgModelGroupsFigure getPrimaryShape() {
        return (OrgModelGroupsFigure) primaryShape;
    }

    protected NodeFigure createNodePlate() {
        DefaultSizeNodeFigure result =
                new DefaultSizeNodeFigure(getMapMode().DPtoLP(100),
                        getMapMode().DPtoLP(100));
        return result;
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
        if (childEditPart instanceof LabelOrgModelGroupsEditPart) {
            ((LabelOrgModelGroupsEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getGroupsLabel());
            return true;
        }
        return false;
    }

    protected void addChildVisual(EditPart childEditPart, int index) {
        if (childEditPart instanceof OrgModelGroupsCompartmentEditPart) {
            GridData constGrid = new GridData(GridData.FILL_BOTH);
            IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
            constGrid.horizontalAlignment = GridData.FILL;
            this.getContentPane().add(child, constGrid, -1);
        } else if (addFixedChild(childEditPart)) {
            return;
        } else {
            super.addChildVisual(childEditPart, -1);
        }
    }

    public IFigure getContentPane() {
        if (contentPane != null) {
            return contentPane;
        }
        return super.getContentPane();
    }

    @Override
    protected void handleNotificationEvent(Notification event) {
        super.handleNotificationEvent(event);

        Object notifier = event.getNotifier();

        if ((event.getEventType() == Notification.ADD)
                && (notifier instanceof OrgModel)) {

            if (event.getNewValue() instanceof Group) {
                addListenerFilter("semantic", this, (Group) event.getNewValue());
            }

        }

    }

    public class OrgModelGroupsFigure extends RoundedRectangle {

        private WrappingLabel groupsLabel;

        public OrgModelGroupsFigure() {
            GridLayout gl = new GridLayout();
            gl.numColumns = 1;
            gl.marginWidth = 0;
            gl.marginHeight = 0;
            this.setLayoutManager(gl);
            createContents();
            // this.setOpaque(true);
        }

        private void createContents() {

            groupsLabel = new WrappingLabel();
            groupsLabel
                    .setText(Messages.OrgModelGroupsEditPart_FigureTitle_label);

            GridData constraintGroupsLabel = new GridData();
            constraintGroupsLabel.verticalAlignment = GridData.CENTER;
            constraintGroupsLabel.horizontalAlignment = GridData.CENTER;
            constraintGroupsLabel.horizontalIndent = 0;
            constraintGroupsLabel.grabExcessHorizontalSpace = true;
            constraintGroupsLabel.grabExcessVerticalSpace = false;
            this.add(groupsLabel, constraintGroupsLabel);

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

        public WrappingLabel getGroupsLabel() {
            return groupsLabel;
        }

        public void setGroupsLabel(WrappingLabel groupsLabel) {
            this.groupsLabel = groupsLabel;
        }

    }

}
