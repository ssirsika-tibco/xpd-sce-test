/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Soap Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getBindingStyle <em>Binding Style</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapVersion <em>Soap Version</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapSecurity <em>Soap Security</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapBinding()
 * @model extendedMetaData="name='WsSoapBinding' kind='empty'"
 * @generated
 */
public interface WsSoapBinding extends WsBinding {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Binding Style</b></em>' attribute.
     * The default value is <code>"RpcLiteral"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.SoapBindingStyle}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Binding Style</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Binding Style</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
     * @see #isSetBindingStyle()
     * @see #unsetBindingStyle()
     * @see #setBindingStyle(SoapBindingStyle)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapBinding_BindingStyle()
     * @model default="RpcLiteral" unsettable="true"
     *        extendedMetaData="kind='attribute' name='BindingStyle'"
     * @generated
     */
    SoapBindingStyle getBindingStyle();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getBindingStyle <em>Binding Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Binding Style</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
     * @see #isSetBindingStyle()
     * @see #unsetBindingStyle()
     * @see #getBindingStyle()
     * @generated
     */
    void setBindingStyle(SoapBindingStyle value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getBindingStyle <em>Binding Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBindingStyle()
     * @see #getBindingStyle()
     * @see #setBindingStyle(SoapBindingStyle)
     * @generated
     */
    void unsetBindingStyle();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getBindingStyle <em>Binding Style</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Binding Style</em>' attribute is set.
     * @see #unsetBindingStyle()
     * @see #getBindingStyle()
     * @see #setBindingStyle(SoapBindingStyle)
     * @generated
     */
    boolean isSetBindingStyle();

    /**
     * Returns the value of the '<em><b>Soap Version</b></em>' attribute.
     * The default value is <code>"1.1"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Soap Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Soap Version</em>' attribute.
     * @see #isSetSoapVersion()
     * @see #unsetSoapVersion()
     * @see #setSoapVersion(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapBinding_SoapVersion()
     * @model default="1.1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='SoapVersion'"
     * @generated
     */
    String getSoapVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapVersion <em>Soap Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Soap Version</em>' attribute.
     * @see #isSetSoapVersion()
     * @see #unsetSoapVersion()
     * @see #getSoapVersion()
     * @generated
     */
    void setSoapVersion(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapVersion <em>Soap Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSoapVersion()
     * @see #getSoapVersion()
     * @see #setSoapVersion(String)
     * @generated
     */
    void unsetSoapVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapVersion <em>Soap Version</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Soap Version</em>' attribute is set.
     * @see #unsetSoapVersion()
     * @see #getSoapVersion()
     * @see #setSoapVersion(String)
     * @generated
     */
    boolean isSetSoapVersion();

    /**
     * Returns the value of the '<em><b>Soap Security</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Soap Security</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Soap Security</em>' reference.
     * @see #setSoapSecurity(WsSoapSecurity)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapBinding_SoapSecurity()
     * @model resolveProxies="false" transient="true" volatile="true" derived="true"
     * @generated
     */
    WsSoapSecurity getSoapSecurity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapSecurity <em>Soap Security</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Soap Security</em>' reference.
     * @see #getSoapSecurity()
     * @generated
     */
    void setSoapSecurity(WsSoapSecurity value);

} // WsSoapBinding
