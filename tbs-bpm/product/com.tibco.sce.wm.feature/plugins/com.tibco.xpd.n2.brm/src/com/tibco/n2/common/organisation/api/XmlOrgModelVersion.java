/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Org Model Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Major version number of the organization model.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlOrgModelVersion#getModelVersion <em>Model Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlOrgModelVersion()
 * @model extendedMetaData="name='XmlOrgModelVersion' kind='empty'"
 * @generated
 */
public interface XmlOrgModelVersion extends EObject {
    /**
     * Returns the value of the '<em><b>Model Version</b></em>' attribute.
     * The default value is <code>"-1"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Major version number of the organization model in which an organization model entity resides. 
     * 
     * If not specified, the default value will be the latest version of the organization model.
     * 
     * (Version numbers must be compatible with, and conform to, the OSGi version number schema. For Directory Engine, only the major part of the version number is significant.)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Model Version</em>' attribute.
     * @see #isSetModelVersion()
     * @see #unsetModelVersion()
     * @see #setModelVersion(int)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlOrgModelVersion_ModelVersion()
     * @model default="-1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='model-version'"
     * @generated
     */
    int getModelVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlOrgModelVersion#getModelVersion <em>Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Model Version</em>' attribute.
     * @see #isSetModelVersion()
     * @see #unsetModelVersion()
     * @see #getModelVersion()
     * @generated
     */
    void setModelVersion(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.organisation.api.XmlOrgModelVersion#getModelVersion <em>Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetModelVersion()
     * @see #getModelVersion()
     * @see #setModelVersion(int)
     * @generated
     */
    void unsetModelVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.organisation.api.XmlOrgModelVersion#getModelVersion <em>Model Version</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Model Version</em>' attribute is set.
     * @see #unsetModelVersion()
     * @see #getModelVersion()
     * @see #setModelVersion(int)
     * @generated
     */
    boolean isSetModelVersion();

} // XmlOrgModelVersion
