/**
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Retry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Retry Configuration element used by xpdl:Activity to specify retry options.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.Retry#getMax <em>Max</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.Retry#getInitialPeriod <em>Initial Period</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.Retry#getPeriodIncrement <em>Period Increment</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.Retry#getMaxRetryAction <em>Max Retry Action</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRetry()
 * @model extendedMetaData="name='Retry' kind='elementOnly'"
 * @generated
 */
public interface Retry extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Max</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max</em>' attribute.
     * @see #isSetMax()
     * @see #unsetMax()
     * @see #setMax(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRetry_Max()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='Max'"
     * @generated
     */
    int getMax();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getMax <em>Max</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max</em>' attribute.
     * @see #isSetMax()
     * @see #unsetMax()
     * @see #getMax()
     * @generated
     */
    void setMax(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getMax <em>Max</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMax()
     * @see #getMax()
     * @see #setMax(int)
     * @generated
     */
    void unsetMax();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getMax <em>Max</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Max</em>' attribute is set.
     * @see #unsetMax()
     * @see #getMax()
     * @see #setMax(int)
     * @generated
     */
    boolean isSetMax();

    /**
     * Returns the value of the '<em><b>Initial Period</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Initial Period</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Initial Period</em>' attribute.
     * @see #isSetInitialPeriod()
     * @see #unsetInitialPeriod()
     * @see #setInitialPeriod(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRetry_InitialPeriod()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='InitialPeriod'"
     * @generated
     */
    int getInitialPeriod();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getInitialPeriod <em>Initial Period</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Initial Period</em>' attribute.
     * @see #isSetInitialPeriod()
     * @see #unsetInitialPeriod()
     * @see #getInitialPeriod()
     * @generated
     */
    void setInitialPeriod(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getInitialPeriod <em>Initial Period</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetInitialPeriod()
     * @see #getInitialPeriod()
     * @see #setInitialPeriod(int)
     * @generated
     */
    void unsetInitialPeriod();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getInitialPeriod <em>Initial Period</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Initial Period</em>' attribute is set.
     * @see #unsetInitialPeriod()
     * @see #getInitialPeriod()
     * @see #setInitialPeriod(int)
     * @generated
     */
    boolean isSetInitialPeriod();

    /**
     * Returns the value of the '<em><b>Period Increment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Period Increment</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Period Increment</em>' attribute.
     * @see #isSetPeriodIncrement()
     * @see #unsetPeriodIncrement()
     * @see #setPeriodIncrement(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRetry_PeriodIncrement()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='PeriodIncrement'"
     * @generated
     */
    int getPeriodIncrement();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getPeriodIncrement <em>Period Increment</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Period Increment</em>' attribute.
     * @see #isSetPeriodIncrement()
     * @see #unsetPeriodIncrement()
     * @see #getPeriodIncrement()
     * @generated
     */
    void setPeriodIncrement(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getPeriodIncrement <em>Period Increment</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPeriodIncrement()
     * @see #getPeriodIncrement()
     * @see #setPeriodIncrement(int)
     * @generated
     */
    void unsetPeriodIncrement();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getPeriodIncrement <em>Period Increment</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Period Increment</em>' attribute is set.
     * @see #unsetPeriodIncrement()
     * @see #getPeriodIncrement()
     * @see #setPeriodIncrement(int)
     * @generated
     */
    boolean isSetPeriodIncrement();

    /**
     * Returns the value of the '<em><b>Max Retry Action</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.MaxRetryActionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max Retry Action</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max Retry Action</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.MaxRetryActionType
     * @see #isSetMaxRetryAction()
     * @see #unsetMaxRetryAction()
     * @see #setMaxRetryAction(MaxRetryActionType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRetry_MaxRetryAction()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='MaxRetryAction'"
     * @generated
     */
    MaxRetryActionType getMaxRetryAction();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getMaxRetryAction <em>Max Retry Action</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Retry Action</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.MaxRetryActionType
     * @see #isSetMaxRetryAction()
     * @see #unsetMaxRetryAction()
     * @see #getMaxRetryAction()
     * @generated
     */
    void setMaxRetryAction(MaxRetryActionType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getMaxRetryAction <em>Max Retry Action</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaxRetryAction()
     * @see #getMaxRetryAction()
     * @see #setMaxRetryAction(MaxRetryActionType)
     * @generated
     */
    void unsetMaxRetryAction();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.Retry#getMaxRetryAction <em>Max Retry Action</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Max Retry Action</em>' attribute is set.
     * @see #unsetMaxRetryAction()
     * @see #getMaxRetryAction()
     * @see #setMaxRetryAction(MaxRetryActionType)
     * @generated
     */
    boolean isSetMaxRetryAction();

} // Retry
