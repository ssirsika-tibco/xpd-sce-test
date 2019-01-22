/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.InputSet#getInput <em>Input</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.InputSet#getArtifactInput <em>Artifact Input</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.InputSet#getPropertyInput <em>Property Input</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getInputSet()
 * @model extendedMetaData="name='InputSet_._type' kind='elementOnly'"
 * @generated
 */
public interface InputSet extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Input</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Input}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Input</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Input</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getInputSet_Input()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Input' namespace='##targetNamespace'"
     * @generated
     */
    EList<Input> getInput();

    /**
     * Returns the value of the '<em><b>Artifact Input</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.ArtifactInput}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Artifact Input</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Artifact Input</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getInputSet_ArtifactInput()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ArtifactInput' namespace='##targetNamespace'"
     * @generated
     */
    EList<ArtifactInput> getArtifactInput();

    /**
     * Returns the value of the '<em><b>Property Input</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.PropertyInput}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Property Input</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Property Input</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getInputSet_PropertyInput()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PropertyInput' namespace='##targetNamespace'"
     * @generated
     */
    EList<PropertyInput> getPropertyInput();

} // InputSet
