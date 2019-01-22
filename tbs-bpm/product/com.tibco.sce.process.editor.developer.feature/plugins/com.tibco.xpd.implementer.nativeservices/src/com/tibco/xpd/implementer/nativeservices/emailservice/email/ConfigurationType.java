/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Configuration Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getConfigurationType()
 * @model
 * @generated
 */
public final class ConfigurationType extends AbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The '<em><b>Custom</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Custom</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_LITERAL
	 * @model name="Custom"
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOM = 0;

	/**
	 * The '<em><b>Server</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Server</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SERVER_LITERAL
	 * @model name="Server"
	 * @generated
	 * @ordered
	 */
	public static final int SERVER = 1;

	/**
	 * The '<em><b>Custom</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOM
	 * @generated
	 * @ordered
	 */
	public static final ConfigurationType CUSTOM_LITERAL = new ConfigurationType(
			CUSTOM, "Custom", "Custom"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Server</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SERVER
	 * @generated
	 * @ordered
	 */
	public static final ConfigurationType SERVER_LITERAL = new ConfigurationType(
			SERVER, "Server", "Server"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * An array of all the '<em><b>Configuration Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ConfigurationType[] VALUES_ARRAY = new ConfigurationType[] {
			CUSTOM_LITERAL, SERVER_LITERAL, };

	/**
	 * A public read-only list of all the '<em><b>Configuration Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays
			.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Configuration Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigurationType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ConfigurationType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Configuration Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigurationType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ConfigurationType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Configuration Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigurationType get(int value) {
		switch (value) {
		case CUSTOM:
			return CUSTOM_LITERAL;
		case SERVER:
			return SERVER_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ConfigurationType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //ConfigurationType
