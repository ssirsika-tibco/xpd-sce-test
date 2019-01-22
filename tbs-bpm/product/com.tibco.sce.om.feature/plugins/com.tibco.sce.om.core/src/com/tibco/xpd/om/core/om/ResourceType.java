/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.ResourceType#isHumanResourceType <em>Human Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.ResourceType#isDurableResourceType <em>Durable Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.ResourceType#isConsumableResourceType <em>Consumable Resource Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getResourceType()
 * @model
 * @generated
 */
public interface ResourceType extends OrgElementType {

    /**
     * Returns the value of the '<em><b>Human Resource Type</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Human Resource Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Human Resource Type</em>' attribute.
     * @see com.tibco.xpd.om.core.om.OMPackage#getResourceType_HumanResourceType()
     * @model default="false" required="true" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    boolean isHumanResourceType();

    /**
     * Returns the value of the '<em><b>Durable Resource Type</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Durable Resource Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Durable Resource Type</em>' attribute.
     * @see com.tibco.xpd.om.core.om.OMPackage#getResourceType_DurableResourceType()
     * @model default="false" required="true" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    boolean isDurableResourceType();

    /**
     * Returns the value of the '<em><b>Consumable Resource Type</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Consumable Resource Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Consumable Resource Type</em>' attribute.
     * @see com.tibco.xpd.om.core.om.OMPackage#getResourceType_ConsumableResourceType()
     * @model default="false" required="true" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    boolean isConsumableResourceType();
} // ResourceType
