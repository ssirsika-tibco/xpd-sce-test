/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Reference#getActivityId <em>Activity Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getReference()
 * @model extendedMetaData="name='Reference_._type' kind='elementOnly'"
 * @generated
 */
public interface Reference extends Implementation, OtherAttributesContainer,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Should be the Id of an activity which invokes a subflow (independent or embedded) or a task. In the BPMN speck this atribute is called ProcessRef
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Id</em>' attribute.
     * @see #setActivityId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getReference_ActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='ActivityId'"
     * @generated
     */
    String getActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Reference#getActivityId <em>Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Id</em>' attribute.
     * @see #getActivityId()
     * @generated
     */
    void setActivityId(String value);

} // Reference
