/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Managed Object ID</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * ID of a specific version of a work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ManagedObjectID#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getManagedObjectID()
 * @model extendedMetaData="name='ManagedObjectID' kind='empty'"
 * @generated
 */
public interface ManagedObjectID extends ObjectID {
    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Version of the work item.
     * 
     * If not set, the latest version will be used.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #isSetVersion()
     * @see #unsetVersion()
     * @see #setVersion(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getManagedObjectID_Version()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    long getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ManagedObjectID#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #isSetVersion()
     * @see #unsetVersion()
     * @see #getVersion()
     * @generated
     */
    void setVersion(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ManagedObjectID#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetVersion()
     * @see #getVersion()
     * @see #setVersion(long)
     * @generated
     */
    void unsetVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ManagedObjectID#getVersion <em>Version</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Version</em>' attribute is set.
     * @see #unsetVersion()
     * @see #getVersion()
     * @see #setVersion(long)
     * @generated
     */
    boolean isSetVersion();

} // ManagedObjectID
