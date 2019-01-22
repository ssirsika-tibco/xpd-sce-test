/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.ParameterFacet;
import com.tibco.xpd.deploy.model.extension.DefaultValueProvider;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Config Parameter Info</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getName <em>Name
 * </em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getDescription
 * <em>Description</em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getKey <em>Key
 * </em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getLabel <em>
 * Label</em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getIcon <em>Icon
 * </em>}</li>
 * <li>
 * {@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getParameterType
 * <em>Parameter Type</em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getDefaultValue
 * <em>Default Value</em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#isRequired <em>
 * Required</em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#isAutomatic <em>
 * Automatic</em>}</li>
 * <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl#getFacets <em>
 * Facets</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ConfigParameterInfoImpl extends EObjectImpl implements
        ConfigParameterInfo {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getKey() <em>Key</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getKey()
     * @generated
     * @ordered
     */
    protected static final String KEY_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getKey() <em>Key</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getKey()
     * @generated
     * @ordered
     */
    protected String key = KEY_EDEFAULT;

    /**
     * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected String label = LABEL_EDEFAULT;

    /**
     * The default value of the '{@link #getIcon() <em>Icon</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getIcon()
     * @generated
     * @ordered
     */
    protected static final String ICON_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getIcon() <em>Icon</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getIcon()
     * @generated
     * @ordered
     */
    protected String icon = ICON_EDEFAULT;

    /**
     * The default value of the '{@link #getParameterType()
     * <em>Parameter Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getParameterType()
     * @generated
     * @ordered
     */
    protected static final ConfigParameterType PARAMETER_TYPE_EDEFAULT =
            ConfigParameterType.STRING_LITERAL;

    /**
     * The cached value of the '{@link #getParameterType()
     * <em>Parameter Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getParameterType()
     * @generated
     * @ordered
     */
    protected ConfigParameterType parameterType = PARAMETER_TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getDefaultValue()
     * <em>Default Value</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_VALUE_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getDefaultValue()
     * <em>Default Value</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

    /**
     * The default value of the '{@link #isRequired() <em>Required</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isRequired()
     * @generated
     * @ordered
     */
    protected static final boolean REQUIRED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isRequired() <em>Required</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isRequired()
     * @generated
     * @ordered
     */
    protected boolean required = REQUIRED_EDEFAULT;

    /**
     * The default value of the '{@link #isAutomatic() <em>Automatic</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isAutomatic()
     * @generated
     * @ordered
     */
    protected static final boolean AUTOMATIC_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isAutomatic() <em>Automatic</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isAutomatic()
     * @generated
     * @ordered
     */
    protected boolean automatic = AUTOMATIC_EDEFAULT;

    /**
     * The cached value of the '{@link #getFacets() <em>Facets</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getFacets()
     * @generated
     * @ordered
     */
    protected EList<ParameterFacet> facets;

    private DefaultValueProvider defaultValueProvider;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ConfigParameterInfoImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.CONFIG_PARAMETER_INFO;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__DESCRIPTION,
                    oldDescription, description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getKey() {
        return key;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setKey(String newKey) {
        String oldKey = key;
        key = newKey;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__KEY, oldKey, key));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getLabel() {
        return label;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setLabel(String newLabel) {
        String oldLabel = label;
        label = newLabel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__LABEL, oldLabel, label));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getIcon() {
        return icon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setIcon(String newIcon) {
        String oldIcon = icon;
        icon = newIcon;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__ICON, oldIcon, icon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ConfigParameterType getParameterType() {
        return parameterType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setParameterType(ConfigParameterType newParameterType) {
        ConfigParameterType oldParameterType = parameterType;
        parameterType =
                newParameterType == null ? PARAMETER_TYPE_EDEFAULT
                        : newParameterType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__PARAMETER_TYPE,
                    oldParameterType, parameterType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getDefaultValue() {
        if (defaultValueProvider != null) {
            return defaultValueProvider.getDefaultValue(this);
        }
        return defaultValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDefaultValue(String newDefaultValue) {
        String oldDefaultValue = defaultValue;
        defaultValue = newDefaultValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__DEFAULT_VALUE,
                    oldDefaultValue, defaultValue));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setRequired(boolean newRequired) {
        boolean oldRequired = required;
        required = newRequired;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__REQUIRED, oldRequired,
                    required));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isAutomatic() {
        return automatic;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setAutomatic(boolean newAutomatic) {
        boolean oldAutomatic = automatic;
        automatic = newAutomatic;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER_INFO__AUTOMATIC,
                    oldAutomatic, automatic));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<ParameterFacet> getFacets() {
        if (facets == null) {
            facets =
                    new EObjectContainmentEList<ParameterFacet>(
                            ParameterFacet.class, this,
                            DeployPackage.CONFIG_PARAMETER_INFO__FACETS);
        }
        return facets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getFacet(String key) {
        for (ParameterFacet facet : getFacets()) {
            if (key.equals(facet.getKey())) {
                return facet.getValue();
            }
        }
        return null;

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Map<String, String> getFacetsMap() {
        Map<String, String> facetsMap = new HashMap<String, String>();
        for (ParameterFacet facet : getFacets()) {
            facetsMap.put(facet.getKey(), facet.getValue());
        }
        return facetsMap;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.CONFIG_PARAMETER_INFO__FACETS:
            return ((InternalEList<?>) getFacets()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.CONFIG_PARAMETER_INFO__NAME:
            return getName();
        case DeployPackage.CONFIG_PARAMETER_INFO__DESCRIPTION:
            return getDescription();
        case DeployPackage.CONFIG_PARAMETER_INFO__KEY:
            return getKey();
        case DeployPackage.CONFIG_PARAMETER_INFO__LABEL:
            return getLabel();
        case DeployPackage.CONFIG_PARAMETER_INFO__ICON:
            return getIcon();
        case DeployPackage.CONFIG_PARAMETER_INFO__PARAMETER_TYPE:
            return getParameterType();
        case DeployPackage.CONFIG_PARAMETER_INFO__DEFAULT_VALUE:
            return getDefaultValue();
        case DeployPackage.CONFIG_PARAMETER_INFO__REQUIRED:
            return isRequired() ? Boolean.TRUE : Boolean.FALSE;
        case DeployPackage.CONFIG_PARAMETER_INFO__AUTOMATIC:
            return isAutomatic() ? Boolean.TRUE : Boolean.FALSE;
        case DeployPackage.CONFIG_PARAMETER_INFO__FACETS:
            return getFacets();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DeployPackage.CONFIG_PARAMETER_INFO__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__KEY:
            setKey((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__LABEL:
            setLabel((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__ICON:
            setIcon((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__PARAMETER_TYPE:
            setParameterType((ConfigParameterType) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__DEFAULT_VALUE:
            setDefaultValue((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__REQUIRED:
            setRequired(((Boolean) newValue).booleanValue());
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__AUTOMATIC:
            setAutomatic(((Boolean) newValue).booleanValue());
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__FACETS:
            getFacets().clear();
            getFacets().addAll((Collection<? extends ParameterFacet>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case DeployPackage.CONFIG_PARAMETER_INFO__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__KEY:
            setKey(KEY_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__LABEL:
            setLabel(LABEL_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__ICON:
            setIcon(ICON_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__PARAMETER_TYPE:
            setParameterType(PARAMETER_TYPE_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__DEFAULT_VALUE:
            setDefaultValue(DEFAULT_VALUE_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__REQUIRED:
            setRequired(REQUIRED_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__AUTOMATIC:
            setAutomatic(AUTOMATIC_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__FACETS:
            getFacets().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DeployPackage.CONFIG_PARAMETER_INFO__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.CONFIG_PARAMETER_INFO__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.CONFIG_PARAMETER_INFO__KEY:
            return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT
                    .equals(key);
        case DeployPackage.CONFIG_PARAMETER_INFO__LABEL:
            return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT
                    .equals(label);
        case DeployPackage.CONFIG_PARAMETER_INFO__ICON:
            return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT
                    .equals(icon);
        case DeployPackage.CONFIG_PARAMETER_INFO__PARAMETER_TYPE:
            return parameterType != PARAMETER_TYPE_EDEFAULT;
        case DeployPackage.CONFIG_PARAMETER_INFO__DEFAULT_VALUE:
            return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null
                    : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
        case DeployPackage.CONFIG_PARAMETER_INFO__REQUIRED:
            return required != REQUIRED_EDEFAULT;
        case DeployPackage.CONFIG_PARAMETER_INFO__AUTOMATIC:
            return automatic != AUTOMATIC_EDEFAULT;
        case DeployPackage.CONFIG_PARAMETER_INFO__FACETS:
            return facets != null && !facets.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", description: ");
        result.append(description);
        result.append(", key: ");
        result.append(key);
        result.append(", label: ");
        result.append(label);
        result.append(", icon: ");
        result.append(icon);
        result.append(", parameterType: ");
        result.append(parameterType);
        result.append(", defaultValue: ");
        result.append(defaultValue);
        result.append(", required: ");
        result.append(required);
        result.append(", automatic: ");
        result.append(automatic);
        result.append(')');
        return result.toString();
    }

    /**
     * @param defaultValueProvider
     *            the defaultValueProvider to set
     */
    public void setDefaultValueProvider(
            DefaultValueProvider defaultValueProvider) {
        this.defaultValueProvider = defaultValueProvider;
    }

    /**
     * @return the defaultValueProvider
     */
    public DefaultValueProvider getDefaultValueProvider() {
        return defaultValueProvider;
    }

} // ConfigParameterInfoImpl
