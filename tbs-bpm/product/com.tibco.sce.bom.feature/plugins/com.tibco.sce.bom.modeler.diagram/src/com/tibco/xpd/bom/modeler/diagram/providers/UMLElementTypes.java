/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;

/**
 * @generated
 */
public class UMLElementTypes extends ElementInitializers {

    /**
     * @generated
     */
    private UMLElementTypes() {
    }

    /**
     * @generated
     */
    private static Map elements;

    /**
     * @generated
     */
    private static ImageRegistry imageRegistry;

    /**
     * @generated
     */
    private static Set KNOWN_ELEMENT_TYPES;

    /**
     * @generated
     */
    public static final IElementType Package_79 = getElementType("com.tibco.xpd.bom.modeler.diagram.Package_79"); //$NON-NLS-1$
    /**
     * @generated
     */
    public static final IElementType Package_1001 = getElementType("com.tibco.xpd.bom.modeler.diagram.Package_1001"); //$NON-NLS-1$
    /**
     * @generated
     */
    public static final IElementType Class_1002 = getElementType("com.tibco.xpd.bom.modeler.diagram.Class_1002"); //$NON-NLS-1$
    /**
     * @generated
     */
    public static final IElementType PrimitiveType_1003 = getElementType("com.tibco.xpd.bom.modeler.diagram.PrimitiveType_1003"); //$NON-NLS-1$
    /**
     * @generated
     */
    public static final IElementType Enumeration_1004 = getElementType("com.tibco.xpd.bom.modeler.diagram.Enumeration_1004"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType AssociationClass_1005 = getElementType("com.tibco.xpd.bom.modeler.diagram.AssociationClass_1005"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType AssociationClass_1006 = getElementType("com.tibco.xpd.bom.modeler.diagram.AssociationClass_1006"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Package_1007 = getElementType("com.tibco.xpd.bom.modeler.diagram.Package_1007"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Property_2001 = getElementType("com.tibco.xpd.bom.modeler.diagram.Property_2001"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Operation_2002 = getElementType("com.tibco.xpd.bom.modeler.diagram.Operation_2002"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType EnumerationLiteral_2003 = getElementType("com.tibco.xpd.bom.modeler.diagram.EnumerationLiteral_2003"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Property_2004 = getElementType("com.tibco.xpd.bom.modeler.diagram.Property_2004"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Operation_2005 = getElementType("com.tibco.xpd.bom.modeler.diagram.Operation_2005"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType ProfileApplication_2006 = getElementType("com.tibco.xpd.bom.modeler.diagram.ProfileApplication_2006"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Generalization_3001 = getElementType("com.tibco.xpd.bom.modeler.diagram.Generalization_3001"); //$NON-NLS-1$
    /**
     * @generated
     */
    public static final IElementType Association_3002 = getElementType("com.tibco.xpd.bom.modeler.diagram.Association_3002"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Property_3003 = getElementType("com.tibco.xpd.bom.modeler.diagram.Property_3003"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType AssociationClass_3004 = getElementType("com.tibco.xpd.bom.modeler.diagram.AssociationClass_3004"); //$NON-NLS-1$

    /**
     * @generated
     */
    private static ImageRegistry getImageRegistry() {
        if (imageRegistry == null) {
            imageRegistry = new ImageRegistry();
        }
        return imageRegistry;
    }

    /**
     * @generated
     */
    private static String getImageRegistryKey(ENamedElement element) {
        return element.getName();
    }

    /**
     * @generated
     */
    private static ImageDescriptor getProvidedImageDescriptor(
            ENamedElement element) {
        if (element instanceof EStructuralFeature) {
            EStructuralFeature feature = ((EStructuralFeature) element);
            EClass eContainingClass = feature.getEContainingClass();
            EClassifier eType = feature.getEType();
            if (eContainingClass != null && !eContainingClass.isAbstract()) {
                element = eContainingClass;
            } else if (eType instanceof EClass
                    && !((EClass) eType).isAbstract()) {
                element = eType;
            }
        }
        if (element instanceof EClass) {
            EClass eClass = (EClass) element;
            if (!eClass.isAbstract()) {
                return BOMDiagramEditorPlugin.getInstance()
                        .getItemImageDescriptor(
                                eClass.getEPackage().getEFactoryInstance()
                                        .create(eClass));
            }
        }
        // TODO : support structural features
        return null;
    }

    /**
     * @generated
     */
    public static ImageDescriptor getImageDescriptor(ENamedElement element) {
        String key = getImageRegistryKey(element);
        ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
        if (imageDescriptor == null) {
            imageDescriptor = getProvidedImageDescriptor(element);
            if (imageDescriptor == null) {
                imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
            }
            getImageRegistry().put(key, imageDescriptor);
        }
        return imageDescriptor;
    }

    /**
     * @generated
     */
    public static Image getImage(ENamedElement element) {
        String key = getImageRegistryKey(element);
        Image image = getImageRegistry().get(key);
        if (image == null) {
            ImageDescriptor imageDescriptor = getProvidedImageDescriptor(element);
            if (imageDescriptor == null) {
                imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
            }
            getImageRegistry().put(key, imageDescriptor);
            image = getImageRegistry().get(key);
        }
        return image;
    }

    /**
     * @generated
     */
    public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
        ENamedElement element = getElement(hint);
        if (element == null) {
            return null;
        }
        return getImageDescriptor(element);
    }

    /**
     * @generated
     */
    public static Image getImage(IAdaptable hint) {
        ENamedElement element = getElement(hint);
        if (element == null) {
            return null;
        }
        return getImage(element);
    }

    /**
     * Returns 'type' of the ecore object associated with the hint.
     * 
     * @generated
     */
    public static ENamedElement getElement(IAdaptable hint) {
        Object type = hint.getAdapter(IElementType.class);
        if (elements == null) {
            elements = new IdentityHashMap();

            elements.put(Package_79, UMLPackage.eINSTANCE.getPackage());

            elements.put(Package_1001, UMLPackage.eINSTANCE.getPackage());

            elements.put(Class_1002, UMLPackage.eINSTANCE.getClass_());

            elements.put(PrimitiveType_1003, UMLPackage.eINSTANCE
                    .getPrimitiveType());

            elements.put(Enumeration_1004, UMLPackage.eINSTANCE
                    .getEnumeration());

            elements.put(AssociationClass_1005, UMLPackage.eINSTANCE
                    .getAssociationClass());

            elements.put(AssociationClass_1006, UMLPackage.eINSTANCE
                    .getAssociationClass());

            elements.put(Package_1007, UMLPackage.eINSTANCE.getPackage());

            elements.put(Property_2001, UMLPackage.eINSTANCE.getProperty());

            elements.put(Operation_2002, UMLPackage.eINSTANCE.getOperation());

            elements.put(EnumerationLiteral_2003, UMLPackage.eINSTANCE
                    .getEnumerationLiteral());

            elements.put(Property_2004, UMLPackage.eINSTANCE.getProperty());

            elements.put(Operation_2005, UMLPackage.eINSTANCE.getOperation());

            elements.put(ProfileApplication_2006, UMLPackage.eINSTANCE
                    .getProfileApplication());

            elements.put(Generalization_3001, UMLPackage.eINSTANCE
                    .getGeneralization());

            elements.put(Association_3002, UMLPackage.eINSTANCE
                    .getAssociation());

            elements.put(Property_3003, UMLPackage.eINSTANCE.getProperty());

            elements.put(AssociationClass_3004, UMLPackage.eINSTANCE
                    .getAssociationClass());
        }
        return (ENamedElement) elements.get(type);
    }

    /**
     * @generated
     */
    private static IElementType getElementType(String id) {
        return ElementTypeRegistry.getInstance().getType(id);
    }

    /**
     * @generated
     */
    public static boolean isKnownElementType(IElementType elementType) {
        if (KNOWN_ELEMENT_TYPES == null) {
            KNOWN_ELEMENT_TYPES = new HashSet();
            KNOWN_ELEMENT_TYPES.add(Package_79);
            KNOWN_ELEMENT_TYPES.add(Package_1001);
            KNOWN_ELEMENT_TYPES.add(Class_1002);
            KNOWN_ELEMENT_TYPES.add(PrimitiveType_1003);
            KNOWN_ELEMENT_TYPES.add(Enumeration_1004);
            KNOWN_ELEMENT_TYPES.add(AssociationClass_1005);
            KNOWN_ELEMENT_TYPES.add(AssociationClass_1006);
            KNOWN_ELEMENT_TYPES.add(Package_1007);
            KNOWN_ELEMENT_TYPES.add(Property_2001);
            KNOWN_ELEMENT_TYPES.add(Operation_2002);
            KNOWN_ELEMENT_TYPES.add(EnumerationLiteral_2003);
            KNOWN_ELEMENT_TYPES.add(Property_2004);
            KNOWN_ELEMENT_TYPES.add(Operation_2005);
            KNOWN_ELEMENT_TYPES.add(ProfileApplication_2006);
            KNOWN_ELEMENT_TYPES.add(Generalization_3001);
            KNOWN_ELEMENT_TYPES.add(Association_3002);
            KNOWN_ELEMENT_TYPES.add(Property_3003);
            KNOWN_ELEMENT_TYPES.add(AssociationClass_3004);
        }
        return KNOWN_ELEMENT_TYPES.contains(elementType);
    }

}
