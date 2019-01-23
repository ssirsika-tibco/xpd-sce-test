/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Offer Set Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetOfferSetResponseType#getEntityGuid <em>Entity Guid</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetOfferSetResponseType#getEntityId <em>Entity Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOfferSetResponseType()
 * @model extendedMetaData="name='getOfferSetResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetOfferSetResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Entity Guid</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of an organization model entitiy that belongs to the offer set.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Guid</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOfferSetResponseType_EntityGuid()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='entityGuid'"
     * @generated
     */
    EList<String> getEntityGuid();

    /**
     * Returns the value of the '<em><b>Entity Id</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.organisation.api.XmlModelEntityId}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The ID (including version and type) of the entity that belongs to the offer set.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Id</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOfferSetResponseType_EntityId()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='entityId'"
     * @generated
     */
    EList<XmlModelEntityId> getEntityId();

} // GetOfferSetResponseType
