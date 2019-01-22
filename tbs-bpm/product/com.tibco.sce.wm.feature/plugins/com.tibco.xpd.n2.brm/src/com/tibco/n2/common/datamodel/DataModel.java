/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Sets of INPUT, OUTPUT and INOUT parameters that define the complete data model for a work item or page activity.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.DataModel#getInputs <em>Inputs</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.DataModel#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.DataModel#getInouts <em>Inouts</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.datamodel.DatamodelPackage#getDataModel()
 * @model extendedMetaData="name='DataModel' kind='elementOnly'"
 * @generated
 */
public interface DataModel extends EObject {
    /**
     * Returns the value of the '<em><b>Inputs</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.datamodel.FieldType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * An array of parameters passed to a work item or page activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Inputs</em>' containment reference list.
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getDataModel_Inputs()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='inputs'"
     * @generated
     */
    EList<FieldType> getInputs();

    /**
     * Returns the value of the '<em><b>Outputs</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.datamodel.FieldType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * An array of parameters passed from a work item or page activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Outputs</em>' containment reference list.
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getDataModel_Outputs()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='outputs'"
     * @generated
     */
    EList<FieldType> getOutputs();

    /**
     * Returns the value of the '<em><b>Inouts</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.datamodel.FieldType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * An array of parameters passed to/from a work item or page activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Inouts</em>' containment reference list.
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getDataModel_Inouts()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='inouts'"
     * @generated
     */
    EList<FieldType> getInouts();

} // DataModel
