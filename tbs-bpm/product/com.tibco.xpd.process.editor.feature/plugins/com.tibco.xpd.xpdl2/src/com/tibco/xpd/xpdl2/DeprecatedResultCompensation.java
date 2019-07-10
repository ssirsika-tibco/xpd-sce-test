/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deprecated Result Compensation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.DeprecatedResultCompensation#getActivityId <em>Activity Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeprecatedResultCompensation()
 * @model extendedMetaData="name='ResultCompensation_._type' kind='elementOnly'"
 * @generated
 */
public interface DeprecatedResultCompensation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  This supplies the Id of the Activity to be Compensated. Used only for intermediate events or end events in the seuence flow. Events attached to the boundary of an activity already know the Id.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Id</em>' attribute.
     * @see #setActivityId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeprecatedResultCompensation_ActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ActivityId'"
     * @generated
     */
    String getActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DeprecatedResultCompensation#getActivityId <em>Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Id</em>' attribute.
     * @see #getActivityId()
     * @generated
     */
    void setActivityId(String value);

} // DeprecatedResultCompensation
