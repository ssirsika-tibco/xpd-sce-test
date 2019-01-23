/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.resources.ui.omnotation;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage
 * @generated
 */
public interface OmNotationFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    OmNotationFactory eINSTANCE = com.tibco.xpd.om.resources.ui.omnotation.impl.OmNotationFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Shape Gradient Style</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Shape Gradient Style</em>'.
     * @generated
     */
    ShapeGradientStyle createShapeGradientStyle();

    /**
     * Returns a new object of class '<em>Border Gradient Style</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Border Gradient Style</em>'.
     * @generated
     */
    BorderGradientStyle createBorderGradientStyle();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    OmNotationPackage getOmNotationPackage();

} //OmNotationFactory
