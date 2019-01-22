/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.pageactivitymodel;

import com.tibco.n2.common.datamodel.DataModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Work type definition. (This should be considered as the API specification of any work item created for this work type.)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityModelID <em>Activity Model ID</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityDescription <em>Activity Description</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleName <em>Module Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleVersion <em>Module Version</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getProcessName <em>Process Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getDataModel <em>Data Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage#getPageActivity()
 * @model extendedMetaData="name='PageActivity' kind='elementOnly'"
 * @generated
 */
public interface PageActivity extends EObject {
    /**
     * Returns the value of the '<em><b>Activity Model ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique ID of the activity model.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Model ID</em>' attribute.
     * @see #setActivityModelID(String)
     * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage#getPageActivity_ActivityModelID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityModelID' namespace='##targetNamespace'"
     * @generated
     */
    String getActivityModelID();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityModelID <em>Activity Model ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Model ID</em>' attribute.
     * @see #getActivityModelID()
     * @generated
     */
    void setActivityModelID(String value);

    /**
     * Returns the value of the '<em><b>Activity Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Textual description of the activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Description</em>' attribute.
     * @see #setActivityDescription(String)
     * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage#getPageActivity_ActivityDescription()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityDescription' namespace='##targetNamespace'"
     * @generated
     */
    String getActivityDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityDescription <em>Activity Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Description</em>' attribute.
     * @see #getActivityDescription()
     * @generated
     */
    void setActivityDescription(String value);

    /**
     * Returns the value of the '<em><b>Module Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Module Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Module Name</em>' attribute.
     * @see #setModuleName(String)
     * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage#getPageActivity_ModuleName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='moduleName' namespace='##targetNamespace'"
     * @generated
     */
    String getModuleName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleName <em>Module Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Module Name</em>' attribute.
     * @see #getModuleName()
     * @generated
     */
    void setModuleName(String value);

    /**
     * Returns the value of the '<em><b>Module Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Module Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Module Version</em>' attribute.
     * @see #setModuleVersion(String)
     * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage#getPageActivity_ModuleVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='moduleVersion' namespace='##targetNamespace'"
     * @generated
     */
    String getModuleVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleVersion <em>Module Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Module Version</em>' attribute.
     * @see #getModuleVersion()
     * @generated
     */
    void setModuleVersion(String value);

    /**
     * Returns the value of the '<em><b>Process Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Name</em>' attribute.
     * @see #setProcessName(String)
     * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage#getPageActivity_ProcessName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='processName' namespace='##targetNamespace'"
     * @generated
     */
    String getProcessName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getProcessName <em>Process Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Name</em>' attribute.
     * @see #getProcessName()
     * @generated
     */
    void setProcessName(String value);

    /**
     * Returns the value of the '<em><b>Data Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specification of INPUT, OUTPUT and INOUT parameters, which comprise the data model for the work type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Data Model</em>' containment reference.
     * @see #setDataModel(DataModel)
     * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage#getPageActivity_DataModel()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='dataModel' namespace='##targetNamespace'"
     * @generated
     */
    DataModel getDataModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getDataModel <em>Data Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Data Model</em>' containment reference.
     * @see #getDataModel()
     * @generated
     */
    void setDataModel(DataModel value);

} // PageActivity
