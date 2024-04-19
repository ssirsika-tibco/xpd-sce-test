/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Add Link Associations Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAddCaseRefField <em>Add Case Ref Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAssociationName <em>Association Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAddLinkAssociationsType()
 * @model extendedMetaData="name='AddLinkAssociations_._type' kind='empty'"
 * @generated
 */
public interface AddLinkAssociationsType extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Add Case Ref Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Single instance or Array field containing ref(s) to add to the given association (array field is invalid if association multipleicty=1).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Add Case Ref Field</em>' attribute.
	 * @see #setAddCaseRefField(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAddLinkAssociationsType_AddCaseRefField()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='AddCaseRefField'"
	 * @generated
	 */
	String getAddCaseRefField();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAddCaseRefField <em>Add Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Add Case Ref Field</em>' attribute.
	 * @see #getAddCaseRefField()
	 * @generated
	 */
	void setAddCaseRefField(String value);

	/**
	 * Returns the value of the '<em><b>Association Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Name</em>' attribute.
	 * @see #setAssociationName(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAddLinkAssociationsType_AssociationName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='AssociationName'"
	 * @generated
	 */
	String getAssociationName();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAssociationName <em>Association Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Association Name</em>' attribute.
	 * @see #getAssociationName()
	 * @generated
	 */
	void setAssociationName(String value);

} // AddLinkAssociationsType
