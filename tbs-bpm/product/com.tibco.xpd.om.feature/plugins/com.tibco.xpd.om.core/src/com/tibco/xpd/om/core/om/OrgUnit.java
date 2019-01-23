/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Org Unit</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnit#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnit#getPositions <em>Positions</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnit#getOrgUnitType <em>Org Unit Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnit#getOutgoingRelationships <em>Outgoing Relationships</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnit#getIncomingRelationships <em>Incoming Relationships</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnit()
 * @model
 * @generated
 */
public interface OrgUnit extends OrgTypedElement, Authorizable, Allocable,
        Locatable, OrgNode {
    /**
     * Returns the value of the '<em><b>Feature</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Feature</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Feature</em>' reference.
     * @see #setFeature(OrgUnitFeature)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnit_Feature()
     * @model
     * @generated
     */
    OrgUnitFeature getFeature();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgUnit#getFeature <em>Feature</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Feature</em>' reference.
     * @see #getFeature()
     * @generated
     */
    void setFeature(OrgUnitFeature value);

    /**
     * Returns the value of the '<em><b>Outgoing Relationships</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgUnitRelationship}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getFrom <em>From</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outgoing Relationships</em>' reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outgoing Relationships</em>' reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnit_OutgoingRelationships()
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship#getFrom
     * @model opposite="from"
     * @generated
     */
    EList<OrgUnitRelationship> getOutgoingRelationships();

    /**
     * Returns the value of the '<em><b>Incoming Relationships</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgUnitRelationship}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getTo <em>To</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Incoming Relationships</em>' reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Incoming Relationships</em>' reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnit_IncomingRelationships()
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship#getTo
     * @model opposite="to"
     * @generated
     */
    EList<OrgUnitRelationship> getIncomingRelationships();

    /**
     * <!-- begin-user-doc --> Returns subUnits of this unit in hierarchy. Only
     * sub units from the same Organization will be returned. <!-- end-user-doc
     * -->
     * 
     * @model kind="operation"
     * @generated
     */
    EList<OrgUnit> getSubUnits();

    /**
     * <!-- begin-user-doc --> Returns parent OrgUnit or <code>null</code> if
     * the unit is a top level unit in the organization. <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    OrgUnit getParentOrgUnit();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<OrgUnitRelationship> getOutgoingHierachicalRelationships();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<OrgUnitRelationship> getOutgoingNonHierachicalRelationships();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    OrgUnitRelationship getIncomingHierachicalRelationship();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<OrgUnitRelationship> getIncomingNonHierachicalRelationships();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    Organization getOrganization();

    /**
     * Returns the value of the '<em><b>Positions</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Position}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Positions</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Positions</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnit_Positions()
     * @model containment="true"
     * @generated
     */
    EList<Position> getPositions();

    /**
     * Returns the value of the '<em><b>Org Unit Type</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Unit Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Org Unit Type</em>' reference.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnit_OrgUnitType()
     * @model resolveProxies="false" transient="true" changeable="false"
     *        volatile="true" derived="true"
     * @generated
     */
    OrgUnitType getOrgUnitType();

} // OrgUnit
