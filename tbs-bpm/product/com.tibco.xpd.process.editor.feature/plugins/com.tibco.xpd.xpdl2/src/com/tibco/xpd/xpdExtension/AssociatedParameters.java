/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Associated Parameters</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The associated parameters for an activity.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedParameters#getAssociatedParameter <em>Associated Parameter</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedParameters#isDisableImplicitAssociation <em>Disable Implicit Association</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParameters()
 * @model extendedMetaData="name='AssociatedParameters' kind='elementOnly'"
 * @generated
 */
public interface AssociatedParameters extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Associated Parameter</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.AssociatedParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Associated Parameter</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParameters_AssociatedParameter()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AssociatedParameter' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AssociatedParameter> getAssociatedParameter();

	/**
	 * Returns the value of the '<em><b>Disable Implicit Association</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disable Implicit Association</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disable Implicit Association</em>' attribute.
	 * @see #isSetDisableImplicitAssociation()
	 * @see #unsetDisableImplicitAssociation()
	 * @see #setDisableImplicitAssociation(boolean)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParameters_DisableImplicitAssociation()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='DisableImplicitAssociation'"
	 * @generated
	 */
	boolean isDisableImplicitAssociation();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameters#isDisableImplicitAssociation <em>Disable Implicit Association</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disable Implicit Association</em>' attribute.
	 * @see #isSetDisableImplicitAssociation()
	 * @see #unsetDisableImplicitAssociation()
	 * @see #isDisableImplicitAssociation()
	 * @generated
	 */
	void setDisableImplicitAssociation(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameters#isDisableImplicitAssociation <em>Disable Implicit Association</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDisableImplicitAssociation()
	 * @see #isDisableImplicitAssociation()
	 * @see #setDisableImplicitAssociation(boolean)
	 * @generated
	 */
	void unsetDisableImplicitAssociation();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameters#isDisableImplicitAssociation <em>Disable Implicit Association</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Disable Implicit Association</em>' attribute is set.
	 * @see #unsetDisableImplicitAssociation()
	 * @see #isDisableImplicitAssociation()
	 * @see #setDisableImplicitAssociation(boolean)
	 * @generated
	 */
	boolean isSetDisableImplicitAssociation();

} // AssociatedParameters