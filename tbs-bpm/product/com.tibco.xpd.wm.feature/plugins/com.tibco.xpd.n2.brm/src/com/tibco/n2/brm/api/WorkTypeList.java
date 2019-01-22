/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.WorkType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Type List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Defines a 'page' of work type objects, which is a window over the entire set of work types defined in BRM.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkTypeList#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkTypeList#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkTypeList#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkTypeList()
 * @model extendedMetaData="name='WorkTypeList' kind='elementOnly'"
 * @generated
 */
public interface WorkTypeList extends EObject {
    /**
     * Returns the value of the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Position (in BRM's complete work type set) of the first work type in the WorkTypeList.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #setStartPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkTypeList_StartPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='startPosition'"
     * @generated
     */
    long getStartPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkTypeList#getStartPosition <em>Start Position</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkTypeList#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    void unsetStartPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkTypeList#getStartPosition <em>Start Position</em>}' attribute is set.
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
     * Position (in BRM's complete work type set) of the last work type in the WorkTypeList.
     * <!-- end-model-doc -->
     * @return the value of the '<em>End Position</em>' attribute.
     * @see #isSetEndPosition()
     * @see #unsetEndPosition()
     * @see #setEndPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkTypeList_EndPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='endPosition'"
     * @generated
     */
    long getEndPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkTypeList#getEndPosition <em>End Position</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkTypeList#getEndPosition <em>End Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEndPosition()
     * @see #getEndPosition()
     * @see #setEndPosition(long)
     * @generated
     */
    void unsetEndPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkTypeList#getEndPosition <em>End Position</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.datamodel.WorkType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Contents of the WorkTypeList (as an array of work types).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Types</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkTypeList_Types()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='types'"
     * @generated
     */
    EList<WorkType> getTypes();

} // WorkTypeList
