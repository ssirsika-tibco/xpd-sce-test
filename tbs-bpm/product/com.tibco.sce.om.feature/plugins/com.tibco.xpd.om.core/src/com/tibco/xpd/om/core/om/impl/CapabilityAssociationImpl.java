/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Capability Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.CapabilityAssociationImpl#getQualifierValue <em>Qualifier Value</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.CapabilityAssociationImpl#getCapability <em>Capability</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CapabilityAssociationImpl extends EObjectImpl implements
        CapabilityAssociation {
    /**
     * The cached value of the '{@link #getQualifierValue() <em>Qualifier Value</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQualifierValue()
     * @generated
     * @ordered
     */
    protected AttributeValue qualifierValue;

    /**
     * The cached value of the '{@link #getCapability() <em>Capability</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCapability()
     * @generated
     * @ordered
     */
    protected Capability capability;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CapabilityAssociationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.CAPABILITY_ASSOCIATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeValue getQualifierValue() {
        return qualifierValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetQualifierValue(
            AttributeValue newQualifierValue, NotificationChain msgs) {
        AttributeValue oldQualifierValue = qualifierValue;
        qualifierValue = newQualifierValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE,
                            oldQualifierValue, newQualifierValue);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQualifierValue(AttributeValue newQualifierValue) {
        if (newQualifierValue != qualifierValue) {
            NotificationChain msgs = null;
            if (qualifierValue != null)
                msgs =
                        ((InternalEObject) qualifierValue)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE,
                                        null,
                                        msgs);
            if (newQualifierValue != null)
                msgs =
                        ((InternalEObject) newQualifierValue)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE,
                                        null,
                                        msgs);
            msgs = basicSetQualifierValue(newQualifierValue, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE,
                    newQualifierValue, newQualifierValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Capability getCapability() {
        if (capability != null && capability.eIsProxy()) {
            InternalEObject oldCapability = (InternalEObject) capability;
            capability = (Capability) eResolveProxy(oldCapability);
            if (capability != oldCapability) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.CAPABILITY_ASSOCIATION__CAPABILITY,
                            oldCapability, capability));
            }
        }
        return capability;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Capability basicGetCapability() {
        return capability;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCapability(Capability newCapability) {
        Capability oldCapability = capability;
        capability = newCapability;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.CAPABILITY_ASSOCIATION__CAPABILITY,
                    oldCapability, capability));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE:
            return basicSetQualifierValue(null, msgs);
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
        case OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE:
            return getQualifierValue();
        case OMPackage.CAPABILITY_ASSOCIATION__CAPABILITY:
            if (resolve)
                return getCapability();
            return basicGetCapability();
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
        case OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE:
            setQualifierValue((AttributeValue) newValue);
            return;
        case OMPackage.CAPABILITY_ASSOCIATION__CAPABILITY:
            setCapability((Capability) newValue);
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
        case OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE:
            setQualifierValue((AttributeValue) null);
            return;
        case OMPackage.CAPABILITY_ASSOCIATION__CAPABILITY:
            setCapability((Capability) null);
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
        case OMPackage.CAPABILITY_ASSOCIATION__QUALIFIER_VALUE:
            return qualifierValue != null;
        case OMPackage.CAPABILITY_ASSOCIATION__CAPABILITY:
            return capability != null;
        }
        return super.eIsSet(featureID);
    }

} //CapabilityAssociationImpl
