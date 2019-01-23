/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OrgEntityConfigAttributeSet;
import com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Org Entity Config Attributes Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesTypeImpl#getOrgEntityConfigAttributeSet <em>Org Entity Config Attribute Set</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesTypeImpl#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetOrgEntityConfigAttributesTypeImpl extends EObjectImpl implements SetOrgEntityConfigAttributesType {
    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected FeatureMap group;

    /**
     * The default value of the '{@link #getResource() <em>Resource</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResource()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResource() <em>Resource</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResource()
     * @generated
     * @ordered
     */
    protected String resource = RESOURCE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SetOrgEntityConfigAttributesTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgEntityConfigAttributeSet> getOrgEntityConfigAttributeSet() {
        return getGroup().list(N2BRMPackage.Literals.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResource() {
        return resource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResource(String newResource) {
        String oldResource = resource;
        resource = newResource;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE, oldResource, resource));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET:
                return ((InternalEList<?>)getOrgEntityConfigAttributeSet()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP:
                if (coreType) return getGroup();
                return ((FeatureMap.Internal)getGroup()).getWrapper();
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET:
                return getOrgEntityConfigAttributeSet();
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE:
                return getResource();
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
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP:
                ((FeatureMap.Internal)getGroup()).set(newValue);
                return;
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET:
                getOrgEntityConfigAttributeSet().clear();
                getOrgEntityConfigAttributeSet().addAll((Collection<? extends OrgEntityConfigAttributeSet>)newValue);
                return;
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE:
                setResource((String)newValue);
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
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP:
                getGroup().clear();
                return;
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET:
                getOrgEntityConfigAttributeSet().clear();
                return;
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE:
                setResource(RESOURCE_EDEFAULT);
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
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP:
                return group != null && !group.isEmpty();
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET:
                return !getOrgEntityConfigAttributeSet().isEmpty();
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE:
                return RESOURCE_EDEFAULT == null ? resource != null : !RESOURCE_EDEFAULT.equals(resource);
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
        result.append(" (group: ");
        result.append(group);
        result.append(", resource: ");
        result.append(resource);
        result.append(')');
        return result.toString();
    }

} //SetOrgEntityConfigAttributesTypeImpl
