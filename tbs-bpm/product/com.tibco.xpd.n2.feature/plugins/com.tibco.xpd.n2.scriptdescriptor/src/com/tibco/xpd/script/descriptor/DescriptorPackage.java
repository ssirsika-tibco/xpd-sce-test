/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor;

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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.script.descriptor.DescriptorFactory
 * @model kind="package"
 * @generated
 */
public interface DescriptorPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "descriptor";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/XPD/ScriptDescriptor/";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "descriptor";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DescriptorPackage eINSTANCE = com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.script.descriptor.impl.CacTypeImpl <em>Cac Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.script.descriptor.impl.CacTypeImpl
     * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getCacType()
     * @generated
     */
    int CAC_TYPE = 0;

    /**
     * The feature id for the '<em><b>Scripting Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAC_TYPE__SCRIPTING_NAME = 0;

    /**
     * The feature id for the '<em><b>Canonical Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAC_TYPE__CANONICAL_CLASS_NAME = 1;

    /**
     * The number of structural features of the '<em>Cac Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAC_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.script.descriptor.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.script.descriptor.impl.DocumentRootImpl
     * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 1;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>Scriptdescriptor</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SCRIPTDESCRIPTOR = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.script.descriptor.impl.EnumTypeImpl <em>Enum Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.script.descriptor.impl.EnumTypeImpl
     * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getEnumType()
     * @generated
     */
    int ENUM_TYPE = 2;

    /**
     * The feature id for the '<em><b>Scripting Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_TYPE__SCRIPTING_NAME = 0;

    /**
     * The feature id for the '<em><b>Canonical Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_TYPE__CANONICAL_CLASS_NAME = 1;

    /**
     * The number of structural features of the '<em>Enum Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.script.descriptor.impl.FactoryTypeImpl <em>Factory Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.script.descriptor.impl.FactoryTypeImpl
     * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getFactoryType()
     * @generated
     */
    int FACTORY_TYPE = 3;

    /**
     * The feature id for the '<em><b>Scripting Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACTORY_TYPE__SCRIPTING_NAME = 0;

    /**
     * The feature id for the '<em><b>Canonical Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACTORY_TYPE__CANONICAL_CLASS_NAME = 1;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACTORY_TYPE__NAMESPACE = 2;

    /**
     * The number of structural features of the '<em>Factory Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACTORY_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.script.descriptor.impl.ProcessTypeImpl <em>Process Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.script.descriptor.impl.ProcessTypeImpl
     * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getProcessType()
     * @generated
     */
    int PROCESS_TYPE = 4;

    /**
     * The feature id for the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_TYPE__PROCESS_ID = 0;

    /**
     * The feature id for the '<em><b>Package Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_TYPE__PACKAGE_ID = 1;

    /**
     * The number of structural features of the '<em>Process Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.script.descriptor.impl.ScriptDescriptorTypeImpl <em>Script Descriptor Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.script.descriptor.impl.ScriptDescriptorTypeImpl
     * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getScriptDescriptorType()
     * @generated
     */
    int SCRIPT_DESCRIPTOR_TYPE = 5;

    /**
     * The feature id for the '<em><b>Script</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DESCRIPTOR_TYPE__SCRIPT = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DESCRIPTOR_TYPE__VERSION = 1;

    /**
     * The number of structural features of the '<em>Script Descriptor Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DESCRIPTOR_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl <em>Script Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl
     * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getScriptType()
     * @generated
     */
    int SCRIPT_TYPE = 6;

    /**
     * The feature id for the '<em><b>Process</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_TYPE__PROCESS = 0;

    /**
     * The feature id for the '<em><b>Task Library Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_TYPE__TASK_LIBRARY_ID = 1;

    /**
     * The feature id for the '<em><b>Factory</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_TYPE__FACTORY = 2;

    /**
     * The feature id for the '<em><b>Enum</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_TYPE__ENUM = 3;

    /**
     * The feature id for the '<em><b>Cac</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_TYPE__CAC = 4;

    /**
     * The number of structural features of the '<em>Script Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_TYPE_FEATURE_COUNT = 5;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.script.descriptor.CacType <em>Cac Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cac Type</em>'.
     * @see com.tibco.xpd.script.descriptor.CacType
     * @generated
     */
    EClass getCacType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.CacType#getScriptingName <em>Scripting Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Scripting Name</em>'.
     * @see com.tibco.xpd.script.descriptor.CacType#getScriptingName()
     * @see #getCacType()
     * @generated
     */
    EAttribute getCacType_ScriptingName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.CacType#getCanonicalClassName <em>Canonical Class Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Canonical Class Name</em>'.
     * @see com.tibco.xpd.script.descriptor.CacType#getCanonicalClassName()
     * @see #getCacType()
     * @generated
     */
    EAttribute getCacType_CanonicalClassName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.script.descriptor.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.script.descriptor.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.script.descriptor.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.script.descriptor.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.script.descriptor.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.script.descriptor.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.script.descriptor.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.script.descriptor.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.script.descriptor.DocumentRoot#getScriptdescriptor <em>Scriptdescriptor</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Scriptdescriptor</em>'.
     * @see com.tibco.xpd.script.descriptor.DocumentRoot#getScriptdescriptor()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Scriptdescriptor();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.script.descriptor.EnumType <em>Enum Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enum Type</em>'.
     * @see com.tibco.xpd.script.descriptor.EnumType
     * @generated
     */
    EClass getEnumType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.EnumType#getScriptingName <em>Scripting Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Scripting Name</em>'.
     * @see com.tibco.xpd.script.descriptor.EnumType#getScriptingName()
     * @see #getEnumType()
     * @generated
     */
    EAttribute getEnumType_ScriptingName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.EnumType#getCanonicalClassName <em>Canonical Class Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Canonical Class Name</em>'.
     * @see com.tibco.xpd.script.descriptor.EnumType#getCanonicalClassName()
     * @see #getEnumType()
     * @generated
     */
    EAttribute getEnumType_CanonicalClassName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.script.descriptor.FactoryType <em>Factory Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Factory Type</em>'.
     * @see com.tibco.xpd.script.descriptor.FactoryType
     * @generated
     */
    EClass getFactoryType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.FactoryType#getScriptingName <em>Scripting Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Scripting Name</em>'.
     * @see com.tibco.xpd.script.descriptor.FactoryType#getScriptingName()
     * @see #getFactoryType()
     * @generated
     */
    EAttribute getFactoryType_ScriptingName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.FactoryType#getCanonicalClassName <em>Canonical Class Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Canonical Class Name</em>'.
     * @see com.tibco.xpd.script.descriptor.FactoryType#getCanonicalClassName()
     * @see #getFactoryType()
     * @generated
     */
    EAttribute getFactoryType_CanonicalClassName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.FactoryType#getNamespace <em>Namespace</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Namespace</em>'.
     * @see com.tibco.xpd.script.descriptor.FactoryType#getNamespace()
     * @see #getFactoryType()
     * @generated
     */
    EAttribute getFactoryType_Namespace();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.script.descriptor.ProcessType <em>Process Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process Type</em>'.
     * @see com.tibco.xpd.script.descriptor.ProcessType
     * @generated
     */
    EClass getProcessType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.ProcessType#getProcessId <em>Process Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Id</em>'.
     * @see com.tibco.xpd.script.descriptor.ProcessType#getProcessId()
     * @see #getProcessType()
     * @generated
     */
    EAttribute getProcessType_ProcessId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.ProcessType#getPackageId <em>Package Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Id</em>'.
     * @see com.tibco.xpd.script.descriptor.ProcessType#getPackageId()
     * @see #getProcessType()
     * @generated
     */
    EAttribute getProcessType_PackageId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.script.descriptor.ScriptDescriptorType <em>Script Descriptor Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Script Descriptor Type</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptDescriptorType
     * @generated
     */
    EClass getScriptDescriptorType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.script.descriptor.ScriptDescriptorType#getScript <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Script</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptDescriptorType#getScript()
     * @see #getScriptDescriptorType()
     * @generated
     */
    EReference getScriptDescriptorType_Script();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.ScriptDescriptorType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptDescriptorType#getVersion()
     * @see #getScriptDescriptorType()
     * @generated
     */
    EAttribute getScriptDescriptorType_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.script.descriptor.ScriptType <em>Script Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Script Type</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptType
     * @generated
     */
    EClass getScriptType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.script.descriptor.ScriptType#getProcess <em>Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Process</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptType#getProcess()
     * @see #getScriptType()
     * @generated
     */
    EReference getScriptType_Process();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.script.descriptor.ScriptType#getTaskLibraryId <em>Task Library Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Task Library Id</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptType#getTaskLibraryId()
     * @see #getScriptType()
     * @generated
     */
    EAttribute getScriptType_TaskLibraryId();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.script.descriptor.ScriptType#getFactory <em>Factory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Factory</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptType#getFactory()
     * @see #getScriptType()
     * @generated
     */
    EReference getScriptType_Factory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.script.descriptor.ScriptType#getEnum <em>Enum</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Enum</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptType#getEnum()
     * @see #getScriptType()
     * @generated
     */
    EReference getScriptType_Enum();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.script.descriptor.ScriptType#getCac <em>Cac</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Cac</em>'.
     * @see com.tibco.xpd.script.descriptor.ScriptType#getCac()
     * @see #getScriptType()
     * @generated
     */
    EReference getScriptType_Cac();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DescriptorFactory getDescriptorFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.script.descriptor.impl.CacTypeImpl <em>Cac Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.script.descriptor.impl.CacTypeImpl
         * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getCacType()
         * @generated
         */
        EClass CAC_TYPE = eINSTANCE.getCacType();

        /**
         * The meta object literal for the '<em><b>Scripting Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CAC_TYPE__SCRIPTING_NAME = eINSTANCE.getCacType_ScriptingName();

        /**
         * The meta object literal for the '<em><b>Canonical Class Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CAC_TYPE__CANONICAL_CLASS_NAME = eINSTANCE.getCacType_CanonicalClassName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.script.descriptor.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.script.descriptor.impl.DocumentRootImpl
         * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Scriptdescriptor</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SCRIPTDESCRIPTOR = eINSTANCE.getDocumentRoot_Scriptdescriptor();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.script.descriptor.impl.EnumTypeImpl <em>Enum Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.script.descriptor.impl.EnumTypeImpl
         * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getEnumType()
         * @generated
         */
        EClass ENUM_TYPE = eINSTANCE.getEnumType();

        /**
         * The meta object literal for the '<em><b>Scripting Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUM_TYPE__SCRIPTING_NAME = eINSTANCE.getEnumType_ScriptingName();

        /**
         * The meta object literal for the '<em><b>Canonical Class Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUM_TYPE__CANONICAL_CLASS_NAME = eINSTANCE.getEnumType_CanonicalClassName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.script.descriptor.impl.FactoryTypeImpl <em>Factory Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.script.descriptor.impl.FactoryTypeImpl
         * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getFactoryType()
         * @generated
         */
        EClass FACTORY_TYPE = eINSTANCE.getFactoryType();

        /**
         * The meta object literal for the '<em><b>Scripting Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FACTORY_TYPE__SCRIPTING_NAME = eINSTANCE.getFactoryType_ScriptingName();

        /**
         * The meta object literal for the '<em><b>Canonical Class Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FACTORY_TYPE__CANONICAL_CLASS_NAME = eINSTANCE.getFactoryType_CanonicalClassName();

        /**
         * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FACTORY_TYPE__NAMESPACE = eINSTANCE.getFactoryType_Namespace();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.script.descriptor.impl.ProcessTypeImpl <em>Process Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.script.descriptor.impl.ProcessTypeImpl
         * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getProcessType()
         * @generated
         */
        EClass PROCESS_TYPE = eINSTANCE.getProcessType();

        /**
         * The meta object literal for the '<em><b>Process Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS_TYPE__PROCESS_ID = eINSTANCE.getProcessType_ProcessId();

        /**
         * The meta object literal for the '<em><b>Package Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS_TYPE__PACKAGE_ID = eINSTANCE.getProcessType_PackageId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.script.descriptor.impl.ScriptDescriptorTypeImpl <em>Script Descriptor Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.script.descriptor.impl.ScriptDescriptorTypeImpl
         * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getScriptDescriptorType()
         * @generated
         */
        EClass SCRIPT_DESCRIPTOR_TYPE = eINSTANCE.getScriptDescriptorType();

        /**
         * The meta object literal for the '<em><b>Script</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_DESCRIPTOR_TYPE__SCRIPT = eINSTANCE.getScriptDescriptorType_Script();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT_DESCRIPTOR_TYPE__VERSION = eINSTANCE.getScriptDescriptorType_Version();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl <em>Script Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.script.descriptor.impl.ScriptTypeImpl
         * @see com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl#getScriptType()
         * @generated
         */
        EClass SCRIPT_TYPE = eINSTANCE.getScriptType();

        /**
         * The meta object literal for the '<em><b>Process</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_TYPE__PROCESS = eINSTANCE.getScriptType_Process();

        /**
         * The meta object literal for the '<em><b>Task Library Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT_TYPE__TASK_LIBRARY_ID = eINSTANCE.getScriptType_TaskLibraryId();

        /**
         * The meta object literal for the '<em><b>Factory</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_TYPE__FACTORY = eINSTANCE.getScriptType_Factory();

        /**
         * The meta object literal for the '<em><b>Enum</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_TYPE__ENUM = eINSTANCE.getScriptType_Enum();

        /**
         * The meta object literal for the '<em><b>Cac</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_TYPE__CAC = eINSTANCE.getScriptType_Cac();

    }

} //DescriptorPackage
