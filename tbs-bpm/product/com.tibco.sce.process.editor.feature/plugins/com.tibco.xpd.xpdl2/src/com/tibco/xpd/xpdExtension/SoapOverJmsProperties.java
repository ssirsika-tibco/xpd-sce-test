/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Soap Over Jms Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactoryConfiguration <em>Connection Factory Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactory <em>Connection Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getInboundDestination <em>Inbound Destination</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getOutboundDestination <em>Outbound Destination</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSoapOverJmsProperties()
 * @model extendedMetaData="name='SoapOverJmsProperties' kind='elementOnly'"
 * @generated
 */
public interface SoapOverJmsProperties extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Connection Factory Configuration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connection Factory Configuration</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connection Factory Configuration</em>' attribute.
     * @see #isSetConnectionFactoryConfiguration()
     * @see #unsetConnectionFactoryConfiguration()
     * @see #setConnectionFactoryConfiguration(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSoapOverJmsProperties_ConnectionFactoryConfiguration()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" ordered="false"
     *        extendedMetaData="kind='element' name='ConnectionFactoryConfiguration' namespace='##targetNamespace'"
     * @generated
     */
    String getConnectionFactoryConfiguration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactoryConfiguration <em>Connection Factory Configuration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Connection Factory Configuration</em>' attribute.
     * @see #isSetConnectionFactoryConfiguration()
     * @see #unsetConnectionFactoryConfiguration()
     * @see #getConnectionFactoryConfiguration()
     * @generated
     */
    void setConnectionFactoryConfiguration(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactoryConfiguration <em>Connection Factory Configuration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetConnectionFactoryConfiguration()
     * @see #getConnectionFactoryConfiguration()
     * @see #setConnectionFactoryConfiguration(String)
     * @generated
     */
    void unsetConnectionFactoryConfiguration();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactoryConfiguration <em>Connection Factory Configuration</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Connection Factory Configuration</em>' attribute is set.
     * @see #unsetConnectionFactoryConfiguration()
     * @see #getConnectionFactoryConfiguration()
     * @see #setConnectionFactoryConfiguration(String)
     * @generated
     */
    boolean isSetConnectionFactoryConfiguration();

    /**
     * Returns the value of the '<em><b>Connection Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connection Factory</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connection Factory</em>' attribute.
     * @see #isSetConnectionFactory()
     * @see #unsetConnectionFactory()
     * @see #setConnectionFactory(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSoapOverJmsProperties_ConnectionFactory()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" ordered="false"
     *        extendedMetaData="kind='element' name='ConnectionFactory' namespace='##targetNamespace'"
     * @generated
     */
    String getConnectionFactory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactory <em>Connection Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Connection Factory</em>' attribute.
     * @see #isSetConnectionFactory()
     * @see #unsetConnectionFactory()
     * @see #getConnectionFactory()
     * @generated
     */
    void setConnectionFactory(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactory <em>Connection Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetConnectionFactory()
     * @see #getConnectionFactory()
     * @see #setConnectionFactory(String)
     * @generated
     */
    void unsetConnectionFactory();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getConnectionFactory <em>Connection Factory</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Connection Factory</em>' attribute is set.
     * @see #unsetConnectionFactory()
     * @see #getConnectionFactory()
     * @see #setConnectionFactory(String)
     * @generated
     */
    boolean isSetConnectionFactory();

    /**
     * Returns the value of the '<em><b>Inbound Destination</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inbound Destination</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Inbound Destination</em>' attribute.
     * @see #isSetInboundDestination()
     * @see #unsetInboundDestination()
     * @see #setInboundDestination(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSoapOverJmsProperties_InboundDestination()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" ordered="false"
     *        extendedMetaData="kind='element' name='InboundDestination' namespace='##targetNamespace'"
     * @generated
     */
    String getInboundDestination();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getInboundDestination <em>Inbound Destination</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inbound Destination</em>' attribute.
     * @see #isSetInboundDestination()
     * @see #unsetInboundDestination()
     * @see #getInboundDestination()
     * @generated
     */
    void setInboundDestination(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getInboundDestination <em>Inbound Destination</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetInboundDestination()
     * @see #getInboundDestination()
     * @see #setInboundDestination(String)
     * @generated
     */
    void unsetInboundDestination();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getInboundDestination <em>Inbound Destination</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Inbound Destination</em>' attribute is set.
     * @see #unsetInboundDestination()
     * @see #getInboundDestination()
     * @see #setInboundDestination(String)
     * @generated
     */
    boolean isSetInboundDestination();

    /**
     * Returns the value of the '<em><b>Outbound Destination</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outbound Destination</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outbound Destination</em>' attribute.
     * @see #isSetOutboundDestination()
     * @see #unsetOutboundDestination()
     * @see #setOutboundDestination(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSoapOverJmsProperties_OutboundDestination()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" ordered="false"
     *        extendedMetaData="kind='element' name='OutboundDestination' namespace='##targetNamespace'"
     * @generated
     */
    String getOutboundDestination();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getOutboundDestination <em>Outbound Destination</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outbound Destination</em>' attribute.
     * @see #isSetOutboundDestination()
     * @see #unsetOutboundDestination()
     * @see #getOutboundDestination()
     * @generated
     */
    void setOutboundDestination(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getOutboundDestination <em>Outbound Destination</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOutboundDestination()
     * @see #getOutboundDestination()
     * @see #setOutboundDestination(String)
     * @generated
     */
    void unsetOutboundDestination();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.SoapOverJmsProperties#getOutboundDestination <em>Outbound Destination</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Outbound Destination</em>' attribute is set.
     * @see #unsetOutboundDestination()
     * @see #getOutboundDestination()
     * @see #setOutboundDestination(String)
     * @generated
     */
    boolean isSetOutboundDestination();

} // SoapOverJmsProperties
