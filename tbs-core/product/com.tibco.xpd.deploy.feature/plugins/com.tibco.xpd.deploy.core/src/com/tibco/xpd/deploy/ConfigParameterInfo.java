/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import java.util.Map;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config Parameter Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#getParameterType <em>Parameter Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#isRequired <em>Required</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#isAutomatic <em>Automatic</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ConfigParameterInfo#getFacets <em>Facets</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo()
 * @model
 * @generated
 */
public interface ConfigParameterInfo extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Key</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Key</em>' attribute.
     * @see #setKey(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_Key()
     * @model default="" required="true"
     * @generated
     */
    String getKey();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getKey <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Key</em>' attribute.
     * @see #getKey()
     * @generated
     */
    void setKey(String value);

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Label</em>' attribute.
     * @see #setLabel(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_Label()
     * @model default=""
     * @generated
     */
    String getLabel();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getLabel <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Label</em>' attribute.
     * @see #getLabel()
     * @generated
     */
    void setLabel(String value);

    /**
     * Returns the value of the '<em><b>Icon</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Icon</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Icon</em>' attribute.
     * @see #setIcon(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_Icon()
     * @model default=""
     * @generated
     */
    String getIcon();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getIcon <em>Icon</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Icon</em>' attribute.
     * @see #getIcon()
     * @generated
     */
    void setIcon(String value);

    /**
     * Returns the value of the '<em><b>Parameter Type</b></em>' attribute.
     * The default value is <code>""</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.deploy.ConfigParameterType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Type</em>' attribute.
     * @see com.tibco.xpd.deploy.ConfigParameterType
     * @see #setParameterType(ConfigParameterType)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_ParameterType()
     * @model default=""
     * @generated
     */
    ConfigParameterType getParameterType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getParameterType <em>Parameter Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Type</em>' attribute.
     * @see com.tibco.xpd.deploy.ConfigParameterType
     * @see #getParameterType()
     * @generated
     */
    void setParameterType(ConfigParameterType value);

    /**
     * Returns the value of the '<em><b>Default Value</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Value</em>' attribute.
     * @see #setDefaultValue(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_DefaultValue()
     * @model default=""
     * @generated
     */
    String getDefaultValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getDefaultValue <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Value</em>' attribute.
     * @see #getDefaultValue()
     * @generated
     */
    void setDefaultValue(String value);

    /**
     * Returns the value of the '<em><b>Required</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Required</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Required</em>' attribute.
     * @see #setRequired(boolean)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_Required()
     * @model default="false" required="true"
     * @generated
     */
    boolean isRequired();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameterInfo#isRequired <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Required</em>' attribute.
     * @see #isRequired()
     * @generated
     */
    void setRequired(boolean value);

    /**
     * Returns the value of the '<em><b>Automatic</b></em>' attribute.
     * The default value is <code>"true"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Automatic</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Automatic</em>' attribute.
     * @see #setAutomatic(boolean)
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_Automatic()
     * @model default="true" required="true"
     * @generated
     */
    boolean isAutomatic();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ConfigParameterInfo#isAutomatic <em>Automatic</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Automatic</em>' attribute.
     * @see #isAutomatic()
     * @generated
     */
    void setAutomatic(boolean value);

    /**
     * Returns the value of the '<em><b>Facets</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ParameterFacet}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Facets</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Facets</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterInfo_Facets()
     * @model containment="true" transient="true"
     * @generated
     */
    EList<ParameterFacet> getFacets();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    String getFacet(String key);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    Map<String, String> getFacetsMap();

} // ConfigParameterInfo
