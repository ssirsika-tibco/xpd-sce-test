package com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.subdiagram.edit.commands.custom.ExpandCollapseOrgUnitNodeHierarchyCommand;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.CustomLineBorder;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.GradientFigureUtil;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.OrgUnitCustomFigure;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;

public abstract class OrgUnitCustomEditPart extends ShapeNodeEditPart {

    public OrgUnitCustomEditPart(View view) {
        super(view);
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(this.getFigure());

    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(this.getFigure());

    }

    /*
     * 
     * Creates and executes a command to expand the OrgUnit view hierarchy below
     * this EditPart.
     */
    public void expandHierarchyViews() {
        TransactionalEditingDomain ed = getEditingDomain();
        Command cmd =
                new ExpandCollapseOrgUnitNodeHierarchyCommand(ed, this,
                        ExpandCollapseOrgUnitNodeHierarchyCommand.HIER_EXPAND);
        ed.getCommandStack().execute(cmd);
    }

    /**
     * Creates and executes a command to collapse the OrgUnit view hierarchy
     * below this EditPart.
     */
    public void collapseHierarchyViews() {
        TransactionalEditingDomain ed = getEditingDomain();
        Command cmd =
                new ExpandCollapseOrgUnitNodeHierarchyCommand(ed, this,
                        ExpandCollapseOrgUnitNodeHierarchyCommand.HIER_COLLAPSE);
        ed.getCommandStack().execute(cmd);
    }

    /**
     * Creates and executes a command to expand or collapse the OrgUnit view
     * hierarchy below this EditPart. The command will determine the current
     * state of the hierarchy i.e. expanded or collapsed, and invert it.
     */
    public void changeOrgUnitNodeHierarchy() {
        TransactionalEditingDomain ed = getEditingDomain();
        Command cmd = new ExpandCollapseOrgUnitNodeHierarchyCommand(ed, this);
        ed.getCommandStack().execute(cmd);
    }

    /**
     * Sets the "Expand" icon on this EditPart's header figure.
     */
    public void setFigureExpandIcon() {
        OrgUnitCustomFigure fig = getPrimaryShape();
        fig.setExpandIcon();
        refreshVisuals();
    }

    /**
     * Sets the "Expand" icon on this EditPart's header figure.
     */
    public void setFigureCollapseIcon() {
        OrgUnitCustomFigure fig = getPrimaryShape();
        fig.setCollapseIcon();
        refreshVisuals();
    }

    public abstract OrgUnitCustomFigure getPrimaryShape();

    @Override
    protected void handleNotificationEvent(Notification event) {

        // If an outgoing OrgUnitRelationship has been added to this OrgUnit the
        // we need to determine if we should rebuild the gradient header withe
        // expand/collapse icon.
        Object feature = event.getFeature();
        if ((feature == OMPackage.Literals.ORG_UNIT__OUTGOING_RELATIONSHIPS)
                && (event.getNotifier() == resolveSemanticElement())) {
            if (event.getEventType() == Notification.ADD) {
                Object newValue = event.getNewValue();

                if (newValue instanceof OrgUnitRelationship) {
                    OrgUnitRelationship rel = (OrgUnitRelationship) newValue;
                    if (rel.isIsHierarchical()) {
                        getPrimaryShape()
                                .rebuildFigureWithExpandCollapseIcon(true, 0);
                    }
                }
            } else if (event.getEventType() == Notification.REMOVE) {
                Object oldValue = event.getOldValue();

                if (oldValue instanceof OrgUnitRelationship) {
                    OrgUnitRelationship rel = (OrgUnitRelationship) oldValue;
                    if (rel.isIsHierarchical()) {

                        // Check that there aren't any other hierarchical
                        // relationships which will require the icon to be
                        // present.
                        OrgUnit element = (OrgUnit) resolveSemanticElement();

                        EList<OrgUnitRelationship> outgoingRelationships =
                                element.getOutgoingRelationships();

                        int numHierarchicalRels = 0;

                        for (OrgUnitRelationship oGR : outgoingRelationships) {
                            if (oGR.isIsHierarchical()) {
                                numHierarchicalRels++;
                                break;
                            }
                        }

                        if (numHierarchicalRels == 0) {
                            getPrimaryShape()
                                    .rebuildFigureWithExpandCollapseIcon(false,
                                            0);
                        }
                    }
                }
            }
            refreshVisuals();
        }

        // If the parent Organization's Type has been removed we need to
        // make sure that this editparts figure does not show a Feature label.
        if ((feature == OMPackage.Literals.ORGANIZATION__ORGANIZATION_TYPE)
                && (event.getNotifier() == resolveSemanticElement()
                        .eContainer())) {
            if ((event.getEventType() == Notification.SET)
                    && (event.getNewValue() == null)) {
                getPrimaryShape().rebuildFigureWithFeatureLabel(false);
            }

        }

        // handle gradient change
        if (GradientFigureUtil.isGradientChange(event)) {
            refreshVisuals();
            return;
        }

        super.handleNotificationEvent(event);
    }

    /**
     * Set the border of this org unit.
     * 
     * @param figure
     *            top-level figure of this orgunit
     * @param isDouble
     *            <code>true</code> will create a double border (also changes
     *            the layout manager of this figure, to
     *            {@link StackLayoutWithMargin}, to allow the drawing of a
     *            double border). The outer border is dashed and the inner
     *            border is solid. <code>false</code> will remove the outer
     *            border and reset the layout manager back to
     *            {@link StackLayout}.
     */
    protected void setBorder(IFigure figure, boolean isDouble) {
        if (figure != null) {
            if (isDouble) {
                if (figure.getBorder() == null) {
                    // Create outer dashed border
                    CustomLineBorder border = new CustomLineBorder();
                    border.makeDashed();
                    /*
                     * Also set the border line color to default, otherwise it
                     * comes up as black.
                     */
                    if (border.getColor() == null) {
                        IPreferenceStore store =
                                (IPreferenceStore) OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                                        .getPreferenceStore();
                        Color lineColor =
                                DiagramColorRegistry
                                        .getInstance()
                                        .getColor(PreferenceConverter.getColor(store,
                                                IOMSubPreferenceConstants.PREF_ORGUNIT_LINE_COLOR));
                        border.setColor(lineColor);
                    }

                    figure.setBorder(border);
                }

                if (!(figure.getLayoutManager() instanceof StackLayoutWithMargin)) {
                    figure.setLayoutManager(new StackLayoutWithMargin(2, 2));
                }
            } else {
                if (figure.getBorder() != null) {
                    figure.setBorder(null);
                }

                if (figure.getLayoutManager() instanceof StackLayoutWithMargin) {
                    figure.setLayoutManager(new StackLayout());
                }
            }
        }
    }

    @Override
    protected void refreshVisuals() {
        // TODO Auto-generated method stub
        super.refreshVisuals();

        // update gradient colors
        IPreferenceStore store =
                (IPreferenceStore) OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT
                        .getPreferenceStore();
        GradientFigureUtil
                .updateFigureGradient(getPrimaryView(),
                        getPrimaryShape(),
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR)),
                        FigureUtilities.RGBToInteger(PreferenceConverter
                                .getColor(store,
                                        IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR)));
    }

    /**
     * Check if this org unit is in a Dynamic Organization.
     * 
     * @return
     */
    protected final boolean isDynamicOrganization() {
        EObject elem = resolveSemanticElement();
        if (elem instanceof OrgUnit) {
            Organization organization = ((OrgUnit) elem).getOrganization();
            return organization != null && organization.isDynamic();
        }
        return false;
    }

    /**
     * Check if this is root org unit (one without any incoming connections).
     * 
     * @return
     */
    protected final boolean isRootOrgUnit() {
        EObject elem = resolveSemanticElement();
        if (elem instanceof OrgUnit) {
            OrgUnitRelationship relationship =
                    ((OrgUnit) elem).getIncomingHierachicalRelationship();
            return relationship == null;
        }
        return false;
    }

}
