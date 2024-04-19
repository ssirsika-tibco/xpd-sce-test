/**
 * Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Library Function Use In</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLibraryFunctionUseIn()
 * @model
 * @generated
 */
public enum LibraryFunctionUseIn implements Enumerator
{
	/**
	 * The '<em><b>All</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALL_VALUE
	 * @generated
	 * @ordered
	 */
	ALL(1, "All", "All"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Process Manager</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_MANAGER_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESS_MANAGER(2, "ProcessManager", "ProcessManager"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Work Manager</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WORK_MANAGER_VALUE
	 * @generated
	 * @ordered
	 */
	WORK_MANAGER(3, "WorkManager", "WorkManager"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String						copyright				= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The '<em><b>All</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALL
	 * @model name="All"
	 * @generated
	 * @ordered
	 */
	public static final int							ALL_VALUE				= 1;

	/**
	 * The '<em><b>Process Manager</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_MANAGER
	 * @model name="ProcessManager"
	 * @generated
	 * @ordered
	 */
	public static final int							PROCESS_MANAGER_VALUE	= 2;

	/**
	 * The '<em><b>Work Manager</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WORK_MANAGER
	 * @model name="WorkManager"
	 * @generated
	 * @ordered
	 */
	public static final int							WORK_MANAGER_VALUE		= 3;

	/**
	 * An array of all the '<em><b>Library Function Use In</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LibraryFunctionUseIn[]		VALUES_ARRAY			= new LibraryFunctionUseIn[]{ALL,
			PROCESS_MANAGER, WORK_MANAGER,};

	/**
	 * A public read-only list of all the '<em><b>Library Function Use In</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<LibraryFunctionUseIn>	VALUES					= Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Library Function Use In</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static LibraryFunctionUseIn get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			LibraryFunctionUseIn result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Library Function Use In</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static LibraryFunctionUseIn getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			LibraryFunctionUseIn result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Library Function Use In</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static LibraryFunctionUseIn get(int value)
	{
		switch (value)
		{
			case ALL_VALUE:
				return ALL;
			case PROCESS_MANAGER_VALUE:
				return PROCESS_MANAGER;
			case WORK_MANAGER_VALUE:
				return WORK_MANAGER;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int		value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String	name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String	literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private LibraryFunctionUseIn(int value, String name, String literal)
	{
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral()
	{
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		return literal;
	}

} //LibraryFunctionUseIn
