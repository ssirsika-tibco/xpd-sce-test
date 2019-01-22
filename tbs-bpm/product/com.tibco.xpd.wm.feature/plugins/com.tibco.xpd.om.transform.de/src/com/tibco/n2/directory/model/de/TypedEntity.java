/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Typed Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         The sub-class of org-model entities that can be defined by an EntityType within
 *         the meta-model. These are Location, Position, OrgUnit and Organization.
 *         If the referenced EntityType carries any AttributeType definitions, the TypedEntity
 *         can carry values for those attributes.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.TypedEntity#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.TypedEntity#getDefinition <em>Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getTypedEntity()
 * @model abstract="true"
 *        extendedMetaData="name='TypedEntity' kind='elementOnly'"
 * @generated
 */
public interface TypedEntity extends NamedEntity {
    /**
     * Returns the value of the '<em><b>Attribute Value</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Attribute}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 The value of an AttributeType configured in the referenced EntityType.
     *                 The particular AttributeType is identified by the enclosed "definition"
     *                 reference.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute Value</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getTypedEntity_AttributeValue()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='attribute-value' namespace='##targetNamespace'"
     * @generated
     */
    EList<Attribute> getAttributeValue();

    /**
     * Returns the value of the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               The identity reference to the EntityType from which this entity is derived,
     *               if any.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Definition</em>' reference.
     * @see #setDefinition(EntityType)
     * @see com.tibco.n2.directory.model.de.DePackage#getTypedEntity_Definition()
     * @model resolveProxies="false"
     *        extendedMetaData="kind='attribute' name='definition'"
     * @generated
     */
    EntityType getDefinition();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.TypedEntity#getDefinition <em>Definition</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Definition</em>' reference.
     * @see #getDefinition()
     * @generated
     */
    void setDefinition(EntityType value);

} // TypedEntity
