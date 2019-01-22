/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.common.channeltype.ImplementationType;
import com.tibco.n2.common.channeltype.PresentationType;
import com.tibco.n2.wp.archive.service.BusinessServiceType;
import com.tibco.n2.wp.archive.service.ChannelExtentionType;
import com.tibco.n2.wp.archive.service.ChannelExtention;
import com.tibco.n2.wp.archive.service.ChannelType;
import com.tibco.n2.wp.archive.service.ExtendedPropertiesType;
import com.tibco.n2.wp.archive.service.PageFlowType;
import com.tibco.n2.wp.archive.service.WPPackage;
import com.tibco.n2.wp.archive.service.WorkTypeType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Channel Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getTargetChannelType <em>Target Channel Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getPresentationChannelType <em>Presentation Channel Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getImplementationType <em>Implementation Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getWorkType <em>Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getExtendedProperties <em>Extended Properties</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getExtensionConfig <em>Extension Config</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getBusinessService <em>Business Service</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getPageFlow <em>Page Flow</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getChannelId <em>Channel Id</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#isDefaultChannel <em>Default Channel</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChannelTypeImpl extends EObjectImpl implements ChannelType {
    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * This is true if the Description attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean descriptionESet;

    /**
     * The default value of the '{@link #getTargetChannelType() <em>Target Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTargetChannelType()
     * @generated
     * @ordered
     */
    protected static final com.tibco.n2.common.channeltype.ChannelType TARGET_CHANNEL_TYPE_EDEFAULT = com.tibco.n2.common.channeltype.ChannelType.JSP_CHANNEL;

    /**
     * The cached value of the '{@link #getTargetChannelType() <em>Target Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTargetChannelType()
     * @generated
     * @ordered
     */
    protected com.tibco.n2.common.channeltype.ChannelType targetChannelType = TARGET_CHANNEL_TYPE_EDEFAULT;

    /**
     * This is true if the Target Channel Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean targetChannelTypeESet;

    /**
     * The default value of the '{@link #getPresentationChannelType() <em>Presentation Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPresentationChannelType()
     * @generated
     * @ordered
     */
    protected static final PresentationType PRESENTATION_CHANNEL_TYPE_EDEFAULT = PresentationType.JSP;

    /**
     * The cached value of the '{@link #getPresentationChannelType() <em>Presentation Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPresentationChannelType()
     * @generated
     * @ordered
     */
    protected PresentationType presentationChannelType = PRESENTATION_CHANNEL_TYPE_EDEFAULT;

    /**
     * This is true if the Presentation Channel Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean presentationChannelTypeESet;

    /**
     * The default value of the '{@link #getImplementationType() <em>Implementation Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementationType()
     * @generated
     * @ordered
     */
    protected static final ImplementationType IMPLEMENTATION_TYPE_EDEFAULT = ImplementationType.PULL;

    /**
     * The cached value of the '{@link #getImplementationType() <em>Implementation Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementationType()
     * @generated
     * @ordered
     */
    protected ImplementationType implementationType = IMPLEMENTATION_TYPE_EDEFAULT;

    /**
     * This is true if the Implementation Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean implementationTypeESet;

    /**
     * The cached value of the '{@link #getWorkType() <em>Work Type</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkType()
     * @generated
     * @ordered
     */
    protected EList<WorkTypeType> workType;

    /**
     * The default value of the '{@link #getDomain() <em>Domain</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDomain()
     * @generated
     * @ordered
     */
    protected static final String DOMAIN_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDomain() <em>Domain</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDomain()
     * @generated
     * @ordered
     */
    protected String domain = DOMAIN_EDEFAULT;

    /**
     * The cached value of the '{@link #getExtendedProperties() <em>Extended Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedProperties()
     * @generated
     * @ordered
     */
    protected ExtendedPropertiesType extendedProperties;

    /**
     * This is true if the Extended Properties containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean extendedPropertiesESet;

    /**
     * The cached value of the '{@link #getExtensionConfig() <em>Extension Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtensionConfig()
     * @generated
     * @ordered
     */
    protected ChannelExtentionType extensionConfig;

    /**
     * This is true if the Extension Config containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean extensionConfigESet;

    /**
     * The cached value of the '{@link #getBusinessService() <em>Business Service</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBusinessService()
     * @generated
     * @ordered
     */
    protected EList<BusinessServiceType> businessService;

    /**
     * The cached value of the '{@link #getPageFlow() <em>Page Flow</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPageFlow()
     * @generated
     * @ordered
     */
    protected EList<PageFlowType> pageFlow;

    /**
     * The default value of the '{@link #getChannelId() <em>Channel Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChannelId()
     * @generated
     * @ordered
     */
    protected static final String CHANNEL_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChannelId() <em>Channel Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChannelId()
     * @generated
     * @ordered
     */
    protected String channelId = CHANNEL_ID_EDEFAULT;

    /**
     * The default value of the '{@link #isDefaultChannel() <em>Default Channel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDefaultChannel()
     * @generated
     * @ordered
     */
    protected static final boolean DEFAULT_CHANNEL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDefaultChannel() <em>Default Channel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDefaultChannel()
     * @generated
     * @ordered
     */
    protected boolean defaultChannel = DEFAULT_CHANNEL_EDEFAULT;

    /**
     * This is true if the Default Channel attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean defaultChannelESet;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ChannelTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WPPackage.Literals.CHANNEL_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        boolean oldDescriptionESet = descriptionESet;
        descriptionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDescription() {
        String oldDescription = description;
        boolean oldDescriptionESet = descriptionESet;
        description = DESCRIPTION_EDEFAULT;
        descriptionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDescription() {
        return descriptionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.n2.common.channeltype.ChannelType getTargetChannelType() {
        return targetChannelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTargetChannelType(com.tibco.n2.common.channeltype.ChannelType newTargetChannelType) {
        com.tibco.n2.common.channeltype.ChannelType oldTargetChannelType = targetChannelType;
        targetChannelType = newTargetChannelType == null ? TARGET_CHANNEL_TYPE_EDEFAULT : newTargetChannelType;
        boolean oldTargetChannelTypeESet = targetChannelTypeESet;
        targetChannelTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__TARGET_CHANNEL_TYPE, oldTargetChannelType, targetChannelType, !oldTargetChannelTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetTargetChannelType() {
        com.tibco.n2.common.channeltype.ChannelType oldTargetChannelType = targetChannelType;
        boolean oldTargetChannelTypeESet = targetChannelTypeESet;
        targetChannelType = TARGET_CHANNEL_TYPE_EDEFAULT;
        targetChannelTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__TARGET_CHANNEL_TYPE, oldTargetChannelType, TARGET_CHANNEL_TYPE_EDEFAULT, oldTargetChannelTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTargetChannelType() {
        return targetChannelTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PresentationType getPresentationChannelType() {
        return presentationChannelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPresentationChannelType(PresentationType newPresentationChannelType) {
        PresentationType oldPresentationChannelType = presentationChannelType;
        presentationChannelType = newPresentationChannelType == null ? PRESENTATION_CHANNEL_TYPE_EDEFAULT : newPresentationChannelType;
        boolean oldPresentationChannelTypeESet = presentationChannelTypeESet;
        presentationChannelTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE, oldPresentationChannelType, presentationChannelType, !oldPresentationChannelTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPresentationChannelType() {
        PresentationType oldPresentationChannelType = presentationChannelType;
        boolean oldPresentationChannelTypeESet = presentationChannelTypeESet;
        presentationChannelType = PRESENTATION_CHANNEL_TYPE_EDEFAULT;
        presentationChannelTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE, oldPresentationChannelType, PRESENTATION_CHANNEL_TYPE_EDEFAULT, oldPresentationChannelTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPresentationChannelType() {
        return presentationChannelTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ImplementationType getImplementationType() {
        return implementationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setImplementationType(ImplementationType newImplementationType) {
        ImplementationType oldImplementationType = implementationType;
        implementationType = newImplementationType == null ? IMPLEMENTATION_TYPE_EDEFAULT : newImplementationType;
        boolean oldImplementationTypeESet = implementationTypeESet;
        implementationTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__IMPLEMENTATION_TYPE, oldImplementationType, implementationType, !oldImplementationTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetImplementationType() {
        ImplementationType oldImplementationType = implementationType;
        boolean oldImplementationTypeESet = implementationTypeESet;
        implementationType = IMPLEMENTATION_TYPE_EDEFAULT;
        implementationTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__IMPLEMENTATION_TYPE, oldImplementationType, IMPLEMENTATION_TYPE_EDEFAULT, oldImplementationTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetImplementationType() {
        return implementationTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkTypeType> getWorkType() {
        if (workType == null) {
            workType = new EObjectContainmentEList<WorkTypeType>(WorkTypeType.class, this, WPPackage.CHANNEL_TYPE__WORK_TYPE);
        }
        return workType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDomain() {
        return domain;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDomain(String newDomain) {
        String oldDomain = domain;
        domain = newDomain;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__DOMAIN, oldDomain, domain));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExtendedPropertiesType getExtendedProperties() {
        return extendedProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExtendedProperties(ExtendedPropertiesType newExtendedProperties, NotificationChain msgs) {
        ExtendedPropertiesType oldExtendedProperties = extendedProperties;
        extendedProperties = newExtendedProperties;
        boolean oldExtendedPropertiesESet = extendedPropertiesESet;
        extendedPropertiesESet = true;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES, oldExtendedProperties, newExtendedProperties, !oldExtendedPropertiesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExtendedProperties(ExtendedPropertiesType newExtendedProperties) {
        if (newExtendedProperties != extendedProperties) {
            NotificationChain msgs = null;
            if (extendedProperties != null)
                msgs = ((InternalEObject)extendedProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES, null, msgs);
            if (newExtendedProperties != null)
                msgs = ((InternalEObject)newExtendedProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES, null, msgs);
            msgs = basicSetExtendedProperties(newExtendedProperties, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else {
            boolean oldExtendedPropertiesESet = extendedPropertiesESet;
            extendedPropertiesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES, newExtendedProperties, newExtendedProperties, !oldExtendedPropertiesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetExtendedProperties(NotificationChain msgs) {
        ExtendedPropertiesType oldExtendedProperties = extendedProperties;
        extendedProperties = null;
        boolean oldExtendedPropertiesESet = extendedPropertiesESet;
        extendedPropertiesESet = false;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES, oldExtendedProperties, null, oldExtendedPropertiesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExtendedProperties() {
        if (extendedProperties != null) {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)extendedProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES, null, msgs);
            msgs = basicUnsetExtendedProperties(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else {
            boolean oldExtendedPropertiesESet = extendedPropertiesESet;
            extendedPropertiesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES, null, null, oldExtendedPropertiesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExtendedProperties() {
        return extendedPropertiesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelExtentionType getExtensionConfig() {
        return extensionConfig;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExtensionConfig(ChannelExtentionType newExtensionConfig, NotificationChain msgs) {
        ChannelExtentionType oldExtensionConfig = extensionConfig;
        extensionConfig = newExtensionConfig;
        boolean oldExtensionConfigESet = extensionConfigESet;
        extensionConfigESet = true;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG, oldExtensionConfig, newExtensionConfig, !oldExtensionConfigESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExtensionConfig(ChannelExtentionType newExtensionConfig) {
        if (newExtensionConfig != extensionConfig) {
            NotificationChain msgs = null;
            if (extensionConfig != null)
                msgs = ((InternalEObject)extensionConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG, null, msgs);
            if (newExtensionConfig != null)
                msgs = ((InternalEObject)newExtensionConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG, null, msgs);
            msgs = basicSetExtensionConfig(newExtensionConfig, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else {
            boolean oldExtensionConfigESet = extensionConfigESet;
            extensionConfigESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG, newExtensionConfig, newExtensionConfig, !oldExtensionConfigESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetExtensionConfig(NotificationChain msgs) {
        ChannelExtentionType oldExtensionConfig = extensionConfig;
        extensionConfig = null;
        boolean oldExtensionConfigESet = extensionConfigESet;
        extensionConfigESet = false;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG, oldExtensionConfig, null, oldExtensionConfigESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExtensionConfig() {
        if (extensionConfig != null) {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)extensionConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG, null, msgs);
            msgs = basicUnsetExtensionConfig(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else {
            boolean oldExtensionConfigESet = extensionConfigESet;
            extensionConfigESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG, null, null, oldExtensionConfigESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExtensionConfig() {
        return extensionConfigESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<BusinessServiceType> getBusinessService() {
        if (businessService == null) {
            businessService = new EObjectContainmentEList<BusinessServiceType>(BusinessServiceType.class, this, WPPackage.CHANNEL_TYPE__BUSINESS_SERVICE);
        }
        return businessService;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PageFlowType> getPageFlow() {
        if (pageFlow == null) {
            pageFlow = new EObjectContainmentEList<PageFlowType>(PageFlowType.class, this, WPPackage.CHANNEL_TYPE__PAGE_FLOW);
        }
        return pageFlow;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setChannelId(String newChannelId) {
        String oldChannelId = channelId;
        channelId = newChannelId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__CHANNEL_ID, oldChannelId, channelId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDefaultChannel() {
        return defaultChannel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultChannel(boolean newDefaultChannel) {
        boolean oldDefaultChannel = defaultChannel;
        defaultChannel = newDefaultChannel;
        boolean oldDefaultChannelESet = defaultChannelESet;
        defaultChannelESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__DEFAULT_CHANNEL, oldDefaultChannel, defaultChannel, !oldDefaultChannelESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDefaultChannel() {
        boolean oldDefaultChannel = defaultChannel;
        boolean oldDefaultChannelESet = defaultChannelESet;
        defaultChannel = DEFAULT_CHANNEL_EDEFAULT;
        defaultChannelESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.CHANNEL_TYPE__DEFAULT_CHANNEL, oldDefaultChannel, DEFAULT_CHANNEL_EDEFAULT, oldDefaultChannelESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDefaultChannel() {
        return defaultChannelESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.CHANNEL_TYPE__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case WPPackage.CHANNEL_TYPE__WORK_TYPE:
                return ((InternalEList<?>)getWorkType()).basicRemove(otherEnd, msgs);
            case WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES:
                return basicUnsetExtendedProperties(msgs);
            case WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG:
                return basicUnsetExtensionConfig(msgs);
            case WPPackage.CHANNEL_TYPE__BUSINESS_SERVICE:
                return ((InternalEList<?>)getBusinessService()).basicRemove(otherEnd, msgs);
            case WPPackage.CHANNEL_TYPE__PAGE_FLOW:
                return ((InternalEList<?>)getPageFlow()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case WPPackage.CHANNEL_TYPE__DESCRIPTION:
                return getDescription();
            case WPPackage.CHANNEL_TYPE__TARGET_CHANNEL_TYPE:
                return getTargetChannelType();
            case WPPackage.CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE:
                return getPresentationChannelType();
            case WPPackage.CHANNEL_TYPE__IMPLEMENTATION_TYPE:
                return getImplementationType();
            case WPPackage.CHANNEL_TYPE__WORK_TYPE:
                return getWorkType();
            case WPPackage.CHANNEL_TYPE__DOMAIN:
                return getDomain();
            case WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES:
                return getExtendedProperties();
            case WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG:
                return getExtensionConfig();
            case WPPackage.CHANNEL_TYPE__BUSINESS_SERVICE:
                return getBusinessService();
            case WPPackage.CHANNEL_TYPE__PAGE_FLOW:
                return getPageFlow();
            case WPPackage.CHANNEL_TYPE__CHANNEL_ID:
                return getChannelId();
            case WPPackage.CHANNEL_TYPE__DEFAULT_CHANNEL:
                return isDefaultChannel();
            case WPPackage.CHANNEL_TYPE__NAME:
                return getName();
            case WPPackage.CHANNEL_TYPE__VERSION:
                return getVersion();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case WPPackage.CHANNEL_TYPE__DESCRIPTION:
                setDescription((String)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__TARGET_CHANNEL_TYPE:
                setTargetChannelType((com.tibco.n2.common.channeltype.ChannelType)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE:
                setPresentationChannelType((PresentationType)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__IMPLEMENTATION_TYPE:
                setImplementationType((ImplementationType)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__WORK_TYPE:
                getWorkType().clear();
                getWorkType().addAll((Collection<? extends WorkTypeType>)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__DOMAIN:
                setDomain((String)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES:
                setExtendedProperties((ExtendedPropertiesType)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG:
                setExtensionConfig((ChannelExtentionType)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__BUSINESS_SERVICE:
                getBusinessService().clear();
                getBusinessService().addAll((Collection<? extends BusinessServiceType>)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__PAGE_FLOW:
                getPageFlow().clear();
                getPageFlow().addAll((Collection<? extends PageFlowType>)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__CHANNEL_ID:
                setChannelId((String)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__DEFAULT_CHANNEL:
                setDefaultChannel((Boolean)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__NAME:
                setName((String)newValue);
                return;
            case WPPackage.CHANNEL_TYPE__VERSION:
                setVersion((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case WPPackage.CHANNEL_TYPE__DESCRIPTION:
                unsetDescription();
                return;
            case WPPackage.CHANNEL_TYPE__TARGET_CHANNEL_TYPE:
                unsetTargetChannelType();
                return;
            case WPPackage.CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE:
                unsetPresentationChannelType();
                return;
            case WPPackage.CHANNEL_TYPE__IMPLEMENTATION_TYPE:
                unsetImplementationType();
                return;
            case WPPackage.CHANNEL_TYPE__WORK_TYPE:
                getWorkType().clear();
                return;
            case WPPackage.CHANNEL_TYPE__DOMAIN:
                setDomain(DOMAIN_EDEFAULT);
                return;
            case WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES:
                unsetExtendedProperties();
                return;
            case WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG:
                unsetExtensionConfig();
                return;
            case WPPackage.CHANNEL_TYPE__BUSINESS_SERVICE:
                getBusinessService().clear();
                return;
            case WPPackage.CHANNEL_TYPE__PAGE_FLOW:
                getPageFlow().clear();
                return;
            case WPPackage.CHANNEL_TYPE__CHANNEL_ID:
                setChannelId(CHANNEL_ID_EDEFAULT);
                return;
            case WPPackage.CHANNEL_TYPE__DEFAULT_CHANNEL:
                unsetDefaultChannel();
                return;
            case WPPackage.CHANNEL_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case WPPackage.CHANNEL_TYPE__VERSION:
                setVersion(VERSION_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case WPPackage.CHANNEL_TYPE__DESCRIPTION:
                return isSetDescription();
            case WPPackage.CHANNEL_TYPE__TARGET_CHANNEL_TYPE:
                return isSetTargetChannelType();
            case WPPackage.CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE:
                return isSetPresentationChannelType();
            case WPPackage.CHANNEL_TYPE__IMPLEMENTATION_TYPE:
                return isSetImplementationType();
            case WPPackage.CHANNEL_TYPE__WORK_TYPE:
                return workType != null && !workType.isEmpty();
            case WPPackage.CHANNEL_TYPE__DOMAIN:
                return DOMAIN_EDEFAULT == null ? domain != null : !DOMAIN_EDEFAULT.equals(domain);
            case WPPackage.CHANNEL_TYPE__EXTENDED_PROPERTIES:
                return isSetExtendedProperties();
            case WPPackage.CHANNEL_TYPE__EXTENSION_CONFIG:
                return isSetExtensionConfig();
            case WPPackage.CHANNEL_TYPE__BUSINESS_SERVICE:
                return businessService != null && !businessService.isEmpty();
            case WPPackage.CHANNEL_TYPE__PAGE_FLOW:
                return pageFlow != null && !pageFlow.isEmpty();
            case WPPackage.CHANNEL_TYPE__CHANNEL_ID:
                return CHANNEL_ID_EDEFAULT == null ? channelId != null : !CHANNEL_ID_EDEFAULT.equals(channelId);
            case WPPackage.CHANNEL_TYPE__DEFAULT_CHANNEL:
                return isSetDefaultChannel();
            case WPPackage.CHANNEL_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case WPPackage.CHANNEL_TYPE__VERSION:
                return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (description: ");
        if (descriptionESet) result.append(description); else result.append("<unset>");
        result.append(", targetChannelType: ");
        if (targetChannelTypeESet) result.append(targetChannelType); else result.append("<unset>");
        result.append(", presentationChannelType: ");
        if (presentationChannelTypeESet) result.append(presentationChannelType); else result.append("<unset>");
        result.append(", implementationType: ");
        if (implementationTypeESet) result.append(implementationType); else result.append("<unset>");
        result.append(", domain: ");
        result.append(domain);
        result.append(", channelId: ");
        result.append(channelId);
        result.append(", defaultChannel: ");
        if (defaultChannelESet) result.append(defaultChannel); else result.append("<unset>");
        result.append(", name: ");
        result.append(name);
        result.append(", version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //ChannelTypeImpl
