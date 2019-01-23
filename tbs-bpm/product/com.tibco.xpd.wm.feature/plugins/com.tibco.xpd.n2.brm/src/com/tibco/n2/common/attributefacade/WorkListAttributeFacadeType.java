/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work List Attribute Facade Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of the Work Item Attribute facade.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.attributefacade.AttributefacadePackage#getWorkListAttributeFacadeType()
 * @model extendedMetaData="name='WorkListAttributeFacadeType' kind='elementOnly'"
 * @generated
 */
public interface WorkListAttributeFacadeType extends EObject {
    /**
     * Returns the value of the '<em><b>Alias</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.attributefacade.AttributeAliasType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * List of aliases for this Work Item Attribute Facade.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Alias</em>' containment reference list.
     * @see com.tibco.n2.common.attributefacade.AttributefacadePackage#getWorkListAttributeFacadeType_Alias()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='alias'"
     * @generated
     */
    EList<AttributeAliasType> getAlias();

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.n2.common.attributefacade.AttributefacadePackage#getWorkListAttributeFacadeType_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // WorkListAttributeFacadeType
