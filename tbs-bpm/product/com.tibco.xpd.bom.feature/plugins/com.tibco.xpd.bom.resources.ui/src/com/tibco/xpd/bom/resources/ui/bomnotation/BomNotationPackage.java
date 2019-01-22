/**
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.bom.resources.ui.bomnotation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.gmf.runtime.notation.NotationPackage;

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
 * @see com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationFactory
 * @model kind="package"
 * @generated
 */
public interface BomNotationPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "bomnotation"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/models/bomnotation"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "bomnotation"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    BomNotationPackage eINSTANCE = com.tibco.xpd.bom.resources.ui.bomnotation.impl.BomNotationPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.bom.resources.ui.bomnotation.impl.ShapeGradientStyleImpl <em>Shape Gradient Style</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.bom.resources.ui.bomnotation.impl.ShapeGradientStyleImpl
     * @see com.tibco.xpd.bom.resources.ui.bomnotation.impl.BomNotationPackageImpl#getShapeGradientStyle()
     * @generated
     */
    int SHAPE_GRADIENT_STYLE = 0;

    /**
     * The feature id for the '<em><b>Grad Start Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SHAPE_GRADIENT_STYLE__GRAD_START_COLOR = NotationPackage.STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Grad End Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SHAPE_GRADIENT_STYLE__GRAD_END_COLOR = NotationPackage.STYLE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Shape Gradient Style</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SHAPE_GRADIENT_STYLE_FEATURE_COUNT = NotationPackage.STYLE_FEATURE_COUNT + 2;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle <em>Shape Gradient Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Shape Gradient Style</em>'.
     * @see com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle
     * @generated
     */
    EClass getShapeGradientStyle();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle#getGradStartColor <em>Grad Start Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grad Start Color</em>'.
     * @see com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle#getGradStartColor()
     * @see #getShapeGradientStyle()
     * @generated
     */
    EAttribute getShapeGradientStyle_GradStartColor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle#getGradEndColor <em>Grad End Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grad End Color</em>'.
     * @see com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle#getGradEndColor()
     * @see #getShapeGradientStyle()
     * @generated
     */
    EAttribute getShapeGradientStyle_GradEndColor();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    BomNotationFactory getBomNotationFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.bom.resources.ui.bomnotation.impl.ShapeGradientStyleImpl <em>Shape Gradient Style</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.bom.resources.ui.bomnotation.impl.ShapeGradientStyleImpl
         * @see com.tibco.xpd.bom.resources.ui.bomnotation.impl.BomNotationPackageImpl#getShapeGradientStyle()
         * @generated
         */
        EClass SHAPE_GRADIENT_STYLE = eINSTANCE.getShapeGradientStyle();

        /**
         * The meta object literal for the '<em><b>Grad Start Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SHAPE_GRADIENT_STYLE__GRAD_START_COLOR = eINSTANCE.getShapeGradientStyle_GradStartColor();

        /**
         * The meta object literal for the '<em><b>Grad End Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SHAPE_GRADIENT_STYLE__GRAD_END_COLOR = eINSTANCE.getShapeGradientStyle_GradEndColor();

    }

} //BomNotationPackage
