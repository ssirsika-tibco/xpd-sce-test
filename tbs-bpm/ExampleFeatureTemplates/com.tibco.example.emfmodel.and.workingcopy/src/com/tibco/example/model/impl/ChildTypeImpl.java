/**
 * Copyright (c) 2004 - 2015 TIBCO Software Inc. ALL RIGHTS RESERVED.
 */
package com.tibco.example.model.impl;

import com.tibco.example.model.ChildType;
import com.tibco.example.model.ModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Child Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.example.model.impl.ChildTypeImpl#getTestElement <em>Test Element</em>}</li>
 *   <li>{@link com.tibco.example.model.impl.ChildTypeImpl#getChildAttribute <em>Child Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChildTypeImpl extends MinimalEObjectImpl.Container implements ChildType {
    /**
     * The default value of the '{@link #getTestElement() <em>Test Element</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTestElement()
     * @generated
     * @ordered
     */
    protected static final String TEST_ELEMENT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTestElement() <em>Test Element</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTestElement()
     * @generated
     * @ordered
     */
    protected String testElement = TEST_ELEMENT_EDEFAULT;

    /**
     * The default value of the '{@link #getChildAttribute() <em>Child Attribute</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChildAttribute()
     * @generated
     * @ordered
     */
    protected static final String CHILD_ATTRIBUTE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChildAttribute() <em>Child Attribute</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChildAttribute()
     * @generated
     * @ordered
     */
    protected String childAttribute = CHILD_ATTRIBUTE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ChildTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ModelPackage.Literals.CHILD_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTestElement() {
        return testElement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTestElement(String newTestElement) {
        String oldTestElement = testElement;
        testElement = newTestElement;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHILD_TYPE__TEST_ELEMENT, oldTestElement, testElement));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getChildAttribute() {
        return childAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setChildAttribute(String newChildAttribute) {
        String oldChildAttribute = childAttribute;
        childAttribute = newChildAttribute;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHILD_TYPE__CHILD_ATTRIBUTE, oldChildAttribute, childAttribute));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ModelPackage.CHILD_TYPE__TEST_ELEMENT:
                return getTestElement();
            case ModelPackage.CHILD_TYPE__CHILD_ATTRIBUTE:
                return getChildAttribute();
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
            case ModelPackage.CHILD_TYPE__TEST_ELEMENT:
                setTestElement((String)newValue);
                return;
            case ModelPackage.CHILD_TYPE__CHILD_ATTRIBUTE:
                setChildAttribute((String)newValue);
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
            case ModelPackage.CHILD_TYPE__TEST_ELEMENT:
                setTestElement(TEST_ELEMENT_EDEFAULT);
                return;
            case ModelPackage.CHILD_TYPE__CHILD_ATTRIBUTE:
                setChildAttribute(CHILD_ATTRIBUTE_EDEFAULT);
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
            case ModelPackage.CHILD_TYPE__TEST_ELEMENT:
                return TEST_ELEMENT_EDEFAULT == null ? testElement != null : !TEST_ELEMENT_EDEFAULT.equals(testElement);
            case ModelPackage.CHILD_TYPE__CHILD_ATTRIBUTE:
                return CHILD_ATTRIBUTE_EDEFAULT == null ? childAttribute != null : !CHILD_ATTRIBUTE_EDEFAULT.equals(childAttribute);
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
        result.append(" (testElement: ");
        result.append(testElement);
        result.append(", childAttribute: ");
        result.append(childAttribute);
        result.append(')');
        return result.toString();
    }

} //ChildTypeImpl
