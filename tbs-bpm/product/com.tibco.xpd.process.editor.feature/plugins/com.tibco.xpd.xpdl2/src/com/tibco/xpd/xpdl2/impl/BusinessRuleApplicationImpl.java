/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.BusinessRuleApplication;
import com.tibco.xpd.xpdl2.Location;
import com.tibco.xpd.xpdl2.RuleName;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Business Rule Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BusinessRuleApplicationImpl#getRuleName <em>Rule Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BusinessRuleApplicationImpl#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BusinessRuleApplicationImpl extends EObjectImpl implements BusinessRuleApplication {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getRuleName() <em>Rule Name</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRuleName()
     * @generated
     * @ordered
     */
    protected RuleName ruleName;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected Location location;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BusinessRuleApplicationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.BUSINESS_RULE_APPLICATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RuleName getRuleName() {
        return ruleName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRuleName(RuleName newRuleName, NotificationChain msgs) {
        RuleName oldRuleName = ruleName;
        ruleName = newRuleName;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME, oldRuleName, newRuleName);
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
    public void setRuleName(RuleName newRuleName) {
        if (newRuleName != ruleName) {
            NotificationChain msgs = null;
            if (ruleName != null)
                msgs = ((InternalEObject) ruleName).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME,
                        null,
                        msgs);
            if (newRuleName != null)
                msgs = ((InternalEObject) newRuleName).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME,
                        null,
                        msgs);
            msgs = basicSetRuleName(newRuleName, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME,
                    newRuleName, newRuleName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Location getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLocation(Location newLocation, NotificationChain msgs) {
        Location oldLocation = location;
        location = newLocation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION, oldLocation, newLocation);
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
    public void setLocation(Location newLocation) {
        if (newLocation != location) {
            NotificationChain msgs = null;
            if (location != null)
                msgs = ((InternalEObject) location).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION,
                        null,
                        msgs);
            if (newLocation != null)
                msgs = ((InternalEObject) newLocation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION,
                        null,
                        msgs);
            msgs = basicSetLocation(newLocation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION,
                    newLocation, newLocation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME:
            return basicSetRuleName(null, msgs);
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION:
            return basicSetLocation(null, msgs);
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
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME:
            return getRuleName();
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION:
            return getLocation();
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
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME:
            setRuleName((RuleName) newValue);
            return;
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION:
            setLocation((Location) newValue);
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
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME:
            setRuleName((RuleName) null);
            return;
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION:
            setLocation((Location) null);
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
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__RULE_NAME:
            return ruleName != null;
        case Xpdl2Package.BUSINESS_RULE_APPLICATION__LOCATION:
            return location != null;
        }
        return super.eIsSet(featureID);
    }

} //BusinessRuleApplicationImpl
