/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.util;

import com.tibco.n2.common.datamodel.*;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.datamodel.DatamodelPackage
 * @generated
 */
public class DatamodelValidator extends EObjectValidator {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final DatamodelValidator INSTANCE = new DatamodelValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "com.tibco.n2.common.datamodel";

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written constants.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * The cached base package validator.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XMLTypeValidator xmlTypeValidator;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatamodelValidator() {
        super();
        xmlTypeValidator = XMLTypeValidator.INSTANCE;
    }

    /**
     * Returns the package of this validator switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
      return DatamodelPackage.eINSTANCE;
    }

    /**
     * Calls <code>validateXXX</code> for the corresponding classifier of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
        switch (classifierID) {
            case DatamodelPackage.ALIAS_TYPE:
                return validateAliasType((AliasType)value, diagnostics, context);
            case DatamodelPackage.COMPLEX_SPEC_TYPE:
                return validateComplexSpecType((ComplexSpecType)value, diagnostics, context);
            case DatamodelPackage.DATA_MODEL:
                return validateDataModel((DataModel)value, diagnostics, context);
            case DatamodelPackage.FIELD_TYPE:
                return validateFieldType((FieldType)value, diagnostics, context);
            case DatamodelPackage.SIMPLE_SPEC_TYPE:
                return validateSimpleSpecType((SimpleSpecType)value, diagnostics, context);
            case DatamodelPackage.WORK_TYPE:
                return validateWorkType((WorkType)value, diagnostics, context);
            case DatamodelPackage.WORK_TYPE_SPEC:
                return validateWorkTypeSpec((WorkTypeSpec)value, diagnostics, context);
            case DatamodelPackage.ALIAS_TYPE_TYPE:
                return validateAliasTypeType((AliasTypeType)value, diagnostics, context);
            case DatamodelPackage.TYPE_TYPE:
                return validateTypeType((TypeType)value, diagnostics, context);
            case DatamodelPackage.ALIAS_DESCRIPTION_TYPE:
                return validateAliasDescriptionType((String)value, diagnostics, context);
            case DatamodelPackage.ALIAS_NAME_TYPE:
                return validateAliasNameType((String)value, diagnostics, context);
            case DatamodelPackage.ALIAS_TYPE_TYPE_OBJECT:
                return validateAliasTypeTypeObject((AliasTypeType)value, diagnostics, context);
            case DatamodelPackage.FACADE_NAME_TYPE:
                return validateFacadeNameType((String)value, diagnostics, context);
            case DatamodelPackage.TYPE_TYPE_OBJECT:
                return validateTypeTypeObject((TypeType)value, diagnostics, context);
            default:
                return true;
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAliasType(AliasType aliasType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(aliasType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateComplexSpecType(ComplexSpecType complexSpecType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(complexSpecType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDataModel(DataModel dataModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(dataModel, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateFieldType(FieldType fieldType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(fieldType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSimpleSpecType(SimpleSpecType simpleSpecType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(simpleSpecType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkType(WorkType workType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkTypeSpec(WorkTypeSpec workTypeSpec, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workTypeSpec, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAliasTypeType(AliasTypeType aliasTypeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateTypeType(TypeType typeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAliasDescriptionType(String aliasDescriptionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAliasDescriptionType_MaxLength(aliasDescriptionType, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Alias Description Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAliasDescriptionType_MaxLength(String aliasDescriptionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = aliasDescriptionType.length();
        boolean result = length <= 256;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(DatamodelPackage.Literals.ALIAS_DESCRIPTION_TYPE, aliasDescriptionType, length, 256, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAliasNameType(String aliasNameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAliasNameType_MaxLength(aliasNameType, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Alias Name Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAliasNameType_MaxLength(String aliasNameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = aliasNameType.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(DatamodelPackage.Literals.ALIAS_NAME_TYPE, aliasNameType, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAliasTypeTypeObject(AliasTypeType aliasTypeTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateFacadeNameType(String facadeNameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateFacadeNameType_MaxLength(facadeNameType, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Facade Name Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateFacadeNameType_MaxLength(String facadeNameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = facadeNameType.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(DatamodelPackage.Literals.FACADE_NAME_TYPE, facadeNameType, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateTypeTypeObject(TypeType typeTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        // TODO
        // Specialize this to return a resource locator for messages specific to this validator.
        // Ensure that you remove @generated or mark it @generated NOT
        return super.getResourceLocator();
    }

} //DatamodelValidator
