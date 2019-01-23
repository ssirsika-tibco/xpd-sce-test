/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;
import com.tibco.n2.common.organisation.api.XmlResourceQuery;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Model Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a work model entity, either as a set of entities or as an expression to evalute at runtime.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelEntity#getEntityQuery <em>Entity Query</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelEntity#getEntities <em>Entities</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelEntity#getDistributionStrategy <em>Distribution Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelEntity()
 * @model extendedMetaData="name='WorkModelEntity' kind='elementOnly'"
 * @generated
 */
public interface WorkModelEntity extends EObject {
    /**
     * Returns the value of the '<em><b>Entity Query</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This is an expression that should be used by BRM at runtime to query Directory Engine for a set of organistion enities to use when scheduling the item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Query</em>' containment reference.
     * @see #setEntityQuery(XmlResourceQuery)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelEntity_EntityQuery()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='EntityQuery'"
     * @generated
     */
    XmlResourceQuery getEntityQuery();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelEntity#getEntityQuery <em>Entity Query</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Query</em>' containment reference.
     * @see #getEntityQuery()
     * @generated
     */
    void setEntityQuery(XmlResourceQuery value);

    /**
     * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.organisation.api.XmlModelEntityId}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * A set of organisational entities that should be used by BRM at runtime when scheduling the item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entities</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelEntity_Entities()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Entities'"
     * @generated
     */
    EList<XmlModelEntityId> getEntities();

    /**
     * Returns the value of the '<em><b>Distribution Strategy</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.DistributionStrategy}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Whether to use ALLOCATE or OFFER with the provided set to entities or entity expression.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Distribution Strategy</em>' attribute.
     * @see com.tibco.n2.brm.api.DistributionStrategy
     * @see #isSetDistributionStrategy()
     * @see #unsetDistributionStrategy()
     * @see #setDistributionStrategy(DistributionStrategy)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelEntity_DistributionStrategy()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='distributionStrategy'"
     * @generated
     */
    DistributionStrategy getDistributionStrategy();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelEntity#getDistributionStrategy <em>Distribution Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Distribution Strategy</em>' attribute.
     * @see com.tibco.n2.brm.api.DistributionStrategy
     * @see #isSetDistributionStrategy()
     * @see #unsetDistributionStrategy()
     * @see #getDistributionStrategy()
     * @generated
     */
    void setDistributionStrategy(DistributionStrategy value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkModelEntity#getDistributionStrategy <em>Distribution Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDistributionStrategy()
     * @see #getDistributionStrategy()
     * @see #setDistributionStrategy(DistributionStrategy)
     * @generated
     */
    void unsetDistributionStrategy();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkModelEntity#getDistributionStrategy <em>Distribution Strategy</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Distribution Strategy</em>' attribute is set.
     * @see #unsetDistributionStrategy()
     * @see #getDistributionStrategy()
     * @see #setDistributionStrategy(DistributionStrategy)
     * @generated
     */
    boolean isSetDistributionStrategy();

} // WorkModelEntity
