/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Doc Ref Operations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getMoveCaseDocOperation <em>Move Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getUnlinkCaseDocOperation <em>Unlink Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getLinkCaseDocOperation <em>Link Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getDeleteCaseDocOperation <em>Delete Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getCaseDocumentRefField <em>Case Document Ref Field</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocRefOperations()
 * @model extendedMetaData="name='CaseDocRefOperations' kind='elementOnly'"
 * @generated
 */
public interface CaseDocRefOperations extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Move Case Doc Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Operation to move a Document existing in ECM , from one Case Document Folder to another Case Document Folder. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Move Case Doc Operation</em>' containment reference.
	 * @see #setMoveCaseDocOperation(MoveCaseDocOperation)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocRefOperations_MoveCaseDocOperation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='MoveCaseDocOperation' namespace='##targetNamespace'"
	 * @generated
	 */
	MoveCaseDocOperation getMoveCaseDocOperation();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getMoveCaseDocOperation <em>Move Case Doc Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Move Case Doc Operation</em>' containment reference.
	 * @see #getMoveCaseDocOperation()
	 * @generated
	 */
	void setMoveCaseDocOperation(MoveCaseDocOperation value);

	/**
	 * Returns the value of the '<em><b>Unlink Case Doc Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Operation to unlink a Document from a Case.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unlink Case Doc Operation</em>' containment reference.
	 * @see #setUnlinkCaseDocOperation(UnlinkCaseDocOperation)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocRefOperations_UnlinkCaseDocOperation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='UnlinkCaseDocOperation' namespace='##targetNamespace'"
	 * @generated
	 */
	UnlinkCaseDocOperation getUnlinkCaseDocOperation();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getUnlinkCaseDocOperation <em>Unlink Case Doc Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unlink Case Doc Operation</em>' containment reference.
	 * @see #getUnlinkCaseDocOperation()
	 * @generated
	 */
	void setUnlinkCaseDocOperation(UnlinkCaseDocOperation value);

	/**
	 * Returns the value of the '<em><b>Link Case Doc Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Operation to link a Document to a Case Object.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Link Case Doc Operation</em>' containment reference.
	 * @see #setLinkCaseDocOperation(LinkCaseDocOperation)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocRefOperations_LinkCaseDocOperation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='LinkCaseDocOperation' namespace='##targetNamespace'"
	 * @generated
	 */
	LinkCaseDocOperation getLinkCaseDocOperation();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getLinkCaseDocOperation <em>Link Case Doc Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link Case Doc Operation</em>' containment reference.
	 * @see #getLinkCaseDocOperation()
	 * @generated
	 */
	void setLinkCaseDocOperation(LinkCaseDocOperation value);

	/**
	 * Returns the value of the '<em><b>Delete Case Doc Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Operation to delete an existing Case Document identified by the Document Reference.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delete Case Doc Operation</em>' containment reference.
	 * @see #setDeleteCaseDocOperation(DeleteCaseDocOperation)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocRefOperations_DeleteCaseDocOperation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='DeleteCaseDocOperation' namespace='##targetNamespace'"
	 * @generated
	 */
	DeleteCaseDocOperation getDeleteCaseDocOperation();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getDeleteCaseDocOperation <em>Delete Case Doc Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Case Doc Operation</em>' containment reference.
	 * @see #getDeleteCaseDocOperation()
	 * @generated
	 */
	void setDeleteCaseDocOperation(DeleteCaseDocOperation value);

	/**
	 * Returns the value of the '<em><b>Case Document Ref Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Document Reference identifying the Case Document for the operation.Type String.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Case Document Ref Field</em>' attribute.
	 * @see #setCaseDocumentRefField(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocRefOperations_CaseDocumentRefField()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='CaseDocumentRefField'"
	 * @generated
	 */
	String getCaseDocumentRefField();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getCaseDocumentRefField <em>Case Document Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Case Document Ref Field</em>' attribute.
	 * @see #getCaseDocumentRefField()
	 * @generated
	 */
	void setCaseDocumentRefField(String value);

} // CaseDocRefOperations
