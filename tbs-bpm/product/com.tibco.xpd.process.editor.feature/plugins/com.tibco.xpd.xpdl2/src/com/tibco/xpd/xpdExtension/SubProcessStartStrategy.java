/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Sub Process Start Strategy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Added to xpdl:Subflow elemnt to define the start strategy 
 * for sub-process invocations
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSubProcessStartStrategy()
 * @model extendedMetaData="name='SubProcessStartStrategy_._type'"
 * @generated
 */
public enum SubProcessStartStrategy implements Enumerator
{
	/**
	 * The '<em><b>Start Immediately</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #START_IMMEDIATELY_VALUE
	 * @generated
	 * @ordered
	 */
	START_IMMEDIATELY(0, "StartImmediately", "StartImmediately"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Schedule Start</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCHEDULE_START_VALUE
	 * @generated
	 * @ordered
	 */
	SCHEDULE_START(1, "ScheduleStart", "ScheduleStart"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String							copyright				= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The '<em><b>Start Immediately</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Start Immediately</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #START_IMMEDIATELY
	 * @model name="StartImmediately"
	 * @generated
	 * @ordered
	 */
	public static final int								START_IMMEDIATELY_VALUE	= 0;

	/**
	 * The '<em><b>Schedule Start</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Schedule Start</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCHEDULE_START
	 * @model name="ScheduleStart"
	 * @generated
	 * @ordered
	 */
	public static final int								SCHEDULE_START_VALUE	= 1;

	/**
	 * An array of all the '<em><b>Sub Process Start Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SubProcessStartStrategy[]		VALUES_ARRAY			= new SubProcessStartStrategy[]{
			START_IMMEDIATELY, SCHEDULE_START,};

	/**
	 * A public read-only list of all the '<em><b>Sub Process Start Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SubProcessStartStrategy>	VALUES					= Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Sub Process Start Strategy</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SubProcessStartStrategy get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			SubProcessStartStrategy result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Sub Process Start Strategy</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SubProcessStartStrategy getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			SubProcessStartStrategy result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Sub Process Start Strategy</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SubProcessStartStrategy get(int value)
	{
		switch (value)
		{
			case START_IMMEDIATELY_VALUE:
				return START_IMMEDIATELY;
			case SCHEDULE_START_VALUE:
				return SCHEDULE_START;
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
	private SubProcessStartStrategy(int value, String name, String literal)
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

} //SubProcessStartStrategy
