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
 * A representation of the model object '<em><b>Work List View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a full work list view object.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkListView#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListView#getLocker <em>Locker</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListView#getModificationDate <em>Modification Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListView#getWorkViewID <em>Work View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListView()
 * @model extendedMetaData="name='WorkListView' kind='elementOnly'"
 * @generated
 */
public interface WorkListView extends WorkListViewEdit {
    /**
     * Returns the value of the '<em><b>Creation Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The date the work view was created.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Creation Date</em>' attribute.
     * @see #setCreationDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListView_CreationDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
     *        extendedMetaData="kind='attribute' name='creationDate'"
     * @generated
     */
    XMLGregorianCalendar getCreationDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListView#getCreationDate <em>Creation Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Creation Date</em>' attribute.
     * @see #getCreationDate()
     * @generated
     */
    void setCreationDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Locker</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The GUID of the resource that currently had this work list view locked for editing.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Locker</em>' attribute.
     * @see #setLocker(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListView_Locker()
     * @model dataType="com.tibco.n2.brm.api.LockerType"
     *        extendedMetaData="kind='attribute' name='locker'"
     * @generated
     */
    String getLocker();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListView#getLocker <em>Locker</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Locker</em>' attribute.
     * @see #getLocker()
     * @generated
     */
    void setLocker(String value);

    /**
     * Returns the value of the '<em><b>Modification Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The date the work view was last modified.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Modification Date</em>' attribute.
     * @see #setModificationDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListView_ModificationDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
     *        extendedMetaData="kind='attribute' name='modificationDate'"
     * @generated
     */
    XMLGregorianCalendar getModificationDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListView#getModificationDate <em>Modification Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Modification Date</em>' attribute.
     * @see #getModificationDate()
     * @generated
     */
    void setModificationDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Work View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The uniique ID of the work view.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work View ID</em>' attribute.
     * @see #isSetWorkViewID()
     * @see #unsetWorkViewID()
     * @see #setWorkViewID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListView_WorkViewID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='workViewID'"
     * @generated
     */
    long getWorkViewID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListView#getWorkViewID <em>Work View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work View ID</em>' attribute.
     * @see #isSetWorkViewID()
     * @see #unsetWorkViewID()
     * @see #getWorkViewID()
     * @generated
     */
    void setWorkViewID(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkListView#getWorkViewID <em>Work View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWorkViewID()
     * @see #getWorkViewID()
     * @see #setWorkViewID(long)
     * @generated
     */
    void unsetWorkViewID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkListView#getWorkViewID <em>Work View ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Work View ID</em>' attribute is set.
     * @see #unsetWorkViewID()
     * @see #getWorkViewID()
     * @see #setWorkViewID(long)
     * @generated
     */
    boolean isSetWorkViewID();

} // WorkListView
