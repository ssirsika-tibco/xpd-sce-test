/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Item Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base information required for all work items.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.BaseItemInfo#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.BaseItemInfo#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.BaseItemInfo#getDistributionStrategy <em>Distribution Strategy</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.BaseItemInfo#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.BaseItemInfo#getPriority <em>Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseItemInfo()
 * @model abstract="true"
 *        extendedMetaData="name='BaseItemInfo' kind='elementOnly'"
 * @generated
 */
public interface BaseItemInfo extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseItemInfo_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Textual description of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseItemInfo_Description()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Distribution Strategy</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.DistributionStrategy}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Distribution strategy to be used for the work item - either OFFER or ALLOCATE.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Distribution Strategy</em>' attribute.
     * @see com.tibco.n2.brm.api.DistributionStrategy
     * @see #isSetDistributionStrategy()
     * @see #unsetDistributionStrategy()
     * @see #setDistributionStrategy(DistributionStrategy)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseItemInfo_DistributionStrategy()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='distributionStrategy'"
     * @generated
     */
    DistributionStrategy getDistributionStrategy();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getDistributionStrategy <em>Distribution Strategy</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getDistributionStrategy <em>Distribution Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDistributionStrategy()
     * @see #getDistributionStrategy()
     * @see #setDistributionStrategy(DistributionStrategy)
     * @generated
     */
    void unsetDistributionStrategy();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getDistributionStrategy <em>Distribution Strategy</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Distribution Strategy</em>' attribute is set.
     * @see #unsetDistributionStrategy()
     * @see #getDistributionStrategy()
     * @see #setDistributionStrategy(DistributionStrategy)
     * @generated
     */
    boolean isSetDistributionStrategy();

    /**
     * Returns the value of the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work group that the work item belongs to.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #setGroupID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseItemInfo_GroupID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='attribute' name='groupID'"
     * @generated
     */
    long getGroupID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #getGroupID()
     * @generated
     */
    void setGroupID(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    void unsetGroupID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getGroupID <em>Group ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Group ID</em>' attribute is set.
     * @see #unsetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    boolean isSetGroupID();

    /**
     * Returns the value of the '<em><b>Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Priority of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Priority</em>' attribute.
     * @see #isSetPriority()
     * @see #unsetPriority()
     * @see #setPriority(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseItemInfo_Priority()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='priority'"
     * @generated
     */
    int getPriority();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getPriority <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Priority</em>' attribute.
     * @see #isSetPriority()
     * @see #unsetPriority()
     * @see #getPriority()
     * @generated
     */
    void setPriority(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getPriority <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPriority()
     * @see #getPriority()
     * @see #setPriority(int)
     * @generated
     */
    void unsetPriority();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.BaseItemInfo#getPriority <em>Priority</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Priority</em>' attribute is set.
     * @see #unsetPriority()
     * @see #getPriority()
     * @see #setPriority(int)
     * @generated
     */
    boolean isSetPriority();

} // BaseItemInfo
