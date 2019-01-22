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
 * A representation of the model object '<em><b>Entity Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         The sub-class of all meta-model entities. These meta-model entities provide
 *         additional schema information (and attributes) for the TypedEntities that
 *         reference them.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.EntityType#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getEntityType()
 * @model abstract="true"
 *        extendedMetaData="name='EntityType' kind='elementOnly'"
 * @generated
 */
public interface EntityType extends NamedEntity {
    /**
     * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.AttributeType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Describes an attribute that TypedEntities that reference this EntityType can hold.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getEntityType_Attribute()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='attribute' namespace='##targetNamespace'"
     * @generated
     */
    EList<AttributeType> getAttribute();

} // EntityType
