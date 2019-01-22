package com.tibco.xpd.om.modeler.diagram.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.OMPackage;

/**
 * @generated
 */
public class OrganizationModelDomainModelElementTester extends PropertyTester {

	/**
	 * @generated
	 */
	public boolean test(Object receiver, String method, Object[] args,
			Object expectedValue) {
		if (false == receiver instanceof EObject) {
			return false;
		}
		EObject eObject = (EObject) receiver;
		EClass eClass = eObject.eClass();
		if (eClass == OMPackage.eINSTANCE.getOrgUnit()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getDynamicOrgUnit()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getDynamicOrgReference()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getPosition()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getLocation()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgNode()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgElement()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getCapability()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getGroup()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrganization()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getDynamicOrgIdentifier()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getAttributeValue()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getAttribute()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrganizationType()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgUnitType()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getPositionType()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgUnitFeature()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getMultipleFeature()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getPositionFeature()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgElementType()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgUnitRelationship()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgUnitRelationshipType()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getCapabilityCategory()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getLocationType()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getNamedElement()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgModel()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getEnumValue()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getPrivilege()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getQualifiedOrgElement()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getAuthorizable()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getFeature()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgTypedElement()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getNamespace()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getCapabilityAssociation()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgMetaModel()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getBaseOrgModel()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getModelElement()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getPrivilegeAssociation()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getCapable()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getQualifiedAssociation()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getPrivilegeCategory()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getAllocable()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getResourceType()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getResource()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getResourceAssociation()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getAssociableWithResources()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getOrgQuery()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getLocatable()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getSystemAction()) {
			return true;
		}
		if (eClass == OMPackage.eINSTANCE.getAssociableWithPrivileges()) {
			return true;
		}
		return false;
	}

}
