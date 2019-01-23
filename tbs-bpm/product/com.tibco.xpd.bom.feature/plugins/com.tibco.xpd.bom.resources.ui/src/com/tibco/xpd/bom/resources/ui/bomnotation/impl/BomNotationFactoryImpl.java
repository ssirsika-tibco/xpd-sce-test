/**
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.bom.resources.ui.bomnotation.impl;

import com.tibco.xpd.bom.resources.ui.bomnotation.*;

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
public class BomNotationFactoryImpl extends EFactoryImpl implements BomNotationFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static BomNotationFactory init() {
        try {
            BomNotationFactory theBomNotationFactory = (BomNotationFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/models/bomnotation"); //$NON-NLS-1$ 
            if (theBomNotationFactory != null) {
                return theBomNotationFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new BomNotationFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BomNotationFactoryImpl() {
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
            case BomNotationPackage.SHAPE_GRADIENT_STYLE: return createShapeGradientStyle();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ShapeGradientStyle createShapeGradientStyle() {
        ShapeGradientStyleImpl shapeGradientStyle = new ShapeGradientStyleImpl();
        return shapeGradientStyle;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BomNotationPackage getBomNotationPackage() {
        return (BomNotationPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static BomNotationPackage getPackage() {
        return BomNotationPackage.eINSTANCE;
    }

} //BomNotationFactoryImpl
