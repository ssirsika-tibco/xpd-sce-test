/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.VendorExtension;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vendor Extension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.VendorExtensionImpl#getExtensionDescription <em>Extension Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.VendorExtensionImpl#getSchemaLocation <em>Schema Location</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.VendorExtensionImpl#getToolId <em>Tool Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VendorExtensionImpl extends EObjectImpl implements VendorExtension {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getExtensionDescription() <em>Extension Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtensionDescription()
     * @generated
     * @ordered
     */
    protected static final String EXTENSION_DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getExtensionDescription() <em>Extension Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtensionDescription()
     * @generated
     * @ordered
     */
    protected String extensionDescription = EXTENSION_DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getSchemaLocation() <em>Schema Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSchemaLocation()
     * @generated
     * @ordered
     */
    protected static final String SCHEMA_LOCATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSchemaLocation() <em>Schema Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSchemaLocation()
     * @generated
     * @ordered
     */
    protected String schemaLocation = SCHEMA_LOCATION_EDEFAULT;

    /**
     * The default value of the '{@link #getToolId() <em>Tool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToolId()
     * @generated
     * @ordered
     */
    protected static final String TOOL_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getToolId() <em>Tool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToolId()
     * @generated
     * @ordered
     */
    protected String toolId = TOOL_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected VendorExtensionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.VENDOR_EXTENSION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getExtensionDescription() {
        return extensionDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExtensionDescription(String newExtensionDescription) {
        String oldExtensionDescription = extensionDescription;
        extensionDescription = newExtensionDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.VENDOR_EXTENSION__EXTENSION_DESCRIPTION,
                    oldExtensionDescription, extensionDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSchemaLocation() {
        return schemaLocation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSchemaLocation(String newSchemaLocation) {
        String oldSchemaLocation = schemaLocation;
        schemaLocation = newSchemaLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.VENDOR_EXTENSION__SCHEMA_LOCATION,
                    oldSchemaLocation, schemaLocation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getToolId() {
        return toolId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setToolId(String newToolId) {
        String oldToolId = toolId;
        toolId = newToolId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.VENDOR_EXTENSION__TOOL_ID, oldToolId,
                    toolId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.VENDOR_EXTENSION__EXTENSION_DESCRIPTION:
            return getExtensionDescription();
        case Xpdl2Package.VENDOR_EXTENSION__SCHEMA_LOCATION:
            return getSchemaLocation();
        case Xpdl2Package.VENDOR_EXTENSION__TOOL_ID:
            return getToolId();
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
        case Xpdl2Package.VENDOR_EXTENSION__EXTENSION_DESCRIPTION:
            setExtensionDescription((String) newValue);
            return;
        case Xpdl2Package.VENDOR_EXTENSION__SCHEMA_LOCATION:
            setSchemaLocation((String) newValue);
            return;
        case Xpdl2Package.VENDOR_EXTENSION__TOOL_ID:
            setToolId((String) newValue);
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
        case Xpdl2Package.VENDOR_EXTENSION__EXTENSION_DESCRIPTION:
            setExtensionDescription(EXTENSION_DESCRIPTION_EDEFAULT);
            return;
        case Xpdl2Package.VENDOR_EXTENSION__SCHEMA_LOCATION:
            setSchemaLocation(SCHEMA_LOCATION_EDEFAULT);
            return;
        case Xpdl2Package.VENDOR_EXTENSION__TOOL_ID:
            setToolId(TOOL_ID_EDEFAULT);
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
        case Xpdl2Package.VENDOR_EXTENSION__EXTENSION_DESCRIPTION:
            return EXTENSION_DESCRIPTION_EDEFAULT == null ? extensionDescription != null
                    : !EXTENSION_DESCRIPTION_EDEFAULT.equals(extensionDescription);
        case Xpdl2Package.VENDOR_EXTENSION__SCHEMA_LOCATION:
            return SCHEMA_LOCATION_EDEFAULT == null ? schemaLocation != null
                    : !SCHEMA_LOCATION_EDEFAULT.equals(schemaLocation);
        case Xpdl2Package.VENDOR_EXTENSION__TOOL_ID:
            return TOOL_ID_EDEFAULT == null ? toolId != null : !TOOL_ID_EDEFAULT.equals(toolId);
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (extensionDescription: "); //$NON-NLS-1$
        result.append(extensionDescription);
        result.append(", schemaLocation: "); //$NON-NLS-1$
        result.append(schemaLocation);
        result.append(", toolId: "); //$NON-NLS-1$
        result.append(toolId);
        result.append(')');
        return result.toString();
    }

} //VendorExtensionImpl
