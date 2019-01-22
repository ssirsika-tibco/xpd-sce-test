/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.impl;

import com.tibco.n2.common.organisation.api.OrganisationPackage;
import com.tibco.n2.common.organisation.api.XmlOrgModelVersion;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Org Model Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlOrgModelVersionImpl#getModelVersion <em>Model Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XmlOrgModelVersionImpl extends EObjectImpl implements XmlOrgModelVersion {
    /**
     * The default value of the '{@link #getModelVersion() <em>Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModelVersion()
     * @generated
     * @ordered
     */
    protected static final int MODEL_VERSION_EDEFAULT = -1;

    /**
     * The cached value of the '{@link #getModelVersion() <em>Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModelVersion()
     * @generated
     * @ordered
     */
    protected int modelVersion = MODEL_VERSION_EDEFAULT;

    /**
     * This is true if the Model Version attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean modelVersionESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XmlOrgModelVersionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OrganisationPackage.Literals.XML_ORG_MODEL_VERSION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getModelVersion() {
        return modelVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModelVersion(int newModelVersion) {
        int oldModelVersion = modelVersion;
        modelVersion = newModelVersion;
        boolean oldModelVersionESet = modelVersionESet;
        modelVersionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_ORG_MODEL_VERSION__MODEL_VERSION, oldModelVersion, modelVersion, !oldModelVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetModelVersion() {
        int oldModelVersion = modelVersion;
        boolean oldModelVersionESet = modelVersionESet;
        modelVersion = MODEL_VERSION_EDEFAULT;
        modelVersionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, OrganisationPackage.XML_ORG_MODEL_VERSION__MODEL_VERSION, oldModelVersion, MODEL_VERSION_EDEFAULT, oldModelVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetModelVersion() {
        return modelVersionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrganisationPackage.XML_ORG_MODEL_VERSION__MODEL_VERSION:
                return getModelVersion();
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
            case OrganisationPackage.XML_ORG_MODEL_VERSION__MODEL_VERSION:
                setModelVersion((Integer)newValue);
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
            case OrganisationPackage.XML_ORG_MODEL_VERSION__MODEL_VERSION:
                unsetModelVersion();
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
            case OrganisationPackage.XML_ORG_MODEL_VERSION__MODEL_VERSION:
                return isSetModelVersion();
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
        result.append(" (modelVersion: ");
        if (modelVersionESet) result.append(modelVersion); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //XmlOrgModelVersionImpl
