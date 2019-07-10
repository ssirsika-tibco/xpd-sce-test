/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact Input</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ArtifactInput#getArtifactId <em>Artifact Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArtifactInput#isRequiredForStart <em>Required For Start</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifactInput()
 * @model
 * @generated
 */
public interface ArtifactInput extends OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Artifact Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Artifact Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Artifact Id</em>' attribute.
     * @see #setArtifactId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifactInput_ArtifactId()
     * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ArtifactId'"
     * @generated
     */
    String getArtifactId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArtifactInput#getArtifactId <em>Artifact Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Artifact Id</em>' attribute.
     * @see #getArtifactId()
     * @generated
     */
    void setArtifactId(String value);

    /**
     * Returns the value of the '<em><b>Required For Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Required For Start</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Required For Start</em>' attribute.
     * @see #setRequiredForStart(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifactInput_RequiredForStart()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='RequiredForStart'"
     * @generated
     */
    boolean isRequiredForStart();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArtifactInput#isRequiredForStart <em>Required For Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Required For Start</em>' attribute.
     * @see #isRequiredForStart()
     * @generated
     */
    void setRequiredForStart(boolean value);

} // ArtifactInput
