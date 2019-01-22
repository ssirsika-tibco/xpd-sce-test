/**
 */
package com.tibco.xpd.rsd.impl;

import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.RsdPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Payload Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.impl.PayloadReferenceImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.PayloadReferenceImpl#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.PayloadReferenceImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.PayloadReferenceImpl#getMediaType <em>Media Type</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.PayloadReferenceImpl#isArray <em>Array</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PayloadReferenceImpl extends MinimalEObjectImpl.Container implements PayloadReference {
    /**
     * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected static final String NAMESPACE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected String namespace = NAMESPACE_EDEFAULT;

    /**
     * The default value of the '{@link #getRef() <em>Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRef()
     * @generated
     * @ordered
     */
    protected static final String REF_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRef() <em>Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRef()
     * @generated
     * @ordered
     */
    protected String ref = REF_EDEFAULT;

    /**
     * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected static final String LOCATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected String location = LOCATION_EDEFAULT;

    /**
     * The default value of the '{@link #getMediaType() <em>Media Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMediaType()
     * @generated
     * @ordered
     */
    protected static final String MEDIA_TYPE_EDEFAULT = null;

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
     * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isArray()
     * @generated
     * @ordered
     */
    protected static final boolean ARRAY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isArray()
     * @generated
     * @ordered
     */
    protected boolean array = ARRAY_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PayloadReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RsdPackage.Literals.PAYLOAD_REFERENCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNamespace(String newNamespace) {
        String oldNamespace = namespace;
        namespace = newNamespace;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.PAYLOAD_REFERENCE__NAMESPACE, oldNamespace, namespace));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRef() {
        return ref;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRef(String newRef) {
        String oldRef = ref;
        ref = newRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.PAYLOAD_REFERENCE__REF, oldRef, ref));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLocation(String newLocation) {
        String oldLocation = location;
        location = newLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.PAYLOAD_REFERENCE__LOCATION, oldLocation, location));
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
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.PAYLOAD_REFERENCE__MEDIA_TYPE, oldMediaType, mediaType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isArray() {
        return array;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setArray(boolean newArray) {
        boolean oldArray = array;
        array = newArray;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.PAYLOAD_REFERENCE__ARRAY, oldArray, array));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case RsdPackage.PAYLOAD_REFERENCE__NAMESPACE:
                return getNamespace();
            case RsdPackage.PAYLOAD_REFERENCE__REF:
                return getRef();
            case RsdPackage.PAYLOAD_REFERENCE__LOCATION:
                return getLocation();
            case RsdPackage.PAYLOAD_REFERENCE__MEDIA_TYPE:
                return getMediaType();
            case RsdPackage.PAYLOAD_REFERENCE__ARRAY:
                return isArray();
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
            case RsdPackage.PAYLOAD_REFERENCE__NAMESPACE:
                setNamespace((String)newValue);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__REF:
                setRef((String)newValue);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__LOCATION:
                setLocation((String)newValue);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__MEDIA_TYPE:
                setMediaType((String)newValue);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__ARRAY:
                setArray((Boolean)newValue);
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
            case RsdPackage.PAYLOAD_REFERENCE__NAMESPACE:
                setNamespace(NAMESPACE_EDEFAULT);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__REF:
                setRef(REF_EDEFAULT);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__LOCATION:
                setLocation(LOCATION_EDEFAULT);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__MEDIA_TYPE:
                setMediaType(MEDIA_TYPE_EDEFAULT);
                return;
            case RsdPackage.PAYLOAD_REFERENCE__ARRAY:
                setArray(ARRAY_EDEFAULT);
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
            case RsdPackage.PAYLOAD_REFERENCE__NAMESPACE:
                return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
            case RsdPackage.PAYLOAD_REFERENCE__REF:
                return REF_EDEFAULT == null ? ref != null : !REF_EDEFAULT.equals(ref);
            case RsdPackage.PAYLOAD_REFERENCE__LOCATION:
                return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
            case RsdPackage.PAYLOAD_REFERENCE__MEDIA_TYPE:
                return MEDIA_TYPE_EDEFAULT == null ? mediaType != null : !MEDIA_TYPE_EDEFAULT.equals(mediaType);
            case RsdPackage.PAYLOAD_REFERENCE__ARRAY:
                return array != ARRAY_EDEFAULT;
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
        result.append(" (namespace: ");
        result.append(namespace);
        result.append(", ref: ");
        result.append(ref);
        result.append(", location: ");
        result.append(location);
        result.append(", mediaType: ");
        result.append(mediaType);
        result.append(", array: ");
        result.append(array);
        result.append(')');
        return result.toString();
    }

} //PayloadReferenceImpl
