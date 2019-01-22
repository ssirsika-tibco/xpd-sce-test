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
 * A representation of the model object '<em><b>Work Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a work model.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getBaseModelInfo <em>Base Model Info</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getWorkModelSpecification <em>Work Model Specification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getWorkModelEntities <em>Work Model Entities</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getWorkModelTypes <em>Work Model Types</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getItemPrivileges <em>Item Privileges</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getWorkModelScripts <em>Work Model Scripts</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getAttributeAliasList <em>Attribute Alias List</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModel#getWorkModelUID <em>Work Model UID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel()
 * @model extendedMetaData="name='WorkModel' kind='elementOnly'"
 * @generated
 */
public interface WorkModel extends EObject {
    /**
     * Returns the value of the '<em><b>Base Model Info</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Base Model Info</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Base Model Info</em>' containment reference.
     * @see #setBaseModelInfo(BaseModelInfo)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_BaseModelInfo()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='BaseModelInfo'"
     * @generated
     */
    BaseModelInfo getBaseModelInfo();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getBaseModelInfo <em>Base Model Info</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Base Model Info</em>' containment reference.
     * @see #getBaseModelInfo()
     * @generated
     */
    void setBaseModelInfo(BaseModelInfo value);

    /**
     * Returns the value of the '<em><b>Work Model Specification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model Specification</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model Specification</em>' containment reference.
     * @see #setWorkModelSpecification(WorkModelSpecification)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_WorkModelSpecification()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkModelSpecification'"
     * @generated
     */
    WorkModelSpecification getWorkModelSpecification();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelSpecification <em>Work Model Specification</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model Specification</em>' containment reference.
     * @see #getWorkModelSpecification()
     * @generated
     */
    void setWorkModelSpecification(WorkModelSpecification value);

    /**
     * Returns the value of the '<em><b>Work Model Entities</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model Entities</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model Entities</em>' containment reference.
     * @see #setWorkModelEntities(WorkModelEntities)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_WorkModelEntities()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkModelEntities'"
     * @generated
     */
    WorkModelEntities getWorkModelEntities();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelEntities <em>Work Model Entities</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model Entities</em>' containment reference.
     * @see #getWorkModelEntities()
     * @generated
     */
    void setWorkModelEntities(WorkModelEntities value);

    /**
     * Returns the value of the '<em><b>Work Model Types</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model Types</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model Types</em>' containment reference.
     * @see #setWorkModelTypes(WorkModelTypes)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_WorkModelTypes()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkModelTypes'"
     * @generated
     */
    WorkModelTypes getWorkModelTypes();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelTypes <em>Work Model Types</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model Types</em>' containment reference.
     * @see #getWorkModelTypes()
     * @generated
     */
    void setWorkModelTypes(WorkModelTypes value);

    /**
     * Returns the value of the '<em><b>Item Privileges</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Item Privileges</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Item Privileges</em>' containment reference.
     * @see #setItemPrivileges(ItemPrivilege)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_ItemPrivileges()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ItemPrivileges'"
     * @generated
     */
    ItemPrivilege getItemPrivileges();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getItemPrivileges <em>Item Privileges</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Privileges</em>' containment reference.
     * @see #getItemPrivileges()
     * @generated
     */
    void setItemPrivileges(ItemPrivilege value);

    /**
     * Returns the value of the '<em><b>Work Model Scripts</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model Scripts</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model Scripts</em>' containment reference.
     * @see #setWorkModelScripts(WorkModelScripts)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_WorkModelScripts()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WorkModelScripts'"
     * @generated
     */
    WorkModelScripts getWorkModelScripts();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelScripts <em>Work Model Scripts</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model Scripts</em>' containment reference.
     * @see #getWorkModelScripts()
     * @generated
     */
    void setWorkModelScripts(WorkModelScripts value);

    /**
     * Returns the value of the '<em><b>Attribute Alias List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * If supplied will be a group of attribute aliases that may be used to name attributes at runtime.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute Alias List</em>' containment reference.
     * @see #setAttributeAliasList(AttributeAliasListType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_AttributeAliasList()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='AttributeAliasList'"
     * @generated
     */
    AttributeAliasListType getAttributeAliasList();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getAttributeAliasList <em>Attribute Alias List</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Alias List</em>' containment reference.
     * @see #getAttributeAliasList()
     * @generated
     */
    void setAttributeAliasList(AttributeAliasListType value);

    /**
     * Returns the value of the '<em><b>Work Model UID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique ID (or GUID) of the work model.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Model UID</em>' attribute.
     * @see #setWorkModelUID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModel_WorkModelUID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='workModelUID'"
     * @generated
     */
    String getWorkModelUID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelUID <em>Work Model UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model UID</em>' attribute.
     * @see #getWorkModelUID()
     * @generated
     */
    void setWorkModelUID(String value);

} // WorkModel
