/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Namespace Prefix Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is added under xpdl:Activity for web service
 * activities that use XPath mappings so that we can relate namespace to prefix
 * in existing mappings when these change in the WSDL.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.NamespacePrefixMap#getNamespaceEntries <em>Namespace Entries</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.NamespacePrefixMap#isPrefixMappingDisabled <em>Prefix Mapping Disabled</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getNamespacePrefixMap()
 * @model extendedMetaData="name='NamespacePrefixMap' kind='empty'"
 * @generated
 */
public interface NamespacePrefixMap extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Namespace Entries</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.NamespaceMapEntry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Namespace Entries</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Namespace Entries</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getNamespacePrefixMap_NamespaceEntries()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='NamespaceEntry' namespace='##targetNamespace' wrap='NamespaceEntries'"
     * @generated
     */
    EList<NamespaceMapEntry> getNamespaceEntries();

    /**
     * Returns the value of the '<em><b>Prefix Mapping Disabled</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Prefix Mapping Disabled</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Prefix Mapping Disabled</em>' attribute.
     * @see #setPrefixMappingDisabled(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getNamespacePrefixMap_PrefixMappingDisabled()
     * @model default="false" unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='PrefixMappingDisabled'"
     * @generated
     */
    boolean isPrefixMappingDisabled();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.NamespacePrefixMap#isPrefixMappingDisabled <em>Prefix Mapping Disabled</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Prefix Mapping Disabled</em>' attribute.
     * @see #isPrefixMappingDisabled()
     * @generated
     */
    void setPrefixMappingDisabled(boolean value);

} // NamespacePrefixMap
