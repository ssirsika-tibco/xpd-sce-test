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
 * A representation of the model object '<em><b>Item Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Work item body definition, containing each data field (as a name/value pair) required by the data model defined in the work item's work type. 
 * 
 * 				The associated work type specifies each data field's type and whether it is an INPUT, OUTPUT or INOUT parameter.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ItemBody#getParameter <em>Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getItemBody()
 * @model extendedMetaData="name='ItemBody' kind='elementOnly'"
 * @generated
 */
public interface ItemBody extends EObject {
    /**
     * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.ParameterType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the data fields associated with this work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Parameter</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemBody_Parameter()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='parameter'"
     * @generated
     */
    EList<ParameterType> getParameter();

} // ItemBody
