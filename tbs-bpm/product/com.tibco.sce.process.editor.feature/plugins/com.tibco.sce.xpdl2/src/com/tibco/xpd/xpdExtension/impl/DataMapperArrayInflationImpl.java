/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Mapper Array Inflation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DataMapperArrayInflationImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DataMapperArrayInflationImpl#getMappingType <em>Mapping Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DataMapperArrayInflationImpl#getContributorId <em>Contributor Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataMapperArrayInflationImpl extends EObjectImpl
        implements DataMapperArrayInflation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPath()
     * @generated
     * @ordered
     */
    protected static final String PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPath()
     * @generated
     * @ordered
     */
    protected String path = PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getMappingType() <em>Mapping Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMappingType()
     * @generated
     * @ordered
     */
    protected static final DataMapperArrayInflationType MAPPING_TYPE_EDEFAULT =
            DataMapperArrayInflationType.APPEND_LIST;

    /**
     * The cached value of the '{@link #getMappingType() <em>Mapping Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMappingType()
     * @generated
     * @ordered
     */
    protected DataMapperArrayInflationType mappingType = MAPPING_TYPE_EDEFAULT;

    /**
     * This is true if the Mapping Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean mappingTypeESet;

    /**
     * The default value of the '{@link #getContributorId() <em>Contributor Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContributorId()
     * @generated
     * @ordered
     */
    protected static final String CONTRIBUTOR_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getContributorId() <em>Contributor Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContributorId()
     * @generated
     * @ordered
     */
    protected String contributorId = CONTRIBUTOR_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DataMapperArrayInflationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DATA_MAPPER_ARRAY_INFLATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPath() {
        return path;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPath(String newPath) {
        String oldPath = path;
        path = newPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__PATH,
                    oldPath, path));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataMapperArrayInflationType getMappingType() {
        return mappingType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMappingType(DataMapperArrayInflationType newMappingType) {
        DataMapperArrayInflationType oldMappingType = mappingType;
        mappingType =
                newMappingType == null ? MAPPING_TYPE_EDEFAULT : newMappingType;
        boolean oldMappingTypeESet = mappingTypeESet;
        mappingTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE,
                    oldMappingType, mappingType, !oldMappingTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMappingType() {
        DataMapperArrayInflationType oldMappingType = mappingType;
        boolean oldMappingTypeESet = mappingTypeESet;
        mappingType = MAPPING_TYPE_EDEFAULT;
        mappingTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE,
                    oldMappingType, MAPPING_TYPE_EDEFAULT, oldMappingTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMappingType() {
        return mappingTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getContributorId() {
        return contributorId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setContributorId(String newContributorId) {
        String oldContributorId = contributorId;
        contributorId = newContributorId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID,
                    oldContributorId, contributorId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__PATH:
            return getPath();
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE:
            return getMappingType();
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID:
            return getContributorId();
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
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__PATH:
            setPath((String) newValue);
            return;
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE:
            setMappingType((DataMapperArrayInflationType) newValue);
            return;
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID:
            setContributorId((String) newValue);
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
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__PATH:
            setPath(PATH_EDEFAULT);
            return;
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE:
            unsetMappingType();
            return;
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID:
            setContributorId(CONTRIBUTOR_ID_EDEFAULT);
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
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__PATH:
            return PATH_EDEFAULT == null ? path != null
                    : !PATH_EDEFAULT.equals(path);
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE:
            return isSetMappingType();
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID:
            return CONTRIBUTOR_ID_EDEFAULT == null ? contributorId != null
                    : !CONTRIBUTOR_ID_EDEFAULT.equals(contributorId);
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
        result.append(" (path: "); //$NON-NLS-1$
        result.append(path);
        result.append(", mappingType: "); //$NON-NLS-1$
        if (mappingTypeESet)
            result.append(mappingType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", contributorId: "); //$NON-NLS-1$
        result.append(contributorId);
        result.append(')');
        return result.toString();
    }

} //DataMapperArrayInflationImpl
