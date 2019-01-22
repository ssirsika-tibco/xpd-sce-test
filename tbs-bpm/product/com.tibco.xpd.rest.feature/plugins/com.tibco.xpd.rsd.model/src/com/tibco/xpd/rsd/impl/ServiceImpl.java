/**
 */
package com.tibco.xpd.rsd.impl;

import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.impl.ServiceImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.ServiceImpl#getContextPath <em>Context Path</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.ServiceImpl#getMediaType <em>Media Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceImpl extends NamedElementImpl implements Service {
    /**
     * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResources()
     * @generated
     * @ordered
     */
    protected EList<Resource> resources;

    /**
     * The default value of the '{@link #getContextPath() <em>Context Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContextPath()
     * @generated
     * @ordered
     */
    protected static final String CONTEXT_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getContextPath() <em>Context Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContextPath()
     * @generated
     * @ordered
     */
    protected String contextPath = CONTEXT_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getMediaType() <em>Media Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMediaType()
     * @generated
     * @ordered
     */
    protected static final String MEDIA_TYPE_EDEFAULT = "application/json";

    /**
     * The cached value of the '{@link #getMediaType() <em>Media Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMediaType()
     * @generated
     * @ordered
     */
    protected String mediaType = MEDIA_TYPE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ServiceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RsdPackage.Literals.SERVICE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Resource> getResources() {
        if (resources == null) {
            resources = new EObjectContainmentEList<Resource>(Resource.class, this, RsdPackage.SERVICE__RESOURCES);
        }
        return resources;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setContextPath(String newContextPath) {
        String oldContextPath = contextPath;
        contextPath = newContextPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.SERVICE__CONTEXT_PATH, oldContextPath, contextPath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMediaType(String newMediaType) {
        String oldMediaType = mediaType;
        mediaType = newMediaType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.SERVICE__MEDIA_TYPE, oldMediaType, mediaType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case RsdPackage.SERVICE__RESOURCES:
                return ((InternalEList<?>)getResources()).basicRemove(otherEnd, msgs);
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
            case RsdPackage.SERVICE__RESOURCES:
                return getResources();
            case RsdPackage.SERVICE__CONTEXT_PATH:
                return getContextPath();
            case RsdPackage.SERVICE__MEDIA_TYPE:
                return getMediaType();
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
            case RsdPackage.SERVICE__RESOURCES:
                getResources().clear();
                getResources().addAll((Collection<? extends Resource>)newValue);
                return;
            case RsdPackage.SERVICE__CONTEXT_PATH:
                setContextPath((String)newValue);
                return;
            case RsdPackage.SERVICE__MEDIA_TYPE:
                setMediaType((String)newValue);
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
            case RsdPackage.SERVICE__RESOURCES:
                getResources().clear();
                return;
            case RsdPackage.SERVICE__CONTEXT_PATH:
                setContextPath(CONTEXT_PATH_EDEFAULT);
                return;
            case RsdPackage.SERVICE__MEDIA_TYPE:
                setMediaType(MEDIA_TYPE_EDEFAULT);
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
            case RsdPackage.SERVICE__RESOURCES:
                return resources != null && !resources.isEmpty();
            case RsdPackage.SERVICE__CONTEXT_PATH:
                return CONTEXT_PATH_EDEFAULT == null ? contextPath != null : !CONTEXT_PATH_EDEFAULT.equals(contextPath);
            case RsdPackage.SERVICE__MEDIA_TYPE:
                return MEDIA_TYPE_EDEFAULT == null ? mediaType != null : !MEDIA_TYPE_EDEFAULT.equals(mediaType);
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
        result.append(" (contextPath: ");
        result.append(contextPath);
        result.append(", mediaType: ");
        result.append(mediaType);
        result.append(')');
        return result.toString();
    }

} //ServiceImpl
