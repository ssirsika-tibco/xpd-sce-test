/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.ExternalReference;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Service</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseServiceImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseServiceImpl#getCaseClassType <em>Case Class Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseServiceImpl#getVisibleForCaseStates <em>Visible For Case States</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseServiceImpl extends EObjectImpl implements CaseService {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The cached value of the '{@link #getCaseClassType() <em>Case Class Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseClassType()
     * @generated
     * @ordered
     */
    protected ExternalReference caseClassType;

    /**
     * The cached value of the '{@link #getVisibleForCaseStates() <em>Visible For Case States</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVisibleForCaseStates()
     * @generated
     * @ordered
     */
    protected VisibleForCaseStates visibleForCaseStates;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CaseServiceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CASE_SERVICE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, XpdExtensionPackage.CASE_SERVICE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExternalReference getCaseClassType() {
        return caseClassType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCaseClassType(ExternalReference newCaseClassType, NotificationChain msgs) {
        ExternalReference oldCaseClassType = caseClassType;
        caseClassType = newCaseClassType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE, oldCaseClassType, newCaseClassType);
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
    public void setCaseClassType(ExternalReference newCaseClassType) {
        if (newCaseClassType != caseClassType) {
            NotificationChain msgs = null;
            if (caseClassType != null)
                msgs = ((InternalEObject) caseClassType).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE,
                        null,
                        msgs);
            if (newCaseClassType != null)
                msgs = ((InternalEObject) newCaseClassType).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE,
                        null,
                        msgs);
            msgs = basicSetCaseClassType(newCaseClassType, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE,
                    newCaseClassType, newCaseClassType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public VisibleForCaseStates getVisibleForCaseStates() {
        return visibleForCaseStates;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetVisibleForCaseStates(VisibleForCaseStates newVisibleForCaseStates,
            NotificationChain msgs) {
        VisibleForCaseStates oldVisibleForCaseStates = visibleForCaseStates;
        visibleForCaseStates = newVisibleForCaseStates;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES, oldVisibleForCaseStates,
                    newVisibleForCaseStates);
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
    public void setVisibleForCaseStates(VisibleForCaseStates newVisibleForCaseStates) {
        if (newVisibleForCaseStates != visibleForCaseStates) {
            NotificationChain msgs = null;
            if (visibleForCaseStates != null)
                msgs = ((InternalEObject) visibleForCaseStates).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES,
                        null,
                        msgs);
            if (newVisibleForCaseStates != null)
                msgs = ((InternalEObject) newVisibleForCaseStates).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES,
                        null,
                        msgs);
            msgs = basicSetVisibleForCaseStates(newVisibleForCaseStates, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES, newVisibleForCaseStates,
                    newVisibleForCaseStates));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getOtherElement(String elementName) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.CASE_SERVICE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE:
            return basicSetCaseClassType(null, msgs);
        case XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES:
            return basicSetVisibleForCaseStates(null, msgs);
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
        case XpdExtensionPackage.CASE_SERVICE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE:
            return getCaseClassType();
        case XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES:
            return getVisibleForCaseStates();
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
        case XpdExtensionPackage.CASE_SERVICE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE:
            setCaseClassType((ExternalReference) newValue);
            return;
        case XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES:
            setVisibleForCaseStates((VisibleForCaseStates) newValue);
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
        case XpdExtensionPackage.CASE_SERVICE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE:
            setCaseClassType((ExternalReference) null);
            return;
        case XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES:
            setVisibleForCaseStates((VisibleForCaseStates) null);
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
        case XpdExtensionPackage.CASE_SERVICE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case XpdExtensionPackage.CASE_SERVICE__CASE_CLASS_TYPE:
            return caseClassType != null;
        case XpdExtensionPackage.CASE_SERVICE__VISIBLE_FOR_CASE_STATES:
            return visibleForCaseStates != null;
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(')');
        return result.toString();
    }

} //CaseServiceImpl
