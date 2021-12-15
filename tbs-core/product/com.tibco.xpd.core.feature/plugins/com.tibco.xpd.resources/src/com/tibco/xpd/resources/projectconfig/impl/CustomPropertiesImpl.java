/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.impl;

import com.tibco.xpd.resources.projectconfig.CustomProperties;
import com.tibco.xpd.resources.projectconfig.CustomProperty;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Custom Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.CustomPropertiesImpl#getCustomProperty <em>Custom Property</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomPropertiesImpl extends EObjectImpl implements CustomProperties {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.";

    /**
     * The cached value of the '{@link #getCustomProperty() <em>Custom Property</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCustomProperty()
     * @generated
     * @ordered
     */
    protected EList<CustomProperty> customProperty;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CustomPropertiesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProjectConfigPackage.Literals.CUSTOM_PROPERTIES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CustomProperty> getCustomProperty() {
        if (customProperty == null) {
            customProperty = new EObjectContainmentEList<CustomProperty>(CustomProperty.class, this,
                    ProjectConfigPackage.CUSTOM_PROPERTIES__CUSTOM_PROPERTY);
        }
        return customProperty;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ProjectConfigPackage.CUSTOM_PROPERTIES__CUSTOM_PROPERTY:
            return ((InternalEList<?>) getCustomProperty()).basicRemove(otherEnd, msgs);
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
        case ProjectConfigPackage.CUSTOM_PROPERTIES__CUSTOM_PROPERTY:
            return getCustomProperty();
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
        case ProjectConfigPackage.CUSTOM_PROPERTIES__CUSTOM_PROPERTY:
            getCustomProperty().clear();
            getCustomProperty().addAll((Collection<? extends CustomProperty>) newValue);
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
        case ProjectConfigPackage.CUSTOM_PROPERTIES__CUSTOM_PROPERTY:
            getCustomProperty().clear();
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
        case ProjectConfigPackage.CUSTOM_PROPERTIES__CUSTOM_PROPERTY:
            return customProperty != null && !customProperty.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //CustomPropertiesImpl
