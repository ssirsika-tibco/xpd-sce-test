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
 * A representation of the model object '<em><b>Applications Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationsContainer#getApplications <em>Applications</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationsContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ApplicationsContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Applications</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Application}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Applications</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Applications</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationsContainer_Applications()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Application' namespace='##targetNamespace' wrap='Applications'"
     * @generated
     */
    EList<Application> getApplications();

} // ApplicationsContainer
