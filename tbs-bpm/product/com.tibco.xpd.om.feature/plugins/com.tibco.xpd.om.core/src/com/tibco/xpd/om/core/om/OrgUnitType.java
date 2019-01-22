/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Org Unit Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitType#getOrgUnitFeatures <em>Org Unit Features</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitType#getPositionFeatures <em>Position Features</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitType()
 * @model
 * @generated
 */
public interface OrgUnitType extends OrgElementType {
    /**
     * Returns the value of the '<em><b>Org Unit Features</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgUnitFeature}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Unit Features</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Unit Features</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitType_OrgUnitFeatures()
     * @model containment="true"
     * @generated
     */
    EList<OrgUnitFeature> getOrgUnitFeatures();

    /**
     * Returns the value of the '<em><b>Position Features</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.PositionFeature}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Position Features</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Position Features</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitType_PositionFeatures()
     * @model containment="true"
     * @generated
     */
    EList<PositionFeature> getPositionFeatures();

} // OrgUnitType
