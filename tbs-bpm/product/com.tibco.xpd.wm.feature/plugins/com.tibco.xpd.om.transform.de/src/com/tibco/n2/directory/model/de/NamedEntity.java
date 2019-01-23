/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         This is the super-class of all entities (model and meta-model) that carry an ID and
 *         name. The ID will uniquely identify the entity within all organizational models, and
 *         can also be used to create references between elements in the XML document.
 *         The name is purely descriptive.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.NamedEntity#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.NamedEntity#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.NamedEntity#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.NamedEntity#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getNamedEntity()
 * @model abstract="true"
 *        extendedMetaData="name='NamedEntity' kind='empty'"
 * @generated
 */
public interface NamedEntity extends EObject {
    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           The entity's description.
     *         
     * <!-- end-model-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getNamedEntity_Description()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.NamedEntity#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           The entity's unique identifier.
     *         
     * <!-- end-model-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getNamedEntity_Id()
     * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.NCName" required="true"
     *        extendedMetaData="kind='attribute' name='id'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.NamedEntity#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           The alternative (display) name of the entity.
     *         
     * <!-- end-model-doc -->
     * @return the value of the '<em>Label</em>' attribute.
     * @see #setLabel(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getNamedEntity_Label()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='label'"
     * @generated
     */
    String getLabel();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.NamedEntity#getLabel <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Label</em>' attribute.
     * @see #getLabel()
     * @generated
     */
    void setLabel(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           The name of the entity.
     *         
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getNamedEntity_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.NamedEntity#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // NamedEntity
