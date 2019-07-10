/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete By Composite Identifiers Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType#getCompositeIdentifier <em>Composite Identifier</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDeleteByCompositeIdentifiersType()
 * @model extendedMetaData="name='DeleteByCompositeIdentifiers_._type' kind='elementOnly'"
 * @generated
 */
public interface DeleteByCompositeIdentifiersType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' attribute list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDeleteByCompositeIdentifiersType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Composite Identifier</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.CompositeIdentifierType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Identifier name and source value pairs
     * <!-- end-model-doc -->
     * @return the value of the '<em>Composite Identifier</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDeleteByCompositeIdentifiersType_CompositeIdentifier()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='CompositeIdentifier' group='#group:0' namespace='##targetNamespace'"
     * @generated
     */
    EList<CompositeIdentifierType> getCompositeIdentifier();

} // DeleteByCompositeIdentifiersType
