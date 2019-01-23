package com.tibco.xpd.om.modeler.diagram.providers;

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

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;

/**
 * @generated
 */
public class OrganizationModelElementTypes extends ElementInitializers {

	/**
	 * @generated
	 */
	private OrganizationModelElementTypes() {
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
	public static final IElementType OrgModel_79 = getElementType("com.tibco.xpd.om.modeler.diagram.OrgModel_79"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Organization_1001 = getElementType("com.tibco.xpd.om.modeler.diagram.Organization_1001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType OrgUnit_2001 = getElementType("com.tibco.xpd.om.modeler.diagram.OrgUnit_2001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Position_2002 = getElementType("com.tibco.xpd.om.modeler.diagram.Position_2002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType DynamicOrgUnit_2003 = getElementType("com.tibco.xpd.om.modeler.diagram.DynamicOrgUnit_2003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType OrgUnitRelationship_3001 = getElementType("com.tibco.xpd.om.modeler.diagram.OrgUnitRelationship_3001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType DynamicOrgReference_3002 = getElementType("com.tibco.xpd.om.modeler.diagram.DynamicOrgReference_3002"); //$NON-NLS-1$

	/*
	 * Custom types
	 */
	public static final IElementType Group_2050 = getElementType("com.tibco.xpd.om.modeler.diagram.Group_2050"); //$NON-NLS-1$

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
				return OrganizationModelDiagramEditorPlugin.getInstance()
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

	public static ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap();

			elements.put(OrgModel_79, OMPackage.eINSTANCE.getOrgModel());

			elements.put(Organization_1001, OMPackage.eINSTANCE
					.getOrganization());

			elements.put(OrgUnit_2001, OMPackage.eINSTANCE.getOrgUnit());

			elements.put(Position_2002, OMPackage.eINSTANCE.getPosition());

			elements.put(OrgUnitRelationship_3001, OMPackage.eINSTANCE
					.getOrgUnitRelationship());

			// Custom type
			elements.put(Group_2050, OMPackage.eINSTANCE.getGroup());

			// XPD-5300: Addition of dynamic element
			elements.put(DynamicOrgUnit_2003, OMPackage.eINSTANCE
					.getDynamicOrgUnit());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElementGen(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap();

			elements.put(OrgModel_79, OMPackage.eINSTANCE.getOrgModel());

			elements.put(Organization_1001, OMPackage.eINSTANCE
					.getOrganization());

			elements.put(OrgUnit_2001, OMPackage.eINSTANCE.getOrgUnit());

			elements.put(Position_2002, OMPackage.eINSTANCE.getPosition());

			elements.put(DynamicOrgUnit_2003, OMPackage.eINSTANCE
					.getDynamicOrgUnit());

			elements.put(OrgUnitRelationship_3001, OMPackage.eINSTANCE
					.getOrgUnitRelationship());

			elements.put(DynamicOrgReference_3002, OMPackage.eINSTANCE
					.getDynamicOrgReference());
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
			KNOWN_ELEMENT_TYPES.add(OrgModel_79);
			KNOWN_ELEMENT_TYPES.add(Organization_1001);
			KNOWN_ELEMENT_TYPES.add(OrgUnit_2001);
			KNOWN_ELEMENT_TYPES.add(Position_2002);
			KNOWN_ELEMENT_TYPES.add(DynamicOrgUnit_2003);
			KNOWN_ELEMENT_TYPES.add(OrgUnitRelationship_3001);
			KNOWN_ELEMENT_TYPES.add(DynamicOrgReference_3002);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

}
