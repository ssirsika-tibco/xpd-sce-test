/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Work List Items For View Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Contains a page of work item returned from a user request.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getTotalItems <em>Total Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getWorkItems <em>Work Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getCustomData <em>Custom Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewResponseType()
 * @model extendedMetaData="name='getWorkListItemsForViewResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkListItemsForViewResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Position in the work item list of the first work item on this page.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #setStartPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewResponseType_StartPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='startPosition'"
     * @generated
     */
    long getStartPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @generated
     */
    void setStartPosition(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    void unsetStartPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getStartPosition <em>Start Position</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Position</em>' attribute is set.
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    boolean isSetStartPosition();

    /**
     * Returns the value of the '<em><b>End Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Position in the work item list of the last work item on this page.
     * <!-- end-model-doc -->
     * @return the value of the '<em>End Position</em>' attribute.
     * @see #isSetEndPosition()
     * @see #unsetEndPosition()
     * @see #setEndPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewResponseType_EndPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='endPosition'"
     * @generated
     */
    long getEndPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getEndPosition <em>End Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Position</em>' attribute.
     * @see #isSetEndPosition()
     * @see #unsetEndPosition()
     * @see #getEndPosition()
     * @generated
     */
    void setEndPosition(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getEndPosition <em>End Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEndPosition()
     * @see #getEndPosition()
     * @see #setEndPosition(long)
     * @generated
     */
    void unsetEndPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getEndPosition <em>End Position</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>End Position</em>' attribute is set.
     * @see #unsetEndPosition()
     * @see #getEndPosition()
     * @see #setEndPosition(long)
     * @generated
     */
    boolean isSetEndPosition();

    /**
     * Returns the value of the '<em><b>Total Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The value returned in this attribute depends on the value specified for the getTotalCount attribute in the request. If:
     * 
     * - getTotalCount was 'true', totalItems returns the total number of work items in the work item list.
     * 
     * - getTotalCount was 'false', totalItems returns -1 if the work item list contains at least one work item, or 0 (zero) if the work item list is empty.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Total Items</em>' attribute.
     * @see #isSetTotalItems()
     * @see #unsetTotalItems()
     * @see #setTotalItems(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewResponseType_TotalItems()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='totalItems'"
     * @generated
     */
    long getTotalItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getTotalItems <em>Total Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Total Items</em>' attribute.
     * @see #isSetTotalItems()
     * @see #unsetTotalItems()
     * @see #getTotalItems()
     * @generated
     */
    void setTotalItems(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getTotalItems <em>Total Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTotalItems()
     * @see #getTotalItems()
     * @see #setTotalItems(long)
     * @generated
     */
    void unsetTotalItems();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getTotalItems <em>Total Items</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Total Items</em>' attribute is set.
     * @see #unsetTotalItems()
     * @see #getTotalItems()
     * @see #setTotalItems(long)
     * @generated
     */
    boolean isSetTotalItems();

    /**
     * Returns the value of the '<em><b>Work Items</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkItem}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Complete details of each work item in this page of the work item  list.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Items</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewResponseType_WorkItems()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='workItems'"
     * @generated
     */
    EList<WorkItem> getWorkItems();

    /**
     * Returns the value of the '<em><b>Custom Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * A string attribute the caller can use to provide and custom data they wish to store with the work list view.  BRM will not look at or use this attribute value but will just store it with the work view definition.   If storing XML data the value should be wrapped in a CDATA section.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Custom Data</em>' attribute.
     * @see #setCustomData(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewResponseType_CustomData()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='customData'"
     * @generated
     */
    String getCustomData();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getCustomData <em>Custom Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Custom Data</em>' attribute.
     * @see #getCustomData()
     * @generated
     */
    void setCustomData(String value);

} // GetWorkListItemsForViewResponseType
