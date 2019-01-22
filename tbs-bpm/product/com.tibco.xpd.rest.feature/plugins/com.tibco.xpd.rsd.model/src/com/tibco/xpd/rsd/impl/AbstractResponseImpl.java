/**
 */
package com.tibco.xpd.rsd.impl;

import com.tibco.xpd.rsd.AbstractResponse;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.RsdPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.impl.AbstractResponseImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.AbstractResponseImpl#getPayloadReference <em>Payload Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractResponseImpl extends MinimalEObjectImpl.Container implements AbstractResponse {
    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected EList<Parameter> parameters;

    /**
     * The cached value of the '{@link #getPayloadReference() <em>Payload Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPayloadReference()
     * @generated
     * @ordered
     */
    protected PayloadReference payloadReference;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AbstractResponseImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RsdPackage.Literals.ABSTRACT_RESPONSE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Parameter> getParameters() {
        if (parameters == null) {
            parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, RsdPackage.ABSTRACT_RESPONSE__PARAMETERS);
        }
        return parameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PayloadReference getPayloadReference() {
        return payloadReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPayloadReference(PayloadReference newPayloadReference, NotificationChain msgs) {
        PayloadReference oldPayloadReference = payloadReference;
        payloadReference = newPayloadReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE, oldPayloadReference, newPayloadReference);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPayloadReference(PayloadReference newPayloadReference) {
        if (newPayloadReference != payloadReference) {
            NotificationChain msgs = null;
            if (payloadReference != null)
                msgs = ((InternalEObject)payloadReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE, null, msgs);
            if (newPayloadReference != null)
                msgs = ((InternalEObject)newPayloadReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE, null, msgs);
            msgs = basicSetPayloadReference(newPayloadReference, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE, newPayloadReference, newPayloadReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case RsdPackage.ABSTRACT_RESPONSE__PARAMETERS:
                return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
            case RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE:
                return basicSetPayloadReference(null, msgs);
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
            case RsdPackage.ABSTRACT_RESPONSE__PARAMETERS:
                return getParameters();
            case RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE:
                return getPayloadReference();
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
            case RsdPackage.ABSTRACT_RESPONSE__PARAMETERS:
                getParameters().clear();
                getParameters().addAll((Collection<? extends Parameter>)newValue);
                return;
            case RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE:
                setPayloadReference((PayloadReference)newValue);
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
            case RsdPackage.ABSTRACT_RESPONSE__PARAMETERS:
                getParameters().clear();
                return;
            case RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE:
                setPayloadReference((PayloadReference)null);
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
            case RsdPackage.ABSTRACT_RESPONSE__PARAMETERS:
                return parameters != null && !parameters.isEmpty();
            case RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE:
                return payloadReference != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == PayloadRefContainer.class) {
            switch (derivedFeatureID) {
                case RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE: return RsdPackage.PAYLOAD_REF_CONTAINER__PAYLOAD_REFERENCE;
                default: return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == PayloadRefContainer.class) {
            switch (baseFeatureID) {
                case RsdPackage.PAYLOAD_REF_CONTAINER__PAYLOAD_REFERENCE: return RsdPackage.ABSTRACT_RESPONSE__PAYLOAD_REFERENCE;
                default: return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} //AbstractResponseImpl
