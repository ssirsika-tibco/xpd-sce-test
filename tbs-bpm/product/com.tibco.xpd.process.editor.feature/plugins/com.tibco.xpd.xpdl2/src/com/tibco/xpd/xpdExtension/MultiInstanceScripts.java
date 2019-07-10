/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Instance Scripts</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * MultiInstanceScripts are scripts which can be defined when a  Task has 'Activity Marker' as 'Multi Instance Loop' and are added to the xpdl element 'LoopMultiInstance'
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.MultiInstanceScripts#getAdditionalInstances <em>Additional Instances</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getMultiInstanceScripts()
 * @model extendedMetaData="name='MultiInstanceScripts' kind='elementOnly'"
 * @generated
 */
public interface MultiInstanceScripts extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Additional Instances</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Additional Instances</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Additional Instances</em>' containment reference.
     * @see #setAdditionalInstances(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getMultiInstanceScripts_AdditionalInstances()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='AdditionalInstances' namespace='##targetNamespace'"
     * @generated
     */
    Expression getAdditionalInstances();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.MultiInstanceScripts#getAdditionalInstances <em>Additional Instances</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Additional Instances</em>' containment reference.
     * @see #getAdditionalInstances()
     * @generated
     */
    void setAdditionalInstances(Expression value);

} // MultiInstanceScripts