/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.channeltype;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.tibco.n2.common.channeltype.ChanneltypeFactory
 * @model kind="package"
 * @generated
 */
public interface ChanneltypePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "channeltype";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://channeltype.common.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "channeltype";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ChanneltypePackage eINSTANCE = com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.common.channeltype.ChannelType <em>Channel Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.channeltype.ChannelType
     * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getChannelType()
     * @generated
     */
    int CHANNEL_TYPE = 0;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.channeltype.ImplementationType <em>Implementation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.channeltype.ImplementationType
     * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getImplementationType()
     * @generated
     */
    int IMPLEMENTATION_TYPE = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.channeltype.PresentationType <em>Presentation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.channeltype.PresentationType
     * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getPresentationType()
     * @generated
     */
    int PRESENTATION_TYPE = 2;

    /**
     * The meta object id for the '<em>Channel Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.channeltype.ChannelType
     * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getChannelTypeObject()
     * @generated
     */
    int CHANNEL_TYPE_OBJECT = 3;


    /**
     * The meta object id for the '<em>Implementation Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.channeltype.ImplementationType
     * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getImplementationTypeObject()
     * @generated
     */
    int IMPLEMENTATION_TYPE_OBJECT = 4;

    /**
     * The meta object id for the '<em>Presentation Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.channeltype.PresentationType
     * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getPresentationTypeObject()
     * @generated
     */
    int PRESENTATION_TYPE_OBJECT = 5;


    /**
     * Returns the meta object for enum '{@link com.tibco.n2.common.channeltype.ChannelType <em>Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Channel Type</em>'.
     * @see com.tibco.n2.common.channeltype.ChannelType
     * @generated
     */
    EEnum getChannelType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.common.channeltype.ImplementationType <em>Implementation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Implementation Type</em>'.
     * @see com.tibco.n2.common.channeltype.ImplementationType
     * @generated
     */
    EEnum getImplementationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.common.channeltype.PresentationType <em>Presentation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Presentation Type</em>'.
     * @see com.tibco.n2.common.channeltype.PresentationType
     * @generated
     */
    EEnum getPresentationType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.common.channeltype.ChannelType <em>Channel Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Channel Type Object</em>'.
     * @see com.tibco.n2.common.channeltype.ChannelType
     * @model instanceClass="com.tibco.n2.common.channeltype.ChannelType"
     *        extendedMetaData="name='ChannelType:Object' baseType='ChannelType'"
     * @generated
     */
    EDataType getChannelTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.common.channeltype.ImplementationType <em>Implementation Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Implementation Type Object</em>'.
     * @see com.tibco.n2.common.channeltype.ImplementationType
     * @model instanceClass="com.tibco.n2.common.channeltype.ImplementationType"
     *        extendedMetaData="name='ImplementationType:Object' baseType='ImplementationType'"
     * @generated
     */
    EDataType getImplementationTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.common.channeltype.PresentationType <em>Presentation Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Presentation Type Object</em>'.
     * @see com.tibco.n2.common.channeltype.PresentationType
     * @model instanceClass="com.tibco.n2.common.channeltype.PresentationType"
     *        extendedMetaData="name='PresentationType:Object' baseType='PresentationType'"
     * @generated
     */
    EDataType getPresentationTypeObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ChanneltypeFactory getChanneltypeFactory();

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
         * The meta object literal for the '{@link com.tibco.n2.common.channeltype.ChannelType <em>Channel Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.channeltype.ChannelType
         * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getChannelType()
         * @generated
         */
        EEnum CHANNEL_TYPE = eINSTANCE.getChannelType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.channeltype.ImplementationType <em>Implementation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.channeltype.ImplementationType
         * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getImplementationType()
         * @generated
         */
        EEnum IMPLEMENTATION_TYPE = eINSTANCE.getImplementationType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.channeltype.PresentationType <em>Presentation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.channeltype.PresentationType
         * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getPresentationType()
         * @generated
         */
        EEnum PRESENTATION_TYPE = eINSTANCE.getPresentationType();

        /**
         * The meta object literal for the '<em>Channel Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.channeltype.ChannelType
         * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getChannelTypeObject()
         * @generated
         */
        EDataType CHANNEL_TYPE_OBJECT = eINSTANCE.getChannelTypeObject();

        /**
         * The meta object literal for the '<em>Implementation Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.channeltype.ImplementationType
         * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getImplementationTypeObject()
         * @generated
         */
        EDataType IMPLEMENTATION_TYPE_OBJECT = eINSTANCE.getImplementationTypeObject();

        /**
         * The meta object literal for the '<em>Presentation Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.channeltype.PresentationType
         * @see com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl#getPresentationTypeObject()
         * @generated
         */
        EDataType PRESENTATION_TYPE_OBJECT = eINSTANCE.getPresentationTypeObject();

    }

} //ChanneltypePackage
