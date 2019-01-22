/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextFactory
 * @model kind="package"
 * @generated
 */
public interface EnumlitextPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "enumlitext";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/EnumLiteralExt";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "elx";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EnumlitextPackage eINSTANCE = com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.DomainValueImpl <em>Domain Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.DomainValueImpl
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextPackageImpl#getDomainValue()
     * @generated
     */
    int DOMAIN_VALUE = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMAIN_VALUE__VALUE = 0;

    /**
     * The number of structural features of the '<em>Domain Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOMAIN_VALUE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.SingleValueImpl <em>Single Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.SingleValueImpl
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextPackageImpl#getSingleValue()
     * @generated
     */
    int SINGLE_VALUE = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SINGLE_VALUE__VALUE = DOMAIN_VALUE__VALUE;

    /**
     * The number of structural features of the '<em>Single Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SINGLE_VALUE_FEATURE_COUNT = DOMAIN_VALUE_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl <em>Range Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextPackageImpl#getRangeValue()
     * @generated
     */
    int RANGE_VALUE = 2;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RANGE_VALUE__VALUE = DOMAIN_VALUE__VALUE;

    /**
     * The feature id for the '<em><b>Lower</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RANGE_VALUE__LOWER = DOMAIN_VALUE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Upper</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RANGE_VALUE__UPPER = DOMAIN_VALUE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Lower Inclusive</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RANGE_VALUE__LOWER_INCLUSIVE = DOMAIN_VALUE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Upper Inclusive</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RANGE_VALUE__UPPER_INCLUSIVE = DOMAIN_VALUE_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Range Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RANGE_VALUE_FEATURE_COUNT = DOMAIN_VALUE_FEATURE_COUNT + 4;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue <em>Domain Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Domain Value</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue
     * @generated
     */
    EClass getDomainValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue#getValue()
     * @see #getDomainValue()
     * @generated
     */
    EAttribute getDomainValue_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue <em>Single Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Single Value</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue
     * @generated
     */
    EClass getSingleValue();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue <em>Range Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Range Value</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue
     * @generated
     */
    EClass getRangeValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#getLower <em>Lower</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lower</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#getLower()
     * @see #getRangeValue()
     * @generated
     */
    EAttribute getRangeValue_Lower();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#getUpper <em>Upper</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Upper</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#getUpper()
     * @see #getRangeValue()
     * @generated
     */
    EAttribute getRangeValue_Upper();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#isLowerInclusive <em>Lower Inclusive</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lower Inclusive</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#isLowerInclusive()
     * @see #getRangeValue()
     * @generated
     */
    EAttribute getRangeValue_LowerInclusive();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#isUpperInclusive <em>Upper Inclusive</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Upper Inclusive</em>'.
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue#isUpperInclusive()
     * @see #getRangeValue()
     * @generated
     */
    EAttribute getRangeValue_UpperInclusive();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    EnumlitextFactory getEnumlitextFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.DomainValueImpl <em>Domain Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.DomainValueImpl
         * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextPackageImpl#getDomainValue()
         * @generated
         */
        EClass DOMAIN_VALUE = eINSTANCE.getDomainValue();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOMAIN_VALUE__VALUE = eINSTANCE.getDomainValue_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.SingleValueImpl <em>Single Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.SingleValueImpl
         * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextPackageImpl#getSingleValue()
         * @generated
         */
        EClass SINGLE_VALUE = eINSTANCE.getSingleValue();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl <em>Range Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl
         * @see com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextPackageImpl#getRangeValue()
         * @generated
         */
        EClass RANGE_VALUE = eINSTANCE.getRangeValue();

        /**
         * The meta object literal for the '<em><b>Lower</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RANGE_VALUE__LOWER = eINSTANCE.getRangeValue_Lower();

        /**
         * The meta object literal for the '<em><b>Upper</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RANGE_VALUE__UPPER = eINSTANCE.getRangeValue_Upper();

        /**
         * The meta object literal for the '<em><b>Lower Inclusive</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RANGE_VALUE__LOWER_INCLUSIVE = eINSTANCE.getRangeValue_LowerInclusive();

        /**
         * The meta object literal for the '<em><b>Upper Inclusive</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RANGE_VALUE__UPPER_INCLUSIVE = eINSTANCE.getRangeValue_UpperInclusive();

    }

} //EnumlitextPackage
