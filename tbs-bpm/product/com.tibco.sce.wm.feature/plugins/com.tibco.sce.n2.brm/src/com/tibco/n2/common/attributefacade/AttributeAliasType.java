/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Alias Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeAlias <em>Attribute Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeName <em>Attribute Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.attributefacade.AttributefacadePackage#getAttributeAliasType()
 * @model extendedMetaData="name='AttributeAliasType' kind='empty'"
 * @generated
 */
public interface AttributeAliasType extends EObject {
    /**
     * Returns the value of the '<em><b>Attribute Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Alias to be used for this Work Item attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute Alias</em>' attribute.
     * @see #setAttributeAlias(Object)
     * @see com.tibco.n2.common.attributefacade.AttributefacadePackage#getAttributeAliasType_AttributeAlias()
     * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
     *        extendedMetaData="kind='attribute' name='attributeAlias'"
     * @generated
     */
    Object getAttributeAlias();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeAlias <em>Attribute Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Alias</em>' attribute.
     * @see #getAttributeAlias()
     * @generated
     */
    void setAttributeAlias(Object value);

    /**
     * Returns the value of the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Real name of a Work Item attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute Name</em>' attribute.
     * @see #setAttributeName(Object)
     * @see com.tibco.n2.common.attributefacade.AttributefacadePackage#getAttributeAliasType_AttributeName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType" required="true"
     *        extendedMetaData="kind='attribute' name='attributeName'"
     * @generated
     */
    Object getAttributeName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeName <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Name</em>' attribute.
     * @see #getAttributeName()
     * @generated
     */
    void setAttributeName(Object value);

} // AttributeAliasType
