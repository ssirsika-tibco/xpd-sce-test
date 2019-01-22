/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesFactory
 * @model kind="package"
 * @generated
 */
public interface ChannelTypesPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "channeltypes";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/xpd/presentation/channeltypes/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "channeltypes";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ChannelTypesPackage eINSTANCE = com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ModelElementImpl <em>Model Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.ModelElementImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getModelElement()
     * @generated
     */
    int MODEL_ELEMENT = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT__ID = 0;

    /**
     * The number of structural features of the '<em>Model Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.NamedElementImpl <em>Named Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.NamedElementImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getNamedElement()
     * @generated
     */
    int NAMED_ELEMENT = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__NAME = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__DISPLAY_NAME = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__DESCRIPTION = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Named Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.TargetImpl <em>Target</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.TargetImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getTarget()
     * @generated
     */
    int TARGET = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TARGET__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TARGET__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TARGET__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TARGET__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Bindings</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TARGET__BINDINGS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Target</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TARGET_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.PresentationImpl <em>Presentation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.PresentationImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getPresentation()
     * @generated
     */
    int PRESENTATION = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRESENTATION__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRESENTATION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRESENTATION__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRESENTATION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Bindings</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRESENTATION__BINDINGS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Presentation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRESENTATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ImplementationImpl <em>Implementation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.ImplementationImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getImplementation()
     * @generated
     */
    int IMPLEMENTATION = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Bindings</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION__BINDINGS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Implementation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl <em>Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getAttribute()
     * @generated
     */
    int ATTRIBUTE = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Enum Literals</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__ENUM_LITERALS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Default Enum Set</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DEFAULT_ENUM_SET = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DEFAULT_VALUE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__REQUIRED = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Attribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl <em>Channel Types</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getChannelTypes()
     * @generated
     */
    int CHANNEL_TYPES = 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.EnumLiteralImpl <em>Enum Literal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.EnumLiteralImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getEnumLiteral()
     * @generated
     */
    int ENUM_LITERAL = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_LITERAL__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_LITERAL__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_LITERAL__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_LITERAL__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The number of structural features of the '<em>Enum Literal</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_LITERAL_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl <em>Channel Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getChannelType()
     * @generated
     */
    int CHANNEL_TYPE = 7;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__TARGET = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Presentation</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__PRESENTATION = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__IMPLEMENTATION = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Runtime Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__RUNTIME_VERSION = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Destinations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__DESTINATIONS = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Channel Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Targets</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPES__TARGETS = 0;

    /**
     * The feature id for the '<em><b>Presentations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPES__PRESENTATIONS = 1;

    /**
     * The feature id for the '<em><b>Implementations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPES__IMPLEMENTATIONS = 2;

    /**
     * The feature id for the '<em><b>Channel Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPES__CHANNEL_TYPES = 3;

    /**
     * The feature id for the '<em><b>Channel Destinations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPES__CHANNEL_DESTINATIONS = 4;

    /**
     * The number of structural features of the '<em>Channel Types</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPES_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelDestinationImpl <em>Channel Destination</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelDestinationImpl
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getChannelDestination()
     * @generated
     */
    int CHANNEL_DESTINATION = 9;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_DESTINATION__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_DESTINATION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_DESTINATION__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_DESTINATION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Channel Types</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_DESTINATION__CHANNEL_TYPES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Channel Destination</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_DESTINATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.presentation.channeltypes.AttributeType <em>Attribute Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.presentation.channeltypes.AttributeType
     * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getAttributeType()
     * @generated
     */
    int ATTRIBUTE_TYPE = 10;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.Target <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Target</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Target
     * @generated
     */
    EClass getTarget();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channeltypes.Target#getBindings <em>Bindings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Bindings</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Target#getBindings()
     * @see #getTarget()
     * @generated
     */
    EReference getTarget_Bindings();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.Presentation <em>Presentation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Presentation</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Presentation
     * @generated
     */
    EClass getPresentation();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channeltypes.Presentation#getBindings <em>Bindings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Bindings</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Presentation#getBindings()
     * @see #getPresentation()
     * @generated
     */
    EReference getPresentation_Bindings();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.Implementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Implementation</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Implementation
     * @generated
     */
    EClass getImplementation();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channeltypes.Implementation#getBindings <em>Bindings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Bindings</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Implementation#getBindings()
     * @see #getImplementation()
     * @generated
     */
    EReference getImplementation_Bindings();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.Attribute <em>Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Attribute
     * @generated
     */
    EClass getAttribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Attribute#getType()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Type();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getEnumLiterals <em>Enum Literals</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Enum Literals</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Attribute#getEnumLiterals()
     * @see #getAttribute()
     * @generated
     */
    EReference getAttribute_EnumLiterals();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultEnumSet <em>Default Enum Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Default Enum Set</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultEnumSet()
     * @see #getAttribute()
     * @generated
     */
    EReference getAttribute_DefaultEnumSet();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultValue <em>Default Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Value</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultValue()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_DefaultValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.Attribute#isRequired <em>Required</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Required</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.Attribute#isRequired()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Required();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes <em>Channel Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channel Types</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypes
     * @generated
     */
    EClass getChannelTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getTargets <em>Targets</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Targets</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypes#getTargets()
     * @see #getChannelTypes()
     * @generated
     */
    EReference getChannelTypes_Targets();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getPresentations <em>Presentations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Presentations</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypes#getPresentations()
     * @see #getChannelTypes()
     * @generated
     */
    EReference getChannelTypes_Presentations();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getImplementations <em>Implementations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Implementations</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypes#getImplementations()
     * @see #getChannelTypes()
     * @generated
     */
    EReference getChannelTypes_Implementations();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getChannelTypes <em>Channel Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Channel Types</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypes#getChannelTypes()
     * @see #getChannelTypes()
     * @generated
     */
    EReference getChannelTypes_ChannelTypes();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getChannelDestinations <em>Channel Destinations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Channel Destinations</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypes#getChannelDestinations()
     * @see #getChannelTypes()
     * @generated
     */
    EReference getChannelTypes_ChannelDestinations();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.ChannelDestination <em>Channel Destination</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channel Destination</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelDestination
     * @generated
     */
    EClass getChannelDestination();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelDestination#getChannelTypes <em>Channel Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Channel Types</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelDestination#getChannelTypes()
     * @see #getChannelDestination()
     * @generated
     */
    EReference getChannelDestination_ChannelTypes();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.ModelElement <em>Model Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Model Element</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ModelElement
     * @generated
     */
    EClass getModelElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.ModelElement#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ModelElement#getId()
     * @see #getModelElement()
     * @generated
     */
    EAttribute getModelElement_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Named Element</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.NamedElement
     * @generated
     */
    EClass getNamedElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.NamedElement#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.NamedElement#getName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.NamedElement#getDisplayName <em>Display Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Name</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.NamedElement#getDisplayName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_DisplayName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.NamedElement#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.NamedElement#getDescription()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Description();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.EnumLiteral <em>Enum Literal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enum Literal</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.EnumLiteral
     * @generated
     */
    EClass getEnumLiteral();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.presentation.channeltypes.ChannelType <em>Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channel Type</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType
     * @generated
     */
    EClass getChannelType();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Target</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getTarget()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_Target();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getPresentation <em>Presentation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Presentation</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getPresentation()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_Presentation();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Implementation</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getImplementation()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_Implementation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getAttributes <em>Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attributes</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getAttributes()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_Attributes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getRuntimeVersion <em>Runtime Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Runtime Version</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getRuntimeVersion()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_RuntimeVersion();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getDestinations <em>Destinations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Destinations</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getDestinations()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_Destinations();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.presentation.channeltypes.AttributeType <em>Attribute Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Attribute Type</em>'.
     * @see com.tibco.xpd.presentation.channeltypes.AttributeType
     * @generated
     */
    EEnum getAttributeType();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ChannelTypesFactory getChannelTypesFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.TargetImpl <em>Target</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.TargetImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getTarget()
         * @generated
         */
        EClass TARGET = eINSTANCE.getTarget();

        /**
         * The meta object literal for the '<em><b>Bindings</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TARGET__BINDINGS = eINSTANCE.getTarget_Bindings();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.PresentationImpl <em>Presentation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.PresentationImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getPresentation()
         * @generated
         */
        EClass PRESENTATION = eINSTANCE.getPresentation();

        /**
         * The meta object literal for the '<em><b>Bindings</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRESENTATION__BINDINGS = eINSTANCE.getPresentation_Bindings();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ImplementationImpl <em>Implementation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.ImplementationImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getImplementation()
         * @generated
         */
        EClass IMPLEMENTATION = eINSTANCE.getImplementation();

        /**
         * The meta object literal for the '<em><b>Bindings</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference IMPLEMENTATION__BINDINGS = eINSTANCE.getImplementation_Bindings();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl <em>Attribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getAttribute()
         * @generated
         */
        EClass ATTRIBUTE = eINSTANCE.getAttribute();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

        /**
         * The meta object literal for the '<em><b>Enum Literals</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE__ENUM_LITERALS = eINSTANCE.getAttribute_EnumLiterals();

        /**
         * The meta object literal for the '<em><b>Default Enum Set</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE__DEFAULT_ENUM_SET = eINSTANCE.getAttribute_DefaultEnumSet();

        /**
         * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__DEFAULT_VALUE = eINSTANCE.getAttribute_DefaultValue();

        /**
         * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__REQUIRED = eINSTANCE.getAttribute_Required();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl <em>Channel Types</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getChannelTypes()
         * @generated
         */
        EClass CHANNEL_TYPES = eINSTANCE.getChannelTypes();

        /**
         * The meta object literal for the '<em><b>Targets</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPES__TARGETS = eINSTANCE.getChannelTypes_Targets();

        /**
         * The meta object literal for the '<em><b>Presentations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPES__PRESENTATIONS = eINSTANCE.getChannelTypes_Presentations();

        /**
         * The meta object literal for the '<em><b>Implementations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPES__IMPLEMENTATIONS = eINSTANCE.getChannelTypes_Implementations();

        /**
         * The meta object literal for the '<em><b>Channel Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPES__CHANNEL_TYPES = eINSTANCE.getChannelTypes_ChannelTypes();

        /**
         * The meta object literal for the '<em><b>Channel Destinations</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPES__CHANNEL_DESTINATIONS = eINSTANCE.getChannelTypes_ChannelDestinations();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelDestinationImpl <em>Channel Destination</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelDestinationImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getChannelDestination()
         * @generated
         */
        EClass CHANNEL_DESTINATION = eINSTANCE.getChannelDestination();

        /**
         * The meta object literal for the '<em><b>Channel Types</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_DESTINATION__CHANNEL_TYPES = eINSTANCE.getChannelDestination_ChannelTypes();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ModelElementImpl <em>Model Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.ModelElementImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getModelElement()
         * @generated
         */
        EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_ELEMENT__ID = eINSTANCE.getModelElement_Id();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.NamedElementImpl <em>Named Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.NamedElementImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getNamedElement()
         * @generated
         */
        EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

        /**
         * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__DISPLAY_NAME = eINSTANCE.getNamedElement_DisplayName();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__DESCRIPTION = eINSTANCE.getNamedElement_Description();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.EnumLiteralImpl <em>Enum Literal</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.EnumLiteralImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getEnumLiteral()
         * @generated
         */
        EClass ENUM_LITERAL = eINSTANCE.getEnumLiteral();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl <em>Channel Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getChannelType()
         * @generated
         */
        EClass CHANNEL_TYPE = eINSTANCE.getChannelType();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__TARGET = eINSTANCE.getChannelType_Target();

        /**
         * The meta object literal for the '<em><b>Presentation</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__PRESENTATION = eINSTANCE.getChannelType_Presentation();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__IMPLEMENTATION = eINSTANCE.getChannelType_Implementation();

        /**
         * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__ATTRIBUTES = eINSTANCE.getChannelType_Attributes();

        /**
         * The meta object literal for the '<em><b>Runtime Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__RUNTIME_VERSION = eINSTANCE.getChannelType_RuntimeVersion();

        /**
         * The meta object literal for the '<em><b>Destinations</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__DESTINATIONS = eINSTANCE.getChannelType_Destinations();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.presentation.channeltypes.AttributeType <em>Attribute Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.presentation.channeltypes.AttributeType
         * @see com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl#getAttributeType()
         * @generated
         */
        EEnum ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

    }

} //ChannelTypesPackage
