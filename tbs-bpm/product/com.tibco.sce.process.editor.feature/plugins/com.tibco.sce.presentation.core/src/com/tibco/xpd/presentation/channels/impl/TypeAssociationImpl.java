/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels.impl;

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

import com.tibco.xpd.presentation.channels.AttributeValue;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.ExtendedAttribute;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Type Association</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl#getChannelType <em>Channel Type</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.TypeAssociationImpl#getDerivedId <em>Derived Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TypeAssociationImpl extends EObjectImpl implements TypeAssociation {
    /**
     * The cached value of the '{@link #getChannelType() <em>Channel Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getChannelType()
     * @generated
     * @ordered
     */
    protected ChannelType channelType;

    /**
     * The cached value of the '{@link #getAttributeValues()
     * <em>Attribute Values</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAttributeValues()
     * @generated
     * @ordered
     */
    protected EList<AttributeValue> attributeValues;

    /**
     * The cached value of the '{@link #getExtendedAttributes()
     * <em>Extended Attributes</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The default value of the '{@link #getDerivedId() <em>Derived Id</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDerivedId()
     * @generated
     * @ordered
     */
    protected static final String DERIVED_ID_EDEFAULT = "";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected TypeAssociationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChannelsPackage.Literals.TYPE_ASSOCIATION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ChannelType getChannelType() {
        if (channelType != null && channelType.eIsProxy()) {
            InternalEObject oldChannelType = (InternalEObject)channelType;
            channelType = (ChannelType)eResolveProxy(oldChannelType);
            if (channelType != oldChannelType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelsPackage.TYPE_ASSOCIATION__CHANNEL_TYPE, oldChannelType, channelType));
            }
        }
        return channelType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ChannelType basicGetChannelType() {
        return channelType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setChannelType(ChannelType newChannelType) {
        ChannelType oldChannelType = channelType;
        channelType = newChannelType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelsPackage.TYPE_ASSOCIATION__CHANNEL_TYPE, oldChannelType, channelType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<AttributeValue> getAttributeValues() {
        if (attributeValues == null) {
            attributeValues = new EObjectContainmentEList<AttributeValue>(AttributeValue.class, this, ChannelsPackage.TYPE_ASSOCIATION__ATTRIBUTE_VALUES);
        }
        return attributeValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this, ChannelsPackage.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Channel getChannel() {
        Channel channel = basicGetChannel();
        return channel != null && channel.eIsProxy() ? (Channel)eResolveProxy((InternalEObject)channel) : channel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Channel basicGetChannel() {
        if (eContainer instanceof Channel) {
            return (Channel) eContainer;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getDerivedId() {
        String channelTypeId = channelType != null ? channelType.getId() : ""; //$NON-NLS-1$
        String channelName = getChannel() != null ? getChannel().getName() : ""; //$NON-NLS-1$
        return channelTypeId + "_" //$NON-NLS-1$
                + channelName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ChannelsPackage.TYPE_ASSOCIATION__ATTRIBUTE_VALUES:
                return ((InternalEList<?>)getAttributeValues()).basicRemove(otherEnd, msgs);
            case ChannelsPackage.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES:
                return ((InternalEList<?>)getExtendedAttributes()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ChannelsPackage.TYPE_ASSOCIATION__CHANNEL_TYPE:
                if (resolve) return getChannelType();
                return basicGetChannelType();
            case ChannelsPackage.TYPE_ASSOCIATION__ATTRIBUTE_VALUES:
                return getAttributeValues();
            case ChannelsPackage.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES:
                return getExtendedAttributes();
            case ChannelsPackage.TYPE_ASSOCIATION__CHANNEL:
                if (resolve) return getChannel();
                return basicGetChannel();
            case ChannelsPackage.TYPE_ASSOCIATION__DERIVED_ID:
                return getDerivedId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ChannelsPackage.TYPE_ASSOCIATION__CHANNEL_TYPE:
                setChannelType((ChannelType)newValue);
                return;
            case ChannelsPackage.TYPE_ASSOCIATION__ATTRIBUTE_VALUES:
                getAttributeValues().clear();
                getAttributeValues().addAll((Collection<? extends AttributeValue>)newValue);
                return;
            case ChannelsPackage.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES:
                getExtendedAttributes().clear();
                getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ChannelsPackage.TYPE_ASSOCIATION__CHANNEL_TYPE:
                setChannelType((ChannelType)null);
                return;
            case ChannelsPackage.TYPE_ASSOCIATION__ATTRIBUTE_VALUES:
                getAttributeValues().clear();
                return;
            case ChannelsPackage.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES:
                getExtendedAttributes().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ChannelsPackage.TYPE_ASSOCIATION__CHANNEL_TYPE:
                return channelType != null;
            case ChannelsPackage.TYPE_ASSOCIATION__ATTRIBUTE_VALUES:
                return attributeValues != null && !attributeValues.isEmpty();
            case ChannelsPackage.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES:
                return extendedAttributes != null && !extendedAttributes.isEmpty();
            case ChannelsPackage.TYPE_ASSOCIATION__CHANNEL:
                return basicGetChannel() != null;
            case ChannelsPackage.TYPE_ASSOCIATION__DERIVED_ID:
                return DERIVED_ID_EDEFAULT == null ? getDerivedId() != null : !DERIVED_ID_EDEFAULT.equals(getDerivedId());
        }
        return super.eIsSet(featureID);
    }

} // TypeAssociationImpl
