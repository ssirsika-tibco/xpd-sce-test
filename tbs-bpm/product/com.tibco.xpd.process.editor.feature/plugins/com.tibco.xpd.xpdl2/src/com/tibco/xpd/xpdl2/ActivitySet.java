/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ActivitySet#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ActivitySet#getProcess <em>Process</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivitySet()
 * @model extendedMetaData="name='ActivitySet_._type' kind='elementOnly' features-order='activities transitions object'"
 * @generated
 */
public interface ActivitySet extends NamedElement, FlowContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference.
     * @see #setObject(com.tibco.xpd.xpdl2.Object)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivitySet_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ActivitySet#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Process</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Process#getActivitySets <em>Activity Sets</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process</em>' container reference.
     * @see #setProcess(com.tibco.xpd.xpdl2.Process)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivitySet_Process()
     * @see com.tibco.xpd.xpdl2.Process#getActivitySets
     * @model opposite="activitySets" transient="false"
     * @generated
     */
    com.tibco.xpd.xpdl2.Process getProcess();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ActivitySet#getProcess <em>Process</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process</em>' container reference.
     * @see #getProcess()
     * @generated
     */
    void setProcess(com.tibco.xpd.xpdl2.Process value);

} // ActivitySet
