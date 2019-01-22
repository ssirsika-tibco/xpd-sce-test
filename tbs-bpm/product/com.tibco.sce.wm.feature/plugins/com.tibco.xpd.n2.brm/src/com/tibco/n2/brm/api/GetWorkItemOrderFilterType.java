/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Work Item Order Filter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterType#getLimitColumns <em>Limit Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkItemOrderFilterType()
 * @model extendedMetaData="name='getWorkItemOrderFilter_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkItemOrderFilterType extends EObject {
    /**
     * Returns the value of the '<em><b>Limit Columns</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of fields about which information should be returned. (Fields means the fields defined by BRM that can be used in sort/filter criteria expressions for work item lists.)
     * 
     * A value of 0 returns information about all fields.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Limit Columns</em>' attribute.
     * @see #isSetLimitColumns()
     * @see #unsetLimitColumns()
     * @see #setLimitColumns(short)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkItemOrderFilterType_LimitColumns()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
     *        extendedMetaData="kind='element' name='limitColumns'"
     * @generated
     */
    short getLimitColumns();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterType#getLimitColumns <em>Limit Columns</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Limit Columns</em>' attribute.
     * @see #isSetLimitColumns()
     * @see #unsetLimitColumns()
     * @see #getLimitColumns()
     * @generated
     */
    void setLimitColumns(short value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterType#getLimitColumns <em>Limit Columns</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLimitColumns()
     * @see #getLimitColumns()
     * @see #setLimitColumns(short)
     * @generated
     */
    void unsetLimitColumns();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterType#getLimitColumns <em>Limit Columns</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Limit Columns</em>' attribute is set.
     * @see #unsetLimitColumns()
     * @see #getLimitColumns()
     * @see #setLimitColumns(short)
     * @generated
     */
    boolean isSetLimitColumns();

} // GetWorkItemOrderFilterType
