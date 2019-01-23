/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.ModelTemplate;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTemplateImpl#getInstanceIdAttribute <em>Instance Id Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelTemplateImpl extends ModelOrgUnitImpl implements ModelTemplate {
    /**
     * The cached value of the '{@link #getInstanceIdAttribute() <em>Instance Id Attribute</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInstanceIdAttribute()
     * @generated
     * @ordered
     */
    protected EList<String> instanceIdAttribute;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ModelTemplateImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.MODEL_TEMPLATE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getInstanceIdAttribute() {
        if (instanceIdAttribute == null) {
            instanceIdAttribute = new EDataTypeEList<String>(String.class, this, DePackage.MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE);
        }
        return instanceIdAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DePackage.MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE:
                return getInstanceIdAttribute();
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
            case DePackage.MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE:
                getInstanceIdAttribute().clear();
                getInstanceIdAttribute().addAll((Collection<? extends String>)newValue);
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
            case DePackage.MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE:
                getInstanceIdAttribute().clear();
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
            case DePackage.MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE:
                return instanceIdAttribute != null && !instanceIdAttribute.isEmpty();
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
        result.append(" (instanceIdAttribute: ");
        result.append(instanceIdAttribute);
        result.append(')');
        return result.toString();
    }

} //ModelTemplateImpl
