package com.tibco.xpd.om.modeler.subdiagram.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.LabelOrgModelSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.LabelOrgTypeSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgModelNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgTypeSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationNameSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrganizationModelEditPartFactory implements EditPartFactory {

    @Override
    public EditPart createEditPart(EditPart context, Object model) {
        if (model instanceof View) {
            View view = (View) model;

            if (view.getType() != null
                    && view.getType()
                            .equals(OrganizationSubBadgeEditPart.VISUAL_ID)) {
                // return new Note2EditPart(view);
                return new OrganizationSubBadgeEditPart(view);
            }

            if (view.getType() != null
                    && view.getType()
                            .equals(OrganizationNameSubBadgeEditPart.VISUAL_ID)) {
                // return new Note2EditPart(view);
                return new OrganizationNameSubBadgeEditPart(view);
            }

            if (view.getType() != null
                    && view.getType()
                            .equals(OrgModelNameSubBadgeEditPart.VISUAL_ID)) {
                // return new Note2EditPart(view);
                return new OrgModelNameSubBadgeEditPart(view);
            }

            if (view.getType() != null
                    && view.getType()
                            .equals(LabelOrgModelSubBadgeEditPart.VISUAL_ID)) {
                // return new Note2EditPart(view);
                return new LabelOrgModelSubBadgeEditPart(view);
            }

            if (view.getType() != null
                    && view.getType().equals(OrgTypeSubBadgeEditPart.VISUAL_ID)) {
                // return new Note2EditPart(view);
                return new OrgTypeSubBadgeEditPart(view);
            }

            if (view.getType() != null
                    && view.getType()
                            .equals(LabelOrgTypeSubBadgeEditPart.VISUAL_ID)) {
                // return new Note2EditPart(view);
                return new LabelOrgTypeSubBadgeEditPart(view);
            }

        }

        return createEditPartGen(context, model);
    }

    /**
     * @generated
     */
    public EditPart createEditPartGen(EditPart context, Object model) {
        if (model instanceof View) {
            View view = (View) model;
            switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {

            case OrganizationSubEditPart.VISUAL_ID:
                return new OrganizationSubEditPart(view);

            case OrgUnitSubEditPart.VISUAL_ID:
                return new OrgUnitSubEditPart(view);

            case OrgUnitSubDisplayNameEditPart.VISUAL_ID:
                return new OrgUnitSubDisplayNameEditPart(view);

            case OrgUnitSubFeatureNameEditPart.VISUAL_ID:
                return new OrgUnitSubFeatureNameEditPart(view);

            case DynamicOrgUnitEditPart.VISUAL_ID:
                return new DynamicOrgUnitEditPart(view);

            case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
                return new DynamicOrgUnitDisplayNameEditPart(view);

            case DynamicOrgReferenceLabelEditPart.VISUAL_ID:
                return new DynamicOrgReferenceLabelEditPart(view);

            case PositionSubEditPart.VISUAL_ID:
                return new PositionSubEditPart(view);

            case OrgUnitPositionSubCompartmentEditPart.VISUAL_ID:
                return new OrgUnitPositionSubCompartmentEditPart(view);

            case OrgUnitSubRelationshipEditPart.VISUAL_ID:
                return new OrgUnitSubRelationshipEditPart(view);

            case OrgUnitSubRelationshipDisplayNameEditPart.VISUAL_ID:
                return new OrgUnitSubRelationshipDisplayNameEditPart(view);
            }
        }
        return createUnrecognizedEditPart(context, model);
    }

    /**
     * @generated
     */
    private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
        // Handle creation of unrecognized child node EditParts here
        return null;
    }

    /**
     * @generated
     */
    public static CellEditorLocator getTextCellEditorLocator(
            ITextAwareEditPart source) {
        if (source.getFigure() instanceof WrappingLabel)
            return new TextCellEditorLocator((WrappingLabel) source.getFigure());
        else {
            return new LabelCellEditorLocator((Label) source.getFigure());
        }
    }

    /**
     * @generated
     */
    static private class TextCellEditorLocator implements CellEditorLocator {

        /**
         * @generated
         */
        private WrappingLabel wrapLabel;

        /**
         * @generated
         */
        public TextCellEditorLocator(WrappingLabel wrapLabel) {
            this.wrapLabel = wrapLabel;
        }

        /**
         * @generated
         */
        public WrappingLabel getWrapLabel() {
            return wrapLabel;
        }

        /**
         * @generated
         */
        @Override
        public void relocate(CellEditor celleditor) {
            Text text = (Text) celleditor.getControl();
            Rectangle rect = getWrapLabel().getTextBounds().getCopy();
            getWrapLabel().translateToAbsolute(rect);
            if (getWrapLabel().isTextWrapOn()
                    && getWrapLabel().getText().length() > 0) {
                rect.setSize(new Dimension(text.computeSize(rect.width,
                        SWT.DEFAULT)));
            } else {
                int avr =
                        FigureUtilities.getFontMetrics(text.getFont())
                                .getAverageCharWidth();
                rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
                        SWT.DEFAULT)).expand(avr * 2, 0));
            }
            if (!rect.equals(new Rectangle(text.getBounds()))) {
                text.setBounds(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    /**
     * @generated
     */
    private static class LabelCellEditorLocator implements CellEditorLocator {

        /**
         * @generated
         */
        private Label label;

        /**
         * @generated
         */
        public LabelCellEditorLocator(Label label) {
            this.label = label;
        }

        /**
         * @generated
         */
        public Label getLabel() {
            return label;
        }

        /**
         * @generated
         */
        @Override
        public void relocate(CellEditor celleditor) {
            Text text = (Text) celleditor.getControl();
            Rectangle rect = getLabel().getTextBounds().getCopy();
            getLabel().translateToAbsolute(rect);
            int avr =
                    FigureUtilities.getFontMetrics(text.getFont())
                            .getAverageCharWidth();
            rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
                    SWT.DEFAULT)).expand(avr * 2, 0));
            if (!rect.equals(new Rectangle(text.getBounds()))) {
                text.setBounds(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }
}
