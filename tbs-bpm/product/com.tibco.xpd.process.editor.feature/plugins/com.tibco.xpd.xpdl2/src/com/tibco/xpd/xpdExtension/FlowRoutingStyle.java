/**
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Flow Routing Style</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * FlowRoutingStyle attribute is added to the xpdl element 'WorkflowProcess' and  specifies the routing style of the process.
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFlowRoutingStyle()
 * @model extendedMetaData="name='FlowRoutingStyle_._type'"
 * @generated
 */
public enum FlowRoutingStyle implements Enumerator
{
	/**
	 * The '<em><b>Uncentered On Tasks</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNCENTERED_ON_TASKS_VALUE
	 * @generated
	 * @ordered
	 */
	UNCENTERED_ON_TASKS(1, "UncenteredOnTasks", "UncenteredOnTasks"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Single Entry Exit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SINGLE_ENTRY_EXIT_VALUE
	 * @generated
	 * @ordered
	 */
	SINGLE_ENTRY_EXIT(2, "SingleEntryExit", "SingleEntryExit"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Multi Entry Exit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MULTI_ENTRY_EXIT_VALUE
	 * @generated
	 * @ordered
	 */
	MULTI_ENTRY_EXIT(3, "MultiEntryExit", "MultiEntryExit"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String					copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The '<em><b>Uncentered On Tasks</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Uncentered On Tasks</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNCENTERED_ON_TASKS
	 * @model name="UncenteredOnTasks"
	 * @generated
	 * @ordered
	 */
	public static final int						UNCENTERED_ON_TASKS_VALUE	= 1;

	/**
	 * The '<em><b>Single Entry Exit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Single Entry Exit</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SINGLE_ENTRY_EXIT
	 * @model name="SingleEntryExit"
	 * @generated
	 * @ordered
	 */
	public static final int						SINGLE_ENTRY_EXIT_VALUE		= 2;

	/**
	 * The '<em><b>Multi Entry Exit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Multi Entry Exit</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MULTI_ENTRY_EXIT
	 * @model name="MultiEntryExit"
	 * @generated
	 * @ordered
	 */
	public static final int						MULTI_ENTRY_EXIT_VALUE		= 3;

	/**
	 * An array of all the '<em><b>Flow Routing Style</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FlowRoutingStyle[]		VALUES_ARRAY				= new FlowRoutingStyle[]{
			UNCENTERED_ON_TASKS, SINGLE_ENTRY_EXIT, MULTI_ENTRY_EXIT,};

	/**
	 * A public read-only list of all the '<em><b>Flow Routing Style</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FlowRoutingStyle>	VALUES						= Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Flow Routing Style</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FlowRoutingStyle get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			FlowRoutingStyle result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Flow Routing Style</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FlowRoutingStyle getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			FlowRoutingStyle result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Flow Routing Style</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FlowRoutingStyle get(int value)
	{
		switch (value)
		{
			case UNCENTERED_ON_TASKS_VALUE:
				return UNCENTERED_ON_TASKS;
			case SINGLE_ENTRY_EXIT_VALUE:
				return SINGLE_ENTRY_EXIT;
			case MULTI_ENTRY_EXIT_VALUE:
				return MULTI_ENTRY_EXIT;
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
	private FlowRoutingStyle(int value, String name, String literal)
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

} //FlowRoutingStyle
