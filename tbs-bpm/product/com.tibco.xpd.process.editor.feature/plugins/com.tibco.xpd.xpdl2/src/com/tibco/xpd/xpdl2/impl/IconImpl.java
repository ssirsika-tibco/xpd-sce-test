/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.SHAPEType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Icon</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IconImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IconImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IconImpl#getShape <em>Shape</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IconImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IconImpl#getXCoord <em>XCoord</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IconImpl#getYCoord <em>YCoord</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IconImpl extends EObjectImpl implements Icon {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected String value = VALUE_EDEFAULT;

    /**
     * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHeight()
     * @generated
     * @ordered
     */
    protected static final BigInteger HEIGHT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHeight()
     * @generated
     * @ordered
     */
    protected BigInteger height = HEIGHT_EDEFAULT;

    /**
     * The default value of the '{@link #getShape() <em>Shape</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getShape()
     * @generated
     * @ordered
     */
    protected static final SHAPEType SHAPE_EDEFAULT =
            SHAPEType.ROUND_RECTANGLE_LITERAL;

    /**
     * The cached value of the '{@link #getShape() <em>Shape</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getShape()
     * @generated
     * @ordered
     */
    protected SHAPEType shape = SHAPE_EDEFAULT;

    /**
     * This is true if the Shape attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean shapeESet;

    /**
     * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWidth()
     * @generated
     * @ordered
     */
    protected static final BigInteger WIDTH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWidth()
     * @generated
     * @ordered
     */
    protected BigInteger width = WIDTH_EDEFAULT;

    /**
     * The default value of the '{@link #getXCoord() <em>XCoord</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXCoord()
     * @generated
     * @ordered
     */
    protected static final BigInteger XCOORD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getXCoord() <em>XCoord</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXCoord()
     * @generated
     * @ordered
     */
    protected BigInteger xCoord = XCOORD_EDEFAULT;

    /**
     * The default value of the '{@link #getYCoord() <em>YCoord</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYCoord()
     * @generated
     * @ordered
     */
    protected static final BigInteger YCOORD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getYCoord() <em>YCoord</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYCoord()
     * @generated
     * @ordered
     */
    protected BigInteger yCoord = YCOORD_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IconImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ICON;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValue(String newValue) {
        String oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ICON__VALUE, oldValue, value));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getHeight() {
        return height;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHeight(BigInteger newHeight) {
        BigInteger oldHeight = height;
        height = newHeight;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ICON__HEIGHT, oldHeight, height));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SHAPEType getShape() {
        return shape;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setShape(SHAPEType newShape) {
        SHAPEType oldShape = shape;
        shape = newShape == null ? SHAPE_EDEFAULT : newShape;
        boolean oldShapeESet = shapeESet;
        shapeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ICON__SHAPE, oldShape, shape, !oldShapeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetShape() {
        SHAPEType oldShape = shape;
        boolean oldShapeESet = shapeESet;
        shape = SHAPE_EDEFAULT;
        shapeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ICON__SHAPE, oldShape, SHAPE_EDEFAULT,
                    oldShapeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetShape() {
        return shapeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getWidth() {
        return width;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWidth(BigInteger newWidth) {
        BigInteger oldWidth = width;
        width = newWidth;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ICON__WIDTH, oldWidth, width));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getXCoord() {
        return xCoord;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setXCoord(BigInteger newXCoord) {
        BigInteger oldXCoord = xCoord;
        xCoord = newXCoord;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ICON__XCOORD, oldXCoord, xCoord));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getYCoord() {
        return yCoord;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setYCoord(BigInteger newYCoord) {
        BigInteger oldYCoord = yCoord;
        yCoord = newYCoord;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ICON__YCOORD, oldYCoord, yCoord));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.ICON__VALUE:
            return getValue();
        case Xpdl2Package.ICON__HEIGHT:
            return getHeight();
        case Xpdl2Package.ICON__SHAPE:
            return getShape();
        case Xpdl2Package.ICON__WIDTH:
            return getWidth();
        case Xpdl2Package.ICON__XCOORD:
            return getXCoord();
        case Xpdl2Package.ICON__YCOORD:
            return getYCoord();
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
        case Xpdl2Package.ICON__VALUE:
            setValue((String) newValue);
            return;
        case Xpdl2Package.ICON__HEIGHT:
            setHeight((BigInteger) newValue);
            return;
        case Xpdl2Package.ICON__SHAPE:
            setShape((SHAPEType) newValue);
            return;
        case Xpdl2Package.ICON__WIDTH:
            setWidth((BigInteger) newValue);
            return;
        case Xpdl2Package.ICON__XCOORD:
            setXCoord((BigInteger) newValue);
            return;
        case Xpdl2Package.ICON__YCOORD:
            setYCoord((BigInteger) newValue);
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
        case Xpdl2Package.ICON__VALUE:
            setValue(VALUE_EDEFAULT);
            return;
        case Xpdl2Package.ICON__HEIGHT:
            setHeight(HEIGHT_EDEFAULT);
            return;
        case Xpdl2Package.ICON__SHAPE:
            unsetShape();
            return;
        case Xpdl2Package.ICON__WIDTH:
            setWidth(WIDTH_EDEFAULT);
            return;
        case Xpdl2Package.ICON__XCOORD:
            setXCoord(XCOORD_EDEFAULT);
            return;
        case Xpdl2Package.ICON__YCOORD:
            setYCoord(YCOORD_EDEFAULT);
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
        case Xpdl2Package.ICON__VALUE:
            return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT
                    .equals(value);
        case Xpdl2Package.ICON__HEIGHT:
            return HEIGHT_EDEFAULT == null ? height != null : !HEIGHT_EDEFAULT
                    .equals(height);
        case Xpdl2Package.ICON__SHAPE:
            return isSetShape();
        case Xpdl2Package.ICON__WIDTH:
            return WIDTH_EDEFAULT == null ? width != null : !WIDTH_EDEFAULT
                    .equals(width);
        case Xpdl2Package.ICON__XCOORD:
            return XCOORD_EDEFAULT == null ? xCoord != null : !XCOORD_EDEFAULT
                    .equals(xCoord);
        case Xpdl2Package.ICON__YCOORD:
            return YCOORD_EDEFAULT == null ? yCoord != null : !YCOORD_EDEFAULT
                    .equals(yCoord);
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
        result.append(" (value: "); //$NON-NLS-1$
        result.append(value);
        result.append(", height: "); //$NON-NLS-1$
        result.append(height);
        result.append(", shape: "); //$NON-NLS-1$
        if (shapeESet)
            result.append(shape);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", width: "); //$NON-NLS-1$
        result.append(width);
        result.append(", xCoord: "); //$NON-NLS-1$
        result.append(xCoord);
        result.append(", yCoord: "); //$NON-NLS-1$
        result.append(yCoord);
        result.append(')');
        return result.toString();
    }

} //IconImpl
