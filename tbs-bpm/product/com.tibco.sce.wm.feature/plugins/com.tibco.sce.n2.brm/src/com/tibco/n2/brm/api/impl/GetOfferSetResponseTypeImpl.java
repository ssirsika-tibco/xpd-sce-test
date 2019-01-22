/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetOfferSetResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Offer Set Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetOfferSetResponseTypeImpl#getEntityGuid <em>Entity Guid</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetOfferSetResponseTypeImpl#getEntityId <em>Entity Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetOfferSetResponseTypeImpl extends EObjectImpl implements GetOfferSetResponseType {
    /**
     * The cached value of the '{@link #getEntityGuid() <em>Entity Guid</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityGuid()
     * @generated
     * @ordered
     */
    protected EList<String> entityGuid;

    /**
     * The cached value of the '{@link #getEntityId() <em>Entity Id</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityId()
     * @generated
     * @ordered
     */
    protected EList<XmlModelEntityId> entityId;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetOfferSetResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_OFFER_SET_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getEntityGuid() {
        if (entityGuid == null) {
            entityGuid = new EDataTypeEList<String>(String.class, this, N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID);
        }
        return entityGuid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<XmlModelEntityId> getEntityId() {
        if (entityId == null) {
            entityId = new EObjectContainmentEList<XmlModelEntityId>(XmlModelEntityId.class, this, N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID);
        }
        return entityId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID:
                return ((InternalEList<?>)getEntityId()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID:
                return getEntityGuid();
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID:
                return getEntityId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID:
                getEntityGuid().clear();
                getEntityGuid().addAll((Collection<? extends String>)newValue);
                return;
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID:
                getEntityId().clear();
                getEntityId().addAll((Collection<? extends XmlModelEntityId>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID:
                getEntityGuid().clear();
                return;
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID:
                getEntityId().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID:
                return entityGuid != null && !entityGuid.isEmpty();
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID:
                return entityId != null && !entityId.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (entityGuid: ");
        result.append(entityGuid);
        result.append(')');
        return result.toString();
    }

} //GetOfferSetResponseTypeImpl
