/**
 */
package com.tibco.xpd.globalSignalDefinition.impl;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import java.math.BigInteger;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Signal Definitions</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl#getGlobalSignals <em>Global Signals</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl#getFormatVersion <em>Format Version</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionsImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalSignalDefinitionsImpl extends MinimalEObjectImpl.Container implements GlobalSignalDefinitions {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

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
     * The cached value of the '{@link #getGlobalSignals() <em>Global Signals</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGlobalSignals()
     * @generated
     * @ordered
     */
    protected EList<GlobalSignal> globalSignals;

    /**
     * The default value of the '{@link #getFormatVersion() <em>Format Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormatVersion()
     * @generated
     * @ordered
     */
    protected static final BigInteger FORMAT_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFormatVersion() <em>Format Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormatVersion()
     * @generated
     * @ordered
     */
    protected BigInteger formatVersion = FORMAT_VERSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMixed()
     * @generated
     * @ordered
     */
    protected FeatureMap mixed;

    /**
     * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXMLNSPrefixMap()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xMLNSPrefixMap;

    /**
     * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXSISchemaLocation()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xSISchemaLocation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GlobalSignalDefinitionsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<GlobalSignal> getGlobalSignals() {
        if (globalSignals == null) {
            globalSignals = new EObjectContainmentEList<GlobalSignal>(GlobalSignal.class, this, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS);
        }
        return globalSignals;
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
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION, oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getFormatVersion() {
        return formatVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormatVersion(BigInteger newFormatVersion) {
        BigInteger oldFormatVersion = formatVersion;
        formatVersion = newFormatVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION, oldFormatVersion, formatVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getMixed() {
        if (mixed == null) {
            mixed = new BasicFeatureMap(this, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__MIXED);
        }
        return mixed;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXMLNSPrefixMap() {
        if (xMLNSPrefixMap == null) {
            xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP);
        }
        return xMLNSPrefixMap;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXSISchemaLocation() {
        if (xSISchemaLocation == null) {
            xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION);
        }
        return xSISchemaLocation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS:
                return ((InternalEList<?>)getGlobalSignals()).basicRemove(otherEnd, msgs);
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__MIXED:
                return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP:
                return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION:
                return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION:
                return getDescription();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS:
                return getGlobalSignals();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION:
                return getFormatVersion();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__MIXED:
                if (coreType) return getMixed();
                return ((FeatureMap.Internal)getMixed()).getWrapper();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP:
                if (coreType) return getXMLNSPrefixMap();
                else return getXMLNSPrefixMap().map();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION:
                if (coreType) return getXSISchemaLocation();
                else return getXSISchemaLocation().map();
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION:
                setDescription((String)newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS:
                getGlobalSignals().clear();
                getGlobalSignals().addAll((Collection<? extends GlobalSignal>)newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION:
                setFormatVersion((BigInteger)newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__MIXED:
                ((FeatureMap.Internal)getMixed()).set(newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP:
                ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION:
                ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS:
                getGlobalSignals().clear();
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION:
                setFormatVersion(FORMAT_VERSION_EDEFAULT);
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__MIXED:
                getMixed().clear();
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP:
                getXMLNSPrefixMap().clear();
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION:
                getXSISchemaLocation().clear();
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS:
                return globalSignals != null && !globalSignals.isEmpty();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION:
                return FORMAT_VERSION_EDEFAULT == null ? formatVersion != null : !FORMAT_VERSION_EDEFAULT.equals(formatVersion);
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__MIXED:
                return mixed != null && !mixed.isEmpty();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP:
                return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION:
                return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
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
        result.append(" (description: "); //$NON-NLS-1$
        result.append(description);
        result.append(", formatVersion: "); //$NON-NLS-1$
        result.append(formatVersion);
        result.append(", mixed: "); //$NON-NLS-1$
        result.append(mixed);
        result.append(')');
        return result.toString();
    }

} //GlobalSignalDefinitionsImpl
