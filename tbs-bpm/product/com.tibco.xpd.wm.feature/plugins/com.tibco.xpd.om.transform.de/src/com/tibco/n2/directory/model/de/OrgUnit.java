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
 * A representation of the model object '<em><b>Org Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Defines an organizational unit within an organization. organizational units may be entire
 *         departments; such as accounts department or call centre, that are likely to be
 *         long-lived.
 *         An OrgUnit may, in addition to its normal role, be chosen as an Extension Point.  
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.OrgUnit#getModelTemplateRef <em>Model Template Ref</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.OrgUnit#getPosition <em>Position</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.OrgUnit#getOrgUnit <em>Org Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnit()
 * @model extendedMetaData="name='OrgUnit' kind='elementOnly'"
 * @generated
 */
public interface OrgUnit extends OrgUnitBase {
    /**
     * Returns the value of the '<em><b>Model Template Ref</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               Identifies this OrgUnit as a Model Extension Point, and identifies the ModelTemplate
     *               element that will form the template for the dynamically generated Model Instances.
     *               When an instance is created this OrgUnit will become its parent within the OrgUnit
     *               hierarchy.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Model Template Ref</em>' reference.
     * @see #setModelTemplateRef(ModelTemplate)
     * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnit_ModelTemplateRef()
     * @model resolveProxies="false"
     *        extendedMetaData="kind='element' name='model-template-ref' namespace='##targetNamespace'"
     * @generated
     */
    ModelTemplate getModelTemplateRef();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.OrgUnit#getModelTemplateRef <em>Model Template Ref</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Model Template Ref</em>' reference.
     * @see #getModelTemplateRef()
     * @generated
     */
    void setModelTemplateRef(ModelTemplate value);

    /**
     * Returns the value of the '<em><b>Position</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Position}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Defines a Position contained within this OrgUnit.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Position</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnit_Position()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='position' namespace='##targetNamespace'"
     * @generated
     */
    EList<Position> getPosition();

    /**
     * Returns the value of the '<em><b>Org Unit</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.OrgUnit}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Defines sub-OrgUnits to this OrgUnit; creating a hierarchical structure.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Org Unit</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getOrgUnit_OrgUnit()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='org-unit' namespace='##targetNamespace'"
     * @generated
     */
    EList<OrgUnit> getOrgUnit();

} // OrgUnit
