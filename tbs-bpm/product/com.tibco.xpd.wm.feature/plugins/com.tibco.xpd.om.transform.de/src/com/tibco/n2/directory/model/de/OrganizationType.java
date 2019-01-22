/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Organization Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Describes an organization as a legal entity in which organizational units reside.
 *         A meta-model can define several types of organization, and references between
 *         the organizational units can span organizations.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.OrganizationType#getOrgUnitFeature <em>Org Unit Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getOrganizationType()
 * @model extendedMetaData="name='OrganizationType' kind='elementOnly'"
 * @generated
 */
public interface OrganizationType extends EntityType {
    /**
     * Returns the value of the '<em><b>Org Unit Feature</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Feature}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Unit Feature</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Unit Feature</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getOrganizationType_OrgUnitFeature()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='org-unit-feature' namespace='##targetNamespace'"
     * @generated
     */
    EList<Feature> getOrgUnitFeature();

} // OrganizationType
