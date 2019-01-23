/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hidden Period</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Information about how long the specified work item should remain hidden.
 * 
 * If a hiddenPeriod is specified the work item will transition to the PendHidden state, rather than Pended. Operations that work on the Pended state (e.g. reallocateWorkItem) cannot then access the work item until either of the following occurs.
 * 
 * - the hiddenPeriod expires.
 * 
 * - a further pendWorkItem operation is specified with a hiddenPeriod of 0. This cancels the current hiddenPeriod. (A negative duration or date that is in the past will have the same effect.)
 * 
 * The work item then transitions back to the Pended state.
 * 
 * NOTE: The duration of a hiddenPeriod can also be extended or reduced by issuing a further pendWorkItem operation with a new hiddenPeriod. The new hiddenPeriod overrides the existing hiddenPeriod and is calculated from the current date/time.
 * 
 * For example, suppose that a work item was pended at 2.30 with a hiddenPeriod of 30 minutes. If a further pendWorkItem is issued at 2.45 with:
 * 
 * - a hiddenPeriod of 2 hours, the work item will remain hidden until 4.45.
 * 
 * - a hiddenPeriod of 5 minutes, the work item will remain hidden until 2.50.
 * 
 * - a hiddenPeriod of 0, the work item will immediately transition to the Pended state.
 * 
 * - a hiddenPeriod of -5 minutes, the work item will immediately transition to the Pended state.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.HiddenPeriod#getHiddenDuration <em>Hidden Duration</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.HiddenPeriod#getVisibleDate <em>Visible Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getHiddenPeriod()
 * @model extendedMetaData="name='HiddenPeriod' kind='elementOnly'"
 * @generated
 */
public interface HiddenPeriod extends EObject {
    /**
     * Returns the value of the '<em><b>Hidden Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Duration that the work item is hidden.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Hidden Duration</em>' containment reference.
     * @see #setHiddenDuration(ItemDuration)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getHiddenPeriod_HiddenDuration()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='hiddenDuration'"
     * @generated
     */
    ItemDuration getHiddenDuration();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.HiddenPeriod#getHiddenDuration <em>Hidden Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Hidden Duration</em>' containment reference.
     * @see #getHiddenDuration()
     * @generated
     */
    void setHiddenDuration(ItemDuration value);

    /**
     * Returns the value of the '<em><b>Visible Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Fixed date/time when the work item becomes visible.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Visible Date</em>' attribute.
     * @see #setVisibleDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getHiddenPeriod_VisibleDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='element' name='visibleDate'"
     * @generated
     */
    XMLGregorianCalendar getVisibleDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.HiddenPeriod#getVisibleDate <em>Visible Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Visible Date</em>' attribute.
     * @see #getVisibleDate()
     * @generated
     */
    void setVisibleDate(XMLGregorianCalendar value);

} // HiddenPeriod
