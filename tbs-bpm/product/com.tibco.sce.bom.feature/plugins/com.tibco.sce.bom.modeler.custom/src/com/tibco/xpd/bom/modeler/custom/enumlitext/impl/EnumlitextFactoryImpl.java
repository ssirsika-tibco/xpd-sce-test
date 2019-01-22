/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.impl;

import com.tibco.xpd.bom.modeler.custom.enumlitext.*;

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
public class EnumlitextFactoryImpl extends EFactoryImpl implements EnumlitextFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static EnumlitextFactory init() {
        try {
            EnumlitextFactory theEnumlitextFactory = (EnumlitextFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/EnumLiteralExt"); 
            if (theEnumlitextFactory != null) {
                return theEnumlitextFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new EnumlitextFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumlitextFactoryImpl() {
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
            case EnumlitextPackage.DOMAIN_VALUE: return createDomainValue();
            case EnumlitextPackage.SINGLE_VALUE: return createSingleValue();
            case EnumlitextPackage.RANGE_VALUE: return createRangeValue();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DomainValue createDomainValue() {
        DomainValueImpl domainValue = new DomainValueImpl();
        return domainValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SingleValue createSingleValue() {
        SingleValueImpl singleValue = new SingleValueImpl();
        return singleValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RangeValue createRangeValue() {
        RangeValueImpl rangeValue = new RangeValueImpl();
        return rangeValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumlitextPackage getEnumlitextPackage() {
        return (EnumlitextPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static EnumlitextPackage getPackage() {
        return EnumlitextPackage.eINSTANCE;
    }

} //EnumlitextFactoryImpl
