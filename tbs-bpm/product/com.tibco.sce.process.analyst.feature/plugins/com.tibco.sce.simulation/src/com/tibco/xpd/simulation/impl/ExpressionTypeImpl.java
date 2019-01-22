/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.ExpressionTypeImpl#getEnumBasedExpression <em>Enum Based Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ExpressionTypeImpl#getScriptExpression <em>Script Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ExpressionTypeImpl#getStructuredExpression <em>Structured Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ExpressionTypeImpl#getDefault <em>Default</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExpressionTypeImpl extends EObjectImpl implements ExpressionType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getEnumBasedExpression() <em>Enum Based Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnumBasedExpression()
     * @generated
     * @ordered
     */
    protected EnumBasedExpressionType enumBasedExpression;

    /**
     * The default value of the '{@link #getScriptExpression() <em>Script Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptExpression()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptExpression() <em>Script Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptExpression()
     * @generated
     * @ordered
     */
    protected String scriptExpression = SCRIPT_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStructuredExpression() <em>Structured Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStructuredExpression()
     * @generated
     * @ordered
     */
    protected EObject structuredExpression;

    /**
     * The cached value of the '{@link #getDefault() <em>Default</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefault()
     * @generated
     * @ordered
     */
    protected EObject default_;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExpressionTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.EXPRESSION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumBasedExpressionType getEnumBasedExpression() {
        return enumBasedExpression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEnumBasedExpression(
            EnumBasedExpressionType newEnumBasedExpression,
            NotificationChain msgs) {
        EnumBasedExpressionType oldEnumBasedExpression = enumBasedExpression;
        enumBasedExpression = newEnumBasedExpression;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION,
                            oldEnumBasedExpression, newEnumBasedExpression);
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
    public void setEnumBasedExpression(
            EnumBasedExpressionType newEnumBasedExpression) {
        if (newEnumBasedExpression != enumBasedExpression) {
            NotificationChain msgs = null;
            if (enumBasedExpression != null)
                msgs =
                        ((InternalEObject) enumBasedExpression)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION,
                                        null,
                                        msgs);
            if (newEnumBasedExpression != null)
                msgs =
                        ((InternalEObject) newEnumBasedExpression)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION,
                                        null,
                                        msgs);
            msgs = basicSetEnumBasedExpression(newEnumBasedExpression, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION,
                    newEnumBasedExpression, newEnumBasedExpression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getScriptExpression() {
        return scriptExpression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptExpression(String newScriptExpression) {
        String oldScriptExpression = scriptExpression;
        scriptExpression = newScriptExpression;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.EXPRESSION_TYPE__SCRIPT_EXPRESSION,
                    oldScriptExpression, scriptExpression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getStructuredExpression() {
        return structuredExpression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetStructuredExpression(
            EObject newStructuredExpression, NotificationChain msgs) {
        EObject oldStructuredExpression = structuredExpression;
        structuredExpression = newStructuredExpression;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                            oldStructuredExpression, newStructuredExpression);
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
    public void setStructuredExpression(EObject newStructuredExpression) {
        if (newStructuredExpression != structuredExpression) {
            NotificationChain msgs = null;
            if (structuredExpression != null)
                msgs =
                        ((InternalEObject) structuredExpression)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                                        null,
                                        msgs);
            if (newStructuredExpression != null)
                msgs =
                        ((InternalEObject) newStructuredExpression)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                                        null,
                                        msgs);
            msgs = basicSetStructuredExpression(newStructuredExpression, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                    newStructuredExpression, newStructuredExpression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getDefault() {
        return default_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDefault(EObject newDefault,
            NotificationChain msgs) {
        EObject oldDefault = default_;
        default_ = newDefault;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimulationPackage.EXPRESSION_TYPE__DEFAULT,
                            oldDefault, newDefault);
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
    public void setDefault(EObject newDefault) {
        if (newDefault != default_) {
            NotificationChain msgs = null;
            if (default_ != null)
                msgs =
                        ((InternalEObject) default_)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.EXPRESSION_TYPE__DEFAULT,
                                        null,
                                        msgs);
            if (newDefault != null)
                msgs =
                        ((InternalEObject) newDefault)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.EXPRESSION_TYPE__DEFAULT,
                                        null,
                                        msgs);
            msgs = basicSetDefault(newDefault, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.EXPRESSION_TYPE__DEFAULT, newDefault,
                    newDefault));
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
        case SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION:
            return basicSetEnumBasedExpression(null, msgs);
        case SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION:
            return basicSetStructuredExpression(null, msgs);
        case SimulationPackage.EXPRESSION_TYPE__DEFAULT:
            return basicSetDefault(null, msgs);
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
        case SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION:
            return getEnumBasedExpression();
        case SimulationPackage.EXPRESSION_TYPE__SCRIPT_EXPRESSION:
            return getScriptExpression();
        case SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION:
            return getStructuredExpression();
        case SimulationPackage.EXPRESSION_TYPE__DEFAULT:
            return getDefault();
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
        case SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION:
            setEnumBasedExpression((EnumBasedExpressionType) newValue);
            return;
        case SimulationPackage.EXPRESSION_TYPE__SCRIPT_EXPRESSION:
            setScriptExpression((String) newValue);
            return;
        case SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION:
            setStructuredExpression((EObject) newValue);
            return;
        case SimulationPackage.EXPRESSION_TYPE__DEFAULT:
            setDefault((EObject) newValue);
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
        case SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION:
            setEnumBasedExpression((EnumBasedExpressionType) null);
            return;
        case SimulationPackage.EXPRESSION_TYPE__SCRIPT_EXPRESSION:
            setScriptExpression(SCRIPT_EXPRESSION_EDEFAULT);
            return;
        case SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION:
            setStructuredExpression((EObject) null);
            return;
        case SimulationPackage.EXPRESSION_TYPE__DEFAULT:
            setDefault((EObject) null);
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
        case SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION:
            return enumBasedExpression != null;
        case SimulationPackage.EXPRESSION_TYPE__SCRIPT_EXPRESSION:
            return SCRIPT_EXPRESSION_EDEFAULT == null ? scriptExpression != null
                    : !SCRIPT_EXPRESSION_EDEFAULT.equals(scriptExpression);
        case SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION:
            return structuredExpression != null;
        case SimulationPackage.EXPRESSION_TYPE__DEFAULT:
            return default_ != null;
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
        result.append(" (scriptExpression: "); //$NON-NLS-1$
        result.append(scriptExpression);
        result.append(')');
        return result.toString();
    }

} //ExpressionTypeImpl