/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter Dependent Distribution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.ParameterDependentDistributionImpl#getBasicDistribution <em>Basic Distribution</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ParameterDependentDistributionImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParameterDependentDistributionImpl extends EObjectImpl implements
        ParameterDependentDistribution {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getBasicDistribution() <em>Basic Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBasicDistribution()
     * @generated
     * @ordered
     */
    protected AbstractBasicDistribution basicDistribution;

    /**
     * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected ExpressionType expression;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ParameterDependentDistributionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.PARAMETER_DEPENDENT_DISTRIBUTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AbstractBasicDistribution getBasicDistribution() {
        return basicDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBasicDistribution(
            AbstractBasicDistribution newBasicDistribution,
            NotificationChain msgs) {
        AbstractBasicDistribution oldBasicDistribution = basicDistribution;
        basicDistribution = newBasicDistribution;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION,
                            oldBasicDistribution, newBasicDistribution);
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
    public void setBasicDistribution(
            AbstractBasicDistribution newBasicDistribution) {
        if (newBasicDistribution != basicDistribution) {
            NotificationChain msgs = null;
            if (basicDistribution != null)
                msgs =
                        ((InternalEObject) basicDistribution)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION,
                                        null,
                                        msgs);
            if (newBasicDistribution != null)
                msgs =
                        ((InternalEObject) newBasicDistribution)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION,
                                        null,
                                        msgs);
            msgs = basicSetBasicDistribution(newBasicDistribution, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION,
                    newBasicDistribution, newBasicDistribution));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExpressionType getExpression() {
        return expression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExpression(ExpressionType newExpression,
            NotificationChain msgs) {
        ExpressionType oldExpression = expression;
        expression = newExpression;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION,
                            oldExpression, newExpression);
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
    public void setExpression(ExpressionType newExpression) {
        if (newExpression != expression) {
            NotificationChain msgs = null;
            if (expression != null)
                msgs =
                        ((InternalEObject) expression)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION,
                                        null,
                                        msgs);
            if (newExpression != null)
                msgs =
                        ((InternalEObject) newExpression)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION,
                                        null,
                                        msgs);
            msgs = basicSetExpression(newExpression, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION,
                    newExpression, newExpression));
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
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION:
            return basicSetBasicDistribution(null, msgs);
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION:
            return basicSetExpression(null, msgs);
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
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION:
            return getBasicDistribution();
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION:
            return getExpression();
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
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION:
            setBasicDistribution((AbstractBasicDistribution) newValue);
            return;
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION:
            setExpression((ExpressionType) newValue);
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
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION:
            setBasicDistribution((AbstractBasicDistribution) null);
            return;
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION:
            setExpression((ExpressionType) null);
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
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION:
            return basicDistribution != null;
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION:
            return expression != null;
        }
        return super.eIsSet(featureID);
    }

} //ParameterDependentDistributionImpl