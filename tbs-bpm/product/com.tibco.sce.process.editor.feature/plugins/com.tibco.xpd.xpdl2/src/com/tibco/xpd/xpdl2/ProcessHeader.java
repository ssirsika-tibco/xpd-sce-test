/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessHeader#getCreated <em>Created</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessHeader#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessHeader#getLimit <em>Limit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessHeader#getValidFrom <em>Valid From</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessHeader#getValidTo <em>Valid To</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessHeader#getTimeEstimation <em>Time Estimation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessHeader#getDurationUnit <em>Duration Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader()
 * @model extendedMetaData="name='ProcessHeader_._type' kind='elementOnly' features-order='created description priority limit validFrom validTo timeEstimation'"
 * @generated
 */
public interface ProcessHeader extends DescribedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Created</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Created</em>' attribute.
     * @see #setCreated(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader_Created()
     * @model extendedMetaData="kind='element' name='Created' namespace='##targetNamespace'"
     * @generated
     */
    String getCreated();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getCreated <em>Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Created</em>' attribute.
     * @see #getCreated()
     * @generated
     */
    void setCreated(String value);

    /**
     * Returns the value of the '<em><b>Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Priority</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Priority</em>' containment reference.
     * @see #setPriority(Priority)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader_Priority()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Priority' namespace='##targetNamespace'"
     * @generated
     */
    Priority getPriority();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getPriority <em>Priority</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Priority</em>' containment reference.
     * @see #getPriority()
     * @generated
     */
    void setPriority(Priority value);

    /**
     * Returns the value of the '<em><b>Limit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Limit</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Limit</em>' containment reference.
     * @see #setLimit(Limit)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader_Limit()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Limit' namespace='##targetNamespace'"
     * @generated
     */
    Limit getLimit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getLimit <em>Limit</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Limit</em>' containment reference.
     * @see #getLimit()
     * @generated
     */
    void setLimit(Limit value);

    /**
     * Returns the value of the '<em><b>Valid From</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Valid From</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Valid From</em>' containment reference.
     * @see #setValidFrom(ValidFrom)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader_ValidFrom()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ValidFrom' namespace='##targetNamespace'"
     * @generated
     */
    ValidFrom getValidFrom();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getValidFrom <em>Valid From</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Valid From</em>' containment reference.
     * @see #getValidFrom()
     * @generated
     */
    void setValidFrom(ValidFrom value);

    /**
     * Returns the value of the '<em><b>Valid To</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Valid To</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Valid To</em>' containment reference.
     * @see #setValidTo(ValidTo)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader_ValidTo()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ValidTo' namespace='##targetNamespace'"
     * @generated
     */
    ValidTo getValidTo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getValidTo <em>Valid To</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Valid To</em>' containment reference.
     * @see #getValidTo()
     * @generated
     */
    void setValidTo(ValidTo value);

    /**
     * Returns the value of the '<em><b>Time Estimation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Estimation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Estimation</em>' containment reference.
     * @see #setTimeEstimation(TimeEstimation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader_TimeEstimation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TimeEstimation' namespace='##targetNamespace'"
     * @generated
     */
    TimeEstimation getTimeEstimation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getTimeEstimation <em>Time Estimation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Estimation</em>' containment reference.
     * @see #getTimeEstimation()
     * @generated
     */
    void setTimeEstimation(TimeEstimation value);

    /**
     * Returns the value of the '<em><b>Duration Unit</b></em>' attribute.
     * The default value is <code>"Y"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.DurationUnitType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Duration Unit</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Duration Unit</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DurationUnitType
     * @see #isSetDurationUnit()
     * @see #unsetDurationUnit()
     * @see #setDurationUnit(DurationUnitType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessHeader_DurationUnit()
     * @model default="Y" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='DurationUnit'"
     * @generated
     */
    DurationUnitType getDurationUnit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getDurationUnit <em>Duration Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Duration Unit</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DurationUnitType
     * @see #isSetDurationUnit()
     * @see #unsetDurationUnit()
     * @see #getDurationUnit()
     * @generated
     */
    void setDurationUnit(DurationUnitType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getDurationUnit <em>Duration Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDurationUnit()
     * @see #getDurationUnit()
     * @see #setDurationUnit(DurationUnitType)
     * @generated
     */
    void unsetDurationUnit();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.ProcessHeader#getDurationUnit <em>Duration Unit</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Duration Unit</em>' attribute is set.
     * @see #unsetDurationUnit()
     * @see #getDurationUnit()
     * @see #setDurationUnit(DurationUnitType)
     * @generated
     */
    boolean isSetDurationUnit();

} // ProcessHeader
