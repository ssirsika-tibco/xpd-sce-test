/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.wp.archive.service.FormType;
import com.tibco.n2.wp.archive.service.WPPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Form Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl#getFormIdentifier <em>Form Identifier</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl#getRelativePath <em>Relative Path</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl#getBasePath <em>Base Path</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl#getGuid <em>Guid</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FormTypeImpl extends EObjectImpl implements FormType {
    /**
     * The default value of the '{@link #getFormIdentifier() <em>Form Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormIdentifier()
     * @generated
     * @ordered
     */
    protected static final String FORM_IDENTIFIER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFormIdentifier() <em>Form Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormIdentifier()
     * @generated
     * @ordered
     */
    protected String formIdentifier = FORM_IDENTIFIER_EDEFAULT;

    /**
     * The default value of the '{@link #getRelativePath() <em>Relative Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRelativePath()
     * @generated
     * @ordered
     */
    protected static final String RELATIVE_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRelativePath() <em>Relative Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRelativePath()
     * @generated
     * @ordered
     */
    protected String relativePath = RELATIVE_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getBasePath() <em>Base Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBasePath()
     * @generated
     * @ordered
     */
    protected static final String BASE_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getBasePath() <em>Base Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBasePath()
     * @generated
     * @ordered
     */
    protected String basePath = BASE_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getGuid() <em>Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGuid()
     * @generated
     * @ordered
     */
    protected static final String GUID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGuid() <em>Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGuid()
     * @generated
     * @ordered
     */
    protected String guid = GUID_EDEFAULT;

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
    protected FormTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WPPackage.Literals.FORM_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFormIdentifier() {
        return formIdentifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormIdentifier(String newFormIdentifier) {
        String oldFormIdentifier = formIdentifier;
        formIdentifier = newFormIdentifier;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.FORM_TYPE__FORM_IDENTIFIER, oldFormIdentifier, formIdentifier));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRelativePath() {
        return relativePath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRelativePath(String newRelativePath) {
        String oldRelativePath = relativePath;
        relativePath = newRelativePath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.FORM_TYPE__RELATIVE_PATH, oldRelativePath, relativePath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBasePath(String newBasePath) {
        String oldBasePath = basePath;
        basePath = newBasePath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.FORM_TYPE__BASE_PATH, oldBasePath, basePath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getGuid() {
        return guid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGuid(String newGuid) {
        String oldGuid = guid;
        guid = newGuid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.FORM_TYPE__GUID, oldGuid, guid));
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
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.FORM_TYPE__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.FORM_TYPE__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case WPPackage.FORM_TYPE__FORM_IDENTIFIER:
                return getFormIdentifier();
            case WPPackage.FORM_TYPE__RELATIVE_PATH:
                return getRelativePath();
            case WPPackage.FORM_TYPE__BASE_PATH:
                return getBasePath();
            case WPPackage.FORM_TYPE__GUID:
                return getGuid();
            case WPPackage.FORM_TYPE__NAME:
                return getName();
            case WPPackage.FORM_TYPE__VERSION:
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
            case WPPackage.FORM_TYPE__FORM_IDENTIFIER:
                setFormIdentifier((String)newValue);
                return;
            case WPPackage.FORM_TYPE__RELATIVE_PATH:
                setRelativePath((String)newValue);
                return;
            case WPPackage.FORM_TYPE__BASE_PATH:
                setBasePath((String)newValue);
                return;
            case WPPackage.FORM_TYPE__GUID:
                setGuid((String)newValue);
                return;
            case WPPackage.FORM_TYPE__NAME:
                setName((String)newValue);
                return;
            case WPPackage.FORM_TYPE__VERSION:
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
            case WPPackage.FORM_TYPE__FORM_IDENTIFIER:
                setFormIdentifier(FORM_IDENTIFIER_EDEFAULT);
                return;
            case WPPackage.FORM_TYPE__RELATIVE_PATH:
                setRelativePath(RELATIVE_PATH_EDEFAULT);
                return;
            case WPPackage.FORM_TYPE__BASE_PATH:
                setBasePath(BASE_PATH_EDEFAULT);
                return;
            case WPPackage.FORM_TYPE__GUID:
                setGuid(GUID_EDEFAULT);
                return;
            case WPPackage.FORM_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case WPPackage.FORM_TYPE__VERSION:
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
            case WPPackage.FORM_TYPE__FORM_IDENTIFIER:
                return FORM_IDENTIFIER_EDEFAULT == null ? formIdentifier != null : !FORM_IDENTIFIER_EDEFAULT.equals(formIdentifier);
            case WPPackage.FORM_TYPE__RELATIVE_PATH:
                return RELATIVE_PATH_EDEFAULT == null ? relativePath != null : !RELATIVE_PATH_EDEFAULT.equals(relativePath);
            case WPPackage.FORM_TYPE__BASE_PATH:
                return BASE_PATH_EDEFAULT == null ? basePath != null : !BASE_PATH_EDEFAULT.equals(basePath);
            case WPPackage.FORM_TYPE__GUID:
                return GUID_EDEFAULT == null ? guid != null : !GUID_EDEFAULT.equals(guid);
            case WPPackage.FORM_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case WPPackage.FORM_TYPE__VERSION:
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
        result.append(" (formIdentifier: ");
        result.append(formIdentifier);
        result.append(", relativePath: ");
        result.append(relativePath);
        result.append(", basePath: ");
        result.append(basePath);
        result.append(", guid: ");
        result.append(guid);
        result.append(", name: ");
        result.append(name);
        result.append(", version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //FormTypeImpl
