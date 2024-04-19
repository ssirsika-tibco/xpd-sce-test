/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExternalReference;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Access Operations Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCaseClassExternalReference <em>Case Class External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCreate <em>Create</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCaseIdentifier <em>Delete By Case Identifier</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCompositeIdentifiers <em>Delete By Composite Identifiers</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseAccessOperationsType()
 * @model extendedMetaData="name='CaseAccessOperations_._type' kind='elementOnly'"
 * @generated
 */
public interface CaseAccessOperationsType extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Case Class External Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Standard xpdl2 ExternalReference to CaseClass concerned.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Case Class External Reference</em>' containment reference.
	 * @see #setCaseClassExternalReference(ExternalReference)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseAccessOperationsType_CaseClassExternalReference()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='CaseClassExternalReference' namespace='##targetNamespace'"
	 * @generated
	 */
	ExternalReference getCaseClassExternalReference();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCaseClassExternalReference <em>Case Class External Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Case Class External Reference</em>' containment reference.
	 * @see #getCaseClassExternalReference()
	 * @generated
	 */
	void setCaseClassExternalReference(ExternalReference value);

	/**
	 * Returns the value of the '<em><b>Create</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Operation to create new case object from given local class field and assign return field to a refernece to it. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Create</em>' containment reference.
	 * @see #setCreate(CreateCaseOperationType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseAccessOperationsType_Create()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Create' namespace='##targetNamespace'"
	 * @generated
	 */
	CreateCaseOperationType getCreate();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCreate <em>Create</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create</em>' containment reference.
	 * @see #getCreate()
	 * @generated
	 */
	void setCreate(CreateCaseOperationType value);

	/**
	 * Returns the value of the '<em><b>Delete By Case Identifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Operation to delete an existing case object whose named case identifier is equal to the value of the provided field value
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delete By Case Identifier</em>' containment reference.
	 * @see #setDeleteByCaseIdentifier(DeleteByCaseIdentifierType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseAccessOperationsType_DeleteByCaseIdentifier()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='DeleteByCaseIdentifier' namespace='##targetNamespace'"
	 * @generated
	 */
	DeleteByCaseIdentifierType getDeleteByCaseIdentifier();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCaseIdentifier <em>Delete By Case Identifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete By Case Identifier</em>' containment reference.
	 * @see #getDeleteByCaseIdentifier()
	 * @generated
	 */
	void setDeleteByCaseIdentifier(DeleteByCaseIdentifierType value);

	/**
	 * Returns the value of the '<em><b>Delete By Composite Identifiers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Operation to delete an existing case object whose composite identfiers match the values of the Field that each is paired with.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delete By Composite Identifiers</em>' containment reference.
	 * @see #setDeleteByCompositeIdentifiers(DeleteByCompositeIdentifiersType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseAccessOperationsType_DeleteByCompositeIdentifiers()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='DeleteByCompositeIdentifiers' namespace='##targetNamespace'"
	 * @generated
	 */
	DeleteByCompositeIdentifiersType getDeleteByCompositeIdentifiers();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCompositeIdentifiers <em>Delete By Composite Identifiers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete By Composite Identifiers</em>' containment reference.
	 * @see #getDeleteByCompositeIdentifiers()
	 * @generated
	 */
	void setDeleteByCompositeIdentifiers(DeleteByCompositeIdentifiersType value);

} // CaseAccessOperationsType
