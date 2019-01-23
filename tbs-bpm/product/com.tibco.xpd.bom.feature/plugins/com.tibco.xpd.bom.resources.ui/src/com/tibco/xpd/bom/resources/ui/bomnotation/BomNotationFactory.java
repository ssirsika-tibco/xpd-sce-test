/**
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.bom.resources.ui.bomnotation;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage
 * @generated
 */
public interface BomNotationFactory extends EFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    BomNotationFactory eINSTANCE = com.tibco.xpd.bom.resources.ui.bomnotation.impl.BomNotationFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Shape Gradient Style</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Shape Gradient Style</em>'.
     * @generated
     */
    ShapeGradientStyle createShapeGradientStyle();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    BomNotationPackage getBomNotationPackage();

} //BomNotationFactory
