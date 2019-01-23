/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor.impl;

import com.tibco.xpd.script.descriptor.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DescriptorFactoryImpl extends EFactoryImpl implements DescriptorFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DescriptorFactory init() {
        try {
            DescriptorFactory theDescriptorFactory = (DescriptorFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/XPD/ScriptDescriptor/"); 
            if (theDescriptorFactory != null) {
                return theDescriptorFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DescriptorFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DescriptorFactoryImpl() {
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
            case DescriptorPackage.CAC_TYPE: return createCacType();
            case DescriptorPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case DescriptorPackage.ENUM_TYPE: return createEnumType();
            case DescriptorPackage.FACTORY_TYPE: return createFactoryType();
            case DescriptorPackage.PROCESS_TYPE: return createProcessType();
            case DescriptorPackage.SCRIPT_DESCRIPTOR_TYPE: return createScriptDescriptorType();
            case DescriptorPackage.SCRIPT_TYPE: return createScriptType();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CacType createCacType() {
        CacTypeImpl cacType = new CacTypeImpl();
        return cacType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumType createEnumType() {
        EnumTypeImpl enumType = new EnumTypeImpl();
        return enumType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FactoryType createFactoryType() {
        FactoryTypeImpl factoryType = new FactoryTypeImpl();
        return factoryType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ProcessType createProcessType() {
        ProcessTypeImpl processType = new ProcessTypeImpl();
        return processType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScriptDescriptorType createScriptDescriptorType() {
        ScriptDescriptorTypeImpl scriptDescriptorType = new ScriptDescriptorTypeImpl();
        return scriptDescriptorType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScriptType createScriptType() {
        ScriptTypeImpl scriptType = new ScriptTypeImpl();
        return scriptType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DescriptorPackage getDescriptorPackage() {
        return (DescriptorPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DescriptorPackage getPackage() {
        return DescriptorPackage.eINSTANCE;
    }

} //DescriptorFactoryImpl
