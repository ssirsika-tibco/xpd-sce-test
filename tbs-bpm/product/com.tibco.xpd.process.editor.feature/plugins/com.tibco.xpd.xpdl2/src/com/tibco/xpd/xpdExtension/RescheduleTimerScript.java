/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reschedule Timer Script</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is optionally added under xpdl2:TriggerTimer and specifies 
 * how to recalculate expiry time of timer.
 * DurationRelativeTo specifies how to treat durations if they are returned by the
 * script (i.e. as relative to time of reschedule, or to eixsiting timeout for timer event)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RescheduleTimerScript#getDurationRelativeTo <em>Duration Relative To</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRescheduleTimerScript()
 * @model extendedMetaData="name='RescheduleTimerScript' namespace='##targetNamespace' kind='mixed'"
 * @generated
 */
public interface RescheduleTimerScript extends Expression {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Duration Relative To</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.RescheduleDurationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Duration Relative To</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Duration Relative To</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.RescheduleDurationType
     * @see #isSetDurationRelativeTo()
     * @see #unsetDurationRelativeTo()
     * @see #setDurationRelativeTo(RescheduleDurationType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRescheduleTimerScript_DurationRelativeTo()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='DurationRelativeTo'"
     * @generated
     */
    RescheduleDurationType getDurationRelativeTo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RescheduleTimerScript#getDurationRelativeTo <em>Duration Relative To</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Duration Relative To</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.RescheduleDurationType
     * @see #isSetDurationRelativeTo()
     * @see #unsetDurationRelativeTo()
     * @see #getDurationRelativeTo()
     * @generated
     */
    void setDurationRelativeTo(RescheduleDurationType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.RescheduleTimerScript#getDurationRelativeTo <em>Duration Relative To</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDurationRelativeTo()
     * @see #getDurationRelativeTo()
     * @see #setDurationRelativeTo(RescheduleDurationType)
     * @generated
     */
    void unsetDurationRelativeTo();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.RescheduleTimerScript#getDurationRelativeTo <em>Duration Relative To</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Duration Relative To</em>' attribute is set.
     * @see #unsetDurationRelativeTo()
     * @see #getDurationRelativeTo()
     * @see #setDurationRelativeTo(RescheduleDurationType)
     * @generated
     */
    boolean isSetDurationRelativeTo();

} // RescheduleTimerScript
