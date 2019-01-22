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
 * A representation of the model object '<em><b>Item Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Context of the work item, as supplied by the application that scheduled the work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ItemContext#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemContext#getActivityName <em>Activity Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemContext#getAppInstance <em>App Instance</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemContext#getAppName <em>App Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemContext#getAppID <em>App ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemContext#getAppInstanceDescription <em>App Instance Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getItemContext()
 * @model extendedMetaData="name='ItemContext' kind='elementOnly'"
 * @generated
 */
public interface ItemContext extends EObject {
    /**
     * Returns the value of the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the activity from which the work item was generated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity ID</em>' attribute.
     * @see #setActivityID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemContext_ActivityID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityID'"
     * @generated
     */
    String getActivityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemContext#getActivityID <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity ID</em>' attribute.
     * @see #getActivityID()
     * @generated
     */
    void setActivityID(String value);

    /**
     * Returns the value of the '<em><b>Activity Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the activity from which the work item was generated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Name</em>' attribute.
     * @see #setActivityName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemContext_ActivityName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityName'"
     * @generated
     */
    String getActivityName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemContext#getActivityName <em>Activity Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Name</em>' attribute.
     * @see #getActivityName()
     * @generated
     */
    void setActivityName(String value);

    /**
     * Returns the value of the '<em><b>App Instance</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Identifier of the application instance.
     * <!-- end-model-doc -->
     * @return the value of the '<em>App Instance</em>' attribute.
     * @see #setAppInstance(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemContext_AppInstance()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='appInstance'"
     * @generated
     */
    String getAppInstance();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemContext#getAppInstance <em>App Instance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>App Instance</em>' attribute.
     * @see #getAppInstance()
     * @generated
     */
    void setAppInstance(String value);

    /**
     * Returns the value of the '<em><b>App Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the application.
     * <!-- end-model-doc -->
     * @return the value of the '<em>App Name</em>' attribute.
     * @see #setAppName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemContext_AppName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='appName'"
     * @generated
     */
    String getAppName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemContext#getAppName <em>App Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>App Name</em>' attribute.
     * @see #getAppName()
     * @generated
     */
    void setAppName(String value);

    /**
     * Returns the value of the '<em><b>App ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the application.
     * <!-- end-model-doc -->
     * @return the value of the '<em>App ID</em>' attribute.
     * @see #setAppID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemContext_AppID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='appID'"
     * @generated
     */
    String getAppID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemContext#getAppID <em>App ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>App ID</em>' attribute.
     * @see #getAppID()
     * @generated
     */
    void setAppID(String value);

    /**
     * Returns the value of the '<em><b>App Instance Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Description of the application instance.
     * <!-- end-model-doc -->
     * @return the value of the '<em>App Instance Description</em>' attribute.
     * @see #setAppInstanceDescription(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemContext_AppInstanceDescription()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='appInstanceDescription'"
     * @generated
     */
    String getAppInstanceDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemContext#getAppInstanceDescription <em>App Instance Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>App Instance Description</em>' attribute.
     * @see #getAppInstanceDescription()
     * @generated
     */
    void setAppInstanceDescription(String value);

} // ItemContext
