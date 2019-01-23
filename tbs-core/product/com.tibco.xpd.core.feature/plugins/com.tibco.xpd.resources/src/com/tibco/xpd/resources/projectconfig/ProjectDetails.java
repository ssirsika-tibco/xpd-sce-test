/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Details</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Project details such as id, version, status and destination environments
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getGlobalDestinations <em>Global Destinations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectDetails()
 * @model
 * @generated
 */
public interface ProjectDetails extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectDetails_Id()
     * @model default="" required="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * The default value is <code>"1.0.0.qualifier"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectDetails_Version()
     * @model default="1.0.0.qualifier" required="true"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

    /**
     * Returns the value of the '<em><b>Status</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.resources.projectconfig.ProjectStatus}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Status</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Status</em>' attribute.
     * @see com.tibco.xpd.resources.projectconfig.ProjectStatus
     * @see #setStatus(ProjectStatus)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectDetails_Status()
     * @model default="0" required="true"
     * @generated
     */
    ProjectStatus getStatus();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getStatus <em>Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Status</em>' attribute.
     * @see com.tibco.xpd.resources.projectconfig.ProjectStatus
     * @see #getStatus()
     * @generated
     */
    void setStatus(ProjectStatus value);

    /**
     * Returns the value of the '<em><b>Global Destinations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Global Destinations</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Global Destinations</em>' containment reference.
     * @see #setGlobalDestinations(Destinations)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectDetails_GlobalDestinations()
     * @model containment="true"
     * @generated
     */
    Destinations getGlobalDestinations();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getGlobalDestinations <em>Global Destinations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Global Destinations</em>' containment reference.
     * @see #getGlobalDestinations()
     * @generated
     */
    void setGlobalDestinations(Destinations value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    boolean isGlobalDestinationEnabled(String globalDestinationId);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<String> getEnabledGlobalDestinationIds();

} // ProjectDetails
