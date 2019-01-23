/**
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capability Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *       	Allows Categories to be grouped into categories; perhaps by functionality. The categories can also be nested.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.CapabilityCategory#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.CapabilityCategory#getCapabilityCategory <em>Capability Category</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.CapabilityCategory#getCapability <em>Capability</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getCapabilityCategory()
 * @model extendedMetaData="name='CapabilityCategory' kind='elementOnly'"
 * @generated
 */
public interface CapabilityCategory extends NamedEntity {
    /**
     * Returns the value of the '<em><b>Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getCapabilityCategory_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:4'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Capability Category</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.CapabilityCategory}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 	            Defines sub-categories to this Category; creating a hierarchical structure.
     * 	          
     * <!-- end-model-doc -->
     * @return the value of the '<em>Capability Category</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getCapabilityCategory_CapabilityCategory()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='capability-category' namespace='##targetNamespace' group='#group:4'"
     * @generated
     */
    EList<CapabilityCategory> getCapabilityCategory();

    /**
     * Returns the value of the '<em><b>Capability</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Capability}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 	            A capability that is contained within this category.
     * 	          
     * <!-- end-model-doc -->
     * @return the value of the '<em>Capability</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getCapabilityCategory_Capability()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='capability' namespace='##targetNamespace' group='#group:4'"
     * @generated
     */
    EList<Capability> getCapability();

} // CapabilityCategory
