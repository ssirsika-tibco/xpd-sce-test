/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Work Item Attribute Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DataWorkItemAttributeMappingImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DataWorkItemAttributeMappingImpl#getProcessData <em>Process Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataWorkItemAttributeMappingImpl extends EObjectImpl
        implements DataWorkItemAttributeMapping {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttribute()
     * @generated
     * @ordered
     */
    protected String attribute = ATTRIBUTE_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessData() <em>Process Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessData()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_DATA_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessData() <em>Process Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessData()
     * @generated
     * @ordered
     */
    protected String processData = PROCESS_DATA_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DataWorkItemAttributeMappingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DATA_WORK_ITEM_ATTRIBUTE_MAPPING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute(String newAttribute) {
        String oldAttribute = attribute;
        attribute = newAttribute;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE,
                    oldAttribute, attribute));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessData() {
        return processData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessData(String newProcessData) {
        String oldProcessData = processData;
        processData = newProcessData;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA,
                    oldProcessData, processData));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE:
            return getAttribute();
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA:
            return getProcessData();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE:
            setAttribute((String) newValue);
            return;
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA:
            setProcessData((String) newValue);
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
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE:
            setAttribute(ATTRIBUTE_EDEFAULT);
            return;
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA:
            setProcessData(PROCESS_DATA_EDEFAULT);
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
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE:
            return ATTRIBUTE_EDEFAULT == null ? attribute != null
                    : !ATTRIBUTE_EDEFAULT.equals(attribute);
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA:
            return PROCESS_DATA_EDEFAULT == null ? processData != null
                    : !PROCESS_DATA_EDEFAULT.equals(processData);
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
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (attribute: "); //$NON-NLS-1$
        result.append(attribute);
        result.append(", processData: "); //$NON-NLS-1$
        result.append(processData);
        result.append(')');
        return result.toString();
    }

} //DataWorkItemAttributeMappingImpl
