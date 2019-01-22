package com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.ui.services.parser.CommonParserHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableEditPolicyEx;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenDiagramEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ViewComponentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.editpolicies.DiagramLinkDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.resources.wc.InputStreamOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * 
 * 
 * Base editpart class for OM badges. Badges are displayed on two diagrams:
 * 
 * <ul>
 * <li>1. Organization Model diagram</li>
 * <li>2. Organization diagram</li>
 * </ul>
 * 
 * 
 * @author rgreen
 * 
 */
public abstract class BadgeEditPart extends ShapeNodeEditPart {

    protected IFigure primaryShape;

    protected IFigure contentPane;

    public BadgeEditPart(View view) {
        super(view);
    }

    protected abstract IFigure createNodeShape();

    protected abstract NodeFigure createNodeFigure();

    public abstract IFigure getPrimaryShape();

    /**
     * @generated NOT
     */
    protected NodeFigure createNodePlate() {
        DefaultSizeNodeFigure result =
                new DefaultSizeNodeFigure(getMapMode().DPtoLP(40), getMapMode()
                        .DPtoLP(40));

        // Set the drop shadow here
        // result.setBorder(new RectangularDropShadowLineBorder());
        result.setBorder(new LineBorder());

        return result;
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

    @Override
    public void installEditPolicy(Object key, EditPolicy editPolicy) {

        // Need to overide this method so that we can install a
        // NonResizableShapeEditPolicy.
        // We can't do it in the createDefaultEditPolicies method because the
        // ResizableShapeEditPolicy is applied to this editpart by the parent
        // editpart (Organization diagram)when it installs policies on its
        // children (i.e. after createDefaulteditPolicies() has been called.

        if (editPolicy instanceof ResizableShapeEditPolicy) {
            super.installEditPolicy(key, new NonResizableEditPolicyEx());

        } else {
            super.installEditPolicy(key, editPolicy);
        }

    }

    /** Adds support for diagram links. */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();

        // Remove semantic edit policy and install a non-semantic edit policy
        removeEditPolicy(EditPolicyRoles.SEMANTIC_ROLE);
        // installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
        // new NonSemanticEditPolicy());

        // Add Note support for diagram links
        // The following two edit policies support the links.
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DiagramLinkDragDropEditPolicy());

        installEditPolicy(EditPolicyRoles.OPEN_ROLE,
                new OpenDiagramEditPolicy());

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
     * this method will return the primary child EditPart inside this edit part
     * 
     * @return the primary child view inside this edit part
     */
    public EditPart getPrimaryChildEditPart() {
        return getChildBySemanticHint(CommonParserHint.DESCRIPTION);
    }

    public Object getPreferredValue(EStructuralFeature feature) {
        Object preferenceStore =
                getDiagramPreferencesHint().getPreferenceStore();
        if (preferenceStore instanceof IPreferenceStore) {
            if (feature == NotationPackage.eINSTANCE.getLineStyle_LineColor()) {

                return FigureUtilities.RGBToInteger(PreferenceConverter
                        .getColor((IPreferenceStore) preferenceStore,
                                IPreferenceConstants.PREF_NOTE_LINE_COLOR));

            } else if (feature == NotationPackage.eINSTANCE
                    .getFillStyle_FillColor()) {

                return FigureUtilities.RGBToInteger(PreferenceConverter
                        .getColor((IPreferenceStore) preferenceStore,
                                IPreferenceConstants.PREF_NOTE_FILL_COLOR));

            }
        }

        return super.getPreferredValue(feature);
    }

    protected void handleNotificationEvent(Notification notification) {

        Object feature = notification.getFeature();
        if (feature == NotationPackage.eINSTANCE.getView_Element()
                && notification.getEventType() == Notification.RESOLVE
                && ((EObject) notification.getNotifier()) == getNotationView()) {
            // skipping the resolve event whenever the editpart is already
            // resolved.
            return;
        }
        if (NotationPackage.eINSTANCE.getLineStyle_LineWidth().equals(feature)) {
            refreshLineWidth();
        } else if (NotationPackage.eINSTANCE.getLineTypeStyle_LineType()
                .equals(feature)) {
            refreshLineType();
        } else {
            super.handleNotificationEvent(notification);
        }

    }

    /**
     * Check if the resource being edited is read-only (resource history).
     * 
     * @return
     * @since 3.5
     */
    protected boolean isReadOnly() {
        Diagram view = getDiagramView();
        if (view != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(view);
            if (wc == null || wc instanceof InputStreamOMWorkingCopy) {
                return true;
            }
        }
        return false;
    }

}
