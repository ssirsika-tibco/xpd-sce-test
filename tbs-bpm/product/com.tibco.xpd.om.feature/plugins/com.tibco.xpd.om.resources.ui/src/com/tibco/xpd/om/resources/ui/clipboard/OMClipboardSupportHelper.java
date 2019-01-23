/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.clipboard;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardSupportUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.CopyOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverrideCopyOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteAction;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteOption;
import org.eclipse.gmf.runtime.emf.core.clipboard.AbstractClipboardSupport;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.providers.internal.copypaste.ConnectorViewPasteOperation;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Clipboard support for the OM. (This is used directly for copy/paste rather
 * than go through the clipboard support framework due to current limitations in
 * the framework.)
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("restriction")
public class OMClipboardSupportHelper extends AbstractClipboardSupport {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport#destroy(
     * org.eclipse.emf.ecore.EObject)
     */
    @Override
    public void destroy(EObject eObject) {
        DestroyElementCommand.destroy(eObject);
    }

    /**
     * Get the paste collision action.
     * 
     * @return the {@link PasteAction#ADD}action, always
     */
    @Override
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
    @Override
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
    @Override
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
     * Provides a paste operation for notation models.
     */
    @Override
    public boolean shouldOverrideChildPasteOperation(EObject parentElement,
            EObject childEObject) {

        if (childEObject instanceof EAnnotation) {
            return getTypeResourceURI((EAnnotation) childEObject) != null;
        }

        return (NotationPackage.eINSTANCE == childEObject.eClass()
                .getEPackage()) || childEObject instanceof OrgElementType;
    }

    /**
     * By default, don't provide any copy override behaviour.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean shouldOverrideCopyOperation(Collection eObjects, Map hintMap) {
        return false;
    }

    private String getTypeResourceURI(EAnnotation annot) {
        if (OMClipboardHelper.ANNOT_TYPERESOURCE.equals(annot.getSource())) {
            EList<EObject> contents = annot.getContents();
            if (contents != null && !contents.isEmpty()) {
                EObject eo = contents.get(0);
                if (eo instanceof EAnnotation) {
                    annot = (EAnnotation) eo;
                    if (annot.getSource() != null) {
                        return annot.getSource();
                    }
                }
            }
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

        // If this an EAnnotation then return true
        if (eObject instanceof EAnnotation) {
            return getTypeResourceURI((EAnnotation) eObject) != null;
        }

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
            EObject sematicDiagramElement = ((View) parentEObject).getElement();
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

            EObject semanticParent = ((View) parentEObject).getElement();
            if (semanticParent != null) {
                return shouldAllowPaste(semanticParent, semanticChildElement);
            }
        } else if (eObject != null && parentEObject != null) {
            // Get semantic elements
            if (eObject instanceof View) {
                eObject = ((View) eObject).getElement();
            }
            if (parentEObject instanceof View) {
                parentEObject = ((View) parentEObject).getElement();
            }
            return shouldAllowPaste(parentEObject, eObject);
        }
        return false;
    }

    /**
     * Check if given element can be pasted in the parent at semantic level.
     * 
     * @param semanticParent
     * @param semanticElement
     * @return
     */
    private boolean shouldAllowPaste(EObject semanticParent,
            EObject semanticElement) {
        if (semanticParent != null && semanticElement != null) {
            EList<EReference> containments =
                    semanticParent.eClass().getEAllContainments();
            EClass childClass = semanticElement.eClass();
            if (containments != null && !containments.isEmpty()
                    && childClass != null) {
                for (EReference ref : containments) {
                    /*
                     * XPD-7211: Adding 'childClass.getESuperTypes()
                     * .contains(ref.getEType()' check over here as there might
                     * be scenario where the Containtment is the super
                     * type/super class of the element. e.g In case of
                     * Organization the Containtment are 'OrgUnit',
                     * DynamicOrgIdentifiers etc etc... so Organization does not
                     * have DynamicOrgUnit as its Containtment however
                     * 'DynamicOrgUnit' is a sub type of 'OrgUnit' and hence
                     * copy paste of Dynamic Org unit to Organization did not
                     * work.
                     */
                    if (childClass == ref.getEType()
                            || childClass.getESuperTypes()
                                    .contains(ref.getEType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport#
     * getOverrideChildPasteOperation
     * (org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation)
     */
    @Override
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
            } else if (eObject instanceof EAnnotation) {
                String typeResourceURI =
                        getTypeResourceURI((EAnnotation) eObject);
                if (typeResourceURI != null
                        && parentEObject.eResource() != null) {
                    if (!typeResourceURI.equals(parentEObject.eResource()
                            .getURI().toString())) {
                        return new OverridePasteChildOperation(
                                overriddenChildPasteOperation) {
                            @Override
                            public void paste() throws Exception {
                                final Display display =
                                        XpdResourcesPlugin.getStandardDisplay();
                                display.syncExec(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean doPaste =
                                                OMClipboardHelper
                                                        .shouldContinuePasteIntoExternalResource(display
                                                                .getActiveShell());

                                        if (!doPaste) {
                                            throwCancelException();
                                        }
                                    }
                                });
                            }
                        };
                    }
                }
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
    @Override
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
    @Override
    @SuppressWarnings("unchecked")
    public Collection getExcludedCopyObjects(Set eObjects) {
        return new HashSet<EObject>();
    }

    /**
     * By default, we always copy all contents of an object.
     * 
     * @return <code>true</code>
     */
    @Override
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
    @Override
    @SuppressWarnings("unchecked")
    public void performPostPasteProcessing(Set pastedEObjects) {
        /*
         * If OrgUnit or Position with a feature set in being pasted then check
         * if the parent has the right type set - if not then clear the features
         * from the pasted elements.
         */
        /*
         * if typed element being pasted then remove type if it is from another
         * resource
         */
        if (pastedEObjects != null) {
            for (Object obj : pastedEObjects) {
                if (obj instanceof OrgUnit) {
                    OrgUnit unit = (OrgUnit) obj;
                    OrgUnitFeature feature = unit.getFeature();
                    if (feature != null && !feature.eIsProxy()) {
                        EObject featureContainer = feature.eContainer();
                        EObject container =
                                unit.getParentOrgUnit() != null ? unit
                                        .getParentOrgUnit() : unit.eContainer();
                        if (featureContainer != null
                                && container instanceof OrgTypedElement) {
                            OrgElementType type =
                                    ((OrgTypedElement) container).getType();
                            if (type == null || type != null
                                    && !type.equals(featureContainer)) {
                                resetFeature(unit);
                            }
                        } else {
                            resetFeature(unit);
                        }
                    } else if (feature != null) {
                        // Is proxy so maybe unit is being pasted in another
                        // resource
                        resetFeature(unit);
                    }

                    if (obj instanceof DynamicOrgUnit) {
                        /*
                         * XPD-7211: Post paste the references to dynamic
                         * orginations were broken, hence we need to handle this
                         * special case.
                         */
                        fixReferencesToDynamicOrgPostPaste((EObject) obj);
                    }

                } else if (obj instanceof Position) {
                    Position position = (Position) obj;
                    PositionFeature feature = position.getFeature();
                    if (feature != null && !feature.eIsProxy()) {
                        EObject featureContainer = feature.eContainer();

                        if (featureContainer != null
                                && position.eContainer() instanceof OrgTypedElement) {
                            OrgElementType type =
                                    ((OrgTypedElement) position.eContainer())
                                            .getType();
                            if (type == null || type != null
                                    && !type.equals(featureContainer)) {
                                resetFeature(position);
                            }
                        } else {
                            resetFeature(position);
                        }
                    } else if (feature != null) {
                        // Is proxy so maybe position is being pasted in another
                        // resource
                        resetFeature(position);
                    }

                } else if (obj instanceof OrgTypedElement) {
                    resetTypes((OrgTypedElement) obj);

                    if ((obj instanceof Organization)
                            && (((Organization) obj).eContainer() instanceof OrgModel)) {
                        /*
                         * XPD-7211: Post paste the references to dynamic
                         * orginations were broken, hence we need to handle this
                         * special case.
                         */
                        fixReferencesToDynamicOrgPostPaste((EObject) obj);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param omEntity
     *            the om entity
     * @return the Parent OrgModel of the passed omEntity else if not found
     *         return <code>null</code>
     */
    private OrgModel getParentOrgModel(EObject omEntity) {
        if (omEntity != null) {
            if (omEntity instanceof OrgModel) {
                return (OrgModel) omEntity;
            } else {
                return getParentOrgModel(omEntity.eContainer());
            }
        }
        return null;
    }

    /**
     * Checks if the pasted Organization has any Dynamic Org units in it, if yes
     * then creates a new 'DynamicOrgReference' and adds it to the OrgModel and
     * updates the references in DynamicOrgUnit.
     * 
     * @param organization
     *            the Organization which has been pasted
     * @param orgModel
     *            the parent model.
     */
    private void fixReferencesToDynamicOrgPostPaste(EObject pastedObject) {

        OrgModel parentOrgModel = getParentOrgModel(pastedObject);

        if (parentOrgModel != null) {
            /*
             * note: we dont need commands over here as this is already done in
             * a transaction.
             */
            if (pastedObject instanceof Organization) {

                EList<OrgUnit> units = ((Organization) pastedObject).getUnits();

                for (OrgUnit orgUnit : units) {

                    if (orgUnit instanceof DynamicOrgUnit) {

                        DynamicOrgReference createDynamicOrgReference =
                                getCreateDynamicOrgReference((DynamicOrgUnit) orgUnit);

                        /*
                         * add the Dynamic Org Reference to the OrgModel
                         */
                        if (createDynamicOrgReference != null) {
                            parentOrgModel.getDynamicOrgReferences()
                                    .add(createDynamicOrgReference);
                        }

                    }
                }
            } else if (pastedObject instanceof DynamicOrgUnit) {
                DynamicOrgReference createDynamicOrgReference =
                        getCreateDynamicOrgReference((DynamicOrgUnit) pastedObject);

                /*
                 * add the Dynamic Org Reference to the OrgModel
                 */
                if (createDynamicOrgReference != null) {
                    parentOrgModel.getDynamicOrgReferences()
                            .add(createDynamicOrgReference);
                }
            }
        }
    }

    /**
     * 
     * @param dynamicOrgUnit
     *            the Dynamic Org Unit that is copied
     * @return the new Created SynamicOrgReference
     */
    private DynamicOrgReference getCreateDynamicOrgReference(
            DynamicOrgUnit dynamicOrgUnit) {
        DynamicOrgReference createDynamicOrgReference = null;
        /*
         * get the Dynamic Org Reference from the Dynamic Org unit.
         */
        DynamicOrgReference dynamicOrganization =
                dynamicOrgUnit.getDynamicOrganization();

        if (dynamicOrganization != null) {
            Organization toOrg = dynamicOrganization.getTo();
            /*
             * Create new 'DynamicOrgReference' as we would want to add new
             * Dynamic Reference.
             */
            createDynamicOrgReference =
                    OMFactory.eINSTANCE.createDynamicOrgReference();

            createDynamicOrgReference.setTo(toOrg);
            createDynamicOrgReference.setFrom(dynamicOrgUnit);
            /*
             * update the dynamic org unit reference
             */
            dynamicOrgUnit.setDynamicOrganization(createDynamicOrgReference);
        }

        return createDynamicOrgReference;
    }

    /**
     * Reset the types of all {@link OrgTypedElement} pasted into a resource
     * from another resource.
     * 
     * @param typedElem
     */
    private void resetTypes(EObject elem) {
        if (elem != null) {
            if (elem instanceof OrgTypedElement) {
                OrgTypedElement typedElem = (OrgTypedElement) elem;
                OrgElementType type = typedElem.getType();
                if (type != null) {
                    if (type.eIsProxy() || type.eResource() == null
                            || type.eResource() != typedElem.eResource()) {
                        if (typedElem instanceof OrgUnit) {
                            resetFeature((OrgUnit) typedElem);
                        } else if (typedElem instanceof Position) {
                            resetFeature((Position) typedElem);
                        } else {
                            typedElem.setType(null);
                        }
                    }
                }
            }

            TreeIterator<EObject> contents = elem.eAllContents();
            if (contents != null) {
                while (contents.hasNext()) {
                    Object next = contents.next();
                    if (next instanceof EObject) {
                        resetTypes((EObject) next);
                    }
                }
            }
        }
    }

    /**
     * Reset the feature of the given {@link OrgUnit}. This will also reset
     * features of its children.
     * 
     * @param unit
     */
    private void resetFeature(OrgUnit unit) {
        if (unit != null) {
            unit.setFeature(null);
            EList<Position> positions = unit.getPositions();
            if (positions != null) {
                for (Position position : positions) {
                    resetFeature(position);
                }
            }
            EList<OrgUnit> units = unit.getSubUnits();
            if (units != null) {
                for (OrgUnit orgUnit : units) {
                    resetFeature(orgUnit);
                }
            }
        }
    }

    /**
     * Reset the feature of the given {@link Position}.
     * 
     * @param position
     */
    private void resetFeature(Position position) {
        if (position != null) {
            position.setFeature(null);
        }
    }

    @Override
    public void setName(EObject object, String name) {
        // Update the display name
        if (object instanceof NamedElement) {
            ((NamedElement) object).setDisplayName(name);
            /*
             * XPD-3147: If the name is not set then a stack overflow can occur
             * during copy-paste. This is because the name of the new element
             * being pasted does not change and the clipboard api will detect
             * this as a name collision and will continue calling this method to
             * change the name.
             */
            ((NamedElement) object).setName(name);
        } else {
            super.setName(object, name);
        }
    }
}
