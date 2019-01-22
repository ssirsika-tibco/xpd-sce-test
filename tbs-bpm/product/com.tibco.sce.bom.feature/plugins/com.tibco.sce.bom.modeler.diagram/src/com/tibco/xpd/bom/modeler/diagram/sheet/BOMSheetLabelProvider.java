/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.sheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewType;
import org.eclipse.gmf.runtime.diagram.ui.l10n.SharedImages;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Element;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @generated
 */
public class BOMSheetLabelProvider extends DecoratingLabelProvider {

    private Image noteAttachment;

    private static final String NOTE_LABEL =
            Messages.BOMSheetLabelProvider_note_label;

    private static final String TEXT_LABEL =
            Messages.BOMSheetLabelProvider_text_label;

    private static final String NOTE_ATTACHMENT_LABEL =
            Messages.BOMSheetLabelProvider_noteAttachment_label;

    private static final String DESCRIPTION_ID = "Description"; //$NON-NLS-1$

    /**
     * @generated
     */
    public BOMSheetLabelProvider() {
        super(new AdapterFactoryLabelProvider(BOMDiagramEditorPlugin
                .getInstance().getItemProvidersAdapterFactory()), null);
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
            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopyFor((EObject) element);
            if (wc != null && wc.isReadOnly()) {
                text = String.format(Messages.BOMSheetLabelProvider_readOnlyTag_properties_title, text);
            }
        }

        return text;
    };

    /**
     * Get the meta-name of the selected object.
     * 
     * @param element
     * @return
     */
    private String _getText(Object element) {

        if (element instanceof IStructuredSelection
                && ((IStructuredSelection) element).size() > 1) {
            return String
                    .format(Messages.BOMSheetLabelProvider_multiselect_label,
                            ((IStructuredSelection) element).size());
        }
        Object selected = unwrap(element);

        if (selected instanceof View && ((View) selected).getElement() == null) {
            // GMF notation element
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
        if (selected instanceof Element) {
            return WorkingCopyUtil.getMetaText((EObject) selected);
        }

        if (selected instanceof Diagram) {
            return Messages.BOMSheetLabelProvider_diagram_label;
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

        if (element instanceof IStructuredSelection
                && ((IStructuredSelection) element).size() > 1) {
            return null;
        }
        Object selected = unwrap(element);

        if (selected instanceof View) {

            /*
             * If the view is a diagram then return the diagram image (we don't
             * control its item provider)
             */
            if (selected instanceof Diagram) {
                return Activator.getDefault().getImageRegistry()
                        .get(BOMImages.DIAGRAM);
            }

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
                    ImageDescriptor imageDesc =
                            SharedImages.DESC_NOTE_ATTACHMENT;
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
     * @generated NOT
     */
    private Object unwrap(Object element) {
        if (element instanceof IStructuredSelection) {
            return unwrap(((IStructuredSelection) element).getFirstElement());
        }

        if (element instanceof EditPart) {
            Object model = null;
            if (element instanceof AssociationSourceLabelEditPart
                    || element instanceof AssociationCardinalityAtSourceLabelEditPart) {

                // association source role label
                model = ((EditPart) element).getAdapter(EObject.class);
                if (model instanceof Association) {
                    model = ((Association) model).getMemberEnds().get(0);
                }
            } else if (element instanceof AssociationTargetLabelEditPart
                    || element instanceof AssociationCardinalityAtTargetLabelEditPart) {

                // association target role label
                model = ((EditPart) element).getAdapter(EObject.class);
                if (model instanceof Association) {
                    model = ((Association) model).getMemberEnds().get(1);
                }
            } else {
                // generic case
                return unwrapEditPart((EditPart) element);
            }

            if (model instanceof View) {
                return unwrapView((View) model);
            } else if (model instanceof EObject) {
                return model;
            }
        } else if (element instanceof IAdaptable) {
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
        if (view instanceof Diagram && BomUIUtil.isUserDiagram((Diagram) view)) {
            return view;
        }

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
