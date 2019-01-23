/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.wp.archive.service.ChannelsType;
import com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType;
import com.tibco.n2.wp.archive.service.WPPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Archive Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ServiceArchiveDescriptorTypeImpl#getChannels <em>Channels</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.ServiceArchiveDescriptorTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceArchiveDescriptorTypeImpl extends EObjectImpl implements ServiceArchiveDescriptorType {
    /**
     * The cached value of the '{@link #getChannels() <em>Channels</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChannels()
     * @generated
     * @ordered
     */
    protected ChannelsType channels;

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected EObject version;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ServiceArchiveDescriptorTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WPPackage.Literals.SERVICE_ARCHIVE_DESCRIPTOR_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelsType getChannels() {
        return channels;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetChannels(ChannelsType newChannels, NotificationChain msgs) {
        ChannelsType oldChannels = channels;
        channels = newChannels;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS, oldChannels, newChannels);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setChannels(ChannelsType newChannels) {
        if (newChannels != channels) {
            NotificationChain msgs = null;
            if (channels != null)
                msgs = ((InternalEObject)channels).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS, null, msgs);
            if (newChannels != null)
                msgs = ((InternalEObject)newChannels).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS, null, msgs);
            msgs = basicSetChannels(newChannels, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS, newChannels, newChannels));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetVersion(EObject newVersion, NotificationChain msgs) {
        EObject oldVersion = version;
        version = newVersion;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION, oldVersion, newVersion);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(EObject newVersion) {
        if (newVersion != version) {
            NotificationChain msgs = null;
            if (version != null)
                msgs = ((InternalEObject)version).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION, null, msgs);
            if (newVersion != null)
                msgs = ((InternalEObject)newVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION, null, msgs);
            msgs = basicSetVersion(newVersion, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION, newVersion, newVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS:
                return basicSetChannels(null, msgs);
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION:
                return basicSetVersion(null, msgs);
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
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS:
                return getChannels();
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION:
                return getVersion();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS:
                setChannels((ChannelsType)newValue);
                return;
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION:
                setVersion((EObject)newValue);
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
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS:
                setChannels((ChannelsType)null);
                return;
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION:
                setVersion((EObject)null);
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
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS:
                return channels != null;
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION:
                return version != null;
        }
        return super.eIsSet(featureID);
    }

} //ServiceArchiveDescriptorTypeImpl
