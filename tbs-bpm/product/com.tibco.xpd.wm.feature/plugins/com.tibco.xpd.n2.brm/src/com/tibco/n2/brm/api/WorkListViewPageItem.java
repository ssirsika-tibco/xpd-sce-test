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
 * A representation of the model object '<em><b>Work List View Page Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a work list view used for paginated lists.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewPageItem#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewPageItem#getModificationDate <em>Modification Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewPageItem#getWorkViewID <em>Work View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewPageItem()
 * @model extendedMetaData="name='WorkListViewPageItem' kind='elementOnly'"
 * @generated
 */
public interface WorkListViewPageItem extends WorkListViewCommon {
    /**
     * Returns the value of the '<em><b>Creation Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The date the work view was created.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Creation Date</em>' attribute.
     * @see #setCreationDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewPageItem_CreationDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
     *        extendedMetaData="kind='attribute' name='creationDate'"
     * @generated
     */
    XMLGregorianCalendar getCreationDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getCreationDate <em>Creation Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Creation Date</em>' attribute.
     * @see #getCreationDate()
     * @generated
     */
    void setCreationDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Modification Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The date the work view was last modified.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Modification Date</em>' attribute.
     * @see #setModificationDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewPageItem_ModificationDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
     *        extendedMetaData="kind='attribute' name='modificationDate'"
     * @generated
     */
    XMLGregorianCalendar getModificationDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getModificationDate <em>Modification Date</em>}' attribute.
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewPageItem_WorkViewID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='workViewID'"
     * @generated
     */
    long getWorkViewID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getWorkViewID <em>Work View ID</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getWorkViewID <em>Work View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWorkViewID()
     * @see #getWorkViewID()
     * @see #setWorkViewID(long)
     * @generated
     */
    void unsetWorkViewID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getWorkViewID <em>Work View ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Work View ID</em>' attribute is set.
     * @see #unsetWorkViewID()
     * @see #getWorkViewID()
     * @see #setWorkViewID(long)
     * @generated
     */
    boolean isSetWorkViewID();

} // WorkListViewPageItem
