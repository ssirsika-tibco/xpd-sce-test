/**
 */
package com.tibco.xpd.globalSignalDefinition;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Signal Definitions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getGlobalSignals <em>Global Signals</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getFormatVersion <em>Format Version</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignalDefinitions()
 * @model extendedMetaData="name='GlobalSignalDefinitions' kind='elementOnly'"
 * @generated
 */
public interface GlobalSignalDefinitions extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Global Signals</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.globalSignalDefinition.GlobalSignal}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Global Signals</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Global Signals</em>' containment reference list.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignalDefinitions_GlobalSignals()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='GlobalSignal' namespace='##targetNamespace' wrap='GlobalSignals'"
     * @generated
     */
    EList<GlobalSignal> getGlobalSignals();

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignalDefinitions_Description()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Format Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Format Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Format Version</em>' attribute.
     * @see #setFormatVersion(BigInteger)
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignalDefinitions_FormatVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='FormatVersion'"
     * @generated
     */
    BigInteger getFormatVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getFormatVersion <em>Format Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Format Version</em>' attribute.
     * @see #getFormatVersion()
     * @generated
     */
    void setFormatVersion(BigInteger value);

    /**
     * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mixed</em>' attribute list.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignalDefinitions_Mixed()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='elementWildcard' name=':mixed'"
     * @generated
     */
    FeatureMap getMixed();

    /**
     * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XMLNS Prefix Map</em>' map.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignalDefinitions_XMLNSPrefixMap()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
     *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
     * @generated
     */
    EMap<String, String> getXMLNSPrefixMap();

    /**
     * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XSI Schema Location</em>' map.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignalDefinitions_XSISchemaLocation()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
     *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
     * @generated
     */
    EMap<String, String> getXSISchemaLocation();

} // GlobalSignalDefinitions
