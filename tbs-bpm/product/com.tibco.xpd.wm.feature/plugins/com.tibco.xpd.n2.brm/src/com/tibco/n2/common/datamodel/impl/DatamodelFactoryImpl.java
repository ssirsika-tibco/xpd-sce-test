/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DatamodelFactoryImpl extends EFactoryImpl implements DatamodelFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DatamodelFactory init() {
        try {
            DatamodelFactory theDatamodelFactory = (DatamodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://datamodel.common.n2.tibco.com"); 
            if (theDatamodelFactory != null) {
                return theDatamodelFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DatamodelFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatamodelFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case DatamodelPackage.ALIAS_TYPE: return createAliasType();
            case DatamodelPackage.COMPLEX_SPEC_TYPE: return createComplexSpecType();
            case DatamodelPackage.DATA_MODEL: return createDataModel();
            case DatamodelPackage.FIELD_TYPE: return createFieldType();
            case DatamodelPackage.SIMPLE_SPEC_TYPE: return createSimpleSpecType();
            case DatamodelPackage.WORK_TYPE: return createWorkType();
            case DatamodelPackage.WORK_TYPE_SPEC: return createWorkTypeSpec();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case DatamodelPackage.ALIAS_TYPE_TYPE:
                return createAliasTypeTypeFromString(eDataType, initialValue);
            case DatamodelPackage.TYPE_TYPE:
                return createTypeTypeFromString(eDataType, initialValue);
            case DatamodelPackage.ALIAS_DESCRIPTION_TYPE:
                return createAliasDescriptionTypeFromString(eDataType, initialValue);
            case DatamodelPackage.ALIAS_NAME_TYPE:
                return createAliasNameTypeFromString(eDataType, initialValue);
            case DatamodelPackage.ALIAS_TYPE_TYPE_OBJECT:
                return createAliasTypeTypeObjectFromString(eDataType, initialValue);
            case DatamodelPackage.FACADE_NAME_TYPE:
                return createFacadeNameTypeFromString(eDataType, initialValue);
            case DatamodelPackage.TYPE_TYPE_OBJECT:
                return createTypeTypeObjectFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case DatamodelPackage.ALIAS_TYPE_TYPE:
                return convertAliasTypeTypeToString(eDataType, instanceValue);
            case DatamodelPackage.TYPE_TYPE:
                return convertTypeTypeToString(eDataType, instanceValue);
            case DatamodelPackage.ALIAS_DESCRIPTION_TYPE:
                return convertAliasDescriptionTypeToString(eDataType, instanceValue);
            case DatamodelPackage.ALIAS_NAME_TYPE:
                return convertAliasNameTypeToString(eDataType, instanceValue);
            case DatamodelPackage.ALIAS_TYPE_TYPE_OBJECT:
                return convertAliasTypeTypeObjectToString(eDataType, instanceValue);
            case DatamodelPackage.FACADE_NAME_TYPE:
                return convertFacadeNameTypeToString(eDataType, instanceValue);
            case DatamodelPackage.TYPE_TYPE_OBJECT:
                return convertTypeTypeObjectToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AliasType createAliasType() {
        AliasTypeImpl aliasType = new AliasTypeImpl();
        return aliasType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ComplexSpecType createComplexSpecType() {
        ComplexSpecTypeImpl complexSpecType = new ComplexSpecTypeImpl();
        return complexSpecType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataModel createDataModel() {
        DataModelImpl dataModel = new DataModelImpl();
        return dataModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FieldType createFieldType() {
        FieldTypeImpl fieldType = new FieldTypeImpl();
        return fieldType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimpleSpecType createSimpleSpecType() {
        SimpleSpecTypeImpl simpleSpecType = new SimpleSpecTypeImpl();
        return simpleSpecType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkType createWorkType() {
        WorkTypeImpl workType = new WorkTypeImpl();
        return workType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeSpec createWorkTypeSpec() {
        WorkTypeSpecImpl workTypeSpec = new WorkTypeSpecImpl();
        return workTypeSpec;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AliasTypeType createAliasTypeTypeFromString(EDataType eDataType, String initialValue) {
        AliasTypeType result = AliasTypeType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAliasTypeTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TypeType createTypeTypeFromString(EDataType eDataType, String initialValue) {
        TypeType result = TypeType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTypeTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAliasDescriptionTypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAliasDescriptionTypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAliasNameTypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAliasNameTypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AliasTypeType createAliasTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createAliasTypeTypeFromString(DatamodelPackage.Literals.ALIAS_TYPE_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAliasTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertAliasTypeTypeToString(DatamodelPackage.Literals.ALIAS_TYPE_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createFacadeNameTypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertFacadeNameTypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TypeType createTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createTypeTypeFromString(DatamodelPackage.Literals.TYPE_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertTypeTypeToString(DatamodelPackage.Literals.TYPE_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatamodelPackage getDatamodelPackage() {
        return (DatamodelPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DatamodelPackage getPackage() {
        return DatamodelPackage.eINSTANCE;
    }

} //DatamodelFactoryImpl
