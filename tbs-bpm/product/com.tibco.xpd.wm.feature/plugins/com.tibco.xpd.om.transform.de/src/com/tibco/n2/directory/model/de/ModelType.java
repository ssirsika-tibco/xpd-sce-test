/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getMetaModel <em>Meta Model</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getModelTemplate <em>Model Template</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getCapability <em>Capability</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getCapabilityCategory <em>Capability Category</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getPrivilege <em>Privilege</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getPrivilegeCategory <em>Privilege Category</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getResourceAttribute <em>Resource Attribute</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getResource <em>Resource</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getModelType()
 * @model extendedMetaData="name='model_._type' kind='elementOnly'"
 * @generated
 */
public interface ModelType extends EObject {
    /**
     * Returns the value of the '<em><b>Meta Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Meta Model</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Meta Model</em>' containment reference.
     * @see #setMetaModel(MetaModel)
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_MetaModel()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='meta-model' namespace='##targetNamespace'"
     * @generated
     */
    MetaModel getMetaModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.ModelType#getMetaModel <em>Meta Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Meta Model</em>' containment reference.
     * @see #getMetaModel()
     * @generated
     */
    void setMetaModel(MetaModel value);

    /**
     * Returns the value of the '<em><b>Choice Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Choice Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Choice Group</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_ChoiceGroup()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='choiceGroup:1'"
     * @generated
     */
    FeatureMap getChoiceGroup();

    /**
     * Returns the value of the '<em><b>Model Template</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.ModelTemplate}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Model Template</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Model Template</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_ModelTemplate()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='model-template' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<ModelTemplate> getModelTemplate();

    /**
     * Returns the value of the '<em><b>Capability</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Capability}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Capability</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Capability</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Capability()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='capability' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<Capability> getCapability();

    /**
     * Returns the value of the '<em><b>Capability Category</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.CapabilityCategory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Capability Category</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Capability Category</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_CapabilityCategory()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='capability-category' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<CapabilityCategory> getCapabilityCategory();

    /**
     * Returns the value of the '<em><b>Privilege</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Privilege}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Privilege</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Privilege</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Privilege()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='privilege' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<Privilege> getPrivilege();

    /**
     * Returns the value of the '<em><b>Privilege Category</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.PrivilegeCategory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Privilege Category</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Privilege Category</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_PrivilegeCategory()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='privilege-category' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<PrivilegeCategory> getPrivilegeCategory();

    /**
     * Returns the value of the '<em><b>Location</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Location}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Location()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='location' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<Location> getLocation();

    /**
     * Returns the value of the '<em><b>Group</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Group}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Group()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='group' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<Group> getGroup();

    /**
     * Returns the value of the '<em><b>Organization</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Organization}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Organization</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Organization</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Organization()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='organization' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<Organization> getOrganization();

    /**
     * Returns the value of the '<em><b>Resource Attribute</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.AttributeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Attribute</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Attribute</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_ResourceAttribute()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='resource-attribute' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<AttributeType> getResourceAttribute();

    /**
     * Returns the value of the '<em><b>System Action</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.SystemAction}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>System Action</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>System Action</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_SystemAction()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='system-action' namespace='##targetNamespace' group='#choiceGroup:1'"
     * @generated
     */
    EList<SystemAction> getSystemAction();

    /**
     * Returns the value of the '<em><b>Resource</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Resource}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Resource()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='resource' namespace='##targetNamespace'"
     * @generated
     */
    EList<Resource> getResource();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.ModelType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getModelType_Version()
     * @model dataType="com.tibco.n2.directory.model.de.VersionNumber" required="true"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.ModelType#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // ModelType
