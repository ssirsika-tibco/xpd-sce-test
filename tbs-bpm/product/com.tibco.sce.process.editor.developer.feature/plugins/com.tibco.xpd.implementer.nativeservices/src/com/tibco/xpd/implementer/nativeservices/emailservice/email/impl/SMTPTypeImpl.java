/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email.impl;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType;

import java.math.BigDecimal;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SMTP Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.SMTPTypeImpl#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.SMTPTypeImpl#getHost <em>Host</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.SMTPTypeImpl#getPort <em>Port</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SMTPTypeImpl extends EObjectImpl implements SMTPType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getConfiguration() <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfiguration()
	 * @generated
	 * @ordered
	 */
	protected static final ConfigurationType CONFIGURATION_EDEFAULT = ConfigurationType.SERVER_LITERAL;

	/**
	 * The cached value of the '{@link #getConfiguration() <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfiguration()
	 * @generated
	 * @ordered
	 */
	protected ConfigurationType configuration = CONFIGURATION_EDEFAULT;

	/**
	 * This is true if the Configuration attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean configurationESet = false;

	/**
	 * The default value of the '{@link #getHost() <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHost()
	 * @generated
	 * @ordered
	 */
	protected static final String HOST_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHost() <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHost()
	 * @generated
	 * @ordered
	 */
	protected String host = HOST_EDEFAULT;

	/**
	 * The default value of the '{@link #getPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal PORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal port = PORT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SMTPTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EmailPackage.Literals.SMTP_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType getConfiguration() {
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfiguration(ConfigurationType newConfiguration) {
		ConfigurationType oldConfiguration = configuration;
		configuration = newConfiguration == null ? CONFIGURATION_EDEFAULT
				: newConfiguration;
		boolean oldConfigurationESet = configurationESet;
		configurationESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.SMTP_TYPE__CONFIGURATION, oldConfiguration,
					configuration, !oldConfigurationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetConfiguration() {
		ConfigurationType oldConfiguration = configuration;
		boolean oldConfigurationESet = configurationESet;
		configuration = CONFIGURATION_EDEFAULT;
		configurationESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					EmailPackage.SMTP_TYPE__CONFIGURATION, oldConfiguration,
					CONFIGURATION_EDEFAULT, oldConfigurationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetConfiguration() {
		return configurationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHost() {
		return host;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHost(String newHost) {
		String oldHost = host;
		host = newHost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.SMTP_TYPE__HOST, oldHost, host));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getPort() {
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(BigDecimal newPort) {
		BigDecimal oldPort = port;
		port = newPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EmailPackage.SMTP_TYPE__PORT, oldPort, port));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EmailPackage.SMTP_TYPE__CONFIGURATION:
			return getConfiguration();
		case EmailPackage.SMTP_TYPE__HOST:
			return getHost();
		case EmailPackage.SMTP_TYPE__PORT:
			return getPort();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EmailPackage.SMTP_TYPE__CONFIGURATION:
			setConfiguration((ConfigurationType) newValue);
			return;
		case EmailPackage.SMTP_TYPE__HOST:
			setHost((String) newValue);
			return;
		case EmailPackage.SMTP_TYPE__PORT:
			setPort((BigDecimal) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
		case EmailPackage.SMTP_TYPE__CONFIGURATION:
			unsetConfiguration();
			return;
		case EmailPackage.SMTP_TYPE__HOST:
			setHost(HOST_EDEFAULT);
			return;
		case EmailPackage.SMTP_TYPE__PORT:
			setPort(PORT_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EmailPackage.SMTP_TYPE__CONFIGURATION:
			return isSetConfiguration();
		case EmailPackage.SMTP_TYPE__HOST:
			return HOST_EDEFAULT == null ? host != null : !HOST_EDEFAULT
					.equals(host);
		case EmailPackage.SMTP_TYPE__PORT:
			return PORT_EDEFAULT == null ? port != null : !PORT_EDEFAULT
					.equals(port);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (configuration: "); //$NON-NLS-1$
		if (configurationESet)
			result.append(configuration);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", host: "); //$NON-NLS-1$
		result.append(host);
		result.append(", port: "); //$NON-NLS-1$
		result.append(port);
		result.append(')');
		return result.toString();
	}

} //SMTPTypeImpl