/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Org Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl#getDateCreated <em>Date Created</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class BaseOrgModelImpl extends NamedElementImpl implements
        BaseOrgModel {
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
     * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStatus()
     * @generated
     * @ordered
     */
    protected static final String STATUS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStatus()
     * @generated
     * @ordered
     */
    protected String status = STATUS_EDEFAULT;

    /**
     * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAuthor()
     * @generated
     * @ordered
     */
    protected static final String AUTHOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAuthor()
     * @generated
     * @ordered
     */
    protected String author = AUTHOR_EDEFAULT;

    /**
     * The default value of the '{@link #getDateCreated() <em>Date Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDateCreated()
     * @generated
     * @ordered
     */
    protected static final long DATE_CREATED_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getDateCreated() <em>Date Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDateCreated()
     * @generated
     * @ordered
     */
    protected long dateCreated = DATE_CREATED_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BaseOrgModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.BASE_ORG_MODEL;
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
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.BASE_ORG_MODEL__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStatus(String newStatus) {
        String oldStatus = status;
        status = newStatus;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.BASE_ORG_MODEL__STATUS, oldStatus, status));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAuthor() {
        return author;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAuthor(String newAuthor) {
        String oldAuthor = author;
        author = newAuthor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.BASE_ORG_MODEL__AUTHOR, oldAuthor, author));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getDateCreated() {
        return dateCreated;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDateCreated(long newDateCreated) {
        long oldDateCreated = dateCreated;
        dateCreated = newDateCreated;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.BASE_ORG_MODEL__DATE_CREATED, oldDateCreated,
                    dateCreated));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.BASE_ORG_MODEL__VERSION:
            return getVersion();
        case OMPackage.BASE_ORG_MODEL__STATUS:
            return getStatus();
        case OMPackage.BASE_ORG_MODEL__AUTHOR:
            return getAuthor();
        case OMPackage.BASE_ORG_MODEL__DATE_CREATED:
            return new Long(getDateCreated());
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
        case OMPackage.BASE_ORG_MODEL__VERSION:
            setVersion((String) newValue);
            return;
        case OMPackage.BASE_ORG_MODEL__STATUS:
            setStatus((String) newValue);
            return;
        case OMPackage.BASE_ORG_MODEL__AUTHOR:
            setAuthor((String) newValue);
            return;
        case OMPackage.BASE_ORG_MODEL__DATE_CREATED:
            setDateCreated(((Long) newValue).longValue());
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
        case OMPackage.BASE_ORG_MODEL__VERSION:
            setVersion(VERSION_EDEFAULT);
            return;
        case OMPackage.BASE_ORG_MODEL__STATUS:
            setStatus(STATUS_EDEFAULT);
            return;
        case OMPackage.BASE_ORG_MODEL__AUTHOR:
            setAuthor(AUTHOR_EDEFAULT);
            return;
        case OMPackage.BASE_ORG_MODEL__DATE_CREATED:
            setDateCreated(DATE_CREATED_EDEFAULT);
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
        case OMPackage.BASE_ORG_MODEL__VERSION:
            return VERSION_EDEFAULT == null ? version != null
                    : !VERSION_EDEFAULT.equals(version);
        case OMPackage.BASE_ORG_MODEL__STATUS:
            return STATUS_EDEFAULT == null ? status != null : !STATUS_EDEFAULT
                    .equals(status);
        case OMPackage.BASE_ORG_MODEL__AUTHOR:
            return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT
                    .equals(author);
        case OMPackage.BASE_ORG_MODEL__DATE_CREATED:
            return dateCreated != DATE_CREATED_EDEFAULT;
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
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (version: "); //$NON-NLS-1$
        result.append(version);
        result.append(", status: "); //$NON-NLS-1$
        result.append(status);
        result.append(", author: "); //$NON-NLS-1$
        result.append(author);
        result.append(", dateCreated: "); //$NON-NLS-1$
        result.append(dateCreated);
        result.append(')');
        return result.toString();
    }

} //BaseOrgModelImpl
