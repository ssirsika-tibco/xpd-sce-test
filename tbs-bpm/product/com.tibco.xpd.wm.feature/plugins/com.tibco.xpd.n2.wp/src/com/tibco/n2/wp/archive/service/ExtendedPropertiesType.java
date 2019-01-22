/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extended Properties Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.ExtendedPropertiesType#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getExtendedPropertiesType()
 * @model extendedMetaData="name='extendedPropertiesType' kind='elementOnly'"
 * @generated
 */
public interface ExtendedPropertiesType extends EObject {
    /**
     * Returns the value of the '<em><b>Property</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.wp.archive.service.PropertyType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Property</em>' containment reference list.
     * @see com.tibco.n2.wp.archive.service.WPPackage#getExtendedPropertiesType_Property()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace'"
     * @generated
     */
    EList<PropertyType> getProperty();

} // ExtendedPropertiesType
