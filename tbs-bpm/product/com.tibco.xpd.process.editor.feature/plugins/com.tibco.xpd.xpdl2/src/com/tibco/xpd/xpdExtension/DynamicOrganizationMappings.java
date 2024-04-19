/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Organization Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * DynamicOrganizationMappings specify the mappings from the 'Dynamic Organization Identifier' to the dynamic participants. They are defined in the  'Work Resource' property tab of business process. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMappings#getDynamicOrganizationMapping <em>Dynamic Organization Mapping</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrganizationMappings()
 * @model extendedMetaData="name='DynamicOrganizationMappings' kind='empty'"
 * @generated
 */
public interface DynamicOrganizationMappings extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Dynamic Organization Mapping</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dynamic Organization Mapping</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dynamic Organization Mapping</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrganizationMappings_DynamicOrganizationMapping()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='DynamicOrganizationMapping' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<DynamicOrganizationMapping> getDynamicOrganizationMapping();

} // DynamicOrganizationMappings
