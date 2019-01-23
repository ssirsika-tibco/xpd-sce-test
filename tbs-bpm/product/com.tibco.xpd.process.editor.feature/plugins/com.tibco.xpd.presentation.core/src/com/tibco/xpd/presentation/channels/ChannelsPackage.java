/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels;

import com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see com.tibco.xpd.presentation.channels.ChannelsFactory
 * @model kind="package"
 * @generated
 */
public interface ChannelsPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "channels";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/xpd/presentation/channels/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "channels";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ChannelsPackage eINSTANCE = com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channels.impl.ChannelsImpl <em>Channels</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channels.impl.ChannelsImpl
     * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getChannels()
     * @generated
     */
    int CHANNELS = 0;

    /**
     * The feature id for the '<em><b>Channels</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNELS__CHANNELS = 0;

    /**
     * The number of structural features of the '<em>Channels</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNELS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channels.impl.ChannelImpl <em>Channel</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channels.impl.ChannelImpl
     * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getChannel()
     * @generated
     */
    int CHANNEL = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL__ID = ChannelTypesPackage.NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL__NAME = ChannelTypesPackage.NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL__DISPLAY_NAME = ChannelTypesPackage.NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL__DESCRIPTION = ChannelTypesPackage.NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Type Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL__TYPE_ASSOCIATIONS = ChannelTypesPackage.NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Default</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL__DEFAULT = ChannelTypesPackage.NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Channel</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_FEATURE_COUNT = ChannelTypesPackage.NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channels.impl.AttributeValueImpl <em>Attribute Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channels.impl.AttributeValueImpl
     * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getAttributeValue()
     * @generated
     */
    int ATTRIBUTE_VALUE = 2;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__VALUE = 0;

    /**
     * The feature id for the '<em><b>Enum Values</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__ENUM_VALUES = 1;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__ATTRIBUTE = 2;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__TYPE = 3;

    /**
     * The number of structural features of the '<em>Attribute Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl <em>Type Association</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl
     * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getTypeAssociation()
     * @generated
     */
    int TYPE_ASSOCIATION = 3;

    /**
     * The feature id for the '<em><b>Channel Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_ASSOCIATION__CHANNEL_TYPE = 0;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_ASSOCIATION__ATTRIBUTE_VALUES = 1;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES = 2;

    /**
     * The feature id for the '<em><b>Channel</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_ASSOCIATION__CHANNEL = 3;

    /**
     * The feature id for the '<em><b>Derived Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_ASSOCIATION__DERIVED_ID = 4;

    /**
     * The number of structural features of the '<em>Type Association</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_ASSOCIATION_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channels.impl.ExtendedAttributeImpl <em>Extended Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channels.impl.ExtendedAttributeImpl
     * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getExtendedAttribute()
     * @generated
     */
    int EXTENDED_ATTRIBUTE = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE__NAME = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE__VALUE = 1;

    /**
     * The number of structural features of the '<em>Extended Attribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE_FEATURE_COUNT = 2;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channels.Channels <em>Channels</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channels</em>'.
     * @see com.tibco.xpd.presentation.channels.Channels
     * @generated
     */
    EClass getChannels();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channels.Channels#getChannels <em>Channels</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Channels</em>'.
     * @see com.tibco.xpd.presentation.channels.Channels#getChannels()
     * @see #getChannels()
     * @generated
     */
    EReference getChannels_Channels();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channels.Channel <em>Channel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channel</em>'.
     * @see com.tibco.xpd.presentation.channels.Channel
     * @generated
     */
    EClass getChannel();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channels.Channel#getTypeAssociations <em>Type Associations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Type Associations</em>'.
     * @see com.tibco.xpd.presentation.channels.Channel#getTypeAssociations()
     * @see #getChannel()
     * @generated
     */
    EReference getChannel_TypeAssociations();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channels.Channel#isDefault <em>Default</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default</em>'.
     * @see com.tibco.xpd.presentation.channels.Channel#isDefault()
     * @see #getChannel()
     * @generated
     */
    EAttribute getChannel_Default();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channels.AttributeValue <em>Attribute Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute Value</em>'.
     * @see com.tibco.xpd.presentation.channels.AttributeValue
     * @generated
     */
    EClass getAttributeValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channels.AttributeValue#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.presentation.channels.AttributeValue#getValue()
     * @see #getAttributeValue()
     * @generated
     */
    EAttribute getAttributeValue_Value();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channels.AttributeValue#getEnumValues <em>Enum Values</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Enum Values</em>'.
     * @see com.tibco.xpd.presentation.channels.AttributeValue#getEnumValues()
     * @see #getAttributeValue()
     * @generated
     */
    EReference getAttributeValue_EnumValues();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.presentation.channels.AttributeValue#getAttribute <em>Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Attribute</em>'.
     * @see com.tibco.xpd.presentation.channels.AttributeValue#getAttribute()
     * @see #getAttributeValue()
     * @generated
     */
    EReference getAttributeValue_Attribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channels.AttributeValue#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.presentation.channels.AttributeValue#getType()
     * @see #getAttributeValue()
     * @generated
     */
    EAttribute getAttributeValue_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channels.TypeAssociation <em>Type Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Type Association</em>'.
     * @see com.tibco.xpd.presentation.channels.TypeAssociation
     * @generated
     */
    EClass getTypeAssociation();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.presentation.channels.TypeAssociation#getChannelType <em>Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Channel Type</em>'.
     * @see com.tibco.xpd.presentation.channels.TypeAssociation#getChannelType()
     * @see #getTypeAssociation()
     * @generated
     */
    EReference getTypeAssociation_ChannelType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channels.TypeAssociation#getAttributeValues <em>Attribute Values</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attribute Values</em>'.
     * @see com.tibco.xpd.presentation.channels.TypeAssociation#getAttributeValues()
     * @see #getTypeAssociation()
     * @generated
     */
    EReference getTypeAssociation_AttributeValues();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channels.TypeAssociation#getExtendedAttributes <em>Extended Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Extended Attributes</em>'.
     * @see com.tibco.xpd.presentation.channels.TypeAssociation#getExtendedAttributes()
     * @see #getTypeAssociation()
     * @generated
     */
    EReference getTypeAssociation_ExtendedAttributes();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.presentation.channels.TypeAssociation#getChannel <em>Channel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Channel</em>'.
     * @see com.tibco.xpd.presentation.channels.TypeAssociation#getChannel()
     * @see #getTypeAssociation()
     * @generated
     */
    EReference getTypeAssociation_Channel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channels.TypeAssociation#getDerivedId <em>Derived Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Derived Id</em>'.
     * @see com.tibco.xpd.presentation.channels.TypeAssociation#getDerivedId()
     * @see #getTypeAssociation()
     * @generated
     */
    EAttribute getTypeAssociation_DerivedId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channels.ExtendedAttribute <em>Extended Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Extended Attribute</em>'.
     * @see com.tibco.xpd.presentation.channels.ExtendedAttribute
     * @generated
     */
    EClass getExtendedAttribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channels.ExtendedAttribute#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.presentation.channels.ExtendedAttribute#getName()
     * @see #getExtendedAttribute()
     * @generated
     */
    EAttribute getExtendedAttribute_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channels.ExtendedAttribute#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.presentation.channels.ExtendedAttribute#getValue()
     * @see #getExtendedAttribute()
     * @generated
     */
    EAttribute getExtendedAttribute_Value();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ChannelsFactory getChannelsFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channels.impl.ChannelsImpl <em>Channels</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channels.impl.ChannelsImpl
         * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getChannels()
         * @generated
         */
        EClass CHANNELS = eINSTANCE.getChannels();

        /**
         * The meta object literal for the '<em><b>Channels</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNELS__CHANNELS = eINSTANCE.getChannels_Channels();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channels.impl.ChannelImpl <em>Channel</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channels.impl.ChannelImpl
         * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getChannel()
         * @generated
         */
        EClass CHANNEL = eINSTANCE.getChannel();

        /**
         * The meta object literal for the '<em><b>Type Associations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL__TYPE_ASSOCIATIONS = eINSTANCE.getChannel_TypeAssociations();

        /**
         * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL__DEFAULT = eINSTANCE.getChannel_Default();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channels.impl.AttributeValueImpl <em>Attribute Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channels.impl.AttributeValueImpl
         * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getAttributeValue()
         * @generated
         */
        EClass ATTRIBUTE_VALUE = eINSTANCE.getAttributeValue();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_VALUE__VALUE = eINSTANCE.getAttributeValue_Value();

        /**
         * The meta object literal for the '<em><b>Enum Values</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE_VALUE__ENUM_VALUES = eINSTANCE.getAttributeValue_EnumValues();

        /**
         * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE_VALUE__ATTRIBUTE = eINSTANCE.getAttributeValue_Attribute();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_VALUE__TYPE = eINSTANCE.getAttributeValue_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl <em>Type Association</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl
         * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getTypeAssociation()
         * @generated
         */
        EClass TYPE_ASSOCIATION = eINSTANCE.getTypeAssociation();

        /**
         * The meta object literal for the '<em><b>Channel Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_ASSOCIATION__CHANNEL_TYPE = eINSTANCE.getTypeAssociation_ChannelType();

        /**
         * The meta object literal for the '<em><b>Attribute Values</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_ASSOCIATION__ATTRIBUTE_VALUES = eINSTANCE.getTypeAssociation_AttributeValues();

        /**
         * The meta object literal for the '<em><b>Extended Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES = eINSTANCE.getTypeAssociation_ExtendedAttributes();

        /**
         * The meta object literal for the '<em><b>Channel</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_ASSOCIATION__CHANNEL = eINSTANCE.getTypeAssociation_Channel();

        /**
         * The meta object literal for the '<em><b>Derived Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TYPE_ASSOCIATION__DERIVED_ID = eINSTANCE.getTypeAssociation_DerivedId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channels.impl.ExtendedAttributeImpl <em>Extended Attribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channels.impl.ExtendedAttributeImpl
         * @see com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl#getExtendedAttribute()
         * @generated
         */
        EClass EXTENDED_ATTRIBUTE = eINSTANCE.getExtendedAttribute();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTENDED_ATTRIBUTE__NAME = eINSTANCE.getExtendedAttribute_Name();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTENDED_ATTRIBUTE__VALUE = eINSTANCE.getExtendedAttribute_Value();

    }

} //ChannelsPackage
