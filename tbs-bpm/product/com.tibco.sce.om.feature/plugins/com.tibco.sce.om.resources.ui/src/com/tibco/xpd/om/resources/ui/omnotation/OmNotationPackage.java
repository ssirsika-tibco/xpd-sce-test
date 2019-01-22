/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.resources.ui.omnotation;

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
 * @see com.tibco.xpd.om.resources.ui.omnotation.OmNotationFactory
 * @model kind="package"
 * @generated
 */
public interface OmNotationPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "omnotation";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/models/omnotation";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "omnotation";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    OmNotationPackage eINSTANCE = com.tibco.xpd.om.resources.ui.omnotation.impl.OmNotationPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.resources.ui.omnotation.impl.ShapeGradientStyleImpl <em>Shape Gradient Style</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.resources.ui.omnotation.impl.ShapeGradientStyleImpl
     * @see com.tibco.xpd.om.resources.ui.omnotation.impl.OmNotationPackageImpl#getShapeGradientStyle()
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
     * The meta object id for the '{@link com.tibco.xpd.om.resources.ui.omnotation.impl.BorderGradientStyleImpl <em>Border Gradient Style</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.resources.ui.omnotation.impl.BorderGradientStyleImpl
     * @see com.tibco.xpd.om.resources.ui.omnotation.impl.OmNotationPackageImpl#getBorderGradientStyle()
     * @generated
     */
    int BORDER_GRADIENT_STYLE = 1;

    /**
     * The feature id for the '<em><b>Grad Start Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BORDER_GRADIENT_STYLE__GRAD_START_COLOR = NotationPackage.STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Grad End Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BORDER_GRADIENT_STYLE__GRAD_END_COLOR = NotationPackage.STYLE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Border Gradient Style</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BORDER_GRADIENT_STYLE_FEATURE_COUNT = NotationPackage.STYLE_FEATURE_COUNT + 2;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle <em>Shape Gradient Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Shape Gradient Style</em>'.
     * @see com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle
     * @generated
     */
    EClass getShapeGradientStyle();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradStartColor <em>Grad Start Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grad Start Color</em>'.
     * @see com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradStartColor()
     * @see #getShapeGradientStyle()
     * @generated
     */
    EAttribute getShapeGradientStyle_GradStartColor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradEndColor <em>Grad End Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grad End Color</em>'.
     * @see com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle#getGradEndColor()
     * @see #getShapeGradientStyle()
     * @generated
     */
    EAttribute getShapeGradientStyle_GradEndColor();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle <em>Border Gradient Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Border Gradient Style</em>'.
     * @see com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle
     * @generated
     */
    EClass getBorderGradientStyle();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle#getGradStartColor <em>Grad Start Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grad Start Color</em>'.
     * @see com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle#getGradStartColor()
     * @see #getBorderGradientStyle()
     * @generated
     */
    EAttribute getBorderGradientStyle_GradStartColor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle#getGradEndColor <em>Grad End Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grad End Color</em>'.
     * @see com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle#getGradEndColor()
     * @see #getBorderGradientStyle()
     * @generated
     */
    EAttribute getBorderGradientStyle_GradEndColor();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    OmNotationFactory getOmNotationFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.om.resources.ui.omnotation.impl.ShapeGradientStyleImpl <em>Shape Gradient Style</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.resources.ui.omnotation.impl.ShapeGradientStyleImpl
         * @see com.tibco.xpd.om.resources.ui.omnotation.impl.OmNotationPackageImpl#getShapeGradientStyle()
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

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.resources.ui.omnotation.impl.BorderGradientStyleImpl <em>Border Gradient Style</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.resources.ui.omnotation.impl.BorderGradientStyleImpl
         * @see com.tibco.xpd.om.resources.ui.omnotation.impl.OmNotationPackageImpl#getBorderGradientStyle()
         * @generated
         */
        EClass BORDER_GRADIENT_STYLE = eINSTANCE.getBorderGradientStyle();

        /**
         * The meta object literal for the '<em><b>Grad Start Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BORDER_GRADIENT_STYLE__GRAD_START_COLOR = eINSTANCE.getBorderGradientStyle_GradStartColor();

        /**
         * The meta object literal for the '<em><b>Grad End Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BORDER_GRADIENT_STYLE__GRAD_END_COLOR = eINSTANCE.getBorderGradientStyle_GradEndColor();

    }

} //OmNotationPackage
