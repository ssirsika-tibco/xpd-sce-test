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
 * A representation of the model object '<em><b>Model Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         An extension of OrgUnitBase that forms a model fragment known as a Model Template.
 *         Each Model Template consists of a hierarchy of OrgUnits, Positions and Sub-OrgUnits.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.ModelTemplate#getInstanceIdAttribute <em>Instance Id Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getModelTemplate()
 * @model extendedMetaData="name='ModelTemplate' kind='elementOnly'"
 * @generated
 */
public interface ModelTemplate extends ModelOrgUnit {
    /**
     * Returns the value of the '<em><b>Instance Id Attribute</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The root entity of a Model Template will carry additional attributes,
     *                the names of which will listed here (at least one should be assigned).
     *                Upon instantiation of the Model Template Instance, these named properties will be assigned
     *                values from associated LDAP Attributes, selected in the Organisation Extension Point
     *                configuration. These will form the uniquely distinguishing attributes of a Dynamic
     *                Organisation Participant.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Instance Id Attribute</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getModelTemplate_InstanceIdAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Token" required="true"
     *        extendedMetaData="kind='element' name='instance-id-attribute' namespace='##targetNamespace'"
     * @generated
     */
    EList<String> getInstanceIdAttribute();

} // ModelTemplate
