/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.channeltype.impl;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.n2.common.channeltype.ChannelType;
import com.tibco.n2.common.channeltype.ChanneltypeFactory;
import com.tibco.n2.common.channeltype.ChanneltypePackage;
import com.tibco.n2.common.channeltype.ImplementationType;
import com.tibco.n2.common.channeltype.PresentationType;
import com.tibco.n2.wp.archive.service.WPPackage;
import com.tibco.n2.wp.archive.service.impl.WPPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class ChanneltypePackageImpl extends EPackageImpl implements
        ChanneltypePackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum channelTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum implementationTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum presentationTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType channelTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType implementationTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType presentationTypeObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
     * package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory
     * method {@link #init init()}, which also performs initialization of the
     * package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.n2.common.channeltype.ChanneltypePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ChanneltypePackageImpl() {
        super(eNS_URI, ChanneltypeFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link ChanneltypePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ChanneltypePackage init() {
        if (isInited) return (ChanneltypePackage)EPackage.Registry.INSTANCE.getEPackage(ChanneltypePackage.eNS_URI);

        // Obtain or create and register package
        ChanneltypePackageImpl theChanneltypePackage = (ChanneltypePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ChanneltypePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ChanneltypePackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        WPPackageImpl theWPPackage = (WPPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WPPackage.eNS_URI) instanceof WPPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WPPackage.eNS_URI) : WPPackage.eINSTANCE);

        // Create package meta-data objects
        theChanneltypePackage.createPackageContents();
        theWPPackage.createPackageContents();

        // Initialize created meta-data
        theChanneltypePackage.initializePackageContents();
        theWPPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theChanneltypePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ChanneltypePackage.eNS_URI, theChanneltypePackage);
        return theChanneltypePackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getChannelType() {
        return channelTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getImplementationType() {
        return implementationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getPresentationType() {
        return presentationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getChannelTypeObject() {
        return channelTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getImplementationTypeObject() {
        return implementationTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getPresentationTypeObject() {
        return presentationTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ChanneltypeFactory getChanneltypeFactory() {
        return (ChanneltypeFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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

        // Create enums
        channelTypeEEnum = createEEnum(CHANNEL_TYPE);
        implementationTypeEEnum = createEEnum(IMPLEMENTATION_TYPE);
        presentationTypeEEnum = createEEnum(PRESENTATION_TYPE);

        // Create data types
        channelTypeObjectEDataType = createEDataType(CHANNEL_TYPE_OBJECT);
        implementationTypeObjectEDataType = createEDataType(IMPLEMENTATION_TYPE_OBJECT);
        presentationTypeObjectEDataType = createEDataType(PRESENTATION_TYPE_OBJECT);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This
     * method is guarded to have no affect on any invocation but its first. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Initialize enums and add enum literals
        initEEnum(channelTypeEEnum, ChannelType.class, "ChannelType");
        addEEnumLiteral(channelTypeEEnum, ChannelType.JSP_CHANNEL);
        addEEnumLiteral(channelTypeEEnum, ChannelType.GI_CHANNEL);
        addEEnumLiteral(channelTypeEEnum, ChannelType.PAGEFLOW_CHANNEL);
        addEEnumLiteral(channelTypeEEnum, ChannelType.EMAIL_CHANNEL);
        addEEnumLiteral(channelTypeEEnum, ChannelType.RSS_CHANNEL);
        addEEnumLiteral(channelTypeEEnum, ChannelType.OPENSPACE_CHANNEL);
        addEEnumLiteral(channelTypeEEnum, ChannelType.MOBILE_CHANNEL);

        initEEnum(implementationTypeEEnum, ImplementationType.class, "ImplementationType");
        addEEnumLiteral(implementationTypeEEnum, ImplementationType.PULL);
        addEEnumLiteral(implementationTypeEEnum, ImplementationType.PUSH);

        initEEnum(presentationTypeEEnum, PresentationType.class, "PresentationType");
        addEEnumLiteral(presentationTypeEEnum, PresentationType.JSP);
        addEEnumLiteral(presentationTypeEEnum, PresentationType.GI);
        addEEnumLiteral(presentationTypeEEnum, PresentationType.PAGEFLOW);
        addEEnumLiteral(presentationTypeEEnum, PresentationType.EMAIL);
        addEEnumLiteral(presentationTypeEEnum, PresentationType.RSS);
        addEEnumLiteral(presentationTypeEEnum, PresentationType.GWT);
        addEEnumLiteral(presentationTypeEEnum, PresentationType.IPHONE);

        // Initialize data types
        initEDataType(channelTypeObjectEDataType, ChannelType.class, "ChannelTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(implementationTypeObjectEDataType, ImplementationType.class, "ImplementationTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(presentationTypeObjectEDataType, PresentationType.class, "PresentationTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for
     * <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
        addAnnotation
          (channelTypeEEnum, 
           source, 
           new String[] {
             "name", "ChannelType"
           });		
        addAnnotation
          (channelTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "ChannelType:Object",
             "baseType", "ChannelType"
           });		
        addAnnotation
          (implementationTypeEEnum, 
           source, 
           new String[] {
             "name", "ImplementationType"
           });		
        addAnnotation
          (implementationTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "ImplementationType:Object",
             "baseType", "ImplementationType"
           });		
        addAnnotation
          (presentationTypeEEnum, 
           source, 
           new String[] {
             "name", "PresentationType"
           });		
        addAnnotation
          (presentationTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "PresentationType:Object",
             "baseType", "PresentationType"
           });
    }

} // ChanneltypePackageImpl
