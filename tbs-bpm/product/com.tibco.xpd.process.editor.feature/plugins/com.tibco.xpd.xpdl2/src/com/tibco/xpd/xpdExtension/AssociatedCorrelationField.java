/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.DescribedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Associated Correlation Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getCorrelationMode <em>Correlation Mode</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedCorrelationField()
 * @model extendedMetaData="name='AssociatedCorrelationField' kind='elementOnly'"
 * @generated
 */
public interface AssociatedCorrelationField extends DescribedElement
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedCorrelationField_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Correlation Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.CorrelationMode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correlation Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correlation Mode</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.CorrelationMode
	 * @see #isSetCorrelationMode()
	 * @see #unsetCorrelationMode()
	 * @see #setCorrelationMode(CorrelationMode)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedCorrelationField_CorrelationMode()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='CorrelationMode'"
	 * @generated
	 */
	CorrelationMode getCorrelationMode();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getCorrelationMode <em>Correlation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Correlation Mode</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.CorrelationMode
	 * @see #isSetCorrelationMode()
	 * @see #unsetCorrelationMode()
	 * @see #getCorrelationMode()
	 * @generated
	 */
	void setCorrelationMode(CorrelationMode value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getCorrelationMode <em>Correlation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCorrelationMode()
	 * @see #getCorrelationMode()
	 * @see #setCorrelationMode(CorrelationMode)
	 * @generated
	 */
	void unsetCorrelationMode();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getCorrelationMode <em>Correlation Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Correlation Mode</em>' attribute is set.
	 * @see #unsetCorrelationMode()
	 * @see #getCorrelationMode()
	 * @see #setCorrelationMode(CorrelationMode)
	 * @generated
	 */
	boolean isSetCorrelationMode();

} // AssociatedCorrelationField
