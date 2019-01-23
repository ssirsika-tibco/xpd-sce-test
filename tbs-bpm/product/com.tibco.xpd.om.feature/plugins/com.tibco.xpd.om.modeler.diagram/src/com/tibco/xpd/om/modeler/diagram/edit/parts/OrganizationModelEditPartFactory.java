package com.tibco.xpd.om.modeler.diagram.edit.parts;

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

import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelOrgModelGroupsEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.GroupItemEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelNameBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrganizationModelEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;

			switch (OrganizationModelVisualIDRegistry.getVisualID(view)) {

			case OrgModelGroupsEditPart.VISUAL_ID:
				return new OrgModelGroupsEditPart(view);
			case LabelOrgModelGroupsEditPart.VISUAL_ID:
				return new LabelOrgModelGroupsEditPart(view);
			case OrgModelGroupsCompartmentEditPart.VISUAL_ID:
				return new OrgModelGroupsCompartmentEditPart(view);
			case GroupItemEditPart.VISUAL_ID:
				return new GroupItemEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(OrgModelBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new OrgModelBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							OrgModelNameBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new OrgModelNameBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							OrgModelAuthorBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new OrgModelAuthorBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							OrgModelDateCreatedBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new OrgModelDateCreatedBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							OrgModelDateModifiedBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new OrgModelDateModifiedBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType()
							.equals(LabelAuthorBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new LabelAuthorBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							LabelDateCreatedBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new LabelDateCreatedBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							LabelDateModifiedBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new LabelDateModifiedBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							OrgModelVersionBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new OrgModelVersionBadgeEditPart(view);
			}

			if (view.getType() != null
					&& view.getType().equals(
							LabelVersionBadgeEditPart.VISUAL_ID)) {
				// return new Note2EditPart(view);
				return new LabelVersionBadgeEditPart(view);
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

			case OrgModelEditPart.VISUAL_ID:
				return new OrgModelEditPart(view);

			case OrganizationEditPart.VISUAL_ID:
				return new OrganizationEditPart(view);

			case OrganizationDisplayNameEditPart.VISUAL_ID:
				return new OrganizationDisplayNameEditPart(view);

			case OrganizationTypeNameEditPart.VISUAL_ID:
				return new OrganizationTypeNameEditPart(view);

			case OrgUnitEditPart.VISUAL_ID:
				return new OrgUnitEditPart(view);

			case OrgUnitDisplayNameEditPart.VISUAL_ID:
				return new OrgUnitDisplayNameEditPart(view);

			case OrgUnitFeatureNameEditPart.VISUAL_ID:
				return new OrgUnitFeatureNameEditPart(view);

			case PositionEditPart.VISUAL_ID:
				return new PositionEditPart(view);

			case DynamicOrgUnitEditPart.VISUAL_ID:
				return new DynamicOrgUnitEditPart(view);

			case DynamicOrgUnitDisplayNameEditPart.VISUAL_ID:
				return new DynamicOrgUnitDisplayNameEditPart(view);

			case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
				return new OrganizationOrgUnitCompartmentEditPart(view);

			case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
				return new OrgUnitPositionCompartmentEditPart(view);

			case OrgUnitRelationshipEditPart.VISUAL_ID:
				return new OrgUnitRelationshipEditPart(view);

			case OrgUnitRelationshipDisplayNameEditPart.VISUAL_ID:
				return new OrgUnitRelationshipDisplayNameEditPart(view);

			case DynamicOrgReferenceEditPart.VISUAL_ID:
				return new DynamicOrgReferenceEditPart(view);
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
				int avr = FigureUtilities.getFontMetrics(text.getFont())
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
			int avr = FigureUtilities.getFontMetrics(text.getFont())
					.getAverageCharWidth();
			rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
					SWT.DEFAULT)).expand(avr * 2, 0));
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
}
