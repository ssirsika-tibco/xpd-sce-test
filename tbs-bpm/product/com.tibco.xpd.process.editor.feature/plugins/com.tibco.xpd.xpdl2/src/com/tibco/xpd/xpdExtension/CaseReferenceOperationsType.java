/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Reference Operations Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getCaseRefField <em>Case Ref Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getUpdate <em>Update</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getDelete <em>Delete</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getAddLinkAssociations <em>Add Link Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getRemoveLinkAssociations <em>Remove Link Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getRemoveAllLinksByName <em>Remove All Links By Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseReferenceOperationsType()
 * @model extendedMetaData="name='CaseReferenceOperations_._type' kind='elementOnly'"
 * @generated
 */
public interface CaseReferenceOperationsType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The case reference  field or array field name
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Ref Field</em>' attribute.
     * @see #setCaseRefField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseReferenceOperationsType_CaseRefField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='CaseRefField' namespace='##targetNamespace'"
     * @generated
     */
    String getCaseRefField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getCaseRefField <em>Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Ref Field</em>' attribute.
     * @see #getCaseRefField()
     * @generated
     */
    void setCaseRefField(String value);

    /**
     * Returns the value of the '<em><b>Update</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operation to update the referenced case object with the given local class data.
     * 
     * IT IS IMPLIED that the return CaseRefField IS THE source CaseRefFielld AND THEREFORE CaseRefField will be updated to the new version automatically if the task succeeds.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Update</em>' containment reference.
     * @see #setUpdate(UpdateCaseOperationType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseReferenceOperationsType_Update()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Update' namespace='##targetNamespace'"
     * @generated
     */
    UpdateCaseOperationType getUpdate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getUpdate <em>Update</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Update</em>' containment reference.
     * @see #getUpdate()
     * @generated
     */
    void setUpdate(UpdateCaseOperationType value);

    /**
     * Returns the value of the '<em><b>Delete</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * No further properties are required by the delete operation as everything can be derived from the CaseRefField (including CAC class for the cac.delete(arrayOfRefs) that will be needed for a case reference array.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delete</em>' containment reference.
     * @see #setDelete(DeleteCaseReferenceOperationType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseReferenceOperationsType_Delete()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Delete' namespace='##targetNamespace'"
     * @generated
     */
    DeleteCaseReferenceOperationType getDelete();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getDelete <em>Delete</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete</em>' containment reference.
     * @see #getDelete()
     * @generated
     */
    void setDelete(DeleteCaseReferenceOperationType value);

    /**
     * Returns the value of the '<em><b>Add Link Associations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operation to add link(s) (refs) to other case object(s) for a given case-class to case-class association (within the case object identifier by CaseRefField)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Add Link Associations</em>' containment reference.
     * @see #setAddLinkAssociations(AddLinkAssociationsType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseReferenceOperationsType_AddLinkAssociations()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='AddLinkAssociations' namespace='##targetNamespace'"
     * @generated
     */
    AddLinkAssociationsType getAddLinkAssociations();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getAddLinkAssociations <em>Add Link Associations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Add Link Associations</em>' containment reference.
     * @see #getAddLinkAssociations()
     * @generated
     */
    void setAddLinkAssociations(AddLinkAssociationsType value);

    /**
     * Returns the value of the '<em><b>Remove Link Associations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operation to remove link(s) (refs) to other case object(s) for a given case-class to case-class association (within the case object identified by CaseRefField)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Remove Link Associations</em>' containment reference.
     * @see #setRemoveLinkAssociations(RemoveLinkAssociationsType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseReferenceOperationsType_RemoveLinkAssociations()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='RemoveLinkAssociations' namespace='##targetNamespace'"
     * @generated
     */
    RemoveLinkAssociationsType getRemoveLinkAssociations();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getRemoveLinkAssociations <em>Remove Link Associations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Remove Link Associations</em>' containment reference.
     * @see #getRemoveLinkAssociations()
     * @generated
     */
    void setRemoveLinkAssociations(RemoveLinkAssociationsType value);

    /**
     * Returns the value of the '<em><b>Remove All Links By Name</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operation to remove all links (refs) to other case object(s) for a given case-class association (within the case object identified by CaseRefField)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Remove All Links By Name</em>' containment reference.
     * @see #setRemoveAllLinksByName(RemoveAllLinksByNameType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseReferenceOperationsType_RemoveAllLinksByName()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='RemoveAllLinksByName' namespace='##targetNamespace'"
     * @generated
     */
    RemoveAllLinksByNameType getRemoveAllLinksByName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getRemoveAllLinksByName <em>Remove All Links By Name</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Remove All Links By Name</em>' containment reference.
     * @see #getRemoveAllLinksByName()
     * @generated
     */
    void setRemoveAllLinksByName(RemoveAllLinksByNameType value);

} // CaseReferenceOperationsType
