/**
 */
package com.tibco.xpd.globalSignalDefinition;

import com.tibco.xpd.xpdl2.Xpdl2Package;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory
 * @model kind="package"
 *        extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface GlobalSignalDefinitionPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "globalSignalDefinition";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/XPD/GlobalSignalDefinition1.0.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "gsd";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    GlobalSignalDefinitionPackage eINSTANCE = com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl <em>Global Signal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl
     * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionPackageImpl#getGlobalSignal()
     * @generated
     */
    int GLOBAL_SIGNAL = 0;

    /**
     * The feature id for the '<em><b>Payload Data Fields</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS = 0;

    /**
     * The feature id for the '<em><b>Correlation Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL__CORRELATION_TIMEOUT = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL__DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL__NAME = 3;

    /**
     * The number of structural features of the '<em>Global Signal</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl <em>Global Signal Definitions</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl
     * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionPackageImpl#getGlobalSignalDefinitions()
     * @generated
     */
    int GLOBAL_SIGNAL_DEFINITIONS = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Global Signals</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS = 1;

    /**
     * The feature id for the '<em><b>Format Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION = 2;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_DEFINITIONS__MIXED = 3;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP = 4;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION = 5;

    /**
     * The number of structural features of the '<em>Global Signal Definitions</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_SIGNAL_DEFINITIONS_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.xpd.globalSignalDefinition.impl.PayloadDataFieldImpl <em>Payload Data Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.globalSignalDefinition.impl.PayloadDataFieldImpl
     * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionPackageImpl#getPayloadDataField()
     * @generated
     */
    int PAYLOAD_DATA_FIELD = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__ID = Xpdl2Package.DATA_FIELD__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__OTHER_ATTRIBUTES = Xpdl2Package.DATA_FIELD__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__NAME = Xpdl2Package.DATA_FIELD__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__DESCRIPTION = Xpdl2Package.DATA_FIELD__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__OTHER_ELEMENTS = Xpdl2Package.DATA_FIELD__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Data Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__DATA_TYPE = Xpdl2Package.DATA_FIELD__DATA_TYPE;

    /**
     * The feature id for the '<em><b>Length</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__LENGTH = Xpdl2Package.DATA_FIELD__LENGTH;

    /**
     * The feature id for the '<em><b>Is Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__IS_ARRAY = Xpdl2Package.DATA_FIELD__IS_ARRAY;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__READ_ONLY = Xpdl2Package.DATA_FIELD__READ_ONLY;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__EXTENDED_ATTRIBUTES = Xpdl2Package.DATA_FIELD__EXTENDED_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Initial Value</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__INITIAL_VALUE = Xpdl2Package.DATA_FIELD__INITIAL_VALUE;

    /**
     * The feature id for the '<em><b>Correlation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__CORRELATION = Xpdl2Package.DATA_FIELD__CORRELATION;

    /**
     * The feature id for the '<em><b>Deprecated Data Is Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__DEPRECATED_DATA_IS_ARRAY = Xpdl2Package.DATA_FIELD__DEPRECATED_DATA_IS_ARRAY;

    /**
     * The feature id for the '<em><b>Mandatory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD__MANDATORY = Xpdl2Package.DATA_FIELD_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Payload Data Field</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAYLOAD_DATA_FIELD_FEATURE_COUNT = Xpdl2Package.DATA_FIELD_FEATURE_COUNT + 1;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal <em>Global Signal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Global Signal</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignal
     * @generated
     */
    EClass getGlobalSignal();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getPayloadDataFields <em>Payload Data Fields</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Payload Data Fields</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignal#getPayloadDataFields()
     * @see #getGlobalSignal()
     * @generated
     */
    EReference getGlobalSignal_PayloadDataFields();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getCorrelationTimeout <em>Correlation Timeout</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Correlation Timeout</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignal#getCorrelationTimeout()
     * @see #getGlobalSignal()
     * @generated
     */
    EAttribute getGlobalSignal_CorrelationTimeout();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignal#getDescription()
     * @see #getGlobalSignal()
     * @generated
     */
    EAttribute getGlobalSignal_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignal#getName()
     * @see #getGlobalSignal()
     * @generated
     */
    EAttribute getGlobalSignal_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions <em>Global Signal Definitions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Global Signal Definitions</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions
     * @generated
     */
    EClass getGlobalSignalDefinitions();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getGlobalSignals <em>Global Signals</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Global Signals</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getGlobalSignals()
     * @see #getGlobalSignalDefinitions()
     * @generated
     */
    EReference getGlobalSignalDefinitions_GlobalSignals();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getDescription()
     * @see #getGlobalSignalDefinitions()
     * @generated
     */
    EAttribute getGlobalSignalDefinitions_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getFormatVersion <em>Format Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Format Version</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getFormatVersion()
     * @see #getGlobalSignalDefinitions()
     * @generated
     */
    EAttribute getGlobalSignalDefinitions_FormatVersion();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getMixed()
     * @see #getGlobalSignalDefinitions()
     * @generated
     */
    EAttribute getGlobalSignalDefinitions_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getXMLNSPrefixMap()
     * @see #getGlobalSignalDefinitions()
     * @generated
     */
    EReference getGlobalSignalDefinitions_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions#getXSISchemaLocation()
     * @see #getGlobalSignalDefinitions()
     * @generated
     */
    EReference getGlobalSignalDefinitions_XSISchemaLocation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.globalSignalDefinition.PayloadDataField <em>Payload Data Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Payload Data Field</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.PayloadDataField
     * @generated
     */
    EClass getPayloadDataField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.globalSignalDefinition.PayloadDataField#isMandatory <em>Mandatory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mandatory</em>'.
     * @see com.tibco.xpd.globalSignalDefinition.PayloadDataField#isMandatory()
     * @see #getPayloadDataField()
     * @generated
     */
    EAttribute getPayloadDataField_Mandatory();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    GlobalSignalDefinitionFactory getGlobalSignalDefinitionFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl <em>Global Signal</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalImpl
         * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionPackageImpl#getGlobalSignal()
         * @generated
         */
        EClass GLOBAL_SIGNAL = eINSTANCE.getGlobalSignal();

        /**
         * The meta object literal for the '<em><b>Payload Data Fields</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS = eINSTANCE.getGlobalSignal_PayloadDataFields();

        /**
         * The meta object literal for the '<em><b>Correlation Timeout</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GLOBAL_SIGNAL__CORRELATION_TIMEOUT = eINSTANCE.getGlobalSignal_CorrelationTimeout();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GLOBAL_SIGNAL__DESCRIPTION = eINSTANCE.getGlobalSignal_Description();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GLOBAL_SIGNAL__NAME = eINSTANCE.getGlobalSignal_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl <em>Global Signal Definitions</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl
         * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionPackageImpl#getGlobalSignalDefinitions()
         * @generated
         */
        EClass GLOBAL_SIGNAL_DEFINITIONS = eINSTANCE.getGlobalSignalDefinitions();

        /**
         * The meta object literal for the '<em><b>Global Signals</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS = eINSTANCE.getGlobalSignalDefinitions_GlobalSignals();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION = eINSTANCE.getGlobalSignalDefinitions_Description();

        /**
         * The meta object literal for the '<em><b>Format Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION = eINSTANCE.getGlobalSignalDefinitions_FormatVersion();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GLOBAL_SIGNAL_DEFINITIONS__MIXED = eINSTANCE.getGlobalSignalDefinitions_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP = eINSTANCE.getGlobalSignalDefinitions_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION = eINSTANCE.getGlobalSignalDefinitions_XSISchemaLocation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.globalSignalDefinition.impl.PayloadDataFieldImpl <em>Payload Data Field</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.globalSignalDefinition.impl.PayloadDataFieldImpl
         * @see com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionPackageImpl#getPayloadDataField()
         * @generated
         */
        EClass PAYLOAD_DATA_FIELD = eINSTANCE.getPayloadDataField();

        /**
         * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAYLOAD_DATA_FIELD__MANDATORY = eINSTANCE.getPayloadDataField_Mandatory();

    }

} //GlobalSignalDefinitionPackage
