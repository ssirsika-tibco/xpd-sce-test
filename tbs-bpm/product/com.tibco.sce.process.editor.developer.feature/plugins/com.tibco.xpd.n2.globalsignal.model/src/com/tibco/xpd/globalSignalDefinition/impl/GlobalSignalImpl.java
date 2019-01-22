/**
 */
package com.tibco.xpd.globalSignalDefinition.impl;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import java.math.BigInteger;
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
 * An implementation of the model object '<em><b>Global Signal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl#getPayloadDataFields <em>Payload Data Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl#getCorrelationTimeout <em>Correlation Timeout</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalSignalImpl extends MinimalEObjectImpl.Container implements GlobalSignal {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * The cached value of the '{@link #getPayloadDataFields() <em>Payload Data Fields</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPayloadDataFields()
     * @generated
     * @ordered
     */
    protected EList<PayloadDataField> payloadDataFields;

    /**
     * The default value of the '{@link #getCorrelationTimeout() <em>Correlation Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCorrelationTimeout()
     * @generated
     * @ordered
     */
    protected static final BigInteger CORRELATION_TIMEOUT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCorrelationTimeout() <em>Correlation Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCorrelationTimeout()
     * @generated
     * @ordered
     */
    protected BigInteger correlationTimeout = CORRELATION_TIMEOUT_EDEFAULT;

    /**
     * This is true if the Correlation Timeout attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean correlationTimeoutESet;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GlobalSignalImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PayloadDataField> getPayloadDataFields() {
        if (payloadDataFields == null) {
            payloadDataFields = new EObjectContainmentEList<PayloadDataField>(PayloadDataField.class, this, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS);
        }
        return payloadDataFields;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getCorrelationTimeout() {
        return correlationTimeout;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCorrelationTimeout(BigInteger newCorrelationTimeout) {
        BigInteger oldCorrelationTimeout = correlationTimeout;
        correlationTimeout = newCorrelationTimeout;
        boolean oldCorrelationTimeoutESet = correlationTimeoutESet;
        correlationTimeoutESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__CORRELATION_TIMEOUT, oldCorrelationTimeout, correlationTimeout, !oldCorrelationTimeoutESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCorrelationTimeout() {
        BigInteger oldCorrelationTimeout = correlationTimeout;
        boolean oldCorrelationTimeoutESet = correlationTimeoutESet;
        correlationTimeout = CORRELATION_TIMEOUT_EDEFAULT;
        correlationTimeoutESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__CORRELATION_TIMEOUT, oldCorrelationTimeout, CORRELATION_TIMEOUT_EDEFAULT, oldCorrelationTimeoutESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCorrelationTimeout() {
        return correlationTimeoutESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__DESCRIPTION, oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS:
                return ((InternalEList<?>)getPayloadDataFields()).basicRemove(otherEnd, msgs);
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS:
                return getPayloadDataFields();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__CORRELATION_TIMEOUT:
                return getCorrelationTimeout();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__DESCRIPTION:
                return getDescription();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__NAME:
                return getName();
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS:
                getPayloadDataFields().clear();
                getPayloadDataFields().addAll((Collection<? extends PayloadDataField>)newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__CORRELATION_TIMEOUT:
                setCorrelationTimeout((BigInteger)newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__DESCRIPTION:
                setDescription((String)newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__NAME:
                setName((String)newValue);
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS:
                getPayloadDataFields().clear();
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__CORRELATION_TIMEOUT:
                unsetCorrelationTimeout();
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__NAME:
                setName(NAME_EDEFAULT);
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS:
                return payloadDataFields != null && !payloadDataFields.isEmpty();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__CORRELATION_TIMEOUT:
                return isSetCorrelationTimeout();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
        result.append(" (correlationTimeout: "); //$NON-NLS-1$
        if (correlationTimeoutESet) result.append(correlationTimeout); else result.append("<unset>"); //$NON-NLS-1$
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //GlobalSignalImpl
