/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Description;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Associated Correlation Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldImpl#getCorrelationMode <em>Correlation Mode</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssociatedCorrelationFieldImpl extends EObjectImpl
        implements AssociatedCorrelationField {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

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
     * The default value of the '{@link #getCorrelationMode() <em>Correlation Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCorrelationMode()
     * @generated
     * @ordered
     */
    protected static final CorrelationMode CORRELATION_MODE_EDEFAULT =
            CorrelationMode.INITIALIZE;

    /**
     * The cached value of the '{@link #getCorrelationMode() <em>Correlation Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCorrelationMode()
     * @generated
     * @ordered
     */
    protected CorrelationMode correlationMode = CORRELATION_MODE_EDEFAULT;

    /**
     * This is true if the Correlation Mode attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean correlationModeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AssociatedCorrelationFieldImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.ASSOCIATED_CORRELATION_FIELD;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Description getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDescription(Description newDescription,
            NotificationChain msgs) {
        Description oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION,
                    oldDescription, newDescription);
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
    public void setDescription(Description newDescription) {
        if (newDescription != description) {
            NotificationChain msgs = null;
            if (description != null)
                msgs = ((InternalEObject) description).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION,
                        null,
                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION,
                    newDescription, newDescription));
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
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__NAME,
                    oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CorrelationMode getCorrelationMode() {
        return correlationMode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCorrelationMode(CorrelationMode newCorrelationMode) {
        CorrelationMode oldCorrelationMode = correlationMode;
        correlationMode = newCorrelationMode == null ? CORRELATION_MODE_EDEFAULT
                : newCorrelationMode;
        boolean oldCorrelationModeESet = correlationModeESet;
        correlationModeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE,
                    oldCorrelationMode, correlationMode,
                    !oldCorrelationModeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCorrelationMode() {
        CorrelationMode oldCorrelationMode = correlationMode;
        boolean oldCorrelationModeESet = correlationModeESet;
        correlationMode = CORRELATION_MODE_EDEFAULT;
        correlationModeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE,
                    oldCorrelationMode, CORRELATION_MODE_EDEFAULT,
                    oldCorrelationModeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCorrelationMode() {
        return correlationModeESet;
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
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION:
            return basicSetDescription(null, msgs);
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
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION:
            return getDescription();
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__NAME:
            return getName();
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE:
            return getCorrelationMode();
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
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__NAME:
            setName((String) newValue);
            return;
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE:
            setCorrelationMode((CorrelationMode) newValue);
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
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION:
            setDescription((Description) null);
            return;
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__NAME:
            setName(NAME_EDEFAULT);
            return;
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE:
            unsetCorrelationMode();
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
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__DESCRIPTION:
            return description != null;
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__NAME:
            return NAME_EDEFAULT == null ? name != null
                    : !NAME_EDEFAULT.equals(name);
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE:
            return isSetCorrelationMode();
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
        result.append(" (name: "); //$NON-NLS-1$
        result.append(name);
        result.append(", correlationMode: "); //$NON-NLS-1$
        if (correlationModeESet)
            result.append(correlationMode);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //AssociatedCorrelationFieldImpl
