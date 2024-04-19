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
 * A representation of the literals of the enumeration '<em><b>Event Handler Flow Strategy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Attribtue added to xpdl:TriggerResultMessage element for event handler
 * activity, to control the behaviour of multiple concurrent flows
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getEventHandlerFlowStrategy()
 * @model extendedMetaData="name='EventHandlerFlowStrategy_._type'"
 * @generated
 */
public enum EventHandlerFlowStrategy implements Enumerator
{
	/**
	 * The '<em><b>Serialize Concurrent</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SERIALIZE_CONCURRENT_VALUE
	 * @generated
	 * @ordered
	 */
	SERIALIZE_CONCURRENT(1, "SerializeConcurrent", "SerializeConcurrent"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Allow Concurrent</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALLOW_CONCURRENT_VALUE
	 * @generated
	 * @ordered
	 */
	ALLOW_CONCURRENT(2, "AllowConcurrent", "AllowConcurrent"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String							copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The '<em><b>Serialize Concurrent</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Serialize Concurrent</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SERIALIZE_CONCURRENT
	 * @model name="SerializeConcurrent"
	 * @generated
	 * @ordered
	 */
	public static final int								SERIALIZE_CONCURRENT_VALUE	= 1;

	/**
	 * The '<em><b>Allow Concurrent</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Allow Concurrent</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALLOW_CONCURRENT
	 * @model name="AllowConcurrent"
	 * @generated
	 * @ordered
	 */
	public static final int								ALLOW_CONCURRENT_VALUE		= 2;

	/**
	 * An array of all the '<em><b>Event Handler Flow Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EventHandlerFlowStrategy[]		VALUES_ARRAY				= new EventHandlerFlowStrategy[]{
			SERIALIZE_CONCURRENT, ALLOW_CONCURRENT,};

	/**
	 * A public read-only list of all the '<em><b>Event Handler Flow Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EventHandlerFlowStrategy>	VALUES						= Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Event Handler Flow Strategy</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EventHandlerFlowStrategy get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EventHandlerFlowStrategy result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Event Handler Flow Strategy</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EventHandlerFlowStrategy getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EventHandlerFlowStrategy result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Event Handler Flow Strategy</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EventHandlerFlowStrategy get(int value)
	{
		switch (value)
		{
			case SERIALIZE_CONCURRENT_VALUE:
				return SERIALIZE_CONCURRENT;
			case ALLOW_CONCURRENT_VALUE:
				return ALLOW_CONCURRENT;
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
	private EventHandlerFlowStrategy(int value, String name, String literal)
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

} //EventHandlerFlowStrategy
