/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.wp.archive.service.PageActivityType;
import com.tibco.n2.wp.archive.service.PageFlowType;
import com.tibco.n2.wp.archive.service.WPPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page Flow Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl#getPageActivity <em>Page Activity</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl#getModuleName <em>Module Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl#getModuleVersion <em>Module Version</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PageFlowTypeImpl extends EObjectImpl implements PageFlowType {
    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected FeatureMap group;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleName()
     * @generated
     * @ordered
     */
    protected static final String MODULE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleName()
     * @generated
     * @ordered
     */
    protected String moduleName = MODULE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getModuleVersion() <em>Module Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleVersion()
     * @generated
     * @ordered
     */
    protected static final String MODULE_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getModuleVersion() <em>Module Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleVersion()
     * @generated
     * @ordered
     */
    protected String moduleVersion = MODULE_VERSION_EDEFAULT;

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
     * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUrl()
     * @generated
     * @ordered
     */
    protected static final String URL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUrl()
     * @generated
     * @ordered
     */
    protected String url = URL_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PageFlowTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WPPackage.Literals.PAGE_FLOW_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, WPPackage.PAGE_FLOW_TYPE__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PageActivityType> getPageActivity() {
        return getGroup().list(WPPackage.Literals.PAGE_FLOW_TYPE__PAGE_ACTIVITY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_FLOW_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModuleName(String newModuleName) {
        String oldModuleName = moduleName;
        moduleName = newModuleName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_FLOW_TYPE__MODULE_NAME, oldModuleName, moduleName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getModuleVersion() {
        return moduleVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModuleVersion(String newModuleVersion) {
        String oldModuleVersion = moduleVersion;
        moduleVersion = newModuleVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_FLOW_TYPE__MODULE_VERSION, oldModuleVersion, moduleVersion));
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
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_FLOW_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUrl(String newUrl) {
        String oldUrl = url;
        url = newUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_FLOW_TYPE__URL, oldUrl, url));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case WPPackage.PAGE_FLOW_TYPE__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case WPPackage.PAGE_FLOW_TYPE__PAGE_ACTIVITY:
                return ((InternalEList<?>)getPageActivity()).basicRemove(otherEnd, msgs);
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
            case WPPackage.PAGE_FLOW_TYPE__GROUP:
                if (coreType) return getGroup();
                return ((FeatureMap.Internal)getGroup()).getWrapper();
            case WPPackage.PAGE_FLOW_TYPE__PAGE_ACTIVITY:
                return getPageActivity();
            case WPPackage.PAGE_FLOW_TYPE__ID:
                return getId();
            case WPPackage.PAGE_FLOW_TYPE__MODULE_NAME:
                return getModuleName();
            case WPPackage.PAGE_FLOW_TYPE__MODULE_VERSION:
                return getModuleVersion();
            case WPPackage.PAGE_FLOW_TYPE__NAME:
                return getName();
            case WPPackage.PAGE_FLOW_TYPE__URL:
                return getUrl();
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
            case WPPackage.PAGE_FLOW_TYPE__GROUP:
                ((FeatureMap.Internal)getGroup()).set(newValue);
                return;
            case WPPackage.PAGE_FLOW_TYPE__PAGE_ACTIVITY:
                getPageActivity().clear();
                getPageActivity().addAll((Collection<? extends PageActivityType>)newValue);
                return;
            case WPPackage.PAGE_FLOW_TYPE__ID:
                setId((String)newValue);
                return;
            case WPPackage.PAGE_FLOW_TYPE__MODULE_NAME:
                setModuleName((String)newValue);
                return;
            case WPPackage.PAGE_FLOW_TYPE__MODULE_VERSION:
                setModuleVersion((String)newValue);
                return;
            case WPPackage.PAGE_FLOW_TYPE__NAME:
                setName((String)newValue);
                return;
            case WPPackage.PAGE_FLOW_TYPE__URL:
                setUrl((String)newValue);
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
            case WPPackage.PAGE_FLOW_TYPE__GROUP:
                getGroup().clear();
                return;
            case WPPackage.PAGE_FLOW_TYPE__PAGE_ACTIVITY:
                getPageActivity().clear();
                return;
            case WPPackage.PAGE_FLOW_TYPE__ID:
                setId(ID_EDEFAULT);
                return;
            case WPPackage.PAGE_FLOW_TYPE__MODULE_NAME:
                setModuleName(MODULE_NAME_EDEFAULT);
                return;
            case WPPackage.PAGE_FLOW_TYPE__MODULE_VERSION:
                setModuleVersion(MODULE_VERSION_EDEFAULT);
                return;
            case WPPackage.PAGE_FLOW_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case WPPackage.PAGE_FLOW_TYPE__URL:
                setUrl(URL_EDEFAULT);
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
            case WPPackage.PAGE_FLOW_TYPE__GROUP:
                return group != null && !group.isEmpty();
            case WPPackage.PAGE_FLOW_TYPE__PAGE_ACTIVITY:
                return !getPageActivity().isEmpty();
            case WPPackage.PAGE_FLOW_TYPE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case WPPackage.PAGE_FLOW_TYPE__MODULE_NAME:
                return MODULE_NAME_EDEFAULT == null ? moduleName != null : !MODULE_NAME_EDEFAULT.equals(moduleName);
            case WPPackage.PAGE_FLOW_TYPE__MODULE_VERSION:
                return MODULE_VERSION_EDEFAULT == null ? moduleVersion != null : !MODULE_VERSION_EDEFAULT.equals(moduleVersion);
            case WPPackage.PAGE_FLOW_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case WPPackage.PAGE_FLOW_TYPE__URL:
                return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
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
        result.append(" (group: ");
        result.append(group);
        result.append(", id: ");
        result.append(id);
        result.append(", moduleName: ");
        result.append(moduleName);
        result.append(", moduleVersion: ");
        result.append(moduleVersion);
        result.append(", name: ");
        result.append(name);
        result.append(", url: ");
        result.append(url);
        result.append(')');
        return result.toString();
    }

} //PageFlowTypeImpl
