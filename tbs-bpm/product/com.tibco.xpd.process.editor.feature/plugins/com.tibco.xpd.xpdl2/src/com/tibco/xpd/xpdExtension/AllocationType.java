/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Allocation Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationType()
 * @model
 * @generated
 */
public enum AllocationType implements Enumerator
{
	/**
	 * The '<em><b>Offer All</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OFFER_ALL_VALUE
	 * @generated
	 * @ordered
	 */
	OFFER_ALL(1, "OfferAll", "OfferAll"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Offer One</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OFFER_ONE_VALUE
	 * @generated
	 * @ordered
	 */
	OFFER_ONE(2, "OfferOne", "OfferOne"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Allocate One</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALLOCATE_ONE_VALUE
	 * @generated
	 * @ordered
	 */
	ALLOCATE_ONE(3, "AllocateOne", "AllocateOne"),
	/**
	 * The '<em><b>Allocate Offer Set Member</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALLOCATE_OFFER_SET_MEMBER_VALUE
	 * @generated
	 * @ordered
	 */
	ALLOCATE_OFFER_SET_MEMBER(4, "AllocateOfferSetMember", //$NON-NLS-1$
			"AllocateOfferSetMember"); //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String					copyright						= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The '<em><b>Offer All</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Offer All</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OFFER_ALL
	 * @model name="OfferAll"
	 * @generated
	 * @ordered
	 */
	public static final int						OFFER_ALL_VALUE					= 1;

	/**
	 * The '<em><b>Offer One</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Offer One</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OFFER_ONE
	 * @model name="OfferOne"
	 * @generated
	 * @ordered
	 */
	public static final int						OFFER_ONE_VALUE					= 2;

	/**
	 * The '<em><b>Allocate One</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Allocate One</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALLOCATE_ONE
	 * @model name="AllocateOne"
	 * @generated
	 * @ordered
	 */
	public static final int						ALLOCATE_ONE_VALUE				= 3;

	/**
	 * The '<em><b>Allocate Offer Set Member</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Allocate Offer Set Member</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALLOCATE_OFFER_SET_MEMBER
	 * @model name="AllocateOfferSetMember"
	 * @generated
	 * @ordered
	 */
	public static final int						ALLOCATE_OFFER_SET_MEMBER_VALUE	= 4;

	/**
	 * An array of all the '<em><b>Allocation Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AllocationType[]		VALUES_ARRAY					= new AllocationType[]{OFFER_ALL,
			OFFER_ONE, ALLOCATE_ONE, ALLOCATE_OFFER_SET_MEMBER,};

	/**
	 * A public read-only list of all the '<em><b>Allocation Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AllocationType>	VALUES							= Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Allocation Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AllocationType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			AllocationType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Allocation Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AllocationType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			AllocationType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Allocation Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AllocationType get(int value)
	{
		switch (value)
		{
			case OFFER_ALL_VALUE:
				return OFFER_ALL;
			case OFFER_ONE_VALUE:
				return OFFER_ONE;
			case ALLOCATE_ONE_VALUE:
				return ALLOCATE_ONE;
			case ALLOCATE_OFFER_SET_MEMBER_VALUE:
				return ALLOCATE_OFFER_SET_MEMBER;
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
	private AllocationType(int value, String name, String literal)
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

} //AllocationType
