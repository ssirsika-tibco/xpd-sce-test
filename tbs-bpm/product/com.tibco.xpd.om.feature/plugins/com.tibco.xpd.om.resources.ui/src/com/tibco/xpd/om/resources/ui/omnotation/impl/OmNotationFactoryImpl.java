/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.resources.ui.omnotation.impl;

import com.tibco.xpd.om.resources.ui.omnotation.*;

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
public class OmNotationFactoryImpl extends EFactoryImpl implements OmNotationFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static OmNotationFactory init() {
        try {
            OmNotationFactory theOmNotationFactory = (OmNotationFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/models/omnotation"); 
            if (theOmNotationFactory != null) {
                return theOmNotationFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new OmNotationFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OmNotationFactoryImpl() {
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
            case OmNotationPackage.SHAPE_GRADIENT_STYLE: return createShapeGradientStyle();
            case OmNotationPackage.BORDER_GRADIENT_STYLE: return createBorderGradientStyle();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
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
    public BorderGradientStyle createBorderGradientStyle() {
        BorderGradientStyleImpl borderGradientStyle = new BorderGradientStyleImpl();
        return borderGradientStyle;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OmNotationPackage getOmNotationPackage() {
        return (OmNotationPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static OmNotationPackage getPackage() {
        return OmNotationPackage.eINSTANCE;
    }

} //OmNotationFactoryImpl
