/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Mapper Array Inflation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getMappingType <em>Mapping Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getContributorId <em>Contributor Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataMapperArrayInflation()
 * @model extendedMetaData="name='DataMapperArrayInflation' kind='empty'"
 * @generated
 */
public interface DataMapperArrayInflation extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Stores path of the target array to be inflated from a source array.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataMapperArrayInflation_Path()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Mapping Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.DataMapperArrayInflationType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Array inflation mapping type attribute is used to create the target array when inflating from source array. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mapping Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflationType
	 * @see #isSetMappingType()
	 * @see #unsetMappingType()
	 * @see #setMappingType(DataMapperArrayInflationType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataMapperArrayInflation_MappingType()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='MappingType'"
	 * @generated
	 */
	DataMapperArrayInflationType getMappingType();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getMappingType <em>Mapping Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflationType
	 * @see #isSetMappingType()
	 * @see #unsetMappingType()
	 * @see #getMappingType()
	 * @generated
	 */
	void setMappingType(DataMapperArrayInflationType value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getMappingType <em>Mapping Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMappingType()
	 * @see #getMappingType()
	 * @see #setMappingType(DataMapperArrayInflationType)
	 * @generated
	 */
	void unsetMappingType();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getMappingType <em>Mapping Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Mapping Type</em>' attribute is set.
	 * @see #unsetMappingType()
	 * @see #getMappingType()
	 * @see #setMappingType(DataMapperArrayInflationType)
	 * @generated
	 */
	boolean isSetMappingType();

	/**
	 * Returns the value of the '<em><b>Contributor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Stores id of the contributor for the path.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Contributor Id</em>' attribute.
	 * @see #setContributorId(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataMapperArrayInflation_ContributorId()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='ContributorId'"
	 * @generated
	 */
	String getContributorId();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getContributorId <em>Contributor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contributor Id</em>' attribute.
	 * @see #getContributorId()
	 * @generated
	 */
	void setContributorId(String value);

} // DataMapperArrayInflation
