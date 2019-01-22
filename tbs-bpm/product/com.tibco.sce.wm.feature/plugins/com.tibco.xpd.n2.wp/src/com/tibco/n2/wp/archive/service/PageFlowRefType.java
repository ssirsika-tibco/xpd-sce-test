/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page Flow Ref Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This is type represents the reference to the pageflow element.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowRefType#getRefId <em>Ref Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowRefType()
 * @model extendedMetaData="name='pageFlowRefType' kind='empty'"
 * @generated
 */
public interface PageFlowRefType extends EObject {
    /**
     * Returns the value of the '<em><b>Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Ref Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Ref Id</em>' attribute.
     * @see #setRefId(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowRefType_RefId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='refId'"
     * @generated
     */
    String getRefId();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageFlowRefType#getRefId <em>Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ref Id</em>' attribute.
     * @see #getRefId()
     * @generated
     */
    void setRefId(String value);

} // PageFlowRefType
