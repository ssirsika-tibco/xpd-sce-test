/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.clipboard;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

/**
 * BOM Paste operation. This is a copy of the
 * org.eclipse.gmf.runtime.notation.providers.internal.copypaste
 * PositionalGeneralViewPasteOperation with BOM specific changes.
 * 
 * @author njpatel
 * 
 */
public class PositionalGeneralViewPasteOperation extends
        OverridePasteChildOperation {

    private static final String CLASS_ATTR_COMPARTMENT_TYPE = "5002"; //$NON-NLS-1$

    private static final String CLASS_OPERATION_COMPARTMENT_TYPE = "5003"; //$NON-NLS-1$

    private static final String PACKAGE_CLASS_COMPARTMENT_TYPE = "5001"; //$NON-NLS-1$
    
    private static final String ENUM_ENUMLIT_COMPARTMENT_TYPE = "5004"; //$NON-NLS-1$
  

    private boolean shouldPasteAlwaysCopyObject;

    /**
     * @param overriddenChildPasteOperation
     */
    public PositionalGeneralViewPasteOperation(
            PasteChildOperation overriddenChildPasteOperation,
            boolean shouldPasteAlwaysCopyObject) {
        super(overriddenChildPasteOperation);
        this.shouldPasteAlwaysCopyObject = shouldPasteAlwaysCopyObject;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#paste()
     */
    public void paste() throws Exception {
        // delay unsetting of connector refs
    }

    protected boolean shouldPasteAlwaysCopyObject(
            ObjectInfo alwaysCopyObjectInfo) {
        return shouldPasteAlwaysCopyObject;
    }

    private EObject getSemanticPasteTarget(View view) {
        EObject eo = null;

        if (view != null) {
            EObject container = view.eContainer();
            if (container instanceof View) {
                eo = ((View) container).getElement();
            }
        }
        return eo;
    }

    public PasteChildOperation getPostPasteOperation() {

        return new PostPasteChildOperation(this, EMPTY_ARRAY) {

            public void paste() throws Exception {

                if (isCancelled()) {
                    throwCancelException();
                }

                /*
                 * unset connectors before pasting so that it won't affect real
                 * connectors especially if they happen to belong to the same
                 * target diagram
                 */
                Node view = (Node) getEObject();
                view.eUnset(NotationPackage.eINSTANCE.getView_SourceEdges());
                view.eUnset(NotationPackage.eINSTANCE.getView_TargetEdges());

                // now paste view
                EObject pastedElement = doPasteInto(getParentEObject());
                // did we succeed?
                if (pastedElement != null) {
                    setPastedElement(pastedElement);
                    addPastedElement(pastedElement);
                } else {
                    addPasteFailuresObject(getEObject());
                }
            }

            protected boolean shouldPasteAlwaysCopyObject(
                    ObjectInfo alwaysCopyObjectInfo) {
                return PositionalGeneralViewPasteOperation.this
                        .shouldPasteAlwaysCopyObject(alwaysCopyObjectInfo);
            }

            @Override
            protected EObject doPasteInto(EObject pasteIntoEObject) {
                if (pasteIntoEObject != null) {
                    EReference reference = null;
                    EObject src = getEObject();

                    if (pasteIntoEObject instanceof View && src instanceof View) {
                        View parentView = (View) pasteIntoEObject;
                        View srcView = (View) src;

                        EObject parentSemanticElement = parentView.getElement();
                        EObject srcSemanticElement = srcView.getElement();

                        if (parentView.getType() == srcView.getType()
                                && !(parentSemanticElement instanceof Package)) {
                            // Copying object into another object of the same
                            // type so try pasting into the target's
                            // parent(except for packages)
                            if (pasteIntoEObject.eContainer() != null) {
                                return doPasteInto(pasteIntoEObject
                                        .eContainer());
                            }
                        }

                        if (!(parentSemanticElement instanceof Model)) {
                            if (srcSemanticElement != null) {
                                // Don't allow paste into primitive types or
                                // Class/Primitive Type into Class
                                if (parentSemanticElement instanceof PrimitiveType
                                        || (parentSemanticElement instanceof Class && srcSemanticElement instanceof Classifier)
                                        || (parentSemanticElement instanceof Package && !(srcSemanticElement instanceof PackageableElement))) {
                                    return null;
                                }

                                if (parentSemanticElement instanceof Class) {
                                    if (srcSemanticElement instanceof Property) {
                                        View container = getContainer(
                                                parentView,
                                                CLASS_ATTR_COMPARTMENT_TYPE);
                                        if (container != null) {
                                            pasteIntoEObject = container;
                                            reference = container
                                                    .eContainmentFeature();
                                        }

                                    } else if (srcSemanticElement instanceof Operation) {
                                        View container = getContainer(
                                                parentView,
                                                CLASS_OPERATION_COMPARTMENT_TYPE);
                                        if (container != null) {
                                            pasteIntoEObject = container;
                                            reference = container
                                                    .eContainmentFeature();
                                        }
                                    }
                                } else if (parentSemanticElement instanceof Package) {
                                    if (srcSemanticElement instanceof PackageableElement) {
                                        View container = getContainer(
                                                parentView,
                                                PACKAGE_CLASS_COMPARTMENT_TYPE);
                                        if (container != null) {
                                            pasteIntoEObject = container;
                                            reference = container
                                                    .eContainmentFeature();
                                        }
                                    }
                                } else if (parentSemanticElement instanceof Enumeration) {
                                    if (srcSemanticElement instanceof EnumerationLiteral) {
                                        View container = getContainer(
                                                parentView,
                                                ENUM_ENUMLIT_COMPARTMENT_TYPE);
                                        if (container != null) {
                                            pasteIntoEObject = container;
                                            reference = container
                                                    .eContainmentFeature();
                                        }
                                    }
                                } 
                            } else {
                                /*
                                 * Source semantic element is null so this is
                                 * either a shape, note or text. Therefore,
                                 * paste into correct container if package or
                                 * class, otherwise disallow paste
                                 */
                                if (parentSemanticElement instanceof Package) {
                                    View container = getContainer(parentView,
                                            PACKAGE_CLASS_COMPARTMENT_TYPE);
                                    if (container != null) {
                                        pasteIntoEObject = container;
                                        reference = container
                                                .eContainmentFeature();
                                    }
                                } else if (parentSemanticElement instanceof Class) {
                                    // If right compartment is not selected then
                                    // default to property's compartment

                                    // Not allow Text to be pasted into Class
                                    if ("Text".equals(srcView.getType())) { //$NON-NLS-1$
                                        return null;
                                    }

                                    if (!(parentView.getType().equals(
                                            CLASS_ATTR_COMPARTMENT_TYPE) || parentView
                                            .getType()
                                            .equals(
                                                    CLASS_OPERATION_COMPARTMENT_TYPE))) {
                                        View container = getContainer(
                                                parentView,
                                                CLASS_ATTR_COMPARTMENT_TYPE);
                                        if (container != null) {
                                            pasteIntoEObject = container;
                                            reference = container
                                                    .eContainmentFeature();
                                        }
                                    }
                                } else {
                                    return null;
                                }
                            }
                        }
                    }

                    if (reference == null) {
                        reference = getPasteContainmentFeature(pasteIntoEObject);
                    }
                    if (reference != null && pasteIntoEObject != null) {
                        return doPasteInto(pasteIntoEObject, reference);
                    }
                }
                return null;
            }

            /**
             * Get the correct compartment (type) of the given view.
             * 
             * @param view
             *            parent view
             * @param type
             *            compartment type required from the view
             * @return compartment view if found, <code>null</code> otherwise.
             */
            private View getContainer(View view, String type) {
                View containerView = null;

                if (view != null && type != null) {
                    if (view.getType().equals(type)) {
                        containerView = view;
                    } else {
                        // Search all children
                        EList<?> children = view.getChildren();

                        if (children != null) {
                            for (Object child : children) {
                                if (child instanceof View
                                        && ((View) child).getType()
                                                .equals(type)) {
                                    containerView = (View) child;
                                    break;
                                }
                            }
                        }
                    }

                    // If no container view was found of the given type then
                    // check this view's siblings
                    if (containerView == null
                            && view.eContainer() instanceof View) {
                        containerView = getContainer((View) view.eContainer(),
                                type);
                    }
                }

                return containerView;
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.gmf.runtime.emf.core.internal.copypaste.PasteChildOperation#makeAuxiliaryChildPasteProcess(org.eclipse.gmf.runtime.emf.core.internal.copypaste.ObjectInfo)
             */
            @SuppressWarnings("restriction")
            protected PasteChildOperation makeAuxiliaryChildPasteProcess(
                    ObjectInfo auxiliaryChildEObjectInfo) {
                EObject semanticPasteTarget = getSemanticPasteTarget((View) getPastedElement());

                if (semanticPasteTarget == null) {
                    return null;
                }
                return new PasteChildOperation(getParentPasteProcess().clone(
                        semanticPasteTarget), auxiliaryChildEObjectInfo);
            }

            public PasteChildOperation getPostPasteOperation() {
                List<?> operations = getAlwaysCopyObjectPasteOperations();
                return new PostPasteChildOperation(this, operations);
            }
        };
    }
}