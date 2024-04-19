/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Script Information</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Contains information of the script used in a DataMapping. It is used when a data mapping involves a script.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptInformation#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptInformation#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptInformation#getActivity <em>Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptInformation#isReference <em>Reference</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptInformation()
 * @model extendedMetaData="name='ScriptInformation' kind='elementOnly'"
 * @generated
 */
public interface ScriptInformation extends NamedElement
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * rvalue expression of the assignment
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptInformation_Expression()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Expression' namespace='##targetNamespace'"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptInformation#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.DirectionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see com.tibco.xpd.xpdl2.DirectionType
	 * @see #setDirection(DirectionType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptInformation_Direction()
	 * @model extendedMetaData="kind='attribute' name='Direction'"
	 * @generated
	 */
	DirectionType getDirection();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptInformation#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see com.tibco.xpd.xpdl2.DirectionType
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(DirectionType value);

	/**
	 * Returns the value of the '<em><b>Activity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * transient reference to allow new ScriptInformation items to be associated with an activity without being attached.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Activity</em>' reference.
	 * @see #setActivity(Activity)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptInformation_Activity()
	 * @model transient="true"
	 *        extendedMetaData="kind='element' name='Activity' namespace='##targetNamespace'"
	 * @generated
	 */
	Activity getActivity();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptInformation#getActivity <em>Activity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity</em>' reference.
	 * @see #getActivity()
	 * @generated
	 */
	void setActivity(Activity value);

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' attribute.
	 * @see #setReference(boolean)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptInformation_Reference()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='Reference'"
	 * @generated
	 */
	boolean isReference();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptInformation#isReference <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' attribute.
	 * @see #isReference()
	 * @generated
	 */
	void setReference(boolean value);

} // ScriptInformation