/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.resources.ui.omnotation.impl;

import com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage;
import com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shape Gradient Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.resources.ui.omnotation.impl.ShapeGradientStyleImpl#getGradStartColor <em>Grad Start Color</em>}</li>
 *   <li>{@link com.tibco.xpd.om.resources.ui.omnotation.impl.ShapeGradientStyleImpl#getGradEndColor <em>Grad End Color</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ShapeGradientStyleImpl extends EObjectImpl implements ShapeGradientStyle {
    /**
     * The default value of the '{@link #getGradStartColor() <em>Grad Start Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGradStartColor()
     * @generated
     * @ordered
     */
    protected static final int GRAD_START_COLOR_EDEFAULT = 16777215;

    /**
     * The cached value of the '{@link #getGradStartColor() <em>Grad Start Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGradStartColor()
     * @generated
     * @ordered
     */
    protected int gradStartColor = GRAD_START_COLOR_EDEFAULT;

    /**
     * The default value of the '{@link #getGradEndColor() <em>Grad End Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGradEndColor()
     * @generated
     * @ordered
     */
    protected static final int GRAD_END_COLOR_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getGradEndColor() <em>Grad End Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGradEndColor()
     * @generated
     * @ordered
     */
    protected int gradEndColor = GRAD_END_COLOR_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ShapeGradientStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OmNotationPackage.Literals.SHAPE_GRADIENT_STYLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getGradStartColor() {
        return gradStartColor;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGradStartColor(int newGradStartColor) {
        int oldGradStartColor = gradStartColor;
        gradStartColor = newGradStartColor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_START_COLOR, oldGradStartColor, gradStartColor));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getGradEndColor() {
        return gradEndColor;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGradEndColor(int newGradEndColor) {
        int oldGradEndColor = gradEndColor;
        gradEndColor = newGradEndColor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_END_COLOR, oldGradEndColor, gradEndColor));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_START_COLOR:
                return new Integer(getGradStartColor());
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_END_COLOR:
                return new Integer(getGradEndColor());
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
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_START_COLOR:
                setGradStartColor(((Integer)newValue).intValue());
                return;
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_END_COLOR:
                setGradEndColor(((Integer)newValue).intValue());
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
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_START_COLOR:
                setGradStartColor(GRAD_START_COLOR_EDEFAULT);
                return;
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_END_COLOR:
                setGradEndColor(GRAD_END_COLOR_EDEFAULT);
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
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_START_COLOR:
                return gradStartColor != GRAD_START_COLOR_EDEFAULT;
            case OmNotationPackage.SHAPE_GRADIENT_STYLE__GRAD_END_COLOR:
                return gradEndColor != GRAD_END_COLOR_EDEFAULT;
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
        result.append(" (gradStartColor: ");
        result.append(gradStartColor);
        result.append(", gradEndColor: ");
        result.append(gradEndColor);
        result.append(')');
        return result.toString();
    }

} //ShapeGradientStyleImpl
