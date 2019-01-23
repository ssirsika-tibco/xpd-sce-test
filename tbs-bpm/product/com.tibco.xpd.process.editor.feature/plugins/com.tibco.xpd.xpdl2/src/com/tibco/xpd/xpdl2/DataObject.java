/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedProducedAtCompletion <em>Deprecated Produced At Completion</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedRequiredForStart <em>Deprecated Required For Start</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.DataObject#getState <em>State</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataObject()
 * @model extendedMetaData="name='DataObject_._type' kind='elementOnly' features-order='dataFields'"
 * @generated
 */
public interface DataObject extends NamedElement, DataFieldsContainer,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Deprecated Produced At Completion</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Produced At Completion</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Produced At Completion</em>' attribute.
     * @see #isSetDeprecatedProducedAtCompletion()
     * @see #unsetDeprecatedProducedAtCompletion()
     * @see #setDeprecatedProducedAtCompletion(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataObject_DeprecatedProducedAtCompletion()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='ProducedAtCompletion'"
     * @generated
     */
    boolean isDeprecatedProducedAtCompletion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedProducedAtCompletion <em>Deprecated Produced At Completion</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Produced At Completion</em>' attribute.
     * @see #isSetDeprecatedProducedAtCompletion()
     * @see #unsetDeprecatedProducedAtCompletion()
     * @see #isDeprecatedProducedAtCompletion()
     * @generated
     */
    void setDeprecatedProducedAtCompletion(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedProducedAtCompletion <em>Deprecated Produced At Completion</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDeprecatedProducedAtCompletion()
     * @see #isDeprecatedProducedAtCompletion()
     * @see #setDeprecatedProducedAtCompletion(boolean)
     * @generated
     */
    void unsetDeprecatedProducedAtCompletion();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedProducedAtCompletion <em>Deprecated Produced At Completion</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Deprecated Produced At Completion</em>' attribute is set.
     * @see #unsetDeprecatedProducedAtCompletion()
     * @see #isDeprecatedProducedAtCompletion()
     * @see #setDeprecatedProducedAtCompletion(boolean)
     * @generated
     */
    boolean isSetDeprecatedProducedAtCompletion();

    /**
     * Returns the value of the '<em><b>Deprecated Required For Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Required For Start</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Required For Start</em>' attribute.
     * @see #isSetDeprecatedRequiredForStart()
     * @see #unsetDeprecatedRequiredForStart()
     * @see #setDeprecatedRequiredForStart(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataObject_DeprecatedRequiredForStart()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='RequiredForStart'"
     * @generated
     */
    boolean isDeprecatedRequiredForStart();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedRequiredForStart <em>Deprecated Required For Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Required For Start</em>' attribute.
     * @see #isSetDeprecatedRequiredForStart()
     * @see #unsetDeprecatedRequiredForStart()
     * @see #isDeprecatedRequiredForStart()
     * @generated
     */
    void setDeprecatedRequiredForStart(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedRequiredForStart <em>Deprecated Required For Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDeprecatedRequiredForStart()
     * @see #isDeprecatedRequiredForStart()
     * @see #setDeprecatedRequiredForStart(boolean)
     * @generated
     */
    void unsetDeprecatedRequiredForStart();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedRequiredForStart <em>Deprecated Required For Start</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Deprecated Required For Start</em>' attribute is set.
     * @see #unsetDeprecatedRequiredForStart()
     * @see #isDeprecatedRequiredForStart()
     * @see #setDeprecatedRequiredForStart(boolean)
     * @generated
     */
    boolean isSetDeprecatedRequiredForStart();

    /**
     * Returns the value of the '<em><b>State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>State</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>State</em>' attribute.
     * @see #setState(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataObject_State()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='State'"
     * @generated
     */
    String getState();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataObject#getState <em>State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>State</em>' attribute.
     * @see #getState()
     * @generated
     */
    void setState(String value);

} // DataObject
