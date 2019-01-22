/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.clipboard;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardSupportUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.CopyOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverrideCopyOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteAction;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteOption;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.ClipboardPlugin;
import org.eclipse.gmf.runtime.emf.clipboard.core.internal.ObjectCopyType;
import org.eclipse.gmf.runtime.emf.core.clipboard.AbstractClipboardSupport;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.providers.internal.copypaste.ConnectorViewPasteOperation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * Clipboard support for the BOM. (This is used directly for copy/paste rather
 * than go through the clipboard support framework due to current limitations in
 * the framework.)
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("restriction")
public class BOMClipboardSupportHelper extends AbstractClipboardSupport {

    private static final String GENERALIZATION_VISUAL_ID = "3001"; //$NON-NLS-1$

    private static final String OPERATION_COMPARTMENT_VISUAL_ID = "5003"; //$NON-NLS-1$

    private Set<String> stereotypeAppIds;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport#destroy(
     * org.eclipse.emf.ecore.EObject)
     */
    public void destroy(EObject eObject) {
        DestroyElementCommand.destroy(eObject);
    }

    /**
     * Get the paste collision action.
     * 
     * @return the {@link PasteAction#ADD}action, always
     */
    public PasteAction getPasteCollisionAction(EClass eClass) {
        return PasteAction.ADD;
    }

    /**
     * By default, the following paste options are supported:
     * <ul>
     * <li>{@link PasteOption#NORMAL}: always</li>
     * <li>{@link PasteOption#PARENT}: never</li>
     * <li>{@link PasteOption#DISTANT}: if and only only if the
     * <code>eStructuralFeature</code> is a
     * {@link org.eclipse.gmf.runtime.notation.View}'s reference to its semantic
     * {@linkplain org.eclipse.gmf.runtime.notation.View#getElement() element}</li>
     * </ul>
     */
    public boolean hasPasteOption(EObject contextEObject,
            EStructuralFeature eStructuralFeature, PasteOption pasteOption) {
        if (pasteOption.equals(PasteOption.NORMAL)) {
            return true;
        } else if (pasteOption.equals(PasteOption.PARENT)) {
            // disable the copy-parent functionality completely.
            return false;
        } else if (pasteOption.equals(PasteOption.DISTANT)) {
            if (eStructuralFeature == null) {
                return false;
            }
            return NotationPackage.eINSTANCE.getView_Element()
                    .equals(eStructuralFeature);
        } else {
            return false;
        }
    }

    /**
     * By default, transient and derived references are never copied, and
     * containment references and the
     * {@linkplain org.eclipse.gmf.runtime.notation.View#getElement() element}
     * reference always are copied.
     */
    public boolean isCopyAlways(EObject context, EReference eReference,
            Object value) {

        if ((eReference.isTransient()) || (eReference.isDerived())) {
            return false;
        } else if (eReference.equals(NotationPackage.eINSTANCE
                .getView_Element())) {
            return true;
        } else {
            return eReference.isContainment();
        }
    }

    /**
     * By default, provides a paste operation for notation models.
     */
    public boolean shouldOverrideChildPasteOperation(EObject parentElement,
            EObject childEObject) {
        EPackage pkg = childEObject.eClass().getEPackage();

        if (pkg == EcorePackage.eINSTANCE) {
            // If this is profile annotation then override (also if
            // stereotype annotation then register it)
            if (childEObject instanceof EAnnotation) {
                if (isProfileAnnotation(childEObject)) {
                    return true;
                }
                if (((EAnnotation) childEObject).getSource()
                        .equals(BOMCopyPasteCommandFactory.ANNOT_STEREOTYPES)) {
                    stereotypeAppIds =
                            getStereotypeAppIds((EAnnotation) childEObject);
                    return true;
                }
            }

        } else if (pkg == NotationPackage.eINSTANCE) {
            // Only override view elements that have an attached semantic model
            // and is recognised
            if (childEObject instanceof View) {
                View childView = (View) childEObject;

                if (childView.getElement() != null
                        && isInterestedSemanticElement(childView.getElement())) {
                    return true;
                }

                /*
                 * If the parent is known to us and the child view has no
                 * element then override paste (this will allow us to disallow
                 * pasting of Note or Text into Class for instance).
                 */
                if (childView.getElement() == null) {
                    EObject parentEObject =
                            parentElement instanceof View ? ((View) parentElement)
                                    .getElement() : parentElement;

                    if (isInterestedSemanticElement(parentEObject)) {
                        return true;
                    }
                }
            }
        } else if (pkg == UMLPackage.eINSTANCE) {
            if (isInterestedSemanticElement(childEObject)) {
                return true;
            }
        } else if (isStereotypeApplication(childEObject)) {
            return true;
        }

        return false;
    }

    /**
     * Check if the given semantic element is a recognised element.
     * 
     * @param elem
     *            semantic element.
     * @return <code>true</code> if the element is an instance of
     *         <code>Package</code>, <code>Class</code>,
     *         <code>PrimitiveType</code>, <code>Property</code>,
     *         <code>Operation</code>, <code>Association</code> or
     *         <code>Generalization</code>, <code>false</code> otherwise.
     */
    private boolean isInterestedSemanticElement(EObject elem) {
        boolean ret = false;

        if (elem != null) {
            ret =
                    elem instanceof Package || elem instanceof Class
                            || elem instanceof PrimitiveType
                            || elem instanceof Property
                            || elem instanceof Operation
                            || elem instanceof Association
                            || elem instanceof Generalization
                            || elem instanceof Enumeration
                            || elem instanceof EnumerationLiteral;
        }

        return ret;
    }

    /**
     * By default, don't provide any copy override behaviour.
     */
    @SuppressWarnings("rawtypes")
    public boolean shouldOverrideCopyOperation(Collection eObjects, Map hintMap) {
        return false;
    }

    /**
     * Check if the given <code>EObject</code> is a profile annotation.
     * 
     * @param eo
     *            object to test
     * @return <code>true</code> if profile annotation, <code>false</code>
     *         otherwise.
     */
    private boolean isProfileAnnotation(EObject eo) {
        boolean isProfileAnnot = false;
        if (eo instanceof EAnnotation) {
            EAnnotation annot = (EAnnotation) eo;

            isProfileAnnot =
                    annot.getSource() != null
                            && annot.getSource()
                                    .equals(BOMCopyPasteCommandFactory.ANNOT_PROFILES);

        }
        return isProfileAnnot;
    }

    /**
     * Check if the given <code>EObject</code> is a <code>Stereotype</code>
     * application. This will use the stereotype annotation in the copied
     * objects.
     * 
     * @param eo
     *            EObject to check
     * @return <code>true</code> if given <code>EObject</code> is a
     *         <code>Stereotype</code> application, <code>false</code>
     *         otherwise.
     */
    private boolean isStereotypeApplication(EObject eo) {
        boolean isApp = false;

        if (eo != null) {
            XMLResource res = getResource(eo);

            if (res != null) {
                // If the stereotype annotation hasn't already been processed
                // then look it up in the resource
                if (stereotypeAppIds == null) {
                    EList<EObject> contents = res.getContents();

                    if (contents != null) {
                        for (EObject content : contents) {
                            if (content instanceof EAnnotation) {
                                stereotypeAppIds =
                                        getStereotypeAppIds((EAnnotation) content);

                                if (stereotypeAppIds != null) {
                                    // ids found
                                    break;
                                }
                            }
                        }
                    }
                }

                if (stereotypeAppIds != null) {
                    String id = res.getID(eo);

                    isApp = id != null && stereotypeAppIds.contains(id);
                }
            }
        }

        return isApp;
    }

    /**
     * Get the Stereotype application Ids from the given annotation. If the
     * given annotation is not a stereotype annotation then this will do nothing
     * and return <code>null</code>.
     * 
     * @param annot
     *            annotation to get stereotype ids from if stereotype
     *            annotation.
     * @return set of stereotype ids if stereotype annotation, <code>null</code>
     *         otherwise.
     */
    private Set<String> getStereotypeAppIds(EAnnotation annot) {
        if (annot != null
                && annot.getSource() != null
                && annot.getSource()
                        .equals(BOMCopyPasteCommandFactory.ANNOT_STEREOTYPES)) {
            Set<String> ids = new HashSet<String>();

            EList<EObject> contents = annot.getContents();

            for (EObject content : contents) {
                if (content instanceof EAnnotation) {
                    ids.add(((EAnnotation) content).getSource());
                }
            }

            return ids;
        }
        return null;
    }

    /**
     * Check if paste should be allowed.
     * 
     * @param overriddenChildPasteOperation
     * @return
     */
    private boolean shouldAllowPaste(
            PasteChildOperation overriddenChildPasteOperation) {
        EObject eObject = overriddenChildPasteOperation.getEObject();
        EObject parentEObject =
                overriddenChildPasteOperation.getParentEObject();

        if (isStereotypeApplication(eObject) || isProfileAnnotation(eObject)) {
            return true;
        } else {
            if ((parentEObject instanceof View) && (eObject instanceof View)) {
                EObject semanticChildElement = ((View) eObject).getElement();
                if (semanticChildElement == null) {
                    return true;
                }
                if (semanticChildElement.eIsProxy()) {
                    semanticChildElement =
                            ClipboardSupportUtil.resolve(semanticChildElement,
                                    overriddenChildPasteOperation
                                            .getParentPasteProcess()
                                            .getLoadedIDToEObjectMapCopy());
                    if (semanticChildElement.eIsProxy()) {
                        semanticChildElement =
                                EcoreUtil.resolve(semanticChildElement,
                                        getResource(parentEObject));
                    }
                }

                EPackage semanticChildEpackage =
                        semanticChildElement.eClass().getEPackage();
                EPackage diagramRootContainerEpackage =
                        EcoreUtil.getRootContainer(parentEObject).eClass()
                                .getEPackage();
                EPackage sematicDiagramRootContainerEpackage = null;
                EObject sematicDiagramElement =
                        ((View) parentEObject).getElement();
                if (sematicDiagramElement != null) {
                    sematicDiagramRootContainerEpackage =
                            EcoreUtil.getRootContainer(sematicDiagramElement)
                                    .eClass().getEPackage();
                }

                if (diagramRootContainerEpackage != NotationPackage.eINSTANCE) {
                    if (semanticChildEpackage != diagramRootContainerEpackage) {
                        return false;
                    }
                }

                if ((sematicDiagramRootContainerEpackage != null)
                        && (sematicDiagramRootContainerEpackage != NotationPackage.eINSTANCE)) {
                    if (semanticChildEpackage != sematicDiagramRootContainerEpackage) {
                        return false;
                    }
                }
            }

            /*
             * Before checking semantic model, if the child is an edge and the
             * parent a node then allow paste as in the notation view the edge
             * is contained in model/package view and the generalization is not
             * contained in model/package but in class.
             */
            if (eObject instanceof Edge
                    && (parentEObject instanceof Node || parentEObject instanceof Diagram)) {
                return true;
            }

            if (parentEObject instanceof View) {
                parentEObject = ((View) parentEObject).getElement();
            }
            if (eObject instanceof View) {
                eObject = ((View) eObject).getElement();

                if (eObject == null) {
                    // This will be case with note, text etc.
                    return true;
                }
            }

            if (eObject.eClass().getEPackage() == UMLPackage.eINSTANCE
                    && parentEObject.eClass().getEPackage() == UMLPackage.eINSTANCE) {
                // If parent and child are of same type then try pasting into
                // parent's container (except package as it can contain other
                // packages)
                if (!(parentEObject instanceof Package)
                        && parentEObject.eClass() == eObject.eClass()) {
                    if (parentEObject.eContainer() != null) {
                        parentEObject = parentEObject.eContainer();
                    }
                }
                boolean canPaste = false;

                if (parentEObject instanceof Package) {
                    if (eObject instanceof PackageableElement) {
                        canPaste = true;
                    }
                } else if (parentEObject instanceof Class) {
                    if (eObject instanceof Property) {
                        // Need to check to see if the property being pasted is
                        // a case identifier as these can only be pasted into
                        // Case Classes
                        Property prop = (Property) eObject;
                        if (GlobalDataProfileManager.getInstance()
                                .isAutoCaseIdentifier(prop)
                                || GlobalDataProfileManager.getInstance()
                                        .isCID(prop)
                                || GlobalDataProfileManager.getInstance()
                                        .isCompositeCaseIdentifier(prop)) {
                            // If this property is a case identifier, then only
                            // allow the paste if this is a Case Class
                            if (GlobalDataProfileManager.getInstance()
                                    .isCase((Class) parentEObject)) {
                                canPaste = true;
                            }
                        } else {
                            canPaste = true;
                        }
                    } else if (eObject instanceof Operation) {
                        canPaste = true;
                    }
                } else if (parentEObject instanceof Enumeration) {
                    if (eObject instanceof EnumerationLiteral) {
                        canPaste = true;
                    }
                }

                if (canPaste) {
                    // Check if there are any containment restrictions on
                    // stereotypes
                    if (parentEObject instanceof Element) {
                        canPaste = canPaste((Element) parentEObject, eObject);
                    }

                    if (canPaste) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * By default, provide child paste override behaviour for notation objects.
     * 
     * @return <code>null</code>, always
     */
    public OverridePasteChildOperation getOverrideChildPasteOperation(
            PasteChildOperation overriddenChildPasteOperation) {
        if (shouldAllowPaste(overriddenChildPasteOperation)) {
            EObject eObject = overriddenChildPasteOperation.getEObject();
            EObject parentEObject =
                    overriddenChildPasteOperation.getParentEObject();
            if (eObject instanceof Node && parentEObject instanceof View) {
                Node node = (Node) eObject;
                EObject element = node.getElement();
                if ((element != null)) {
                    return new PositionalGeneralViewPasteOperation(
                            overriddenChildPasteOperation, true);
                }
                return new PositionalGeneralViewPasteOperation(
                        overriddenChildPasteOperation, false);
            } else if (eObject instanceof Edge) {
                return new ConnectorViewPasteOperation(
                        overriddenChildPasteOperation);
            } else if (isStereotypeApplication(eObject)) {
                return new StereotypePasteOperation(
                        overriddenChildPasteOperation);
            } else if (isProfileAnnotation(eObject)) {
                return new ProfilePasteOperation(overriddenChildPasteOperation);
            } else {
                /*
                 * Allow pasting of Notation object into semantic parent or
                 * vice-versa.
                 */
                return new OverridePasteChildOperation(
                        overriddenChildPasteOperation) {

                    private boolean getSemanticElement = false;

                    @Override
                    public void paste() throws Exception {
                        EObject parentEObject = getParentEObject();
                        EObject pasteEObject = getEObject();

                        if (parentEObject != null) {
                            /*
                             * If copying a diagram then paste into the
                             * resource.
                             */
                            if (pasteEObject instanceof Diagram) {
                                EObject pastedObj =
                                        doPasteInto(parentEObject.eResource());
                                if (pastedObj != null) {
                                    setPastedElement(pastedObj);
                                    addPastedElement(pastedObj);
                                } else {
                                    addPasteFailuresObject(pastedObj);
                                }
                            } else {
                                EReference ref =
                                        getPasteContainmentFeature(parentEObject);

                                if (ref == null
                                        && parentEObject instanceof View) {
                                    // See if can be pasted to semantic element
                                    parentEObject =
                                            ((View) parentEObject).getElement();
                                    if (parentEObject != null) {
                                        ref =
                                                getPasteContainmentFeature(parentEObject);
                                    }
                                } else if (pasteEObject instanceof View) {
                                    getSemanticElement = true;
                                    ref =
                                            getPasteContainmentFeature(parentEObject);
                                }

                                if (ref != null) {
                                    EObject eo =
                                            doPasteInto(parentEObject, ref);

                                    if (eo != null) {
                                        setPastedElement(eo);
                                        addPastedElement(eo);
                                    } else {
                                        addPasteFailuresObject(getEObject());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public EObject getEObject() {
                        EObject eo = super.getEObject();

                        if (eo instanceof View && getSemanticElement) {
                            eo = ((View) eo).getElement();
                        }

                        return eo;
                    }

                    /**
                     * Custom rename class to handle the rename for enumeration
                     * literal values
                     * 
                     * @param list
                     * @param eObject
                     */
                    private void renameEnumLit(Collection<?> list,
                            EObject eObject) {
                        if (isNameable(eObject) == false) {
                            return;
                        }
                        String new_name = null;
                        String name =
                                ClipboardUtil.createClipboardSupport(eObject)
                                        .getName(eObject);

                        try {
                            int i = name.length();
                            while (i > 0
                                    && Character.isDigit(name.charAt(i - 1))) {
                                i--;
                            }
                            int appendNum = 1;
                            if (i < name.length()) {
                                appendNum = Integer.parseInt(name.substring(i));
                                appendNum++;
                            }
                            // Create the new name
                            new_name = name.substring(0, i) + appendNum;

                        } catch (NumberFormatException nfe) {
                            ClipboardPlugin
                                    .catching(ClipboardSupportUtil.class,
                                            "rename", nfe); //$NON-NLS-1$
                            new_name = null;
                        }

                        if (new_name != null) {
                            ClipboardUtil.createClipboardSupport(eObject)
                                    .setName(eObject, new_name);

                            // check this new name itself does not collide with
                            // an existing one
                            if (ClipboardSupportUtil.hasNameCollision(list,
                                    eObject)) {
                                renameEnumLit(list, eObject);
                            }
                        }
                    }

                    @SuppressWarnings("rawtypes")
                    @Override
                    protected boolean handleCollision(EReference reference,
                            List list, EObject eObject, ObjectInfo eObjectInfo) {
                        if (!(eObject instanceof EnumerationLiteral)) {
                            return super.handleCollision(reference,
                                    list,
                                    eObject,
                                    eObjectInfo);
                        }

                        // Must be an Enum Literal if we get here

                        // We need to ensure we do not use the normal renaming
                        // rules for enumeration literals as that will normally
                        // add a Copy_1_ to the start of the name, however
                        // enumerations literals have a validation that insists
                        // they must be in upper case and not contain
                        // underscores. For this reason we have our own renaming
                        // rules for these

                        PasteAction pasteCollisionAction =
                                (eObjectInfo.objCopyType
                                        .equals(ObjectCopyType.OBJ_COPY_TYPE_ALWAYS)) ? PasteAction.CLONE
                                        : getClipboardOperationHelper()
                                                .getPasteCollisionAction(eObject
                                                        .eClass());

                        if (pasteCollisionAction == PasteAction.DISCARD) {
                            // Do not paste. Such elements are typically copied
                            // in order to find an appropriate parent
                            return false;
                        }

                        if (list.isEmpty()) {
                            return true;
                        }

                        EObject object = null;
                        Iterator<?> it = list.iterator();
                        while (it.hasNext()) {
                            object = (EObject) it.next();
                            if (ClipboardSupportUtil.hasNameCollision(object,
                                    eObject)) {
                                if (pasteCollisionAction
                                        .equals(PasteAction.ADD)) {
                                    // Create new element with different name
                                    renameEnumLit(list, eObject);
                                    return true; // insert child
                                } else if (pasteCollisionAction
                                        .equals(PasteAction.REPLACE)) {
                                    if (canBeReplaced(object)) {
                                        // Remove collision element, if any.
                                        // Create new element
                                        // in the same location.
                                        if (reference == null) {
                                            // paste target is the resouce
                                            ClipboardSupportUtil
                                                    .destroyEObjectInResource(object);
                                        } else {
                                            ClipboardSupportUtil
                                                    .destroyEObjectInCollection(object
                                                            .eContainer(),
                                                            reference,
                                                            object);
                                        }
                                        return true;
                                    }
                                    return false; // ignore it since we can't
                                                  // replace the other
                                } else if (pasteCollisionAction
                                        .equals(PasteAction.IGNORE)) {
                                    // Leave existing element, if found.
                                    // Otherwise create new
                                    // element.
                                    return false;
                                } else if (pasteCollisionAction
                                        .equals(PasteAction.MERGE)) {
                                    mergeEObjects(eObjectInfo.hasHint(ClipboardUtil.MERGE_HINT_WEAK),
                                            object,
                                            eObject,
                                            eObjectInfo);

                                    // record the existing object that we
                                    // collided with as the
                                    // pasted element, so that we will know that
                                    // the logical
                                    // paste operation succeeded
                                    setPastedElement(object);
                                    return false; // don't insert child since we
                                                  // merged it
                                } else if (pasteCollisionAction
                                        .equals(PasteAction.CLONE)) {
                                    // Always copy, even if indirectly selected.
                                    // Y.L. treat it as add???
                                    renameEnumLit(list, eObject);
                                    return true; // insert child
                                }
                            } // hasNameCollision
                        } // while
                        return true; // insert child
                    }

                    @Override
                    protected boolean handleCollision(EReference reference,
                            EObject object, EObject eObject,
                            ObjectInfo eObjectInfo) {
                        // TODO Auto-generated method stub
                        return super.handleCollision(reference,
                                object,
                                eObject,
                                eObjectInfo);
                    }
                };
            }
        }
        return null;
    }

    /**
     * By default, don't provide any copy override behaviour.
     * 
     * @return <code>null</code>, always
     */
    public OverrideCopyOperation getOverrideCopyOperation(
            CopyOperation overriddenCopyOperation) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport#
     * getExcludedCopyObjects(java.util.Set)
     */
    @SuppressWarnings("rawtypes")
    public Collection getExcludedCopyObjects(Set eObjects) {
        Set<EObject> exclude = new HashSet<EObject>();
        Set<EObject> allObjs = new HashSet<EObject>();

        // Find all descendants of the selected objects
        for (Object obj : eObjects) {
            if (obj instanceof EObject) {
                allObjs.add((EObject) obj);
            }

            TreeIterator<Object> contents =
                    EcoreUtil.getAllProperContents((EObject) obj, false);

            while (contents.hasNext()) {
                Object next = contents.next();

                if (next instanceof EObject) {
                    allObjs.add((EObject) next);
                }
            }
        }

        // Process all selected objects and their descendants
        for (EObject obj : allObjs) {
            // Ignore AssociationClass as this can have owned ends without the
            // end classes in the copied elements
            if (obj instanceof AssociationClass) {
                continue;
            }
            if (obj instanceof Class) {
                // Exclude association properties that don't have the other end
                // in the copy objects
                getPropertiesToExclude(eObjects, (Class) obj, exclude);
            } else if (obj instanceof Association) {
                /*
                 * Make sure that all end types of the association are included
                 * in the copy selection, otherwise remove the association
                 */
                EList<Type> endTypes = ((Association) obj).getEndTypes();
                if (endTypes != null) {
                    for (Type type : endTypes) {
                        if (!allObjs.contains(type)) {
                            exclude.add(obj);
                            break;
                        }
                    }
                } else {
                    // No end types found (this should not happen)
                    exclude.add(obj);
                }
            }
        }
        return exclude;
    }

    /**
     * By default, we always copy all contents of an object.
     * 
     * @return <code>true</code>
     */
    public boolean shouldSaveContainmentFeature(EObject eObj) {
        if (EcorePackage.eINSTANCE.getEClassifiers().contains(eObj.eClass())) {
            return false;
        }
        try {
            eObj.eResource().getURIFragment(eObj);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * By default, there is no post processing to be done.
     */
    @SuppressWarnings("rawtypes")
    public void performPostPasteProcessing(Set pastedEObjects) {

        // Get all contents of the pasted objects
        for (Object pastedObj : pastedEObjects) {
            // Process pasted obj
            if (visitPostPasteProcessingDelta(pastedObj)) {
                if (pastedObj instanceof EObject) {
                    // Iterate through all descendants of the object
                    TreeIterator<Object> contents =
                            EcoreUtil.getAllProperContents((EObject) pastedObj,
                                    false);
                    while (contents.hasNext()) {
                        Object next = contents.next();
                        if (next instanceof Element || next instanceof View) {
                            if (!visitPostPasteProcessingDelta(next)) {
                                // Don't continue processing children
                                contents.prune();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Find a notation view (of the given type) of the element.
     * 
     * @param general
     *            semantic model to find view of
     * @param type
     *            view type
     * @return <code>View</code> if found, <code>null</code> otherwise.
     */
    private View findView(Element general, String type) {
        if (type == null) {
            return null;
        }
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(general);
        if (wc instanceof AbstractGMFWorkingCopy) {
            List<Diagram> diagrams =
                    ((AbstractGMFWorkingCopy) wc).getDiagrams();
            if (diagrams.size() == 1) {
                TreeIterator<EObject> contents = diagrams.get(0).eAllContents();
                while (contents.hasNext()) {
                    EObject next = contents.next();
                    if (next instanceof View) {
                        View view = (View) next;
                        if ((view).getElement() == general
                                && type.equals(view.getType())) {
                            return view;
                        }
                    } else {
                        contents.prune();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Perform post paste processing on the given paste object.
     * 
     * @param obj
     *            object to process
     * @return <code>true</code> if the processing should continue with the
     *         descendants of this object, <code>false</code> if the children of
     *         this object should not be processed.
     */
    private boolean visitPostPasteProcessingDelta(Object obj) {
        boolean cont = true;

        if (obj instanceof Generalization) {
            Generalization generalization = (Generalization) obj;
            Classifier general = generalization.getGeneral();
            if (general != null) {
                if (general.eResource() == generalization.eResource()) {
                    if (findView(generalization, GENERALIZATION_VISUAL_ID) == null) {
                        destroy(generalization);
                        return false;
                    }
                } else {
                    // The generalization has been pasted across different
                    // projects, so we need to try and link the two projects
                    // together
                    if (!BOMUtils.hasSameProject(generalization, general)) {
                        // The two ends of the generalization are in different
                        // projects so need to check to see if the projects are
                        // already links of not
                        if (!BomUIUtil.checkProjectDependencies(Display
                                .getCurrent().getActiveShell(),
                                generalization,
                                general,
                                "")) {
                            // Need to clear the generalization as the user did
                            // not link the projects
                            if (findView(generalization,
                                    GENERALIZATION_VISUAL_ID) == null) {
                                destroy(generalization);
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // Check if an attribute of a class has been pasted
        if (obj instanceof Property) {
            Property prop = (Property) obj;
            // Get the details of the type that this property is
            Type type = prop.getType();
            if (type != null) {
                // Check if the type is in a different project from the
                // attribute
                if (!BOMUtils.hasSameProject(prop, type)) {
                    // Check if the dependency already exists, and if not prompt
                    // the user to add it
                    if (!BomUIUtil.checkProjectDependencies(Display
                            .getCurrent().getActiveShell(), prop, type, "")) {
                        // The user did not want to link the projects, so clear
                        // the type for the attribute - this will cause a
                        // validation error that the user will then be prompted
                        // to manually fix
                        prop.setType(null);
                        return false;
                    }
                }
            }
        }

        if (obj instanceof Element) {
            Element elem = (Element) obj;
            EList<EReference> containments =
                    elem.eClass().getEAllContainments();

            for (EReference ref : containments) {
                if (!canPaste(elem, ref)) {
                    elem.eUnset(ref);
                }
            }

        } else if (obj instanceof View) {
            /*
             * TODO: QUICKFIX: Need more generic solution for the hiding/showing
             * of the Operation compartment for Concept Model(MR35198).
             */
            View view = (View) obj;
            if (view.getType().equals(OPERATION_COMPARTMENT_VISUAL_ID)) {
                // Operations compartment
                EObject element = view.getElement();

                if (element instanceof Class
                        && !canPaste((Element) element,
                                UMLPackage.eINSTANCE.getClass_OwnedOperation())) {
                    // Operations cannot be pasted so hide
                    // compartment
                    if (view.isVisible()) {
                        view.setVisible(false);
                    }
                } else {
                    // Operations can be pasted so show compartment
                    if (!view.isVisible()) {
                        view.setVisible(true);
                    }
                }
                cont = false;
            }
        }

        return cont;
    }

    /**
     * Get properties to exclude from the given class. Association properties
     * that don't have the target class included in the copy will be excluded
     * during the copy process.
     * 
     * @param selection
     *            copy selection.
     * @param cls
     *            Class to check properties of.
     * @param exclude
     *            add the excluded objects to this collection.
     */
    private void getPropertiesToExclude(final Collection<?> selection,
            final Class cls, Set<EObject> exclude) {
        if (exclude != null && cls != null) {
            EList<Property> attrs = cls.getAllAttributes();
            if (attrs != null) {

                for (Property prop : attrs) {
                    if (prop.getAssociation() != null) {
                        Type type = prop.getType();

                        if (type != null) {
                            if (!isObjectContainedInSelection(selection, type)) {
                                exclude.add(prop);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the given object is included in the selection. This will also
     * check if the container (ancestor) of the given object is included in the
     * selection.
     * 
     * @param selection
     *            copy selection
     * @param obj
     *            object to check
     * @return <code>true</code> if contained in the selection (or ancestor
     *         contained), <code>false</code> otherwise.
     */
    private boolean isObjectContainedInSelection(Collection<?> selection,
            EObject obj) {
        boolean isContained = false;

        if (selection != null && obj != null) {
            for (Object sel : selection) {
                isContained = sel == obj;
                if (!isContained && sel instanceof Package) {
                    isContained = EcoreUtil.isAncestor((EObject) sel, obj);
                }

                if (isContained) {
                    // Object is contained
                    break;
                }
            }
        }

        return isContained;
    }

    private boolean canPaste(Element parent, EObject child) {
        boolean ret = false;

        if (parent != null && child != null) {
            EReference ref = null;
            // Find the containment feature
            EList<EReference> containments =
                    parent.eClass().getEAllContainments();
            EClass childMetaClass = child.eClass();
            if (containments != null) {
                for (EReference reference : containments) {
                    EClassifier type = reference.getEType();

                    if (type != null
                            && (type == childMetaClass || type
                                    .isInstance(child))) {
                        ref = reference;
                        break;
                    }
                }
            }

            if (ref != null) {
                ret = canPaste(parent, ref);
            }
        }

        return ret;
    }

    /**
     * Check if the given reference in the <code>Element</code> can be pasted.
     * This will check if any constraints have been applied to any
     * <code>Stereotype</code>s that may be applied to the <code>Element</code>.
     * 
     * @param semanticTarget
     * @param ref
     * @return <code>true</code> if object of the given reference can be pasted,
     *         <code>false</code> otherwise.
     */
    private boolean canPaste(Element semanticTarget, EReference ref) {
        if (semanticTarget != null && ref != null) {
            EList<Stereotype> stereotypes =
                    semanticTarget.getAppliedStereotypes();

            for (Stereotype type : stereotypes) {
                EList<Constraint> rules = type.getOwnedRules();

                for (Constraint rule : rules) {
                    EList<Element> constrainedElements =
                            rule.getConstrainedElements();

                    for (Element element : constrainedElements) {
                        if (element instanceof NamedElement
                                && ((NamedElement) element).getName()
                                        .equals(ref.getName())) {
                            ValueSpecification spec = rule.getSpecification();

                            if (spec instanceof LiteralNull
                                    || (spec instanceof LiteralInteger && ((LiteralInteger) spec)
                                            .getValue() == 0)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

}
