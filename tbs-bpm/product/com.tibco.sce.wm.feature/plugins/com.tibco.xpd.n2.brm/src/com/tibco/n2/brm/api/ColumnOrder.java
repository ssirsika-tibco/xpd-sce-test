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
 * A representation of the model object '<em><b>Column Order</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Details of the sort order of a field defined by BRM that can be used in sort/filter criteria expressions for work item lists.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ColumnOrder#getColumnDef <em>Column Def</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ColumnOrder#isAscending <em>Ascending</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnOrder()
 * @model extendedMetaData="name='ColumnOrder' kind='elementOnly'"
 * @generated
 */
public interface ColumnOrder extends EObject {
    /**
     * Returns the value of the '<em><b>Column Def</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Column Def</em>' containment reference.
     * @see #setColumnDef(ColumnDefinition)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnOrder_ColumnDef()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='columnDef'"
     * @generated
     */
    ColumnDefinition getColumnDef();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ColumnOrder#getColumnDef <em>Column Def</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Column Def</em>' containment reference.
     * @see #getColumnDef()
     * @generated
     */
    void setColumnDef(ColumnDefinition value);

    /**
     * Returns the value of the '<em><b>Ascending</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean value defining whether the column should be sorted in ascending (TRUE) or descending (FALSE) order.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ascending</em>' attribute.
     * @see #isSetAscending()
     * @see #unsetAscending()
     * @see #setAscending(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnOrder_Ascending()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='ascending'"
     * @generated
     */
    boolean isAscending();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ColumnOrder#isAscending <em>Ascending</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ascending</em>' attribute.
     * @see #isSetAscending()
     * @see #unsetAscending()
     * @see #isAscending()
     * @generated
     */
    void setAscending(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ColumnOrder#isAscending <em>Ascending</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAscending()
     * @see #isAscending()
     * @see #setAscending(boolean)
     * @generated
     */
    void unsetAscending();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ColumnOrder#isAscending <em>Ascending</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Ascending</em>' attribute is set.
     * @see #unsetAscending()
     * @see #isAscending()
     * @see #setAscending(boolean)
     * @generated
     */
    boolean isSetAscending();

} // ColumnOrder
