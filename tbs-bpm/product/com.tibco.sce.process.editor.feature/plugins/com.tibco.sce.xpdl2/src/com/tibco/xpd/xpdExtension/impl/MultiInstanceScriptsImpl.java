/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Instance Scripts</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.MultiInstanceScriptsImpl#getAdditionalInstances <em>Additional Instances</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiInstanceScriptsImpl extends EObjectImpl
        implements MultiInstanceScripts {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAdditionalInstances() <em>Additional Instances</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAdditionalInstances()
     * @generated
     * @ordered
     */
    protected Expression additionalInstances;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MultiInstanceScriptsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.MULTI_INSTANCE_SCRIPTS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getAdditionalInstances() {
        return additionalInstances;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAdditionalInstances(
            Expression newAdditionalInstances, NotificationChain msgs) {
        Expression oldAdditionalInstances = additionalInstances;
        additionalInstances = newAdditionalInstances;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES,
                    oldAdditionalInstances, newAdditionalInstances);
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
    public void setAdditionalInstances(Expression newAdditionalInstances) {
        if (newAdditionalInstances != additionalInstances) {
            NotificationChain msgs = null;
            if (additionalInstances != null)
                msgs = ((InternalEObject) additionalInstances).eInverseRemove(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES,
                        null,
                        msgs);
            if (newAdditionalInstances != null)
                msgs = ((InternalEObject) newAdditionalInstances).eInverseAdd(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES,
                        null,
                        msgs);
            msgs = basicSetAdditionalInstances(newAdditionalInstances, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES,
                    newAdditionalInstances, newAdditionalInstances));
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
        case XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES:
            return basicSetAdditionalInstances(null, msgs);
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
        case XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES:
            return getAdditionalInstances();
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
        case XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES:
            setAdditionalInstances((Expression) newValue);
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
        case XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES:
            setAdditionalInstances((Expression) null);
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
        case XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES:
            return additionalInstances != null;
        }
        return super.eIsSet(featureID);
    }

} //MultiInstanceScriptsImpl