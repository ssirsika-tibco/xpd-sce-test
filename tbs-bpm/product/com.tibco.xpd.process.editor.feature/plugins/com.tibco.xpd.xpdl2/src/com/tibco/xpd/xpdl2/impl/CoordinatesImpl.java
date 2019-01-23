/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Coordinates</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.CoordinatesImpl#getXCoordinate <em>XCoordinate</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.CoordinatesImpl#getYCoordinate <em>YCoordinate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CoordinatesImpl extends EObjectImpl implements Coordinates {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getXCoordinate() <em>XCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXCoordinate()
     * @generated
     * @ordered
     */
    protected static final double XCOORDINATE_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getXCoordinate() <em>XCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXCoordinate()
     * @generated
     * @ordered
     */
    protected double xCoordinate = XCOORDINATE_EDEFAULT;

    /**
     * This is true if the XCoordinate attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean xCoordinateESet;

    /**
     * The default value of the '{@link #getYCoordinate() <em>YCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYCoordinate()
     * @generated
     * @ordered
     */
    protected static final double YCOORDINATE_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getYCoordinate() <em>YCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYCoordinate()
     * @generated
     * @ordered
     */
    protected double yCoordinate = YCOORDINATE_EDEFAULT;

    /**
     * This is true if the YCoordinate attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean yCoordinateESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CoordinatesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.COORDINATES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getXCoordinate() {
        return xCoordinate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setXCoordinate(double newXCoordinate) {
        double oldXCoordinate = xCoordinate;
        xCoordinate = newXCoordinate;
        boolean oldXCoordinateESet = xCoordinateESet;
        xCoordinateESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.COORDINATES__XCOORDINATE, oldXCoordinate,
                    xCoordinate, !oldXCoordinateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetXCoordinate() {
        double oldXCoordinate = xCoordinate;
        boolean oldXCoordinateESet = xCoordinateESet;
        xCoordinate = XCOORDINATE_EDEFAULT;
        xCoordinateESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.COORDINATES__XCOORDINATE, oldXCoordinate,
                    XCOORDINATE_EDEFAULT, oldXCoordinateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetXCoordinate() {
        return xCoordinateESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getYCoordinate() {
        return yCoordinate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setYCoordinate(double newYCoordinate) {
        double oldYCoordinate = yCoordinate;
        yCoordinate = newYCoordinate;
        boolean oldYCoordinateESet = yCoordinateESet;
        yCoordinateESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.COORDINATES__YCOORDINATE, oldYCoordinate,
                    yCoordinate, !oldYCoordinateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetYCoordinate() {
        double oldYCoordinate = yCoordinate;
        boolean oldYCoordinateESet = yCoordinateESet;
        yCoordinate = YCOORDINATE_EDEFAULT;
        yCoordinateESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.COORDINATES__YCOORDINATE, oldYCoordinate,
                    YCOORDINATE_EDEFAULT, oldYCoordinateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetYCoordinate() {
        return yCoordinateESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.COORDINATES__XCOORDINATE:
            return getXCoordinate();
        case Xpdl2Package.COORDINATES__YCOORDINATE:
            return getYCoordinate();
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
        case Xpdl2Package.COORDINATES__XCOORDINATE:
            setXCoordinate((Double) newValue);
            return;
        case Xpdl2Package.COORDINATES__YCOORDINATE:
            setYCoordinate((Double) newValue);
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
        case Xpdl2Package.COORDINATES__XCOORDINATE:
            unsetXCoordinate();
            return;
        case Xpdl2Package.COORDINATES__YCOORDINATE:
            unsetYCoordinate();
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
        case Xpdl2Package.COORDINATES__XCOORDINATE:
            return isSetXCoordinate();
        case Xpdl2Package.COORDINATES__YCOORDINATE:
            return isSetYCoordinate();
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
        result.append(" (xCoordinate: "); //$NON-NLS-1$
        if (xCoordinateESet)
            result.append(xCoordinate);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", yCoordinate: "); //$NON-NLS-1$
        if (yCoordinateESet)
            result.append(yCoordinate);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //CoordinatesImpl
