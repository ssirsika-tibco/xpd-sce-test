/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.wp.archive.service.ChannelExtentionType;
import com.tibco.n2.wp.archive.service.ChannelExtention;
import com.tibco.n2.wp.archive.service.ExtendedPropertiesType;
import com.tibco.n2.wp.archive.service.FormType;
import com.tibco.n2.wp.archive.service.PageFlowRefType;
import com.tibco.n2.wp.archive.service.PageFlowType;
import com.tibco.n2.wp.archive.service.WPPackage;
import com.tibco.n2.wp.archive.service.WorkTypeType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Type Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getExtendedProperties <em>Extended Properties</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getForm <em>Form</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getPageFlow <em>Page Flow</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getExtensionConfig <em>Extension Config</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getPageFlowRef <em>Page Flow Ref</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getGuid <em>Guid</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkTypeTypeImpl extends EObjectImpl implements WorkTypeType {
    /**
     * The cached value of the '{@link #getExtendedProperties() <em>Extended Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedProperties()
     * @generated
     * @ordered
     */
    protected ExtendedPropertiesType extendedProperties;

    /**
     * This is true if the Extended Properties containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean extendedPropertiesESet;

    /**
     * The cached value of the '{@link #getForm() <em>Form</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getForm()
     * @generated
     * @ordered
     */
    protected FormType form;

    /**
     * The cached value of the '{@link #getPageFlow() <em>Page Flow</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPageFlow()
     * @generated
     * @ordered
     */
    protected PageFlowType pageFlow;

    /**
     * The cached value of the '{@link #getExtensionConfig() <em>Extension Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtensionConfig()
     * @generated
     * @ordered
     */
    protected ChannelExtentionType extensionConfig;

    /**
     * The cached value of the '{@link #getPageFlowRef() <em>Page Flow Ref</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPageFlowRef()
     * @generated
     * @ordered
     */
    protected PageFlowRefType pageFlowRef;

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
    protected WorkTypeTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WPPackage.Literals.WORK_TYPE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExtendedPropertiesType getExtendedProperties() {
        return extendedProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExtendedProperties(ExtendedPropertiesType newExtendedProperties, NotificationChain msgs) {
        ExtendedPropertiesType oldExtendedProperties = extendedProperties;
        extendedProperties = newExtendedProperties;
        boolean oldExtendedPropertiesESet = extendedPropertiesESet;
        extendedPropertiesESet = true;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES, oldExtendedProperties, newExtendedProperties, !oldExtendedPropertiesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExtendedProperties(ExtendedPropertiesType newExtendedProperties) {
        if (newExtendedProperties != extendedProperties) {
            NotificationChain msgs = null;
            if (extendedProperties != null)
                msgs = ((InternalEObject)extendedProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES, null, msgs);
            if (newExtendedProperties != null)
                msgs = ((InternalEObject)newExtendedProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES, null, msgs);
            msgs = basicSetExtendedProperties(newExtendedProperties, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else {
            boolean oldExtendedPropertiesESet = extendedPropertiesESet;
            extendedPropertiesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES, newExtendedProperties, newExtendedProperties, !oldExtendedPropertiesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetExtendedProperties(NotificationChain msgs) {
        ExtendedPropertiesType oldExtendedProperties = extendedProperties;
        extendedProperties = null;
        boolean oldExtendedPropertiesESet = extendedPropertiesESet;
        extendedPropertiesESet = false;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES, oldExtendedProperties, null, oldExtendedPropertiesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExtendedProperties() {
        if (extendedProperties != null) {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)extendedProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES, null, msgs);
            msgs = basicUnsetExtendedProperties(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else {
            boolean oldExtendedPropertiesESet = extendedPropertiesESet;
            extendedPropertiesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES, null, null, oldExtendedPropertiesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExtendedProperties() {
        return extendedPropertiesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FormType getForm() {
        return form;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetForm(FormType newForm, NotificationChain msgs) {
        FormType oldForm = form;
        form = newForm;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__FORM, oldForm, newForm);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setForm(FormType newForm) {
        if (newForm != form) {
            NotificationChain msgs = null;
            if (form != null)
                msgs = ((InternalEObject)form).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__FORM, null, msgs);
            if (newForm != null)
                msgs = ((InternalEObject)newForm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__FORM, null, msgs);
            msgs = basicSetForm(newForm, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__FORM, newForm, newForm));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageFlowType getPageFlow() {
        return pageFlow;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPageFlow(PageFlowType newPageFlow, NotificationChain msgs) {
        PageFlowType oldPageFlow = pageFlow;
        pageFlow = newPageFlow;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__PAGE_FLOW, oldPageFlow, newPageFlow);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPageFlow(PageFlowType newPageFlow) {
        if (newPageFlow != pageFlow) {
            NotificationChain msgs = null;
            if (pageFlow != null)
                msgs = ((InternalEObject)pageFlow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__PAGE_FLOW, null, msgs);
            if (newPageFlow != null)
                msgs = ((InternalEObject)newPageFlow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__PAGE_FLOW, null, msgs);
            msgs = basicSetPageFlow(newPageFlow, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__PAGE_FLOW, newPageFlow, newPageFlow));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelExtentionType getExtensionConfig() {
        return extensionConfig;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExtensionConfig(ChannelExtentionType newExtensionConfig, NotificationChain msgs) {
        ChannelExtentionType oldExtensionConfig = extensionConfig;
        extensionConfig = newExtensionConfig;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG, oldExtensionConfig, newExtensionConfig);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExtensionConfig(ChannelExtentionType newExtensionConfig) {
        if (newExtensionConfig != extensionConfig) {
            NotificationChain msgs = null;
            if (extensionConfig != null)
                msgs = ((InternalEObject)extensionConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG, null, msgs);
            if (newExtensionConfig != null)
                msgs = ((InternalEObject)newExtensionConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG, null, msgs);
            msgs = basicSetExtensionConfig(newExtensionConfig, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG, newExtensionConfig, newExtensionConfig));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageFlowRefType getPageFlowRef() {
        return pageFlowRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPageFlowRef(PageFlowRefType newPageFlowRef, NotificationChain msgs) {
        PageFlowRefType oldPageFlowRef = pageFlowRef;
        pageFlowRef = newPageFlowRef;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF, oldPageFlowRef, newPageFlowRef);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPageFlowRef(PageFlowRefType newPageFlowRef) {
        if (newPageFlowRef != pageFlowRef) {
            NotificationChain msgs = null;
            if (pageFlowRef != null)
                msgs = ((InternalEObject)pageFlowRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF, null, msgs);
            if (newPageFlowRef != null)
                msgs = ((InternalEObject)newPageFlowRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF, null, msgs);
            msgs = basicSetPageFlowRef(newPageFlowRef, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF, newPageFlowRef, newPageFlowRef));
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
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__GUID, oldGuid, guid));
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
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.WORK_TYPE_TYPE__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES:
                return basicUnsetExtendedProperties(msgs);
            case WPPackage.WORK_TYPE_TYPE__FORM:
                return basicSetForm(null, msgs);
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW:
                return basicSetPageFlow(null, msgs);
            case WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG:
                return basicSetExtensionConfig(null, msgs);
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF:
                return basicSetPageFlowRef(null, msgs);
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
            case WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES:
                return getExtendedProperties();
            case WPPackage.WORK_TYPE_TYPE__FORM:
                return getForm();
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW:
                return getPageFlow();
            case WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG:
                return getExtensionConfig();
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF:
                return getPageFlowRef();
            case WPPackage.WORK_TYPE_TYPE__GUID:
                return getGuid();
            case WPPackage.WORK_TYPE_TYPE__NAME:
                return getName();
            case WPPackage.WORK_TYPE_TYPE__VERSION:
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
            case WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES:
                setExtendedProperties((ExtendedPropertiesType)newValue);
                return;
            case WPPackage.WORK_TYPE_TYPE__FORM:
                setForm((FormType)newValue);
                return;
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW:
                setPageFlow((PageFlowType)newValue);
                return;
            case WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG:
                setExtensionConfig((ChannelExtentionType)newValue);
                return;
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF:
                setPageFlowRef((PageFlowRefType)newValue);
                return;
            case WPPackage.WORK_TYPE_TYPE__GUID:
                setGuid((String)newValue);
                return;
            case WPPackage.WORK_TYPE_TYPE__NAME:
                setName((String)newValue);
                return;
            case WPPackage.WORK_TYPE_TYPE__VERSION:
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
            case WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES:
                unsetExtendedProperties();
                return;
            case WPPackage.WORK_TYPE_TYPE__FORM:
                setForm((FormType)null);
                return;
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW:
                setPageFlow((PageFlowType)null);
                return;
            case WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG:
                setExtensionConfig((ChannelExtentionType)null);
                return;
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF:
                setPageFlowRef((PageFlowRefType)null);
                return;
            case WPPackage.WORK_TYPE_TYPE__GUID:
                setGuid(GUID_EDEFAULT);
                return;
            case WPPackage.WORK_TYPE_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case WPPackage.WORK_TYPE_TYPE__VERSION:
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
            case WPPackage.WORK_TYPE_TYPE__EXTENDED_PROPERTIES:
                return isSetExtendedProperties();
            case WPPackage.WORK_TYPE_TYPE__FORM:
                return form != null;
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW:
                return pageFlow != null;
            case WPPackage.WORK_TYPE_TYPE__EXTENSION_CONFIG:
                return extensionConfig != null;
            case WPPackage.WORK_TYPE_TYPE__PAGE_FLOW_REF:
                return pageFlowRef != null;
            case WPPackage.WORK_TYPE_TYPE__GUID:
                return GUID_EDEFAULT == null ? guid != null : !GUID_EDEFAULT.equals(guid);
            case WPPackage.WORK_TYPE_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case WPPackage.WORK_TYPE_TYPE__VERSION:
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
        result.append(" (guid: ");
        result.append(guid);
        result.append(", name: ");
        result.append(name);
        result.append(", version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //WorkTypeTypeImpl
