/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Main information about a work item used in a work list.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemHeader#getFlags <em>Flags</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemHeader#getItemContext <em>Item Context</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemHeader#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemHeader#getStartDate <em>Start Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemHeader()
 * @model extendedMetaData="name='WorkItemHeader' kind='elementOnly'"
 * @generated
 */
public interface WorkItemHeader extends BaseItemInfo {
    /**
     * Returns the value of the '<em><b>Flags</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Various flags that can be set against a work item in a work list.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Flags</em>' containment reference.
     * @see #setFlags(WorkItemFlags)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemHeader_Flags()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='flags'"
     * @generated
     */
    WorkItemFlags getFlags();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemHeader#getFlags <em>Flags</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Flags</em>' containment reference.
     * @see #getFlags()
     * @generated
     */
    void setFlags(WorkItemFlags value);

    /**
     * Returns the value of the '<em><b>Item Context</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Context of the work item, as supplied by the application that scheduled the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Context</em>' containment reference.
     * @see #setItemContext(ItemContext)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemHeader_ItemContext()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='itemContext'"
     * @generated
     */
    ItemContext getItemContext();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemHeader#getItemContext <em>Item Context</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Context</em>' containment reference.
     * @see #getItemContext()
     * @generated
     */
    void setItemContext(ItemContext value);

    /**
     * Returns the value of the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Date / time by which work on this work item must be completed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>End Date</em>' attribute.
     * @see #setEndDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemHeader_EndDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='endDate'"
     * @generated
     */
    XMLGregorianCalendar getEndDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemHeader#getEndDate <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Date</em>' attribute.
     * @see #getEndDate()
     * @generated
     */
    void setEndDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Date / time from which work on this work item may start.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Date</em>' attribute.
     * @see #setStartDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemHeader_StartDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='startDate'"
     * @generated
     */
    XMLGregorianCalendar getStartDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemHeader#getStartDate <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Date</em>' attribute.
     * @see #getStartDate()
     * @generated
     */
    void setStartDate(XMLGregorianCalendar value);

} // WorkItemHeader
