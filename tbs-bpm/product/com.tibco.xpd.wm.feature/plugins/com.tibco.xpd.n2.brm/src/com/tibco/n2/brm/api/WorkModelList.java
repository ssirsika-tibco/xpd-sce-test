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
 * A representation of the model object '<em><b>Work Model List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a 'page' of work model objects, which is a window over the entire set of work models defined in BRM.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelList#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelList#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelList#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelList()
 * @model extendedMetaData="name='WorkModelList' kind='elementOnly'"
 * @generated
 */
public interface WorkModelList extends EObject {
    /**
     * Returns the value of the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The starting position of the first work model in the requested page.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #setStartPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelList_StartPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='startPosition'"
     * @generated
     */
    long getStartPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelList#getStartPosition <em>Start Position</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkModelList#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    void unsetStartPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkModelList#getStartPosition <em>Start Position</em>}' attribute is set.
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
     * The end position of the last work model in the requested page.
     * <!-- end-model-doc -->
     * @return the value of the '<em>End Position</em>' attribute.
     * @see #isSetEndPosition()
     * @see #unsetEndPosition()
     * @see #setEndPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelList_EndPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='endPosition'"
     * @generated
     */
    long getEndPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelList#getEndPosition <em>End Position</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkModelList#getEndPosition <em>End Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEndPosition()
     * @see #getEndPosition()
     * @see #setEndPosition(long)
     * @generated
     */
    void unsetEndPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkModelList#getEndPosition <em>End Position</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkModel}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Array of work models that constitute the page.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Type</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelList_Type()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='type'"
     * @generated
     */
    EList<WorkModel> getType();

} // WorkModelList
