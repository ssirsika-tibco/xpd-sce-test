/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.AliasType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Alias List Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AttributeAliasListType#getAttributeAlias <em>Attribute Alias</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAttributeAliasListType()
 * @model extendedMetaData="name='AttributeAliasList_._type' kind='elementOnly'"
 * @generated
 */
public interface AttributeAliasListType extends EObject {
    /**
     * Returns the value of the '<em><b>Attribute Alias</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.datamodel.AliasType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Alias</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute Alias</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAttributeAliasListType_AttributeAlias()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='AttributeAlias'"
     * @generated
     */
    EList<AliasType> getAttributeAlias();

} // AttributeAliasListType
