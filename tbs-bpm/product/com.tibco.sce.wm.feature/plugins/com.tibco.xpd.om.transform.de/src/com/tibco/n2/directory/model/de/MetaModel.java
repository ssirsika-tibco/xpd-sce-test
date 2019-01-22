/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Meta Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         The root node of the meta-model document.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.MetaModel#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.MetaModel#getLocationType <em>Location Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.MetaModel#getPositionType <em>Position Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.MetaModel#getOrgUnitType <em>Org Unit Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.MetaModel#getOrganizationType <em>Organization Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getMetaModel()
 * @model extendedMetaData="name='MetaModel' kind='elementOnly'"
 * @generated
 */
public interface MetaModel extends EObject {
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
     * @see com.tibco.n2.directory.model.de.DePackage#getMetaModel_ChoiceGroup()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='choiceGroup:0'"
     * @generated
     */
    FeatureMap getChoiceGroup();

    /**
     * Returns the value of the '<em><b>Location Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.LocationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location Type</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getMetaModel_LocationType()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='location-type' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<LocationType> getLocationType();

    /**
     * Returns the value of the '<em><b>Position Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.PositionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Position Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Position Type</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getMetaModel_PositionType()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='position-type' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<PositionType> getPositionType();

    /**
     * Returns the value of the '<em><b>Org Unit Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.OrgUnitType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Unit Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Unit Type</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getMetaModel_OrgUnitType()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='org-unit-type' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<OrgUnitType> getOrgUnitType();

    /**
     * Returns the value of the '<em><b>Organization Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.OrganizationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Organization Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Organization Type</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getMetaModel_OrganizationType()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='organization-type' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<OrganizationType> getOrganizationType();

} // MetaModel
