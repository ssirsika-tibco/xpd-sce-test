/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.impl;

import com.tibco.n2.common.organisation.api.OrganisationPackage;
import com.tibco.n2.common.organisation.api.OrganisationalEntityType;
import com.tibco.n2.common.organisation.api.QualifierSetType;
import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Model Entity Id</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl#getQualifierSet <em>Qualifier Set</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl#getEntityType <em>Entity Type</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl#getGuid <em>Guid</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XmlModelEntityIdImpl extends XmlOrgModelVersionImpl implements XmlModelEntityId {
    /**
     * The cached value of the '{@link #getQualifierSet() <em>Qualifier Set</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQualifierSet()
     * @generated
     * @ordered
     */
    protected EList<QualifierSetType> qualifierSet;

    /**
     * The default value of the '{@link #getEntityType() <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityType()
     * @generated
     * @ordered
     */
    protected static final OrganisationalEntityType ENTITY_TYPE_EDEFAULT = OrganisationalEntityType.ORGANIZATION;

    /**
     * The cached value of the '{@link #getEntityType() <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityType()
     * @generated
     * @ordered
     */
    protected OrganisationalEntityType entityType = ENTITY_TYPE_EDEFAULT;

    /**
     * This is true if the Entity Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean entityTypeESet;

    /**
     * The default value of the '{@link #getGuid() <em>Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGuid()
     * @generated
     * @ordered
     */
    protected static final String GUID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGuid() <em>Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGuid()
     * @generated
     * @ordered
     */
    protected String guid = GUID_EDEFAULT;

    /**
     * The default value of the '{@link #getQualifier() <em>Qualifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQualifier()
     * @generated
     * @ordered
     */
    protected static final String QUALIFIER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getQualifier() <em>Qualifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQualifier()
     * @generated
     * @ordered
     */
    protected String qualifier = QUALIFIER_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XmlModelEntityIdImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OrganisationPackage.Literals.XML_MODEL_ENTITY_ID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<QualifierSetType> getQualifierSet() {
        if (qualifierSet == null) {
            qualifierSet = new EObjectContainmentEList<QualifierSetType>(QualifierSetType.class, this, OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER_SET);
        }
        return qualifierSet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationalEntityType getEntityType() {
        return entityType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityType(OrganisationalEntityType newEntityType) {
        OrganisationalEntityType oldEntityType = entityType;
        entityType = newEntityType == null ? ENTITY_TYPE_EDEFAULT : newEntityType;
        boolean oldEntityTypeESet = entityTypeESet;
        entityTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_MODEL_ENTITY_ID__ENTITY_TYPE, oldEntityType, entityType, !oldEntityTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetEntityType() {
        OrganisationalEntityType oldEntityType = entityType;
        boolean oldEntityTypeESet = entityTypeESet;
        entityType = ENTITY_TYPE_EDEFAULT;
        entityTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, OrganisationPackage.XML_MODEL_ENTITY_ID__ENTITY_TYPE, oldEntityType, ENTITY_TYPE_EDEFAULT, oldEntityTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetEntityType() {
        return entityTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getGuid() {
        return guid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGuid(String newGuid) {
        String oldGuid = guid;
        guid = newGuid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_MODEL_ENTITY_ID__GUID, oldGuid, guid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQualifier(String newQualifier) {
        String oldQualifier = qualifier;
        qualifier = newQualifier;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER, oldQualifier, qualifier));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER_SET:
                return ((InternalEList<?>)getQualifierSet()).basicRemove(otherEnd, msgs);
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
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER_SET:
                return getQualifierSet();
            case OrganisationPackage.XML_MODEL_ENTITY_ID__ENTITY_TYPE:
                return getEntityType();
            case OrganisationPackage.XML_MODEL_ENTITY_ID__GUID:
                return getGuid();
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER:
                return getQualifier();
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
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER_SET:
                getQualifierSet().clear();
                getQualifierSet().addAll((Collection<? extends QualifierSetType>)newValue);
                return;
            case OrganisationPackage.XML_MODEL_ENTITY_ID__ENTITY_TYPE:
                setEntityType((OrganisationalEntityType)newValue);
                return;
            case OrganisationPackage.XML_MODEL_ENTITY_ID__GUID:
                setGuid((String)newValue);
                return;
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER:
                setQualifier((String)newValue);
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
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER_SET:
                getQualifierSet().clear();
                return;
            case OrganisationPackage.XML_MODEL_ENTITY_ID__ENTITY_TYPE:
                unsetEntityType();
                return;
            case OrganisationPackage.XML_MODEL_ENTITY_ID__GUID:
                setGuid(GUID_EDEFAULT);
                return;
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER:
                setQualifier(QUALIFIER_EDEFAULT);
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
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER_SET:
                return qualifierSet != null && !qualifierSet.isEmpty();
            case OrganisationPackage.XML_MODEL_ENTITY_ID__ENTITY_TYPE:
                return isSetEntityType();
            case OrganisationPackage.XML_MODEL_ENTITY_ID__GUID:
                return GUID_EDEFAULT == null ? guid != null : !GUID_EDEFAULT.equals(guid);
            case OrganisationPackage.XML_MODEL_ENTITY_ID__QUALIFIER:
                return QUALIFIER_EDEFAULT == null ? qualifier != null : !QUALIFIER_EDEFAULT.equals(qualifier);
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
        result.append(" (entityType: ");
        if (entityTypeESet) result.append(entityType); else result.append("<unset>");
        result.append(", guid: ");
        result.append(guid);
        result.append(", qualifier: ");
        result.append(qualifier);
        result.append(')');
        return result.toString();
    }

} //XmlModelEntityIdImpl
