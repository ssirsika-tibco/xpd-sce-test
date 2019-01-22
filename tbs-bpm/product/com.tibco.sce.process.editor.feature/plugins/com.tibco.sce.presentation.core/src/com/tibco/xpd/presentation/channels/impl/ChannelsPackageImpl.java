/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels.impl;

import com.tibco.xpd.presentation.channels.AttributeValue;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.ChannelsFactory;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.ExtendedAttribute;
import com.tibco.xpd.presentation.channels.TypeAssociation;

import com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage;

import com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChannelsPackageImpl extends EPackageImpl implements ChannelsPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attributeValueEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass typeAssociationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass extendedAttributeEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ChannelsPackageImpl() {
        super(eNS_URI, ChannelsFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link ChannelsPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ChannelsPackage init() {
        if (isInited) return (ChannelsPackage)EPackage.Registry.INSTANCE.getEPackage(ChannelsPackage.eNS_URI);

        // Obtain or create and register package
        ChannelsPackageImpl theChannelsPackage = (ChannelsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ChannelsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ChannelsPackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        ChannelTypesPackageImpl theChannelTypesPackage = (ChannelTypesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelTypesPackage.eNS_URI) instanceof ChannelTypesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelTypesPackage.eNS_URI) : ChannelTypesPackage.eINSTANCE);

        // Create package meta-data objects
        theChannelsPackage.createPackageContents();
        theChannelTypesPackage.createPackageContents();

        // Initialize created meta-data
        theChannelsPackage.initializePackageContents();
        theChannelTypesPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theChannelsPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ChannelsPackage.eNS_URI, theChannelsPackage);
        return theChannelsPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannels() {
        return channelsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannels_Channels() {
        return (EReference)channelsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannel() {
        return channelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannel_TypeAssociations() {
        return (EReference)channelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannel_Default() {
        return (EAttribute)channelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttributeValue() {
        return attributeValueEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeValue_Value() {
        return (EAttribute)attributeValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttributeValue_EnumValues() {
        return (EReference)attributeValueEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttributeValue_Attribute() {
        return (EReference)attributeValueEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeValue_Type() {
        return (EAttribute)attributeValueEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTypeAssociation() {
        return typeAssociationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeAssociation_ChannelType() {
        return (EReference)typeAssociationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeAssociation_AttributeValues() {
        return (EReference)typeAssociationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeAssociation_ExtendedAttributes() {
        return (EReference)typeAssociationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypeAssociation_Channel() {
        return (EReference)typeAssociationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTypeAssociation_DerivedId() {
        return (EAttribute)typeAssociationEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExtendedAttribute() {
        return extendedAttributeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExtendedAttribute_Name() {
        return (EAttribute)extendedAttributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExtendedAttribute_Value() {
        return (EAttribute)extendedAttributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelsFactory getChannelsFactory() {
        return (ChannelsFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        channelsEClass = createEClass(CHANNELS);
        createEReference(channelsEClass, CHANNELS__CHANNELS);

        channelEClass = createEClass(CHANNEL);
        createEReference(channelEClass, CHANNEL__TYPE_ASSOCIATIONS);
        createEAttribute(channelEClass, CHANNEL__DEFAULT);

        attributeValueEClass = createEClass(ATTRIBUTE_VALUE);
        createEAttribute(attributeValueEClass, ATTRIBUTE_VALUE__VALUE);
        createEReference(attributeValueEClass, ATTRIBUTE_VALUE__ENUM_VALUES);
        createEReference(attributeValueEClass, ATTRIBUTE_VALUE__ATTRIBUTE);
        createEAttribute(attributeValueEClass, ATTRIBUTE_VALUE__TYPE);

        typeAssociationEClass = createEClass(TYPE_ASSOCIATION);
        createEReference(typeAssociationEClass, TYPE_ASSOCIATION__CHANNEL_TYPE);
        createEReference(typeAssociationEClass, TYPE_ASSOCIATION__ATTRIBUTE_VALUES);
        createEReference(typeAssociationEClass, TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES);
        createEReference(typeAssociationEClass, TYPE_ASSOCIATION__CHANNEL);
        createEAttribute(typeAssociationEClass, TYPE_ASSOCIATION__DERIVED_ID);

        extendedAttributeEClass = createEClass(EXTENDED_ATTRIBUTE);
        createEAttribute(extendedAttributeEClass, EXTENDED_ATTRIBUTE__NAME);
        createEAttribute(extendedAttributeEClass, EXTENDED_ATTRIBUTE__VALUE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        ChannelTypesPackage theChannelTypesPackage = (ChannelTypesPackage)EPackage.Registry.INSTANCE.getEPackage(ChannelTypesPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        channelEClass.getESuperTypes().add(theChannelTypesPackage.getNamedElement());

        // Initialize classes and features; add operations and parameters
        initEClass(channelsEClass, Channels.class, "Channels", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getChannels_Channels(), this.getChannel(), null, "channels", null, 0, -1, Channels.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(channelEClass, Channel.class, "Channel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getChannel_TypeAssociations(), this.getTypeAssociation(), null, "typeAssociations", null, 0, -1, Channel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannel_Default(), ecorePackage.getEBoolean(), "default", "false", 0, 1, Channel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        EOperation op = addEOperation(channelEClass, this.getTypeAssociation(), "getTypeAssociations", 0, -1, IS_UNIQUE, !IS_ORDERED);
        addEParameter(op, theChannelTypesPackage.getChannelDestination(), "destinations", 0, -1, IS_UNIQUE, !IS_ORDERED);

        initEClass(attributeValueEClass, AttributeValue.class, "AttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAttributeValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, AttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttributeValue_EnumValues(), theChannelTypesPackage.getEnumLiteral(), null, "enumValues", null, 0, -1, AttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttributeValue_Attribute(), theChannelTypesPackage.getAttribute(), null, "attribute", null, 1, 1, AttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttributeValue_Type(), theChannelTypesPackage.getAttributeType(), "type", null, 0, 1, AttributeValue.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        op = addEOperation(attributeValueEClass, ecorePackage.getEString(), "getResolvedValue", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEBoolean(), "useDefault", 0, 1, IS_UNIQUE, IS_ORDERED);

        addEOperation(attributeValueEClass, ecorePackage.getEString(), "getAttributeName", 0, 1, IS_UNIQUE, IS_ORDERED);

        initEClass(typeAssociationEClass, TypeAssociation.class, "TypeAssociation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getTypeAssociation_ChannelType(), theChannelTypesPackage.getChannelType(), null, "channelType", null, 0, 1, TypeAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTypeAssociation_AttributeValues(), this.getAttributeValue(), null, "attributeValues", null, 0, -1, TypeAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTypeAssociation_ExtendedAttributes(), this.getExtendedAttribute(), null, "extendedAttributes", null, 0, -1, TypeAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTypeAssociation_Channel(), this.getChannel(), null, "channel", null, 0, 1, TypeAssociation.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getTypeAssociation_DerivedId(), ecorePackage.getEString(), "derivedId", "", 0, 1, TypeAssociation.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(extendedAttributeEClass, ExtendedAttribute.class, "ExtendedAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getExtendedAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, ExtendedAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getExtendedAttribute_Value(), ecorePackage.getEString(), "value", null, 0, 1, ExtendedAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
    }

} //ChannelsPackageImpl
