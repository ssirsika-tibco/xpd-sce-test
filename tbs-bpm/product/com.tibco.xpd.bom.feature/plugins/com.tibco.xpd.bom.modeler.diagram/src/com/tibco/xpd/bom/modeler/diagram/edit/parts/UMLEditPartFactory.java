/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

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

import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class UMLEditPartFactory implements EditPartFactory {

    public EditPart createEditPart(EditPart context, Object model) {

        if (model instanceof View) {
            View view = (View) model;
            switch (UMLVisualIDRegistry.getVisualID(view)) {

            case BadgeDiagramNameEditPart.VISUAL_ID:
                return new BadgeDiagramNameEditPart(view);
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
            switch (UMLVisualIDRegistry.getVisualID(view)) {

            case CanvasPackageEditPart.VISUAL_ID:
                return new CanvasPackageEditPart(view);

            case PackageEditPart.VISUAL_ID:
                return new PackageEditPart(view);

            case PackageNameEditPart.VISUAL_ID:
                return new PackageNameEditPart(view);

            case PackageStereoTypeLabelEditPart.VISUAL_ID:
                return new PackageStereoTypeLabelEditPart(view);

            case ClassEditPart.VISUAL_ID:
                return new ClassEditPart(view);

            case ClassNameEditPart.VISUAL_ID:
                return new ClassNameEditPart(view);

            case SuperClassNameLabelEditPart.VISUAL_ID:
                return new SuperClassNameLabelEditPart(view);

            case ClassStereoTypeLabelEditPart.VISUAL_ID:
                return new ClassStereoTypeLabelEditPart(view);

            case PrimitiveTypeEditPart.VISUAL_ID:
                return new PrimitiveTypeEditPart(view);

            case PrimitiveTypeNameEditPart.VISUAL_ID:
                return new PrimitiveTypeNameEditPart(view);

            case PrimTypeSuperTypeNameLabelEditPart.VISUAL_ID:
                return new PrimTypeSuperTypeNameLabelEditPart(view);

            case PrimTypeStereoTypeLabelEditPart.VISUAL_ID:
                return new PrimTypeStereoTypeLabelEditPart(view);

            case EnumerationEditPart.VISUAL_ID:
                return new EnumerationEditPart(view);

            case EnumerationNameEditPart.VISUAL_ID:
                return new EnumerationNameEditPart(view);

            case EnumerationSuperTypeNameEditPart.VISUAL_ID:
                return new EnumerationSuperTypeNameEditPart(view);

            case EnumerationStereoTypeLabelEditPart.VISUAL_ID:
                return new EnumerationStereoTypeLabelEditPart(view);

            case AssociationClassEditPart.VISUAL_ID:
                return new AssociationClassEditPart(view);

            case AssociationClassNameEditPart.VISUAL_ID:
                return new AssociationClassNameEditPart(view);

            case AssociationClassSuperClassNameEditPart.VISUAL_ID:
                return new AssociationClassSuperClassNameEditPart(view);

            case AssociationClassStereoTypeLabelEditPart.VISUAL_ID:
                return new AssociationClassStereoTypeLabelEditPart(view);

            case AssociationClassDanglingNodeEditPart.VISUAL_ID:
                return new AssociationClassDanglingNodeEditPart(view);

            case BadgeEditPart.VISUAL_ID:
                return new BadgeEditPart(view);

            case BadgeModelNameEditPart.VISUAL_ID:
                return new BadgeModelNameEditPart(view);

            case BadgeLabelAuthorEditPart.VISUAL_ID:
                return new BadgeLabelAuthorEditPart(view);

            case BadgeAuthorEditPart.VISUAL_ID:
                return new BadgeAuthorEditPart(view);

            case BadgeLabelModelIconEditPart.VISUAL_ID:
                return new BadgeLabelModelIconEditPart(view);

            case BadgeLabelDateCreatedEditPart.VISUAL_ID:
                return new BadgeLabelDateCreatedEditPart(view);

            case BadgeDateCreatedEditPart.VISUAL_ID:
                return new BadgeDateCreatedEditPart(view);

            case BadgeLabelDateModifiedEditPart.VISUAL_ID:
                return new BadgeLabelDateModifiedEditPart(view);

            case BadgeDateModifiedEditPart.VISUAL_ID:
                return new BadgeDateModifiedEditPart(view);

            case PropertyEditPart.VISUAL_ID:
                return new PropertyEditPart(view);

            case OperationEditPart.VISUAL_ID:
                return new OperationEditPart(view);

            case EnumerationLiteralEditPart.VISUAL_ID:
                return new EnumerationLiteralEditPart(view);

            case AssociationClassPropertyEditPart.VISUAL_ID:
                return new AssociationClassPropertyEditPart(view);

            case AssociationClassOperationEditPart.VISUAL_ID:
                return new AssociationClassOperationEditPart(view);

            case ProfileApplicationEditPart.VISUAL_ID:
                return new ProfileApplicationEditPart(view);

            case PackagePackageContentsCompartmentEditPart.VISUAL_ID:
                return new PackagePackageContentsCompartmentEditPart(view);

            case ClassClassAttributesCompartmentEditPart.VISUAL_ID:
                return new ClassClassAttributesCompartmentEditPart(view);

            case ClassClassOperationsCompartmentEditPart.VISUAL_ID:
                return new ClassClassOperationsCompartmentEditPart(view);

            case EnumerationEnumLitCompartmentEditPart.VISUAL_ID:
                return new EnumerationEnumLitCompartmentEditPart(view);

            case AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID:
                return new AssociationClassClassifierAttributesCompartmentEditPart(
                        view);

            case AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID:
                return new AssociationClassClassifierOperationsCompartmentEditPart(
                        view);

            case PackageBadgeProfilesCompartmentEditPart.VISUAL_ID:
                return new PackageBadgeProfilesCompartmentEditPart(view);

            case GeneralizationEditPart.VISUAL_ID:
                return new GeneralizationEditPart(view);

            case StereotypeLineLabelEditPart.VISUAL_ID:
                return new StereotypeLineLabelEditPart(view);

            case AssociationEditPart.VISUAL_ID:
                return new AssociationEditPart(view);

            case AssociationNameEditPart.VISUAL_ID:
                return new AssociationNameEditPart(view);

            case AssociationCardinalityAtSourceLabelEditPart.VISUAL_ID:
                return new AssociationCardinalityAtSourceLabelEditPart(view);

            case AssociationSourceLabelEditPart.VISUAL_ID:
                return new AssociationSourceLabelEditPart(view);

            case AssociationCardinalityAtTargetLabelEditPart.VISUAL_ID:
                return new AssociationCardinalityAtTargetLabelEditPart(view);

            case AssociationTargetLabelEditPart.VISUAL_ID:
                return new AssociationTargetLabelEditPart(view);

            case AssociationEndEditPart.VISUAL_ID:
                return new AssociationEndEditPart(view);

            case AssociationEndTargetNameLabelEditPart.VISUAL_ID:
                return new AssociationEndTargetNameLabelEditPart(view);

            case AssociationEndTargetMultiplicityLabelEditPart.VISUAL_ID:
                return new AssociationEndTargetMultiplicityLabelEditPart(view);

            case AssociationClassConnectorEditPart.VISUAL_ID:
                return new AssociationClassConnectorEditPart(view);
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
