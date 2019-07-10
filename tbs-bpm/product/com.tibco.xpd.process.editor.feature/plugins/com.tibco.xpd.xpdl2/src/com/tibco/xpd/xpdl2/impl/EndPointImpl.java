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

import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>End Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndPointImpl#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndPointImpl#getEndPointType <em>End Point Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EndPointImpl extends EObjectImpl implements EndPoint {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExternalReference() <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExternalReference()
     * @generated
     * @ordered
     */
    protected ExternalReference externalReference;

    /**
     * The default value of the '{@link #getEndPointType() <em>End Point Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndPointType()
     * @generated
     * @ordered
     */
    protected static final EndPointTypeType END_POINT_TYPE_EDEFAULT = EndPointTypeType.WSDL_LITERAL;

    /**
     * The cached value of the '{@link #getEndPointType() <em>End Point Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndPointType()
     * @generated
     * @ordered
     */
    protected EndPointTypeType endPointType = END_POINT_TYPE_EDEFAULT;

    /**
     * This is true if the End Point Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean endPointTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EndPointImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.END_POINT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExternalReference getExternalReference() {
        return externalReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExternalReference(ExternalReference newExternalReference, NotificationChain msgs) {
        ExternalReference oldExternalReference = externalReference;
        externalReference = newExternalReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_POINT__EXTERNAL_REFERENCE, oldExternalReference, newExternalReference);
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
    public void setExternalReference(ExternalReference newExternalReference) {
        if (newExternalReference != externalReference) {
            NotificationChain msgs = null;
            if (externalReference != null)
                msgs = ((InternalEObject) externalReference).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_POINT__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            if (newExternalReference != null)
                msgs = ((InternalEObject) newExternalReference).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_POINT__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            msgs = basicSetExternalReference(newExternalReference, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_POINT__EXTERNAL_REFERENCE,
                    newExternalReference, newExternalReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndPointTypeType getEndPointType() {
        return endPointType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndPointType(EndPointTypeType newEndPointType) {
        EndPointTypeType oldEndPointType = endPointType;
        endPointType = newEndPointType == null ? END_POINT_TYPE_EDEFAULT : newEndPointType;
        boolean oldEndPointTypeESet = endPointTypeESet;
        endPointTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_POINT__END_POINT_TYPE,
                    oldEndPointType, endPointType, !oldEndPointTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetEndPointType() {
        EndPointTypeType oldEndPointType = endPointType;
        boolean oldEndPointTypeESet = endPointTypeESet;
        endPointType = END_POINT_TYPE_EDEFAULT;
        endPointTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.END_POINT__END_POINT_TYPE,
                    oldEndPointType, END_POINT_TYPE_EDEFAULT, oldEndPointTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetEndPointType() {
        return endPointTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.END_POINT__EXTERNAL_REFERENCE:
            return basicSetExternalReference(null, msgs);
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
        case Xpdl2Package.END_POINT__EXTERNAL_REFERENCE:
            return getExternalReference();
        case Xpdl2Package.END_POINT__END_POINT_TYPE:
            return getEndPointType();
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
        case Xpdl2Package.END_POINT__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) newValue);
            return;
        case Xpdl2Package.END_POINT__END_POINT_TYPE:
            setEndPointType((EndPointTypeType) newValue);
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
        case Xpdl2Package.END_POINT__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) null);
            return;
        case Xpdl2Package.END_POINT__END_POINT_TYPE:
            unsetEndPointType();
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
        case Xpdl2Package.END_POINT__EXTERNAL_REFERENCE:
            return externalReference != null;
        case Xpdl2Package.END_POINT__END_POINT_TYPE:
            return isSetEndPointType();
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
        result.append(" (endPointType: "); //$NON-NLS-1$
        if (endPointTypeESet)
            result.append(endPointType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //EndPointImpl
