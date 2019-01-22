package com.tibco.xpd.om.modeler.subdiagram.sheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewType;
import org.eclipse.gmf.runtime.diagram.ui.l10n.SharedImages;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.modeler.subdiagram.navigator.OrganizationModelSubNavigatorGroup;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @generated
 */
public class OrganizationModelSubSheetLabelProvider extends
		DecoratingLabelProvider {

	private Image noteAttachment;

	private static final String NOTE_LABEL = Messages.OrganizationModelSubSheetLabelProvider_note_label;

	private static final String TEXT_LABEL = Messages.OrganizationModelSubSheetLabelProvider_text_label;

	private static final String NOTE_ATTACHMENT_LABEL = Messages.OrganizationModelSubSheetLabelProvider_noteAttachment_label;

	private static final String DESCRIPTION_ID = "Description"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public OrganizationModelSubSheetLabelProvider() {
		super(new AdapterFactoryLabelProvider(
				OrganizationModelSubDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory()), null);
	}

	/**
	 * @generated NOT
	 */
	public String getText(Object element) {
		String text = _getText(element);

		if (element instanceof IStructuredSelection) {
			element = ((IStructuredSelection) element).getFirstElement();
		}

		element = unwrap(element);

		if (element instanceof EObject) {
			WorkingCopy wc = WorkingCopyUtil
					.getWorkingCopyFor((EObject) element);
			if (wc != null && wc.isReadOnly()) {
				text = String
						.format(
								Messages.OrganizationModelSubSheetLabelProvider_readOnlyTagged_element_label,
								text);
			}
		}

		return text;
	}

	/**
	 * Get the meta-name of the selected object.
	 * 
	 * @param element
	 * @return
	 */
	private String _getText(Object element) {
		// Make sure the description text relates to the EObject
		Object selected = unwrap(element);
		if (selected instanceof OrganizationModelSubNavigatorGroup) {
			return ((OrganizationModelSubNavigatorGroup) selected)
					.getGroupName();
		} else if (selected instanceof EObject) {
			if (selected instanceof View
					&& ((View) selected).getElement() == null) {
				View view = (View) selected;
				String type = view.getType();

				if (DESCRIPTION_ID.equals(type)) {
					// If this is a description then get its parent's type
					if (view.eContainer() instanceof View) {
						view = (View) view.eContainer();
						type = view.getType();
					}
				}

				if (ViewType.NOTE.equals(type)) {
					// Check if this is a note attachment
					if (hasNoteAttachment(view.getSourceEdges())
							|| hasNoteAttachment(view.getTargetEdges())) {
						return NOTE_ATTACHMENT_LABEL;
					}
					return NOTE_LABEL;
				} else if (ViewType.TEXT.equals(type)) {
					return TEXT_LABEL;
				} else if (ViewType.NOTEATTACHMENT.equals(type)) {
					return NOTE_ATTACHMENT_LABEL;
				}

				return capitalize(type);
			}

			String label = WorkingCopyUtil.getMetaText((EObject) selected);
			if (label != null) {
				return label;
			}
		}
		return super.getText(selected);
	}

	/**
	 * Set the first character in the given string to an uppercase.
	 * 
	 * @param str
	 * @return
	 */
	private String capitalize(String str) {
		if (str != null && str.length() > 1) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

	/**
	 * @generated NOT
	 */
	public Image getImage(Object element) {
		Object selected = unwrap(element);

		if (selected instanceof View) {
			View view = (View) selected;
			String type = view.getType();
			boolean isNoteAttachment = false;

			if (DESCRIPTION_ID.equals(type)) {
				// If this is a description then get its parent's type
				if (view.eContainer() instanceof View) {
					view = (View) view.eContainer();
					type = view.getType();
				}
			}

			if (ViewType.NOTE.equals(type)) {
				// Check if this is a note attachment
				if (hasNoteAttachment(view.getSourceEdges())
						|| hasNoteAttachment(view.getTargetEdges())) {
					isNoteAttachment = true;
				} else {
					// This is a note
					return SharedImages.get(SharedImages.IMG_NOTE);
				}
			} else if (ViewType.TEXT.equals(type)) {
				return SharedImages.get(SharedImages.IMG_TEXT);
			} else if (ViewType.NOTEATTACHMENT.equals(type)) {
				isNoteAttachment = true;
			}

			if (isNoteAttachment) {
				if (noteAttachment == null) {
					ImageDescriptor imageDesc = SharedImages.DESC_NOTE_ATTACHMENT;
					if (imageDesc != null) {
						noteAttachment = imageDesc.createImage();
					}
				}
				if (noteAttachment != null) {
					return noteAttachment;
				}
			}
		}
		return super.getImage(selected);
	}

	/**
	 * Check if the given list of {@link Edge}s contains a note attachment.
	 * 
	 * @param edges
	 * @return
	 */
	private boolean hasNoteAttachment(EList<?> edges) {
		if (edges != null && !edges.isEmpty()) {
			for (Object obj : edges) {
				if (obj instanceof Edge
						&& ViewType.NOTEATTACHMENT.equals(((Edge) obj)
								.getType())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	private Object unwrap(Object element) {
		if (element instanceof IStructuredSelection) {
			return unwrap(((IStructuredSelection) element).getFirstElement());
		}
		if (element instanceof EditPart) {
			return unwrapEditPart((EditPart) element);
		}
		if (element instanceof IAdaptable) {
			View view = (View) ((IAdaptable) element).getAdapter(View.class);
			if (view != null) {
				return unwrapView(view);
			}
		}
		return element;
	}

	/**
	 * @generated
	 */
	private Object unwrapEditPart(EditPart p) {
		if (p.getModel() instanceof View) {
			return unwrapView((View) p.getModel());
		}
		return p.getModel();
	}

	/**
	 * @generated
	 */
	private Object unwrapView(View view) {
		return view.getElement() == null ? view : view.getElement();
	}

	@Override
	public void dispose() {
		if (noteAttachment != null) {
			noteAttachment.dispose();
			noteAttachment = null;
		}
		super.dispose();
	}
}
