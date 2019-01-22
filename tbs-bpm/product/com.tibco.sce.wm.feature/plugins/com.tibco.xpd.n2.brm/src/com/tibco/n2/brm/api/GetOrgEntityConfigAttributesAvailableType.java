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
 * A representation of the model object '<em><b>Get Org Entity Config Attributes Available Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getStartAt <em>Start At</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getNumToReturn <em>Num To Return</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesAvailableType()
 * @model extendedMetaData="name='getOrgEntityConfigAttributesAvailable_._type' kind='elementOnly'"
 * @generated
 */
public interface GetOrgEntityConfigAttributesAvailableType extends EObject {
    /**
     * Returns the value of the '<em><b>Start At</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Position from which to start listing configuration attributes.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start At</em>' attribute.
     * @see #isSetStartAt()
     * @see #unsetStartAt()
     * @see #setStartAt(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesAvailableType_StartAt()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='startAt'"
     * @generated
     */
    int getStartAt();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getStartAt <em>Start At</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start At</em>' attribute.
     * @see #isSetStartAt()
     * @see #unsetStartAt()
     * @see #getStartAt()
     * @generated
     */
    void setStartAt(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getStartAt <em>Start At</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartAt()
     * @see #getStartAt()
     * @see #setStartAt(int)
     * @generated
     */
    void unsetStartAt();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getStartAt <em>Start At</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start At</em>' attribute is set.
     * @see #unsetStartAt()
     * @see #getStartAt()
     * @see #setStartAt(int)
     * @generated
     */
    boolean isSetStartAt();

    /**
     * Returns the value of the '<em><b>Num To Return</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of configuration attributes to list.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Num To Return</em>' attribute.
     * @see #isSetNumToReturn()
     * @see #unsetNumToReturn()
     * @see #setNumToReturn(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesAvailableType_NumToReturn()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='numToReturn'"
     * @generated
     */
    int getNumToReturn();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getNumToReturn <em>Num To Return</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Num To Return</em>' attribute.
     * @see #isSetNumToReturn()
     * @see #unsetNumToReturn()
     * @see #getNumToReturn()
     * @generated
     */
    void setNumToReturn(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getNumToReturn <em>Num To Return</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNumToReturn()
     * @see #getNumToReturn()
     * @see #setNumToReturn(int)
     * @generated
     */
    void unsetNumToReturn();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getNumToReturn <em>Num To Return</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Num To Return</em>' attribute is set.
     * @see #unsetNumToReturn()
     * @see #getNumToReturn()
     * @see #setNumToReturn(int)
     * @generated
     */
    boolean isSetNumToReturn();

} // GetOrgEntityConfigAttributesAvailableType
