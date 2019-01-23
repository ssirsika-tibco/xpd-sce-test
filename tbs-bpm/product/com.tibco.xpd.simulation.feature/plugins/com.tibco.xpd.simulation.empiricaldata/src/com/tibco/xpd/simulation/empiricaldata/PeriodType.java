/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Period Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getPeriodType()
 * @model
 * @generated
 */
public final class PeriodType extends AbstractEnumerator {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * The '<em><b>Month Of Year</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Month Of Year</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MONTH_OF_YEAR_LITERAL
	 * @model name="MonthOfYear"
	 * @generated
	 * @ordered
	 */
	public static final int MONTH_OF_YEAR = 0;

	/**
	 * The '<em><b>Day Of Month</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Day Of Month</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DAY_OF_MONTH_LITERAL
	 * @model name="DayOfMonth"
	 * @generated
	 * @ordered
	 */
	public static final int DAY_OF_MONTH = 1;

	/**
	 * The '<em><b>Day Of Week</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Day Of Week</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DAY_OF_WEEK_LITERAL
	 * @model name="DayOfWeek"
	 * @generated
	 * @ordered
	 */
	public static final int DAY_OF_WEEK = 2;

	/**
	 * The '<em><b>Hour Of Day</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Hour Of Day</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HOUR_OF_DAY_LITERAL
	 * @model name="HourOfDay"
	 * @generated
	 * @ordered
	 */
	public static final int HOUR_OF_DAY = 3;

	/**
	 * The '<em><b>Month Of Year</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MONTH_OF_YEAR
	 * @generated
	 * @ordered
	 */
	public static final PeriodType MONTH_OF_YEAR_LITERAL = new PeriodType(
			MONTH_OF_YEAR, "MonthOfYear", "MonthOfYear"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Day Of Month</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DAY_OF_MONTH
	 * @generated
	 * @ordered
	 */
	public static final PeriodType DAY_OF_MONTH_LITERAL = new PeriodType(
			DAY_OF_MONTH, "DayOfMonth", "DayOfMonth"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Day Of Week</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DAY_OF_WEEK
	 * @generated
	 * @ordered
	 */
	public static final PeriodType DAY_OF_WEEK_LITERAL = new PeriodType(
			DAY_OF_WEEK, "DayOfWeek", "DayOfWeek"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Hour Of Day</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HOUR_OF_DAY
	 * @generated
	 * @ordered
	 */
	public static final PeriodType HOUR_OF_DAY_LITERAL = new PeriodType(
			HOUR_OF_DAY, "HourOfDay", "HourOfDay"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * An array of all the '<em><b>Period Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PeriodType[] VALUES_ARRAY = new PeriodType[] {
			MONTH_OF_YEAR_LITERAL, DAY_OF_MONTH_LITERAL, DAY_OF_WEEK_LITERAL,
			HOUR_OF_DAY_LITERAL, };

	/**
	 * A public read-only list of all the '<em><b>Period Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays
			.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Period Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PeriodType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PeriodType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Period Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PeriodType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PeriodType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Period Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PeriodType get(int value) {
		switch (value) {
		case MONTH_OF_YEAR:
			return MONTH_OF_YEAR_LITERAL;
		case DAY_OF_MONTH:
			return DAY_OF_MONTH_LITERAL;
		case DAY_OF_WEEK:
			return DAY_OF_WEEK_LITERAL;
		case HOUR_OF_DAY:
			return HOUR_OF_DAY_LITERAL;
		}
		return null;
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PeriodType(int value, String name, String literal) {
		super(value, name, literal);
	}

} //PeriodType
