/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameter#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameter#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameter#getConfigParameterInfo <em>Config Parameter Info</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameter()
 * @model
 * @generated
 */
public interface ConfigParameter extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Key</em>' attribute.
     * @see #setKey(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameter_Key()
     * @model
     * @generated
     */
    String getKey();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameter#getKey <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Key</em>' attribute.
     * @see #getKey()
     * @generated
     */
    void setKey(String value);

    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Value</em>' attribute.
     * @see #isSetValue()
     * @see #unsetValue()
     * @see #setValue(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameter_Value()
     * @model default="" unsettable="true"
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameter#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #isSetValue()
     * @see #unsetValue()
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.deploy.ConfigParameter#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetValue()
     * @see #getValue()
     * @see #setValue(String)
     * @generated
     */
    void unsetValue();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.deploy.ConfigParameter#getValue <em>Value</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Value</em>' attribute is set.
     * @see #unsetValue()
     * @see #getValue()
     * @see #setValue(String)
     * @generated
     */
    boolean isSetValue();

    /**
     * Returns the value of the '<em><b>Config Parameter Info</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Config Parameter Info</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Config Parameter Info</em>' reference.
     * @see #setConfigParameterInfo(ConfigParameterInfo)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameter_ConfigParameterInfo()
     * @model transient="true"
     * @generated
     */
    ConfigParameterInfo getConfigParameterInfo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameter#getConfigParameterInfo <em>Config Parameter Info</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Config Parameter Info</em>' reference.
     * @see #getConfigParameterInfo()
     * @generated
     */
    void setConfigParameterInfo(ConfigParameterInfo value);

} // ConfigParameter
