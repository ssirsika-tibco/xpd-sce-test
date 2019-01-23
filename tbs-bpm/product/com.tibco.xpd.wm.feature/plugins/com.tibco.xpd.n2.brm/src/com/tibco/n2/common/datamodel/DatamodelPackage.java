/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.tibco.n2.common.datamodel.DatamodelFactory
 * @model kind="package"
 * @generated
 */
public interface DatamodelPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "datamodel";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://datamodel.common.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "datamodel";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DatamodelPackage eINSTANCE = com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.impl.AliasTypeImpl <em>Alias Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.impl.AliasTypeImpl
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasType()
     * @generated
     */
    int ALIAS_TYPE = 0;

    /**
     * The feature id for the '<em><b>Alias Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALIAS_TYPE__ALIAS_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Alias Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALIAS_TYPE__ALIAS_NAME = 1;

    /**
     * The feature id for the '<em><b>Alias Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALIAS_TYPE__ALIAS_TYPE = 2;

    /**
     * The feature id for the '<em><b>Facade Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALIAS_TYPE__FACADE_NAME = 3;

    /**
     * The number of structural features of the '<em>Alias Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALIAS_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.impl.ComplexSpecTypeImpl <em>Complex Spec Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.impl.ComplexSpecTypeImpl
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getComplexSpecType()
     * @generated
     */
    int COMPLEX_SPEC_TYPE = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_SPEC_TYPE__VALUE = 0;

    /**
     * The feature id for the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_SPEC_TYPE__CLASS_NAME = 1;

    /**
     * The feature id for the '<em><b>Go Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_SPEC_TYPE__GO_REF_ID = 2;

    /**
     * The number of structural features of the '<em>Complex Spec Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_SPEC_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.impl.DataModelImpl <em>Data Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.impl.DataModelImpl
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getDataModel()
     * @generated
     */
    int DATA_MODEL = 2;

    /**
     * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MODEL__INPUTS = 0;

    /**
     * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MODEL__OUTPUTS = 1;

    /**
     * The feature id for the '<em><b>Inouts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MODEL__INOUTS = 2;

    /**
     * The number of structural features of the '<em>Data Model</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MODEL_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl <em>Field Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.impl.FieldTypeImpl
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getFieldType()
     * @generated
     */
    int FIELD_TYPE = 3;

    /**
     * The feature id for the '<em><b>Simple Spec</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIELD_TYPE__SIMPLE_SPEC = 0;

    /**
     * The feature id for the '<em><b>Complex Spec</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIELD_TYPE__COMPLEX_SPEC = 1;

    /**
     * The feature id for the '<em><b>Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIELD_TYPE__ARRAY = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIELD_TYPE__NAME = 3;

    /**
     * The feature id for the '<em><b>Optional</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIELD_TYPE__OPTIONAL = 4;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIELD_TYPE__TYPE = 5;

    /**
     * The number of structural features of the '<em>Field Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIELD_TYPE_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.impl.SimpleSpecTypeImpl <em>Simple Spec Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.impl.SimpleSpecTypeImpl
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getSimpleSpecType()
     * @generated
     */
    int SIMPLE_SPEC_TYPE = 4;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMPLE_SPEC_TYPE__VALUE = 0;

    /**
     * The feature id for the '<em><b>Decimal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMPLE_SPEC_TYPE__DECIMAL = 1;

    /**
     * The feature id for the '<em><b>Length</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMPLE_SPEC_TYPE__LENGTH = 2;

    /**
     * The number of structural features of the '<em>Simple Spec Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMPLE_SPEC_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl <em>Work Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.impl.WorkTypeImpl
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getWorkType()
     * @generated
     */
    int WORK_TYPE = 5;

    /**
     * The feature id for the '<em><b>Work Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__WORK_TYPE_ID = 0;

    /**
     * The feature id for the '<em><b>Work Type UID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__WORK_TYPE_UID = 1;

    /**
     * The feature id for the '<em><b>Work Type Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__WORK_TYPE_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Data Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__DATA_MODEL = 3;

    /**
     * The feature id for the '<em><b>Type Piled</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__TYPE_PILED = 4;

    /**
     * The feature id for the '<em><b>Piling Limit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__PILING_LIMIT = 5;

    /**
     * The feature id for the '<em><b>Ignore Incoming Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__IGNORE_INCOMING_DATA = 6;

    /**
     * The feature id for the '<em><b>Reoffer On Close</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__REOFFER_ON_CLOSE = 7;

    /**
     * The feature id for the '<em><b>Reoffer On Cancel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE__REOFFER_ON_CANCEL = 8;

    /**
     * The number of structural features of the '<em>Work Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_FEATURE_COUNT = 9;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.impl.WorkTypeSpecImpl <em>Work Type Spec</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.impl.WorkTypeSpecImpl
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getWorkTypeSpec()
     * @generated
     */
    int WORK_TYPE_SPEC = 6;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_SPEC__VERSION = 0;

    /**
     * The feature id for the '<em><b>Work Type Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Work Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_SPEC__WORK_TYPE_ID = 2;

    /**
     * The number of structural features of the '<em>Work Type Spec</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_SPEC_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.AliasTypeType <em>Alias Type Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.AliasTypeType
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasTypeType()
     * @generated
     */
    int ALIAS_TYPE_TYPE = 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.datamodel.TypeType <em>Type Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.TypeType
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getTypeType()
     * @generated
     */
    int TYPE_TYPE = 8;

    /**
     * The meta object id for the '<em>Alias Description Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasDescriptionType()
     * @generated
     */
    int ALIAS_DESCRIPTION_TYPE = 9;

    /**
     * The meta object id for the '<em>Alias Name Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasNameType()
     * @generated
     */
    int ALIAS_NAME_TYPE = 10;

    /**
     * The meta object id for the '<em>Alias Type Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.AliasTypeType
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasTypeTypeObject()
     * @generated
     */
    int ALIAS_TYPE_TYPE_OBJECT = 11;

    /**
     * The meta object id for the '<em>Facade Name Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getFacadeNameType()
     * @generated
     */
    int FACADE_NAME_TYPE = 12;

    /**
     * The meta object id for the '<em>Type Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.datamodel.TypeType
     * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getTypeTypeObject()
     * @generated
     */
    int TYPE_TYPE_OBJECT = 13;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.datamodel.AliasType <em>Alias Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Alias Type</em>'.
     * @see com.tibco.n2.common.datamodel.AliasType
     * @generated
     */
    EClass getAliasType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.AliasType#getAliasDescription <em>Alias Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alias Description</em>'.
     * @see com.tibco.n2.common.datamodel.AliasType#getAliasDescription()
     * @see #getAliasType()
     * @generated
     */
    EAttribute getAliasType_AliasDescription();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.AliasType#getAliasName <em>Alias Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alias Name</em>'.
     * @see com.tibco.n2.common.datamodel.AliasType#getAliasName()
     * @see #getAliasType()
     * @generated
     */
    EAttribute getAliasType_AliasName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.AliasType#getAliasType <em>Alias Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alias Type</em>'.
     * @see com.tibco.n2.common.datamodel.AliasType#getAliasType()
     * @see #getAliasType()
     * @generated
     */
    EAttribute getAliasType_AliasType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.AliasType#getFacadeName <em>Facade Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Facade Name</em>'.
     * @see com.tibco.n2.common.datamodel.AliasType#getFacadeName()
     * @see #getAliasType()
     * @generated
     */
    EAttribute getAliasType_FacadeName();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.datamodel.ComplexSpecType <em>Complex Spec Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Complex Spec Type</em>'.
     * @see com.tibco.n2.common.datamodel.ComplexSpecType
     * @generated
     */
    EClass getComplexSpecType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.datamodel.ComplexSpecType#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Value</em>'.
     * @see com.tibco.n2.common.datamodel.ComplexSpecType#getValue()
     * @see #getComplexSpecType()
     * @generated
     */
    EReference getComplexSpecType_Value();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.ComplexSpecType#getClassName <em>Class Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Class Name</em>'.
     * @see com.tibco.n2.common.datamodel.ComplexSpecType#getClassName()
     * @see #getComplexSpecType()
     * @generated
     */
    EAttribute getComplexSpecType_ClassName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.ComplexSpecType#getGoRefId <em>Go Ref Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Go Ref Id</em>'.
     * @see com.tibco.n2.common.datamodel.ComplexSpecType#getGoRefId()
     * @see #getComplexSpecType()
     * @generated
     */
    EAttribute getComplexSpecType_GoRefId();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.datamodel.DataModel <em>Data Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Model</em>'.
     * @see com.tibco.n2.common.datamodel.DataModel
     * @generated
     */
    EClass getDataModel();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.datamodel.DataModel#getInputs <em>Inputs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Inputs</em>'.
     * @see com.tibco.n2.common.datamodel.DataModel#getInputs()
     * @see #getDataModel()
     * @generated
     */
    EReference getDataModel_Inputs();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.datamodel.DataModel#getOutputs <em>Outputs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Outputs</em>'.
     * @see com.tibco.n2.common.datamodel.DataModel#getOutputs()
     * @see #getDataModel()
     * @generated
     */
    EReference getDataModel_Outputs();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.datamodel.DataModel#getInouts <em>Inouts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Inouts</em>'.
     * @see com.tibco.n2.common.datamodel.DataModel#getInouts()
     * @see #getDataModel()
     * @generated
     */
    EReference getDataModel_Inouts();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.datamodel.FieldType <em>Field Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Field Type</em>'.
     * @see com.tibco.n2.common.datamodel.FieldType
     * @generated
     */
    EClass getFieldType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.datamodel.FieldType#getSimpleSpec <em>Simple Spec</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Simple Spec</em>'.
     * @see com.tibco.n2.common.datamodel.FieldType#getSimpleSpec()
     * @see #getFieldType()
     * @generated
     */
    EReference getFieldType_SimpleSpec();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.datamodel.FieldType#getComplexSpec <em>Complex Spec</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Complex Spec</em>'.
     * @see com.tibco.n2.common.datamodel.FieldType#getComplexSpec()
     * @see #getFieldType()
     * @generated
     */
    EReference getFieldType_ComplexSpec();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.FieldType#isArray <em>Array</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Array</em>'.
     * @see com.tibco.n2.common.datamodel.FieldType#isArray()
     * @see #getFieldType()
     * @generated
     */
    EAttribute getFieldType_Array();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.FieldType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.common.datamodel.FieldType#getName()
     * @see #getFieldType()
     * @generated
     */
    EAttribute getFieldType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.FieldType#isOptional <em>Optional</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Optional</em>'.
     * @see com.tibco.n2.common.datamodel.FieldType#isOptional()
     * @see #getFieldType()
     * @generated
     */
    EAttribute getFieldType_Optional();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.FieldType#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.n2.common.datamodel.FieldType#getType()
     * @see #getFieldType()
     * @generated
     */
    EAttribute getFieldType_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.datamodel.SimpleSpecType <em>Simple Spec Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Simple Spec Type</em>'.
     * @see com.tibco.n2.common.datamodel.SimpleSpecType
     * @generated
     */
    EClass getSimpleSpecType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Value</em>'.
     * @see com.tibco.n2.common.datamodel.SimpleSpecType#getValue()
     * @see #getSimpleSpecType()
     * @generated
     */
    EAttribute getSimpleSpecType_Value();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getDecimal <em>Decimal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Decimal</em>'.
     * @see com.tibco.n2.common.datamodel.SimpleSpecType#getDecimal()
     * @see #getSimpleSpecType()
     * @generated
     */
    EAttribute getSimpleSpecType_Decimal();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getLength <em>Length</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Length</em>'.
     * @see com.tibco.n2.common.datamodel.SimpleSpecType#getLength()
     * @see #getSimpleSpecType()
     * @generated
     */
    EAttribute getSimpleSpecType_Length();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.datamodel.WorkType <em>Work Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Type</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType
     * @generated
     */
    EClass getWorkType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#getWorkTypeID <em>Work Type ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type ID</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#getWorkTypeID()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_WorkTypeID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#getWorkTypeUID <em>Work Type UID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type UID</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#getWorkTypeUID()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_WorkTypeUID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#getWorkTypeDescription <em>Work Type Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type Description</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#getWorkTypeDescription()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_WorkTypeDescription();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.datamodel.WorkType#getDataModel <em>Data Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Data Model</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#getDataModel()
     * @see #getWorkType()
     * @generated
     */
    EReference getWorkType_DataModel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#isTypePiled <em>Type Piled</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type Piled</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#isTypePiled()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_TypePiled();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#getPilingLimit <em>Piling Limit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Piling Limit</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#getPilingLimit()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_PilingLimit();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#isIgnoreIncomingData <em>Ignore Incoming Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ignore Incoming Data</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#isIgnoreIncomingData()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_IgnoreIncomingData();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#isReofferOnClose <em>Reoffer On Close</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reoffer On Close</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#isReofferOnClose()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_ReofferOnClose();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkType#isReofferOnCancel <em>Reoffer On Cancel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reoffer On Cancel</em>'.
     * @see com.tibco.n2.common.datamodel.WorkType#isReofferOnCancel()
     * @see #getWorkType()
     * @generated
     */
    EAttribute getWorkType_ReofferOnCancel();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.datamodel.WorkTypeSpec <em>Work Type Spec</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Type Spec</em>'.
     * @see com.tibco.n2.common.datamodel.WorkTypeSpec
     * @generated
     */
    EClass getWorkTypeSpec();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.common.datamodel.WorkTypeSpec#getVersion()
     * @see #getWorkTypeSpec()
     * @generated
     */
    EAttribute getWorkTypeSpec_Version();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeDescription <em>Work Type Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type Description</em>'.
     * @see com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeDescription()
     * @see #getWorkTypeSpec()
     * @generated
     */
    EAttribute getWorkTypeSpec_WorkTypeDescription();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeID <em>Work Type ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type ID</em>'.
     * @see com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeID()
     * @see #getWorkTypeSpec()
     * @generated
     */
    EAttribute getWorkTypeSpec_WorkTypeID();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.common.datamodel.AliasTypeType <em>Alias Type Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Alias Type Type</em>'.
     * @see com.tibco.n2.common.datamodel.AliasTypeType
     * @generated
     */
    EEnum getAliasTypeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.common.datamodel.TypeType <em>Type Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Type Type</em>'.
     * @see com.tibco.n2.common.datamodel.TypeType
     * @generated
     */
    EEnum getTypeType();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Alias Description Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Alias Description Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='aliasDescription_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='256'"
     * @generated
     */
    EDataType getAliasDescriptionType();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Alias Name Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Alias Name Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='aliasName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAliasNameType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.common.datamodel.AliasTypeType <em>Alias Type Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Alias Type Type Object</em>'.
     * @see com.tibco.n2.common.datamodel.AliasTypeType
     * @model instanceClass="com.tibco.n2.common.datamodel.AliasTypeType"
     *        extendedMetaData="name='aliasType_._type:Object' baseType='aliasType_._type'"
     * @generated
     */
    EDataType getAliasTypeTypeObject();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Facade Name Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Facade Name Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='facadeName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getFacadeNameType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.common.datamodel.TypeType <em>Type Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Type Type Object</em>'.
     * @see com.tibco.n2.common.datamodel.TypeType
     * @model instanceClass="com.tibco.n2.common.datamodel.TypeType"
     *        extendedMetaData="name='type_._type:Object' baseType='type_._type'"
     * @generated
     */
    EDataType getTypeTypeObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DatamodelFactory getDatamodelFactory();

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
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.impl.AliasTypeImpl <em>Alias Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.impl.AliasTypeImpl
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasType()
         * @generated
         */
        EClass ALIAS_TYPE = eINSTANCE.getAliasType();

        /**
         * The meta object literal for the '<em><b>Alias Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALIAS_TYPE__ALIAS_DESCRIPTION = eINSTANCE.getAliasType_AliasDescription();

        /**
         * The meta object literal for the '<em><b>Alias Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALIAS_TYPE__ALIAS_NAME = eINSTANCE.getAliasType_AliasName();

        /**
         * The meta object literal for the '<em><b>Alias Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALIAS_TYPE__ALIAS_TYPE = eINSTANCE.getAliasType_AliasType();

        /**
         * The meta object literal for the '<em><b>Facade Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALIAS_TYPE__FACADE_NAME = eINSTANCE.getAliasType_FacadeName();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.impl.ComplexSpecTypeImpl <em>Complex Spec Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.impl.ComplexSpecTypeImpl
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getComplexSpecType()
         * @generated
         */
        EClass COMPLEX_SPEC_TYPE = eINSTANCE.getComplexSpecType();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COMPLEX_SPEC_TYPE__VALUE = eINSTANCE.getComplexSpecType_Value();

        /**
         * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPLEX_SPEC_TYPE__CLASS_NAME = eINSTANCE.getComplexSpecType_ClassName();

        /**
         * The meta object literal for the '<em><b>Go Ref Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPLEX_SPEC_TYPE__GO_REF_ID = eINSTANCE.getComplexSpecType_GoRefId();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.impl.DataModelImpl <em>Data Model</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.impl.DataModelImpl
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getDataModel()
         * @generated
         */
        EClass DATA_MODEL = eINSTANCE.getDataModel();

        /**
         * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATA_MODEL__INPUTS = eINSTANCE.getDataModel_Inputs();

        /**
         * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATA_MODEL__OUTPUTS = eINSTANCE.getDataModel_Outputs();

        /**
         * The meta object literal for the '<em><b>Inouts</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATA_MODEL__INOUTS = eINSTANCE.getDataModel_Inouts();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl <em>Field Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.impl.FieldTypeImpl
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getFieldType()
         * @generated
         */
        EClass FIELD_TYPE = eINSTANCE.getFieldType();

        /**
         * The meta object literal for the '<em><b>Simple Spec</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FIELD_TYPE__SIMPLE_SPEC = eINSTANCE.getFieldType_SimpleSpec();

        /**
         * The meta object literal for the '<em><b>Complex Spec</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FIELD_TYPE__COMPLEX_SPEC = eINSTANCE.getFieldType_ComplexSpec();

        /**
         * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FIELD_TYPE__ARRAY = eINSTANCE.getFieldType_Array();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FIELD_TYPE__NAME = eINSTANCE.getFieldType_Name();

        /**
         * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FIELD_TYPE__OPTIONAL = eINSTANCE.getFieldType_Optional();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FIELD_TYPE__TYPE = eINSTANCE.getFieldType_Type();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.impl.SimpleSpecTypeImpl <em>Simple Spec Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.impl.SimpleSpecTypeImpl
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getSimpleSpecType()
         * @generated
         */
        EClass SIMPLE_SPEC_TYPE = eINSTANCE.getSimpleSpecType();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIMPLE_SPEC_TYPE__VALUE = eINSTANCE.getSimpleSpecType_Value();

        /**
         * The meta object literal for the '<em><b>Decimal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIMPLE_SPEC_TYPE__DECIMAL = eINSTANCE.getSimpleSpecType_Decimal();

        /**
         * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIMPLE_SPEC_TYPE__LENGTH = eINSTANCE.getSimpleSpecType_Length();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl <em>Work Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.impl.WorkTypeImpl
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getWorkType()
         * @generated
         */
        EClass WORK_TYPE = eINSTANCE.getWorkType();

        /**
         * The meta object literal for the '<em><b>Work Type ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__WORK_TYPE_ID = eINSTANCE.getWorkType_WorkTypeID();

        /**
         * The meta object literal for the '<em><b>Work Type UID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__WORK_TYPE_UID = eINSTANCE.getWorkType_WorkTypeUID();

        /**
         * The meta object literal for the '<em><b>Work Type Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__WORK_TYPE_DESCRIPTION = eINSTANCE.getWorkType_WorkTypeDescription();

        /**
         * The meta object literal for the '<em><b>Data Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE__DATA_MODEL = eINSTANCE.getWorkType_DataModel();

        /**
         * The meta object literal for the '<em><b>Type Piled</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__TYPE_PILED = eINSTANCE.getWorkType_TypePiled();

        /**
         * The meta object literal for the '<em><b>Piling Limit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__PILING_LIMIT = eINSTANCE.getWorkType_PilingLimit();

        /**
         * The meta object literal for the '<em><b>Ignore Incoming Data</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__IGNORE_INCOMING_DATA = eINSTANCE.getWorkType_IgnoreIncomingData();

        /**
         * The meta object literal for the '<em><b>Reoffer On Close</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__REOFFER_ON_CLOSE = eINSTANCE.getWorkType_ReofferOnClose();

        /**
         * The meta object literal for the '<em><b>Reoffer On Cancel</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE__REOFFER_ON_CANCEL = eINSTANCE.getWorkType_ReofferOnCancel();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.impl.WorkTypeSpecImpl <em>Work Type Spec</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.impl.WorkTypeSpecImpl
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getWorkTypeSpec()
         * @generated
         */
        EClass WORK_TYPE_SPEC = eINSTANCE.getWorkTypeSpec();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_SPEC__VERSION = eINSTANCE.getWorkTypeSpec_Version();

        /**
         * The meta object literal for the '<em><b>Work Type Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION = eINSTANCE.getWorkTypeSpec_WorkTypeDescription();

        /**
         * The meta object literal for the '<em><b>Work Type ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_SPEC__WORK_TYPE_ID = eINSTANCE.getWorkTypeSpec_WorkTypeID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.AliasTypeType <em>Alias Type Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.AliasTypeType
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasTypeType()
         * @generated
         */
        EEnum ALIAS_TYPE_TYPE = eINSTANCE.getAliasTypeType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.datamodel.TypeType <em>Type Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.TypeType
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getTypeType()
         * @generated
         */
        EEnum TYPE_TYPE = eINSTANCE.getTypeType();

        /**
         * The meta object literal for the '<em>Alias Description Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasDescriptionType()
         * @generated
         */
        EDataType ALIAS_DESCRIPTION_TYPE = eINSTANCE.getAliasDescriptionType();

        /**
         * The meta object literal for the '<em>Alias Name Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasNameType()
         * @generated
         */
        EDataType ALIAS_NAME_TYPE = eINSTANCE.getAliasNameType();

        /**
         * The meta object literal for the '<em>Alias Type Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.AliasTypeType
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getAliasTypeTypeObject()
         * @generated
         */
        EDataType ALIAS_TYPE_TYPE_OBJECT = eINSTANCE.getAliasTypeTypeObject();

        /**
         * The meta object literal for the '<em>Facade Name Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getFacadeNameType()
         * @generated
         */
        EDataType FACADE_NAME_TYPE = eINSTANCE.getFacadeNameType();

        /**
         * The meta object literal for the '<em>Type Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.datamodel.TypeType
         * @see com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl#getTypeTypeObject()
         * @generated
         */
        EDataType TYPE_TYPE_OBJECT = eINSTANCE.getTypeTypeObject();

    }

} //DatamodelPackage
