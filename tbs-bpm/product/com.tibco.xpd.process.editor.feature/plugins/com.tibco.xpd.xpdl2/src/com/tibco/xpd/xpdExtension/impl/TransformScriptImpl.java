/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.TransformScript;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.DataMapping;

import com.tibco.xpd.xpdl2.ExtendedAttribute;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transform Script</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.TransformScriptImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.TransformScriptImpl#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.TransformScriptImpl#getInputDom <em>Input Dom</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.TransformScriptImpl#getOutputDom <em>Output Dom</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransformScriptImpl extends EObjectImpl implements TransformScript {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExtendedAttributes() <em>Extended Attributes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The cached value of the '{@link #getDataMappings() <em>Data Mappings</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataMappings()
     * @generated
     * @ordered
     */
    protected EList<DataMapping> dataMappings;

    /**
     * The cached value of the '{@link #getInputDom() <em>Input Dom</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInputDom()
     * @generated
     * @ordered
     */
    protected EList<String> inputDom;

    /**
     * The cached value of the '{@link #getOutputDom() <em>Output Dom</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutputDom()
     * @generated
     * @ordered
     */
    protected EList<String> outputDom;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TransformScriptImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.TRANSFORM_SCRIPT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    XpdExtensionPackage.TRANSFORM_SCRIPT__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DataMapping> getDataMappings() {
        if (dataMappings == null) {
            dataMappings = new EObjectContainmentEList<DataMapping>(DataMapping.class, this,
                    XpdExtensionPackage.TRANSFORM_SCRIPT__DATA_MAPPINGS);
        }
        return dataMappings;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getInputDom() {
        if (inputDom == null) {
            inputDom = new EDataTypeUniqueEList<String>(String.class, this,
                    XpdExtensionPackage.TRANSFORM_SCRIPT__INPUT_DOM);
        }
        return inputDom;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getOutputDom() {
        if (outputDom == null) {
            outputDom = new EDataTypeUniqueEList<String>(String.class, this,
                    XpdExtensionPackage.TRANSFORM_SCRIPT__OUTPUT_DOM);
        }
        return outputDom;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.TRANSFORM_SCRIPT__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.TRANSFORM_SCRIPT__DATA_MAPPINGS:
            return ((InternalEList<?>) getDataMappings()).basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.TRANSFORM_SCRIPT__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case XpdExtensionPackage.TRANSFORM_SCRIPT__DATA_MAPPINGS:
            return getDataMappings();
        case XpdExtensionPackage.TRANSFORM_SCRIPT__INPUT_DOM:
            return getInputDom();
        case XpdExtensionPackage.TRANSFORM_SCRIPT__OUTPUT_DOM:
            return getOutputDom();
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
        case XpdExtensionPackage.TRANSFORM_SCRIPT__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case XpdExtensionPackage.TRANSFORM_SCRIPT__DATA_MAPPINGS:
            getDataMappings().clear();
            getDataMappings().addAll((Collection<? extends DataMapping>) newValue);
            return;
        case XpdExtensionPackage.TRANSFORM_SCRIPT__INPUT_DOM:
            getInputDom().clear();
            getInputDom().addAll((Collection<? extends String>) newValue);
            return;
        case XpdExtensionPackage.TRANSFORM_SCRIPT__OUTPUT_DOM:
            getOutputDom().clear();
            getOutputDom().addAll((Collection<? extends String>) newValue);
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
        case XpdExtensionPackage.TRANSFORM_SCRIPT__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case XpdExtensionPackage.TRANSFORM_SCRIPT__DATA_MAPPINGS:
            getDataMappings().clear();
            return;
        case XpdExtensionPackage.TRANSFORM_SCRIPT__INPUT_DOM:
            getInputDom().clear();
            return;
        case XpdExtensionPackage.TRANSFORM_SCRIPT__OUTPUT_DOM:
            getOutputDom().clear();
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
        case XpdExtensionPackage.TRANSFORM_SCRIPT__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case XpdExtensionPackage.TRANSFORM_SCRIPT__DATA_MAPPINGS:
            return dataMappings != null && !dataMappings.isEmpty();
        case XpdExtensionPackage.TRANSFORM_SCRIPT__INPUT_DOM:
            return inputDom != null && !inputDom.isEmpty();
        case XpdExtensionPackage.TRANSFORM_SCRIPT__OUTPUT_DOM:
            return outputDom != null && !outputDom.isEmpty();
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (inputDom: "); //$NON-NLS-1$
        result.append(inputDom);
        result.append(", outputDom: "); //$NON-NLS-1$
        result.append(outputDom);
        result.append(')');
        return result.toString();
    }

} //TransformScriptImpl
