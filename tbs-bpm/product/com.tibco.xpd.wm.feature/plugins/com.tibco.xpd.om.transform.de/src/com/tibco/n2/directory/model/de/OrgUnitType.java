/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Org Unit Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Describes a type of organizational unit within an organization.
 *         Or they may be project teams, that are created only for the duration of a project.
 *         OrgUnitType also defines the types of positions that it may contain.
 *         They may also define the types of sub-org-units that it may contain.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.OrgUnitType#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.OrgUnitType#getPositionFeature <em>Position Feature</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.OrgUnitType#getOrgUnitFeature <em>Org Unit Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnitType()
 * @model extendedMetaData="name='OrgUnitType' kind='elementOnly'"
 * @generated
 */
public interface OrgUnitType extends EntityType {
    /**
     * Returns the value of the '<em><b>Choice Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Choice Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Choice Group</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnitType_ChoiceGroup()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='choiceGroup:5'"
     * @generated
     */
    FeatureMap getChoiceGroup();

    /**
     * Returns the value of the '<em><b>Position Feature</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Feature}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Position Feature</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Position Feature</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnitType_PositionFeature()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='position-feature' namespace='##targetNamespace' group='#choiceGroup:5'"
     * @generated
     */
    EList<Feature> getPositionFeature();

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
     * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnitType_OrgUnitFeature()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='org-unit-feature' namespace='##targetNamespace' group='#choiceGroup:5'"
     * @generated
     */
    EList<Feature> getOrgUnitFeature();

} // OrgUnitType
