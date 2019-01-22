/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.RepositoryConfig;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Repository Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryConfigImpl#getConfigParameters <em>Config Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryConfigImpl extends EObjectImpl implements
        RepositoryConfig {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The cached value of the '{@link #getConfigParameters() <em>Config Parameters</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConfigParameters()
     * @generated
     * @ordered
     */
    protected EList<ConfigParameter> configParameters;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected RepositoryConfigImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.REPOSITORY_CONFIG;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ConfigParameter> getConfigParameters() {
        if (configParameters == null) {
            configParameters = new EObjectContainmentEList<ConfigParameter>(
                    ConfigParameter.class, this,
                    DeployPackage.REPOSITORY_CONFIG__CONFIG_PARAMETERS);
        }
        return configParameters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    // @SuppressWarnings("unchecked")
    public ConfigParameter getConfigParameter(String key) {
        if (key != null) {
            List<ConfigParameter> params = (List<ConfigParameter>) getConfigParameters();
            for (ConfigParameter parameter : params) {
                if (key.equals(parameter.getKey())) {
                    return parameter;
                }
            }
        }
        throw new RuntimeException(
                "Cannot find necessery parameter in configuration. Key: " + key);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.REPOSITORY_CONFIG__CONFIG_PARAMETERS:
            return ((InternalEList<?>) getConfigParameters()).basicRemove(
                    otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.REPOSITORY_CONFIG__CONFIG_PARAMETERS:
            return getConfigParameters();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DeployPackage.REPOSITORY_CONFIG__CONFIG_PARAMETERS:
            getConfigParameters().clear();
            getConfigParameters().addAll(
                    (Collection<? extends ConfigParameter>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case DeployPackage.REPOSITORY_CONFIG__CONFIG_PARAMETERS:
            getConfigParameters().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DeployPackage.REPOSITORY_CONFIG__CONFIG_PARAMETERS:
            return configParameters != null && !configParameters.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // RepositoryConfigImpl
