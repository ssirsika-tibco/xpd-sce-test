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
 * A representation of the model object '<em><b>Model Org Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         An extension of OrgUnitBase that forms a model fragment known as a Model Template.
 *         Each Model Template consists of a hierarchy of OrgUnits, Positions and Sub-OrgUnits.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelOrgUnit#getModelPosition <em>Model Position</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelOrgUnit#getModelOrgUnit <em>Model Org Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getModelOrgUnit()
 * @model extendedMetaData="name='ModelOrgUnit' kind='elementOnly'"
 * @generated
 */
public interface ModelOrgUnit extends OrgUnitBase {
    /**
     * Returns the value of the '<em><b>Model Position</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Position}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Defines a Position contained within this OrgUnit.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Model Position</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelOrgUnit_ModelPosition()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='model-position' namespace='##targetNamespace'"
     * @generated
     */
    EList<Position> getModelPosition();

    /**
     * Returns the value of the '<em><b>Model Org Unit</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.ModelOrgUnit}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Defines sub-OrgUnits to this OrgUnit; creating a hierarchical structure.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Model Org Unit</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelOrgUnit_ModelOrgUnit()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='model-org-unit' namespace='##targetNamespace'"
     * @generated
     */
    EList<ModelOrgUnit> getModelOrgUnit();

} // ModelOrgUnit
