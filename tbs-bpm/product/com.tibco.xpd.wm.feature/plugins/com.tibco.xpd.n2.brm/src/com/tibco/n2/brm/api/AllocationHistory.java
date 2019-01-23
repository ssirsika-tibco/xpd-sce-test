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
 * A representation of the model object '<em><b>Allocation History</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Contains single allocation history record
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AllocationHistory#getResourceID <em>Resource ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationDate <em>Allocation Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationID <em>Allocation ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocationHistory()
 * @model abstract="true"
 *        extendedMetaData="name='AllocationHistory' kind='elementOnly'"
 * @generated
 */
public interface AllocationHistory extends EObject {
    /**
     * Returns the value of the '<em><b>Resource ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource ID</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource ID</em>' attribute.
     * @see #setResourceID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocationHistory_ResourceID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='resourceID'"
     * @generated
     */
    String getResourceID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AllocationHistory#getResourceID <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource ID</em>' attribute.
     * @see #getResourceID()
     * @generated
     */
    void setResourceID(String value);

    /**
     * Returns the value of the '<em><b>Allocation Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Allocation Date</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Allocation Date</em>' attribute.
     * @see #setAllocationDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocationHistory_AllocationDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
     *        extendedMetaData="kind='attribute' name='allocationDate'"
     * @generated
     */
    XMLGregorianCalendar getAllocationDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationDate <em>Allocation Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocation Date</em>' attribute.
     * @see #getAllocationDate()
     * @generated
     */
    void setAllocationDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Allocation ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Allocation ID</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Allocation ID</em>' attribute.
     * @see #isSetAllocationID()
     * @see #unsetAllocationID()
     * @see #setAllocationID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocationHistory_AllocationID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='allocationID'"
     * @generated
     */
    long getAllocationID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationID <em>Allocation ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocation ID</em>' attribute.
     * @see #isSetAllocationID()
     * @see #unsetAllocationID()
     * @see #getAllocationID()
     * @generated
     */
    void setAllocationID(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationID <em>Allocation ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAllocationID()
     * @see #getAllocationID()
     * @see #setAllocationID(long)
     * @generated
     */
    void unsetAllocationID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationID <em>Allocation ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Allocation ID</em>' attribute is set.
     * @see #unsetAllocationID()
     * @see #getAllocationID()
     * @see #setAllocationID(long)
     * @generated
     */
    boolean isSetAllocationID();

} // AllocationHistory
