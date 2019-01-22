/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.impl;

import com.tibco.xpd.presentation.channels.ChannelsPackage;

import com.tibco.xpd.presentation.channels.impl.ChannelsPackageImpl;

import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.ChannelTypes;
import com.tibco.xpd.presentation.channeltypes.ChannelTypesFactory;
import com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage;
import com.tibco.xpd.presentation.channeltypes.EnumLiteral;
import com.tibco.xpd.presentation.channeltypes.Implementation;
import com.tibco.xpd.presentation.channeltypes.ModelElement;
import com.tibco.xpd.presentation.channeltypes.NamedElement;
import com.tibco.xpd.presentation.channeltypes.Presentation;
import com.tibco.xpd.presentation.channeltypes.Target;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
public class ChannelTypesPackageImpl extends EPackageImpl implements ChannelTypesPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass targetEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass presentationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass implementationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attributeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelTypesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelDestinationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modelElementEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass namedElementEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass enumLiteralEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum attributeTypeEEnum = null;

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
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ChannelTypesPackageImpl() {
        super(eNS_URI, ChannelTypesFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link ChannelTypesPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ChannelTypesPackage init() {
        if (isInited) return (ChannelTypesPackage)EPackage.Registry.INSTANCE.getEPackage(ChannelTypesPackage.eNS_URI);

        // Obtain or create and register package
        ChannelTypesPackageImpl theChannelTypesPackage = (ChannelTypesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ChannelTypesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ChannelTypesPackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        ChannelsPackageImpl theChannelsPackage = (ChannelsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelsPackage.eNS_URI) instanceof ChannelsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelsPackage.eNS_URI) : ChannelsPackage.eINSTANCE);

        // Create package meta-data objects
        theChannelTypesPackage.createPackageContents();
        theChannelsPackage.createPackageContents();

        // Initialize created meta-data
        theChannelTypesPackage.initializePackageContents();
        theChannelsPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theChannelTypesPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ChannelTypesPackage.eNS_URI, theChannelTypesPackage);
        return theChannelTypesPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTarget() {
        return targetEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTarget_Bindings() {
        return (EReference)targetEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPresentation() {
        return presentationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPresentation_Bindings() {
        return (EReference)presentationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getImplementation() {
        return implementationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getImplementation_Bindings() {
        return (EReference)implementationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttribute() {
        return attributeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Type() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttribute_EnumLiterals() {
        return (EReference)attributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttribute_DefaultEnumSet() {
        return (EReference)attributeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_DefaultValue() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Required() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannelTypes() {
        return channelTypesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelTypes_Targets() {
        return (EReference)channelTypesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelTypes_Presentations() {
        return (EReference)channelTypesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelTypes_Implementations() {
        return (EReference)channelTypesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelTypes_ChannelTypes() {
        return (EReference)channelTypesEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelTypes_ChannelDestinations() {
        return (EReference)channelTypesEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannelDestination() {
        return channelDestinationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelDestination_ChannelTypes() {
        return (EReference)channelDestinationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getModelElement() {
        return modelElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getModelElement_Id() {
        return (EAttribute)modelElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNamedElement() {
        return namedElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_Name() {
        return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_DisplayName() {
        return (EAttribute)namedElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_Description() {
        return (EAttribute)namedElementEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnumLiteral() {
        return enumLiteralEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannelType() {
        return channelTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_Target() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_Presentation() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_Implementation() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_Attributes() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_RuntimeVersion() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_Destinations() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAttributeType() {
        return attributeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelTypesFactory getChannelTypesFactory() {
        return (ChannelTypesFactory)getEFactoryInstance();
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
        targetEClass = createEClass(TARGET);
        createEReference(targetEClass, TARGET__BINDINGS);

        presentationEClass = createEClass(PRESENTATION);
        createEReference(presentationEClass, PRESENTATION__BINDINGS);

        implementationEClass = createEClass(IMPLEMENTATION);
        createEReference(implementationEClass, IMPLEMENTATION__BINDINGS);

        attributeEClass = createEClass(ATTRIBUTE);
        createEAttribute(attributeEClass, ATTRIBUTE__TYPE);
        createEReference(attributeEClass, ATTRIBUTE__ENUM_LITERALS);
        createEReference(attributeEClass, ATTRIBUTE__DEFAULT_ENUM_SET);
        createEAttribute(attributeEClass, ATTRIBUTE__DEFAULT_VALUE);
        createEAttribute(attributeEClass, ATTRIBUTE__REQUIRED);

        modelElementEClass = createEClass(MODEL_ELEMENT);
        createEAttribute(modelElementEClass, MODEL_ELEMENT__ID);

        namedElementEClass = createEClass(NAMED_ELEMENT);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__DISPLAY_NAME);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__DESCRIPTION);

        enumLiteralEClass = createEClass(ENUM_LITERAL);

        channelTypeEClass = createEClass(CHANNEL_TYPE);
        createEReference(channelTypeEClass, CHANNEL_TYPE__TARGET);
        createEReference(channelTypeEClass, CHANNEL_TYPE__PRESENTATION);
        createEReference(channelTypeEClass, CHANNEL_TYPE__IMPLEMENTATION);
        createEReference(channelTypeEClass, CHANNEL_TYPE__ATTRIBUTES);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__RUNTIME_VERSION);
        createEReference(channelTypeEClass, CHANNEL_TYPE__DESTINATIONS);

        channelTypesEClass = createEClass(CHANNEL_TYPES);
        createEReference(channelTypesEClass, CHANNEL_TYPES__TARGETS);
        createEReference(channelTypesEClass, CHANNEL_TYPES__PRESENTATIONS);
        createEReference(channelTypesEClass, CHANNEL_TYPES__IMPLEMENTATIONS);
        createEReference(channelTypesEClass, CHANNEL_TYPES__CHANNEL_TYPES);
        createEReference(channelTypesEClass, CHANNEL_TYPES__CHANNEL_DESTINATIONS);

        channelDestinationEClass = createEClass(CHANNEL_DESTINATION);
        createEReference(channelDestinationEClass, CHANNEL_DESTINATION__CHANNEL_TYPES);

        // Create enums
        attributeTypeEEnum = createEEnum(ATTRIBUTE_TYPE);
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

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        targetEClass.getESuperTypes().add(this.getNamedElement());
        presentationEClass.getESuperTypes().add(this.getNamedElement());
        implementationEClass.getESuperTypes().add(this.getNamedElement());
        attributeEClass.getESuperTypes().add(this.getNamedElement());
        namedElementEClass.getESuperTypes().add(this.getModelElement());
        enumLiteralEClass.getESuperTypes().add(this.getNamedElement());
        channelTypeEClass.getESuperTypes().add(this.getNamedElement());
        channelDestinationEClass.getESuperTypes().add(this.getNamedElement());

        // Initialize classes and features; add operations and parameters
        initEClass(targetEClass, Target.class, "Target", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getTarget_Bindings(), this.getChannelType(), this.getChannelType_Target(), "bindings", null, 0, -1, Target.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(presentationEClass, Presentation.class, "Presentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPresentation_Bindings(), this.getChannelType(), this.getChannelType_Presentation(), "bindings", null, 0, -1, Presentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(implementationEClass, Implementation.class, "Implementation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getImplementation_Bindings(), this.getChannelType(), this.getChannelType_Implementation(), "bindings", null, 0, -1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAttribute_Type(), this.getAttributeType(), "type", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttribute_EnumLiterals(), this.getEnumLiteral(), null, "enumLiterals", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttribute_DefaultEnumSet(), this.getEnumLiteral(), null, "defaultEnumSet", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Required(), ecorePackage.getEBoolean(), "required", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        addEOperation(attributeEClass, ecorePackage.getEString(), "getResolvedDefaultValue", 0, 1, IS_UNIQUE, IS_ORDERED);

        initEClass(modelElementEClass, ModelElement.class, "ModelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getModelElement_Id(), ecorePackage.getEString(), "id", null, 1, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedElement_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedElement_Description(), ecorePackage.getEString(), "description", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(enumLiteralEClass, EnumLiteral.class, "EnumLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(channelTypeEClass, ChannelType.class, "ChannelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getChannelType_Target(), this.getTarget(), this.getTarget_Bindings(), "target", null, 1, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_Presentation(), this.getPresentation(), this.getPresentation_Bindings(), "presentation", null, 1, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_Implementation(), this.getImplementation(), this.getImplementation_Bindings(), "implementation", null, 1, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_RuntimeVersion(), ecorePackage.getEString(), "runtimeVersion", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_Destinations(), this.getChannelDestination(), this.getChannelDestination_ChannelTypes(), "destinations", null, 0, -1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(channelTypesEClass, ChannelTypes.class, "ChannelTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getChannelTypes_Targets(), this.getTarget(), null, "targets", null, 0, -1, ChannelTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelTypes_Presentations(), this.getPresentation(), null, "presentations", null, 0, -1, ChannelTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelTypes_Implementations(), this.getImplementation(), null, "implementations", null, 0, -1, ChannelTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelTypes_ChannelTypes(), this.getChannelType(), null, "channelTypes", null, 0, -1, ChannelTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelTypes_ChannelDestinations(), this.getChannelDestination(), null, "channelDestinations", null, 0, -1, ChannelTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        EOperation op = addEOperation(channelTypesEClass, this.getChannelType(), "getChannelType", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "channelTypeId", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(channelTypesEClass, this.getTarget(), "getTarget", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "targetId", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(channelTypesEClass, this.getPresentation(), "getPresentation", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "presentationId", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(channelTypesEClass, this.getImplementation(), "getImplementation", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "implementationId", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(channelTypesEClass, this.getChannelType(), "getChannelTypes", 0, -1, IS_UNIQUE, !IS_ORDERED);
        addEParameter(op, this.getChannelDestination(), "destinations", 0, -1, IS_UNIQUE, !IS_ORDERED);

        initEClass(channelDestinationEClass, ChannelDestination.class, "ChannelDestination", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getChannelDestination_ChannelTypes(), this.getChannelType(), this.getChannelType_Destinations(), "channelTypes", null, 0, -1, ChannelDestination.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(attributeTypeEEnum, AttributeType.class, "AttributeType");
        addEEnumLiteral(attributeTypeEEnum, AttributeType.TEXT);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.DECIMAL);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.INTEGER);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.BOOLEAN);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.ENUM);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.ENUM_SET);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.RESOURCE);

        // Create resource
        createResource(eNS_URI);
    }

} //ChannelTypesPackageImpl
